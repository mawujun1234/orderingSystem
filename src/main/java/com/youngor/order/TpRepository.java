package com.youngor.order;

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
public interface TpRepository {
	public  Pager<Map<String,Object>> tpAllQuery( Pager<Map<String,Object>> pager);
	
	public  List<Map<String,Object>> queryTpYxgsColumns();
	public  Pager<Map<String,Object>> tpYxgsQuery( Pager<Map<String,Object>> pager) ;
	
	
	public  List<Map<String,Object>> queryTpQyColumns(@Param("yxgsno")String yxgsno);
	public  Pager<Map<String,Object>> tpQyQuery( Pager<Map<String,Object>> pager) ;
	
}
