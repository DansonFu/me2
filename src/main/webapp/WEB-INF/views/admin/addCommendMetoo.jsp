
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
		<h3 class="header smaller lighter blue">推荐蜜图</h3>
		<br>
		<br>
		<div class="table-responsive">
			<table id="sample-table-2" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center" >
							密图ID
						</th>
						<th class="center">A面</th>
						<th class="center">排序</th>
						
						<th class="center">有效期</th>
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
	window.location="<%=basePath %>admin/viewcommendmetoo?pid="+pid;
}
	
function del(pid){
	window.location="<%=basePath %>admin/del/commend?pid="+pid;
}
	
$(document).ready(function(){
		var oTable1 = $('#sample-table-2').dataTable( {
			"bSort":false,
			"bFilter": false,
			"aoColumnDefs": [
   	           {
   	        	   "aTargets": [1],
   	        	   "fnRender":function(data,type){
   	        		   return  '<img src="'+data.aData[1]+'" width="100px" height="100px">';
   	        	   }
   	           },
   	        {
   	        	   "aTargets": [4],
   	        	   "fnRender":function(data,type){
   	        		   var str = '<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">';
//   	        			str += '<a class="blue" href="javascript:;" onclick="view('+"'"+data.aData[0]+"'"+')">';
//   	        			str += '<i class="icon-zoom-in bigger-130"></i>';
//   	        			str += '</a>';
   	        			str += '<a class="green" href="javascript:void(0);" onclick="update('+"'"+data.aData[0]+"'"+')" >';
   	        			str += '<i class="icon-pencil bigger-130"></i>';
   	        			str += '</a>';
   	        			
   	        			str += '<input type="button"  value="删除"  onclick="del('+"'"+data.aData[0]+"'"+')"/>';
   	        			str += '</div>';
   	        			
   	        		   return  str;
   	        	   }
   	           }
   	        	
   	         ],
		     "bServerSide": true,//这个用来指明是通过服务端来取数据
		     "sAjaxSource": "<%=basePath %>/admin/showcommendmetoo",//这个是请求的地址
		     "fnServerData": retrieveData, // 获取数据的处理函数
		} );
		//3个参数的名字可以随便命名,但必须是3个参数,少一个都不行
		  function retrieveData( sSource1113,aoData1113, fnCallback1113) {
			 // var ps = $('#ps').val();

		      $.ajax({
		          url : sSource1113,//这个就是请求地址对应sAjaxSource
		          data : {"aoData":JSON.stringify(aoData1113)
		        	 // "ps":ps
		        	  },//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
		          type : 'post',
		          dataType : 'json',
		          async : false,
		          success : function(result) {
		              fnCallback1113(result);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
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
