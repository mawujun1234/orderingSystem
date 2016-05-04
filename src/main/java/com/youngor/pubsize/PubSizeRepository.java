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
public interface PubSizeRepository extends IRepository<PubSize, PubSize.PK>{

	public List<PubSizeDtlVO> queryPrdpStdsz(@Param("fszty")String fszty,@Param("fszno")String fszno);
	public List<Map<String,Object>> querySizegpBradnoSpclno(@Param("bradno")String bradno,@Param("spclno")String spclno) ;
	
	public List<PubSizeDtlVO> querySizegp(@Param("fszty")String fszty,@Param("fszno")String fszno,@Param("sizety")String sizety);
	
	public List<PubSizeDtlVO> queryPrdszty(@Param("fszty")String fszty,@Param("fszno")String fszno,@Param("sizety")String sizety);
}
