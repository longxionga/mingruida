function loadData(param) {
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
						url : "queryGoodsOrder",
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						frozenColumns:[[
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'order_type',
									title : '订单类型',
                                    formatter: function (value, row, index) {
                                        if (value == '1')
                                            return '其他商品';
                                        else
                                            return '普通商品';
                                    }
								},
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'order_id',
									title : '订单编号'
								},
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'order_code',
									title : '订单编码'
								},
								{
									sortable : false,
									field : 'logistics',
									title : '物流公司',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'logistics_no',
									title : '物流单号',
									width : parseInt($(this).width() * 0.1)
								}
								
						                ]],
						columns : [ [
								{
									 sortable : false,
									 field : 'receiver_mobile',
									 title : '收货电话',
									 align: 'center',
									 width : 100
								},
								{
									sortable : false,
									field : 'receiver_address',
									title : '收货地址',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'receiver_name',
									title : '收货人姓名',
									width : parseInt($(this).width() * 0.1)
								},								
								{
									sortable : false,
									field : 'logistics_price',
									title : '物流费用',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'total_price',
									title : '总价',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'order_status',
									title : '订单状态',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'logistics_status',
									title : '物流状态',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'create_time',
									title : '创建时间',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'update_time',
									title : '更新时间',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'spec_name',
									title : '商品规格',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'sku_name',
									title : 'SKU',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'goods_name',
									title : '商品名称',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'goods_alias',
									title : '商品别名',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'quantity',
									title : '商品数量',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'goods_price',
									title : '商品单价',
									width : parseInt($(this).width() * 0.1)
								},
								{
					            	 field : 'is_use',
					            	 width : 50,
					            	 title : '是否可用',
					            	 align : 'center',
					            	 formatter: function (value, row, index) {
					            		 if (value == 1)
					            			 return '是';
					            		 else
					            			 return '<span style="color:#f00;">否</span>';
					            	 }
					             },
								{
									field : 'op',
									title : '操作',
									width : 238,
									align : 'center',
									formatter : function(value, row, index) {										
										 var str="";
										 if(row.order_status==20){
											  str = '\<a class="btns" href="javascript:;" onclick="putIn(\'' +index  + '\');">填写单号</a>\
									 			<a class="btns" href="javascript:;" onclick="sendOut(\'' +row.order_id  + '\');">确认发货</a>\
			            			 ';
											}
				            		 return str;
									}
								} ] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
							 $(".datagrid-body td[field='order_type']," +
				        			   ".datagrid-body td[field='order_no']," +
				        			   ".datagrid-body td[field='logistics']," +
				        			   ".datagrid-body td[field='logistics_no']" ).overTitle();
						}
					});
}
//审核通过
function sendOut(id) {         	
	$.messager.confirm('确认发货', '确定发货吗?', function (r) {
		if (r) {
			$.ajax({
				url: "sendOutGoodsOrder",
				type: "POST",
				data : {order_id:id},
				success: function (data) {
					if (data.success == true) {
						$.messager.alert('提示信息', data.msg, 'info');
						$('#data').datagrid('reload');
					} else {
						$.messager.alert('错误信息', data.msg, 'error');
					}
				}
			});
		}
	});
}
//审核不通过
function putIn(id) {
	var $this = $('#update');
	var $form = $this.find("form");
	$this.window({
			title : '填写物流信息'
	});
	// 重置无效
	$("#reset").linkbutton('enable');
	$form.attr("action", $form.attr("action-edit"));
	$("#order_id").val($('#data').datagrid('getData').rows[id].order_id);
	$this.form('clear');
	$this.form('load', $('#data').datagrid('getData').rows[id]);

	$this.window({
    	top:($(window).height() - 212) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 588) * 0.5
    });
	$this.window('open');
}

$("#search").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param);//查询数据
	return false;
});

loadData({order_type:queryDescOrAsc.create_date});//初始化加载数据





	$('#goods_name').combobox({
		url : '../goods/queryGoodsName',
		valueField : 'goods_id',
		textField : 'goods_name',
		width : '150',
		height : '30',
		editable : false,
		panelHeight : '200',
		multiple:false
	});
