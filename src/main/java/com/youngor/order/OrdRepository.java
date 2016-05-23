package com.youngor.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository.IRepository;
import com.youngor.sample.SampleDesignStpr;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface OrdRepository extends IRepository<Ord, String>{
	public Ord haveOrd(Ord ord);
	
	public SampleVO querySample(@Param("sampnm")String sampnm,@Param("ormtno")String ormtno);
	public List<SampleDesignStpr> querySampleDesignStpr(@Param("sampno")String sampno);
	
	public List<SuitVO> querySuitVO_T00(SampleVO sampleVO);
	
	/**
	 * 
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param sampno
	 * @param sptyno 小类
	 * @param sexno 性别
	 * @param suitty 套装种类
	 * @param spltmk 套西是否拆套
	 * @return
	 */
//	public List<SuitVO> querySuitVO(@Param("sampno")String sampno,
//			@Param("sptyno")String sptyno,@Param("sexno")String sexno,@Param("suitty")String suitty,@Param("spltmk")Integer spltmk);
	public List<SuitVO> querySuitVO(SampleVO sampleVO);
	
	public void clearOrddtl(@Param("mtorno")String mtorno,@Param("sampno")String sampno);
	public void clearOrdszdtl(@Param("mtorno")String mtorno,@Param("sampno")String sampno);

}
