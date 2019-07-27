/**
 * 经纪人月报表
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
						idField:'broker_id',
						url : url,
						remoteSort:false,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						frozenColumns:[[
								{
									width : 100,
									sortable : true,
									field : 'statistics_day',
									align : 'center',
									title : '统计日期'
								},
								{
									width : 100,
									sortable : false,
									field : 'broker_name',
									title : '经纪人名称',
									align : 'center'
								}
				        ]],
						columns : [ [
								{
									width : 100,
									sortable : false,
									field : 'mobile',
									title : '经纪人手机',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'settle_name',
									title : '位属服务商',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'agent_name',
									title : '位属代理商',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'dept_name',
									title : '位属部门',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'broker_parent_name',
									title : '位属经纪人',
									align : 'center'
								},
								{
									width : 100,
									sortable : true,
									field : 'recharge_month',
									title : '用户月充值额',
									align : 'right'
								},
								{
									width : 100,
									sortable : true,
									field : 'withdrawals_month',
									title : '用户月提现额',
									align : 'right'
								},
								{
									width : 100,
									sortable : true,
									field : 'profit_loss_month',
									title : '用户月盈亏额',
									align : 'right'
								},
								{
									width : 100,
									sortable : true,
									field : 'counter_fee_month',
									title : '用户月仓储费',
									align : 'right'
								},								
								{
									width : 100,
									sortable : true,
									field : 'balance_month',
									title : '月余额',
									align : 'right'
								},
								{
									width : 100,
									sortable : true,
									field : 'r_counter_fee_month',
									title : '推荐人仓储费',
									align : 'right'
								},
								{
									width : 100,
									sortable : true,
									field : 'bad_day',
									title : '月冲红',
									align : 'right'
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
								},
								{
									sortable : false,
									field : 'for_day',
									title : '所属日',
									align : 'center',
									width : parseInt($(this).width() * 0.05)									
								},
								{
									sortable : true,
									field : 'for_week',
									title : '所属周',
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
		url : "queryBrokerMonthSum",
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
				<span>推荐人仓储费汇总：¥ '+fmoney(data[0].r_counter_fee_month_count,2)+'\</span>\
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
	loadData(param,"queryBrokerMonthReport");//查询数据
	sum(param);
	return false;
});

function expInfo() {
	var broker_name=document.getElementById("broker_name").value;
	var start_date=$('#start_date').datebox('getValue');  
	var end_date=$('#end_date').datebox('getValue');  
	var broker_mobile=document.getElementById("broker_mobile").value;
	var settle_id=$('#settle_id').combobox('getValue');//获取combobox的value方法
	var agent_id=$('#agent_id').combobox('getValue');
	var DID=$('#DID').combobox('getValue');
	var broker_parent_name=document.getElementById("broker_parent_name").value;
	$.ajax({
		url : "queryBrokerMonthReportForExcel",
		type : "POST",
		data : {broker_name:broker_name,
			start_date:start_date,
			end_date:end_date,
			broker_mobile:broker_mobile,
			settle_id:settle_id,
			agent_id:agent_id,
			DID:DID,
			broker_parent_name:broker_parent_name},
			success : function(msg) {
				if(msg.num>0){
					window.location.href= msg.url;
				}else{
					$.messager.alert('提示信息', '导出失败，查询无数据');
				}

			}
	});		
}
loadData();