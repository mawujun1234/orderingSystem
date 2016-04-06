package com.youngor.org;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.youngor.org.Position;
import com.youngor.org.PositionRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PositionService extends AbstractService<Position, String>{

	@Autowired
	private PositionRepository positionRepository;
	
	@Override
	public PositionRepository getRepository() {
		return positionRepository;
	}

}
