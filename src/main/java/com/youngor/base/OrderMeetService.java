package com.youngor.base;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;
import com.youngor.base.OrderMeet;
import com.youngor.base.OrderMeetRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OrderMeetService extends AbstractService<OrderMeet, UUID>{

	@Autowired
	private OrderMeetRepository orderMeetRepository;
	
	@Override
	public OrderMeetRepository getRepository() {
		return orderMeetRepository;
	}

}
