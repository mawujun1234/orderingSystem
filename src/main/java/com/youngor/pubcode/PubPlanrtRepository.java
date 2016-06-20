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
public interface PubPlanrtRepository extends IRepository<PubPlanrt, com.youngor.pubsize.PubSize.PK>{
	public List<PubPlanrt> queryGrid(@Param("bradno")String bradno,@Param("spclno")String spclno,@Param("sptyno")String sptyno);

}
