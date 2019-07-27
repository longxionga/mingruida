/**
 *
 */
//dictInfo(1002,"is_use");
dict(1002);
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
						idField:'id',
						url : 'queryTradeOrderInfoPageList',
						remoteSort:false,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						columns : [ [
                            {
                                width: 150,
                                sortable : true,
                                field : 'order_id',
                                title : '订单id'
                            },

								{
									width: 80,
									sortable : true,
									field : 'merchant_code',
									title : '商户编号'
								},
								{
									width: 100,
									sortable : true,
									field : 'merchant_name',
									title : '商户名称'
								},
                            {
                                width: 100,
                                sortable : true,
                                field : 'merchant_code',
                                title : '直属代理商编号'
                            },
                            {
                                width: 150,
                                sortable : true,
                                field : 'broker_name',
                                title : '直属代理商名称'
                            },
							{
								width: 100,
								sortable : true,
								field : 'brand_name',
								title : '机具所属品牌'
							},
                            {
                                width: 80,
                                sortable : true,
                                field : 'settle_type',
                                title : '结算类型'
                            },
                            {
                                width: 100,
                                sortable : true,
                                field : 'machine_code',
                                title : '机具编号'
                            },
                            {
                                width: 100,
                                sortable : true,
                                field : 'merchant_rate',
                                title : '商户费率(%)'
                            },
								{
									width: 80,
									sortable : true,
									field : 'total_amount',
									title : '交易金额'
								},
								{
									width: 100,
									sortable : true,
									field : 'real_amount',
									title : '到账金额'
								},
                            {
                                width: 80,
                                field: 'order_state',
                                title: '订单状态',
                                align : 'center',
                                formatter: function (value, row, index) {
                                    if (value == 'PAY_SUCCESS')
                                        return '支付成功';
                                    else if (value =='PAY_FAILED')
                                        return '<span style="color:#f00;">失败</span>';
                                    else
										return '';
                                }
                            },
                            {
                                width: 120,
                                sortable : true,
                                field : 'trade_time',
                                title : '交易日期',
                                formatter:dateTimeFormatter
                            },
                            {
                                width: 80,
                                sortable : true,
                                field : 'trade_day',
                                title : '交易时间'
                            },
								{
									width: 120,
                                    align: 'center',
									sortable : true,
									field : 'create_time',
									title : '创建时间',
                                    formatter:dateTimeFormatter
                                },
								{
									width: 100,
									align: 'center',
									sortable : true,
									field : 'update_time',
									title : '更新时间',
									formatter:dateTimeFormatter
								},
								{
									width: 250,
									field : 'op',
									title : '操作',
									align : 'center'
								}
								] ],
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




