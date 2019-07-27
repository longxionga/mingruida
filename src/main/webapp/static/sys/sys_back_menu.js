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
				url : "queryBackMenu",
				queryParams : param,// 查询参数
				toolbar : '#toolbar',// 工具栏
				idField:'menu_id',
				treeField: 'menu_name',
				columns : [ [
				             {
				            	 width : 250,
				            	 field : 'menu_name',
				            	 title : '菜单名称'
				             },
				             {
				            	 width : 200,
				            	 field : 'menu_url',
				            	 title : '菜单地址'
				             },	
				             {
				            	 width : 200,
				            	 field : 'menu_icon',
				            	 title : '菜单图标'
				             },	
				             {
				            	 field : 'menu_index',
				            	 width : 50,
				            	 title : '排序',
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
				            			 var str = '\<a class="btns" href="javascript:;" onclick="addChild(\'' + row.menu_id + '\');">添加子菜单</a>\
					            			 ';
					            		 return str;
				            		 }else{
				            		 var str = '\
				            			 <a class="btns" href="javascript:;" onclick="update(\'' + row.menu_id + '\');">编辑</a>\
				            			 <a class="btns" href="javascript:;" onclick="addChild1(\'' + row.menu_id  + '\');">添加子菜单</a>\
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

//删除方法
function del(menu_id) {         	
	$.messager.confirm('确认删除', '确定要删除吗?', function (r) {
		if (r) {
			$.ajax({
				url: "deleteBackMenu",
				type: "POST",
				data: { menu_id: menu_id},
				success: function (data) {
					if (data.success == true) {
						$.messager.alert('提示信息', data.msg, 'info');
						$('#data').treegrid('reload');
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
var updateIds;
function update(id) {
	
	var $this = $('#update');
	var $form = $this.find("form");
	$this.form('clear');
	updateIds=id;
   if (updateIds == undefined) {
		$this.window({
			title : '添加'
		});
		
		//添加默认值
		setDefaulVal();
		
		// 重置有效
		$("#reset").linkbutton('enable');
		$form.attr("action", $form.attr("action-add"));	
		$("#menu_name").textbox("enableValidation");	
		//$this.form('clear');
		$("#menu_parent_id").val("-1");
	} else {
		$this.window({
			title : '编辑'
		});		
		// 重置无效
		//$("#reset").linkbutton('disable');
		$("#reset").linkbutton('enable');	
		$this.form('clear');
		$("#menu_name").textbox('readonly',false);
		$form.attr("action", $form.attr("action-edit"));
		$("#menu_name").textbox("disableValidation");				
		$("#menu_id").val(id);
	
		$this.form('load', 'queryBackMenuInfo?menu_id='+id);
	}

	$this.window({
    	top:($(window).height() - 289) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 588) * 0.5
    });
	$this.window('open');
}

//添加子菜单
function addChild1(id){
	
	var $this = $('#update');
	var $form = $this.find("form");
	updateIds = id+"adds";
	updateId=id;
	$this.form('clear');
	//添加默认值
	setDefaulVal();
	
	$this.window({
		title : '添加'
	});
	// 重置有效
	$("#reset").linkbutton('enable');
	
	$form.attr("action", $form.attr("action-add"));	
	$("#menu_name").textbox("enableValidation");
	$("#menu_parent_id").val(id);
	
	$this.window({
    	top:($(window).height() - 289) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 588) * 0.5
    });
	$this.window('open');
}
//添加子菜单
function addChild(id){
	var $this = $('#update');
	var $form = $this.find("form");
	updateIds=id;
	$this.form('clear');
	//添加默认值
	setDefaulVal();
	
	$this.window({
		title : '添加'
	});
	// 重置有效
	$("#reset").linkbutton('enable');
	
	$form.attr("action", $form.attr("action-add"));	
	$("#menu_name").textbox("enableValidation");
	$("#menu_parent_id").val(id);
	
	$this.window({
    	top:($(window).height() - 289) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 588) * 0.5
    });
	$this.window('open');
}


$("#reset").click(function(){
	if (updateIds == updateId +"adds") {
		$('#updateform').form('clear');
		setDefaulVal();
	}else if(updateIds == "-1"){
		$('#updateform').form('clear');	
		setDefaulVal();
	}else{
		update(updateIds);
	}	
});

//设置默认值的方法
function setDefaulVal(){
	var $this = $('#update');
	var thisData = {"is_use":1,"is_leaf":1};
	$('#update').form('load', thisData);
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
