
$(function(){

    $('.anwser-item input').on('change',function () {
        // console.log($(this).prop('type'));
        if($(this).prop('type') == 'radio'){
            $(this).parents('.ca_div').find('.anwser-item').removeClass('selected_active');
            $(this).parents('.anwser-item').addClass('selected_active');
        }else if($(this).prop('type') == 'checkbox'){
            // console.log(111);
            if($(this).parents('.anwser-item').hasClass('selected_active')){
                $(this).parents('.anwser-item').removeClass('selected_active');
            }else{
                $(this).parents('.anwser-item').addClass('selected_active');
            }
        }
    });
});

function popup(_text) {
    $('.popup').text(_text);
    if (!$('.popup').hasClass('active')) {
        $('.popup').addClass('active');
    }
    setTimeout(function () {
        $('.popup').removeClass('active');
    },800)
}
function changePage(index , flag){
    if(flag == 1){
        $(".question_page").hide();
        $("#q_detail"+index).show();
    }else{
        //判断当前是否有填写内容
        var c  = 1;
        if(index >  1){
            c = parseInt(index-1);
        }
        var len = $("#option_checkbox_"+c).find(".selected_active").length;
        if(len == 0){
            popup('当前题目您还未回答');
        }else{
            $(".question_page").hide();
            $("#q_detail"+index).show();
        }
    }
}
/**
 * 提交报告
 */
function toSubmitPage(){
    //遍历所有的题目
    var str = {};
    str['examId'] = $("#examId").val();
    var a_q = [];
    var cc = '';
    $(".question_page").each(function(i , v){
        var q_id = $(this).attr('data-id');
        var d_index = $(this).attr('data-index');
        var a_obj = {};
        a_obj.qid = q_id;
        var checks = [];
        $(this).find('.selected_active').each(function(i , v){
            checks.push($(this).attr('data-v'));
        });
        if(checks.length == 0){
            cc = '第'  + d_index +'题未答.';
        }
        a_obj.key = checks.join(',');
        a_obj.qindex = d_index;
        a_q.push(a_obj);
    });
    if(cc != '' ){
        popup(cc);
        return false;
    }
    str['contentModelList'] = a_q;
    $.post(base + '/exam/answerToSubmit' , {'jsonStr':JSON.stringify(str)} , function(data){
       if(data.success){
           window.location.href = base + '/exam/userCenter';
       } else {
           popup(data.message);
       }
    });
}
function restCurrentPage(_index){
    $("#option_checkbox_"+_index).find('.anwser-item').removeClass('selected_active');
}