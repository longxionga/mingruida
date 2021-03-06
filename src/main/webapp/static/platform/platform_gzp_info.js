/**
 * 
 */
//dictInfo(1002,"is_use");
dict(1002);
dictInfo(1002,"is_zc")
function loadData(param) {
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
						border : !0,// 不显示边框
						striped : true,// 条纹行
						fit : true,// 自适应高度
						nowrap : true,// 单元格内文字禁止换行
						rownumbers : true,// 显示行号
						checkOnSelect : false, // 只有选择复选框时才选中
						selectOnCheck : false,// 选择行时选中复选框
						pagination : !0,// 显示分页
						pageSize : 20,// 每页显示数量 参数必须为pageList的对应
						pageList : [ 20,40,80 ],// 每页显示数量设置参数
						idField:'gzp_id',
						url : 'queryGzpInfo',
						remoteSort:false,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						columns : [ [
								{
									width: 100,
									sortable : true,
									field : 'gzp_name',
									title : '商品名称'
								},
								{
									width: 100,
									sortable : true,
									field : 'gzp_alias',
									title : '商品别名'
								},
								{
									width: 80,
									sortable : true,
									field : 'gzp_code',
									title : '商品标号'
								},
								{
									width: 80,
									sortable : true,
									field : 'gzp_number',
									title : '商品数型'
								},
								{
									width: 80,
									sortable : true,
									align : 'center',
									field : 'gzp_order',
									title : '商品排序'
								},
								{
									width: 80,
									align: 'center',
									sortable : false,
									field : 'gzp_unit',
									title : '商品单位'
								},
								{
									width: 80,
									sortable : true,
									field : 'gzp_type',
									title : '商品类型'
								},
								{
									width: 80,
									align: 'right',
									sortable : true,
									field : 'gzp_money',
									title : '商品价格'
								},
								{
									width: 80,
									align: 'right',
									sortable : true,
									field : 'gzp_poundage',
									title : '商品仓储费'
								},
								{
									width: 80,
									align: 'right',
									sortable : false,
									field : 'gzp_profit',
									title : '商品盈亏'
								},
								{
									width: 80,
									align: 'center',
									sortable : true,
									field : 'gzp_lot',
									title : '商品手数'
								},								
								{
									width: 90,
									sortable : false,
									field : 'gzp_buy_no_date',
									title : '商品非交易日期'
								},
								{
									sortable : false,
									field : 'gzp_buy_no_time',
									title : '商品非交易时间',
									width : 150
								},
								{
									width: 50,
									align: 'center',
									sortable : false,
									field : 'is_zc',
									title : '是否众筹',
									formatter : function(value, row, index) {
											if (value == 1)
												return '是';
											else
												return '否';
									}
								},
								{
									width: 50,
									sortable : false,
									field : 'is_use',
									title : '是否可用',
									align : 'center',
									//formatter:dictFormatter,
									formatter : function(value, row, index) {
										if (value == 1)
											return '是';
										else
											return '<span style="color:#f00;">否</span>';
									}
								},
								{
									width: 120,
									sortable : true,
									field : 'create_date',
									title : '创建时间'
								},
								{
									width: 150,
									field : 'op',
									title : '操作',
									align : 'center',
									formatter : function(value, row, index) {
										var str = '\
										<a class="btns" href="javascript:;" onclick="update(\'' + index + '\');">编辑</a>\
										<a class="btns" href="javascript:;" onclick="deptupdate(\'' + row.gzp_id + '\');">部门选择</a>\
									';
										return str;
									}
								} ] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
							$(".datagrid-body td[field='gzp_buy_no_time']").overTitle();
						}
					});
}

