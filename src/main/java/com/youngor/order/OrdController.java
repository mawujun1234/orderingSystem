package com.youngor.order;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.mawujun.utils.page.Pager;
import com.youngor.ordmt.Ordmt;
import com.youngor.org.Org;
import com.youngor.permission.ShiroUtils;
import com.youngor.utils.ContextUtils;
import com.youngor.utils.MapParams;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/ord")
public class OrdController {

	@Resource
	private OrdService ordService;
	@Resource
	private OrdtyService ordtyService;
	@RequestMapping("/ord/mobile/getOrdmt.do")
	@ResponseBody
	public Ordmt getOrdmt() {
		return ContextUtils.getFirstOrdmt();
	}

	/**
	 * 查询某个样衣的信息
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param id
	 * @return
	 */
	@RequestMapping("/ord/mobile/querySample.do")
	@ResponseBody
	public Map<String,Object> querySample(String sampnm) {
		//在查询的时候判断，是否已经超时了，如果已经超时了，就不能查询了
		
		
		return ordService.querySample(sampnm);
	}
	/**
	 * 创建订单明细 和规格明细数据
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param suitVOs
	 * @return
	 */
	@RequestMapping("/ord/mobile/createOrddtl.do")
	@ResponseBody
	public String createOrddtl(@RequestBody SuitVO[] suitVOs) {
		//System.out.println(suitVOs.length);
		ordService.createOrddtl(suitVOs);
		return "success";
	}
	
	@RequestMapping("/ord/mobile/clearSampno.do")
	@ResponseBody
	public String clearSampno(String sampno) {
		//System.out.println(suitVOs.length);
		ordService.mobile_clearSampno(sampno);
		return "success";
	}
	
	
	@RequestMapping("/ord/mobile/checked_closeing_info.do")
	@ResponseBody
	public Map<String,Object> checked_closeing_info() {
		return ordService.checked_closeing_info();
	}
	
	@RequestMapping("/ord/mobile/queryMyInfoVO.do")
	@ResponseBody
	public MyInfoVO queryMyInfoVO() {
		return ordService.queryMyInfoVO();
	}
	
	@RequestMapping("/ord/mobile/confirm.do")
	@ResponseBody
	public String confirm() {
		ordService.confirm();
		return "{success:true}";
	}
	/**
	 * 二次订货后，订单完成的时候
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	@RequestMapping("/ord/mobile/confirm2.do")
	@ResponseBody
	public String confirm2() {
		ordService.confirm2();
		return "{success:true}";
	}
	
	
	
	
	
	/**
	 * 当移动端第一次提交后。总公司在后台进行审批过后，移动端才可以进行二次定后,第一次审批
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	@RequestMapping("/ord/process1.do")
	@ResponseBody
	public String process1(MapParams params) {
		ordService.process1(params.getParams());
		return "{success:true}";
	}
	
	/**
	 * 订单管理--审批
	 * 总部进行审批，第二次审批
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	@RequestMapping("/ord/ordMgr/process2.do")
	@ResponseBody
	public String ordMgr_process2(String[] mlornoes ) {
		//
		ordService.ordMgr_process2(mlornoes);
		return "{success:true}";
	}
	/**
	 * 订单管理--退回
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param mlornoes
	 * @return
	 */
	@RequestMapping("/ord/ordMgr/back.do")
	@ResponseBody
	public String ordMgr_back(String[] mlornoes ) {
		ordService.ordMgr_back(mlornoes);
		return "{success:true}";
	}
	/**
	 * 订单流转
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param mlorno
	 * @param sdtyno
	 * @return
	 */
	@RequestMapping("/ord/ordMgr/ordercircle.do")
	@ResponseBody
	public String ordMgr_ordercircle( String[] mlornoes,String sdtyno) {
		ordService.ordMgr_ordercircle(mlornoes,sdtyno);
		return "{success:true}";
	}
	@RequestMapping("/ord/ordMgr/isfect_no.do")
	@ResponseBody
	public String ordMgr_isfect_no(String[] mlornoes ) {
		ordService.ordMgr_isfect_no(mlornoes);
		return "{success:true}";
	}
	@RequestMapping("/ord/ordty/queryAll.do")
	@ResponseBody
	public List<Ordty> queryAll() {
		List<Ordty> list=ordtyService.queryAll();
		return list;
	}
	
	
	/**
	 * 营销公司登录后，获取订单状态，如果下面所有的区域都已经提交了的话，
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	@RequestMapping("/ord/mobile/yxgs/getOrstat.do")
	@ResponseBody
	public Map<String,Object> yxgs_getOrstat() {
		Integer  canConfirm=ordService.yxgs_getOrstat();
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("canConfirm", canConfirm);
		result.put("orgnm", ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg().getOrgnm());
		return result;
		//return "{success:true}";
	}
	@RequestMapping("/ord/mobile/confirm_yxgs.do")
	@ResponseBody
	public String confirm_yxgs() {
		ordService.yxgs_confirm();
		return "{success:true}";
	}
	
	/**
	 * 查询这次订货会当前区域下的订货单位
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	@RequestMapping("/ord/queryOrdorg.do")
	@ResponseBody
	public List<Org> queryOrdorg(String ormtno,String qyno,String channo,String ortyno,Boolean showBlank) {
		//return null;
		List<Org> orges= ordService.queryOrdorg(ormtno,qyno,channo,ortyno);
		if(showBlank!=null && showBlank==true){
			Org org=new Org();
			org.setOrgno("");
			org.setOrgnm("不限");
			orges.add(0, org);
		}
		
		return orges;
	}
	
	/**
	 * 查询区域下的
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param pager
	 * @return
	 */
	@RequestMapping("/ord/quVO/queryQyVO.do")
	@ResponseBody
	public Pager<QyVO> queryQyVO(Pager<QyVO> pager){
		return ordService.queryQyVO(pager);
	}
	
