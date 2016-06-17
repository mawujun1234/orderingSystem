package com.youngor.suno;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Pager;
import com.youngor.utils.M;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/pubSuno")
public class PubSunoController {

	@Resource
	private PubSunoService pubSunoService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/pubSuno/query.do")
	@ResponseBody
	public Pager<PubSuno> query(Pager<PubSuno> pager){
		return pubSunoService.queryPage(pager);
	}

	@RequestMapping("/pubSuno/query4Combo.do")
	@ResponseBody
	public List<PubSuno> query4Combo(String query) {	
		List<PubSuno> pubSudoes=new ArrayList<PubSuno>();
		if(query!=null && !"".equals(query)){
			pubSudoes=pubSunoService.query(Cnd.select().andLikeIf(M.PubSuno.idsunm, query));
		}		
				
		PubSuno wu=new PubSuno();
		wu.setIdsuno("");
		wu.setIdsunm("无");
		pubSudoes.add(0, wu);
		return pubSudoes;
	}
	

	@RequestMapping("/pubSuno/load.do")
	public PubSuno load(String id) {
		return pubSunoService.get(id);
	}
	
	@RequestMapping("/pubSuno/create.do")
	//@ResponseBody
	public PubSuno create(@RequestBody PubSuno pubSuno) {
		pubSunoService.create(pubSuno);
		return pubSuno;
	}
	
	@RequestMapping("/pubSuno/update.do")
	//@ResponseBody
	public  PubSuno update(@RequestBody PubSuno pubSuno) {
		pubSunoService.update(pubSuno);
		return pubSuno;
	}
	
	@RequestMapping("/pubSuno/deleteById.do")
	//@ResponseBody
	public String deleteById(String id) {
		pubSunoService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/pubSuno/destroy.do")
	//@ResponseBody
	public PubSuno destroy(@RequestBody PubSuno pubSuno) {
		pubSunoService.delete(pubSuno);
		return pubSuno;
	}
	
	
}
