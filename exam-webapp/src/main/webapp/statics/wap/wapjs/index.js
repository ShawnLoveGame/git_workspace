$(function(){

    //初始化选择单位
    if(departmentId!=''){
        $("#item_"+departmentId).addClass('selected_active').siblings().removeClass('selected_active');
        var $gw  = $("#item_"+departmentId).parents('.group-wrapper');
        $('.group-wrapper').removeAttr('groupcount');
        $gw.attr("groupcount", true);

        //清除所有input选中状态
        $('.comp-item').each(function( i , v){
            $(this).find('input').eq(0).removeProp('checked');
        });
        $("#item_"+departmentId).find('input').eq(0).prop('checked' , true);

    }
    //初始化组织机构

    $('.comp-item input').on('change',function () {
        var $gw = $(this).parents('.group-wrapper');
        $('.group-wrapper').removeAttr('groupcount');
        $gw.attr("groupcount", true);

        $(".comp-item").removeClass('selected_active');
        $(this).parents('.comp-item').addClass('selected_active');
    });


})

function popup(_text) {
    $('.popup').text(_text);
    if (!$('.popup').hasClass('active')) {
        $('.popup').addClass('active');
    }
    setTimeout(function () {
        $('.popup').removeClass('active');
    },800)
}

function startAnswer() {
    popup('答题已结束');
    // var len = $("#group_d").find('.selected_active');
    // if (len.length == 0 ) {
    //     // 这里有弹框,提示只能选一个组别
    //     popup('请选择所在部门');
    // }else {
    //     // 只选中了一个小组
    //     var d_id = $("#group_d").find('.selected_active').eq(0).attr('data-id');
    //     $.post(base + '/exam/toAnswerQuestion' , {'departmentId':d_id} , function(data){
    //        if(data.success){
    //             window.location.href = base + '/exam/toquestionPage';
    //        } else{
    //            popup(data.message);
    //        }
    //     });
    // }
}