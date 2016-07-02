package com.youngor.sample;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.naming.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.repository.idEntity.UUIDGenerator;
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
public class SamplePhotoService extends AbstractService<SamplePhoto, String>{

	@Autowired
	private SamplePhotoRepository samplePhotoRepository;
//	@Autowired
//	private SamplePlanRepository samplePlanRepository;
	@Autowired
	private SampleDesignRepository sampleDesignRepository;
	
	@Override
	public SamplePhotoRepository getRepository() {
		return samplePhotoRepository;
	}
	
	public String create(SamplePhoto samplePhoto,MultipartFile imageFile,String contextPath) throws IOException {
		//获取订货会编号
		String ormtno=ContextUtils.getFirstOrdmt().getOrmtno();// samplePlanRepository.queryOrmtno(samplePhoto.getSampno());
		String id=ormtno+"_"+samplePhoto.getSampno();//UUIDGenerator.generate();
		SampleDesign sampleDesign=sampleDesignRepository.get(samplePhoto.getSampno());//.getSampleDesignBySampno(ormtno, samplePhoto.getSampno());
		if(sampleDesign.getPhotno() ==null || "".equals(sampleDesign.getPhotno())){
			sampleDesign.setPhotno("1");
			id=id+"_"+sampleDesign.getPhotno();
		} else {
			Integer photno=Integer.parseInt(sampleDesign.getPhotno());
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
		
		
		samplePhoto.setImgnm("/" +ormtno+"/"+imgnm);
		File dir=new File(contextPath + File.separator +ormtno);
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		FileOutputStream outputStream = new FileOutputStream(dir.getAbsolutePath() + File.separator + imgnm);
		int byteCount = 0;
		byte[] bytes = new byte[1024];
		while ((byteCount = stream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, byteCount);
		}
		outputStream.close();
		stream.close();
		
		super.create(samplePhoto);
		return id;
		
	}
	
	public void delete(SamplePhoto samplePhoto,String contextPath) {
		super.delete(samplePhoto);
		//如果全部删除了，修改样衣的照片上传状态为不可以上传
		Integer count=sampleDesignRepository.count_sampleDesign_photo_num(samplePhoto.getSampno());
		if(count==null || count==0){
			sampleDesignRepository.update(Cnd.update().set(M.SampleDesign.photno, null).andEquals(M.SampleDesign.sampno, samplePhoto.getSampno()));
		}
		String filepath=contextPath+samplePhoto.getImgnm();
		File file=new File(filepath);
		if(file.exists()){
			file.delete();
		}
		
	}

}
