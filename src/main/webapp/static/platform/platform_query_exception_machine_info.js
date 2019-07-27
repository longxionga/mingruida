
function loadData(param) {
	var boolFitColumns=true;
	if($(document).width()<=1600-220){
		boolFitColumns=false;
	}
	$('#data').datagrid(

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
			pageList : [ 20,40,80 ],// 每页显示数量设置参数
			idField:'id',
			url : 'queryExceptionMachineInfoPageList',
			remoteSort:false,
			queryParams : param,// 查询参数
			toolbar : '#toolbar',// 工具栏
			columns : [ [
				{
					checkbox : true,
					align : 'center',
					width : 20,
					//hidden:true,
					field : 'ck',
					title : '选择项'
				},
				{
					width: 60,
					sortable : true,
					field : 'brindname',
					title : '所属品牌'
				},
				{
					width: 80,
					sortable : true,
					field : 'batchcode',
					title : '批次号'
				},
				{
					width: 80,
					sortable : true,
					field : 'machineSN',
					title : '机具编号'
				},
				{
					width: 80,
					sortable : true,
					field : 'machinecode',
					title : '机具序列号'
				},
				{
					width: 80,
					sortable : true,
					field : 'create_time',
					title : '创建时间',
					formatter: dateTimeFormatter
				},
				{
					width: 80,
					sortable : true,
					field : 'update_time',
					title : '更新时间',
					formatter: dateTimeFormatter
				},
				{
					width: 100,
					sortable : true,
					field : 'staffcode',
					title : '归属员工编号'

				},
				{
					width: 120,
					sortable : true,
					field : 'staffname',
					title : '归属员工名称'

				},
				{
					width: 50,
					sortable : true,
					field : 'isbound',
					title : '是否绑定',
					formatter : function(value) {
						if(value==1){
							return "是";
						}else if(value==2){
							return "否";
						}else if(value==3){
							return "已分配服务商";
						}else {
							return "";
						}
					}
				},
				{
					width: 50,
					sortable : true,
					field : 'machinetype',
					title : '机具类型'
				},

				{
					width: 80,
					sortable : true,
					field : 'ifdeposit',
					title : '是否已缴纳押金'

				},
				{
					width: 180,
					field : 'op',
					title : '操作',
					align : 'center',
					formatter : function(value, row, index) {
						var str = '\
							<a class="btns" href="javascript:" onclick="remoremachine(\''+ row.id+ '\');">机具删除</a>\
									';
						return str;
					}
				}
			] ],
			onLoadSuccess : function() {
				$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
			}
		});
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



//机具批量移除
function remoremachine(id){
	$.messager.confirm('机具删除', '是否删除机具', function(r) {
		if (r) {
			$.ajax({
				url : "removeMachineInfo",
				type : "POST",
				dataType : "json",
				cache:false,
				data : {
					machine_id:id
				},
				beforeSend: function () {
					$.messager.progress({
						title:'请等待',
						msg:'正在删除...',
						text:'努力中...',
						interval:'600'
					});

				},

				complete: function () {
					$.messager.progress('close');
				},
				success : function(data) {
					$.messager.alert('提示信息', data.msg, 'info');
					$('#search').submit();
				}
			});

		}
	});
}

//批量删除机具
function brachremore(){
	if($('#data').datagrid('getChecked').length==0){
		$.messager.alert('提示', '请先勾选删除的机具!', 'error');
		return;
	}
	$.messager.confirm('提示信息', '是否删除这些机具?', function(r){
		if (r){
			$.messager.progress({
				title:'请等待',
				msg:'正在发送请求...',
				timeout:5000
			});
			/************经纪人id和姓名*****************/
			var orders=$('#data').datagrid('getChecked');
			var order_ids="";
			for(var i=0;i<orders.length;i++){
				if(i==(orders.length-1)){
					order_ids=order_ids+orders[i].id;
				}else{
					order_ids=order_ids+orders[i].id+",";
				}
			}
			$.ajax({
				type: "POST",
				url: "removeMachineInfoAll",
				data: {
					order_ids:order_ids
				},
				dataType: 'json',
				async: true,
				success: function(msg){
					$.messager.progress("close");
					$('#search').submit();
					$.messager.alert('提示信息', '删除成功');
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

$('#brindnameid').combobox({
    url : '../platform/queryDictBrandRule',
    valueField : 'id',
    textField : 'name',
    width : '150',
    height : '30',
    editable : false,
    panelHeight : '200'
});

//调拨方式
$('#ischange').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '1',
		value : '需要确认是否移除的数据',
        selected:true

	},{
		label : '2',
		value : '已更新的数据'
	} ]


});


