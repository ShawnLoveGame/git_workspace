$(function () {
    //初始化加载 中心数据
    getCenterData();

    $('.res-info-nav li').on({
        click:function (e) {
            $('.res-info-nav li').removeClass('active');
            $(this).addClass('active');
            findGroupDepartmentData($(this).attr('data-id'));
        }
    });
});
function hideTips(){
    $("#tip_div").hide();
}

function getCenterData(){
    $.post(base + '/exam/getUserCenterData' , {} ,function(data){
       console.log(data);
       var resultData = data.data.resultData;
        $("#cu_order").html(resultData.cuNum);
        $("#total_num").html(resultData.totalUsers);
        findGroupDepartmentData(1);
    });
}

function findGroupDepartmentData(groupId){

    $.post(base + '/exam/findGroupDepartmentData' , {'groupId':groupId}, function(data){
        var res = data.data.resultData;
        var ops = '';
        $.each(res  , function(i , v){
            ops +=' <ul class="table-list-line clearfix">';
            if(i < 3 && v.totalScore > 0){
                if(i == 0 ){
                    ops +='<li><img src="'+base+'/statics/wap/images/badge_1.png" width="30px" style="vertical-align: middle;"></li>';
                }else if(i == 1){
                    ops +='<li><img src="'+base+'/statics/wap/images/badge_2.png" width="30px" style="vertical-align: middle;"></li>';
                }else if(i == 2){
                    ops +='<li><img src="'+base+'/statics/wap/images/badge_3.png" width="30px" style="vertical-align: middle;"></li>';
                }
            }else{
                ops +='    <li>'+(i+1)+'</li>';
            }
            ops +='    <li>'+v.departmentName+'</li>';
            ops +='    <li style="font-size: 20px;color:#e60000;">'+v.totalScore+'</li>';
            ops +='    </ul>';
        });
        $("#depart_div").html(ops);
    });
}


