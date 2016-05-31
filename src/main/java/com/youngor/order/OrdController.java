package com.youngor.order;
import java.util.ArrayList;
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
import com.youngor.utils.ContextUtils;
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
	
	
	@RequestMapping("/ord/ordty/queryAll.do")
	@ResponseBody
	public List<Ordty> queryAll() {
		List<Ordty> list=ordtyService.queryAll();
		return list;
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
		return ordService.queryOrdorg(ormtno,qyno,channo,ortyno);
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
	/**
	 * 提交审批,某个订货单位下的指定品牌，大类的订单都提交
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	@RequestMapping("/ord/qyVO/updateApprove.do")
	@ResponseBody
	public String updateApprove(String qyno,String channo,String ordorg,String ormtno,String bradno,String spclno){
		//System.out.println(orddtles);
		
		ordService.updateApprove_org(qyno, channo, ordorg, ormtno, bradno, spclno);
		return "{success:true}";
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
