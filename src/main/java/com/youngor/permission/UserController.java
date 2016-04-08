package com.youngor.permission;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
//@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;


	@RequestMapping("/user/login.do")
	//@ResponseBody
	public String login(HttpServletRequest request,String username,String password,Model model) {
		Subject subject = SecurityUtils.getSubject(); 
		
		UsernamePasswordToken token = new UsernamePasswordToken(username, password); 
		//subject.login(token);
		String error=null;
		try {  
            subject.login(token);  
        } catch (UnknownAccountException e) {  
            error = "用户名/密码错误";  
        } catch (IncorrectCredentialsException e) {  
            error = "用户名/密码错误";  
        } catch (AuthenticationException e) {  
            //其他错误，比如锁定，如果想单独处理请单独catch处理  
            error = "认证失败，账号不存在!";  
        }  
        if(error != null) {//出错了，返回登录页面  
        	model.addAttribute("msg", error);
        	model.addAttribute("success", false);
        	//return "/main/login.jsp";
        	return "/main/failure.jsp";
        } else {//登录成功  
            //req.getRequestDispatcher("/WEB-INF/jsp/loginSuccess.jsp").forward(req, resp); 
        	 String successUrl = null;
        	 SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
             if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)) {
                 successUrl = savedRequest.getRequestUrl();
             }
             if(successUrl==null){
            	 successUrl="/main/index.jsp";
             }
             model.addAttribute("success", true);
            //ShiroUtils.getAuthenticationInfo().setIpAddr(getIpAddr(request));
             
             
             //显示调用这个，来初始化ShiroAuthorizingRealm中的doGetAuthorizationInfo方法，来获取用户可以访问的资源,否则将不会调用doGetAuthorizationInfo
             SecurityUtils.getSubject().hasRole("XXX") ;
             return successUrl;
        }  
       // return model;
	}
	private String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = request.getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	@RequestMapping("/user/logout.do")
	//@ResponseBody
	public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Subject subject = SecurityUtils.getSubject(); 
		subject.logout();
		//return "/main/login";
		response.sendRedirect("/main/login.jsp");
		//response.getRequestDispatcher("/main/login").forward(request, response);
	}
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
		pager.addParam_like(M.User.name,  name);
		pager.addParam_like(M.User.loginName,  loginName);
		pager.addParam("role_id",  role_id);
		return userService.queryPage(pager);
	}
	
	@RequestMapping("/user/queryByPosition.do")
	@ResponseBody
	public Pager<User> queryByPosition(Pager<User> pager,String name,String loginName,String position_id){
		pager.addParam_like(M.User.name,  name);
		pager.addParam_like(M.User.loginName,  loginName);
		pager.addParam("position_id",  position_id);
		return userService.queryByPosition(pager);
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
	
	@RequestMapping("/user/addRole.do")
	//@ResponseBody
	public String addRole(String user_id,String role_id) {
		userService.addRole(user_id,role_id);
		return user_id;
	}
	
	@RequestMapping("/user/create.do")
	//@ResponseBody
	public User create(@RequestBody User user,String position_id,String orgno) {
		userService.create(user,position_id,orgno);
		return user;
	}
	
	@RequestMapping("/user/update.do")
	//@ResponseBody
	public  User update(@RequestBody User user) {
		userService.update(user);
		return user;
	}
	
	@RequestMapping("/user/updatePwd.do")
	//@ResponseBody
	public String updatePwd(String password,String password_new) {
		userService.update(Cnd.update().set(M.User.pwd, password_new).andEquals(M.User.id, ShiroUtils.getUserId()).andEquals(M.User.pwd, password));
		return ShiroUtils.getUserId();
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
		userService.delete(user);
		return user;
	}
	
	@RequestMapping("/user/deleteByRole.do")
	//@ResponseBody
	public String deleteByRole(String user_id,String role_id) {
		userService.deleteByRole(user_id,role_id);
		return user_id;
	}
	
	
}
