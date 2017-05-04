package com.youngor.sample;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.youngor.permission.ShiroUtils;
import com.youngor.utils.ContextUtils;
import com.youngor.utils.M;

import net.coobird.thumbnailator.Thumbnails;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SamplePhotoService extends AbstractService<SamplePhoto, String>{

	@Autowired
	private SamplePhotoRepository samplePhotoRepository;
//	@Autowired
//	private SamplePlanRepository samplePlanRepository;
	@Autowired
	private SampleDesignRepository sampleDesignRepository;
	@Autowired
	private SampleClphtRepository sampleClphtRepository;
	
	@Override
	public SamplePhotoRepository getRepository() {
		return samplePhotoRepository;
	}
	/**
	 * 返回照片的序号，一共几张照片了
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param samplePhoto
	 * @param imageFile
	 * @param contextPath
	 * @return
	 * @throws IOException
	 */
	public Integer create(SamplePhoto samplePhoto,MultipartFile imageFile,String contextPath) throws IOException {
		//获取订货会编号
		String ormtno=samplePhoto.getOrmtno();
		//String id=ormtno+"_"+samplePhoto.getSampno();//UUIDGenerator.generate();
		String id=samplePhoto.getSampno();
		SampleDesign sampleDesign=sampleDesignRepository.get(samplePhoto.getSampno());//.getSampleDesignBySampno(ormtno, samplePhoto.getSampno());
		Integer photno=1;
		if(sampleDesign.getPhotno() ==null || "".equals(sampleDesign.getPhotno())){
			sampleDesign.setPhotno(photno+"");
			id=id+"_"+sampleDesign.getPhotno();
		} else {
			photno=Integer.parseInt(sampleDesign.getPhotno());
			photno++;
			id=id+"_"+photno;
			sampleDesign.setPhotno(photno+"");
		}
		sampleDesignRepository.update(sampleDesign);
		
		samplePhoto.setId(id);
		samplePhoto.setPhotnm(imageFile.getOriginalFilename());
		samplePhoto.setRgdt(new Date());
		samplePhoto.setRgsp(ShiroUtils.getLoginName());
				
		InputStream stream = imageFile.getInputStream();
		String[] aa=samplePhoto.getPhotnm().split("\\.");
		//以照片的id作为文件名的id
		String imgnm=id+"."+aa[aa.length-1];
		
		
		//samplePhoto.setImgnm("/" +ormtno+"/"+imgnm);
		//这个是网页访问的地址
		samplePhoto.setImgnm("/photoes/"+ormtno+"/"+imgnm);
		
		//直接保存在另一个项目中，项目名称是photoes
		contextPath=ContextUtils.getPhotoBakDir();
		File dir=new File(contextPath + File.separator+"photoes"+File.separator+ormtno);
		//直接保存在另一个项目中，项目名称是photoes
		//File dir=new File(ContextUtils.getPhotoBakDir() + File.separator+"photoes");
		if(!dir.exists()){
			dir.mkdirs();
		}
		File file=new File(dir.getAbsolutePath() + File.separator + imgnm);
		if(file.exists()){
			file.delete();
		}
		FileOutputStream outputStream = new FileOutputStream(file);
		int byteCount = 0;
		byte[] bytes = new byte[1024];
		while ((byteCount = stream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, byteCount);
		}
		outputStream.close();
		stream.close();
		
		File dir_thumb=new File(dir.getAbsolutePath() + File.separator +"thumb");
		if(!dir_thumb.exists()){
			dir_thumb.mkdirs();
		}
		File file_thumb=new File(dir.getAbsolutePath() + File.separator +"thumb" + File.separator+imgnm);
		samplePhoto.setThumb("/photoes/"+ormtno+"/thumb/"+imgnm);
		createThumb(file,file_thumb);
		
		//对上传文件进行备份
		File photo_bak=new File(contextPath+ File.separator+"photoes_bak"+File.separator+ormtno);
		if(!photo_bak.exists()){
			photo_bak.mkdirs();
		}
		if(!photo_bak.exists()){
			throw new BusinessException("上传备份目录不存在，上传失败!");
		}
		try {
			FileUtils.copyFile(file, new File(contextPath+ File.separator+"photoes_bak"+File.separator+ormtno+File.separator+imgnm));
			//FileUtils.copyf
		} catch(Exception e) {
			throw new BusinessException("上传到备份目录失败!");
		}
		super.create(samplePhoto);
		return photno;
		
	}
	public void createThumb(File file,File file_thumb) {
		if(file.exists() && file.isFile()){
			//System.out.println(file.getAbsolutePath());
			//samplePhotoService.update(Cnd.update().set("thumb", imgnm).andEquals(M.SamplePhoto.id, photo.getId()));
			if(file.length()>204800){//200kb
				try {
					if(file.length()>2048000){
						Thumbnails.of(file) .scale(0.1f).toFile(file_thumb);	 
					} else if(file.length()>1024000){
						Thumbnails.of(file) .scale(0.25f).toFile(file_thumb);	 
					} else if(file.length()>512000){
						Thumbnails.of(file) .scale(0.8f).toFile(file_thumb);	 
					}  else {
						FileUtils.copyFile(file, file_thumb);
					}
					   
				} catch (IOException e) {
					System.out.println(e);
					throw new BusinessException("请把图片改成RGB模式，再上传");
				}
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					try {
//						FileUtils.copyFile(file, destFile);
//					} catch (IOException ex) {
//						// TODO Auto-generated catch block
//						ex.printStackTrace();
//						thumb_imgnm=imgnm;//如果报错，就用原始图
//					}
				}  
			} else {
//				try {
//					FileUtils.copyFile(file, destFile);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					thumb_imgnm=imgnm;//如果报错，就用原始图
//				}
				
			}
	}
	/**
	 * SampleDesignService中也有相应的文件删除
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param samplePhoto
	 * @param contextPath
	 */
	public Integer delete(SamplePhoto samplePhoto,String contextPath) {
		
		contextPath=ContextUtils.getPhotoBakDir();
		
		
		super.delete(samplePhoto);
		//如果全部删除了，修改样衣的照片上传状态为不可以上传
		Integer count=sampleDesignRepository.count_sampleDesign_photo_num(samplePhoto.getSampno());
		if(count==null || count==0){
			sampleDesignRepository.update(Cnd.update().set(M.SampleDesign.photno, null).andEquals(M.SampleDesign.sampno, samplePhoto.getSampno()));
		} else {
			sampleDesignRepository.update(Cnd.update().set(M.SampleDesign.photno, count).andEquals(M.SampleDesign.sampno, samplePhoto.getSampno()));
		}
		String filepath=contextPath+samplePhoto.getImgnm();
		File file=new File(filepath);
		if(file.exists()){
			file.delete();
		}
		return count;
		
	}
	
	public void thumbCreate(String ormtno,String common_path) {
		//String ormtno=ContextUtils.getFirstOrdmt().getOrmtno();
		
		
		
		//ormtno="201607";
		//common_path="F:\\bak";
		
		List<SamplePhoto> list=this.query(Cnd.select().andLike(M.SamplePhoto.id, ormtno));
		
		
		//String thumbpath="/opt/apache-tomcat-8.0.36/webapps";
		
		
		//String thumb="/photoes/"+ormtno+"/thumb/";
		for(SamplePhoto photo:list){
			String imgnm=photo.getImgnm();
			String thumb_imgnm=imgnm.replace("/photoes/"+ormtno+"/", "/photoes/"+ormtno+"/thumb/");
			//System.out.println("F:\201607\thumb"+imgnm);
			File file=new File(common_path+imgnm);
			File destFile=new File(common_path+thumb_imgnm);
			if(file.exists() && file.isFile()){
				//System.out.println(file.getAbsolutePath());
				//samplePhotoService.update(Cnd.update().set("thumb", imgnm).andEquals(M.SamplePhoto.id, photo.getId()));
				if(file.length()>204800){//200kb
					try {
						if(file.length()>2048000){
							Thumbnails.of(file) .scale(0.1f).toFile(destFile);	 
						} else if(file.length()>1024000){
							Thumbnails.of(file) .scale(0.25f).toFile(destFile);	 
						} else if(file.length()>512000){
							Thumbnails.of(file) .scale(0.8f).toFile(destFile);	 
						}  else {
							FileUtils.copyFile(file, destFile);
						}
						   
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						try {
							FileUtils.copyFile(file, destFile);
						} catch (IOException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
							thumb_imgnm=imgnm;//如果报错，就用原始图
						}
					}  
				} else {
					try {
						FileUtils.copyFile(file, destFile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						thumb_imgnm=imgnm;//如果报错，就用原始图
					}
					
				}
				//photo.setThumb(thumb_imgnm);
				this.update(Cnd.update().set(M.SamplePhoto.thumb, thumb_imgnm).andEquals(M.SamplePhoto.id, photo.getId()));
			}
			
		}
	}
	
	public void thumbCreate_dapei(String ormtno,String common_path) {
		//String ormtno=ContextUtils.getFirstOrdmt().getOrmtno();
		//String common_path="/opt/apache-tomcat-8.0.36/webapps";
		
		
		//ormtno="201607";
		//common_path="F:\\bak";
		
		List<SampleClpht> list=sampleClphtRepository.queryAll();
		
		
		//String thumbpath="/opt/apache-tomcat-8.0.36/webapps";
		
		
		//String thumb="/photoes/"+ormtno+"/thumb/";
		for(SampleClpht photo:list){
			String imgnm=photo.getImgnm();
			String thumb_imgnm=imgnm.replace("/photoes/"+ormtno+"/dapei/", "/photoes/"+ormtno+"/dapei/thumb/");
			//System.out.println("F:\201607\thumb"+imgnm);
			File file=new File(common_path+imgnm);
			File destFile=new File(common_path+thumb_imgnm);
			if(file.exists() && file.isFile()){
				//System.out.println(file.getAbsolutePath());
				//samplePhotoService.update(Cnd.update().set("thumb", imgnm).andEquals(M.SamplePhoto.id, photo.getId()));
				if(file.length()>204800){//200kb
					try {
						if(file.length()>2048000){
							Thumbnails.of(file) .scale(0.1f).toFile(destFile);	 
						} else if(file.length()>1024000){
							Thumbnails.of(file) .scale(0.25f).toFile(destFile);	 
						} else if(file.length()>512000){
							Thumbnails.of(file) .scale(0.8f).toFile(destFile);	 
						}  else {
							FileUtils.copyFile(file, destFile);
						} 
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						try {
							FileUtils.copyFile(file, destFile);
						} catch (IOException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
							thumb_imgnm=imgnm;//如果报错，就用原始图
						}
					}  
				} else {
					try {
						FileUtils.copyFile(file, destFile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						thumb_imgnm=imgnm;//如果报错，就用原始图
					}
					
				}
				//photo.setThumb(thumb_imgnm);
				//sampleClphtRepository.update(photo);
				sampleClphtRepository.update(Cnd.update().set(M.SampleClpht.thumb, thumb_imgnm).andEquals(M.SampleClpht.id, photo.getId()));
			}

		}
	}

}
