<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telphone=no, email=no"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
    <title>backend chats</title>
    <link rel="stylesheet" href="${base}/statics/css/backend-chats.css">
    <script>
        var base ='${base}';
    </script>
</head>
<body>

<div class="chat-container">
    <div class="conversation-window">

        <div class="both-big-wrap">
            <div class="buddy-container">
                <div class="switch-tabs-wrap clearfix">
                    <div class="switch-tabs-item switch-tabs-item-all active">All</div>
                    <#--<div class="switch-tabs-item switch-tabs-item-unread">Unread</div>-->
                </div>
                <div class="buddy-list-wrap" id="buddy-list-wrap">

                </div>
            </div>

            <div class="chats-window-container">
                <div class="chats-container">
                    <div class="wrap-chats-contnets">
                        <div class="chat-content swiper-no-swiping">
                            <div class="chat-scroll-wrap swiper-wrapper">
                                <div class="swiper-slide">

                                </div>
                            </div>
                            <div class="chat-scrollbar"></div>
                        </div>
                    </div>

                    <div class="chat-pannel">
                        <div id="type_area" class="div_text_area" contenteditable="true">
                        </div>
                        <#--<textarea class="type-text-area" id="type_area" placeholder="Type a message..." maxlength="5000">-->

                        <#--</textarea>-->
                        <div class="chat-toolbar">
                            <div class="tools-wrap">
                                <span class="tool-photo tool-grid"></span>
                                <span class="tool-expression tool-grid"></span>
                                <div class="qqface-wrap" style="display: none;">
                                    <div class="qqface-layer clearfix">
                                        <i data-code="微笑"><img src="${base}/statics/images/gif/weixiao.gif" width="24" height="24" class="qqface" title="微笑"></i>
                                        <i data-code="撇嘴"><img src="${base}/statics/images/gif/piezui.gif" width="24" height="24" class="qqface" title="撇嘴"></i>
                                        <i data-code="色"><img src="${base}/statics/images/gif/se.gif" width="24" height="24" class="qqface" title="色"></i>
                                        <i data-code="发呆"><img src="${base}/statics/images/gif/fadai.gif" width="24" height="24" class="qqface" title="发呆"></i>
                                        <i data-code="得意"><img src="${base}/statics/images/gif/deyi.gif" width="24" height="24" class="qqface" title="得意"></i>
                                        <i data-code="流泪"><img src="${base}/statics/images/gif/liulei.gif" width="24" height="24" class="qqface" title="流泪"></i>
                                        <i data-code="害羞"><img src="${base}/statics/images/gif/haixiu.gif" width="24" height="24" class="qqface" title="害羞"></i>
                                        <i data-code="闭嘴"><img src="${base}/statics/images/gif/bizui.gif" width="24" height="24" class="qqface" title="闭嘴"></i>
                                        <i data-code="睡"><img src="${base}/statics/images/gif/shui.gif" width="24" height="24" class="qqface" title="睡"></i>
                                        <i data-code="大哭"><img src="${base}/statics/images/gif/daku.gif" width="24" height="24" class="qqface" title="大哭"></i>
                                        <i data-code="尴尬"><img src="${base}/statics/images/gif/ganga.gif" width="24" height="24" class="qqface" title="尴尬"></i>
                                        <i data-code="发怒"><img src="${base}/statics/images/gif/fanu.gif" width="24" height="24" class="qqface" title="发怒"></i>
                                        <i data-code="调皮"><img src="${base}/statics/images/gif/tiaopi.gif" width="24" height="24" class="qqface" title="调皮"></i>
                                        <i data-code="呲牙"><img src="${base}/statics/images/gif/ciya.gif" width="24" height="24" class="qqface" title="呲牙"></i>
                                        <i data-code="惊讶"><img src="${base}/statics/images/gif/jingya.gif" width="24" height="24" class="qqface" title="惊讶"></i>
                                        <i data-code="难过"><img src="${base}/statics/images/gif/nanguo.gif" width="24" height="24" class="qqface" title="难过"></i>
                                        <i data-code="酷"><img src="${base}/statics/images/gif/ku.gif" width="24" height="24" class="qqface" title="酷"></i>
                                        <i data-code="冷汗"><img src="${base}/statics/images/gif/lenghan.gif" width="24" height="24" class="qqface" title="冷汗"></i>
                                        <i data-code="抓狂"><img src="${base}/statics/images/gif/zhuakuang.gif" width="24" height="24" class="qqface" title="抓狂"></i>
                                        <i data-code="吐"><img src="${base}/statics/images/gif/tu.gif" width="24" height="24" class="qqface" title="吐"></i>
                                        <i data-code="偷笑"><img src="${base}/statics/images/gif/touxiao.gif" width="24" height="24" class="qqface" title="偷笑"></i>
                                        <i data-code="可爱"><img src="${base}/statics/images/gif/keai.gif" width="24" height="24" class="qqface" title="可爱"></i>
                                        <i data-code="白眼"><img src="${base}/statics/images/gif/baiyan.gif" width="24" height="24" class="qqface" title="白眼"></i>
                                        <i data-code="-傲慢"><img src="${base}/statics/images/gif/aoman.gif" width="24" height="24" class="qqface" title="-傲慢"></i>
                                        <i data-code="饥饿"><img src="${base}/statics/images/gif/jie.gif" width="24" height="24" class="qqface" title="饥饿"></i>
                                        <i data-code="困"><img src="${base}/statics/images/gif/kun.gif" width="24" height="24" class="qqface" title="困"></i>
                                        <i data-code="惊恐"><img src="${base}/statics/images/gif/jingkong.gif" width="24" height="24" class="qqface" title="惊恐"></i>
                                        <i data-code="流汗"><img src="${base}/statics/images/gif/liuhan.gif" width="24" height="24" class="qqface" title="流汗"></i>
                                        <i data-code="憨笑"><img src="${base}/statics/images/gif/hanxiao.gif" width="24" height="24" class="qqface" title="憨笑"></i>
                                        <i data-code="大兵"><img src="${base}/statics/images/gif/dabing.gif" width="24" height="24" class="qqface" title="大兵"></i>
                                        <i data-code="奋斗"><img src="${base}/statics/images/gif/fendou.gif" width="24" height="24" class="qqface" title="奋斗"></i>
                                        <i data-code="咒骂"><img src="${base}/statics/images/gif/zhouma.gif" width="24" height="24" class="qqface" title="咒骂"></i>
                                        <i data-code="疑问"><img src="${base}/statics/images/gif/yiwen.gif" width="24" height="24" class="qqface" title="疑问"></i>
                                        <i data-code="嘘"><img src="${base}/statics/images/gif/xu.gif" width="24" height="24" class="qqface" title="嘘"></i>
                                        <i data-code="晕"><img src="${base}/statics/images/gif/yun.gif" width="24" height="24" class="qqface" title="晕"></i>
                                        <i data-code="折磨"><img src="${base}/statics/images/gif/zhemo.gif" width="24" height="24" class="qqface" title="折磨"></i>
                                        <i data-code="衰"><img src="${base}/statics/images/gif/shuai.gif" width="24" height="24" class="qqface" title="衰"></i>
                                        <i data-code="骷髅"><img src="${base}/statics/images/gif/kulou.gif" width="24" height="24" class="qqface" title="骷髅"></i>
                                        <i data-code="敲打"><img src="${base}/statics/images/gif/qiaoda.gif" width="24" height="24" class="qqface" title="敲打"></i>
                                        <i data-code="再见"><img src="${base}/statics/images/gif/zaijian.gif" width="24" height="24" class="qqface" title="再见"></i>
                                        <i data-code="擦汗"><img src="${base}/statics/images/gif/cahan.gif" width="24" height="24" class="qqface" title="擦汗"></i>
                                        <i data-code="抠鼻"><img src="${base}/statics/images/gif/koubi.gif" width="24" height="24" class="qqface" title="抠鼻"></i>
                                        <i data-code="鼓掌"><img src="${base}/statics/images/gif/guzhang.gif" width="24" height="24" class="qqface" title="鼓掌"></i>
                                        <i data-code="糗大了"><img src="${base}/statics/images/gif/qiudale.gif" width="24" height="24" class="qqface" title="糗大了"></i>
                                        <i data-code="坏笑"><img src="${base}/statics/images/gif/huaixiao.gif" width="24" height="24" class="qqface" title="坏笑"></i>
                                        <i data-code="左哼哼"><img src="${base}/statics/images/gif/zuohengheng.gif" width="24" height="24" class="qqface" title="左哼哼"></i>
                                        <i data-code="右哼哼"><img src="${base}/statics/images/gif/youhengheng.gif" width="24" height="24" class="qqface" title="右哼哼"></i>
                                        <i data-code="哈欠"><img src="${base}/statics/images/gif/haqian.gif" width="24" height="24" class="qqface" title="哈欠"></i>
                                        <i data-code="鄙视"><img src="${base}/statics/images/gif/bishi.gif" width="24" height="24" class="qqface" title="鄙视"></i>
                                        <i data-code="委屈"><img src="${base}/statics/images/gif/weiqu.gif" width="24" height="24" class="qqface" title="委屈"></i>
                                        <i data-code="快哭了"><img src="${base}/statics/images/gif/kuaikule.gif" width="24" height="24" class="qqface" title="快哭了"></i>
                                        <i data-code="阴险"><img src="${base}/statics/images/gif/yinxian.gif" width="24" height="24" class="qqface" title="阴险"></i>
                                        <i data-code="亲亲"><img src="${base}/statics/images/gif/qinqin.gif" width="24" height="24" class="qqface" title="亲亲"></i>
                                        <i data-code="吓"><img src="${base}/statics/images/gif/xia.gif" width="24" height="24" class="qqface" title="吓"></i>
                                        <i data-code="可怜"><img src="${base}/statics/images/gif/kelian.gif" width="24" height="24" class="qqface" title="可怜"></i>
                                        <i data-code="菜刀"><img src="${base}/statics/images/gif/caidao.gif" width="24" height="24" class="qqface" title="菜刀"></i>
                                        <i data-code="西瓜"><img src="${base}/statics/images/gif/xigua.gif" width="24" height="24" class="qqface" title="西瓜"></i>
                                        <i data-code="啤酒"><img src="${base}/statics/images/gif/pijiu.gif" width="24" height="24" class="qqface" title="啤酒"></i>
                                        <i data-code="篮球"><img src="${base}/statics/images/gif/lanqiu.gif" width="24" height="24" class="qqface" title="篮球"></i>
                                        <i data-code="乒乓"><img src="${base}/statics/images/gif/pingpang.gif" width="24" height="24" class="qqface" title="乒乓"></i>
                                        <i data-code="咖啡"><img src="${base}/statics/images/gif/kafei.gif" width="24" height="24" class="qqface" title="咖啡"></i>
                                        <i data-code="饭"><img src="${base}/statics/images/gif/fan.gif" width="24" height="24" class="qqface" title="饭"></i>
                                        <i data-code="猪头"><img src="${base}/statics/images/gif/zhutou.gif" width="24" height="24" class="qqface" title="猪头"></i>
                                        <i data-code="玫瑰"><img src="${base}/statics/images/gif/meigui.gif" width="24" height="24" class="qqface" title="玫瑰"></i>
                                        <i data-code="凋谢"><img src="${base}/statics/images/gif/diaoxie.gif" width="24" height="24" class="qqface" title="凋谢"></i>
                                        <i data-code="示爱"><img src="${base}/statics/images/gif/shiai.gif" width="24" height="24" class="qqface" title="示爱"></i>
                                        <i data-code="爱心"><img src="${base}/statics/images/gif/aixin.gif" width="24" height="24" class="qqface" title="爱心"></i>
                                        <i data-code="心碎"><img src="${base}/statics/images/gif/xinsui.gif" width="24" height="24" class="qqface" title="心碎"></i>
                                        <i data-code="蛋糕"><img src="${base}/statics/images/gif/dangao.gif" width="24" height="24" class="qqface" title="蛋糕"></i>
                                        <i data-code="闪电"><img src="${base}/statics/images/gif/shandian.gif" width="24" height="24" class="qqface" title="闪电"></i>
                                        <i data-code="炸弹"><img src="${base}/statics/images/gif/zhadan.gif" width="24" height="24" class="qqface" title="炸弹"></i>
                                        <i data-code="刀"><img src="${base}/statics/images/gif/dao.gif" width="24" height="24" class="qqface" title="刀"></i>
                                        <i data-code="足球"><img src="${base}/statics/images/gif/zuqiu.gif" width="24" height="24" class="qqface" title="足球"></i>
                                        <i data-code="瓢虫"><img src="${base}/statics/images/gif/piaochong.gif" width="24" height="24" class="qqface" title="瓢虫"></i>
                                        <i data-code="便便"><img src="${base}/statics/images/gif/bianbian.gif" width="24" height="24" class="qqface" title="便便"></i>
                                        <i data-code="月亮"><img src="${base}/statics/images/gif/yueliang.gif" width="24" height="24" class="qqface" title="月亮"></i>
                                        <i data-code="太阳"><img src="${base}/statics/images/gif/taiyang.gif" width="24" height="24" class="qqface" title="太阳"></i>
                                        <i data-code="礼物"><img src="${base}/statics/images/gif/liwu.gif" width="24" height="24" class="qqface" title="礼物"></i>
                                        <i data-code="拥抱"><img src="${base}/statics/images/gif/yongbao.gif" width="24" height="24" class="qqface" title="拥抱"></i>
                                        <i data-code="强"><img src="${base}/statics/images/gif/qiang.gif" width="24" height="24" class="qqface" title="强"></i>
                                        <i data-code="弱"><img src="${base}/statics/images/gif/ruo.gif" width="24" height="24" class="qqface" title="弱"></i>
                                        <i data-code="握手"><img src="${base}/statics/images/gif/woshou.gif" width="24" height="24" class="qqface" title="握手"></i>
                                        <i data-code="胜利"><img src="${base}/statics/images/gif/shengli.gif" width="24" height="24" class="qqface" title="胜利"></i>
                                        <i data-code="抱拳"><img src="${base}/statics/images/gif/baoquan.gif" width="24" height="24" class="qqface" title="抱拳"></i>
                                        <i data-code="勾引"><img src="${base}/statics/images/gif/gouyin.gif" width="24" height="24" class="qqface" title="勾引"></i>
                                        <i data-code="拳头"><img src="${base}/statics/images/gif/quantou.gif" width="24" height="24" class="qqface" title="拳头"></i>
                                        <i data-code="差劲"><img src="${base}/statics/images/gif/chajin.gif" width="24" height="24" class="qqface" title="差劲"></i>
                                        <i data-code="爱你"><img src="${base}/statics/images/gif/aini.gif" width="24" height="24" class="qqface" title="爱你"></i>
                                        <i data-code="NO"><img src="${base}/statics/images/gif/no.gif" width="24" height="24" class="qqface" title="NO"></i>
                                        <i data-code="OK"><img src="${base}/statics/images/gif/ok.gif" width="24" height="24" class="qqface" title="OK"></i></div>
                                </div>
                            </div>
                            <button class="btn-send">Send</button>
                        </div>
                    </div>
                </div>

                <div class="detail-container">
                    <nav>
                        <ul>
                            <li class="active">Info</li>
                            <li>More</li>
                        </ul>
                    </nav>
                    <div class="detail-content">
                        <div class="detail-content-item">
                            <h4><i class="icon-s icon-person"></i><span>user info</span></h4>
                            <div class="form-wrap">
                                <div class="formitem">
                                    <label for="name">name</label>
                                    <div class="info-text">
                                        <input type="text" name="name" value="" id="customer_name">
                                    </div>
                                </div>
                            </div>

                        </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<div style="display: none;" id="c_div">
    <form enctype="multipart/form-data" target="file_upload_return" id="addArticleForm" method="post" action="" >
        <input id="chat_files" name="myfile"   type="file" accept="image/jpg,image/jpeg,image/png"   style="display: none;">
    </form>
