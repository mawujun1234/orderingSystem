package com.youngor.report.mobile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.utils.page.Pager;

@Repository
public interface MobileReportRepository {
	public List<Map<String,Object>> queryOrdorgCondition(@Param("ormtno")String ormtno,@Param("user_id")String user_id);
	
	public List<ReportFirst> queryReportFirst_allBradno(@Param("ormtno")String ormtno,
			@Param("channo")String channo,@Param("ordorg")String ordorg,@Param("bradno")String bradno);
	public Map<String,BigDecimal> queryReportFirst_Y(@Param("ormtno")String ormtno,
			@Param("channo")String channo,@Param("ordorg")String ordorg);
	public Map<String,BigDecimal> queryReportFirst_other_bradno(@Param("ormtno")String ormtno,
			@Param("channo")String channo,@Param("ordorg")String ordorg,@Param("bradno")String bradno);
	
	public List<ReportSplcno> queryReportSplcno(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno
			,@Param("channo")String channo);
	public List<ReportSplcno> queryReportSplcno_other_bradno(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno
			,@Param("channo")String channo);
	public List<ReportSplcno> queryReportSplcno_TX(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno,@Param("channo")String channo);
//	public List<ReportSplcno> queryReportSplcno_YXGS(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno);
//	public List<ReportSplcno> queryReportSplcno_ALL(@Param("ormtno")String ormtno,@Param("bradno")String bradno);
	
	
	public List<ReportSplcno> queryReportSptyno(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno,
			@Param("spclno")String spclno,@Param("channo")String channo);
	public List<ReportSplcno> queryReportSpseno(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno,
			@Param("spclno")String spclno,@Param("channo")String channo);
	
	public List<ReportSplcno> queryReportSptyno_TX(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno,
			@Param("spclno")String spclno,@Param("channo")String channo);
	public List<ReportSplcno> queryReportSpseno_TX(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno,
			@Param("spclno")String spclno,@Param("channo")String channo);

	
	
	public Pager<AlreadyOd> queryReportAlreadyOd(Pager<AlreadyOd> pager);
	
	public List<Map<String,Object>> queryReportAlreadyOd_totalData(Map<String,Object> params);
	
	public List<ReportMoney> queryReportMoney(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno
			,@Param("spclno")String spclno
			,@Param("sptyno")String sptyno
			,@Param("spseno")String spseno,@Param("channo")String channo
			);
	
//	public List<ReportMoney> queryReportMoney_YXGS(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno
//			,@Param("spclno")String spclno
//			,@Param("sptyno")String sptyno
//			,@Param("spseno")String spseno
//			);
//	public List<ReportMoney> queryReportMoney_QY(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno
//			,@Param("spclno")String spclno
//			,@Param("sptyno")String sptyno
//			,@Param("spseno")String spseno
//			);
//	public List<ReportMoney> queryReportMoney_TX(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno
//			,@Param("spclno")String spclno
//			,@Param("sptyno")String sptyno
//			,@Param("spseno")String spseno
//			);
//	public List<ReportMoney> queryReportMoney_ALL(@Param("ormtno")String ormtno,@Param("bradno")String bradno
//			,@Param("spclno")String spclno
//			,@Param("sptyno")String sptyno
//			,@Param("spseno")String spseno
//			);
	
	
	public List<ReportOrg> queryReportOrg_YXGS(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno
			,@Param("spclno")String spclno
			,@Param("sptyno")String sptyno
			,@Param("spseno")String spseno
			);
	public List<ReportOrg> queryReportOrg_ALL(@Param("ormtno")String ormtno,@Param("bradno")String bradno
			,@Param("spclno")String spclno
			,@Param("sptyno")String sptyno
			,@Param("spseno")String spseno
			);
	public List<ReportOrg> queryReportOrg_ALL_other_bradno(@Param("ormtno")String ormtno,@Param("bradno")String bradno
			,@Param("spclno")String spclno
			,@Param("sptyno")String sptyno
			,@Param("spseno")String spseno
			);
	
	public List<SampleInfoField> queryOrmtqt_sum_by_sampno(@Param("sampno")String sampno,@Param("yxgsno")String yxgsno);
	
	/**
	 * 
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param ormtno 订货会编号
	 * @param ordorg 订货单位
	 * @return
	 */
	public List<ReportDapei> queryDapei(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg);
	public List<ReportDapeiList> queryDapei_yxgs_list(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("clppnm")String clppnm);
	
	public List<Map<String,Object>> queryQY(@Param("yxgsno")String yxgsno);


}
