var tblOption;
$(function(){
	tblOption=$('#tbl-option').DataTable(buildOptions());
	trCssEvent();
	$("#role-permission-modal").load("goBindAuthPage");
	
	$("#btn-search-role").click(roleSearch);
	$("#btn-add-role").click(goAdd);
	$("#btn-update-role").click(goUpdate);
	$("#btn-auth-role").click(goBindAuthPage);
	$("#btn-del-role").click(goDel);
});

function buildOptions() {
	var options = {
		"searching" : false,
		"ordering" : true,
		"processing" : true,
		"language" : {
			"url" : basePath + 'js/datatables/chinese.json'
		},
		"serverSide" : true,
		"ajax" : {
			"url" : 'query',
			"type" : 'POST',
			data : function(params) {
				params['name'] = $("#search-name").val();
			}
		},
		"columns" : [ {
			"data" : "id"
		}, {
			"data" : "name"
		}, {
			"data" : "createTime"
		}, {
			"data" : "updateTime"
		},  {
			"data" : "remark"
		} ],
		columnDefs : [ {
			targets : 0,
			// 4个属性的意思是: data : 当前单元格的数据； type:
			// 当前列的类型；row: 当前行完整的数据对象；
			// meta: 为一个子对象,包含3个属性row: 当前行的索引；
			// col: 当前列的索引；
			// settings:当前DataTables控件的setttings对象
			render : function(data, type, row, meta) {
				return meta.row + 1;
			}
		}
		],
		"dom" : "<'row'<'col-xs-12't>><'row'<'col-xs-4'l><'col-xs-4'i><'col-xs-4'p>>"
	};
	return options;
}

function trCssEvent() {
	tblOption.on('click', 'tr', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        } else {
        	tblOption.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    });
}


function roleSearch(){
	tblOption.ajax.reload();
}

function goAdd(){
	$("#btn-add-role").attr("disabled", "disabled");
	var url = "goMergePage";
	$("#role-modal").load(url, function() {
		$("#btn-add-role").removeAttr("disabled");
	});
	
}

function goUpdate() {
	$("#btn-update-role").attr("disabled", "disabled");
	var rows = tblOption.row('.selected');
	if (rows.length != 1) {
		alert("请选择一条数据进行操作");
		$("#btn-update-role").removeAttr("disabled");
		return;
	}
	var url = "goMergePage";
	$("#role-modal").load(url, {
		id : rows.data().id
	}, function() {
		$("#btn-update-role").removeAttr("disabled");
	});
}

function goDel() {
    var rows = tblOption.row('.selected');
    if (rows.length != 1) {
        alert("请选择一条数据进行操作");
        return;
    }
    var flag = confirm("确定将删除本条数据么？");
    if (flag) {
        $.ajax({
            url: "del",
            data: {
                "id": rows.data().id
            },
            type: "post",
            success: function (data) {
                if (data.result == true) {
                	tblOption.ajax.reload();
                    alert("删除成功！");
                } else {
                    alert(data.msg == null ? "删除失败！" : data.msg);
                }
            }
        });
    }
}

function goBindAuthPage() {
    var rows = tblOption.row('.selected');
    if (rows.length != 1) {
        alert("请选择一条数据进行授权！");
        return;
    }
    $("input[id^='ckb_op_']").prop("checked", false);
    var roleId = rows.data().id;
    var roleName = rows.data().name;
    $('#role-permission-title').html("角色授权：" + roleName);
    $('#role-permission-id').val(roleId);
    $.ajax({
        //url: "role/findAllRolePermissions",
        url:"findAllRolePermissions",
    	data: {
            "id": roleId
        },
        type: "post",
        success: function (data) {
            if (data && data.result) {
                var pIds = data.permissionIds;
                for (var o in pIds) {
                    $("#ckb_op_" + pIds[o]).prop("checked", true);
                }
            } else {
                alert(data.msg ? data.msg : "获取角色权限失败！");
            }
            $('#role-permisson-modal').modal('show');
        }
    });
}
