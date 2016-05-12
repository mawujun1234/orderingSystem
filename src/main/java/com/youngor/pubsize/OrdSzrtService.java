package com.youngor.pubsize;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.youngor.pubsize.OrdSzrt;
import com.youngor.pubsize.OrdSzrtRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OrdSzrtService extends AbstractService<OrdSzrt, String>{

	@Autowired
	private OrdSzrtRepository ordSzrtRepository;
	
	@Override
	public OrdSzrtRepository getRepository() {
		return ordSzrtRepository;
	}

}
