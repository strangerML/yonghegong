$(function() {
	bootstrapDateTime();
	UE.delEditor('contents');
	var ue = UE.getEditor('contents');
	//初始化fileinput控件（第一次初始化）
	initFileInput("file-only", basePath + "upload?isSimditorUpload=1");
	//初始化微信上传封面图片
	initPicInput("pic-only", basePath + "upload?isSimditorUpload=1");
	//逻辑删除微信封面图片
	$("#btn-del").click(remove);
	
	$('#news-merge-modal').modal('show');
	$("#btn-news-merge").click(newsMerge);
	newsValidator();

});

//时间控件
function bootstrapDateTime(){
	 $("#createTime").datetimepicker({
	      	//选择日期后，跳转去选择时分秒 
		 	format: 'yyyy-mm-dd hh:ii:ss',
		    language: 'zh-CN',
		    autoclose:true,
		    startDate:new Date(),
	 		autoclose:true ,//选择日期后自动关闭
	    }).on("click",function(){
	    	//时间选择器datetimepicker
	        $("#createTime").datetimepicker("setStartDate")
	    });
		
}

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
    		document.getElementById('fileNames').value = fileName;
    	}
    })
}

//初始化微信上传封面图片
function initPicInput(ctrlName, uploadUrl) {    
    var control = $('#' + ctrlName); 
    control.fileinput({
 		 language: 'zh', //设置语言
         uploadUrl: uploadUrl, //上传的地址
         allowedFileExtensions : ['jpg', 'png','gif'],//接收的文件后缀,
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
    	if(fileId =="pic-only"){
    		document.getElementById('picUrl').value = path;
    	}
    })
}
//逻辑删除微信封面图片
function remove(){
	console.log($("#picUrl").val());
	$("#picUrl").val("");
	document.getElementById("delImgId").src = "";
	console.log($("#picUrl").val());
}


function newsValidator() {
	$('#news-merge-form').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			newsTitle : {
				validators : {
					notEmpty : {
						message : '标题不能为空！'
					}
				}
			},columnType: {
				validators : {
					notEmpty : {
						message : '所属栏目不能为空!'
					}
				}
			}
		}
	});
}

function newsMerge() {
	$('#news-merge-form').data('bootstrapValidator').validate();
	if ($('#news-merge-form').data('bootstrapValidator').isValid() != true) {
		return;
	}
	$.ajax({
		url : "merge",
		type : "post",
		data : $('#news-merge-form').formSerialize(),
		success : function(data) {
			if (data.result == true) {
				tblOption.ajax.reload();
				$("#news-merge-modal").modal("hide");
			} else {
				alert("保存失败" + (data.msg ? "：" + data.msg : "！"));
			}
		}
	});

}