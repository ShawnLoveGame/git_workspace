$(function () {
    initData();


    $("#questionType").change(function () {
        var type = $(this).val();
        if(type == 3){
            $("#add_option").hide();
        }else{
            $("#add_option").show();
        }
        if ($(this).val() == 1 || $(this).val() == 3) {
            $('input[name="options"]').each(function () {
                $(this).attr('type', 'radio');
                $(this).attr('data-type' , type);
                $(this).removeAttr('checked');
                $(this).removeClass('active');
            });
        } else {
            $('input[name="options"]').each(function () {
                $(this).attr('type', 'checkbox');
                $(this).attr('data-type' , '2');
                $(this).removeAttr('checked');
                $(this).removeClass('active');
            });
        }
        initOptions(type);
    });

    $('body').on({
        change:function(){
            var type = $(this).attr('data-type');
            if($(this).prop('checked')){
                $(this).addClass('active');
                var v = $(this).val();
                $(this).attr('checked' , 'checked');
                if(type == 1){//单选
                    $(this).parent('td').parent('tr').siblings().each(function(i  ,v ){
                        $(this).find('.options').removeClass('active');
                        $(this).find('.options').removeAttr('checked');
                    });
                }
            }else{
                $(this).removeClass('active');
            }

        }
    },'.options')

});

