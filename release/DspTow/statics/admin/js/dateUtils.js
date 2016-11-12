//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) {
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

function jsonDateTimeFormat(jsonDate) {//json日期格式转换为正常格式
    try {
        var date = new Date(jsonDate);
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        return date.getFullYear() + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    } catch (ex) {
        return "";
    }
}

function jsonDateFormat(jsonDate) {//json日期格式转换为正常格式
    try {
        var date = new Date(jsonDate);
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        return date.getFullYear() + "-" + month + "-" + day;//+ " " + hours + ":" + minutes + ":" + seconds + "." + milliseconds;
    } catch (ex) {
        return "";
    }
}

function jsonTimeFormat(jsonDate) {//json时间格式转换为正常格式
    try {
        var date = new Date(jsonDate);
        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        //var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        //var milliseconds = date.getMilliseconds();
        return hours + ":" + minutes;//+ ":" + seconds ;//+ "." + milliseconds;
    } catch (ex) {
        return "";
    }
}

//日期字符串转换数字类型
function dateString2Long(dateS, type) {
    if (type == 1) {//年月日  2015-04-07
        dateS = dateS + " 00:00:00";
    } else if (type == 2) { //时分
        dateS = "2010-01-01 " + dateS + ":00";
    }
    dateS = dateS.replace(/-/g, "/");
    var date = new Date(dateS);
    return date.getTime();
}


function   dateAdd(interval,number,date)
{
/*
  *   功能:实现VBScript的DateAdd功能.
  *   参数:interval,字符串表达式，表示要添加的时间间隔.
  *   参数:number,数值表达式，表示要添加的时间间隔的个数.
  *   参数:date,时间对象.
  *   返回:新的时间对象.
  *   var   now   =   new   Date();
  *   var   newDate   =   DateAdd( "d ",5,now);
  *---------------   DateAdd(interval,number,date)   -----------------
  */
        switch(interval)
        {
                case   "y "   :   {
                        date.setFullYear(date.getFullYear()+number);
                        return   date;
                        break;
                }
                case   "q "   :   {
                        date.setMonth(date.getMonth()+number*3);
                        return   date;
                        break;
                }
                case   "m "   :   {
                        date.setMonth(date.getMonth()+number);
                        return   date;
                        break;
                }
                case   "w "   :   {
                        date.setDate(date.getDate()+number*7);
                        return   date;
                        break;
                }
                case   "d "   :   {
                        date.setDate(date.getDate()+number);
                        return   date;
                        break;
                }
                case   "h "   :   {
                        date.setHours(date.getHours()+number);
                        return   date;
                        break;
                }
                case   "m "   :   {
                        date.setMinutes(date.getMinutes()+number);
                        return   date;
                        break;
                }
                case   "s "   :   {
                        date.setSeconds(date.getSeconds()+number);
                        return   date;
                        break;
                }
                default   :   {
                        date.setDate(d.getDate()+number);
                        return   date;
                        break;
                }
        }
}

/**DataGrid 时间格式化**/
function formatDatebox(value) {
    if (value == null || value == '') {
        return '';
    }
    var dt;
    if (value instanceof Date) {
        dt = value;
    } else {
        dt = new Date(value);
    }

    return dt.Format("yyyy-MM-dd"); //扩展的Date的format方法(上述插件实现)
}

function formatDatetimebox(value) {
    if (value == null || value == '') {
        return '';
    }
    var dt;
    if (value instanceof Date) {
        dt = value;
    } else {
        dt = new Date(value);
    }

    return dt.Format("yyyy-MM-dd hh:mm:ss"); //扩展的Date的format方法(上述插件实现)
}


/** 
 * 计算两日期时间差 
 * @param   interval 计算类型：D是按照天、H是按照小时、M是按照分钟、S是按照秒、T是按照毫秒 
 * @param  date1 起始日期  格式为年月格式 为2012-06-20 
 * @param  date2 结束日期 
 * @return  
 */  
function countTimeLength(interval, date1, date2) {  
    var objInterval = {'D' : 1000 * 60 * 60 * 24, 'H' : 1000 * 60 * 60, 'M' : 1000 * 60, 'S' : 1000, 'T' : 1};  
    interval = interval.toUpperCase();  
    var dt1 = Date.parse(date1.replace(/-/g, "/"));  
    var dt2 = Date.parse(date2.replace(/-/g, "/"));  
    try{  
        return ((dt2 - dt1) / objInterval[interval]).toFixed(2);//保留两位小数点  
    }catch (e){  
        return e.message;  
    }  
}