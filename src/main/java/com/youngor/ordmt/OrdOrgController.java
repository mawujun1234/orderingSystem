package com.youngor.ordmt;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.mawujun.utils.page.Pager;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/ordOrg")
public class OrdOrgController {

	@Resource
	private OrdOrgService ordOrgService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/ordOrg/queryPager.do")
	@ResponseBody
	public Pager<OrdOrg> queryPager(Pager<OrdOrg> pager){
		return ordOrgService.queryPage(pager);
	}

	@RequestMapping("/ordOrg/getOrdOrgByOrg.do")
	@ResponseBody
	public OrdOrg getOrdOrgByOrg(String ormtno,String orgno) {	
		OrdOrg ordOrg=ordOrgService.getOrdOrgByOrg(ormtno,orgno);
		if(ordOrg==null){
			return new OrdOrg();
		}
		return ordOrg;
	}
	@RequestMapping("/ordOrg/print.do")
	//@ResponseBody
	public void print(HttpServletRequest request,HttpServletResponse response,String ormtno,String[] ordorgs) throws WriterException, IOException{
		String contextPath=WebUtils.getRealPath(request.getServletContext(), "/");
		String filePath=contextPath+"qrcode_temp";
		//String filePath = "D://";  
         
//        JSONObject json = new JSONObject();  
//        json.put(  
//                "zxing",  
//                "https://github.com/zxing/zxing/tree/zxing-3.0.0/javase/src/main/java/com/google/zxing");  
//        json.put("author", "shihy");  
       // String content = json.toJSONString();// 内容  
        int width = 200; // 图像宽度  
        int height = 200; // 图像高度  
        String format = "png";// 图像类型  
        
        List<OrdOrg> list=ordOrgService.queryForPrint(ormtno, ordorgs);
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
        for(OrdOrg ordOrg:list){
        	String fileName = ordOrg.getOrdorg()+".png"; 
        	 BitMatrix bitMatrix = new MultiFormatWriter().encode(ordOrg.getLoginname()+"+##+"+ordOrg.getLoginname(),BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵  
             Path path = FileSystems.getDefault().getPath(filePath, fileName);  
             MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像  
             System.out.println("输出成功.");  
        }
       
       
        
        
        
        
        String TEMPLATE_FILE_NAME = request.getSession().getServletContext()
 		       .getRealPath("/ordmt/template/template2.html");
 		
 		String templateHtmlStr = "";

 	    BufferedReader bfdreader=null;
 	    InputStreamReader read = null;
 	     
 		try{
 			     File fp=new File(TEMPLATE_FILE_NAME);
 			     read = new InputStreamReader(new FileInputStream(fp),"UTF-8");
 			     bfdreader=new BufferedReader(read);

 			     String str_line=bfdreader.readLine();
 			     while(str_line!=null) {
 			    	templateHtmlStr = templateHtmlStr + str_line;
 			        str_line=bfdreader.readLine();
 		         }
 			     
 		   }catch(IOException e) {
 				  throw new IOException(" 模板读取错误！");
 	    }finally
 	    {
 	    	 bfdreader.close();
 		     read.close();
 	    }
 		//读取要打印的组织单元的数量
 		
 		String outPutString = "";
		
		int template_cols = this.getSubStrCount(templateHtmlStr, "name='div_diaop_area'");
	    
	    
	    int recordCount = list.size();
	    int index = 0;
	    
	    while(index < recordCount){
	    	
	    	String rowString = templateHtmlStr;
	    	for(int i=0;i<template_cols;i++){
	    		
	    		if(index >= recordCount){
	    			break;
	    		}
	    		
	    		OrdOrg record = list.get(index);
	    		index ++;
	    		
	    		//Object mim = record.getObject("MIM");
	    	    String quanc = record.getOrgnm();
	    	    String fileName = record.getOrdorg()+".png"; 
	    	    if(quanc.length() > 9){
	    	    	quanc = quanc.substring(0, 9);
	    	    }
	    		//rowString =rowString.replaceAll("\\{DAIM_MIM_" + i + "\\}", record.getString("DAIM")+","+record.getObject("MIM"));
	    		rowString =rowString.replaceAll("\\{QUANC_" + i + "\\}", quanc);
	    		rowString =rowString.replaceAll("\\{DAIM_" + i + "\\}", record.getLoginname());
	    		rowString =rowString.replaceAll("\\{fileName_" + i + "\\}", fileName);
	    	}
	    	
	        outPutString = outPutString + rowString;
	    }
	    
	    //更新打印状态
	    ordOrgService.updatePrint(list);
		//response.reset();
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE>");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE></TITLE></HEAD>");
		out.println("  <BODY style='text-align:center'>");
		out.println(outPutString);
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
	//计算 subStr在str中出现的次数 
	private int getSubStrCount( String  str,String  subStr)   
	{ 
		int   subStrLen   =   subStr.length(); 
		int   strLen   =   str.length(); 
		strLen   =   strLen   -   str.replace(subStr,   "").length(); 
		return   strLen   /   subStrLen; 
	}
//	
//
//	@RequestMapping("/ordOrg/load.do")
//	@ResponseBody
//	public OrdOrg load(com.youngor.ordmt.OrdOrg.PK id) {
//		return ordOrgService.get(id);
//	}
	
	@RequestMapping("/ordOrg/create.do")
	@ResponseBody
	public String create(String addModel,String ordorg,Integer sztype,String role_id) {
		 ordOrgService.create(addModel, ordorg, sztype, role_id);
		 return "{success:true}";
	}
	
	@RequestMapping("/ordOrg/update.do")
	@ResponseBody
	public  OrdOrg update(@RequestBody OrdOrg ordOrg) {

		ordOrgService.update(ordOrg);
		return ordOrg;
	}
	
	@RequestMapping("/ordOrg/deleteById.do")
	@ResponseBody
	public com.youngor.ordmt.OrdOrg.PK deleteById(com.youngor.ordmt.OrdOrg.PK id) {
		ordOrgService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/ordOrg/destroy.do")
	@ResponseBody
	public OrdOrg destroy(@RequestBody OrdOrg ordOrg) {
		ordOrgService.destroy(ordOrg);
		return ordOrg;
	}
	
	
}
