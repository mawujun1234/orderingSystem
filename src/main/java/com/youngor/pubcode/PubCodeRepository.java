package com.youngor.pubcode;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository.IRepository;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface PubCodeRepository extends IRepository<PubCode, String>{

	public List<PubCode> query(@Param("tyno")String tyno,@Param("fitno")String fitno,@Param("bradno")String bradno,@Param("stat_stat")String stat_stat,@Param("query")String query);
	public List<PubCode> query1(@Param("tyno")String tyno,@Param("fitno")String fitno,@Param("bradno")String bradno,@Param("stat")String stat);
	
	public List<PubCode> queryBradno4Ordmt(@Param("ormtno")String ormtno);
	
	public List<PubCode> queryVersno4Ordmt(@Param("ormtno")String ormtno,@Param("bradno")String bradno,@Param("spclno")String spclno);

	public List<PubCode> querySpseno4Ordmt(@Param("ormtno")String ormtno,@Param("bradno")String bradno,@Param("spclno")String spclno);
	
	public List<PubCode> queryList(@Param("tyno")String tyno,@Param("fitno")String fitno,@Param("bradno")String bradno);
	
	public  void updateStat(@Param("bradno")String bradno,@Param("tyno")String tyno,@Param("itno")String itno,@Param("stat")String stat);

}
