package com.youngor.plan;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.multipart.MultipartFile;

import com.youngor.utils.MapParams;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/planOrg")
public class PlanOrgController {

	@Resource
	private PlanOrgService planOrgService;


//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/planOrg/queryPager.do")
//	@ResponseBody
//	public Pager<PlanOrg> queryPager(Pager<PlanOrg> pager){
//		
//		return planOrgService.queryPage(pager);
//	}

	@RequestMapping("/planOrg/queryPlanOrgdtlVO.do")
	@ResponseBody
	public List<PlanOrgdtlVO> queryPlanOrgdtlVO(MapParams params) {	
		List<PlanOrgdtlVO> planOrges=new ArrayList<PlanOrgdtlVO>();//planOrgService.queryPlanOrgdtlVO(params);
		if(params.getParams().get("yxgsno")==null || "".equals(params.getParams().get("yxgsno"))){
			planOrges= planOrgService.queryPlanOrgdtlVO_all(params);
		} else {
			planOrges= planOrgService.queryPlanOrgdtlVO(params);
		}
		return planOrges;
	}
	
	@RequestMapping("/planOrg/createOrUpdate.do")
	@ResponseBody
	public  PlanOrgdtlVO createOrUpdate(@RequestBody PlanOrgdtlVO planOrgdtlVO) {
		planOrgService.update(planOrgdtlVO);
		return planOrgdtlVO;
	}
	@RequestMapping("/planOrg/onSubmit.do")
	@ResponseBody
	public  String onSubmit(String ormtno,String yxgsno, String ordorg,String bradno) {
		planOrgService.onSubmit(ormtno,yxgsno,ordorg,bradno);
		return "{success:true}";
	}
	@RequestMapping("/planOrg/onPass.do")
	@ResponseBody
	public  String onPass(String ormtno,String yxgsno, String ordorg,String bradno) {
		planOrgService.onPass(ormtno,yxgsno,ordorg,bradno);
		return "{success:true}";
	}
	@RequestMapping("/planOrg/onBack.do")
	@ResponseBody
	public  String onBack(String ormtno, String yxgsno,String ordorg,String bradno) {
		planOrgService.onBack(ormtno,yxgsno,ordorg,bradno);
		return "{success:true}";
	}
	
	
	@RequestMapping("/planOrg/export.do")
	@ResponseBody
	public void export(MapParams params,HttpServletResponse response) throws Exception {
		//samplePlanService.lockOrunlock(plspno, plspst);
		System.out.println(params);
		XSSFWorkbook wb = new XSSFWorkbook();    
		Sheet sheet1 = wb.createSheet("订货指标");
		crreateTitle(wb,sheet1);
		
		createData(wb,sheet1,params);

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");    
        response.setHeader("Content-disposition", "attachment;filename="+new String("订货指标".getBytes(),"ISO8859-1")+".xlsx");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
	}
	
	private void createData(XSSFWorkbook wb,Sheet sheet1,MapParams params){
		List<PlanOrgdtlVO> planOrges=planOrgService.queryPlanOrgdtlVO(params);
		int row_index=0;
		for(int i=0;i<planOrges.size();i++){
			PlanOrgdtlVO planOrgdtlVO=planOrges.get(i);
			if(planOrgdtlVO.getIsTotal()){
				continue;
			}
			Row row = sheet1.createRow((short)(row_index+2));
			row_index++;
			
			Cell cell0 = row.createCell(0);
			cell0.setCellValue(planOrgdtlVO.getOrgnm());
			
			Cell cell01 = row.createCell(1);
			cell01.setCellValue(planOrgdtlVO.getBradnm());
			
			Cell cell1 = row.createCell(2);
			cell1.setCellValue(planOrgdtlVO.getSpclnm());
			
			Cell cell2 = row.createCell(3);
			cell2.setCellValue(planOrgdtlVO.getSptynm());
			
			Cell cell3 = row.createCell(4);
			cell3.setCellValue(planOrgdtlVO.getSpsenm());
			
			Cell cell4 = row.createCell(5);
			if(planOrgdtlVO.getQymtqt()!=null){
				cell4.setCellValue(planOrgdtlVO.getQymtqt());
			}
				
			Cell cell5 = row.createCell(6);
			if(planOrgdtlVO.getQymtam()!=null){
				cell5.setCellValue(planOrgdtlVO.getQymtam());
			}
			
			Cell cell6 = row.createCell(7);
			if(planOrgdtlVO.getTxmtqt()!=null){
				cell6.setCellValue(planOrgdtlVO.getTxmtqt());
			}
			
			Cell cell7 = row.createCell(8);
			if(planOrgdtlVO.getTxmtam()!=null){
				cell7.setCellValue(planOrgdtlVO.getTxmtam());
			}

		}
		
	}
	
