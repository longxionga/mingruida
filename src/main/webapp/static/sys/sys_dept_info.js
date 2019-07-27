dict(1001);
function loadData(param, url) {
    var boolFitColumns = true;
    if ($(document).width() <= 1600 - 220) {
        boolFitColumns = false;
    }
    var fits = 0, columns = [{
        width: 220,
        field: 'dept_name',
        title: '单位名称'
    }, {
        width: 30,
        field: 'dept_id',
        align: 'center',
        title: '部门ID'
    },
        {
            width: 80,
            field: 'dept_code',
            title: '单位编码'
        }, {
            width: 80,
            field: 'dept_type',
            title: '级别',
            formatter: dictFormatter
        }, {
            width: 90,
            field: 'dept_money',
            align: 'right',
            title: '余额',
            formatter: function (value) {
                return value + " 元";
            }
        },
        {
            width: 100,
            align: 'center',
            field: 'dept_mobile',
            title: '联系电话'
        }];

    if ($("#deptType").val() < 4) {
        columns.push({
            width: 50,
            field: 'tj_ratio',
            align: 'right',
            title: '推荐人比率',
            formatter: function (value, row) {
                if (row.is_tj_man == 1) {
                    return value + " %";
                }
            }
        });
    }
    ;
    if ($("#deptType").val() < 5) {
        columns.push({
            width: 130,
            field: 'dept_app_id',
            title: '微信ID'
        });
    }
    ;
    columns.push({
            width: 160,
            field: 'dept_ratio',
            title: '分配比率',
            formatter: function (value, row) {
                if (row.dept_type == 4) {
                    return row.ce_allot + "% | " + row.ch_allot + "% | " + row.s_allot + "% | " + value + "%";
                } else {
                    return value + "%";
                }
            }
        },
        {
            width: 50,
            field: 'is_use',
            title: '是否有效',
            align: 'center',
            formatter: function (value) {
                if (value == 1) {
                    return '有效';
                } else if (value == 0) {
                    return '<span style="color:#f00;">已禁用</span>';
                }
                else if (value == 4) {
                    return '<span style="color:#f00;">已退出</span>';
                }
            }
        },
        {
            width: 150,
            field: 'dept_title',
            title: '商城名'
        },
        {
            width: 150,
            field: 'dept_url',
            title: '机构网站'
        },
        {
            width: 150,
            field: 'broker_url',
            title: '经纪人链接'
        },
        {
            width: 350,
            field: 'op',
            title: '操作',
            formatter: function (value, row, index) {

                var str = '';
                if (row.dept_mobile != -1 && row.dept_type != 5) {
                    str += '\<a class="btns" href="javascript:update(\'' + row.dept_id + '\',\'update\',\'' + row.dept_type + '\',\'' + row._parentId + '\');">编辑</a>';
                }
                if (row.dept_type < 5) {
                    str += '\<a class="btns" href="javascript:update(\'' + row.dept_id + '\',\'insert\',\'' + row.dept_type + '\',\'' + row._parentId + '\');">添加单位</a>';
                }
                if (row.dept_type == 4 && dept_type == 0) {
                    str += '\<a class="btns" href="javascript:update1(\'' + row.dept_id + '\',\'' + row + '\');">比率模板</a>';
                }
                if (row.dept_type == 4) {
                    str += '\<a class="btns" href="javascript:;" onclick="clearUserWxId(\'' + row.dept_id + '\');">清空微信ID</a>';
                }
                if (row.dept_type == 5) {
                    str += '\<a class="btns" href="javascript:;" onclick="deptupdate(\'' + row.dept_id + '\');">转移部门</a>';
                }
                return str;
            }
        });

    if (columns.length > 10) {
        fits = 0;
    }
    columns = [columns];
    $('#data').treegrid(
        {
            title: '',// 当前页面标题
            fitColumns: false,// 自动列宽
            autoRowHeight: true,// 自动行高度
            singleSelect: true,// 禁用选择多行
            border: !1,// 不显示边框
            striped: true,// 条纹行
            fit: true,// 自适应高度
            nowrap: true,// 单元格内文字禁止换行
            rownumbers: true,// 显示行号
            idField: 'dept_id',
            url: url,
            queryParams: param,// 查询参数
            animate: true,
            collapsible: true,
            treeField: 'dept_name',
            toolbar: '#toolbar',// 工具栏
            columns: columns,
            onLoadSuccess: function () {
                $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;border-radius:4px;line-height:12px;padding:2px 10px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
                $(".datagrid-body td[field='dept_app_id']," +
                    ".datagrid-body td[field='dept_url']," +
                    ".datagrid-body td[field='broker_url']").overTitle();
            }
        });
}

