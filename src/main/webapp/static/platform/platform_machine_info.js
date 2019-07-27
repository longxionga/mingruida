
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
			url : 'queryMachineInfoPageList',
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
							return "已绑定";
						}else if(value==2){
							return "未绑定";
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

				},

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
					width: 80,
					sortable : true,
					field : 'brindid',
					title : '品牌'
				},*/
				{
					width: 180,
					field : 'op',
					title : '操作',
					align : 'center',
					formatter : function(value, row, index) {
						var str = '\
							<a class="btns" href="javascript:" onclick="querystaff(\''+ index+ '\');">变更机具所属员工</a>\
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

//添加/修改方法
var updateId;
function update(id) {
	console.log(id);
	var $this = $('#update');
	var $form = $this.find("form");
	updateId=id;
	if (updateId == undefined) {
		$('#updateform').form('clear');
		var thisData = {"cz_is_use":1,"tx_is_use":1};
		$this.form('load', thisData);
		$("#resetBtn").linkbutton('enable');
		$this.window({ title: '添加' });
		$form.attr("action", $form.attr("action-add"));
	} else {
		$("#resetBtn").linkbutton('disable');
		$this.window({ title: '编辑' });
		$form.attr("action", $form.attr("action-edit"));
		// $("#id").val($('#data').datagrid('getData').rows[id].id);
		$this.form('clear');
		$this.form('load', $('#data').datagrid('getData').rows[id]);
	}


	$this.window({
		top:($(window).height() - 450) * 0.5+$(document).scrollTop(),
		left:($(window).width() - 680) * 0.5
	});
	$this.window('open');
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

$('#brindnameid').combobox({
	url : '../platform/queryDictBrandRule',
	valueField : 'id',
	textField : 'name',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'
});

/**
 * 查询所有员工信息
 * @param id
 */
//设置员工
function querystaff(id) {
	var user_id = $('#data').datagrid('getData').rows[id].id;
	var brindid = $('#data').datagrid('getData').rows[id].brindid;
	var staffid = $('#data').datagrid('getData').rows[id].staffid;

	var machinecode = $('#data').datagrid('getData').rows[id].machinecode;
	var brindid = $('#data').datagrid('getData').rows[id].brindid;
	//将broker_id 设置到隐藏域
	console.log($('#data').datagrid('getData').rows[id])
	$("#dataID").val(user_id);
	$("#staffid").val(staffid);
	$("#machinecode").val(machinecode);
	$("#brindid").val(brindid);
	//打开用户转移窗口
	$('#broker_id').window({
		top:($(window).height() - 450) * 0.5+$(document).scrollTop(),
		left:($(window).width() - 700) * 0.5
	});
	$('#broker_id').window('open');
	//部门id
	//设置全局参数
	loadAgentDept({'brandid':brindid});
}

function loadAgentDept(param) {
	$('#broker').datagrid({
		title: '',//当前页面标题
		fitColumns: true,//自动列宽
		autoRowHeight: true,//自动行高度
		singleSelect: true,//禁用选择多行
		border: !0,//不显示边框
		striped: true,//条纹行
		fit: true,//自适应高度
		nowrap: true,//单元格内文字禁止换行
		rownumbers: true,//显示行号
		checkOnSelect: false, //只有选择复选框时才选中
		selectOnCheck: false,//选择行时选中复选框
		pagination : !0,// 显示分页
		pageSize : 20,// 每页显示数量 参数必须为pageList的对应
		pageList : [ 20,40,80 ],// 每页显示数量设置参数
		url: "querystaffInfoInfoList",
		queryParams: param,//查询参数
		toolbar: '#toolbar1',//工具栏
		columns: [[
			{
				width: 80,
				sortable : true,
				field : 'brandname',
				title : '品牌'
			},
			{
				width: 100,
				sortable : true,
				field : 'dept_name',
				title : '所属部门'
			},
			{
				width: 80,
				sortable : true,
				field : 'staffcode',
				title : '员工编号'
			},
			{
				width: 100,
				sortable : true,
				field : 'staffname',
				title : '员工名称'
			},
		/*	{
				width: 80,
				sortable : true,
				field : 'bystages',
				title : '分期',
				formatter : function(value) {
					if(value==1){
						return "一期";
					}else if(value==2){
						return "二期";
					}else if(value==3){
						return "三期";
					}else {
						return '<span style="color:#f00;">其他</span>';
					}
				}
			},*/
		/*	{
				width: 100,
				sortable : true,
				field : 'agentcode',
				title : '代理商编号'

			},
			{
				width: 150,
				sortable : true,
				field : 'agentname',
				title : '代理商名称'
			},*/
			{
				width: 100,
				sortable : true,
				field : 'phoneNO',
				title : '手机号码'
			},
			{
				width: 100,
				sortable : true,
				field : 'staffstate',
				title : '状态',
				formatter : function(value) {
					if(value==1){
						return "在职";
					}else if(value==2){
						return "离职";
					}else {
						return '<span style="color:#f00;">其他</span>';
					}
				}
			},
			{
				width: 100,
				sortable : true,
				field : 'position',
				title : '岗位',
				formatter : function(value) {
					if(value==1){
						return "经理";
					}else if(value==2){
						return "主管";
					}else if(value==3 || value==5 || value==6 ){
						return "组员";
					}else if(value==4){
						return "人事";
					}else if(value==9){
						return '<span style="color:#f00;">其他</span>';
					}
				}
			},

			{
				width: parseInt($(this).width() * 0.12),
				field: 'op',
				title: '操作',
				align: 'center',
				formatter: function (value, row, index) {

						var str = '\
			        			   <a class="btns" href="javascript:getSelected(\''+ row.id+ '\');">更换</a>\
									';
						return str;

				}}
		]],
		onLoadSuccess: function () {
			$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
		}
	});
}

function getSelected(id) {
	$.messager.confirm('转移代理商', '是否确认需要转移', function (r) {
		if (r) {
			var machine_id = $('#dataID').val();
			var staffid = $('#staffid').val();

			var machinecode = $('#machinecode').val();
			var brindid = $('#brindid').val();


			$.ajax({
				url: "savaMachaneToStaff",
				type: "POST",
				dataType: "json",
				data: {
					staff_id: id,
					machine_id: machine_id,
					upstaffid: staffid,
					machinecode: machinecode,
					brindid: brindid

				},
				beforeSend: function () {
					$.messager.progress({
						title:'请等待',
						msg:'正在回调...',
						text:'努力中...',
						interval:'600'
					});

				},

				complete: function () {
					$.messager.progress('close');
				},
				success: function (data) {
					$.messager.alert('提示信息', data.msg, 'info');
					if (data.success == true) {
						$("#dataID").val(null);
						$("#staffid").val(null);
						$("#machinecode").val(null);
						$("#brindid").val(null);
						$('#broker_id').dialog('close');
						var $this=$('#broker_id');
						var param = getFormJson(this);
						loadData(param);//查询数据
					}
				}

			});

		}
	});
}

$("#search1").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);

	loadAgentDept(param);//查询数据
	return false;
});


