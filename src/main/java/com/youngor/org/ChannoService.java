package com.youngor.org;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.youngor.org.Channo;
import com.youngor.org.ChannoRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ChannoService extends AbstractService<Channo, String>{

	@Autowired
	private ChannoRepository channoRepository;
	
	@Override
	public ChannoRepository getRepository() {
		return channoRepository;
	}

}
