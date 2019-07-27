/*dictInfo(1029,"broker_level");
dict(1029);*/
function loadData(param) {
	var  columns  =  [
	                  {
	                	   width: 50,
			        	   field: 'broker_name',
			        	   title: '用户名'
			           },
			           {
			        	   width: 100,
			        	   align: 'center',
			        	   field: 'broker_mobile',
			        	   title: '手机号'
			           },
			           {
			        	   width: 50,
			        	   field: 'broker_money',
			        	   title: '金额 ',
			        	   align: 'right',
			           },
			           {
			        	   field: 'settle_name',
			        	   title: '服务商 ',
			        	   width:100
			           },
			           {
			        	   field: 'agent_name',
			        	   title: '代理 ',
			        	   width:100
			           },
			           {
			        	   field: 'dept_name',
			        	   title: '部门',
			        	   width:100
			           },
			            {
			        	    field: 'broker_level',
			        	    title: '代理商等级',
                            width:100,
			        	    formatter:function (value, row, index) {
			        		     if(value=="1"){
			        		        return '普通代理商';
			        		    }else{
			        		 	   return '<span style="color:#f00;">高级代理商</span>';
			        		    }
			        	     },
			            },
			           {
			        	   width: 50,
			        	   align: 'center',
			        	   field: 'broker_incodes',
			        	   title: '邀请码 '	,
			        	  formatter:function (value, row, index) {
			        		   var broker_incodes = row.broker_incodes;
			        		    if(row.broker_incodes=="未开通"){
			        		       return '<span style="color:#f00;">未开通</span>';
			        		   }else{
			        			   return broker_incodes;
			        		   }
			        	   }
			           },
						{
			        	   width: 100,
			        	   field: 'parent_broker_name',
			        	   title: '上级经纪人 '
			           },
						{
							width: 100,
							field: 'parent_broker_mobile',
							title: '上级经纪人手机号 '
						},
			           {
			        	   width: 100,
			        	   field: 'create_date',
			        	   title: '创建时间 ',
			        	   formatter:dateTimeFormatter
			           },
			           {
			        	   width: 50,
			        	   field: 'is_use',
			        	   title: '是否可用',
			        	   align: 'center',
			        	   formatter: function (value, row, index) {
			        		   if (value == 2)
			        			   return '<span style="color:#f00;">否</span>';
			        		   else 
			        			   return '是';		        			  
			        	   }
			           },
         ]
	
		if(dept_type==0){
			columns.push({
			 field: 'op',
	  	   title: '操作',
	  	   align: 'center',
	  	   formatter: function (value, row, index) {
	  		 var str='\<a class="btns" href="javascript:queryBrokerUser(\''+ index+ '\');">更换经纪人</a>\
	           ';  
	  		   if(row.is_use==2){
	  			   str += '\<a class="btns" href="javascript:on(\''+ index+ '\');">启用</a>\ ';  			  
	  		   }/*else if(row.is_use==0){
	  			   str+= '\
	  			   <a class="btns" href="javascript:reset(\'' + index + '\');">重置密码</a>\  ';      		  			  
	  		   }*/else{
	  			   str+= '\
	      			   <a class="btns" href="javascript:off(\'' + index+'\');">禁用</a>\   ';  
	  			 if(row.broker_level==1){
		  			   str+='\<a class="btns" href="javascript:;" onclick="upBroker(\'' + row.broker_id + '\',\'' + row.agent_id + '\',\'' + row.broker_mobile + '\');">升级</a>';
		  			  // str += '\<a class="btns" href="javascript:upBroker(\''+ row.broker_id+ '\',\'' + row.agent_id + '\');">升为高级代理商</a>\';
		  		   }else if(row.broker_level==2){
		  			 str+='\<a class="btns" href="javascript:;" onclick="downBroker(\'' + row.broker_id + '\',\'' + row.agent_id + '\');">降级</a>';
		  		   }
	  		   }
	  		 return str;	
	  	   },
	  	   width: 250
		});
		};
// 		else if(dept_type==4){
// 			columns.push({
// 				 field: 'op',
// 		 	   title: '操作',
// 		 	   align: 'center',
// 		 	   formatter: function (value, row, index) {
// 		 		   var str='\<a class="btns" href="javascript:queryBrokerUser(\''+ index+ '\');">更换代理商</a>\ ';
// 		 		  if(row.is_use!=2 && row.is_use!=0){
// 		 			 if(row.broker_level==1){
// 			  			   str+='\<a class="btns" href="javascript:;" onclick="upBroker(\'' + row.broker_id + '\',\'' + row.agent_id + '\',\'' + row.broker_mobile + '\');">升级</a>';
// 			  			  // str += '\<a class="btns" href="javascript:upBroker(\''+ row.broker_id+ '\',\'' + row.agent_id + '\');">升为高级代理商</a>\';
// 			  		   }else if(row.broker_level==2){
// 			  			 str+='\<a class="btns" href="javascript:;" onclick="downBroker(\'' + row.broker_id + '\',\'' + row.agent_id + '\');">降级</a>';
// 			  		   }
// 		 		  }
//		 		  
// 		 		 return str;	
// 		 	   },
// 		 	   width: 250
// 			});
// }


columns = [columns];

	$('#data').datagrid({
		title: '',//当前页面标题
		fitColumns: false,//自动列宽
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
		url: "queryBroker",
		queryParams: param,//查询参数
		toolbar: '#toolbar',//工具栏
		columns: columns,
		           onLoadSuccess: function () {
		        	   $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
		           }
	});
}

