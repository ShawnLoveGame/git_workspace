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
    <script src="${base}/statics/wap/wapjs/question.js" charset="UTF-8"></script>
</head>
<body>
<div style="">
<div id="question_list">
<#--<#if success == false>-->
<#--<div style="padding: 40px;color: #e60000">-->
<#--${message}-->
<#--</div>-->
<#--<#else>-->
    <input type="hidden" value="${examInfo.id}" id="examId" />
<#if questionList??>
    <#list questionList as q>
        <#assign  q_length = "${questionList?size}"/>
        <div class="question_page" style="<#if q_index != 0>display:none;</#if> " id="q_detail${q_index+1}" data-index="${q_index+1}" data-id="${q.id}">
            <div style="margin-top: 20px;padding: 25px;">
                <div class="square">
                    <div class="float-tag">
                        <div class="num">${q_index+1}</div>
                        <div class="title"><#if q.questionType == 1>单选题
                        <#elseif q.questionType == 2>
                            多选题<#else>判断题</#if></div>
                    </div>
                    <p class="fsz-16"> ${q.questionContent} </p>
                </div>
            </div>
            <div style="padding: 0 0 0 40px;" id="option_checkbox_${q_index+1}" class="ca_div">
                <#if q.questionType !=3>
                    <#list q.optionList as o>
                        <div class="anwser-item" data-v="${o.key}">
                            <label>
                                <input class="anwser-checkbox" name="options${q_index+1}" value="${o.key}" type="<#if q.questionType==1>radio<#else>checkbox</#if>">
                                <span>${o.key}、${o.content}</span>
                            </label>
                        </div>
                    </#list>
                <#else>
                    <div class="anwser-item" data-v="Y">
                        <label>
                            <input class="anwser-checkbox" name="options${q_index+1}" value="Y" type="radio"/>正确
                        </label>
                    </div>
                    <div class="anwser-item" data-v="N">
                        <label>
                            <input class="anwser-checkbox" name="options${q_index+1}" value="N" type="radio"/>错误
                        </label>
                    </div>
                </#if>

            </div>

            <div style="margin:70px 0 30px 0;padding: 0 15px;">
                <div class="clearfix">
                    <#if q_index &gt; 0 >
                        <div style="width: 25%;float: left;">
                            <div style="padding-right: 6px;" onclick="changePage(${q_index} , 1);">
                                <div class="level-btn pre">上一题</div>
                            </div>
                        </div>
                    </#if>
                    <div style="width: 50%;float: left;">
                        <#if q_index+1== q_length>
                            <div style="padding: 0 6px;" onclick="toSubmitPage();">
                                <div class="level-btn next">提交答案</div>
                            </div>
                        <#else>
                            <div style="padding: 0 6px;" onclick="changePage(${q_index+2} , 2);">
                                <div class="level-btn next">下一题</div>
                            </div>
                        </#if>

                    </div>
                    <div style="width: 25%;float: left;">
                        <div style="padding-left: 6px;" onclick="restCurrentPage(${q_index+1});">
                            <div class="level-btn repeat">重新答题</div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </#list>
</#if>
<#--</#if>-->
</div>
</div>
<div class="popup">
    请选择一个组别哦~
</div>
<div class="mask"></div>
</body>
</html>
