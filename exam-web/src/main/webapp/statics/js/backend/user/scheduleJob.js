//页面初始化
$(function(){
	// 初始化table
	initTable();
});

//初始化列表页面
function initTable() {
	$('#data_result').bootstrapTable({
        url: base + '/job/findScheduleJobsPgByDTO',
        animate: true,
        pagination: true,
        method: "post",
        sidePagination: 'server',
        pageSize: 15,
        pageList: [15, 30, 50],
        queryParamsType: 'limit',
        contentType: "application/x-www-form-urlencoded",
		columns : [[
		            {field : 'jobName',title : '定时器名称',width :$(this).width() * 0.20,align : 'center'},
					{field : 'jobGroup',title : '定时器分组',width :$(this).width() * 0.11,align : 'center'},
					{field : 'cronExpression',title : 'cron_expression',width : $(this).width() * 0.13,align : 'center'},
					{field : 'jobClass',title : '执行路径',width : $(this).width() * 0.30,align : 'center'},
					{field : 'jobStatus',title : '状态',width :$(this).width() * 0.08,align : 'center',
						formatter : function(value, rec, index) {
							if(value == 0){
								return "停用";
							}
							if(value == 1){
								return "<b><font color='red'>启用</font></b>";
							}
							if(value == 2){
								return "删除";
							}
						}	
					},
					{field : 'jobDesc',title : '说明',width :$(this).width() * 0.20,align : 'center'},
					{field : 'createTime',title : '创建时间',width :$(this).width() * 0.20,align : 'center',
						formatter : function(value, rec, index) {
							var str = "";
							if(value != '') {
								str = backendCommon.dateTimeToLocalStringWithSeconds(rec.createTime);
							}
							return str;
						}	
					},
					{field : 'uid',title : '操作',width : $(this).width() * 0.35,align : 'center',
						 formatter:function(value,rec){
							 var  b = '';
							 if(rec.jobStatus == 0){
								 b += '<a class="publish" onclick="updateJob('+rec.id+',\'1\');" href="javascript:void(0)"  style="width:80px" >启用</a>&nbsp;&nbsp;';
							 }
							 if(rec.jobStatus == 1){
								 b += '<a class="publish" onclick="updateJob('+rec.id+',\'0\');" href="javascript:void(0)"  style="width:80px" >停用</a>&nbsp;&nbsp;';
							 }
							 b += '<a class="publish" onclick="updateJob('+rec.id+',\'3\');" href="javascript:void(0)"  style="width:80px" >立即执行一次</a>&nbsp;&nbsp;';
							 b += '<a class="publish" onclick="updateJob('+rec.id+',\'2\');" href="javascript:void(0)"  style="width:80px" >删除</a>';
							 return b;
						   }
					}


				]]
	});
};

function updateJob(id,jobStatus){
    Confirm({content:'是否确认进行该操作?',
		onOk:function(){
            var params = {'id':id,'jobStatus':jobStatus};
            $.post(base +'/job/updateScheduleJob.action',params,function(result){
                window.location.href=base + "/job/updateScheduleJob"
            },'json');
        }
    });
}