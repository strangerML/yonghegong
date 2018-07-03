var tblOption;
$(function() {
	tblOption = $('#tbl-option').DataTable(buildOptions());
	trCssEvent();
	$("#btn-search-news").click(optionSearch);
	$("#btn-add-news").click(goAdd);
	$("#btn-update-news").click(goUpdate);
	$("#btn-del-news").click(goDel);
	$("#btn-freeze-news").click(goFreeze);
	$("#btn-toFreeze-news").click(goRelieveFreeze);
	
	
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
				params['newsTitle'] = $("#search-newsTitle").val();
				params['newsTypeId'] = $("#newsTypeId").val();
			}
		},
		"columns" : [ {
			"data" : null
		},{
			"data" : "newsTitle"
		}, {
			"data" : "createTime"
		}, {
			"data" : "newsCount"
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
		    	targets : 4,
				render : function(data, type, row, meta) {
					if(row.postState=="0"){
						return "正常";
					}else if(row.postState=="1"){
						return "冻结";
					}
					
				}
			}, 
		    {
      			targets : 5,
      			render : function(data, type, row, meta) {
      				var id = row.id;
      				if(row.newsTypeId=="1"){
      					return "<a href='goCenterNews?id="+id+"' target='_blank' style='color:blue;'>预览</a>";
      				}else if(row.newsTypeId=="2"){
      					return "<a href='findNewsDetails?id="+id+"' target='_blank' style='color:blue;'>预览</a>";
      				}else if(row.newsTypeId=="3"){
      					return "<a href='findNewsDetails?id="+id+"' target='_blank' style='color:blue;'>预览</a>";
					}else if(row.newsTypeId=="4"){
						return "<a href='findNewsDetail?id="+id+"' target='_blank' style='color:blue;'>预览</a>";
					}else if(row.newsTypeId=="5"){
						return "<a href='findNewsDetailss?id="+id+"' target='_blank' style='color:blue;'>预览</a>";
					}else if(row.newsTypeId=="6"){
						return "<a href='findAdultDetails?id="+id+"' target='_blank' style='color:blue;'>预览</a>";
					}else if(row.newsTypeId=="7"){
						return "<a href='findMiddleDetails?id="+id+"' target='_blank' style='color:blue;'>预览</a>";
					}else if(row.newsTypeId=="8"){
						return "<a href='findHighsDetails?id="+id+"' target='_blank' style='color:blue;'>预览</a>";
					}else if(row.newsTypeId=="9"){
						return "<a href='findSelfsDetails?id="+id+"' target='_blank' style='color:blue;'>预览</a>";
					}else if(row.newsTypeId=="10"){
						return "<a href='findSchoolsDetails?id="+id+"' target='_blank' style='color:blue;'>预览</a>";
					}else if(row.newsTypeId=="11"){
						return "<a href='findJuniorDetails?id="+id+"' target='_blank' style='color:blue;'>预览</a>";
					}
      			}
      		}
		
		],
		"dom" : "<'row'<'col-xs-12't>><'row'<'col-xs-4 lstyle'l><'col-xs-4'i><'col-xs-4'p>>"
	};
	return options;
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
	$("#btn-add-news").attr("disabled", "disabled");
	var url = "goMergePage";
	$("#news-modal").load(url,{ 
		newsTypeId : $("#newsTypeId").val(),
	},function() {
		$("#btn-add-news").removeAttr("disabled");
	});

}

/**
 * 更新新闻
 */
function goUpdate() {
	$("#btn-update-news").attr("disabled", "disabled");
	var rows = tblOption.row('.selected');
	if (rows.length != 1) {
		alert("请选择一条数据进行操作");
		$("#btn-update-news").removeAttr("disabled");
		return;
	}
	var url = "goMergePage";
	$("#news-modal").load(url, {
		id : rows.data().id
	}, function() {
		$("#btn-update-news").removeAttr("disabled");
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

function goFreeze() {
	
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
	var flag = confirm("确定将所选数据冻结么？");
	if (flag) {
		
		$.ajax({
			url : "freezeNews",
			data : {
				"newsIds" : ids
			},
			type : "post",
			success : function(data) {
				if (data.result == true) {
					tblOption.ajax.reload();
					alert("冻结成功！");
				} else {
					alert(data.msg == null ? "冻结失败！" : data.msg);
				}
			}
		});
	}
    
}

function goRelieveFreeze() {
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
	var flag = confirm("确定将本条数据解除冻结么？");
	if (flag) {
		$.ajax({
			url : "relieveFreezeNews",
			data : {
				"newsIds" : ids
			},
			type : "post",
			success : function(data) {
				if (data.result == true) {
					tblOption.ajax.reload();
					alert("解除冻结成功！");
				} else {
					alert(data.msg == null ? "解除冻结失败！" : data.msg);
				}
			}
		});
	}

}