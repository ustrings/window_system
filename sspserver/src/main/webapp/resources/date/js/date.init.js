/**
 * Created by JetBrains PhpStorm.
 * User: ZTD
 * Date: 14-7-1
 * Time: 上午11:11
 * To change this template use File | Settings | File Templates.
 */

/*
* 初始化日期控件默认显示格式为中文
* 其中 startTime，和 endTime 分别代表的是要作为日期控件的 input 的 id
 */

$(function() {
    // 根据 class 属性进行初始化
    $(".input_datepicker").datepicker({
        changeMonth : false,
        changeYear : false,
        gotoCurrent: true,
        dateFormat : "yy-mm-dd"
    });

    $.datepicker.regional['zh-CN'] = {
        clearText: '清除',
        clearStatus: '清除已选日期',
        closeText: '关闭',
        closeStatus: '不改变当前选择',
        prevText: '<上月',
        prevStatus: '显示上月',
        prevBigText: '<<',
        prevBigStatus: '显示上一年',
        nextText: '下月>',
        nextStatus: '显示下月',
        nextBigText: '>>',
        nextBigStatus: '显示下一年',
        currentText: '今天',
        currentStatus: '显示本月',
        monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
        monthNamesShort: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
        monthStatus: '选择月份',
        yearStatus: '选择年份',
        weekHeader: '周',
        weekStatus: '年内周次',
        dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
        dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
        dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
        dayStatus: '设置 DD 为一周起始',
        dateStatus: '选择 m月 d日, DD',
        dateFormat: 'yy-mm-dd',
        firstDay: 1,
        initStatus: '请选择日期',
        isRTL: false
    };
    $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
    // 为日期控件设置默认的日期值（如果不需要设置日期的值，那么就把此方法注释掉即可）
    setDefaultDate();
});

//设置默认时间值
var defaultDate = getCurdate();
// 处理时间控件的修改事件（强制设置日期控件默认值）
function setDefaultDate(obj) {
    if (obj == null || obj == undefined) {
        $("#startTime").val(defaultDate);
        $("#endTime").val(defaultDate);
    } else if(obj.id == "startTime") {
        $(obj).val(defaultDate);
    } else {
        $(obj).val(defaultDate);
    }
}

function getCurdate()
{
    var now=new Date();
    y=now.getFullYear();
    m=now.getMonth()+1;
    d=now.getDate();
    m=m<10?"0"+m:m;
    d=d<10?"0"+d:d;
    return y+"-"+m+"-"+d;
}

