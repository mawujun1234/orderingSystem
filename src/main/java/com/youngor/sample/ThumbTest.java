package com.youngor.sample;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.mawujun.exception.BusinessException;

import net.coobird.thumbnailator.Thumbnails;

public class ThumbTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file=new File("C:\\Users\\mawujun.YOUNGOR\\Desktop\\1111\\201707KZ023KZ023A_1.jpg");
		File file_thumb=new File("C:\\Users\\mawujun.YOUNGOR\\Desktop\\1111\\201707KZ023KZ023A_1_thumb.jpg");
		createThumb(file,file_thumb);
	}
	
	
	public static void createThumb(File file,File file_thumb) {
		if(file.exists() && file.isFile()){
			//System.out.println(file.getAbsolutePath());
			//samplePhotoService.update(Cnd.update().set("thumb", imgnm).andEquals(M.SamplePhoto.id, photo.getId()));
			if(file.length()>102400){//200kb
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
				try {
					FileUtils.copyFile(file, file_thumb);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//thumb_imgnm=imgnm;//如果报错，就用原始图
				}
				
			}
	}

}
