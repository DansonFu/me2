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
		<h3 class="header smaller lighter blue">审核蜜图</h3>
		<br>
		<form class="form-horizontal" role="form" id="me2form" action="<%=basePath %>admin/commendcheckmetoo" method="post" enctype="multipart/form-data">
		<input type="text"   name="tag"   style="width:30%;" class="form-control search-query" placeholder="Type your query" />
		<input type="hidden"  id="tag" value="${tag }" />
																		<button type="button" class="btn btn-purple btn-sm"  onclick="submitform()">
																			Search
																			<i class="icon-search icon-on-right bigger-110"></i>
																		</button>
		</form>
		<br>
		<div class="table-responsive">
			<table id="sample-table-2" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center" >
							密图ID
						</th>
						<th class="center">内部用户</th>
						<th class="center">A面</th>
						<th class="center">A面心情</th>
						<th class="hidden-480 center">B面</th>
						<th class="center">B面心情</th>
						<th class="center">B面类型</th>
						<th class="center">标签</th>
						<!-- 
						<th class="center">发贴者</th>
						 -->
						<th class="center">创建时间</th>
						<th class="center">是否推荐</th>
						<th class="center">操作</th>
					</tr>
				</thead>
	
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>

<script type="text/javascript">
	function update(pid){
		window.location="<%=basePath %>admin/viewcheckpicture?pid="+pid;
	}
	function del(pid){
		if(confirm('确定要逻辑删除该蜜图吗?')){
		     window.location="<%=basePath %>/admin/delcheckmetoo/commend?pid="+pid;
		}
	}
	function dela(pid){
		if(confirm('确定要恢复逻辑删除吗?')){
		     window.location="<%=basePath %>/admin/delcheckmetooa/commend?pid="+pid;
		}
	}
	function add(pid){
		window.location="<%=basePath %>admin/save/commend?pid="+pid;
	}
	function comment(pid){
		window.location="<%=basePath %>admin/checkview/comment?pid="+pid;
	}
	function submitform(){
		$("#me2form").submit();
		$(":button").attr("disabled", true);  
	}
    
	$(document).ready(function(){
		var oTable1 = $('#sample-table-2').dataTable( {
			"bSort":false,
			"bFilter": false,
			"autoWidth": false,
			"aoColumnDefs": [
			    {
   	        	   "aTargets": [1],
   	        	   "fnRender":function(data,type){
   	        		   var str = " ";
   	        		   if(data.aData[1]=="1"){
   	        				str = "是";
   	        		   }else if(data.aData[1]=="0"){
   	        				str = "否";
   	        		   }
   	        		   return  str;
   	        	   }
   	           },
   	           {
   	        	   "aTargets": [2],
   	        	   "fnRender":function(data,type){
   	        		   return  '<a href="'+data.aData[2]+'" target="_blank" id="afront">  <img src="'+data.aData[2]+'" width="100px" height="100px" id="a"> </a>';
   	        	   }
   	           },
   	        	{ "sWidth": "1.9" ,"width": "1.10",
   	        	   "aTargets": [4],
   	        	   "fnRender":function(data,type){
   	        		   var str = "";
   	        		   if(data.aData[6]==1){
   	        				str = '<a href="'+data.aData[4]+'" target="_blank" id="abpicture">  <img src="'+data.aData[4]+'" width="100px" height="100px" id="b"> </a>';
   	        		   }else{
   	        				str = data.aData[4];
   	        		   }
   	        		   return  str;
   	        	   }
   	           },
   	        	{
   	        	   "aTargets": [6],
   	        	   "fnRender":function(data,type){
   	        		   var str = "";
   	        		   if(data.aData[6]=="1"){
   	        				str = "图片";
   	        		   }else if(data.aData[6]=="2"){
   	        				str = "URL";
   	        		   }else if(data.aData[6]==""){
   	        				str = "";
   	        		   }
   	        		   return  str;
   	        	   }
   	           },
   	             {
   	        	   "aTargets": [9],
   	        	   "fnRender":function(data,type){
   	        		   var str = "";
   	        		   if(data.aData[9]=="1"){
   	        				str = "是";
   	        		   }else if(data.aData[9]=="0"){
   	        				str = "否";
   	        		   }
   	        		   return  str;
   	        	   }
   	           },
   	        	{
   	        	   "aTargets": [10],
   	        	   "fnRender":function(data,type){
   	        		   var str = '<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">';
//   	        			str += '<a class="blue" href="javascript:;" onclick="view('+"'"+data.aData[0]+"'"+')">';
//   	        			str += '<i class="icon-zoom-in bigger-130"></i>';
//   	        			str += '</a>';
   	        			str += '<a class="green" href="javascript:void(0);" onclick="update('+"'"+data.aData[0]+"'"+')" >';
   	        			str += '<i class="icon-pencil bigger-130"></i>';
   	        			str += '</a><br>';
   	        			if(data.aData[11]=="0"){
   	        				str += '<input type="button"  value="OFF"  onclick="del('+"'"+data.aData[0]+"'"+')"/><br>';
   	        			}else if(data.aData[11]=="1"){
   	        				str += '<input type="button"  value="ON"  onclick="dela('+"'"+data.aData[0]+"'"+')"/><br>';
   	        			}
   	 //       			str += '<input name="switch-field-1" class="ace ace-switch" type="checkbox"  onclick="del('+"'"+data.aData[0]+"'"+')"/>  <span class="lbl"></span> <br>';
   	        			
   	//        			str += '<input type="button"  value="逻辑删除"  onclick="del('+"'"+data.aData[0]+"'"+')"/><br>';
   	        			str += '<input type="button"  value="加入滚播"  onclick="add('+"'"+data.aData[0]+"'"+')"/><br>';
   	        			str += '<input type="button"  value="查看评论"  onclick="comment('+"'"+data.aData[0]+"'"+')"/>';
   	        			str += '</div>';
   	        			
   	        		   return  str;
   	        	   }
   	           }
   	         ],
		     "bServerSide": true,//这个用来指明是通过服务端来取数据
		     "sAjaxSource": "<%=basePath %>/amdin/check/metoo",//这个是请求的地址
		     "fnServerData": retrieveData, // 获取数据的处理函数
		} );
		//3个参数的名字可以随便命名,但必须是3个参数,少一个都不行
		  function retrieveData( sSource111,aoData111, fnCallback111) {
			 // var ps = $('#ps').val();
              var tag=$('#tag').val();
		      $.ajax({
		          url : sSource111,//这个就是请求地址对应sAjaxSource
		          data : {"aoData":JSON.stringify(aoData111),
		        	  "tag":tag
		        	  },//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
		          type : 'post',
		          dataType : 'json',
		          async : false,
		          success : function(result) {
		              fnCallback111(result);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
		          },
		          error : function(msg) {
		          }
		      });
		  }
		
		  //$("#ps").bind("change", function(){
			 // oTable1.fnPageChange('first');
		 // });
		  
		  

	});
	 
</script>

</body>
</html>