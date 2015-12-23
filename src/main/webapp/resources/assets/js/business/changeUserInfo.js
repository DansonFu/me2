function billReset(){
	document.getElementById("changePasswordForm").reset();
}

function billSubmit(){
	var name=$("#name").val();
	var phone=$("#phone").val();
	var email=$("#email").val();
	var idcard=$("#idcard").val();
	var sex=$("#sex").val();
	var qq=$("#qq").val();
	var address=$("#address").val();
	
	$.ajax({
		url:basePath+"erp/userInfo/changeUserInfo",
		type:"post",
		datatype:"json",
		data:{"name":name,"phone":phone,"email":email,"idcard":idcard,"sex":sex,"qq":qq,"address":address},
		success:function(data){
			if(data.message=="success"){
				fDialogAlert("修改成功");
			}else{
				fDialogAlert("修改失败");
			}
		}
    });
}