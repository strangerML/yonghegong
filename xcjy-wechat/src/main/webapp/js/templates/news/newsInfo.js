$(function() {
	$('#page-params').val('0,10');//轮播图下方列表值

	getNewsData('暂无数据……');
});


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
		url : basePath + "news/getnews",
		type : "post",
		data : {
			newsTypeId : $('#newsType').val(),
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
						createNewsItem(data, newsInfo);
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
	//标题太长截取
	var str= newsInfo.newsTitle;//原始字符串
	var title = str;//要展示的字符串
	if(str.length>32){
		title=str.substring(0,32)+"...";
	}
	
	var itemhtml ='<a href="'+ url +'">'+
    					'<div class="font">	'+     
    					 '<img src="'+ imgurl +'" width="60" height="60">'+
    						'<font>'+title+'</font><br>'+
    							'<span>'+newsInfo.newsTypeName+'|'+newsInfo.createTime+'</span>'+     	      
    					'</div>'+
    				'</a>';
	$('#news-dynamic').append(itemhtml);//添加入显示数据的div
}
