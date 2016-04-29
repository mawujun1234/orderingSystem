package com.youngor.pubsize;
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
//@RequestMapping("/pubSize")
public class PubSizeController {

	@Resource
	private PubSizeService pubSizeService;





	@RequestMapping("/pubSize/queryStdsz.do")
	@ResponseBody
	public Pager<PubSize> queryStdsz(Pager<PubSize> pager){
		pager.addParam(M.PubSize.sizety, "STDSZ");
		return pubSizeService.queryPage(pager);
	}
	
	@RequestMapping("/pubSize/queryPrdp.do")
	@ResponseBody
	public Pager<PubSize> queryPrdp(Pager<PubSize> pager){
		pager.addParam(M.PubSize.sizety, "PRDPK");
		return pubSizeService.queryPage(pager);
	}
	
	/**
	 * 查询规格范围,查询条件是品牌+大类,在样衣管理的时候使用
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	@RequestMapping("/pubSize/queryPRDSZTY.do")
	@ResponseBody
	public List<PubSize> queryPRDSZTY(String szbrad,String szclno) {	
		List<PubSize> pubSizees=pubSizeService.query(Cnd.select()
				.andEquals(M.PubSize.sizety, "PRDSZTY")
				.andEquals(M.PubSize.szbrad, szbrad)
				.andEquals(M.PubSize.szclno, szclno)
				.asc(M.PubSize.sizeso));
		return pubSizees;
	}
	

	@RequestMapping("/pubSize/load.do")
	@ResponseBody
	public PubSize load(PubSize.PK id) {
		return pubSizeService.get(id);
	}
	
	@RequestMapping("/pubSize/create.do")
	//@ResponseBody
	public PubSize create(@RequestBody PubSize pubSize) {
		pubSizeService.create(pubSize);
		return pubSize;
	}
	
	@RequestMapping("/pubSize/update.do")
	//@ResponseBody
	public  PubSize update(@RequestBody PubSize pubSize) {
		pubSizeService.update(pubSize);
		return pubSize;
	}
	
	@RequestMapping("/pubSize/deleteById.do")
	//@ResponseBody
	public PubSize.PK deleteById(PubSize.PK id) {
		pubSizeService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/pubSize/destroy.do")
	//@ResponseBody
	public PubSize destroy(@RequestBody PubSize pubSize) {
		pubSizeService.delete(pubSize);
		return pubSize;
	}
	
	
}
