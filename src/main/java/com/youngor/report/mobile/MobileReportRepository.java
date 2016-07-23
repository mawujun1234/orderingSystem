package com.youngor.report.mobile;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.utils.page.Pager;

@Repository
public interface MobileReportRepository {
	public List<Map<String,Object>> queryOrdorgCondition(@Param("ormtno")String ormtno,@Param("user_id")String user_id);
	
	public List<ReportSplcno> queryReportSplcno(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno,@Param("user_id")String user_id);
	public List<ReportSplcno> queryReportSplcno_TX(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno,@Param("user_id")String user_id);
	public List<ReportSplcno> queryReportSplcno_YXGS(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno,@Param("user_id")String user_id);
	public List<ReportSplcno> queryReportSplcno_ALL(@Param("ormtno")String ormtno,@Param("bradno")String bradno,@Param("user_id")String user_id);
	
	
	public Pager<AlreadyOd> queryReportAlreadyOd(Pager<AlreadyOd> pager);
	
	public List<Map<String,Object>> queryReportAlreadyOd_totalData(Map<String,Object> params);
	
	public List<ReportMoney> queryReportMoney_YXGS(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno
			,@Param("spclno")String spclno
			,@Param("sptyno")String sptyno
			,@Param("spseno")String spseno
			,@Param("user_id")String user_id);
	public List<ReportMoney> queryReportMoney_QY(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno
			,@Param("spclno")String spclno
			,@Param("sptyno")String sptyno
			,@Param("spseno")String spseno
			,@Param("user_id")String user_id);
	public List<ReportMoney> queryReportMoney_TX(@Param("ormtno")String ormtno,@Param("ordorg")String ordorg,@Param("bradno")String bradno
			,@Param("spclno")String spclno
			,@Param("sptyno")String sptyno
			,@Param("spseno")String spseno
			,@Param("user_id")String user_id);
	public List<ReportMoney> queryReportMoney_ALL(@Param("ormtno")String ormtno,@Param("bradno")String bradno
			,@Param("spclno")String spclno
			,@Param("sptyno")String sptyno
			,@Param("spseno")String spseno
			,@Param("user_id")String user_id);
	
	
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


}