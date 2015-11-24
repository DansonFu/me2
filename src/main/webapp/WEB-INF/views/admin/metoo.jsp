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
			<form class="form-horizontal" role="form" id="me2form" action="<%=basePath %>admin/savemetoo" method="post" enctype="multipart/form-data">

				<div class="table-responsive">
					<table id="sample-table-1" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th width="10%">用户</th>
								<th width="10%">A面</th>
								<th width="20%" class="hidden-480">标签(英文逗号分隔)</th>
								<th width="30%">心情</th>
								<th width="10%">B面</th>
								<th width="10%" class="hidden-480">解蜜</th>
							</tr>
						</thead>

						<tbody>
							<tr>
								<td>
									<select name="username" >
										<c:forEach items="${customers}" var="customer" varStatus="">
											<option value="${customer.customerId }">${customer.username }</option>
										</c:forEach>
									</select>
								</td>
								<td><input type="file" name="afile"/></td>
								<td class="hidden-480"><input type="text" name="tags" size="50"/></td>
								<td><input type="text" name="feel" size="60"/></td>
								<td><input type="file" name="bfile"/></td>
								<td class="hidden-480">
									<select name="gameid" >
										<c:forEach items="${games}" var="game" varStatus="">
											<option value="${game.gameId }">${game.name }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
													<tr>
								<td>
									<select name="username" >
										<c:forEach items="${customers}" var="customer" varStatus="">
											<option value="${customer.customerId }">${customer.username }</option>
										</c:forEach>
									</select>
								</td>
								<td><input type="file" name="afile"/></td>
								<td class="hidden-480"><input type="text" name="tags" size="50"/></td>
								<td><input type="text" name="feel" size="60"/></td>
								<td><input type="file" name="bfile"/></td>
								<td class="hidden-480">
									<select name="gameid" >
										<c:forEach items="${games}" var="game" varStatus="">
											<option value="${game.gameId }">${game.name }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
														<tr>
								<td>
									<select name="username" >
										<c:forEach items="${customers}" var="customer" varStatus="">
											<option value="${customer.customerId }">${customer.username }</option>
										</c:forEach>
									</select>
								</td>
								<td><input type="file" name="afile"/></td>
								<td class="hidden-480"><input type="text" name="tags" size="50"/></td>
								<td><input type="text" name="feel" size="60"/></td>
								<td><input type="file" name="bfile"/></td>
								<td class="hidden-480">
									<select name="gameid" >
										<c:forEach items="${games}" var="game" varStatus="">
											<option value="${game.gameId }">${game.name }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td>
									<select name="username" >
										<c:forEach items="${customers}" var="customer" varStatus="">
											<option value="${customer.customerId }">${customer.username }</option>
										</c:forEach>
									</select>
								</td>
								<td><input type="file" name="afile"/></td>
								<td class="hidden-480"><input type="text" name="tags" size="50"/></td>
								<td><input type="text" name="feel" size="60"/></td>
								<td><input type="file" name="bfile"/></td>
								<td class="hidden-480">
									<select name="gameid" >
										<c:forEach items="${games}" var="game" varStatus="">
											<option value="${game.gameId }">${game.name }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
														<tr>
								<td>
									<select name="username" >
										<c:forEach items="${customers}" var="customer" varStatus="">
											<option value="${customer.customerId }">${customer.username }</option>
										</c:forEach>
									</select>
								</td>
								<td><input type="file" name="afile"/></td>
								<td class="hidden-480"><input type="text" name="tags" size="50"/></td>
								<td><input type="text" name="feel" size="60"/></td>
								<td><input type="file" name="bfile"/></td>
								<td class="hidden-480">
									<select name="gameid" >
										<c:forEach items="${games}" var="game" varStatus="">
											<option value="${game.gameId }">${game.name }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
														<tr>
								<td>
									<select name="username" >
										<c:forEach items="${customers}" var="customer" varStatus="">
											<option value="${customer.customerId }">${customer.username }</option>
										</c:forEach>
									</select>
								</td>
								<td><input type="file" name="afile"/></td>
								<td class="hidden-480"><input type="text" name="tags" size="50"/></td>
								<td><input type="text" name="feel" size="60"/></td>
								<td><input type="file" name="bfile"/></td>
								<td class="hidden-480">
									<select name="gameid" >
										<c:forEach items="${games}" var="game" varStatus="">
											<option value="${game.gameId }">${game.name }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
														<tr>
								<td>
									<select name="username" >
										<c:forEach items="${customers}" var="customer" varStatus="">
											<option value="${customer.customerId }">${customer.username }</option>
										</c:forEach>
									</select>
								</td>
								<td><input type="file" name="afile"/></td>
								<td class="hidden-480"><input type="text" name="tags" size="50"/></td>
								<td><input type="text" name="feel" size="60"/></td>
								<td><input type="file" name="bfile"/></td>
								<td class="hidden-480">
									<select name="gameid" >
										<c:forEach items="${games}" var="game" varStatus="">
											<option value="${game.gameId }">${game.name }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
														<tr>
								<td>
									<select name="username" >
										<c:forEach items="${customers}" var="customer" varStatus="">
											<option value="${customer.customerId }">${customer.username }</option>
										</c:forEach>
									</select>
								</td>
								<td><input type="file" name="afile"/></td>
								<td class="hidden-480"><input type="text" name="tags" size="50"/></td>
								<td><input type="text" name="feel" size="60"/></td>
								<td><input type="file" name="bfile"/></td>
								<td class="hidden-480">
									<select name="gameid" >
										<c:forEach items="${games}" var="game" varStatus="">
											<option value="${game.gameId }">${game.name }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
														<tr>
								<td>
									<select name="username" >
										<c:forEach items="${customers}" var="customer" varStatus="">
											<option value="${customer.customerId }">${customer.username }</option>
										</c:forEach>
									</select>
								</td>
								<td><input type="file" name="afile"/></td>
								<td class="hidden-480"><input type="text" name="tags" size="50"/></td>
								<td><input type="text" name="feel" size="60"/></td>
								<td><input type="file" name="bfile"/></td>
								<td class="hidden-480">
									<select name="gameid" >
										<c:forEach items="${games}" var="game" varStatus="">
											<option value="${game.gameId }">${game.name }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
														<tr>
								<td>
									<select name="username" >
										<c:forEach items="${customers}" var="customer" varStatus="">
											<option value="${customer.customerId }">${customer.username }</option>
										</c:forEach>
									</select>
								</td>
								<td><input type="file" name="afile"/></td>
								<td class="hidden-480"><input type="text" name="tags" size="50"/></td>
								<td><input type="text" name="feel" size="60"/></td>
								<td><input type="file" name="bfile"/></td>
								<td class="hidden-480">
									<select name="gameid" >
										<c:forEach items="${games}" var="game" varStatus="">
											<option value="${game.gameId }">${game.name }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</tbody>
					</table>
				</div><!-- /.table-responsive -->
										
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
		<script src="<%=basePath %>resources/assets/js/jquery.form.js"></script>
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
		$("#me2form").submit();
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

</script>		
	</body>
</html>

