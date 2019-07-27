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
									field : 'SETTLE_NAME',
									align : 'center',
									title : '服务商'
								},
								{
									sortable : false,
									width : 100,
									field : 'AGENT_NAME',
									align : 'center',
									title : '代理商'
								},								
								{
									sortable : true,
									width : 100,
									field : 'RECHARGE_DAY',
									align : 'right',
									title : '用户日充值额'
								},
								{
									sortable : true,
									width : 100,
									field : 'WITHDREWALS_DAY',
									align : 'right',
									title : '用户日提现额'
								},
								{
									sortable : true,
									field : 'PROFIT_LOSS_DAY',
									title : '用户日盈亏额',
									align : 'right',
									width : 100
								},
								{
									sortable : true,
									field : 'COUNTER_FEE_DAY',
									title : '用户日仓储费',
									align : 'right',
									width : 100
								},								
								{
									sortable : true,
									field : 'BALANCE_DAY',
									title : '日余额',
									align : 'right',
									width : 100
								},
								{
									sortable : true,
									field : 'BAD_DAY',
									title : '日冲红',
									align : 'right',
									width : 100
								}								
								] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}

function agentdaysum(param){
	$.ajax({
		url : "queryAgentDaySum",
		type : "POST",
		data : param,
		success : function(data) {
			if(data == null){
				return ;
			}
			var html = '\
				<span>日充值汇总：¥ '+fmoney(data[0].RECHARGE_DAYCOUNT,2)+'\</span>\
				<span>日提现汇总：¥ '+fmoney(data[0].WITHDRAWALS_DAYCOUNT,2)+'\</span>\
				<span>日盈亏汇总：¥ '+fmoney(data[0].PROFIT_LOSS_DAYCOUNT,2)+'\</span>\
				<span>日仓储汇总：¥ '+fmoney(data[0].COUNTER_FEE_DAYCOUNT,2)+'\</span>\
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
	loadData(param,"queryReportAgentDayInfo");//查询数据
	agentdaysum(param);
	return false;
});

function expInfo() {
	//var arrayData = $("#search").serialize();
	window.location.href = 'exportAgentdayReportInfo';
}
loadData();//初始化加载数据