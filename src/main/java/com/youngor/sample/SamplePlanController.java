package com.youngor.sample;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.exception.BusinessException;
import com.mawujun.utils.page.Pager;
import com.youngor.permission.ShiroUtils;
import com.youngor.utils.MapParams;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/samplePlan")
public class SamplePlanController {

	@Resource
	private SamplePlanService samplePlanService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/samplePlan/query.do")
	@ResponseBody
	public Pager<SamplePlan> query(Pager<SamplePlan> pager){
		
		return samplePlanService.queryPage(pager);
	}

//	@RequestMapping("/samplePlan/queryAll.do")
//	@ResponseBody
//	public List<SamplePlan> queryAll() {	
//		List<SamplePlan> samplePlanes=samplePlanService.queryAll();
//		return samplePlanes;
//	}
//	

	@RequestMapping("/samplePlan/load.do")
	public SamplePlan load(String id) {
		return samplePlanService.get(id);
	}
	
	@RequestMapping("/samplePlan/create.do")
	//@ResponseBody
	public SamplePlan create(@RequestBody SamplePlan samplePlan) {
		//samplePlan.setPlspno("222");
		//samplePlan.setOrmtno("201604");
		
		
		
		samplePlan.setPlspno(samplePlan.getOrmtno()+samplePlan.getPlspnm());
		SamplePlan aaa=samplePlanService.get(samplePlan.getPlspno());
		if(aaa!=null){
			throw new BusinessException("该样衣编号已经存在!");
		}
		samplePlan.setRgdt(new Date());
		samplePlan.setRgsp(ShiroUtils.getLoginName());
		samplePlan.setLmdt(new Date());
		samplePlan.setLmsp(ShiroUtils.getLoginName());
		samplePlan.setPlstat(1);
		samplePlanService.create(samplePlan);
		return samplePlan;
	}
	
	@RequestMapping("/samplePlan/update.do")
	//@ResponseBody
	public  SamplePlan update(@RequestBody SamplePlan samplePlan) {
		if(samplePlan.getPlstat()==1){
			throw new BusinessException("已经锁定,不能更新!");
		}
		samplePlan.setLmdt(new Date());
		samplePlan.setLmsp(ShiroUtils.getLoginName());
		samplePlanService.update(samplePlan);
		return samplePlan;
	}
	
//	@RequestMapping("/samplePlan/deleteById.do")
//	//@ResponseBody
//	public String deleteById(String id) {
//		
//		samplePlanService.deleteById(id);
//		return id;
//	}
	
	@RequestMapping("/samplePlan/destroy.do")
	//@ResponseBody
	public SamplePlan destroy(@RequestBody SamplePlan samplePlan) {
		samplePlanService.delete(samplePlan);
		return samplePlan;
	}
	
	
	@RequestMapping("/samplePlan/lockOrunlock.do")
	@ResponseBody
	public void lockOrunlock(String plspno,Integer plspst) {
		samplePlanService.lockOrunlock(plspno, plspst);
	}
	
	@RequestMapping("/samplePlan/export.do")
	@ResponseBody
	public void export(MapParams params,HttpServletResponse response) throws Exception {
		//samplePlanService.lockOrunlock(plspno, plspst);
		//System.out.println(params);
		XSSFWorkbook wb = new XSSFWorkbook();    
		Sheet sheet1 = wb.createSheet("资料");
		crreateTitle(wb,sheet1);
		
		List<SamplePlanVO> list=samplePlanService.queryList4Export(params);
		if(list!=null){
			crreateData(wb,sheet1,list);
		}
		
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");    
        response.setHeader("Content-disposition", "attachment;filename="+new String("企划样衣资料".getBytes(),"ISO8859-1")+".xlsx");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
	}
	
