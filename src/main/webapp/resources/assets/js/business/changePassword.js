function billReset(){
	document.getElementById("changePasswordForm").reset();
}

function billSubmit(){
	var oldPassword=$("#oldPassword").val();
	var newPassword=$("#newPassword").val();
	var confirmPassword=$("#confirmPassword").val();
	if(oldPassword==null||oldPassword==""){
		fDialogAlert("请输入原密码");
		return;
	}
	if(newPassword==null||newPassword==""){
		fDialogAlert("请输入新密码");
		return;
	}
	if(confirmPassword==null||confirmPassword==""){
		fDialogAlert("请输入确认密码");
		return;
	}
	if(newPassword!=confirmPassword){
		fDialogAlert("新密码和确认密码不一致");
		return;
	}
	$.ajax({
		url:basePath+"erp/userInfo/changePassword",
		type:"post",
		datatype:"json",
		data:{"oldPassword":oldPassword,"newPassword":newPassword},
		success:function(data){
			if(data.message=="success"){
				fDialogAlert("修改成功，请重新登录");
				location.href=basePath+"erp/index";
			}else{
				fDialogAlert("修改失败，请联系管理员");
			}
		}
    });
}