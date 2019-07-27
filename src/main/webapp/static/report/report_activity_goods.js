function loadData(param,url) {
    var boolFitColumns=true;
    if($(document).width()<=1600-220){
        boolFitColumns=false;
    }
    $('#data').datagrid(
        {
            title : '',// 当前页面标题
            fitColumns : boolFitColumns,// 自动列宽
            autoRowHeight : true,// 自动行高度
            singleSelect : true,// 禁用选择多行
            border : !1,// 不显示边框
            striped : true,// 条纹行
            fit : true,// 自适应高度
            nowrap : true,// 单元格内文字禁止换行
            rownumbers : true,// 显示行号
            checkOnSelect : false, // 只有选择复选框时才选中
            selectOnCheck : false,// 选择行时选中复选框
            pagination : !0,// 显示分页
            pageSize : 20,// 每页显示数量 参数必须为pageList的对应
            pageList : [ 20,40,80 ],// 每页显示数量设置参数
            idField:'id',
            url : url,
            remoteSort:false,
            queryParams : param,// 查询参数
            toolbar : '#toolbar',// 工具栏
            frozenColumns:[[
                {
                    sortable : true,
                    field : 'machine_code',
                    title : '机具编号',
                    align : 'right',
                    width : 160
                },
                {
                    sortable : true,
                    field : 'total_amount',
                    title : '订单金额',
                    align : 'right',
                    width : 100
                }
            ]],
            columns : [ [

                {
                    width: 150,
                    field: 'id',
                    title: '订单号'
                }, {
                    width: 50,
                    field: 'order_type',
                    title: '订单类型'
                },
                {
                    width: 50,
                    field: 'order_state',
                    title: '订单状态'
                },
                {
                    width: 50,
                    field: 'pay_type',
                    title: '支付状态',
                    formatter:function(value,row,index){
                        return '<span style="color:#f00;">' + value + '</span>';
                    }
                },
                {
                    width: 100,
                    field: 'unionpay_merchant_code',
                    title: '银联商户号'
                },
                {
                    width: 50,
                    field: 'auth_code',
                    title: '授权码'
                },
                {
                    width: 50,
                    field: 'settle_mode',
                    title: '结算模式'
                },
                {
                    width: 50,
                    field: 'settle_state',
                    title: '结算状态'
                },
                {
                    width: 50,
                    field: 'settle_merchant_code',
                    title: '结算商户号'
                },
                {
                    width: 100,
                    field: 'settle_merchant_name',
                    title: '结算商户名称'
                },
                {
                    width: 50,
                    field: 'settle_total_amount',
                    title: '结算总金额'
                },
                {
                    width: 50,
                    field: 'settle_service_amount',
                    title: '结算手续费'
                },
                {
                    width: 50,
                    field: 'settle_service_ratio',
                    title: '结算服务比列'
                },
                {
                    width: 130,
                    field: 'machine_code',
                    title: '机具编号'
                },
                {
                    width: 50,
                    field: 'card_type',
                    title: '卡类型'
                },
                {
                    width: 100,
                    field: 'card_code',
                    title: '卡号'
                },{
                    width: 50,
                    field: 'total_amount',
                    title: '商品总金额'
                },
                {
                    width: 50,
                    field: 'amount',
                    title: '订单总金额'
                },
                {
                    width: 50,
                    field: 'real_amount',
                    title: '支付金额'
                },
                {
                    width: 50,
                    field: 'trade_day',
                    title: '交易日期'
                },
                {
                    width: 120,
                    field: 'trade_time',
                    formatter : dateTimeFormatter,
                    title: '交易时间'
                },
                {
                    width: 50,
                    field: 'create_day',
                    title: '下单日期'
                },
                {
                    width: 120,
                    field: 'create_time',
                    formatter : dateTimeFormatter,
                    title: '创建时间'
                },
                {
                    field : 'broker_name',
                    title : '经纪人',
                    width:80
                },
                {
                    field : 'dept_name',
                    title : '部门',
                    width:80
                },
                {
                    field : 'agent_name',
                    title : '代理商',
                    width:80
                },
                {
                    field : 'settle_name',
                    title : '服务商',
                    width:80
                }

            ] ],
            onLoadSuccess : function() {
              //  $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
            }
        });
}

function salesSum(param){
    $.ajax({
        url : "../report/queryReportSalesSum",
        type : "POST",
        data : param,
        success : function(data) {
            if(data == null){
                return ;
            }
            var html = '\
				<span>销售总额：¥ '+fmoney(data[0].salesSum,2)+'\</span>\
				<span>商品销量：¥ '+data[0].productSum+'\</span>\
				';
            $("#total").html(html);
        }
    });
}

$("#search").submit(function() {
    var $this = $(this);
    if (!$this.form("validate")) {
        return false;
    }
    var param = getFormJson(this);
    loadData(param,"queryActivityGoodsReport");//查询数据
    salesSum(param);
    return false;
});

function expInfo() {
    //var arrayData = $("#search").serialize();
    window.location.href = 'exportAgentdayReportInfo';
}
loadData();//初始化加载数据