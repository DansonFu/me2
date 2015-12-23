//设置messager插件用的JQUERY版本
var $126 = $;

//定时读取报警信息
$(function(){
	//setInterval(getWarning,3000);

});
//弹出报警提示
function showWindows(){
	$126.messager.lays(300, 200);
	$126.messager.show('超标报警', 'XX设备粉尘超标，请注意处理！', 0);
}
function getWarning(){
	
	$126.messager.close();
	//设置1秒后再弹出提示，messager关闭时间需要0.6秒
	setTimeout('showWindows()',1000);
}

function toServices(url, id){
	$("#leftmenu li").removeClass("active");
	$("#"+id).parent().addClass('active');
	url = basePath +"erp/"+url;
	$("#main_target").attr("src", url);
	setIframeHeight();
}

//iframe 自适应高
function reinitIframeService(){
	var iframe = $("#main_target")[0];
	try{
		var height =  iframe.contentWindow.document.body.scrollHeight;
		if(height<480){
			iframe.height=480;
       	}else{
       		iframe.height = height;
           }
	}catch (ex){}
}

function setIframeHeight(){
	isIE11 = (navigator.userAgent.toLowerCase().indexOf("trident") > -1 && navigator.userAgent.indexOf("rv") > -1);
	if(navigator.userAgent.indexOf('MSIE') >= 0||isIE11){
		var iframe = $("#main_target")[0];
   		try{
   			var height =  iframe.contentWindow.document.body.scrollHeight;
   			if(height<480){
   				iframe.height=480;
           	}else{
           		iframe.height = height;
               }
   		}catch (ex){}
	}else{
		var mainheight = $("#main_target").contents().find("body").height();
    	if(mainheight<480){
    		mainheight=480;
    	}
    	$("#main_target").height(mainheight);
	}
}

$(function(){
	window.setInterval("setIframeHeight()", 200);
	
	$.ajax({
		url:basePath+"erp/message/getNewMessage",
		type:"post",
		datatype:"json",
		data: "",
		success:function(data){
			if(data.message!=null){
				$("#marqueeId").text(data.message.title);
				$("#marqueeId").attr("href","#");
				$("#marqueeId").attr("onclick","javascript:scrollShowMessage("+data.message.messageId+");");
			}else{
				$("#marqueeId").text("目前没有最新通知公告");
			}
		}
	});
});

function addressList(){
	$.fDialog({
		title : '<b>通讯录</b>',
		content : basePath+"erp/toAddressList",
		module : 'iframe',
		w : '80%',
		h : '80%',
		buttons : ['关闭'],
		beforeRemove : function($f){
			return true;
		},
		drag:true,
		footer : true, //不显示按钮栏
		shade : true //显示遮挡层
	});
}

function scrollShowMessage(messageId){
	$.fDialog({
		title : '<b>通知公告详情</b>',
		content : basePath+"erp/toShowMessage?messageId="+messageId,
		module : 'iframe',
		w : '80%',
		h : '80%',
		buttons : ['确定'],
		beforeRemove : function($f){
			return true;
		},
		drag:true,
		footer : true, //不显示按钮栏
		shade : true //显示遮挡层
	});
}

function changePassword(){
	$.fDialog({
		title : '<b>修改密码</b>',
		content : basePath+"erp/toChangePassword",
		module : 'iframe',
		w : '80%',
		h : '60%',
		beforeRemove : function($f){
			return true;
		},
		drag:true,
		footer : true, //不显示按钮栏
		shade : true //显示遮挡层
	});
}

function changeUserInfo(){
	$.fDialog({
		title : '<b>修改个人信息</b>',
		content : basePath+"erp/toChangeUserInfo",
		module : 'iframe',
		w : '80%',
		h : '60%',
		beforeRemove : function($f){
			return true;
		},
		drag:true,
		footer : true, //不显示按钮栏
		shade : true //显示遮挡层
	});
}