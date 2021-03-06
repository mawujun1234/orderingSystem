package com.youngor.pubcode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
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
	
	@PostConstruct
	public void initPubCodeCache(){
		//PubCodeCache.refreshPubCode();
		List<PubCodeType> pubCodeTypes=pubCodeTypeService.queryAll();
		for(PubCodeType pubCodeType:pubCodeTypes){
			List<PubCode> pubCodes=pubCodeService.query(Cnd.select().andEquals(M.PubCode.tyno, pubCodeType.getTyno()));
			PubCodeCache.setPubCode(pubCodeType, pubCodes);
		}
	}
	
	@Scheduled(cron="0 0/10 * * * ? ")   //每5分执行一次 
	public void refeshPubCodeCache() {
		initPubCodeCache();
		System.out.println("=================================================刷新缓存");
		System.out.println("=================================================刷新缓存");
		System.out.println("=================================================刷新缓存");
		System.out.println("=================================================刷新缓存");
	}

	/**
	 * 
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param tyno
	 * @param fitno
	 * @param bradno
	 * @param stat_stat 获取当季可用的 1:就是获取当季
	 * @param showBlank：是否显示"无"这个数据
	 * @param query 输入的查询参数
	 * @return
	 */
	@RequestMapping("/pubCode/query4Combo.do")
	@ResponseBody
	public List<PubCode> query4Combo(String tyno,String fitno,String bradno,String stat_stat,Boolean showBlank,String query) {
		if("none".equals(fitno)){
			List<PubCode> pubCodes=new ArrayList<PubCode>();
			PubCode wu=new PubCode();
			wu.setItno("");
			wu.setItnm("无");
			pubCodes.add(0, wu);
			return pubCodes;
		}

		//默认是所有课访问品牌中的第一个品牌，和前端的品牌combobox要对应起来
		if(bradno==null){
			bradno=ContextUtils.getFirstBradno();
		}
		
		
		List<PubCode> pubCodes=pubCodeService.query(tyno,fitno, bradno,stat_stat,query);
		if(!ShiroUtils.isLogon()){
			return pubCodes;
		}
		//如果返回的是品牌大类，就过滤成用户可访问的品牌大类
		if("1".equals(tyno)){
			List<PubCode> list=new ArrayList<PubCode>();
			for(PubCode pubCode:pubCodes){
				for(PubCode itno:ShiroUtils.getAuthenticationInfo().getBrandes()){
					if(itno.getItno().equals(pubCode.getItno())){
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
		if(showBlank!=null && showBlank==true){
			PubCode wu=new PubCode();
			wu.setItno("");
			wu.setItnm("无");
			pubCodes.add(0, wu);
		}
		
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
	
	@RequestMapping("/pubCode/queryUnms4Combo.do")
	@ResponseBody
	public List<Map<String,Object>> queryUnms4Combo(String spclno,String sptyno,String spseno) {
		List<Map<String,Object>> list= pubCodeRepository.queryUnms4Combo(spclno, sptyno, spseno);
		
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map:list){
			Map<String,Object> aa=new HashMap<String,Object>();
			aa.put("id", map.get("SPUNMS"));
			aa.put("name", map.get("SPUNMS"));
			result.add(aa);
		}
		return result;
	}
	
	@RequestMapping("/pubCodeType/queryVersno4Ordmt.do")
	@ResponseBody
	public List<PubCode> queryVersno4Ordmt(String ormtno,String bradno,String spclno,Boolean showBlank) {
		// 默认是所有课访问品牌中的第一个品牌，和前端的品牌combobox要对应起来
		if (bradno == null) {
			bradno = ContextUtils.getFirstBradno();
		}
		
		if(ormtno==null || "".equals(ormtno)){
			Ordmt ordmt= ContextUtils.getFirstOrdmt();
			ormtno=ordmt.getOrmtno();
		}
		
		
		List<PubCode> list= pubCodeRepository.queryVersno4Ordmt(ormtno, bradno,spclno);
		if(showBlank!=null && showBlank==true){
			PubCode pubCode=new PubCode();
			pubCode.setItno("");
			pubCode.setItnm("所有");
			list.add(0,pubCode);
		}
		return list;
		
	}
	
	@RequestMapping("/pubCodeType/querySpseno4Ordmt.do")
	@ResponseBody
	public List<PubCode> querySpseno4Ordmt(String ormtno,String bradno,String spclno,Boolean showBlank) {
		// 默认是所有课访问品牌中的第一个品牌，和前端的品牌combobox要对应起来
		if (bradno == null) {
			bradno = ContextUtils.getFirstBradno();
		}
		
		if(ormtno==null || "".equals(ormtno)){
			Ordmt ordmt= ContextUtils.getFirstOrdmt();
			ormtno=ordmt.getOrmtno();
		}
		
		//return pubCodeRepository.querySpseno4Ordmt(ormtno, bradno,spclno);
		List<PubCode> list= pubCodeRepository.querySpseno4Ordmt(ormtno, bradno,spclno);
		if(showBlank!=null && showBlank==true){
			PubCode pubCode=new PubCode();
			pubCode.setItno("");
			pubCode.setItnm("所有");
			list.add(0,pubCode);
		}
		return list;
		
	}
	

	


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/pubCode/queryList.do")
	@ResponseBody
	public List<PubCode> queryList(String tyno,String fitno,String bradno){
		return pubCodeService.queryList(tyno,fitno,bradno);
	}

	@RequestMapping("/pubCode/query.do")
	@ResponseBody
	public List<PubCode> query(String tyno) {	
		List<PubCode> pubCodees=
				pubCodeService.query(Cnd.select().andEquals(M.PubCode.tyno, tyno)
				.asc(M.PubCode.itso));
		return pubCodees;
	}
	
	/**
	 * 更新状态的目的
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param tyno
	 * @param itno
	 * @param itst
	 * @param stat
	 * @return
	 */
	@RequestMapping("/pubCode/updateStat.do")
	@ResponseBody
	public  String updateStat(String bradno,String tyno,String itno,String stat) {
		//pubCodeService.update(Cnd.update().set(M.PubCode.itst, itst).set(M.PubCode.stat, stat).andEquals(M.PubCode.tyno, tyno).andEquals(M.PubCode.itno, itno));
		pubCodeService.updateStat(bradno, tyno, itno, stat);
		return "{success:true}";
	}
	

//	@RequestMapping("/pubCode/load.do")
//	public PubCode load(String id) {
//		return pubCodeService.get(id);
//	}
//	
//	@RequestMapping("/pubCode/create.do")
//	//@ResponseBody
//	public PubCode create(@RequestBody PubCode pubCode) {
//		pubCodeService.create(pubCode);
//		return pubCode;
//	}
//	
//	@RequestMapping("/pubCode/update.do")
//	//@ResponseBody
//	public  PubCode update(@RequestBody PubCode pubCode) {
//		pubCodeService.update(pubCode);
//		return pubCode;
//	}
//	
//	@RequestMapping("/pubCode/deleteById.do")
//	//@ResponseBody
//	public String deleteById(String id) {
//		pubCodeService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/pubCode/destroy.do")
//	//@ResponseBody
//	public PubCode destroy(@RequestBody PubCode pubCode) {
//		pubCodeService.delete(pubCode);
//		return pubCode;
//	}
	
	
}
