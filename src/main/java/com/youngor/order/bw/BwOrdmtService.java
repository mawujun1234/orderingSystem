package com.youngor.order.bw;
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

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.DateUtils;
import com.mawujun.utils.bean.BeanUtils;
import com.mawujun.utils.string.StringUtils;
import com.youngor.order.OrdService;
import com.youngor.permission.ShiroUtils;
import com.youngor.pubcode.PubCodeCache;
import com.youngor.utils.M;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class BwOrdmtService extends AbstractService<BwOrdmt, String>{

	@Autowired
	private BwOrdmtRepository bwOrdmtRepository;
	@Autowired
	private BwOrdhdRepository bwOrdhdRepository;
	@Autowired
	private BwOrdszdtlRepository bwOrdszdtlRepository;
	@Autowired
	private BwOrddtRepository bwOrddtRepository;
	
	@Autowired
	private OrdService ordService;
	
	//SimpleDateFormat formater=new 
			
	
	@Override
	public BwOrdmtRepository getRepository() {
		return bwOrdmtRepository;
	}
	
	//public List<Map<String,Object>> querySizeVOColumns(String sizegp,String ormtno,String ordorg,String bradno,String spclno,String suitno) {
	public List<Map<String,Object>> querySizeVOColumns(Map<String,Object> params){
		//String mtorno=ordService.getMtorno(ormtno, "BW", ordorg);
		return bwOrdmtRepository.querySizeVOColumns(params);
	}
	
	public List<Map<String,Object>> querySizeVOData(Map<String,Object> params) {
		//String mtorno=ordService.getMtorno(params.get("ormtno").toString(), "BW", params.get("ordorg").toString());
		//params.put("mtorno", mtorno);
		//params.put("mlorno", mtorno+params.get("bradno").toString()+params.get("spclno").toString());
		
		List<Map<String,Object>> result= new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> list= bwOrdmtRepository.querySizeVOData(params);
		//进行行列转换
		Map<String,Map<String,Object>> key_map=new HashMap<String,Map<String,Object>>();
		//汇总
		Map<String,Object> total=new HashMap<String,Object>();
		total.put("ORDORG", "TOTAL");
		total.put("ORDORG_NAME", "合计:");
		
		
		
		for(Map<String,Object> listmap:list){
			Map<String,Object> map=key_map.get(listmap.get("SAMPNO").toString()+listmap.get("SUITNO")+listmap.get("ORDORG"));
			if(map==null) {
				map=new HashMap<String,Object>();
	
				map.put("ORDORG_NAME", listmap.get("ORDORG_NAME"));
				map.put("SPTYNO", listmap.get("SPTYNO"));
				map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name((String)listmap.get("SPTYNO")));
				map.put("SPSENO", listmap.get("SPSENO"));
				map.put("SPSENO_NAME", PubCodeCache.getSpseno_name((String)listmap.get("SPSENO")));
				map.put("VERSNO", listmap.get("VERSNO"));
				if(listmap.get("VERSNO")!=null){
					map.put("VERSNO_NAME", PubCodeCache.getVersno_name((String)listmap.get("VERSNO")));
				}
				map.put("PRODNM", listmap.get("PRODNM"));
				
				map.put("PLSPNO", listmap.get("PLSPNO"));
				map.put("PLSPNM", listmap.get("PLSPNM"));
				map.put("SAMPNO", listmap.get("SAMPNO"));
				map.put("SAMPNM", listmap.get("SAMPNM"));
				map.put("ORMTQT", listmap.get("ORMTQT"));
				map.put("ORSZST", listmap.get("ORSZST"));
				
				//map.put("PACKQT", listmap.get("PACKQT"));
				map.put("SUITNO", listmap.get("SUITNO"));
				//map.put("SUITNO_NAME", PubCodeCache.getSuitno_name(listmap.get("SUITNO").toString()));
				map.put("MTORNO", listmap.get("MTORNO"));
				map.put("MLORNO", listmap.get("MLORNO"));
				
				
				
				
				
				result.add(map);
				key_map.put(listmap.get("SAMPNO").toString()+listmap.get("SUITNO")+listmap.get("ORDORG"), map);
				
				map.put("ORBGQT_SUBTOTAL", new BigDecimal(0));//剩余的备忘数量
				map.put("ORSZQT_SUBTOTAL", new BigDecimal(0));//兑现数量
				
				
			}
			
			if(listmap.get("MLDATE")!=null){
				map.put("MLDATE", listmap.get("MLDATE"));
			}
			if(listmap.get("PLDATE")!=null){
				map.put("PLDATE", listmap.get("PLDATE"));
			}
			if(listmap.get("PPLACE")!=null){
				map.put("PPLACE", listmap.get("PPLACE"));
			}
			if(listmap.get("ORMMNO")!=null){
				map.put("ORMMNO", listmap.get("ORMMNO"));
			}
			if(listmap.get("MMORNO")!=null){
				map.put("MMORNO", listmap.get("MMORNO"));
			}
			if(listmap.get("ORDORG")!=null){
				map.put("ORDORG", listmap.get("ORDORG"));
			}
			map.put("SIZETY___"+listmap.get("SIZENO"), listmap.get("SIZETY"));
			BigDecimal PACKQT=(BigDecimal)listmap.get("PACKQT");
			map.put("PACKQT___"+listmap.get("SIZENO"), PACKQT);
			//接下来是行列转换
			map.put("ORBGQT_"+listmap.get("SIZENO"), listmap.get("ORBGQT"));
			map.put("ORSZQT_"+listmap.get("SIZENO"), listmap.get("ORSZQT"));
			//添加小计
			if(listmap.get("ORBGQT")!=null){
				BigDecimal ORBGQT_SUBTOTAL=(BigDecimal)map.get("ORBGQT_SUBTOTAL");
				if("PRDPK".equals(listmap.get("SIZETY"))){
					ORBGQT_SUBTOTAL=ORBGQT_SUBTOTAL.add(((BigDecimal)listmap.get("ORBGQT")).multiply(PACKQT));
				} else {
					ORBGQT_SUBTOTAL=ORBGQT_SUBTOTAL.add((BigDecimal)listmap.get("ORBGQT"));
				}
				
				map.put("ORBGQT_SUBTOTAL",ORBGQT_SUBTOTAL);
			}
			//兑现数量
			if(listmap.get("ORSZQT")!=null){
				if("PRDPK".equals(listmap.get("SIZETY"))){
					map.put("ORSZQT_SUBTOTAL", ((BigDecimal)map.get("ORSZQT_SUBTOTAL")).add(((BigDecimal)listmap.get("ORSZQT")).multiply(PACKQT)));
				} else {
					map.put("ORSZQT_SUBTOTAL", ((BigDecimal)map.get("ORSZQT_SUBTOTAL")).add((BigDecimal)listmap.get("ORSZQT")));
				}
				
			}
			
			//计算汇总
			String key="ORBGQT_"+listmap.get("SIZENO");
			if(total.get(key)==null) {
				total.put(key, new BigDecimal(0));
			} 
			if(map.get(key)!=null){
				total.put(key, ((BigDecimal)total.get(key)).add((BigDecimal)map.get(key)));
			}
			key="ORSZQT_"+listmap.get("SIZENO");
			if(total.get(key)==null) {
				total.put(key, new BigDecimal(0));
			} 
			if(map.get(key)!=null){
				total.put(key, ((BigDecimal)total.get(key)).add((BigDecimal)map.get(key)));
			}
//			key="ORBGQT_SUBTOTAL";
//			if(total.get(key)==null) {
//				total.put(key, new BigDecimal(0));
//			} 
//			if(map.get(key)!=null){
//				total.put(key, ((BigDecimal)total.get(key)).add((BigDecimal)map.get(key)));
//			}
//			key="ORSZQT_SUBTOTAL";
//			if(total.get(key)==null) {
//				total.put(key, new BigDecimal(0));
//			} 
//			if(map.get(key)!=null) {
//				total.put(key, ((BigDecimal)total.get(key)).add((BigDecimal)map.get(key)));
//			}
		}
		result.add(total);
		
		for(Map<String,Object> map:result){
			if("TOTAL".equals(map.get("ORDORG"))){
				continue;
			}
			String key="ORBGQT_SUBTOTAL";
			if(total.get(key)==null) {
				total.put(key, new BigDecimal(0));
			} 
			if(map.get(key)!=null){
				total.put(key, ((BigDecimal)total.get(key)).add((BigDecimal)map.get(key)));
			}
			key="ORSZQT_SUBTOTAL";
			if(total.get(key)==null) {
				total.put(key, new BigDecimal(0));
			} 
			if(map.get(key)!=null) {
				total.put(key, ((BigDecimal)total.get(key)).add((BigDecimal)map.get(key)));
			}
		}
		
		return result;
	}

	public void updateOrszqt(BwOrdszdtlVO bwOrdszdtlVO) {
		String ormmno=bwOrdszdtlVO.getOrmmno();
		if(ormmno==null || "".equals(ormmno)){
			ormmno=bwOrdmtRepository.getOrmmno(bwOrdszdtlVO.getOrmtno());
			if(ormmno==null || "".equals(ormmno)){
				ormmno=bwOrdszdtlVO.getOrmtno()+"_"+DateUtils.format(new Date(), "yyyyMMdd");
				BwOrdmt bwOrdmt=new BwOrdmt();
				bwOrdmt.setOrmmno(ormmno);
				bwOrdmt.setOrmtno(bwOrdszdtlVO.getOrmtno());
				bwOrdmtRepository.create(bwOrdmt);
			}
		}
		String mmorno=bwOrdszdtlVO.getMmorno();
		if(mmorno==null || "".equals(mmorno)) {
			mmorno=ormmno+bwOrdszdtlVO.getBradno()+bwOrdszdtlVO.getSpclno();
			BwOrdhd bwOrdhd=bwOrdhdRepository.get(mmorno);
			if(bwOrdhd==null){
				bwOrdhd=new BwOrdhd();
				bwOrdhd.setOrmmno(ormmno);
				bwOrdhd.setMmorno(mmorno);
				bwOrdhd.setBradno(bwOrdszdtlVO.getBradno());
				bwOrdhd.setSpclno(bwOrdszdtlVO.getSpclno());
				bwOrdhd.setOrstat(0);
				bwOrdhd.setIsfect(1);
				
				bwOrdhdRepository.create(bwOrdhd);
			}

		}
		BwOrdszdtl bwOrdszdtl=BeanUtils.copyOrCast(bwOrdszdtlVO, BwOrdszdtl.class);
		bwOrdszdtl.setMmorno(mmorno);
		
		bwOrdszdtlRepository.createOrUpdate(bwOrdszdtl);
	}
	
	public void approve(String[] mmornoes) {
//		String mmorno=bwOrdmtRepository.getMmorno(ormtno, bradno, spclno);
//		bwOrdszdtlRepository.update(Cnd.update().set(M.BwOrdhd.orstat,1).andEquals(M.BwOrdhd.mmorno, mmorno));
		if(mmornoes==null || mmornoes.length==0){
			return;
		}
		for(String mmorno:mmornoes){
			bwOrdhdRepository.update(Cnd.update().set(M.BwOrdhd.orstat,1)
					.andEquals(M.BwOrdhd.mmorno, mmorno).andEquals(M.BwOrdhd.orstat,0));
		}
	}
	
	public Integer getBwOrdhdOrstat(String ormtno,String ordorg,String channo,String bradno,String spclno) {
		//先判断备忘订单的规格是否已经“审批通过”了，如果已经“审批通过”，才可以审批
		//String mtorno=ordService.getMtorno(ormtno, "BW", ordorg);
		Integer szstat= bwOrdmtRepository.getBwOrdhdSzstat(ormtno,ordorg,channo, bradno, spclno);
		if(szstat!=3){
			return 1;
		}
		
		
		
		
		Integer orstat=bwOrdmtRepository.getBwOrdhdOrstat(ormtno, bradno, spclno);
		if(orstat==null){
			return 0;
		}
		return orstat;
	}
	
	
	public List<BwOrdhd> queryBwOrdhd(String ormtno,String ormmno,String bradno,String spclno) {
		return bwOrdhdRepository.query(Cnd.select().andEquals(M.BwOrdhd.ormmno, ormmno)
				.andEqualsIf(M.BwOrdhd.bradno, bradno).andEqualsIf(M.BwOrdhd.spclno, spclno));
	}
	
	public List<Map<String,Object>> queryBwSizeMgrList(Map<String,Object> params) {
		 List<Map<String,Object>> list=bwOrdmtRepository.queryBwSizeMgrList(params);
		 for(Map<String,Object> listmap:list){
			 listmap.put("SPTYNO_NAME", PubCodeCache.getSptyno_name((String)listmap.get("SPTYNO")));
			 listmap.put("SPSENO_NAME", PubCodeCache.getSpseno_name((String)listmap.get("SPSENO")));
			 if(listmap.get("VERSNO")!=null){
				listmap.put("VERSNO_NAME", PubCodeCache.getVersno_name((String)listmap.get("VERSNO")));
			 }
			 listmap.put("SUITNO_NAME", PubCodeCache.getSuitno_name((String)listmap.get("SUITNO")));
		 }
		 
		 return list;
	}
	
	public  List<String> updateBwOrddt( BwOrddt[] bwOrddtes,String field) {
		ArrayList<String> sampno_none=new ArrayList<String>();
		for(BwOrddt bwOrddt:bwOrddtes){
			if(!StringUtils.hasText(bwOrddt.getOrmmno()) || !StringUtils.hasText(bwOrddt.getOrdorg())){
				sampno_none.add(bwOrddt.getSampnm());
				continue;
			}
			BwOrddt bwOrddt_exists=bwOrddtRepository.get(bwOrddt.geetPK());
			if(bwOrddt_exists==null){
				bwOrddt.setRgdt(new Date());
				bwOrddt.setRgsp(ShiroUtils.getLoginName());
				bwOrddtRepository.create(bwOrddt);
			} else if("mldate".equals(field)){
				bwOrddt_exists.setOrmtqt(bwOrddt.getOrmtqt());
				bwOrddt_exists.setLmdt(new Date());
				bwOrddt_exists.setLmsp(ShiroUtils.getLoginName());
				bwOrddt_exists.setMldate(bwOrddt.getMldate());
				bwOrddtRepository.update(bwOrddt_exists);	
			} else if("pldate".equals(field)){
				bwOrddt_exists.setOrmtqt(bwOrddt.getOrmtqt());
				bwOrddt_exists.setLmdt(new Date());
				bwOrddt_exists.setLmsp(ShiroUtils.getLoginName());
				bwOrddt_exists.setPldate(bwOrddt.getPldate());
				bwOrddtRepository.update(bwOrddt_exists);	
			} else if("pplace".equals(field)){
				bwOrddt_exists.setOrmtqt(bwOrddt.getOrmtqt());
				bwOrddt_exists.setLmdt(new Date());
				bwOrddt_exists.setLmsp(ShiroUtils.getLoginName());
				bwOrddt_exists.setPplace(bwOrddt.getPplace());
				bwOrddtRepository.update(bwOrddt_exists);	
			}
		}
		return sampno_none;
	}
}
