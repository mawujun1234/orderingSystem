package com.youngor.ordmt;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.page.Pager;
import com.youngor.permission.ShiroUtils;
import com.youngor.utils.M;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OrdmtService extends AbstractService<Ordmt, String>{

	@Autowired
	private OrdmtRepository ordmtRepository;
	@Autowired
	private  OrdmtSeasonRepository ordmtSeasonRepository;
	
	@Override
	public OrdmtRepository getRepository() {
		return ordmtRepository;
	}
	
	@Override
	public String create(Ordmt entity) {
		//判断是否存在有正在进行中的订货会
		int count=this.queryCount(Cnd.count().andEquals(M.Ordmt.ormtst, 0));
		if(count>0){
			throw new BusinessException("请先结束‘进行中’的订货会，只允许一个‘进行中’的订货会");
		}
		
		return this.getRepository().create(entity);
	}
	
	public Pager<OrdmtVO> queryPageVO(Pager<OrdmtVO> pager) {
		return ordmtRepository.queryPageVO(pager);
	}

	public void create(Ordmt ordmt,String[] seasnos)  {
		super.create(ordmt);
		if(seasnos!=null){
		for(String seasno:seasnos){
			OrdmtSeason a=new OrdmtSeason();
			a.setSeasno(seasno);
			a.setOrmtno(ordmt.getOrmtno());
			a.setLmdt(new Date());
			a.setLmsp(ShiroUtils.getLoginName());
			ordmtSeasonRepository.create(a);
		}
		}
	}
	
	public  void update(Ordmt ordmt,String[] seasnos) {
		super.update(ordmt);
		ordmtSeasonRepository.deleteBatch(Cnd.delete().andEquals(M.OrdmtSeason.ormtno, ordmt.getOrmtno()));
		if(seasnos!=null){
		for(String seasno:seasnos){
			OrdmtSeason a=new OrdmtSeason();
			a.setSeasno(seasno);
			a.setOrmtno(ordmt.getOrmtno());
			a.setLmdt(new Date());
			a.setLmsp(ShiroUtils.getLoginName());
			ordmtSeasonRepository.create(a);
		}
		}
	}
}
