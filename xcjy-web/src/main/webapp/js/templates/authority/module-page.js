$(function(){
	$('#mm').menu({});
	$('#tt').tree({
		url:'getModules' ,
		animate:true,
		loadFilter: function(rows){
			return convert(rows);
		},
		onContextMenu: function(e,node){
			e.preventDefault();
			$(this).tree('select',node.target);
			if(node.parentId){//不是根节点
				$("#addRootMenu").css("display","none");
				if(node.isLeaf == 1){
					console.log(2);
					$("#addMenu").css("display","none");
					$("#mm").css("height","51px");
				}
				else{
					$("#addMenu").css("display","block");
					$("#mm").css("height","71px");
				}
			}
			else{//根节点
				$("#addRootMenu").css("display","block");
				$("#mm").css("height","91px");
			}
			$('#mm').menu('show',{
				left: e.pageX,
				top: e.pageY
			});
		},

	});

});

function append(){
	var node = $('#tt').tree('getSelected');
	var ps = "";
	if(node.parentId){
		ps = "?isLeaf=1";
	}else{
		ps = "?isLeaf=0";
	}
	var url = "goMerge"+ps;
	$("#modal").load(url,function(){
		//form  validator-------------------
		validator();
		$('#myModal').modal('show');
		$("#parentName").val(node.text);
		$("#parentId").val(node.id);
		if(node.parentId){
			$("#isLeaf").val(1);
			$("#moduleUrl").show();
		}else{
			$("#isLeaf").val(0);
		}
	});

}
/**
 * 新增根节点
 */
function appendRoot(){
	var node = $('#tt').tree('getSelected');
	var url = "goMerge";
	$("#modal").load(url,function(){
		//form  validator-------------------
		validator();
		$('#myModal').modal('show');
		$("#parentNameDiv").hide();
		$("#isLeaf").val(0);
	});

}

function modify(){
	var node = $('#tt').tree('getSelected');
	var ps = "";
	if(node.parentId){
		ps = "&isLeaf=1";
	}else{
		ps = "&isLeaf=0";
	}
	var url = "goMerge?id="+node.id+ps;
	$("#modal").load(url,function(){
		//form  validator-------------------
		validator();
		$('#myModal').modal('show');
		if(node.parentId){
			$("#moduleUrl").show();
		}
	});
}
function del(){
	var node = $('#tt').tree('getSelected');
	var id = node.id;
	var text = "确定将删除本条数据么？"
	if(node.isLeaf !=1){
		text = "删除本条数据，对应的子节点也将被删除，确定删除么？";
	}
	var flag = confirm(text);
	if(flag){
		$.ajax({
			url: "del",
			data: {
				"id": id
			},
			success: function (data) {
				if(data.result){
					$('#tt').tree('reload');
				}else{
					alert(data.msg);
				}

			}
		});
	}
}

function save(){
	$('#mudule-add-form').data('bootstrapValidator').validate();
	setTimeout("secondStep()", 300);


}
function secondStep(){
	if($('#mudule-add-form').data('bootstrapValidator').isValid()==true){
		$.ajax({
			url:"merge" ,
			type:"post",//必须加，否则会乱码
			data: $('#mudule-add-form').formSerialize(),
			success: function (data) {
				$('#tt').tree('reload');
				$("#myModal").modal("hide");
			}
		});
	}
}


function convert(rows){
	function exists(rows, parentId){
		for(var i=0; i<rows.length; i++){
			if (rows[i].id == parentId) return true;
		}
		return false;
	}

	var nodes = [];
	// get the top level nodes
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		if (!exists(rows, row.parentId)){
			nodes.push({
				id:row.id,
				text:row.name,
				isLeaf:row.isLeaf,
				parentId:row.parentId
			});
		}
	}

	var toDo = [];
	for(var i=0; i<nodes.length; i++){
		toDo.push(nodes[i]);
	}
	while(toDo.length){
		var node = toDo.shift();	// the parent node
		// get the children nodes
		for(var i=0; i<rows.length; i++){
			var row = rows[i];
			if (row.parentId == node.id){
				var child = {id:row.id,text:row.name,isLeaf:row.isLeaf,parentId:row.parentId};
				if (node.children){
					node.children.push(child);
				} else {
					node.children = [child];
				}
				toDo.push(child);
			}
		}
	}
	return nodes;
}

function validator(){
	$('#mudule-add-form').bootstrapValidator({
		message: '此数据填写不合法',
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			name: {
				message: '权限名称不合法',
				validators: {
					notEmpty: {
						message: '必填项，不能为空!'
					},

				}
			}/*,
			sn: {
				validators: {
					notEmpty: {
						message: '必填项，不能为空!'
					}
					,
					remote: {
						url: 'checkSN?id='+$('#moduleId').val(),
						message: '权限标识重复！'
					}
				}
			}*/
		}
	});

}
