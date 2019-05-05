

$.get("/product/list" , function (result) {
    var data = {rows:result.resultData.data};
    var pagination = {row:result.pagination}
    console.log(pagination.row);
    var oTableInit = new Object();
    // 得到查询的参数  条件查询
    oTableInit.queryParams = function(params) {
        alert(params)
        var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            size: params.pageSize, // 页面大小
            page: params.currentPage, // 页码
            startdate : $("#start").val(),
            enddate : $("#end").val(),
            statu : $("#span").html()
        };
        return temp;
    };
    $('#tb_departments').bootstrapTable({
        data:data.rows,
        dataType:"json",
        toolbar : '#toolbar', // 工具按钮用哪个容器
        striped : true, // 是否显示行间隔色
        cache:false,
        striped:true,
        toolbar : '#toolbar', // 工具按钮用哪个容器
        striped : true, // 是否显示行间隔色
        cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination : true, // 是否显示分页（*）
        sortable : false, // 是否启用排序
        sortOrder : "asc", // 排序方式
        sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
        pageNumber : 1, // 初始化加载第一页，默认第一页
        pageSize : 10, // 每页的记录行数（*）
        pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
        search : true, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch : true,
        showColumns : true, // 是否显示所有的列
        showRefresh : false, // 是否显示刷新按钮
        minimumCountColumns : 1, // 最少允许的列数
        clickToSelect : true, // 是否启用点击选中行
        height : 400, // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId : "id", // 每一行的唯一标识，一般为主键列
        showToggle : true, // 是否显示详细视图和列表视图的切换按钮
        cardView : false, // 是否显示详细视图
        detailView : false, // 是否显示父子表
        queryParamsType : "",  //我使用的是向后台传输 page和size 还有另一种方式，请自行百度
        queryParams : oTableInit.queryParams,
        columns: [{
                    checkbox: true
                }, {
                    field: 'id',
                    title: 'UID'
                }, {
                    field: 'model',
                    title: 'Model:A SIN'
                }, {
                    field: 'productName',
                    title: 'ProductName'
                }, {
                    field: 'brand',
                    title: 'Brand'
                }, {
                    field: 'sku',
                    title: 'SKU'
                }, {
                    field: 'weight',
                    title: 'W(LB)'
                }, {
                    field: 'length',
                    title: 'Size(Inch)'
                }, {
                    field: 'price',
                    title: '家里收货价格'
                }, {
                    field: 'promotQuantity',
                    title: '家里加价数量'
                }, {
                    field: 'promotPrice',
                    title: '家里加价价格'
                }, {
                    field: 'warehousePrice',
                    title: '仓库收货价格'
                }, {
                    field: 'warehousePromotQuantity',
                    title: '仓库加价数量'
                }, {
                    field: 'warehousePromotePrice',
                    title: '仓库加价价格'
                }, {
                    field: 'userNote',
                    title: 'note'
                }, {
                    field: 'status',
                    title: 'Status'
                }, {
                    field: 'Update',
                    title: 'Update'
                }]
    })
},"json")



function fmtDate(obj) {   //时间转换的方法
    var date = new Date(obj);
    var y = 1900 + date.getYear();
    var m = "0" + (date.getMonth() + 1);
    var d = "0" + date.getDate();
    var h = date.getHours();
    var mm = date.getMinutes();
    var s = date.getSeconds();
    return y + "-" + m.substring(m.length - 2, m.length) + "-"
        + d.substring(d.length - 2, d.length) + "  " + h + ":" + mm
        + ":" + s;
}


