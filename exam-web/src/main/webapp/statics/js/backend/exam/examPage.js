$(function () {
    initData();
});

function initData() {

    $("#result_table").bootstrapTable({
        url: base + '/exam/findExamInfoWithPg',
        animate: true,
        pagination: true,
        method: "post",
        sidePagination: 'server',
        pageSize: 15,
        pageList: [15, 30, 50],
        queryParamsType: 'limit',
        contentType: "application/x-www-form-urlencoded",
        queryParams: function (params) {
            return {
                'page': params.offset / params.limit + 1,
                'rows': params.limit,
                'examTitle': $("#e_title").val(),
                "examStatus": $("#e_status").val()
            };
        },
        columns: [[
            {
                title: '名称', field: 'examTitle', width: 120, align: 'center'
            },
            {
                title: '状态', field: 'examStatus', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (value == 0) {
                        return "启用";
                    } else {
                        return "冻结";
                    }
                }
            },
            {
                title: '时间设定', field: 'time', width: 120, align: 'center',
                formatter: function (value, rec) {
                    return '开始时间：' + backendCommon.dateTimeToLocalStringWithSeconds(rec.examStartTime) + '<br>' +
                        '结束时间：' + backendCommon.dateTimeToLocalStringWithSeconds(rec.examEndTime);
                    // return '';
                }
            },
            // {
            //     title: '考试时长', field: 'examTimes', width: 120, align: 'center'
            // },
            {
                title: '试卷类型', field: 'examType', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (value == 1) {
                        return "普通试卷";
                    } else {
                        return "随机试卷";
                    }
                }
            },
            {
                title: '卷面总分', field: 'totalScore', width: 120, align: 'center'
            },
            {
                title: '操作时间', field: 'opTime', width: 120, align: 'center',
                formatter: function (value, rec) {
                    return backendCommon.dateTimeToLocalStringWithSeconds(value);
                }
            },
            {
                title: '操作', field: 'opt', width: 120, align: 'center',
                formatter: function (value, rec) {
                    var ops = '<button class="btn btn-sm blue" onclick="editExamInfo(' + rec.id + ' )">编辑</button>';
                    if (rec.examStatus == 0) {
                        ops += '&nbsp;&nbsp;&nbsp;<button class="btn btn-sm default" onclick="changeStatus(' + rec.id + ' , 1)">冻结</button>';
                    } else {
                        ops += '&nbsp;&nbsp;&nbsp;<button class="btn btn-sm default" onclick="changeStatus(' + rec.id + ' , 0)">启用</button>';
                    }
                    return ops;
                }
            },
        ]]
    });

}
function queryData(){
    backendCommon.refreshTableData('result_table' , base + '/exam/findExamInfoWithPg' , {'examTitle': $("#e_title").val(),
        "examStatus": $("#e_status").val()});
}
function changeStatus(id , status){
    Confirm({
        content:'确定要操作吗？',
        onOk:function(){
            var params = {
                'id':id ,
                'examStatus':status
            }
            saveExamInfo(params);
        }
    })
}
function saveExamInfo(params){
    //更新标题
    $.post(base + '/exam/saveExamInfo', params, function (result) {
        if (!result.success) {
            //如果失败，则提示错误信息
            Alert(result.message);
            return false;
        }
        queryData();
    }, 'json');
}
function addNewExamInfo(){
    $("#lib_win").modal('show');
    $("#examId").val('');
    clearData();
    addOptions();
}
function addOptions(){
    var ops = '<tr>';
    ops +='<td><select class="c_input questiontype"><option value="1">单选</option><option value="2">多选</option></select><td>';
    ops +='<td>试题数量：<input type="text" class="q_nums" /></td>';
    ops +='<td>单题分值：<input type="text" class="q_score" /></td>';
    ops +='<td><a href="javascript:;" onclick="$(this).parent().parent().remove()" class="tm_ico_delete"></a></td></tr>';
    $("#content_table").append(ops);
}
function clearData(){
    $("#examTitle").val('');
    $("#examStartTimeStr").val('');
    $("#examEndTimeStr").val('');
    $("#examTimes").val('');
    $("#examType").val('1');
    $("#examStatus").val('0');
    $("#content_table").html('');
}
function editExamInfo(id){
    clearData();
    $("#examId").val(id);
    $("#lib_win").modal('show');

    $.post(base + '/exam/findExamInfoById' , {'id':id} , function(data){
       if(data){
           $("#examTitle").val(data.examTitle);
           if(data.examStartTime){
               $("#examStartTimeStr").val(backendCommon.dateTimeToLocalStringWithSeconds(data.examStartTime));
           }
           if(data.examEndTime){
               $("#examEndTimeStr").val(backendCommon.dateTimeToLocalStringWithSeconds(data.examEndTime));
           }
           // $("#examTimes").val(data.examTimes);
           $("#examType").val(data.examType);
           $("#examStatus").val(data.examStatus);

           var ops = '';
           $.each(data.contentList , function(i , v){
               ops += '<tr>';
               ops +='<td><select class="c_input questiontype">';
               if(v.quetionType == 1){
                   ops +='<option value="1" selected>单选</option><option value="2">多选</option>';
               }else{
                   ops +='<option value="1">单选</option><option value="2" selected>多选</option>';
               }
               ops +='</select><td>';
               ops +='<td>试题数量：<input type="text" class="q_nums" value="'+v.totalNum+'"/></td>';
               ops +='<td>单题分值：<input type="text" class="q_score" value="'+v.singleScore+'"/></td>';
               ops +='<td><a href="javascript:;" onclick="$(this).parent().parent().remove()" class="tm_ico_delete"></a></td></tr>';
           });
           $("#content_table").html(ops);
       }
    });

}
function saveExam(){
    var examId = $("#examId").val();
    var examTitle =  $("#examTitle").val();
    var examStartTimeStr =  $("#examStartTimeStr").val();
    var examEndTimeStr =  $("#examEndTimeStr").val();
    if(examStartTimeStr == '' || examEndTimeStr == '' ){
        Alert('请输入答题时间');
        return false;
    }
    if(examStartTimeStr > examEndTimeStr){
        Alert('答题开始时间不能大于结束时间');
        return false;
    }
    // var examTimes =  $("#examTimes").val();
    var examType =  $("#examType").val();
    var examStatus =  $("#examStatus").val();
    if(examTitle == '' ){
        Alert('请输入试卷名称');
        return false;
    }

    var tmp = [];
    var objs = $("#content_table").find('tr');
    if(objs.length == 0){
        Alert('请设置试题题目构成');
        return false;
    }
    var flag = '';
    $.each(objs  , function (i , v) {
        var questiontype = $(this).find('.questiontype').eq(0).val();
        var q_nums = $(this).find('.q_nums').eq(0).val();
        var q_score  = $(this).find('.q_score').eq(0).val();
        if(q_nums == '' || q_score == '' ){
            flag = '请填写考试题目构成信息';
        }
        var content = {};
        content['quetionType'] = questiontype;
        content['totalNum'] = q_nums;
        content['singleScore'] = q_score;
        tmp.push(content);
    });

    if(flag != '' ){
        Alert(flag);
        return false;
    }
    var wdto = {};
    wdto['id'] = examId ;
    wdto['examType'] = $("#examType").val() ;
    wdto['examStartTimeStr'] = examStartTimeStr;
    wdto['examEndTimeStr'] = examEndTimeStr;
    wdto['examTitle'] = examTitle ;
    wdto['examStatus'] = examStatus ;
    wdto['contentList'] = tmp ;

    $.post(base + '/exam/saveEditExamInfo' , {'jsonStr':JSON.stringify(wdto)} , function(data){
        console.log(data);
        if(data.success){
            $("#lib_win").modal('hide');
            queryData();
        }else{
            Alert(data.message);
        }
    })

}
