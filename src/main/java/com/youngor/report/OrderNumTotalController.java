package com.youngor.report;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.Pager;
import com.youngor.org.OrgService;
import com.youngor.permission.ShiroUtils;
import com.youngor.pubcode.PubCodeCache;
import com.youngor.utils.ContextUtils;
import com.youngor.utils.MapParams;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class OrderNumTotalController {
	@Autowired
	private OrderNumTotalRepository orderNumTotalRepository;
	@Autowired
	private OrgService orgService;
	
	@RequestMapping("/ordernumtotal/query.do")
	@ResponseBody
	public  Pager<OrderNumTotal> queryClothPurePlan( Pager<OrderNumTotal> pager) {
		//在查询的时候判断，是否已经超时了，如果已经超时了，就不能查询了
		
		
		pager= orderNumTotalRepository.query(pager);
		//List<Map<String,Object>> list=pager.getRoot();

		return pager;
	}
	

	/**
	 * 导出
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param params
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("/ordernumtotal/export.do")
	@ResponseBody
	public  void export(MapParams params,HttpServletRequest request,HttpServletResponse response) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		XSSFWorkbook wb = new XSSFWorkbook();    
		Sheet sheet1 = wb.createSheet("报表");
		LinkedHashMap<String,String> titles=new LinkedHashMap<String,String>();
		titles.put("yxgsnm", "营销公司");
		titles.put("qynm", "区域");
		titles.put("orgnm", "订货单位");
		titles.put("bradno_name", "品牌");
		titles.put("spclno_name", "大类");
		titles.put("sptyno_name", "小类");
		titles.put("spseno_name", "系列");
		titles.put("colrno_name", "颜色");
		titles.put("spsean_name", "季节");
		titles.put("spbano_name", "上市批次");
		titles.put("versno_name", "版型");
		titles.put("prodnm", "产品货号");
		titles.put("sampnm", "设计样衣编号");
		titles.put("suitno_name", "套件");
		titles.put("ormtqt", "数量");
		titles.put("spftpr", "出厂价");
		titles.put("spftpr_jine", "出厂金额");
		titles.put("sprtpr", "零售价");
		titles.put("sprtpr_jine_wan", "零售金额(万元)");

		crreateTitle_export(wb,sheet1,titles);
		
		List<OrderNumTotal> list=orderNumTotalRepository.query(params.getParams());
		crreateData_export(wb,sheet1,titles,list);
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");    
        response.setHeader("Content-disposition", "attachment;filename="+new String("订单数量汇总报表".getBytes(),"ISO8859-1")+".xlsx");    
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
	    //font.setFontHeightInPoints(18);
	    font.setFontName("Courier New");
	    cellStyle.setFont(font);
		 
		Row title = sheet1.createRow(0);
		
		int i=0;
		for(Entry<String,String> entry:titles.entrySet()){
			Cell cell = title.createCell(i);
			cell.setCellValue(entry.getValue());
			cell.setCellStyle(cellStyle);
			i++;
		}
		
	}
	private void crreateData_export(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles,List<OrderNumTotal> list) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		
		
		if(list==null || list.size()==0){
			return;
		}
		for(int i=0;i<list.size();i++){
			OrderNumTotal orderNumTotal=list.get(i);
			Row row = sheet1.createRow(i+1);
			int j=0;
			for(Entry<String,String> entry:titles.entrySet()){
				Cell cell = row.createCell(j);
				j++;
				String get_name="get"+StringUtils.capitalize(entry.getKey());
				Object value=ReflectionUtils.findMethod(OrderNumTotal.class, get_name).invoke(orderNumTotal);
				if(value!=null){
					cell.setCellValue(value.toString());
				}
			}
		}
	}
	
	
	@RequestMapping("/ordernumtotal/exportAll.do")
	@ResponseBody
	public  void exportAll(MapParams params,HttpServletRequest request,HttpServletResponse response) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		XSSFWorkbook wb = new XSSFWorkbook();    
		Sheet sheet1 = wb.createSheet("报表");
		LinkedHashMap<String,String> titles=new LinkedHashMap<String,String>();
		titles.put("yxgsnm", "营销公司");
		titles.put("qynm", "区域");
		titles.put("orgnm", "订货单位");
		titles.put("ortyno_name", "订货类型");
		titles.put("bradno_name", "品牌");
		titles.put("spclno_name", "大类");
		titles.put("sptyno_name", "小类");
		titles.put("spseno_name", "系列");
		titles.put("colrno_name", "颜色");
		titles.put("spsean_name", "季节");
		titles.put("spbano_name", "上市批次");
		titles.put("versno_name", "版型");
		titles.put("prodnm", "产品货号");
		titles.put("sampnm", "设计样衣编号");
		titles.put("suitno_name", "套件");
		titles.put("ormtqt", "数量");
		titles.put("spftpr", "出厂价");
		titles.put("spftpr_jine_wan", "出厂金额(万元)");
		titles.put("sprtpr", "零售价");
		titles.put("sprtpr_jine_wan", "零售金额(万元)");

		crreateTitle_export(wb,sheet1,titles);
		
		params.getParams().put("user_id", ShiroUtils.getUserId());
		List<OrderNumTotal> list=orderNumTotalRepository.exportAll(params.getParams());
		crreateData_export(wb,sheet1,titles,list);
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");    
        response.setHeader("Content-disposition", "attachment;filename="+new String("订单数量汇总报表".getBytes(),"ISO8859-1")+".xlsx");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
	}
	
	
	/**
	 * 特许打印
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param params
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws JRException
	 */
	@RequestMapping("/ordernumtotal/export_print.do")
	@ResponseBody
	public  void orderTotalPrint_export1(MapParams params,HttpServletRequest request,HttpServletResponse response) throws IOException, JRException {
		List<OrderNumTotal> list=orderNumTotalRepository.query(params.getParams());//export_print.orderTotalPrint_export1(params.getParams());

		String reportFilePath = "";
		reportFilePath = request
				.getSession()
				.getServletContext()
				.getRealPath(File.separator+"report"+File.separator+"ireport"+File.separator+"OrderNumTotal.jasper");

		Map<String, Object> rpt_params = new HashMap<String, Object>();
		
		String yxgsnm=orgService.get(params.getParams().get("yxgsno").toString()).getOrgnm();////params.getParams().get("yxgsnm").toString();
		//yxgsnm= new String(yxgsnm.getBytes("iso-8859-1"),"utf-8");
		String qynm=orgService.get(params.getParams().get("qyno").toString()).getOrgnm();//params.getParams().get("qynm").toString();
		//qynm= new String(qynm.getBytes("iso-8859-1"),"utf-8");
		String orgnm=orgService.get(params.getParams().get("ordorg").toString()).getOrgnm();;//params.getParams().get("orgnm").toString();
		//orgnm= new String(orgnm.getBytes("iso-8859-1"),"utf-8");
		rpt_params.put("bradno_name", PubCodeCache.getBradno_name(params.getParams().get("bradno").toString()));
		rpt_params.put("yxgsnm",yxgsnm);
		rpt_params.put("qynm",qynm);
		rpt_params.put("orgnm",orgnm);
		String ormtnm=ContextUtils.getOrdmt(params.getParams().get("ormtno").toString()).getOrmtnm();
		rpt_params.put("ormtnm",ormtnm);


		File reportFile = new File(reportFilePath);

		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());

		JasperPrint jasperprint = JasperFillManager.fillReport(jasperReport,
				rpt_params, new JRBeanCollectionDataSource(list));

		OutputStream httpOut = response.getOutputStream();
		response.reset();
		response.setCharacterEncoding("GBK");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((orgnm+"_总量报表").getBytes("GBK"), "iso8859-1")
				+ ".xls");

		expoertReportToExcelStream(jasperprint, httpOut);

		httpOut.close(); 
	}

	
	private void expoertReportToExcelStream(JasperPrint jasperPrint,
			OutputStream outputStream) throws JRException {

		try {
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					outputStream);
			outputStream.flush();
			exporter.exportReport();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
