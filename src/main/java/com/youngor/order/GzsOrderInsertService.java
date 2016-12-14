package com.youngor.order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.youngor.org.Chancl;
import com.youngor.permission.ShiroUtils;
import com.youngor.pubcode.PubCodeCache;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class GzsOrderInsertService {
	@Autowired
	private GzsOrderInsertRepository gzsOrderInsertRepository;
	@Autowired
	private OrdService ordService1;
	@Autowired
	private OrdRepository ordRepository1;
	@Autowired
	private OrdhdRepository ordhdRepository1;
	@Autowired
	private OrddtlRepository orddtlRepository1;
	@Autowired
	private OrdszdtlRepository ordszdtlRepository1;
	
	public List<Map<String,Object>> queryColumns(String sizegp) {
		List<Map<String,Object>> columns=gzsOrderInsertRepository.queryColumns(sizegp);
		List<Map<String,Object>> columns_new=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> columns_new_PRDPK=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map:columns){
			if("STDSZ".equals(map.get("SIZETY"))){//如果是单规，就生成单规的规格组
				Map<String,Object> map_new = new HashMap<String,Object>();
				//map_new.put("SIZETY", "STDSZPRDPK");//变成包装箱组的单规
				//map_new.put("SIZENO", "STDSZPRDPK___"+map.get("SIZENO"));//通过三条下划线来分隔
				map_new.put("SIZETY", "STDSZ");//变成包装箱组的单规
				map_new.put("SIZENO", "STDSZ___"+map.get("SIZENO"));//通过三条下划线来分隔
				map_new.put("SIZENM", map.get("SIZENM"));
				columns_new.add(map_new);
			}
			if("PRDPK".equals(map.get("SIZETY"))){
				Map<String,Object> map_new = new HashMap<String,Object>();
				map_new.put("SIZETY", "PRDPK");//变成包装箱组的单规
				map_new.put("SIZENO", "PRDPK___"+map.get("SIZENO"));//通过三条下划线来分隔
				map_new.put("SIZENM", map.get("SIZENM"));
				map_new.put("PACKQT", map.get("PACKQT"));
				columns_new_PRDPK.add(map_new);
			}
		}
		columns_new_PRDPK.addAll(columns_new);
		return columns_new_PRDPK;
	}
	
	public List<Map<String,Object>> queryData(Map<String,Object> params) {
		params.put("mtorno", ordService1.getMtorno(params.get("ormtno").toString(),params.get("ortyno").toString(),params.get("ordorg").toString()));
		List<Map<String,Object>> list= gzsOrderInsertRepository.queryData(params);
		List<Map<String,Object>> result= new ArrayList<Map<String,Object>>();
		
		Map<String,Map<String,Object>> key_map=new HashMap<String,Map<String,Object>>();
		for(Map<String,Object> listmap:list){
			Map<String,Object> map=key_map.get(listmap.get("SAMPNO").toString());
			if(map==null) {
				map=new HashMap<String,Object>();
				map.put("ORDORG_NAME", params.get("ordorg_name"));
				map.put("SPTYNO", listmap.get("SPTYNO"));
				map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name((String)listmap.get("SPTYNO")));
				map.put("SPSENO", listmap.get("SPSENO"));
				map.put("SPSENO_NAME", PubCodeCache.getSpseno_name((String)listmap.get("SPSENO")));
				map.put("VERSNO", listmap.get("VERSNO"));
				map.put("VERSNO_NAME", PubCodeCache.getVersno_name((String)listmap.get("VERSNO")));
				map.put("PLSPNO", listmap.get("PLSPNO"));
				map.put("PLSPNM", listmap.get("PLSPNM"));
				map.put("SAMPNO", listmap.get("SAMPNO"));
				map.put("SAMPNM", listmap.get("SAMPNM"));
				map.put("ORMTQT", listmap.get("ORMTQT"));
				map.put("ORSZST", listmap.get("ORSZST"));
				
				map.put("PACKQT", listmap.get("PACKQT"));
				map.put("SUITNO", listmap.get("SUITNO"));
				map.put("MTORNO", listmap.get("MTORNO"));
				map.put("MLORNO", listmap.get("MLORNO"));
				map.put("SZTYPE", "2");
				result.add(map);
				key_map.put(listmap.get("SAMPNO").toString(), map);	
				
				map.put("STDSZ___SUBTOTAL", new BigDecimal(0));
				map.put("PRDPK___SUBTOTAL", new BigDecimal(0));
				map.put("ORMTQT_NOW", new BigDecimal(0));//兑现数量
			}
			//接下来是行列转换
			if("STDSZ".equals(listmap.get("SIZETY"))){//如果是单规
				//map.put("STDSZPRDPK___"+listmap.get("SIZENO"), listmap.get("ORBGQT"));
				map.put("STDSZ___"+listmap.get("SIZENO"), listmap.get("ORBGQT"));
				//添加小计
				if(listmap.get("ORBGQT")!=null){
					BigDecimal aaa=(BigDecimal)map.get("STDSZ___SUBTOTAL");
					aaa=aaa.add((BigDecimal)listmap.get("ORBGQT"));
					map.put("STDSZ___SUBTOTAL",aaa);
					
					//兑现数量
					map.put("ORMTQT_NOW", ((BigDecimal)map.get("ORMTQT_NOW")).add((BigDecimal)listmap.get("ORBGQT")));
				}
				
			} else if("PRDPK".equals(listmap.get("SIZETY"))){
				map.put("PRDPK___"+listmap.get("SIZENO"), listmap.get("ORBGQT"));
				map.put("PRDPK___PACKQT___"+listmap.get("SIZENO"), listmap.get("PACKQT"));
				//添加小计
				if(listmap.get("ORBGQT")!=null){
					BigDecimal aaa=(BigDecimal)map.get("PRDPK___SUBTOTAL");
					BigDecimal ORBGQT=(BigDecimal)listmap.get("ORBGQT");
					if(ORBGQT==null){
						ORBGQT=new BigDecimal(0);
					}
					aaa=aaa.add(ORBGQT);
					map.put("PRDPK___SUBTOTAL",aaa);
					
					//兑现数量
					BigDecimal packqt=(BigDecimal)listmap.get("PACKQT");
					if(packqt==null){
						packqt=new BigDecimal(1);
					}
					map.put("ORMTQT_NOW", ((BigDecimal)map.get("ORMTQT_NOW")).add(ORBGQT.multiply(packqt)));
					

				}
			}
			
		}
		return result;
	}
	
	public Integer getSzstat(Map<String,Object> params) {
		Integer szstat= gzsOrderInsertRepository.getSzstat(params);
		if(szstat==null){
			szstat=0;
		}
		return szstat;
	}
	
	
	public void updateOrdszdtl(String ormtno,String ordorg,String ortyno,String sampno,String bradno,String spclno,String suitno
			,String sizety,String sizeno,Integer orszqt) {
		if( orszqt==null ){
			return;
		}
		String mtorno=ordService1.getMtorno(ormtno,ortyno,ordorg);
		Ord ord=ordRepository1.get(mtorno);
		if(ord==null){
			ord=new Ord();
			ord.setMtorno(mtorno);
			ord.setChanno(Chancl.QY.toString());
			ord.setOrdorg(ordorg);
			ord.setOrmtno(ormtno);
			ord.setOrtyno("TP");
			ordRepository1.create(ord);
		}
		
		String mlorno=mtorno+bradno+spclno;
		Ordhd.PK pk=new Ordhd.PK();
		pk.setBradno(bradno);
		pk.setMtorno(mtorno);
		pk.setSpclno(spclno);
		Ordhd ordhd=ordhdRepository1.get(pk);
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
			

			ordhdRepository1.create(ordhd);
		}
		
		//插入订单明细表
		Orddtl.PK pk1=new Orddtl.PK();
		pk1.setMtorno(mtorno);
		pk1.setSampno(sampno);
		pk1.setSuitno(suitno);
		Orddtl orddtl=orddtlRepository1.get(pk1);
		if(orddtl==null){
			orddtl=new Orddtl();
			orddtl.setMtorno(mtorno);
			orddtl.setSampno(sampno);
			orddtl.setSuitno(suitno);
			orddtl.setMlorno(ordhd.getMlorno());
			orddtl.setMlorvn(ordhd.getMlorvn());
			//orddtl.setOrmtqs(ormtqs);
			//orddtl.setOrmtqt(ormtqt);
//			if("TP".equals(ortyno)){
//				orddtl.setOrmtqt1(ormtqt);
//			}
			orddtl.setRgdt(new Date());
			orddtl.setRgsp(ShiroUtils.getLoginName());
			orddtl.setLmdt(new Date());
			orddtl.setLmsp(ShiroUtils.getLoginName());
			orddtlRepository1.create(orddtl);
			
		} 
