var healthSwiper;
var i=0;
$(function() {
	$('#page-params').val('0,10');//轮播图下方列表值

	//healthSwiper = buildSwiper('news-fixed');//轮播图方法
	getNewsData('暂无数据……');
});

/**
 * 构建Swiper
 * 
 * @param domId：元素id
 * @returns {Swiper}
 */
/*function buildSwiper(domId) {
	var  height = window.innerHeight;
	var  width = window.innerWidth;
	var percentage = 0.10;
	var newHeight = height * percentage;
	console.log(newHeight);
	return new Swiper('#' + domId, {
		width : width,
		height : newHeight,
		speed : 300,// 滑动速度，即slider自动滑动开始到结束的时间（单位ms）。 默认：300
		autoplay : 5000,// 可选选项，自动滑动。自动切换的时间间隔（单位ms），不设定该参数slide不会自动切换。默认： 0
		autoplayDisableOnInteraction : false,// 用户操作swiper之后，是否禁止autoplay。默认为true：停止。false，用户操作swiper之后自动切换不会停止，每次都会重新启动autoplay。操作包括触碰，拖动，点击pagination等。
		autoplayStopOnLast : false,// 如果设置为true，当切换到最后一个slide时停止自动切换。（loop模式下无效）。默认：false
		pagination : '.swiper-pagination',
		loop : true,
		spaceBetween : 10,
		paginationClickable : true
	});
}
*/
/**
 * 动态加载下拉列表数据
 * 
 * @param hintstr
 */
function getNewsData(hintstr) {
	$('#btn-more').hide();//隐藏input
	$('#news-hint').hide();//隐藏input
	$('#data-loading').show();//显示div

	var pageParams = $('#page-params').val().split(",");//获取分页参数
	var startNum = pageParams[0];//分页起始值
	var lengthNum = pageParams[1];//分页数据长度
	if (lengthNum % 2 != 0) {
		lengthNum++;
	}
	//ajax请求数据
	$.ajax({
		url : basePath + "news/getMainNews",
		type : "post",
		data : {
			newsType : 3,
			start : startNum,
			length : lengthNum
		},
		success : function(data) {
			//判断data（总数据） 和 data.pageData（分页数据）是否为空
			if (data && data.pageData) {
				var dataArr = data.pageData;
				//判断分页数据长度是否大于0，大于0遍历
				
				if (dataArr.length > 0) {
					// 下面使用each进行遍历
					$.each(dataArr, function(n, newsInfo) {
						//调用每条数据的显示方法
						i++;
						createItem(data, newsInfo,i);
					});
					//设置新的查询起始值和查询长度
					$('#page-params').val(
							(dataArr.length + data.start) + ',' + data.length);
					//判断请求到的总数据长度是否等于数据分页长度
					if (data.length == dataArr.length) {
						$('#btn-more').show();//条件成立，显示数据加载完毕
					} else {
						$('#news-hint').html(hintstr ? hintstr : '暂无数据……');//显示没有数据
					}
				} else {
					$('#news-hint').html(hintstr ? hintstr : '暂无数据……');
				}
			} else {
				$('#news-hint').html("获取数据失败！");//显示没有数据
			}
			$('#data-loading').hide();//数据显示完隐藏div
			$('#news-hint').show();//显示提示字体
		}
	});
}

/**
 * 每条数据的div方法
 * @param n 遍历下标
 * @param newsInfo 每条新闻
 */
function createItem(n, newsInfo,i) {
//	if(i <= 6){
//		//详情页跳转地址
//		var url = basePath + 'news/goFindByIdToNewsInfoDetails?id=' + newsInfo.id;
//		//图片地址
//		var imgurl = basePath + 'download?filePath=' + newsInfo.thumbnail;
//		//标题
//		var titleStr = newsInfo.headline;
//		if (titleStr.length > 16) {
//			titleStr = titleStr.substring(0, 16) + '……';
//		}
//		var o=document.getElementById('news-fixed');//获得元素
//		var  height = window.innerHeight;
//		var  width = window.innerWidth;
//		var percentage = 0.40;
//		var newHeight = height * percentage;
//		var swiperSlide = bulidNewsWrapper(url, imgurl, titleStr);
//		// $('#news-fixed-wrapper').append(swiperSlide);
//		healthSwiper.appendSlide(swiperSlide); // 加到Swiper的最后
//		o.style.height =newHeight+ "px";
//		healthSwiper.update();
//		healthSwiper.attachEvents();
//	}
		createNewsItem(n,newsInfo);
}
/**
 * 列表显示方法
 * @param n 遍历下标
 * @param newsInfo 一个新闻
 */
