/**
 * Created by wangli on 2017/11/8.
 */
function loadData(param) {
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
            url : "queryGoodsInfoForSettle",
            idField:'spec_id',
            queryParams : param,// 查询参数
            toolbar : '#toolbar',// 工具栏
            columns : [ [
                {
                    sortable : false,
                    width : parseInt($(this).width() * 0.1),
                    field : 'goods_name',
                    title : '商品名称'
                },
                {
                    sortable : true,
                    width : parseInt($(this).width() * 0.1),
                    field : 'spec_name',
                    title : '商品规格'
                },
                {
                    sortable : true,
                    field : 'booked_amount',
                    title : '可定数量',
                    align: 'center',
                    width : 100
                },
                {
                    sortable : true,
                    field : 'img_uri',
                    title : '图片预览',
                    align: 'center',
                    width : 100
                },
                {
                    width: 100,
                    field: 'op',
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {

                            var str = '\
									<a class="btns" href="javascript:update(\''+ row.goods_id+'\',\''+ row.spec_id+'\');">采购</a>\
									<a class="btns" href="javascript:queryDetail(\''+ row.goods_id+'\',\''+ row.spec_id+'\');">采购记录</a>\
						';
                            return str;


                    }
                }] ],
            onLoadSuccess : function() {
                var imgUrl=$(".datagrid-body td[field='img_uri']");
                imgUrl.overDialog('<div class="over_dialog_img"><img src="#" /></div>');
            }
        });
}

// 添加/修改方法
function update(goods_id,spec_id) {
    var $this = $('#update');
    var $form = $this.find("form");
    $this.window({ title: '下单' });
    // 重置有效
    $("#reset").linkbutton('enable');

    $form.attr("action", $form.attr("action-edit"));
    $this.form('clear');
    //$this.form('load', $('#data').datagrid('getData').rows[id]);
    $("#goods_id").val(goods_id);
    $("#spec_id").val(spec_id);
    $this.window({
        top:($(window).height() - 289) * 0.5+$(document).scrollTop(),
        left:($(window).width() - 588) * 0.5
    });
    $this.window('open');

}

//设置经纪人
function queryDetail(goods_id,spec_id) {

    //打开窗口
    $('#order_detail').window({
        top:($(window).height() - 450) * 0.5+$(document).scrollTop(),
        left:($(window).width() - 700) * 0.5
    });
    $('#order_detail').window('open');
    //部门id
    //设置全局参数
    queryDescOrAsc.order_goods_id=goods_id;
    queryDescOrAsc.order_spec_id=spec_id;
    window.queryOrder={
        order_goods_id:goods_id,
        order_spec_id:spec_id
    }
    $("#order_goods_id").val(goods_id);
    $("#order_spec_id").val(spec_id);
    loadOrder(queryOrder);
}


function loadOrder(param) {
    $('#order').datagrid({
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
        url: "queryPurchase",
        queryParams:param,// 查询参数
        toolbar: '#toolbar1',//工具栏
        columns: [[
            {
                width: parseInt($(this).width() * 0.1),
                field: 'goods_name',
                align: 'center',
                title: '商品名称'
            },
            {
                width: parseInt($(this).width() * 0.1),
                field: 'spec_name',
                title: '规格名称'
            },
            {
                width: parseInt($(this).width() * 0.1),
                field: 'create_time',
                title: '下单时间'
            },
            {
                width: parseInt($(this).width() * 0.1),
                field: 'num',
                title: '购买数量'
            },
            {
                width: parseInt($(this).width() * 0.1),
                field: 'status',
                title: '审核状态',
                formatter: function (value, row, index) {
                    if (value == 0)
                        return '待审核';
                    else if (value == 1)
                        return '审核通过';
                    else if (value == 2)
                        return '审核不通过';
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



$("#search1").submit(function() {
    var $this = $(this);
    if (!$this.form("validate")) {
        return false;
    }
    var param = getFormJson(this);
    loadOrder(param);
    return false;
});


//提现方式
$('#status').combobox({
    width : '150',
    height : '23',
    valueField : 'label',
    textField : 'value',
    panelHeight : 'auto',
    editable: false,
    data : [ {
        label : '0',
        value : '待审核'
    },{
        label : '1',
        value : '审核通过'
    },{
        label : '2',
        value : '审核不通过'
    }  ]
});
loadData({order_type:queryDescOrAsc.create_time});//初始化加载数据

