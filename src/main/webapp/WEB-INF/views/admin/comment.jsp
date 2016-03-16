<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                    <div class="panel-heading">
                        <div class="panel-btns">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="expand-link">
                                <i class="fa fa-expand"></i>
                            </a>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                        <h4>用户信息</h4>
                    </div>
                    <div class="panel-body">
                            <div class="row form-group">
                            	<div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	 头像：
                                    </div>
                                    <div class="col-lg-9">
                                    	<img  src="${domain}/${picture.customer.headimgurl}" width="70px" height="70px" id="headimgurl"/>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	ID：
                                    </div>
                                    <div class="col-lg-9">
                                    	<label id="customerId">${picture.customer.customerId }</label>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                                
                            </div>
                            <!-- row -->
                          <div class="row form-group">
                          		<div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	呢称：
                                    </div>
                                    <div class="col-lg-9" id="username">
                                        ${picture.customer.username }
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	性别：
                                    </div>
                                    <div class="col-lg-9" id="sex">
                                    	<c:if test="${picture.customer.sex=='0' }">未知</c:if>
                                        <c:if test="${picture.customer.sex=='1' }">男</c:if>
                                        <c:if test="${picture.customer.sex=='2' }">女</c:if>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                            </div>
                            <!-- row -->
                    </div>
                    <!-- panel-body -->
                </div>
			
			
			<div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="panel-btns">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="expand-link">
                                <i class="fa fa-expand"></i>
                            </a>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                        <h4>蜜图</h4>
                    </div>
                    <div class="panel-body">
                            <div class="row form-group">
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column" style="height:220px ">
                                        	A面：
                                    </div>
                                    <div class="col-lg-9">
                                       <a href="${domain}/${picture.qiniukey}" target="_blank" id="afront">
                                        <img  src="${domain}/${picture.qiniukey}" width="200px" height="200px" id="imgfront"/>
                                       </a>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column" style="height:220px ">
                                        B面：
                                    </div>
                                    <div class="col-lg-9">
                                       <a href="${BURL}" target="_blank" id="abpicture">
                                        <img  src="${BURL}" width="200px" height="200px" id="imgbpicture"/>
                                       </a>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                            </div>
                            <!-- row -->
                            <div class="row form-group">
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                        	标签：
                                    </div>
                                    <div class="col-lg-9">
                                        <label id="tags">${picture.tags }</label>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                        	A面心情：
                                    </div>
                                    <div class="col-lg-9">
										<label id="amood">${picture.mood }</label>
                                    </div>
                                </div>
                                 <div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                        	B面心情：
                                    </div>
                                    <div class="col-lg-9">
										<label id="bmood">${bpicture.mood }</label>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                            </div>
                        
                            <div class="row form-group">
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                        	位置：
                                    </div>
                                    <div class="col-lg-9">
                                        <label id="location">${picture.locationTitle }:${picture.locationContent }</label>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                        	创建时间：
                                    </div>
                                    <div class="col-lg-9">
										<label id="creatTime">
											<fmt:formatDate value="${picture.creatTime }" pattern="yyyy/MM/dd  HH:mm:ss"/>
										</label>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                            </div>
                            
			                 <div class="row form-group">
								<div style="text-align: center">
									<button class="btn btn-info" type="button" onclick="randmetoo()">
										<i class="icon-ok bigger-110"></i>
										随机
									</button>
									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="reset" onclick="nextmetoo()">
										<i class="icon-undo bigger-110"></i>
										下一个
									</button>
								</div>
							</div>
                    </div>
                    <!-- panel-body -->
                </div>
              
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="panel-btns">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="expand-link">
                                <i class="fa fa-expand"></i>
                            </a>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                        <h4>已有评论</h4>
                    </div>
                    <div id="comments">

                    </div>
                    <!-- row -->

             </div>
               
             <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="panel-btns">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="expand-link">
                                <i class="fa fa-expand"></i>
                            </a>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                        <h4>评论</h4>
                    </div>
                 <form class="form-horizontal" role="form" id="me2form" action="<%=basePath %>admin/savecomment" method="post" enctype="multipart/form-data">
                    <input type="hidden" id="pid" name="pid" value="${picture.pid}">
                    <!-- row -->
                    <c:forEach items="${customers }" var="customer">
                    <div class="row form-group">
                        <div class="col-lg-12">
                            <div class="col-lg-2 input-column">
                               <input type="hidden" value="${customer.customerId }" name="cid"> 	${customer.username }：
                            </div>
                            <div class="col-lg-8">
                                <textarea class="form-control" rows="5" placeholder="评论内容" name="content"
                                	style="margin-top: 0px; margin-bottom: 0px; height: 60px;"></textarea>
                            </div>
                            <div class="col-lg-2">
                                <input type="file"  name="file"/>
                            </div>
                        </div>
                        <!-- col-lg- -->
                    </div>
                    </c:forEach>
                    <!-- row -->
                    <div class="row form-group">
						<div style="text-align: center">
							<button class="btn btn-info" type="button" onclick="submitform()">
								<i class="icon-ok bigger-110"></i>
								提交
							</button>
							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset" >
								<i class="icon-undo bigger-110"></i>
								取消
							</button>
						</div>
					</div>
                 </form>
             </div>
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
	//7牛公共资源域名
	var domain = '${domain}';
	$(document).ready(function(){
		randmetoo();
	}); 
	
	function submitform(){
		$("#me2form").submit();
		$(":button").attr("disabled", true);  
	}

	function randmetoo(){
		var bkey;
		$.ajax({
			url:"<%=basePath%>admin/randmetoo",
			type:"get",
			datatype:"json",
			success:function(data){
				//蜜图信息
				$("#afront").attr("href",domain+"/"+data[0].qiniukey);
				$("#imgfront").attr("src",domain+"/"+data[0].qiniukey);
				$("#tags").text(data[0].tags);
				$("#amood").text(data[0].mood);
				$("#bmood").text(data[0].mood);
				$("#location").text(data[0].locationTitle+":"+data[0].locationContent);
				$("#creatTime").text(data[0].creatTime);
				$("#pid").val(data[0].pid);
				//发布人信息
				$("#headimgurl").attr("src",domain+"/"+data[0].customer.headimgurl);
				$("#customerId").text(data[0].customer.customerId);
				$("#username").text(data[0].customer.username);
				var sex="";
				if("0"==data[0].customer.sex){
					sex="未知";
				}else if("1"==data[0].customer.sex){
					sex="男";
				}else if("2"==data[0].customer.sex){
					sex="女";
				}
				$("#sex").text(sex);
				
				//已有评论
				var html="";
				for(var i=0;i<data[1].length;i++){
					html += '<div class="row form-group">';
					html += '<div class="col-lg-12">';
					html += '<div class="col-lg-2 input-column">';
					html += data[1][i].customer.username;
					html += '</div>';
					html += '<div class="col-lg-10">';
					if(data[1][i].qiniukey!=null && data[1][i].qiniukey!=""){
						html += '<img alt="" src="'+domain+'/'+data[1][i].qiniukey+'" width="100px" height="100px"><br>';
					}
					html += '<label>'+data[1][i].content+'</label>';
					html += '</div>';
					html += '</div>';
					html += '</div>';
				}
                $("comments").html(html);
				
				bkey = data[0].bpicture.qiniukey;
				$.ajax({
					url:"<%=basePath%>qiniutoken/download/"+bkey+".json",
					type:"get",
					async: false,
					datatype:"json",
					success:function(data){
						$("#abpicture").attr("href",data.obj);
						$("#imgbpicture").attr("src",data.obj);
					}
				});
			}
		});

	}
	function nextmetoo(){
		var bkey;
		var pid = $("#pid").val();
		$.ajax({
			url:"<%=basePath%>admin/nextmetoo/"+pid,
			type:"get",
			datatype:"json",
			success:function(data){
				$("#afront").attr("href",domain+"/"+data[0].qiniukey);
				$("#imgfront").attr("src",domain+"/"+data[0].qiniukey);
				$("#tags").text(data[0].tags);
				$("#mood").text(data[0].mood);
				$("#location").text(data[0].locationTitle+":"+data[0].locationContent);
				$("#creatTime").text(data[0].creatTime);
				$("#pid").val(data[0].pid);
				//发布人信息
				$("#headimgurl").attr("src",domain+"/"+data[0].customer.headimgurl);
				$("#customerId").text(data[0].customer.customerId);
				$("#username").text(data[0].customer.username);
				var sex="";
				if("0"==data[0].customer.sex){
					sex="未知";
				}else if("1"==data[0].customer.sex){
					sex="男";
				}else if("2"==data[0].customer.sex){
					sex="女";
				}
				$("#sex").text(sex);
				
				//已有评论
				var html="";
				for(var i=0;i<data[1].length;i++){
					html += '<div class="row form-group">';
					html += '<div class="col-lg-12">';
					html += '<div class="col-lg-2 input-column">';
					html += data[1][i].customer.username;
					html += '</div>';
					html += '<div class="col-lg-10">';
					if(data[1][i].qiniukey!=null && data[1][i].qiniukey!=""){
						html += '<img alt="" src="'+domain+'/'+data[1][i].qiniukey+'" width="100px" height="100px"><br>';
					}
					html += '<label>'+data[1][i].content+'</label>';
					html += '</div>';
					html += '</div>';
					html += '</div>';
				}
                $("comments").html(html);
                
				bkey = data[0].bpicture.qiniukey;
				$.ajax({
					url:"<%=basePath%>qiniutoken/download/"+bkey+".json",
					type:"get",
					async: false,
					datatype:"json",
					success:function(data){
						$("#abpicture").attr("href",data.obj);
						$("#imgbpicture").attr("src",data.obj);
					}
				});
			}
		});

	}

</script>		
	</body>
</html>

