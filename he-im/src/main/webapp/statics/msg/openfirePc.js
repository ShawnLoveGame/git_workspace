var fromName='daizy-33';
var toName='daizy-12';
var imDomain='http://47.90.77.140:8100';
var chats = new webChats(jQuery,Swiper,{
    avatar: {
        seller: '',        // 商家头像
        customer: 'http://www.baidu.com/favicon.ico',      // 客户头像
    },
	formAction:imDomain+'/im/upload/uploadWinPic',
    title: 'What is your name?',           // 标题
	goods: {
		url: 'http://www.baidu.com',  // 商品链接
		img: 'http://www.baidu.com/favicon.ico',  //商品图片
		title: '商品标题',
		price: '$320'   // 商品价格
	},
	btnChat: function () {
		/* 弹出聊天回调 */
		$.ajax({  
	        type : "post",  
	        async:false,  
	        url : imDomain+'/api/openIm',  
	        data:{userName:fromName},
	        dataType : "jsonp",  
	        jsonp: "jsonpCallback",
	        success : function(data){  
	        	setInterval("getMsg()",3000);
	        },  
	        error:function(){  
	            
	        }  
	    });   
	},
	sendMessage: sendMsg,
	fileBtn: function () {
		/* 图片按钮回调 */
		
	},
	hideWindowBtn: function (imgsrc) {
		/* 隐藏聊天回调 */
		
	}
});


function sendMsg(content,type){
	/* 发送消息回调 发送图片也会回调 */	
	var param = {content:content,
			fromName:fromName,
			toName:toName,
			type:type
			};
	 $.ajax({  
        type : "post",  
        async:false,  
        url : imDomain+'/api/toSendMsg',  
        data:param,
        dataType : "jsonp",  
        jsonp: "jsonpCallback",
        success : function(data){  
           console.log(data); 
        }
    });   
}

function getMsg(){
	$.ajax({  
        type : "post",  
        async:false,  
        url : imDomain+'/api/getMsg',  
        data:{fromName:fromName,toName:toName},
        dataType : "jsonp",  
        jsonp: "jsonpCallback",
        success : function(result){  
        	if(result.success){
    			if(result.data.msgList != null && result.data.msgList.length>0){
                    $.each(result.data.msgList,function(n,th) {
                        //decode emoji
                        if(th.type == 0){
                            var c_t = decodeEmoji(th.text);
                            chats.getMsg(c_t,{type: 'content'});
                        }
                        // img
                        if(th.type == 1){ 
                        	chats.getMsg(th.text,{type: 'img'});
                        }
                    });
                }
    		}
        } 
    });   
}

//获取所有的表情字典
function decodeEmoji(content){
    var t_map = {};
    var codes  = [];
    $(".qqface-wrap").find('i').each(function(){
       var code = $(this).attr('data-code');
        t_map[code] = $(this).html();
        codes.push(code);
    });
    for(var  i = 0 ; i< codes.length ; i++){
        var c  = codes[i];
        var t = '['+c+']';
        if(content.indexOf(t) > -1 ){
            content = content.replace(RegExp('\\['+c+'\\]','g') , t_map[c]);
        };
    };
    return content;
}