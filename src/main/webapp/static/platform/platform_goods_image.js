dictInfo(1004,"type");
goodsCategory("category_code");
dict(1004);
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
						url : "queryGoodsImage",
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						columns : [ [
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'category_name',
									title : '类别名称'
								},
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'category_code',
									title : '分类编码'
								},
								{
									 sortable : false,
					            	 field : 'type',
					            	 title : '分类图片类型',
					            	 formatter:dictFormatter,
					            	 align: 'center',
					            	 width : 100
								},
								{
									 sortable : false,
					            	 field : 'category_url',
					            	 title : '分类图片地址',
					            	 align: 'center',
					            	 width : 100
								},
								{
									sortable : false,
									field : 'is_order',
									title : '排序',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'category_desc',
									title : '分类图片说明',
									width : parseInt($(this).width() * 0.1)
								},
								{
									field : 'op',
									title : '操作',
									width : 238,
									align : 'center',
									formatter : function(value, row, index) {
										var str = '\
										<a class="btns" href="javascript:;" onclick="update(\'' + index + '\');">修改</a>\
										<a class="btns" href="javascript:;" onclick="del(\'' + row.id + '\',\'' + row.type + '\',\'' + row.category_url + '\');">删除</a>\
									';
										return str;
									}
								} ] ],
						onLoadSuccess : function() {
							/*var imgUrl=$(".datagrid-body td[field='goods_url']");
							imgUrl.mouseover(function(){
								var thisObj=$(this);
								imgUrl.overDialog('<div class="over_dialog_img"><img src="'+thisObj.find("div").html()+'" /></div>');
							});*/
							var imgUrl=$(".datagrid-body td[field='category_url']");
							imgUrl.overDialog('<div class="over_dialog_img"><img src="#" /></div>');
						}
					});
}

//删除方法
function del(id,type,category_url) {
	$.messager.confirm('确认删除', '确定要删除吗?', function (r) {
		if (r) {
			$.ajax({
				url: "delGoodsImage",
				type: "POST",
				data : {id:id,type:type,category_url:category_url},
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
		$("#category_code").textbox('readonly',false);
		$("#category_code").textbox("enableValidation");
		//$("#reset").show();
	} else {
		$this.panel({
			title : '编辑'
		});
		// 重置无效
		$("#reset").linkbutton('disable');
		$form.attr("action", $form.attr("action-edit"));
		$("#category_code").textbox("disableValidation");
		$('#category_code').textbox('readonly');
		$("#id").val($('#data').datagrid('getData').rows[id].id);
	//	document.getElementById("filePickerBtn").style.display="none";
	}
	$this.form('clear');
	$this.form('load', $('#data').datagrid('getData').rows[id]);

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


//添加/修改方法
function upload() {
	var $this = $('#upload');
	$this.window('open');
	
	$this.panel({
		title : '上传图片'
	});
}


//下拉商品名称
function goodsCategory(controlName){
	$('#'+controlName).combobox({
		url : 'getGoodsCategory',
		valueField : 'category_id',
		textField : 'category_name',
		width : '150',
		height : '30',
		editable : false,
		panelHeight : 'auto'
	});
}



$(function(){

	$("#category_code").combobox(
			{
				onSelect : function(record) {
					$("#category_url").textbox("clear");
					var gName=$('#category_code').combobox('getText');
					$("#category_name").attr("value", gName);
				}
			});	
			

});





