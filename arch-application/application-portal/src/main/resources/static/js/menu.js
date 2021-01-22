$(function(){

	$.ajax({
        type: "post",
        url: "./admin/getMenu",
        dataType: "json",
        data: {
            "action": "getMenu"
        },
        async: true,
        //异步请求（true）,默认是异步，同步是false
        error: function () {
            showError("系统错误，加载系统菜单失败。请联系管理员处理！", 'error');
        },
        success: function (data) {
            //console.log(data);
            if (data.code == 200) {
                var menuAry = data.data;
                showMenu(menuAry);

                //计算内容区域高度
                var calcHeight = function () {
                    var browserHeight = $(window).innerHeight();
                    var topHeight = $('#page-header').outerHeight(true);
                    var tabMarginTop = parseInt($('#mainFrameTabs').css('margin-top'));//获取间距
                    var tabHeadHeight = $('ul.nav-tabs', $('#mainFrameTabs')).outerHeight(true) + tabMarginTop;
                    var contentMarginTop = parseInt($('div.tab-content', $('#mainFrameTabs')).css('margin-top'));//获取内容区间距
                    var contentHeight = browserHeight - topHeight - tabHeadHeight - contentMarginTop;
                    //var contentHeight = browserHeight;
                    $('div.tab-content', $('#mainFrameTabs')).height(contentHeight);
                };
                //菜单点击
                //一级菜单点击
                $('.firstmenu').on('click', function (e) {
                    var menuId = $(this).attr('mid');
                    var url = $(this).attr('funurl');
                    if (menuId && url) {
                        e.stopPropagation();
                        var title = $(this).text();
                        $('#mainFrameTabs').bTabsAdd(menuId, title, url);

                        //计算Tab可用区域高度
                        calcHeight();
                    }
                });

                //二级菜单点击
                $('.secondmenu li a').on('click', function (e) {
                    e.stopPropagation();
                    var li = $(this).closest('li');
                    var menuId = $(li).attr('mid');
                    var url = $(li).attr('funurl');
                    var title = $(this).text();
                    $('#mainFrameTabs').bTabsAdd(menuId, title, url);

                    //计算Tab可用区域高度
                    calcHeight();
                });

            } else {
                showError(json.msg, 'error');
            }
        }
    });

	//初始化
	$('#mainFrameTabs').bTabs();
});

function showMenu(menuAry) {
    for (var i = 0; i < menuAry.length; i++) {
        var id = menuAry[i].id;
        var text = menuAry[i].name;
        var url = menuAry[i].url;
        var icon = menuAry[i].icon;
        var submenu = menuAry[i].submenu;
        var tag = url.replace(/\//g, "");
        var str = '<li>\n';
        if (submenu.length > 0) {
            str = str + '<a href="#' + tag + '" class="nav-header collapsed" data-toggle="collapse">\n' +
                '<i class="fa ' + icon + '"></i>\n' + text +
                '<span class="pull-right fa fa-chevron-toggle"></span>\n' +
                '</a>\n' +
                '<ul id="' + tag + '" class="nav nav-list collapse secondmenu" style="height: 0px;">\n';
            for (var k = 0; k < submenu.length; k++) {
                var subid = submenu[k].id;
                var subtext = submenu[k].name;
                var suburl = submenu[k].url;
                var subicon = submenu[k].icon;
                var subtag = suburl.replace(/\//g, "");
                str = str + '<li mid="tab' + subid + '" funurl="' + suburl + '.html"><a href="#">\n' +
                    '<i class="' + subicon + '"></i>' + subtext + '</a></li>\n';
            }
            str = str + '</ul>\n</li>';
        } else {
            str = str + '<a href="#" class="nav-header collapsed" data-toggle="collapse">\n' +
                '<i class="' + icon + '"></i>\n' + text +
                '<span class="pull-right fa fa-chevron-toggle"></span>\n' +
                '</a>\n' + '</li>\n';
        }
        $("#main-nav").append(str);
    }
}