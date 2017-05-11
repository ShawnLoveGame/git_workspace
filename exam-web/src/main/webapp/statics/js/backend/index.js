$(function(){

});
function toggle_menu(obj) {
    $(obj).parent().siblings().toggle();
}

function toPage(_this) {
    $("#iframe").attr('src' , $(_this).attr('data-src'));
}