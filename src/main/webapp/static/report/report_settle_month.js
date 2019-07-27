/**
 * 结算服务商月报表
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
						idField:'SETTLE_ID',
						url : url,
						remoteSort:false,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						columns : [ [
								{
									sortable : true,
									width : 100,
									field : 'statistics_day',
									align : 'center',
									title : '统计日期'
								},
								{
									sortable : false,
									width : 100,
									field : 'settle_name',
									align : 'center',
									title : '服务商名称'
								},
								{
									sortable : false,
									width : 100,
									field : 'recharge_month',
									align : 'right',
									title : '用户日充值总额'
								},
								
								{
									sortable : true,
									width : 100,
									field : 'withdrawals_month',
									align : 'right',
									title : '用户日提现额'
								},
								{
									sortable : true,
									field : 'profit_loss_month',
									align : 'right',
									title : '用户日盈亏额',
									width : 100
								},
								{
									sortable : true,
									field : 'counter_fee_month',
									title : '用户日仓储费',
									align : 'right',
									width : 100
								},								
								{
									sortable : true,
									field : 'balance_month',
									title : '日余额',
									align : 'right',
									width : 100
								},
								{
									sortable : true,
									field : 'bad_month',
									title : '日冲红',
									align : 'right',
									width : 100
								}/*,
								{
									sortable : false,
									field : 'for_year',
									title : '所属年',
									width : parseInt($(this).width() * 0.05)
								},
								{
									sortable : false,
									field : 'for_month',
									title : '所属月',
									width : parseInt($(this).width() * 0.05)
								}*/
								] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}

function sum(param){
	$.ajax({
		url : "querySettleMonthSum",
		type : "POST",
		data : param,
		success : function(data) {
			if(data == null){
				return ;
			}
			var html = '\
				<span>充值汇总：¥ '+fmoney(data[0].recharge_month_count,2)+'\</span>\
				<span>仓储费汇总：¥ '+fmoney(data[0].counter_fee_month_count,2)+'\</span>\
				<span>提现汇总：¥ '+fmoney(data[0].withdrawals_month_count,2)+'\</span>\
				<span>盈亏汇总：¥ '+fmoney(data[0].profit_loss_month_count,2)+'\</span>\
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
	loadData(param,"querySettleMonthReport");//查询数据
	sum(param);
	return false;
});

function expInfo() {
	var start_date=$('#start_date').datebox('getValue');  
	var end_date=$('#end_date').datebox('getValue'); 
	var settle_id=$('#settle_id').combobox('getValue');
	$.ajax({
		url : "querySettleMonthReportForExcel",
		type : "POST",
		data : {
			start_date:start_date,
			end_date:end_date,			
			settle_id:settle_id
			},
			success : function(msg) {
				if(msg.num>0){
					window.location.href= msg.url;
				}else{
					$.messager.alert('提示信息', '导出失败，查询无数据');
				}

			}
	});	
	//window.location.href = 'exportSettleMonthReportInfo';
}
loadData();
