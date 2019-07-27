dictInfo(1009,"tx_bank");
dictInfo(1001,"dept_type");
dict(1009);
function loadData(param) {
	var boolFitColumns=true;
	if($(document).width()<=1600-220){
		boolFitColumns=false;
	}
	$('#data').datagrid({
						title : '',// 当前页面标题
						fitColumns : boolFitColumns,// 自动列宽
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
						url : "queryWithDrawInfoApprove",
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						columns : [ [ {
							width : 260,
							field : 'company_id',
							title : '申请单号'
						},{
							width : 100,
							field : 'tx_name',
							title : '姓名',
							align: 'center'
						}, {
							width : 100,
							field : 'tx_money',
							title : '提现金额(元)',
			        	    align: 'right'
						},
						{
							width : 100,
							field : 'prop_values',
							title : '费率',
			        	    align: 'right'
						},
						{
							width : 100,
							field : 'dept_counter_fee',
							title : '手续费',
			        	    align: 'right'
						},
						{
							width : 100,
							field : 'real_tx_money',
							title : '到账金额(元)',
			        	    align: 'right'
						},
						{
							width : 100,
							field : 'dept_money',
							title : '余额(元)',
			        	    align: 'right',
			        	    formatter: function (value, row, index) {
			            		 if (value <0)
			            			 return '<span style="color:#f00;">'+value+'</span>';
			            		 else
			            			 return value;
			            	 }
						}, {
							width : 100,
							field : 'dept_name',
							title : '部门'
						}, 
						 {
							width : 100,
							field : 'dept_type',
							title : '部门等级'
						},{
							width : 100,
							field : 'dept_mobile',
							title : '部门注册电话'
						}, {
							width : 100,
							field : 'user_name',
							title : '申请人'
						}, {
							width : 100,
							field : 'user_mobile',
							title : '申请人电话'
						}, {
							width : 100,
							field : 'is_status',
							title : '处理状态',
							align: 'center',
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
							}
						}, {
							width : 100,
							field : 'operation_name',
							title : '审批人'
						}, {
							width : 100,
							field : 'create_date',
							title : '创建时间',
							formatter : dateTimeFormatter
						},{
							width : 200,
				        	   field: 'op',
				        	   title: '操作',
				        	   align: 'center',
				        	   formatter: function (value, row, index) {
				        		   var str = '';
				        		   console.log(row);
				        		   if(row.is_status==0){
				        			   str = '\
				        				   <a class="btns" href="javascript:update(\'' + row.company_id+'\',\''+row.is_status + '\',1,0,0);">审核通过</a>\
				        				   <a class="btns" href="javascript:update(\'' + row.company_id+'\',\''+row.is_status + '\',3,0,0);">审核打回</a>\
				        				   ';
				        			   return str;
				        		   };
				        		   if(row.is_status==1){
				        			   str = '\
				        				   <a class="btns" href="javascript:update(\'' + row.company_id+'\',\''+row.is_status +'\',2,\''+row.dept_counter_fee +'\',\''+row.ex_dept_id +'\',\''+row.ex_dept_money +'\');">完成</a>\
				        				   ';
				        			   return str;
				        		   };
				        	   }
				           } ] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
};

function update(id, status, type,exdeptfee,exdeptid,exdeptmoney) {
	$.ajax({
		url: "updWithDrawApproveInfo",
		type: "POST",
		data : {
			company_id : id,
			is_status : status,
			type : type,
			ex_dept_fee : exdeptfee,
			ex_dept_id : exdeptid,
			ex_dept_money : exdeptmoney
		},
		success: function (data) {
			if (data.success == true) {
				$.messager.alert('提示信息', data.msg, 'info');
				$('#data').datagrid('reload');
			} else {
				$.messager.alert('错误信息', datae.msg, 'error');
			}
		}
	});
};

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