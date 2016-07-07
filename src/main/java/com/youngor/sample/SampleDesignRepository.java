package com.youngor.sample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository.IRepository;
import com.mawujun.utils.page.Pager;
import com.youngor.utils.MapParams;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface SampleDesignRepository extends IRepository<SampleDesign, String>{

	public Pager<SamplePlanDesignVO> queryPlanDesign(Pager<SamplePlanDesignVO> pager);
	
	public void lock(Map<String,Object> params);
	public void unlock(Map<String,Object> params);
	public int checkOnlyOne(@Param("ormtno")String ormtno,@Param("sampnm")String sampnm);
	
	//public SampleDesign getSampleDesignBySampno(@Param("ormtno")String ormtno,@Param("sampno")String sampno);
	
	//public void  updateSampleDesingPhotno(SampleDesign sampleDesign);
	
	public void copy_ord_sample_mate(@Param("sampno")String sampno,@Param("old_sampno")String old_sampno);
	public void copy_ord_sample_colth(@Param("sampno")String sampno,@Param("old_sampno")String old_sampno);
	public void copy_ord_sample_design_stpr(@Param("sampno")String sampno,@Param("old_sampno")String old_sampno);
	
	public Integer count_sampleDesign_photo_num(@Param("sampno")String sampno);
	
	public SampleDesign getSampleDesignBySampnm(@Param("ormtno")String ormtno,@Param("sampnm")String sampnm);
	
	public List<Map<String,Object>> query_exportSample(Map<String,Object> params);
}
