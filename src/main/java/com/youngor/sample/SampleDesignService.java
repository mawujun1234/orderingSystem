package com.youngor.sample;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.page.Pager;
import com.youngor.ordmt.Ordmt;
import com.youngor.ordmt.OrdmtRepository;
import com.youngor.permission.ShiroUtils;
import com.youngor.utils.M;
import com.youngor.utils.MapParams;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SampleDesignService extends AbstractService<SampleDesign, String>{

	@Autowired
	private SampleDesignRepository sampleDesignRepository;
	@Autowired
	private SampleDesignSizegpRepository sampleDesignSizegpRepository;
	@Autowired
	private OrdmtRepository ordmtRepository;
	
	
	
	@Override
	public SampleDesignRepository getRepository() {
		return sampleDesignRepository;
	}
	
	//@Override
	public void deleteById(String ormtno,String sampno) {
		SampleDesign sampleDesign=sampleDesignRepository.get(sampno);
		if(sampleDesign.getSpstat() !=null && sampleDesign.getSpstat()!=0){
			throw new BusinessException("已经锁定,不能删除!");
		}
		//再判断订货会是否已经开始了，如果已经开始了，就不能删除了
		Ordmt ordmt=ordmtRepository.get(ormtno);
		Date start=ordmt.getMtstdt();
		if(start.getTime()<(new Date()).getTime()){
			throw new BusinessException("订货会已经开始，不能删除!");
		}
		sampleDesignRepository.delete(sampleDesign);
		
		
		
//		sampleDesign.setSampst(0);
//		sampleDesign.setLmdt(new Date());
//		sampleDesign.setLmsp(ShiroUtils.getLoginName());
//		sampleDesignRepository.update(sampleDesign);
	}
	
	@Override
	public String create(SampleDesign sampleDesign) {
		//判断在本次订货会中，样衣编号是否唯一
		if(sampleDesignRepository.checkOnlyOne(sampleDesign.getOrmtno(),sampleDesign.getSampnm())>0){
			throw new BusinessException("样衣编号已存在");
		}
		
		
		super.delete(sampleDesign);
		sampleDesign.setRgdt(new Date());
		sampleDesign.setRgsp(ShiroUtils.getLoginName());
		sampleDesign.setLmdt(new Date());
		sampleDesign.setLmsp(ShiroUtils.getLoginName());
		sampleDesign.setSampst(1);
		sampleDesign.setSampno(sampleDesign.getPlspno()+sampleDesign.getSampnm());
		String id=super.create(sampleDesign);
		if(sampleDesign.getSampleDesignSizegpes()!=null){
			for(SampleDesignSizegp sampleDesignSizegp:sampleDesign.getSampleDesignSizegpes()){
				sampleDesignSizegp.setSampno(sampleDesign.getSampno());
				sampleDesignSizegpRepository.create(sampleDesignSizegp);
			}
		}
		return id;
	}
	
	public void copy(SampleDesign sampleDesign) {
		String old_sampno=sampleDesign.getSampno();
		sampleDesign.setSampno(null);
		create(sampleDesign);
		String sampno=sampleDesign.getSampno();
		//拷贝面料信息
		sampleDesignRepository.copy_ord_sample_mate(sampno, old_sampno);		
		//拷贝成衣信息
		sampleDesignRepository.copy_ord_sample_colth(sampno, old_sampno);	
		//拷贝成衣套件价格
		sampleDesignRepository.copy_ord_sample_design_stpr(sampno, old_sampno);
		
	}
	@Override
	public  void update(SampleDesign sampleDesign) {
		if(sampleDesign.getSpstat()==1){
			throw new BusinessException("已经锁定,不能更新!");
		}
		sampleDesign.setLmdt(new Date());
		sampleDesign.setLmsp(ShiroUtils.getLoginName());
		super.update(sampleDesign);
		sampleDesignSizegpRepository.deleteBatch(Cnd.delete().andEquals(M.SampleDesignStpr.sampno, sampleDesign.getSampno()));
		if(sampleDesign.getSampleDesignSizegpes()!=null){
			for(SampleDesignSizegp sampleDesignSizegp:sampleDesign.getSampleDesignSizegpes()){
				sampleDesignSizegp.setSampno(sampleDesign.getSampno());
				sampleDesignSizegpRepository.create(sampleDesignSizegp);
			}
		}
	}

	public Pager<SamplePlanDesignVO> queryPlanDesign(Pager<SamplePlanDesignVO> pager) {
		return sampleDesignRepository.queryPlanDesign(pager);
	}
	
	public void lock(Map<String,Object> params) {
		sampleDesignRepository.lock(params);
	}
	public void unlock(Map<String,Object> params){
		sampleDesignRepository.unlock(params);
	}
	
	public List<Map<String,Object>> query_exportSample(MapParams params) {
		return sampleDesignRepository.query_exportSample(params.getParams());
	}
}
