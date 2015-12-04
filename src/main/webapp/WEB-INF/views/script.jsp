<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- basic styles -->
<link href="<%=basePath %>resources/assets/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=basePath %>resources/assets/css/font-awesome.min.css" />

<!--[if IE 7]>
  <link rel="stylesheet" href="<%=basePath %>resources/assets/css/font-awesome-ie7.min.css" />
<![endif]-->

<!-- page specific plugin styles -->
<link rel="stylesheet" href="<%=basePath %>resources/assets/css/jquery-ui-1.10.3.full.min.css" />
<!-- fonts -->

<!-- ace styles -->

<link rel="stylesheet" href="<%=basePath %>resources/assets/css/ace.min.css" />
<link rel="stylesheet" href="<%=basePath %>resources/assets/css/ace-rtl.min.css" />
<link rel="stylesheet" href="<%=basePath %>resources/assets/css/ace-skins.min.css" />

<!--[if lte IE 8]>
  <link rel="stylesheet" href="<%=basePath %>resources/assets/css/ace-ie.min.css" />
<![endif]-->

<!-- inline styles related to this page -->

<!-- ace settings handler -->
<script src="<%=basePath %>resources/assets/js/jquery-1.2.6.pack.js"></script>
<script src="<%=basePath %>resources/assets/js/jquery.messager.js"></script>
<script src="<%=basePath %>resources/assets/js/ace-extra.min.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

<!--[if lt IE 9]>
<script src="<%=basePath %>resources/assets/js/html5shiv.js"></script>
<script src="<%=basePath %>resources/assets/js/respond.min.js"></script>
<![endif]-->


<!-- basic scripts -->

<!--[if !IE]> -->

<script src="<%=basePath %>resources/assets/js/jquery.min.js"></script>

<!-- <![endif]-->

<!--[if IE]>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<![endif]-->

<!--[if !IE]> -->

<script type="text/javascript">
	window.jQuery || document.write("<script src='<%=basePath %>resources/assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='<%=basePath %>resources/assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

<script type="text/javascript">
	if("ontouchend" in document) document.write("<script src='<%=basePath %>resources/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
</script>
<script src="<%=basePath %>resources/assets/js/bootstrap.min.js"></script>
<script src="<%=basePath %>resources/assets/js/typeahead-bs2.min.js"></script>

<!-- page specific plugin scripts -->
<script src="<%=basePath %>resources/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="<%=basePath %>resources/assets/js/jquery.ui.touch-punch.min.js"></script>

<!-- ace scripts -->

<script src="<%=basePath %>resources/assets/js/ace-elements.min.js"></script>
<script src="<%=basePath %>resources/assets/js/ace.min.js"></script>

<!-- inline scripts related to this page -->

<script type="text/javascript">
<!--
	var basePath = "<%=basePath%>";
//-->
</script>



<!-- jQuery.fDialog.v1.0 -->
<link rel="stylesheet" href="<%=basePath %>resources/assets/js/jQuery.fDialog.v1.0/css/fDialog.css" />
<script src="<%=basePath %>resources/assets/js/jQuery.fDialog.v1.0/jquery.fDialog.min.js"></script>
<script src="<%=basePath %>resources/assets/js/jQuery.fDialog.v1.0/fDialog.plugin.js"></script>


<!-- 当前用户页面按钮dwr配置 -->
<script type='text/javascript' src='<%=basePath%>dwr/engine.js'></script>
<script type='text/javascript' src='<%=basePath%>dwr/util.js'></script>
<script type="text/javascript"	src="<%=basePath%>dwr/interface/userMenuButtonService.js"></script>
<script type="text/javascript">
function checkedButton(code){
	$.ajax({
		url : 'checkedButton',
		type : 'POST',
		dataType : 'text',
		async :false,
		data: "&menuCode="+code,
		success : function(jsonArray) {
			return "true"==jsonArray?true:false;
		}
	});	
}
	
	
</script>