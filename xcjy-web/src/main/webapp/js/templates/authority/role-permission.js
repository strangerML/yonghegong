$(function() {
	findAllModulePermission();
	$("#btn-checkbox-all").click(chball);
	$("#btn-checkbox-other").click(chbother);
	$("#btn-permission-save").click(permissionSave);
});

function findAllModulePermission() {
	$.ajax({
		url : "findAllModulePermission",
		type : "get",
		success : function(data) {
			createModuleTree(data, 10);
		}
	});
}

function createModuleTree(data, paddingLeft) {
	if (data) {
		for ( var o in data) {
			var html = '';
			html += '<tr><td ';
			if (paddingLeft) {
				html += ' style="padding-left:' + paddingLeft + 'px;"';
			}
			html += '>' + data[o].name + '</td>';
			//console.log(o);
			//console.log(data[o].isLeaf);
			//console.log(data[o].pageOperations);
			if (data[o].isLeaf == 1 && data[o].pageOperations) {
				html += '<td>';
				var pageOperations = data[o].pageOperations;
				for ( var op in pageOperations) {
					html += '<label class="checkbox-inline"><input type="checkbox" ';
					var opid = pageOperations[op].permissionId;
					html += ' id="ckb_op_' + opid
							+ '" name="permissionIds" value="' + opid;
					html += '"> ' + pageOperations[op].name
							+ '</input></label>';
				}
				html += '&nbsp;</td>';
			} else {
				html += '<td>&nbsp;</td>';
			}
			html += '</tr>';
			$("#module-tree").append(html);
			if (data[o].children) {
				createModuleTree(data[o].children, paddingLeft + 35);
			}
		}
	}
}

function chball() {
	$("input[id^='ckb_op_']").prop("checked", true);
}
function chbother() {
	$("input[id^='ckb_op_']").each(function() {
		if ($(this).prop("checked")) {
			$(this).prop("checked", false);
		} else {
			$(this).prop("checked", true);
		}
	});
}

function permissionSave() {
	$.ajax({
		url : "permissionSave",
		type : "post",// 必须加，否则会乱码
		data : $('#role-permission-form').formSerialize(),
		success : function(data) {
			if (data.result == true) {
				alert("授权成功！");
				$("#role-permisson-modal").modal("hide");
			} else {
				alert("保存失败" + (data.msg ? "：" + data.msg : "！"));
			}
		}
	});
}
