package com.youngor.order;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.utils.page.Pager;

@Repository
public interface BW2DZRepository {
	public Pager<Map<String,Object>> queryPager(Pager<Map<String,Object>> pager);
	
	public Map<String,Object> get_mlorno_mlorvn(Map<String,Object> params);
	
	public void update_DZ_ormtqt(@Param("mtorno")String mtorno,@Param("sampno")String sampno,@Param("suitno")String suitno,@Param("num")Integer num);
	public void update_BW_ormtqt(@Param("mtorno")String mtorno,@Param("sampno")String sampno,@Param("suitno")String suitno,@Param("num")Integer num);
}
