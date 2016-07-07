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
	public  Pager<Map<String,Object>> zgs_tpAllQuery( Pager<Map<String,Object>> pager);
	public  List<Map<String,Object>> zgs_tpAllQuery( Map<String,Object> pager);
	
	public void zgs_update_DZ_ormtqt(@Param("sampno")String sampno,@Param("mtorno")String mtorno);
	public void zgs_update_DZ_ormtqt_0(@Param("sampno")String sampno,@Param("mtorno")String mtorno);
	public void zgs_restoreDZ(@Param("sampno")String sampno,@Param("mtorno")String mtorno);
	public void zgs_over(@Param("mtorno")String mtorno);
	public Integer zgs_getOrstat(@Param("mtorno")String mtorno);
	public void tpYxgs_over_GSBB(@Param("mtorno")String mtorno);
	public void tpYxgs_over(@Param("ormtno")String ormtno);
	public List<String> tpYxgs_check_diff(@Param("ormtno")String ormtno,@Param("mtorno")String mtorno);
	
	public  List<Map<String,Object>> queryTpYxgsColumns();
	public  List<Map<String,Object>> tpYxgsQuery( Map<String,Object> params) ;
	public List<Map<String,Object>> tpYxgs_getStat(@Param("mtorno")String mtorno);
	
	//public  Pager<Map<String,Object>> tpYxgsQuery( Pager<Map<String,Object>> pager) ;
	
	
	public  List<Map<String,Object>> queryTpQyColumns(@Param("yxgsno")String yxgsno);
	public  List<Map<String,Object>> tpQyQuery(Map<String,Object> params) ;
	public List<Map<String,Object>> tpQy_getStat(String ormtno, String yxgsno);
	public List<String> tpQy_check_diff(@Param("ormtno")String ormtno,@Param("mtorno")String mtorno);
	public void  tpQy_over_YXGS(@Param("mtorno")String mtorno);
	public void tpQy_over(@Param("ormtno")String ormtno,@Param("yxgsno")String yxgsno);
}
