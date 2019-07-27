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
						url : "queryDeptProrateTemp",
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						columns : [ [
								{
									sortable : false,
									width : 100,
									field : 'ce_name',
									title : '交易中心',
									formatter : function(value, row, index) {
										if($('#deptType').val()<=1){
											return '<font color=red>'+row.ce_allot+'%</font> '+row.ce_name;
										}else{
											return row.ce_name;
										}
									}
								},
								{
									sortable : false,
									width : 100,
									field : 'ch_name',
									title : '运营中心',
									formatter : function(value, row, index) {
										if($('#deptType').val()<=2){
											return '<font color=red>'+row.ch_allot+'%</font> '+row.ch_name;
										}else{
											return row.ch_name;
										}
									}
								},
								{
									sortable : false,
									width : 100,
									field : 's_name',
									title : '服务商',
									formatter : function(value, row, index) {
										if($('#deptType').val()<=3){
											return '<font color=red>'+row.s_allot+'%</font> '+row.s_name;
										}else{
											return row.s_name;
										}
									}
								},
								{
									sortable : false,
									width : 100,
									field : 'a_name',
									title : '代理商',
									formatter : function(value, row, index) {
										if($('#deptType').val()<=4){
											return '<font color=red>'+row.a_allot+'%</font> '+row.a_name;
										}else{
											return row.a_name;
										}
									}
								},
								{
									sortable : false,
									width : 100,
									field : 'broker_name',
									title : '经纪人名称',
									formatter : function(value, row, index) {
										return '<font color=red>'+row.b_allot+'%</font> '+row.broker_name;
									}
								},
								{
									align: 'center',
									sortable : false,
									width : 100,
									field : 'broker_mobile',
									title : '经纪人手机号'
								},
								{
									sortable : false,
									width : 100,
									field : 'create_date',
									title : '更新时间',
									formatter : dateTimeFormatter
								},
								{
									width : 100,
									field : 'op',
									title : '操作',
									align : 'center',
									formatter : function(value, row, index) {
										return '\<a class="btns" href="javascript:;" onclick="update(\'' + index + '\');">比率变更</a>\
									';
									}
								} ] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}

// 添加/修改方法
function update(id) {
	var $this = $('#update');
	var $form = $this.find("form");
	
	$("#reset").attr("data-repickID",id);

	if($('#deptType').val()>=2){
		$("#ce_name").closest("tr").hide();
	}	
	if($('#deptType').val()>=3){
		$("#ce_name,#ch_name").closest("tr").hide();
	}	
	if($('#deptType').val()>=4){
		$("#ce_name,#ch_name,#s_name").closest("tr").hide();
	}	
	if($('#deptType').val()>=5){
		$("#ce_name,#ch_name,#s_name,#a_name").closest("tr").hide();
	}	
	

	
	$('#ce_name').html($('#data').datagrid('getData').rows[id].ce_name+" <font color=red>"+$('#data').datagrid('getData').rows[id].ce_allot+"</font>");
	$('#ch_name').html($('#data').datagrid('getData').rows[id].ch_name+" <font color=red>"+$('#data').datagrid('getData').rows[id].ch_allot+"</font>");
	$('#s_name').html($('#data').datagrid('getData').rows[id].s_name+" <font color=red>"+$('#data').datagrid('getData').rows[id].s_allot+"</font>");
	$('#a_name').html($('#data').datagrid('getData').rows[id].a_name+" <font color=red>"+$('#data').datagrid('getData').rows[id].a_allot+"</font>");
	$('#broker_name').html($('#data').datagrid('getData').rows[id].broker_name+" <font color=red>"+$('#data').datagrid('getData').rows[id].b_allot+"</font>");
	
	$("#a_allot").textbox("disableValidation").textbox("readonly");
//	$("#sys_id").val($('#data').datagrid('getData').rows[id].sys_id);
	$this.form('clear');
	$this.form('load', $('#data').datagrid('getData').rows[id]);
	$this.window({
		title : '分配比率变更'
	});
	
	$this.window({
    	top:($(window).height() - 381) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 588) * 0.5
    });

	$this.window('open');
	
};

function repick() {
	var $this = $('#update');
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
var checkSubmitFlg = false; 
$("#updateform").submit(function() {
	if(checkSubmitFlg ==true){
		return false; //当表单被提交过一次后checkSubmitFlg将变为true,根据判断将无法进行提交。 
	} 
	checkSubmitFlg ==true; 

	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}

	var vv = 100-$('#ce_allot').textbox('getValue')-
		$('#ch_allot').textbox('getValue')-
		$('#s_allot').textbox('getValue')-
		$('#a_allot').textbox('getValue')-
		$('#b_allot').textbox('getValue');
	if($('#s_allot').textbox('getValue') <40)
	{
		$.messager.alert('错误信息',"服务商比例不能小于40%",'error');
		return false;
	}
	if($('#b_allot').textbox('getValue') >20)
	{
		$.messager.alert('错误信息',"经纪人比例不能大于20%",'error');
		return false;
	}
	if($('#a_allot').textbox('getValue') >0)
	{
		$.messager.alert('错误信息',"代理商只能为0",'error');
		return false;
	}
	if(vv!=0){
		$.messager.alert('错误信息',"请重新验算分配比率",'error');
		return false;
	}
});
loadData({order_type:queryDescOrAsc.create_date});//初始化加载数据