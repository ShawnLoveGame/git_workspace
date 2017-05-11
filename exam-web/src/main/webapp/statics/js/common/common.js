var backendCommon = {
    refreshTableData:function(id, url ,params){
        $("#"+id).bootstrapTable('refresh' , {
            url:url ,
            query:params
        })
    },
	dateTimeToLocalString : function(dateTime) {
		if (dateTime == null) {
			return "";
		}
		var date = new Date(dateTime);
		Y = date.getFullYear();
		M = (date.getMonth() + 1) < 10 ? ("0" + (date.getMonth() + 1)) : (date
				.getMonth() + 1);
		D = date.getDate() < 10 ? ("0" + date.getDate()) : date.getDate();
		var hh = date.getHours() < 10 ? ("0" + date.getHours()) : date
				.getHours();
		var mm = date.getMinutes() < 10 ? ("0" + date.getMinutes()) : date
				.getMinutes();
		return Y + '-' + M + '-' + D + ' ' + hh + ':' + mm;
	},
	dateToLocalString : function(dateTime) {
		if (dateTime == null) {
			return "";
		}
		var date = new Date(dateTime);
		Y = date.getFullYear();
		M = (date.getMonth() + 1) < 10 ? ("0" + (date.getMonth() + 1)) : (date
			.getMonth() + 1);
		D = date.getDate() < 10 ? ("0" + date.getDate()) : date.getDate();

		return Y + '-' + M + '-' + D;
	},
	dateToLocalStringdatebox : function(dateTime) {
		if (dateTime == null) {
			return "";
		}
		var date = new Date(dateTime);
		Y = date.getFullYear();
		M = (date.getMonth() + 1) < 10 ? ("0" + (date.getMonth() + 1)) : (date
			.getMonth() + 1);
		D = date.getDate() < 10 ? ("0" + date.getDate()) : date.getDate();

		return  Y+ '/' + M + '/' + D;
	},
	beforDateToLocalString : function(dateTime , n) {
		if (dateTime == null) {
			return "";
		}
		var date = new Date(dateTime);
		Y = date.getFullYear();
		var cur_m = date.getMonth() + 1;
		var cur_d = date.getDate();
		var M ,D ;
		if(cur_m == 1 || cur_m == 3  || cur_m == 5  || cur_m == 7  || cur_m == 8  || cur_m == 10  || cur_m == 12){//判断当前月份是否是是 31 天的
			if((cur_d - n) >=0){
				D = cur_d - n;
			}else{
				M = cur_m - 1;
				D = 31 - (n- cur_d);
			}
		}else if(cur_m == 4 || cur_m == 6  || cur_m == 9  || cur_m == 11){
			if((cur_d - n) >=0){
				D = cur_d - n;
			}else{
				M = cur_m - 1;
				D = 30 - (n- cur_d);
			}
		}else{//2月份
			if((cur_d - n) >=0){
				D = cur_d - n;
			}else{
				M = cur_m - 1;
				D = 29 - (n- cur_d);
			}
		}
		M = M < 10 ? ("0" + M) : M;
		D = D< 10 ? ("0" + D) : D;
		return Y + '-' + M + '-' + D;
	},
	dateTimeToLocalStringWithSeconds : function(dateTime) {
		if (dateTime == null) {
			return "";
		}
		var date = new Date(dateTime);
		Y = date.getFullYear();
		M = (date.getMonth() + 1) < 10 ? ("0" + (date.getMonth() + 1)) : (date
				.getMonth() + 1);
		D = date.getDate() < 10 ? ("0" + date.getDate()) : date.getDate();
		var hh = date.getHours() < 10 ? ("0" + date.getHours()) : date
				.getHours();
		var mm = date.getMinutes() < 10 ? ("0" + date.getMinutes()) : date
				.getMinutes();
		var ss = date.getSeconds() < 10 ? ("0" + date.getSeconds()) : date
				.getSeconds();
		return Y + '-' + M + '-' + D + ' ' + hh + ':' + mm + ':' + ss;
	},



    dateTimeToLocalStringWithtimeStamp : function(dateTime) {
        if (dateTime == null) {
            return "";
        }
        var date = new Date(dateTime);
        Y = date.getFullYear();
        M = (date.getMonth() + 1) < 10 ? ("0" + (date.getMonth() + 1)) : (date
            .getMonth() + 1);
        D = date.getDate() < 10 ? ("0" + date.getDate()) : date.getDate();
        var hh = date.getHours() < 10 ? ("0" + date.getHours()) : date
            .getHours();
        var mm = date.getMinutes() < 10 ? ("0" + date.getMinutes()) : date
            .getMinutes();
        var ss = date.getSeconds() < 10 ? ("0" + date.getSeconds()) : date
            .getSeconds();
        return Y + M  + D  + hh + mm+ ss;
    },


	// 重置表单内容 （不适应请自己扩展）
	formReset : function(formId) {
		$('input,select,textarea', '#' + formId)
				.not(':button,:submit,:reset')
				.each(
						function() {
							var t = this.type, tag = this.tagName.toLowerCase();
							if (t == 'text' || t == 'password'
									|| tag == 'textarea') {
								if ($(this).attr('default')) {
									this.value = $(this).attr('default');
								} else {
									this.value = '';
								}
							} else if (t == 'file') {
								var file = $(this);
								file.after(file.clone().val(''));
								file.remove();
							} else if (t == 'checkbox' || t == 'radio') {
								if ($(this).attr('default')) {
									this.checked = true;
								} else {
									this.checked = false;
								}
							} else if (tag == 'select') {
								var defaultOpt = $(this).find(
										'option[default="true"]');
								if (defaultOpt.length > 0) {
									$(this).val(defaultOpt.val());
								} else {
									this.selectedIndex = 0;
								}
							}
						});
	},
	str2DateTime : function(str) {
		str = str.replace("-", "/").replace("-", "/");
		return Date.parse(str);
	},
	// 计算日期时间差多少天
	dateDiff : function(strDate1, strDate2) {
		strDate1 = strDate1.replace("-", "/").replace("-", "/");
		strDate2 = strDate2.replace("-", "/").replace("-", "/");
		var date1 = Date.parse(strDate1);
		var date2 = Date.parse(strDate2);
		return (date2 - date1) / (60 * 60 * 1000 * 24);
	},
	// 正则校验是否是数字
	regIsNum : function(str) {
		var reg = new RegExp("^[0-9]*$");
		if (!reg.test(str)) {
			return false;
		} else {
			return true;
		}
	},
	regIsZsNum :function(str){
		var reg = new RegExp("^-?[1-9]\d*$");
		if (!reg.test(str)) {
			return false;
		} else {
			return true;
		}
	},
	// 正则校验是否是中文/[\u4E00-\u9FA5]/
	regIsChinese : function(str) {
		var reg = new RegExp("^[\u4E00-\u9FA5]+$");
		if (!reg.test(str)) {
			return false;
		} else {
			return true;
		}
	},
	/**
	 * 判断是否是字母
	 * @param str
     */
	regIsABC : function(str){
		var reg = new RegExp("^[a-zA-Z]+$");
		if (!reg.test(str)) {
			return false;
		} else {
			return true;
		}
	},
	// 正则校验是否是汉字 字母 数字 组合 ()-.+/*_
	regIsText : function(str) {
		var reg = new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\.\+\/\*\\s\-_\(\)]+$");
		if (!reg.test(str)) {
			return false;
		} else {
			return true;
		}
	},
	// 正则校验是否是汉字 字母 数字 标点 组合
	regIsRichText : function(str) {
		var reg = new RegExp(
				"^[a-zA-Z0-9_\u4e00-\u9fa5]|[，。？：；‘’！“”—……、]|(－{2})|(（）)|(【】)|({})|(《》)+$");
		if (!reg.test(str)) {
			return false;
		} else {
			return true;
		}
	},
	regIsEmail:function(str){
		var reg = new RegExp("^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$");
		if (!reg.test(str)) {
			return false;
		} else {
			return true;
		}
	},
	// 去掉html标签
	removeHTMLTag : function(str) {
		if (str == null && str == "") {
			return "";
		}
		str = str.replace(/<\/?[^>]*>/g, ''); // 去除HTML tag
		str = str.replace(/[ | ]*\n/g, '\n'); // 去除行尾空白
		str = str.replace(/\n[\s| | ]*\r/g, '\n'); // 去除多余空行
		str = str.replace(/&nbsp;/ig, '');// 去掉&nbsp;
		str = str.replace(/\\/g, "");
		str = str.replace(/</g, "");
		return str;
	},
	// 是否为手机号
	regIsMobileNumber : function(str) {
		var reg = new RegExp("(13[0|1|2]|15[5|6]|18[5|6])\\d{8}");
		if (!reg.test(str)) {
			return false;
		} else {
			return true;
		}
	},
	regIsMobile : function(str) {
		var reg = new RegExp("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		if (!reg.test(str)) {
			return false;
		} else {
			return true;
		}
	},
	// 是否包含特殊字符
	regIsSpecial : function(str) {
		var reg = new RegExp(
				"[`~!@#$^&*%()-.+*_=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
		if (!reg.test(str)) {
			return false;
		} else {
			return true;
		}
	},
	AutoResizeImage : function(maxWidth, maxHeight, objImg) {
		var img = new Image();
		img.src = objImg.src;
		var hRatio;
		var wRatio;
		var Ratio = 1;
		var w = img.width;
		var h = img.height;
		wRatio = maxWidth / w;
		hRatio = maxHeight / h;
		if (maxWidth == 0 && maxHeight == 0) {
			Ratio = 1;
		} else if (maxWidth == 0) {//
			if (hRatio < 1)
				Ratio = hRatio;
		} else if (maxHeight == 0) {
			if (wRatio < 1)
				Ratio = wRatio;
		} else if (wRatio < 1 || hRatio < 1) {
			Ratio = (wRatio <= hRatio ? wRatio : hRatio);
		}
		if (Ratio < 1) {
			w = w * Ratio;
			h = h * Ratio;
		}
		objImg.height = h;
		objImg.width = w;
	},
    // 快速排序
    quickSort: function(array) {
        var i = 0;
        var j = array.length - 1;
        var Sort = function(i, j) {
            // 结束条件
            if (i == j) {
                return
            };

            var key = array[i];
            var stepi = i; // 记录开始位置
            var stepj = j; // 记录结束位置
            while (j > i) {
                // j <<-------------- 向前查找
                if (array[j] >= key) {
                    j--;
                } else {
                    array[i] = array[j]
                    //i++ ------------>>向后查找
                    while (j > ++i) {
                        if (array[i] > key) {
                            array[j] = array[i];
                            break;
                        }
                    }
                }
            }

            // 如果第一个取出的 key 是最小的数
            if (stepi == i) {
                Sort(++i, stepj);
                return;
            }

            // 最后一个空位留给 key
            array[i] = key;

            // 递归
            Sort(stepi, i);
            Sort(j, stepj);
        }

        Sort(i, j);

        return array;
    },
    array_remove_repeat:function(a) { // 去重
        var r = [];
        for(var i = 0; i < a.length; i ++) {
            var flag = true;
            var temp = a[i];
            for(var j = 0; j < r.length; j ++) {
                if(temp === r[j]) {
                    flag = false;
                    break;
                }
            }
            if(flag) {
                r.push(temp);
            }
        }
        return r;
    },

	common_alert:function(){

	},
	common_confirm:function(){

	}
};
// 除法函数，用来得到精确的除法结果
// 说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
// 调用：accDiv(arg1,arg2)
// 返回值：arg1除以arg2的精确结果
function accDiv(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length
	} catch (e) {
	}
	try {
		t2 = arg2.toString().split(".")[1].length
	} catch (e) {
	}
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""))
		r2 = Number(arg2.toString().replace(".", ""))
		return (r1 / r2) * pow(10, t2 - t1);
	}
}
// 给Number类型增加一个div方法，调用起来更加方便。
Number.prototype.div = function(arg) {
	return accDiv(this, arg);
}

