dictInfo(1018,"is_use");
dictInfo(1017,"number_type");
dictInfo(1019,"group_type");
dictInfo(1017,"number_types");
dictInfo(1019,"group_types");
dict(1018);
function loadData(param) {
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
						url : "queryNumberInfo",
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						columns : [ [
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'number_code',
									title : '靓号'
								},
								{
									sortable : true,
									width : parseInt($(this).width() * 0.1),
									field : 'number_money',
									title : '价格'
								},
								{
									 sortable : true,
					            	 field : 'number_type',
					            	 title : '靓号类型',
					            	 formatter: function (value, row, index) {
						        		   if (value == 1)
						        			   return 'AAAAAA';
						        		   else if (value == 2)
						        			   return 'AAABBB';	
						        		   else if (value == 3)
						        			   return 'AABBCC';	
						        		   else if (value == 4)
						        			   return 'ABABAB';	
						        		   else if (value == 5)
						        			   return 'ABCABC';	
						        		   else if (value == 0)
						        			   return '自定义';	
					            	 },
					            	 align: 'center',
					            	 width : 100
								},
								{
									 sortable : true,
					            	 field : 'group_type',
					            	 title : '组合类型',
					            	 formatter: function (value, row, index) {
						        		   if (value == 1)
						        			   return '纯字母组合';
						        		   else if (value == 2)
						        			   return '纯数字组合';	
						        		   else if (value == 3)
						        			   return '字母数字组合';
					            	 },
					            	 align: 'center',
					            	 width : 100
								},
								{
									 sortable : true,
					            	 field : 'number_level',
					            	 title : '靓号等级',
					            	 formatter: function (value, row, index) {
						        		   if (value == 1)
						        			   return '顶级靓号';
						        		   else if (value == 2)
						        			   return '高级靓号';	
						        		   else if (value == 3)
						        			   return '普通靓号';
					            	 },
					            	 align: 'center',
					            	 width : 100
								},
								{
									 sortable : true,
					            	 field : 'operate_date',
					            	 title : '出售时间',
					            	 align: 'center',
					            	 width : 100
								},
								{
									sortable : true,
									field : 'create_date',
									title : '创建时间',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'is_use',
									title : '状态',
									formatter:dictFormatter,
									width : parseInt($(this).width() * 0.1)
								},
								{
									field : 'op',
									title : '操作',
									width : 238,
									align : 'center',
									formatter : function(value, row, index) {
										if(row.is_use!=2){
											var str = '\
												<a class="btns" href="javascript:;" onclick="update(\'' + index + '\');">修改</a>\
												<a class="btns" href="javascript:;" onclick="del(\'' + row.number_id + '\');">删除</a>\
											';
												return str;	
										}else{
											var str="";
											return str;
										}
										
									}
								} ] ],
						onLoadSuccess : function() {
							
						}
					});
}

//删除方法
function del(id) {   
	$.messager.confirm('确认删除', '确定要删除吗?', function (r) {
		if (r) {
			$.ajax({
				url: "delNumberInfo",
				type: "POST",
				data : {number_id:id},
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

// 添加/修改方法
function update(id) {
	var $this = $('#update');
	var $form = $this.find("form");
	$this.window('open');
	if (id == undefined) {
		$this.panel({
			title : '添加'
		});
		// 重置有效
		$("#reset").linkbutton('enable');		
		$form.attr("action", $form.attr("action-add"));
		$("#number_code").textbox('readonly',false);
		//$("#num").combobox("enableValidation");
		$('#sum').textbox('readonly',false);
		$("#num").combobox('readonly',false);
		//$("#number_code").textbox("enableValidation");
			
	} else {
		$this.panel({
			title : '编辑'
		});
		// 重置无效
		$("#reset").linkbutton('disable');
		$form.attr("action", $form.attr("action-edit"));
		$("#num").combobox("disableValidation");
		$('#sum').textbox('readonly',true);
		$("#num").combobox('readonly',true);
		//$("#sum").textbox('setValue', '1');	
		$("#number_id").val($('#data').datagrid('getData').rows[id].number_id);
	}
	$this.form('clear');
	$this.form('load', $('#data').datagrid('getData').rows[id]);
	
	if (id != undefined) {
		$("#sum").textbox('setValue', '1');	
	}
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






$(function(){
	/*//判断账号类型，账号类型为S时默认为非批量增加，且靓号需要自定义为必填
	$("#number_type").combobox(
			{
				onSelect : function(record) {
					
					var type=$('#number_type').combobox('getValue');
					if(type==3){
						$("#num").combobox("select", "one");
						$("#num").combobox('readonly',true);						
						$("#sum").textbox('setText', "1");
						$('#number_code').validatebox({ required:true }); 
					}
					
				}
			});	
	//判断是否批量增加，如果批量增加，则账号类型选择不能有s类，且不能输入数量，如果已有数量则改为1
	$("#num").combobox(
			{
				onSelect : function(record) {
					var num=$('#num').combobox('getText');
					var type=$('#number_type').combobox('getText');
					if(num=='more'){
						if(type==3){
							$("#number_type").combobox("clear");
							$("#sum").attr("value", "1");
						}
					}
				}
			});*/	
			
	$("#num").combobox(
			{
				onSelect : function(record) {
					
					var num=$('#num').combobox('getValue');
					if(num=="one"){
						$("#sum").textbox('readonly',true);						
						$("#sum").textbox('setValue', '1');	
						$("#number_code").textbox('readonly',false);

					}else{
						$("#sum").textbox('readonly',false);	
						$("#number_code").textbox("clear");
						$("#number_code").textbox('readonly',true);						
						//$('#sum').validatebox({ required:true }); 
					}
					
				}
			});
});

//提现方式
$('#number_level').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '1',
		value : '顶级靓号'
	},{
		label : '2',
		value : '高级靓号'
	},{
		label : '3',
		value : '普通靓号'
	} ]
});

loadData({order_type:queryDescOrAsc.create_date});//初始化加载数据