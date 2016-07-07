package com.youngor.sample;
import java.io.OutputStream;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Pager;
import com.youngor.utils.ContextUtils;
import com.youngor.utils.M;
import com.youngor.utils.MapParams;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/sampleDesign")
public class SampleDesignController {

	@Resource
	private SampleDesignService sampleDesignService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/sampleDesign/query.do")
	@ResponseBody
	public Pager<SampleDesign> query(Pager<SampleDesign> pager){
		return sampleDesignService.queryPage(pager);
	}

	@RequestMapping("/sampleDesign/queryAll.do")
	@ResponseBody
	public List<SampleDesign> queryAll() {	
		List<SampleDesign> sampleDesignes=sampleDesignService.queryAll();
		return sampleDesignes;
	}
	

	@RequestMapping("/sampleDesign/load.do")
	@ResponseBody
	public SampleDesign load(String id) {
		return sampleDesignService.get(id);
	}
	
	@RequestMapping("/sampleDesign/create.do")
	//@ResponseBody
	public SampleDesign create(@RequestBody SampleDesign sampleDesign) {
		sampleDesignService.create(sampleDesign);
		return sampleDesign;
	}
	
	@RequestMapping("/sampleDesign/copy.do")
	//@ResponseBody
	public SampleDesign copy(@RequestBody SampleDesign sampleDesign) {
		sampleDesignService.copy(sampleDesign);
		return sampleDesign;
	}
	
	@RequestMapping("/sampleDesign/update.do")
	//@ResponseBody
	public  SampleDesign update(@RequestBody SampleDesign sampleDesign) {
		sampleDesignService.update(sampleDesign);
		return sampleDesign;
	}
	
	@RequestMapping("/sampleDesign/deleteById.do")
	@ResponseBody
	public String deleteById(String ormtno,String sampno) {
		sampleDesignService.deleteById(ormtno,sampno);
		return sampno;
	}
	
	/**
	 * 设计师输入设计资料的时候用的
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param pager
	 * @return
	 */
	@RequestMapping("/sampleDesign/queryPlanDesign.do")
	@ResponseBody
	public Pager<SamplePlanDesignVO> queryPlanDesign(Pager<SamplePlanDesignVO> pager) {
		return sampleDesignService.queryPlanDesign(pager);
	}
	
	@RequestMapping("/sampleDesign/lock.do")
	@ResponseBody
	public void lock(String[] sampnos,MapParams mapParams) {
		if(sampnos!=null){
			sampleDesignService.update(Cnd.update().set(M.SampleDesign.spstat, 1).andIn(M.SampleDesign.sampno, sampnos));
		}
		if(mapParams.getParams()!=null){
			sampleDesignService.lock(mapParams.getParams());
		}
		
	}
	@RequestMapping("/sampleDesign/unlock.do")
	@ResponseBody
	public void unlock(String[] sampnos,MapParams mapParams) {
		if(sampnos!=null){
			sampleDesignService.update(Cnd.update().set(M.SampleDesign.spstat, 0).andIn(M.SampleDesign.sampno, sampnos));
		}
		if(mapParams.getParams()!=null){
			sampleDesignService.unlock(mapParams.getParams());
		}
	}
	