loadData('', 'queryDeptInfos');//初始化加载数据


var updateId;
var actions;
var depttype;
function update(dept_id, action, dept_type, dept_parentId) {

    var $this = $('#update');
    var $form = $this.find("form");

    updateId = dept_id;
    actions = action;
    depttype = dept_type;
    $this.form('clear');
    if (action == "insert") {
        var thisData = {
            "is_use": 1,
            "is_tj_man": 0,
            "tj_ratio": 0,
            "dept_money": 0,
            "dept_parent_id": dept_id,
            "dept_type": dept_type
        };
        $this.form('load', thisData);
        // if (dept_type == '4') {
        //     $("#dept_title").closest("tr").show();
        // } else {
        //     $("#dept_title").closest("tr").hide();
        // }
        if (dept_type == '2') {
            $("#is_tj_man,#tj_ratio").closest("tr").show();
        } else {
            $("#is_tj_man,#tj_ratio").closest("tr").hide();

        }
        if (dept_type == '3') {
            $("#dept_app_id,#dept_app_secret").closest("tr").show();
            $("#dept_url,#broker_url").closest("tr").show();
            $("#dept_title").closest("tr").show();
        } else {
            $("#dept_app_id,#dept_app_secret").closest("tr").hide();
            $("#dept_url,#broker_url").closest("tr").hide();
            $("#dept_title").closest("tr").hide();

        }
        //去掉校验
        $("#dept_code,#dept_name,#dept_ratio").textbox("enableValidation").textbox("readonly", false);
        // 重置有效
        $("#reset").linkbutton('enable');
        $this.window({
            title: '添加单位'
        });
        $form.attr("action", $form.attr("action-add"));
    } else {
        if (dept_type == '3') {
            $("#is_tj_man,#tj_ratio").closest("tr").show();
            $("#dept_title").closest("tr").hide();
        } else {
            $("#is_tj_man,tj_ratio").closest("tr").hide();
            $("#dept_title").closest("tr").hide();
        }
        if (dept_type == '4') {
            $("#dept_app_id,#dept_app_secret").closest("tr").show();
            $("#dept_url,#broker_url").closest("tr").show();
            $("#dept_title").closest("tr").show();
        } else {
            $("#dept_app_id,#dept_app_secret").closest("tr").hide();
            $("#dept_url,#broker_url").closest("tr").hide();
            $("#dept_title").closest("tr").hide();
        }
        $('#dept_parent_id').val(dept_parentId);
        // 重置无效
        $("#reset").linkbutton('enable');
        $form.attr("action", $form.attr("action-edit"));
        //去掉校验
        $("#dept_code,#dept_name,#dept_ratio").textbox("disableValidation").textbox("readonly");
        $this.form('load', $('#data').treegrid('getSelected'));
        $this.window({
            title: '编辑'
        });
    }

    $this.window({
        top: 0,
        left: ($(window).width() - 595) * 0.5
    });
    $this.window('open');
    $(".panel.window,.window-shadow").css({top: ($(window).height() - $(".panel.window").outerHeight()) * 0.5 + $(document).scrollTop()})
}

//为重置按钮添加单击事件
$("#reset").click(function () {
    if (updateId == undefined) {
        $('#updateform').form('clear');
    } else {
        update(updateId, actions, depttype);
    }
});