//
// $(function () {
//     //1.初始化Table
//     var oTable = new TableInit();
//     oTable.Init();
//
//     // //2.初始化Button的点击事件
//     // var oButtonInit = new ButtonInit();
//     // oButtonInit.Init();
//
// });
//
//
// var TableInit = function () {
//     var data;
//     var oTableInit = new Object();
//     //初始化Table
//     oTableInit.Init = function () {
//         $('#tb_departments').bootstrapTable({
//
//
//             // ajax: function (request){
//             //     $.ajax({
//             //         type : "GET",
//             //         url : "http://localhost:8080/product/list?currentPage=1&pageSize=10",
//             //         contentType: "application/json;charset=utf-8",
//             //         dataType:"jsonp",
//             //         data:'',
//             //         jsonp:'callback',
//             //         success:function (msg) {
//             //             alert(msg);
//             //             request.success({
//             //                 row:msg
//             //             });
//             //             $('#tb_departments').bootstrapTable('load', msg);
//             //         },
//             //         error:function(msg){
//             //             console.log(msg.responseText);
//             //             alert(msg.responseText['resultData']);
//             //             var obj = JSON.parse(msg.responseText);
//             //             console.log(obj);
//             //             alert(obj.resultData.data[0].asin);
//             //             $('#tb_departments').bootstrapTable('load', obj.resultData.data);
//             //             alert("错误");
//             //         }
//             //     });
//             // },
//
//
//             url: '/product/list',     //请求后台的URL（*）
//             method: 'get',                      //请求方式（*）
//             toolbar: '#toolbar',                //工具按钮用哪个容器
//             striped: true,                      //是否显示行间隔色
//             cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
//             pagination: true,                   //是否显示分页（*）
//             sortable: false,                     //是否启用排序
//             sortOrder: "asc",                   //排序方式
//             queryParams: oTableInit.queryParams,//传递参数（*）
//             contentType: "application/x-www-form-urlencoded",
//             sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
//             pageNumber:1,                       //初始化加载第一页，默认第一页
//             pageSize: 10,                       //每页的记录行数（*）
//             pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
//             search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
//             strictSearch: true,
//             showColumns: true,                  //是否显示所有的列
//             showRefresh: true,                  //是否显示刷新按钮
//             minimumCountColumns: 1,             //最少允许的列数
//             clickToSelect: true,                //是否启用点击选中行
//             height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
//             uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
//             showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
//             cardView: false,                    //是否显示详细视图
//             detailView: false,                   //是否显示父子表
//             url: "/product/list",
//             dataType: "json",
//             singleSelect: false,
//             striped: true, //是否显示行间隔色
//             cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
//             sortable: true, //是否启用排序
//             pagination: true,   //显示分页按钮
//             sortName:"starttime",
//             sortOrder:"desc", //默认排序
//             pageNumber: 1, //初始化加载第一页，默认第一页
//             pageSize: 10,   //默认显示的每页个数
//             pageList: [10, 25, 50, 100],    //可供选择的每页的行数（*）
//             queryParamsType: '',
//             sidePagination: "server", //服务端处理分页
//             //showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
//             //cardView: false,                    //是否显示详细视图
//             detailView: false,                   //是否显示父子表
//
//             onLoadSuccess:function (msg) {
//                 console.log(msg);
//                 data = msg;
//             },
//             responseHandler:function(res){
//                 //动态渲染表格之前获取有后台传递的数据时,用于获取出除去本身渲染所需的数据的额外参数
//                 //详见此函数参数的api
//                 console.log(res);
//                 return res.resultData.data;
//             },
//             columns: [{
//                 checkbox: true
//             }, {
//                 field: 'id',
//                 title: 'UID'
//             }, {
//                 field: 'model',
//                 title: 'Model:A SIN'
//             }, {
//                 field: 'productName',
//                 title: 'ProductName'
//             }, {
//                 field: 'brand',
//                 title: 'Brand'
//             }, {
//                 field: 'sku',
//                 title: 'SKU'
//             }, {
//                 field: 'weight',
//                 title: 'W(LB)'
//             }, {
//                 field: 'length',
//                 title: 'Size(Inch)'
//             }, {
//                 field: 'price',
//                 title: '家里收货价格'
//             }, {
//                 field: 'promotQuantity',
//                 title: '家里加价数量'
//             }, {
//                 field: 'promotPrice',
//                 title: '家里加价价格'
//             }, {
//                 field: 'warehousePrice',
//                 title: '仓库收货价格'
//             }, {
//                 field: 'warehousePromotQuantity',
//                 title: '仓库加价数量'
//             }, {
//                 field: 'warehousePromotePrice',
//                 title: '仓库加价价格'
//             }, {
//                 field: 'userNote',
//                 title: 'note'
//             }, {
//                 field: 'status',
//                 title: 'Status'
//             }, {
//                 field: 'Update',
//                 title: 'Update'
//             }]
//         });
//     };
//
//
//
//
//
//
//     //得到查询的参数
//     oTableInit.queryParams = function (params) {
//         var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
//             limit: params.limit,   //页面大小
//             offset: params.offset,  //页码
//             departmentname: $("#txt_search_departmentname").val(),
//             statu: $("#txt_search_statu").val(),
//             pageNumber: this.pageNumber,
//             pageSize: this.pageSize
//         };
//         return temp;
//     };
//     return oTableInit;
// };
//
//
//
//
// function formatData(data){
//     alert(data);
//     console.log(data)
// }


