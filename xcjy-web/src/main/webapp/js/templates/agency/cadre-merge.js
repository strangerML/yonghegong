$(function() {
	UE.delEditor('contents');
	var ue = UE.getEditor('contents');
	//初始化fileinput控件（第一次初始化）
	initFileInput("file-only", basePath + "upload?isSimditorUpload=1");
	$('#cadre-merge-modal').modal('show');
	$("#btn-cadre-merge").click(cadreMerge);
	cadreValidator();

});

//初始化fileinput控件（第一次初始化）
function initFileInput(ctrlName, uploadUrl) {    
    var control = $('#' + ctrlName); 
    //alert(ctrlName);
    //console.log(uploadUrl);
    control.fileinput({
 		language: 'zh', //设置语言
         uploadUrl: uploadUrl, //上传的地址
         allowedFileExtensions : ['jpg', 'png','gif'],//接收的文件后缀,
         maxFileCount: 10,
         showUpload: false, //是否显示上传按钮
         showCaption: false,//是否显示标题
         browseClass: "btn btn-primary", //按钮样式             
         previewFileIcon: "<i class='glyphicon glyphicon-king'></i>"
        
    });
    
    control.on("fileuploaded", function(event, data, previewId, index) {
    	var fileId = event.target.id;
    	var path = data.response.img_path;
    	if(fileId =="file-only"){
    		document.getElementById('pictures').value = path;
    	}
    })
}



function cadreValidator() {
	$('#cadre-merge-form').bootstrapValidator({
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
						message : '名称不能为空！'
					}
				}
			}
			
		}
	});
}

function cadreMerge() {
	$('#cadre-merge-form').data('bootstrapValidator').validate();
	if ($('#cadre-merge-form').data('bootstrapValidator').isValid() != true) {
		return;
	}
	$.ajax({
		url : "merge",
		type : "post",
		data : $('#cadre-merge-form').formSerialize(),
		success : function(data) {
			if (data.result == true) {
				tblOption.ajax.reload();
				$("#cadre-merge-modal").modal("hide");
			} else {
				alert("保存失败" + (data.msg ? "：" + data.msg : "！"));
			}
		}
	});

}