	private void crreateTitle(XSSFWorkbook wb,Sheet sheet1){
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
	    cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    //cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		//cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		
		Font font = wb.createFont();
	    //font.setFontHeightInPoints((short)14);
	    font.setFontName("Courier New");
	    font.setBold(true);
	    cellStyle.setFont(font);
		 
		Row title0 = sheet1.createRow((short)0);
		Cell cell4_title0 = title0.createCell(5);
		cell4_title0.setCellValue("区域");
		cell4_title0.setCellStyle(cellStyle);

		Cell cell6_title0 = title0.createCell(7);
		cell6_title0.setCellValue("特许");
		cell6_title0.setCellStyle(cellStyle);
		
		Row title1 = sheet1.createRow((short)1);
		Cell cell0 = title1.createCell(0);
		cell0.setCellValue("区域");
		cell0.setCellStyle(cellStyle);
		
		Cell cell01 = title1.createCell(1);
		cell01.setCellValue("品牌");
		cell01.setCellStyle(cellStyle);
		
		Cell cell1 = title1.createCell(2);
		cell1.setCellValue("大类");
		cell1.setCellStyle(cellStyle);
		
		Cell cell2 = title1.createCell(3);
		cell2.setCellValue("小类");
		cell2.setCellStyle(cellStyle);
		
		Cell cell3 = title1.createCell(4);
		cell3.setCellValue("系列");
		cell3.setCellStyle(cellStyle);
		
		Cell cell4 = title1.createCell(5);
		cell4.setCellValue("区域指标数量");
		cell4.setCellStyle(cellStyle);
		
		Cell cell5 = title1.createCell(6);
		cell5.setCellValue("区域指标金额(万元)");
		cell5.setCellStyle(cellStyle);
		
		Cell cell6 = title1.createCell(7);
		cell6.setCellValue("特许指标数量");
		cell6.setCellStyle(cellStyle);
		
		Cell cell7 = title1.createCell(8);
		cell7.setCellValue("特许指标金额(万元)");
		cell7.setCellStyle(cellStyle);
		
		sheet1.addMergedRegion(new CellRangeAddress(0,0,5,6));
		sheet1.addMergedRegion(new CellRangeAddress(0,0,7,8));
	}
	/**
	 * 导入
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param params
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/planOrg/import.do")
	@ResponseBody
	public void onimport(HttpServletRequest request,HttpServletResponse response,MultipartFile imageFile,MapParams params) throws Exception {
		planOrgService.onimport(imageFile);
		//return "success";
		response.getWriter().write("{success:true}");
		response.getWriter().close();
	}
	

//	@RequestMapping("/planOrg/load.do")
//	public PlanOrg load(String id) {
//		return planOrgService.get(id);
//	}
//	
//	@RequestMapping("/planOrg/create.do")
//	//@ResponseBody
//	public PlanOrg create(@RequestBody PlanOrg planOrg) {
//		planOrgService.create(planOrg);
//		return planOrg;
//	}
//	
//	@RequestMapping("/planOrg/update.do")
//	//@ResponseBody
//	public  PlanOrg update(@RequestBody PlanOrg planOrg) {
//		planOrgService.update(planOrg);
//		return planOrg;
//	}
//	
//	@RequestMapping("/planOrg/deleteById.do")
//	//@ResponseBody
//	public String deleteById(String id) {
//		planOrgService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/planOrg/destroy.do")
//	//@ResponseBody
//	public PlanOrg destroy(@RequestBody PlanOrg planOrg) {
//		planOrgService.delete(planOrg);
//		return planOrg;
//	}
//	
//	
}
