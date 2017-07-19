<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta charset="UTF-8">


	<%@include file="../common/init.jsp" %>
	
	<script type="text/javascript" src="login.js"></script>
	<script>

	</script>
	
	<style>
	 .ormtno_name {
	 	text-align:center;
		position:absolute;
		top:30%;
		left:50%;
		margin-left:-190px;
		color:white;
		font-family:sans-serif,"幼圆","黑体";
		font-size:45px;
	 }
	</style>
  </head>
  
  <body style="background-image: url(bg.jpg);background-position:center;background-repeat:no-repeat;background-attachment:fixed;">
   <div class="ormtno_name">
   	2018春夏商品订货会
   </div>
  </body>
</html>