function createNewsItem(n,newsInfo){
	//详情页跳转地址
	var url = basePath + 'news/goFindByIdToNewsInfoDetails?id=' + newsInfo.id;
	//图片地址
	var imgurl;
	var exp = newsInfo.picUrl;
	if (exp == null || exp == undefined || exp == ''){
	    if(newsInfo.newsTypeId == 2){
	    //中心新闻
	         imgurl = basePath + 'images/picLogo/img03.png';
	    }else if(newsInfo.newsTypeId == 3){
	    //最新公告
	    	 imgurl = basePath + 'images/picLogo/img01.png';	
	    }else if(newsInfo.newsTypeId == 4){
	    //党政工作
	    	// imgurl = basePath + 'images/picLogo/img02.png';
	    	 //8:工会活动9：党务工作10：行政公开
	    	 if(newsInfo.columnType == 8){
	    		 imgurl = basePath + 'images/picLogo/img408.png';
	    	 }else if(newsInfo.columnType == 9){
	    		 imgurl = basePath + 'images/picLogo/img409.png';
	    	 }else if(newsInfo.columnType == 10){
	    		 imgurl = basePath + 'images/picLogo/img410.png';
	    	 }
	    	 
	    }else if(newsInfo.newsTypeId == 5){
		//普通高校
		     imgurl = basePath + 'images/picLogo/img09.png';
	    }else if(newsInfo.newsTypeId == 6){
		//成人高考
		     imgurl = basePath + 'images/picLogo/img10.png';
	    }else if(newsInfo.newsTypeId == 7){
	    //中考中招
	    	 imgurl = basePath + 'images/picLogo/img07.png';
	    }else if(newsInfo.newsTypeId == 8){
	    //高中会考
	    	 imgurl = basePath + 'images/picLogo/img08.png';
	    }else if(newsInfo.newsTypeId == 9){
	    //自学考试
	    	 imgurl = basePath + 'images/picLogo/img11.png';
	    }else if(newsInfo.newsTypeId == 10){
	    //小学入学
	    	 imgurl = basePath + 'images/picLogo/img05.png';
	    }else if(newsInfo.newsTypeId == 11){
	    //初中入学
	    	 imgurl = basePath + 'images/picLogo/img06.png';
	    }else{
	    //政策公告
	    	 imgurl = basePath + 'images/picLogo/img04.png';
	    }
	}else{
	    imgurl = basePath + 'download?filePath=' + newsInfo.picUrl;
	}
	
//	var itemhtml = '<div class="media mediaSimple"><a href="'
//			+ url
//			+ '">'
//			+'<div class="media-left"><img alt="imge" class="media-object" style="width: 64px; height: 64px;" src="'
//			+ imgurl
//			+ '" data-holder-rendered="true"   onerror="this.onerror=\'\';this.src=\''
//			+ basePath
//			+ 'images/7-1.jpg\'" ></div>'
//			+'<div class="media-body" style="color:#333;"><div style="height:44px;"><h4 class="media-heading" style="font-size:14px;">'
//			+ newsInfo.newsTitle + '</h4></div><small>' + newsInfo.newsTypeName + '•'
//			+ newsInfo.createTime + '</small></div></a></div>';
	var str= newsInfo.newsTitle;//原始字符串
	var title = str;//要展示的字符串
	if(str.length>32){
		title=str.substring(0,32)+"...";
	}
	var itemhtml1 ='<a href="'+ url +'">'+
    					'<div class="font">	'+    
    					  '<img src="'+ imgurl +'" width="60" height="60">'+
    						'<font>'+title+'</font><br>'+
    							'<span>'+newsInfo.newsTypeName+'|'+newsInfo.createTime+'</span>'+     	      
    					'</div>'+
    				'</a>';
	$('#news-dynamic').append(itemhtml1);//添加入显示数据的div
}

/**
 * 构建轮播图元素
 * 
 * @param url资讯详情超链接地址
 * @param imgurl图片地址
 * @param title标题
 * @returns {String}
 */
//function bulidNewsWrapper(url, imgurl, title) {
//	var  height = window.innerHeight;
//	var  width = window.innerWidth;
//	var percentage = 0.40;
//	var newHeight = height * percentage;
//	var swiperSlide = '<div class="swiper-slide"><a href="'
//			+ url + '"><div class="newsSwiper"><img class="img-responsive center-block"'
//			+'sytle="height:'+newHeight+'px;width:'+width+'px;"'
//			+' src="'+ imgurl + '" alt="' + title
//			+ '"  onerror="this.onerror=\'\';this.src=\'' + basePath
//			+ 'images/7-1.jpg\'" ><div>' + title + '</div></div></a></div>';
//	return swiperSlide;
//}
