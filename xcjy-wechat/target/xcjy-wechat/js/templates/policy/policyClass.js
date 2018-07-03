$(function() {
	var ntype = $('#newsType').val();//新闻类型
	titleSelect(ntype);
	
});
function titleSelect(newsType) {
	
	if (5 == newsType){
		document.title = "普通高等学校招生";
		document.getElementById("jianjie").innerText = "负责辖区内高级中等教育学校或具有同等学力的社会考生的高考报名、组考、录取工作";
		document.getElementById("dianhua").innerText = "66127094";
	}else if (6 == newsType){
		document.title = "成人高等学校招生";
		document.getElementById("jianjie").innerText = "负责辖区内成人高考的报名、组考、录取工作";
		document.getElementById("dianhua").innerText = "66127094";
	}else if (7 == newsType){
		document.title = "高级中等学校招生";
		document.getElementById("jianjie").innerText = "负责辖区内初中毕业生升学、报名、组考、录取工作";
		document.getElementById("dianhua").innerText = "66139429";
	}else if (8 == newsType){
		document.title = "普通高中会考";
		document.getElementById("jianjie").innerText = "负责辖区内高中学生、高中毕业会考工作";
		document.getElementById("dianhua").innerText = "66127034";
	}else if (9 == newsType){
		document.title = "高等教育自学考试";
		document.getElementById("jianjie").innerText = "负责社会各类考生的高等教育自学考试的报名、组考、成绩发放、毕业生资格审查及毕业证的颁发工作";
		document.getElementById("dianhua").innerText = "66560135";
	}else if (10 == newsType){
		document.title = "义务教育：小学入学";
		document.getElementById("jianjie").innerText = "负责区域内小学招生方案的具体组织与实施工作；负责区域内小学学生的学籍管理工作";
		document.getElementById("dianhua").innerText = "63542149";
	}else if (11 == newsType){
		document.title = "义务教育：初中入学";
		document.getElementById("jianjie").innerText = "负责区域内初中招生方案的具体组织与实施工作；负责区域内中学学生的学籍管理工作";
		document.getElementById("dianhua").innerText = "66151858";
	}
	
}
function goPolicyNews(columnType){
	var newstype = $('#newsType').val();
	window.location.href = basePath + 'news/goPolicyClassNewsPage?newsTypeId='+newstype+'&columnType='+columnType;
	
}