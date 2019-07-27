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
		                        url:"queryBankCardInfo?user_id="+row.user_id,
		                        fitColumns:true,
		                        singleSelect:true,
		                        rownumbers:true,
		                        height:'auto',
		                        columns:[[
                                    // {
				                    //     field:'bank_id',
				                    //     title:'编号',
				                    //     width:200
				                    // },
		                            {
		                            	field:'name',
		                            	title:'用户姓名',
		                            	width:200
		                            },
		                            {
		                            	field:'mobile',
		                            	title:'用户手机号',
		                            	width:200
		                            },
		                            {
		                            	field:'user_bank_type',
		                            	title:'银行名称',
		                            	width:200
		                            },
		                            {
		                            	field:'user_bank_no',
		                            	title:'银行卡号',
		                            	width:200
		                            },		                           
		                            {
		                            	field:'dict_name',
		                            	title:'银行卡状态',
		                            	width:200
		                            },
		                            {
		                            	field:'create_time',
		                            	title:'创建时间',
		                            	width:200
		                            }
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
									sortable : false,									
									field : 'name',
									align : 'center',
									title : '用户名'
								},								
								{		
									width : 100,
									sortable : false,
									field : 'mobile',
									title : '手机号',
									align : 'center'									
								},
								{			
									width : 100,
									sortable : false,
									field : 'settle_name',
									title : '服务商',
									align : 'center'
									
								},
								{			
									width : 100,
									sortable : false,
									field : 'agent_name',
									title : '代理商',
									align : 'center'								
								},
								{			
									width : 100,
									sortable : false,
									field : 'ch_name',
									title : '渠道',
									align : 'center'								
								},	
								{				
									width : 100,
									sortable : false,
									field : 'ce_name',
									title : '交易所',
									align : 'center'								
								}								
								] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}



$("#search").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param,"queryBankCard");//查询数据
	return false;
});







loadData("","queryBankCard");//初始化加载数据