package com.youngor.sample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository.IRepository;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface SamplePlanRepository extends IRepository<SamplePlan, String>{

	
	public int checkPlanInDesign(@Param("plspno")String plspno);
	public List<SamplePlanVO> queryPage(Map<String,Object> params);
	//public String queryOrmtno(String plspno);
}