function loadBrokerUser(param) {
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
		url: "queryBrokerUser",
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
		           /*{
		        	   width: parseInt($(this).width() * 0.1),
		        	   field: 'create_date',
		        	   align: 'center',
		        	   title: '创建时间'
		        	   formatter:dateTimeFormatter
		        	   
		           },*/
		           {
		        	   width: parseInt($(this).width() * 0.15),
		        	   field: 'broker_name',
		        	   align: 'center',
		        	   title: '代理商名称'
		           },
		          /* {
		        	   width: parseInt($(this).width() * 0.1),
		        	   field: 'broker_incodes',
		        	   align: 'center',
		        	   title: '邀请码',
		        	   formatter:function (value, row, index) {
		        		   var broker_incodes = row.broker_incodes;
		        		    if(row.broker_incodes=='未开通'){
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
		        	   title: '位属服务商'
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
		        		   if(row.is_use==0){
		        		       return '<span style="color:#f00;">未开通</span>';
		        		   }else{
		        			   var str = '\
			        			   <a class="btns" href="javascript:updBrokerUser(\''+ index+ '\');">更换</a>\
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


function reset(id){	
	$.messager.confirm('重置密码', '是否重置密码',
			function(r) {
				if (r) {
					var broker_id = $('#data').datagrid('getData').rows[id].broker_id;
					//alert(user_id);
					$.ajax({
						url : "updBrokerPwd",
						type : "POST",
						dataType:"json",
						data : {
							broker_id:broker_id
						},
						success : function(data){
				            $.messager.alert('提示信息', data.msg, 'info');
						}
					});
				}
			});
}	

function off(id){	
	$.messager.confirm('禁用用户', '是否禁用用户',
			function(r) {
				if (r) {
					var broker_id = $('#data').datagrid('getData').rows[id].broker_id;
					$.ajax({
						url : "updBrokerState",
						type : "POST",
						dataType:"json",
						data : {
							broker_id:broker_id,
							is_use:"2"
						},
						success : function(data){
				            $.messager.alert('提示信息', data.msg, 'info');
						}
					});
					loadData();//重新加载
				}
			});
}

function on(id){
	$.messager.confirm('启用用户', '是否启用用户',
			function(r) {
				if (r) {
					var broker_id = $('#data').datagrid('getData').rows[id].broker_id;
					$.ajax({
						url : "updBrokerState",
						type : "POST",
						dataType:"json",
						data : {
							broker_id:broker_id,
							is_use:"1"
						},
						success : function(data){
				            $.messager.alert('提示信息', data.msg, 'info');
						}
					});
					loadData();//重新加载
				}
			});
}

/**
 * 查询某代理商所属服务商下的所有代理商
 * @param id
 */
//设置代理商
function queryBrokerUser(id) {
	var broker_id = $('#data').datagrid('getData').rows[id].broker_id;
	var broker_name = $('#data').datagrid('getData').rows[id].broker_name;
	//将broker_id 设置到隐藏域
	$("#dataID").val(broker_id);
	//打开用户转移窗口
	$('#broker_id').window({
    	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 700) * 0.5
    });
	$('#broker_id').window('open');
	
	//部门id
	//设置全局参数
	queryDescOrAsc.broker_id=broker_id;
	queryDescOrAsc.broker_names=broker_name;
	
	loadBrokerUser(queryDescOrAsc);
}


