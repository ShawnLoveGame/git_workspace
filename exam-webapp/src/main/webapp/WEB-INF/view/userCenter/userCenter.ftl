<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telphone=no, email=no"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
    <title>开始答题</title>
    <link rel="stylesheet" href="${base}/statics/wap/css/index.css">
    <script src="${base}/statics/wap/js/zepto.min.js"></script>
    <script>
        var base = '${base}';
    </script>
    <script src="${base}/statics/wap/wapjs/userCenter.js" charset="UTF-8"></script>
</head>
<body>
<div id="tip_div" style="background-color: #575B59;z-index: 10;padding: 5px;color: #FFF;">
    <span style="position: absolute;right: 10px;top: 20px;" onclick="hideTips();">X</span>
    <div style="color: #FFF;margin-left: 0px;">您已进入抽奖对象名单，请在明天的微信推送文章里查看中奖名单。</div>
</div>
<div>
    <div class="res-info-top">
        <div class="clearfix" style="padding: 20px 10px;">
            <div class="res-top-item" style="padding-right: 20px;">
                <div style="text-align: right;">
                    <img src="${user.headPic?default('')}" width="60px" style="border-radius: 30px;">
                    <div><p style="width: 70px;float: right;text-align: center;">${user.nickName}</p></div>
                </div>
            </div>
            <div class="res-top-item" style="padding-left: 20px;">
                <div style="text-align: left;">
                    <div style="font-size: 30px;">${user.examUserAnswer.userRecord?default('0')}</div>
                    <div>我的分数(分)</div>
                </div>
            </div>
        </div>
    </div>
    <div class="res-info-top md-line">
        <div class="clearfix" style="padding: 0 10px 20px 0;">
            <div class="res-top-item" style="padding-right: 20px;">
                <div style="text-align: center;">
                    <div id="cu_order">110</div>
                    <div>我的单位排名</div>
                </div>
            </div>
            <div class="res-top-item" style="padding-left: 20px;">
                <div style="text-align: center;">
                    <div id="total_num">500</div>
                    <div>参加考试人数</div>
                </div>
            </div>
        </div>
        rz
    </div>

    <div style="padding: 15px;">
        <ul class="res-info-nav clearfix" id="groupList">
            <li class="active" data-id="1">A组</li>
            <li class="" data-id="2">B组</li>
            <li class="" data-id="3">C组</li>
        </ul>
    </div>

    <div style="padding: 0 15px;">
        <ul class="table-list active">
            <li class="clearfix">
                <ul class="table-list-line clearfix">
                    <li>排名</li>
                    <li>单位名</li>
                    <li>排名分数</li>
                </ul>
                <div id="depart_div">

                </div>


            </li>
        </ul>
    </div>
</div>
</body>
</html>
