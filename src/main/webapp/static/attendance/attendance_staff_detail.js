
function loadData(param) {
	var boolFitColumns=true;
	if($(document).width()<=1600-220){
		boolFitColumns=false;
	}
	$('#data').datagrid(
		{
			title : '',// 当前页面标题
			fitColumns : false,// 自动列宽
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
			url : 'queryStaffWorkAttendanceDetailPageList',
			remoteSort:false,
			queryParams : param,// 查询参数
			toolbar : '#toolbar',// 工具栏
			columns : [ [
				{
					width: 80,
					sortable : true,
					field : 'brandid',
					title : '品牌'
				},
				{
					width: 80,
					sortable : true,
					field : 'branch',
					title : '分公司'
				},
				{
					width: 80,
					sortable : true,
					field : 'managername',
					title : '经理'
				},
				{
					width: 80,
					sortable : true,
					field : 'pos',
					title : '岗位'
				},
				{
					width: 80,
					sortable : true,
					field : 'staff_name',
					title : '员工姓名'
				},
				{
					width: 80,
					sortable : true,
					field : 'day_count',
					title : '全勤天数'
				},
				{
					width: 100,
					sortable : true,
					field : 'attendance_time',
					title : '考勤月份'
				},
				{
					width: 100,
					sortable : true,
					field : 'entry_time',
                    formatter : dateTimeFormatter2,
					title : '入职时间'
				},
				{
					width: 100,
					sortable : true,
					field : 'departure_time',
                    formatter : dateTimeFormatter2,
					title : '离职时间'
				},
				{
					width: 100,
					sortable : true,
					field : 'jxcsrq',
					title : '计薪初始日期'

				},
				{
					width: 100,
					sortable : true,
					field : 'jxjsrq',
					title : '计薪结束日期'
				},
				{
					width: 100,
					sortable : true,
					field : 'dxts',
					title : '员工底薪应计天数'
				},
				{
					width: 100,
					sortable : true,
					field : 'ygqqts',
					title : '计薪期间员工全勤应有工作日'

				},
				{
					width: 100,
					sortable : true,
					field : 'ygsjts',
					title : '员工实际工作日'
				},

				{
					width: 100,
					sortable : true,
					field : 'sjdays',
					title : '事假'
				},
				{
					width: 100,
					sortable : true,
					field : 'bjdays',
					title : '病假'
				},
				{
					width: 100,
					sortable : true,
					field : 'qqsum',
					title : '实际应得全勤'
				},
				{
					width: 100,
					sortable : true,
					field : 'cdkk',
					title : '迟到扣款'
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

$('#brindnameid').combobox({
	url : '../platform/queryDictBrandRule',
	valueField : 'id',
	textField : 'name',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'
});



