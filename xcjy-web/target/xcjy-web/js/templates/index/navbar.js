var firstNavBar;
var firstLeftMenu;
$(function() {

	$("#menus").html("");

	$.ajax({
		url : basePath + "getMenus",
		type : "post",
		success : function(data) {
			if (data) {
				window.navbarMenusData = data;
				var ms = createNavbarMenu(data);
				$("#menus").html(ms);
				if (firstNavBar) {
					$('#' + firstNavBar).click();
					if (firstLeftMenu) {
						$('#' + firstLeftMenu).click();
						var $ulObj = $('#' + firstLeftMenu).next();
						if ($ulObj && $ulObj.is('ul')) {
							var phrefStr = $('#' + firstLeftMenu).attr('href');
							var ulIdStr = '#' + $ulObj.attr('id');
							if (ulIdStr == phrefStr) {
								$ulObj.children().first().children().first()
										.click();
							}
						}
					}
				}
			} else {
				alert("获取菜单失败！");
			}
		}
	});
})

function createNavbarMenu(rootMenus) {
	if (!rootMenus || rootMenus.length == 0) {
		return "";
	}
	var menuStr = "";
	$
			.each(
					rootMenus,
					function(n, rootMenu) {
						var navberAId = 'navberMainA_' + rootMenu.id;
						if (n == 0) {
							firstNavBar = navberAId;
						}
						if (rootMenu.isLeaf == 1) {
							menuStr += '<li><a id="'
									+ navberAId
									+ '" href="#" data-toggle="pill" onclick="return showMain(\''
									+ basePath + rootMenu.url + '\');" >'
									+ rootMenu.name + '</a></li>';
						} else {
							var children = rootMenu.children;
							if (children && children.length > 0) {
								menuStr += '<li><a id="'
										+ navberAId
										+ '" href="#" data-toggle="pill" onclick="return creatLeftMenus('
										+ rootMenu.id + ');" >' + rootMenu.name
										+ '</a></li>';
							} else {
								menuStr += '<li><a id="' + navberAId
										+ '" href="#" data-toggle="pill">'
										+ rootMenu.name + '</a></li>';
							}
						}
					});
	return menuStr;
}