// 乘法函数，用来得到精确的乘法结果
// 说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
// 调用：accMul(arg1,arg2)
// 返回值：arg1乘以arg2的精确结果

function accMul(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length
	} catch (e) {
	}
	try {
		m += s2.split(".")[1].length
	} catch (e) {
	}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", ""))
			/ Math.pow(10, m)
}
// 给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.mul = function(arg) {
	return accMul(arg, this);
}

// 加法函数，用来得到精确的加法结果
// 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
// 调用：accAdd(arg1,arg2)
// 返回值：arg1加上arg2的精确结果
function accAdd(arg1, arg2) {
	var r1, r2, m;
	try {
		r1 = arg1.toString().split(".")[1].length
	} catch (e) {
		r1 = 0
	}
	try {
		r2 = arg2.toString().split(".")[1].length
	} catch (e) {
		r2 = 0
	}
	m = Math.pow(10, Math.max(r1, r2))
	return (arg1 * m + arg2 * m) / m
}
// 给Number类型增加一个add方法，调用起来更加方便。
Number.prototype.add = function(arg) {
	return accAdd(arg, this);
}

// 减法函数
function accSub(arg1, arg2) {
	var r1, r2, m, n;
	try {
		r1 = arg1.toString().split(".")[1].length
	} catch (e) {
		r1 = 0
	}
	try {
		r2 = arg2.toString().split(".")[1].length
	} catch (e) {
		r2 = 0
	}
	m = Math.pow(10, Math.max(r1, r2));
	// last modify by deeka
	// 动态控制精度长度
	n = (r1 >= r2) ? r1 : r2;
	return ((arg1 * m - arg2 * m) / m).toFixed(n);
}
// 给Number类型增加一个sub方法，调用起来更加方便。
Number.prototype.sub = function(arg) {
	return accSub(this, arg);
}

/* js中却没有这3个内置方法，需要手工编写扩展 */
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.ltrim = function() {
	return this.replace(/(^\s*)/g, "");
}
String.prototype.rtrim = function() {
	return this.replace(/(\s*$)/g, "");
}

function winClose() {
	window.close();
}

function getMap() {//初始化map_,给map_对象增加方法，使map_像Map
    var map_ = new Object();
    map_.put = function(key, value) {
        map_[key+'_'] = value;
    };
    map_.get = function(key) {
        return map_[key+'_'];
    };
    map_.remove = function(key) {
        delete map_[key+'_'];
    };
    map_.keyset = function() {
        var ret = "";
        for(var p in map_) {
            if(typeof p == 'string' && p.substring(p.length-1) == "_") {
                ret += ",";
                ret += p.substring(0,p.length-1);
            }
        }
        if(ret == "") {
            return ret.split(",");
        } else {
            return ret.substring(1).split(",");
        }
    };
    return map_;
}