	private void crreateTitle(XSSFWorkbook wb,Sheet sheet1){
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
	    cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		
		Font font = wb.createFont();
	    //font.setFontHeightInPoints((short)15);
	    font.setFontName("Courier New");
	    cellStyle.setFont(font);
		 
		Row title = sheet1.createRow((short)0);
		Cell cell0 = title.createCell(0);
		cell0.setCellValue("企划样衣编号");
		cell0.setCellStyle(cellStyle);
		
		Cell cell1 = title.createCell(1);
		cell1.setCellValue("品牌");
		cell1.setCellStyle(cellStyle);
		
		Cell cell2 = title.createCell(2);
		cell2.setCellValue("年份");
		cell2.setCellStyle(cellStyle);
		
		Cell cell3 = title.createCell(3);
		cell3.setCellValue("季节");
		cell3.setCellStyle(cellStyle);
		
		Cell cell4 = title.createCell(4);
		cell4.setCellValue("大系列");
		cell4.setCellStyle(cellStyle);
		
		Cell cell5 = title.createCell(5);
		cell5.setCellValue("品牌系列");
		cell5.setCellStyle(cellStyle);
		
		Cell cell6 = title.createCell(6);
		cell6.setCellValue("大类");
		cell6.setCellStyle(cellStyle);
		
		Cell cell7 = title.createCell(7);
		cell7.setCellValue("小类");
		cell7.setCellStyle(cellStyle);
		
		Cell cell8 = title.createCell(8);
		cell8.setCellValue("系列");
		cell8.setCellStyle(cellStyle);
		
		Cell cell9 = title.createCell(9);
		cell9.setCellValue("定位");
		cell9.setCellStyle(cellStyle);
		
		Cell cell10 = title.createCell(10);
		cell10.setCellValue("上市批次");
		cell10.setCellStyle(cellStyle);
		
		Cell cell11 = title.createCell(11);
		cell11.setCellValue("出厂价");
		cell11.setCellStyle(cellStyle);
		
		Cell cell12 = title.createCell(12);
		cell12.setCellValue("零售价");
		cell12.setCellStyle(cellStyle);
		
		Cell cell13 = title.createCell(13);
		cell13.setCellValue("企划倍率");
		cell13.setCellStyle(cellStyle);
		
		Cell cell14 = title.createCell(14);
		cell14.setCellValue("企划成本");
		cell14.setCellStyle(cellStyle);
		
	}
	
	private void crreateData(XSSFWorkbook wb,Sheet sheet1,List<SamplePlanVO> list){
		int row_index=1;
		for(SamplePlanVO vo:list){
			Row row = sheet1.createRow((short)row_index);
			row_index++;
			
			Cell cell0 = row.createCell(0);
			cell0.setCellValue(vo.getPlspnm());
			
			Cell cell1 = row.createCell(1);
			cell1.setCellValue(vo.getBradno_name());
			
			Cell cell2 = row.createCell(2);
			cell2.setCellValue(vo.getSpyear());
			
			Cell cell3 = row.createCell(3);
			cell3.setCellValue(vo.getSpsean_name());
			
			Cell cell4 = row.createCell(4);
			cell4.setCellValue(vo.getSpbseno_name());
			
			Cell cell5 = row.createCell(5);
			cell5.setCellValue(vo.getSprseno_name());
			
			Cell cell6 = row.createCell(6);
			cell6.setCellValue(vo.getSpclno_name());
			
			Cell cell7 = row.createCell(7);
			cell7.setCellValue(vo.getSptyno_name());
			
			Cell cell8 = row.createCell(8);
			cell8.setCellValue(vo.getSpseno_name());
			
			Cell cell9 = row.createCell(9);
			cell9.setCellValue(vo.getSplcno_name());
			
			Cell cell10 = row.createCell(10);
			cell10.setCellValue(vo.getSpbano_name());
			
			Cell cell11 = row.createCell(11);
			cell11.setCellValue(vo.getSpftpr());
			
			Cell cell12 = row.createCell(12);
			cell12.setCellValue(vo.getSprtpr());
			
			Cell cell13 = row.createCell(13);
			cell13.setCellValue(vo.getSpplrd());
			
			Cell cell14 = row.createCell(14);
			cell14.setCellValue(vo.getPlctpr());
			

			
		}
	}

	
}
