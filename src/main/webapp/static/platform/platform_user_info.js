function loadData(param, url) {
	var  columns  =  [
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
				field : 'name',
				title : '姓名',
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
				title: '工资类型',
				align : 'center',
				formatter: function (value, row, index) {
					if (value == '01')
						return '员工';
					else
						return '<span style="color:#f00;">代理商</span>';
				}
			},
			{
				field : 'job',
				title : '岗位',
				align : 'right',
				width:80
			},
			{
				field : 'job_day',
				title : '入职时间',
				align : 'right',
				width:80
			},
			{
				field : 'word_days',
				title : '上班工作日',
				width:100
			},
			{
				field : 'leave_days',
				title : '请假天数',
				width:100
			},
			{
				field : 'month_check',
				title : '本月业绩达标',
				width:100
			},
			{
				field : 'month_uncheck',
				title : '本月未达标',
				width:80
			},

        {
            field : 'total_map',
            title : '薪资及绩效提成',
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
            title : '应扣工资',
            width:80,
            formatter : function(value, row, index) {
                var str = '\
		                  <a class="btns" href="javascript:showDeductMap(\'' + index + '\');">查看明细</a>\
		                  ';
                return str;
            }
        },
        {
            field : 'total_amount',
            title : '应发工资',
            width:100
        },
        {
            field : 'deduct_amount',
            title : '预支工资',
            width:80
        },
        {
            field : 'real_amount',
            title : '实发工资',
            width:100
        },
        {
            field : 'notes',
            title : '备注',
            width:160
        },
			{
				field : 'create_time',
				title : '创建时间',
				formatter : dateTimeFormatter,
				width:110
			},
			{
				field : 'status',
				title : '是否可用',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 1)
						return '是';
					else if(value==2)
						return '<span style="color:#f00;">否</span>';
					else
						return '<span style="color:#f00;">已注销</span>';
				},
				width:50
			},
			/*{
				field : 'op',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					if (row.is_use == 1) {
						var str = '\
		                  <a class="btns" href="javascript:reset(\''+ index+ '\');">密码重置</a>\
		                  <a class="btns" href="javascript:off(\''+ index+ '\');">禁用用户</a>\
		                  ';
						if (dept_type == 4) {

							str += '\<a class="btns" href="javascript:queryBrokers(\''+ index+ '\');">更换经纪人 </a>';
						}
						return str;
					} else {
						var str = '\
		                   <a class="btns" href="javascript:on(\''+ index + '\');">启用用户</a>';
						if (dept_type == 4) {
							str += '\<a class="btns" href="javascript:queryBrokers(\''+ index+ '\');">更换经纪人 </a>';
						}

						return str;
					}
				}*/
             ];
	
	if(dept_type == 0){
		columns.push({
			field : 'op',
			title : '操作',
			align : 'center',
			formatter : function(value, row, index) {
				if (dept_type == 0){
					if (row.status == 1) {
                        var str = '\
		                  <a class="btns" href="javascript:reset(\''+ index+ '\');">密码重置</a>\
		                  <a class="btns" href="javascript:off(\''+ index+ '\');">删除用户</a>\
		                  ';
					}
					else if (row.status == 3) {
						var str = '';

						/*if (dept_type == 4) {
							str += '\<a class="btns" href="javascript:queryBrokers(\''+ index+ '\');">更换经纪人 </a>';
						}
		*/
						return str;
					}else if(row.status==2){
						var str = '\
							   <a class="btns" href="javascript:on(\''+ index + '\');">启用用户</a>\
							   ';
						return str;
					}

                    if (row.id_card_auth === '' || row.id_card_auth === '01' || row.id_card_auth === '03') {
                        str += '\<a class="btns" href="javascript:idCardAuth(\''+ index+ '\');">身份认证 </a>';
                    }
                    return str;
				}
			},
			width:200
		});
	};
