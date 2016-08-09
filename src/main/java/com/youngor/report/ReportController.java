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
public class ReportController {
	@Autowired
	private ReportRepository reportRepository;
	
	@RequestMapping("/report/queryClothPurePlan.do")
	@ResponseBody
	public  Pager<Map<String,Object>> queryClothPurePlan( Pager<Map<String,Object>> pager) {
		//在查询的时候判断，是否已经超时了，如果已经超时了，就不能查询了
		
		
		pager= reportRepository.queryClothPurePlan(pager);
		List<Map<String,Object>> list=pager.getRoot();

		return pager;
	}
	
	@RequestMapping("/report/queryMatePurePlan.do")
	@ResponseBody
	public  Pager<Map<String,Object>> queryMatePurePlan( Pager<Map<String,Object>> pager) {
		//在查询的时候判断，是否已经超时了，如果已经超时了，就不能查询了
		
		
		pager= reportRepository.queryMatePurePlan(pager);
		List<Map<String,Object>> list=pager.getRoot();

		return pager;
	}
	
	@RequestMapping("/report/exportMatePurePlan.do")
	@ResponseBody
	public  void exportMatePurePlan( MapParams params,HttpServletRequest request,HttpServletResponse response) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		XSSFWorkbook wb = new XSSFWorkbook();    
		Sheet sheet1 = wb.createSheet("报表");
		LinkedHashMap<String,String> titles=new LinkedHashMap<String,String>();
		titles.put("SAMPNM", "订货样衣编号");
		titles.put("PRODNM", "产品货号");
		titles.put("IDSUNM","原供应商");
		titles.put("NWSUNM","新供应商");
		titles.put("MTTYPE","进口/国产");
		titles.put("MATENO","供应商面料货号");
		titles.put("MLITNO","面料货号");
		titles.put("MATESO","主料/拼料");
		titles.put("PLDATE","计划成衣交期");
		titles.put("MLDATE","计划面料交期");
		titles.put("MTPUPR","面料单价");
		titles.put("HTTRPR","合同单价");
		titles.put("MTCOMP","面料成分");
		titles.put("YARMCT","纱支针数");
		titles.put("GRAMWT","克重密度");
		titles.put("MLWDTH","门幅");
		titles.put("ORMTQT","下单件数");
		titles.put("MTCNQT","单耗");
		titles.put("ORMLQT","下单米数");
		titles.put("HTTRQT","合同数量");
		titles.put("HTORDT","合同交期");
		titles.put("SPSEANM","季节");
		titles.put("SPBANM","上市批次");
		titles.put("COLRNM","颜色");
		titles.put("VERSNM","版型");
		titles.put("SPBSENM","大系列");
		titles.put("SPCLNM","大类");
		titles.put("SPTYNM","小类");
		titles.put("SPSENM","系列");



