<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>chat backend</title>
    <link rel="shortcut icon" href="favicon.ico" />

    <link rel="stylesheet" type="text/css" href="${base}/statics/skins/default/style.css" />
</head>

<frameset rows="40,*"  frameborder="no" border="0" framespacing="0">
    <frame src="${base}/backend/headPage" noresize="noresize" frameborder="no" name="top" scrolling="no" marginwidth="0" marginheight="0" target="main" />
    <frameset cols="200,*" id="mainframe" frameborder="no" border="0" framespacing="0">
        <frame src="${base}/backend/menuPage" name="menu" id="menu" marginwidth="0" marginheight="0" frameborder="0" scrolling="no" target="main" />
        <frame src="" name="main" id="main" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto" target="_self" />
    </frameset>
</frameset>

</html>
