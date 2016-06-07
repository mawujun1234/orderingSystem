package com.youngor.pubcode;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
//@RequestMapping("/pubCode")
public class PubCodeController {

	@Resource
	private PubCodeService pubCodeService;
	@Resource
	private PubCodeRepository pubCodeRepository;
	@Resource
	private PubCodeTypeService pubCodeTypeService;

	@RequestMapping("/pubCode/query4Combo.do")
	@ResponseBody
	public List<PubCode> query4Combo(String tyno,String fitno,String bradno,String stat) {

		//默认是所有课访问品牌中的第一个品牌，和前端的品牌combobox要对应起来
		if(bradno==null){
			bradno=ContextUtils.getFirstBradno();
		}
		
		
		List<PubCode> pubCodes=pubCodeService.query(tyno,fitno, bradno,stat);
		//如果返回的是品牌大类，就过滤成用户可访问的品牌大类
		if("1".equals(tyno)){
			List<PubCode> list=new ArrayList<PubCode>();
			for(PubCode pubCode:pubCodes){
				for(String itno:ShiroUtils.getAuthenticationInfo().getBrandes()){
					if(itno.equals(pubCode.getItno())){
						list.add(pubCode);
					}
				}
			}
			pubCodes=list;
		}
		if("0".equals(tyno)){
			List<PubCode> list=new ArrayList<PubCode>();
			for(PubCode pubCode:pubCodes){
				for(String itno:ShiroUtils.getAuthenticationInfo().getClasses()){
					if(itno.equals(pubCode.getItno())){
						list.add(pubCode);
					}
				}
			}
			pubCodes=list;
		}
		PubCode wu=new PubCode();
		wu.setItno("  ");
		wu.setItnm("无");
		pubCodes.add(0, wu);
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
	
	@RequestMapping("/pubCodeType/queryVersno4Ordmt.do")
	@ResponseBody
	public List<PubCode> queryVersno4Ordmt(String ormtno,String bradno,String spclno) {
		// 默认是所有课访问品牌中的第一个品牌，和前端的品牌combobox要对应起来
		if (bradno == null) {
			bradno = ContextUtils.getFirstBradno();
		}
		
		if(ormtno==null || "".equals(ormtno)){
			Ordmt ordmt= ContextUtils.getFirstOrdmt();
			ormtno=ordmt.getOrmtno();
		}
		
		return pubCodeRepository.queryVersno4Ordmt(ormtno, bradno,spclno);
		
	}
	
	@RequestMapping("/pubCodeType/querySpseno4Ordmt.do")
	@ResponseBody
	public List<PubCode> querySpseno4Ordmt(String ormtno,String bradno,String spclno) {
		// 默认是所有课访问品牌中的第一个品牌，和前端的品牌combobox要对应起来
		if (bradno == null) {
			bradno = ContextUtils.getFirstBradno();
		}
		
		if(ormtno==null || "".equals(ormtno)){
			Ordmt ordmt= ContextUtils.getFirstOrdmt();
			ormtno=ordmt.getOrmtno();
		}
		
		return pubCodeRepository.querySpseno4Ordmt(ormtno, bradno,spclno);
		
	}
	
	@PostConstruct
	public void initPubCodeCache(){
		List<PubCodeType> pubCodeTypes=pubCodeTypeService.queryAll();
		for(PubCodeType pubCodeType:pubCodeTypes){
			List<PubCode> pubCodes=pubCodeService.query(Cnd.select().andEquals(M.PubCode.tyno, pubCodeType.getTyno()));
			PubCodeCache.setPubCode(pubCodeType, pubCodes);
		}
	}
	


//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/pubCode/query.do")
//	@ResponseBody
//	public Pager<PubCode> query(Pager<PubCode> pager){
//		return pubCodeService.queryPage(pager);
//	}

	@RequestMapping("/pubCode/query.do")
	@ResponseBody
	public List<PubCode> query(String tyno) {	
		List<PubCode> pubCodees=
				pubCodeService.query(Cnd.select().andEquals(M.PubCode.tyno, tyno).andEquals(M.PubCode.itst, 1)
				.asc(M.PubCode.itso));
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
