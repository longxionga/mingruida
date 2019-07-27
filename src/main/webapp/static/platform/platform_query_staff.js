dictInfo(1030,'position_yg');
// dictInfo(1030,'position');
dictInfo(1031,'staffstate');
function loadData(param) {
	var boolFitColumns=true;
	if($(document).width()<=1600-220){
		boolFitColumns=false;
	}
	$('#data').datagrid(
		{
			title : '',// 当前页面标题
			toolbar: [{
				iconCls: 'icon-edit',
				handler: function(){alert('edit')}
			},'-',{
				iconCls: 'icon-help',
				handler: function(){alert('help')}
			}],
			fitColumns : boolFitColumns,// 自动列宽
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
			url : 'queryStaffInfoPageList',
			remoteSort:false,
			queryParams : param,// 查询参数
			toolbar : '#toolbar',// 工具栏
			columns : [ [
                {
                    width: 100,
                    sortable : true,
                    field : 'brandname',
                    title : '品牌'

                },
				{
					width: 120,
					sortable : true,
					field : 'dept_name',
					title : '所属部门'
				},
			/*	{
					width: 100,
					sortable : true,
					field : 'staffcode',
					title : '员工编号'
				},*/
				{
					width: 80,
					sortable : true,
					field : 'staffname',
					title : '员工名称'
				},
				/*{
					width: 60,
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
				// {
				// 	width: 100,
				// 	sortable : true,
				// 	field : 'agentcode',
				// 	hidden:false,
				// 	title : '代理商编号'
				//
				// },
				// {
				// 	width: 150,
				// 	sortable : true,
				// 	field : 'agentname',
				// 	title : '代理商名称'
				// },

				{
					width: 120,
					sortable : true,
					field : 'logincode',
					title : '登陆账号'
				},

				{
					width: 60,
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
				/*{
					width: 120,
					sortable : true,
					field : 'startdate',
					title : '员工的入职时间',
					formatter: dateTimeFormatter
				},
				{
					width: 80,
					sortable : true,
					field : 'enddate',
					title : '员工的离职时间',
					formatter: dateTimeFormatter
				},*/
				{
					width: 60,
					sortable : true,
					field : 'position',
					title : '岗位',
					formatter : function(value) {
						if(value==1){
							return "经理";
						}else if(value==2){
							return "主管";
						}else if(value==3){
							return "组员";
						}else if(value==4){
							return "人事";
						}else if(value==5){
							return "组员";
						}else if(value==6){
							return "组员";
						}else if(value==9){
							return '<span style="color:#f00;">其他</span>';
						}
					}
				},
				{
					width: 60,
					sortable : true,
					field : 'isstate',
					title : '员工状态',
					formatter : function(value) {
						if(value==1){
							return "正常";
						}else if(value==2){
							return '<span style="color:#f00;">禁用</span>';
						}else {
							return '<span style="color:#f00;">其他</span>';
						}
					}
				},
				/*{
					width: 80,
					sortable : true,
					field : 'remarks',
					title : '备注信息'
				},*/
				{
					width: 150,
					field : 'op',
					title : '操作',
					align : 'center',
					formatter : function(value, row, index) {
						if (row.istart==1) {
							var str = '\
							<span class="btns" style="color: red" >不可编辑</span>\
									';
							return str;
						}else {
							if (row.position==3){
								if (row.shengzhistate == 1){
									var str = '\
									<a class="btns" href="javascript:" onclick="update(\''+ index+ '\');">编辑</a>\
									<a class="btns" href="javascript:" style="color: red">已升职</a>\
											';
									return str;
								} else {
									var str = '\
									<a class="btns" href="javascript:" onclick="update(\''+ index+ '\');">编辑</a>\
									<a class="btns" href="javascript:" onclick="app(\''+ index+ '\');">组员升职</a>\
											';
									return str;
								}

							} else {
							var str = '\
							<a class="btns" href="javascript:" onclick="update(\''+ index+ '\');">编辑</a>\
									';
								return str;
							}

						}

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

$("#search").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param);//查询数据
	return false;
});
loadData();//初始化加载数据


$('#branch_name').combobox({
	url : '../platform/querystaffCompanyInfo',
	valueField : 'id',
	textField : 'branchname',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'
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
		$("#resetBtn").linkbutton('enable');
		// $('#brand_id').textbox('readonly',false);
		// $('#sdept_id').textbox('readonly',false);
		$('#position').textbox('readonly',false);

		$this.window({ title: '添加' });
		$form.attr("action", $form.attr("action-add"));
	} else {
		$("#resetBtn").linkbutton('disable');
		$this.window({ title: '编辑' });
		$form.attr("action", $form.attr("action-edit"));
		// $('#brand_id').textbox('readonly');
		// $('#sdept_id').textbox('readonly');
		$('#position').textbox('readonly');
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

// 为重置按钮添加单击事件
$("#reset").click(function(){
	if (updateId == undefined) {
		$('#updateform').form('clear');
	}else{
		update(updateId);
	}
});


function up(){
	$("#updateform").submit();
	loadData();//初始化加载数据

}

function closeAll() {
	$.messager.confirm('删除员工', '是否删除所有员工', function(r) {
		if (r) {
			$.ajax({
				url : "deleteCompanyStaffAll",
				type : "POST",
				dataType : "json",
				data : {
				},
				success : function(data) {
					$.messager.alert('提示信息', data.msg, 'info');
					$('#search').submit();
				}
			});
		}
	});
}

function closeAll() {
	$.messager.confirm('删除员工', '是否删除所有员工', function(r) {
		if (r) {
			$.ajax({
				url : "deleteCompanyStaffAll",
				type : "POST",
				dataType : "json",
				data : {
				},
				success : function(data) {
					$.messager.alert('提示信息', data.msg, 'info');
					$('#search').submit();
				}
			});
		}
	});
}

$('#ibrand_id').combobox({
	url : "../platform/querystaffInfoForCombox",
	valueField : 'id',
	textField : 'text',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'
});
$('#brand_id').combobox({
	url : "../platform/querystaffInfoForCombox",
	valueField : 'id',
	textField : 'text',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'

});

$('#isdept_id').combobox({
	url : "../platform/queryStaffDeptInfoForCombox",
	valueField : 'id',
	textField : 'text',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'
});

$('#sdept_id').combobox({
	url : "../platform/queryStaffDeptInfoForCombox",
	valueField : 'id',
	textField : 'text',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'
});

//员工状态
$('#isstate').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '1',
		value : '正常'
	},{
		label : '2',
		value : '禁用'
	} ]
});

/*//分期
$('#bystages').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '1',
		value : '一期'
	},{
		label : '2',
		value : '二期'
	},{
		label : '3',
		value : '三期'
	},{
		label : '9',
		value : '其他'
	} ]
});*/

/*
$('#parentid').combobox({
    prompt:'输入关键字后自动搜索',
    required:false,
    mode:'remote',
    url:'../platform/getCompanyStaffBox',
    editable:true,
    hasDownArrow:false,
    onBeforeLoad: function(param){
        if(param == null || param.p == null || param.p.replace(/ /g, '') == ''){
            var value = $(this).combobox('getValue');
            // var value1 = $('#brand_id').combobox('getValue');
            // var value2 = $('#sdept_id').combobox('getValue');
            // var value3 = $('#bystages').combobox('getValue');
            // var value4 = $('#position').combobox('getValue');
            // if(value1){// 修改的时候才会出现q为空而value不为空
            //     param.brandid = value1;
            // }
            // if(value2){// 修改的时候才会出现q为空而value不为空
            //     param.branch = value2;
            // }
            // if(value3){// 修改的时候才会出现q为空而value不为空
            //     param.bystages = value3;
            // }
            // if(value4){// 修改的时候才会出现q为空而value不为空
            //     param.position = value4;
            // }
            if(value){// 修改的时候才会出现q为空而value不为空
                param.id = value;
                // return true ;
            }
            return true;
        }
    }
});
*/

//上级级员工下拉框
$(function(){
    $.ajax({
        url : "../platform/getCompanyStaffBox",
        success : function(data) {
            $("#parentid").combobox("loadData", data);
        }
    });

});

//上级级员工下拉框
$('#parentid_1').combobox({
	url :  "../platform/getCompanyStaffBox",
	valueField : 'id',
	textField : 'text',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'
});

//岗位
$('#position').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '2',
		value : '主管'
	},{
		label : '3',
		value : '组员'
	}
	]
});

//岗位
$('#position_1').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '2',
		value : '主管'
	},{
		label : '3',
		value : '组员'
	}
	]
});

//员工升值主管方法
function app(id) {
	console.log(id);
	var $this = $('#promotion');
	var $form = $this.find("promotionform");
	updateId=id;

		$("#resetBtn").linkbutton('disable');
		$this.window({ title: '组员升职信息查看' });
		$('#staffname_1').textbox('readonly');
		$this.form('clear');
		$this.form('load', $('#data').datagrid('getData').rows[id]);


	$this.window({
		top:($(window).height() - 450) * 0.5+$(document).scrollTop(),
		left:($(window).width() - 680) * 0.5
	});
	$this.window('open');
}

// 为重置按钮添加单击事件
$("#proreset").click(function(){
    $('#promotionform').form('clear');
});


function uppromotion(){
    $("#promotionform").submit();
    loadData();//初始化加载数据

}