<%@ page  isELIgnored="false" language="java" pageEncoding="UTF-8" %>
<%
String extjscontextPath=request.getContextPath();
//String ip=request.getRemoteAddr();
//if("localhost".equals(ip) ||"127.0.0.1".equals(ip) || ip.startsWith("192.168.")){
//	
//} else {
	//http://extj1234.duapp.com
	//主要用于发布到到百多的开发者平台的时候使用的，这个时候war包就不会把extjs的内容打包进去了
	//extjscontextPath="http://extj1234.duapp.com";
//}

%>

    <!-- <link rel="stylesheet" type="text/css" href="./ext6/build/classic/theme-neptune/resources/theme-neptune-all.css">
    <script type="text/javascript" src="./ext6/build/classic/theme-neptune/theme-neptune.js"></script>
    <link rel="stylesheet" type="text/css" href="./ext6/build/classic/theme-triton/resources/theme-triton-all.css">
    <script type="text/javascript" src="./ext6/build/classic/theme-neptune/theme-triton.js"></script>-->
    
    
    
     <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext6/build/classic/theme-crisp/resources/theme-crisp-all.css">
   
        <script type="text/javascript" src="<%=request.getContextPath()%>/ext6/build/ext-all-debug.js"></script>
         <script type="text/javascript" src="<%=request.getContextPath()%>/ext6/build/classic/theme-crisp/theme-crisp.js"></script>
        
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome.css">

    
<style>

</style>
<%


%>


<script type="text/javascript">

Ext.Loader.setConfig({
	enabled: true,
	paths:{
		'y':'../',
		//'y':'.',
		'Ext.ux':'../ext6/packages/ux/classic/src'
		//'MyDesktop':'.'
	}
});
Ext.setGlyphFontFamily('FontAwesome');
Ext.required='<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
Ext.ContextPath="<%=request.getContextPath()%>";
</script>

