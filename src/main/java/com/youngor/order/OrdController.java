package com.youngor.order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
		ordService.clearSampno(sampno);
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
	 * 总部进行审批，第二次审批
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	@RequestMapping("/ord/process2.do")
	@ResponseBody
	public String process2(String[] mlornoes ) {
		ordService.process2(mlornoes);
		return "{success:true}";
	}
	
	@RequestMapping("/ord/back.do")
	@ResponseBody
	public String back(String[] mlornoes ) {
		ordService.back(mlornoes);
		return "{success:true}";
	}
	@RequestMapping("/ord/isfect_no.do")
	@ResponseBody
	public String isfect_no(String[] mlornoes ) {
		ordService.isfect_no(mlornoes);
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
	public List<Org> queryOrdorg(String ormtno,String qyno,String channo,String ortyno) {
		//return null;
		List<Org> orges= ordService.queryOrdorg(ormtno,qyno,channo,ortyno);
		Org org=new Org();
		org.setOrgno("");
		org.setOrgnm("不限");
		orges.add(0, org);
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
	public Pager<Map<String,Object>> queryZgsVO(Pager<Map<String,Object>> pager){
		return ordService.queryZgsVO(pager);
	}
	
	@RequestMapping("/ord/zgsVO/zgs_check_canedit.do")
	@ResponseBody
	public Map<String,Object> zgs_check_canedit(String ormtno,String bradno,String spclno){
		Map<String,Object> map= ordService.zgs_check_canedit(ormtno, bradno, spclno);
		if(map==null || map.size()==0){
			map=new HashMap<String,Object>();
			map.put("canedit", false);
		} else {
			BigDecimal SDTYNO=(BigDecimal)map.get("SDTYNO");
			BigDecimal ORSTAT=(BigDecimal)map.get("ORSTAT");
			if(SDTYNO.intValue()>0 && ORSTAT.intValue()>0){
				map.put("canedit", true);
			} else {
				map.put("canedit", false);
			}
		}
		return map;
	}
	
	@RequestMapping("/ord/zgsVO/queryOrderState.do")
	@ResponseBody
	public List<Map<String,Object>> zgs_queryOrderState(String ormtno,String bradno,String spclno){
		return ordService.zgs_queryOrderState(ormtno, bradno, spclno);
	}
	/**
	 * 清零
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param sampnos
	 * @return
	 */
	@RequestMapping("/ord/zgsVO/clearNum.do")
	@ResponseBody
	public String clearNum(String[] sampnos,String ormtno){
		ordService.clearNum(sampnos,ormtno);
		return "success";
	}
	@RequestMapping("/ord/zgsVO/meger_all.do")
	@ResponseBody
	public String meger_all(@RequestBody ArrayList<Map<String,Object>> data) {
		ordService.meger_all(data);
		return "success";
	}
	@RequestMapping("/ord/zgsVO/meger_comp.do")
	@ResponseBody
	public String meger_comp(@RequestBody ArrayList<Map<String,Object>> data) {
		ordService.meger_comp(data);
		return "success";
	}
	@RequestMapping("/ord/zgsVO/query_meger_comp.do")
	@ResponseBody
	public List<Map<String,Object>> query_meger_comp(String SAMPNO) {
		return ordService.query_meger_comp(SAMPNO);
	}
	@RequestMapping("/ord/zgsVO/recover.do")
	@ResponseBody
	public String recover(String[] sampnos,String ormtno) {
		ordService.recover(sampnos,ormtno);
		return "success";
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
	public List<Map<String,Object>> querySizeVOColumns(String sizegp,Integer sztype){
		
		return ordService.sizeVO_querySizeVOColumns( sizegp, sztype);
	}
	
	@RequestMapping("/ord/sizeVO/querySizeVOData.do")
	@ResponseBody
	public List<Map<String,Object>> querySizeVOData(@RequestBody Map<String,Object> params){
		return ordService.sizeVO_querySizeVOData(params);
	}
	
	//=====================================================================
	@RequestMapping("/ord/ordMgr/queryOrdMgr.do")
	@ResponseBody
	public Pager<Map<String,Object>> queryOrdMgr(Pager<Map<String,Object>> pager){
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