// <a class="btns" href="javascript:logout(\''+ index+ '\');">注销</a>\
	
	if(dept_type == 4){
		columns.unshift({
			checkbox : true,
			align : 'center',
			width : 20,
			//hidden:true,
			field : 'ck',
			title : '选择项'	
		});
	};	
	
	columns = [columns];	
	$('#data')
			.datagrid(
					{
						title : '',// 当前页面标题
						fitColumns : false,// 自动列宽
						autoRowHeight : true,// 自动行高度
						singleSelect : true,// 禁用选择多行
						border : !0,// 不显示边框
						striped : true,// 条纹行
						fit : true,// 自适应高度
						nowrap : true,// 单元格内文字禁止换行
						rownumbers : true,// 显示行号
						checkOnSelect : false, // 只有选择复选框时才选中
						selectOnCheck : false,// 选择行时选中复选框
						pagination : !0,// 显示分页
						pageSize : 20,// 每页显示数量 参数必须为pageList的对应
						pageList : [ 20, 40, 80 ],// 每页显示数量设置参数
						url : url,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						/*frozenColumns:[[
							{
								field : 'user_name',
								title : '用户名'
							}
		        		]],*/
						columns :columns,
						onLoadSuccess : function() {
                            var imgUrl=$(".datagrid-body td[field='id_card_front']");
                            imgUrl.overDialog('<div class="over_dialog_img"><img src="#" /></div>');
                            var imgUrl2=$(".datagrid-body td[field='id_card_back']");
                            imgUrl2.overDialog('<div class="over_dialog_img"><img src="#" /></div>');
							$("head")
									.append(
											"<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}
function loadBroker(param) {
	$('#broker').datagrid({
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
		url: "queryBrokers",
		queryParams: param,//查询参数
		toolbar: '#toolbar1',//工具栏
		columns: [[
					/*{
						checkbox:true,
						align:'center',
						width : 20,
						field : 'ck',
						title : '选择项'
					},*/
		          /* {
		        	   width: parseInt($(this).width() * 0.1),
		        	   field: 'create_date',
		        	   align: 'center',
		        	   title: '创建时间'
		        	   formatter : dateTimeFormatter
		           },*/
		          {
		        	   width: parseInt($(this).width() * 0.15),
		        	   field: 'broker_name',
		        	   align: 'center',
		        	   title: '经纪人名称'
		           },
		           /*{
		        	   width: parseInt($(this).width() * 0.1),
		        	   field: 'broker_incodes',
		        	   align: 'center',
		        	   title: '邀请码',
		        	   formatter:function (value, row, index) {
		        		   var broker_incodes = row.broker_incodes;
		        		    if(row.broker_incodes=="未开通"){
		        		       return '<span style="color:#f00;">未开通</span>';
		        		   }else{
		        			   return broker_incodes;
		        		   }
		        	   }
		           },  */
		           {
		        	   width: parseInt($(this).width() * 0.15),
		        	   field: 'broker_mobile',
		        	   align: 'center',
		        	   title: '手机号'
		           }, 
		           {
		        	   width: parseInt($(this).width() * 0.18),
		        	   field: 'agent_name',
		        	   align: 'center',
		        	   title: '位属代理商'
		           }, 
		           {
		        	   width: parseInt($(this).width() * 0.3),
		        	   field: 'dept_name',
		        	   align: 'center',
		        	   title: '位属部门'
		           },
		           {
		        	   width: parseInt($(this).width() * 0.12),
		        	   field: 'op',
		        	   title: '操作',
		        	   align: 'center',
		        	   formatter: function (value, row, index) {		        		        
		        		   if(row.status==0){
		        		       return '<span style="color:#f00;">未开通</span>';
		        		   }else{
		        			   var str = '\
			        			   <a class="btns" href="javascript:updateUserBroker(\''+ index+ '\');">更换</a>\
									';
			        			   return str;
		        		   }
		        	   }}
		           ]],
		           onLoadSuccess: function () {
		        	   $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
		           }
	});
}
function reset(id) {
	$.messager.confirm('重置密码', '是否确认重置密码', function(r) {
		if (r) {
			var ids = $('#data').datagrid('getData').rows[id].id;
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

function weixinreset(id,type) {
	$.messager.confirm('微信重置', '是否确认重置微信信息', function(r) {
		if (r) {
			var ids = $('#data').datagrid('getData').rows[id].id;
			$.ajax({
				url : "updUserWeiXinInfo",
				type : "POST",
				dataType : "json",
				data : {
					id : ids,
					type : type
				},
				success : function(data) {
					$.messager.alert('提示信息', data.msg, 'info');
				}
			});
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
			var ids = $('#data').datagrid('getData').rows[id].id;
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
			var ids = $('#data').datagrid('getData').rows[id].id;
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

//用户注销
// function logout(id) {
// 	$.messager.confirm('注销用户', '是否注销用户', function(r) {
// 		if (r) {
// 			var ids = $('#data').datagrid('getData').rows[id].id;
// 			var name = $('#data').datagrid('getData').rows[id].name;
// 			var mobiles = $('#data').datagrid('getData').rows[id].mobile;
//
// 			$.ajax({
// 				url : "logoutUserInfoState",
// 				type : "POST",
// 				dataType : "json",
// 				data : {
// 					id : ids,
// 					name:name,
// 					status : "3"
// 					//mobile : mobiles
// 				},
// 				success : function(data) {
// 					$.messager.alert('提示信息', data.msg, 'info');
// 				}
// 			});
// 			loadData();// 重新加载
// 		}
// 	});
// }

/**
 * 查询登陆代理商下的所有经纪人 
 * @param id
 */
//设置经纪人
/*function queryBrokers(id) {
	var agent_id = $('#data').datagrid('getData').rows[id].agent_id;
	var user_id = $('#data').datagrid('getData').rows[id].user_id;
	//将agent_id 设置到隐藏域
	$("#dataID").val(agent_id);
	//打开用户转移窗口
	$('#broker_id').window('open');
	//$('#broker').datagrid('reload');
	
	//设置全局参数	
	queryDescOrAsc.user_id=user_id;
	queryDescOrAsc.agent_id=agent_id;
	
	loadBroker(queryDescOrAsc);
}*/

$("#search1").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	
	loadBroker(param);//查询数据
	return false;
});
//刷新功能
$("#refresh").click(function(){
	$('#search1').submit();
});

$("#search").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param, "queryUserlogin");// 查询数据
	return false;
});

$("#decryption").click(function(){
	/*$("#dataID").val("decryption");
	$("#search").submit();*/
	//decryption();
	var $this = $('#code');
	var $form = $this.find("form");
	$('#codeform').form('clear');	        	
	$("#reset").linkbutton('enable');
	$this.window({ title: '请填写验证码' });		
	$form.attr("action", $form.attr("action-code"));
	$this.window({
    	top:($(window).height() - 240) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 350) * 0.5
    });
	$this.window('open');
	
});

function showDeductMap(id) {
    var total_map = $('#data').datagrid('getData').rows[id].deduct_map;
    /*$("#dataID").val("decryption");
    $("#search").submit();*/
    //decryption();
    var $this = $('#code');
    var $form = $this.find("form");
    $this.window({ title: '应扣工资' });
    $this.window({
        top:($(window).height() - 240) * 0.5+$(document).scrollTop(),
        left:($(window).width() - 350) * 0.5
    });
    $("#tables tr").empty();
    //addTr2('tables', 2)
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
    $this.window('open');

}


function showTotalMap(id) {
    var total_map = $('#data').datagrid('getData').rows[id].total_map;
    /*$("#dataID").val("decryption");
    $("#search").submit();*/
    //decryption();
    var $this = $('#code');
    var $form = $this.find("form");
    $this.window({ title: '薪资及绩效提成' });
    $this.window({
        top:($(window).height() - 240) * 0.5+$(document).scrollTop(),
        left:($(window).width() - 350) * 0.5
    });
    $("#tables tr").empty();
    //addTr2('tables', 2)
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
    $this.window('open');

}

/**
 * 为table指定行添加一行
 *
 * tab 表id
 * row 行数，如：0->第一行 1->第二行 -2->倒数第二行 -1->最后一行
 * trHtml 添加行的html代码
 *
 */
function addTr(tab, row, trHtml){
    //获取table最后一行 $("#tab tr:last")
    //获取table第一行 $("#tab tr").eq(0)
    //获取table倒数第二行 $("#tab tr").eq(-2)
    //$tr.after(trHtml);
    $("#"+tab+" tbody").append(trHtml);
}

function addTr2(tab, row){
    var trHtml="<tr align='center'><td width='30%'><input type='checkbox' name='ckb'/></td><td width='30%'>地理</td><td width='30%'>60</td></tr>";
    addTr(tab, row, trHtml);
}

function decryption(){//点击解密发送短信验证码
	 var setTime;
     var elem = $(".j_account_repicksms").find("span");
     var elemCount = $(".j_account_repicksms").find("span").html();
     var hideObj = $(".j_account_repicksms").show();
     $(".j_account_getsms").hide();
     setTime = setInterval(function () {
         if (elem.html() == 0) {
             hideObj.hide().parent().find(".j_account_getsms").show();
             elem.html(60);
             clearInterval(setTime);
             return false;
         }
         elemCount--;
         elem.html(elemCount);
     }, 1000);
	
	 $.ajax({
        type: "POST",      
        url: "sendPhoneCode",
        dataType: 'json',
        async: true,
        success: function(msg){  
        	if(msg.success==true){
        		$.messager.alert('提示信息', "信息发送成功,请注意查收手机信息", 'info');
        	}else{
        		$.messager.alert('提示', "信息发送失败,一分钟后请重新获取", 'error');
        	}
        }
 
     });
}

function checkCode(){
	var decryption_code= $('#decryption_code').val();
	$.ajax({
        type: "POST",      
        url: "checkCode",
        dataType: 'json',
        async: true,
        data:{decryption_code:decryption_code},
        success: function(msg){  
        	if(msg.success==true){	
        		$('#code').window('close');
        		$("#dataID").val("decryption");
        		$("#search").submit();
        	}else{
        		$.messager.alert('提示', "验证码输入有误", 'error');
        	}
           }
 
     });
}

loadData('','queryUserlogin');//初始化加载数据
//loadData();// 初始化加载数据



function idCardAuth(id) {
    $.messager.confirm('身份认证', '是否认证通过', function(r) {
        if (r) {
            var ids = $('#data').datagrid('getData').rows[id].id;
            $.ajax({
                url : "updUserInfoidCardAuth",
                type : "POST",
                dataType : "json",
                data : {
                    id : ids,
                    id_card_auth : "02"
                },
                success : function(data) {
                    $.messager.alert('提示信息', data.msg, 'info');
                }
            });
            loadData();// 重新加载
        }else{
            var ids = $('#data').datagrid('getData').rows[id].id;
            $.ajax({
                url : "updUserInfoidCardAuth",
                type : "POST",
                dataType : "json",
                data : {
                    id : ids,
                    id_card_auth : "03"
                },
                success : function(data) {
                    $.messager.alert('提示信息', data.msg, 'info');
                }
            });
            loadData();// 重新加载
		}
    });
}


function closeAll() {
    $.messager.confirm('删除用户', '是否删除所有用户', function(r) {
        if (r) {
            $.ajax({
                url : "closeAll",
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
                url: "allDisAgree",
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

