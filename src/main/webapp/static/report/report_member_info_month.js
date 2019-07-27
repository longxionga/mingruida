/**
 * 
 */
function loadData(param,url) {
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
						border : !1,// 不显示边框
						striped : true,// 条纹行
						fit : true,// 自适应高度
						nowrap : true,// 单元格内文字禁止换行
						rownumbers : true,// 显示行号
						checkOnSelect : false, // 只有选择复选框时才选中
						selectOnCheck : false,// 选择行时选中复选框
						pagination : !0,// 显示分页
						pageSize : 20,// 每页显示数量 参数必须为pageList的对应
						pageList : [ 20,40,80 ],// 每页显示数量设置参数
						idField:'AGENT_ID',
						url : url,
						remoteSort:false,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						frozenColumns:[[
								{
									width : 100,
									sortable : false,
									field : 'USER_NAME',
									title : '用户名',
									align : 'center'
								},
								{
									width : 100,
									sortable : true,
									field : 'STATISTICS_DAY',
									align : 'center',
									title : '统计日期'
								}
				        ]],
						columns : [ [
								{
									width : 100,
									sortable : false,
									field : 'mobile',
									title : '手机号码',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'BROKER_NAME',
									title : '经纪人名称',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'SETTLE_NAME',
									title : '服务商名称',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'AGENT_NAME',
									title : '代理商名称',
									align : 'center'
								},							
								{
									width : 100,
									sortable : false,
									field : 'DEPT_NAME',
									title : '部门名称',
									align : 'center'
								},
								{
									width : 100,
									sortable : true,
									field : 'RECHARGE_MONTH',
									align : 'right',
									title : '月充值额'
								},
								{
									width : 100,
									sortable : true,
									field : 'WITHDRAWALS_MONTH',
									align : 'right',
									title : '月提现额'
								},
								{
									width : 100,
									sortable : true,
									field : 'PROFIT_LOSS_MONTH',
									title : '月盈亏额',
									align : 'right'
								},
								{
									width : 100,
									sortable : true,
									field : 'COUNTER_FEE_MONTH',
									title : '月仓储费',
									align : 'right'
								},								
								{
									width : 100,
									sortable : true,
									field : 'BALANCE_MONTH',
									title : '月余额',
									align : 'right'
								},
								{
									width : 100,
									sortable : true,
									field : 'BAD_MONTH',
									title : '月冲红',
									align : 'right'
								}
								] ],
						onLoadSuccess : function() {
							}
					});
}

function membermonthsum(param){
	$.ajax({
		url : "queryMemberMonthSum",
		type : "POST",
		data : param,
		success : function(data) {
			if(data == null){
				return ;
			}
			var html = '\
				<span>月充值汇总：¥ '+fmoney(data[0].RECHARGE_MONTHCOUNT,2)+'\</span>\
				<span>月提现汇总：¥ '+fmoney(data[0].WITHDRAWALS_MONTHCOUNT,2)+'\</span>\
				<span>月盈亏汇总：¥ '+fmoney(data[0].PROFIT_LOSS_MONTHCOUNT,2)+'\</span>\
				<span>月仓储汇总：¥ '+fmoney(data[0].COUNTER_FEE_MONTHCOUNT,2)+'\</span>\
				';
			$("#total").html(html);
		}
	});
}

$("#search").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param,"queryReportMemberMonthInfo");//查询数据
	membermonthsum(param);
	return false;
});


function onSelect(date){  //开始日期选择时触发  
	
    //$('#enddate').datebox('enable');    //启用结束日期控件  
    //$('#enddate').datebox('reset')      //重置结束日期的值 
};  

function expInfo() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	$.ajax({
		url : "exportMemberMonthReportInfo",
		type : "POST",
		data : param,
		success : function(data) {
			if (data.success == true) {
				window.location.reload();
			} else {
				$.messager.alert('导出提示', data.msg, 'error');
			}
		}
	});
	
	
	//window.location.href = 'exportMemberMonthReportInfo';
}
loadData();//初始化加载数据