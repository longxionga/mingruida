function loadData(param) {
	$('#data').datagrid({
		title: '',//当前页面标题
		fitColumns: true,//自动列宽
		autoRowHeight: true,//自动行高度
		singleSelect: false,//禁用选择多行
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
		url: "queryBackDictInfo",
		remoteSort:false,
		queryParams: param,//查询参数
		toolbar: '#toolbar',//工具栏
		columns: [[
		           {
		        	   width: 100,
		        	   field: 'dict_code',
		        	   align: 'center',
		        	   title: '字典编码'
		           },
		           {
		        	   width: 100,
		        	   field: 'dict_name',
		        	   title: '字典名称'
		           },
					{
						width: 100,
						field: 'dict_value',
						title: '字典值'
					},
		           {
		        	   width: 100,
		        	   field: 'dict_desc',
		        	   title: '字典说明'					    
		           },
		           {
		        	   width: 100,
		        	   field: 'dict_id',
		        	   align: 'center',
		        	   title: '字典id'
		           },
		           {
		        	   sortable : true,
		        	   width: 100,
		        	   field: 'is_order',
		        	   title: '排序',
		        	   align: 'center'				    
		           },
		           {
		        	   width: 50,
		        	   field: 'is_use',
		        	   title: '是否可用',
		        	   align: 'center',
		        	   formatter: function (value, row, index) {
		        		   if (value == 1)
		        			   return '是';
		        		   else
		        			   return '<span style="color:#f00;">否</span>';
		        	   }
		           },

		           {
		        	   width: 50,
		        	   field: 'op',
		        	   title: '操作',
		        	   align: 'center',
		        	   formatter: function (value, row, index) {
		        		   var str = '\
		        			   <a class="btns" href="javascript:update(\'' + index + '\');">编辑</a>\ ';
		        			//   <a class="btns" href="javascript:del(\'' + row.dict_id+'\',\''+row.dict_code + '\');">删除</a>\
		        			   
		        		   return str;
		        	   }
		           }
		           ]],
		           onLoadSuccess: function () {
		        	   $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
		           }
	});
}

//删除方法
function del(dict_id,dict_code) {         	
	$.messager.confirm('确认删除', '确定要删除吗?', function (r) {
		if (r) {
			$.ajax({
				url: "delBackDictInfo",
				type: "POST",
				data: { dict_code: dict_code,dict_id:dict_id },
				success: function (data) {
					if (data.success == true) {
						$.messager.alert('提示信息', data.msg, 'info');
						$('#data').datagrid('reload');
					} else {
						$.messager.alert('错误信息', datae.msg, 'error');
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
	//$("#reset").attr("data-repickID",id);
	if (updateId == undefined) {
		var thisData = {"is_use":1};
		$this.form('clear');	
		$this.form('load', thisData);
		$("#resetBtn").linkbutton('enable');
		//只读
		$('#dict_code,#dict_id,#dict_name').textbox('readonly',false);
		$("#dict_code,#dict_id").textbox("enableValidation");
		$form.attr("action", $form.attr("action-add"));
		$this.window({ title: '添加' });
	} else {
		$("#resetBtn").linkbutton('disable');
		$this.window({ title: '编辑' });
		// 重置有效
		$("#reset").linkbutton('enable');
		//只读
		$('#dict_code,#dict_id').textbox('readonly');
		$("#dict_code,#dict_id").textbox("disableValidation");
		$form.attr("action", $form.attr("action-edit"));
//		$("#dict_id").val($('#data').datagrid('getData').rows[id].dict_id);
		$this.form('clear');
		$this.form('load', $('#data').datagrid('getData').rows[id]);
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

$('#dict_desc').combobox({
	url : '../sys/getDictDesc',
	valueField : 'dict_desc',
	textField : 'dict_desc',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '260'
});