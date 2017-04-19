package com.youngor.sample;
import java.io.File;
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
import com.youngor.order.OrddtlRepository;
import com.youngor.ordmt.Ordmt;
import com.youngor.ordmt.OrdmtRepository;
import com.youngor.permission.ShiroUtils;
import com.youngor.utils.ContextUtils;
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
	private SampleDesignStprRepository sampleDesignStprRepository;
	@Autowired
	private SamplePhotoRepository samplePhotoRepository;
	@Autowired
	private OrdmtRepository ordmtRepository;
	@Autowired
	private SampleMateRepository sampleMateRepository;
	@Autowired
	private SampleColthRepository sampleColthRepository;
	//@Autowired
	//private OrddtlRepository orddtlRepository;
	
	
	
	@Override
	public SampleDesignRepository getRepository() {
		return sampleDesignRepository;
	}
	
	//@Override
	public void deleteById(String ormtno,String[] sampnos,String contextPath) {
		if(sampnos==null){
			return;
		}
		//再判断订货会是否已经开始了，如果已经开始了，就不能删除了
//		Ordmt ordmt=ordmtRepository.get(ormtno);
//		Date start=ordmt.getMtstdt();
//		if(start.getTime()<(new Date()).getTime() || ordmt.getOrmtst()){
//			throw new BusinessException("订货会已经开始或结束，不能删除!");
//		}
		
		for(String sampno:sampnos){

			SampleDesign sampleDesign=sampleDesignRepository.get(sampno);
			if(sampleDesign.getSpstat() !=null && sampleDesign.getSpstat()!=0){
				throw new BusinessException("已经锁定,不能删除!");
			}
			//判断样衣是否已经被订货，如果已经被订 了的话，就不能删除
			int count=sampleDesignRepository.checkExistOrddtl(sampno);
			if(count>0){
				throw new BusinessException("样衣已被订货,不能删除!");
			}
			
			
			List<SamplePhoto> photoes=samplePhotoRepository.query(Cnd.select().andEquals(M.SamplePhoto.sampno, sampno));

			sampleDesignRepository.delete(sampleDesign);
			
			//同时删除规格范围
			sampleDesignSizegpRepository.deleteBySampno(sampno);
			//同时删除按套件的价格
			sampleDesignStprRepository.deleteBySampno(sampno);
			//删除照片的数据
			samplePhotoRepository.deleteBySampno(sampno);
			//删除面料信息
			sampleMateRepository.deleteBySampno(sampno);
			//删除成衣信息
			sampleColthRepository.deleteBySampno(sampno);
			
			//删除照片的时候，同时删除目录中的照片
			contextPath=ContextUtils.getPhotoBakDir();
			for(SamplePhoto samplePhoto:photoes){
				String filepath=contextPath+samplePhoto.getImgnm();
				File file=new File(filepath);
				if(file.exists()){
					file.delete();
				}
			}
			
		}
		
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
		
		sum_sampleColth_sppdcy(sampleDesign.getSampno());
	}
	
	public void sum_sampleColth_sppdcy(String sampno){
		//只有自产的才进行这个更新
		SampleDesign sampleDesign=sampleDesignRepository.get(sampno);
		if("ZC".equals(sampleDesign.getSpmtno())){
			//获取面料的最大生产周期
			Integer mtmpcy= sampleDesignRepository.sum_sample_mate_mtmpcy(sampno);
			if(mtmpcy==null){
				mtmpcy=0;
			}
			//获取企划 样衣的成衣生产周期
			Integer spfpcy=sampleDesignRepository.sum_sample_plan_spfpcy(sampno);
			if(spfpcy==null){
				spfpcy=0;
			}
			//更新成衣的生产周期
			sampleColthRepository.update(Cnd.update().set(M.SampleColth.sppdcy, mtmpcy+spfpcy).andEquals(M.SampleColth.sampno, sampno));
		}
		
	}

	public Pager<SamplePlanDesignVO> queryPlanDesign(Pager<SamplePlanDesignVO> pager) {
		((Map<String,Object>)pager.getParams()).put("user_id", ShiroUtils.getUserId());
		return sampleDesignRepository.queryPlanDesign(pager);
	}
	
	public void lock(Map<String,Object> params) {
		sampleDesignRepository.lock(params);
	}
	public void unlock(Map<String,Object> params){
		sampleDesignRepository.unlock(params);
	}
	
	public List<Map<String,Object>> query_exportSample(MapParams params) {
		((Map<String,Object>)params.getParams()).put("user_id", ShiroUtils.getUserId());
		List<Map<String,Object>> list= sampleDesignRepository.query_exportSample(params.getParams());
		
		return list;
	}
	
	public List<Map<String,Object>> query_exportSampleMate(MapParams params) {
		((Map<String,Object>)params.getParams()).put("user_id", ShiroUtils.getUserId());
		return sampleDesignRepository.query_exportSampleMate(params.getParams());
	}
	public List<Map<String,Object>> query_exportSampleMate_other(MapParams params) {
		((Map<String,Object>)params.getParams()).put("user_id", ShiroUtils.getUserId());
		return sampleDesignRepository.query_exportSampleMate_other(params.getParams());
	}
}
