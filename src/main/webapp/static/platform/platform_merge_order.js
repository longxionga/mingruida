function loadData(param) {
    $('#data').datagrid(
        {
            title: '',// 当前页面标题
            fitColumns: true,// 自动列宽
            autoRowHeight: true,// 自动行高度
            singleSelect: true,// 禁用选择多行
            border: !1,// 不显示边框
            striped: true,// 条纹行
            fit: true,// 自适应高度
            nowrap: true,// 单元格内文字禁止换行
            rownumbers: true,// 显示行号
            checkOnSelect: false, // 只有选择复选框时才选中
            selectOnCheck: false,// 选择行时选中复选框
            pagination: !0,// 显示分页
            pageSize: 20,// 每页显示数量 参数必须为pageList的对应
            pageList: [20, 40, 80],// 每页显示数量设置参数
            url: "queryMergeOrder",
            queryParams: param,// 查询参数
            toolbar: '#toolbar',// 工具栏
            columns: [[
                {
                    width: 100,
                    field: 'nick_name',
                    title: '昵称'
                }, {
                    width: 100,
                    field: 'mobile',
                    title: '手机号'
                },
                {
                    width: 100,
                    field: 'name',
                    title: '名称'
                },
                {
                    width: 100,
                    field: 'num',
                    title: '待发货订单总数',
                    formatter:function(value,row,index){
                       return '<span style="color:#f00;">' + value + '</span>';
                    }
                },
                {
                    field : 'broker_name',
                    title : '经纪人',
                    width:100
                },
                {
                    field : 'dept_name',
                    title : '部门',
                    width:100
                },
                {
                    field : 'agent_name',
                    title : '代理商',
                    width:100
                },
                {
                    field : 'settle_name',
                    title : '服务商',
                    width:80
                },
                {
                    width: 120,
                    field: 'op',
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        var str = '\
										<a class="btns" href="javascript:;" onclick="order(\'' + row.id + '\');">订单详情</a>\
									';
                        return str;
                    }
                }]],
            onLoadSuccess: function () {
                $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
            }
        });
}


$("#search1").submit(function () {
    var $this = $(this);
    if (!$this.form("validate")) {
        return false;
    }
    var param = getFormJson(this);
    loadUser(param);
    return false;
});

//加载人员信息
function loadOrder(user_id) {
    $('#order').datagrid(
        {
            title: '',// 当前页面标题
            fitColumns: true,// 自动列宽
            autoRowHeight: true,// 自动行高度
            singleSelect: true,// 禁用选择多行
            border: !1,// 不显示边框
            striped: true,// 条纹行
            fit: true,// 自适应高度
            nowrap: true,// 单元格内文字禁止换行
            rownumbers: true,// 显示行号
            checkOnSelect: false, // 只有选择复选框时才选中
            selectOnCheck: false,// 选择行时选中复选框
            queryParams: {user_id: user_id},// 查询参数
            toolbar: '#toolbar3',// 工具栏
            url: "queryOrderDetail",
            idField: 'id',
            treeField: 'goods_name',
            columns: [[
                {
                    title: '<input type=\"checkbox\" name=\"checkorder\" onclick=\"checkall1();\"  id=\"checkorder\"></input>',
                    field: 'user_id',
                    formatter: function (value, rowData, rowIndex) {
                        /*
                         if(list !=null && list!='' && list!=undefined){
                         var strorder='';
                         $.each(list, function(key, value) {
                         strorder += value.id + ",";
                         });
                         //if(struser.indexOf(rowData.user_id)>-1)
                         if(strSplit(strorder,rowData.id))
                         {
                         return "<input type=\"checkbox\" checked=\"checked\" name=\"checkuserorder\" id='check_"+rowData.id+"'></input>";
                         }else
                         {
                         return "<input type=\"checkbox\" name=\"checkuserorder\" id='check_"+rowData.id+"'></input>";
                         }
                         }else
                         {*/
                        return "<input type=\"checkbox\" name=\"checkuserorder\" id='check_" + rowData.id + "'></input>";
                        //   }

                    },
                    width: 50
                },
                 {
                    sortable : false,
                    width : parseInt($(this).width() * 0.1),
                     field : 'product_name',
                     title : '商品名称'
                },
                {
                    sortable: false,
                    width: parseInt($(this).width() * 0.1),
                    field: 'product_price',
                    title: '商品单价'
                } ,
                 {
                     sortable : false,
                    width : parseInt($(this).width() * 0.1),
                     field : 'product_number',
                     title : '商品数量'
                },
                {
                    sortable : false,
                    width : parseInt($(this).width() * 0.1),
                    field : 'product_amount',
                    title : '商品总金额'
                },
                {
                    sortable : false,
                    width : parseInt($(this).width() * 0.1),
                    field : 'payment_amount',
                    title : '实际支付金额'
                },
                {
                    sortable : false,
                    width : parseInt($(this).width() * 0.1),
                    field : 'address_address',
                    title : '收货地址'
                }
                ,
                {
                    sortable : false,
                    width : parseInt($(this).width() * 0.1),
                    field : 'address_name',
                    title : '收货人'
                }
                ,
                {
                    sortable : false,
                    width : parseInt($(this).width() * 0.1),
                    field : 'address_mobile',
                    title : '联系电话'
                }
            ]],
            onLoadSuccess: function () {
                $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
            }
        });
}