		crreateTitle_export(wb,sheet1,titles);
		crreateData_export(wb,sheet1,titles,params);
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");    
        response.setHeader("Content-disposition", "attachment;filename="+new String("面料采购计划表".getBytes(),"ISO8859-1")+".xlsx");    
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
		
		
		List<OrderNumTotal> list=null;//orderNumTotalRepository.query(params.getParams());
		if(list==null || list.size()==0){
			return;
		}
		for(int i=0;i<list.size();i++){
			OrderNumTotal orderNumTotal=list.get(i);
			Row row = sheet1.createRow((short)i+1);
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
	
	/**
	 * 双击面料计划表的的时候，弹出框
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param ormtno
	 * @param htitno
	 * @return
	 */
	public  List<Map<String,Object>> query_mate_podtl(String ormtno,String htitno) {
		return reportRepository.query_mate_podtl(ormtno, htitno);
	}
	@RequestMapping("/report/orderTotalPrint/query.do")
	@ResponseBody
	public  Pager<Map<String,Object>> queryOrderTotalPrint( Pager<Map<String,Object>> pager) {
		//在查询的时候判断，是否已经超时了，如果已经超时了，就不能查询了
		
		
		pager= reportRepository.queryOrderTotalPrint(pager);
		List<Map<String,Object>> list=pager.getRoot();
		for(Map<String,Object> map:list){
			map.put("BRADNM", PubCodeCache.getBradno_name(map.get("BRADNO").toString()));
			map.put("SPCLNM", PubCodeCache.getSpclno_name(map.get("SPCLNO").toString()));
			map.put("SPTYNM",PubCodeCache.getSptyno_name( map.get("SPTYNO").toString()));
			map.put("SPSENM", PubCodeCache.getSpseno_name(map.get("SPSENO").toString()));
			map.put("SPBANM", PubCodeCache.getSpbano_name(map.get("SPBANO").toString()));
			map.put("CHANNM", ContextUtils.getChanno(map.get("CHANNO").toString()).getChannm());
			
		}

		return pager;
	}
	
	@RequestMapping("/report/orderTotalPrint/export1.do")
	@ResponseBody
	public  void orderTotalPrint_export1(MapParams params,HttpServletRequest request,HttpServletResponse response) throws IOException, JRException {
		List<OrderPrint1> list=reportRepository.orderTotalPrint_export1(params.getParams());

		String reportFilePath = "";
		reportFilePath = request
				.getSession()
				.getServletContext()
				.getRealPath(
						File.separator+"report"+File.separator+"ireport"+File.separator+"Dinghhz_YXGS_Daxl.jasper");

		Map<String, Object> rpt_params = new HashMap<String, Object>();
		rpt_params.put("p_shul_title", "订货数量");


		File reportFile = new File(reportFilePath);

		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());

		JasperPrint jasperprint = JasperFillManager.fillReport(jasperReport,
				rpt_params, new JRBeanCollectionDataSource(list));

		OutputStream httpOut = response.getOutputStream();
		response.reset();
		response.setCharacterEncoding("GBK");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String("订货汇总".getBytes("GBK"), "iso8859-1")
				+ ".xls");

		expoertReportToExcelStream(jasperprint, httpOut);

		httpOut.close(); 
	}
	
	@RequestMapping("/report/orderTotalPrint/export2.do")
	@ResponseBody
	public  void orderTotalPrint_export2(MapParams params,HttpServletRequest request,HttpServletResponse response) throws IOException, JRException {
		List<OrderPrint1> list=reportRepository.orderTotalPrint_export2(params.getParams());

		String reportFilePath = "";
		reportFilePath = request
				.getSession()
				.getServletContext()
				.getRealPath(
						File.separator+"report"+File.separator+"ireport"+File.separator+"Dinghhz_YXGS_Daxl _gongys.jasper");

		Map<String, Object> rpt_params = new HashMap<String, Object>();
		rpt_params.put("p_shul_title", "订货数量");


		File reportFile = new File(reportFilePath);

		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());

		JasperPrint jasperprint = JasperFillManager.fillReport(jasperReport,
				rpt_params, new JRBeanCollectionDataSource(list));

		OutputStream httpOut = response.getOutputStream();
		response.reset();
		response.setCharacterEncoding("GBK");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String("订货汇总-供应商".getBytes("GBK"), "iso8859-1")
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


	@RequestMapping("/report/orderTotalPrint/export3.do")
	@ResponseBody
	public  void orderTotalPrint_export3(MapParams params,HttpServletRequest request,HttpServletResponse response) throws IOException, JRException {
		List<OrderPrint1> list=reportRepository.orderTotalPrint_export3(params.getParams());

		String reportFilePath = "";
		reportFilePath = request
				.getSession()
				.getServletContext()
				.getRealPath(
						File.separator+"report"+File.separator+"ireport"+File.separator+"Dinghhz_YXGS_Daxl _gongys.jasper");

		Map<String, Object> rpt_params = new HashMap<String, Object>();
		rpt_params.put("p_shul_title", "订货数量");


		File reportFile = new File(reportFilePath);

		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());

		JasperPrint jasperprint = JasperFillManager.fillReport(jasperReport,
				rpt_params, new JRBeanCollectionDataSource(list));

		OutputStream httpOut = response.getOutputStream();
		response.reset();
		response.setCharacterEncoding("GBK");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String("订货汇总-供应商".getBytes("GBK"), "iso8859-1")
				+ ".xls");

		expoertReportToExcelStream(jasperprint, httpOut);

		httpOut.close(); 
	}


}
