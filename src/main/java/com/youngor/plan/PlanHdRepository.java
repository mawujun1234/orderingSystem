package com.youngor.plan;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import com.mawujun.repository.IRepository;

import com.youngor.plan.PlanHd;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface PlanHdRepository extends IRepository<PlanHd, com.youngor.plan.PlanHd.PK>{

	public List<PlanHdVO> queryHdGrid(@Param("ormtno")String ormtno,@Param("yxgsno")String yxgsno,@Param("bradno")String bradno,@Param("spclno")String spclno);
	public  void onPass(@Param("ormtno")String ormtno,@Param("yxgsno")String yxgsno,@Param("bradno")String bradno,@Param("spclno")String spclno);
}