function deptupdate(id,specId){
	//$("#data1").clear();	
	var $this = $('#deptupdate');
	$this.form('clear');
	//$this.form.refresh();
	var $form = $this.find("form");
	
	$("#data1").html("");
	$('#deptupdate').dialog('open');
	//var data = eval("({userid:'"+id+"'})")
	//gzp_id=idl
	$("#hgoods_id").val(id);
    $("#hspec_id").val(specId);
	$.ajax({
		url : "queryGoodsDeptInfo",
		type : "POST",
		dataType:"json",
		data : {				
			goods_id:id,
            spec_id:specId
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
		//$("#gzp_name").textbox('readonly',false);
		//$("#gzp_name").textbox("enableValidation");
	} else {
		$this.window({
			title : '编辑'
		});	
		// 重置有效
		$("#reset").linkbutton('enable');
		$form.attr("action", $form.attr("action-edit"));
		//$("#gzp_name").textbox("disableValidation");
		//$("#gzp_id").val($('#data').datagrid('getData').rows[id].gzp_id);
		$this.form('clear');
		$this.form('load', $('#data').datagrid('getData').rows[id]);
	}
	

	$this.window({
    	top:($(window).height() - 350) * 0.5+$(document).scrollTop(),   
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


function initInfo(id)
{
	//alert('初始化机具');
	$.ajax({
		url : "initGoodsInfo",
		type : "POST",
		dataType:"json",
		data : {
			goods_id:id
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
     var goods_id = $("#hgoods_id").val();
    var spec_id = $("#hspec_id").val();
     $.ajax({
		url : "saveGoodsDeptInfo",
		type : "POST",
		dataType:"json",
		data : {
			dept_id:idList,
            goods_id:goods_id,
			spec_id:spec_id
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
     var gzp_id = $("#hgoods_id").val();
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
		deptupdate($("#hgoods_id").val());
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
	deptupdate($("#hgoods_id").val());
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


$('#category_id').combobox({
        url : '../goods/getGoodsCategory',
        valueField : 'category_id',
        textField : 'category_name',
        width : '150',
        height : '30',
        editable : false,
        panelHeight : '200',
        multiple:false
    });


//设置规格
function set(id) {
	var goods_id=$('#data').datagrid('getData').rows[id].goods_id;

    //打开用户转移窗口
	$('#goodsSpec').window({
    	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 700) * 0.5
    });
	$('#goodsSpec').window('open');
    $('#goods_ids').val(goods_id);
    console.log($("#goods_ids").val());
    //
	//部门id
	//设置全局参数
	queryDescOrAsc.goods_id=goods_id;
	window.queryGoodsId={
			goods_id:goods_id
	}
	loadSpec(queryGoodsId);
}



function loadSpec(param) {
	$('#spec').datagrid(
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
						url : "queryGoodsSpec",
						queryParams : param,// 查询参数
						toolbar : '#toolbarSpec',// 工具栏
						columns : [ [
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'goods_name',
									title : '机具名称'
								},
								{
									 sortable : false,
					            	 field : 'spec_names',
					            	 title : '规格名称',
					            	 align: 'center',
					            	 width : 100
								},
								{
									sortable : false,
									field : 'spec_order',
									title : '排序',
									align: 'center',
									width : 50
								},
                            	{
									sortable : false,
									field : 'create_time',
									title : '创建时间',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'update_time',
									title : '更新时间',
									width : parseInt($(this).width() * 0.1)
								},
								{
					            	 field : 'is_uses',
					            	 width : 50,
					            	 title : '是否可用',
					            	 align : 'center',
					            	 formatter: function (value, row, index) {
					            		 if (value == 1)
					            			 return '是';
					            		 else
					            			 return '<span style="color:#f00;">否</span>';
					            	 }
					             },
								{
									field : 'op',
									title : '操作',
									width : 238,
									align : 'center',
									formatter : function(value, row, index) {										
										 var str = '\<a class="btns" href="javascript:;" onclick="updateSpec(\'' +index  + '\');">修改</a>\
				            			 ';
				            		 return str;
									}
								} ] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}


//添加/修改方法
function updateSpec(id) {
	var $this = $('#updGoodsSpec');
	var $form = $("#specform");
	$this.window('open');
	if (id == undefined) {
		$this.panel({
			title : '添加'
		});
		// 重置有效
		$("#reset1").linkbutton('enable');
		$form.attr("action", $form.attr("action-add"));
		/*$("#goods_id").textbox('readonly',false);
		$("#goods_id").textbox("enableValidation");*/
		
		
	} else {
		$this.panel({
			title : '编辑'
		});
		// 重置无效
		$("#reset1").linkbutton('disable');
		$form.attr("action", $form.attr("action-edit"));
		/*$("#goods_id").textbox("disableValidation");
		$('#goods_id').textbox('readonly');*/
		$("#spec_ids").val($('#spec').datagrid('getData').rows[id].spec_ids);
	}
	$this.form('clear');
	$("#goods_ids").val(queryDescOrAsc.goods_id);
	$this.form('load', $('#spec').datagrid('getData').rows[id]);
}



//设置参数
function setParam(id) {
	var goods_id=$('#data').datagrid('getData').rows[id].goods_id;

  //打开用户转移窗口
	$('#goodsParam').window({
  	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),   
  	left:($(window).width() - 700) * 0.5
  });
	$('#goodsParam').window('open');
  $('#param_goods_id').val(goods_id);
	//部门id
	//设置全局参数
	queryDescOrAsc.goods_id=goods_id;
	window.queryGoodsId={
			goods_id:goods_id
	}
	loadParam(queryGoodsId);
}



function loadParam(param) {
	$('#param').datagrid(
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
						url : "queryGoodsParam",
						queryParams : param,// 查询参数
						toolbar : '#toolbarParam',// 工具栏
						columns : [ [
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'goods_name',
									title : '机具名称'
								},
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'param_names',
									title : '参数名称'
								},
								{
									 sortable : false,
					            	 field : 'param_values',
					            	 title : '参数值',
					            	 align: 'center',
					            	 width : 100
								},
								{
									sortable : false,
									field : 'create_time',
									title : '创建时间',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'update_time',
									title : '更新时间',
									width : parseInt($(this).width() * 0.1)
								},
								{
					            	 field : 'param_is_use',
					            	 width : 50,
					            	 title : '是否可用',
					            	 align : 'center',
					            	 formatter: function (value, row, index) {
					            		 if (value == 1)
					            			 return '是';
					            		 else
					            			 return '<span style="color:#f00;">否</span>';
					            	 }
					             },
								{
									field : 'op',
									title : '操作',
									width : 238,
									align : 'center',
									formatter : function(value, row, index) {										
										 var str = '\<a class="btns" href="javascript:;" onclick="updateParam(\'' +index  + '\');">修改</a>\
				            			 ';
				            		 return str;
									}
								} ] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}



//添加/修改方法
function updateParam(id) {
	var $this = $('#updGoodsParam');
	var $form = $("#paramform");
	$this.window('open');
	if (id == undefined) {
		$this.panel({
			title : '添加'
		});
		// 重置有效
		$("#reset2").linkbutton('enable');
		$form.attr("action", $form.attr("action-add"));
		/*$("#goods_id").textbox('readonly',false);
		$("#goods_id").textbox("enableValidation");*/
		
		
	} else {
		$this.panel({
			title : '编辑'
		});
		// 重置无效
		$("#reset2").linkbutton('disable');
		$form.attr("action", $form.attr("action-edit"));
		/*$("#goods_id").textbox("disableValidation");
		$('#goods_id').textbox('readonly');*/
		$("#param_id").val($('#param').datagrid('getData').rows[id].param_id);
	}
	$this.form('clear');
	$("#param_goods_id").val(queryDescOrAsc.goods_id);
	$this.form('load', $('#param').datagrid('getData').rows[id]);

}





//设置机具提成比率
function setProrate(id) {
	var goods_id=$('#data').datagrid('getData').rows[id].goods_id;

  //打开用户转移窗口
	$('#goodsProrate').window({
  	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),   
  	left:($(window).width() - 700) * 0.5
  });
	$('#goodsProrate').window('open');
  $('#Prorate_goods_id').val(goods_id);
	//部门id
	//设置全局参数
	queryDescOrAsc.goods_id=goods_id;
	window.queryGoodsId={
			goods_id:goods_id
	}
	loadProrate(queryGoodsId);
}



function loadProrate(param) {
	$('#prorate').datagrid(
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
						url : "queryGoodsProrate",
						queryParams : param,// 查询参数
						toolbar : '#toolbarProrate',// 工具栏
						columns : [ [
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'goods_name',
									title : '机具名称'
								},
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'level_name',
									title : '等级名称'
								},
								{
									 sortable : false,
					            	 field : 'g_allot',
					            	 title : '百分比',
					            	 align: 'center',
					            	 width : 100
								},								
								{
									sortable : false,
									field : 'create_time',
									title : '创建时间',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'update_time',
									title : '更新时间',
									width : parseInt($(this).width() * 0.1)
								},
								{
					            	 field : 'is_use',
					            	 width : 50,
					            	 title : '是否可用',
					            	 align : 'center',
					            	 formatter: function (value, row, index) {
					            		 if (value == 1)
					            			 return '是';
					            		 else
					            			 return '<span style="color:#f00;">否</span>';
					            	 }
					             },
								{
									field : 'op',
									title : '操作',
									width : 238,
									align : 'center',
									formatter : function(value, row, index) {										
										 var str = '\<a class="btns" href="javascript:;" onclick="updateProrate(\'' +index  + '\');">修改</a>\
				            			 ';
				            		 return str;
									}
								} ] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}
function loadProrate(param) {
	$('#prorate').datagrid(
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
						url : "queryGoodsProrate",
						queryParams : param,// 查询参数
						toolbar : '#toolbarProrate',// 工具栏
						columns : [ [
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'goods_name',
									title : '机具名称'
								},
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'level_name',
									title : '等级名称'
								},
								{
									 sortable : false,
					            	 field : 'g_allot',
					            	 title : '百分比',
					            	 align: 'center',
					            	 width : 100
								},								
								{
									sortable : false,
									field : 'create_time',
									title : '创建时间',
									width : parseInt($(this).width() * 0.1)
								},
								{
									sortable : false,
									field : 'update_time',
									title : '更新时间',
									width : parseInt($(this).width() * 0.1)
								},
								{
					            	 field : 'prorate_is_use',
					            	 width : 50,
					            	 title : '是否可用',
					            	 align : 'center',
					            	 formatter: function (value, row, index) {
					            		 if (value == 1)
					            			 return '是';
					            		 else
					            			 return '<span style="color:#f00;">否</span>';
					            	 }
					             },
								{
									field : 'op',
									title : '操作',
									width : 238,
									align : 'center',
									formatter : function(value, row, index) {										
										 var str = '\<a class="btns" href="javascript:;" onclick="updateProrate(\'' +index  + '\');">修改</a>\
				            			 ';
				            		 return str;
									}
								} ] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}



//添加/修改方法
function updateProrate(id) {
	var $this = $('#updGoodsProrate');
	var $form = $("#prorateform");
	$this.window('open');
	if (id == undefined) {
		$this.panel({
			title : '添加'
		});
		// 重置有效
		$("#resetProrate").linkbutton('enable');
		$form.attr("action", $form.attr("action-add"));
		/*$("#goods_id").textbox('readonly',false);
		$("#goods_id").textbox("enableValidation");*/
		$("#level_id").textbox('readonly',false);
		$("#level_id").textbox("enableValidation");
		
	} else {
		$this.panel({
			title : '编辑'
		});
		// 重置无效
		$("#resetProrate").linkbutton('disable');
		$form.attr("action", $form.attr("action-edit"));
		/*$("#goods_id").textbox("disableValidation");
		$('#goods_id').textbox('readonly');*/
		$("#level_id").textbox("disableValidation");
		$('#level_id').textbox('readonly');
		$("#goods_prorate_id").val($('#prorate').datagrid('getData').rows[id].goods_prorate_id);
	}
	$this.form('clear');
	$("#prorate_goods_id").val(queryDescOrAsc.goods_id);
	$this.form('load', $('#prorate').datagrid('getData').rows[id]);

}


$('#level_id').combobox({
	url : '../goods/getMallLevel',
	valueField : 'level_id',
	textField : 'level_name',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200',
	multiple:false
});


$('#brand_id').combobox({
    url : '../platform/queryDictBrandRule',
    valueField : 'id',
    textField : 'name',
    width : '150',
    height : '30',
    editable : false,
    panelHeight : '200'
});
