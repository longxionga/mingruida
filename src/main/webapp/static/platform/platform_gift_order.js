dictInfo(1023,"logistics");
function loadData(param,url) {
    var boolFitColumns=true;
    if($(document).width()<=1600-220){
        boolFitColumns=false;
    }
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
            idField:'',
            url : url,
            remoteSort:false,
            queryParams : param,// 查询参数
            toolbar : '#toolbar',// 工具栏
            view: detailview,
            detailFormatter:function(index,row){
                return '<div style="padding:2px"><table class="ddv"></table></div>';
            },
            onExpandRow: function(index,row){
                var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');

                ddv.datagrid({
                    url:"queryGiftDetail?id="+row.id+"",
                    fitColumns:true,
                    singleSelect:true,
                    rownumbers:true,
                    height:'auto',
                    columns:[[
                        {
                            width: 120,
                            field : 'id',
                            title : '订单编号'
                        },
                        {
                            width: 50,
                            field : 'product_id',
                            title : '商品编号'
                        },
                        {
                            width: 100,
                            field : 'product_name',
                            title : '商品名称'
                        },
                        {
                            width: 100,
                            field : 'order_amount',
                            title : '订单总额'
                        },
                        {
                            width: 80,
                            field : 'product_amount',
                            title : '商品总额'
                        },
                        {
                            width: 60,
                            field : 'product_number',
                            title : '商品数量'
                        },
                        {
                            width: 100,
                            field : 'product_price',
                            title : '商品单价'
                        },
                        {
                            width: 100,
                            field : 'notes',
                            title : '订单备注'
                        }

                        ]],
                    onResize:function(){
                        $('#data').datagrid('fixDetailRowHeight',index);
                    },
                    onLoadSuccess:function(){
                        setTimeout(function(){
                            $('#data').datagrid('fixDetailRowHeight',index);
                        },0);
                    }
                });
                $('#data').datagrid('fixDetailRowHeight',index);
            },
            columns : [ [
                {
                    width: 100,
                    field : 'user_name',
                    title : '用户名称'
                },
                {
                    width: 80,
                    field : 'user_mobile',
                    title : '手机号'
                },
                {
                    width: 100,
                    field : 'receiver_addr',
                    title : '地址'
                },
                {
                    width: 50,
                    field : 'num',
                    title : '订单数量'
                },
                // {
                //     width: 100,
                //     field : 'amount',
                //     title : '赠品总价'
                // },
                {
                    width: 80,
                    field : 'dict_name',
                    title : '快递公司'
                },
                {
                    width: 100,
                    field : 'logistics_no',
                    title : '快递单号'
                },
                {
                    width: 100,
                    field : 'create_time',
                    title : '创建时间'
                },
                {
                    width: 120,
                    field : 'op',
                    title : '操作',
                    align : 'center',
                    formatter : function(value, row, index) {
                        var str = '\<a class="btns" href="javascript:;" onclick="update(\'' + index+'\');">快递单号</a>\
                       \<a class="btns" href="javascript:;" onclick="check(\'' + row.dict_value+'\',\'' + row.logistics_no + '\');">查询物流</a>\
									';
                        return str;
                    }
                } ] ],
            onLoadSuccess : function() {
                $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
            }
        });
}


//添加/修改方法
var updateId;
function update(id) {
    var $this = $('#update');
    var $form = $this.find("form");
    updateId=id;
    $("#resetBtn").linkbutton('disable');
    $this.window({ title: '编辑' });
    // 重置有效
    $("#reset").linkbutton('enable');

    $form.attr("action", $form.attr("action-edit"));
    $this.form('clear');
    $this.form('load', $('#data').datagrid('getData').rows[id]);
    $("#id").val($('#data').datagrid('getData').rows[id].id);
    $this.window({
        top:($(window).height() - 289) * 0.5+$(document).scrollTop(),
        left:($(window).width() - 588) * 0.5
    });
    $this.window('open');
}
//为重置按钮添加单击事件
$("#reset").click(function(){
    if (updateId == undefined) {
        $('#updateform').form('clear');
        var thisData = {"is_use":1};
        $('#update').form('load', thisData);
    }else{
        update(updateId);
    }
});

$("#search").submit(function() {
    var $this = $(this);
    if (!$this.form("validate")) {
        return false;
    }
    var param = getFormJson(this);
    loadData(param,"queryGiftOrder");//查询数据
    return false;
});
loadData("","queryGiftOrder");//初始化加载数据



function check(com,number) {
    /*var html="";
    $.ajax({
        type: "POST",
        url: "queryLogistics",
        dataType: 'json',
        data:{logistics:com,logistics_no:number},
        success: function (msg) {
            if(msg.data.length==0){
                    html="暂无物流信息";
            }else {
                for(var i=0;i<msg.data.length;i++){
                    html+=msg.data[i].time+msg.data[i].context+"\r\n<br/>";
                }
            }

            $("#msg").html(html);
            $('#msg').window({
                top:($(window).height() - 400) * 0.5+$(document).scrollTop(),
                left:($(window).width() - 800) * 0.5
            });
            $('#msg').window('open');
        }
    });*/
}
