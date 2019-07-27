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
		url: "queryAgentDept",
		queryParams: param,//查询参数
		toolbar: '#toolbar',//工具栏
		columns: [[
		           {
		        	   width: 100,
		        	   field: 'create_date',
		        	   title: '创建时间',
		        	   formatter : dateTimeFormatter
		           },
		           {
		        	   width: 200,
		        	   field: 'dept_name',
		        	   title: '部门名称'
		           },
		           {
		        	   width: 100,
		        	   field: 'dept_parent_name',
		        	   title: '所属公司'					    
		           },
		           {
		        	   align: 'center',
		        	   width: 100,
		        	   field: 'dept_mobile',
		        	   title: '联系电话'
		           },
		           {
		        	   width: 100,
		        	   field: 'op',
		        	   title: '操作',
		        	   align: 'center',
		        	   formatter: function (value, row, index) {
		        		  if(deptType==4){
		        			  var str = '\
									<a class="btns" href="javascript:update(\''
									+ index
									+ '\');">编辑</a>\
									\<a class="btns" href="javascript:set(\'' + index + '\');">设置经纪人</a>\
						';
										return str;  
		        		  }else{
		        			  var str = '\<a class="btns" href="javascript:set(\'' + index + '\');">设置经纪人</a>\
								';
									return str;
		        		  }
		        		  
		        	   }
		           }
		           ]],
		           onLoadSuccess: function () {
		        	   $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
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
		url: "queryAgentDeptBrokerInfo",
		queryParams:param,// 查询参数
		toolbar: '#toolbar1',//工具栏
		columns: [[
					{
						checkbox:true,
						align:'center',
						width : 20,
						field : 'ck',
						title : '选择项'
					},
		           {
		        	   width: parseInt($(this).width() * 0.1),
		        	   field: 'broker_name',
		        	   align: 'center',
		        	   title: '经纪人名称'
		           },
		           {
		        	   width: parseInt($(this).width() * 0.1),
		        	   field: 'dept_name',
		        	   title: '部门名称'
		           }
		           ]],
		           onLoadSuccess: function () {
		        	   $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
		           }
	});
}

//设置经纪人
function set(id) {
	var dept_name=$('#data').datagrid('getData').rows[id].dept_name;
	var dept_id=$('#data').datagrid('getData').rows[id].dept_id;
	//打开用户转移窗口
	$('#broker_dept').window({
    	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 700) * 0.5
    });
	$('#broker_dept').window('open');
	//部门id
	//设置全局参数
	queryDescOrAsc.dept_id=dept_id;
	queryDescOrAsc.dept_name=dept_name;
	window.queryDeptId={
			dept_id:dept_id
	}
	$("#DID").val(dept_id); 
	loadBroker(queryDeptId);
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

//选择部门
function transferUser(){
	if($('#broker').datagrid('getChecked').length==0){
		$.messager.alert('提示', '请先勾选经纪人!', 'error');
		return;
	}
	
	
	/************经纪人id和姓名*****************/
	var brokers=$('#broker').datagrid('getChecked');
	var brokers_ids="";
	var brokers_names="";
	for(var i=0;i<brokers.length;i++){
		if(i==(brokers.length-1)){
			brokers_ids=brokers_ids+brokers[i].broker_id;
			brokers_names=brokers_names+brokers[i].broker_name;
		}else{
			brokers_ids=brokers_ids+brokers[i].broker_id+",";
			brokers_names=brokers_names+brokers[i].broker_name+",";
		}
	}
	/************经纪人id和姓名******************/
    $.ajax({
        type: "POST",
        url: "updAgentdeptBrokers",
        data: {
        	brokers_id:brokers_ids,
        	brokers_names:brokers_names,
        	dept_id:queryDescOrAsc.dept_id,
        	dept_name:queryDescOrAsc.dept_name
        },
        dataType: 'json',
        async: true,
        success: function(msg){
        	if(msg.success==true){
        		$.messager.alert('提示', msg.msg, 'info');
        		$('#broker').datagrid('clearChecked');
        		$('#broker').datagrid('reload');
        		$('#broker_dept').window('close');
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
	loadBroker(param);
	return false;
});


$("#refresh").click(function(){
    $("#broker_name").textbox('setValue','');
	$('#search1').submit();
});

//添加/修改方法
function update(id) {
	var $this = $('#update');
	var $form = $this.find("form");
	
	if (id == undefined) {	
		$this.form('clear');
		$this.window({ title: '添加' });
		$("#reset").linkbutton('enable');
		//只读
		$form.attr("action", $form.attr("action-add"));
	} else {
		$this.window({ title: '编辑' });
		// 重置无效
		$("#reset").linkbutton('enable');
		//只读
		$("#dept_name").textbox("enableValidation");
		$form.attr("action", $form.attr("action-edit"));
		$("#dept_name").val($('#data').datagrid('getData').rows[id].dept_name);
		$("#dept_id").val($('#data').datagrid('getData').rows[id].dept_id);
		
		$this.form('clear');
		$this.form('load', $('#data').datagrid('getData').rows[id]);
	}

	$this.window({
    	top:($(window).height() - 185) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 588) * 0.5
    });
	$this.window('open');
}


function tongbu()
{
	//alert('初始化模式一');
	$.ajax({
		url : "queryDeptForMon",
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