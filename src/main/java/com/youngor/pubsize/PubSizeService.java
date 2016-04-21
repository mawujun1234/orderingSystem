package com.youngor.pubsize;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.youngor.pubsize.PubSize;
import com.youngor.pubsize.PubSizeRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PubSizeService extends AbstractService<PubSize, String>{

	@Autowired
	private PubSizeRepository pubSizeRepository;
	
	@Override
	public PubSizeRepository getRepository() {
		return pubSizeRepository;
	}

}
