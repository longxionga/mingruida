
function loadData(param) {
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
			border : !0,// 不显示边框
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
			url : 'getMachineInfoPageLists',
			remoteSort:false,
			queryParams : param,// 查询参数
			toolbar : '#toolbar',// 工具栏
			columns : [ [
				{
					width: 60,
					sortable : true,
					field : 'brindname',
					title : '所属品牌'
				},
				{
					width: 80,
					sortable : true,
					field : 'batchcode',
					title : '批次号'
				},
				{
					width: 80,
					sortable : true,
					field : 'machineSN',
					title : '机具编号'
				},
				{
					width: 80,
					sortable : true,
					field : 'machinecode',
					title : '机具序列号'
				},

				{
					width: 100,
					sortable : true,
					field : 'staffcode',
					title : '归属员工编号'

				},
				{
					width: 120,
					sortable : true,
					field : 'bystages',
					title : '归属员工名称'

				},
				{
					width: 100,
					sortable : true,
					field : 'merchantcode',
					title : '商户编号'

				},
				{
					width: 120,
					sortable : true,
					field : 'merchantname',
					title : '商户名称'

				},
				{
					width: 50,
					sortable : true,
					field : 'isbound',
					title : '是否绑定',
					formatter : function(value) {
						if(value==1){
							return "已绑定";
						}else if(value==2){
							return "未绑定";
						}else if(value==3){
							return "已分配服务商";
						}else {
							return "";
						}
					}
				},
				{
					width: 50,
					sortable : true,
					field : 'machinetype',
					title : '机具类型'
				},

				{
					width: 80,
					sortable : true,
					field : 'ifdeposit',
					title : '是否已缴纳押金'

				},

				{
					width: 180,
					field : 'op',
					title : '操作',
					align : 'center',
					formatter : function(value, row, index) {
						var str = '\
							<a class="btns" href="javascript:" onclick="machineCallbackSingle(\''+ row.id+ '\');">机具回调</a>\
									';
						return str;
					}
				}
			] ],
			onLoadSuccess : function() {
				$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
			}
		});
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

//为重置按钮添加单击事件
$("#reset2").click(function(){
	if (updateId == undefined) {
		$('#updateformhd').form('clear');
		var thisData = {"is_use":1};
		$('#updatehd').form('load', thisData);
	}else{
		update(updateId);
	}
});

//机具调拨
function allocation() {
    $("#resetBtn").linkbutton('enable');
	var $this = $('#update');
	var $form = $this.find("form");
	$('#updateform').form('clear');
	$this.window({ title: '机具调拨' });
	$form.attr("action", $form.attr("action-add"));
    $this.form('clear');
	$('#allocation_mode').combobox('setValue','3');
	$this.window({
		top:($(window).height() - 450) * 0.5+$(document).scrollTop(),
		left:($(window).width() - 680) * 0.5
	});
	$this.window('open');
}

//机具机具信息导出
function exportExcel() {
	$.messager.confirm('导入Excel', '是否导出索检到的数据？', function(r) {
		if (r) {
            var batchcode=$("#batchcode").textbox("getValue");
			// var machineSN=document.getElementById("machineSN").value;
			var machinecode=document.getElementById("machinecode").value;
			var staffname111=document.getElementById("staffname111").value;
			var isbound=$('#isbound').combobox('getValue');//获取combobox的value方法

			$.ajax({
				url : "queryMachineReportForExcel",
				type : "POST",
				dataType : "json",
				cache:false,
				data : {
					batchcode:batchcode,
					machinecode:machinecode,
					staffname:staffname111,
					isbound:isbound
				},
				beforeSend: function () {
					$.messager.progress({
						title:'请等待',
						msg:'正在导出...',
						text:'努力中...',
						interval:'600'
					});

				},

				complete: function () {
					$.messager.progress('close');
				},
				success : function(msg) {
					if(msg.num>0){
						window.location.href= msg.url;
					}else{
						$.messager.alert('提示信息', '导出失败，查询无数据');
					}
				}
			});
		}
	});
}
/*
function expInfo() {
	var broker_name=document.getElementById("broker_name").value;
	var start_date=$('#start_date').datebox('getValue');
	var end_date=$('#end_date').datebox('getValue');
	var broker_mobile=document.getElementById("broker_mobile").value;
	var settle_id=$('#settle_id').combobox('getValue');//获取combobox的value方法
	var agent_id=$('#agent_id').combobox('getValue');
	var DID=$('#DID').combobox('getValue');
	var broker_parent_name=document.getElementById("broker_parent_name").value;
	$.ajax({
		url : "queryBrokerDayReportForExcel",
		type : "POST",
		data : {
			broker_name:broker_name,
			start_date:start_date,
			end_date:end_date,
			broker_mobile:broker_mobile,
			settle_id:settle_id,
			agent_id:agent_id,
			DID:DID,
			broker_parent_name:broker_parent_name
		},
		success : function(msg) {
			if(msg.num>0){
				window.location.href= msg.url;
			}else{
				$.messager.alert('提示信息', '导出失败，查询无数据');
			}

		}
	});
}*/

function machineCallbackSingle(id){
	$.messager.confirm('机具回调', '是否机具回调', function(r) {
		if (r) {
			$.ajax({
				url : "machineCallbackSingle",
				type : "POST",
				dataType : "json",
				cache:false,
				data : {
					machine_id:id
				},
				beforeSend: function () {
					$.messager.progress({
						title:'请等待',
						msg:'正在回调...',
						text:'努力中...',
						interval:'600'
					});

				},

				complete: function () {
					$.messager.progress('close');
				},
				success : function(data) {
					$.messager.alert('提示信息', data.msg, 'info');
					$('#search').submit();
				}
			});

			// $.messager.progress({
			// 	title:'请等待',
			// 	msg:'正在回调...',
			// 	timeout:10
			// });
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
loadData();//初始化加载数据

$('#brindnameid').combobox({
	url : '../platform/queryDictBrandRule',
	valueField : 'id',
	textField : 'name',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'
});

$("#search1").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);

	loadAgentDept(param);//查询数据
	return false;
});

//调拨方式
$('#allocation_mode').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '3',
		value : '机具区间',
		'selected':true
	},{
		label : '2',
		value : '机具序列号',

	} ]

});

//调拨方式
$('#allocation_mode2').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '1',
		value : '机具批次号',
		selected:true
	},{
		label : '2',
		value : '机具序列号'
	} ]

});

//是否绑定
$('#isbound').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '',
		value : '全选',
		selected:true
	},{
		label : '1',
		value : '已绑定'
	},{
		label : '2',
		value : '未绑定'
	},{
		label : '3',
		value : '已分配服务商'
	} ]

});
//下下级员工下拉框
$(function(){
    $.ajax({
        url : "../platform/querySubStaffForCombox",
        success : function(data) {
            $("#substaff_id").combobox("loadData", data);
        }
    });

});

function up(){
    $("#updateform").submit();
    loadData();//初始化加载数据

}


function hdup(){
	$("#updateformhd").submit();
	loadData();//初始化加载数据

}

