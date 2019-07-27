function loadData(param,url) {
    $('#data').datagrid(
        {
            title : '',// 当前页面标题
            fitColumns : true,// 自动列宽
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
            columns : [ [
                {
                    sortable : true,
                    width : 100,
                    field : 'create_time',
                    align : 'center',
                    title : '创建时间'
                },
                {
                    sortable : false,
                    width : 100,
                    field : 'settle_name',
                    align : 'center',
                    title : '服务商名称'
                },
                {
                    sortable : false,
                    width : 100,
                    field : 'agent_name',
                    align : 'center',
                    title : '代理商名称'
                },
                {
                    sortable : true,
                    field : 'nick_name',
                    title : '昵称',
                    align : 'right',
                    width : 100
                },
                {
                    sortable : true,
                    field : 'mobile',
                    title : '手机号',
                    align : 'right',
                    width : 100
                },
                {
                    sortable : true,
                    width : 100,
                    field : 'product_name',
                    align : 'right',
                    title : '商品名称'
                },
                {
                    sortable : true,
                    width : 100,
                    field : 'product_price',
                    align : 'right',
                    title : '商品单价'
                },
                {
                    sortable : true,
                    field : 'product_number',
                    title : '商品数量',
                    align : 'right',
                    width : 100
                },
                {
                    sortable : true,
                    field : 'product_amount',
                    title : '商品总价',
                    align : 'right',
                    width : 100
                },
                {
                    sortable : true,
                    field : 'order_amount',
                    title : '订单总金额',
                    align : 'right',
                    width : 100
                },
                {
                    sortable : true,
                    field : 'payment_amount',
                    title : '实际支付金额',
                    align : 'right',
                    width : 100
                },
                {
                    sortable : true,
                    field : 'post_amount',
                    title : '运费',
                    align : 'right',
                    width : 100
                },
                {
                    sortable : true,
                    field : 'status',
                    title : '状态',
                    align : 'right',
                    width : 100,
                    formatter: function (value, row, index) {
                        //1待发货,2,活动订单确认发货,3.已发货,4.退款,5.订单完成,已关闭
                        if (value == 1)
                            return '<span style="color:#f00;">待发货</span>';
                        else if(value==3)
                            return '已发货';
                        else if(value==4)
                            return '退款';
                        else if(value==5)
                            return '订单完成';
                    }
                }
            ] ],
            onLoadSuccess : function() {
                $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
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
				<span>营业总额：¥ '+fmoney(data[0].salesSum,2)+'\</span>\
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
    loadData(param,"querySalesGoodsReport");//查询数据
    salesSum(param);
    return false;
});

function expInfo() {
    //var arrayData = $("#search").serialize();
    window.location.href = 'exportAgentdayReportInfo';
}
loadData();//初始化加载数据