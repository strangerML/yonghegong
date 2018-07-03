$(function(){
	//alert(212);
	//var tags=document.getElementById("slideBox")//获取HTML的所有标签 
	
	//tags.slide({mainCell:".bd ul",autoPlay:true});
	$(".slideBox").slide({
		mainCell:".bd ul",
		autoPlay:true
	});
	setTimeout(mapbegin(),5000);
 });

function mapbegin(){
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	var point = new BMap.Point(116.366115,39.946057);
	map.centerAndZoom(point, 16);
	var marker = new BMap.Marker(point);  // 创建标注
	map.addOverlay(marker);              // 将标注添加到地图中

	var label = new BMap.Label("",{offset:new BMap.Size(8,8)});
	marker.setLabel(label);
}