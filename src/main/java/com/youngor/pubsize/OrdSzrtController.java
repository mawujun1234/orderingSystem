package com.youngor.pubsize;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youngor.pubcode.PubCodeCache;
import com.youngor.utils.MapParams;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/ordSzrt")
public class OrdSzrtController {

	@Resource
	private OrdSzrtService ordSzrtService;
	@Resource
	private OrdSzrtRepository ordSzrtRepository;

	/**
	 * 	
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param sizegp 规格范围
	 * @return
	 */
	@RequestMapping("/ordSzrt/queryColumns.do")
	@ResponseBody
	public Map<String,List<PubSize>> queryColumns(String sizegp){
		Map<String,List<PubSize>> result=new HashMap<String,List<PubSize>>();
		result.put("szrtColumns", ordSzrtRepository.queryStdszColumns(sizegp));
		result.put("prdpkColumns", ordSzrtRepository.queryPrdpkColumns(sizegp));
		return result;
	}
	/**
	 * 查询规格比例数据
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param sizegp
	 * @return
	 */
	@RequestMapping("/ordSzrt/querySzrtData.do")
	@ResponseBody
	public List<Map<String,Object>> querySzrtData(String ordorg,String ormtno,String bradno,String spclno,String spseno,String versno,String sizegp,String sizety) {
		//组装成页面需要的格式
		List<Map<String,Object>> szrtData_a=ordSzrtRepository.querySzrtData_a(ordorg, ormtno, bradno, spclno, spseno, versno, sizegp);
		
		
		//注意规格代码要填写进去
		List<OrdSzrt> szrtData_b=ordSzrtRepository.querySzrtData_b(ordorg, ormtno, bradno, spclno, spseno, versno, sizegp,sizety);
		for(Map<String,Object> map:szrtData_a){
			map.put("SPSENO_NAME", PubCodeCache.getSpseno_name((String)map.get("SPSENO")));
			map.put("VERSNO_NAME", PubCodeCache.getVersno_name((String)map.get("VERSNO")));
			
			for(OrdSzrt ordSzrt:szrtData_b){
				if(ordSzrt.getSpseno().equals(map.get("SPSENO"))){
					//System.out.println(ordSzrt.getVersno());
					//System.out.println(map.get("VERSNO"));
					if(ordSzrt.getVersno()==null && map.get("VERSNO")==null){//select * from ord_ord_szrt a
						System.out.println(map.get("VERSNO"));
						map.put(ordSzrt.getSizeno(), ordSzrt.getSzrate());
					} else if(ordSzrt.getVersno().equals((map.get("VERSNO")))){
						map.put(ordSzrt.getSizeno(), ordSzrt.getSzrate());
					}
					
				}

			}
		}
		return szrtData_a;
	}
	
	@RequestMapping("/ordSzrt/create.do")
	@ResponseBody
	public void create(OrdSzrt ordSzrt) {
		//先把组织单元数据加上去
		//System.out.println(111);
		ordSzrtService.create(ordSzrt);
		
	}
	@RequestMapping("/ordSzrt/querySaleHisGrid.do")
	@ResponseBody
	public Map<String,Object> querySaleHisGrid(MapParams params) {
		return ordSzrtService.querySaleHisGrid(params.getParams());
	}

//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/ordSzrt/query.do")
//	@ResponseBody
//	public Pager<OrdSzrt> query(Pager<OrdSzrt> pager){
//		return ordSzrtService.queryPage(pager);
//	}
//
//	@RequestMapping("/ordSzrt/queryAll.do")
//	@ResponseBody
//	public List<OrdSzrt> queryAll() {	
//		List<OrdSzrt> ordSzrtes=ordSzrtService.queryAll();
//		return ordSzrtes;
//	}
//	
//
//	@RequestMapping("/ordSzrt/load.do")
//	@ResponseBody
//	public OrdSzrt load(String id) {
//		return ordSzrtService.get(id);
//	}
//	
//
//	
//	@RequestMapping("/ordSzrt/update.do")
//	@ResponseBody
//	public  OrdSzrt update(@RequestBody OrdSzrt ordSzrt) {
//		ordSzrtService.update(ordSzrt);
//		return ordSzrt;
//	}
//	
//	@RequestMapping("/ordSzrt/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		ordSzrtService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/ordSzrt/destroy.do")
//	@ResponseBody
//	public OrdSzrt destroy(@RequestBody OrdSzrt ordSzrt) {
//		ordSzrtService.delete(ordSzrt);
//		return ordSzrt;
//	}
	
	
}
