$(function () {


    //密码输入框、验证码输入框回车
    $("#username,#password").keydown(function(e){
        e.stopPropagation();
        if(e.keyCode == 13){
            login();
        }
    });


    $("#login_btn").click(function(){
        login();
    });
});

function login(){
    var username = $.trim($("#username").val());
    var password = $("#password").val();

    if(username == ''){
        $(".alert-error").html('请输入用户名');
        return false;
    }
    if(password == '' ){
        $(".alert-error").html('请输入密码');
        return false;
    }
    $(".alert-error").html('');

    var remember = 0;
    if($("#remember").attr('checked')){
        remember = 1;
    }

    $.post(base + '/login/login' , {"userName":username , "password":password ,'autoLogin':remember} , function(data){
        if(data.success){
            window.location.href = base+'/backend/toIndex';
        }else{
            $(".alert-error").html(data.message);
        }
    });
}