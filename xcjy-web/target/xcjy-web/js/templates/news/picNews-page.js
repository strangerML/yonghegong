var tblOption;
$(function() {
	tblOption = $('#tbl-option').DataTable(buildOptions());
	trCssEvent();
	$("#btn-search-picNews").click(optionSearch);
	$("#btn-add-picNews").click(goAdd);
	$("#btn-update-picNews").click(goUpdate);
	$("#btn-del-picNews").click(goDel);
	$("#btn-release-picNews").click(goRelease);
	$("#btn-relieveRelease-picNews").click(goRelieveRelease);
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
			"data" : null
		},{
			"data" : "name"
		}, {
			"data" : "createTime"
		},{
			"data" : null
		},{
			"data" : null
		}],
		columnDefs : [ 
		     {
			  	targets : 0,
					render : function(data, type, row, meta) {
						var id = row.id;
						return "<input type='checkbox' name='' value='"+id+"' class='check_class' />";
						
					}
				 }, 
			 {
		    	targets : 3,
				render : function(data, type, row, meta) {
					if(row.status=="0"){
						return "不显示";
					}else if(row.status=="1"){
						return "显示";
					}
					
				}
			}, 
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
	$("#btn-add-picNews").attr("disabled", "disabled");
	var url = "check";
	$("#picNews-modal").load(url, {
		id :id,
	}, function() {
		$("#btn-add-picNews").removeAttr("disabled");
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
	$("#btn-add-picNews").attr("disabled", "disabled");
	var url = "goMergePage";
	$("#picNews-modal").load(url,{ 
		picNewsTypeId : $("#picNewsTypeId").val(),
	},function() {
		$("#btn-add-picNews").removeAttr("disabled");
	});

}

/**
 * 更新新闻
 */
function goUpdate() {
	$("#btn-update-picNews").attr("disabled", "disabled");
	var rows = tblOption.row('.selected');
	if (rows.length != 1) {
		alert("请选择一条数据进行操作");
		$("#btn-update-picNews").removeAttr("disabled");
		return;
	}
	var url = "goMergePage";
	$("#picNews-modal").load(url, {
		id : rows.data().id
	}, function() {
		$("#btn-update-picNews").removeAttr("disabled");
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


function goRelease() {
	var checkedObjs = $(".check_class:checked");
	
	if (checkedObjs.length == 0) {
		alert("请选择一条或多条数据！");
		return;
	}
	var buffer = new StringBuffer();
    for(var i = 0;i<checkedObjs.length;i++){
    	var aa = $(checkedObjs[i]).val();
    	buffer.append(aa);
    	if(i+1!=checkedObjs.length){
    		buffer.append(",");
    	}
    }
    
    var ids = buffer.toString();
	var flag = confirm("确定在主页显示本图片么？");
	if (flag) {
		$.ajax({
			url : "releasePicNews",
			data : {
				"picNewsIds" : ids
			},
			type : "post",
			success : function(data) {
				if (data.result == true) {
					tblOption.ajax.reload();
					alert("显示成功！");
				} else {
					alert(data.msg == null ? "显示失败！" : data.msg);
				}
			}
		});
	}

}
function StringBuffer() {
    this.__strings__ = new Array();
}
StringBuffer.prototype.append = function (str) {
    this.__strings__.push(str);
    return this;    //方便链式操作
}
StringBuffer.prototype.toString = function () {
    return this.__strings__.join("");
}
function goRelieveRelease() {
	var checkedObjs = $(".check_class:checked");
	
	if (checkedObjs.length == 0) {
		alert("请选择一条或多条数据！");
		return;
	}
	var buffer = new StringBuffer();
    for(var i = 0;i<checkedObjs.length;i++){
    	var aa = $(checkedObjs[i]).val();
    	buffer.append(aa);
    	if(i+1!=checkedObjs.length){
    		buffer.append(",");
    	}
    }
    
    var ids = buffer.toString();
	var flag = confirm("确定将本不在主页显示么？");
	if (flag) {
		$.ajax({
			url : "relieveReleasePicNews",
			data : {
				"picNewsIds" : ids
			},
			type : "post",
			success : function(data) {
				if (data.result == true) {
					tblOption.ajax.reload();
					alert("解除主页显示！");
				} else {
					alert(data.msg == null ? "解除主页显示失败！" : data.msg);
				}
			}
		});
	}

}