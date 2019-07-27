
function loadData(param) {
	var boolFitColumns=true;
	if($(document).width()<=1600-220){
		boolFitColumns=false;
	}
	$('#data').datagrid(
		{
			title : '',// 当前页面标题
			fitColumns : true,// 自动列宽
			autoRowHeight : false,// 自动行高度
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
			url : 'queryManageTransactionPageList',
			remoteSort:false,
			queryParams : param,// 查询参数
			toolbar : '#toolbar',// 工具栏
			columns : [ [
				{
					width: 80,
					sortable : true,
					field : 'brandname',
					title : '机具品牌'
				},
				{
					width: 80,
					sortable : true,
					field : 'dept_name',
					title : '所属部门'
				},
				{
					width: 80,
					sortable : true,
					field : 'staffname',
					title : '员工名称'
				},

				{
					width: 100,
					sortable : true,
					field : 'totalsum',
					title : '交易总金额'
				},
				{
					width: 100,
					sortable : true,
					field : 'daijikasum',
					title : '贷记卡'
				},
				{
					width: 100,
					sortable : true,
					field : 'zhundaijikasum',
					title : '准贷记卡'
				},
				{
					width: 100,
					sortable : true,
					field : 'jijikasum',
					title : '借记卡'
				},
				{
					width: 100,
					sortable : true,
					field : 'zhifubaosum',
					title : '支付宝'
				},
				{
					width: 100,
					sortable : true,
					field : 'weixinsum',
					title : '微信支付'
				},
				{
					width: 100,
					sortable : true,
					field : 'yinliansum',
					title : '银联二维码'
				},
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

$("#search").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param);//查询数据
	return false;
});

$('#brind_id').combobox({
	url : '../platform/queryDictBrandRule',
	valueField : 'id',
	textField : 'name',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'
});

$('#sept_id').combobox({
	url : "../platform/queryStaffDeptInfoForCombox",
	valueField : 'id',
	textField : 'text',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'
});

//商户达标信息导出
function exportExcel() {
	$.messager.confirm('导入Excel', '是否导出索检到的数据？', function(r) {
		if (r) {
			var brind_id=$('#brind_id').combobox('getValue');//获取combobox的value方法
			var sept_id=$('#sept_id').combobox('getValue');//获取combobox的value方法
			var position_yg=$('#position_yg').combobox('getValue');//获取combobox的value方法
			var staffname=document.getElementById("staffname").value;

			var bufstate=$('#bufstate').combobox('getValue');//获取combobox的value方法
			var skssj=$('#skssj').datebox('getValue');//获取combobox的value方法
			var sjssj=$('#sjssj').datebox('getValue');//获取combobox的value方法

			$.ajax({
				url : "queryManageTradeReportForExcel",
				type : "POST",
				dataType : "json",
				cache:false,
				data : {
					brindid:sept_id,
					branch:sept_id,
					position:position_yg,
					staffname:staffname,
					bufstate:bufstate,
					start_date:skssj,
					end_date:sjssj
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

//是否包含下级
$('#bufstate').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '1',
		value : '包含'
	},{
		label : '2',
		value : '不包含',
		selected:true
	} ]

});