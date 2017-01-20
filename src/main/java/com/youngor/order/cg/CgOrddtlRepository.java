package com.youngor.order.cg;

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
public interface CgOrddtlRepository extends IRepository<CgOrddtl, com.youngor.order.cg.CgOrddtl.PK>{
	public  List<CgOrddtlVO> queryPage(Map<String,Object> params);

	public Pager<CgOrddt> queryPage4Insert(Pager<CgOrddt> pager);
	
	public  List<CgOrddtlVO> queryPage4Insert(Map<String,Object> params);
	
	public void delete4Page4Insert(Map<String,Object> params);
	public void updatePldate(@Param("sampno")String sampno);
}
