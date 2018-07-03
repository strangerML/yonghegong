$(function() {
	$('#operation-merge-modal').modal('show');
	$("#btn-operation-merge").click(operationMerge);
	operationValidator();
	bootstrapDateTime();
});
function bootstrapDateTime(){
	 $("#birthday").datetimepicker({
	      	minView: "month", //选择日期后，不会再跳转去选择时分秒 
			format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式 
			language: 'zh-CN', //汉化 
			autoclose:true //选择日期后自动关闭
	    });
}
function operationValidator() {
	$('#operation-merge-form').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			name : {
				validators : {
					notEmpty : {
						message : '操作名称不能为空！'
					}
				}
			},
			sn : {
				validators : {
					notEmpty : {
						message : '操作标识符不能为空!'
					}
				}
			}
		}
	});
}

function operationMerge() {
	$('#operation-merge-form').data('bootstrapValidator').validate();
	if ($('#operation-merge-form').data('bootstrapValidator').isValid() != true) {
		return;
	}
	$.ajax({
		url : "merge",
		type : "post",
		data : $('#operation-merge-form').formSerialize(),
		success : function(data) {
			if (data.result == true) {
				tblOption.ajax.reload();
				$("#operation-merge-modal").modal("hide");
			} else {
				alert("保存失败" + (data.msg ? "：" + data.msg : "！"));
			}
		}
	});

}