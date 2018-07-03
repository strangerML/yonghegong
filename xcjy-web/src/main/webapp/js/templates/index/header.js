$(function(){
	$("#btn-change-password").click(changePassword);
	resetPassword();
})


function resetPassword(){
	$("#oldPassword").val("");
	$("#password").val("");
	$("#confirmPassword").val("");
	$("#oldPassword").focus();
}

function goChangePwdPage(){
	resetPassword();
	$("#changePwdModal").modal('show');
}

function changePassword(){
	$.ajax({
		url : basePath+"changePassword",
		type : "post",
		data : $('#change-password-form').formSerialize(),
		success : function(data) {
			if (data.result == true) {
				alert("修改密码成功！");
				$("#changePwdModal").modal("hide");
			} else {
				alert("修改密码失败" + (data.msg ? "：" + data.msg : "！"));
			}
		}
	});
}