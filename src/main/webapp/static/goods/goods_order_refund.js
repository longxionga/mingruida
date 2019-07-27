function loadData(param) {
	$('#data').datagrid(
					{ 
						title: '',//当前页面标题
						fitColumns: false,//自动列宽
						autoRowHeight: true,//自动行高度
						singleSelect: true,//禁用选择多行
						border: !0,//不显示边框
						striped: true,//条纹行
						fit: true,//自适应高度
						nowrap: true,//单元格内文字禁止换行
						rownumbers: true,//显示行号
						checkOnSelect: false, //只有选择复选框时才选中
						selectOnCheck: false,//选择行时选中复选框	
						pagination : !0,// 显示分页
						pageSize : 20,// 每页显示数量 参数必须为pageList的对应
						pageList : [ 20,40,80 ],// 每页显示数量设置参数
						url : "queryGoodsOrderRefund",
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						columns : [ [
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'order_id',
									title : '订单ID'
								},{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'order_code',
									title : '订单编码'
                       		     },{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'refund_type',
									title : '退货类型',
									formatter: function (value, row, index) {
					            		 if (value == 1){
					            			 return '退款';
					            		 } else{
					            			 return '退货';
					            		 }
					            	}
								} ,
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'refund_reason',
									title : '退货理由'
								},
								{
									 sortable : false,
					            	 field : 'refund_money',
					            	 title : '退货金额',
					            	 align: 'center',
					            	 width : 100
								},
								{
									sortable : false,
									field : 'refund_explain',
									title : '退货说明',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'status',
									title : '退货状态',
									width : parseInt($(this).width() * 0.1),
									formatter: function (value, row, index) {
					            		 if (value == 1){
					            			 return '待处理';
					            		 } else if(value == 2){
					            			 return '处理完成';
					            		 }else{
					            			 return '已撤销';
					            		 }
					            	}
								},
								{
									sortable : false,
									field : 'reject_status',
									title : '审核状态',
									width : parseInt($(this).width() * 0.1),
									formatter: function (value, row, index) {
					            		 if (value == 1){
					            			 return '待审核';
					            		 } else if(value == 2){
					            			 return '审核成功';
					            		 }else{
					            			 return '驳回';
					            		 }
					            	}
								},
								{
									sortable : false,
									field : 'reject_time',
									title : '审核时间',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'reject_reason',
									title : '驳回原因',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'voucher_uri1',
									title : '凭证1',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'voucher_uri2',
									title : '凭证2',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'voucher_uri3',
									title : '凭证3',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'refund_address',
									title : '退货地址',
									width : parseInt($(this).width() * 0.1)
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
									title : '运单号',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'ship_desc',
									title : '发货说明',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'ship_voucher',
									title : '发货凭证',
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
                                        if(row.status==3){
                                            var str = '';
                                        } else if(row.status==2){
											var str = '\<a class="btns" href="javascript:;" onclick="update(\'' +index  + '\');">快递单号</a>\
								 			';
										}else{
											var str = '\<a class="btns" href="javascript:;" onclick="update(\'' +index  + '\');">快递单号</a>\
								 			<a class="btns" href="javascript:;" onclick="agree(\'' +row.refund_id  + '\');">通过</a>\
								 			<a class="btns" href="javascript:;" onclick="disAgree(\'' +index  + '\');">不通过</a>\
								 			';
										}
				            		 return str;
									}
								} ] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
							var voucher_uri1=$(".datagrid-body td[field='voucher_uri1']");
								voucher_uri1.overDialog('<div class="over_dialog_img"><img src="#" /></div>');
				        	   var voucher_uri2=$(".datagrid-body td[field='voucher_uri2']");
				        	   voucher_uri2.overDialog('<div class="over_dialog_img"><img src="#" /></div>');
				        	   var voucher_uri3=$(".datagrid-body td[field='voucher_uri3']");
				        	   voucher_uri3.overDialog('<div class="over_dialog_img"><img src="#" /></div>');
						}
					});
}

//添加/修改方法
var updateId;
function update(id) {
	var $this = $('#update');
	var $form = $this.find("form");
	updateId=id;
	if (updateId == undefined) {
		$('#updateform').form('clear');
		var thisData = {"cz_is_use":1,"tx_is_use":1};
		$this.form('load', thisData);
		$("#resetBtn").linkbutton('enable');
		$this.window({ title: '添加' });		
		$form.attr("action", $form.attr("action-add"));
	} else {
		$("#resetBtn").linkbutton('disable');
		$this.window({ title: '编辑' });
		$form.attr("action", $form.attr("action-edit"));
		//$("#sys_id").val($('#data').datagrid('getData').rows[id].sys_id);
		$this.form('clear');
		$this.form('load', $('#data').datagrid('getData').rows[id]);
	}
	
	
	$this.window({
    	top:($(window).height() - 258) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 588) * 0.5
    });
	$this.window('open');
}


//审核通过
function agree(id) {         	
	$.messager.confirm('确认退款', '确定要退款吗?', function (r) {
		if (r) {
			$.ajax({
				url: "agreeGoodsOrderRefund",
				type: "POST",
				data : {refund_id:id},
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
function disAgree(id) {
	var $this = $('#disupdate');
	var $form = $this.find("form");
	updateId=id;
	if (updateId == undefined) {
		$('#disupdateform').form('clear');
		var thisData = {"cz_is_use":1,"tx_is_use":1};
		$this.form('load', thisData);
		$("#resetBtn").linkbutton('enable');
		$this.window({ title: '添加' });		
		$form.attr("action", $form.attr("action-add"));
	} else {
		$("#resetBtn").linkbutton('disable');
		$this.window({ title: '编辑' });
		$form.attr("action", $form.attr("action-edit"));
		//$("#sys_id").val($('#data').datagrid('getData').rows[id].sys_id);
		$this.form('clear');
		$this.form('load', $('#data').datagrid('getData').rows[id]);
	}
	
	$this.window({
    	top:($(window).height() - 314) * 0.5+$(document).scrollTop(),   
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
		panelHeight : 'auto',
		multiple:false
	});
