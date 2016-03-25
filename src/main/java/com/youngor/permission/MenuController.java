package com.youngor.permission;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.youngor.utils.M;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/menu")
public class MenuController {

	@Resource
	private MenuService menuService;


	/**
	 * 请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param id 是父节点的id
	 * @return
	 */
	/**@RequestMapping("/menu/query.do")
	@ResponseBody
	public List<Menu> query(String id) {
		Cnd cnd=Cnd.select().andEquals(M.Menu.id, "root".equals(id)?null:id);
		List<Menu> menues=menuService.query(cnd);
		//JsonConfigHolder.setFilterPropertys(Menu.class,M.Menu.parent.name());
		return menues;
	}
	**/

//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/menu/query.do")
//	@ResponseBody
//	public PageResult<Menu> query(Integer start,Integer limit,String sampleName){
//		//PageParam page=PageParam.getInstance(start,limit);//.addParam(M.Menu.sampleName, "%"+sampleName+"%");
//		return menuService.queryPage(page);
//	}

	@RequestMapping("/menu/query.do")
	@ResponseBody
	public List<Menu> query(String id) {	
		
		//List<Menu> menues=menuService.query(Cnd.where().andEquals(M.Menu.parent_id, id));
		List<Menu> menues=menuService.queryAll();
		return menues;
	}
	

	@RequestMapping("/menu/load.do")
	public Menu load(UUID id) {
		return menuService.get(id);
	}
	
	@RequestMapping("/menu/create.do")
	//@ResponseBody
	public Menu create(@RequestBody Menu menu) {
		menuService.create(menu);
		return menu;
	}
	
	@RequestMapping("/menu/update.do")
	//@ResponseBody
	public  Menu update(@RequestBody Menu menu) {
		menuService.update(menu);
		return menu;
	}
	
	@RequestMapping("/menu/deleteById.do")
	//@ResponseBody
	public UUID deleteById(UUID id) {
		menuService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/menu/destroy.do")
	//@ResponseBody
	public Menu destroy(@RequestBody Menu menu) {
		
		
		menuService.delete(menu);
		
		return menu;
	}
	
	
}
