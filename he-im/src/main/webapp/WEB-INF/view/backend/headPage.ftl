<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>head</title>
    <link rel="stylesheet" type="text/css" href="${base}/statics/skins/default/style.css"/>
    <script src="${base}/statics/js/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        var tm_var_menustatus = 0;
        function tm_switch_menu() {
            if (tm_var_menustatus == 0) {
                tm_var_menustatus = 1;
                $('#mainframe', window.parent.document).attr("cols", "0,*");
            } else {
                tm_var_menustatus = 0;
                $('#mainframe', window.parent.document).attr("cols", "200,*");
            }

        }

        var tm = {
            logout: function () {
                top.location.href = "${base}/login/logout";
            }
        };

        var tm_activemenu = function (obj) {
            $(obj).parent().parent().children("li").attr("class", "");
            $(obj).parent().attr("class", "active");
        }

    </script>
</head>
<body>

<div class="tm_head">

    <div class="tm_head_logo" style="    color: #FFF;    font-size: 18px;    margin-top: 10px;">chat</div>
    <div class="tm_head_switch"><a href="javascript:void(0);" onclick="tm_switch_menu()"><img src="${base}/statics/skins/images/ico_lines.png"/></a></div>

    <div class="tm_head_menu">
        <ul>

        </ul>
    </div>

    <div class="tm_head_tools">
        <img src="${base}/statics/skins/images/ico_account.png" align="absmiddle"/>
        <span style="cursor:pointer" title=''>
					${backendOperatorVO.userName!""}
				</span> |
        <a href="javascript:void(0);" onclick="return tm.logout();">Sing out</a>
    </div>
</div>
</body>
</html>


