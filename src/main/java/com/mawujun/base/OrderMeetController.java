package com.mawujun.base;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.m.M;
import com.mawujun.utils.page.PageParam;
import com.mawujun.utils.page.PageResult;

import com.mawujun.base.OrderMeet;
import com.mawujun.base.OrderMeetService;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/orderMeet")
public class OrderMeetController {

	@Resource
	private OrderMeetService orderMeetService;


	/**
	 * 请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param id 是父节点的id
	 * @return
	 */
	/**@RequestMapping("/orderMeet/query.do")
	@ResponseBody
	public List<OrderMeet> query(String id) {
		Cnd cnd=Cnd.select().andEquals(M.OrderMeet.id, "root".equals(id)?null:id);
		List<OrderMeet> orderMeetes=orderMeetService.query(cnd);
		//JsonConfigHolder.setFilterPropertys(OrderMeet.class,M.OrderMeet.parent.name());
		return orderMeetes;
	}
	**/

	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/orderMeet/query.do")
	@ResponseBody
	public PageResult<OrderMeet> query(Integer start,Integer limit,String sampleName){
		PageParam page=PageParam.getInstance(start,limit);//.addParam(M.OrderMeet.sampleName, "%"+sampleName+"%");
		return orderMeetService.queryPage(page);
	}

	@RequestMapping("/orderMeet/query.do")
	@ResponseBody
	public List<OrderMeet> query() {	
		List<OrderMeet> orderMeetes=orderMeetService.queryAll();
		return orderMeetes;
	}
	

	@RequestMapping("/orderMeet/load.do")
	public OrderMeet load(UUID id) {
		return orderMeetService.get(id);
	}
	
	@RequestMapping("/orderMeet/create.do")
	//@ResponseBody
	public OrderMeet create(OrderMeet orderMeet) {
		orderMeetService.create(orderMeet);
		return orderMeet;
	}
	
	@RequestMapping("/orderMeet/update.do")
	//@ResponseBody
	public  OrderMeet update(OrderMeet orderMeet) {
		orderMeetService.update(orderMeet);
		return orderMeet;
	}
	
	@RequestMapping("/orderMeet/deleteById.do")
	//@ResponseBody
	public UUID deleteById(UUID id) {
		orderMeetService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/orderMeet/destroy.do")
	//@ResponseBody
	public OrderMeet destroy(OrderMeet orderMeet) {
		orderMeetService.delete(orderMeet);
		return orderMeet;
	}
	
	
}
