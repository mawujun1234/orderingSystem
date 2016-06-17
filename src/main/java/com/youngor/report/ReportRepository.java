package com.youngor.report;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.utils.page.Pager;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface ReportRepository {
	public  Pager<Map<String,Object>> queryClothPurePlan( Pager<Map<String,Object>> pager);
	
	public  Pager<Map<String,Object>> queryMatePurePlan( Pager<Map<String,Object>> pager);
	public  List<Map<String,Object>> query_mate_podtl(@Param("ormtno")String ormtno,@Param("htitno")String htitno);

	
}
