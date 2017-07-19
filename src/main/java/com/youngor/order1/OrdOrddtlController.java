package com.youngor.order1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Pager;
import com.youngor.permission.ShiroUtils;
import com.youngor.sample.SampleProdVO;
import com.youngor.utils.M;
import com.youngor.utils.MapParams;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/ordOrddtl")
public class OrdOrddtlController {

	@Resource
	private OrdOrddtlService ordOrddtlService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/ordOrddtl/queryPager1.do")
	@ResponseBody
	public Pager<OrdOrddtlQuery> queryPager1(Pager<OrdOrddtlQuery> pager){
		pager.addParam("user_id", ShiroUtils.getUserId());
		return ordOrddtlService.queryPage1(pager);
	}
	
	@RequestMapping("/ordOrddtl/download.do")
	@ResponseBody
	public void download(MapParams params,HttpServletRequest request,HttpServletResponse response) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		
		XSSFWorkbook wb = new XSSFWorkbook();    
		Sheet sheet1 = wb.createSheet("报表");
		LinkedHashMap<String,String> titles=new LinkedHashMap<String,String>();
		
		titles.put("sampnm", "设计样衣编号");
		titles.put("sampnm1", "订货样衣编号");
		titles.put("suitno_name", "套件");
		titles.put("prodnm", "货号名称");
		titles.put("lasted_ordate", "最晚下单日期");
		titles.put("plan_indate", "计划入库日期");
		titles.put("ormtqt", "订货数量");
		titles.put("total_orodqt", "下单总量");
		titles.put("spclno_name", "大类");
		titles.put("sptyno_name", "小类");
		titles.put("spseno_name", "系列");
		titles.put("spbano_name", "上市月份");
		titles.put("spftpr", "出厂价");
		titles.put("sprtpr", "零售价");


		crreateTitle_export(wb,sheet1,titles);
		crreateData_export(wb,sheet1,titles,params);
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");    
        response.setHeader("Content-disposition", "attachment;filename="+new String("产品下单统计".getBytes(),"ISO8859-1")+".xlsx");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
        
		
	}
	
	private void crreateTitle_export(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles){
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
	    cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		
		Font font = wb.createFont();
	    //font.setFontHeightInPoints((short)18);
	    font.setFontName("Courier New");
	    cellStyle.setFont(font);
		 
		Row title = sheet1.createRow((short)0);
		
		int i=0;
		for(Entry<String,String> entry:titles.entrySet()){
			Cell cell = title.createCell(i);
			cell.setCellValue(entry.getValue());
			cell.setCellStyle(cellStyle);
			i++;
		}
		
	}
	private void crreateData_export(XSSFWorkbook wb,Sheet sheet1,LinkedHashMap<String,String> titles,MapParams params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		//params.getParams().put("user_id", ShiroUtils.getUserId());
		List<OrdOrddtlQuery> list=ordOrddtlService.queryPage1(params);
		if(list==null || list.size()==0){
			return;
		}
		for(int i=0;i<list.size();i++){
			OrdOrddtlQuery orderNumTotal=list.get(i);
			Row row = sheet1.createRow((short)i+1);
			int j=0;
			for(Entry<String,String> entry:titles.entrySet()){
				Cell cell = row.createCell(j);
				j++;
				String get_name="get"+StringUtils.capitalize(entry.getKey());
				System.out.println(get_name+":"+orderNumTotal.getSampnm());
				Object value=ReflectionUtils.findMethod(SampleProdVO.class, get_name).invoke(orderNumTotal);
				if(value!=null){
					cell.setCellValue(value.toString());
				}
			}
		}
	}

	@RequestMapping("/ordOrddtl/queryAll.do")
	@ResponseBody
	public List<OrdOrddtl> queryAll(String ormtno,String sampno,String suitno) {	
		List<OrdOrddtl> ordOrddtles=ordOrddtlService.query(Cnd.select()
				.andEquals(M.OrdOrddtl.ormtno, ormtno)
				.andEquals(M.OrdOrddtl.sampno, sampno)
				.andEquals(M.OrdOrddtl.suitno, suitno).desc(M.OrdOrddtl.ordseq)
				);
		return ordOrddtles;
	}
	

