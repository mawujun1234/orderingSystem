package com.youngor.pubsize;

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
public interface OrdSzrtRepository extends IRepository<OrdSzrt, String>{
	public List<PubSize> queryStdszColumns(@Param("sizegp")String sizegp);

}