var num1, num2, num3, num4;
var name1, name2, name3, name4;
function update1(id, row) {
    var m = getMap();
    var m1 = getMap();
    var $this = $('#update1');
    var $form = $this.find("form");
    //$this.form('load', $('#data').treegrid('getSelected'));
    $.ajax({
        type: "POST",
        url: "getDeptRatioModel",
        data: {
            dept_id: id
        },
        dataType: 'json',
        async: true,
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                m.put(data[i].dept_id, data[i].dept_ratio);
                m1.put(data[i].dept_id, data[i].dept_name);
                num1 = m.get(data[1].dept_id);
                num2 = m.get(data[2].dept_id);
                num3 = m.get(data[3].dept_id);
                num4 = m.get(data[4].dept_id);
                name1 = m1.get(data[1].dept_id);
                name2 = m1.get(data[2].dept_id);
                name3 = m1.get(data[3].dept_id);
                name4 = m1.get(data[4].dept_id);
            }
            $('#ce_name').html(name1 + " <font color=red>" + num1 + "%</font>");
            $("#ch_name").html(name2 + " <font color=red>" + num2 + "%</font>");
            $("#s_name").html(name3 + " <font color=red>" + num3 + "%</font>");
            $("#a_name").html(name4 + " <font color=red>" + num4 + "%</font>");
        }
    });
    $this.window({
        title: '分配比率模板变更'
    });
    $("#dept_ratios").textbox("disableValidation").textbox("readonly");
    $this.window({
        top: ($(window).height() - 381) * 0.5 + $(document).scrollTop(),
        left: ($(window).width() - 588) * 0.5
    });


    $this.form('load', $('#data').treegrid('getSelected'));
    $this.window('open');
};

//应用
$("#yingyong").click(function () {
    var thisData = {"ce_allot": num1, "ch_allot": num2, "s_allot": num3, "dept_ratio": num4};
    $("#update1").form('load', thisData);
});


//重置
$("#reset2").click(function () {
    var $this = $('#update1');
    var $form = $this.find("form");
    $this.form('load', $('#data').treegrid('getSelected'));
});

$("#search").submit(function () {
    var $this = $(this);
    if (!$this.form("validate")) {
        return false;
    }
    ;
    if ($.trim($("#search_dept_id").val()) != "" || $.trim($("#search_dept_name").val()) != "") {
        var param = getFormJson(this);
        loadData(param, 'queryDeptInfoByPa');//查询数据
        return false;
    }
});

$("#updateform1").submit(function () {
    var $this = $(this);
    if (!$this.form("validate")) {
        return false;
    }
    if ($('#s_allot').textbox('getValue') < 40) {
        $.messager.alert('错误信息', "服务商不能小于40%", 'error');
        return false;
    }

    var vv1 = 100 - $('#ce_allot').textbox('getValue') -
        $('#ch_allot').textbox('getValue') -
        $('#s_allot').textbox('getValue') -
        $('#dept_ratios').textbox('getValue');
    //alert($('#ce_allot').textbox('getValue')+$('#ch_allot').textbox('getValue')+$('#s_allot').textbox('getValue')+$('#dept_ratios').textbox('getValue'));
    //alert(vv);
    if (vv1 > 20) {
        $.messager.alert('错误信息', "设置分配比率总数不能小于80%", 'error');
        return false;
    }

    var vv = 100 - $('#ce_allot').textbox('getValue') -
        $('#ch_allot').textbox('getValue') -
        $('#s_allot').textbox('getValue') -
        $('#dept_ratios').textbox('getValue');
    //alert($('#ce_allot').textbox('getValue')+$('#ch_allot').textbox('getValue')+$('#s_allot').textbox('getValue')+$('#dept_ratios').textbox('getValue'));
    //alert(vv);
    if (vv < 0) {
        $.messager.alert('错误信息', "请重新验算分配比率", 'error');
        return false;
    }
});

