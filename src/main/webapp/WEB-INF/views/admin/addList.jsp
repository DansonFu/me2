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
			
			 <div class="panel panel-default">
                    <div class="panel-body">
                    	<form class="form-bordered"  id="me2form" action="<%=basePath %>admin/insertList" method="post" enctype="multipart/form-data">
                             
                                    <div class="col-lg-3 input-column">
                                       	新建集合：
                                    </div>
                                    <div class="col-lg-9">
                                    	id:&nbsp;&nbsp;&nbsp;<input type="hidden" id="id" name="id" value="${list.id}">
										 <br/>
                                       	排序:&nbsp;&nbsp;<input type="text" id="num" name="num" value="${list.num}">
										 <br/>
										 七牛:&nbsp;&nbsp;<input type="hidden" id="qiniukey" name="qiniukey" value="${list.qiniukey}">
										<br/>
										<div class="col-lg-9" onchange="changetype()">
										 <div id="fileDiv">
                                    	<input type="file" name="key"/>
                                   		 </div>
                                   		 </div>
										 <br/>
										集合名称:<input type="text" name="title"  value="${list.title}"/>
                                    </div>
                              
					 <br>
			               <div  style="text-align: center">
							<div >
								<button class="btn btn-primary" type="button" onclick="submitform()">
									<i class="icon-ok bigger-110"></i>
									提交
								</button>
			
								&nbsp; &nbsp; &nbsp;
								<button class="btn" type="reset" id="back">
									<i class="icon-undo bigger-110"></i>
									取消
								</button>
							</div>
						</div>
                        
           		</form>
           </div>
		</div>
	</div>		
<script type="text/javascript">

$("#back").on("click",function(){
	window.location="<%=basePath %>admin/viewList";
});
function changetype(){
	
	if(type=='1'){
		$("#fileDiv").css('display','block');
		
	}else if(type=='5'||type=='2'){
		$("#fileDiv").css('display','none');
		
	}
}
	function submitform(){
		
		if($("input[name='title']").val()==""){
			$("input[name='title']").focus();
			return;
		}
		if($("input[name='title']").val()==null||$("input[name='title']").val()==""){
			$("input[name='title']").focus();
			return;
		}
	
		if($("input[name='num']").val()==""){
			$("input[name='num']").focus();
			return;
		}
		if($("input[name='num']").val()==null||$("input[name='num']").val()==""){
			$("input[name='num']").focus();
			return;
		}
		

		$("#me2form").submit();
		$(":button").attr("disabled", true);  
	}
</script>

</body>
</html>