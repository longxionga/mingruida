dict(1023);
function loadData(param) {
	$('#data').datagrid({
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
		url: "queryShoppingRefund",
		queryParams: param,//查询参数
		toolbar: '#toolbar',//工具栏
		frozenColumns:[[
		               /* {

							   width: 230,
							   halign: 'center',
				        	   field: 'shopping_refund_id',
				        	   title: 'ID'
				           },
				           {

							   width: 230,
							   halign: 'center',
				        	   field: 'shopping_order_id',
				        	   title: '购物订单号'
				           },*/
				           {
				        	   width: 230,
				        	   halign: 'center',
				        	   field: 'order_id',
				        	   title: '订单ID'
				           },{
				        	   width: 150,
				        	   halign: 'center',
				        	   field: 'create_date',
				        	   title: '创建时间',
				        	   formatter : dateTimeFormatter,
				           }
		        ]],
		columns: [[
		           {
		        	   width: 100,
		        	   align : 'center',
		        	   field: 'user_name',
		        	   title: '用户名'
		           },
		           {
		        	   width: 80,
		        	   align : 'center',
		        	   field: 'item_name',
		        	   title: '商品名称'
		           },
		           {

					   width: 200,
					   halign: 'center',
		        	   field: 'refund_reason',
		        	   title: '用户退款原因'
		           },
		           {
		        	   width: 100,
		        	   halign: 'center',
		        	   align : 'right',
		        	   field: 'amount',
		        	   title: '订单总金额'
		           },
		           {

					   width: 100,
					   halign: 'center',
					   align : 'right',
		        	   field: 'refund_money',
		        	   title: '申请退款金额'
		           },
		           {

					   width: 80,
					   align : 'center',
		        	   field: 'receipt_status',
		        	   title: '收货状态',
		        	   formatter: function (value, row, index) {
		        		   if (value == 0)
		        			   return '未收到货';
		        		   else if(value==1)
		        			   return '<span style="color:#f00;">已收到货</span>';
		        	   }	  
		           },
		           {

					   width: 80,
					   align : 'center',
		        	   field: 'refund_type',
		        	   title: '退款类型',
		        	   formatter: function (value, row, index) {
		        		   if (value == 1)
		        			   return '退款';
		        		   else if(value==2)
		        			   return '<span style="color:#f00;">退货</span>';
		        	   }	
		           },
		           {

					   width: 100,
					   align : 'center',
		        	   field: 'status',
		        	   title: '客户订单状态',
		        	   formatter:dictFormatter
		           },
		           {

					   width: 200,
		        	   field: 'shopping_refund_status',
		        	   title: '客户订单退款状态',
		        	   formatter: function (value, row, index) {
		        		   if (value == 0)
		        			   return '正常';
		        		   else if(value==1)
		        			   return '退款处理中';
		        		   else if(value==2)
		        			   return '退款失败';
		        		   else if(value==3)
		        			   return '退款成功';		        		  
		        	   }	
		           },
		           {

					   width: 100,
					   align : 'center',
		        	   field: 'refund_status',
		        	   title: '退款状态',
		        	   formatter: function (value, row, index) {
		        		   if (value == 0)
		        			   return '退款中';
		        		   else if(value==1)
		        			   return '退款成功';
		        		   else if(value==2)
		        			   return '<span style="color:#f00;">退款失败</span>';
		        	   }	
		           },
		           {

					   width: 200,
					   halign: 'center',
		        	   field: 'refund_explain',
		        	   title: '客户退款说明'
		           },
		           {

					   width: 200,
					   halign: 'center',
		        	   field: 'voucher_url1',
		        	   title: '凭证图片1'
		           },
		           {

					   width: 200,
					   halign: 'center',
		        	   field: 'voucher_url2',
		        	   title: '凭证图片2'
		           },
		           {

					   width: 200,
					   halign: 'center',
		        	   field: 'voucher_url3',
		        	   title: '凭证图片3'
		           },  
		           {

					   width: 200,
					   halign: 'center',
		        	   field: 'settle_name',
		        	   title: '服务商'
		           },
		           {

					   width: 200,
					   halign: 'center',
		        	   field: 'agent_name',
		        	   title: '代理商'
		           },
		           {

					   width: 200,
					   halign: 'center',
		        	   field: 'dept_name',
		        	   title: '部门'
		           },
		           {

					   width: 200,
					   halign: 'center',
		        	   field: 'broker_name',
		        	   title: '经纪人'
		           },
		           {

					   width: 200,
					   halign: 'center',
		        	   field: 'reject_reason',
		        	   title: '退款驳回原因'
		           },
		           {
		        	   width: 100,
		        	   field: 'op',
		        	   title: '操作',
		        	   align: 'center',
		        	   formatter: function (value, row, index) {
		        		  var str="";
		        		   if(row.refund_status==0){
		        			   str = '\
			        			   <a class="btns" href="javascript:agree(\'' + row.shopping_refund_id + '\',\'' + row.user_id + '\',\'' + row.shopping_order_id + '\');">退款</a>\
			        			   <a class="btns" href="javascript:update(\'' + index + '\');">驳回</a>\
			        			   ';
		        		  }		        		   
		        		   return str;
		        	   }
		           }
		           ]],
		           onLoadSuccess: function () {
		        	   $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
		        	   $(".datagrid-body td[field='order_id']," +
	        			   ".datagrid-body td[field='user_name']," +
	        			   ".datagrid-body td[field='item_name']," +
	        			   ".datagrid-body td[field='item_name_alias']," +
	        	   			".datagrid-body td[field='amount']").overTitle();
		        	   var voucher_url1=$(".datagrid-body td[field='voucher_url1']");
		        	   voucher_url1.overDialog('<div class="over_dialog_img"><img src="#" /></div>');
		        	   var voucher_url2=$(".datagrid-body td[field='voucher_url2']");
		        	   voucher_url2.overDialog('<div class="over_dialog_img"><img src="#" /></div>');
		        	   var voucher_url3=$(".datagrid-body td[field='voucher_url3']");
		        	   voucher_url3.overDialog('<div class="over_dialog_img"><img src="#" /></div>');
		           }
	});
}

