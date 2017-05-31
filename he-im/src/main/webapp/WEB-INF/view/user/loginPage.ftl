<html>
<head>

    <title>Sign in </title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=9">
    <meta name="renderer" content="webkit">
    <style type="text/css"></style>
    <link type="text/css" rel="stylesheet" href="${base}/statics/css/login.css">
    <script src="${base}/statics/js/jquery-1.10.2.min.js" type="text/javascript"></script>
</head>
<body>
<div class="j-loginbox g-mn" id="auto-id-1493012850455">
    <div class="m-login" id="auto-id-1493012850460">
        <form id="loginForm" data-focus-mode="0" autocomplete="off">
            <span class="z-tip j-tip f-dn"></span>
            <div class="iptbox">
                <span class="auto-1493012850454 js-mhd-parent">
                    <input class="ipt" type="text" name="username" maxlength="50" placeholder="name" id="account">
                    <span class="auto-1493012850454-show js-mhd js-pass js-nej-ok" style="display: block;">&nbsp;</span></span>
            </div>
            <div class="iptbox">
                <input class="ipt f-ime-dis" type="password" name="password" maxlength="50" placeholder="******" id="pwd">
                <i class="u-icon-caps f-dn j-ticon"></i>
            </div>
            <button type="button" class="u-btn" id="auto-id-1493012850445" onclick="toLogin();"><span class="j-submitBtn">SIGN IN</span></button>
        </form>
    </div>
</div>

<script>
    $("#account , #pwd").keydown(function(e){
        e.stopPropagation();
        if(e.keyCode == 13){
            toLogin();
        }
    });
    function toLogin(){
        var account = $("#account").val();
        var pwd = $("#pwd").val();
        if(account == '' || pwd == ''){
            $(".j-tip").html('Incorrect username or password. ');
            return ;
        }
        $.post('${base}/login/toLogin' , {'jid':account , 'pwd':pwd} , function(data){
            if(data.success){
                window.location.href="${base}/backend/toIndex";
            }else{
                alert('login error');
            }
        });
    }
</script>
</body>
</html>