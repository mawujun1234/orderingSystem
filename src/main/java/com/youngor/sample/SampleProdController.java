package com.youngor.sample;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.youngor.permission.ShiroUtils;
import com.mawujun.utils.page.Pager;
import com.youngor.utils.MapParams;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/sampleProd")
public class SampleProdController {

	@Resource
	private SampleProdService sampleProdService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/sampleProd/queryPager.do")
	@ResponseBody
	public Pager<SampleProd> queryPager(Pager<SampleProd> pager){
		pager.addParam("user_id", ShiroUtils.getUserId());
		return sampleProdService.queryPage(pager);
	}

//	@RequestMapping("/sampleProd/queryAll.do")
//	@ResponseBody
//	public List<SampleProd> queryAll() {	
//		List<SampleProd> sampleProdes=sampleProdService.queryAll();
//		return sampleProdes;
//	}
	

	@RequestMapping("/sampleProd/load.do")
	public SampleProd load(com.youngor.sample.SampleProd.PK id) {
		return sampleProdService.get(id);
	}
	
	@RequestMapping("/sampleProd/createOrUpdate.do")
	@ResponseBody
	public SampleProd createOrUpdate(@RequestBody SampleProd sampleProd) {
		sampleProdService.createOrUpdate(sampleProd);
		return sampleProd;
	}
	
	@RequestMapping("/sampleProd/download.do")
	@ResponseBody
	public void download(MapParams params,HttpServletRequest request,HttpServletResponse response) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		
		XSSFWorkbook wb = new XSSFWorkbook();    
		Sheet sheet1 = wb.createSheet("报表");
		LinkedHashMap<String,String> titles=new LinkedHashMap<String,String>();
		titles.put("ormtno", "别动");
		titles.put("sampno", "别动");
		titles.put("suitno", "别动");
		titles.put("sampnm", "设计样衣编号");
		titles.put("suitno_name", "套件");
		titles.put("prseqm", "序号");
		//titles.put("prodno", "货号代码");
		titles.put("prodnm", "货号名称");
		titles.put("sizegp_name", "规格系列");
		titles.put("bradno_name", "品牌");
		titles.put("spyear", "年份");
		titles.put("spsean_name", "季节");
		titles.put("spbseno_name", "大系列");
		titles.put("sprseno_name", "品牌系列");
		titles.put("spclno_name", "大类");
		titles.put("sptyno_name", "小类");
		titles.put("spseno_name", "系列");
		titles.put("splcno_name", "定位");
		titles.put("spbano_name", "上市批次");
		titles.put("versno_name", "版型");
		titles.put("stseno_name", "工作室系列");
		titles.put("buspno", "外买样衣编号");
		titles.put("spmtno_name", "生产类型");
		titles.put("gustno", "客供编号");
		titles.put("colrno_name", "颜色");
		titles.put("pattno_name", "花型");
		titles.put("stylno_name", "款式");
		titles.put("stylgp", "款式组");
		titles.put("sexno_name", "性别");
		titles.put("slveno", "长短袖");
		titles.put("mateno", "面料");
		titles.put("prftpr", "出厂价");
		titles.put("prrtpr", "零售价");
		titles.put("prctpr", "成本价");
		titles.put("prmtam", "加工费");
		titles.put("prmlam", "面料费");
		titles.put("prorpr", "合同价");
		titles.put("prflam", "包装辅料费");


		crreateTitle_export(wb,sheet1,titles);
		crreateData_export(wb,sheet1,titles,params);
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");    
        response.setHeader("Content-disposition", "attachment;filename="+new String("产品货号".getBytes(),"ISO8859-1")+".xlsx");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
        
		
	}
	
	private void crreateTitle_export(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles){
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
	private void crreateData_export(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles,MapParams params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		params.getParams().put("user_id", ShiroUtils.getUserId());
		List<SampleProdVO> list=sampleProdService.queryPage(params);
		if(list==null || list.size()==0){
			return;
		}
		for(int i=0;i<list.size();i++){
			SampleProdVO orderNumTotal=list.get(i);
			Row row = sheet1.createRow((short)i+1);
			int j=0;
			for(Entry<String,String> entry:titles.entrySet()){
				Cell cell = row.createCell(j);
				j++;
				String get_name="get"+StringUtils.capitalize(entry.getKey());
				System.out.println(get_name+":"+orderNumTotal.getSampnm());
				Object value=ReflectionUtils.findMethod(SampleProdVO.class, get_name).invoke(orderNumTotal);
				if(value!=null){
					cell.setCellValue(value.toString());
				}
			}
		}
	}
	
	@RequestMapping("/sampleProd/import.do")
	@ResponseBody
	public void onimport(HttpServletRequest request,HttpServletResponse response,MultipartFile imageFile,MapParams params) throws Exception {
		sampleProdService.onimport(imageFile);
		//return "success";
		response.getWriter().write("{success:true}");
		response.getWriter().close();
	}
	
//	@RequestMapping("/sampleProd/create.do")
//	@ResponseBody
//	public SampleProd create(@RequestBody SampleProd sampleProd) {
//		sampleProdService.create(sampleProd);
//		return sampleProd;
//	}
//	
//	@RequestMapping("/sampleProd/update.do")
//	@ResponseBody
//	public  SampleProd update(@RequestBody SampleProd sampleProd) {
//		sampleProdService.update(sampleProd);
//		return sampleProd;
//	}
//	
//	@RequestMapping("/sampleProd/deleteById.do")
//	@ResponseBody
//	public com.youngor.sample.SampleProd.PK deleteById(com.youngor.sample.SampleProd.PK id) {
//		sampleProdService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/sampleProd/destroy.do")
//	@ResponseBody
//	public SampleProd destroy(@RequestBody SampleProd sampleProd) {
//		sampleProdService.delete(sampleProd);
//		return sampleProd;
//	}
	
	
}
