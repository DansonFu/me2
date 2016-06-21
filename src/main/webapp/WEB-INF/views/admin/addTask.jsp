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
<script src="<%=basePath %>resources/assets/js/jiaoben3174/datetime.js"></script>
<script src="<%=basePath %>resources/assets/js/laydate.js"></script>
</head>
<body>

                    <div class="panel-body">
                    	<form class="form-bordered"  id="me2form" action="<%=basePath %>admin/addTask" method="post" enctype="multipart/form-data">
                                  	新建任务：
                                    <div>
                                   	id:&nbsp;&nbsp;&nbsp;<input type="hidden" id="id" name="id" value="${task.id}">
                                   <div>
										任务名称:<input type="text" name="title"  value="${task.title}"/>
                        			</div>
                        			 <div>
										任务说明:<input type="text" name="content"  value="${task.content}"/>
                        			</div>
                        			 <div>
										任务类型:<input type="text" name="tasktype"  value="${task.tasktype}"/>
										<span style="color:red">(1.系统任务 2.个性任务)</span>
                        			</div>
                        			 <div>
										任务类型 :<input type="text" name="taskstyle"  value="${task.taskstyle}"/>
										<span style="color:red">(1.长期任务 2.一次性任务)</span>
                        			</div>
                        			 <div >
										任务发布时间:<input placeholder="请输入日期" class="laydate-icon" name="createTime"
										onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                        			</div>
                        			 <div>
										任务过期时间:<input placeholder="请输入日期" class="laydate-icon" name="lastTime"
										onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                        			</div>
                        			 <div>
										花费的糖块:<input type="text" name="custom"  value="${task.custom}"/>
                        			</div>
                        			<div>
										奖励的糖块:<input type="text" name="award"  value="${task.award}"/>
                        			</div>
                                   	</div>
										 <br/>
                                  
			               <div  style="text-align: center">
							<div >
								<button class="btn btn-primary" type="submit" onclick="submitform()">
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
	<script>
!function(){
laydate.skin('molv');//切换皮肤，请查看skins下面皮肤库
laydate({elem: '#demo'});//绑定元素
}();
//日期范围限制
var start = {
    elem: '#start',
    format: 'YYYY-MM-DD',
    min: laydate.now(), //设定最小日期为当前日期
    max: '2099-06-16', //最大日期
    istime: true,
    istoday: false,
    choose: function(datas){
         end.min = datas; //开始日选好后，重置结束日的最小日期
         end.start = datas //将结束日的初始值设定为开始日
    }
};
var end = {
    elem: '#end',
    format: 'YYYY-MM-DD',
    min: laydate.now(),
    max: '2099-06-16',
    istime: true,
    istoday: false,
    choose: function(datas){
        start.max = datas; //结束日选好后，充值开始日的最大日期
    }
};
laydate(start);
laydate(end);
//自定义日期格式
laydate({
    elem: '#test1',
    format: 'YYYY年MM月DD日',
    festival: true, //显示节日
    choose: function(datas){ //选择日期完毕的回调
        alert('得到：'+datas);
    }
});
//日期范围限定在昨天到明天
laydate({
    elem: '#hello3',
    min: laydate.now(-1), //-1代表昨天，-2代表前天，以此类推
    max: laydate.now(+1) //+1代表明天，+2代表后天，以此类推
});
</script>       		
<script type="text/javascript">

$("#back").on("click",function(){
	window.location="<%=basePath %>admin/viewTasks";
});

	function submitform(){
		if($("input[name='title']").val()==""){
			$("input[name='title']").focus();
			return;
		}
		if($("input[name='title']").val()==null||$("input[name='title']").val()==""){
			$("input[name='title']").focus();
			return;
		}
		if($("input[name='content']").val()==""){
			$("input[name='content']").focus();
			return;
		}
		if($("input[name='content']").val()==null||$("input[name='content']").val()==""){
			$("input[name='content']").focus();
			return;
		}
		if($("input[name='tasktype']").val()==""){
			$("input[name='tasktype']").focus();
			return;
		}
		if($("input[name='tasktype']").val()==null||$("input[name='tasktype']").val()==""){
			$("input[name='tasktype']").focus();
			return;
		}
		if($("input[name='taskstyle']").val()==""){
			$("input[name='taskstyle']").focus();
			return;
		}
		if($("input[name='taskstyle']").val()==null||$("input[name='taskstyle']").val()==""){
			$("input[name='taskstyle']").focus();
			return;
		}
		if($("input[name='createTime']").val()==""){
			$("input[name='createTime']").focus();
			return;
		}
		if($("input[name='createTime']").val()==null||$("input[name='createTime']").val()==""){
			$("input[name='createTime']").focus();
			return;
		}
		if($("input[name='lastTime']").val()==""){
			$("input[name='lastTime']").focus();
			return;
		}
		if($("input[name='lastTime']").val()==null||$("input[name='lastTime']").val()==""){
			$("input[name='lastTime']").focus();
			return;
		}
		if($("input[name='custom']").val()==""){
			$("input[name='custom']").focus();
			return;
		}
		if($("input[name='custom']").val()==null||$("input[name='custom']").val()==""){
			$("input[name='custom']").focus();
			return;
		}
		if($("input[name='award']").val()==""){
			$("input[name='award']").focus();
			return;
		}
		if($("input[name='award']").val()==null||$("input[name='award']").val()==""){
			$("input[name='award']").focus();
			return;
		}
		
		

		$("#me2form").submit();
		$(":button").attr("disabled", true);  
	}
	
</script>

</body>
</html>