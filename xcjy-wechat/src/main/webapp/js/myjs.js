/*********************************
Design by Jason.leung,
QQ,147430218
鏂版氮寰崥,@鍒囩墖楹靛寘
Email, jason.leung58@icloud.com
*********************************/
/* 閰风珯浠ｇ爜鏁寸悊 杞浇璇锋敞鏄庡嚭澶� http://www.5icool.org */
window.onload=function()
{
	var dealy=3000;				//3绉掓痪鍕曚竴寮犲湒鐗囷紝鍙嚜琛屼慨鏀�
	var oBox=document.getElementById('box');
	var aList=document.getElementById('list');
	var aNum=document.getElementById('num').getElementsByTagName('li');
	var timer=null;
	var now=0;
	info();
	for (var i=0; i<aNum.length; i++)
	{
		aNum[i].index=i;
		aNum[i].onmouseover=function()
		{
			now=this.index;
			play(Running);
		}
	}
	function play(fn)
	{
		for (var i=0; i<aNum.length; i++)
		{
			aNum[i].className='';
		}
		aNum[now].className='active';
		fn(aList,{top:-170*now});			
	}
	function auto()
	{
		clearInterval(timer);
		timer=setInterval(function()
		{
			now++;
			if(now==aNum.length)
			{
				now=0;
			}
			play(Flexing);
		}, dealy);
	}
	auto();
	oBox.onmouseover=function()
	{
		clearInterval(timer);
	}
	oBox.onmouseout=function()
	{
		auto();
	}
}
// JavaScript Document