</div>
<iframe id="file_upload_return" name="file_upload_return" style="display: none"></iframe>


<div style="display: none;">
    <textarea id="ck_content"></textarea>
</div>

<script src="${base}/statics/js/jquery-1.10.2.min.js"></script>
<script src="${base}/statics/node_modules/swiper/dist/js/swiper.js"></script>
<script src="${base}/statics/msg/message.js"></script>
<script>
    var current_from = '';
    $(document).ready(function() {
        getCurrentChat();
        // 指定websocket路径
        var websocket;
        var uname = '${userInfo.userName}';
        uname = uname.split('@').join('+40').split(' ').join('-40');
        if ('WebSocket' in window) {
            websocket = new WebSocket("ws://${ws_domain}/ws?userName=" + uname);
        } else if ('MozWebSocket' in window) {
            websocket = new MozWebSocket("ws://${ws_domain}/ws" + uname);
        } else {
            websocket = new SockJS("http://${ws_domain}/ws/sockjs"+uname);
        }
        websocket.onmessage = function(event) {
            var data=JSON.parse(event.data);
            //1 发消息的人
            console.log(data);
            if(data.type == 1){
                var objs = $("#buddy-list-wrap").find('.buddy-item');
                var from  =data.fromName;
                var c_from = from.split('+40').join('@').split('-40').join(' ');
                var c_flag = false;
                if(objs.length > 0){
                    $.each(objs , function(i ,v ){
                        var f_name = $(this).attr('data-from');
                        if(f_name == from){
                            $(this).remove();
                        }
                    });
                }
                if(!c_flag){
                    var ops ='<div class="buddy-item" data-from="'+from+'" id="active_'+from+'">';
                    if(data.text != null && data.text != 0){
                        ops +='<span class="bubble" id="chide_'+from+'">'+data.text+'</span>';
                    }
                    ops +='<div class="buddy-item-avatar-name" style="margin-left: 40px;">'+c_from+'</div>';
//                    ops +='<div class="sbtn-item-close"></div>';
                    ops +='</div>';
                    $('#buddy-list-wrap').append(ops);

                    var last = document.getElementById('buddy-list-wrap').lastChild;
                    var first = document.getElementById('buddy-list-wrap').childNodes[0];
                    document.getElementById('buddy-list-wrap').insertBefore(last, first);


                    //
                }
                $('#buddy-list-wrap').find('.buddy-item').each(function(i , v){
                    var tf = $(this).attr('data-from');
                    if(tf == current_from){
                        $(this).addClass('active').siblings().removeClass('active');
                    }
                })

                //绑定buddy-item click 事件
                $('.buddy-item').bind('click' , function(){
                    $('.buddy-item').removeClass('active');
                    $('.swiper-slide').html('');
                    $(this).addClass('active');
                    $(this).next('span').find('.bubble').eq(0).addClass('chide');
                    current_from = $(this).attr('data-from');
                    $("#customer_name").val(current_from);
                    //get history
                    getHistoryMsg();
                });

            }else if(data.type == 3){
                //消息解析
                var th =  JSON.parse(data.text);
                if(th != '' ){
                    //
                    var str = '<div class="chat-message-item seller">';
                    str += '<div class="message-avatar">' ;
                    str += '<img src=' + seller + ' alt="seller avatar">' ;
                    str +=  '</div>' ;
                    str += '<div class="message-content">';
                    //decode emoji
                    if(th.type == 0){
                        var c_t = decodeEmoji(th.text);
                        str += '<div class="chat-bubble">'+ c_t +'</div>' ;
                    }
                    if(th.type == 1){
                        str += '<div class="chat-bubble"><img src="'+th.text+'" style="width:100px;height:100px;"></div>' ;
                    }else if(th.type == 2){
                        str +='<div class="chat-bubble">' +
                                '<table><tr><td rowspan="4">' +
                                '<img src="'+th.text.goodsImg+'" style="width:100px;height:100px;"></td>' +
                                '<td style="">' + th.text.goodsName +'</td></tr>' +
                                '<tr><td>' + th.text.goodsPrice+'</td>'+
                                '</tr></table>';
                    }
                    str += '<div class="timestamp">'+th.time+'</div>' ;
                    str += '</div>' ;
                    str += '</div>';
                    //判断有无选中 //判断有无当前用户
                    var dobjs = $(".buddy-list-wrap").find('.active');
                    if(dobjs.length > 0){
                        var dj  = dobjs[0];
                        var dfromname = $(dj).attr('data-from');
                        if(dfromname == th.fromName && str != ''){
                            $('.swiper-slide').append(str);
                        }
                    }
                    reChangeMessageBox();
                }
            }
            //3 接收消息内容
        };
    });
    //获取当前存在的会话
    function getCurrentChat(){
        $.post('${base}/im/message/getCurrentChat' , {} , function(data){
           if(data.success){
               var ops = '';
               $.each(data.data.resultData , function( i , v){
                   var cname = v.cname.split('+40').join('@').split('-40').join(' ');
                   ops +='<div class="buddy-item" data-from="'+v.cname+'" id="active_'+v.cname+'">';
                   if(v.unread == null || v.unread == 0){
                       ops +='<span class="bubble chide" id="chide_'+v.cname+'">'+v.unread+'</span>';
                   }else{
                       ops +='<span class="bubble" id="chide_'+v.cname+'">'+v.unread+'</span>';
                   }
                   ops +='<div class="buddy-item-avatar-name" style="margin-left: 40px;">'+cname+'</div>';
//                   ops +='<div class="sbtn-item-close"></div>';
                   ops +='</div>';
               });
               $("#buddy-list-wrap").empty().append(ops);

               //绑定buddy-item click 事件
               $('.buddy-item').bind('click' , function(){
                   $('.buddy-item').removeClass('active');
                   $('.swiper-slide').html('');
                   $(this).addClass('active');
                   $(this).next('span').find('.bubble').eq(0).addClass('chide');
                   current_from = $(this).attr('data-from');
                   $("#chide_"+current_from).addClass('chide');
                   $("#customer_name").val(current_from);
                   //get history
                   getHistoryMsg();
               });
           }
        });
    }
    var imgSrc = "${base}/statics/images/default.png";

    var seller = "${base}/statics/images/default.png";

    $('.sbtn-item-close').on('click',function () {
        console.log(this);
    });

    $('.tool-photo').on('click',function () {
        $('#chat_files').click();
    });



    var Chats = new Swiper('.chat-content', {
        scrollbar: '.chat-scrollbar',
        direction: 'vertical',
        slidesPerView: 'auto',
        mousewheelControl: true,
        freeMode: true,
        noSwiping: true
    });
    Chats.slideTo(1, 0, true);
    function reChangeMessageBox() {
        Chats.init();
        Chats.slideTo(1, 0, false);
    }

    function typeAreaContent() {
        var val = $('#type_area').html();
        return val.toString().length > 5000 ? false : val;
    }

    /* expression */
    $('.tool-expression').on('click',function () {
        var $expWrap = $('.qqface-wrap');
        var isNone = $expWrap.css('display');
        console.log(isNone == 'none');
        if (isNone == 'none') {
            $expWrap.css({
                'display': 'block'
            })
        }else {
            $expWrap.css({
                'display': 'none'
            })
        }
    });
    /* send message */
    function sendMessage() {
        var messageContent,
                messageDate;
        function sendMes() {
            var content = $.trim($('#type_area').html());
            if(current_from == '' ){
                alert('please select a user to send msg');
                return false;
            }
            if(content == ''){
                return false;
            }
            //check emoji
            var qface = $("#type_area").find('.qqface');
            var c_tmp = content;
            if(qface.length > 0 ){
                //解析qface
                $("#type_area").find('.qqface').each(function(){
                    var ca = $(this).attr('title');
                    c_tmp = c_tmp.replace($(this).prop("outerHTML") , '['+ca+']');
                });
            }
            var type = 0;
            var str = '<div class="chat-message-item customer">' +
                    '<div class="message-avatar">' +
                    '<img src="${base}/statics/images/default.png" alt="seller avatar">' +
                    '</div>' +
                    '<div class="message-content">';
            if(type == 0){
                str +='<div class="chat-bubble">'+ content +'</div>' ;
            }
            if(type == 1){
                str+='<div class="chat-bubble"><img src="'+content+'" style="width:100px;height:100px;"></div>' ;
            }
            str +='<div class="timestamp">'+getTime()+'</div></div></div>';
            $('.swiper-slide').append(str);
            reChangeMessageBox();
            var ff = current_from.split('@').join('+40').split(' ').join('-40');
            $.post('${base}/im/message/toSendMsg', {'content': c_tmp, 'toAcount': ff,'type':type }, function (data) {
                if (data.success) {

                }
            });

        }

        if(typeAreaContent().toString().trim().length){
            messageContent = typeAreaContent().toString();
            var dealContent = '';
            messageContent.split(/\n/).forEach(function (el) {
                if (!el.length){
                    el = '&nbsp;';
                }
                dealContent += '<div style="word-break: break-all">'+ el +'</div>';
            });
            messageContent = dealContent;
            sendMes(0);
        }

        $('#type_area').html('');
    }
    $(".btn-send").on('click', sendMessage);
    $('#type_area').on('keydown',function (e) {
        if (e.keyCode == 13 && e.ctrlKey) {
            $('#type_area')[0].html += '\n';
        }else if(e.keyCode == 13){
            e.preventDefault();
            sendMessage();
        }

    });
    //click the emoji
    $(".qqface-layer i").on('click' , function(){
        $("#type_area").append($(this).html());
        $(".qqface-wrap").hide();
    });

</script>

</body>
</html>