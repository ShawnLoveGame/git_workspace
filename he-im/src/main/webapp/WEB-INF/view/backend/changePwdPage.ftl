<html>
<head>
    <script src="${base}/statics/js/jquery-1.10.2.min.js" type="text/javascript"></script>
</head>
<body>

    <input type="hidden" id="opid" value="${backendOperatorVO.id}" />
    <dl class="form-group password-confirmation-form">
        <dt><label for="user_old_password">Old password</label></dt>
        <dd><input class="form-control form-control" id="user_old_password"  required="required" tabindex="2" type="password"></dd>
    </dl>
    <dl class="form-group password-confirmation-form">
        <dt><label for="user_new_password">New password</label></dt>
        <dd><input class="form-control form-control"  id="user_new_password" required="required" tabindex="2" type="password"></dd>
    </dl>
    <dl class="form-group password-confirmation-form">
        <dt><label for="user_confirm_new_password">Confirm new password</label></dt>
        <dd><input class="form-control form-control" id="user_confirm_new_password" required="required" tabindex="2" type="password"></dd>
    </dl>
    <p>
        <button class="btn mr-2" type="submit" onclick="toChange();">Update password</button>
    </p>

<style>
    .form-group {
        margin: 15px 0;
    }
    .form-group dt {
        margin: 0 0 6px;
    }
    .form-group dt label{
        margin-left: 40px;
    }
    .form-group label {
        position: static;
    }
    .form-group .form-control {
        width: 440px;
        max-width: 100%;
        margin-right: 5px;
        background-color: #fafbfc;
    }
    .form-control, .form-select {
        min-height: 34px;
        padding: 6px 8px;
        font-size: 14px;
        line-height: 20px;
        color: #24292e;
        vertical-align: middle;
        background-color: #fff;
        background-repeat: no-repeat;
        background-position: right 8px center;
        border: 1px solid #d1d5da;
        border-radius: 3px;
        outline: none;
        box-shadow: inset 0 1px 2px rgba(27,31,35,0.075);
    }

    input, select, textarea, button {
        font-family: inherit;
        font-size: inherit;
        line-height: inherit;
    }
    button, input {
        overflow: visible;
    }
    button, input, select, textarea {
        font: inherit;
        margin: 0;
    }

    .mr-2 {
        margin-right: 8px !important;
    }
    .btn {
        color: #24292e;
        background-color: #eff3f6;
        background-image: -webkit-linear-gradient(270deg, #fafbfc 0%, #eff3f6 90%);
        background-image: linear-gradient(-180deg, #fafbfc 0%, #eff3f6 90%);
    }
    .btn {
        position: relative;
        display: inline-block;
        padding: 6px 12px;
        font-size: 14px;
        font-weight: 600;
        line-height: 20px;
        white-space: nowrap;
        vertical-align: middle;
        cursor: pointer;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
        background-repeat: repeat-x;
        background-position: -1px -1px;
        background-size: 110% 110%;
        border: 1px solid rgba(27,31,35,0.2);
        border-radius: 0.25em;
        -webkit-appearance: none;
        -moz-appearance: none;
        appearance: none;
    }
</style>

<script>
    function toChange(){
        var old = $("#user_old_password").val();
        var user_new_password = $("#user_new_password").val();
        var user_confirm_new_password = $("#user_confirm_new_password").val();
        var id = $("#opid").val();

        if(old == '' || user_new_password == '' || user_confirm_new_password == ''){
            alert('please check your pwd');
            return false;
        }

        $.post('${base}/im/user/changePwdData' , {'oldPwd':old , 'newPwd':user_new_password , 'confirmPwd':user_confirm_new_password , 'id':id} , function(data){
            if(data.success){
                parent.location.href = "${base}/login/index";
            }else{
                alert(data.message);
            }
        });
    }
</script>
</body>
</html>