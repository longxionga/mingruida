function loadData(param) {
	$('#data').treegrid(
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
				url : "queryGoodsCategory",
				queryParams : param,// 查询参数
				toolbar : '#toolbar',// 工具栏
				idField:'category_id',
				treeField: 'category_name',
				columns : [ [
				              {
				            	 width : 250,
				            	 field : 'category_name',
				            	 title : '类目名称'
				             },
				            /* {
				            	 width : 200,
				            	 field : 'category_level',
				            	 title : '类目等级'
				             },*/
				             {
				            	 width : 200,
				            	 field : 'create_time',
				            	 title : '创建时间'
				             },	
				             {
				            	 field : 'update_time',
				            	 width : 50,
				            	 title : '更新时间',
			            		 align : 'center'
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
				            	 width : 150,
				            	 title : '操作',
				            	 align : 'center',
				            	 formatter : function(value, row, index) {
				            		 if(row.menu_id=="-1"){
				            			 var str = '\<a class="btns" href="javascript:;" onclick="addChild(\'' + row.category_id  + '\',\'' + row.category_level  + '\');">添加子菜单</a>\
					            			 ';
					            		 return str;
				            		 }else{
				            			 var str = '\
					            			 <a class="btns" href="javascript:;" onclick="update(\'' + row.category_id + '\',\''+ row.is_use +'\',\''+ row.category_name +'\');">编辑</a>\
					            			 <a class="btns" href="javascript:;" onclick="addChild(\'' + row.category_id  + '\',\'' + row.category_level  + '\');">添加子菜单</a>\
					            			 ';
					            		 return str;
				            		 }
				            	 }
				             } ] ],
				             onLoadSuccess : function() {
				            	 $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
				             }
			});
}



//添加/修改方法
var updateId;
function update(category_id,is_use,category_name) {
	var $this = $('#update');
	var $form = $this.find("form");
	updateId=category_id;
	if (updateId == undefined) {
		$('#updateform').form('clear');
		$("#superior_id").val(0);
		$("#category_level").val(1);
		$this.form('load', thisData);
		$("#reset").linkbutton('enable');
		$this.window({ title: '添加' });		
		$form.attr("action", $form.attr("action-add"));
	} else {
		$("#reset").linkbutton('disable');
		$this.window({ title: '编辑' });
		$form.attr("action", $form.attr("action-edit"));
		$this.form('clear');
		var thisData = {"is_use":is_use,"category_id":category_id,"category_name":category_name};
		$this.form('load', thisData);
	}
	$this.window({
    	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 680) * 0.5
    });
	$this.window('open');
}


//添加子菜单
function addChild(superior_id,category_level){
	
	var $this = $('#update');
	var $form = $this.find("form");
	$this.form('clear');
	
	$this.window({
		title : '添加'
	});
	// 重置有效
	$("#reset").linkbutton('enable');
	$form.attr("action", $form.attr("action-add"));	
	$("#category_name").textbox("enableValidation");
	$("#superior_id").val(superior_id);
	$("#category_level").val(category_level);
	
	$this.window({
    	top:($(window).height() - 289) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 588) * 0.5
    });
	$this.window('open');
}
loadData({order_type:queryDescOrAsc.create_date});//初始化加载数据