
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
			url : 'queryStaffWagesPageList',
			remoteSort:false,
			queryParams : param,// 查询参数
			toolbar : '#toolbar',// 工具栏
			columns : [ [
				{
					width: 80,
					sortable : true,
					field : 'staffname',
					title : '姓名'
				},
				{
					width: 80,
					sortable : true,
					field : 'branch',
					title : '公司'
				},
				{
					width: 100,
					sortable : true,
					field : 'jjkkjzf',
					title : '贷记卡快捷支付'
				},
				{
					width: 100,
					sortable : true,
					field : 'kjzffl',
					title : '提成标准'
				},
				{
					width: 100,
					sortable : true,
					field : 'kjzftc',
					title : '流水提成'
				},
				{
					width: 100,
					sortable : true,
					field : 'jjkxkzf',
					title : '贷记卡刷卡支付'

				},
				{
					width: 100,
					sortable : true,
					field : 'skzffl',
					title : '提成标准'
				},
				{
					width: 100,
					sortable : true,
					field : 'skzftc',
					title : '流水提成'
				},
				{
					width: 100,
					sortable : true,
					field : 'hj',
					title : '流水提成合计'

				},
				{
					width: 100,
					sortable : true,
					field : 'dksd',
					title : '代扣税点'
				},
				{
					width: 100,
					sortable : true,
					field : 'shtc',
					title : '税后提成'
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

$("#search").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param);//查询数据
	return false;
});

$('#brindnameid').combobox({
	url : '../platform/queryDictBrandRule',
	valueField : 'id',
	textField : 'name',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'
});

$('#company_id').combobox({
	url : '../platform/querystaffCompanyInfo',
	valueField : 'id',
	textField : 'branchname',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'
});

