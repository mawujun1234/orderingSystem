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
import com.youngor.order.OrdService;
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
	private OrdService ordService;
	
	//SimpleDateFormat formater=new 
			
	
	@Override
	public BwOrdmtRepository getRepository() {
		return bwOrdmtRepository;
	}
	
	public List<Map<String,Object>> querySizeVOColumns(String sizegp,String ormtno,String ordorg,String bradno,String spclno,String suitno) {
		String mtorno=ordService.getMtorno(ormtno, "BW", ordorg);
		return bwOrdmtRepository.querySizeVOColumns(sizegp, mtorno+bradno+spclno, suitno);
	}
	
	public List<Map<String,Object>> querySizeVOData(Map<String,Object> params) {
		String mtorno=ordService.getMtorno(params.get("ormtno").toString(), "BW", params.get("ordorg").toString());
		params.put("mtorno", mtorno);
		params.put("mlorno", mtorno+params.get("bradno").toString()+params.get("spclno").toString());
		
		List<Map<String,Object>> result= new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> list= bwOrdmtRepository.querySizeVOData(params);
		//进行行列转换
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
				if(listmap.get("VERSNO")!=null){
					map.put("VERSNO_NAME", PubCodeCache.getVersno_name((String)listmap.get("VERSNO")));
				}
				
				map.put("PLSPNO", listmap.get("PLSPNO"));
				map.put("PLSPNM", listmap.get("PLSPNM"));
				map.put("SAMPNO", listmap.get("SAMPNO"));
				map.put("SAMPNM", listmap.get("SAMPNM"));
				map.put("ORMTQT", listmap.get("ORMTQT"));
				map.put("ORSZST", listmap.get("ORSZST"));
				
				//map.put("PACKQT", listmap.get("PACKQT"));
				map.put("SUITNO", listmap.get("SUITNO"));
				map.put("MTORNO", listmap.get("MTORNO"));
				map.put("MLORNO", listmap.get("MLORNO"));
				map.put("SIZETY", listmap.get("SIZETY"));
				map.put("ORMMNO", listmap.get("ORMMNO"));
				map.put("MMORNO", listmap.get("MMORNO"));
				result.add(map);
				key_map.put(listmap.get("SAMPNO").toString(), map);
				
				map.put("ORBGQT_SUBTOTAL", new BigDecimal(0));//剩余的备忘数量
				map.put("ORSZQT_SUBTOTAL", new BigDecimal(0));//兑现数量
			}
			//接下来是行列转换
			map.put("ORBGQT_"+listmap.get("SIZENO"), listmap.get("ORBGQT"));
			map.put("ORSZQT_"+listmap.get("SIZENO"), listmap.get("ORSZQT"));
			//添加小计
			if(listmap.get("ORBGQT")!=null){
				BigDecimal ORBGQT_SUBTOTAL=(BigDecimal)map.get("ORBGQT_SUBTOTAL");
				ORBGQT_SUBTOTAL=ORBGQT_SUBTOTAL.add((BigDecimal)listmap.get("ORBGQT"));
				map.put("ORBGQT_SUBTOTAL",ORBGQT_SUBTOTAL);
			}
			//兑现数量
			if(listmap.get("ORSZQT")!=null){
				map.put("ORSZQT_SUBTOTAL", ((BigDecimal)map.get("ORSZQT_SUBTOTAL")).add((BigDecimal)listmap.get("ORSZQT")));
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
	
	public void approve(String ormtno,String bradno,String spclno) {
		String mmorno=bwOrdmtRepository.getMmorno(ormtno, bradno, spclno);
		bwOrdszdtlRepository.update(Cnd.update().set(M.BwOrdhd.orstat,1).andEquals(M.BwOrdhd.mmorno, mmorno));
	}
	
	public Integer getBwOrdhdOrstat(String ormtno,String bradno,String spclno) {
		Integer orstat=bwOrdmtRepository.getBwOrdhdOrstat(ormtno, bradno, spclno);
		if(orstat==null){
			return 0;
		}
		return orstat;
	}
}
