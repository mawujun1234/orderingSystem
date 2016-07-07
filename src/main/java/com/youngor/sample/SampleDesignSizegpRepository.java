package com.youngor.sample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository.IRepository;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface SampleDesignSizegpRepository extends IRepository<SampleDesignSizegp, SampleDesignSizegp.PK>{

	public List<SampleDesignSizegp> querySampleDesignSizegp(@Param("sampno")String sampno,@Param("suitty")String suitty);
	public List<SampleDesignSizegp> querySampleDesignSizegp_T00(@Param("sampno")String sampno);
	
	public void deleteBySampno(@Param("sampno")String sampno);
}
