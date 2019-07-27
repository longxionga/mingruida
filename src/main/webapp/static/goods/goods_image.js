dictInfo(1014,"img_type");
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
						url : "queryGoodsImage",
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						columns : [ [
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'goods_name',
									title : '机具名称'
								},
								{
									 sortable : false,
					            	 field : 'img_type',
					            	 title : '机具图片类型',
					            	 align: 'center',
					            	 formatter:dictFormatter,
					            	 width : 100
								},
								{
									 sortable : false,
					            	 field : 'img_uri',
					            	 title : '图片链接',
					            	 align: 'center',
					            	 width : 100
								},
								{
									sortable : false,
									field : 'img_seq',
									title : '排序',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'create_time',
									title : '创建时间',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'update_time',
									title : '更新时间',
									width : parseInt($(this).width() * 0.1)
								},
								{
					            	 field : 'is_use',
					            	 width : 50,
					            	 title : '是否可用',
					            	 align : 'center',
					            	 formatter: function (value, row, index) {
					            		 if (value == 1)
					            			 return '是';
					            		 else
					            			 return '<span style="color:#f00;">否</span>';
					            	 }
					             },
								{
									field : 'op',
									title : '操作',
									width : 238,
									align : 'center',
									formatter : function(value, row, index) {
										 var str = '\<a class="btns" href="javascript:;" onclick="update(\'' +index  + '\');">修改</a>\
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
							var imgUrl=$(".datagrid-body td[field='img_uri']");
							imgUrl.overDialog('<div class="over_dialog_img"><img src="#" /></div>');
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
		$("#goods_id").textbox('readonly',false);
		$("#goods_id").textbox("enableValidation");
	} else {
		$this.panel({
			title : '编辑'
		});
		// 重置无效
		$("#reset").linkbutton('disable');
		$form.attr("action", $form.attr("action-edit"));
		$("#goods_id").textbox("disableValidation");
		$('#goods_id').textbox('readonly');
		$("#img_id").val($('#data').datagrid('getData').rows[id].img_id);
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

	$('#goods_id').combobox({
		url : '../goods/queryGoodsName',
		valueField : 'goods_id',
		textField : 'goods_name',
		width : '150',
		height : '30',
		editable : false,
		panelHeight : '200',
		multiple:false
	});




