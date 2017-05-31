var regBox = {
    uploadExcelFile: /^.*?\.(jpg|jpeg|png|gif|bmp)$/,
};
$(function () {
    upload();
});

var upload = function () {
    $("#c_div").on({
        change: function () {
            if (!($(this).val() != ""
                && regBox.uploadExcelFile.test($(this).val().toLowerCase()))) {
                $(this).val('');
                return;
            } else {
                $('#addArticleForm').attr('action', base + '/im/upload/uploadWinPic');
                $('#addArticleForm').attr('target', 'file_upload_return');
                $('#addArticleForm').submit();
            }
        }
    }, '#chat_files')

    $("#file_upload_return").load(function (loadData) {
        $('#file_upload_return').hide();
        var returnObj = $('body', window.frames['file_upload_return'].document).find('#uploadResultId');
        var data = returnObj.val();
        if (data != undefined && data != '') {
            if (parseInt(data) == 1) {
                var url = returnObj.attr('data-url');
                sendPicMessage(url, 1);
            } else {
                return false;
            }
        }
    });

}

function sendPicMessage(content, type) {
    if (current_from != '') {
        $.post(base + '/im/message/toSendMsg', {'content': content, 'toAcount': current_from, 'type': type}, function (data) {
            if (data.success) {
                var str = '<div class="chat-message-item customer">' +
                    '<div class="message-avatar">' +
                    '<img src= "'+base+'/statics/images/default.png" alt="seller avatar">' +
                    '</div>' +
                    '<div class="message-content">';
                if (type == 0) {
                    str += '<div class="chat-bubble">' + content + '</div>';
                }
                if (type == 1) {
                    str += '<div class="chat-bubble"><img src="' + content + '" style="width:100px;height:100px;"></div>';
                }
                str += '<div class="timestamp">' + getTime() + '</div></div></div>';
                $('.swiper-slide').append(str);
                reChangeMessageBox();
            }
        });
    }
}

function decodeEmoji(content) {
    //获取所有的表情字典
    var t_map = {};
    var codes = [];
    $(".qqface-wrap").find('i').each(function () {
        var code = $(this).attr('data-code');
        t_map[code] = $(this).html();
        codes.push(code);
    });
    for (var i = 0; i < codes.length; i++) {
        var c = codes[i];
        var t = '[' + c + ']';
        if (content.indexOf(t) > -1) {
            content = content.replace(RegExp('\\[' + c + '\\]', 'g'), t_map[c]);
        }
    }
    return content;
}
function getHistoryMsg() {

    var fn = $('.buddy-item.active').attr('data-from');
    $("#chide_"+fn).hide();
    $("#customer_name").val(fn.split('+40').join('@').split('-40').join(' '));
    var fff = $('.buddy-item.active').attr('data-from').split('@').join('+40').split(' ').join('-40');
    $.post(base + '/im/message/findHistoryMsgDetail', {'from': fff}, function (data) {
        if (data.success) {
            var str = '';
            var username = data.data.username;
            $.each(data.data.detailList, function (n, th) {
                if(th.from == username){
                    str +='<div class="chat-message-item customer">';
                }else{
                    str += '<div class="chat-message-item seller">';
                }
                str += '<div class="message-avatar">';
                str += '<img src=' + seller + ' alt="seller avatar">';
                str += '</div>';
                str += '<div class="message-content">';
                if (th.type == 0) {
                    var c_t = decodeEmoji(th.text);
                    str += '<div class="chat-bubble">' + c_t + '</div>';
                }
                if (th.type == 1) {
                    str += '<div class="chat-bubble"><img src="' + th.text + '" style="width:100px;height:100px;"></div>';
                }else if(th.type == 2){
                    str +='<div class="chat-bubble">' +
                        '<table><tr><td rowspan="4">' +
                        '<img src="'+th.text.goodsImg+'" style="width:100px;height:100px;"></td>' +
                        '<td style="">' + th.text.goodsName +'</td></tr>' +
                        '<tr><td>' + th.text.goodsPrice+'</td>'+
                        '</tr></table>';
                 }
                str += '<div class="timestamp">' + th.time + '</div>';
                str += '</div>';
                str += '</div>';
            });
        }
        if (str != '') {
            $('.swiper-slide').html(str);
            reChangeMessageBox();
        }
        // setInterval("getMsg()", 3000);
    })
}

function getTime() {
    var date = new Date();
    var Y = date.getFullYear();
    var M = (date.getMonth() + 1) < 10 ? ("0" + (date.getMonth() + 1)) : (date
        .getMonth() + 1);
    var D = date.getDate() < 10 ? ("0" + date.getDate()) : date.getDate();
    var hh = date.getHours() < 10 ? ("0" + date.getHours()) : date
        .getHours();
    var mm = date.getMinutes() < 10 ? ("0" + date.getMinutes()) : date
        .getMinutes();
    var ss = date.getSeconds() < 10 ? ("0" + date.getSeconds()) : date
        .getSeconds();
    return Y + '-' + M + '-' + D + ' ' + hh + ':' + mm + ':' + ss;
}