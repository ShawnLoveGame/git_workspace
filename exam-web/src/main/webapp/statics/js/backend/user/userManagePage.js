$(function () {
    initData();
    //初始化查询分组
    initGroupList();
});
function initGroupList(){
    $.post(base + '/user/findDepartmentList' , {}, function(data){
        if(data){
            var ops = '<option value="">---请选择---</option>';
            $.each(data  , function (i ,v ) {
                ops +='<option value="'+v.id+'">'+v.departmentName+'</option>'
            });
            $("#g_id").html(ops);
        }
    });
}

function initData() {

    $("#result_table").bootstrapTable({
        url: base + '/user/findUserWithPg',
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
                'userName': $("#d_name").val(),
                'departmentId': $("#g_id").val(),
            };
        },
        columns: [[
            {
                title: '单位', field: 'departmentName', width: 120, align: 'center'
            },
            {
                title: '用户名', field: 'userName', width: 120, align: 'center'
            },
            {
                title: '头像', field: 'headPic', width: 120, align: 'center',
                formatter:function(value , rec){
                    if(value != '' ){
                        return '<img src="'+value+'"  style="width:60px;height:60px;">'
                    }else{
                        return '';
                    }
                }
            },
            {
                title: '性别', field: 'sex', width: 120, align: 'center',
                formatter:function(value , rec){
                    if(value == 1){
                        return '男';
                    }else {
                        return '女';
                    }
                }
            },
            {
                title: '是否已参加考试', field: 'hasExam', width: 120, align: 'center',
                formatter:function(value , rec){
                    if(value == 1){
                        return '是';
                    }else {
                        return '否';
                    }
                }
            },
            {
                title: '操作', field: 'opt', width: 120, align: 'center',
                formatter:function(value , rec){
                    var ops = '';
                    if(rec.hasExam == 1){
                        ops +='<button class="btn btn-sm blue" onclick="queryExam('+rec.id+');">查看考试情况</button>';
                    }
                    return ops;
                }
            },
        ]],
    });

}
function queryData(){
    backendCommon.refreshTableData('result_table' , base + '/user/findUserWithPg' , {'userName': $("#d_name").val(),
        'departmentId': $("#g_id").val()});
}
function queryExam(userId){
    window.location.href= base + '/user/findUserExamByUserId?userId='+userId;
}