function loadData1(param) {
    $('#data1').treegrid(
        {
            //title : '部门选择',// 当前页面标题
            fitColumns: true,// 自动列宽
            autoRowHeight: true,// 自动行高度
            checkOnSelect: true, // 只有选择复选框时才选中
            selectOnCheck: false,// 选择行时选中复选框
            singleSelect: true,// 禁用选择多行
            border: !1,// 不显示边框
            striped: true,// 条纹行
            fit: true,// 自适应高度
            nowrap: true,// 单元格内文字禁止换行
            rownumbers: true,// 显示行号
            animate: true,
            collapsible: true,
            url: "queryDeptInfoByPa?dept_type=5",
            idField: 'dept_id',
            treeField: 'dept_name',
            queryParams: param,// 查询参数
            toolbar: '#toolbar1',// 工具栏
            columns: [[
                {
                    width: parseInt($(this).width() * 0.05),
                    field: 'dept_id',
                    align: 'center',
                    title: '部门ID'
                },
                {
                    width: parseInt($(this).width() * 0.2),
                    field: 'dept_name',
                    align: 'center',
                    title: '部门名称'
                }, {
                    width: parseInt($(this).width() * 0.1),
                    field: 'dept_code',
                    align: 'center',
                    title: '部门编码'
                }, {
                    width: parseInt($(this).width() * 0.05),
                    field: 'dept_type',
                    align: 'center',
                    title: '级别',
                    formatter: function (value) {
                        if (value == 0) {
                            return "平台";
                        }
                        if (value == 1) {
                            return "交易中心";
                        }
                        if (value == 2) {
                            return "运行中心";
                        }
                        if (value == 3) {
                            return "服务商";
                        }
                        if (value == 4) {
                            return "代理商";
                        }
                        if (value == 5) {
                            return "部门";
                        }
                    }
                },
                {
                    width: parseInt($(this).width() * 0.05),
                    field: 'is_use',
                    title: '是否有效',
                    align: 'center',
                    formatter: function (value) {
                        if (value == 1) {
                            return "有效";
                        } else if (value == 0) {
                            return "<span class='red'>无效</span>";
                        } else if (value == 4) {
                            return "<span class='red'>已退出</span>";
                        }
                    }
                },
                {
                    width: 100,
                    align: 'center',
                    field: 'op',
                    title: '操作',
                    formatter: function (value, row, index) {
                        var str = '';
                        if (row.is_use != 4) {
                            str += '\<a class="btns" href="javascript:;" onclick="getSelected(\'' + row.dept_id + '\');">转移</a>';
                        }
                        return str;
                    }
                }]],
            onLoadSuccess: function () {
                $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;border-radius:4px;line-height:12px;padding:2px 10px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
                $("#checkdept").attr("checked", false);
            }
        });
}

function deptupdate(id) {
    var $this = $('#deptupdate');
    $this.form('clear');
    var $form = $this.find("form");
    $("#data1").html("");

    //var data = eval("({userid:'"+id+"'})")
    //$this.form('clear');
    $("#odept_id").val(id);
    loadData1();

    $this.window({
        top: ($(window).height() - 450) * 0.5 + $(document).scrollTop(),
        left: ($(window).width() - 700) * 0.5
    });
    //$this.form('clear');
    $this.window('open');
}

//获取查询数据
function getParams() {
    //获取输入框的值
    var values = $("#dept_names").val();
    if (values != '') {
        var rows = $('#data1').treegrid('getData'), rst = [], rst1 = [], rst2 = [];
        for (var i = 0; i < rows.length; i++) {
            if ((rows[i].dept_name).indexOf(values, 0) > -1) {
                rst.push(rows[i]);
            } else {
                rst1.push(rows[i]);
            }
        }
        rst2 = rst.concat(rst1);
        $('#data1').treegrid('loadData', rst2);
        $("tr[id^='datagrid-row-r2-2-'],tr[id^='datagrid-row-r2-1-']").removeClass("datagrid-row-selected");
        for (var i = 0; i < rows.length; i++) {
            if ((rows[i].dept_name).indexOf(values, 0) > -1) {
                $("[node-id='" + rows[i].dept_id + "']").addClass("datagrid-row-selected");

            }
        }
    } else {
        deptupdate($("#odept_id").val());
    }
}

//刷新功能
$("#refresh").click(function () {
    $("#dept_names").textbox('setValue', "");
    deptupdate($("#odept_id").val());
});

function getSelected(id) {
    $.messager.confirm('转移部门', '是否确认需要转移', function (r) {
        if (r) {
            var odept_id = $('#odept_id').val();
            $.ajax({
                url: "saveODeptToNDeptInfo",
                type: "POST",
                dataType: "json",
                data: {
                    ndept_id: id,
                    odept_id: odept_id
                },
                success: function (data) {
                    $.messager.alert('提示信息', data.msg, 'info');
                    if (data.success == true) {
                        $('#deptupdate').dialog('close');
                    }
                }

            });

        }
    });
}

function clearUserWxId(id) {
    $.messager.confirm('微信清空重置', '是否确认重置清空所有用户的微信ID', function (r) {
        if (r) {
            $.ajax({
                url: "clearUserWeiXinInfo",
                type: "POST",
                dataType: "json",
                data: {
                    agent_id: id
                },
                success: function (data) {
                    $.messager.alert('提示信息', data.msg, 'info');
                }
            });
        }
    });
}