	/**
	 * 调整数量
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 */
	@RequestMapping("/ord/quVO/updateOrmtqt.do")
	@ResponseBody
	public void updateOrmtqt(String mtorno,String sampno,String suitno,Integer ormtqt){
		ordService.updateOrmtqt(mtorno, sampno, suitno, ormtqt);
	}
	/**
	 * 获取某次订货会中的某个样衣编号使用的套件
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param mtorno
	 * @param sampnm
	 */
	@RequestMapping("/ord/qyVO/querySuitBySampnm.do")
	@ResponseBody
	public List<QyNewFormVO> querySuitBySampnm(String ormtno,String sampnm){
		return ordService.querySuitBySampnm(ormtno, sampnm);
	}
	
	@RequestMapping("/ord/qyVO/createNew.do")
	@ResponseBody
	public String createNew(@RequestBody ArrayList<Orddtl> orddtles,String ordorg,String ortyno,String channo,String ormtno){
		//System.out.println(orddtles);
		ordService.createNew(orddtles, ordorg, ortyno, channo, ormtno);
		return "{success:true}";
	}
//	/**
//	 * 提交审批,某个订货单位下的指定品牌，大类的订单都提交
//	 * @author mawujun qq:16064988 mawujun1234@163.com
//	 * @return
//	 */
//	@RequestMapping("/ord/qyVO/updateApprove.do")
//	@ResponseBody
//	public String updateApprove(String qyno,String channo,String ordorg,String ormtno,String bradno,String spclno){
//		//System.out.println(orddtles);
//		ordService.updateApprove_org(qyno, channo, ordorg, ormtno, bradno, spclno);
//		return "{success:true}";
//	}
	@RequestMapping("/ord/qyVO/reloadTotal.do")
	@ResponseBody
	public ReloadTotal reloadTotal(MapParams params){
		
		ReloadTotal reloadTotal= ordService.reloadTotal(params);
		return reloadTotal==null?new ReloadTotal():reloadTotal;
	}
	
	/**
	 * 查询区域下的
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param pager
	 * @return
	 */
	@RequestMapping("/ord/zgsVO/queryZgsVO.do")
	@ResponseBody
	public Pager<Map<String,Object>> zgsVO_queryZgsVO(Pager<Map<String,Object>> pager){
		return ordService.zgsVO_queryZgsVO(pager);
	}
	
