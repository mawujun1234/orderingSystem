package com.youngor.report;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.utils.page.Pager;
import com.youngor.report.mobile.ReportDapei;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface ReportRepository {
	public  Pager<Map<String,Object>> queryClothPurePlan( Pager<Map<String,Object>> pager);
	public  List<Map<String,Object>> queryClothPurePlan( Map<String,Object> params);
	
	public  Pager<Map<String,Object>> queryMatePurePlan( Pager<Map<String,Object>> pager);
	public  List<Map<String,Object>> queryMatePurePlan( Map<String,Object> params);
	public  List<Map<String,Object>> query_mate_podtl(@Param("ormtno")String ormtno,@Param("htitno")String htitno);
	
	public  Pager<Map<String,Object>> queryOrderTotalPrint( Pager<Map<String,Object>> pager);
	public  List<OrderPrint1> orderTotalPrint_export1( Map<String,Object> params);
	public  List<OrderPrint1> orderTotalPrint_export2( Map<String,Object> params);
	
	public  List<OrderPrint1> orderTotalPrint_export3( Map<String,Object> params);
	
	

	public  List<Map<String,Object>> printDaPei(@Param("ormtno")String ormtno);
}
