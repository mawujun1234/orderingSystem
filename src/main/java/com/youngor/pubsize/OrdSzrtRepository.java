package com.youngor.pubsize;

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
public interface OrdSzrtRepository extends IRepository<OrdSzrt, OrdSzrt.PK>{
	public List<PubSize> queryStdszColumns(@Param("sizegp")String sizegp);
	
	public List<Map<String,Object>> querySzrtData_a(@Param("ordorg")String ordorg,@Param("ormtno")String ormtno,@Param("bradno")String bradno,@Param("spclno")String spclno,
			@Param("spseno")String spseno,@Param("versno")String versno,@Param("sizegp")String sizegp);
	public List<OrdSzrt> querySzrtData_b(@Param("ordorg")String ordorg,@Param("ormtno")String ormtno,@Param("bradno")String bradno,@Param("spclno")String spclno,
			@Param("spseno")String spseno,@Param("versno")String versno,@Param("sizegp")String sizegp,@Param("sizety")String sizety);
	
	public List<PubSize> queryPrdpkColumns(@Param("sizegp")String sizegp);
	
	public List<Map<String,Object>> querySaleHisGrid(Map<String,Object> params);

}
