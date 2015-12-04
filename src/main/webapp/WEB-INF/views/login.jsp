<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${systemTitle }</title>
<!-- 避免IE使用兼容模式 -->
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- <meta name="viewport" content="width=device-width, initial-scale=1.0" /> -->
<meta name="viewport" content="width=device-width,target-densitydpi=high-dpi,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0"/>
<!-- 启用360浏览器的极速模式(webkit) -->
    	<meta name="renderer" content="webkit">
    	<!-- UC应用模式 -->
    <meta name="browsermode" content="application">
    <!-- QQ应用模式 -->
    <meta name="x5-page-mode" content="app">
<!-- basic styles -->
<link rel="stylesheet" href="<%=basePath %>resources/assets/js/jQuery.fDialog.v1.0/css/fDialog.css" />
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

<!--[if lte IE 8]>
		  <link rel="stylesheet" href="<%=basePath %>resources/assets/css/ace-ie.min.css" />
		<![endif]-->

<!-- inline styles related to this page -->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

<!--[if lt IE 9]>
		<script src="<%=basePath %>resources/assets/js/html5shiv.js"></script>
		<script src="<%=basePath %>resources/assets/js/respond.min.js"></script>
		<![endif]-->
		
<script type="text/javascript">
function login(){
	var name=$("#userName").val();
	var pwd=$("#password").val();
	var vc=$("#verifyCode").val();
	if(name==null||name==""){
		$("#errorMessage").text("用户名不得为空");
		return false;
	}
	if(pwd==null||pwd==""){
		$("#errorMessage").text("密码不得为空");
		return false;
	}
	if(vc==null||vc==""){
		$("#errorMessage").text("验证码不得为空");
		return false;
	}
	$.ajax({
		type:"post",
		url:"<%=basePath %>toLogin/login",
		dataType:"text",
		data:{userName:name,password:pwd,verifyCodeClient:vc},
		success:function(data,textStatus){
			if(data=="success"){
				location.href="<%=basePath %>admin/index";
			}else{
				$("#errorMessage").text(data);
				changeVerify();
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			fDialogAlert("登录失败,请联系管理员!");
		}
	})
	return false
}

function changeVerify(){
	document.getElementById("verifyCodeImage").src="<%=basePath %>toLogin/getVerifyCodeImage?random="+Math.random();
}	

function toTop(){
	if(window.top != window.self){
		window.top.location =  window.self.location;
	}
}

window.onload = toTop;
</script>
</head>
<body class="login-layout">
	<div class="main-container">
		<div class="main-content">
			<div class="row">
				<div class="col-sm-10 col-sm-offset-1">
					<div class="login-container">
						<div class="center">
							<h1>
								<i class="icon-leaf green"></i><span
									class="white">${systemTitle }</span>
							</h1>
							<h4 class="blue"></h4>
						</div>

						<div class="space-6"></div>
						<div class="space-6"></div>
						<div class="space-6"></div>
						<div class="space-6"></div>

						<div class="position-relative">
							<div id="login-box"
								class="login-box visible widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header blue lighter bigger">
											<i class="icon-coffee green"></i> 请输入你的登录信息
										</h4>

										<div class="space-6"></div>
										<div class="space-6"></div>

										<form id="loginForm">
											<fieldset>
												<label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="text" class="form-control" placeholder="用户名" id="userName" name="userName"/>
														<i class="icon-user"></i>
												</span>
												</label> <label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="password" class="form-control"
														placeholder="密码" id="password" name="password"/> <i class="icon-lock"></i>
												</span>
												</label>
												<label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="text" class="form-control" placeholder="验证码" id="verifyCode" name="verifyCode"/>
														<i class="icon-info-sign"></i>
												</span>
												</label>

												<div class="space"></div>
												<div class="space"></div>

												<div class="clearfix">
													<!-- <label class="inline"> <input type="checkbox"
														class="ace" /> <span class="lbl">记住我</span>
													</label> -->

													<button type="submit"
														class="width-35 pull-left btn btn-sm btn-primary"
														onclick="javascript:return login()" id="loginButton">
														<i class="icon-key"></i> 登录
													</button>
													
													<a title="单击换一张" class="pull-right" href="javascript:changeVerify();"><img alt="加载中。。。"  id="verifyCodeImage" src="<%=basePath %>toLogin/getVerifyCodeImage"
													height="35px"></a>
												</div>
												<div class="space-4"></div>
												<label class="block clearfix">
													<span id="errorMessage" class="red bigger-120"></span>
												</label>
												<div class="space-4"></div>
												<label class="block clearfix">

												</label>
												<div class="space-4"></div>
											</fieldset>
										</form>

										<!-- <div class="social-or-login center">
											<span class="bigger-110">Or Login Using</span>
										</div>

										<div class="social-login center">
											<a class="btn btn-primary"> <i class="icon-facebook"></i>
											</a> <a class="btn btn-info"> <i class="icon-twitter"></i>
											</a> <a class="btn btn-danger"> <i class="icon-google-plus"></i>
											</a>
										</div> -->
									</div>
									<!-- /widget-main -->

									<!-- <div class="toolbar clearfix">
										<div>
											<a href="#" onclick="show_box('forgot-box'); return false;"
												class="forgot-password-link"> <i class="icon-arrow-left"></i>
												I forgot my password
											</a>
										</div>

										<div>
											<a href="#" onclick="show_box('signup-box'); return false;"
												class="user-signup-link"> I want to register <i
												class="icon-arrow-right"></i>
											</a>
										</div>
									</div> -->
								</div>
								<!-- /widget-body -->
							</div>
							<!-- /login-box -->

							<!-- <div id="forgot-box" class="forgot-box widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header red lighter bigger">
											<i class="icon-key"></i> Retrieve Password
										</h4>

										<div class="space-6"></div>
										<p>Enter your email and to receive instructions</p>

										<form>
											<fieldset>
												<label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="email" class="form-control" placeholder="Email" />
														<i class="icon-envelope"></i>
												</span>
												</label>

												<div class="clearfix">
													<button type="button"
														class="width-35 pull-right btn btn-sm btn-danger">
														<i class="icon-lightbulb"></i> Send Me!
													</button>
												</div>
											</fieldset>
										</form>
									</div>
									<!-- /widget-main -->

									<!-- <div class="toolbar center">
										<a href="#" onclick="show_box('login-box'); return false;"
											class="back-to-login-link"> Back to login <i
											class="icon-arrow-right"></i>
										</a>
									</div> -->
								</div>
								<!-- /widget-body -->
							</div>
							<!-- /forgot-box -->

							<!-- <div id="signup-box" class="signup-box widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header green lighter bigger">
											<i class="icon-group blue"></i> New User Registration
										</h4>

										<div class="space-6"></div>
										<p>Enter your details to begin:</p>

										<form>
											<fieldset>
												<label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="email" class="form-control" placeholder="Email" />
														<i class="icon-envelope"></i>
												</span>
												</label> <label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="text" class="form-control" placeholder="Username" />
														<i class="icon-user"></i>
												</span>
												</label> <label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="password" class="form-control"
														placeholder="Password" /> <i class="icon-lock"></i>
												</span>
												</label> <label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="password" class="form-control"
														placeholder="Repeat password" /> <i class="icon-retweet"></i>
												</span>
												</label> <label class="block"> <input type="checkbox"
													class="ace" /> <span class="lbl"> I accept the <a
														href="#">User Agreement</a>
												</span>
												</label>

												<div class="space-24"></div>

												<div class="clearfix">
													<button type="reset" class="width-30 pull-left btn btn-sm">
														<i class="icon-refresh"></i> Reset
													</button>

													<button type="button"
														class="width-65 pull-right btn btn-sm btn-success">
														Register <i class="icon-arrow-right icon-on-right"></i>
													</button>
												</div>
											</fieldset>
										</form>
									</div>

									<div class="toolbar center">
										<a href="#" onclick="show_box('login-box'); return false;"
											class="back-to-login-link"> <i class="icon-arrow-left"></i>
											Back to login
										</a>
									</div>
								</div>
								<!-- /widget-body -->
							</div>
							<!-- /signup-box -->
						</div>
						<!-- /position-relative -->
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->

	<!--[if !IE]> -->

	<script src="<%=basePath %>resources/assets/js/jquery.min.js"></script>

	<!-- <![endif]-->

	<!--[if IE]>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<![endif]-->

	<!--[if !IE]> -->

	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='<%=basePath %>resources/assets/js/jquery-2.0.3.min.js'>"
								+ "<"+"/script>");
	</script>

	<!-- <![endif]-->

	<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='<%=basePath %>resources/assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

	<script type="text/javascript">
		if ("ontouchend" in document)
			document
					.write("<script src='<%=basePath %>resources/assets/js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
	</script>

	<!-- inline scripts related to this page -->

	<script type="text/javascript">
		function show_box(id) {
			jQuery('.widget-box.visible').removeClass('visible');
			jQuery('#' + id).addClass('visible');
		}
	</script>
	
	<!-- jQuery.fDialog.v1.0 -->
	<script src="<%=basePath %>resources/assets/js/jQuery.fDialog.v1.0/jquery.fDialog.min.js"></script>
	<script src="<%=basePath %>resources/assets/js/jQuery.fDialog.v1.0/fDialog.plugin.js"></script>
</body>
</html>