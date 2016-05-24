package com.youngor.sample;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository.IRepository;
import com.mawujun.utils.page.Pager;
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
	public int checkOnlyOne(@Param("sampnm")String sampnm);
}
