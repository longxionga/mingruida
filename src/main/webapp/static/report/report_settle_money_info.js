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
						idField:'tx_order_id',
						url : url,
						remoteSort:false,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏					
						columns : [ [
								{
									width : 100,
									sortable : false,
									field : 'dept_code',
									title : '服务商编码',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'dept_name',
									title : '服务商名称',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'dept_mobile',
									title : '电话',
									align : 'center'
								},
								{
									width : 100,
									sortable : true,
									field : 'dept_money',
									align : 'right',
									title : '余额'
								},
								{
									width : 100,
									sortable : true,
									field : 'is_use',
									align : 'center',
									title : '是否可用',
									formatter: function(value, row, index){
									    if (value == "1") {
									        return '可用';
									    }									    
									    else {
									        return '禁用';
									    }
									}
								}
								] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}

function deptsettlemoneysum(param){
	$.ajax({
		url : "queryDeptsMoneyInfoSum?dept_type=3",
		type : "POST",
		data : param,
		success : function(data) {
			if(data == null){
				return ;
			}
			var html = '\
				<span>服务商余额汇总：¥ '+fmoney(data[0].sumdeptmoney,2)+'\</span>';
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
	loadData(param,"queryDeptsMoneyInfo?dept_type=3");//查询数据
	deptsettlemoneysum(param);
	return false;
});

function expInfo() {
	window.location.href = '';
}
loadData();//初始化加载数据