//删除方法
function agree(id,user_id,shopping_order_id) {         	
	$.messager.confirm('确认退款', '确定要退款吗?', function (r) {
		if (r) {
			$.ajax({
				url: "agreeShoppingRefund",
				type: "POST",
				data : {shopping_refund_id:id,user_id:user_id,shopping_order_id:shopping_order_id},
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

//添加/修改方法
var updateId;
function update(id) {
	var $this = $('#update');
	var $form = $this.find("form");
	updateId=id;
	if (updateId == undefined) {
		var thisData = {"is_use":1};
		$this.form('clear');	
		$this.form('load', thisData);
		$("#resetBtn").linkbutton('enable');
		//只读
		$form.attr("action", $form.attr("action-add"));
		$this.window({ title: '添加' });
	} else {
		$("#resetBtn").linkbutton('disable');
		$this.window({ title: '编辑' });
		// 重置有效
		$("#reset").linkbutton('enable');
		$form.attr("action", $form.attr("action-edit"));
		$this.form('clear');
		$this.form('load', $('#data').datagrid('getData').rows[id]);
		$("#shopping_refund_id").val($('#data').datagrid('getData').rows[id].shopping_refund_id);
	}
	
	$this.window({
    	top:($(window).height() - 289) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 588) * 0.5
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


$("#search").submit(function () {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param);//查询数据
	return false;
});
loadData();//初始化加载数据

$('#refund_status').combobox({
	width : '150',
	height : '30',
	valueField : 'label',
	textField : 'value',
	required : false,
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '0',
		value : '退款中'
	}, {
		label : '1',
		value : '退款成功'
	}]
});
