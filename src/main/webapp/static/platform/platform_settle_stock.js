/**
 * Created by wangli on 2017/11/8.
 */
function loadData(param) {
    var  columns  =  [  {
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
            field : 'total_amount',
            title : '库存数量',
            align: 'center',
            formatter: function (value, row, index) {
                if (value <100)
                    return '<span style="color:#f00;">'+value+'</span>';
                else
                    return value;
            },
            width : 100
        },
        {
            sortable : true,
            field : 'sale_amount',
            title : '商品销量',
            align: 'center',
            width : 100
        },]

    if(dept_type==0){
        columns.push({
            sortable : true,
            field : 'dept_name',
            title : '服务商名称',
            align: 'center',
            width : 100
        });
    };
    columns = [columns];
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
            url : "querySettleStock",
            queryParams : param,// 查询参数
            toolbar : '#toolbar',// 工具栏
            columns: columns,
            onLoadSuccess : function() {

            }
        });
}

// 添加/修改方法
function update() {
    queryDescOrAsc.goods_id="";
    var $this = $('#update');
    var $form = $this.find("form");
    $this.window('open');

        $this.panel({
            title : '添加'
        });
        // 重置有效
        $("#reset").linkbutton('enable');
        $form.attr("action", $form.attr("action-add"));

    $this.form('clear');


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

//下拉联动加载结算服务商和代理商
$(function(){
    $.ajax({
        url : "../platform/queryGoodsList",
        success : function(data) {
            $("#goods_id").combobox("loadData", data);
        }
    });
    $("#goods_id").combobox(
        {
            onSelect : function(record) {
                queryDescOrAsc.goods_id=record.id;
                $("#spec_id").combobox(
                    "reload",
                    "../platform/queryGoodsSpec?goods_id=" + record.id);
            }
        });
    $("#spec_id").combobox(
        {
            onSelect : function(record) {
                $.ajax({
                    url : "../platform/queryGoodsStock",
                    type : "POST",
                    data : {goods_id:queryDescOrAsc.goods_id,spec_id:record.id},
                    success : function(data) {
                        if(data == null){
                            return ;
                        }
                        var html = '\
				<span style="color:red;">价格：¥ '+fmoney(data[0].original_price,2)+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+'\</span>\
				<span style="color:red;">可订数量： '+data[0].booked_amount+'\</span>\
				';
                        $("#show").html(html);
                    }
                });

            }
        });
});


loadData({order_type:queryDescOrAsc.create_time});//初始化加载数据

