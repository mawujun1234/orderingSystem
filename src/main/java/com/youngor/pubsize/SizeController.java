package com.youngor.pubsize;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.youngor.ordmt.Ordmt;
import com.youngor.permission.ShiroUtils;
import com.youngor.utils.ContextUtils;
import com.youngor.utils.M;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/size")
public class SizeController {

	@Resource
	private SizeService sizeService;


//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/size/queryPager.do")
//	@ResponseBody
//	public Pager<Size> queryPager(Pager<Size> pager){
//		
//		return sizeService.queryPage(pager);
//	}

	@RequestMapping("/size/query.do")
	@ResponseBody
	public List<Size> query(String ormtno,String ysizety,String ysizeno) {	
		List<Size> sizees=sizeService.query(Cnd.select().andEquals(M.Size.ormtno, ormtno)
				.andEquals(M.Size.ysizety, ysizety)
				.andEquals(M.Size.ysizeno, ysizeno));
		return sizees;
	}
	

	@RequestMapping("/size/load.do")
	public Size load(String id) {
		return sizeService.get(id);
	}
	
	@RequestMapping("/size/create.do")
	@ResponseBody
	public Size create(@RequestBody Size size) {
		//判断订货会是否已经开始，如果已经开始，就不能进行增，删，改了
		Ordmt ordmt=  ContextUtils.getOrdmt(size.getOrmtno());
		if(!ordmt.isBeforeMtstdt()){
			throw new BusinessException("订货会已经开始或结束，不能新增!");
		}
		
		
		size.setRgdt(new Date());
		size.setRgsp(ShiroUtils.getLoginName());
		size.setLmdt(new Date());
		size.setLmsp(ShiroUtils.getLoginName());
		sizeService.create(size);
		return size;
	}
	
	@RequestMapping("/size/update.do")
	@ResponseBody
	public  Size update(@RequestBody Size size) {
		// 判断订货会是否已经开始，如果已经开始，就不能进行增，删，改了
		Ordmt ordmt = ContextUtils.getOrdmt(size.getOrmtno());
		if (!ordmt.isBeforeMtstdt()) {
			throw new BusinessException("订货会已经开始或结束，不能更新!");
		}

		size.setLmdt(new Date());
		size.setLmsp(ShiroUtils.getLoginName());
		sizeService.update(size);
		return size;
	}
	
//	@RequestMapping("/size/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		sizeService.deleteById(id);
//		return id;
//	}
	
	@RequestMapping("/size/destroy.do")
	@ResponseBody
	public Size destroy(@RequestBody Size size) {
		// 判断订货会是否已经开始，如果已经开始，就不能进行增，删，改了
		Ordmt ordmt = ContextUtils.getOrdmt(size.getOrmtno());
		if (!ordmt.isBeforeMtstdt()) {
			throw new BusinessException("订货会已经开始或结束，不能删除!");
		}
		//判断这个规格范围是否已经被引用，如果已经被引用就不能删除
		if(sizeService.sizeInSample(size.getSizeno())>0){
			throw new BusinessException("改规格范围已经被使用，不能删除!");
		}

		sizeService.delete(size);
		return size;
	}
	
	@RequestMapping("/size/copy.do")
	@ResponseBody
	public  String copy(String sizeno_old) {
		// 判断订货会是否已经开始，如果已经开始，就不能进行增，删，改了
		Ordmt ordmt = ContextUtils.getFirstOrdmt();//ContextUtils.getOrdmt(size.getOrmtno());
		if (!ordmt.isBeforeMtstdt()) {
			throw new BusinessException("订货会已经开始或结束，不能更新!");
		}
		sizeService.copy(sizeno_old);
//		size.setLmdt(new Date());
//		size.setLmsp(ShiroUtils.getLoginName());
//		sizeService.update(size);
		return "{success:true}";
	}
}
