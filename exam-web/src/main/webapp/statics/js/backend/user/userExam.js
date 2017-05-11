$(function(){
    initData();
});

function initData() {

    $("#result_table").bootstrapTable({
        url: base + '/user/findUserExamWithPg',
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
                'userId':$("#userId").val()
            };
        },
        columns: [[
            {
                title: '所属分组', field: 'groupName', width: 120, align: 'center'
            },
            {
                title: '单位', field: 'departmentName', width: 120, align: 'center'
            },
            {
                title: '用户名', field: 'userName', width: 120, align: 'center'
            },
            // {
            //     title: '考试名称', field: 'examName', width: 120, align: 'center'
            // },
            {
                title: '答题时间', field: 'submitTime', width: 120, align: 'center',
                formatter:function(value , rec){
                    return backendCommon.dateTimeToLocalStringWithSeconds(value);
                }
            },
            {
                title: '得分', field: 'userRecord', width: 120, align: 'center'
            },
        ]],
    });

}