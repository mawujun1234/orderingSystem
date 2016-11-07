package com.youngor.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface GzsOrderInsertRepository {
	public  List<Map<String,Object>> queryColumns(@Param("sizegp")String sizegp);
	public List<Map<String,Object>> queryData(Map<String,Object> params);
	public Integer getSzstat(Map<String,Object> params);
	
	public void update_orddtl_ormtqt(@Param("mtorno")String mtorno,@Param("sampno")String sampno,@Param("suitno")String suitno,@Param("ortyno")String ortyno);
	
	public void submit_ordhd(@Param("mtorno")String mtorno,@Param("bradno")String bradno);
	public void submit_ordszdtl(@Param("mtorno")String mtorno,@Param("bradno")String bradno);
}
	