var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function () {
        //$("#btn_add").click(function () {
        //    $("#myModalLabel").text("新增");
        //    $("#myModal").find(".form-control").val("");
        //    $('#myModal').modal()

        //    postdata.DEPARTMENT_ID = "";
        //});

        //$("#btn_edit").click(function () {
        //    var arrselections = $("#tb_departments").bootstrapTable('getSelections');
        //    if (arrselections.length > 1) {
        //        toastr.warning('只能选择一行进行编辑');

        //        return;
        //    }
        //    if (arrselections.length <= 0) {
        //        toastr.warning('请选择有效数据');

        //        return;
        //    }
        //    $("#myModalLabel").text("编辑");
        //    $("#txt_departmentname").val(arrselections[0].DEPARTMENT_NAME);
        //    $("#txt_parentdepartment").val(arrselections[0].PARENT_ID);
        //    $("#txt_departmentlevel").val(arrselections[0].DEPARTMENT_LEVEL);
        //    $("#txt_statu").val(arrselections[0].STATUS);

        //    postdata.DEPARTMENT_ID = arrselections[0].DEPARTMENT_ID;
        //    $('#myModal').modal();
        //});

        //$("#btn_delete").click(function () {
        //    var arrselections = $("#tb_departments").bootstrapTable('getSelections');
        //    if (arrselections.length <= 0) {
        //        toastr.warning('请选择有效数据');
        //        return;
        //    }

        //    Ewin.confirm({ message: "确认要删除选择的数据吗？" }).on(function (e) {
        //        if (!e) {
        //            return;
        //        }
        //        $.ajax({
        //            type: "post",
        //            url: "/Home/Delete",
        //            data: { "": JSON.stringify(arrselections) },
        //            success: function (data, status) {
        //                if (status == "success") {
        //                    toastr.success('提交数据成功');
        //                    $("#tb_departments").bootstrapTable('refresh');
        //                }
        //            },
        //            error: function () {
        //                toastr.error('Error');
        //            },
        //            complete: function () {

        //            }

        //        });
        //    });
        //});

        //$("#btn_submit").click(function () {
        //    postdata.DEPARTMENT_NAME = $("#txt_departmentname").val();
        //    postdata.PARENT_ID = $("#txt_parentdepartment").val();
        //    postdata.DEPARTMENT_LEVEL = $("#txt_departmentlevel").val();
        //    postdata.STATUS = $("#txt_statu").val();
        //    $.ajax({
        //        type: "post",
        //        url: "/Home/GetEdit",
        //        data: { "": JSON.stringify(postdata) },
        //        success: function (data, status) {
        //            if (status == "success") {
        //                toastr.success('提交数据成功');
        //                $("#tb_departments").bootstrapTable('refresh');
        //            }
        //        },
        //        error: function () {
        //            toastr.error('Error');
        //        },
        //        complete: function () {

        //        }

        //    });
        //});

        //$("#btn_query").click(function () {
        //    $("#tb_departments").bootstrapTable('refresh');
        //});
    };

    return oInit;
};