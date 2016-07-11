package com.youngor.pubsize;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Pager;
import com.youngor.utils.M;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/pubSize")
public class PubSizeController {

	@Resource
	private PubSizeService pubSizeService;
	@Resource
	private PubSizeDtlService pubSizeDtlService;




	/**
	 * 查询所有的单规
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param pager
	 * @return
	 */
	@RequestMapping("/pubSize/queryStdsz.do")
	@ResponseBody
	public Pager<PubSize> queryStdsz(Pager<PubSize> pager){
		//pager.addParam(M.PubSize.sizety, "STDSZ");
		return pubSizeService.queryPage(pager);
	}
	/**
	 * 为包装规格，规格池，规格系类选择单规的时候查询的
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param pager
	 * @return
	 */
	@RequestMapping("/pubSize/queryStdsz4Sel.do")
	@ResponseBody
	public Pager<PubSize> queryStdsz4Sel(Pager<PubSize> pager){
		//pager.addParam(M.PubSize.sizety, "STDSZ");
		return pubSizeService.queryStdsz4Sel(pager);
	}
	
	@RequestMapping("/pubSize/queryPrdp.do")
	@ResponseBody
	public Pager<PubSize> queryPrdp(Pager<PubSize> pager){
		pager.addParam(M.PubSize.sizety, "PRDPK");
		return pubSizeService.queryPage(pager);
	}
	
	@RequestMapping("/pubSize/queryPrdpStdsz.do")
	@ResponseBody
	public List<PubSizeDtlVO> queryPrdpStdsz(String fszno){
		//pager.addParam(M.PubSize.sizety, "PRDPK");
		//pubSizeDtlService.query(Cnd.select().andEquals(M.PubSizeDtl.fszty, "PRDPK").andEquals(M.PubSizeDtl.fszno, val))
		return pubSizeService.queryPrdpStdsz("PRDPK",fszno);
		
	}
	
	@RequestMapping("/pubSize/selPrdpStdsz.do")
	@ResponseBody
	public void selPrdpStdsz(String fszno,String sizety,String sizeno){
		//pager.addParam(M.PubSize.sizety, "PRDPK");
		//pubSizeDtlService.query(Cnd.select().andEquals(M.PubSizeDtl.fszty, "PRDPK").andEquals(M.PubSizeDtl.fszno, val))
		PubSizeDtl dtl=new PubSizeDtl();
		dtl.setFszty("PRDPK");
		dtl.setFszno(fszno);
		dtl.setSizety(sizety);
		dtl.setSizeno(sizeno);
		pubSizeDtlService.create(dtl);
		
	}
	
	@RequestMapping("/pubSize/updatePrdpStdszSizeqt.do")
	@ResponseBody
	public void updatePrdpStdszSizeqt(String fszno,String sizety,String sizeno,Integer sizeqt){
		
		pubSizeDtlService.update(Cnd.update().andEquals(M.PubSizeDtl.fszno, fszno).andEquals(M.PubSizeDtl.fszty, "PRDPK")
				.andEquals(M.PubSizeDtl.sizeno, sizeno).andEquals(M.PubSizeDtl.sizety, sizety).set(M.PubSizeDtl.sizeqt, sizeqt));
		
	}
	
	@RequestMapping("/pubSize/deletePrdpStdszSizeqt.do")
	@ResponseBody
	public void deletePrdpStdszSizeqt(String fszno,String sizety,String sizeno){
		
		pubSizeDtlService.deleteBatch(Cnd.delete().andEquals(M.PubSizeDtl.fszno, fszno).andEquals(M.PubSizeDtl.fszty, "PRDPK")
				.andEquals(M.PubSizeDtl.sizeno, sizeno).andEquals(M.PubSizeDtl.sizety, sizety));
		
	}
	
