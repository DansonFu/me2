<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>蜜图管理台</title>
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<!-- basic styles -->
		<link href="<%=basePath %>resources/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="<%=basePath %>resources/assets/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="<%=basePath %>resources/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<!-- fonts -->

		<!-- ace styles -->

		<link rel="stylesheet" href="<%=basePath %>resources/assets/css/ace.min.css" />
		<link rel="stylesheet" href="<%=basePath %>resources/assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="<%=basePath %>resources/assets/css/ace-skins.min.css" />
		<link rel="stylesheet" href="<%=basePath %>resources/assets/css/chosen.css" />
		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="<%=basePath %>resources/assets/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="<%=basePath %>resources/assets/js/html5shiv.js"></script>
		<script src="<%=basePath %>resources/assets/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body>
		<div class="page-content">
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 选择发布用户</label>
					<div class="col-sm-3">
						<select id="form-field-select-3" >
							<c:forEach items="${customers}" var="customer" varStatus="">
								<option value="${customer.customerId }">${customer.username }</option>
							</c:forEach>
						</select>
					</div>
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> A面</label>
					<div class="col-sm-3">
						<input type="file" />
					</div>
				</div>

				<div class="space-4"></div>



				<div class="clearfix form-actions">
					<div class="col-md-offset-3 col-md-9">
						<button class="btn btn-info" type="button" onclick="submitform()">
							<i class="icon-ok bigger-110"></i>
							提交
						</button>

						&nbsp; &nbsp; &nbsp;
						<button class="btn" type="reset">
							<i class="icon-undo bigger-110"></i>
							取消
						</button>
					</div>
				</div>

			</form>
		</div>
		
		<script src="<%=basePath %>resources/assets/js/jquery.min.js"></script>
		<script src="<%=basePath %>resources/assets/js/bootstrap.min.js"></script>
		<script src="<%=basePath %>resources/assets/js/typeahead-bs2.min.js"></script>
		<script src="<%=basePath %>resources/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="<%=basePath %>resources/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="<%=basePath %>resources/assets/js/jquery.slimscroll.min.js"></script>
		<script src="<%=basePath %>resources/assets/js/jquery.easy-pie-chart.min.js"></script>
		<script src="<%=basePath %>resources/assets/js/jquery.sparkline.min.js"></script>
		<script src="<%=basePath %>resources/assets/js/flot/jquery.flot.min.js"></script>
		<script src="<%=basePath %>resources/assets/js/flot/jquery.flot.pie.min.js"></script>
		<script src="<%=basePath %>resources/assets/js/flot/jquery.flot.resize.min.js"></script>

		<!-- ace scripts -->

		<script src="<%=basePath %>resources/assets/js/ace-elements.min.js"></script>
		<script src="<%=basePath %>resources/assets/js/ace.min.js"></script>		
		
<script type="text/javascript">
	function submitform(){
		alert(getToken());
	}
	function getToken(){
		var token;
		$.ajax({
			async:false,
			url:"qiniutoken/simple/a.json",
			type:"get",
			contentType:"application/json",
			datatype:"json",
			success:function(data){
				token = data.obj;
			}
		});
		return token;
	}
	function uploadPicture2qiniu(){
		
	}
</script>		
	</body>
</html>

