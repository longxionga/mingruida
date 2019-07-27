goodsName(1015,"goods_code");
goodsPro(1016,"goods_pro");
//goodsCode("goods_code");
dict(1014);
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
						url : "queryGoodsInfo",
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						columns : [ [
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'goods_name',
									title : '商品名称'
								},
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'goods_code',
									title : '商品编码'
								},
								{
									 sortable : false,
					            	 field : 'goods_pro',
					            	 title : '图片属性',
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
									field : 'goods_desc',
									title : '说明',
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
										<a class="btns" href="javascript:;" onclick="del(\'' + row.goods_id + '\');">删除</a>\
									';
										return str;
									}
								} ] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}

//删除方法
function del(goods_id) {         	
	$.messager.confirm('确认删除', '确定要删除吗?', function (r) {
		if (r) {
			$.ajax({
				url: "delGoodsInfo",
				type: "POST",
				data : {goods_id:goods_id},
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
		$("#goods_code").textbox('readonly',false);
		$("#goods_code").textbox("enableValidation");
		//$("#reset").show();
	} else {
		$this.panel({
			title : '编辑'
		});
		// 重置无效
		$("#reset").linkbutton('disable');
		$form.attr("action", $form.attr("action-edit"));
		$("#goods_code").textbox("disableValidation");
		$('#goods_code').textbox('readonly');
		$("#goods_id").val($('#data').datagrid('getData').rows[id].goods_id);
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



$(function(){

	$("#goods_code").combobox(
			{
				onSelect : function(record) {
					var gName=$('#goods_code').combobox('getText');
					$("#goods_name").attr("value", gName);
				}
			});	
	
	$("#goods_pro").combobox(
			{
				onSelect : function(record) {
					var gName=$('#goods_pro').combobox('getText');
					$("#goods_pros").attr("value", gName);
				}
			});	
			

});

//下拉菜单字典
function goodsPro(dict_code,controlName){	
	$('#'+controlName).combobox({
		url : '../sys/getDict?dict_code='+dict_code,
		valueField : 'dict_name',
		textField : 'dict_name',
		width : '150',
		height : '30',
		editable : false,
		panelHeight : '100',
		multiple:false
	});
}

function goodsName(dict_code,controlName){	
	$('#'+controlName).combobox({
		url : '../platform/getGoodsName?dict_code='+dict_code,
		valueField : 'dict_id',
		textField : 'dict_name',
		width : '150',
		height : '30',
		editable : false,
		panelHeight : 'auto',
		multiple:false
	});
}





