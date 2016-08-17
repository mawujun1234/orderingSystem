package com.youngor.order.bw;

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
public interface BwOrdmtRepository extends IRepository<BwOrdmt, String>{

	public List<Map<String,Object>> querySizeVOColumns(@Param("sizegp")String sizegp,@Param("mlorno")String mlorno,@Param("suitno")String suitno);	
	
	public List<Map<String,Object>> querySizeVOData(Map<String,Object> params);
	
	public String getOrmmno(@Param("ormtno")String ormtno);
	public String getMmorno(@Param("ormtno")String ormtno,@Param("bradno")String bradno,@Param("spclno")String spclno);
	public Integer getBwOrdhdOrstat(@Param("ormtno")String ormtno,@Param("bradno")String bradno,@Param("spclno")String spclno);
}