function initData() {

    $("#result_table").bootstrapTable({
        url: base + '/exam/findQuestionWithPg',
        animate: true,
        pagination: true,
        method: "post",
        sidePagination: 'server',
        pageSize: 15,
        pageList: [15, 30, 50],
        queryParamsType: 'limit',
        contentType: "application/x-www-form-urlencoded",
        clickToSelect:true,
        queryParams: function (params) {
            return {
                'page': params.offset / params.limit + 1,
                'rows': params.limit,
                'status': $("#status").val(),
                'questionContent': $.trim($("#q_content").val()),
                "questionType": $("#q_type").val()
            };
        },
        columns: [[
            {
                field: 'ck', checkbox: true
            },
            {
                title: '答题时间', field: 'answerTime', width: 80, align: 'center'
            },
            {
                title: '类型', field: 'questionType', width: 60, align: 'center',
                formatter: function (value, rec) {
                    if (value == 1) {
                        return '单选';
                    } else if(value == 2) {
                        return '多选';
                    } else if(value == 3){
                        return '判断题';
                    }
                }
            },
            {
                title: '状态', field: 'status', width: 60, align: 'center',
                formatter: function (value, rec) {
                    if (value == 0) {
                        return "启用";
                    } else {
                        return "冻结";
                    }
                }
            },
            {
                title: '题干', field: 'questionContent', width: 200, align: 'center'
            },
            {
                title: '操作时间', field: 'opTime', width: 100, align: 'center',
                formatter: function (value, rec) {
                    return backendCommon.dateTimeToLocalStringWithSeconds(value);
                }
            },
            {
                title: '操作', field: 'opt', width: 120, align: 'center',
                formatter: function (value, rec) {
                    var ops = '<button class="btn btn-sm blue" onclick="editQuestions(' + rec.id + ')">编辑</button>';
                    if (rec.status == 0) {
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
function queryData() {
    var params = {
        'status': $("#status").val(),
        'questionContent': $.trim($("#q_content").val()),
        "questionType": $("#q_type").val()
    };
    backendCommon.refreshTableData('result_table', base + '/exam/findQuestionWithPg', params);
}

function addoptions() {
    var tm_options = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
    var qtype = $("#questionType").val();
    for (var i = 0; i < tm_options.length; i = i + 1) {
        var this_value = tm_options[i];
        //不存在就添加
        if (!document.getElementById("tm_option_" + this_value)) {
            var html = [];
            html.push('<tr>');
            html.push('	<td  style="width: 15%" >');
            if (1 == qtype) {
                html.push('	<input type="radio" data-type="1" id="tm_option_' + this_value + '" class="options"  name="options" value="' + this_value + '" /><span>选项' + this_value + '：</span></td>');
            } else if (2 == qtype) {
                html.push('	<input type="checkbox" data-type="2" id="tm_option_' + this_value + '"  class="options"  name="options" value="' + this_value + '" /><span>选项' + this_value + '：</span></td>');
            }

            html.push('	<td><input  class="form-control  option_content" data-type="' + this_value + '"/></td>');
            html.push('	<td width="50"><a href="javascript:;" onclick="$(this).parent().parent().remove()" class="tm_ico_delete"></a></td>');
            html.push('</tr>');
            $("#options_table").append(html.join(""));
            break;
        }
    }
}
function changeStatus(id, status) {
    Confirm({
        content: '确定要操作吗？',
        onOk: function () {
            var params = {
                'id': id,
                'status': status
            }
            $.post(base + '/exam/updateQuestionsInfo' , params , function(data){
                if(data.success){
                    queryData();
                }else{
                    Alert(data.message);
                }
            })
        }
    })
}

function addNewLibrary() {
    $("#question_win").modal('show');

    clearFormData();

    findAllLibraryList('');

    //初始化选项
    initOptions('');
}
function initOptions(c_type) {
    var qtype = $("#questionType").val();
    // if(c_type == 3){
        $("#options_table").html('');
    // }
    var tm_options ;
    if(qtype == 3){
        tm_options = ['A', 'B'];
    }else {
        tm_options = ['A', 'B', 'C', 'D'];
    }
    var html = [];

    for (var i = 0; i < tm_options.length; i = i + 1) {
        var this_value = tm_options[i];
        //不存在就添加
        if (!document.getElementById("tm_option_" + this_value)) {
            html.push('<tr>');
            html.push('	<td  style="width: 15%" >');
            if (1 == qtype) {
                html.push('	<input type="radio" data-type="1"  id="tm_option_' + this_value + '" class="options"  name="options" value="' + this_value + '" /><span>选项' + this_value + '：</span></td>');
            } else if (2 == qtype) {
                html.push('	<input type="checkbox" data-type="2"  id="tm_option_' + this_value + '"  class="options" name="options" value="' + this_value + '" /><span>选项' + this_value + '：</span></td>');
            }else if(3 == qtype){
                if(this_value == 'A'){
                    html.push('<input type="radio" data-type="3"  id="tm_option_' + this_value + '" class="options"  name="options" value="Y" />正确');
                }else{
                    html.push('<input type="radio" data-type="3"  id="tm_option_' + this_value + '" class="options"  name="options" value="N" />错误');
                }
            }
            if(qtype != 3){
                html.push('	<td><input class="options form-control option_content" data-type="' + this_value + '"/></td>');
                html.push('	<td width="50"><a href="javascript:;" onclick="$(this).parent().parent().remove()" class="tm_ico_delete"></a></td>');
            }
            html.push('</tr>');
        }
    }
    $("#options_table").append(html.join(""));

}
function clearFormData() {
    $("#questionId").val('');
    $("#questionType").val('1');
    $("#questionStatus").val('0');
    $("#content").val('');
    $("#options_table").html('');
}

function editQuestions(id) {
    $("#question_win").modal('show');
    clearFormData();

    $.post(base + '/exam/findQuestionById', {'id': id}, function (data) {
        if (data) {
            $("#answerTime").val(data.answerTime);
            $("#questionId").val(data.id);
            $("#questionType").val(data.questionType);
            $("#questionStatus").val(data.status);
            $("#content").val(data.questionContent);
            //类型

            //选项 并赋值
            var ops = '';
            var keys = [];
            if(data.questionType != 3){
                $("#add_option").show();
                $.each(data.optionList, function (i, v) {
                    ops += '<tr>' +
                        '<td style="width: 15%" >';
                    if (data.questionType == 1 ) {
                        if (v.checked) {
                            keys.push(v.key);
                        }
                        ops += '<input type="radio" id="tm_option_' + v.key + '" class="options"  name="options" value="' + v.key + '" class="" data-type="'+data.questionType+'">';
                    } else {
                        if (v.checked) {
                            keys.push(v.key);
                        }
                        ops += '<input type="checkbox" id="tm_option_' + v.key + '" class="options"  name="options" value="' + v.key + '" class="" data-type="'+data.questionType+'">';
                    }
                    ops += '<span>选项' + v.key + '：</span></td><td>' +
                        '<input type="text" class="form-control  option_content" value="' + v.content + '" data-type="' + v.key + '">' +
                        '</td>' +
                        '<td width="50"><a href="javascript:;" onclick="$(this).parent().parent().remove()" class="tm_ico_delete"></a></td>' +
                        '</tr>';
                });
                //动态赋值
                $("#options_table").html(ops);
                for(var i = 0 ; i<keys.length ; i++){
                    $("#tm_option_"+keys[i]).attr('checked' , 'checked');
                    $("#tm_option_"+keys[i]).attr('checked' , 'checked');
                }

            }else{
                $("#add_option").hide();
                ops +='<tr>';
                ops += '<td><input type="radio" data-type="3"  id="tm_option_A" class="options"  name="options" value="Y" />正确</td>';
                ops += '<td><input type="radio" data-type="3"  id="tm_option_B" class="options"  name="options" value="N" />错误</td>';
                $("#options_table").html(ops);

                if(data.answers == 'Y'){
                    $("#tm_option_A").attr('checked' , 'checked');
                }else if(data.answers == 'N'){
                    $("#tm_option_B").attr('checked' , 'checked');
                }
            }

        }
    });
}


function findAllLibraryList(libId) {
    $.post(base + '/exam/findAllLibraryList', {}, function (data) {
        if (data) {
            var ops = '<option value="">--请选择--</option>';
            $.each(data, function (i, v) {
                ops += '<option value="' + v.id + '">' + v.libraryTitle + '</option>';
            });

            $("#libId").html(ops);
            if (libId != '') {
                $("#libId").val(libId);
            }

        }
    })
}
function saveQuestionInfo() {
    var id = $("#questionId").val();
    var info = {};
    info['id'] = id;
    info['questionType'] = $("#questionType").val();
    var libId = $("#libId").val();
    if (libId == '') {
        Alert('请选择题目所在题库');
        return false;
    }
    info['answerTime'] = $("#answerTime").val();
    info['questionStatus'] = $("#questionStatus").val();
    var content = $.trim($("#content").val());
    if (content == '') {
        Alert('请输入题干内容');
        return false;
    }
    info['questionContent'] = content;

    var options = [];
    if($("#questionType").val() != 3){
        var objs = $("#options_table").find('.option_content');
        if (objs.length == 0) {
            Alert('请添加选项');
            return false;
        }
        $.each(objs, function (i, v) {
            var o_b = {};
            o_b.content = $(this).val();
            var key = $(this).attr('data-type');
            o_b.key = key;
            if ($("#tm_option_" + key).attr('checked')) {
                o_b.checked = true;
            } else {
                o_b.checked = false;
            }
            options.push(o_b);
        });
    }else{
        //
        if($("#tm_option_A").prop('checked')){
            info['answers'] = 'Y';
        }else{
            info['answers'] = 'N';
        }
    }
    info['optionList'] = options;

    $.post(base + '/exam/saveQuestionInfo', {"jsonStr":JSON.stringify(info)}, function (result) {
        if (!result.success) {
            Alert(result.message);
            return false;
        }else{
            $("#question_win").modal('hide');
            queryData();
        }

    }, 'json');
}

function batchDelete(){
    var rows = $("#result_table").bootstrapTable('getSelections');
    if(rows.length == 0){
        Alert('请选择条目在删除');
        return false;
    }
    var ids = [];
    $.each(rows , function( i , v){
        ids.push(v.id);
    });
    Confirm({
        content:'确认要删除吗',
        onOk:function(){
            $.post(base + '/exam/batchDeleteQuestion' , {'idStr':JSON.stringify(ids)} , function(data){
                queryData();
            });
        }
    })

}
