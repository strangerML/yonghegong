$(function() {

});

function creatLeftMenus(navbarMenuId) {
	$('#main-nav').html('');
	var menus;
	$.each(navbarMenusData, function(n, menu) {
		if (menu.id == navbarMenuId) {
			menus = menu.children;
			return false;
			// break----用return false;
			// continue --用return ture;﻿
		}
	});
	$('#main-nav').html(createMenusTree(menus));

	$("#main-nav a").click(
			function() {
				var $ulObj = $(this).next();
				if (!$ulObj || !$ulObj.is('ul')
						|| ('#' + $ulObj.attr('id')) != $(this).attr('href')) {
					$("#main-nav").find('li.active').removeClass('active');
					$(this).parents('li:not(.active)').addClass('active');

				}
			});
}

function createMenusTree(menus) {
	var menuStr = "";
	if (!menus || menus.length == 0) {
		return menuStr;
	}

	$
			.each(
					menus,
					function(n, menu) {
						var leftMenuId = 'lm_' + menu.id;
						if (n == 0) {
							firstLeftMenu = leftMenuId;
						}
						if (menu.isLeaf == 1) {
							menuStr += '<li><a id="' + leftMenuId
									+ '" href="#" onclick="return showMain(\''
									+ basePath + menu.url + '\');" >'
									+ menu.name + '</a></li>';
						} else {
							var children = menu.children;
							if (children && children.length > 0) {
								var subMenuId = 'm_ul_' + menu.id;
								menuStr += '<li>'
										+ '<a id="'
										+ leftMenuId
										+ '" href="#'
										+ subMenuId
										+ '" class="nav-header collapsed" data-toggle="collapse" aria-expanded="false">'
										+ menu.name
										+ '<span class="badge pull-right">'
										+ children.length
										+ '</span></a>'
										+ '<ul id="'
										+ subMenuId
										+ '" class="nav nav-list secondmenu collapse" aria-expanded="false" style="height: 0px;">';
								// menuStr += createMenusTree(children);//递归所有菜单
								menuStr += createSecondMenusTree(children);
								menuStr += '</ul></li>';
							} else {
								menuStr += '<li><a id="'
										+ leftMenuId
										+ '" href="#">'
										+ menu.name
										+ '<span class="badge pull-right">0</span></a></li>';
							}
						}
					});

	return menuStr;
}

function createSecondMenusTree(menus) {
	var menuStr = "";
	if (!menus || menus.length == 0) {
		return menuStr;
	}

	$.each(menus, function(n, menu) {
		if (menu.isLeaf == 1) {
			menuStr += '<li><a href="#" onclick="return showMain(\'' + basePath
					+ menu.url + '\');" >' + menu.name + '</a></li>';
		} else {
			menuStr += '<li><a href="#">' + menu.name + '</a></li>';

		}
	});

	return menuStr;
}