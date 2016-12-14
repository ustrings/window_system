/**
 * Jquery将表单数据转换为对象
 * @returns {{}}
 */
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        var tagName = $(this)[0].tagName;
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

/**
 * bootstrap-table 查询条件
 * @param formId
 * @param params
 * @returns {{}|jQuery}
 */
function getQueryParam(formId, params) {
    var queryParams = {};
    if(formId) {
        queryParams = $("#" + formId).serializeObject();
    }
    queryParams.page = params.offset / params.limit + 1;
    queryParams.rows = params.limit;
    return queryParams;
}

/**
 * select ajax初始化
 * @param params
 *   url: "",
     valueField:'',
     textField:'',
     loadFilter: function()
 */
$.fn.select2Load = function(params){

    var element = $(this);
    $.post(params.url , params.queryParams, function (result) {
        if(params.loadFilter) {
            result = params.loadFilter(result);
        }

        var selectArray = result.map(function (item, index, array) {
            return {id:item[params.valueField], text:item[params.textField]};
        });
        element.select2({
            data: selectArray,
            width: '150px'
        });
    });
}

/**
 * 表单加载JSON数据
 * @param data
 */
$.fn.formLoadData = function (data) {

    for(key in data) {
        var value = data[key];

        $(this).find("[name='"+key+"'],[name='"+key+"[]']").each(function(){
            var tagName = $(this)[0].tagName;
            var type = $(this).attr('type');
            if(tagName=='INPUT'){
                if(type=='radio'){
                    $(this).attr('checked',$(this).val()==value);
                }else if(type=='checkbox'){
                    var arr = value.split(',');
                    for(var i =0;i<arr.length;i++){
                        if($(this).val()==arr[i]){
                            $(this).attr('checked',true);
                            break;
                        }
                    }
                }else{
                    $(this).val(value);
                }
            }else if(tagName=='SELECT' || tagName=='TEXTAREA'){
                $(this).val(value);
            }
        });

        //手工触发select/input change事件
        $(this).find("input").trigger("change");
        $(this).find("select").trigger("change");
    }
}

/**
 * 重置表单
 */
$.fn.formReset = function () {
    $(this)[0].reset();

    $(this).find("input[type=hidden]").val("");

    //手工触发select/input change事件
    $(this).find("input").trigger("change");
    $(this).find("select").trigger("change");
}

/**
 * 所有modal打开时， enable提交按钮
 */
$(function(){
    $(".modal").on( "show.bs.modal", function() {
        $(this).find("button.btn").each(function (index) {
            if($(this).data("loading-text")) {
                $(this).button('reset');
            }
        });
    });
});

/**
 * BOX加载中
 */
$.fn.ajaxLoading = function (state) {
    if($(this).hasClass("box")) {

        //打开加载框
        if(state == "show") {
            if($(this).find(".overlay").length == 0) {
                var loadingHtml = [];
                loadingHtml.push('<div class="overlay">');
                loadingHtml.push('<i class="fa fa-refresh fa-spin"></i>');
                loadingHtml.push('</div>');
                $(this).append(loadingHtml.join(""));
            }
        } else if(state == "close") {
            if($(this).find(".overlay").length > 0) {
                $(this).find(".overlay").remove();
            }
        }

    }
}

//AJAX全局错误提示
$( document ).ajaxError(function( event, jqxhr, settings, thrownError ) {

    //重置modal提交按钮
    $(".modal").find("button.btn").each(function (index) {
        if($(this).data("loading-text")) {
            $(this).button('reset');
        }
    });

    var result = jqxhr.responseJSON;
    if(result == undefined) {
        result = $.parseJSON(jqxhr.responseText);
    }
    if(result && result.message) {
        bootbox.alert({
            size: "small",
            message: result.message,
        });
    } else {
        bootbox.alert({
            size: "small",
            message: "系统错误!",
        });
    }

});

if(bootbox) {
    bootbox.setDefaults({
        locale: "zh_CN"
    });
}

/**
 * 下载文件
 * @param url
 * @param params
 */
function downloadFile(url, params) {
    var iframe = $("<iframe>");
    iframe.attr('style','display:none');
    var paramString = "";
    for (var key in params) {
        paramString = paramString + key + "=" + params[key] + "&";
    }

    iframe.attr("src", url + "?" + paramString);
    $('body').append(iframe);
}