dictInfos(1008,"refund_channel_rule_id");
function loadData(param) {
	$('#data').datagrid({
		title: '',//当前页面标题
		fitColumns: true,//自动列宽
		autoRowHeight: true,//自动行高度
		singleSelect: true,//禁用选择多行
		border: !0,//不显示边框
		striped: true,//条纹行
		fit: true,//自适应高度
		nowrap: true,//单元格内文字禁止换行
		rownumbers: true,//显示行号
		checkOnSelect: false, //只有选择复选框时才选中
		selectOnCheck: false,//选择行时选中复选框	
		pagination : !0,// 显示分页
		pageSize : 20,// 每页显示数量 参数必须为pageList的对应
		pageList : [ 20,40,80 ],// 每页显示数量设置参数
		url: "queryWithDrawalsApply",
		queryParams: param,//查询参数
		toolbar: '#toolbar',//工具栏
		columns: [[
					{
						checkbox : true,
						align : 'center',
						width : 20,
						//hidden:true,
						field : 'ck',
						title : '选择项'	
					},
		           {
		        	   field: 'id',
		        	   title: '订单号',
		        	   width : 190
		           },
		           {
		        	   field: 'create_time',
		        	   title: '创建时间',
		        	   width : 100
		           },
		           {
		        	   field: 'settle_name',
		        	   title: '位属服务商',
		        	   width : 100
		           },
		           {
		        	   field: 'agent_name',
		        	   title: '位属代理商',
		        	   width : 100
		           },

		           {
		        	   field: 'user_name',
		        	   title: '姓名',
		        	   width : 100
		           },
		           {
		        	   field: 'user_mobile',
		        	   title: '联系电话',
		        	   width : 100
		           },
		           {
		        	   field: 'money',
		        	   title: '提现金额',
		        	   width : 100
		           },
		           {
		        	   field: 'transaction_money',
		        	   title: '到账金额',
		        	   width : 100
		           },
		           {
		        	   field: 'tx_type',
		        	   title: '提现方式',
		        	   width : 100,
		        	   align: 'center'

		           },
		           {
		        	   field: 'duty_money',
		        	   title: '手续费',
		        	   width : 100
		           },
		           {
		        	   field: 'dict_name',
		        	   title: '审核状态',
		        	   width : 100,
		        	   align: 'center'
		           },
		           {
		        	   field: 'op',
		        	   title: '操作',
		        	   width : 400,
		        	   align: 'center',
		        	   formatter: function (value, row, index) {
		        		   if(row.status==1){
		        			   var str = '\<a class="btns" href="javascript:agree(\''+ row.id+ '\');">通过</a>\
		        			               \<a class="btns" href="javascript:disAgree(\''+ row.id+ '\');">不通过</a>\
									';
						        return str;
							}else{
								return "";
							}		        		   
		        	   }
		           }
		           ]],
		           onLoadSuccess: function () {
		        	   $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
		           }
	});
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
//审核通过
function agree(id){

	
	$.messager.confirm('提示信息', '是否通过审核?', function(r){
        if (r){
        	$.messager.progress({
        		title:'请等待',
        		msg:'正在发送请求...',
        		timeout:5000
        	}); 

        	//var id= $('#data').datagrid('getData').rows[id].id;

    		$.ajax({
                type: "POST",
                url: "withdrawals",
                data: {
                	id:id
                },
                dataType: 'json',
                async: true,
                success: function(data){
                	$.messager.progress("close"); 
                	$('#search').submit();  
                    if (data.success == true) {
						$.messager.alert('提示信息', data.msg, 'info');
					} else {
						$.messager.alert('错误信息', data.msg, 'error');
					}
                	
                }
    		});
			
        }
    });	
} 

//审核通过
function disAgree(id){
	$.messager.confirm('提示信息', '是否拒绝通过审核?', function(r){
        if (r){
        	$.messager.progress({
        		title:'请等待',
        		msg:'正在发送请求...',
        		timeout:5000
        	}); 
        	//var id = $('#data').datagrid('getData').rows[id].id;

    		$.ajax({
                type: "POST",
                url: "rebackWithDraw",
                data: {
                	id:id
                },
                dataType: 'json',
                async: true,
                success: function(msg){
                	$.messager.progress("close"); 
                	$('#search').submit();
                    if(msg.success==0){
                    	$.messager.alert('提示信息', '审核失败');
                    }else if(msg.success==1){
                    	$.messager.alert('提示信息', '审核成功');
                    }else if(msg.success==2){
                    	$.messager.alert('提示信息', '已审核，请勿重复审核');
                    }
                	
                }
    		});
			
        }
    });	
} 

//批量审核通过
function allAgree(id){
	if($('#data').datagrid('getChecked').length==0){
		$.messager.alert('提示', '请先勾选提现申请订单!', 'error');
		return;
	}
	$.messager.confirm('提示信息', '是否通过审核?', function(r){
        if (r){
        	$.messager.progress({
        		title:'请等待',
        		msg:'正在发送请求...',
        		timeout:5000
        	}); 
        	
        	/************经纪人id和姓名*****************/
        	var orders=$('#data').datagrid('getChecked');
        	var order_ids="";
        	for(var i=0;i<orders.length;i++){
        		if(i==(orders.length-1)){
        			order_ids=order_ids+orders[i].id;
        		}else{
        			order_ids=order_ids+orders[i].id+",";
        		}
        	}      
    		$.ajax({
                type: "POST",
                url: "allWithDrawals",
                data: {
                	order_ids:order_ids
                },
                dataType: 'json',
                async: true,
                success: function(msg){
                	$.messager.progress("close"); 
                	$('#search').submit();
                	$.messager.alert('提示信息', '已审核');                	
                }
    		});
			
        }
    });	
} 

//批量审核不通过
function allDisAgree(){
	if($('#data').datagrid('getChecked').length==0){
		$.messager.alert('提示', '请先勾选提现申请订单!', 'error');
		return;
	}
	$.messager.confirm('提示信息', '是否打回这些订单?', function(r){
        if (r){
        	$.messager.progress({
        		title:'请等待',
        		msg:'正在发送请求...',
        		timeout:5000
        	}); 
        	
        	/************经纪人id和姓名*****************/
        	var orders=$('#data').datagrid('getChecked');
        	var order_ids="";
        	for(var i=0;i<orders.length;i++){
        		if(i==(orders.length-1)){
        			order_ids=order_ids+orders[i].id;
        		}else{
        			order_ids=order_ids+orders[i].id+",";
        		}
        	}      
    		$.ajax({
                type: "POST",
                url: "allRebackWithDraw",
                data: {
                	order_ids:order_ids
                },
                dataType: 'json',
                async: true,
                success: function(msg){
                	$.messager.progress("close"); 
                	$('#search').submit();
                	$.messager.alert('提示信息', '已审核');                	
                }
    		});
			
        }
    });	
} 

function sum(){
	$.ajax({
		url : "querySumUserTX",
		type : "POST",
		success : function(data) {
			if(data == null){
				return ;
			}
			var html = '\
				<span>当日申请总额：¥ '+fmoney(data.sumTX,2)+'\</span>\
				<span>当日出金总额：¥ '+fmoney(data.sumOut,2)+'\</span>\
				';
			$("#total").html(html);
		}
	});
}



loadData({status:1});//初始化加载数据
sum();


//状态
$('#status').combobox({
    width : '150',
    height : '23',
    valueField : 'label',
    textField : 'value',
    panelHeight : 'auto',
    editable: false,
    onLoadSuccess:function(){
        $(status).combobox('select',1);
    },
    data : [ {
        label : '1',
        value : '待审核'
    },{
        label : '2',
        value : '处理中'
    },{
        label : '3',
        value : '拒绝申请'
    } ,{
        label : '4',
        value : '成功'
    } ,{
        label : '5',
        value : '提现失败'
    },{
        label : '6',
        value : '作废'
    } ]
});
