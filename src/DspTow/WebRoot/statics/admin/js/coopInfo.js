$(function () {

    var data = {
        "total":5,
        "rows":[
            {"coopId":"0000000000","coopName":"公共","coopType":3,"linkMan":"亚信","company":"公共","linkPhone":"13012341234","mobilePhone":"13012341234","remark":"公共","coopTypeDesc":"公共接入商"},
            {"coopId":"1000000001","coopName":"欧飞","coopType":1,"linkMan":"欧飞","company":"欧飞","linkPhone":"025-88888888","mobilePhone":"13911111111","remark":"欧飞","coopTypeDesc":"供应商"},
            {"coopId":"2000000000","coopName":"亚信ABS","coopType":2,"linkMan":"芳芳","company":"亚信","linkPhone":"13012341234","mobilePhone":"13012341234","remark":"联调测试用","coopTypeDesc":"接入商"},
            {"coopId":"2745641000","coopName":"亚信天猫专营店","coopType":2,"linkMan":"严德方","company":"亚信ABS","linkPhone":"18672931130","mobilePhone":"18672931130","remark":"亚信天猫专营店","coopTypeDesc":"接入商"},
            {"coopId":"2745642001","coopName":"亚信在线","coopType":2,"linkMan":"吴亮亮","company":"AsiaInfo","linkPhone":"027-88888888","mobilePhone":"18518314472","remark":"测试数据","coopTypeDesc":"接入商"},
            {"coopId":"2745642001","coopName":"亚信在线","coopType":2,"linkMan":"吴亮亮","company":"AsiaInfo","linkPhone":"027-88888888","mobilePhone":"18518314472","remark":"测试数据","coopTypeDesc":"接入商"},
            {"coopId":"2745642001","coopName":"亚信在线","coopType":2,"linkMan":"吴亮亮","company":"AsiaInfo","linkPhone":"027-88888888","mobilePhone":"18518314472","remark":"测试数据","coopTypeDesc":"接入商"},
            {"coopId":"2745642001","coopName":"亚信在线","coopType":2,"linkMan":"吴亮亮","company":"AsiaInfo","linkPhone":"027-88888888","mobilePhone":"18518314472","remark":"测试数据","coopTypeDesc":"接入商"},
            {"coopId":"2745642001","coopName":"亚信在线","coopType":2,"linkMan":"吴亮亮","company":"AsiaInfo","linkPhone":"027-88888888","mobilePhone":"18518314472","remark":"测试数据","coopTypeDesc":"接入商"},
            {"coopId":"2745642001","coopName":"亚信在线","coopType":2,"linkMan":"吴亮亮","company":"AsiaInfo","linkPhone":"027-88888888","mobilePhone":"18518314472","remark":"测试数据","coopTypeDesc":"接入商"},
            {"coopId":"2745642001","coopName":"亚信在线","coopType":2,"linkMan":"吴亮亮","company":"AsiaInfo","linkPhone":"027-88888888","mobilePhone":"18518314472","remark":"测试数据","coopTypeDesc":"接入商"},
            {"coopId":"2745642001","coopName":"亚信在线","coopType":2,"linkMan":"吴亮亮","company":"AsiaInfo","linkPhone":"027-88888888","mobilePhone":"18518314472","remark":"测试数据","coopTypeDesc":"接入商"},
            {"coopId":"2745642001","coopName":"亚信在线","coopType":2,"linkMan":"吴亮亮","company":"AsiaInfo","linkPhone":"027-88888888","mobilePhone":"18518314472","remark":"测试数据","coopTypeDesc":"接入商"},
            {"coopId":"2745642001","coopName":"亚信在线","coopType":2,"linkMan":"吴亮亮","company":"AsiaInfo","linkPhone":"027-88888888","mobilePhone":"18518314472","remark":"测试数据","coopTypeDesc":"接入商"}
        ]
    };

    $("#coopInfoTable").DataTable({
        language: {
            url: '../plugins/datatables/i18n/Chinese.json'
        },
        "ajax": "../ec-client/js/coopInfo.json",
        // "data": data.rows,
        "processing": true,
        // "serverSide": true,
        "ordering":false,
        "paging": true,
        "info": true,
        "autoWidth": true,
        "searching": false,
        "lengthChange":false,
        // "dom": '<"top">rt<"bottom"lifp><"clear">',
        "columns": [
            {"data": "coopId"},
            {"data": "coopName"},
            {"data": "coopTypeDesc"},
            {"data": "linkMan"},
            {"data": "company"},
            {"data": "linkPhone"},
            {"data": "mobilePhone"},
            {"data": "remark"}
        ],
        // "scrollY": "200px",
        // "scrollCollapse": true,
        // columnDefs: [ {
        //     orderable: false,
        //     className: 'select-checkbox',
        //     targets:   0
        // } ],
        // select: {
        //     style:    'os',
        //     selector: 'td:first-child'
        // },
        // order: [[ 1, 'asc' ]]
        select: true,
        buttons: [
            {
                text: 'Get selected data',
                action: function () {
                    var count = table.rows( { selected: true } ).count();
                    alert(count);
                }
            }
        ]
    });

    //Initialize Select2 Elements
    $(".select2").select2({
        width: '150px'
    });

    //Date range picker with time picker
    // $('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'MM/DD/YYYY h:mm A'});
    $('#reservationtime').daterangepicker();
});