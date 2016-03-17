package com.mawujun.base;

import org.springframework.stereotype.Repository;
import java.util.UUID;
import com.mawujun.repository.IRepository;

import com.mawujun.base.OrderMeet;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface OrderMeetRepository extends IRepository<OrderMeet, UUID>{


}
