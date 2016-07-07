package com.youngor.order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.exception.BusinessException;
import com.mawujun.utils.page.Pager;
import com.youngor.org.Chancl;
import com.youngor.utils.MapParams;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class TpService {

	@Autowired
	private TpRepository tpRepository;
	@Autowired
	private OrdRepository ordRepository;
	@Autowired
	private OrdhdRepository ordhdRepository;
	@Autowired
	private OrddtlRepository orddtlRepository;
	@Autowired
	private OrdService ordService;
	
	private String spb_orgno="10206030000";//商品部的id，也就是总部的ordorg

	public  Pager<Map<String,Object>> zgs_tpAllQuery( Pager<Map<String,Object>> pager) {
		return tpRepository.zgs_tpAllQuery(pager);
	}
	
	public  List<Map<String,Object>> zgs_tpAllExport(MapParams params) {
		return tpRepository.zgs_tpAllQuery(params.getParams());
	}
	
	public  void zgs_updateOrmtqt_tp(String ormtno,String sampno,String bradno,String spclno,String suitno,Integer ormtqs,Integer ormtqt) {
		if(ormtqt==null){
			return;
		}
		String mtorno=ordService.getMtorno(ormtno,"TP",spb_orgno);
		Ord ord=ordRepository.get(mtorno);
		if(ord==null){
			ord=new Ord();
			ord.setMtorno(mtorno);
			ord.setChanno(Chancl.GSBB.toString());
			ord.setOrdorg(spb_orgno);
			ord.setOrmtno(ormtno);
			ord.setOrtyno("TP");
			ordRepository.create(ord);
		}
		
		String mlorno=mtorno+bradno+spclno;
		Ordhd.PK pk=new Ordhd.PK();
		pk.setBradno(bradno);
		pk.setMtorno(mtorno);
		pk.setSpclno(spclno);
		Ordhd ordhd=ordhdRepository.get(pk);
		//插入订单副表
		if(ordhd==null){
			ordhd=new Ordhd();
			ordhd.setMtorno(mtorno);
			ordhd.setBradno(bradno);
			ordhd.setSpclno(spclno);
			ordhd.setMlorno(mlorno);
			ordhd.setMlorvn(1);
			//ordhd.setSdtyno();
			ordhd.setOrstat(0);
			ordhd.setSzstat(0);
			ordhd.setIsfect(0);
			ordhdRepository.create(ordhd);
		}
		
		//插入订单明细表
		Orddtl.PK pk1=new Orddtl.PK();
		pk1.setMtorno(mtorno);
		pk1.setSampno(sampno);
		pk1.setSuitno(suitno);
		Orddtl orddtl=orddtlRepository.get(pk1);
		if(orddtl==null){
			orddtl=new Orddtl();
			orddtl.setMtorno(mtorno);
			orddtl.setSampno(sampno);
			orddtl.setSuitno(suitno);
			orddtl.setMlorno(ordhd.getMlorno());
			orddtl.setMlorvn(ordhd.getMlorvn());
			orddtl.setOrmtqs(ormtqs);
			orddtl.setOrmtqt(ormtqt);
			
			orddtlRepository.create(orddtl);
			
			//一旦录入样衣的统配量，则  更新该样衣 所有订制订单中的原始数量=确认数量，确认数量=0，备注：已统配；
	        //统配总量录入 0 ，也更新所有订制订单中的确认数量为0，备注：已统配
			tpRepository.zgs_update_DZ_ormtqt(sampno,mtorno);
			tpRepository.zgs_update_DZ_ormtqt_0(sampno,mtorno);
		} else {
			orddtl.setOrmtqt(ormtqt);
			orddtlRepository.update(orddtl);
		}
		
		
		
		
		
	}
	
	public void zgs_restoreDZ(String ormtno,String sampno,String suitno) {
		String mtorno=ordService.getMtorno(ormtno,"TP",spb_orgno);
		// 删除订单明细表
		Orddtl.PK pk1 = new Orddtl.PK();
		pk1.setMtorno(mtorno);
		pk1.setSampno(sampno);
		pk1.setSuitno(suitno);
		orddtlRepository.deleteById(pk1);
		
		tpRepository.zgs_restoreDZ(sampno, mtorno);
	}
	
	public void zgs_over(String ormtno) {
		String mtorno=ordService.getMtorno(ormtno,"TP",spb_orgno);
		tpRepository.zgs_over(mtorno);
	}
	public int zgs_getOrstat(String ormtno) {
		String mtorno=ordService.getMtorno(ormtno,"TP",spb_orgno);
		Integer aa= tpRepository.zgs_getOrstat(mtorno);
		if(aa==null){
			return 0;
		}
		return aa;
	}
	
	//=========================================================================================
	public  List<Map<String,Object>> queryTpYxgsColumns() {
		return tpRepository.queryTpYxgsColumns();
	}
	
	public  List<Map<String,Object>> tpYxgsQuery( Map<String,Object> params) {
		List<Map<String,Object>> list=tpRepository.tpYxgsQuery(params);
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		//循环，进行行列转换，把营销公司转换为行显示
		Map<String,Map<String,Object>> key_cache=new HashMap<String,Map<String,Object>>();
		for(Map<String,Object> map:list){
			String key=""+map.get("SAMPNO")+map.get("SUITNO");
			if(!key_cache.containsKey(key)){
				Map<String,Object> new_map=new HashMap<String,Object>();
				new_map.put("BRADNO", map.get("BRADNO"));
				new_map.put("SPCLNO", map.get("SPCLNO"));
				new_map.put("ORMTNO", map.get("ORMTNO"));
				new_map.put("SAMPNO", map.get("SAMPNO"));
				new_map.put("SAMPNM", map.get("SAMPNM"));
				new_map.put("SUITNO", map.get("SUITNO"));
				new_map.put("SPTYNO", map.get("SPTYNO"));
				new_map.put("SPSENO", map.get("SPSENO"));
				new_map.put("PACKQT", map.get("PACKQT"));
				new_map.put("ORMTQT_TOTAL", 0);
				new_map.put("ORMTQT_TP_GSBB", map.get("ORMTQT_TP_GSBB"));
				key_cache.put(key, new_map);
				result.add(new_map);
			}
			Map<String,Object> new_map=key_cache.get(key);
			new_map.put(map.get("YXGSNO")+"_DZ", map.get("ORMTQT_DZ_YXGS"));
			new_map.put(map.get("YXGSNO")+"_TP", map.get("ORMTQT_TP_YXGS"));
			if(map.get("ORMTQT_TP_YXGS")!=null){
				new_map.put("ORMTQT_TOTAL", (Integer)new_map.get("ORMTQT_TOTAL")+((BigDecimal)map.get("ORMTQT_TP_YXGS")).intValue());
			}
			
		}
		
		return result;
	}
	
	public  void tpYxgs_updateOrmtqt_tp(String ormtno,String ordorg,String sampno,String bradno,String spclno,String suitno,Integer ormtqs,Integer ormtqt) {
		if(ormtqt==null){
			return;
		}
		String mtorno=ordService.getMtorno(ormtno,"TP",ordorg);
		Ord ord=ordRepository.get(mtorno);
		if(ord==null){
			ord=new Ord();
			ord.setMtorno(mtorno);
			ord.setChanno(Chancl.YXGS.toString());
			ord.setOrdorg(ordorg);
			ord.setOrmtno(ormtno);
			ord.setOrtyno("TP");
			ordRepository.create(ord);
		}
		
		String mlorno=mtorno+bradno+spclno;
		Ordhd.PK pk=new Ordhd.PK();
		pk.setBradno(bradno);
		pk.setMtorno(mtorno);
		pk.setSpclno(spclno);
		Ordhd ordhd=ordhdRepository.get(pk);
		//插入订单副表
		if(ordhd==null){
			ordhd=new Ordhd();
			ordhd.setMtorno(mtorno);
			ordhd.setBradno(bradno);
			ordhd.setSpclno(spclno);
			ordhd.setMlorno(mlorno);
			ordhd.setMlorvn(1);
			//ordhd.setSdtyno();
			ordhd.setOrstat(0);
			ordhd.setSzstat(0);
			ordhd.setIsfect(0);
			ordhdRepository.create(ordhd);
		}
		
		//插入订单明细表
		Orddtl.PK pk1=new Orddtl.PK();
		pk1.setMtorno(mtorno);
		pk1.setSampno(sampno);
		pk1.setSuitno(suitno);
		Orddtl orddtl=orddtlRepository.get(pk1);
		if(orddtl==null){
			orddtl=new Orddtl();
			orddtl.setMtorno(mtorno);
			orddtl.setSampno(sampno);
			orddtl.setSuitno(suitno);
			orddtl.setMlorno(ordhd.getMlorno());
			orddtl.setMlorvn(ordhd.getMlorvn());
			orddtl.setOrmtqs(ormtqs);
			orddtl.setOrmtqt(ormtqt);
			orddtlRepository.create(orddtl);
			
		} else {
			orddtl.setOrmtqt(ormtqt);
			orddtlRepository.update(orddtl);
		}
	}
	/**
	 * 
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param ormtno
	 * @return 0：不可编辑。
	 */
	public Integer tpYxgs_getStat(String ormtno) {
		//总部的有效状态为1，而且orstat=3的，才说明营销公司可以编辑
		String mtorno=ordService.getMtorno(ormtno,"TP",spb_orgno);
		List<Map<String,Object>> list= tpRepository.tpYxgs_getStat(mtorno);
		if(list==null || list.size()==0){
			return 0;
		}
		if(list.size()>1){
			throw new BusinessException("总部的统配订单状态有问题，请联系管理员检查!");
		}
		Map<String,Object> map=list.get(0);
		if("0".equals(map.get("ISFECT").toString())){
			//表示总公司订单总量还没有准备好
			return 0;
		}
		if("1".equals(map.get("ISFECT").toString())){
			if("3".equals(map.get("ORSTAT").toString())){
				return 1;
			}
		}
		return 0;
		
	}
	
	public  void tpYxgs_over(String ormtno){
		
		String mtorno=ordService.getMtorno(ormtno,"TP",spb_orgno);
		//还要判断各个大区的统配数量之和是否等于总公司的统配数量之和，如果不相等的话，不能提交
		List<String> list=tpRepository.tpYxgs_check_diff(ormtno, mtorno);
		if(list!=null && list.size()>0){
			StringBuilder builder=new StringBuilder();
			for(String str:list){
				builder.append(","+str);
			}
			throw new BusinessException("下列样衣未分配完:"+builder.substring(1));
		}
		//把总部的订单状态改为无效0
		tpRepository.tpYxgs_over_GSBB(mtorno);
		
		//把素有营销公司的 有效状态 改成 有效1，并且orstat 改成 “审批通过”
		tpRepository.tpYxgs_over(ormtno);
		
	}

	//====================================
	public  List<Map<String,Object>> queryTpQyColumns(String yxgsno){
		return tpRepository.queryTpQyColumns(yxgsno);
	}
	
	public  List<Map<String,Object>> tpQyQuery( Map<String,Object> params) {
		List<Map<String,Object>> list= tpRepository.tpQyQuery(params);
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		//循环，进行行列转换，把营销公司转换为行显示
		Map<String,Map<String,Object>> key_cache=new HashMap<String,Map<String,Object>>();
		for(Map<String,Object> map:list){
			String key=""+map.get("SAMPNO")+map.get("SUITNO");
			if(!key_cache.containsKey(key)){
				Map<String,Object> new_map=new HashMap<String,Object>();
				new_map.put("BRADNO", map.get("BRADNO"));
				new_map.put("SPCLNO", map.get("SPCLNO"));
				new_map.put("ORMTNO", map.get("ORMTNO"));
				new_map.put("SAMPNO", map.get("SAMPNO"));
				new_map.put("SAMPNM", map.get("SAMPNM"));
				new_map.put("SUITNO", map.get("SUITNO"));
				new_map.put("SPTYNO", map.get("SPTYNO"));
				new_map.put("SPSENO", map.get("SPSENO"));
				new_map.put("PACKQT", map.get("PACKQT"));
				new_map.put("ORMTQT_TOTAL", 0);
				new_map.put("ORMTQT_TP_YXGS", map.get("ORMTQT_TP_YXGS"));
				key_cache.put(key, new_map);
				result.add(new_map);
			}
			Map<String,Object> new_map=key_cache.get(key);
			new_map.put(map.get("QYNO")+"_DZ_QY", map.get("ORMTQT_DZ_QY"));
			new_map.put(map.get("QYNO")+"_DZ_TX", map.get("ORMTQT_DZ_TX"));
			new_map.put(map.get("QYNO")+"_TP_QY", map.get("ORMTQT_TP_QY"));
			new_map.put(map.get("QYNO")+"_TP_TX", map.get("ORMTQT_TP_TX"));
			if(map.get("ORMTQT_TP_QY")!=null){
				new_map.put("ORMTQT_TOTAL", (Integer)new_map.get("ORMTQT_TOTAL")+((BigDecimal)map.get("ORMTQT_TP_QY")).intValue());
			}
			if(map.get("ORMTQT_TP_TX")!=null){
				new_map.put("ORMTQT_TOTAL", (Integer)new_map.get("ORMTQT_TOTAL")+((BigDecimal)map.get("ORMTQT_TP_TX")).intValue());
			}
			
		}
		
		return result;
	}
	
	/**
	 * 
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param ormtno
	 * @return 0：不可编辑。
	 */
	public int tpQy_getStat(String ormtno, String yxgsno) {
		// 营销公司的有效状态为1，而且orstat=3的，才说明营销公司可以编辑
		String mtorno = ordService.getMtorno(ormtno, "TP", yxgsno);
		List<Map<String, Object>> list = tpRepository.tpYxgs_getStat(mtorno);
		if (list == null || list.size() == 0) {
			return 0;
		}
		if (list.size() > 1) {
			throw new BusinessException("营销公司的统配订单状态有问题，请联系管理员检查!");
		}
		Map<String, Object> map = list.get(0);
		if ("0".equals(map.get("ISFECT").toString())) {
			// 表示总公司订单总量还没有准备好
			return 0;
		}
		if ("1".equals(map.get("ISFECT").toString())) {
			if ("3".equals(map.get("ORSTAT").toString())) {
				return 1;
			}
		}
		return 0;
	}
	public  void tpQy_updateOrmtqt_tp(String ormtno,String ordorg,String sampno,String bradno,String spclno,String suitno,Integer ormtqs,Integer ormtqt,Integer ormtqt1) {
		if(ormtqt==null && ormtqt1==null){
			return;
		}
		String mtorno=ordService.getMtorno(ormtno,"TP",ordorg);
		Ord ord=ordRepository.get(mtorno);
		if(ord==null){
			ord=new Ord();
			ord.setMtorno(mtorno);
			ord.setChanno(Chancl.QY.toString());
			ord.setOrdorg(ordorg);
			ord.setOrmtno(ormtno);
			ord.setOrtyno("TP");
			ordRepository.create(ord);
		}
		
		String mlorno=mtorno+bradno+spclno;
		Ordhd.PK pk=new Ordhd.PK();
		pk.setBradno(bradno);
		pk.setMtorno(mtorno);
		pk.setSpclno(spclno);
		Ordhd ordhd=ordhdRepository.get(pk);
		//插入订单副表
		if(ordhd==null){
			ordhd=new Ordhd();
			ordhd.setMtorno(mtorno);
			ordhd.setBradno(bradno);
			ordhd.setSpclno(spclno);
			ordhd.setMlorno(mlorno);
			ordhd.setMlorvn(1);
			//ordhd.setSdtyno();
			ordhd.setOrstat(0);
			ordhd.setSzstat(0);
			ordhd.setIsfect(0);
			ordhdRepository.create(ordhd);
		}
		
		//插入订单明细表
		Orddtl.PK pk1=new Orddtl.PK();
		pk1.setMtorno(mtorno);
		pk1.setSampno(sampno);
		pk1.setSuitno(suitno);
		Orddtl orddtl=orddtlRepository.get(pk1);
		if(orddtl==null){
			orddtl=new Orddtl();
			orddtl.setMtorno(mtorno);
			orddtl.setSampno(sampno);
			orddtl.setSuitno(suitno);
			orddtl.setMlorno(ordhd.getMlorno());
			orddtl.setMlorvn(ordhd.getMlorvn());
			orddtl.setOrmtqs(ormtqs);
			orddtl.setOrmtqt(ormtqt);
			orddtl.setOrmtqt1(ormtqt1);
			orddtlRepository.create(orddtl);
			
		} else {
			orddtl.setOrmtqt(ormtqt);
			orddtl.setOrmtqt1(ormtqt1);
			orddtlRepository.update(orddtl);
		}
	}
	public void tpQy_over(String ormtno,String yxgsno) {
		String mtorno=ordService.getMtorno(ormtno,"TP",yxgsno);
		//还要判断各个区域的统配数量之和是否等于区域的统配数量之和，如果不相等的话，不能提交
		List<String> list=tpRepository.tpQy_check_diff(ormtno, mtorno);
		if(list!=null && list.size()>0){
			StringBuilder builder=new StringBuilder();
			for(String str:list){
				builder.append(","+str);
			}
			throw new BusinessException("下列样衣未分配完:"+builder.substring(1));
		}
		//把营销公司的订单状态改为无效0
		tpRepository.tpQy_over_YXGS(mtorno);
		
		//把素有营销公司的 有效状态 改成 有效1，并且orstat 改成 “审批通过”
		tpRepository.tpQy_over(ormtno,yxgsno);
	}
	
}