//机具机具信息导出
function exportExcel() {
	$.messager.confirm('导入Excel', '是否导出索检到的数据？', function(r) {
		if (r) {
			var brindnameid=$("#brindnameid").combobox("getValue");
			var batchcode=$("#batchcode").textbox("getValue");

			var machinecode11=$("#machinecode11").textbox("getValue");
			var staffname11=$("#staffname11").textbox("getValue");

			// var machineSN=document.getElementById("machineSN").value;
			// var machinecode=document.getElementById("machinecode").value;
			// var staffname111=document.getElementById("staffname111").value;
			// var isbound=$('#isbound').combobox('getValue');//获取combobox的value方法

			$.ajax({
				url : "queryMachineReportForExcel",
				type : "POST",
				dataType : "json",
				cache:false,
				data : {
					brindnameid:brindnameid,
					batchcode:batchcode,
					machinecode:machinecode11,
					staffname:staffname11
				},
				beforeSend: function () {
					$.messager.progress({
						title:'请等待',
						msg:'正在导出...',
						text:'努力中...',
						interval:'600'
					});

				},

				complete: function () {
					$.messager.progress('close');
				},
				success : function(msg) {
					if(msg.num>0){
						window.location.href= msg.url;
					}else{
						$.messager.alert('提示信息', '导出失败，查询无数据');
					}
				}
			});
		}
	});
}