/**
 * 
 */
dictInfo(1013,"use_type");
dictInfo(1022,"entrance");
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
						idField:'tx_order_id',
						url : url,
						remoteSort:false,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						frozenColumns:[[
								{
									width : 150,
									sortable : false,
									field : 'tx_create_date',
									title : '订单创建时间',
									align : 'center',
									formatter:dateTimeFormatter
								},
								{
									width : 150,
									sortable : false,
									field : 'rs_create_date',
									title : '订单到账时间',
									align : 'center',
									formatter:dateTimeFormatter
								},
								{
									width : 100,
									sortable : true,
									field : 'user_name',
									align : 'center',
									title : '用户名'
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
                                    sortable : true,
                                    field : 'tx_receipts',
                                    align : 'right',
                                    title : '实际到账金额'
                                },
								{
									width : 100,
									sortable : false,
									field : 'tx_poundage',
									title : '提现手续费',
									align : 'right'
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
									sortable : false,
									field : 'IS_USE',
									title : '提现状态',
									align : 'center',
									formatter: function(value, row, index){
									    if (value == "1") {
									        return '提现成功';
									    }
									    if (value == "0"){
									        return '提现失败';
									    }
                                        if (value == "3"){
                                            return '无效单';
                                        }
                                        if (value == "4"){
                                            return '人工审核退款单';
                                        }
                                        else {
                                            return '未审核';
                                        }
									}
								},
								{
									width : 100,
									sortable : false,
									field : 'tx_rs_status',
									title : '提现结果',
									align : 'center'								
								},
								{
									width : 100,
									sortable : true,
									field : 'tx_type',
									align : 'center',
									title : '提现类型'

								},
								{
									width : 80,
									sortable : false,
									field : 'entrance',
									title : '提现入口',
									align : 'center'
								}
								] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}

function withdrawalsum(param){
	$.ajax({
		url : "queryWithdrawalAccSum",
		type : "POST",
		data : param,
		success : function(data) {
		    console.log(data);
			if(data == null){
				return ;
			}
			var html = '\
				<span>用户提现汇总：¥ '+fmoney(data[0].TX_MONEYCOUNT,2)+'\</span>\
                <span>实际提现汇总：¥ '+fmoney(data[0].TX_RECEIPTSCOUNT,2)+'\</span>\
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
	loadData(param,"queryReportWithdrawalAccInfo");//查询数据
	withdrawalsum(param);
	return false;
});

function expInfo() {
	window.location.href = 'exportMemberdayReportInfo';
}
loadData();//初始化加载数据