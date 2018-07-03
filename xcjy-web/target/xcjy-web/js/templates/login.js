$("html").addClass("login-bg");
$("html").css("background-image",
		"url(" + basePath + "images/bgs/landscape.jpg)");

$(function(){
	changeCaptcha();
	$("#accountNum").focus();
	
	$("html").keydown(function() {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $('#btn_login').click();
        }
    });
});

function changeCaptcha() {
	$("#img_captcha").attr(
			"src",
			basePath + "kaptcha.jpg?temp="
					+ (new Date().getTime().toString(36)));
}