	@RequestMapping("/sampleDesign/mustOrder.do")
	@ResponseBody
	public void mustOrder(String[] sampnos,Integer abstat) {
		if(sampnos!=null){
			sampleDesignService.update(Cnd.update().set(M.SampleDesign.abstat, abstat).andIn(M.SampleDesign.sampno, sampnos));
		}

		
	}
	
//	@RequestMapping("/sampleDesign/destroy.do")
//	//@ResponseBody
//	public SampleDesign destroy(@RequestBody SampleDesign sampleDesign) {
//		sampleDesignService.delete(sampleDesign);
//		return sampleDesign;
//	}
	
	
	@RequestMapping("/sampleDesign/exportSample.do")
	@ResponseBody
	public void exportSample(MapParams params,HttpServletResponse response) throws Exception {
		//samplePlanService.lockOrunlock(plspno, plspst);
		System.out.println(params);
		XSSFWorkbook wb = new XSSFWorkbook();    
		Sheet sheet1 = wb.createSheet("资料");
		
		LinkedHashMap<String,String> titles=new LinkedHashMap<String,String>();
		titles.put("PLSPNM", "企划样衣编号");
		titles.put("BRADNM", "品牌");
		titles.put("SPYEAR", "年份");
		titles.put("SPSEAN", "季节");
		titles.put("SPBSENM", "大系列");
		titles.put("SPRSENM", "品牌系列");
		titles.put("SPCLNM", "大类");
		titles.put("SPTYNM", "小类");
		titles.put("SPSENM", "系列");
		titles.put("SPLCNM", "定位");
		titles.put("SPBANM", "上市批次");
		titles.put("SPFTPR", "出厂价");
		titles.put("SPRTPR", "零售价");
		titles.put("SPPLRD", "企划倍率");
		titles.put("PLCTPR", "企划成本价");
		
		titles.put("SAMPNM", "订货样衣编号");
		titles.put("SAMPNM1", "出样样衣编号");
		titles.put("VERSNM", "版型");
		titles.put("STSENM", "工作室系列");
		titles.put("DESGNM", "设计师");
		titles.put("BUSPNO", "外买样衣编号");
		titles.put("SPMTNM", "生产类型");
		titles.put("GUSTNO", "客供编号");
		titles.put("COLRNM", "颜色");
		titles.put("PATTNO", "花型");
		titles.put("STYLNM", "款式");
		titles.put("STYLGP", "款式组");
		titles.put("SEXNM", "性别");
		titles.put("SLVENM", "长短袖");
		//titles.put("SUITTY", "套装种类");
		titles.put("SUITTYNM", "套装种类");
		titles.put("DESP", "规格版型说明");
		//titles.put("SIZEGP", "规格范围");
		titles.put("SIZENM", "规格范围");
		titles.put("PACKQT", "包装要求");
		titles.put("SPLTMK", "套西是否拆套");
		titles.put("PRINT", "吊牌打印标志");
		//titles.put("ABSTAT", "必订款标志");
		
		//titles.put("MTSUNO", "供应商");
		titles.put("MATENO", "供应商面料货号");
//		titles.put("MTBRAD", "面料品牌");
//		titles.put("MTTYPE", "进口/国产");
//		titles.put("MTCOMP", "面料成分");
//		titles.put("YARMCT", "纱支规格");
//		titles.put("GRAMWT", "克重/密度");
//		titles.put("AFTRMT", "后整理");
//		titles.put("WIDTH", "门幅");
//		titles.put("MTPUPR", "面料单价");
//		titles.put("MTCNQT", "单件用料");

		
		titles.put("SPCOTN", "纱厂");
		//titles.put("SPSUNO", "开发供应商代码");
		//titles.put("PRSUNO", "货号采购供应商代码");
		titles.put("IDSUNM", "货号采购供应商代码");
		titles.put("SPTAPA", "含税工缴");
		titles.put("SPACRY", "含税辅料");
		titles.put("SPCLBD", "服饰配料");
		titles.put("SPNWPR", "新成衣价");
		titles.put("CONTQT", "成衣数量");
		titles.put("CONTAM", "成衣金额");
		titles.put("CONTPR", "成衣核价克重");
		titles.put("CTDWDT", "合同交期");
		titles.put("ACSYAM", "包装辅料费");
		titles.put("SPCTPR", "预计成本价");
		titles.put("SPRMK", "备注");

		
		crreateTitle_exportSample(wb,sheet1,titles);
		
		crreateData_exportSample(wb,sheet1,titles,params);

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");    
        response.setHeader("Content-disposition", "attachment;filename="+new String("企划样衣资料".getBytes(),"ISO8859-1")+".xlsx");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
	}
	
