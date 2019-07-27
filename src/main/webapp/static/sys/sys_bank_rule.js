
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
		url: "queryBankRule",
		queryParams: param,//查询参数
		toolbar: '#toolbar',//工具栏
		
		columns: [[
		           {

					   width: 200,
		        	   field: 'tx_date',
		        	   title: '提现日期段'
		           },
		           {
		        	   width: 200,
		        	   field: 'tx_time',
		        	   title: '提现时间段'
		           },
		           {
		        	   width: 100,
		        	   align : 'right',
		        	   field: 'tx_min_money',
		        	   title: '最小提现金额'
		           },
		           {
		        	   width: 100,
		        	   align : 'right',
		        	   field: 'tx_max_money',
		        	   title: '最大提现金额'
		           },
		           {
		        	   width: 100,
		        	   align : 'right',
		        	   field: 'tx_day_max_money',
		        	   title: '当天最大提现金额'
		           },
		           {
		        	   width: 100,
		        	   align : 'right',
		        	   field: 'cz_rate',
		        	   title: '充值费率(千分率)'
		           },
		           {
		        	   width: 100,
		        	   align : 'right',
		        	   field: 'tx_rate',
		        	   title: '提现费率(千分率)'
		           },
		           {
		        	   width: 200,
		        	   field: 'cz_date',
		        	   title: '充值日期段'
		           },
		           {
		        	   width: 200,
		        	   field: 'cz_time',
		        	   title: '充值时间段'
		           },
		           {
		        	   width: 100,
		        	   align : 'right',
		        	   field: 'cz_min_money',
		        	   title: '最小充值金额'
		           },{
		        	   width: 100,
		        	   align : 'right',
		        	   field: 'cz_max_money',
		        	   title: '最大充值金额'
		           },{
		        	   width: 100,
		        	   align : 'right',
		        	   field: 'cz_day_max_money',
		        	   title: '当天最大充值金额'
		           },
		           {
		        	   width: 150,
		        	   field: 'cash_money',
		        	   title: '现金券'
		           },
		           {
		        	   width: 150,
		        	   field: 'cz_sum_money',
		        	   title: '最大充值总金额'
		           },
		           {
		        	   width: 150,
		        	   field: 'tx_sum_money',
		        	   title: '最大提现总金额'
		           },
		           {
		        	   width: 80,
		        	   field: 'cz_is_use',
		        	   title: '充值是否可用',
		        	   align : 'center',
		        	   formatter: function (value, row, index) {
		        		   if (value == 1)
		        			   return '是';
		        		   else
		        			   return '<span style="color:#f00;">否</span>';
		        	   }
		           },
		           {
		        	   width: 80,
		        	   field: 'tx_is_use',
		        	   title: '提现是否可用',
		        	   align : 'center',
		        	   formatter: function (value, row, index) {
		        		   if (value == 1)
		        			   return '是';
		        		   else
		        			   return '<span style="color:#f00;">否</span>';
		        	   }
		           },
		           {
		        	   width: 120,
		        	   field: 'create_time',
		        	   title: '创建日期'					    
		           },

		           {
		        	   width: 100,
		        	   field: 'op',
		        	   title: '操作',
		        	   align: 'center',
		        	   formatter: function (value, row, index) {
		        		   var str = '\
		        			   <a class="btns" href="javascript:update(\'' + index + '\');">编辑</a>\
		        			   ';
		        		   return str;
		        	   }
		           }
		           ]],
		           onLoadSuccess: function () {
		        	   $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
		        	   $(".datagrid-body td[field='tx_date']," +
	        			   ".datagrid-body td[field='tx_time']," +
	        			   ".datagrid-body td[field='cz_date']," +
	        			   ".datagrid-body td[field='cz_time']" ).overTitle();
		           }
	});
}

// //删除方法
// function del(id) {
// 	$.messager.confirm('确认删除', '确定要删除吗?', function (r) {
// 		if (r) {
// 			$.ajax({
// 				url: "delBankRule",
// 				type: "POST",
// 				data : {sys_id:id},
// 				success: function (data) {
// 					if (data.success == true) {
// 						$.messager.alert('提示信息', data.msg, 'info');
// 						$('#data').datagrid('reload');
// 					} else {
// 						$.messager.alert('错误信息', data.msg, 'error');
// 					}
// 				}
// 			});
// 		}
// 	});
// }

//添加/修改方法
var updateId;
function update(id) {
	console.log(id);
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
		// $("#id").val($('#data').datagrid('getData').rows[id].id);
		$this.form('clear');
		$this.form('load', $('#data').datagrid('getData').rows[id]);
	}
	
	
	$this.window({
    	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 680) * 0.5
    });
	$this.window('open');
}

// 为重置按钮添加单击事件 
$("#reset").click(function(){
	if (updateId == undefined) {
		$('#updateform').form('clear');
		var thisData = {"cz_is_use":1,"tx_is_use":1};
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



$('#tx_is_use').combobox({
	width : '150',
	height : '30',
	valueField : 'label',
	textField : 'value',
	required : 'true',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '1',
		value : '是'
	}, {
		label : '0',
		value : '否'
	} ]
});

$('#cz_is_use').combobox({
	width : '150',
	height : '30',
	valueField : 'label',
	textField : 'value',
	required : 'true',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '1',
		value : '是'
	}, {
		label : '0',
		value : '否'
	} ]
});

$('#tx_date').combobox({
	width : '150',
	height : '30',
	valueField : 'label',
	textField : 'value',
	required : 'true',
	panelHeight : 'auto',
	multiple:true,
	editable: false,
	data : [ {
		label : '星期一',
		value : '星期一'
	}, {
		label : '星期二',
		value : '星期二'
	}, 
	{
		label : '星期三',
		value : '星期三'
	}, 
	{
		label : '星期四',
		value : '星期四'
	}, 
	{
		label : '星期五',
		value : '星期五'
	}, 
	{
		label : '星期六',
		value : '星期六'
	}, 
	{
		label : '星期日',
		value : '星期日'
	} ]
});

$('#cz_date').combobox({
	width : '150',
	height : '30',
	valueField : 'label',
	textField : 'value',
	required : 'true',
	panelHeight : 'auto',
	multiple:true,
	editable: false,
	data : [ {
		label : '星期一',
		value : '星期一'
	}, {
		label : '星期二',
		value : '星期二'
	}, 
	{
		label : '星期三',
		value : '星期三'
	}, 
	{
		label : '星期四',
		value : '星期四'
	}, 
	{
		label : '星期五',
		value : '星期五'
	}, 
	{
		label : '星期六',
		value : '星期六'
	}, 
	{
		label : '星期日',
		value : '星期日'
	} ]
});

function up(){
	var tx_date=$('#tx_date').combobox('getValues').join(',');
	var cz_date=$('#cz_date').combobox('getValues').join(',');
	 $('#tx_date').combobox('setValue',tx_date);
	 $('#cz_date').combobox('setValue',cz_date);
	$("#updateform").submit();
	loadData();//初始化加载数据
}
