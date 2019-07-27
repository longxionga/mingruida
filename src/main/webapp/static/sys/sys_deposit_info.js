/**
 * 
 */
function loadData(param,url) {
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
						idField:'user_name',
						url : url,
						remoteSort:false,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						/*frozenColumns:[[

				        ]],*/
						columns : [ [
								{
									width:150,
									sortable : false,
									field : 'dept_name',
									title : '服务商名',
									align : 'center'
								},
								{
									width:100,
									sortable : true,
									field : 'dept_code',
									align : 'center',
									title : '服务商编码'
								},
								{
									width:100,
									sortable : false,
									field : 'dept_mobile',
									title : '手机号',
									align : 'center'
								},					
								{
									width : 100,
									sortable : false,
									field : 'dept_money',
									title : '金额',
									align : 'center'
								},
								{
									width:100,
									sortable : false,
									field : 'is_use',
									align : 'center',
									title : '是否可用状态',
									formatter: function(value, row, index){
									    if (value == "1") {
									        return '可用';
									    }
									    if (value == "0") {
									        return '不可用';
									    }
									    if (value == "4") {
									        return '已退出';
									    }
									   									    
									}
								},
								{
									width : 150,
									field : 'op',
									title : '操作',
									align : 'center',
									formatter : function(value, row, index) {
																		
										
										var str = '\
										<a class="btns" href="javascript:;" onclick="updateDeposit(\'' + row.dept_id + '\');">调整</a>\
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

function rechargesum(param){
	$.ajax({
		url : "queryDepositSum",
		type : "POST",
		data : param,
		success : function(data) {
			if(data == null){
				return ;
			}
			var html = '\
				<span>用户充值汇总：¥ '+fmoney(data[0].DEPT_MONEYCOUNT,2)+'\</span>';
			$("#total").html(html);
		}
	});
}

$("#search").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param,"queryDepositInfo");//查询数据
	rechargesum(param);
	return false;
});

function expInfo() {
	window.location.href = 'exportMemberdayReportInfo';
}
loadData("","queryDepositInfo");//初始化加载数据

function updateDeposit(id,row) {
  	var $this = $('#update');
	var $form = $this.find("form");
	$this.form('clear');
	$("#o_dept_id").val(id);	
	//$this.form('load', $('#data').treegrid('getSelected'));
	
	$this.window({
		title : '服务商金额调整'
	});
	$("#dept_money").textbox('clear');
	$this.window({
    	top:($(window).height() - 281) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 488) * 0.5
    });
	

	$this.form('load', "");
	$this.window('open');
};	

function modDeposit() {
	var dept_id = $('#o_dept_id').val();
	var dept_money = $('#dept_money').val();
	if(dept_money.length==0)
	{
		$.messager.alert('提示信息', "请输入金额", 'info');
		return;
	}
	$.messager.confirm('更新余额', '是否确认需要更新保证金', function(r) {
		if (r) {		
			$.ajax({
				url : "updDepositInfo",
				type : "POST",
				dataType : "json",
				data : {
					dept_money : dept_money,
					dept_id : dept_id
				},
				success : function(data) {
					$.messager.alert('提示信息', data.msg, 'info');
//					if (data.success == true) {
//						$('#update').dialog('close');
//					}
					var $this = $('#update');
					$this.window('close');
					loadData();
				}

			});

		}
	});
}