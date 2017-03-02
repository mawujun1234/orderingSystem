package com.youngor.order;

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
public interface OrdChgdtlRepository extends IRepository<OrdChgdtl, com.youngor.order.OrdChgdtl.PK>{

	public Pager<OrdChgdtlVO> queryPager1(Pager<OrdChgdtlVO> pager);
	
	public List<OrdChgdtlCompVO> query4comp(Map<String,Object> params);
	
	public List<OrdChgdtlQyVO> query4qy(Map<String,Object> params);
	
	public List<Map<String,Object>> queryOrderQy(@Param("ormtno")String ormtno,@Param("sampno")String sampno,@Param("suitno")String suitno,@Param("compno")String compno
			,@Param("bradno")String bradno,@Param("spclno")String spclno);
}
