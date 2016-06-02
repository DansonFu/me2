<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>滚播蜜图管理</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<!-- basic styles -->
<link href="<%=basePath %>resources/assets/css/bootstrap.min.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<%=basePath %>resources/assets/css/font-awesome.min.css" />
<!--[if IE 7]>
		  <link rel="stylesheet" href="<%=basePath %>resources/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

<!-- page specific plugin styles -->

<!-- fonts -->

<!-- ace styles -->

<style>
* {
	margin: 0;
	padding: 0;
	list-style: none;
}

html {
	background-color: #E3E3E3;
	font-size: 14px;
	color: #000;
	font-family: '微软雅黑'
}

h2 {
	line-height: 30px;
	font-size: 20px;
}

a, a:hover {
	text-decoration: none;
}

pre {
	font-family: '微软雅黑'
}

.box {
	width: 970px;
	padding: 10px 20px;
	background-color: #fff;
	margin: 10px auto;
}

.box a {
	padding-right: 20px;
}

.demo1, .demo2, .demo3, .demo4, .demo5, .demo6 {
	margin: 25px 0;
}

h3 {
	margin: 10px 0;
}

.layinput {
	height: 22px;
	line-height: 22px;
	width: 150px;
	margin: 0;
}
</style>



<link rel="stylesheet"
	href="<%=basePath %>resources/assets/css/ace.min.css" />
<link rel="stylesheet"
	href="<%=basePath %>resources/assets/css/ace-rtl.min.css" />
<link rel="stylesheet"
	href="<%=basePath %>resources/assets/css/ace-skins.min.css" />
<link rel="stylesheet"
	href="<%=basePath %>resources/assets/css/chosen.css" />
<link rel="stylesheet"
	href="<%=basePath %>resources/assets/css/global.css">
<link rel="stylesheet"
	href="<%=basePath %>resources/assets/css/common.css">
<link rel="stylesheet"
	href="<%=basePath %>resources/assets/css/datepicker.css">