function loadData1(param) {
	$('#data1').treegrid(
					{
						//title : '部门选择',// 当前页面标题
						fitColumns : true,// 自动列宽
						autoRowHeight : true,// 自动行高度
						singleSelect : true,// 禁用选择多行
						selectOnCheck : false,// 选择行时选中复选框
						border : !1,// 不显示边框
						striped : true,// 条纹行
						fit : true,// 自适应高度
						nowrap : true,// 单元格内文字禁止换行
						rownumbers : true,// 显示行号
						animate:true,
						collapsible:true,
						url : "../sys/queryDeptInfoByPa?dept_type=4",
						idField:'dept_id',
						treeField:'dept_name',
						queryParams : param,// 查询参数
						toolbar : '#toolbar1',// 工具栏
						columns : [ [
								{title:'<input type=\"checkbox\" name=\"checkdept\" onclick=\"checkall();\"  id=\"checkdept\"></input>',field:'dept_id',formatter:function(value,rowData,rowIndex){
									//alert("接到的参数6"+rowData.dept_type);
									if(rowData.dept_type==4)
									{
										if(param !=undefined){										
										var strdept='';
											$.each(param, function(key, value) {
												strdept += value.dept_id + ",";												
											});	
																		
										
										if(strSplit(strdept,rowData.dept_id))
										{
											return "<input type=\"checkbox\" checked=\"checked\" name=\"checkdeptgdsy\" id='check_"+rowData.dept_id+"'></input>";
										}else
										{
											return "<input type=\"checkbox\" name=\"checkdeptgdsy\" id='check_"+rowData.dept_id+"'></input>";
										}
										}else
										{
											return "<input type=\"checkbox\" name=\"checkdeptgdsy\" id='check_"+rowData.dept_id+"'></input>";
										}
									}
								},width:70},
								{
									width : parseInt($(this).width() * 0.2),
									field : 'dept_name',
									title : '部门名称'
								},{
									width : parseInt($(this).width() * 0.1),
									field : 'dept_code',
									title : '部门编码'
								},{
									width : parseInt($(this).width() * 0.05),
									field : 'dept_type',
									title : '级别',
									formatter : function(value) {
										if(value==0){
											return "平台";
										}
										if(value==1){
											return "交易中心";
										}
										if(value==2){
											return "渠道部";
										}
										if(value==3){
											return "会员中心";
										}
										if(value==4){
											return "代理商";
										}
										if(value==5){
											return "";
										}
									}
								},
								{
									width : parseInt($(this).width() * 0.05),
									field : 'is_use',
									title : '是否有效',
									align : 'center',
									formatter : function(value) {
										if(value==1){
											return "有效";
										}else if(value==0){
											return "<span class='red'>已禁用</span>";
										}
										else if(value==4){
											return "<span class='red'>已退出</span>";
										}
									}
								}] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;border-radius:4px;line-height:12px;padding:2px 10px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
							$("#checkdept").attr("checked",false);
						}
					});
}


// 删除方法
function selDept(id) {
	selects = [ id ];
	if (id == undefined) {
		// 返回所有选中的记录
		selects = $('#data').datagrid('getChecked');
	}
	
	if (selects.length == 0) {
		$.messager.alert('提示', '您选中的记录数为0，请选择后再进行删除操作！', 'error');
		return false;
	}

	// 把选中后的id取出保存为数组型字符串
	var id = [];
	$.each(selects, function(i, k) {
		id.push(k.itemid);
	});
	id = id.join(",");
	

	$.messager.confirm('确认删除', '你选中了' + selects.length + '条记录, 确定要删除吗?',
			function(r) {
				if (r) {
					$.ajax({
						url : "/delGzpinfo",
						type : "POST",
						data : data,
						success : function(data) {
							if (data.success == true) {
								window.location.reload();
							} else {
								$.messager.alert('删除提示', data.msg, 'error');
							}
						}
					});
				}
			});
}

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




function deptupdate(id){
	//$("#data1").clear();	
	var $this = $('#deptupdate');
	$this.form('clear');
	//$this.form.refresh();
	var $form = $this.find("form");
	
	$("#data1").html("");
	$('#deptupdate').dialog('open');
	//var data = eval("({userid:'"+id+"'})")
	//gzp_id=idl
	$("#hgzp_id").val(id);	
	$.ajax({
		url : "queryGzpDpetInfo",
		type : "POST",
		dataType:"json",
		data : {				
			gzp_id:id
		},
		success : function(data){			 
			if(data.length>0)
			{	
				loadData1(data);
			}else
			{
				loadData1();
			}
		}
	});	
	
	$this.window({
    	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 700) * 0.5
    });
	$this.window('open');
}


