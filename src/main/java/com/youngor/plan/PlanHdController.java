package com.youngor.plan;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
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
//@RequestMapping("/planHd")
public class PlanHdController {

	@Resource
	private PlanHdService planHdService;




	@RequestMapping("/planHd/queryHdGrid.do")
	@ResponseBody
	public List<PlanHdVO> queryHdGrid(String ormtno,String yxgsno,String bradno,String spclno) {	
		List<PlanHdVO> planHdes=planHdService.queryHdGrid(ormtno, yxgsno, bradno, spclno);
		return planHdes;
	}
	

	@RequestMapping("/planHd/update.do")
	@ResponseBody
	public  PlanHd update(@RequestBody PlanHd planHd) {
		planHdService.createOrUpdate(planHd);
		return planHd;
	}
	
	@RequestMapping("/planHd/onPass.do")
	@ResponseBody
	public  String onPass(String ormtno,String yxgsno,String bradno,String spclno) {
//		//如果状态是编辑中，但是不是当前组织单元 就不能提交审批
//		if(!ShiroUtils.getAuthenticationInfo().inTheOrg(ordorg)){
//			throw new BusinessException("你没有权限进行提交!");
//		}

		planHdService.onPass(ormtno, yxgsno, bradno, spclno);
		return "success";
	}
	
	@RequestMapping("/planHd/exportHdGrid.do")
	@ResponseBody
	public void exportHdGrid(String ormtno,String yxgsno,String bradno,String spclno,HttpServletResponse response) throws IOException {	
		List<PlanHdVO> list=planHdService.queryHdGrid(ormtno, yxgsno, bradno, spclno);
		
		XSSFWorkbook wb = new XSSFWorkbook();    
		Sheet sheet1 = wb.createSheet("总部指标");
		LinkedHashMap<String,String> titles=new LinkedHashMap<String,String>();
		
		titles.put("orgnm", "渠道代码");
		titles.put("bradno_name", "品牌");
		titles.put("spclno_name", "大类");
		titles.put("plmtqt", "指标数量");
		titles.put("plmtam", "指标金额");
		


		crreateTitle_exportHdGrid(wb,sheet1,titles);
		
		crreateData_exportHdGrid(wb,sheet1,titles,list);

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");    
        response.setHeader("Content-disposition", "attachment;filename="+new String("总部指标".getBytes(),"ISO8859-1")+".xlsx");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
	}
	
	private void crreateTitle_exportHdGrid(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles){
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
	    cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		
		Font font = wb.createFont();
	    //font.setFontHeightInPoints((short)18);
	    font.setFontName("Courier New");
	    cellStyle.setFont(font);
	    
		 
	    Row title = sheet1.createRow((short)0);
		
		int i=0;
		for(Entry<String,String> entry:titles.entrySet()){
			Cell cell = title.createCell(i);
			cell.setCellValue(entry.getValue());
			cell.setCellStyle(cellStyle);
			i++;
		}
	}
	
	private void crreateData_exportHdGrid(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles,List<PlanHdVO> list){
		if(list==null || list.size()==0){
			return;
		}
		for(int i=0;i<list.size();i++){
			PlanHdVO planHdVO=list.get(i);
			Row row = sheet1.createRow((short)i+1);
			Cell cell = row.createCell(0);
			cell.setCellValue(planHdVO.getOrgnm());
			
			cell = row.createCell(1);
			cell.setCellValue(planHdVO.getBradno_name());
			
			cell = row.createCell(2);
			cell.setCellValue(planHdVO.getSpclno_name());
			
			cell = row.createCell(3);
			if(planHdVO.getPlmtqt()!=null){
				cell.setCellValue(planHdVO.getPlmtqt());
			}
			
			cell = row.createCell(4);
			if(planHdVO.getPlmtam()!=null){
				cell.setCellValue(planHdVO.getPlmtam());
			}
	
//			int j=0;
//			for(Entry<String,String> entry:titles.entrySet()){
//				Cell cell = row.createCell(j);
//				j++;
//				if(map.get(entry.getKey())!=null){
//					cell.setCellValue(map.get(entry.getKey()).toString());
//				}
//			}
		}
	}
	
//	@RequestMapping("/planHd/load.do")
//	public PlanHd load(com.youngor.plan.PlanHd.PK id) {
//		return planHdService.get(id);
//	}
//	
//	@RequestMapping("/planHd/create.do")
//	@ResponseBody
//	public PlanHd create(@RequestBody PlanHd planHd) {
//		planHdService.create(planHd);
//		return planHd;
//	}
//	
//	@RequestMapping("/planHd/update.do")
//	@ResponseBody
//	public  PlanHd update(@RequestBody PlanHd planHd) {
//		planHdService.update(planHd);
//		return planHd;
//	}
//	
//	@RequestMapping("/planHd/deleteById.do")
//	@ResponseBody
//	public com.youngor.plan.PlanHd.PK deleteById(com.youngor.plan.PlanHd.PK id) {
//		planHdService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/planHd/destroy.do")
//	@ResponseBody
//	public PlanHd destroy(@RequestBody PlanHd planHd) {
//		planHdService.delete(planHd);
//		return planHd;
//	}
	
	
}
