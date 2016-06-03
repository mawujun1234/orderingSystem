package com.youngor.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository.IRepository;
import com.mawujun.utils.page.Pager;
import com.youngor.ordmt.OrdmtScde;
import com.youngor.org.Org;
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
	
	public OrdmtScde get_ordmt_scde(@Param("ormtno")String ormtno,@Param("channo")String channo);
	
	public MyInfoVO queryMyInfoVO(@Param("mtorno")String mtorno);

	
	public void updateMtornoMlorvn(@Param("mtorno")String mtorno);
	
	//public void updateOrmtqtZeor(@Param("mtorno")String mtorno);
	
	public void createOrd_ordhd(@Param("mtorno")String mtorno);
	
	public void createOrd_ordhd_his(@Param("mtorno")String mtorno);
	public void createOrd_orddtl_his(@Param("mtorno")String mtorno);
	public void update_ordhd_SDTYNO(@Param("mtorno")String mtorno);
	public void update_ord_ordszdtl_MLORNO(@Param("mtorno")String mtorno);
	public List<String> check_is_confirm(@Param("mtorno")String mtorno);
	
	public List<Org> queryOrdorg(@Param("ormtno")String ormtno,@Param("qyno")String qyno,@Param("channo")String channo,@Param("ortyno")String ortyno);
	
	
	public Pager<QyVO> queryQyVO(Pager<QyVO> pager);
	
	
	public void updateOrmtqt(@Param("mtorno")String mtorno,@Param("sampno")String sampno,@Param("suitno")String suitno,@Param("ormtqt")Integer ormtqt);
	
	
	public List<QyNewFormVO> querySuitBySampnm(@Param("ormtno")String ormtno,@Param("sampnm")String sampnm);
	
	public int checkSampnoOrded(@Param("sampno")String sampno,@Param("ordorg")String ordorg,@Param("ortyno")String ortyno,
			@Param("channo")String channo,@Param("ormtno")String ormtno);
	public Ordhd getOrdhd(@Param("sampno")String sampno,@Param("ordorg")String ordorg,@Param("ortyno")String ortyno,
			@Param("channo")String channo,@Param("ormtno")String ormtno);
	
	public void updateApprove_org(@Param("ordorg")String ordorg,@Param("ormtno")String ormtno,@Param("bradno")String bradno,@Param("spclno")String spclno);
	
	
	public Pager<Map<String,Object>> queryZgsVO(Pager<Map<String,Object>> pager);
	
	public void clearnum_orddtl(@Param("sampno")String sampno);
	public void clearnum_ordszdtl(@Param("sampno")String sampno);
	
	public List<Map<String,Object>> query_meger_comp(@Param("SAMPNO")String SAMPNO);
	
	
	public List<Map<String,Object>> sizeVO_querySizeVOColumns(@Param("sizegp")String sizegp,@Param("sztype")Integer sztype);
	public List<Map<String,Object>> sizeVO_querySizeVOData(Map<String,Object> params);
	
	public Pager<Map<String,Object>> ordMgr_queryOrdMgr(Pager<Map<String,Object>> pager);
}
