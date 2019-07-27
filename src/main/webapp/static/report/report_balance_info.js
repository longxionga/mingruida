function loadData(param, url) {
	var  columns  =  [
	        {
			    field : 'user_name',
				title : '用户名',
				width:50
	    	},
			{
	    		align: 'center',
				field : 'mobile',
				title : '手机号',
				width:80
			},
			{
				field : 'user_money',
				title : '余额',
				align : 'right',
				width:50
			},
			{
				field : 'create_date',
				title : '创建时间',
				formatter : dateTimeFormatter,
				width:80
			},
			{
				field : 'dept_name',
				title : '所属部门',
				width:100
			},
			{
				field : 'agent_name',
				title : '代理',
				width:100
			},
			{
				field : 'broker_name',
				title : '位属经纪人',
				width:100
			},
			{
				field : 'settle_name',
				title : '服务商',
				width:80
			},
			{
				field : 'is_use',
				title : '是否可用',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 1)
						return '是';
					else if(value==0)
						return '<span style="color:#f00;">否</span>';
					else
						return '<span style="color:#f00;">已注销</span>';
				},
				width:50
			}];
	
	columns = [columns];	
	$('#data')
			.datagrid(
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
						pageList : [ 20, 40, 80 ],// 每页显示数量设置参数
						url : url,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						columns :columns,
						onLoadSuccess : function() {
							$("head")
									.append(
											"<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
	sum(param);
}

$("#search").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param, "queryUserBalance");// 查询数据
	sum(param);
	return false;
});


loadData('','queryUserBalance');//初始化加载数据
//提现方式
$('#is_useb').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: true,
	data : [ {
		label : '1',
		value : '是'
	},{
		label : '0',
		value : '否'
	},{
		label : '2',
		value : '已注销'
	} ]
});


function sum(param){
	$.ajax({
		url : "queryUserBalanceSum",
		type : "POST",
		data : param,
		success : function(data) {
			if(data == null){
				return ;
			}
			var html = '\
				<span>用户总余额：¥ '+fmoney(data.balance_count,2)+'\</span>\
				';
			$("#total").html(html);
		}
	});
}