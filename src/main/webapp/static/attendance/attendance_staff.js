
function loadData(param) {
    $('#data').datagrid({
        title: '',//当前页面标题
        fitColumns : true,// 自动列宽
        autoRowHeight : true,// 自动行高度
        singleSelect : true,// 禁用选择多行
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
        url: "querystaffworkattendanceinfo",
        queryParams: param,//查询参数
        toolbar: '#toolbar',//工具栏
        columns: [[
            {

                width: 30,
                field: 'id',
                title: '编号'
            },
            {
                width: 200,
                field: 'attendance_time',
                title: '考勤月份'
            },
            {
                width: 200,
                field: 'day_count',
                title: '全勤天数'
            },
            {
                width: 80,
                field: 'attendance_status',
                title: '考勤查询可用',
                align : 'center',
                formatter: function (value, row, index) {
                    if (value == '01')
                        return '是';
                    else
                        return '<span style="color:#f00;">否</span>';
                }
            },
            {
                width: 150,
                field: 'create_time',
                title: '创建时间',
                formatter:dateTimeFormatter
            },
            {
                width: 150,
                field: 'update_time',
                title: '修改时间',
                formatter:dateTimeFormatter
            },
            {
                width: 260,
                field: 'op',
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    var str = '\
		        		      <a class="btns" href="javascript:upFiles(\'' + row.id + '\');">上传考勤明细</a>\
		        			   <a class="btns" href="javascript:update(\'' + index + '\');">编辑</a>\
		        			   <a class="btns" href="javascript:off(\'' + index + '\');">删除考勤明细</a>\
		        			   ';
                    return str;
                }
            }
        ]],
        onLoadSuccess: function () {
            $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
        }
    });
}

// //删除方法
// function del(id) {
// 	$.messager.confirm('确认删除', '确定要删除吗?', function (r) {
// 		if (r) {
// 			$.ajax({
// 				url: "delBankRule",
// 				type: "POST",
// 				data : {sys_id:id},
// 				success: function (data) {
// 					if (data.success == true) {
// 						$.messager.alert('提示信息', data.msg, 'info');
// 						$('#data').datagrid('reload');
// 					} else {
// 						$.messager.alert('错误信息', data.msg, 'error');
// 					}
// 				}
// 			});
// 		}
// 	});
// }

//添加/修改方法
var updateId;
function update(id) {
    console.log(id);
    var $this = $('#update');
    var $form = $this.find("form");
    updateId=id;
    if (updateId == undefined) {
        $('#updateform').form('clear');
        $("#resetBtn").linkbutton('enable');
        $this.window({ title: '添加' });
        $form.attr("action", $form.attr("action-add"));
    } else {
        $("#resetBtn").linkbutton('disable');
        $this.window({ title: '编辑' });
        $form.attr("action", $form.attr("action-edit"));
        // $("#id").val($('#data').datagrid('getData').rows[id].id);
        $this.form('clear');
        $this.form('load', $('#data').datagrid('getData').rows[id]);
    }


    $this.window({
        top:($(window).height() - 450) * 0.5+$(document).scrollTop(),
        left:($(window).width() - 680) * 0.5
    });
    $this.window('open');
}

// 为重置按钮添加单击事件 
$("#reset").click(function(){
    if (updateId == undefined) {
        $('#updateform').form('clear');
    }else{
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
loadData();//初始化加载数据



$('#attendance_status').combobox({
    width : '150',
    height : '30',
    valueField : 'label',
    textField : 'value',
    required : 'true',
    panelHeight : 'auto',
    editable: false,
    data : [ {
        label : '01',
        value : '是'
    }, {
        label : '02',
        value : '否'
    } ]
});

function up(){
    $("#updateform").submit();
    loadData();//初始化加载数据
}


function off(id) {
    $.messager.confirm('删除考勤数据', '是否删除考勤数据', function(r) {
        if (r) {
            var ids = $('#data').datagrid('getData').rows[id].id;
            // var name = $('#data').datagrid('getData').rows[id].name;
            $.ajax({
                url : "deletestaffworkattendancedetail",
                type : "POST",
                dataType : "json",
                data : {
                    attendanceid : ids
                },
                success : function(data) {
                    $.messager.alert('提示信息', data.msg, 'info');
                }
            });
            loadData();// 重新加载
        }
    });
}
$('#brand_id').combobox({
    url : '../platform/queryDictBrandRule',
    valueField : 'id',
    textField : 'name',
    width : '150',
    height : '30',
    editable : false,
    panelHeight : '200'
});
