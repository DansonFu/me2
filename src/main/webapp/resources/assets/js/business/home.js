$(function(){
	//获取最新通知信息
	$.ajax({
		url:basePath+"erp/message/getNewMessage",
		type:"post",
		datatype:"json",
		data: "type=0",
		success:function(data){
			if(data.message!=null){
				$("#messageHead0").text(data.message.title);
				$("#messageBody0").text(data.message.content);
			}else{
				$("#messageHead0").text("目前没有最新通知");
			}
		}
    });
	
	//获取最新公告信息
	$.ajax({
		url:basePath+"erp/message/getNewMessage",
		type:"post",
		datatype:"json",
		data: "type=1",
		success:function(data){
			if(data.message!=null){
				$("#messageHead1").text(data.message.title);
				$("#messageBody1").text(data.message.content);
			}else{
				$("#messageHead1").text("目前没有最新公告");
			}
		}
    });
});

//打印通讯录
function userInfoPrint(){
	$("#userInfoDiv").printArea({popTitle:"通讯录"});
}

function viewTaskForm(taskId){
	location.href = basePath + "erp/viewTaskForm?taskId="+taskId;
}