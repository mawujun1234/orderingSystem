package com.youngor.ordmt;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository.IRepository;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface OrdOrgRepository extends IRepository<OrdOrg, com.youngor.ordmt.OrdOrg.PK>{

	public OrdOrg getOrdOrgByOrg(@Param("ormtno")String ormtno,@Param("orgno")String orgno) ;
	
	public List<OrdOrg> queryForPrint(@Param("ormtno")String ormtno,@Param("in_param")String in_param);
}