//	@RequestMapping("/ordOrddtl/load.do")
//	@ResponseBody
//	public OrdOrddtl load(com.youngor.order1.OrdOrddtl.PK id) {
//		return ordOrddtlService.get(id);
//	}
//	
	@RequestMapping("/ordOrddtl/create.do")
	@ResponseBody
	public OrdOrddtl create(@RequestBody OrdOrddtl ordOrddtl) {
		ordOrddtl.setRgsp(ShiroUtils.getUserId());
		ordOrddtl.setRgdt(new Date());
		ordOrddtl.setLmsp(ShiroUtils.getUserId());
		ordOrddtl.setLmdt(new Date());
		//获取序列号
		int aa=ordOrddtlService.queryCount(Cnd.count()
				.andEquals(M.OrdOrddtl.ormtno, ordOrddtl.getOrmtno())
				.andEquals(M.OrdOrddtl.sampno, ordOrddtl.getSampno())
				.andEquals(M.OrdOrddtl.suitno, ordOrddtl.getSuitno())
				);
		ordOrddtl.setOrdseq(aa+1);
		ordOrddtlService.create(ordOrddtl);
		return ordOrddtl;
	}
	
	@RequestMapping("/ordOrddtl/update.do")
	@ResponseBody
	public  OrdOrddtl update(@RequestBody OrdOrddtl ordOrddtl) {
		ordOrddtl.setLmsp(ShiroUtils.getUserId());
		ordOrddtl.setLmdt(new Date());
		ordOrddtlService.update(ordOrddtl);
		return ordOrddtl;
	}
	
	@RequestMapping("/ordOrddtl/deleteById.do")
	@ResponseBody
	public com.youngor.order1.OrdOrddtl.PK deleteById(com.youngor.order1.OrdOrddtl.PK id) {
		ordOrddtlService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/ordOrddtl/destroy.do")
	@ResponseBody
	public OrdOrddtl destroy(@RequestBody OrdOrddtl ordOrddtl) {
		ordOrddtlService.delete(ordOrddtl);
		return ordOrddtl;
	}
	

	@RequestMapping("/ordOrddtl/triggerOA.do")
	@ResponseBody
	public String triggerOA(String ormtno,String sqr,String ordate) throws UnsupportedOperationException, IOException {
		String httpUrl = "http://192.168.188.96/redev/dh/DHData.jsp";
		List<AAAA> list=ordOrddtlService.querymx(ormtno, ordate);
		if(list==null || list.size()==0){
			return "{success:false,msg:'该下单日期没有单据!'}";
		}
		String aaa=JSONArray.toJSONString(list);
		System.out.println(aaa);
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(httpUrl);
		httppost.addHeader("Connection", "Keep-Alive");

		//说明：xl：小类,xlie:系列, pzgs:品种个数, cpsl:数量, ccje:出厂金额, lsje:零售金额, zbje:指标金额, bz:备注, dlid :大类代码
		//如果用户选择单独 类型，在备注字段中标出类型；
		//OA 流程表：SELECT * from FORMTABLE_MAIN_313@OA
		//SELECT * FROM FORMTABLE_MAIN_313_DT1@OA
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("json", "{sqr:'"+sqr+"',dhph:'"+ormtno+"',mx:"+aaa+"}"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		
		CloseableHttpResponse response = httpclient.execute(httppost);
		
		StringBuilder builder=new StringBuilder();
		try {
			//System.out.println(response.getStatusLine());
		    HttpEntity entity = response.getEntity();
		    //InputStream inputStream=entity.getContent();
		    
		    ContentType contentType = ContentType.getOrDefault(entity);
	        Charset charset = contentType.getCharset();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), charset));
	        
	       
	        String s;
	        while((s=reader.readLine())!=null && s.length()!=0){
	          builder.append(s);
	        }
	        System.out.println(builder);
	        
	       
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    EntityUtils.consume(entity);
		} finally {
		    response.close();
		}


		//HttpClient client = new HttpClient();
//		String result = "";
//		PostMethod postMethod = new PostMethod(httpUrl);
//		// 设置最大连接超时时间
//		client.getHttpConnectionManager().getParams().setConnectionTimeout(8000);
//		postMethod.setParameter("Connection", "Keep-Alive");
//		postMethod.getParams().setContentCharset("UTF-8");
//
//		List<Part> parts = new ArrayList<Part>();// 封装Form表单
//
//		NameValuePair simcard = new NameValuePair(
//				"json",
//				"{sqr:'fs02556',dhph:'201308',mx:[{xl:'1',xlie:'1', pzgs:'1', cpsl:'1', ccje:'1', lsje:'1', zbje:'1', bz:'1', dlid :'1'},{xl:'1',xlie:'1', pzgs:'1', cpsl:'1', ccje:'1', lsje:'1', zbje:'1', bz:'1', dlid :'1'}]}");
//
//		postMethod.setRequestBody(new NameValuePair[]{simcard});
//
//		int httpStat = client.executeMethod(postMethod);
//
//		String responseCode = postMethod.getResponseBodyAsString();
//		// System.out.println(responseCode);
//		if (httpStat == HttpStatus.SC_OK) {
//			result = responseCode;
//		} else {
//			result = "1";
//		}
//		postMethod.releaseConnection();
		
		return "{success:true}";

	}
}
