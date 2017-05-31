<html>
<head>
    <title>客服消息</title>
    <script src="${base}/statics/js/jquery-1.10.2.min.js" type="text/javascript"></script>


</head>
<body>

JID：<input type="text" id="input-jid" value="damon">
密码：<input type="password" id="input-pwd" value="111111">
<button id="btn-login" onclick="toLogin()">登录</button>
<div style="padding:100px;">


    <table>
        <tr>
            <td>
                <div id="msg_div" style="width:800px;overflow-y: scroll;height: 200px;border: 1px solid #CCC;">

                </div>
            </td>
        </tr>
        <tr>
            <td>
            <textarea id="content" style="width:600px;overflow-y: scroll;height: 100px;border: 1px solid #CCC;">

            </textarea>
            </td>
            <td><input type="text" id="sendTo"><input type="button" value="发送" onclick="toSendMessage();"/></td>
        </tr>
    </table>
</div>
</body>
<script>

    function toLogin(){
        var account = $("#input-jid").val();
        var pwd = $("#input-pwd").val();
        if(account == '' || pwd == '' ){
            alert('请填写信息完整');
            return false;
        }
        $.post('${base}/im/user/toLogin' , {'jid':account , 'pwd':pwd} , function(data){
//            alert(data.message);
        });
    }
    function toSendMessage() {
        var content = $.trim($("#content").val());
        if (content == '') {
            return;
        }
        var data = new Date().getTime();
        $.post('${base}/im/message/toSendMsg', {'content': content, 'toAcount': $("#sendTo").val(), 'fromAcount': $("#input-jid").val()}, function (data) {
            if (data.success) {
                var ops = '';
                var lg = data.data.msgList;
                $.each(lg, function (i, v) {
                    ops += '<p>';
                    ops += dateTimeToLocalString() + "    " + v.from.split('@')[0] + ":" + v.body;
                    ops += '</p>';
                })
                $("#msg_div").append(ops);

                $("#content").val('');
            }
        });
    }

    function dateTimeToLocalString() {
        var date = new Date();
        Y = date.getFullYear();
        M = (date.getMonth() + 1) < 10 ? ("0" + (date.getMonth() + 1)) : (date
                .getMonth() + 1);
        D = date.getDate() < 10 ? ("0" + date.getDate()) : date.getDate();
        var hh = date.getHours() < 10 ? ("0" + date.getHours()) : date
                .getHours();
        var mm = date.getMinutes() < 10 ? ("0" + date.getMinutes()) : date
                .getMinutes();
        return Y + '-' + M + '-' + D + ' ' + hh + ':' + mm;
    }
</script>
</html>