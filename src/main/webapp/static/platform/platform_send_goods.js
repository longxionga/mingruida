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
            url : "querySendGoods",
            queryParams : param,// 查询参数
            toolbar : '#toolbar',// 工具栏
            columns : [ [
                {
                    width : parseInt($(this).width() * 0.1),
                    field : 'dept_name',
                    title : '服务商名称'
                },
                {
                    width : parseInt($(this).width() * 0.1),
                    field : 'goods_name',
                    title : '商品名称'
                },
                {
                    width : parseInt($(this).width() * 0.1),
                    field : 'spec_name',
                    title : '商品规格'
                },
                {
                    field : 'num',
                    title : '进货数量',
                    align: 'center',
                    width : 100
                },
                {
                    field : 'create_time',
                    title : '下单时间',
                    align: 'center',
                    width : 100
                }, {
                    field : 'update_time',
                    title : '审核时间',
                    align: 'center',
                    width : 100
                },
                {
                    field : 'status',
                    title : '审核状态',
                    formatter: function (value, row, index) {
                        if (value == 0)
                            return '待审核';
                        else if (value == 1)
                            return '审核通过';
                        else if (value == 2)
                            return '审核不通过';
                    },
                    align: 'center',
                    width : 100
                },
                {
                    field : 'op',
                    title : '操作',
                    width : 238,
                    align : 'center',
                    formatter : function(value, row, index) {
                        var str="";
                        if(row.status==0){
                             str = '\<a class="btns" href="javascript:agree(\'' + row.id + '\',\'' + row.goods_id + '\',\'' + row.spec_id + '\');">通过</a>\
		        			         \<a class="btns" href="javascript:disagree(\'' + row.id + '\',\'' + row.goods_id + '\',\'' + row.spec_id +  '\');">不通过</a>\
									';

                        }
                        return str;
                    }
                }] ],
            onLoadSuccess : function() {

            }
        });
}

function agree(id,goods_id,spec_id) {
    $.messager.confirm('确认审核', '确定已经收到货款吗?', function (r) {
        if (r) {
            $.ajax({
                url: "agree",
                type: "POST",
                data : {id:id,goods_id:goods_id,spec_id:spec_id},
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
    });
}
function disagree(id,goods_id,spec_id) {
    $.messager.confirm('确认审核', '确定拒绝吗?', function (r) {
        if (r) {
            $.ajax({
                url: "disagree",
                type: "POST",
                data : {id:id,goods_id:goods_id,spec_id:spec_id},
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

loadData({stauts:0});//初始化加载数据

