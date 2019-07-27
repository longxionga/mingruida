
function loadData(param) {
	var boolFitColumns=true;
	if($(document).width()<=1600-220){
		boolFitColumns=false;
	}
	$('#data').datagrid(
		{
			title : '',// 当前页面标题
			fitColumns : true,// 自动列宽
			autoRowHeight : false,// 自动行高度
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
			url : 'queryTotalStandardAchievementPageList',
			remoteSort:false,
			queryParams : param,// 查询参数
			toolbar : '#toolbar',// 工具栏
			columns : [ [
				{
					width: 80,
					sortable : true,
					field : 'brandname',
					title : '品牌'
				},
				{
					width: 80,
					sortable : true,
					field : 'dept_name',
					title : '部门'
				},
				{
					width: 80,
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
					width: 80,
					sortable : true,
					field : 'staffname',
					title : '员工身份证姓名'
				},

				{
					width: 100,
					sortable : true,
					field : 'tsum',
					title : '后台机具数'
				},
				{
					width: 100,
					sortable : true,
					field : 'ybdsum',
					title : '已绑定机具数'
				},
				{
					width: 100,
					sortable : true,
					field : 'ydbsum',
					title : '商户已审批通过'
				},
                {
                    width: 100,
                    sortable : true,
                    field : 'gsdbsum',
                    title : '铭锐达标准达标'
                }
                /*,
                {
                    width: 100,
                    sortable : true,
                    field : 'shydbsum',
                    title : '上月绑定本月达标'
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

$("#search").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param);//查询数据
	return false;
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


// $('#sdept_id').combobox({
// 	url : "../platform/queryStaffDeptInfoForCombox",
// 	valueField : 'id',
// 	textField : 'text',
// 	width : '150',
// 	height : '30',
// 	editable : false,
// 	panelHeight : '200'
// });

//员工岗位
$('#position').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '1',
		value : '经理',
		selected:true

	},{
		label : '2',
		value : '主管'
	}]

});

