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
		<title>推荐蜜图管理</title>
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
		<link rel="stylesheet" href="<%=basePath %>resources/assets/css/global.css">
		<link rel="stylesheet" href="<%=basePath %>resources/assets/css/common.css">
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
			
			 <div class="panel panel-default">
                    <div class="panel-body">
                    	<form class="form-bordered"  id="me2form" action="<%=basePath %>admin/updatecommendmetoo" method="post" enctype="multipart/form-data">
                                <div class="col-lg-6" id="bfeelDiv">
                                    <div class="col-lg-3 input-column" >
                                       	<span class="dangger" style="color: red">*</span> 蜜图A面：
                                    </div>
                                    <div class="col-lg-6" style="height: 120px">
                                        <img  src="${domain}/${pct.picture.qiniukey}" width="100px" height="100px"/>
                                    </div>
                                    <div class="col-lg-3 input-column">
                                       	排序：
                                    </div>
                                    <div class="col-lg-9">
										<input type="text" name="bmood" style="width:80%;" value="${pct.sort}"/>
                                    </div>
                                    <div class="col-lg-3 input-column">
                                       	有效期：
                                    </div>
                                    <div class="col-lg-9">
										<input type="text" name="bmood" style="width:80%;" value="${pct.period}"/>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                            </div>
                            <!-- row -->
                           
                    </div>
                    <!-- panel-body -->
                </div>
			
		
		 <br>
               <div  style="text-align: center">
				<div >
					<button class="btn btn-primary" type="button" onclick="submitform()">
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

		if($("input[name='bmood']").val()==null||$("input[name='bmood']").val()==""){
			$("input[name='bmood']").focus();
			return;
		}

		$("#me2form").submit();
		$(":button").attr("disabled", true);  
	}

</script>		
	</body>
</html>

