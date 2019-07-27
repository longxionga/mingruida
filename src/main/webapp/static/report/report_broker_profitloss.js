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
						idField:'broker_name',
						url : url,
						remoteSort:false,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						frozenColumns:[[
								{
									width : 150,
									sortable : true,
									field : 'create_date',
									title : '创建时间',
									align : 'center',
									formatter:dateTimeFormatter
								},
								{
									width : 100,
									sortable : false,
									field : 'broker_name',
									align : 'center',
									title : '经纪人名'
								},
								{
									width : 100,
									sortable : false,
									field : 'mobile',
									title : '手机号',
									align : 'center'
								}
				        ]],
						columns : [ [
								{
									width : 100,
									sortable : false,
									field : 'broker_money',
									title : '金额',
									align : 'center'
								},								
								{
									width : 100,
									sortable : false,
									field : 'broker_parent_name',
									title : '所属经纪人',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'settle_name',
									title : '服务商名称',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'agent_name',
									title : '代理商名称',
									align : 'center'
								},							
								{
									width : 100,
									sortable : false,
									field : 'dept_name',
									title : '部门名称',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'money_type',
									align : 'center',
									title : '流水类型',
									formatter: function(value, row, index){
									    if (value == "18") {
									        return '转入';
									    }
									    if (value == "15") {
									        return '转出';
									    }
									    if (value == "19") {
									        return '购买靓号';
									    }
									}
								}
								] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}

function sum(param){
	$.ajax({
		url : "queryBrokerProfitlossSum",
		type : "POST",
		data : param,
		success : function(data) {
			if(data == null){
				return ;
			}
			var html = '\
				<span>经纪人转入总额：¥ '+fmoney(data.into_count,2)+'\</span>\
				<span>经纪人转出总额：¥ '+fmoney(data.roll_out_count,2)+'\</span>\
				<span>经纪人购买靓号总额：¥ '+fmoney(data.buy_count,2)+'\</span>\
				';
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
	loadData(param,"queryBrokerProfitloss");//查询数据
	sum(param);
	return false;
});

function expInfo() {
	//window.location.href = 'exportMemberdayReportInfo';
}



//提现方式
$('#money_type').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: true,
	data : [ {
		label : '18',
		value : '转入'
	},{
		label : '15',
		value : '转出'
	} ,{
		label : '19',
		value : '购买靓号'
	}]
});

loadData({order_type:queryDescOrAsc.create_date});//初始化加载数据