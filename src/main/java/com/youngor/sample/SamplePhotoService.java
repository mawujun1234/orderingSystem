package com.youngor.sample;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mawujun.repository.idEntity.UUIDGenerator;
import com.mawujun.service.AbstractService;
import com.youngor.permission.ShiroUtils;


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
	@Autowired
	private SamplePlanRepository samplePlanRepository;
	
	@Override
	public SamplePhotoRepository getRepository() {
		return samplePhotoRepository;
	}
	
	public String create(SamplePhoto samplePhoto,MultipartFile imageFile,String contextPath) throws IOException {
		String id=UUIDGenerator.generate();
		samplePhoto.setId(id);
		samplePhoto.setPhotnm(imageFile.getOriginalFilename());
		samplePhoto.setRgdt(new Date());
		samplePhoto.setRgsp(ShiroUtils.getLoginName());
				
		InputStream stream = imageFile.getInputStream();
		String[] aa=samplePhoto.getPhotnm().split("\\.");
		//以照片的id作为文件名的id
		String imgnm=id+"."+aa[aa.length-1];
		
		//获取订货会编号
		String ormtno=samplePlanRepository.queryOrmtno(samplePhoto.getSampno());
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

}