<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'login.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	function reloadCode(){
	var time=new Date().getTime();
	document.getElementById("codeImage").src="<%=request.getContextPath()%>/ValidateCode.do?d="+time;
	}
	
	</script>
  </head>
  
  <body>
    <span style="color:red">${msg}</span>
    <form action="login.do" method="post">
   	 账      号：<input type="text" name ="user_tel"></br>
	 密      码：<input type="password" name = "user_password"></br>
	 验证码：<input type="text" name ="code">
	 <a href="javascript:reloadCode();">看不清楚</a><br/></br>
	 <img id="codeImage" src="<%=request.getContextPath()%>/ValidateCode.do"></br>
	 <input type="submit" value="登录">
    </form>
 	
  </body>
</html>
