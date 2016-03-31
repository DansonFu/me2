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
		<h3 class="header smaller lighter blue">热门标签</h3>
		
		</div>
	
		<div class="table-responsive">
			<table id="sample-table-2" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center" >ID</th>
						<th class="center" >标签ID</th>
						<th class="center">标签名称</th>
						<th class="center">热度</th>
						<th class="center">帖子数</th>
						<th class="center">蜜友</th>
						<th class="center">更新时间</th>
						<th class="center">调整</th>
						<th class="center">删除</th>
					</tr>
				</thead>
					
				<tbody>
				</tbody>
				
			</table>
		</div>
	</div>
	</div>
	
<script type="text/javascript">


function up(id){
	window.location="<%=basePath %>admin/uprecommend?id="+id;
}

function down(id){
	window.location="<%=basePath %>admin/downrecommend?id="+id;
}
function del(id){
		if(confirm('确实删除该集合吗?')){
			window.location="<%=basePath %>admin/delrecommend?id="+id;
		}
	}
	$(document).ready(function(){
		var oTable1 = $('#sample-table-2').dataTable( {
			"bSort":false,
			"bFilter": false,
			"aoColumnDefs": [
      	          
    	        	{
				   	        	 "aTargets": [7],
				 	        	   "fnRender":function(data,type){
				 	        		   var str = '<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">';
				 	        			str += '<input type="button"  value="上调"  onclick="up('+"'"+data.aData[0]+"'"+')"/>';
				   	        			str += '</div>';
				   	        			str += '<input type="button"  value="下调"  onclick="down('+"'"+data.aData[0]+"'"+')"/>';
				   	        			str += '</div>';
				 	        			
				 	        		   return  str;
				 	        	   }
				   	           },
			    	         
				
				 {
				  	        	   "aTargets": [8],
				 	        	   "fnRender":function(data,type){
				       		  		 var str = '<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">';	
				   	        			str += '<a class="blue" onclick="del('+"'"+data.aData[0]+"'"+')" >';
										str += '<i class="icon-trash bigger-130"></i>';
										str += '</a>';
										str += '</div>';
					   					return  str;
				 }
				 } 
			    	         ],
			  "bServerSide": true,//这个用来指明是通过服务端来取数据
		     "sAjaxSource": "<%=basePath %>admin/getmetooByrecommend",  	//这个是请求的地址
		     "fnServerData": retrieveData, // 获取数据的处理函数
		} );
		//3个参数的名字可以随便命名,但必须是3个参数,少一个都不行
		  function retrieveData( sSource111,aoData111, fnCallback111) {
			      $.ajax({
		          url : sSource111,//这个就是请求地址对应sAjaxSource
		          data : {"aoData":JSON.stringify(aoData111)
		        	 
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
		
		  
	});
	
	
</script>

</body>
</html>