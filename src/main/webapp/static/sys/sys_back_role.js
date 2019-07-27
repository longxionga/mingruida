dictInfo(1001,"dict_id");
function loadData(param) {
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
						url : "queryBackRole",
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						columns : [ [
								{
									width: 100,
									field : 'role_name',
									title : '角色名称'
								},
								{
									width: 100,
									field : 'role_desc',
									title : '角色说明'
								},
								{
									width: 100,
									field : 'dict_name',
									title : '等级'
								},
								{
									width: 50,
					            	 field : 'is_use',
					            	 title : '是否可用',
					            	 align: 'center',
					            	 formatter: function (value, row, index) {
					            		 if (value == 1)
					            			 return '是';
					            		 else
					            			 return '<span style="color:#f00;">否</span>';
					            	 }
								},
								{
									width: 100,
									field : 'create_date',
									title : '创建时间'
								},
								{
									width: 120,
									field : 'op',
									title : '操作',
									align : 'center',
									formatter : function(value, row, index) {
										var str = '\
										<a class="btns" href="javascript:;" onclick="update(\'' + index + '\');">编辑</a>\
										<a class="btns" href="javascript:;" onclick="menu(\'' + row.role_id + '\');">菜单分配</a>\
										<a class="btns" href="javascript:;" onclick="user(\'' + row.role_id+'\',\'' + row.dict_id + '\');">人员分配</a>\
									';
										return str;
									}
								} ] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}
//加载菜单信息
function loadMenu(param) {
		
	$('#menu').treegrid(
			{
				title : '',// 当前页面标题
				fitColumns : true,// 自动列宽
				autoRowHeight : true,// 自动行高度
				singleSelect : false,// 禁用选择多行
				border : !1,// 不显示边框
				striped : true,// 条纹行
				fit : true,// 自适应高度
				nowrap : true,// 单元格内文字禁止换行
				rownumbers : true,// 显示行号
				checkOnSelect : false, // 只有选择复选框时才选中
				selectOnCheck : false,// 选择行时选中复选框
				toolbar : '#toolbar2',// 工具栏
				//url : "queryMenuForRole?role_id=6",					
				url : "queryBackMenu",	
				idField:'menu_id',
				treeField: 'menu_name',				
					columns : [ [
					{title:'<input type=\"checkbox\" name=\"checkmenu\" onclick=\"checkall();\"  id=\"checkmenu\"></input>',field:'menu_id',formatter:function(value,rowData,rowIndex){
						
							if(param !=undefined){	
								var strmenu='';
									$.each(param, function(key, value) {
										strmenu += value.menu_id + ",";												
									});										
								if(strmenu.indexOf(rowData.menu_id)>-1)
								{
									return "<input type=\"checkbox\" checked=\"checked\" name=\"checkmenurole\" onclick='checkOne(\""+rowData.menu_id+"\",\"" + rowData.menu_parent_id + "\");' id='check_"+rowData.menu_id+"'></input>";
								}else
								{
									return "<input type=\"checkbox\" name=\"checkmenurole\" onclick='checkOne(\""+rowData.menu_id+"\",\"" + rowData.menu_parent_id + "\");'  id='check_"+rowData.menu_id+"'></input>";
								}
								}else
								{
									return "<input type=\"checkbox\" name=\"checkmenurole\"  onclick='checkOne(\""+rowData.menu_id+"\",\"" + rowData.menu_parent_id + "\");'  id='check_"+rowData.menu_id+"'></input>";
								}
						
					},width:50},
								
				             {
				            	 sortable : false,
				            	 width : parseInt($(this).width() * 0.1),
				            	 field : 'menu_name',
				            	 title : '菜单名称'
				             }] ],
				         	onLoadSuccess : function() {		         		
								$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
							}
			});
}

$("#search1").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadUser(param);
	return false;
});

