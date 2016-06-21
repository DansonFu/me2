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
<link href="<%=basePath %>resources/assets/css/responsive.bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=basePath %>resources/assets/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=basePath %>resources/assets/css/font-awesome.min1.css" />
<link rel="stylesheet" href="<%=basePath %>resources/assets/css/dataTables.bootstrap.css" />
<link rel="stylesheet" href="<%=basePath %>resources/assets/css/dataTables.fontAwesome.css" />
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
<style type="text/css">
	
	.box{
	
		width:100px;
		height:100px;
	}
</style>
</head>
<body>
<div class="page-content">
	<div class="row">
	<div class="col-xs-12">
		<h3 class="header smaller lighter blue">广告图片</h3>
		<br>
		<br>
		
		<!-- 
		<div class="input-append">
                                    <input type="text" placeholder="模糊查询" id="fuzzy-search">
                                    <div class="btn-group">
                                        <button type="button" class="btn" id="btn-simple-search"><i class="fa fa-search"></i></button>
                                        <button type="button" class="btn" title="高级查询" id="toggle-advanced-search">
                                            <i class="fa fa-angle-double-down"></i>
                                        </button>
                                    </div>
                                </div>
                 -->                
                      
                                
        <button type="button" class="btn btn-primary" id="btn-add"><i class="fa fa-plus"></i> 添加</button>                
        
        <div style="display:none;" id="user-add">
        
        <div class="block info-block"  style="display:none;">
        
                        
        
                  <form class="form-bordered" id="me2form" action="<%=basePath %>admin/saveadvertisaaa" method="post" enctype="multipart/form-data">
                  
                            <div class="header-buttons">
                               <button type="button" class="btn btn-primary" id="btn-save-add" onclick="submitform()">确定添加</button>
                               <button type="button" class="btn btn-cancel" id="btn-cancel">取消</button>
                            </div>
                  
                            <div class="control-group">
                                <label class="control-label" for="extn-add"><span
                                    class="red-asterisk"></span>图片</label>
                                <div class="col-lg-9">
                                    	 <input type="file" name="afile" id="upload1" onchange="loadImageFile(event)">
											
               						 <img id="image" src="" class="box"><br>
               						 <input type="button" value="验证图片的大小" id="aid" onclick="checkfile()">
               						 <input type="text" name="sname" id="sid" value=""><span>KB</span>
               						
                                </div>
                            </div>
                  </form>                                            
		
		</div>
		</div>
		
		<div class="table-responsive">
			<table id="sample-table-21" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
					    
					   <th class="center">主键id</th>
						<th class="center" >
							图片
						</th>
						
						
						<th class="center">操作</th>
						
				
					</tr>
				</thead>
	
				<tbody>
				</tbody>
				
				<!-- 
				<tfoot>
        <tr role="row">
            <th>主键ID</th>
            <th>蜜图ID</th>
            <th>A面</th>
            <th>排序</th>
            <th>截止有效期</th>
            <th>操作</th>
            <th>有效期限(天)</th>
            <th class="table-checkbox">
                <input type="checkbox" class="group-checkable" data-set="#sample-table-2 .checkboxes" />
            </th>
        </tr>
    </tfoot>
              -->
    
			</table>
		</div>
	</div>
	</div>
</div>

<script type="text/javascript">
	
	
function del(id){
	if(confirm('确实删除该蜜图吗?')){
		window.location="<%=basePath %>admin/del/advertis?id="+id;
	}
}

function up(id){
	window.location="<%=basePath %>admin/upadvertis?id="+id;
}

function down(id){
	window.location="<%=basePath %>admin/downadvertis?id="+id;
}

function loadImageFile(event){
    var image = document.getElementById('image');
    image.src = URL.createObjectURL(event.target.files[0]); 
};

