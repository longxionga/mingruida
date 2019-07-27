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
						idField:'tx_order_id',
						url : url,
						remoteSort:false,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						frozenColumns:[[
								{
									width : 150,
									sortable : false,
									field : 'create_date',
									title : '创建时间',
									align : 'center',
									formatter:dateTimeFormatter
								},
								{
									width : 100,
									sortable : false,
									field : 'broker_name',
									title : '经纪人名称',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'mobile',
									title : '手机号',
									align : 'center'
								}
				        ]],
						columns : [ [
								{
									width : 100,
									sortable : false,
									field : 'tx_order_id',
									title : '订单号',
									align : 'center'
								},
								{
									width : 100,
									sortable : true,
									field : 'tx_money',
									align : 'right',
									title : '提现金额'
								},
								{
									width : 100,
									sortable : false,
									field : 'settle_name',
									title : '服务商名称',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'agent_name',
									title : '代理商名称',
									align : 'center'
								},
								{
									width : 100,
									sortable : true,
									field : 'tx_type',
									align : 'center',
									title : '提现类型',
									formatter: function(value, row, index){
									    if (value == "wechat") {
									        return '微支付';
									    }
									    if (value == "yimadai"){
									        return '一麻袋';
									    }
									    else {
									        return '其他';
									    }
									}
								}
								] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}

function withdrawalsum(param){
	$.ajax({
		url : "queryBWithdrawalSum",
		type : "POST",
		data : param,
		success : function(data) {
			if(data == null){
				return ;
			}
			var html = '\
				<span>经纪人提现汇总：¥ '+fmoney(data[0].TX_MONEYCOUNT,2)+'\</span>';
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
	loadData(param,"queryReportBWithdrawalInfo");//查询数据
	withdrawalsum(param);
	return false;
});

function expInfo() {
	window.location.href = 'exportMemberdayReportInfo';
}
loadData();//初始化加载数据