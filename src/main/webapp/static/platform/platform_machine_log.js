
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
			url : 'queryMachineLogPageList',
			remoteSort:false,
			queryParams : param,// 查询参数
			toolbar : '#toolbar',// 工具栏
			columns : [ [
				{
					width: 60,
					sortable : true,
					field : 'brand_name',
					title : '机具品牌'
				},
				{
					width: 60,
					sortable : true,
					field : 'create_time',
					title : '调拨时间',
					formatter: dateTimeFormatter
				},

				{
					width: 80,
					sortable : true,
					field : 'operate_mode',
					title : '操作方式',
					formatter : function(value) {
						if(value==1){
							return "调拨";
						}else if(value==2){
							return "回调";
						}else if(value==3){
							return "转移";
						}else if(value==9){
							return '<span style="color:#f00;">其他</span>';
						}
					}
				},
				{
					width: 80,
					sortable : true,
					field : 'allocation_mode',
					title : '调拨方式',
					formatter : function(value) {
						if(value==1){
							return "机具批次号";
						}else if(value==2){
							return "机具序列号";
						}else if(value==3){
							return "机具区间";
						}else if(value==9){
							return '<span style="color:#f00;">其他</span>';
						}
					}

				},

				{
					width: 100,
					sortable : true,
					field : 'machine_code',
					title : '机具编号'

				},
				{
					width: 120,
					sortable : true,
					field : 'before_code',
					title : '操作前归属员工编号'

				},
				{
					width: 120,
					sortable : true,
					field : 'before_name',
					title : '操作前归属员工名称'

				},
				{
					width: 100,
					sortable : true,
					field : 'after_code',
					title : '操作后归属员工编号'

				},
				{
					width: 100,
					sortable : true,
					field : 'after_name',
					title : '操作后归属员工名称'

				},
                {
                    width: 80,
                    sortable : true,
                    field : 'user_name',
                    title : '操作人'
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
loadData({order_type:queryDescOrAsc.create_date});//初始化加载数据
