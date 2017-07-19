package com.youngor.order1;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository.IRepository;
import com.mawujun.utils.page.Pager;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface OrdOrddtlRepository extends IRepository<OrdOrddtl, com.youngor.order1.OrdOrddtl.PK>{

	public Pager<OrdOrddtlQuery> queryPage1(Pager<OrdOrddtlQuery> pager);
	public  List<OrdOrddtlQuery> queryPage1( Map<String,Object> params);
	
	public List<AAAA> querymx(@Param("ormtno")String ormtno,@Param("ordate")String ordate);
}
