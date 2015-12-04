<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<link rel="stylesheet" href="<%=basePath %>resources/assets/css/jquery-ui-1.10.3.full.min.css" />
		<link rel="stylesheet" href="<%=basePath %>resources/assets/css/jquery-ui-timepicker-addon.css" />
		<link rel="stylesheet" href="<%=basePath %>resources/assets/css/ui.jqgrid.css" />
		<jsp:include page="../script.jsp"></jsp:include>
	</head>

	<body>
					<div class="page-content">

						<div class="row">
							<div class="col-xs-12">
								<div class="alert alert-info">
									<i class="icon-hand-right"></i>
									在此你可以对用户信息进行编辑！
									<button class="close" data-dismiss="alert">
										<i class="icon-remove"></i>
									</button>
								</div>
								<table id="grid-table"></table>

								<div id="grid-pager"></div>
								
								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
		<script src="<%=basePath %>resources/assets/js/jquery-ui-1.10.3.full.min.js"></script>
		<script src="<%=basePath %>resources/assets/js/date-time/jquery-ui-timepicker-addon.js"></script>
		<script src="<%=basePath %>resources/assets/js/date-time/jquery.ui.datepicker-zh-CN.js"></script>
		<script src="<%=basePath %>resources/assets/js/date-time/jquery.ui.timepicker-zh-CN.js"></script>
		<script src="<%=basePath %>resources/assets/js/jqGrid/jquery.jqGrid.min.js"></script>
		<script src="<%=basePath %>resources/assets/js/jqGrid/i18n/grid.locale-cn.js"></script>
		<script src="<%=basePath %>resources/assets/js/business/system/userInfo.js"></script>
	</body>
</html>