<link rel="stylesheet"
	href="<%=basePath %>resources/assets/js/need/laydate.css">
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
				<form class="form-bordered" id="me2form"
					action="<%=basePath %>/admin/saveperiodmetoo/commend" method="post"
					enctype="multipart/form-data">
					<div class="col-lg-6" id="bfeelDiv">
						<div class="col-lg-3 input-column">
							<span class="dangger" style="color: red">*</span> 蜜图A面：
						</div>
						<div class="col-lg-6" style="height: 120px">
							<img src="${domain}/${pct.picture.qiniukey}" width="100px"
								height="100px" />
						</div>
						<div class="col-lg-3 input-column">
						<div class="col-lg-9">
							<input type="hidden" id="id" name="id" value="${pct.id}">
							<input type="hidden" id="pid" name="pid" value="${pct.pid}">
							<input type="hidden" id="sort" name="sort" value="${pct.sort}">
							<!-- 
										<input type="text" name="period" style="width:80%;" value="${pct.period}"/>
										 -->




							<div class="box">
							   <!--  
								<pre>
                                   <strong>【注意事项】</strong>
                                    一、请千万勿移动laydate中的目录结构，它们具有完整的依赖体系。使用时，只需引入laydate/laydate.js即可。
                                    二、如果您的网站的js采用合并或模块加载，您需要打开laydate.js，修改path。
                                   </pre>


								<div class="demo1">
									<h3>演示二（普通模式）</h3>
									<input class="laydate-icon" id="demo" value="2014-6-25更新">
								</div>
								-->

								<div class="demo2">
									<h3>有效期</h3>
									<input placeholder="请输入日期" class="laydate-icon" name="period"
										onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
								</div>

                                <!--  
								<div class="demo3">
									<h3>演示三（日期范围限制）</h3>
									<ul class="inline">
										开始日：
										<li class="inline laydate-icon" id="start"
											style="width: 200px; margin-right: 10px;"></li> 结束日：
										<li class="inline laydate-icon" id="end" style="width: 200px;"></li>
									</ul>
								</div>

								<div class="demo4">
									<h3>演示四（自定义日期格式）</h3>
									<div id="test1" class="inline laydate-icon"
										style="width: 150px;"></div>
								</div>

								<div class="demo5">
									<h3>演示五（日期范围限定在昨天到明天）</h3>
									<div class="inline laydate-icon" style="width: 150px;"
										id="hello3"></div>
								</div>

								<div class="demo6">
									<h3>演示六（按钮触发日期）</h3>
									<input readonly class="layinput" id="hello1">
									<div class="laydate-icon "
										onclick="laydate({elem: '#hello1'});"
										style="width: 10px; display: inline-block; border: none;"></div>
								</div>
                                -->
							</div>








							<div class="col-sm-4">
								<div class="widget-box"></div>
								<!-- 
												<div class="widget-body">
													<div class="widget-main">
														<label for="id-date-picker-1">Date Picker</label>

														<div class="row">
															<div class="col-xs-8 col-sm-11">
																<div class="input-group">
																	<input class="form-control date-picker" id="id-date-picker-1" name="period" type="text" data-date-format="dd-mm-yyyy" />
																	<span class="input-group-addon">
																		<i class="icon-calendar bigger-110"></i>
																	</span>
																</div>
															</div>
														</div>

													</div>
												</div>
												 -->
							</div>
						</div>






					</div>
			</div>
			<!-- col-lg-6 -->
		</div>
		<!-- row -->

	</div>
	<!-- panel-body -->
	</div>


	<br>
	<div style="text-align: center">
		<div>
			<button class="btn btn-primary" type="button" onclick="submitform()">
				<i class="icon-ok bigger-110"></i> 提交
			</button>

			&nbsp; &nbsp; &nbsp;
			<button class="btn" type="button"  id="aaa" >
				<i class="icon-undo bigger-110"></i> 取消
			</button>
		</div>
	</div>

	</form>
	</div>

	<script src="<%=basePath %>resources/assets/js/jquery.min.js"></script>
	<script src="<%=basePath %>resources/assets/js/jquery.form.js"></script>
	<script src="<%=basePath %>resources/assets/js/bootstrap.min.js"></script>
	<script src="<%=basePath %>resources/assets/js/typeahead-bs2.min.js"></script>
	<script
		src="<%=basePath %>resources/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script
		src="<%=basePath %>resources/assets/js/jquery.ui.touch-punch.min.js"></script>
	<script
		src="<%=basePath %>resources/assets/js/jquery.slimscroll.min.js"></script>
	<script
		src="<%=basePath %>resources/assets/js/jquery.easy-pie-chart.min.js"></script>
	<script src="<%=basePath %>resources/assets/js/jquery.sparkline.min.js"></script>
	<script src="<%=basePath %>resources/assets/js/flot/jquery.flot.min.js"></script>
	<script
		src="<%=basePath %>resources/assets/js/flot/jquery.flot.pie.min.js"></script>
	<script
		src="<%=basePath %>resources/assets/js/flot/jquery.flot.resize.min.js"></script>
	<script
		src="<%=basePath %>resources/assets/js/date-time/bootstrap-datepicker.min.js"></script>
	<script src="<%=basePath %>resources/assets/js/laydate.js"></script>

	<!-- ace scripts -->

	<script src="<%=basePath %>resources/assets/js/ace-elements.min.js"></script>
	<script src="<%=basePath %>resources/assets/js/ace.min.js"></script>

	<script type="text/javascript">
		$('.date-picker').datepicker({autoclose:true}).next().on(ace.click_event, function(){
			$(this).prev().focus();
		});
		</script>




	<script>
!function(){
laydate.skin('molv');//切换皮肤，请查看skins下面皮肤库
laydate({elem: '#demo'});//绑定元素
}();
//日期范围限制
var start = {
    elem: '#start',
    format: 'YYYY-MM-DD',
    min: laydate.now(), //设定最小日期为当前日期
    max: '2099-06-16', //最大日期
    istime: true,
    istoday: false,
    choose: function(datas){
         end.min = datas; //开始日选好后，重置结束日的最小日期
         end.start = datas //将结束日的初始值设定为开始日
    }
};
var end = {
    elem: '#end',
    format: 'YYYY-MM-DD',
    min: laydate.now(),
    max: '2099-06-16',
    istime: true,
    istoday: false,
    choose: function(datas){
        start.max = datas; //结束日选好后，充值开始日的最大日期
    }
};
laydate(start);
laydate(end);
//自定义日期格式
laydate({
    elem: '#test1',
    format: 'YYYY年MM月DD日',
    festival: true, //显示节日
    choose: function(datas){ //选择日期完毕的回调
        alert('得到：'+datas);
    }
});
//日期范围限定在昨天到明天
laydate({
    elem: '#hello3',
    min: laydate.now(-1), //-1代表昨天，-2代表前天，以此类推
    max: laydate.now(+1) //+1代表明天，+2代表后天，以此类推
});
</script>










	<script type="text/javascript">

	function submitform(){

		//if($("input[name='period']").val()==""){
			//$("input[name='period']").focus();
			//return;
		//}
		
		//if($("input[name='period']").val()==null||$("input[name='period']").val()==""){
			//$("input[name='period']").focus();
			//return;
		//}

		$("#me2form").submit();
		$(":button").attr("disabled", true);  
	}
	
	//function a(){
	// }
	$("#aaa").on("click",function(){
		window.location=  "<%=basePath %>/admin/showcommendcheck";
	});
	

</script>
</body>
</html>

