package com.youngor.ordmt;

import org.springframework.stereotype.Repository;
import java.util.UUID;
import com.mawujun.repository.IRepository;
import com.mawujun.utils.page.Pager;
import com.youngor.ordmt.Ordmt;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface OrdmtRepository extends IRepository<Ordmt, String>{

	public Pager<OrdmtVO> queryPageVO(Pager<OrdmtVO> pager);

}