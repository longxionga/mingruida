/**
 * 
 */
dictInfo(1008,"pay_method");
dictInfo(1027,"status");
dict(1027);
function loadData(param,url) {
	var boolFitColumns=true;
	if($(document).width()<=1600-220){
		boolFitColumns=false;
	}
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
						idField:'order_id',
						url : url,
						remoteSort:false,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						frozenColumns:[[
								{
									width : 150,
									sortable : false,
									field : 'create_time',
									title : '创建时间',
									align : 'center',
									formatter:dateTimeFormatter
								},
								{
									width : 100,
									sortable : true,
									field : 'user_name',
									align : 'center',
									title : '用户名'
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
									field : 'order_id',
									title : '订单号',
									align : 'center'
								},
								{
									width : 100,
									sortable : true,
									field : 'money',
									align : 'right',
									title : '提现金额'
								},
								{
									width : 100,
									sortable : false,
									field : 'broker_name',
									title : '经纪人',
									align : 'center'
								},							
								{
									width : 100,
									sortable : false,
									field : 's_name',
									title : '服务商',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'a_name',
									title : '代理商',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'dept_name',
									title : '部门',
									align : 'center'
								},
								{
									width : 100,
									sortable : false,
									field : 'dict_name',
									title : '提现状态',
									align : 'center'

								},
								{
									width : 100,
									sortable : true,
									field : 'pay_method',
									align : 'center',
									title : '提现类型'

								}
								] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}

function withdrawalsum(param){
	$.ajax({
		url : "queryWithdrawalSum",
		type : "POST",
		data : param,
		success : function(data) {
			if(data == null){
				return ;
			}
			var html = '\
				<span>用户提现汇总：¥ '+fmoney(data[0].TX_MONEYCOUNT,2)+'\</span>';
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
	loadData(param,"queryReportWithdrawalInfo");//查询数据
	withdrawalsum(param);
	return false;
});

function expInfo() {
	window.location.href = 'exportMemberdayReportInfo';
}

function expWithdrawalsInfo() {
    // var date = new Date();
    // console.log(date.getHours());
    // if(date.getHours()>12)
    // {
     //    $.messager.alert('提示信息', '请在12点以前导出数据！');
     //    return;
	// }
    var user_name=document.getElementById("user_name").value;
    var start_date=$('#skssj').datebox('getValue');
    var end_date=$('#sjssj').datebox('getValue');
    var mobile=document.getElementById("mobile").value;
    var settle_id=$('#settle_id').combobox('getValue');//获取combobox的value方法
    var agent_id=$('#agent_id').combobox('getValue');
    var DID=$('#DID').combobox('getValue');
    var status=$('#status').combobox('getValue');
    var pay_method=$('#pay_method').combobox('pay_method');
    var xhr = null;
    if(window.XMLHttpRequest){
        xhr = new window.XMLHttpRequest();
    }else{ // ie
        xhr = new ActiveObject("Microsoft")
    }
    xhr.open("GET","/",true);
    xhr.send(null);
    xhr.onreadystatechange=function(){
        var time,date;
        if(xhr.readyState == 2){
            time = xhr.getResponseHeader("Date");
            date = new Date(time);
            console.log(date);
            if(1==2)//(date.getHours()>=11)
            {
               $.messager.alert('提示信息', '请在11点以前导出数据！');
               return;
            }else
            	{
                    $.ajax({
                        url : "queryWithdrawalForExcel",
                        type : "POST",
                        data : {
                            user_name:user_name,
                            begindate:start_date,
                            enddate:end_date,
                            mobile:mobile,
                            settle_id:settle_id,
                            agent_id:agent_id,
                            DID:DID,
                            status:status,
                            pay_method:pay_method
                        },
                        success : function(msg) {
                            if(msg.data =='error'){
                                $.messager.alert('提示信息', '请选择一天内的日期范围');
                            }else {
                                if (msg.num != '' && msg.num > 0) {
                                    window.location.href = msg.url;
                                } else {
                                    $.messager.alert('提示信息', '导出失败，查询无数据');
                                }
                            }

                        }
                    });
				}
        }
    }



    //window.location.href = 'exportSettleDayReportInfo';
}

loadData();//初始化加载数据