$(function() {
	$('#role-merge-modal').modal('show');
	$("#role-save").click(mergeRole);
});

function mergeRole() {
	$.ajax({
		url : "merge",
		type : "post",// 必须加，否则会乱码
		data : $('#role-merge-form').formSerialize(),
		success : function(data) {
			if (data.result == true) {
				tblOption.ajax.reload();
				$("#role-merge-modal").modal("hide");
			} else {
				alert("保存失败" + (data.msg ? "：" + data.msg : "！"));
			}
		}
	});
}