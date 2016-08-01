package com.youngor.report;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
