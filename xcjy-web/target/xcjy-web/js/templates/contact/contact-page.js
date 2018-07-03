var tblOption;
$(function() {
	tblOption = $('#tbl-option').DataTable(buildOptions());
	trCssEvent();
	$("#btn-search-contact").click(optionSearch);
	$("#btn-add-contact").click(goAdd);
	$("#btn-update-contact").click(goUpdate);
	$("#btn-del-contact").click(goDel);
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
				params['officeName'] = $("#search-officeName").val();
			}
		},
		"columns" : [ {
			"data" : "officeName"
		}, {
			"data" : "tel"
		}, {
			"data" : "createTime"
		},{
			"data" : null
		}],
		columnDefs : [ 
		{
      			targets : 3,
      			render : function(data, type, row, meta) {
      				var id = row.id;
      				
      				return "<a onclick='check(\""+id+"\");' style='color:blue;' role='button'>查看</a>";
      			}
      		}
		
		],
		"dom" : "<'row'<'col-xs-12't>><'row'<'col-xs-4 lstyle'l><'col-xs-4'i><'col-xs-4'p>>"
	};
	return options;
}


/**
 * 根据id查看联系我们
 * @param id 新闻id
 */
function check(id){
	$("#btn-add-contact").attr("disabled", "disabled");
	var url = "check";
	$("#contact-modal").load(url, {
		id :id,
	}, function() {
		$("#btn-add-contact").removeAttr("disabled");
	});
}

function freeze(id){
	freezeOrUnFreeze(id,1);
}
function unfreeze(id){
	freezeOrUnFreeze(id,0);
}

function trCssEvent() {
	tblOption.on('click', 'tr', function() {
		if ($(this).hasClass('selected')) {
			$(this).removeClass('selected');
		} else {
			tblOption.$('tr.selected').removeClass('selected');
			$(this).addClass('selected');
		}
	});
}

function optionSearch() {
	tblOption.ajax.reload();
}

/**
 * 添加新闻
 */
function goAdd() {
	$("#btn-add-contact").attr("disabled", "disabled");
	var url = "goMergePage";
	$("#contact-modal").load(url,function() {
		$("#btn-add-contact").removeAttr("disabled");
	});

}

/**
 * 更新联系我们
 */
function goUpdate() {
	$("#btn-update-contact").attr("disabled", "disabled");
	var rows = tblOption.row('.selected');
	if (rows.length != 1) {
		alert("请选择一条数据进行操作");
		$("#btn-update-contact").removeAttr("disabled");
		return;
	}
	var url = "goMergePage";
	$("#contact-modal").load(url, {
		id : rows.data().id
	}, function() {
		$("#btn-update-contact").removeAttr("disabled");
	});
}

/**
 * 删除联系我们
 */
function goDel() {
	var rows = tblOption.row('.selected');
	if (rows.length != 1) {
		alert("请选择一条数据进行操作");
		return;
	}

	var flag = confirm("确定将删除本条数据么？");
	if (flag) {
		$.ajax({
			url : "del",
			data : {
				"id" : rows.data().id
			},
			type : "post",
			success : function(data) {
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
