
function gen_pagination(totalcount,pagesize,currentpage,action,domitem){
    var totalpage=Math.ceil(totalcount/pagesize);
    if(totalpage==1){
        $("#"+domitem).html("找到["+totalcount+"]条符合条件的数据。");
        //return true;
    }
    var pageStr='<ul class="pager" style="margin-top:2px;margin-bottom-2px;">';
    var firstPageStr = '  <li class="previous"><a href="#" onclick="'+action+'(0,'+pagesize+')">首页</a></li>';
    var prePageStr = '  <li class="previous"><a href="#" onclick="'+action+'('+(currentpage-2)+','+pagesize+')">上一页</a></li>';
    var nextPageStr = '  <li class="previous"><a href="#" onclick="'+action+'('+(currentpage)+','+pagesize+')">下一页</a></li>';
    var lastPageStr = '  <li class="previous"><a href="#" onclick="'+action+'('+(totalpage-1)+','+pagesize+')">尾页</a></li>';

    if(currentpage==1){
        firstPageStr = '  <li class="previous active disabled"><a href="#">首页</a></li>';
        prePageStr = '  <li class="previous disabled"><a href="#">上一页</a></li>';
    }
    if(currentpage==totalpage){
        nextPageStr = '  <li class="previous disabled"><a href="#">下一页</a></li>';
        lastPageStr = '  <li class="previous active disabled"><a href="#">尾页</a></li>';
    }

    var pageSizeAry=[1,5,10,20,50,100];
    var sizeStr = "<li class='previous'><span>" +
        //"<select id='pagesizeset' class='selectpicker' onchange='changsize()'>";
        "<select id='"+action+"_pagesizeset' class='selectpicker' onchange=\""+action+"(0,$(\'#"+action+"_pagesizeset\').val())\">";
    var optStr = "";
    for(var item in pageSizeAry){
        optStr = optStr + "<option value='"+pageSizeAry[item]+"'";
        if(pagesize == pageSizeAry[item]){
            optStr = optStr + 'selected';
        }
        optStr = optStr + ">"+pageSizeAry[item]+"</option>";
        //console.log(optStr);
    }

        sizeStr = sizeStr + optStr + "</select>行/页</span></li>";
    //console.log("currentpage="+currentpage+";   totalpage= "+totalpage+";   pagesize= "+pagesize);
    pageStr=pageStr + firstPageStr + prePageStr
        + '<li class="previous disabled"><a href="#">（第'+currentpage+'/'+totalpage+'页）</a></li>'
        + sizeStr
        + nextPageStr + lastPageStr
        + '</ul>';
    $("#"+domitem).empty().html(pageStr);
}

function showError(txt,type,options){
    window.wxc.xcConfirm(txt, type, options);
}