// 添加/修改方法
var updateId;
function update(id) {
	var $this = $('#update');
	var $form = $this.find("form");
	updateId = id;
	if (updateId == undefined) {
		$this.form('clear');
		var thisData = {"is_use":1};
		$this.form('load', thisData);
		$this.window({
			title : '添加'
		});
		// 重置有效
		$("#reset").linkbutton('enable');
		$form.attr("action", $form.attr("action-add"));
		$("#gzp_name").textbox('readonly',false);
		$("#gzp_name").textbox("enableValidation");
	} else {
		$this.window({
			title : '编辑'
		});	
		// 重置有效
		$("#reset").linkbutton('enable');
		$form.attr("action", $form.attr("action-edit"));
		$("#gzp_name").textbox("disableValidation");
		//$("#gzp_id").val($('#data').datagrid('getData').rows[id].gzp_id);
		$this.form('clear');
		$this.form('load', $('#data').datagrid('getData').rows[id]);
	}
	

	$this.window({
    	top:($(window).height() - 427) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 900) * 0.5
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


function InitInfo()
{
	//alert('初始化模式一');
	$.ajax({
		url : "initGzpGoodsInfo",
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

function InitSimInfo()
{
    $.ajax({
        url : "initSimGoodsInfo",
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

//密码重置
function resetPwd(id){
	var user_id = $('#data').datagrid('getData').rows[id].user_id
	$.ajax({
		url : "/sys/resetPwd",
		type : "POST",
		dataType:"json",
		data : {
			user_id:user_id
		},
		success : function(data){
            $.messager.alert('提示信息', data.msg, 'info');
		}
	});
}

function checkall(){
	  //salert('ok');
	  //alert( $("[name='checkdept']").prop("checked"));
	  if($("[name='checkdept']").prop("checked"))
	  {
		  $("[name='checkdeptgdsy']").each(function(){
			   if($(this).prop("checked"))
			   {
			    //$(this).removeAttr("checked");
			   }else{
			    $(this).prop("checked",'true');
			   }
			  })
	  }
	  if(!$("[name='checkdept']").prop("checked"))
	  {
		  //alert("未选中");
		  $("[name='checkdeptgdsy']").each(function(){			   
			    $(this).removeAttr("checked");			   
			  })	   
	  }
	 
}

function getSelected(){ 
    var idList = "";  
     $("input:checked").each(function(){
        var id = $(this).attr("id"); 
         
        if(id.indexOf('check_type')== -1 && id.indexOf("check_")>-1)
            idList += id.replace("check_",'')+',';
          
     })
     
     idList = idList.substring(0,idList.length-1)
     var gzp_id = $("#hgzp_id").val();
     $.ajax({
		url : "saveGzpGoodsInfo",
		type : "POST",
		dataType:"json",
		data : {
			dept_id:idList,
			gzp_id:gzp_id
		},
		success : function(data){
            $.messager.alert('提示信息', data.msg, 'info');
            if(data.success==true)
            {
  
         	   $('#deptupdate').dialog('close');
            }
		}
	});
    //alert(idList+"---"+gdsy_id);
}

function getSelected1(){ 
    var idList = "";  
     $("input:checked").each(function(){
        var id = $(this).attr("id"); 
         
        if(id.indexOf('check_type')== -1 && id.indexOf("check_")>-1)
            idList += id.replace("check_",'')+',';
          
     })
     
     idList = idList.substring(0,idList.length-1)
     var gzp_id = $("#hgzp_id").val();
     $.ajax({
		url : "saveDeptDisable",
		type : "POST",
		dataType:"json",
		data : {
			dept_id:idList			
		},
		success : function(data){
            $.messager.alert('提示信息', data.msg, 'info');
            if(data.success==true)
            {             
         	   $('#deptdisable').dialog('close');
            }
		}
	});
    //alert(idList+"---"+gdsy_id);
}


//获取查询数据
/*function getParams(){
	//获取输入框的值
	var values = $("#dept_names").val();
	if(values!=''){
	var rows = $('#data1').treegrid('getData'),rst=[];
	$("tr[id^='datagrid-row-r2-2-'],tr[id^='datagrid-row-r2-1-']").removeClass("datagrid-row-selected");
	for(var i=0;i<rows.length;i++){
		if((rows[i].dept_name).indexOf(values,0)>-1){
			$("#datagrid-row-r2-2-"+rows[i].dept_id+",#datagrid-row-r2-1-"+rows[i].dept_id).addClass("datagrid-row-selected");
			rst.push(rows[i]);
		}	
		//$('#data1').treegrid('loadData',rst);
	  }
	}else{
		deptupdate($("#hgzp_id").val());
	}
}*/


function getParams(){
	//获取输入框的值
	var values = $("#dept_names").val();
	if(values!=''){
		var rows = $('#data1').treegrid('getData'),rst=[],rst1=[],rst2=[];
		for(var i=0;i<rows.length;i++){
			if((rows[i].dept_name).indexOf(values,0)>-1){
				rst.push(rows[i]);
			}else{
				rst1.push(rows[i]);
			}
		}
		rst2 = rst.concat(rst1);
		$('#data1').treegrid('loadData',rst2);
		$("tr[id^='datagrid-row-r2-2-'],tr[id^='datagrid-row-r2-1-']").removeClass("datagrid-row-selected");
		for(var i=0;i<rows.length;i++){
			if((rows[i].dept_name).indexOf(values,0)>-1){
				$("[node-id='"+rows[i].dept_id+"']").addClass("datagrid-row-selected");
				
			}
		}
		
	}else{
		deptupdate($("#hgzp_id").val());
	}
}

function getParams1(){
	//获取输入框的值
	var values = $("#dept_namess").val();
	if(values!=''){
		var rows = $('#data2').treegrid('getData'),rst=[],rst1=[],rst2=[];
		for(var i=0;i<rows.length;i++){
			if((rows[i].dept_name).indexOf(values,0)>-1){
				rst.push(rows[i]);
			}else{
				rst1.push(rows[i]);
			}
		}
		rst2 = rst.concat(rst1);
		$('#data2').treegrid('loadData',rst2);
		$("tr[id^='datagrid-row-r2-2-'],tr[id^='datagrid-row-r2-1-']").removeClass("datagrid-row-selected");
		for(var i=0;i<rows.length;i++){
			if((rows[i].dept_name).indexOf(values,0)>-1){
				$("[node-id='"+rows[i].dept_id+"']").addClass("datagrid-row-selected");
				
			}
		}
		
	}else{
		deptdisable();
	}
}

//刷新功能
$("#refresh").click(function(){
	deptupdate($("#hgzp_id").val());	
});

//刷新功能
$("#refresh1").click(function(){
	deptdisable();	
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
loadData({order_type:queryDescOrAsc.create_date});//初始化加载数据