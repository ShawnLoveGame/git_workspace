$(function(){

    $(".pageToNav").click(function(){
        window.location.href= base + $(this).attr('data-src');
    });
    $(".active_menu").click(function(){
        $(this).parent('li').siblings().removeClass('open');
        if($(this).parent('li').hasClass('open')){
            $(this).parent('li').removeClass('open');
        }else{
            $(this).parent('li').addClass('open');
        }
    });
    var url = window.location.pathname;
    $(".nav-list").find('.submenu').each(function(i , v){
        $(this).find('li').each(function(k , no){
            var code = $(this).find('a').eq(0).attr('data-code');
            if(url == code){
                $(this).parents('li').addClass('open').siblings().removeClass('open');
                $(this).addClass('active').siblings().removeClass('active');
            }
        })
    });


    //隐藏左侧菜单

    // page-header-fixed page-sidebar-closed
    $(".sidebar-toggler").click(function(){
        // page-header-fixed
        if($('body').hasClass('page-sidebar-closed')){
            $('body').removeClass('page-sidebar-closed')
        }else{
            $('body').addClass('page-sidebar-closed')
        }
    });
    //
    $(".active_menu2").click(function(){
        if($(this).hasClass('active')){
            $(this).siblings().removeClass('active');
        }else{
            $(this).addClass('active').siblings().removeClass('active');
        }
        var menuId = $(this).attr('data-id');
        if($("#sub_"+menuId).hasClass('active_open')){
            $("#sub_"+menuId).hide();
            $("#sub_"+menuId).removeClass('active_open')
        }else{
            $("#sub_"+menuId).show();
            $("#sub_"+menuId).addClass('active_open')
        }
    });
    //目录
    $(".page-sidebar-menu").find('.sub-menu').each(function(i , v){

        $(this).find('li').each(function(k , no){
            var code = $(this).find('a').eq(0).attr('data-code');
            if(url == code){
                $(this).parent('ul').parent('li').addClass('open').siblings().removeClass('open');
                $(this).parent('ul').parent('li').addClass('active').siblings().removeClass('active');

                $(this).parent('ul').parent('li').show();
                $(this).parent('ul').parent('li').addClass('active_open');
                $(this).removeClass('active').addClass('active').siblings().removeClass('active');
                // $(this).parent('ul').parent('li').siblings().hide();
            }
        })
    });

});