	@RequestMapping("/ord/zgsVO/zgs_check_canedit.do")
	@ResponseBody
	public Map<String,Object> zgsVO_check_canedit(String ormtno,String bradno,String spclno){
		Map<String,Object> map= ordService.zgsVO_check_canedit(ormtno, bradno, spclno);
		if(map==null || map.size()==0){
			map=new HashMap<String,Object>();
			map.put("canedit", false);
		} else {
			BigDecimal SDTYNO=(BigDecimal)map.get("SDTYNO");
			BigDecimal ORSTAT=(BigDecimal)map.get("ORSTAT");
			if(SDTYNO.intValue()>0 && ORSTAT.intValue()==0){
				map.put("canedit", true);
			} else {
				map.put("canedit", false);
			}
		}
		return map;
	}
	
	@RequestMapping("/ord/zgsVO/queryOrderState.do")
	@ResponseBody
	public List<Map<String,Object>> zgsVO_queryOrderState(String ormtno,String bradno,String spclno){
		return ordService.zgsVO_queryOrderState(ormtno, bradno, spclno);
	}
	/**
	 * 清零,取消
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param sampnos
	 * @return
	 */
	@RequestMapping("/ord/zgsVO/clearNum.do")
	@ResponseBody
	public String zgsVO_clearNum(String[] sampnos,String ormtno){
		ordService.zgsVO_clearNum(sampnos,ormtno);
		return "{success:true}";
	}
	/**
	 * 合并
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param data
	 * @param ormtno
	 * @return
	 */
	@RequestMapping("/ord/zgsVO/meger_all.do")
	@ResponseBody
	public String zgsVO_meger_all(@RequestBody ArrayList<Map<String,Object>> data,String ormtno) {
		ordService.zgsVO_meger_all(data,ormtno);
		return "{success:true}";
	}
	/**
	 * 拆分
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param data
	 * @param ormtno
	 * @return
	 */
	@RequestMapping("/ord/zgsVO/meger_comp.do")
	@ResponseBody
	public String zgsVO_meger_comp(@RequestBody ArrayList<Map<String,Object>> data,String ormtno) {
		ordService.zgsVO_meger_comp(data,ormtno);
		return "{success:true}";
	}
	@RequestMapping("/ord/zgsVO/query_meger_comp.do")
	@ResponseBody
	public List<Map<String,Object>> zgsVO_query_meger_comp(String SAMPNO) {
		return ordService.zgsVO_query_meger_comp(SAMPNO);
	}
	@RequestMapping("/ord/zgsVO/recover.do")
	@ResponseBody
	public String zgsVO_recover(@RequestBody ArrayList<Map<String,Object>> data,String ormtno) {
		ordService.zgsVO_recover(data,ormtno);
		return "{success:true}";
	}
	@RequestMapping("/ord/zgsVO/balanceOver.do")
	@ResponseBody
	public String zgsVO_balanceOver(String ormtno,String bradno,String spclno) {
		ordService.zgsVO_balanceOver(ormtno, bradno, spclno);
		return "{success:true}";
	}
	
	@RequestMapping("/ord/zgsVO/exportZgsVO.do")
	@ResponseBody
	public void zgsVO_exportZgsVO(MapParams params,HttpServletResponse response) throws Exception {
		//samplePlanService.lockOrunlock(plspno, plspst);
		//System.out.println(params);
		XSSFWorkbook wb = new XSSFWorkbook();    
		Sheet sheet1 = wb.createSheet("总公司平衡");
		LinkedHashMap<String,String> titles=new LinkedHashMap<String,String>();
		
		titles.put("PALTPY", "状态");
		titles.put("SDTYNO", "订单节点");
		titles.put("SPTYNM", "小类");
		titles.put("SPSENM", "系列");
		titles.put("SAMPNM", "订货样衣编号");
		
		titles.put("ORMTQS00", "标准");
		titles.put("ORMTQS01", "上衣");
		titles.put("ORMTQS02", "裤子");
		titles.put("ORMTQS04", "裙子");
		
		titles.put("ORMTQT00", "标准");
		titles.put("ORMTQT01", "上衣");
		titles.put("ORMTQT02", "裤子");
		titles.put("ORMTQT04", "裙子");
		
		titles.put("PLSPNM", "目标样衣编号");

		zgsVO_crreateTitle_exportZgsVO(wb,sheet1,titles);
		
		zgsVO_crreateData_exportZgsVO(wb,sheet1,titles,params);

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");    
        response.setHeader("Content-disposition", "attachment;filename="+new String("总公司平衡".getBytes(),"ISO8859-1")+".xlsx");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
	}
	private void zgsVO_crreateTitle_exportZgsVO(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles){
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
	    Cell cell5 = title0.createCell(5);
	    cell5.setCellValue("原始数量");
	    cell5.setCellStyle(cellStyle);
	    Cell cell9 = title0.createCell(9);
	    cell9.setCellValue("确认数量");
	    cell9.setCellStyle(cellStyle);
	    sheet1.addMergedRegion(new CellRangeAddress(0, 0, 5, 8));
	    sheet1.addMergedRegion(new CellRangeAddress(0, 0, 9, 12));
		 
	    Row title = sheet1.createRow((short)1);
		
		int i=0;
		for(Entry<String,String> entry:titles.entrySet()){
			Cell cell = title.createCell(i);
			cell.setCellValue(entry.getValue());
			cell.setCellStyle(cellStyle);
			i++;
		}
	}
	
