//agentCode('agent_code');
function loadData(param) {
	$('#data').datagrid({
		title: '',//当前页面标题
		fitColumns: true,//自动列宽
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
		url: "queryEntranceManager",
		queryParams: param,//查询参数
		toolbar: '#toolbar',//工具栏
		
		columns: [[
		           {

					   width: 200,
		        	   field: 'agent_code',
		        	   title: '代理商编码'
		           },
		           {
		        	   width: 200,
		        	   field: 'url',
		        	   title: '链接地址'
		           },
		           {
		        	   width: 100,
		        	   align : 'right',
		        	   field: 'entrance',
		        	   title: '入口'
		           },
		           {
		        	   width: 100,
		        	   align : 'right',
		        	   field: 'default',
		        	   title: '是否默认入口',
		        	   formatter: function (value, row, index) {
		        		   if (value == 1)
		        			   return '<span style="color:#f00;">是</span>';
		        		   else
		        			   return '否';
		        	   }
		           },
		           {
		        	   width: 100,
		        	   field: 'op',
		        	   title: '操作',
		        	   align: 'center',
		        	   formatter: function (value, row, index) {
		        		   var str = '\
		        			   <a class="btns" href="javascript:update(\'' + index + '\');">编辑</a>\
		        			   <a class="btns" href="javascript:del(\'' + row.entrance + '\');">删除</a>\
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
	        			   ".datagrid-body td[field='cz_time']," +
	        	   			".datagrid-body td[field='rule_context']").overTitle();
		           }
	});
}

//删除方法
function del(entrance) {         	
	$.messager.confirm('确认删除', '确定要删除吗?', function (r) {
		if (r) {
			$.ajax({
				url: "delEntranceManager",
				type: "POST",
				data: { entrance: entrance },
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
		$('#updateform').form('clear');
		$("#reset").linkbutton('enable');
		$('#entrance').textbox('readonly',false);
		$this.window({ title: '添加' });		
		$form.attr("action", $form.attr("action-add"));
	} else {
		$("#resetBtn").linkbutton('disable');
		$this.window({ title: '编辑' });
		$form.attr("action", $form.attr("action-edit"));
		$this.form('clear');
		$('#entrance').textbox('readonly');
		$this.form('load', $('#data').datagrid('getData').rows[id]);
	}
	
	
	$this.window({
    	top:($(window).height() - 290) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 680) * 0.5
    });
	$this.window('open');
}

// 为重置按钮添加单击事件 
$("#reset").click(function(){
	if (updateId == undefined) {
		$('#updateform').form('clear');
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



$('#default').combobox({
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

/*
//下拉菜单字典
function agentCode(controlName){	
	$('#'+controlName).combobox({
		url : '../sys/getAgentCode?dept_type=4',
		valueField : 'dept_code',
		textField : 'dept_name',
		width : '150',
		height : '30',
		editable : false,
		panelHeight : 'auto'
	});
}
*/


function up(){
	$("#updateform").submit(); 
	loadData();//初始化加载数据
}
