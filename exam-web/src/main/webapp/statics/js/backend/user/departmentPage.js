$(function () {
    initData();
    //初始化查询分组
    initGroupList(1);
});
function initGroupList(){
    $.post(base + '/user/findGroupList' , {}, function(data){
        if(data){
            var ops = '<option value="">---请选择---</option>';
            $.each(data  , function (i ,v ) {
                ops +='<option value="'+v.id+'">'+v.groupName+'</option>'
            });
            $("#g_id").html(ops);
        }
    });
}

function initData() {

    $("#result_table").bootstrapTable({
        url: base + '/user/findDepartmentWithPg',
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
                'departmentName': $("#d_name").val(),
                'groupId': $("#g_id").val(),
            };
        },
        columns: [[
            {
                title: '序号', field: 'id', width: 120, align: 'center'},
            {
                title: '分组名', field: 'groupName', width: 120, align: 'center'
            },
            {
                title: '部门名称', field: 'departmentName', width: 120, align: 'center', editable: {
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
                    ops += '<button class="btn btn-sm default" onclick="editDepartment(' + rec.id + ')">编辑</button>';
                    return ops;
                }
            },
        ]],
        onEditableSave: function (field, row, oldValue, $el) {
            var params = {'id': row.id, 'departmentName': row.departmentName};
            saveDepartmentInfo(params);
        }
    });

}
function queryData(){
    backendCommon.refreshTableData('result_table' , base + '/user/findDepartmentWithPg' , {'departmentName': $("#g_name").val() , "groupId":$("#g_id").val()});
}
function addNewGroup(){
    $("#lib_win").modal('show');
    clearFormData();

    findGroupList('');
}
function clearFormData(){
    $("#departmentName").val('');
    $("#departmentId").val('');
}

function findGroupList(gid){
    $.post(base + '/user/findGroupList' , {}, function(data){
        if(data){
            var ops = '';
            $.each(data  , function (i ,v ) {
                ops +='<label class="radio-inline"><div class="radio" id="uniform-group'+v.id+'">';
                if(v.id == gid){
                    ops +='<span class="checked" data-id="'+v.id+'">';
                }else{
                    ops +='<span class="" data-id="'+v.id+'">';
                }
                ops +='<input type="radio" name="groups" id="group'+v.id+'" value="'+v.id+'"  checked>';
                ops +='</span></div>';
                ops +=v.groupName + '</label>';
            });
            $("#groupList").html(ops);
        }
    });
}

function editDepartment(id){
    $("#lib_win").modal('show');
    clearFormData();
    $.post(base + '/user/findDepartmentById' , {'id':id} , function(data){
        if(data){
            $("#departmentName").val(data.departmentName);
            $("#departmentId").val(data.id);
            findGroupList(data.groupId);
        }
    });
}


function saveGroup(){
    var departmentName = $.trim($("#departmentName").val());
    if(departmentName == '' ){
        Alert('请输入分组名称');
        return false;
    }
    var gid = '';
    $("#groupList").find('.radio-inline').each(function(){
        if($(this).find('span').hasClass('checked')){
            gid = $(this).find('span').eq(0).attr('data-id');
        }
    })
    if(gid == '' ){
        Alert('请选择部门所属分组');
        return false;
    }
    var params = {'id': $("#departmentId").val(), 'departmentName': departmentName , 'groupId':gid};

    saveDepartmentInfo(params);
    $("#lib_win").modal('hide');
}

function saveDepartmentInfo(params){
    //更新标题
    $.post(base + '/user/saveDepartmentInfo', params, function (result) {
        if (!result.success) {
            //如果失败，则提示错误信息
            Alert(result.message);
            return false;
        }
        queryData();
    }, 'json');
}
