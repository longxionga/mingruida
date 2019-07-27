function loadData(param, url) {
		
	$('#data')
			.datagrid(
					{
						title : '',// 当前页面标题
						fitColumns : true,// 自动列宽
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
						pageList : [ 20, 40, 80 ],// 每页显示数量设置参数
						url : url,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						columns :[[
						        {
								    field : 'user_name',
									title : '用户名',
									width: 100
						    	},
						    	{
									align: 'right',
									field : 'user_money',
									title : '余额',
									width: 100
								},
								{
						    		align: 'center',
									field : 'user_mobile',
									title : '手机号',
									width: 100
								},
								{
									field : 'create_date',
									title : '创建时间',
									width: 100,
									formatter : dateTimeFormatter
								},
								{
									width : 50,
									field : 'is_use',
									title : '是否有效',
									align : 'center',
									formatter : function(value) {
										if(value==1){
											return '是';
										}else if(value==0){
											return '<span style="color:#f00;">否</span>';
										}
									}
								},
								{
									field : 'op',
									title : '操作',
									width: 100,
									align : 'center',
									formatter : function(value, row, index) {
										var str = '\
										   <a class="btns" href="javascript:;" onclick="update(\'' + index + '\');">编辑</a>\
										   <a class="btns" href="javascript:;" onclick="reset(\'' + index + '\');">密码重置</a>\
										';
										return str;
									}
								 }
					            ]],
							onLoadSuccess : function() {
								$("head").append(
											"<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}

function reset(id) {
	$.messager.confirm('重置密码', '是否确认重置密码(888888)', function(r) {
		if (r) {
			var user_id = $('#data').datagrid('getData').rows[id].user_id;
			$.ajax({
				url : "resetSimPwd",
				type : "POST",
				dataType : "json",
				data : {
					user_id : user_id
				},
				success : function(data) {
					$.messager.alert('提示信息', data.msg, 'info');
				}
			});
		}
	});
}


var updateId= null ;
// 添加/修改方法
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
		$("#user_name").textbox('readonly',false);
		$("#user_name").textbox("enableValidation");
		
		$("#user_mobile").textbox('readonly',false);
		$("#user_mobile").textbox("enableValidation");
		//$("#reset").show();
	} else {
		$this.window({
			title : '编辑'
		});
		
		$form.attr("action", $form.attr("action-edit"));
		$("#user_name").textbox("disableValidation");
		$('#user_name').textbox('readonly');
		
		$("#user_mobile").textbox("disableValidation");
		$('#user_mobile').textbox('readonly');
		
		$("#user_id").val($('#data').datagrid('getData').rows[id].user_id);
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
		$('#updateform').form('load', thisData);
	}else{
		update(updateId);	
	}	
});

         
//刷新功能
$("#refresh").click(function(){
    $("#broker_name,#broker_mobile").textbox('setValue','');
	$('#search1').submit();
});

$("#search").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param, "querySimUserlogin");// 查询数据
	return false;
});

$("#decryption").click(function(){
	$("#dataID").val("decryption");
	$("#search").submit();
});






loadData('','querySimUserlogin');//初始化加载数据

