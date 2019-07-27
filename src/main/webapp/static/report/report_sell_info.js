/**
 * 
 */
dictInfo(1012,"trade_type");
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
						idField:'id',
						url : url,
						remoteSort:false,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						frozenColumns:[[
								{
									width : 100,
									sortable : true,									
									field : 'user_name',
									align : 'center',
									title : '用户名称'
								},
								{
									width : 100,
									sortable : false,								
									field : 'user_mobile',
									align : 'center',
									title : '手机号'
								},							
								{
									width : 100,
									sortable : false,								
									field : 'buy_itemname',
									align : 'center',
									title : '品种'
								},
								{
									width : 100,
									sortable : true,								
									field : 'buy_time',
									align : 'center',
									title : '购赊时间',
									formatter:dateTimeFormatter									
								},
								{
									width : 100,
									sortable : true,								
									field : 'sell_time',
									align : 'center',
									title : '退货时间',
									formatter:dateTimeFormatter									
								}
		        		]],
						columns : [ [
								{
									width : 100,
									sortable : true,									
									field : 'buy_point',
									align : 'center',
									title : '买入价格'
								},
								{
									width : 100,
									sortable : true,
									field : 'sell_point',
									title : '卖出价格',
									align : 'center',
									formatter: function(value, row, index){
										if(row.trading_rule=="03")
										{
											return value.toFixed(3);	
										}else
										{
											return value;
										}
								        								   
								}
									
								},
								{
									width : 100,
									sortable : true,
									field : 'buy_all_price',
									title : '交易额',
									align : 'center'								
								},								
								{
									width : 100,
									sortable : true,
									field : 'buy_amount',
									title : '买入数量',
									align : 'center'								
								},
								{
									width : 100,
									sortable : true,
									field : 'buy_brokerage',
									title : '仓储费',
									align : 'center'									
								},
								{
									width : 100,
									sortable : true,
									field : 'buy_type',
									title : '模式',
									align : 'center',
									formatter: function(value, row, index){
										    if (value == "buy") {
										        return '购物';
										    }
										    if (value == "sell") {
										        return '赊货';
										    }
										}
								},
								{
									width : 100,
									sortable : true,
									field : 'trading_rule',
									title : '交易模式',
									align : 'center',
									formatter: function(value, row, index){
									    if (value == "01") {
									        return '模式一';
									    }
									    if (value == "02") {
									        return '模式二';
									    }
									    if (value == "03") {
									        return '模式三';
									    }
									    if (value == "04") {
									        return '模式四';
									    }
									    
									    
									}
								},
								{
									width : 100,
									sortable : true,
									field : 'sell_profit_loss',
									title : '收益',
									align : 'center',
									formatter: function(value, row, index){									    
									        return value.toFixed(2);									   
									}
								},
								{
									width : 100,
									sortable : true,
									field : 'sell_type',
									title : '购赊类型',
									align : 'center',
									formatter: function(value, row, index){
									    if (value == "manuallySell") {
									        return '手动购赊';
									    }
									    if (value == "stopProfitStopLoss") {
									        return '止盈止损';
									    }
									    if (value == "forceSell") {
									        return '自动购赊';
									    }
									    else
									    {
									    	 return '到期购赊';
									    }
									}
								},
								{
									width : 100,
									sortable : true,
									field : 'settle_name',
									title : '服务商',
									align : 'center',									
								},
								{
									width : 100,
									sortable : true,
									field : 'agent_name',
									title : '代理商',
									align : 'center',									
								},
								{
									width : 100,
									sortable : true,
									field : 'dept_name',
									title : '部门名称',
									align : 'center',									
								},
								{
									width : 150,
									sortable : true,
									field : 'broker_name',
									title : '经纪人',
									align : 'center',									
								}
								
								] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}

function buildsum(param){
	$.ajax({
		url : "querySellInfoSum",
		type : "POST",
		data : param,
		success : function(data) {
			if(data == null){
				return ;
			}

			var html = '\
				<span>手数汇总： '+data.buy_amount_count+'\</span>\
				<span>仓储费汇总：¥ '+fmoney(data.buy_brokerage_count,2)+'\</span>\
				<span>收益汇总：¥ '+fmoney(data.buy_price_count,2) +'\</span>\
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
	$.ajax({
		url : "../platform/checkUse",
		data:{sys_type:"01"},
		success : function(data) {
			var is_use=data[0].is_use;
			if(is_use=="0")
			{
				alert("抱歉，由于用户量过大，暂时关闭该功能！");
			}
			else{
			loadData(param,"querySellInfo");//查询数据
			buildsum(param);
			}
		}
	});
	return false;
});

function expInfo() {
	//var arrayData = $("#search").serialize();
	window.location.href = '';
}
loadData();//初始化加载数据