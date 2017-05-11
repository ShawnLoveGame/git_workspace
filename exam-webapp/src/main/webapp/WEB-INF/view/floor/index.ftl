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
        var departmentId = '${user.departmentId!''}';
    </script>
    <script src="${base}/statics/wap/wapjs/index.js?v=10022" charset="UTF-8"></script>
</head>
<body>

<div style="">
    <div class="warm-tip">
        <img class="warm-icon" src="${base}/statics/wap/images/worm.png" height="20">
        <span class="text">请选择您的单位</span>
    </div>
    <div id="group_d">
    <#if groupList??>
        <#list groupList as g>
            <div style="padding: 15px;">
                <div class="group-title">${g.groupName}</div>
                <div class="group-wrapper clearfix">
                    <#if g.departmentList??>
                        <#list g.departmentList as d>
                            <div class="comp-item" data-id="${d.id}" id="item_${d.id}">
                                <label>
                                    <input type="radio" value="${d.id}" name="department">
                                    <span>${d.departmentName}</span>
                                </label>
                            </div>
                        </#list>
                    </#if>
                </div>
            </div>
            <div style="height: 10px;background: #f7f8fc;"></div>
        </#list>
    </#if>
    </div>
    <div style="padding: 10px 0 30px 0;">
        <div id="js_one_level" class="div-btn" onclick="startAnswer()">选好了,开始答题吧</div>
    </div>
</div>
<div class="popup">
    请选择一个组别哦~
</div>
<div class="mask"></div>
</body>
</html>
