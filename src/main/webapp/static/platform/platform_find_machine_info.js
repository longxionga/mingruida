
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
			url : 'findMachineInfoPageLists',
			remoteSort:false,
			queryParams : param,// 查询参数
			toolbar : '#toolbar',// 工具栏
			columns : [ [
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
					width: 100,
					sortable : true,
					field : 'staffcode',
					title : '归属员工编号'

				},
				{
					width: 120,
					sortable : true,
					field : 'bystages',
					title : '归属员工名称'

				},
				{
					width: 100,
					sortable : true,
					field : 'attagentcode',
					title : '归属代理商编号'

				},
				{
					width: 120,
					sortable : true,
					field : 'attagentname',
					title : '归属代理商名称'

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
				/*{
					width: 50,
					sortable : true,
					field : 'machinemodel',
					title : '机具型号'

				},*/
				/*{
					width: 50,
					sortable : true,
					field : 'warehousingtype',
					title : '激活类型'
				},*/
				/*{
					width: 100,
					sortable : true,
					field : 'merchantcode',
					title : '商户编号'
				},
				{
					width: 120,
					sortable : true,
					field : 'merchantname',
					title : '商户名称'

				},*/

				{
					width: 80,
					sortable : true,
					field : 'ifdeposit',
					title : '是否已缴纳押金'

				}

				/*{
					width: 80,
					sortable : true,
					field : 'machineryT1rate',
					title : '机具T1费率(%)'
				},
				{
					width: 80,
					sortable : true,
					field : 'machineryT0rate',
					title : '机具T0费率(%)'
				},
				{
					width: 80,
					sortable : true,
					field : 'machineryamount',
					title : '机具提现费(元)'
				},*/
				/*{
					width: 180,
					field : 'op',
					title : '操作',
					align : 'center',
					formatter : function(value, row, index) {
						var str = '\
							<a class="btns" href="javascript:" onclick="machineCallbackSingle(\''+ row.id+ '\');">机具回调</a>\
									';
						return str;
					}
				}*/
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

//为重置按钮添加单击事件
$("#reset2").click(function(){
	if (updateId == undefined) {
		$('#updateformhd').form('clear');
		var thisData = {"is_use":1};
		$('#updatehd').form('load', thisData);
	}else{
		update(updateId);
	}
});


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

$('#brindnameid').combobox({
	url : '../platform/queryDictBrandRule',
	valueField : 'id',
	textField : 'name',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'
});

$("#search1").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);

	loadAgentDept(param);//查询数据
	return false;
});


