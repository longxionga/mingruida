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
			idField:'id',
			url : 'queryReportUserBalanceInfo',
			remoteSort:false,
			queryParams : param,// 查询参数
			toolbar : '#toolbar',// 工具栏			
			columns : [ [
					{						
						sortable : false,									
						field : 'user_name',
						align : 'center',
						title : '用户名'
					},
					{						
						sortable : false,									
						field : 'user_id',
						align : 'center',
						title : '用户ID'
					},
					{					
						sortable : false,
						field : 'user_mobile',
						title : '手机号',
						align : 'center'
						
					},
					{
						sortable : false,
						field : 'user_money',
						title : 'mysql数据',
						align : 'center'								
					}
					,
					{
						width : 100,
						sortable : false,
						field : 'userredismoney',
						title : 'redis数据',
						align : 'center'								
					},
					{
						width : 150,
						field : 'op',
						title : '操作',
						align : 'center',
						formatter : function(value, row, index) {
							if(row.user_money !=row.userredismoney)
							{								
							
							var str = '\
							<a class="btns" href="javascript:;" onclick="updatebalance(\'' + row.user_id + '\');">修正</a>\
						';}
							return str;
						}
					} 	
					] ],
			onLoadSuccess : function() {
				$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
			}
		});
}

function userbalanceinfo(param){
	$.ajax({
		url : "queryUserBalanceInfo",
		type : "POST",
		data : param,
		success : function(data) {
			if(data.length==0){
				$.messager.alert('提示信息', '没找到该手机号的余额信息！', 'info');
				return ;
			}
			var html = '\
				<span>用户名： '+ data[0].user_name +'\</span>\
				<span>电话号码： '+ data[1].mobiledes +'\</span>\
				<span>MySQL余额：¥ '+ data[0].user_money +'\</span>\
				<span>Redis余额：¥ '+data[1].RBalance+'\</span>\
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
	var is_use=1;
	var param = getFormJson(this);
	if(is_use=="0")
	{
		alert("抱歉，由于用户量过大，暂时关闭该功能！");
	}
	else
	{
		loadData(param);//查询数据
		userbalanceinfo(param);
	}
	return false;
});


function updatebalance(uid)
{
	//alert(uid);
	$.ajax({
		url : "updUserBalanceInfo",
		type : "POST",
		dataType:"json",
		data : {
			user_id:uid
		},
		success : function(data){
            $.messager.alert('提示信息', data.msg, 'info');
            $('#search').submit();
		}
	});
}


function expUserInfo() {
//	var dept_id=document.getElementById("opt_dept_id").value;
//	var type=document.getElementById("opt_dept_type").value;
	$.ajax({
		url : "queryUserBalanceExcel",
		type : "POST",
		data : {},
			success : function(msg) {
				if(msg.num>0){
					window.location.href= msg.url;
				}else{
					$.messager.alert('提示信息', '导出失败，查询无数据');
				}

			}
	});	
	//window.location.href = 'exportSettleDayReportInfo';
}
loadData();//初始化加载数据);//查询数据