function checkfile(){  
	var maxsize = 2*1024*1024;//2M  
    var errMsg = "上传的附件文件不能超过2M！！！";  
    var tipMsg = "您的浏览器暂不支持计算上传文件的大小，确保上传文件不要超过2M，建议使用IE、FireFox、Chrome浏览器。";  
    var  browserCfg = {};  
    var ua = window.navigator.userAgent;  
    if (ua.indexOf("MSIE")>=1){  
        browserCfg.ie = true;  
    }else if(ua.indexOf("Firefox")>=1){  
        browserCfg.firefox = true;  
    }else if(ua.indexOf("Chrome")>=1){  
        browserCfg.chrome = true;  
    }  
    try{  
   		 var obj_file = document.getElementById("upload1");  
   	 if(obj_file.value==""){  
            alert("请先选择上传文件");  
            return;  
        }  
        var filesize = 0;  
        if(browserCfg.firefox || browserCfg.chrome ){  
            filesize = obj_file.files[0].size;  
        }else if(browserCfg.ie){  
            var obj_img = document.getElementById('tempimg');  
            obj_img.dynsrc=obj_file.value;  
            filesize = obj_img.fileSize;  
        }else{  
            alert(tipMsg);  
        return;  
        }  
        if(filesize==-1){  
            alert(tipMsg);  
            return;  
        }else if(filesize>maxsize){  
            alert(errMsg);  
            return;  
        }else{  
            var ssize=filesize/1024;
            $("#sid").val(ssize);
            return;  
        }  
    }catch(e){  
        alert(e);  
    }  
} ;

//$("#btn-save-add").click(function(){
//	 window.location="<%=basePath %>admin/advertis";
    //addItemInit();
// });

$("#btn-add").click(function(){
	$("#user-add").show();
    //addItemInit();
});
$("#btn-cancel").click(function(){
	$("#user-add").hide();
    //addItemInit();
});

$(document).ready(function(){
	 
	    // alert("skjafffffffffffffffffffff");
		var oTable1 = $('#sample-table-21').dataTable( {
			
			"bSort":true,
			"bFilter": false,
			//"pagingType":   "full_numbers",
			"aoColumnDefs": [
			                 
           {
	           //"sWidth": "5%",
	           "aTargets": [0],
           },
	
              
   	           {
   	        	   "aTargets": [1],
   	        	   "fnRender":function(data,type){
   	        		   return  '<img src="'+data.aData[1]+'" width="100px" height="100px">';
   	        	   }
   	           },
   	           {
   	        	 "aTargets": [2],
 	        	   "fnRender":function(data,type){
 	        		   var str = '<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">';
 	        			//str += '<a class="blue" href="javascript:;" onclick="view('+"'"+data.aData[0]+"'"+')">';
 	        			//str += '<i class="icon-zoom-in bigger-130"></i>';
 	        			//str += '</a>';
 	        			str += '<input type="button"  value="上调"  onclick="up('+"'"+data.aData[0]+"'"+')"/>';
 	        			str += '<br>';
   	        			str += '<input type="button"  value="下调"  onclick="down('+"'"+data.aData[0]+"'"+')"/>';
   	        			str += '<br>';
   	        			str += '<input type="button"  value="删除"  onclick="del('+"'"+data.aData[0]+"'"+')"/>';
   	        			str += '</div>';
 	        			
 	        		    return  str;
 	        	   }
   	           },
   	          
   	         ],
		     "bServerSide": true,//这个用来指明是通过服务端来取数据
		     "sAjaxSource": "<%=basePath %>/admin/getAdvertis",//这个是请求的地址
		     "fnServerData": retrieveData, // 获取数据的处理函数
		    
		} );
		// oTable1 .bSort( [[ 1, 'desc' ]] )  .draw( false );
		 //alert("skjaf");
		//3个参数的名字可以随便命名,但必须是3个参数,少一个都不行
		  function retrieveData( sSource1113,aoData1113, fnCallback1113) {
			 // var ps = $('#ps').val();
			var fuzzy = $("#fuzzy-search").val();
			// alert(aoData1113);
	       
		      $.ajax({
		          url : sSource1113,//这个就是请求地址对应sAjaxSource
		          data : {"aoData":JSON.stringify(aoData1113),
		        	 // "ps":ps
		        	  "fuzzy":fuzzy
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
		      
		     // alert("000000000000000");
		  }
		//$("#ps").bind("change", function(){
			 // oTable1.fnPageChange('first');
		 // });
		  $("#btn-simple-search").click(function(){
		        userManage.fuzzySearch = true;
		 
		      //  reload效果与draw(true)或者draw()类似,draw(false)则可在获取新数据的同时停留在当前页码,可自行试验
		     // _table.ajax.reload();
		     // _table.draw(false);
		      //  oTable1.draw();
		  });
	});
	
	
function submitform(){
	
	if($("input[name='afile']").val()==null||$("input[name='afile']").val()==""){
		$("input[name='afile']").focus();
		return;
	}
	
	$("#me2form").submit();
	$(":button").attr("disabled", true); 
	
};
    //function addItemInit() {
     //    $("#form-add")[0].reset();

     //    $("#user-add").show().siblings(".info-block").hide();
  //  };
	
	
</script>		
	</body>
</html>
