/**
 * 
 */
function loadData(param,url) {
	$('#data').datagrid(
					{
						title : '',// 当前页面标题
						fitColumns : true,// 自动列宽
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
						columns : [ [								
								{
									sortable : true,
									width : 100,
									field : 'STATISTICS_DAY',
									align : 'center',
									title : '统计日期'
								},
								{
									sortable : false,
									width : 100,
									field : 'AGENT_NAME',
									align : 'center',
									title : '代理商名称'
								},								
								{
									sortable : false,
									width : 100,
									field : 'SETTLE_NAME',
									align : 'center',
									title : '服务商名称'
								},
								{
									sortable : true,
									width : 100,
									field : 'RECHARGE_MONTH',
									align : 'right',
									title : '用户月充值额'
								},
								{
									sortable : true,
									width : 100,
									field : 'WITHDREWALS_MONTH',
									align : 'right',
									title : '用户月提现额'
								},
								{
									sortable : true,
									width : 100,
									field : 'PROFIT_LOSS_MONTH',
									title : '用户月盈亏额',
									align : 'right'
								},
								{
									sortable : true,
									width : 100,
									field : 'COUNTER_FEE_MONTH',
									title : '用户月仓储费',
									align : 'right',
								},								
								{
									sortable : true,
									width : 100,
									field : 'BALANCE_MONTH',
									title : '月余额',
									align : 'right'
								},
								{
									sortable : true,
									width : 100,
									field : 'BAD_MONTH',
									title : '月冲红',
									align : 'right'
								}
								] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}

function agentmonthsum(param){
	$.ajax({
		url : "queryAgentMonthSum",
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
	loadData(param,"queryReportAgentMonthInfo");//查询数据
	agentmonthsum(param);
	return false;
});

function expMonthInfo() {	
	var param = getFormJson(this);
	$.ajax({
		url : "exportAgentMonthReportInfo",
		type : "POST",
		data : param,
		success : function(data) {
			if (data.success == true) {
				$.messager.alert('导出提示', data.msg, 'success');
				//window.location.reload();
			} else {
				$.messager.alert('导出提示', data.msg, 'error');
			}
		}
	});
	//window.location.href = 'exportAgentMonthReportInfo';
}
loadData();//初始化加载数据