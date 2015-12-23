function mailSubmit(){
	var host=$("#host").val();
	var formName=$("#formName").val();
	var formPassword=$("#formPassword").val();
	var replayAddress=$("#replayAddress").val();
	$.ajax({
		url:basePath+"erp/setting/saveMailSetting",
		type:"post",
		datatype:"json",
		data: {"host":host,"formName":formName,"formPassword":formPassword,"replayAddress":replayAddress},
		success:function(data){
			fDialogAlert("保存成功");
		}
    });
}

function remindSubmit(){
	var sellTimeSolt=$("#sellTimeSolt").val();
	var sellInterval=$("#sellInterval").val();
	var buyTomeSolt=$("#buyTomeSolt").val();
	var buyInterval=$("#buyInterval").val();
	$.ajax({
		url:basePath+"erp/setting/saveRemindSetting",
		type:"post",
		datatype:"json",
		data: {"sellTimeSolt":sellTimeSolt,"sellInterval":sellInterval,"buyTomeSolt":buyTomeSolt,"buyInterval":buyInterval},
		success:function(data){
			fDialogAlert("保存成功");
		}
    });
}