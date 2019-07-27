function loadData(param) {
	var boolFitColumns=true;
	if($(document).width()<=1600-220){
		boolFitColumns=false;
	}
	$('#data').datagrid({
		title: '',//当前页面标题
		fitColumns : boolFitColumns,// 自动列宽
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
		url: "queryUserAgentInfo",
		remoteSort:false,
		queryParams: param,//查询参数
		toolbar: '#toolbar',//工具栏
		frozenColumns:[[
            {
                checkbox : true,
                align : 'center',
                width : 20,
                //hidden:true,
                field : 'ck',
                title : '选择项'
            },
            {
                align: 'center',
                field : 'mobile',
                title : '手机号',
                width:80
            },
            {
                field : 'agent_name',
                title : '代理名称',
                width : 50
            },
            {
                field : 'id_card',
                title : '身份证号',
                align : 'center',
                width: 120
            },
            {
                width: 80,
                field: 'account_type',
                title: '人员类型',
                align : 'center',
                formatter: function (value, row, index) {
                    if (value == '01')
                        return '员工';
                    else
                        return '<span style="color:#f00;">代理商</span>';
                }
            },
			{
				   align: 'center',
				   width: 100,
				   field: 'ch_name',
				   title: '所属渠道'
			},
            {
                align: 'center',
                width: 100,
                field: 'back_name',
                title: '后台名称'
            }
		                ]],
		columns: [[
					{
						field : 'sala_title',
						title : '工资条名称',
						width:100
					},
					{
						width: 80,
						field: 'sala_status',
						title: '工资查询可用',
						align : 'center',
						formatter: function (value, row, index) {
							if (value == '01')
								return '是';
							else
								return '<span style="color:#f00;">否</span>';
						}
					},
					{
						field : 'total_map',
						title : '项目明细',
						width:80,
						formatter : function(value, row, index) {
							var str = '\
								  <a class="btns" href="javascript:showTotalMap(\'' + index + '\');">查看明细</a>\
								  ';
							return str;
						}
					},
					{
						field : 'deduct_map',
						title : '应扣款项',
						width:80,
						formatter : function(value, row, index) {
							var str = '\
								  <a class="btns" href="javascript:showDeductMap(\'' + index + '\');">查看明细</a>\
								  ';
							return str;
						}
					},
					{
						field : 'deduct_amount',
						title : '抵扣所得分润',
						width:80
					},
					{
						field : 'real_amount',
						title : '实发分润',
						width:100
					},
					{
						field : 'brand',
						title : '品牌',
						width:160
					},
					{
						field : 'notes',
						title : '备注',
						width:160
					},
                    {
                        field : 'create_time',
                        title : '创建时间',
                        width:120,
                        formatter:dateTimeFormatter
                    },
            {
                field : 'op',
                title : '操作',
                align : 'center',
                formatter : function(value, row, index) {
                    if (row.status == 1) {
                        var str = '\
                      <a class="btns" href="javascript:reset(\''+ index+ '\');">密码重置</a>\
                      <a class="btns" href="javascript:off(\''+ index+ '\');">禁用用户</a>\
                      ';
                    } else if(row.status==2){
                        var str = '\
                           <a class="btns" href="javascript:on(\''+ index + '\');">启用用户</a>\
                           ';
                        return str;
                    }
                    return str;
                },
                width:200
            }
			   ]],
			   onLoadSuccess: function () {
				   $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
			   }
	});
}


// 禁用用户
/*
 * function off(id){ var user_id =
 * $('#data').datagrid('getData').rows[id].user_id; $.ajax({ url :
 * "updateUserInfoState", type : "POST", dataType:"json", data : {
 * user_id:user_id, is_use:"0" }, success : function(data){
 * $.messager.alert('提示信息', data.msg, 'info'); } }); loadData();//重新加载 }
 */

function off(id) {
    $.messager.confirm('禁用用户', '是否禁用用户', function(r) {
        if (r) {
            var ids = $('#data').datagrid('getData').rows[id].user_id;
            console.info($('#data').datagrid('getData').rows[id]);
            var name = $('#data').datagrid('getData').rows[id].name;
            $.ajax({
                url : "updUserInfoState",
                type : "POST",
                dataType : "json",
                data : {
                    id : ids,
                    name:name,
                    status : "2"
                },
                success : function(data) {
                    $.messager.alert('提示信息', data.msg, 'info');
                }
            });
            loadData();// 重新加载
        }
    });
}
// 启用用户
/*
 * function on(id){ var user_id =
 * $('#data').datagrid('getData').rows[id].user_id; $.ajax({ url :
 * "updateUserInfoState", type : "POST", dataType:"json", data : {
 * user_id:user_id, is_use:"1" }, success : function(data){
 * $.messager.alert('提示信息', data.msg, 'info'); } }); loadData();//重新加载 }
 */

function on(id) {
    $.messager.confirm('启用用户', '是否启用用户', function(r) {
        if (r) {
            var ids = $('#data').datagrid('getData').rows[id].user_id;
            var name = $('#data').datagrid('getData').rows[id].name;
            // alert(user_id);
            $.ajax({
                url : "updUserInfoState",
                type : "POST",
                dataType : "json",
                data : {
                    id : ids,
                    name:name,
                    status : "1"

                },
                success : function(data) {
                    $.messager.alert('提示信息', data.msg, 'info');
                }
            });
            loadData();// 重新加载
        }
    });
}

