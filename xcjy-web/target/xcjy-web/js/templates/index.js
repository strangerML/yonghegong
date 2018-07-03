
var firstLeftMenu;

$(function() {
	$('#main-frame').load(mainFrameLoad);
})

function mainFrameLoad() {
	var contentH = $(
			document.getElementById('main-frame').contentWindow.document.body)
			.height();
	$('#main-frame').css("height", (contentH < 500 ? 500 : contentH) + "px"); // 
}

function showMain(url) {
	$('#main-frame').attr("src", url);
}