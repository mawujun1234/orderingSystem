package com.youngor.order;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.Pager;
import com.youngor.pubcode.PubCodeCache;
import com.youngor.utils.MapParams;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/ord")
public class TpController {

	@Resource
	private TpService tpService;

	/**
	 * 查询某个样衣的信息
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param id
	 * @return
	 */
//	@RequestMapping("/tp/zgs_tpAllQuery.do")
//	@ResponseBody
//	public  Pager<Map<String,Object>> zgs_tpAllQuery( Pager<Map<String,Object>> pager) {
//		//在查询的时候判断，是否已经超时了，如果已经超时了，就不能查询了
//		
//		
//		pager= tpService.zgs_tpAllQuery(pager);
//		List<Map<String,Object>> list=pager.getRoot();
//		for(Map<String,Object> map:list){
//			map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name(map.get("SPTYNO").toString()));
//			map.put("SPSENO_NAME", PubCodeCache.getSpseno_name(map.get("SPSENO").toString()));
//			map.put("SUITNO_NAME", PubCodeCache.getSuitno_name(map.get("SUITNO").toString()));
//		}
//		return pager;
//	}
	@RequestMapping("/tp/zgs_tpAllQuery.do")
	@ResponseBody
	public  List<Map<String,Object>> zgs_tpAllQuery( MapParams pager) {
		//在查询的时候判断，是否已经超时了，如果已经超时了，就不能查询了
		
		
		List<Map<String,Object>>  list= tpService.zgs_tpAllQuery(pager);
		//List<Map<String,Object>> list=pager.getRoot();
		for(Map<String,Object> map:list){
			map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name(map.get("SPTYNO").toString()));
			map.put("SPSENO_NAME", PubCodeCache.getSpseno_name(map.get("SPSENO").toString()));
			map.put("SUITNO_NAME", PubCodeCache.getSuitno_name(map.get("SUITNO").toString()));
		}
		return list;
	}
	
	@RequestMapping("/tp/zgs_updateOrmtqt_tp.do")
	@ResponseBody
	public  String zgs_updateOrmtqt_tp(String ormtno,String sampno,String bradno,String spclno,String suitno,Integer ormtqs,Integer ormtqt) {
		tpService.zgs_updateOrmtqt_tp(ormtno, sampno, bradno, spclno, suitno, ormtqs, ormtqt);
		return "{success:true}";
	}
	@RequestMapping("/tp/zgs_restoreDZ.do")
	@ResponseBody
	public String zgs_restoreDZ(String ormtno,String sampno,String suitno) {
		
		tpService.zgs_restoreDZ( ormtno, sampno, suitno);
		return "{success:true}";
	}
	@RequestMapping("/tp/zgs_over.do")
	@ResponseBody
	public String zgs_over(String ormtno,String bradno,String spclno) {
		//总量提交是按照大类+品牌进行提交的
		tpService.zgs_over( ormtno,bradno,spclno);
		return "{success:true}";
	}
	@RequestMapping("/tp/zgs_getOrstat.do")
	@ResponseBody
	public String zgs_getOrstat(String ormtno,String bradno,String spclno) {
		
		int orstat=tpService.zgs_getOrstat( ormtno,bradno,spclno);
		return "{success:true,orstat:"+orstat+"}";
	}
	
	
	@RequestMapping("/tp/zgs_tpAllExport.do")
	@ResponseBody
	public void zgs_tpAllExport(MapParams params,HttpServletResponse response) throws IOException {
		
		XSSFWorkbook wb = new XSSFWorkbook();    
		Sheet sheet1 = wb.createSheet("统配总量");
		LinkedHashMap<String,String> titles=new LinkedHashMap<String,String>();
		
		titles.put("SPTYNO_NAME", "小类");
		titles.put("SPSENO_NAME", "系列");
		titles.put("SAMPNM", "设计样衣编号");
		titles.put("SUITNO_NAME", "套件");
		titles.put("PACKQT", "包装要求");
		titles.put("ORMTQT", "定制总数");
		titles.put("ORMTQT_TP", "统配总数");
		
		

		zgs_crreateTitle_tpAllExport(wb,sheet1,titles);
		
		zgs_crreateData_tpAllExport(wb,sheet1,titles,params);

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");    
        response.setHeader("Content-disposition", "attachment;filename="+new String("统配总量".getBytes(),"ISO8859-1")+".xlsx");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
	}
	
	private void zgs_crreateTitle_tpAllExport(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles){
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
	
	private void zgs_crreateData_tpAllExport(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles,MapParams params){
		List<Map<String,Object>> list=tpService.zgs_tpAllExport(params);
		for(Map<String,Object> map:list){
			map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name(map.get("SPTYNO").toString()));
			map.put("SPSENO_NAME", PubCodeCache.getSpseno_name(map.get("SPSENO").toString()));
			map.put("SUITNO_NAME", PubCodeCache.getSuitno_name(map.get("SUITNO").toString()));
		}
		
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
	
	@RequestMapping("/tp/queryTpYxgsColumns.do")
	@ResponseBody
	public  List<Map<String,Object>> queryTpYxgsColumns() {
		return tpService.queryTpYxgsColumns();
	}
	@RequestMapping("/tp/tpYxgsQuery.do")
	@ResponseBody
	public  List<Map<String,Object>> tpYxgsQuery(MapParams params) {
	
		List<Map<String,Object>> list=tpService.tpYxgsQuery(params.getParams());
		for(Map<String,Object> map:list){
			map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name(map.get("SPTYNO").toString()));
			map.put("SPSENO_NAME", PubCodeCache.getSpseno_name(map.get("SPSENO").toString()));
			map.put("SUITNO_NAME", PubCodeCache.getSuitno_name(map.get("SUITNO").toString()));
		}
		return list;
	}
	@RequestMapping("/tp/tpYxgs_updateOrmtqt_tp.do")
	@ResponseBody
	public  String tpYxgs_pdateOrmtqt_tp(String ormtno,String ordorg,String sampno,String bradno,String spclno,String suitno,Integer ormtqs,Integer ormtqt) {
		tpService.tpYxgs_updateOrmtqt_tp(ormtno, ordorg, sampno, bradno, spclno, suitno, ormtqs, ormtqt);
		return "{success:true}";
	}
	@RequestMapping("/tp/tpYxgs_getStat.do")
	@ResponseBody
	public String tpYxgs_getStat(String ormtno,String bradno,String spclno) {
		int stat=tpService.tpYxgs_getStat(ormtno,bradno,spclno);
		return "{success:true,stat:"+stat+"}";
	}
	@RequestMapping("/tp/tpYxgs_over.do")
	@ResponseBody
	public String tpYxgs_over(String ormtno,String bradno,String spclno) {
		
		tpService.tpYxgs_over( ormtno,bradno,spclno);
		return "{success:true}";
	}
	@RequestMapping("/tp/tpYxgsExport.do")
	@ResponseBody
	public  void tpYxgsExport(MapParams params,HttpServletResponse response) throws IOException {
		XSSFWorkbook wb = new XSSFWorkbook();    
		Sheet sheet1 = wb.createSheet("统配到大区");
		LinkedHashMap<String,String> titles=new LinkedHashMap<String,String>();
		
		titles.put("SPTYNO_NAME", "小类");
		titles.put("SPSENO_NAME", "系列");
		titles.put("SAMPNM", "设计样衣编号");
		titles.put("SUITNO_NAME", "套件");
		titles.put("PACKQT", "包装要求");
		titles.put("ORMTQT_TP_GSBB", "统配总数");
		titles.put("ORMTQT_TOTAL", "合计");
		List<Map<String,Object>> columns=tpService.queryTpYxgsColumns();
		

		crreateTitle_tpYxgsExport(wb,sheet1,titles,columns);
		
		crreateData_tpYxgsExport(wb,sheet1,titles,params);

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");    
        response.setHeader("Content-disposition", "attachment;filename="+new String("统配到大区".getBytes(),"ISO8859-1")+".xlsx");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();   
		
	}
	
	private void crreateTitle_tpYxgsExport(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles,List<Map<String,Object>> columns){
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
	    cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		
		Font font = wb.createFont();
	    //font.setFontHeightInPoints((short)18);
	    font.setFontName("Courier New");
	    cellStyle.setFont(font);
	    
	    Row title0 = sheet1.createRow((short)0);
//	    Cell cell5 = title0.createCell(5);
//	    cell5.setCellValue("原始数量");
//	    cell5.setCellStyle(cellStyle);
//	    Cell cell9 = title0.createCell(9);
//	    cell9.setCellValue("确认数量");
//	    cell9.setCellStyle(cellStyle);
//	    sheet1.addMergedRegion(new CellRangeAddress(0, 0, 5, 8));
//	    sheet1.addMergedRegion(new CellRangeAddress(0, 0, 9, 12));
		 
	    Row title = sheet1.createRow((short)1);
		
		int i=0;
		for(Entry<String,String> entry:titles.entrySet()){
			Cell cell = title.createCell(i);
			cell.setCellValue(entry.getValue());
			cell.setCellStyle(cellStyle);
			i++;
		}
		//编写营销公司的数据
		int col_index=7;
		for(Map<String,Object> map:columns){
			//编写第一行
			Cell cell5 = title0.createCell(col_index);
		    cell5.setCellValue(map.get("ORGNM").toString());
		    cell5.setCellStyle(cellStyle);
		    sheet1.addMergedRegion(new CellRangeAddress(0, 0, col_index, col_index+1));
		    col_index=col_index+2;
			
			
			//======================第二行的数据
			Cell cell = title.createCell(i);
			cell.setCellValue("定制");
			cell.setCellStyle(cellStyle);
			i++;
			titles.put(map.get("ORGNO")+"_DZ", "定制");
			
			
			cell = title.createCell(i);
			cell.setCellValue("统配");
			cell.setCellStyle(cellStyle);
			i++;
			titles.put(map.get("ORGNO")+"_TP", "统配");
		}
		
	}
	private void crreateData_tpYxgsExport(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles,MapParams params){
		List<Map<String,Object>> list=tpService.tpYxgsQuery(params.getParams());
		for(Map<String,Object> map:list){
			map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name(map.get("SPTYNO").toString()));
			map.put("SPSENO_NAME", PubCodeCache.getSpseno_name(map.get("SPSENO").toString()));
			map.put("SUITNO_NAME", PubCodeCache.getSuitno_name(map.get("SUITNO").toString()));
		}
		if(list==null || list.size()==0){
			return;
		}
		for(int i=0;i<list.size();i++){
			Map<String,Object> map=list.get(i);
			Row row = sheet1.createRow((short)i+2);
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
	
	
	@RequestMapping("/tp/queryTpQyColumns.do")
	@ResponseBody
	public  List<Map<String,Object>> queryTpQyColumns(String yxgsno) {
		return tpService.queryTpQyColumns(yxgsno);
	}
	
	@RequestMapping("/tp/tpQyQuery.do")
	@ResponseBody
	public  List<Map<String,Object>> tpQyQuery(MapParams params) {
		//在查询的时候判断，是否已经超时了，如果已经超时了，就不能查询了
		
		
		List<Map<String,Object>> list= tpService.tpQyQuery(params.getParams());

		for(Map<String,Object> map:list){
			map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name(map.get("SPTYNO").toString()));
			map.put("SPSENO_NAME", PubCodeCache.getSpseno_name(map.get("SPSENO").toString()));
			map.put("SUITNO_NAME", PubCodeCache.getSuitno_name(map.get("SUITNO").toString()));
		}
		return list;
	}
	
	@RequestMapping("/tp/tpQy_getStat.do")
	@ResponseBody
	public String tpQy_getStat(String ormtno,String yxgsno,String bradno,String spclno) {
		int stat=tpService.tpQy_getStat(ormtno,yxgsno,bradno,spclno);
		return "{success:true,stat:"+stat+"}";
	}
	@RequestMapping("/tp/tpQy_updateOrmtqt_tp.do")
	@ResponseBody
	public  String tpQy_updateOrmtqt_tp(String ormtno,String ordorg,String sampno,String bradno,String spclno,String suitno,Integer ormtqs,Integer ormtqt,Integer ormtqt1) {
		tpService.tpQy_updateOrmtqt_tp(ormtno, ordorg, sampno, bradno, spclno, suitno, ormtqs, ormtqt,ormtqt1);
		return "{success:true}";
	}
	@RequestMapping("/tp/tpQy_over.do")
	@ResponseBody
	public String tpQy_over(String ormtno,String yxgsno,String bradno,String spclno) {
		
		tpService.tpQy_over( ormtno,yxgsno,bradno,spclno);
		return "{success:true}";
	}
	
	@RequestMapping("/tp/tpQy_export.do")
	@ResponseBody
	public  void tpQy_export(MapParams params,HttpServletResponse response) throws IOException {
		XSSFWorkbook wb = new XSSFWorkbook();    
		Sheet sheet1 = wb.createSheet("统配到区域");
		LinkedHashMap<String,String> titles=new LinkedHashMap<String,String>();
		
		titles.put("SPTYNO_NAME", "小类");
		titles.put("SPSENO_NAME", "系列");
		titles.put("SAMPNM", "设计样衣编号");
		titles.put("SUITNO_NAME", "套件");
		titles.put("PACKQT", "包装要求");
		titles.put("ORMTQT_TP_YXGS", "统配总数");
		titles.put("ORMTQT_TOTAL", "合计");
		List<Map<String,Object>> columns=tpService.queryTpQyColumns(params.getParams().get("yxgsno").toString());
		

		tpQy_crreateTitle_Export(wb,sheet1,titles,columns);
		
		tpQy_crreateData_Export(wb,sheet1,titles,params);

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");    
        response.setHeader("Content-disposition", "attachment;filename="+new String("统配到区域".getBytes(),"ISO8859-1")+".xlsx");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();   
		
	}
	
	private void tpQy_crreateTitle_Export(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles,List<Map<String,Object>> columns){
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
	    cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		
		Font font = wb.createFont();
	    //font.setFontHeightInPoints((short)18);
	    font.setFontName("Courier New");
	    cellStyle.setFont(font);
	    
	    Row title0 = sheet1.createRow((short)0);

	    Row title = sheet1.createRow((short)1);
		
		int i=0;
		for(Entry<String,String> entry:titles.entrySet()){
			Cell cell = title.createCell(i);
			cell.setCellValue(entry.getValue());
			cell.setCellStyle(cellStyle);
			i++;
		}
		//编写营销公司的数据
		int col_index=7;
		for(Map<String,Object> map:columns){
			//编写第一行
			Cell cell5 = title0.createCell(col_index);
		    cell5.setCellValue(map.get("ORGNM").toString());
		    cell5.setCellStyle(cellStyle);
		    sheet1.addMergedRegion(new CellRangeAddress(0, 0, col_index, col_index+3));
		    col_index=col_index+4;
			
			
			//======================第二行的数据
			Cell cell = title.createCell(i);
			cell.setCellValue("区域定制");
			cell.setCellStyle(cellStyle);
			i++;
			titles.put(map.get("ORGNO")+"_DZ_QY", "区域定制");
			
			cell = title.createCell(i);
			cell.setCellValue("特许定制");
			cell.setCellStyle(cellStyle);
			i++;
			titles.put(map.get("ORGNO")+"_DZ_TX", "特许定制");
			
			
			cell = title.createCell(i);
			cell.setCellValue("区域统配");
			cell.setCellStyle(cellStyle);
			i++;
			titles.put(map.get("ORGNO")+"_TP_QY", "区域统配");
			cell = title.createCell(i);
			cell.setCellValue("特许统配");
			cell.setCellStyle(cellStyle);
			i++;
			titles.put(map.get("ORGNO")+"_TP_TX", "特许统配");
		}
		
	}
	private void tpQy_crreateData_Export(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles,MapParams params) {
		List<Map<String,Object>> list=tpQyQuery(params);
		for(Map<String,Object> map:list){
			map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name(map.get("SPTYNO").toString()));
			map.put("SPSENO_NAME", PubCodeCache.getSpseno_name(map.get("SPSENO").toString()));
			map.put("SUITNO_NAME", PubCodeCache.getSuitno_name(map.get("SUITNO").toString()));
		}
		if(list==null || list.size()==0){
			return;
		}
		for(int i=0;i<list.size();i++){
			Map<String,Object> map=list.get(i);
			Row row = sheet1.createRow((short)i+2);
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
	
}
