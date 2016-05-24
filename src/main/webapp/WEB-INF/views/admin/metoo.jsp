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
<style type="text/css">
	
	.box{
	
		width:100px;
		height:100px;
	}
</style>
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
                            <h2>发布密图</h2>
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
                                    	<img  src=" ${domain}/${customer.headimgurl}" width="70px" height="70px"/>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	ID：
                                    </div>
                                    <div class="col-lg-9">
                                    	<label>${customer.customerId }</label>
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
                                    <div class="col-lg-9" id="username">${customer.username }</div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	性别：
                                    </div>
                                    <div class="col-lg-9">
                                    	<c:if test="${customer.sex=='0' }">未知</c:if>
                                        <c:if test="${customer.sex=='1' }">男</c:if>
                                        <c:if test="${customer.sex=='2' }">女</c:if>
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
                        <h4>发布蜜图</h4>
                    </div>
                    <div class="panel-body">
                    	<form class="form-bordered"  id="me2form" action="<%=basePath %>admin/savemetoo" method="post" enctype="multipart/form-data">
                            <input type="hidden" value="${customer.customerId }" name="customerId" id="customerId" />
                            <div class="row form-group">
                            	<div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	<span class="dangger" style="color: red">*</span> A面：
                                    </div>
                                    <div class="col-lg-9">
                                    	 <input type="file" name="afile" id="upload1" onchange="loadImageFile(event)">
											
               						 <img id="image" src="" class="box"><br>
               						 <input type="button" value="验证图片的大小" id="aid" onclick="checkfile()">
               						 <input type="text" name="sname" id="sid" value=""><span>KB</span>
               						
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column" >
                                       	<span class="dangger" style="color: red">*</span>标签<span style="color:red">(半角符号 # 分隔，最多五个标签，每标签最多7个字)：</span>
                                    </div>
                                    <div class="col-lg-9">
                                    	<input type="text" name="tags" style="width:80%;" value="${customer.username } #"/>
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
                                    	<input type="text" name="locationTitle"  style="width:80%;"  placeholder="陆家嘴正大广场"/>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	地址：
                                    </div>
                                    <div class="col-lg-9">
                                    	<input type="text" name="locationContent" style="width:80%;"  placeholder="上海市浦东新区陆家嘴"/>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                            </div>
                            <!-- row -->
                            <div class="row form-group">
								<!-- col-lg-6 -->
                                
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	A面心情：
                                    </div>
                                    <div class="col-lg-9">
                                    	<input type="text" name="feel" style="width:80%;"  placeholder="A面心情不能超过77个字"/>
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                                <div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	解蜜游戏：
                                    </div>
                                    <div class="col-lg-9">
                                    	<select name="gameid" >
										<c:forEach items="${games}" var="game" varStatus="">
											<option value="${game.gameId }">${game.name }</option>
										</c:forEach>
										</select>
                                    </div>
                                </div>
                            </div>
                            <!-- row -->
                            <div class="row form-group">
                            	<div class="col-lg-6">
                                    <div class="col-lg-3 input-column">
                                       	B面：
                                    </div>
                                    <div class="col-lg-2">
                                    	<select name="type" onchange="changetype()">
                                    		
											<option value="1">图片</option>											 
											<option value="2">URL</option>
											<!-- <option value="3">视频</option>
											<option value="4">音频</option> -->
										</select>
                                    </div>
                                    <div class="col-lg-7" id="fileDiv" >
                                    	<input type="file" name="bfile" id="upload2" onchange="loadImageFile1(event)">

               						 <img id="image1" src="" class="box"><br>
               						 
               						  <input type="button" value="验证图片的大小" id="aid" onclick="checkfile1()">
               						 <input type="text" name="bsname" id="bsid" value=""><span>KB</span>
                                    </div>
                                    <div class="col-lg-7" id="textDiv" style="display: none;">
                                    	<input type="text" name="content" style="width:80%;" id="url" placeholder="http://www.baidu.com"/><br>
                                    	<input type="button" onclick="isurl()" value="验证url格式">
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                                <div class="col-lg-6" id="bfeelDiv">
                                    <div class="col-lg-3 input-column">
                                       	B面心情：
                                    </div>
                                    <div class="col-lg-9"><span style="color:red">心情文字最多77个字(包括标点符号)</span>
										<input type="text" name="bfeel" id="feelid" placeholder="B面心情必须有图片或者URL才可以添加哦!" style="width:80%;"/>
										
                                    </div>
                                </div>
                                <!-- col-lg-6 -->
                            </div>
                            <!-- row -->
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
                            
                        </form>
                    </div>
                    <!-- panel-body -->
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

	function changetype(){
		var type = $("select[name='type']").val();
		
		if(type=='1'||type=='3'||type=='4'){
			$("#fileDiv").css('display','block');
			$("#textDiv").css('display','none');
			$("#bfeelDiv").css('display','block');
		}else if(type=='2'){
			$("#fileDiv").css('display','none');
			$("#textDiv").css('display','block');
			$("#bfeelDiv").css('display','block');
			
		}
			
	};
	 
	
	function isurl(){  
		
	var str_url=$("input[type=text][name=content]").val();
	
	    var strregex = "^((https|http|ftp|rtsp|mms)?://)"   
	                    + "?(([0-9a-za-z_!~*'().&=+$%-]+: )?[0-9a-za-z_!~*'().&=+$%-]+@)?" //ftp的user@  
	                    + "(([0-9]{1,3}.){3}[0-9]{1,3}" // ip形式的url- 199.194.52.184  
	                    + "|" // 允许ip和domain（域名）  
	                    + "([0-9a-za-z_!~*'()-]+.)*" // 域名- www.  
	                    + "([0-9a-za-z][0-9a-za-z-]{0,61})?[0-9a-za-z]." // 二级域名  
	                    + "[a-za-z]{2,6})" // first level domain- .com or .museum  
	                    + "(:[0-9]{1,4})?" // 端口- :80  
	                    + "((/?)|"   
	                    + "(/[0-9a-za-z_!~*'().;?:@&=+$,%#-]+)+/?)$";  
	    var re=new RegExp(strregex); 
	    if (re.test(str_url)){ 
	    	alert("url格式正确");
	      return (true);  
	    	 }else{  
	    	alert("url格式不正确");	 
	      return (false);  
	   } 
	};
	function submitform(){
		
		if($("input[name='afile']").val()==null||$("input[name='afile']").val()==""){
			$("input[name='afile']").focus();
			return;
		}
		
		if($("input[name='tags']").val()==null||$("input[name='tags']").val()==""){
			$("input[name='tags']").focus();
			return;
		}
		
		var arr = $("input[name='tags']").val().split("#"); 
		var username=$("#username").text().trim();
		if(arr.length>5){
			$("input[name='tags']").focus();
			alert("最多添加5个标签");
			return;
		}
		for(var i=0;i<arr.length;i++){
			if(arr[i].length>7 && arr[i].trim()!=username){
				$("input[name='tags']").focus();
				alert("除昵称外每个标签最多7个字");
				return;
			}
		
		}
		var afeel=$("input[name='feel']").val();
		var bfeel=$("input[name='bfeel']").val();
		if(afeel.length>77){
			$("input[name='feel']").focus();
			alert("心情文字最多77个字");
			return;
		}
		if(bfeel.length>77){
			$("input[name='bfeel']").focus();
			alert("心情文字最多77个字");
			return;
		}
		$("#me2form").submit();
		$(":button").attr("disabled", true); 
		
	};
	 function loadImageFile(event){
			
             var image = document.getElementById('image');

             image.src = URL.createObjectURL(event.target.files[0]); 
        
     }; 
     function loadImageFile1(event){

         var image = document.getElementById('image1');

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
     function checkfile1(){  
    	
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
        	 
        		 var obj_file = document.getElementById("upload2");  
        	 
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
                
                	$("#bsid").val(ssize);
                 
                 return;  
             }  
         }catch(e){  
             alert(e);  
         }  
     }  ;
    
</script>		
	</body>
</html>

