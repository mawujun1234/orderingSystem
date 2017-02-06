package com.youngor.report;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.Pager;
import com.youngor.pubcode.PubCodeCache;
import com.youngor.utils.ContextUtils;
import com.youngor.utils.MapParams;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseLineBox;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.type.CalculationEnum;
import net.sf.jasperreports.engine.type.HorizontalAlignEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.ResetTypeEnum;
import net.sf.jasperreports.engine.type.VerticalAlignEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

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
	@RequestMapping("/report/exportClothPurePlan.do")
	@ResponseBody
	public  void exportClothPurePlan( MapParams params,HttpServletRequest request,HttpServletResponse response) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		XSSFWorkbook wb = new XSSFWorkbook();    
		Sheet sheet1 = wb.createSheet("报表");
		LinkedHashMap<String,String> titles=new LinkedHashMap<String,String>();
		titles.put("SAMPNM", "订货样衣编号");
		
		titles.put("SPCLNM","大类");
		titles.put("SPTYNM","小类");
		titles.put("SPSENM","系列");
		titles.put("SPRSENM","风格");
		titles.put("SPSEANM","季节");
		titles.put("SPLCNM","定位");
		titles.put("PLGRNM","商品等级");
		titles.put("SPBANM","上市批次");
		titles.put("SAMPNM","订货样衣编号");
		titles.put("PRODNM","成品货号");
		titles.put("GUSTNO","客供编号");
		titles.put("COLRNM","颜色");
		titles.put("MTTYPE","进口/国产");
		titles.put("MTCOMP","面料成分");
		titles.put("YARMCT","纱支");
		titles.put("GRAMWT","克重");
		titles.put("DESP","规格版型特殊说明");
		titles.put("SPCTPR","成衣原价");
		titles.put("aaaa","新成本价");
		titles.put("SPFTPR","出厂价");
		titles.put("SPRTPR","零售价");
		titles.put("PLDTCT","交货批次");
		titles.put("PLDATE","成衣交期");
		titles.put("IDSUNM","生产单位");
		titles.put("SPMTNM","生产类型");
		titles.put("ORMTQT","订货总量");



		crreateTitle_exportMatePurePlan(wb,sheet1,titles);
		crreateData_exportClothPurePlan(wb,sheet1,titles,params);
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");    
        response.setHeader("Content-disposition", "attachment;filename="+new String("成衣采购计划表".getBytes(),"ISO8859-1")+".xlsx");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
	}
	private void crreateData_exportClothPurePlan(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles,MapParams params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		
		List<Map<String,Object>> list=reportRepository.queryClothPurePlan(params.getParams());
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
				//String get_name="get"+StringUtils.capitalize(entry.getKey());
				//Object value=ReflectionUtils.findMethod(OrderNumTotal.class, get_name).invoke(orderNumTotal);
				Object value=map.get(entry.getKey());
				if(value!=null){
					cell.setCellValue(value.toString());
//					if(value instanceof BigDecimal){
//						//cell.setCellValue(((BigDecimal)value).setScale(2, RoundingMode.HALF_UP).doubleValue());
//						System.out.println(((BigDecimal)value).setScale(2, RoundingMode.HALF_UP));
//						cell.setCellValue(((BigDecimal)value).setScale(2, RoundingMode.HALF_UP).toString());
//					} else {
//						cell.setCellValue(value.toString());
//					}
					
				}
			}
		}
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
		titles.put("SPLCNM","定位");
		titles.put("PLGRNM","商品等级");



		crreateTitle_exportMatePurePlan(wb,sheet1,titles);
		crreateData_exportMatePurePlan(wb,sheet1,titles,params);
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");    
        response.setHeader("Content-disposition", "attachment;filename="+new String("面料采购计划表".getBytes(),"ISO8859-1")+".xlsx");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
	}
	
	private void crreateTitle_exportMatePurePlan(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles){
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
	private void crreateData_exportMatePurePlan(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles,MapParams params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		
		List<Map<String,Object>> list=reportRepository.queryMatePurePlan(params.getParams());
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
				//String get_name="get"+StringUtils.capitalize(entry.getKey());
				//Object value=ReflectionUtils.findMethod(OrderNumTotal.class, get_name).invoke(orderNumTotal);
				Object value=map.get(entry.getKey());
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
	@RequestMapping("/report/query_mate_podtl.do")
	@ResponseBody
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

		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());//(reportFile.getPath());

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
	/**
	 * 打印搭配数据
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param params
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws JRException
	 */
	@RequestMapping("/report/printDaPei.do")
	@ResponseBody
	public  void printDaPei(MapParams params,HttpServletRequest request,HttpServletResponse response) throws IOException, JRException {
		HashMap<String, Object> params_jasper = new HashMap<String, Object>();  
		ArrayList<Map<String, Object>> reportDataList = new ArrayList<Map<String, Object>>();
		
//		for(int i=0;i<10;i++){
//			
//			for(int j=0;j<9;j++){
//				HashMap<String, Object> map=new HashMap<String, Object>();
//				map.put("CLPPNM", "clppnm"+i);
//				map.put("YXGSNM", "浙江");
//				//map.put("SPCLNM", "大类"+j);
//				map.put("SPEC_CODE_01", 1155);
//				reportDataList.add(map);
//			}
//		}
		 
		List<Map<String, Object>> list = reportRepository.printDaPei(params.getParams().get("ormtno").toString());
		//进行行列转换,转换成对应的格式
		for(Map<String, Object> map:list){
			Map<String, Object> reportData_new =null;  
			for(Map<String,Object> reportData:reportDataList){
				if(map.get("CLPPNM").equals(reportData.get("CLPPNM")) && map.get("YXGSNM").toString().substring(0,2).equals(reportData.get("YXGSNM")) ){
					reportData_new=reportData;
					break;
				}
			}
			if(reportData_new==null){
				reportData_new =new HashMap<String,Object>(); 
				reportData_new.put("CLPPNM", map.get("CLPPNM"));
				reportData_new.put("YXGSNM", map.get("YXGSNM").toString().substring(0,2));
				reportData_new.put("SPEC_CODE_all", map.get("DPMTQT"));
				reportDataList.add(reportData_new);
			}
			reportData_new.put("SPEC_CODE_"+map.get("SPCLNO"), map.get("ORMTQT"));
		}

		JRDataSource dataSource = new JRBeanCollectionDataSource(reportDataList);  
		
		String reportFilePath = "";
		reportFilePath = request
				.getSession()
				.getServletContext()
				.getRealPath(
						File.separator+"report"+File.separator+"ireport"+File.separator+"printDapei.jrxml");
		JasperDesign jdesign = JRXmlLoader.load(reportFilePath);
		JRBand[] jrbands=jdesign.getAllBands();
//		for(JRBand jrband:jrbands){
//			JRDesignBand aa=(JRDesignBand)jrband;
//			if(aa.){
//				
//			}
//		}
		JRGroup[]  jRGroups=jdesign.getGroups();
//		JRDesignGroup  aa=new JRDesignGroup();
//		((JRDesignGroup)jRGroups[0]).getGroupHeaderSection().getBands()
		JRDesignBand columnHeader = (JRDesignBand)jrbands[0]; 
		JRDesignBand subtotalFooter = (JRDesignBand)jrbands[1]; 
		JRBand cDetailBand = jdesign.getDetailSection().getBands()[0];  
        JRDesignBand columnDetail = null;  
        if (cDetailBand != null && cDetailBand instanceof JRDesignBand) {  
        	columnDetail = (JRDesignBand) cDetailBand;  
        }  
        JRBaseLineBox pen=null;
		
        List<String[]> spclno_list=new ArrayList<String[]>();
        spclno_list.add(new String[]{"01","衬衫"});
        spclno_list.add(new String[]{"08","毛衫"});
        spclno_list.add(new String[]{"02","西服"});
        spclno_list.add(new String[]{"12","大衣"});
        spclno_list.add(new String[]{"04","夹克"});
        spclno_list.add(new String[]{"03","裤子"});
        spclno_list.add(new String[]{"06","领带"});
        spclno_list.add(new String[]{"07","鞋包"});
        spclno_list.add(new String[]{"all","套数小计"});
        //252是可以设置的最大宽度
        int col_unm=spclno_list.size();
		int width=252/col_unm;
		//==================================================================
		for(int i=0;i<col_unm;i++){
			String[] spclno=spclno_list.get(i);
			//---------设置字段---------------------------------------
        	JRDesignField field = new JRDesignField();  
            field.setName("SPEC_CODE_"+spclno[0] );  
            field.setValueClass(java.lang.Integer.class);  
            jdesign.addField(field);  
//            field = new JRDesignField();  
//            field.setName("TERM_MEMO_" + i);  
//            field.setValueClass(java.lang.String.class);  
//            jdesign.addField(field);  
            
            JRDesignVariable subtotal_var=new JRDesignVariable();
            subtotal_var.setName("VAR_CODE_"+spclno[0]);
            subtotal_var.setValueClass(Integer.class);
            subtotal_var.setCalculation(CalculationEnum.SUM);
            subtotal_var.setResetType(ResetTypeEnum.GROUP);
            subtotal_var.setResetGroup(jRGroups[0]);
            JRDesignExpression var_expression = new JRDesignExpression();  
            var_expression.setText("$F{SPEC_CODE_" + spclno[0] + "}");  
            subtotal_var.setExpression(var_expression);  
            jdesign.addVariable(subtotal_var); 
        	//---------设置标题------------------------------
        	JRDesignStaticText suiteGrpName = new JRDesignStaticText();
        	suiteGrpName.setWidth(width);   
        	suiteGrpName.setY(26); 
        	suiteGrpName.setX(i*width+44);   
        	suiteGrpName.setHeight(30);   
        	suiteGrpName.setText(spclno[1]);
        	suiteGrpName.setBold(true);
        	suiteGrpName.setHorizontalAlignment(HorizontalAlignEnum.CENTER);
        	suiteGrpName.setVerticalAlignment(VerticalAlignEnum.MIDDLE);
        	suiteGrpName.setFontSize(10);
        	suiteGrpName.setFontName("宋体");
        	suiteGrpName.setMode(ModeEnum.OPAQUE);
        	suiteGrpName.setBackcolor(new Color(204,204,204));
        	//设置边框
        	if(pen==null){
        		pen=new JRBaseLineBox(suiteGrpName);
            	pen.getTopPen().setLineColor(Color.BLACK);
            	pen.getTopPen().setLineWidth(0.5f);
            	pen.getRightPen().setLineColor(Color.BLACK);
            	pen.getRightPen().setLineWidth(0.5f);
            	pen.getBottomPen().setLineColor(Color.BLACK);
            	pen.getBottomPen().setLineWidth(0.5f);
            	pen.getLeftPen().setLineColor(Color.BLACK);
            	pen.getLeftPen().setLineWidth(0.5f);	
        	}
        	suiteGrpName.getLineBox().copyTopPen(pen.getTopPen());
        	suiteGrpName.getLineBox().copyRightPen(pen.getRightPen());;
        	suiteGrpName.getLineBox().copyBottomPen(pen.getBottomPen());
        	suiteGrpName.getLineBox().copyLeftPen(pen.getLeftPen());
        	
        	//suiteGrpName.setStyle(style);
        	columnHeader.addElement(suiteGrpName);  
        	
        	//---------画字段 把前面的字段名称拷贝过来------------------------------
        	JRDesignTextField specNoField = new JRDesignTextField();  
        	specNoField.setStretchWithOverflow(true);  
        	specNoField.setWidth(width);   
        	specNoField.setY(0); 
        	specNoField.setX(i*width+44);   
        	specNoField.setHeight(25);   
        	specNoField.setHorizontalAlignment(HorizontalAlignEnum.CENTER);
        	specNoField.setVerticalAlignment(VerticalAlignEnum.MIDDLE);
        	specNoField.setFontSize(10);
        	specNoField.setMode(ModeEnum.OPAQUE);
        	//specNoField.setStyle(suiteGrpName.getStyle());  
        	specNoField.setBlankWhenNull(true);  
        	if("all".equals(spclno[0])){
        		specNoField.setMode(ModeEnum.OPAQUE);
        		specNoField.setBackcolor(new Color(204,204,204));
        	}
        	
        	specNoField.getLineBox().copyTopPen(pen.getTopPen());
        	specNoField.getLineBox().copyRightPen(pen.getRightPen());;
        	specNoField.getLineBox().copyBottomPen(pen.getBottomPen());
        	specNoField.getLineBox().copyLeftPen(pen.getLeftPen());
        	//specNoField.setStretchType(StretchTypeEnum);
            JRDesignExpression expression = new JRDesignExpression(); 
            //expression.setValueClass(java.lang.Integer.class);  
            expression.setText("$F{SPEC_CODE_" + spclno[0] + "}");  
            specNoField.setExpression(expression);  
            columnDetail.addElement(specNoField); 
            
          //---------------------设置小计-----------------------------------------
            JRDesignTextField subtotle = new JRDesignTextField();
            subtotle.setWidth(width);   
            subtotle.setY(0); 
            subtotle.setX(i*width+44);   
            subtotle.setHeight(31);   
            //subtotle.setText("小计"+i);
            subtotle.setBold(true);
            subtotle.setHorizontalAlignment(HorizontalAlignEnum.CENTER);
            subtotle.setVerticalAlignment(VerticalAlignEnum.MIDDLE);
            subtotle.setFontSize(10);
            subtotle.setFontName("宋体");
            subtotle.setMode(ModeEnum.OPAQUE);
            subtotle.setBackcolor(new Color(204,204,204));
            subtotle.getLineBox().copyTopPen(pen.getTopPen());
            subtotle.getLineBox().copyRightPen(pen.getRightPen());;
            subtotle.getLineBox().copyBottomPen(pen.getBottomPen());
            subtotle.getLineBox().copyLeftPen(pen.getLeftPen());
            subtotle.setBlankWhenNull(true);  
            subtotalFooter.addElement(subtotle);  
            
            expression = new JRDesignExpression();  
            expression.setText("$V{VAR_CODE_" + spclno[0] + "}");  
            subtotle.setExpression(expression);  
		}
		
		
		
		//==================================================================
		
		JasperPrint jasperprint =JasperFillManager.fillReport( JasperCompileManager.compileReport(jdesign), 
				params_jasper,dataSource);
		
		OutputStream httpOut = response.getOutputStream();
		response.reset();
		response.setCharacterEncoding("GBK");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String("搭配情况汇总".getBytes("GBK"), "iso8859-1")
				+ ".xls");
		expoertReportToExcelStream(jasperprint, httpOut);
		httpOut.close(); 
		
	}
	
//	private JasperPrint printDaPei_sheet(){
//		
//	}


}
