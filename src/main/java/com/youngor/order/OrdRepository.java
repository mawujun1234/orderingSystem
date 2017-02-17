package com.youngor.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository.IRepository;
import com.mawujun.utils.page.Pager;
import com.youngor.ordmt.OrdmtScde;
import com.youngor.org.Org;
import com.youngor.sample.SampleDesign;
import com.youngor.sample.SampleDesignStpr;
import com.youngor.sample.SamplePlan;
import com.youngor.utils.MapParams;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface OrdRepository extends IRepository<Ord, String>{
	public Ord haveOrd(Ord ord);
	
	public Integer order_dl__order_can(@Param("P_ORMTNO")String P_ORMTNO,@Param("P_ORDORG")String P_ORDORG,@Param("P_SAMPNO")String P_SAMPNO);
	public Integer order_dl__order_isqy(@Param("P_ORMTNO")String P_ORMTNO,@Param("P_ORDORG")String P_ORDORG,@Param("P_SAMPNO")String P_SAMPNO);
	public SampleVO querySample(@Param("sampnm")String sampnm,@Param("ormtno")String ormtno);
	public List<SampleDesignStpr> querySampleDesignStpr(@Param("sampno")String sampno);
	
	public List<SuitVO> querySuitVO_T00(SampleVO sampleVO);
	public List<SuitVO> querySuitVO_T00_PRDPK(SampleVO sampleVO);
	
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
	public List<SuitVO> querySuitVO_PRDPK(SampleVO sampleVO);
	
	public void clearOrddtl(@Param("mtorno")String mtorno,@Param("sampno")String sampno);
	public void clearOrdszdtl(@Param("mtorno")String mtorno,@Param("sampno")String sampno);
	
	public OrdmtScde get_ordmt_scde(@Param("ormtno")String ormtno,@Param("channo")String channo);
	
	public MyInfoVO queryMyInfoVO(@Param("mtorno")String mtorno,@Param("channo")String channo);
	public MyInfoVO queryMyInfoVO_plan(@Param("plorno")String plorno);

	public List<SampleDesign> query_none_abstat(@Param("ormtno")String ormtno,@Param("mtorno")String mtorno);
	
	public void updateMtornoMlorvn(@Param("mtorno")String mtorno);
	
	//public void updateOrmtqtZeor(@Param("mtorno")String mtorno);
	
	public void createOrd_ordhd(@Param("mtorno")String mtorno);
	
	//public void createOrd_ordhd_his(@Param("mtorno")String mtorno);
	//public void createOrd_orddtl_his(@Param("mtorno")String mtorno);
	//public void createOrd_ordhd_hiss_by_yxgsno(@Param("ormtno")String ormtno,@Param("yxgsno")String yxgsno);
	//public void createOrd_orddtl_hiss_by_yxgsno(@Param("ormtno")String ormtno,@Param("yxgsno")String yxgsno);
	public void update_ordhd_SDTYNO(@Param("mtorno")String mtorno,@Param("sdtyno")String sdtyno,@Param("orstat")String orstat);
	public void update_ord_ordszdtl_MLORNO(@Param("mtorno")String mtorno);
	public List<Map<String,Object>> check_is_confirm(@Param("mtorno")String mtorno);
	public  List<Map<String,Object>> process1_check_stats(Map<String,Object> params);
	public void order_dl__print_ps(@Param("P_ORMTNO")String P_ORMTNO,@Param("P_BRADNO")String P_BRADNO,@Param("P_SPCLNO")String P_SPCLNO,@Param("P_RESP")String P_RESP);
	//public void process1_update_ordhd_stats_0(@Param("ormtno")String ormtno,@Param("yxgsno")String yxgsno);
	//public void process1_update_ordhd_mlorvn(@Param("ormtno")String ormtno,@Param("yxgsno")String yxgsno);
	//public void process1_update_orddtl_mlorvn(@Param("ormtno")String ormtno,@Param("yxgsno")String yxgsno);
	public String confirm2_get_mlorvn(@Param("mtorno")String mtorno);
	public void confirm2_updateMtornoMlorvn(@Param("mtorno")String mtorno,@Param("mlorvn")String mlorvn);
	public void confirm2_createOrd_ordhd(@Param("mtorno")String mtorno,@Param("mlorvn")String mlorvn,@Param("sdtyno")String sdtyno,@Param("orstat")String orstat);
	public void confirm2_update_orstat(@Param("mtorno")String mtorno,@Param("orstat")String orstat);
	
	
	public List<Integer> yxgs_getOrstat(@Param("ormtno")String ormtno,@Param("yxgsno")String yxgsno);
	public List<Map<String,Object>> yxgs_confirm_check_stats(@Param("ormtno")String ormtno,@Param("yxgsno")String yxgsno);
	public void yxgs_confirm_update_orstat(@Param("ormtno")String ormtno,@Param("yxgsno")String yxgsno);
	
	public void order_dl__process(@Param("P_MLORNO")String P_MLORNO,@Param("P_TYPE")String P_TYPE,@Param("P_RESP")String P_RESP);
	
	public List<String> ordMgr_check_process2ANDback_stat(@Param("stat")Integer stat,@Param("mlorno_in")String mlorno_in);
	public List<String> ordMgr_check_ordercircle_stat(@Param("mlorno_in")String mlorno_in);
	
	public List<Org> queryOrdorg(@Param("ormtno")String ormtno,@Param("qyno")String qyno,@Param("channo")String channo,@Param("ortyno")String ortyno,@Param("user_id")String user_id);
	
	
	public Pager<QyVO> queryQyVO(Pager<QyVO> pager);
	
	
	public void updateOrmtqt(@Param("mtorno")String mtorno,@Param("sampno")String sampno,@Param("suitno")String suitno,@Param("ormtqt")Integer ormtqt);
	
	public int checkIsS10(@Param("ormtno")String ormtno,@Param("sampnm")String sampnm);
	public SamplePlan getSamplePlanBySampno(@Param("ormtno")String ormtno,@Param("sampno")String sampno);
	public List<QyNewFormVO> querySuitBySampnm(@Param("ormtno")String ormtno,@Param("sampnm")String sampnm);
	public List<QyNewFormVO> querySuitBySampnm_S10(@Param("ormtno")String ormtno,@Param("sampnm")String sampnm);
	
	public int checkSampnoOrded(@Param("sampno")String sampno,@Param("ordorg")String ordorg,@Param("ortyno")String ortyno,
			@Param("channo")String channo,@Param("ormtno")String ormtno);
	public Ordhd getOrdhd(@Param("sampno")String sampno,@Param("ordorg")String ordorg,@Param("ortyno")String ortyno,
			@Param("channo")String channo,@Param("ormtno")String ormtno);
	
	public void updateApprove_org(@Param("ordorg")String ordorg,@Param("ormtno")String ormtno,@Param("bradno")String bradno,@Param("spclno")String spclno);
	
	
	public Pager<Map<String,Object>> queryZgsVO(Pager<Map<String,Object>> pager);
	public List<Map<String,Object>> queryZgsVO(Map<String,Object> params);
	
	//public void clearnum_orddtl(@Param("sampno")String sampno);
	//public void clearnum_ordszdtl(@Param("sampno")String sampno);
	
	public void order_dl__comp_canc(@Param("P_ORMTNO")String P_ORMTNO,@Param("P_SAMPNO")String P_SAMPNO,@Param("P_RESP")String P_RESP);
	public void order_dl__comp_comb(@Param("P_ORMTNO")String P_ORMTNO,@Param("P_SAMPNO")String P_SAMPNO,@Param("P_PSMPNO")String P_PSMPNO,@Param("P_RESP")String P_RESP);
	public void order_dl__comp_comc(@Param("P_ORMTNO")String P_ORMTNO,@Param("P_COMP")String P_COMP,@Param("P_SAMPNO")String P_SAMPNO,@Param("P_PSMPNO")String P_PSMPNO,@Param("P_RESP")String P_RESP);
	public void order_dl__comp_comd(@Param("P_ORMTNO")String P_ORMTNO,@Param("P_DEAL")String P_DEAL,@Param("P_SAMPNO")String P_SAMPNO,@Param("P_RESP")String P_RESP);
	public void order_dl__comp_pass(@Param("P_ORMTNO")String P_ORMTNO,@Param("P_BRADNO")String P_BRADNO,@Param("P_SPCLNO")String P_SPCLNO,@Param("P_RESP")String P_RESP);
	public void order_dl__auto_box(@Param("P_ORMTNO")String P_ORMTNO,@Param("P_ORDTYNO")String P_ORDTYNO,@Param("P_ORDORG")String P_ORDORG,
			@Param("P_BRADNO")String P_BRADNO,@Param("P_SPCLNO")String P_SPCLNO,@Param("P_RESP")String P_RESP);
	public void sizeVO_auto_initsize(@Param("P_ORMTNO")String P_ORMTNO,@Param("P_ORDTYNO")String P_ORDTYNO,@Param("P_ORDORG")String P_ORDORG,
			@Param("P_BRADNO")String P_BRADNO,@Param("P_SPCLNO")String P_SPCLNO,@Param("P_RESP")String P_RESP);
	
	
	public Integer sizeVO_getSzstat(Map<String,Object> params);
	public List<Map<String,Object>> sizeVO_auto_box_check_num(@Param("mtorno")String mtorno,@Param("mlorno")String mlorno,@Param("suitno")String suitno);
	public Integer sizeVO_size_ap_check_orstat(@Param("ormtno")String ormtno,@Param("ortyno")String ortyno,@Param("ordorg")String ordorg
			,@Param("bradno")String bradno,@Param("spclno")String spclno);
	public void order_dl__size_ap(@Param("P_ORMTNO")String P_ORMTNO,@Param("P_ORDTYNO")String P_ORDTYNO,@Param("P_ORDORG")String P_ORDORG,
			@Param("P_BRADNO")String P_BRADNO,@Param("P_SPCLNO")String P_SPCLNO,@Param("P_RESP")String P_RESP);
	
	
	
	public void order_dl__order_to( @Param("P_MLORNO")String P_MLORNO,@Param("P_SDTYNO")String P_SDTYNO,@Param("P_RESP")String P_RESP);
	
	public List<String> wxtz_check_stat(@Param("ormtno")String ormtno,@Param("bradno")String bradno,@Param("spclno")String spclno);
	public Pager<Map<String,Object>> wxtz_queryWx(Pager<Map<String,Object>> pager);
	public List<Map<String,Object>> wxtz_queryWx(Map<String,Object> params);
	public void order_dl__comp_wx(@Param("P_ORMTNO")String P_ORMTNO,@Param("P_BRADNO")String P_BRADNO,@Param("P_SPCLNO")String P_SPCLNO,@Param("P_RESP")String P_RESP);
	public void order_dl__comp_wx_qw(@Param("P_ORMTNO")String P_ORMTNO,@Param("P_BRADNO")String P_BRADNO,@Param("P_SPCLNO")String P_SPCLNO,@Param("P_RESP")String P_RESP);
	public void order_dl__comp_wxps(@Param("P_ORMTNO")String P_ORMTNO,@Param("P_BRADNO")String P_BRADNO,@Param("P_SPCLNO")String P_SPCLNO,@Param("P_RESP")String P_RESP);
	
	
	public Map<String,Object> zgs_check_canedit(@Param("ormtno")String ormtno,@Param("bradno")String bradno,@Param("spclno")String spclno);
	public List<Map<String,Object>> zgs_queryOrderState(@Param("ormtno")String ormtno,@Param("bradno")String bradno,@Param("spclno")String spclno);
	
	public List<Map<String,Object>> query_meger_comp(@Param("SAMPNO")String SAMPNO);
	
	
	public List<Map<String,Object>> sizeVO_querySizeVOColumns(@Param("sizegp")String sizegp,@Param("sztype")Integer sztype);
	public List<Map<String,Object>> sizeVO_querySizeVOData(Map<String,Object> params);
	
	public Pager<Map<String,Object>> ordMgr_queryOrdMgr(Pager<Map<String,Object>> pager);
	public Integer ordMgr_getSztype(@Param("mlorno")String mlorno);
	
//	public List<Map<String,Object>> check_S10_Z0_0(@Param("mtorno")String mtorno);
//	public List<Map<String,Object>> check_S10_Z0_1(@Param("mtorno")String mtorno);
//	public List<Map<String,Object>> check_S10_Z1(@Param("mtorno")String mtorno);
	
	public ReloadTotal reloadTotal(Map<String,Object> params);
}
