$(function(){
    initData();
});

function initData() {

    $("#result_table").bootstrapTable({
        url: base + '/user/findUserWinWithPg',
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
                'startTime':$("#startTime").val(),
                'endTime':$("#endTime").val()
            };
        },
        columns: [[
            {
                title: '获奖时间', field: 'winTime', width: 120, align: 'center'
            },
            {
                title: '单位', field: 'departmentName', width: 120, align: 'center'
            },
            {
                title: '用户名', field: 'userName', width: 120, align: 'center'
            },
        ]],
    });

}
function queryWinData(){
    backendCommon.refreshTableData('result_table' , base + '/user/findUserWinWithPg' , {'startTime':$("#startTime").val(),
        'endTime':$("#endTime").val()});
}