//加载人员信息
function loadUser(list,dict_id,role_id){
	$('#user').treegrid(
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
				queryParams : {dict_id:dict_id,role_id:role_id},// 查询参数
				toolbar : '#toolbar3',// 工具栏
				url : "queryTreeUser",	
				idField:'user_id',
				treeField: 'user_name',
				columns : [ [
				             {title:'<input type=\"checkbox\" name=\"checkuser\" onclick=\"checkall1();\"  id=\"checkuser\"></input>',field:'user_id',formatter:function(value,rowData,rowIndex){
						
							if(list !=null && list!='' && list!=undefined){	
								var struser='';
									$.each(list, function(key, value) {
										struser += value.user_id + ",";												
									});										
								//if(struser.indexOf(rowData.user_id)>-1)
								if(strSplit(struser,rowData.user_id))	
								{
									return "<input type=\"checkbox\" checked=\"checked\" name=\"checkuserrole\" id='check_"+rowData.user_id+"'></input>";
								}else
								{
									return "<input type=\"checkbox\" name=\"checkuserrole\" id='check_"+rowData.user_id+"'></input>";
								}
								}else
								{
									return "<input type=\"checkbox\" name=\"checkuserrole\" id='check_"+rowData.user_id+"'></input>";
								}
						
					},width:50},
				             {
				            	 sortable : false,
				            	 width : parseInt($(this).width() * 0.1),
				            	 field : 'user_nick_name',
				            	 title : '用户名称'
				             }] ],
				         	onLoadSuccess : function() {
								$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
							}
			});
}

//菜单分配
function menu(role_id){	
	var $this = $('#menuupdate');
	var $form = $this.find("form");
	$this.form('clear');
	$this.window({
    	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 700) * 0.5
    });
	$this.window('open');
	$("#menu").html("");
	$('#menuupdate').dialog('open');
	$("#role_id").val(role_id);
	
	$.ajax({
		url : "queryRoleMenu",
		type : "POST",
		dataType:"json",
		data : {				
			role_id:role_id
		},
		success : function(data){	
		
			if(data.length>0)
			{					
				loadMenu(data);
			}else
			{
				loadMenu();
			}
		}
	});	
	loadMenu();		
}

//菜单分配
function saveMenu(id){	
	var url = "saveMenu";	
	selects = [ id ];	
	if (id == undefined) {
		// 返回所有选中的记录
		selects = $('#menu').treegrid('getChecked');	
		
	}	
	
	var modules = '';
		for(var i=0; i<selects.length; i++){
			if (modules != '') modules += ',';
			modules += selects[i].menu_id;
		}	
	
	var roleId=document.getElementById("roleId").value;
    $.post(url, {roleId:roleId, menuIds: modules}, function (data){
        if(data.success) {
        $('#menuDiv').window('close');
        	 alert(data.msg);
        }else{
        	 alert(data.msg);
        }
    }, "json");
	
}

//人员分配
function user(role_id,dict_id){
	/*var $this = $('#userDiv');
	var $form = $this.find("userForm");
	$this.window('open');
	$this.panel({
		title : '人员分配'
	});
	$("#roleId").val(role_id);
	loadUser(role_name);*/
	
	var $this = $('#userupdate');
	var $form = $this.find("form");
	$this.form('clear');
	//$("#user_nick_names").textbox('setValue',"");
	$this.window({
    	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 700) * 0.5
    });
	$("#user").html("");
	$this.window('open');
	$("#role_id").val(role_id);
	$("#dict_id").val(dict_id);
	$.ajax({
		url : "queryRoleUser",
		type : "POST",
		dataType:"json",
		data : {				
			role_id:role_id
		},
		success : function(data){	
		
			if(data.length>0)
			{	
				loadUser(data,dict_id,role_id);
			}else
			{
				loadUser("",dict_id,role_id);
			}
		}
	});	
	loadUser("",dict_id,role_id);
		
}

