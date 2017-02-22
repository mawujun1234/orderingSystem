package com.youngor.sample;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mawujun.exception.BusinessException;
import com.mawujun.service.AbstractService;
import com.youngor.utils.MapParams;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SampleProdService extends AbstractService<SampleProd, com.youngor.sample.SampleProd.PK>{

	@Autowired
	private SampleProdRepository sampleProdRepository;
	
	@Override
	public SampleProdRepository getRepository() {
		return sampleProdRepository;
	}
	
	public void createOrUpdate(SampleProd sampleProd) {
		//PK pk=sampleProd.geetPK();
		sampleProdRepository.createOrUpdate(sampleProd);
	}
	
	public List<SampleProdVO> queryPage(MapParams params) {
		return sampleProdRepository.queryPage(params.getParams());
	}
	
	public void onimport(MultipartFile imageFile) throws IOException, EncryptedDocumentException, InvalidFormatException {
		InputStream stream = imageFile.getInputStream();
		String ormtno=null;
		Workbook wb = WorkbookFactory.create(stream);
		Sheet sheet = wb.getSheetAt(0);
		int rownum=sheet.getLastRowNum();
		for(int i=1;i<=rownum;i++){
			SampleProd.PK pk=new SampleProd.PK();
			Row row = sheet.getRow(i);
			Cell cell = row.getCell(0);
			if(cell!=null){
				pk.setOrmtno(cell.getStringCellValue());
				ormtno=pk.getOrmtno();
			}
			cell = row.getCell(1);
			if(cell!=null){
				pk.setSampno(cell.getStringCellValue());
			}
			cell = row.getCell(2);
			if(cell!=null){
				pk.setSuitno(cell.getStringCellValue());
			}
			
			SampleProd sampleProd=sampleProdRepository.get(pk);
			if(sampleProd==null){
				sampleProd=new SampleProd();
				sampleProd.setOrmtno(pk.getOrmtno());
				sampleProd.setSampno(pk.getSampno());
				sampleProd.setSuitno(pk.getSuitno());
			}
			
			cell = row.getCell(5);
			if(cell!=null){
				
				if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
					Double dd=cell.getNumericCellValue();
					sampleProd.setPrseqm(dd.intValue());
				} else if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
					String aa=cell.getStringCellValue();
					if(aa!=null){
						sampleProd.setPrseqm(Integer.parseInt(aa));
					}
					
				}
				
			}
			cell = row.getCell(6);
			if(cell!=null){
				if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
					sampleProd.setProdnm(cell.getNumericCellValue()+"");
				} else if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
					sampleProd.setProdnm(cell.getStringCellValue());
				}
				
			}
			
			cell = row.getCell(7);
			if(cell!=null){
	
				if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
					sampleProd.setPrprpt(cell.getNumericCellValue()+"");
				} else if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
					sampleProd.setPrprpt(cell.getStringCellValue());
				} 
				
			} else {
				sampleProd.setPrprpt("V01");
			}
				
			this.createOrUpdate(sampleProd);
			
			
		}
		
		//判断货号名称是否有重复，如果有重复，就报错
		List<Map<String,Object>> list=sampleProdRepository.check_repeat_prodnm();
		if(list!=null && list.size()!=0){
			StringBuilder builder=new StringBuilder();
			for(Map<String,Object> map:list){
				builder.append(","+map.get("PRODNM"));
			}
			throw new BusinessException("导入成功，但是下列货号重复:"+builder.substring(1));
		}
		
		//更新所有货号的零售价价，成本价
		sampleProdRepository.updatePrice(ormtno);
	}

}
