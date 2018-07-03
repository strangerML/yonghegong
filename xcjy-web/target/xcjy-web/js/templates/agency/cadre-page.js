var tblOption;
$(function() {
	tblOption = $('#tbl-option').DataTable(buildOptions());
	trCssEvent();
	$("#btn-search-cadre").click(optionSearch);
	$("#btn-add-cadre").click(goAdd);
	$("#btn-update-cadre").click(goUpdate);
	$("#btn-del-cadre").click(goDel);
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
				params['cadreType'] = $("#cadreType").val();
			}
		},
		"columns" : [ {
			"data" : "name"
		}, {
			"data" : "publishUserName"
		}, {
			"data" : "createTime"
		}, {
			"data" : "cadreCount"
		},{
			"data" : null
		}],
		columnDefs : [ 
		   
		    {
      			targets : 4,
      			render : function(data, type, row, meta) {
      				var id = row.id;
      				
      				return "<a href='check?id="+id+"' target='_blank' style='color:blue;'>预览</a>";
      			}
      		}
		
		],
		"dom" : "<'row'<'col-xs-12't>><'row'<'col-xs-4 lstyle'l><'col-xs-4'i><'col-xs-4'p>>"
	};
	return options;
}


/**
 * 根据id查看新闻详情
 * @param id 新闻id
 */
function check(id){
	$("#btn-add-cadre").attr("disabled", "disabled");
	var url = "check";
	$("#cadre-modal").load(url, {
		id :id,
	}, function() {
		$("#btn-add-cadre").removeAttr("disabled");
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
	$("#btn-add-cadre").attr("disabled", "disabled");
	var url = "goMergePage";
	$("#cadre-modal").load(url,{ 
		cadreType : $("#cadreType").val(),
	},function() {
		$("#btn-add-cadre").removeAttr("disabled");
	});

}

/**
 * 更新新闻
 */
function goUpdate() {
	$("#btn-update-cadre").attr("disabled", "disabled");
	var rows = tblOption.row('.selected');
	if (rows.length != 1) {
		alert("请选择一条数据进行操作");
		$("#btn-update-cadre").removeAttr("disabled");
		return;
	}
	var url = "goMergePage";
	$("#cadre-modal").load(url, {
		id : rows.data().id
	}, function() {
		$("#btn-update-cadre").removeAttr("disabled");
	});
}

/**
 * 删除新闻
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
