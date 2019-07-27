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
									field : 'create_date',
									title : '创建时间',
									align : 'center',
									formatter:dateTimeFormatter
								},
								{
									width:100,
									sortable : true,
									field : 'user_name',
									align : 'center',
									title : '用户名'
								},
								{
									width:100,
									sortable : false,
									field : 'order_money',
									title : '充值金额',
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
									width:100,
									sortable : true,
									field : 'cz_type',
									align : 'center',
									title : '充值类型',
									formatter: function(value, row, index){
									    if (value == "dinpay") {
									        return '智付';
									    }
									    if (value == "ecpsspay") {
									        return '汇潮';
									    }
									    if (value == "unionpay") {
									        return '银联';
									    }
									    if (value == "wechat") {
									        return '微信';
									    }
									    if (value == "yimadai"){
									        return '一麻袋';
									    }									    
									}
								},
								{
									width:100,
									sortable : true,
									field : 'cz_pc_mobile',
									align : 'center',
									title : '充值渠道',
									formatter: function(value, row, index){
									    if (value == "pc") {
									        return '电脑端';
									    }
									    if (value == "mobile") {
									        return '手机端';
									    }									   
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
		url : "queryRechargeEnsureSum",
		type : "POST",
		data : param,
		success : function(data) {
			if(data == null){
				return ;
			}
			var html = '\
				<span>用户充值汇总：¥ '+fmoney(data[0].ORDER_MONEYCOUNT,2)+'\</span>';
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
	loadData(param,"queryReportRechargeEnsureInfo");//查询数据
	rechargesum(param);
	return false;
});


function expEnsureInfo() {
    //var date = new Date();
    // console.log(date.getHours());
    // if(date.getHours()>12)
    // {
    //     $.messager.alert('提示信息', '请在12点以前导出数据！');
    //     return;
    // }
    var user_name=document.getElementById("user_name").value;
    var start_date=$('#skssj').datebox('getValue');
    var end_date=$('#sjssj').datebox('getValue');
    //var mobile=document.getElementById("mobile").value;
    var settle_id=$('#settle_id').combobox('getValue');//获取combobox的value方法
    //var agent_id=$('#agent_id').combobox('getValue');
    //var DID=$('#DID').combobox('getValue');
    //var cz_type=$('#cz_type').combobox('getValue');
    //var cz_state=$('#cz_state').combobox('getValue');

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
            if(date.getHours()>=11)
            {
                $.messager.alert('提示信息', '请在11点以前导出数据！');
                return;
            }else
            {
                $.ajax({
                    url : "queryEnsureForExcel",//queryRechargeForExcel
                    type : "POST",
                    data : {
                        user_name:user_name,
                        begindate:start_date,
                        enddate:end_date,
                        settle_id:settle_id
                    },
                    success : function(msg) {

                        console.log(msg);
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