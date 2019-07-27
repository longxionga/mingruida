dictInfo(1009,"tx_bank");
dict(1009);
function loadData(param) {
	$('#data').datagrid({
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
						pageList : [ 20, 40, 80 ],// 每页显示数量设置参数
						url : "queryWithDrawInfo",
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						columns : [ [ {
							width : 150,
							field : 'company_id',
							title : '申请单号'
						}, {
							width : 100,
							field : 'tx_name',
							title : '姓名'
						}, {
							width : 100,
							field : 'tx_money',
							title : '提现金额(元)',
			        	    align: 'right'
						}, {
							width : 100,
							field : 'is_status',
							title : '处理状态',
							formatter : function(value) {
								if (value == 0) {
									return "<font color='lightgreen'>待审核</font>";
								} else if (value == 1) {
									return "<font color='darkblue'>已审核</font>";
								} else if (value == 2) {
									return "完成";
								} else if (value == 3) {
									return "<font color='darkred'>审核打回</font>";
								}
							},
			        	    align: 'center'
						}, {
							width : 100,
							field : 'operation_name',
							title : '审批人'
						}, {
							width : 100,
							field : 'user_role',
							title : '审批人角色'
						}, {
							width : 100,
							field : 'create_date',
							title : '创建时间',
							formatter : dateTimeFormatter
						} ] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
};

//添加方法
function update() {
	var $this = $('#update');
	var $form = $this.find("form");
	$this.form('clear');
	$this.window('close');
	$this.window('open');
	var thisData = {"tx_name":loginName};
	$this.form('load', thisData);
	$('#deptMoney').html("<font color=red>"+deptMoney+"</font>");
	$('#earnestMoney').html("<font color=red>"+earnestMoney+"</font>");
	$('#withDrawMoney').html("<font color=red>"+withDrawMoney+"</font>");
	if(withDrawMoney>0){
		$('#Money').html("<font color=red>"+withDrawMoney+"</font>")
		$('#Money').html("*注意：您的提现申请额度不得超过 "+"<font color=red>"+ withDrawMoney +"</font>"+" 元");
	}else{
	    $('#Money').html("*注意：您的提现申请额度为 "+"<font color=red>"+ 0 +"</font>"+" 元");
	}
	if(deptType!=3){
		$("#h1,#h2,#h3").closest("tr").hide();
	}
	$this.panel({
		title : '申请提现'
	});
	$("#reset").linkbutton('enable');
};

//为重置按钮添加单击事件 
$("#reset").click(function(){
	$('#updateform').form('clear');
	//设置发布人为当前登陆者
	var thisData = {"tx_name":loginName};
	$("#update").form('load', thisData);	
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
loadData({
	order_type : queryDescOrAsc.create_date
});//初始化加载数据