//		else {
//			orddtl.setOrmtqt(ormtqt);
//			if("TP".equals(ortyno)){
//				orddtl.setOrmtqt1(ormtqt);
//			}
//			orddtl.setLmdt(new Date());
//			orddtl.setLmsp(ShiroUtils.getLoginName());
//			orddtlRepository1.update(orddtl);
//		}
		
		
		Ordszdtl.PK pk2=new Ordszdtl.PK();
		pk2.setMtorno(mtorno);
		pk2.setSampno(sampno);
		pk2.setSuitno(suitno);
		pk2.setSizety(sizety);
		pk2.setSizeno(sizeno);
		Ordszdtl ordszdtl=ordszdtlRepository1.get(pk2);
		if(ordszdtl==null){
			ordszdtl=new Ordszdtl();
			ordszdtl.setMtorno(mtorno);
			ordszdtl.setSampno(sampno);
			ordszdtl.setSuitno(suitno);
			ordszdtl.setSizety(sizety);
			ordszdtl.setSizeno(sizeno);
			
			ordszdtl.setMlorno(mlorno);
			ordszdtl.setOrszst(0);
			ordszdtl.setOritqt(orszqt);
			ordszdtl.setOrszqt(orszqt);
			ordszdtl.setOrbgqt(orszqt);
			ordszdtl.setRgdt(new Date());
			ordszdtl.setRgsp(ShiroUtils.getLoginName());
			ordszdtl.setLmdt(new Date());
			ordszdtl.setLmsp(ShiroUtils.getLoginName());
			
			ordszdtlRepository1.create(ordszdtl);
		} else {
			ordszdtl.setOritqt(orszqt);
			ordszdtl.setOrszqt(orszqt);
			ordszdtl.setOrbgqt(orszqt);
			ordszdtl.setLmdt(new Date());
			ordszdtl.setLmsp(ShiroUtils.getLoginName());
			ordszdtlRepository1.update(ordszdtl);
		}
		
		//手动更新Orddtl中的ormtqt和ormtqs
		gzsOrderInsertRepository.update_orddtl_ormtqt(mtorno, sampno, suitno,ortyno);
		
		
	}
	/**
	 * 以 订货会 +订货单位+品牌 
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param ormtno
	 * @param ordorg
	 * @param ortyno
	 * @param bradno
	 * @return
	 */
	public void submit(String ormtno,String ordorg,String ortyno,String bradno){
		String mtorno=ordService1.getMtorno(ormtno,ortyno,ordorg);
		
		gzsOrderInsertRepository.submit_ordhd(mtorno, bradno);
		gzsOrderInsertRepository.submit_ordszdtl(mtorno, bradno);
		
	}
}
