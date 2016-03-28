package com.youngor.permission;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.Pager;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/user/query.do")
	@ResponseBody
	public Pager<User> query(Pager<User> pager,String name,String loginName,String role_id){
		pager.addParam("name",  name);
		pager.addParam("loginName",  loginName);
		pager.addParam("role_id",  role_id);
		return userService.queryPage(pager);
	}

	@RequestMapping("/user/queryAll.do")
	@ResponseBody
	public List<User> queryAll() {	
		List<User> useres=userService.queryAll();
		return useres;
	}
	

	@RequestMapping("/user/load.do")
	public User load(String id) {
		return userService.get(id);
	}
	
	@RequestMapping("/user/create.do")
	//@ResponseBody
	public User create(@RequestBody User user,String role_id) {
		userService.create(user,role_id);
		return user;
	}
	
	@RequestMapping("/user/update.do")
	//@ResponseBody
	public  User update(@RequestBody User user) {
		userService.update(user);
		return user;
	}
	
	@RequestMapping("/user/deleteById.do")
	//@ResponseBody
	public String deleteById(String id) {
		userService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/user/destroy.do")
	//@ResponseBody
	public User destroy(@RequestBody User user,String role_id) {
		userService.delete(user,role_id);
		return user;
	}
	
	
}
