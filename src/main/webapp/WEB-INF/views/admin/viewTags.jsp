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
<meta name="save" content="history">   
<style type="text/css">   
    input{behavior: url (#default#behaviorName)};   
 </style> 
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
		<h3 class="header smaller lighter blue">所有标签<input type="text" name="name" value="${name}" style="border:0px"></h3>
		<input type="hidden" id="flagid" value="${flag }" name="flag"/>
		<div style="float:left;">
		<input type="button" name="" value="查看热门标签页面" onclick="see()"/>
		</div>
		</div>
		<form action="<%=basePath %>admin/getmetooByTags" id="wayid" method="post">
		 <div class="col-lg-6">
		 	<div style="float:right;">
                        <div class="col-lg-3 input-column">
                                       	排序方式：
                                    </div>
                                    <div class="col-lg-9">
                                    	<select id="projectPorperty" name="projectPorperty">  
                                    	 	 <option value="-1">全部</option> 
										    <option value="1">更新时间</option> 
										    <option value="2">热度</option>  
										    <option value="5">标签id</option>  
										    <option value="3">帖子数</option>  
										    <option value="4">蜜友</option> 
										</select>  
										
										</div>
                                    </div>
                                </div>
                              </form>  
		<form action="<%=basePath %>admin/viewflash" id="formid" method="post">
		<div style="float:right;">
			
          		                                                
				<button class="btn" type="submit" id="flashid" onclick="flash()">
				刷新数据
				</button>
		</div>
		</form>
		
		<form action="<%=basePath %>admin/add" id="submitid" method="post">
		<div style="float:right;" id="list">
		<input type="text" name="search" id="math" style="border:0px"/>		
				<input type=text name="arr" id="arrid" style="border:0px"/>
				
				<button class="btn" type="submit" >
				<i class="icon-ok bigger-80"></i>
				提交
				</button>
		</div>
		</form>
		<form action="<%=basePath %>admin/addtag" id="submitid" method="post">
		<div style="float:right;"id="tag">
		<input type="text" name="search" id="mathtag" style="border:0px"/>		
				<input type=text name="arr" id="arridtag" style="border:0px"/>
				
				<button class="btn" type="submit" >
				<i class="icon-ok bigger-80"></i>
				提交
				</button>
		</div>
		</form>
		
		<form action="<%=basePath %>admin/viewTags" id="formid" method="post">
		<div style="float:left;">
		 &nbsp; &nbsp;<input type="text" name="search"/>
			<input type="hidden"  id="searchid" value="${svalue }" />
				<button class="btn" type="submit">
				检索
				</button><span style="color:red">(请输入你想查询的标签名称)</span>
		</div>
		</form>
				
		<div class="table-responsive">
			<table id="sample-table-2" class="table table-striped table-bordered table-hover" >
				<thead>
					<tr>	
						<th class="center">标签ID</th>
						<th class="center">标签名称</th>
						<th class="center">热度</th>
						<th class="center">帖子数</th>
						<th class="center">蜜友</th>
						<th class="center">密图</th>
						<th class="center">更新时间</th>
						<th class="center">添加到精选集合</th>
						<th class="center">添加到热门标签</th>
					</tr>
				</thead>
				<tbody>
				</tbody>				
			</table>					
		</div>
	</div>
	</div>
	<input type="button" style="display: none" id="bu" name="bu" onclick="but()">
	<input type="hidden"  id="aid" name="aname" value="${avalue }">		
<script type="text/javascript">
/* flash();
function flash(){
	document.getElementById("flashid").click();
}
 */
//创建一个数组
var test = [];
//ad方法接收复选框的id,判断后将选中的复选框的id添加到input标签中,提交到后台的方法
function ad(id){
	test.push(id);
	
	
	var b=[];
	for(var i=0;i<test.length;i++){
	    var bFound = false;
	   
	    for(var j=i-1;j>=0;j--){
	        if(test[i]==test[j]) {
	        	test.splice(i,1);
	        	test.splice(j,1);
	        	
	        	bFound=true;
	        	break;
	        	}
	    }
	    if(!bFound) b=test;
	    b.length=test.length;
	}
	$("#arrid").val(b);
	
	$("#math").val(b.length);
	 if(b.length>10){
			alert("最多可以选择10个标签哟!");
		b.splice(b.length-1,1);
		$("#arrid").val(b);
		
		$("#math").val(b.length);
	 }
}; 
//adtag方法为获取复选框的id,存入test数组,判断之后将id存入input域中,提交到后台的方法中
function adtag(id){
	test.push(id);
	
	//去重复的和相同值的方法
	var b=[];
	for(var i=0;i<test.length;i++){
	    var bFound = false;
	    for(var j=i-1;j>=0;j--){
	        if(test[i]==test[j]) {
	        	test.splice(i,1);
	        	test.splice(j,1);
	        	//alert(test);
	        	bFound=true;
	        	break;
	        	}
	    }
	    if(!bFound) b=test;
	    b.length=test.length;
	}
	$("#arridtag").val(b);
	
	$("#mathtag").val(b.length);
	 if(b.length>10){
			alert("最多可以选择10个标签哟!");
		b.splice(b.length-1,1);
		$("#arridtag").val(b);
		
		$("#mathtag").val(b.length);
	 }
}; 

/**
 * 全选反选
 */
/* function checkAll() {
	var code_Values = document.getElementsByName("check");
	for (i = 0; i < code_Values.length; i++) {
		if (code_Values[i].type == "checkbox") {
			code_Values[i].checked = true;
			ad(i+1);
		
		}else if(code_Values[i].checked = false){
			
		}
		
	}
} */
/* function checkAll() {
	 var m = document.getElementsByName('check');
    var l = m.length;
  
    for ( var i=0; i< l; i++)
    {
        m[i].checked == true
            ? m[i].checked = false
            : m[i].checked = true;
}
  
} */
/**
 * 去除数组重复元素
 */
function uniqueArray(data){  
  data = data || [];  
  var a = {};  
  for (var i=0; i<data.length; i++) {  
      var v = data[i];  
      if (typeof(a[v]) == 'undefined'){  
           a[v] = 1;  
      }  
  };  
  data.length=0;  
  for (var i in a){  
       data[data.length] = i;  
  }  
  return data;  
}  ;

sub();
//判断两个提交的按钮的隐藏和显示
function sub(){
	//alert(1);
	 var currentBtn1 = document.getElementById("tag");
	 var currentBtn2 = document.getElementById("list");
	var name=$("#flagid").val();
	
	if (name == "1") {
        currentBtn2.style.display = "none";
       // alert(1);
    }
    else if(name=="2"){
    	//alert(2);
        currentBtn1.style.display = "none"; //style中的display属性
    }
	};
function see(){
	window.location="<%=basePath %>admin/viewrecommend";
}
	$(document).ready(function(){
		
		//3个参数的名字可以随便命名,但必须是3个参数,少一个都不行
		  function retrieveData( sSource111,aoData111, fnCallback111) {
			  var searchid= $('#searchid').val();
			  var font = $('#projectPorperty').val();
			  
			      $.ajax({
		          url : "<%=basePath %>admin/getmetooByTags",//sSource111,//这个就是请求地址对应sAjaxSource
		         data : {"aoData":JSON.stringify(aoData111),
		        	 "searchid":searchid,
		        	"font":font
		        	
		        	  },//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
		          type : 'post',
		          dataType : 'json',
		          async : false,
		          success : function(result) {
		        	 //alert(JSON.stringify(result));
		        	 // datas = result["aaData"][0];
		        	  
		        	  fnCallback111(result);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
		        	  //alert(result);
		          },
		          error : function(msg) {
		        	  
		          }
		      });
		  }
		
		
		var oTable1 = $('#sample-table-2').dataTable( {
			"bSort":false,
			"bFilter":false,
			"bPaginate":true,
			//"data": datas,
			"bInfo":true,
			"aoColumns": [
						{ "asSorting": [ "desc", "asc"] },
						{ "asSorting": [ "desc", "asc"] },
			              { "asSorting": [ "desc", "asc"] },
			              { "asSorting": [ "desc", "asc"] },
			              { "asSorting": [ "desc", "asc"] },
			              { "asSorting": [ "desc", "asc"] },
			            ],
			"aoColumnDefs": [               
{
	     	        	  
	     	        	   "aTargets": [5],
	     	        	   "fnRender":function(data,type){
	     	        		   return  '<img src="'+data.aData[5]+'" width="100px" height="100px">';
	     	        	   }
	     	           }, 
    	        	{
    	        	   "aTargets": [7],
    	        	 
    	        	   "fnRender":function(data,type){
    	        		   var str = '<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">';
    	        			str += '<input type="checkbox" name="check" id="btn" onclick="ad ('+"'"+data.aData[0]+"'"+')"/>';
	    	        		str += '</div>';
    	        		   return  str;
    	        		  
    	        	   }
			    	           }, 
				
				{
					"aTargets" : [8],
					 
					"fnRender" : function(data,type) {
						var str = '<div  class="visible-md visible-lg hidden-sm hidden-xs action-buttons">';
						str += '<input type="checkbox" name="check" id="btn" onclick="adtag('+"'"+data.aData[0]+"'"+')"/>';
  	        			str += '</div>';
  	        			
  	        			
						return str;
					}
				}
			    	         ],
			  "bServerSide": true ,//这个用来指明是通过服务端来取数据
		  "sAjaxSource": "<%=basePath %>/admin/getmetooByTags",//这个是请求的地址	
		      "fnServerData": retrieveData // 获取数据的处理函数
		     
		} );
		//oTable1.fnClearTable(false);
		but();//datatable执行之后调一次but方法
		 
		  $("#projectPorperty").bind("change", function(){
			  oTable1.fnPageChange('first');
		  });

		  //$("input[name='check']").bind("click",function(){self.checkClick(){
			  
				/* $("input[name='check']").click(function(){//获取checkbox的个数
					 var str=0;
				
					var s="";
					 str=$("input[type=checkbox][name='check']:checked").length;
					 var coo =document.setCookie(str);
					 $("#math").val(str);
			
		  }); */
		//判断两列复选框的隐藏和显示
			 	 function but(){
					//alert(1);
					var name=$("#flagid").val();
					var a="1";
					var b="2";
				if(name==b && name==a){
					name==b;
				
				}
					 if(name==b){
						//alert(1);
			oTable1.fnSetColumnVis(8, false);
					}
					 else if(name==a){
						//alert(1);
			 oTable1.fnSetColumnVis(7, false);
					}
					};
			
			  
	});
	
	
		
	
	
</script>

</body>
</html>