function reset(id) {
    $.messager.confirm('重置密码', '是否确认重置密码', function(r) {
        if (r) {
            var ids = $('#data').datagrid('getData').rows[id].user_id;
            $.ajax({
                url : "updUserInfoPwd",
                type : "POST",
                dataType : "json",
                data : {
                    id :ids
                },
                success : function(data) {
                    $.messager.alert('提示信息', data.msg, 'info');
                }
            });
        }
    });
}

function showDeductMap(id) {
    var total_map = $('#data').datagrid('getData').rows[id].deduct_map;
    /*$("#dataID").val("decryption");
    $("#search").submit();*/
    //decryption();
    var $this = $('#code');
    var $form = $this.find("form");
    $this.window({ title: '应扣款项' });
    $this.window({
        top:($(window).height() - 240) * 0.5+$(document).scrollTop(),
        left:($(window).width() - 350) * 0.5
    });
    $("#tables tr").empty();
    //addTr2('tables', 2)
    if(total_map != null){
        for(var i=0;i<total_map.length;i++){
            var v = total_map[i].columnValue;
            if(v ==null){
                v ="";
            }
            var trHtml="<tr>\n" +
                "\t\t\t\t\t\t<td>"+total_map[i].currentColumnName+"：</td>\n" +
                "\t\t\t\t\t\t<td>"+v +"</td>\n" +
                "\t\t\t\t\t</tr>";
            $("#tables tbody").append(trHtml);
        }
    }
    $this.window('open');

}


function showTotalMap(id) {
    var total_map = $('#data').datagrid('getData').rows[id].total_map;
    /*$("#dataID").val("decryption");
    $("#search").submit();*/
    //decryption();
    var $this = $('#code');
    var $form = $this.find("form");
    $this.window({ title: '项目明细' });
    $this.window({
        top:($(window).height() - 240) * 0.5+$(document).scrollTop(),
        left:($(window).width() - 350) * 0.5
    });
    $("#tables tr").empty();
    //addTr2('tables', 2)
	if(total_map != null){
        for(var i=0;i<total_map.length;i++){
            var v = total_map[i].columnValue;
            if(v ==null){
                v ="";
            }
            var trHtml="<tr>\n" +
                "\t\t\t\t\t\t<td>"+total_map[i].currentColumnName+"：</td>\n" +
                "\t\t\t\t\t\t<td>"+v +"</td>\n" +
                "\t\t\t\t\t</tr>";
            $("#tables tbody").append(trHtml);
        }
	}
    $this.window('open');

}


//审核通过
function agree(id){

	
	$.messager.confirm('提示信息', '是否通过审核?', function(r){
        if (r){
        	$.messager.progress({
        		title:'请等待',
        		msg:'正在发送请求...',
        		timeout:5000
        	}); 
        	var order_money = $('#data').datagrid('getData').rows[id].order_money;
        	var order_id= $('#data').datagrid('getData').rows[id].order_id;
        	
        	var user_id = $('#data').datagrid('getData').rows[id].user_id;
        
        	
     
    		$.ajax({
                type: "POST",
                url: "agreeUserAgentRecharge",
                data: {
                	order_money:order_money,
                	order_id:order_id,
                	user_id:user_id
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
        	var order_id= $('#data').datagrid('getData').rows[id].order_id;
        	
        	var user_id = $('#data').datagrid('getData').rows[id].user_id;
        
        	
    		$.ajax({
                type: "POST",
                url: "disAgreeUserAgentRecharge",
                data: {
                
                	user_id:user_id,
                	order_id:order_id
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
$("#search").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param);//查询数据
	return false;
});

function sum(param){
	$.ajax({
		url : "queryTxSum",
		type : "POST",
		data:param,
		success : function(data) {
			if(data == null){
				return ;
			}
			queryDescOrAsc.withDrawMoney=data.withDrawMoney;
			var html = '\
				<span>可代付总额：¥ '+fmoney(data.withDrawMoney,2)+'\</span>\
				<span>已代付总额：¥ '+fmoney(data.sumMoney,2)+'\</span>\
				';
			
			$("#total").html(html);
		}
	});
}




loadData();//初始化加载数据





function closeAll() {
    $.messager.confirm('删除用户', '是否删除所有用户', function(r) {
        if (r) {
            $.ajax({
                url : "closeAgentAll",
                type : "POST",
                dataType : "json",
                data : {
                },
                success : function(data) {
                    $.messager.alert('提示信息', data.msg, 'info');
                    $('#search').submit();
                }
            });
        }
    });
}



//批量审核不通过
function allDisAgree(){
    if($('#data').datagrid('getChecked').length==0){
        $.messager.alert('提示', '请先勾选删除的用户!', 'error');
        return;
    }
    $.messager.confirm('提示信息', '是否删除这些用户?', function(r){
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
                url: "allAgentDisAgree",
                data: {
                    order_ids:order_ids
                },
                dataType: 'json',
                async: true,
                success: function(msg){
                    $.messager.progress("close");
                    $('#search').submit();
                    $.messager.alert('提示信息', '删除成功');
                }
            });

        }
    });
}

$('#brand_id').combobox({
    url : '../platform/queryBrandList',
    valueField : 'id',
    textField : 'textname',
    width : '150',
    height : '30',
    editable : false,
    panelHeight : '200'
});

$('#sala_rule_id').combobox({
    url : '../platform/queryDictSalaRule',
    valueField : 'id',
    textField : 'sala_title',
    width : '150',
    height : '30',
    editable : false,
    panelHeight : '200'
});