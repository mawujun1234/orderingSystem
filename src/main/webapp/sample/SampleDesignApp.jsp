<%@ page language="java" import="java.util.List,com.youngor.permission.Menu,com.youngor.permission.MenuService,com.mawujun.controller.spring.SpringContextHolder;" pageEncoding="UTF-8"%>
<%
String contextPath = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String requestURI=request.getRequestURI();
MenuService menuService=SpringContextHolder.getBean(MenuService.class);
List<Menu> elements=menuService.queryElement(requestURI.replaceAll("/"+contextPath+"/", "/"));
StringBuilder builder=new StringBuilder("{");
for(Menu menu:elements){
	builder.append(menu.getCode()+":true,");
}
String aa=builder.substring(0,builder.length()-1);
aa=aa+"}";

%>

<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta charset="UTF-8">
	<script>
		var Permision = {
    		elements:<%=aa%>,
   			canShow: function(code) {
   				if(!code){
   					alert("请输入界面元素的code!");
   					return;
   				}
				if(this.elements[code]){
					return true;
				} else {
					return false;
				}
			}
		}; 
	</script>
	
	<%@include file="../common/init.jsp" %>
	<script type="text/javascript" src="SampleDesignApp.js"></script>
  </head>
  
  <body>
   
  </body>
</html>