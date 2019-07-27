dictInfo(1023,"status");
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
		url: "queryShoppingOrder",
		queryParams: param,//查询参数
		toolbar: '#toolbar',//工具栏
		frozenColumns:[[
					/*	{						
							   width: 230,
							   align : 'center',
							   field: 'shopping_order_id',
							   title: 'ID'
						},*/
						{
							   width: 230,
							   align : 'center',
							   field: 'order_id',
							   title: '订单ID'
						},
						{
							   width: 150,
							   align : 'center',
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
		        	   width: 100,
		        	   align : 'center',
		        	   field: 'item_name',
		        	   title: '商品名称'
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
		        	   field: 'pre_payment',
		        	   title: '订单预付款'
		           },
		           {
		        	   width: 100,
		        	   halign: 'center',
		        	   align : 'right',
		        	   field: 'balance_payment',
		        	   title: '订单尾款'
		           },
		           {
		        	   width: 100,
		        	   align : 'center',
		        	   field: 'receiver_name',
		        	   title: '收货人姓名'
		           },
		           {
		        	   width: 200,
		        	   align : 'center',
		        	   field: 'receiver_mobile',
		        	   title: '收货人手机号'
		           },
		           {
		        	   width: 200,
		        	   halign: 'center',
		        	   field: 'receiver_addr',
		        	   title: '收货人地址'
		           },
		           {
		        	   width: 250,
		        	   halign: 'center',
		        	   field: 'settle_name',
		        	   title: '服务商名称'
		           },
		          
		           {
		        	   width: 150,
		        	   halign: 'center',
		        	   field: 'agent_name',
		        	   title: '代理商名称'
		           },
		           
		           {
		        	   width: 150,
		        	   halign: 'center',
		        	   field: 'broker_name',
		        	   title: '经纪人名称'
		           },
		          
		           {
		        	   width: 150,
		        	   halign: 'center',
		        	   field: 'dept_name',
		        	   title: '部门名称'
		           },
		           {
		        	   width: 80,
		        	   align : 'center',
		        	   field: 'is_use',
		        	   title: '是否可用',
		        	   align : 'center',
		        	   formatter: function (value, row, index) {
		        		   if (value == 1)
		        			   return '是';
		        		   else
		        			   return '<span style="color:#f00;">否</span>';
		        	   }
		           },
		          
		           {
		        	   width: 150,
		        	   align : 'center',
		        	   field: 'status',
		        	   title: '订单状态',
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
		        	   width: 150,
		        	   align : 'center',
		        	   field: 'tracking_number',
		        	   title: '快递单号'
		           },
		           {
		        	   width: 150,
		        	   halign: 'center',
		        	   field: 'express',
		        	   title: '快递公司'
		           },{
		        	   width: 80,
		        	   halign: 'center',
		        	   align : 'right',
		        	   field: 'buy_amount',
		        	   title: '手数'
		           },
		           {
		        	   width: 80,
		        	   halign: 'center',
		        	   align : 'right',
		        	   field: 'buy_price',
		        	   title: '买入价'
		           },
		           {
		        	   width: 150,
		        	   halign: 'center',
		        	   field: 'buy_unit',
		        	   title: '单位'
		           },
		          
		           
		           {
		        	   width: 150,
		        	   halign: 'center',
		        	   align : 'center',
		        	   field: 'item_code',
		        	   title: '商品编码'
		           },

		           {
		        	   width: 100,
		        	   field: 'op',
		        	   title: '操作',
		        	   align: 'center',
		        	   formatter: function (value, row, index) {
		        		   var str="";
		        		   if(row.status<3){
		        			   str = '\
			        			   <a class="btns" href="javascript:update(\'' + index + '\');">设置快递单号</a>\
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
		$("#shopping_order_id").val($('#data').datagrid('getData').rows[id].shopping_order_id);
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
