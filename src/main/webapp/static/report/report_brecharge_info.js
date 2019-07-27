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
						idField:'user_name',
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
									field : 'order_money',
									title : '充值金额',
									align : 'center'
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
									sortable : false,
									field : 'dept_name',
									title : '部门名称',
									align : 'center'
								},
								{
									width : 100,
									sortable : true,
									field : 'cz_type',
									align : 'center',
									title : '充值类型',
									formatter: function(value, row, index){
									    if (value == "dinpay") {
									        return '智付';
									    }
									    if (value == "ecpsspay") {
									        return '汇潮';
									    }
									    if (value == "unionpay") {
									        return '银联';
									    }
									    if (value == "wechat") {
									        return '微信';
									    }
									    if (value == "yimadai"){
									        return '一麻袋';
									    }
									    if (value == "swiftpass"){
									        return '威富通';
									    }
									    if (value == "yijipay"){
									        return '易极付';
									    }
									}
								},
								{
									width : 100,
									sortable : true,
									field : 'cz_pc_mobile',
									align : 'center',
									title : '充值渠道',
									formatter: function(value, row, index){
									    if (value == "pc") {
									        return '电脑端';
									    }
									    if (value == "mobile") {
									        return '手机端';
									    }									   
									}
								}
								] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}

function rechargesum(param){
	$.ajax({
		url : "queryBRechargeSum",
		type : "POST",
		data : param,
		success : function(data) {
			if(data == null){
				return ;
			}
			var html = '\
				<span>经纪人充值汇总：¥ '+fmoney(data[0].ORDER_MONEYCOUNT,2)+'\</span>';
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
	loadData(param,"queryReportBRechargeInfo");//查询数据
	rechargesum(param);
	return false;
});

function expInfo() {
	window.location.href = 'exportMemberdayReportInfo';
}
loadData();//初始化加载数据