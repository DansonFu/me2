<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title></title>
<meta name="description" content="">
<meta name="keywords" content="">

<link href="<%=basePath %>resources/assets/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=basePath %>resources/assets/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=basePath %>resources/assets/css/ace.min.css" />
<link rel="stylesheet" href="<%=basePath %>resources/assets/css/ace-rtl.min.css" />
<link rel="stylesheet" href="<%=basePath %>resources/assets/css/ace-skins.min.css" />
<script src="<%=basePath %>resources/assets/js/ace-extra.min.js"></script>
<script src="<%=basePath %>resources/assets/js/jquery.min.js"></script>
<script src="<%=basePath %>resources/assets/js/bootstrap.min.js"></script>
<script src="<%=basePath %>resources/assets/js/typeahead-bs2.min.js"></script>
<script src="<%=basePath %>resources/assets/js/jquery.datatables.min.js"></script>
<script src="<%=basePath %>resources/assets/js/jquery.dataTables.bootstrap.js"></script>
<script src="<%=basePath %>resources/assets/js/ace-elements.min.js"></script>
<script src="<%=basePath %>resources/assets/js/ace.min.js"></script>
</head>
<body>
<div class="page-content">
	<div class="row">
	<div class="col-xs-12">
		<h3 class="header smaller lighter blue">添加集合</h3>
		<div style="float: right;">
			
		</div>
		<br>
		<br>
		<div class="table-responsive">
		<form action="<%=basePath %>admin/viewList" method="post" id="add">
			<table id="sample-table-2" class="table table-striped table-bordered table-hover">
				<thead>
					
				</thead>
					
				<tbody>
					  <div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	集合ID：
                                    </div>
                                    <div class="col-lg-9">
                                    	<label>${Taglist.id }</label>
                                    </div>
                                </div>
                                 <div class="row form-group">
                          		<div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	集合名称：
                                    </div>
                                    <div class="col-lg-9">
                                        ${Taglist.title }
                                    </div>
                                     <div class="col-lg-9">
                                    	<input type="text" name="title"/>
                                    </div>
                                </div>
                                 <div>
                          		<div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	七牛key：
                                    </div>
                                    <div class="col-lg-9">
                                        ${Taglist.qiniukey }
                                    </div>
                                     <div class="col-lg-9">
                                    	<input type="text" name="key"/>
                                    </div>
                                </div>
                                 <div>
                          		<div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	排序：
                                    </div>
                                    <div class="col-lg-9">
                                        ${Taglist.num }
                                    </div>
                                     <div class="col-lg-9">
                                    	<input type="text" name="num"/>
                                    </div>
                                </div>
						 <div>
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
				</tbody>
				
			</table>
			</form>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">



	function submitform(){
		if($("input[name='title']").val()==""){
			$("input[name='title']").focus();
			return;
		}
		if($("input[name='key']").val()==""){
			$("input[name='key']").focus();
			return;
		}
		if($("input[name='num']").val()==""){
			$("input[name='num']").focus();
			return;
		}
		

		$("#add").submit();
		$(":button").attr("disabled", true);  
	}
</script>

</body>
</html>