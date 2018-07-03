var newsInfoDetailUrl;
$(function() {
	newsInfoDetailUrl = basePath + 'news/goFindByIdToNewsInfoDetails';//新闻详情跳转地址
	var _ntype = $('#columnType').val();//新闻类型
	tabSelect('tab_health_' + _ntype, _ntype);//第一个参数为类型值，第二个参数为新闻类型
	
	console.log(_ntype);

});
/**
 * 点击栏目请求
 * @param tabId 查询类型值
 * @param columnType  类型id
 */
function tabSelect(tabId, columnType) {
	cssTabSelect(tabId);//调用改变样式方法

	$('#columnType').val(columnType);//设置newType 的值
	$('#page-params').val('0,10');//第一个参数是起始下标  第二个是数据加载条数
	$('#news-dynamic').html("");//设置动态列表为空
	$('#news-hint').html("");//设置提示为空
	$('#news-hint').hide();//隐藏提示
	$('#btn-more').hide();//隐藏 加载更多按钮
	
	if (8 == columnType){
		document.title = "工会活动";
	}
	if (9 == columnType){
		document.title = "党务工作";
	}
	if (10 == columnType){
		document.title = "行政公开";
	}
	$('#data-loading').show();//显示  正在加载 图片
	var hintstr = '暂无数据';//提示没有数据
	//调用ajax方法
	queryPageData(columnType, hintstr, buildNewsDynamic);
}

/**
 * 加载更多
 */
function loadMore() {
	$('#news-hint').html("");// 设置提示为空
	$('#news-hint').hide();//隐藏提示
	$('#btn-more').hide();//隐藏  加载更多  按钮
	$('#data-loading').show();//显示 正在加载 图片
	var hintstr = '所有数据已经加载完成';
	//调用ajax请求
	queryPageData($('#columnType').val(), hintstr, buildNewsDynamic);
}

/**
 * 编辑tab选中样式，选中后样式变化
 * 
 * @param tabId
 */
function cssTabSelect(tabId) {
	var tab = $('#' + tabId);
	tab.parent().siblings().children().removeAttr("style");
	tab.attr("style", "height:39px;color:#035DFF;font-weight:bold;border-bottom:3px solid #039BFF;");
}

/**
 * 请求资讯数据
 * 
 * @param columnType 类型值（类型id）
 * @param hintstr 提示语句
 * @param callback 样式方法
 */
function queryPageData(columnType, hintstr, callback) {
	//，切割分页数据参数，获取分页条件，第一个参数为起始下标   第二个为一个显示多少条数据参数
	var pageParams = $('#page-params').val().split(",");
	var startnum = pageParams[0];
	var lengthnum = pageParams[1];
	//ajax请求
	$.ajax({
		url : basePath + "news/getpartynews",
		type : "post",
		data : {
			columnType : columnType,
			start : startnum,
			length : lengthnum
		},
		success : function(data) {
			//判断总数据和分页数据是否都为空
			if (data && data.pageData) {
				var dataArr = data.pageData;
				//判断分数数据是否为空
				if (dataArr.length > 0) {
					//不为空调用遍历方法
					buildTabHtml(data, callback);
					//为空
				} else {
					$('#news-hint').html(hintstr ? hintstr : '暂无数据');//设置提示 暂无数据
					$('#news-hint').show();//显示提示
				}
			//数据不存在时	
			} else {
				$('#news-hint').html("获取数据失败！");//设置提示  获取数据失败
				$('#news-hint').show();//显示提示
			}
			$('#data-loading').hide();//隐藏正在加载
		}
	});
}

/**
 * 循环方法
 * @param data 分页数据
 * @param callback  样式方法
 */
function buildTabHtml(data, callback) {
	//数组接收分页数据
	var dataArr = data.pageData;
	// 下面使用each进行遍历
	$.each(dataArr, function(n, newsInfo) {
		//调用样式方法
		callback(n, newsInfo);
	});
	//设置新的分页参数 第一个参数是起始下标 第二个参数是查询数目
	$('#page-params').val((dataArr.length + data.start) + ',' + data.length);

	$('#data-loading').hide();//隐藏正在加载
	//判断总数据是否大于查询起始下标 并且数据总长度是否等于分页起始下标
	if (data.recordsTotal > dataArr.length && data.length == dataArr.length) {
		$('#btn-more').show();//显示 加载跟多 按钮
		//否则
	} else {
		$('#news-hint').html('所有数据已经加载完成');//设置提示 为  所有数据已加载完成
		var newType = $('#columnType').val();
		if(newType != 0){
			$('#news-hint').show();//显示提示
		}
		
	}
}


/**
 * 列表样式方法
 * @param n 分页数据下标
 * @param newsInfo 一条新闻
 */
function buildNewsDynamic(n, newsInfo) {
	//详情页跳转地址
	var url = newsInfoDetailUrl + '?id=' + newsInfo.id;
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
	$('#news-dynamic').append(itemhtml);
}
