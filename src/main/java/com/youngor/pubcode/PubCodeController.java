package com.youngor.pubcode;
import java.util.List;

import javax.annotation.PostConstruct;
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
//@RequestMapping("/pubCode")
public class PubCodeController {

	@Resource
	private PubCodeService pubCodeService;
	@Resource
	private PubCodeTypeService pubCodeTypeService;

	@RequestMapping("/pubCodeType/query4Combo.do")
	@ResponseBody
	public List<PubCode> query4Combo(String tyno,String fitno) {
		Cnd cnd=Cnd.select().andEqualsIf(M.PubCode.fitno, fitno).andEquals(M.PubCode.tyno, tyno);
	
		List<PubCode> pubCodes=pubCodeService.query(cnd);
		return pubCodes;
		
//		List<PubCode> list=new ArrayList<PubCode>();
//		for(int i=0;i<10;i++){
//			PubCode code=new PubCode();
//			code.setItno(i+"code");
//			code.setItnm(i+"名称");
//			list.add(code);
//		}
//		return list;
	}
	
	@PostConstruct
	public void initPubCodeCache(){
		List<PubCodeType> pubCodeTypes=pubCodeTypeService.queryAll();
		for(PubCodeType pubCodeType:pubCodeTypes){
			List<PubCode> pubCodes=pubCodeService.query(Cnd.select().andEquals(M.PubCode.tyno, pubCodeType.getTyno()));
			PubCodeCache.setPubCode(pubCodeType, pubCodes);
		}
	}
	


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/pubCode/query.do")
	@ResponseBody
	public Pager<PubCode> query(Pager<PubCode> pager){
		return pubCodeService.queryPage(pager);
	}

	@RequestMapping("/pubCode/queryAll.do")
	@ResponseBody
	public List<PubCode> queryAll() {	
		List<PubCode> pubCodees=pubCodeService.queryAll();
		return pubCodees;
	}
	

	@RequestMapping("/pubCode/load.do")
	public PubCode load(String id) {
		return pubCodeService.get(id);
	}
	
	@RequestMapping("/pubCode/create.do")
	//@ResponseBody
	public PubCode create(@RequestBody PubCode pubCode) {
		pubCodeService.create(pubCode);
		return pubCode;
	}
	
	@RequestMapping("/pubCode/update.do")
	//@ResponseBody
	public  PubCode update(@RequestBody PubCode pubCode) {
		pubCodeService.update(pubCode);
		return pubCode;
	}
	
	@RequestMapping("/pubCode/deleteById.do")
	//@ResponseBody
	public String deleteById(String id) {
		pubCodeService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/pubCode/destroy.do")
	//@ResponseBody
	public PubCode destroy(@RequestBody PubCode pubCode) {
		pubCodeService.delete(pubCode);
		return pubCode;
	}
	
	
}