//人员分配
function saveUser(id){	
	var url = "saveUser";	
	selects = [ id ];	
	
	if (id == undefined) {
		// 返回所有选中的记录
		selects = $('#user').treegrid('getChecked');	
		
	}	
	
	var modules = '';
		for(var i=0; i<selects.length; i++){
			if (modules != '') modules += ',';
			modules += selects[i].user_id;
		}	
	
	var roleId=document.getElementById("roleId").value;
    $.post(url, {roleId:roleId, userIds: modules}, function (data){
        if(data.success) {
        $('#userDiv').window('close');
        	 alert(data.msg);
        }else{
        	 alert(data.msg);
        }
    }, "json");
 
}


// 添加/修改方法
var updateId;
function update(id) {
	var $this = $('#update');
	var $form = $this.find("form");
	updateId=id;
	if (updateId == undefined) {
		//设置默认值为是		
		$this.form('clear');
		var thisData = {"is_use":1};
		$this.form('load', thisData);
		$form.attr("action", $form.attr("action-add"));
		$("#role_name").textbox('readonly',false);
		$("#role_name").textbox("enableValidation");
		$this.window({ title: '添加' });
	} else {
		$this.window({
			title : '编辑'
		});
		$form.attr("action", $form.attr("action-edit"));
		$("#role_name").textbox("disableValidation");
		$('#role_name').textbox('readonly');
		//$("#role_id").val($('#data').datagrid('getData').rows[id].role_id);
		$this.form('clear');
		$this.form('load', $('#data').datagrid('getData').rows[id]);
	}

	$this.window({
    	top:($(window).height() - 258) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 588) * 0.5
    });
	$this.window('open');
}
//为重置按钮添加单击事件 
$("#reset").click(function(){
	if (updateId == undefined) {
		$('#updateform').form('clear');
		var thisData = {"is_use":1};
		$('#update').form('load', thisData);
	}else{
		update(updateId);	
	}	
});

$("#search").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param);//查询数据
	return false;
});
function checkall(){
	if($("[name='checkmenu']").prop("checked"))
	{
		$("[name='checkmenurole']").each(function(){			
			$(this).prop("checked",'true');
			/*if($(this).prop("checked"))
			{
			}else{
				$(this).prop("checked",'true');
			}*/
		});
	}
	if(!$("[name='checkmenu']").prop("checked"))
	{
		$("[name='checkmenurole']").each(function(){			   
			$(this).removeAttr("checked");			   
		});
	}

}
//改变多选框状态时触发
function checkOne(menu_id,menu_parent_id){
	var pid="check_"+menu_parent_id;	
	var chk = document.getElementById(pid);
	chk.checked = false;//默认父级菜单是非选中状态，只要有一个子菜单被选中则父级被选中
	var cid="check_"+menu_id;
	var zchk = document.getElementById(cid);
	//当该多选框为非选中状态时，取消选中以及其子菜单的选中状态
	if(!zchk.checked)	
	{	
		$(this).removeAttr("checked");
		//如果本身是父级节点需要取消该节点下的所有子节点
		// 选子节点
		var nodes = $("#menu").treegrid("getChildren", menu_id);
		for (var i = 0; i < nodes.length; i++) {
			$(('#check_' + nodes[i].menu_id))[0].checked = false;

		}
	}
	//当多选框的状态为选中时，选中该菜单及其所有子菜单和上级菜单
	if(zchk.checked)
	{		 
		$(this).prop("checked");
		var pid="check_"+menu_parent_id;	
		var chk = document.getElementById(pid);		  
		//如果本身是父级节点需要勾选该节点下的所有子节点
		//var s = '#check_' + menu_parent_id;	
		//选子节点
		var nodes = $("#menu").treegrid("getChildren", menu_id);
		for (i = 0; i < nodes.length; i++) {
			$(('#check_' + nodes[i].menu_id))[0].checked =true;

		}	    
	}	  
	//只要有一个子菜单被勾选则父级菜单被勾选
	var nodesp = $("#menu").treegrid("getChildren", menu_parent_id);
	for (i = 0; i < nodesp.length; i++) {
		if($(('#check_' + nodesp[i].menu_id))[0].checked==true){
			chk.checked = true;
		}

	}

}




