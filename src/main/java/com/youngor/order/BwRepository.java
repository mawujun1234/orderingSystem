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
public interface BwRepository {
	public  List<Map<String,Object>> queryQyColumns(@Param("yxgsno")String yxgsno);
	public  List<Map<String,Object>> queryQyData(Map<String,Object> params);
	public Integer qy_getStat(@Param("ormtno")String ormtno,@Param("yxgsno")String yxgsno,@Param("spclno")String spclno);
	
	public void qy_approve(@Param("ormtno")String ormtno,@Param("yxgsno")String yxgsno,@Param("spclno")String spclno);
	public void qy_over(@Param("ormtno")String ormtno,@Param("yxgsno")String yxgsno,@Param("spclno")String spclno);
}
	
