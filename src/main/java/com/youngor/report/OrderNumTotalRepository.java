package com.youngor.report;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.utils.page.Pager;

@Repository
public interface OrderNumTotalRepository {
	public  Pager<OrderNumTotal> query( Pager<OrderNumTotal> pager);
	public  List<OrderNumTotal> query(Map<String,Object> params);
	
	public  List<OrderNumTotal> exportAll(Map<String,Object> params);
}