function checkall1(){
	  if($("[name='checkuser']").prop("checked"))
	  {
		  $("[name='checkuserrole']").each(function(){
			   if($(this).prop("checked"))
			   {
			    //$(this).removeAttr("checked");
			   }else{
			    $(this).prop("checked",'true');
			   }
			  });
	  }
	  if(!$("[name='checkuser']").prop("checked"))
	  {
		  $("[name='checkuserrole']").each(function(){			   
			    $(this).removeAttr("checked");			   
			  });	   
	  }
}

function getSelected(){ 
    var idList = "";  
     $("input:checked").each(function(){
        var id = $(this).attr("id"); 
         
        if(id.indexOf('check_type')== -1 && id.indexOf("check_")>-1)
            idList += id.replace("check_",'')+',';
          
     });
     idList = idList.substring(0,idList.length-1);
     var role_id = $('#role_id').val();
     $.ajax({
		url : "saveMenu",
		type : "POST",
		dataType:"json",
		data : {
			menu_id:idList,
			role_id:role_id
		},
		success : function(data){
            $.messager.alert('提示信息', data.msg, 'info');
           if(data.success==true)
           {
        	   $('#menuupdate').dialog('close');
           }
		}
		
	});
}

function getSelected1(){ 
    var idList = "";  
     $("input:checked").each(function(){
        var id = $(this).attr("id"); 
         
        if(id.indexOf('check_type')== -1 && id.indexOf("check_")>-1)
            idList += id.replace("check_",'')+',';
          
     });
     idList = idList.substring(0,idList.length-1);
     var role_id = $('#role_id').val();
     $.ajax({
		url : "saveUser",
		type : "POST",
		dataType:"json",
		data : {
			userIds:idList,
			roleId:role_id
		},
		success : function(data){
            $.messager.alert('提示信息', data.msg, 'info');
           if(data.success==true)
           {
        	   $('#userupdate').dialog('close');
           }
		}
		
	});
}

loadData({order_type:queryDescOrAsc.create_date});//初始化加载数据


//添加/修改方法
function upload() {
	var $this = $('#upload');
	$this.window('open');
	
	$this.panel({
		title : '上传图片'
	});
}


//获取查询数据
function getParams(){
	//获取输入框的值
	var values = $("#user_nick_names").val();
	if(values==undefined || values==""){
		user($("#role_id").val(),$("#dict_id").val());	
	}else{
		var rows = $('#user').treegrid('getData'),rst=[],zd=[],bzd=[];

		for(var i=0;i<rows.length;i++){
			if((rows[i].user_nick_name).indexOf(values,0)>-1){
				zd.push(rows[i]);
			}	else{
				bzd.push(rows[i]);
			}
			
		}
		rst=zd.concat(bzd);
		$('#user').treegrid('loadData',rst);
		$("tr[id^='datagrid-row-r2-2-'],tr[id^='datagrid-row-r2-1-']").removeClass("datagrid-row-selected");
		for(var i=0;i<rows.length;i++){
			if((rows[i].user_nick_name).indexOf(values,0)>-1){
				$("[node-id='"+rows[i].user_id+"']").addClass("datagrid-row-selected");

			}
		}
	}
	
}
//刷新功能
$("#refresh").click(function(){
	$("#user_nick_names").textbox('setValue',"");
	$("tr[id^='datagrid-row-r2-2-'],tr[id^='datagrid-row-r2-1-']").removeClass("datagrid-row-selected");
	user($("#role_id").val(),$("#dict_id").val());	
});


function strSplit(parm,str)
{
	//str="2,2,3,5,6,6"; //这是一字符串 
	var strs= new Array(); //定义一数组 
	strs=parm.split(","); //字符分割 
	for (i=0;i<strs.length ;i++ ) 
	{ 
		if(str==strs[i])
		{

			return true;
		}
		//document.write(strs[i]+"<br/>"); //分割后的字符输出 		
	}
	return false;
}

