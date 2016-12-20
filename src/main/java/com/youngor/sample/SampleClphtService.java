package com.youngor.sample;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
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


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SampleClphtService extends AbstractService<SampleClpht, String>{

	@Autowired
	private SampleClphtRepository sampleClphtRepository;
	
	@Override
	public SampleClphtRepository getRepository() {
		return sampleClphtRepository;
	}

	public List<SampleClpht> queryAll(String clppno) {
		return sampleClphtRepository.queryAll(clppno);
	}
	
	/**
	 * 返回照片的序号，一共几张照片了
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param sampleClpht
	 * @param imageFile
	 * @param contextPath
	 * @return
	 * @throws IOException
	 */
	public Integer create(SampleClpht sampleClpht,MultipartFile imageFile,String contextPath) throws IOException {
		//获取订货会编号
		String ormtno=sampleClpht.getOrmtno();
		//String id=ormtno+"_"+sampleClpht.getSampno();//UUIDGenerator.generate();
		String id=sampleClpht.getClppno();
//		SampleDesign sampleDesign=sampleDesignRepository.get(sampleClpht.getSampno());//.getSampleDesignBySampno(ormtno, sampleClpht.getSampno());
//		Integer photno=1;
//		if(sampleDesign.getPhotno() ==null || "".equals(sampleDesign.getPhotno())){
//			sampleDesign.setPhotno(photno+"");
//			id=id+"_"+sampleDesign.getPhotno();
//		} else {
//			photno=Integer.parseInt(sampleDesign.getPhotno());
//			photno++;
//			id=id+"_"+photno;
//			sampleDesign.setPhotno(photno+"");
//		}
//		sampleClphtRepository.update(sampleDesign);
		
		//获取已经有几张照片了
		int count=sampleClphtRepository.queryCount(Cnd.count().andEquals(M.SampleClpht.clppno, sampleClpht.getClppno()));
		id=id+count;
		sampleClpht.setId(id);imageFile.getName();
		sampleClpht.setPhotnm(imageFile.getOriginalFilename());

				
		InputStream stream = imageFile.getInputStream();
		String[] aa=sampleClpht.getPhotnm().split("\\.");
		//以照片的id作为文件名的id
		String imgnm=id+"."+aa[aa.length-1];
		
		
		//sampleClpht.setImgnm("/" +ormtno+"/"+imgnm);
		//这个是网页访问的地址
		sampleClpht.setImgnm("/photoes/"+ormtno+"/dapei/"+imgnm);
		
		//直接保存在另一个项目中，项目名称是photoes
		contextPath=ContextUtils.getPhotoBakDir();
		File dir=new File(contextPath + File.separator+"photoes"+File.separator+ormtno+File.separator+"dapei");
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
		
		//对上传文件进行备份
		File photo_bak=new File(contextPath+ File.separator+"photoes_bak"+File.separator+ormtno+File.separator+"dapei");
		if(!photo_bak.exists()){
			photo_bak.mkdirs();
		}
		if(!photo_bak.exists()){
			throw new BusinessException("上传备份目录不存在，上传失败!");
		}
		try {
			FileUtils.copyFile(file, new File(contextPath+ File.separator+"photoes_bak"+File.separator+ormtno+File.separator+"dapei"+File.separator+imgnm));
			//FileUtils.copyf
		} catch(Exception e) {
			throw new BusinessException("上传到备份目录失败!");
		}
		super.create(sampleClpht);
		return 1;
		
	}
}
