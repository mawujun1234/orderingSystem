package com.youngor.pubsize;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.ObjectNotFoundException;
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
public class OrdSzrtService extends AbstractService<OrdSzrt, OrdSzrt.PK>{

	@Autowired
	private OrdSzrtRepository ordSzrtRepository;
	
	@Override
	public OrdSzrtRepository getRepository() {
		return ordSzrtRepository;
	}
	@Override
	public OrdSzrt.PK create(OrdSzrt ordSzrt) {
		try {
		ordSzrtRepository.deleteById(ordSzrt.geetPK());
		} catch( ObjectNotFoundException e) {
			
		}
		ordSzrtRepository.create(ordSzrt);
		return ordSzrt.geetPK();
	}
	
	public Map<String,Object> querySaleHisGrid(Map<String,Object> params) {
		params.put("spclno_spseno", params.get("spclno")+"|"+params.get("spseno"));
		List<Map<String,Object>> list=ordSzrtRepository.querySaleHisGrid(params);
		Map<String,Object> result=new HashMap<String,Object>();
		List<Map<String,Object>> list_data= new ArrayList<Map<String,Object>>();
		//存放列的数据
		List<Map<String,Object>> list_columns= new ArrayList<Map<String,Object>>();
		
		Map<String,Map<String,Object>> key_temp=new HashMap<String,Map<String,Object>>();
		Set<Object> column_temp=new HashSet<Object>();
		for(Map<String,Object> map:list){
			String key=""+map.get("PRSENM")+map.get("BANDNM")+map.get("PRYEAR");
			if(key_temp.get(key)==null) {
				Map<String,Object> map_data=new HashMap<String,Object>();
				map_data.put("PRSENM", map.get("PRSENM"));
				map_data.put("BANDNM", map.get("BANDNM"));
				map_data.put("PRYEAR", map.get("PRYEAR"));
				map_data.put(map.get("SIZENM").toString(), map.get("SAQT"));
				key_temp.put(key, map_data);
				list_data.add(map_data);
				
				
			} else {
				Map<String,Object> map_data=key_temp.get(key);
				map_data.put(map.get("SIZENM").toString(), map.get("SAQT"));
			}
			
			//组装列
			if(!column_temp.contains(map.get("SIZENM"))){
				Map<String,Object> map_column=new HashMap<String,Object>();
				map_column.put("sizeno", map.get("SIZENM"));
				map_column.put("sizenm", map.get("SIZENM"));
				list_columns.add(map_column);
				
				column_temp.add(map.get("SIZENM"));
			}
			
			
		}
		result.put("list_data", list_data);
		result.put("list_columns", list_columns);
		return result;
	}
}
