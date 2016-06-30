package com.youngor.order;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.utils.page.Pager;
import com.youngor.org.Chancl;


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

	public  Pager<Map<String,Object>> zgs_tpAllQuery( Pager<Map<String,Object>> pager) {
		return tpRepository.zgs_tpAllQuery(pager);
	}
	
	public  void zgs_updateOrmtqt_tp(String ormtno,String ordorg,String sampno,String bradno,String spclno,String suitno,Integer ormtqs,Integer ormtqt) {
		if(ormtqt==null){
			return;
		}
		String mtorno=ordService.getMtorno(ormtno,"TP",ordorg);
		Ord ord=ordRepository.get(mtorno);
		if(ord==null){
			ord=new Ord();
			ord.setMtorno(mtorno);
			ord.setChanno(Chancl.GSBB.toString());
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
			ordhd.setIsfect(1);
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
		} else {
			orddtl.setOrmtqt(ormtqt);
			orddtlRepository.update(orddtl);
		}
		
		
		
		
		
	}
	
	public void zgs_restoreDZ(String ordorg,String ormtno,String sampno,String suitno) {
		String mtorno=ordService.getMtorno(ormtno,"TP",ordorg);
		// 删除订单明细表
		Orddtl.PK pk1 = new Orddtl.PK();
		pk1.setMtorno(mtorno);
		pk1.setSampno(sampno);
		pk1.setSuitno(suitno);
		orddtlRepository.deleteById(pk1);
		
		tpRepository.zgs_restoreDZ(sampno, mtorno);
	}
	
	public void zgs_over(String ordorg,String ormtno) {
		String mtorno=ordService.getMtorno(ormtno,"TP",ordorg);
		tpRepository.zgs_over(mtorno);
	}
	public int zgs_getOrstat(String ordorg,String ormtno) {
		String mtorno=ordService.getMtorno(ormtno,"TP",ordorg);
		Integer aa= tpRepository.zgs_getOrstat(mtorno);
		if(aa==null){
			return 0;
		}
		return aa;
	}
	
	
	public  List<Map<String,Object>> queryTpYxgsColumns() {
		return tpRepository.queryTpYxgsColumns();
	}
	
	public  Pager<Map<String,Object>> tpYxgsQuery( Pager<Map<String,Object>> pager) {
		return tpRepository.tpYxgsQuery(pager);
	}
	
	public  List<Map<String,Object>> queryTpQyColumns(String yxgsno){
		return tpRepository.queryTpQyColumns(yxgsno);
	}
	
	public  Pager<Map<String,Object>> tpQyQuery( Pager<Map<String,Object>> pager) {
		return tpRepository.tpQyQuery(pager);
	}
	
}
