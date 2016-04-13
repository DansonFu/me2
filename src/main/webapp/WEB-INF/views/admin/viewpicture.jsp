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
                            <div>
                            <h3>发布密图</h3>
                            </div>
                        </div>
                        <h4>随机用户信息</h4>
                    </div>
                    <div class="panel-body">
                            <div class="row form-group">
                            	<div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	 头像：
                                    </div>
                                    <div class="col-lg-9">
                                    	<img  src="${domain}/${picture.customer.headimgurl}" width="70px" height="70px"/>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	ID：
                                    </div>
                                    <div class="col-lg-9">
                                    	<label>${picture.customer.customerId }</label>
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
                                    <div class="col-lg-9">
                                        ${picture.customer.username }
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	性别：
                                    </div>
                                    <div class="col-lg-9">
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
                        <h4>修改蜜图</h4>
                    </div>
                    <div class="panel-body">
                    	<form class="form-bordered"  id="me2form" action="<%=basePath %>admin/updatepicture" method="post" enctype="multipart/form-data">
                            <input type="hidden" value="${picture.pid }" name="pid" id="pid" />
                            <input type="hidden" value="${picture.bpicture.pid }" name="bpid" id="bpid" />
                            <div class="row form-group">
                            	<div class="col-lg-6">
                                    <div class="col-lg-3 input-column" style="height: 120px">
                                       	<span class="dangger" style="color: red">*</span> A面：
                                    </div>
                                    <div class="col-lg-3" style="height: 120px">
                                    	<input type="file" name="afile"/>
                                    </div>
                                    <div class="col-lg-6" style="height: 120px">
                                    	<a href="${domain}/${picture.qiniukey}" target="_blank" id="afront">
                                        <img  src="${domain}/${picture.qiniukey}" width="100px" height="100px"/>
                                       </a>
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column" style="height: 120px">
                                       	B面：
                                    </div>
                                    <div class="col-lg-2" style="height: 120px">
                                    	<select name="type" onchange="changetype()">
											<option value="1" <c:if test="${picture.bpicture.type == '1'}">selected="selected"</c:if>>图片</option>
											<option value="2" <c:if test="${picture.bpicture.type == '2'}">selected="selected"</c:if>>URL</option>
											<!-- 
											<option value="5" <c:if test="${picture.bpicture.type == '5'}">selected="selected"</c:if>>URL</option>
											 -->
										</select>
                                    </div>
                                    <div class="col-lg-7" id="fileDiv" style="height: 120px">
                                    	<input type="file" name="bfile"/>
                                    	<a href="${picture.bpicture.qiniukey}" target="_blank" id="abpicture">
                                        <img  src="${picture.bpicture.qiniukey}" width="80px" height="80px" id="imgbpicture"/>
                                       </a>
                                    </div>
                                    <div class="col-lg-7" id="textDiv" style="display: none;height: 120px">
                                    	<input type="text" name="content" style="width:80%;" value="${picture.bpicture.qiniukey}"/>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                                
                            </div>
                            <!-- row -->
                            <div class="row form-group">
                            	<div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	位置：
                                    </div>
                                    <div class="col-lg-9">
                                    	<input type="text" name="locationTitle"  style="width:80%;" value="${picture.locationTitle}"/>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	地址：
                                    </div>
                                    <div class="col-lg-9">
                                    	<input type="text" name="locationContent" style="width:80%;" value="${picture.locationContent}"/>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                            </div>
                            <!-- row -->
                            <div class="row form-group">
								<!-- col-lg-6 -->
                                
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	心情：
                                    </div>
                                    <div class="col-lg-9">
                                    	<input type="text" name="mood" style="width:80%;" value="${picture.mood}"/>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	解蜜游戏：
                                    </div>
                                    <div class="col-lg-9">
                                    	<select name="gameId" >
										<c:forEach items="${games}" var="game" varStatus="">
											<option value="${game.gameId }" <c:if test="${game.gameId == picture.gameId}">selected="selected"</c:if> >${game.name }</option>
										</c:forEach>
										</select>
                                    </div>
                                </div>
                            </div>
                            <!-- row -->
                            <div class="row form-group">
                            	<div class="col-lg-6" >
                                    <div class="col-lg-3 input-column" >
                                       	<span class="dangger" style="color: red">*</span>标签<span style="color:red">(半角符号 # 分隔)</span>：
                                    </div>
                                    <div class="col-lg-9" >
                                    	<input type="text" name="tags" style="width:80%;" value="${picture.tags}"/>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                                <div class="col-lg-6" id="bfeelDiv">
                                    <div class="col-lg-3 input-column">
                                       	B面心情：
                                    </div>
                                    <div class="col-lg-9">
										<input type="text" name="bmood" style="width:80%;" value="${picture.bpicture.mood}"/>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                            </div>
                            <!-- row -->
                           
                   
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
                        <h4>修改评论</h4>
                    </div>
					<c:forEach items="${comments }" var="comment">
                    <!-- row -->
					<div class="row form-group">
                        <div class="col-lg-12">
                            <div class="col-lg-2 input-column">
                               <input type="hidden" name="cid" value="${comment.commentId }">${comment.customer.username }：
                            </div>
                            <div class="col-lg-6">
                                <textarea class="form-control" rows="5" placeholder="评论内容" name="commentContent"
                                	style="margin-top: 0px; margin-bottom: 0px; height: 60px;">${comment.content}</textarea>
                            </div>
                            <div class="col-lg-2">
                                <input type="file"  name="file"/>
                            </div>
                            <div class="col-lg-2">
                            	<c:if test="${comment.qiniukey != null}">
                                <img alt="" src="${domain }/${comment.qiniukey }" width="50px" height="50px"><br>
                                </c:if>
                            </div>
                        </div>
                        <!-- col-lg- -->
                    </div>
				</c:forEach>
             </div>
             
		 </form>
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
$(document).ready(function(){
	changetype();
}); 
	function changetype(){
		var type = $("select[name='type']").val();
		if(type=='1'){
			$("#fileDiv").css('display','block');
			$("#textDiv").css('display','none');
			$("#bfeelDiv").css('display','block');
		}else if(type=='2'){
			$("#fileDiv").css('display','none');
			$("#textDiv").css('display','block');
			$("#bfeelDiv").css('display','none');
		}
	}
	function submitform(){

		if($("input[name='tags']").val()==null||$("input[name='tags']").val()==""){
			$("input[name='tags']").focus();
			return;
		}

		$("#me2form").submit();
		$(":button").attr("disabled", true);  
	}

</script>		
	</body>
</html>