	private void crreateTitle_exportSample(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles){
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
	
	private void crreateData_exportSample(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles,MapParams params) {
		List<Map<String,Object>> list=sampleDesignService.query_exportSample(params);
		if(list==null || list.size()==0){
			return;
		}
		for(int i=0;i<list.size();i++){
			Map<String,Object> map=list.get(i);
			Row row = sheet1.createRow((short)i+1);
			int j=0;
			for(Entry<String,String> entry:titles.entrySet()){
				Cell cell = row.createCell(j);
				j++;
				if(map.get(entry.getKey())!=null){
					cell.setCellValue(map.get(entry.getKey()).toString());
				}
			}
		}
	}
	
	@RequestMapping("/sampleDesign/exportSampleMate.do")
	@ResponseBody
	public void exportSampleMate(MapParams params,HttpServletResponse response) throws Exception {
		//samplePlanService.lockOrunlock(plspno, plspst);
		//System.out.println(params);
		XSSFWorkbook wb = new XSSFWorkbook();    
		Sheet sheet1 = wb.createSheet("资料");
		LinkedHashMap<String,String> titles=new LinkedHashMap<String,String>();
		
		titles.put("PLSPNM", "企划样衣编号");
		titles.put("SAMPNM", "订货样衣编号");
		titles.put("MTSUNO", "供应商");
		titles.put("MATENO", "供应商面料货号");
		titles.put("MTBRAD", "面料品牌");
		titles.put("MTTYPE", "进口/国产");
		titles.put("MTCOMP", "面料成分");
		titles.put("YARMCT", "纱支规格");
		titles.put("GRAMWT", "克重/密度");
		titles.put("AFTRMT", "后整理");
		titles.put("WIDTH", "门幅");
		titles.put("MTPUPR", "面料单价");
		titles.put("MTCNQT", "单件用料");
		crreateTitle_exportSampleMate(wb,sheet1,titles);
		
		crreateData_exportSampleMate(wb,sheet1,titles,params);

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");    
        response.setHeader("Content-disposition", "attachment;filename="+new String("主面料信息".getBytes(),"ISO8859-1")+".xlsx");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
	}
	private void crreateTitle_exportSampleMate(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles){
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
	
	private void crreateData_exportSampleMate(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles,MapParams params){
		List<Map<String,Object>> list=sampleDesignService.query_exportSampleMate(params);
		if(list==null || list.size()==0){
			return;
		}
		for(int i=0;i<list.size();i++){
			Map<String,Object> map=list.get(i);
			Row row = sheet1.createRow((short)i+1);
			int j=0;
			for(Entry<String,String> entry:titles.entrySet()){
				Cell cell = row.createCell(j);
				j++;
				if(map.get(entry.getKey())!=null){
					if("MTSUNO".equals(entry.getKey())){
						if(ContextUtils.getPubSuno(entry.getValue())!=null){
							cell.setCellValue(ContextUtils.getPubSuno(entry.getValue()).getIdsunm());
						}
						
					} else {
						cell.setCellValue(map.get(entry.getKey()).toString());
					}
					
				}
			}
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------------------------------------
	
	@RequestMapping("/sampleDesign/exportSampleMate_other.do")
	@ResponseBody
	public void exportSampleMate_other(MapParams params,HttpServletResponse response) throws Exception {
		//samplePlanService.lockOrunlock(plspno, plspst);
		System.out.println(params);
		XSSFWorkbook wb = new XSSFWorkbook();    
		Sheet sheet1 = wb.createSheet("资料");
		LinkedHashMap<String,String> titles=new LinkedHashMap<String,String>();
		
		titles.put("PLSPNM", "企划样衣编号");
		titles.put("SAMPNM", "订货样衣编号");
		titles.put("MTSUNO", "供应商");
		titles.put("MATENO", "供应商面料货号");
		titles.put("MTBRAD", "面料品牌");
		titles.put("MTTYPE", "进口/国产");
		titles.put("MTCOMP", "面料成分");
		titles.put("YARMCT", "纱支规格");
		titles.put("GRAMWT", "克重/密度");
		titles.put("AFTRMT", "后整理");
		titles.put("WIDTH", "门幅");
		titles.put("MTPUPR", "面料单价");
		titles.put("MTCNQT", "单件用料");
		crreateTitle_exportSampleMate_other(wb,sheet1,titles);
		
		crreateData_exportSampleMate_other(wb,sheet1,titles,params);
		
		

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");    
        response.setHeader("Content-disposition", "attachment;filename="+new String("其他面料信息".getBytes(),"ISO8859-1")+".xlsx");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
	}
	private void crreateTitle_exportSampleMate_other(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles){
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
	
	private void crreateData_exportSampleMate_other(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles,MapParams params){
		List<Map<String,Object>> list=sampleDesignService.query_exportSampleMate_other(params);
		if(list==null || list.size()==0){
			return;
		}
		for(int i=0;i<list.size();i++){
			Map<String,Object> map=list.get(i);
			Row row = sheet1.createRow((short)i+1);
			int j=0;
			for(Entry<String,String> entry:titles.entrySet()){
				Cell cell = row.createCell(j);
				j++;
				if(map.get(entry.getKey())!=null){
					if("MTSUNO".equals(entry.getKey())){
						if(ContextUtils.getPubSuno(entry.getValue())!=null){
							cell.setCellValue(ContextUtils.getPubSuno(entry.getValue()).getIdsunm());
						}
						
					} else {
						cell.setCellValue(map.get(entry.getKey()).toString());
					}
					
				}
			}
		}
	}
	
}
