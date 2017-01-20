package com.youngor.order.cg;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
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

import com.mawujun.controller.spring.mvc.MapParams;
import com.mawujun.utils.page.Pager;
import com.youngor.permission.ShiroUtils;
import com.youngor.report.OrderNumTotal;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/cgOrddtl")
public class CgOrddtlController {

	@Resource
	private CgOrddtlService cgOrddtlService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/cgOrddtl/queryPager.do")
	@ResponseBody
	public Pager<CgOrddtl> queryPager(Pager<CgOrddtl> pager){
		
		return cgOrddtlService.queryPage(pager);
	}

//	@RequestMapping("/cgOrddtl/queryAll.do")
//	@ResponseBody
//	public List<CgOrddtl> queryAll() {	
//		List<CgOrddtl> cgOrddtles=cgOrddtlService.queryAll();
//		return cgOrddtles;
//	}
	
	@RequestMapping("/cgOrddtl/queryPage4Insert.do")
	@ResponseBody
	public Pager<CgOrddt> queryPage4Insert(Pager<CgOrddt> pager) {
		return cgOrddtlService.queryPage4Insert(pager);
	}

//	@RequestMapping("/cgOrddtl/load.do")
//	public CgOrddtl load(com.youngor.order.cg.CgOrddtl.PK id) {
//		return cgOrddtlService.get(id);
//	}
	
	@RequestMapping("/cgOrddtl/create.do")
	@ResponseBody
	public CgOrddtl create(CgOrddtl cgOrddtl) {
		cgOrddtlService.create1(cgOrddtl);
		return cgOrddtl;
	}
	
	@RequestMapping("/cgOrddtl/update.do")
	@ResponseBody
	public  CgOrddtl update( CgOrddtl cgOrddtl) {
		if(cgOrddtl.getOrszqt()==0){
			cgOrddtlService.delete(cgOrddtl);
		} else {
			cgOrddtl.setLmdt(new Date());
			cgOrddtl.setLmsp(ShiroUtils.getUserId());
			cgOrddtlService.update(cgOrddtl);
		}
		
		return cgOrddtl;
	}
	
	@RequestMapping("/cgOrddtl/initOrszqt.do")
	@ResponseBody
	public  String initOrszqt(MapParams params) {
		cgOrddtlService.initOrszqt(params);
		return "{success:true}";
	}
	
//	@RequestMapping("/cgOrddtl/deleteById.do")
//	@ResponseBody
//	public com.youngor.order.cg.CgOrddtl.PK deleteById(com.youngor.order.cg.CgOrddtl.PK id) {
//		cgOrddtlService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/cgOrddtl/destroy.do")
//	@ResponseBody
//	public CgOrddtl destroy(@RequestBody CgOrddtl cgOrddtl) {
//		cgOrddtlService.delete(cgOrddtl);
//		return cgOrddtl;
//	}
	
	@RequestMapping("/cgOrddtl/destroyBatch1.do")
	@ResponseBody
	public String destroyBatch1(@RequestBody CgOrddtl.PK[] cgOrddtlPKes) {
		cgOrddtlService.destroyBatch1(cgOrddtlPKes);
		return "{success:true}";
	}
	
	@RequestMapping("/cgOrddtl/export.do")
	@ResponseBody
	public void exportSample(MapParams params,HttpServletResponse response) throws Exception {
		XSSFWorkbook wb = new XSSFWorkbook();    
		Sheet sheet1 = wb.createSheet("资料");
		
		LinkedHashMap<String,String> titles=new LinkedHashMap<String,String>();
		titles.put("sampnm", "订货样衣编号");
		titles.put("suitno_name", "套件");
		titles.put("orszqt_now", "本次数量");
		titles.put("orszqt_residue", "剩余订货量");
		titles.put("orszqt_already", "已确认量");
		titles.put("orszqt_zhanb", "已确认占比");
		titles.put("ormtqt", "订货量");
		titles.put("mldate", "面料交货期");
		titles.put("pldate", "成衣交货期");
		titles.put("pplace", "面料交货地");
		
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
	
	private void crreateData_exportSample(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles,MapParams params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<CgOrddtlVO> list=cgOrddtlService.queryPage(params.getParams());
		if(list==null || list.size()==0){
			return;
		}
		for(int i=0;i<list.size();i++){
			CgOrddtlVO orderNumTotal=list.get(i);
			Row row = sheet1.createRow((short)i+1);
			int j=0;
			for(Entry<String,String> entry:titles.entrySet()){
				Cell cell = row.createCell(j);
				j++;
				String get_name="get"+StringUtils.capitalize(entry.getKey());
				Object value=ReflectionUtils.findMethod(CgOrddtlVO.class, get_name).invoke(orderNumTotal);
				if(value!=null){
					cell.setCellValue(value.toString());
				}
			}
		}
	}
	
	
}
