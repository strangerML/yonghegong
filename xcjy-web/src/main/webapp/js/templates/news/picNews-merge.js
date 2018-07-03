$(function() {
	UE.delEditor('content');
	var ue = UE.getEditor('content');
	//初始化fileinput控件（第一次初始化）
	initFileInput("file-only", basePath + "upload?isSimditorUpload=1");
	bootstrapDateTime();
	$('#picNews-merge-modal').modal('show');
	$("#btn-picNews-merge").click(picNewsMerge);
	picNewsValidator();

});

//初始化fileinput控件（第一次初始化）
function initFileInput(ctrlName, uploadUrl) {    
    var control = $('#' + ctrlName); 
    //alert(ctrlName);
    //console.log(uploadUrl);
    control.fileinput({
 		language: 'zh', //设置语言
         uploadUrl: uploadUrl, //上传的地址
         //allowedFileExtensions : ['jpg', 'png','gif'],//接收的文件后缀,
         maxFileCount: 10,
         showUpload: false, //是否显示上传按钮
         showCaption: true,//是否显示标题
         dropZoneEnabled: false,//是否显示拖拽区域
         browseClass: "btn btn-primary", //按钮样式             
         //previewFileIcon: "<i class='glyphicon glyphicon-king'></i>"
        
    });
    
    control.on("fileuploaded", function(event, data, previewId, index) {
    	var fileId = event.target.id;
    	var path = data.response.img_path;
    	var fileName = data.response.file_name;
    	if(fileId =="file-only"){
    		document.getElementById('pictures').value = path;
    	}
    })
}

function bootstrapDateTime(){
	 $("#createTime").datetimepicker({
	      	minView: "month", //选择日期后，不会再跳转去选择时分秒 
	 		format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式 
	 		language: 'zh-CN', //汉化 
	 		autoclose:true //选择日期后自动关闭
	    });
}

function picNewsValidator() {
	$('#picNews-merge-form').bootstrapValidator({
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
						message : '标题不能为空！'
					}
				}
			}
		}
	});
}

function picNewsMerge() {
	$('#picNews-merge-form').data('bootstrapValidator').validate();
	if ($('#picNews-merge-form').data('bootstrapValidator').isValid() != true) {
		return;
	}
	$.ajax({
		url : "merge",
		type : "post",
		data : $('#picNews-merge-form').formSerialize(),
		success : function(data) {
			if (data.result == true) {
				tblOption.ajax.reload();
				$("#picNews-merge-modal").modal("hide");
			} else {
				alert("保存失败" + (data.msg ? "：" + data.msg : "！"));
			}
		}
	});

}