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
						fitColumns : false,// 自动列宽
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
						idField:'',
						url : url,
						remoteSort:false,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						view: detailview,
		                detailFormatter:function(index,row){
		                    return '<div style="padding:2px"><table class="ddv"></table></div>';
		                },
		                onExpandRow: function(index,row){
		                    var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
		                    
		                    ddv.datagrid({
		                        url:"queryReportDeptDataInfo?feetype=1&settle_id="+row.SETTLE_ID+"&agent_id="+ row.AGENT_ID +"&year="+ row.FOR_YEAR +"&month="+ row.FOR_MONTH +"&day="+ row.FOR_DAY +"",
		                        fitColumns:true,
		                        singleSelect:true,
		                        rownumbers:true,
		                        height:'auto',
		                        columns:[[
		                            {
		                            	field:'curr_date',
		                            	title:'日期',
		                            	width:200
		                            },		                          
		                            {
		                            	field:'DEPT_NAME',
		                            	title:'代理商名称',
		                            	width:200
		                            },
		                            {
		                            	field:'PROFIT_LOSS',
		                            	title:'分润',
		                            	width:200
		                            },
		                            {
		                            	field:'COUNTER_FEE',
		                            	title:'交易',
		                            	width:200
		                            },
		                            {
		                            	width : 200,
										field : 'op',
										title : '操作',
										align : 'center',
										formatter : function(value, row, index) {									
											
											return "<a href='javascript:;' onclick='viewdetail(\"2\","+ row.SETTLE_ID +","+ row.AGENT_ID +","+ row.DEPT_ID +","+ row.FOR_YEAR +","+ row.FOR_MONTH +","+ row.FOR_DAY +");'>查看明细</a>";
										},
		                            }/* ,
		                            {field:'quantity',title:'Quantity',width:100,align:'right'},
		                            {field:'unitprice',title:'Unit Price',width:100,align:'right'} */
		                        ]],
		                        onResize:function(){
		                            $('#data').datagrid('fixDetailRowHeight',index);
		                        },
		                        onLoadSuccess:function(){
		                            setTimeout(function(){
		                                $('#data').datagrid('fixDetailRowHeight',index);
		                            },0);
		                        }
		                    });
		                    $('#data').datagrid('fixDetailRowHeight',index);
		                },
						columns : [ [
								{				
									width : 100,
									sortable : true,									
									field : 'curr_date',
									align : 'center',
									title : '日期'
								},
								{			
									width : 100,
									sortable : true,
									field : 'settle_name',
									title : '服务商',
									align : 'center'
									
								},
								{			
									width : 100,
									sortable : true,
									field : 'agent_name',
									title : '代理商',
									align : 'center'								
								},
                            {
                                field:'total_amount',
                                title:'分润总金额',
                                width:200
                            },
                            {
                                field:'real_amount',
                                title:'分润实际金额',
                                width:200
                            },
                            {
                                field:'total_activation',
                                title:'总激活数',
                                width:200
                            },
                            {
                                field:'total_unactivation',
                                title:'总未激活数',
                                width:200
                            },
                            {
                                field:'total_standard',
                                title:'总达标数',
                                width:200
                            },
                            {
                                field:'total_unstandard',
                                title:'总未达标数',
                                width:200
                            },
                            {
                                field:'total_order_num',
                                title:'订单总数量',
                                width:200
                            },
                            {
                                field:'total_order_amount',
                                title:'订单总金额',
                                width:200
                            }
								] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}

function counterfeesum(param){
	$.ajax({
		url : "queryReportDataInfoSum",
		type : "POST",
		data : param,
		success : function(data) {
			if(data == null){
				return ;
			}
			var html = '\
				<span>代理商交易总金额：¥ '+fmoney(data[0].PROFIT_LOSS,2)+'\</span>\
				<span>分润汇总：¥ '+fmoney(data[0].COUNTER_FEE,2)+'\</span>\
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
	loadData(param,"queryReportMerchantInfo");//查询数据
	counterfeesum(param);
	return false;
});


/*$(document).on("click","[item-cz]",function(){
	alert(11);
	var $this = $(this);
	var id = $this.attr("item-cz");
	if(id == null || id== ''){
		return;
	}
	$("#data2").html("");
	$('#txsh_detail_2').dialog('open');
	var data = eval("({userid:'"+id+"'})")
	LoadDetailInfo(data);
});*/

function viewdetail(feetype,settle_id,agent_id,dept_id,year,month,day){
	$("#data2").html("");
	$('#txsh_detail_2').dialog('open');
	var data = eval("({feetype:'"+feetype+"',settle_id:'"+settle_id+"',agent_id:'"+agent_id+"',dept_id:'"+dept_id+"',year:'"+year+"',month:'"+month+"',day:'"+day+"',})")
	LoadDetailInfo(data);
}

function LoadDetailInfo(param) {  
	  
    /*获取选中行*/  
    //var row = $('#Cse_Bespeak_Log').datagrid('getSelected'); //获取选中行    
      
	$('#data2').datagrid(
			{
				title : '',// 当前页面标题
				fitColumns : true,// 自动列宽
				autoRowHeight : true,// 自动行高度
				singleSelect : true,// 禁用选择多行
				border : !0,// 不显示边框
				striped : true,// 条纹行
				fit : true,// 自适应高度
				nowrap : true,// 单元格内文字禁止换行
				rownumbers : true,// 显示行号
				checkOnSelect : false, // 只有选择复选框时才选中
				selectOnCheck : false,// 选择行时选中复选框
				pagination : !0,// 显示分页
				pageSize : 20,// 每页显示数量 参数必须为pageList的对应
				pageList : [ 20,40,80 ],// 每页显示数量设置参数
				idField:'',
				url : "queryReportDataDetailInfo",
				remoteSort:false,
				queryParams : param,// 查询参数
				//toolbar : '#toolbar',// 工具栏
				columns : [ [
						{
							width: 100,
							sortable : true,
							field : 'curr_date',
							title : '日期'
						},
						{
							width: 80,
							sortable : true,
							field : 'USER_NAME',
							title : '用户名'
						},
						{
							width: 80,
							sortable : true,
							align : 'center',
							field : 'ORDER_DATE',
							title : '交易时间'
						},						
						{
							width: 80,
							align: 'center',
							sortable : true,
							field : 'PROFIT_LOSS',
							title : '盈亏'
						},
						{
							width: 80,
							align: 'center',
							sortable : true,
							field : 'COUNTER_FEE',
							title : '仓储费'
						},
						{
							width: 80,
							align: 'center',
							sortable : true,
							field : 'broker_name',
							title : '所属经纪人'
						}						
						 ] ],
				onLoadSuccess : function() {
					$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
					
				}
			});
   
};  




function expInfo() {
	//var arrayData = $("#search").serialize();
	window.location.href = '';
}
loadData();//初始化加载数据