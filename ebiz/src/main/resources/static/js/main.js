// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function getHtmlPageToIFrame(actionId, targetId, url) {
    $.ajax({
        type: 'GET',
        url: url,
        data: {},
        async: true,
        success: function (res) {
            console.log("success");
            var frame = $(targetId).contents()[0];
            frame.designMode = "on"; //文档进入可编辑模式
            frame.open(); //打开流
            frame.write(res);
            frame.close(); //关闭流
            frame.designMode = "off"; //文档进入非可编辑模式
        }, error: function (res) {
            console.log("error:" + res);
        }, complete: function (res) {
            console.log("complete:" + res);
        }
    });
}

function getHtmlPageToDiv(actionId, targetId, url) {
    $(actionId).click(function () {
        $.ajax({
            type: 'GET',
            url: url,
            data: {},
            async: true,
            success: function (res) {
                console.log(res);
                $(targetId).html(res);
            }, error: function (res) {
                console.log(res)
            }, complete: function (res) {
            }
        });
    });
}

// 获取参数
function getQueryParam(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var decodeUrl = decodeURI(window.location.search);
    var r = decodeUrl.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

//当前所在第几页
var pageIndex = 1;
//每页的记录数
var pageSize = 5;

function initSplitPage() {
    //每页显示的数量发生变化
    $("#pageSize").change(function () {
        pageSize = $("#pageSize").val();
        pageIndex = 1;
        initPage();
    });
    //下一页
    $("#nextPage").click(function () {
        pageIndex = parseInt(pageIndex) + 1;
        var total = parseInt($("#totalPages").text());
        if (parseInt(pageIndex) > total) {
            pageIndex = total;
            return;
        }
        initPage();
    });
    //上一页
    $("#prePage").click(function () {
        if (pageIndex <= 1) {
            return;
        }
        pageIndex = parseInt(pageIndex) - 1;
        initPage();
    });
    //首页
    $("#firstPage").click(function () {
        pageIndex = 1;
        initPage();
    });
    //末页
    $("#lastPage").click(function () {
        pageIndex = $("#totalPages").text();
        initPage();
    });
    //刷新
    $("#refresh").click(function () {
        initPage();
    });
    //跳转
    $("#pageIndex").blur(function () {
        pageIndex = $("#pageIndex").val();
        var total = parseInt($("#totalPages").text());
        if (pageIndex > total) {
            pageIndex = total;
        }
        initPage();
    });
}

function swalSuccess(message) {
    swal({
        title: "提示",
        text: message,
        icon: "success",
        button: "确定"
    })
}

function swalError(message) {
    swal({
        title: "提示",
        text: message,
        icon: "error",
        button: "确定"
    })
}

function swalSysError(res) {
    if (!res) {
        res = "";
    }
    swal({
        title: "提示",
        text: "系统错误，请联系管理员！" + res,
        icon: "error",
        button: "确定"
    });
}

//优化页面显示
function moneyFormatter(data) {
    if (!data) {
        return '';
    }
    return "$" + data;
}

//避免页面上出现 null
function emptyFormatter(data) {
    if (!data || data === 'null') {
        return ""
    }
    return data;
}

function getParameters() {
    //返回当前 URL 的查询部分（问号 ? 之后的部分）。
    var urlParameters = location.search;
    //声明并初始化接收请求参数的对象
    var requestParameters = new Object();
    //如果该求青中有请求的参数，则获取请求的参数，否则打印提示此请求没有请求的参数
    if (urlParameters.indexOf('?') != -1) {
        //获取请求参数的字符串
        var parameters = decodeURI(urlParameters.substr(1));
        //将请求的参数以&分割中字符串数组
        parameterArray = parameters.split('&');
        //循环遍历，将请求的参数封装到请求参数的对象之中
        for (var i = 0; i < parameterArray.length; i++) {
            requestParameters[parameterArray[i].split('=')[0]] = (parameterArray[i].split('=')[1]);
        }
        console.info('theRequest is =====', requestParameters);
    } else {
        console.info('There is no request parameters');
    }
    return requestParameters;
}