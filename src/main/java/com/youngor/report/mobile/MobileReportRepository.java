package com.youngor.report.mobile;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileReportRepository {
	public List<Map<String,Object>> queryOrdorgCondition(@Param("ormtno")String ormtno,@Param("user_id")String user_id);

}
