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
public class BwService {
	@Autowired
	private BwRepository bwRepository;
	@Autowired
	private OrdService ordService;
	@Autowired
	private OrdhdRepository ordhdRepository;
	@Autowired
	private OrdRepository ordRepository;
	@Autowired
	private OrddtlRepository orddtlRepository;
	
	public  List<Map<String,Object>> queryQyColumns(String yxgsno) {
		return bwRepository.queryQyColumns(yxgsno);
	}
	
	public  List<Map<String,Object>> queryQyData(Map<String,Object> params) {
		List<Map<String,Object>> list= bwRepository.queryQyData(params);
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
				//new_map.put("PACKQT", map.get("PACKQT"));
				new_map.put("ORMTQT_TOTAL", 0);
				//new_map.put("ORMTQT_TP_YXGS", map.get("ORMTQT_TP_YXGS"));
				key_cache.put(key, new_map);
				result.add(new_map);
			}
			Map<String,Object> new_map=key_cache.get(key);
			if(map.get("QYNO")!=null){
				new_map.put(map.get("QYNO")+"_ORMTQT", map.get("ORMTQT_BW_QY"));
			}

			if(map.get("ORMTQT_BW_QY")!=null){
				new_map.put("ORMTQT_TOTAL", (Integer)new_map.get("ORMTQT_TOTAL")+((BigDecimal)map.get("ORMTQT_BW_QY")).intValue());
			}

			
		}
		
		return result;
	}
	
	public int qy_getStat(String ormtno,String yxgsno,String spclno) {
		// 营销公司的有效状态为1，而且orstat=3的，才说明营销公司可以编辑
		//String mtorno = ordService.getMtorno(ormtno, "BW", yxgsno);
		Integer orstat=bwRepository.qy_getStat(ormtno,yxgsno,spclno);
		if(orstat==null){
			return 0;
		}
		return orstat;
	}
	
	public  void qy_updateOrmtqt(String ormtno,String ordorg,String sampno,String bradno,String spclno,String suitno,Integer ormtqt) {
		if(ormtqt==null ){
			return;
		}
		String mtorno=ordService.getMtorno(ormtno,"BW",ordorg);
		Ord ord=ordRepository.get(mtorno);
		if(ord==null){
			ord=new Ord();
			ord.setMtorno(mtorno);
			ord.setChanno(Chancl.QY.toString());
			ord.setOrdorg(ordorg);
			ord.setOrmtno(ormtno);
			ord.setOrtyno("BW");
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
			orddtl.setOrmtqs(0);
			orddtl.setOrmtqt(ormtqt);
			//orddtl.setOrmtqt1(ormtqt1);
			orddtlRepository.create(orddtl);
			
		} else {
			orddtl.setOrmtqt(ormtqt);
			//orddtl.setOrmtqt1(ormtqt1);
			orddtlRepository.update(orddtl);
		}
	}
	/**
	 * 按大类，品牌进行提交
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param ormtno
	 * @param yxgsno
	 * @param bradno
	 * @param spclno
	 * @return
	 */
	public void qy_approve(String ormtno,String yxgsno,String bradno,String spclno) {
		//String mtorno=ordService.getMtorno(ormtno,"BW",s);
		bwRepository.qy_approve(ormtno, yxgsno,spclno);
	}
	public void qy_over(String ormtno,String yxgsno,String bradno,String spclno){
		bwRepository.qy_over(ormtno, yxgsno,spclno);
	}
}