	//---------------规格池部分
	@RequestMapping("/pubSize/querySizegpBradnoSpclno.do")
	@ResponseBody
	public List<Map<String,Object>> querySizegpBradnoSpclno(String bradno,String spclno){
		
		List<Map<String,Object>> list= pubSizeService.querySizegpBradnoSpclno(bradno,spclno);
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map:list){
			Map<String,Object> res=new HashMap<String,Object>();
			res.put("bradno", map.get("BRADNO"));
			res.put("spclno", map.get("SPCLNO"));
			res.put("bradno_name", map.get("BRADNO_NAME"));
			res.put("spclno_name", map.get("SPCLNO_NAME"));
			result.add(res);
		}
		return result;
	}
	/**
	 * 查询单规的规格池
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param szbrad
	 * @param szclno
	 * @return
	 */
	@RequestMapping("/pubSize/querySizegpStdsz.do")
	@ResponseBody
	public List<PubSizeDtlVO> querySizegpStdsz(String szbrad,String szclno){
		return pubSizeService.querySizegp("SIZEGP",szbrad+szclno, "STDSZ");
		
	}
	
	
	/**
	 * 创建单规的规格池
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param szbrad
	 * @param szclno
	 * @param sizeno
	 * @param sizety
	 */
	@RequestMapping("/pubSize/createSizegpStdsz.do")
	@ResponseBody
	public void createSizegpStdsz(String szbrad,String szclno,String sizeno) {
		//pager.addParam(M.PubSize.sizety, "PRDPK");
		//pubSizeDtlService.query(Cnd.select().andEquals(M.PubSizeDtl.fszty, "PRDPK").andEquals(M.PubSizeDtl.fszno, val))
		
		pubSizeService.createSizegp(szbrad, szclno, sizeno, "STDSZ");
		
	}
	/**
	 * 删除单规的规格池
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param szbrad
	 * @param szclno
	 * @param sizeno
	 */
	@RequestMapping("/pubSize/deleteSizegpStdsz.do")
	@ResponseBody
	public void deleteSizegpStdsz(String szbrad,String szclno,String sizeno) {
		pubSizeDtlService.deleteBatch(Cnd.delete().andEquals(M.PubSizeDtl.fszno, szbrad+szclno).andEquals(M.PubSizeDtl.fszty, "SIZEGP")
				.andEquals(M.PubSizeDtl.sizeno, sizeno).andEquals(M.PubSizeDtl.sizety, "STDSZ"));
	}
	
	
	/**
	 * 查询单规的规格池
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param szbrad
	 * @param szclno
	 * @return
	 */
	@RequestMapping("/pubSize/querySizegpPrdpk.do")
	@ResponseBody
	public List<PubSizeDtlVO> querySizegpPrdpk(String szbrad,String szclno){
		return pubSizeService.querySizegp("SIZEGP",szbrad+szclno,"PRDPK");
		
	}
	/**
	 * 创建单规的规格池
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param szbrad
	 * @param szclno
	 * @param sizeno
	 * @param sizety
	 */
	@RequestMapping("/pubSize/createSizegpPrdpk.do")
	@ResponseBody
	public void createSizegpPrdpk(String szbrad,String szclno,String sizeno) {
		//pager.addParam(M.PubSize.sizety, "PRDPK");
		//pubSizeDtlService.query(Cnd.select().andEquals(M.PubSizeDtl.fszty, "PRDPK").andEquals(M.PubSizeDtl.fszno, val))
		
		pubSizeService.createSizegp(szbrad, szclno, sizeno, "PRDPK");
		
	}
	/**
	 * 删除单规的规格池
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param szbrad
	 * @param szclno
	 * @param sizeno
	 */
	@RequestMapping("/pubSize/deleteSizegpPrdpk.do")
	@ResponseBody
	public void deleteSizegpPrdpk(String szbrad,String szclno,String sizeno) {
		pubSizeDtlService.deleteBatch(Cnd.delete().andEquals(M.PubSizeDtl.fszno, szbrad+szclno).andEquals(M.PubSizeDtl.fszty, "SIZEGP")
				.andEquals(M.PubSizeDtl.sizeno, sizeno).andEquals(M.PubSizeDtl.sizety, "PRDPK"));
	}
	
	
	// -----------------------------------------------------------------------规格范围部分
	@RequestMapping("/pubSize/queryPrdszty.do")
	@ResponseBody
	public Pager<PubSize> queryPrdszty(Pager<PubSize> pager){
		pager.addParam(M.PubSize.sizety, "PRDSZTY");
		return pubSizeService.queryPage(pager);
	}
	/**
	 * 查询单规的
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param szbrad
	 * @param szclno
	 * @return
	 */
	@RequestMapping("/pubSize/queryPrdsztyStdsz.do")
	@ResponseBody
	public List<PubSizeDtlVO> queryPrdsztyStdsz(String fszno,String fszty){
		return pubSizeService.queryPrdszty("PRDSZTY",fszno, "STDSZ");
		
	}
	@RequestMapping("/pubSize/queryPrdsztyPrdpk.do")
	@ResponseBody
	public List<PubSizeDtlVO> queryPrdsztyPrdpk(String fszno,String fszty){
		return pubSizeService.queryPrdszty("PRDSZTY",fszno, "PRDPK");
		
	}
	/**
	 * 在为规格范围新增单规的时候
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param fszno
	 * @param fszty
	 * @param ormtno
	 * @param fszty_size
	 * @param fszno_size
	 * @return
	 */
	@RequestMapping("/pubSize/queryPrdsztyStdsz4Size.do")
	@ResponseBody
	public List<PubSizeDtlVO> queryPrdsztyStdsz4Size(String fszno,String fszty,String ormtno,String fszty_size,String fszno_size){
		return pubSizeService.queryPrdsztyStdsz4Size("PRDSZTY",fszno, "STDSZ",ormtno,fszty_size,fszno_size);
		
	}
	/**
	 * 在为规格范围新增包装规格的时候
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param fszno
	 * @param fszty
	 * @param ormtno
	 * @param fszty_size
	 * @param fszno_size
	 * @return
	 */
	@RequestMapping("/pubSize/queryPrdsztyPrdpk4Size.do")
	@ResponseBody
	public List<PubSizeDtlVO> queryPrdsztyPrdpk4Size(String fszno,String fszty,String ormtno,String fszty_size,String fszno_size){
		return pubSizeService.queryPrdsztyStdsz4Size("PRDSZTY",fszno,  "PRDPK",ormtno,fszty_size,fszno_size);
		
	}
	/**
	 * 规格范围选取 -- 规格池里的--单规和规格范围的时候,用的
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param szbrad
	 * @param szclno
	 * @return
	 */
	@RequestMapping("/pubSize/querySizegpPrdszty.do")
	@ResponseBody
	public List<PubSizeDtlVO> querySizegpPrdszty(String szbrad,String szclno,String sizety,String fszty,String fszno){
		return pubSizeService.querySizegpPrdszty(szbrad,szclno, sizety,fszty,fszno);
		
	}
	/**
	 * 创建单规的
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param szbrad
	 * @param szclno
	 * @param sizeno
	 * @param sizety
	 */
	@RequestMapping("/pubSize/createPrdsztyDtl.do")
	@ResponseBody
	public void createPrdsztyStdsz(String fszno,String fszty,String[] sizenos,String sizety) {

		//pubSizeService.createSizegp(szbrad, szclno, sizeno, "PRDSZTY");
		for(String sizeno:sizenos){
			PubSizeDtl dtl=new PubSizeDtl();
			dtl.setFszty(fszty);
			dtl.setFszno(fszno);
			dtl.setSizety(sizety);
			dtl.setSizeno(sizeno);
			pubSizeDtlService.create(dtl);
		}
		
		
	}
	/**
	 * 删除单规的
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param szbrad
	 * @param szclno
	 * @param sizeno
	 */
	@RequestMapping("/pubSize/deletePrdsztyDtl.do")
	@ResponseBody
	public void deletePrdsztyDtl(String fszno,String fszty,String sizeno,String sizety) {
		pubSizeDtlService.deleteBatch(Cnd.delete().andEquals(M.PubSizeDtl.fszno, fszno).andEquals(M.PubSizeDtl.fszty, fszty)
				.andEquals(M.PubSizeDtl.sizeno, sizeno).andEquals(M.PubSizeDtl.sizety, sizety));
	}
	
	
	
	
	//-------------------------------------------------------------------------
	/**
	 * 查询规格范围,查询条件是品牌+大类,在样衣管理的时候使用
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	@RequestMapping("/pubSize/queryPRDSZTY4SampleDesign.do")
	@ResponseBody
	public List<PubSize> queryPRDSZTY4SampleDesign(String szbrad,String szclno) {
		List<PubSize> pubSizees=pubSizeService.query(Cnd.select()
				.andEquals(M.PubSize.sizety, "PRDSZTY")
				.andEquals(M.PubSize.szbrad, szbrad)
				.andEquals(M.PubSize.szclno, szclno)
				.andEquals(M.PubSize.sizest, 1)
				.andEquals(M.PubSize.szsast, 1)
				.asc(M.PubSize.sizeso));
		return pubSizees;
	}
	
	/**
	 * 查询规格范围,查寻某次订货会使用的规格范围
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	@RequestMapping("/pubSize/queryPRDSZTY4Ordmt.do")
	@ResponseBody
	public List<PubSizeVO> queryPRDSZTY4Ordmt(String szbrad,String szclno,String ormtno,String versno,String spseno) {	
		List<PubSizeVO> pubSizees=pubSizeService.queryPRDSZTY4Ordmt(szbrad, szclno, ormtno,versno,spseno);
		return pubSizees;
	}
	

	@RequestMapping("/pubSize/load.do")
	@ResponseBody
	public PubSize load(PubSize.PK id) {
		return pubSizeService.get(id);
	}
	
	@RequestMapping("/pubSize/create.do")
	//@ResponseBody
	public PubSize create(@RequestBody PubSize pubSize) {
		pubSizeService.create(pubSize);
		return pubSize;
	}
	
	@RequestMapping("/pubSize/update.do")
	//@ResponseBody
	public  PubSize update(@RequestBody PubSize pubSize) {
		pubSizeService.update(pubSize);
		return pubSize;
	}
	
	@RequestMapping("/pubSize/deleteById.do")
	//@ResponseBody
	public PubSize.PK deleteById(PubSize.PK id) {
		pubSizeService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/pubSize/destroy.do")
	//@ResponseBody
	public PubSize destroy(@RequestBody PubSize pubSize) {
		pubSizeService.delete(pubSize);
		return pubSize;
	}
	
	
}
