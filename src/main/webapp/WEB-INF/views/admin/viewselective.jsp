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
<h3 class="header smaller lighter blue">已选标签&nbsp;<input value="${name }" style="border:0px" /></h3>
			
				
				<%--<<form action="<%=basePath %>admin/getmetoo/selective" id="reagain" method="post">
				<div style="float: left;">
				<br/>
				button class="btn btn-primary" type="submit" onclick="reset()">
					<i class="icon-undo bigger-60"></i>
					恢复默认设置
				</button>
				</div>
				</form>  --%> 
		<div style="float: right;">
		<br/>
			<button class="btn btn-primary" type="submit" onclick="submit()">
					<i class="icon-ok bigger-80"></i>
					提交
				</button>
				</div>
				
			<form action="<%=basePath %>admin/viewTags" id="formid" method="post">
				<div style="float: right;">
				<input type="hidden" name="taglist" value="${tid }" id="tid"/>
				<input type="hidden" name="conn" value="${conn }" id="cid"/>
				<input type="hidden" name="flag" value="${flag }"/>
				&nbsp; &nbsp; &nbsp;
				<br/>
				<button class="btn" type="submit" onclick="add()">
					<i class="icon-undo bigger-80"></i>
					添加标签
				</button>
				</div>
				</form>
<form class="form-bordered"  id="sub" action="<%=basePath %>admin/submit" method="post" enctype="multipart/form-data">
				
		<div>
	
		<br>
		
		<div class="table-responsive">
			<table id="sample-table-2" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center" >
							标签ID
						</th>
						<th class="center">标签名称</th>
						<th class="center">帖子数</th>
						<th class="center">热度</th>
						<th class="center">蜜友</th>
						<th class="center">更新时间</th>
						<th class="center">操作</th>
						</tr>
					</thead>
	
					<tbody>
					</tbody>
				</table>
				</div>
			</div>
	</form>

<script type="text/javascript">
		
	function del(id){
		if(confirm('确实删除该标签吗?')){
			window.location="<%=basePath %>admin/delselective?id="+id;
		}
	}

	$(document).ready(function(){
		var oTable1 = $('#sample-table-2').dataTable( {
			"bSort":false,
			"bFilter": false,
			"aoColumnDefs": [
   	          
   	        	
   	        	{
   	        	   "aTargets": [6],
   	        	   "fnRender":function(data,type){
   	        		   var str = '<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">';
	        		
   	        			str += '<a class="red" href="javascript:void(0);" onclick="del('+"'"+data.aData[0]+"'"+')" >';
   	        			str += '<i class="icon-trash bigger-130"></i>';
   	        			str += '</a>';
   	        			str += '</div>';
   	        		   return  str;
   	        	   }
   	           }
   	         ],
		     "bServerSide": true,//这个用来指明是通过服务端来取数据
		     "sAjaxSource": "<%=basePath %>/admin/getmetoo/selective",//这个是请求的地址
		     "fnServerData": retrieveData, // 获取数据的处理函数
		} );
		//3个参数的名字可以随便命名,但必须是3个参数,少一个都不行
		  function retrieveData( sSource111,aoData111, fnCallback111) {
			 // var addid = document.getElementById("addid").value;
				var tid=$('#tid').val();
				var cid=$('#cid').val();
		      $.ajax({
		          url : sSource111,//这个就是请求地址对应sAjaxSource
		          data : {"aoData":JSON.stringify(aoData111),
		        	  "tid":tid,
		        	  "cid":cid
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
	function submit(){
		

		$("#sub").submit();
		$(":button").attr("disabled", true);  
	}
	function add(){
		$("#formid").submit();
		$(":button").attr("disable",true);
	}
function reagain(){
		

		$("#reset").submit();
		$(":button").attr("disabled", true);  
	}
</script>

</body>
</html>