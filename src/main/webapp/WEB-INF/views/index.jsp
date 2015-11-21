<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=basePath %>resources/css/commons.css" rel="stylesheet">
<link href="<%=basePath %>resources/css/index.css" rel="stylesheet">
<link rel="shortcut icon" href="<%=basePath %>favicon.ico" type="image/x-icon" />
<script type="text/javascript"	src="<%=basePath %>resources/js/jquery-1.9.1.min.js"></script>

</head>

<body>
	welcome page !!!
</body>
</html>