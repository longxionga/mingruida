
dict(1002);
function loadData(param) {
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
						idField:'id',
						url : 'queryMachineOrderPageList',
						remoteSort:false,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						columns : [ [
							{
								width: 100,
								sortable : true,
								field : 'brand_name',
								title : '机具所属品牌'
							},
                            {
                                width: 150,
                                sortable : true,
                                field : 'order_id',
                                title : '交易流水号'
                            },
							{
								width: 120,
								sortable : true,
								field : 'trade_time',
								title : '交易日期',
								formatter:dateTimeFormatter
							},
							{
								width: 80,
								sortable : true,
								field : 'merchant_code',
								title : '商户编号'
							},
							{
								width: 100,
								sortable : true,
								field : 'merchant_name',
								title : '商户名称'
							},
							{
								width: 100,
								sortable : true,
								field : 'machine_code',
								title : '机具编号'
							},
							{
								width: 100,
								sortable : true,
								field : 'machine_type',
								title : '机具类型'
							},

                            {
                                width: 150,
                                sortable : true,
                                field : 'staffnamesub',
                                title : '员工名称'
                            },
                            {
                                width: 80,
                                sortable : true,
                                field : 'settle_type',
                                title : '结算类型'
                            },

                           /* {
                                width: 100,
                                sortable : true,
                                field : 'merchant_rate',
                                title : '商户费率(%)'
                            },*/
								{
									width: 80,
									sortable : true,
									field : 'order_amount',
									title : '交易金额'
								},

								{
									width: 100,
									sortable : true,
									field : 'jyshxf',
									title : '交易手续费'
								},
							{
								width: 80,
								sortable : true,
								field : 'order_type',
								title : '交易类型'
							},
							{
								width: 80,
								sortable : true,
								field : 'pay_type',
								title : '支付类型'
							},
							{
								width: 80,
								sortable : true,
								field : 'pay_card_type',
								title : '支付卡类型'
							},
							{
								width: 80,
								sortable : true,
								field : 'pay_card_type',
								title : '支付卡类型'
							},
                            {
                                width: 80,
                                field: 'order_state',
                                title: '订单状态',
                                align : 'center',
                                formatter: function (value, row, index) {
                                    if (value == '1')
                                        return '交易成功';
                                    else if (value =='2')
                                        return '<span style="color:#f00;">交易失败</span>';
									else if (value =='3')
										return '<span style="color:#f00;">处理中</span>';
                                    else
										return '';
                                }
                            }

                            // {
                            //     width: 80,
                            //     sortable : true,
                            //     field : 'trade_day',
                            //     title : '交易时间'
                            // },
								/*{
									width: 120,
                                    align: 'center',
									sortable : true,
									field : 'create_time',
									title : '创建时间',
                                    formatter:dateTimeFormatter
                                },
								{
									width: 100,
									align: 'center',
									sortable : true,
									field : 'update_time',
									title : '更新时间',
									formatter:dateTimeFormatter
								},*/
							/*{
                                width: 250,
                                field : 'op',
                                title : '操作',
                                align : 'center'
                            }*/
								] ],
						/*onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
							$(".datagrid-body td[field='gzp_buy_no_time']").overTitle();
						}*/
					});
}

//为重置按钮添加单击事件 
$("#reset").click(function(){
	if (updateId == undefined) {
		$('#updateform').form('clear');
		var thisData = {"is_use":1};
		$('#update').form('load', thisData);
	}else{
		update(updateId);	
	}	
});

$("#search").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param);//查询数据
	return false;
});



$('#brand_id').combobox({
    url : '../platform/queryDictBrandRule',
    valueField : 'id',
    textField : 'name',
    width : '150',
    height : '30',
    editable : false,
    panelHeight : '200'
});

//交易类型
$('#order_type_1').combobox({
	url : '../platform/getOrderTypeCombobox',
	valueField : 'id',
	textField : 'text',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'
});
//支付类型
$('#pay_type_1').combobox({
	url : '../platform/getPayTypeCombobox',
	valueField : 'id',
	textField : 'text',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'
});


//订单状态
$('#order_state_1').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '1',
		value : '支付成功'
	},{
		label : '2',
		value : '支付失败'
	},{
		label : '3',
		value : '审批中'
	} ]
});