$("#search").submit(function () {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param);//查询数据
	return false;
});
loadData();//初始化加载数据



//更换上级代理商
function updBrokerUser(id){
	var broker_id=$('#broker').datagrid('getData').rows[id].broker_id;
	var broker_incode=$('#broker').datagrid('getData').rows[id].broker_incode;
	var broker_name=$('#broker').datagrid('getData').rows[id].broker_name;
	 $.ajax({
	        type: "POST",      
	        url: "updBrokerUser",
	        data: {
	        	current_broker_id:broker_id,
	        	current_broker_incode:broker_incode,
	        	current_broker_name:broker_name,
	        	broker_id:queryDescOrAsc.broker_id,
	        	broker_name:queryDescOrAsc.broker_names
	        },
	        dataType: 'json',
	        async: true,
	        success: function(msg){
	        	if(msg.success==true){
	        		$.messager.alert('提示', msg.msg, 'info');
	        		//$('#broker').datagrid('clearChecked');
	        		$('#broker').datagrid('reload');
	        		$('#broker_id').window('close');
	            	$('#data').datagrid('reload');
	        	}else{
	        		$.messager.alert('提示', msg.msg, 'error');
	        	}
	            	
	           }
	     });
	}

$("#search1").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	
	loadBrokerUser(param);//查询数据
	return false;
});

$("#refresh").click(function(){
    $("#broker_name,#broker_mobile").textbox('setValue','');
	$('#search1').submit();
});

function tongbu()
{
	//alert('初始化模式一');
	$.ajax({
		url : "queryBrokerForMon",
		type : "POST",
		dataType:"json",
		data : {
			//user_id:user_id
		},
		success : function(data){
            $.messager.alert('提示信息', data.msg, 'info');
		}
	});
}


 function upBroker(b_id,agent_id,broker_mobile) {
	$.messager.confirm('确认升级为高级代理商', '确认升级为高级代理商?', function (r) {
		if (r) {
			$.ajax({
				url: "upBroker",
				type: "POST",
			data : {
					b_id:b_id,
				    agent_id:agent_id
			},
 				success: function (data) {
				if (data.success == true) {
					$.messager.alert('提示信息', data.msg, 'info');
					$('#data').datagrid('reload');
		} else {
					$.messager.alert('错误信息', data.msg, 'error');
				}
			}
 			});
		}
 	});
 }


 function downBroker(b_id,agent_id) {
	$.messager.confirm('确认降级为普通代理商', '确认降级为普通代理商?', function (r) {
	if (r) {
		$.ajax({
		url: "downBroker",
		type: "POST",
			data : {b_id:b_id,agent_id:agent_id},
 				success: function (data) {
					if (data.success == true) {
						$.messager.alert('提示信息', data.msg, 'info');
					$('#data').datagrid('reload');
				} else {
						$.messager.alert('错误信息', data.msg, 'error');
					}
		}
		});
		}
	});
}


//提现方式
 $('#broker_level').combobox({
 	width : '150',
 	height : '23',
 	valueField : 'label',
 	textField : 'value',
 	panelHeight : 'auto',
 	editable: false,
 	data : [ {
 	 	label : '1',
 	 	value : '代理商'
 	 },{
 	 	label : '2',
 	 	value : '代理商'
 	 } ]
 });

