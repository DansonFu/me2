<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> 你好</title>
</head>
<body>
${name }

 <form action="<%=basePath%>products/info" method="post" enctype="multipart/form-data">
         <input type="text" name="pid"/> 
         <input type="text" name="pname"/> 
         <input type="file" name="file"/>
         <input type="submit"/>
    </form>
    
</body>
</html>