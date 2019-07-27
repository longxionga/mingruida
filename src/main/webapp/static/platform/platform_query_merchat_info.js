
function loadData(param) {
	var boolFitColumns=true;
	if($(document).width()<=1600-220){
		boolFitColumns=false;
	}
	$('#data').datagrid(
		{
			title : '',// 当前页面标题
			fitColumns : boolFitColumns,// 自动列宽
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
			url : 'queryMerchantInfoPageLists',
			remoteSort:false,
			queryParams : param,// 查询参数
			toolbar : '#toolbar',// 工具栏
			columns : [ [
				{
					width: 80,
					sortable : true,
					field : 'brand_name',
					title : '品牌'
				},
				{
					width: 80,
					sortable : true,
					field : 'merchant_code',
					title : '商户编号'
				},
				{
					width: 100,
					sortable : true,
					field : 'merchant_name',
					title : '商户名称'
				},
				{
					width: 80,
					sortable : true,
					field : 'bind_day',
					title : '入网时间'
				},
				{
					width: 100,
					sortable : true,
					field : 'staffcode',
					title : '归属员工编号'
				},
				{
					width: 150,
					sortable : true,
					field : 'staffname',
					title : '归属员工名称'
				},
				{
					width: 100,
					sortable : true,
					field : 'merchant_type',
					title : '商户类型'
				},
				{
					width: 100,
					sortable : true,
					field : 'merchant_state',
					title : '商户状态'
				},


				{
					width: 100,
					sortable : true,
					field : 'machine_type',
					title : '机具类型'
				},
				{
					width: 100,
					sortable : true,
					field : 'audit_status',
					title : '审核状态'
				},
				{
					width: 100,
					sortable : true,
					field : 'rate_type',
					title : '费率类型'
				},
				{
					width: 120,
					align: 'center',
					sortable : true,
					field : 'create_time',
					title : '创建时间',
					formatter: dateTimeFormatter
				},
				{
					width: 120,
					align: 'center',
					sortable : true,
					field : 'update_time',
					title : '更新时间',
					formatter: dateTimeFormatter
				}

			] ],
			onLoadSuccess : function() {
				$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
				$(".datagrid-body td[field='gzp_buy_no_time']").overTitle();
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

$('#brand_id').combobox({
	url : '../platform/queryDictBrandRule',
	valueField : 'id',
	textField : 'name',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'
});

$('#audit_status').combobox({
	url : '../platform/getMerchantAuditStartsCombobox',
	valueField : 'id',
	textField : 'text',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'
});

//商户信息导出
function exportExcel() {
	$.messager.confirm('导入Excel', '是否导出索检到的数据？', function(r) {
		if (r) {
			var brand_id=$('#brand_id').combobox('getValue');//获取combobox的value方法

			var merchant_name=document.getElementById("merchant_name").value;
			var merchant_code=document.getElementById("merchant_code").value;

			var machine_code=document.getElementById("machine_code").value;

			var staffname=document.getElementById("staffname").value;

			var audit_status=$('#audit_status').combobox('getValue');//获取combobox的value方法

			var skssj=$('#skssj').datebox('getValue');//获取combobox的value方法
			var sjssj=$('#sjssj').datebox('getValue');//获取combobox的value方法
			$.ajax({
				url : "queryMerchantInfoReportForExcel",
				type : "POST",
				dataType : "json",
				cache:false,
				data : {
					brand_id:brand_id,
					merchant_name:merchant_name,
					merchant_code:merchant_code,
					machine_code:machine_code,
					staffname:staffname,
					audit_status:audit_status,
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