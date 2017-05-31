<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telphone=no, email=no"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
    <title>Chat</title>
    <link rel="stylesheet" href="${base}/statics/css/chat.css">
</head>
<body>
    <div id="log-container">

    </div>
<script src="${base}/statics/node_modules/jquery/dist/jquery.js"></script>

<script>
    $(document).ready(function() {
        // 指定websocket路径
        var websocket;
        if ('WebSocket' in window) {
            websocket = new WebSocket("ws://127.0.0.1:18092/ws?userName="+'${userInfo.userName}');
        } else if ('MozWebSocket' in window) {
            websocket = new MozWebSocket("ws://127.0.0.1:18092/ws"+${userInfo.userName});
        } else {
            websocket = new SockJS("http://127.0.0.1:18092/ws/sockjs"+${userInfo.userName});
        }
        websocket.onmessage = function(event) {
            var data=JSON.parse(event.data);
            console.log(data);
        };
        $.post("${base}/im/user/getCurrentChatUser",function(data){
            for(var i=0;i<data.length;i++)
                $("#users").append('<a href="#" onclick="talk(this)" class="list-group-item">'+data[i]+'</a>');
        });
    });

</script>

</body>
</html>