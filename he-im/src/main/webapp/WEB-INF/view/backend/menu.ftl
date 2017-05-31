<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>menu</title>
    <link rel="stylesheet" type="text/css" href="${base}/statics/skins/default/style.css"/>

    <script src="${base}/statics/js/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        function tm_toggle_menu(obj) {
            $(obj).parent().siblings().toggle();
        }

        $(document).ready(function () {
            $(".tm_menu_item ul li a").click(function () {
                $(".tm_menu_item ul li").removeClass("itemon");
                $(this).parent().addClass("itemon");
            });

        });

        document.oncontextmenu = function () {
            return false;
        }

    </script>

    <style>
        .tmc_menu_qdb {
            background: url('${base}/statics/skins/images/point_green.png') no-repeat 8px 8px;
        }
        .tmc_menu_user {
            background: url('${base}/statics/skins/images/user.png') no-repeat 8px 8px;
        }
    </style>
</head>
<body oncontextmenu="return false">

<div class="tm_menu">

    <#--<div class="tm_menu_item clearfix">-->
        <#--<h2><a href="javascript:void(0);" onclick="tm_toggle_menu(this)" class="tmc_menu_qdb">Home</a></h2>-->
        <#--<ul>-->
            <#--<li><a href="${base}/backend/welcomePage" target="main">welcome</a></li>-->
        <#--</ul>-->
    <#--</div>-->

    <div class="tm_menu_item clearfix">
        <h2><a href="javascript:void(0);" onclick="tm_toggle_menu(this)" class="tmc_menu_user">Conversation</a></h2>
        <ul>
            <li><a href="${base}/im/message/toMessagePage2" target="main">current</a></li>
            <li><a href="${base}/backend/changePwd" target="main">Settings</a></li>
        </ul>
    </div>


</div>


</body>
</html>


