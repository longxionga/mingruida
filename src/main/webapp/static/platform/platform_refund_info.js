function loadData(param) {
    $('#data').datagrid({
        title: '',//当前页面标题
        fitColumns: true,//自动列宽
        autoRowHeight: true,//自动行高度
        singleSelect: true,//禁用选择多行
        border: !0,//不显示边框
        striped: true,//条纹行
        fit: true,//自适应高度
        nowrap: true,//单元格内文字禁止换行
        rownumbers: true,//显示行号
        checkOnSelect: false, //只有选择复选框时才选中
        selectOnCheck: false,//选择行时选中复选框
        pagination : !0,// 显示分页
        pageSize : 20,// 每页显示数量 参数必须为pageList的对应
        pageList : [ 20,40,80 ],// 每页显示数量设置参数
        url: "queryRefundInfo",
        queryParams: param,//查询参数
        toolbar: '#toolbar',//工具栏
        columns: [[

            {
                field: 'id',
                title: '订单号',
                width : 190
            },
            {
                field: 'create_time',
                title: '创建时间',
                width : 100
            },
            {
                field: 'settle_name',
                title: '位属结算',
                width : 100
            },
            {
                field: 'agent_name',
                title: '位属代理商',
                width : 100
            },

            {
                field: 'user_name',
                title: '姓名',
                width : 100
            },
            {
                field: 'user_mobile',
                title: '联系电话',
                width : 100
            },
            {
                field: 'money',
                title: '提现金额',
                width : 100
            },
            {
                field: 'transaction_money',
                title: '到账金额',
                width : 100
            },
            {
                field: 'tx_type',
                title: '提现方式',
                width : 100,
                align: 'center'

            },
            {
                field: 'duty_money',
                title: '手续费',
                width : 100
            },
            {
                field: 'dict_name',
                title: '审核状态',
                width : 100,
                align: 'center'
            },
            {
                field:'out_trade_id'  ,
                title:'流水单号',
                width : 100
            },
            {
                field: 'op',
                title: '操作',
                width : 400,
                align: 'center',
                formatter: function (value, row, index) {
                    var str='';
                    if(row.refund_channel_rule_id=='jytpay'){
                        str = '\<a class="btns" href="javascript:checkJYT(\''+ row.id+ '\',\''+ row.refund_channel_rule_id+ '\');">查询订单</a>\			        		   		 ';
                    }

                    return str;

                }
            }
        ]],
        onLoadSuccess: function () {
            $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
        }
    });
}

$("#search").submit(function() {
    var $this = $(this);
    if (!$this.form("validate")) {
        return false;
    }
    var param = getFormJson(this);
    loadData(param);//查询数据
    return false;
});


function checkJYT(id,refund_channel_rule_id){
    $.ajax({
        url: "checkJinYunTongPayOrder",
        type: "POST",
        data : {id:id,refund_channel_rule_id:refund_channel_rule_id},
        success: function (data) {
            if (data.success == true) {
                $.messager.alert('提示信息', data.msg, 'info');
                $('#data').datagrid('reload');
            } else {
                $.messager.alert('错误信息', data.msg, 'error');
            }
        }
    });
}

loadData();//初始化加载数据
