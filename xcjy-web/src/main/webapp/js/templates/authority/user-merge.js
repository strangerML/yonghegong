var userId = $('#id').val();
$(document).ready(function() {
	$('#user-merge-modal').modal('show');
	$("#user-save").click(mergeUser);
	userValidateFn(); 
});

function userValidateFn(){
	$('#user-merge-form').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            userName: {
                message: '用户名不合法',
                validators: {
                    notEmpty: {
                        message: '必填项，不能为空!'
                    }
                }
            },
			roleId: {
                validators: {
                    notEmpty: {
                        message: '必填项，不能为空!'
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: '必填项，不能为空!'
                    },
                    emailAddress: {
                        message: '输入的不是一个有效的电子邮件地址'
                    }
                }
            },
            phone: {
                validators: {
                    notEmpty: {
                        message: '必填项，不能为空!'
                    },
                    digits: {
                        message: '只能包含数字的值'
                    },
                    regexp: {
                        regexp:  /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/,
                        message: '手机号无效!'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '必填项，不能为空!'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: '密码和确认密码是不一样的'
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: '必填项，不能为空!'
                    },
                    identical: {
                        field: 'password',
                        message: '密码和确认密码是不一样的'
                    }
                }
            },
            remark: {
                validators: {
                	stringLength: {
                        min: 0,
                        max: 30,
                        message: '小于30个字符'
                    }
                }
            }
            
        }
    });
}


function userValidateF2(){
	$('#user-merge-form').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            userName: {
                message: '用户名不合法',
                validators: {
                    notEmpty: {
                        message: '必填项，不能为空!'
                    }
//	                remote: {
//	                    url: 'user/checkName?userId='+userId,
//	                    message: '用户名已存在！'
//	                }
                    /*stringLength: {
                        min: 6,
                        max: 30,
                        message: '用户名必须超过6,小于30个字符长'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '用户名只能由字母、数字、点和下划线'
                    }*/
                }
            },
			roleId: {
                validators: {
                    notEmpty: {
                        message: '必填项，不能为空!'
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: '必填项，不能为空!'
                    },
                    emailAddress: {
                        message: '输入的不是一个有效的电子邮件地址'
                    }
                }
            },
            phone: {
                validators: {
                    notEmpty: {
                        message: '必填项，不能为空!'
                    },
                    digits: {
                        message: '只能包含数字的值'
                    },
                    regexp: {
                        regexp:  /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/,
                        message: '手机号无效!'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '必填项，不能为空!'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: '密码和确认密码是不一样的'
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: '必填项，不能为空!'
                    },
                    identical: {
                        field: 'password',
                        message: '密码和确认密码是不一样的'
                    }
                }
            },
            remark: {
                validators: {
                	stringLength: {
                        min: 0,
                        max: 30,
                        message: '小于30个字符'
                    }
                }
            }
            
        }
    });
}

function mergeUser() {
    $('#user-merge-form').data('bootstrapValidator').validate();
	if ($('#user-merge-form').data('bootstrapValidator').isValid() != true) {
		return;
	}
	 $.ajax({
         url : "merge",
         type : "post",// 必须加，否则会乱码
         data : $('#user-merge-form').formSerialize(),
         success : function(data) {
             if (data.success == true) {
             	tblOption.ajax.reload();
                 $("#user-merge-modal").modal("hide");
             } else {
                 alert("保存失败" + (data.msg ? "：" + data.msg : "！"));
             }
         }
     });
    
}
