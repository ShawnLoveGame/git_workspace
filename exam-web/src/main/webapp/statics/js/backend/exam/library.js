$(function () {
    initData();
});

function initData() {

    $("#result_table").bootstrapTable({
        url: base + '/exam//findLibraryWithPg',
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
                'libraryTitle': $("#libraryTitle").val(),
                "libraryStatus": $("#libraryStatus").val()
            };
        },
        columns: [[
            {
                title: '标题', field: 'libraryTitle', width: 120, align: 'center', editable: {
                title: '标题',
                validate: function (v) {
                    if (!v) return '标题不能为空';

                }
            }
            },
            {
                title: '状态', field: 'libraryStatus', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (value == 0) {
                        return "启用";
                    } else {
                        return "冻结";
                    }
                }
            },
            {
                title: '操作时间', field: 'updateTime', width: 120, align: 'center',
                formatter: function (value, rec) {
                    return backendCommon.dateTimeToLocalStringWithSeconds(value);
                }
            },
            {
                title: '操作', field: 'opt', width: 120, align: 'center',
                formatter: function (value, rec) {
                    var ops = '';
                    if (rec.libraryStatus == 0) {
                        ops += '&nbsp;&nbsp;&nbsp;<button class="btn btn-sm default" onclick="changeStatus(' + rec.id + ' , 1)">冻结</button>';
                    } else {
                        ops += '&nbsp;&nbsp;&nbsp;<button class="btn btn-sm default" onclick="changeStatus(' + rec.id + ' , 0)">启用</button>';
                    }
                    return ops;
                }
            },
        ]],
        onEditableSave: function (field, row, oldValue, $el) {

            var params = {'id': row.id, 'libraryTitle': row.libraryTitle};
            saveLibraryInfo(params);
            // //更新标题
            // $.post(base + '/exam/saveLibraryInfo', {'id': row.id, 'libraryTitle': row.libraryTitle}, function (result) {
            //     if (!result.success) {
            //         //如果失败，则提示错误信息
            //         Alert(result.message);
            //         return false;
            //     }
            //     queryData();
            // }, 'json');
        }
    });

}
function queryData(){
    backendCommon.refreshTableData('result_table' , base + '/exam//findLibraryWithPg' , {});
}
function changeStatus(id , status){
    Confirm({
        content:'确定要操作吗？',
        onOk:function(){
            var params = {
                'id':id ,
                'libraryStatus':status
            }
            saveLibraryInfo(params);
        }
    })
}
function saveLibraryInfo(params){
    //更新标题
    $.post(base + '/exam/saveLibraryInfo', params, function (result) {
        if (!result.success) {
            //如果失败，则提示错误信息
            Alert(result.message);
            return false;
        }
        queryData();
    }, 'json');
}
function addNewLibrary(){
    $("#lib_win").modal('show');

}
function saveLibInfo(){
    var title = $.trim($("#libtitle").val());
    if(title == '' ){
        Alert('标题不能为空');
        return ;
    }
    var params = {
        'libraryTitle':title,
        'libraryStatus':0
    }
    saveLibraryInfo(params);

    $("#lib_win").modal('hide');
}
