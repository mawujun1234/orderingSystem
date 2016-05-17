package com.youngor.org;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.service.AbstractService;


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
	@Autowired
	private PositionOrgAccessRepository positionOrgAccessRepository;
	@Autowired
	private OrgRepository orgRepository;
	
	@Override
	public PositionRepository getRepository() {
		return positionRepository;
	}
	//@Override
	public String create(Position position,Dim dim) {
		positionRepository.create(position);
		
//		//同时创建该角色可以访问的组织节点范围
//		把所有父节点加进来，然后再把所有的子节点也加进来
//		
		if(position.getAccessRule()==AccessRule.this_org){
			this_org(position,dim);
		} else {
			all_org(position,dim);
		}
		
		return position.getId();
	}
	public void update(Position position,Dim dim) {
		if(position.getAccessRule()==AccessRule.this_org){
			this_org(position,dim);
		} else {
			all_org(position,dim);
		}
		this.getRepository().update(position);
	}
	public void all_org(Position position,Dim dim){
		
	}
	/**
	 * 当选择的规则是  本级组织单元的时候，就选择本级所有的组织单元
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 */
	public void this_org(Position position,Dim dim){
		addParent(position.getId(),position.getOrgno(),dim);
		addChildren(position.getId(),position.getOrgno(),dim);
	}
	
	private void addParent(String position_id,String orgno,Dim dim){
		orgRepository.insert_positionorgaccess(position_id, orgno);
		if(orgno.equals("root")){
			return;
		}
		List<Org> parents=orgRepository.queryParent(orgno, dim);
		for(Org org:parents){
			addParent(position_id ,org.getOrgno(), dim);	
		}
	}
	
	private void addChildren(String position_id,String orgno,Dim dim) {
		List<Org> children=orgRepository.queryChildren(orgno, dim);
		for(Org child:children){
			orgRepository.insert_positionorgaccess(position_id, child.getOrgno());
			if(!child.getOrgty().equals("SHOP")) {
				addChildren(position_id ,child.getOrgno(), dim);
			}
			
		}
	}

}
