/**
 * 
 */
dictInfo(1021,"cz_state");
dictInfo(1022,"entrance");
dictInfoValue(1008,"cz_type");
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
						idField:'user_name',
						url : url,
						remoteSort:false,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						frozenColumns:[[
								{
									width : 130,
									sortable : false,
									field : 'CZ_create_date',
									title : '订单创建时间',
									align : 'center',
									formatter:dateTimeFormatter
								},
								{
									width : 130,
									sortable : false,
									field : 'RS_create_date',
									title : '订单到账时间',
									align : 'center',
									formatter:dateTimeFormatter
								},
								
								{
									width : 100,
									sortable : false,
									field : 'order_id',
									title : '订单号',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'prepay_id',
									title : '订单编号',
									align : 'center'
								},
								{
									width : 80,
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
									field : 'order_money',
									title : '充值金额',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'receipts',
									title : '实际到账金额',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'poundage',
									title : '充值手续费',
									align : 'center'
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
									sortable : true,
									field : 'cz_type',
									align : 'center',
									title : '充值类型'

								},
								{
									width : 100,
									sortable : true,
									field : 'is_use',
									align : 'center',
									title : '充值状态',
									formatter: function(value, row, index){
									    if (row.IS_USE == "1") {
									        return '已成功';
									    }
									    else if(row.IS_USE == "2"){
									        return '等待支付';
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
								},
								{
									width : 80,
									sortable : true,
									field : 'entrance',
									align : 'center',
									title : '充值入口'
								},
						           {
						        	   width: 100,
						        	   field: 'op',
						        	   title: '操作',
						        	   align: 'center',
						        	   formatter: function (value, row, index) {
						        		  if(row.IS_USE=='2' && (row.cz_value=='znpay'|| row.cz_value=='znalipay' || row.cz_value=='unionpay' || row.cz_value=='unionpay2'|| row.cz_value=='unionpay3' || row.cz_value=='ouchuangpay' || row.cz_value=='zhquickpayymd' || row.cz_value=='zhqqpayymd' || row.cz_value=='unionpayqyjt' || row.cz_value=='znwxpayymd' || row.cz_value=='znalipayymd' || row.cz_value=='znwxpayqyjt' || row.cz_value=='znalipayqyjt')){
						        			  var str = '\<a class="btns" href="javascript:checkOrder(\'' + row.order_id + '\');">查询回调</a>\
										';
														return str;  
						        		  }else{
						        			  var str = '';
													return str;
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
		url : "queryRechargeAccSum",
		type : "POST",
		data : param,
		success : function(data) {
			if(data == null){
				return ;
			}
			var html = '\
				<span>用户充值汇总：¥ '+fmoney(data[0].ORDER_MONEYCOUNT,2)+'\</span>\
                <span>实际到账汇总：¥ '+fmoney(data[0].ORDER_RECEIPTSCOUNT,2)+'\</span>\
                ';
			$("#total").html(html);
		}
	});
}

function checkOrder(id){
	$.ajax({
		url : "queryOrderAcc",
		type : "POST",
		data : {order_id:id},
		 success: function(msg){
	        	if(msg.success==true){
	        		$.messager.alert('提示', msg.msg, 'info');	        		
	        	}else{
	        		$.messager.alert('提示', msg.msg, 'error');
	        	}
	            $('#search').submit();
	         }
	});
}

$("#search").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param,"queryReportRechargeAccInfo");//查询数据
	rechargesum(param);
	return false;
});

function expInfo() {
	window.location.href = 'exportMemberdayReportInfo';
}
loadData();//初始化加载数据