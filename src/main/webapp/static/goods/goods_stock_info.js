/**
 * 
 */
//dictInfo(1002,"is_use");
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
						idField:'sku_id',
						url : 'queryGoodsStockInfoList',
						remoteSort:false,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						columns : [ [
								{
									width: 100,
									sortable : true,
									field : 'sku_id',
									title : 'sku_id'
								},
								{
									width: 100,
									sortable : true,
									field : 'spec_id',
									title : '规格ID'
								},
								{
									width: 100,
									sortable : true,
									field : 'goods_name',
									title : '商品名称'
								},
								{
									width: 80,
									sortable : true,
									field : 'spec_name',
									title : '商品规格'
								},
							    {
									width: 80,
									sortable : true,
									align : 'center',
									field : 'original_price',
									title : '原始价格'
								},
								{
									width: 80,
									sortable : true,
									align : 'center',
									field : 'discounted_price',
									title : '优惠价格'
								},
								{
									width: 80,
									align: 'center',
									sortable : false,
									field : 'total_amount',
									title : '总量'
								},
								{
									width: 80,
									align: 'center',
									sortable : false,
									field : 'booked_amount',
									title : '可订数量'
								},
								{
									width: 80,
									align: 'center',
									sortable : false,
									field : 'buy_amount',
									title : '交易数量'
								},
								{
									width: 80,
									sortable : true,
									align : 'center',
									field : 'rebate',
									title : '用户返利(%)'
								},
								{
									width: 80,
									sortable : true,
									align : 'center',
									field : 'fare_price',
									title : '运费'
								},
								{
									width: 80,
									sortable : true,
									align : 'center',
									field : 'fare_rule',
									title : '运费规则'
								}
								// ,
								// {
								// 	width: 50,
								// 	sortable : false,
								// 	field : 'is_activity',
								// 	title : '是否活动',
								// 	align : 'center',
								// 	//formatter:dictFormatter,
								// 	formatter : function(value, row, index) {
								// 		if (value == 1)
								// 			return '是';
								// 		else
								// 			return '<span style="color:#f00;">否</span>';
								// 	}
								// }
								,
								{
									width: 50,
									sortable : false,
									field : 'is_use',
									title : '是否可用',
									align : 'center',
									//formatter:dictFormatter,
									formatter : function(value, row, index) {
										if (value == 1)
											return '是';
										else
											return '<span style="color:#f00;">否</span>';
									}
								},
								{
									width: 120,
									align: 'center',
									sortable : true,
									field : 'update_time',
									title : '更新时间',
                                    formatter:dateTimeFormatter
                                },
								{
									width: 120,
                                    align: 'center',
									sortable : true,
									field : 'create_time',
									title : '创建时间',
                                    formatter:dateTimeFormatter
                                },
								{
									width: 150,
									field : 'op',
									title : '操作',
									align : 'center',
									formatter : function(value, row, index) {
										var str = '\
										<a class="btns" href="javascript:;" onclick="update(\'' + index + '\');">编辑</a>\
									';
										return str;
									}
								} ] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
							$(".datagrid-body td[field='gzp_buy_no_time']").overTitle();
						}
					});
}

// 添加/修改方法
var updateId;
function update(id) {
	var $this = $('#update');
	var $form = $this.find("form");
	updateId = id;
	if (updateId == undefined) {
		$this.form('clear');
		var thisData = {"is_use":1};
		$this.form('load', thisData);
		$this.window({
			title : '添加'
		});
		// 重置有效
		$("#reset").linkbutton('enable');
		$form.attr("action", $form.attr("action-add"));
	} else {
		$this.window({
			title : '编辑'
		});	
		// 重置有效
		$("#reset").linkbutton('enable');
		$form.attr("action", $form.attr("action-edit"));
		$this.form('clear');
		$this.form('load', $('#data').datagrid('getData').rows[id]);
	}
	

	$this.window({
    	top:($(window).height() - 258) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 900) * 0.5
    });
	$this.window('open');
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

function InitInfo()
{
	//alert('初始化库存信息');
	$.ajax({
		url : "initGoodsStockInfo",
		type : "POST",
		dataType:"json",
		data : {
			//user_id:user_id
		},
		success : function(data){
            $.messager.alert('提示信息', data.msg, 'info');
		}
	});
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

loadData({order_type:queryDescOrAsc.goods_id});//初始化加载数据


