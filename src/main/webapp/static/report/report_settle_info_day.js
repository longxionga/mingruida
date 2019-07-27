/**
 * 
 */
function loadData(param,url) {
	var boolFitColumns=true;
	
	$('#data').datagrid(
					{
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
						pageList : [ 20,40,80 ],// 每页显示数量设置参数
						idField:'USER_ID',
						url : url,
						remoteSort:false,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏				
						columns : [ [
								{
									width : 100,
									sortable : false,
									field : 'STATISTICS_DAY',
									title : '统计日期',
									align : 'center'
								},
								{
									width : 150,
									sortable : false,
									field : 'SETTLE_NAME',
									title : '服务商名称',
									align : 'center'
								},	
								{
									width : 100,
									sortable : true,
									field : 'SETTLE_CZCOUNT',
									align : 'right',
									title : '服务商日结仓储费'
								},
								{
									width : 100,
									sortable : true,
									field : 'USER_TXCOUNT',
									align : 'right',
									title : '用户提现仓储费'
								}
								]],
						onLoadSuccess : function() {
						}
					});
}

function memberdaysum(param){
	$.ajax({
		url : "querySettleFeeSum",
		type : "POST",
		data : param,
		success : function(data) {
			if(data.length==0){
				return ;
			}
			console.log(data.length);
			var html = '\
				<span>服务商日充值仓储费汇总：¥ '+fmoney(data[0].SETTLE_CZCOUNT,2)+'\</span>\
				<span>用户日提现仓储费汇总：¥ '+fmoney(data[0].USER_TXCOUNT,2)+'\</span>\
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
	loadData(param,"querySettleFeeInfo");//查询数据
	memberdaysum(param);
	return false;
});

function expInfo() {
	var arrayData = $("#search").serialize();
	var param=DataDeal.formToJson(arrayData)
	//var $this = $(this);
	//var param = getFormJson(arrayData);
	//document.getElementById("opt_dept_id").value
	console.log(arrayData);
	$.ajax({
		url : "queryFeeforExcel",
		type : "POST",
		data : arrayData,
			success : function(msg) {
				if(msg.num>0){
					window.location.href= msg.url;
				}else{
					$.messager.alert('提示信息', '导出失败，查询无数据');
				}

			}
	});	
}

var DataDeal = {  
		//将从form中通过$('#form').serialize()获取的值转成json  
		           formToJson: function (data) {  
		               data=data.replace(/&/g,"\",\"");  
		               data=data.replace(/=/g,"\":\"");  
		               data="{\""+data+"\"}";  
		               return data;  
		            },  
		}; 
loadData();//初始化加载数据