	private void zgsVO_crreateData_exportZgsVO(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles,MapParams params){
		List<Map<String,Object>> list=ordService.zgsVO_query_exportZgsVO(params);
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
	
	/**
	 * 尾箱调整的时候判断，是否可以进行尾箱调整
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param ormtno
	 * @param bradno
	 * @param spclno
	 * @return
	 */
	@RequestMapping("/ord/wxtz/check_stat.do")
	@ResponseBody
	public String wxtz_check_stat(String ormtno,String bradno,String spclno){
		return "{success:true,stat:'"+ordService.wxtz_check_stat(ormtno, bradno, spclno)+"'}";
	}
	@RequestMapping("/ord/wxtz/queryWx.do")
	@ResponseBody
	public Pager<Map<String,Object>> wxtz_queryWx(Pager<Map<String,Object>> pager){
		return ordService.wxtz_queryWx(pager);
	}
	
	/**
	 * 尾箱调整完成
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param ormtno
	 * @param bradno
	 * @param spclno
	 */
	@RequestMapping("/ord/wxtz/comp_wx.do")
	@ResponseBody
	public String wxtz_comp_wx(String ormtno,String bradno,String spclno){
		ordService.wxtz_comp_wx(ormtno, bradno, spclno);
		return "{success:true}";
	}
	/**
	 * 尾箱调整完成
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param ormtno
	 * @param bradno
	 * @param spclno
	 */
	@RequestMapping("/ord/wxtz/comp_wxps.do")
	@ResponseBody
	public String wxtz_comp_wxps(String ormtno,String bradno,String spclno){
		ordService.wxtz_comp_wxps(ormtno, bradno, spclno);
		return "{success:true}";
	}
	
	
	@RequestMapping("/ord/wxtz/export.do")
	@ResponseBody
	public  void wxtz_export(MapParams params,HttpServletResponse response) throws IOException {
		XSSFWorkbook wb = new XSSFWorkbook();    
		Sheet sheet1 = wb.createSheet("尾箱调整");
		LinkedHashMap<String,String> titles=new LinkedHashMap<String,String>();
		
		titles.put("SPCLNM", "大类");
		titles.put("SPTYNM", "小类");
		titles.put("SPSENM", "系列");
		titles.put("SAMPNM", "订货样衣编号");
		titles.put("PACKQT", "包装要求");
		
		titles.put("ORMTQS00", "标准");
		titles.put("ORMTQS01", "上衣");
		titles.put("ORMTQS02", "裤子");
		titles.put("ORMTQS04", "裙子");
		
		titles.put("ORMTQT00", "标准");
		titles.put("ORMTQT01", "上衣");
		titles.put("ORMTQT02", "裤子");
		titles.put("ORMTQT04", "裙子");

		//这里要做
//		List<Map<String,Object>> columns=tpService.queryTpYxgsColumns();
//		
//
		wxtz_crreateTitle_export(wb,sheet1,titles);
		
		wxtz_crreateData_export(wb,sheet1,titles,params);

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");    
        response.setHeader("Content-disposition", "attachment;filename="+new String("尾箱调整".getBytes(),"ISO8859-1")+".xlsx");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();   
		
	}
	private void wxtz_crreateTitle_export(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles){
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
	    Cell cell5 = title0.createCell(5);
	    cell5.setCellValue("原始数量");
	    cell5.setCellStyle(cellStyle);
	    Cell cell9 = title0.createCell(9);
	    cell9.setCellValue("确认数量");
	    cell9.setCellStyle(cellStyle);
	    sheet1.addMergedRegion(new CellRangeAddress(0, 0, 5, 8));
	    sheet1.addMergedRegion(new CellRangeAddress(0, 0, 9, 12));
		 
	    Row title = sheet1.createRow((short)1);
		
		int i=0;
		for(Entry<String,String> entry:titles.entrySet()){
			Cell cell = title.createCell(i);
			cell.setCellValue(entry.getValue());
			cell.setCellStyle(cellStyle);
			i++;
		}
	}
	
	private void wxtz_crreateData_export(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles,MapParams params){
		List<Map<String,Object>> list=ordService.wxtz_exportWx(params);
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

	
	//======================================================================================
	/**
	 * 获取动态生成的列
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param sizegp 规格范围
	 * @param sztype 规格上报方式
	 * @return
	 */
	@RequestMapping("/ord/sizeVO/querySizeVOColumns.do")
	@ResponseBody
	public List<Map<String,Object>> sizeVO_querySizeVOColumns(String sizegp,Integer sztype){
		
		return ordService.sizeVO_querySizeVOColumns( sizegp, sztype);
	}
	
	@RequestMapping("/ord/sizeVO/querySizeVOData.do")
	@ResponseBody
	public List<Map<String,Object>> sizeVO_querySizeVOData(@RequestBody Map<String,Object> params){
		return ordService.sizeVO_querySizeVOData(params);
	}
	
	@RequestMapping("/ord/sizeVO/updateOrdszdtl.do")
	@ResponseBody
	public String sizeVO_updateOrdszdtl(OrdszdtlVO ordszdtlVO){
		ordService.sizeVO_updateOrdszdtl(ordszdtlVO);
		return "{success:true}";
	}
	/**
	 * 自动成箱
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	@RequestMapping("/ord/sizeVO/sizeVO_auto_box.do")
	@ResponseBody
	public String sizeVO_auto_box(String ormtno,String ordtyno,String ordorg,String bradno,String spclno,Integer sztype){
		ordService.sizeVO_auto_box(ormtno, ordtyno, ordorg, bradno, spclno,sztype);
		return "{success:true}";
	}
	/**
	 * 提交审批
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	@RequestMapping("/ord/sizeVO/approve.do")
	@ResponseBody
	public String sizeVO_size_ap(String ormtno,String ordtyno,String ordorg,String bradno,String spclno){
		ordService.sizeVO_size_ap(ormtno, ordtyno, ordorg, bradno, spclno);
		return "{success:true}";
	}
	
	//=====================================================================
	@RequestMapping("/ord/ordMgr/queryOrdMgr.do")
	@ResponseBody
	public Pager<Map<String,Object>> ordMgr_queryOrdMgr(Pager<Map<String,Object>> pager){
		return ordService.ordMgr_queryOrdMgr(pager);
	}
	
//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/ord/query.do")
//	@ResponseBody
//	public Pager<Ord> query(Pager<Ord> pager){
//		return ordService.queryPage(pager);
//	}
//
//	@RequestMapping("/ord/queryAll.do")
//	@ResponseBody
//	public List<Ord> queryAll() {	
//		List<Ord> ordes=ordService.queryAll();
//		return ordes;
//	}
//	
//
//	@RequestMapping("/ord/load.do")
//	@ResponseBody
//	public Ord load(String id) {
//		return ordService.get(id);
//	}
//	
//	@RequestMapping("/ord/create.do")
//	@ResponseBody
//	public Ord create(@RequestBody Ord ord) {
//		ordService.create(ord);
//		return ord;
//	}
//	
//	@RequestMapping("/ord/update.do")
//	@ResponseBody
//	public  Ord update(@RequestBody Ord ord) {
//		ordService.update(ord);
//		return ord;
//	}
//	
//	@RequestMapping("/ord/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		ordService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/ord/destroy.do")
//	@ResponseBody
//	public Ord destroy(@RequestBody Ord ord) {
//		ordService.delete(ord);
//		return ord;
//	}
	
	
}
