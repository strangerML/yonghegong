$(function() {
	$('#contact-merge-modal').modal('show');
	$("#btn-contact-merge").click(contactMerge);
	contactValidator();
    
	
	
});


function contactValidator() {
	$('#contact-merge-form').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			officeName : {
				validators : {
					notEmpty : {
						message : '标题不能为空！'
					}
				}
			},
			tel: {
				validators : {
					notEmpty : {
						message : '内容不能为空!'
					}
				}
			}
		}
	});
}

function contactMerge() {
	$('#contact-merge-form').data('bootstrapValidator').validate();
	if ($('#contact-merge-form').data('bootstrapValidator').isValid() != true) {
		return;
	}
	$.ajax({
		url : "merge",
		type : "post",
		data : $('#contact-merge-form').formSerialize(),
		success : function(data) {
			if (data.result == true) {
				tblOption.ajax.reload();
				$("#contact-merge-modal").modal("hide");
			} else {
				alert("保存失败" + (data.msg ? "：" + data.msg : "！"));
			}
		}
	});

}