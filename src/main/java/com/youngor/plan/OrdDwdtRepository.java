package com.youngor.plan;

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
public interface OrdDwdtRepository extends IRepository<OrdDwdt, com.youngor.plan.OrdDwdt.PK>{

	public Pager<Map<String,Object>> queryPager1(Pager<Map<String,Object>> pager);
	
//	public List<OrdDwdt> queryList(@Param("sampnos")List<Map<String,Object>> sampnos,@Param("ormtno")String ormtno,
//			@Param("ortyno")String ortyno,@Param("count_type")String count_type,@Param("yxgsno")String yxgsno,@Param("qyno")String qyno);
	
	public Integer indsert_dwdt(@Param("sampno")String sampno,@Param("suitno")String suitno,@Param("ormtno")String ormtno,
			@Param("ortyno")String ortyno,@Param("count_type")String count_type,@Param("yxgsno")String yxgsno,@Param("qyno")String qyno
			,@Param("field")String field,@Param("value")String value);
	public Integer update_dwdt(@Param("sampno")String sampno,@Param("suitno")String suitno,@Param("ormtno")String ormtno,
			@Param("ortyno")String ortyno,@Param("count_type")String count_type,@Param("yxgsno")String yxgsno,@Param("qyno")String qyno
			,@Param("field")String field,@Param("value")String value);
	
	
}
