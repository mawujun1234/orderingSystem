package com.youngor.order.cg;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository.IRepository;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface CgOrdhdRepository extends IRepository<CgOrdhd, com.youngor.order.cg.CgOrdhd.PK>{
	public List<CgOrdhdVO> queryAll_1(Map<String,Object> params);

}