//订单详情
function order(user_id) {
    var $this = $('#orderupdate');
    var $form = $this.find("form");
    $this.form('clear');
    $this.window({
        top: ($(window).height() - 450) * 0.5 + $(document).scrollTop(),
        left: ($(window).width() - 700) * 0.5
    });
    $("#user").html("");
    $this.window('open');
    $("#user_id").val(user_id);
    queryDescOrAsc.user_id = user_id;
    $.ajax({
        url: "queryOrderDetail",
        type: "POST",
        dataType: "json",
        data: {
            user_id: user_id
        },
        success: function (data) {
            loadOrder(user_id);
            /* if(data.length>0)
             {
             loadOrder(data,user_id);
             }else
             {
             loadOrder("",user_id);
             }*/
        }
    });
    loadOrder(user_id);

}

//为重置按钮添加单击事件
$("#reset").click(function () {
    if (updateId == undefined) {
        $('#updateform').form('clear');
        var thisData = {"is_use": 1};
        $('#update').form('load', thisData);
    } else {
        update(updateId);
    }
});

$("#search").submit(function () {
    var $this = $(this);
    if (!$this.form("validate")) {
        return false;
    }
    var param = getFormJson(this);
    loadData(param);//查询数据
    return false;
});
function checkall1() {
    if ($("[name='checkorder']").prop("checked")) {
        $("[name='checkuserorder']").each(function () {
            if ($(this).prop("checked")) {
                //$(this).removeAttr("checked");
            } else {
                $(this).prop("checked", 'true');
            }
        });
    }
    if (!$("[name='checkorder']").prop("checked")) {
        $("[name='checkuserorder']").each(function () {
            $(this).removeAttr("checked");
        });
    }
}


function getSelected1() {
    var idList = "";
    var len = 0;
    $("input:checked").each(function () {
        var id = $(this).attr("id");

        if (id.indexOf('check_type') == -1 && id.indexOf("check_") > -1) {
            idList += id.replace("check_", '') + ',';
            len += 1;
        }

    });
    idList = idList.substring(0, idList.length - 1);
    if (len < 1 ) {
        $.messager.alert('提示', '请勾选记录!', 'error');
        return;
    }
    $.ajax({
        url: "saveMergeOrder",
        type: "POST",
        dataType: "json",
        data: {
            payIds: idList
        },
        success: function (data) {
            $.messager.alert('提示信息', data.msg, 'info');
            if (data.success == true) {
                $('#orderupdate').dialog('close');
                $('#search').submit();
            }
        }

    });
}

loadData({order_type: queryDescOrAsc.create_date});//初始化加载数据


//刷新功能
$("#refresh").click(function () {
    $("tr[id^='datagrid-row-r2-2-'],tr[id^='datagrid-row-r2-1-']").removeClass("datagrid-row-selected");
    order(queryDescOrAsc.user_id);
});


function strSplit(parm, str) {
    //str="2,2,3,5,6,6"; //这是一字符串
    var strs = new Array(); //定义一数组
    strs = parm.split(","); //字符分割
    for (i = 0; i < strs.length; i++) {
        if (str == strs[i]) {

            return true;
        }
        //document.write(strs[i]+"<br/>"); //分割后的字符输出
    }
    return false;
}

