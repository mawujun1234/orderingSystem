package com.youngor.pubsize;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.page.Pager;
import com.youngor.pubsize.PubSize;
import com.youngor.pubsize.PubSizeRepository;
import com.youngor.utils.M;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PubSizeService extends AbstractService<PubSize, PubSize.PK>{

	@Autowired
	private PubSizeRepository pubSizeRepository;
	@Resource
	private PubSizeDtlService pubSizeDtlService;
	
	@Override
	public PubSizeRepository getRepository() {
		return pubSizeRepository;
	}
	
	public Pager<PubSize> queryStdsz4Sel(Pager<PubSize> pager) {
		return pubSizeRepository.queryStdsz4Sel(pager);
	}
	public List<PubSizeDtlVO> queryPrdpStdsz(String fszty,String fszno){
		return pubSizeRepository.queryPrdpStdsz(fszty,fszno);
	}

	
	public List<Map<String,Object>> querySizegpBradnoSpclno(String bradno,String spclno) {
		return pubSizeRepository.querySizegpBradnoSpclno(bradno, spclno);
	}
	
	public List<PubSizeDtlVO> querySizegp(String fszty,String fszno,String sizety){
		return pubSizeRepository.querySizegp(fszty,fszno,sizety);
	}
	
	public void createSizegp(String szbrad,String szclno,String sizeno,String sizety) {
		PubSize.PK pk=new PubSize.PK();
		pk.setSizeno(szbrad+szclno);
		pk.setSizety("SIZEGP");
		PubSize pubSize=pubSizeRepository.get(pk);
				//(Cnd.count().andEquals(M.PubSize.sizeno, szbrad+szclno).andEquals(M.PubSize.sizety, "SIZEGP")
				//.andEquals(M.PubSize.szbrad, szbrad).andEquals(M.PubSize.szclno, szclno));
		if(pubSize==null){
			pubSize=new PubSize();
			pubSize.setSizeno(szbrad+szclno);
			pubSize.setSizety("SIZEGP");
			pubSize.setSzbrad(szbrad);
			pubSize.setSzclno(szclno);
			super.create(pubSize);
		}
		
		
		PubSizeDtl dtl=new PubSizeDtl();
		dtl.setFszty(pubSize.getSizety());
		dtl.setFszno(pubSize.getSizeno());
		dtl.setSizety(sizety);
		dtl.setSizeno(sizeno);
		pubSizeDtlService.create(dtl);
	}
	
	public List<PubSizeDtlVO> queryPrdszty(String fszty,String fszno,String sizety){
		return pubSizeRepository.queryPrdszty(fszty,fszno,sizety);
	}
	
	
	public List<PubSizeVO> queryPRDSZTY4Ordmt(String szbrad,String szclno,String ormtno)  {
		return pubSizeRepository.queryPRDSZTY4Ordmt(szbrad, szclno, ormtno);
	}
	
	public List<PubSizeDtlVO> querySizegpPrdszty(String szbrad,String szclno,String sizety) {
		return pubSizeRepository.querySizegpPrdszty(szbrad, szclno, sizety);
	}
}
