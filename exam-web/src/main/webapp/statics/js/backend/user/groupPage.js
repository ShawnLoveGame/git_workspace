$(function () {
    initData();
})

function initData() {

    $("#result_table").bootstrapTable({
        url: base + '/user/findGroupWithPg',
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
                'groupName': $("#g_name").val(),
            };
        },
        columns: [[
            {
                title: '序号', field: 'id', width: 120, align: 'center'},
            {
                title: '分组名', field: 'groupName', width: 120, align: 'center', editable: {
                title: '标题',
                validate: function (v) {
                    if (!v) return '标题不能为空';

                }
            }
            },
            {
                title: '操作', field: 'opt', width: 120, align: 'center',
                formatter: function (value, rec) {
                    var ops = '';
                    // ops += '&nbsp;&nbsp;&nbsp;<button class="btn btn-sm default" onclick="changeStatus(' + rec.id + ' , 1)">冻结</button>';
                    return ops;
                }
            },
        ]],
        onEditableSave: function (field, row, oldValue, $el) {
            var params = {'id': row.id, 'groupName': row.groupName};
            saveGroupInfo(params);
        }
    });

}
function queryData(){
    backendCommon.refreshTableData('result_table' , base + '/user/findGroupWithPg' , {'groupName': $("#g_name").val()});
}
function addNewGroup(){
    $("#lib_win").modal('show');
}
function saveGroup(){
    var groupname = $.trim($("#groupname").val());
    if(groupname == '' ){
        Alert('请输入分组名称');
        return false;
    }
    var params = {'id': '', 'groupName': groupname};

    saveGroupInfo(params);
    $("#lib_win").modal('hide');
}

function saveGroupInfo(params){
    //更新标题
    $.post(base + '/user/saveGroupInfo', params, function (result) {
        if (!result.success) {
            //如果失败，则提示错误信息
            Alert(result.message);
            return false;
        }
        queryData();
    }, 'json');
}
