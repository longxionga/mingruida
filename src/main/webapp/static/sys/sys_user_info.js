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
						url : "queryUserInfo",
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						columns : [ [
								{
									sortable : false,
						        	width: 100,
									field : 'user_name',
									title : '登录名称'
								},
								{
									sortable : false,
									width: 100,
									field : 'user_nick_name',
									title : '昵称'
								},
								{
									sortable : false,
									width: 50,
									align: 'center',
									field : 'user_dict_gender',
									title : '用户性别'
								},
								{
									sortable : false,
									align: 'center',
									width: 100,
									field : 'user_mobile',
									title : '联系电话'
								},
								{
									sortable : false,
									align: 'center',
									width: 100,
									field : 'user_qq',
									title : 'QQ'
								},
								{
									sortable : false,
									width: 100,
									field : 'dept_name',
									title : '部门名称'
								},
								{
									sortable : false,
									width: 50,
									align: 'center',
									field : 'is_use',
									title : '是否可用',
									formatter : function(value) {
										if(value==1){
											return "是";
										}else if(value==0){
											return '<span style="color:#f00;">否</span>';
										}
									}
								},
								{
									sortable : false,
									width: 100,
									field : 'create_date',
									title : '创建时间'
								},
								{
									width: 100,
									field : 'op',
									title : '操作',
									align : 'center',
									formatter : function(value, row, index) {
										var str = '\
										<a class="btns" href="javascript:;" onclick="update(\'' + index + '\');">编辑</a>\
										<a class="btns" href="javascript:;" onclick="resetPwd(\'' + index + '\');">密码重置</a>\
									';
										return str;
									}
								} ] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}

// 删除方法
function del(id) {
	selects = [ id ];
	if (id == undefined) {
		// 返回所有选中的记录
		selects = $('#data').datagrid('getChecked');
	}

	if (selects.length == 0) {
		$.messager.alert('提示', '您选中的记录数为0，请选择后再进行删除操作！', 'error');
		return false;
	}

	//添加/修改方法
	function upload() {
		var $this = $('#upload');
		$this.window('open');
		
		$this.panel({
			title : '上传图片'
		});
	}
	
	// 把选中后的id取出保存为数组型字符串
	var id = [];
	$.each(selects, function(i, k) {
		id.push(k.itemid);
	});
	id = id.join(",");


	$.messager.confirm('确认删除', '你选中了' + selects.length + '条记录, 确定要删除吗?',
			function(r) {
				if (r) {
					$.ajax({
						url : "delUserinfo",
						type : "POST",
						data : data,
						success : function(data) {
							if (data.success == true) {
								window.location.reload();
							} else {
								$.messager.alert('删除提示', data.msg, 'error');
							}
						}
					});
				}
			});
}

// 添加/修改方法
var updateId;
function update(id) {
	var $this = $('#update');
	var $form = $this.find("form");
	updateId=id;
	//$this.form('clear');
	if (updateId == undefined) {
		$this.form('clear');
		var thisData = {"is_use":1};
		$this.form('load', thisData);
		$this.window({
			title : '添加'
		});
		/*$this.closest(".window").css({
			top: ($(window).height() - $this.closest(".window").outerHeight) * 0.5,
			left:($(window).width() - $this.closest(".window").outerWidth) * 0.5
		});*/
		// 重置有效
		$("#reset").linkbutton('enable');
		$form.attr("action", $form.attr("action-add"));
		$("#is_use").combobox('setValue',1);
		$("#user_name").textbox('readonly',false);
		$("#user_name").textbox("enableValidation");
		
		//$("#reset").show();
	} else {
		$this.window({
			title : '编辑'
		});
		
		$form.attr("action", $form.attr("action-edit"));
		$("#user_name").textbox("disableValidation");
		$('#user_name').textbox('readonly');
		//$("#user_id").val($('#data').datagrid('getData').rows[id].user_id);
		//$("#user_dict_gender").combobox('setValue',1);
		$this.form('clear');
		$this.form('load', $('#data').datagrid('getData').rows[id]);
	}
	
	
	$this.window({
    	top:($(window).height() - 335) * 0.5+$(document).scrollTop(),   
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



//密码重置
// 密码重置二次弹框
function resetPwd(id){
	$.messager.confirm('重置密码', '是否确认重置密码',
			function(r) {
				if (r) {				
					var user_id = $('#data').datagrid('getData').rows[id].user_id;
					$.ajax({
						url : "resetPwd",
						type : "POST",
						dataType:"json",
						data : {
							user_id:user_id
						},
						success : function(data){
				            $.messager.alert('提示信息', data.msg, 'info');
						}
					});
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
loadData({order_type:queryDescOrAsc.create_date});//初始化加载数据