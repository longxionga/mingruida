dict(1001);
function loadData(param,url) {
	var boolFitColumns=true;
	if($(document).width()<=1600-220){
		boolFitColumns=false;
	}
	var fits = 0,columns = [{
		                    width : 250,
							field : 'dept_name',
							title : '单位名称'
						},{
							width : 50,
							field : 'dept_id',
							align : 'center',
							title : '部门ID'
						},
						{
							width : 80,
							align : 'center',
							field : 'dept_code',
							title : '单位编码'
						},{
							width : 80,
							field : 'dept_type',
							align : 'center',
							title : '级别',
							formatter : dictFormatter
						},{
							 width : 80,
							field : 'dept_money',
							align : 'right',
							title : '余额',
							formatter:function(value){
								return value+" 元";
							}
						},
						{  
							width : 100,
							align: 'center',
							field : 'dept_mobile',
							title : '联系电话'
						}];

	if($("#deptType").val()<5){
		columns.push({
			        width : 130,
			        align : 'center',
					field : 'dept_app_id',
					title : '微信ID'
				});
	};
	columns.push(
	{
		width : 60,
		field : 'is_use',
		title : '部门状态',
		align : 'center',
		formatter : function(value) {
			if(value==1){
				return '有效';
			}else if(value==0){
				return '<span style="color:#f00;">已禁用</span>';
			}
			else if(value==4){
				return '<span style="color:#f00;">已退出</span>';
			}
		}
	},
	{
		width : 380,
		align : 'center',
		field : 'op',
		title : '操作',
		formatter : function(value, row, index) {			
			var str = '';
			if(row.dept_type==3)
			{
				if(row.is_use==1)
				{
					str += '\<a class="btns" href="javascript:deptquit(\''+ row.dept_id + '\',\''+row.dept_type+'\');">退出</a>';
				}
				if(row.is_use==4)
				{
					str += '\<a class="btns btns-disabled" href="javascript:;">退出</a>';
					str += '\<a class="btns" href="javascript:viewdetails(\''+ row.dept_id + '\',\''+row.dept_type+'\');">服务商余额</a>';
					str += '\<a class="btns" href="javascript:viewdetailb(\''+ row.dept_id + '\',\''+row.dept_type+'\');">经纪人余额</a>';
					str += '\<a class="btns" href="javascript:viewdetailu(\''+ row.dept_id + '\',\''+row.dept_type+'\');">用户余额</a>';
					str += '\<a class="btns" href="javascript:opSettlement(\''+ row.dept_id + '\',\''+row.dept_type+'\');">清算</a>';
				}
				
				
			}
			if(row.dept_type==4)
			{
				if(row.is_use==1)
				{
					str += '\<a class="btns" href="javascript:deptquit(\''+ row.dept_id + '\',\''+row.dept_type+'\');">退出</a>';
				}
				if(row.is_use==4)
				{
					str += '\<a class="btns btns-disabled" href="javascript:;">退出</a>';
					str += '\<a class="btns" href="javascript:viewdetails(\''+ row.dept_id + '\',\''+row.dept_type+'\');">代理商余额</a>';
					str += '\<a class="btns" href="javascript:viewdetailb(\''+ row.dept_id + '\',\''+row.dept_type+'\');">经纪人余额</a>';
					str += '\<a class="btns" href="javascript:viewdetailu(\''+ row.dept_id + '\',\''+row.dept_type+'\');">用户余额</a>';
					str += '\<a class="btns" href="javascript:opSettlement(\''+ row.dept_id + '\',\''+row.dept_type+'\');">清算</a>';
				}
				
			}
			return str;
		}
	});
	
	if(columns.length >10){
		fits = 0;
	}
	columns = [columns];
	$('#data').treegrid(
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
						idField:'dept_id',
						url : url,
						queryParams : param,// 查询参数
						animate:true,
						collapsible:true,
						treeField:'dept_name',
						toolbar : '#toolbar',// 工具栏
						columns : columns,
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;border-radius:4px;line-height:12px;padding:2px 10px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
							$(".datagrid-body td[field='dept_app_id']," +
									".datagrid-body td[field='dept_url']," +
									".datagrid-body td[field='broker_url']").overTitle();
						}
					});
}

loadData('','queryOPDeptInfos');//初始化加载数据


var updateId;
var actions;
var depttype;
function update(dept_id,action,dept_type,dept_parentId) {

	var $this = $('#update');
	var $form = $this.find("form");

	updateId = dept_id;
	actions = action;
	depttype = dept_type;
	
	$this.form('clear');
	if (action=="insert") {
		var thisData = {"is_use":1,"is_tj_man":0,"tj_ratio":0,"dept_money":0,"dept_parent_id":dept_id,"dept_type":dept_type};
		$this.form('load', thisData);
		if(dept_type=='2'){
			$("#is_tj_man,#tj_ratio").closest("tr").show();
		}else{
			$("#is_tj_man,#tj_ratio").closest("tr").hide();
		}
		if(dept_type=='3'){
			$("#dept_app_id,#dept_app_secret").closest("tr").show();
			$("#dept_url,#broker_url").closest("tr").show();
		}else{
			$("#dept_app_id,#dept_app_secret").closest("tr").hide();
			$("#dept_url,#broker_url").closest("tr").hide();
			
		}
		//去掉校验
    	$("#dept_code,#dept_name,#dept_ratio").textbox("enableValidation").textbox("readonly",false);
		// 重置有效
		$("#reset").linkbutton('enable');
		$this.window({
			title : '添加单位'
		});
		$form.attr("action", $form.attr("action-add"));
	} else {
		if(dept_type=='3'){
			$("#is_tj_man,#tj_ratio").closest("tr").show();
		}else{
			$("#is_tj_man,tj_ratio").closest("tr").hide();
		}
		if(dept_type=='4'){
			$("#dept_app_id,#dept_app_secret").closest("tr").show();
			$("#dept_url,#broker_url").closest("tr").show();			
		}else{
			$("#dept_app_id,#dept_app_secret").closest("tr").hide();
			$("#dept_url,#broker_url").closest("tr").hide();
		}
		$('#dept_parent_id').val(dept_parentId);
		// 重置无效
		$("#reset").linkbutton('enable');
		$form.attr("action", $form.attr("action-edit"));
		//去掉校验
		$("#dept_code,#dept_name,#dept_ratio").textbox("disableValidation").textbox("readonly");
		$this.form('load', $('#data').treegrid('getSelected'));
		$this.window({
			title : '编辑'
		});
	}
	
	$this.window({
		top:0,   
    	left:($(window).width() - 595) * 0.5
    });
	$this.window('open');
	$(".panel.window,.window-shadow").css({top:($(window).height() - $(".panel.window").outerHeight()) * 0.5+$(document).scrollTop()})
}
	
	//为重置按钮添加单击事件 
	$("#reset").click(function(){
		if (updateId == undefined) {
			$('#updateform').form('clear');
		}else{
			update(updateId,actions,depttype);	
		}	
	});

	var num1,num2,num3,num4;
	var name1,name2,name3,name4;
	function update1(id,row) {
	    var m = getMap();
	    var m1=getMap();
		var $this = $('#update1');
		var $form = $this.find("form");
		//$this.form('load', $('#data').treegrid('getSelected'));
		$.ajax({
			    type: "POST",      
			    url: "getOPDeptRatioModel",
			    data: {
			    	dept_id:id
			    },
			    dataType: 'json',
			    async: true,
			    success: function(data){
			    	for(var i=0;i<data.length;i++){
			     		m.put(data[i].dept_id,data[i].dept_ratio);  
			     		m1.put(data[i].dept_id,data[i].dept_name);
			     		num1=m.get(data[1].dept_id);
			     		num2=m.get(data[2].dept_id);
			     		num3=m.get(data[3].dept_id);
			     		num4=m.get(data[4].dept_id);
			     		name1=m1.get(data[1].dept_id);
			     		name2=m1.get(data[2].dept_id);
			     		name3=m1.get(data[3].dept_id);
			     		name4=m1.get(data[4].dept_id);
			     	}  
			    	$('#ce_name').html(name1+" <font color=red>"+num1+"%</font>");
			    	$("#ch_name").html(name2+" <font color=red>"+num2+"%</font>");
			    	$("#s_name").html(name3+" <font color=red>"+num3+"%</font>");
			    	$("#a_name").html(name4+" <font color=red>"+num4+"%</font>");
		       }
		 });
		$this.window({
			title : '分配比率模板变更'
		});
		
		$this.window({
	    	top:($(window).height() - 381) * 0.5+$(document).scrollTop(),   
	    	left:($(window).width() - 588) * 0.5
	    });
		$this.form('load', $('#data').treegrid('getSelected'));
		$this.window('open');
	};	
	
	//应用
	$("#yingyong").click(function(){
		var thisData = {"ce_allot":num1,"ch_allot":num2,"s_allot":num3,"dept_ratio":num4};
		$("#update1").form('load', thisData);
	});
	
	
	//重置
	$("#reset2").click(function(){
		var $this = $('#update1');
		var $form = $this.find("form");
		$this.form('load', $('#data').treegrid('getSelected'));
	});
	
$("#search").submit(function () {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	};
	if($.trim($("#search_dept_id").val())!=""||$.trim($("#search_dept_name").val())!=""){
		var param = getFormJson(this);
		loadData(param,'queryOPDeptInfoByPa');//查询数据
		return false;
	}
});

$("#updateform1").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	if($('#s_allot').textbox('getValue')<40)
	{
		$.messager.alert('错误信息',"服务商不能小于40%",'error');
		return false;
	}

		var vv1 = 100-$('#ce_allot').textbox('getValue')-
		$('#ch_allot').textbox('getValue')-
		$('#s_allot').textbox('getValue')-
		$('#dept_ratios').textbox('getValue');
		//alert($('#ce_allot').textbox('getValue')+$('#ch_allot').textbox('getValue')+$('#s_allot').textbox('getValue')+$('#dept_ratios').textbox('getValue'));
		//alert(vv);
		if(vv1>20){
			$.messager.alert('错误信息',"设置分配比率总数不能小于80%",'error');
			return false;
		}
		
		var vv = 100-$('#ce_allot').textbox('getValue')-
		$('#ch_allot').textbox('getValue')-
		$('#s_allot').textbox('getValue')-
		$('#dept_ratios').textbox('getValue');
	//alert($('#ce_allot').textbox('getValue')+$('#ch_allot').textbox('getValue')+$('#s_allot').textbox('getValue')+$('#dept_ratios').textbox('getValue'));
	//alert(vv);
	if(vv<0){
		$.messager.alert('错误信息',"请重新验算分配比率",'error');
		return false;
	}
});

function loadData1(param) {
	$('#data1').treegrid(
					{
						//title : '部门选择',// 当前页面标题
						fitColumns : true,// 自动列宽
						autoRowHeight : true,// 自动行高度
						checkOnSelect : true, // 只有选择复选框时才选中
						selectOnCheck : false,// 选择行时选中复选框
						singleSelect : true,// 禁用选择多行
						border : !1,// 不显示边框
						striped : true,// 条纹行
						fit : true,// 自适应高度
						nowrap : true,// 单元格内文字禁止换行
						rownumbers : true,// 显示行号
						animate:true,
						collapsible:true,
						url : "queryOPDeptInfoByPa?dept_type=5",
						idField:'dept_id',
						treeField:'dept_name',
						queryParams : param,// 查询参数
						toolbar : '#toolbar1',// 工具栏
						columns : [ [
						         {
									width : parseInt($(this).width() * 0.05),
									field : 'dept_id',
									align : 'center',
									title : '部门ID'
						        },								
								{
									width : parseInt($(this).width() * 0.2),
									field : 'dept_name',
									align : 'center',
									title : '部门名称'
								},{
									width : parseInt($(this).width() * 0.1),
									field : 'dept_code',
									align : 'center',
									title : '部门编码'
								},{
									width : parseInt($(this).width() * 0.05),
									field : 'dept_type',
									align : 'center',
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
											return "部门";
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
								},
								{
									width : 100,
									align : 'center',
									field : 'op',
									title : '操作',
									formatter : function(value, row, index) {
										var str = '';									
										str += '\<a class="btns" href="javascript:;" onclick="getSelected(\'' + row.dept_id + '\');">转移</a>';										
										return str;
									}
								}] ],
						onLoadSuccess : function() {
							$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;border-radius:4px;line-height:12px;padding:2px 10px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
							$("#checkdept").attr("checked",false);
						}
					});
}

function deptupdate(id){	
	var $this = $('#deptupdate');
	$this.form('clear');
	var $form = $this.find("form");
	$("#data1").html("");
	
	//var data = eval("({userid:'"+id+"'})")
	//$this.form('clear');
	$("#odept_id").val(id);	
	loadData1();
	
	$this.window({
    	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 700) * 0.5
    });
	//$this.form('clear');
	$this.window('open');
}

//获取查询数据
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
		deptupdate($("#odept_id").val());
	}
}

//刷新功能
$("#refresh").click(function(){
	$("#dept_names").textbox('setValue',"");
	deptupdate($("#odept_id").val());	
});

function deptquit(id,type) {
	$.messager.confirm('部门退出信息', '是否确认需要退出?', function(r) {
		if (r) {
			//var odept_id = $('#odept_id').val();
			$.ajax({
				url : "quitDeptInfo",
				type : "POST",
				dataType : "json",
				data : {
					dept_id : id,
					type : type
				},
				success : function(data) {
					$.messager.alert('提示信息', data.msg, 'info');
					if (data.success == true) {
						//$('#deptupdate').dialog('close');
						$('#data').treegrid('reload');
						 //window.location.reload();
					}
					
				}

			});

		}
	});
}

function opSettlement(id,type) {
	$.messager.confirm('清算', '是否确认清算，注意此过程不可逆,请谨慎操作！', function(r) {
		if (r) {
			//var odept_id = $('#odept_id').val();
			$.ajax({
				url : "opSettlementInfo",
				type : "POST",
				dataType : "json",
				data : {
					dept_id : id,
					type : type
				},
				success : function(data) {
					$.messager.alert('提示信息', data.msg, 'info');
					if (data.success == true) {
						//$('#deptupdate').dialog('close');
						$('#data').treegrid('reload');
					}
				}

			});

		}
	});
}

function cleanweixinid(id,type) {
	$.messager.confirm('清空微信信息', '是否确认需要清空微信信息', function(r) {
		if (r) {
			//var odept_id = $('#odept_id').val();
			$.ajax({
				url : "opWeiXinClearInfo",
				type : "POST",
				dataType : "json",
				data : {
					dept_id : id,
					type : type					
				},
				success : function(data) {
					$.messager.alert('提示信息', data.msg, 'info');
					if (data.success == true) {
						//$('#deptupdate').dialog('close');
					}
				}

			});

		}
	});
}

function getSelected(id) {
	$.messager.confirm('转移部门', '是否确认需要转移', function(r) {
		if (r) {
			var odept_id = $('#odept_id').val();
			$.ajax({
				url : "saveOOPDeptToNDeptInfo",
				type : "POST",
				dataType : "json",
				data : {
					ndept_id : id,
					odept_id : odept_id
				},
				success : function(data) {
					$.messager.alert('提示信息', data.msg, 'info');
					if (data.success == true) {
						$('#deptupdate').dialog('close');
					}
				}

			});

		}
	});
}


function viewdetails(dept_id,type){
	$("#data2").html("");
	$('#txsh_detail_2').window({
    	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 700) * 0.5
    });
	$('#txsh_detail_2').window('open');
	//$('#txsh_detail_2').dialog('open');
	var data = eval("({dept_id:'"+dept_id+"',type:'"+type+"',})")
	$('#opt_dept_id').val(dept_id);
	$('#opt_dept_type').val(type);
	LoadSADetailInfo(data);
	deptbalacesum(data);
}

function viewdetailb(dept_id,type){
	$("#data3").html("");
	$('#txsh_detail_3').window({
    	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 700) * 0.5
    });
	$('#txsh_detail_3').window('open');
	$('#opt_dept_id').val(dept_id);
	$('#opt_dept_type').val(type);
	var data = eval("({dept_id:'"+dept_id+"',type:'"+type+"',})")
	LoadBDetailInfo(data);
	brokerbalacesum(data)
}

function viewdetailu(dept_id,type){
	$("#data4").html("");
	$('#txsh_detail_4').window({
    	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 700) * 0.5
    });
	$('#txsh_detail_4').window('open');
	$('#opt_dept_id').val(dept_id);
	$('#opt_dept_type').val(type);
	var data = eval("({dept_id:'"+dept_id+"',type:'"+type+"',})")
	LoadUDetailInfo(data);
	userbalacesum(data)
}

function LoadSADetailInfo(param) {	  
    /*获取选中行*/  
    //var row = $('#Cse_Bespeak_Log').datagrid('getSelected'); //获取选中行     
	$('#data2').datagrid(
			{
				title : '',// 当前页面标题
				fitColumns : true,// 自动列宽
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
				idField:'',
				url : "queryDeptBalaceInfo",
				remoteSort:false,
				queryParams : param,// 查询参数
				toolbar : '#toolbar02',// 工具栏
				columns : [ [
						{
							width: 100,
							sortable : true,
							field : 'dept_code',
							title : '编码'
						},
						{
							width: 80,
							sortable : true,
							align: 'center',
							field : 'dept_name',
							title : '名称'
						},
						{
							width: 80,
							sortable : true,
							align : 'center',
							field : 'dept_mobile',
							title : '电话'
						},						
						{
							width: 80,
							align: 'center',
							sortable : true,
							field : 'dept_money',
							title : '余额'
						}										
						 ] ],
				onLoadSuccess : function() {
					$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
					
				}
			});   
};

function LoadBDetailInfo(param) {	  
    /*获取选中行*/  
	$('#data3').datagrid(
			{
				title : '',// 当前页面标题
				fitColumns : true,// 自动列宽
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
				pageList : [20,40,80],// 每页显示数量设置参数
				idField:'',
				url : "queryBrokerBalaceInfo",
				remoteSort:false,
				queryParams : param,// 查询参数
				toolbar : '#toolbar03',// 工具栏
				columns : [ [
						{
							width: 100,
							sortable : true,
							field : 'broker_name',
							title : '经纪人名'
						},
						{
							width: 80,
							sortable : true,
							field : 'broker_mobile',
							title : '经纪人电话'
						},
						{
							width: 80,
							sortable : true,
							align : 'center',
							field : 'broker_money',
							title : '经纪人余额'
						}					
						 ] ],
				onLoadSuccess : function() {
					$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");

				}
			});
   
}; 

function LoadUDetailInfo(param) {	  
    /*获取选中行*/  
	$('#data4').datagrid(
			{
				title : '',// 当前页面标题
				fitColumns : true,// 自动列宽
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
				pageList : [20,40,80],// 每页显示数量设置参数
				idField:'',
				url : "queryUserBalaceInfo",
				remoteSort:false,
				queryParams : param,// 查询参数			
				toolbar : '#toolbar04',// 工具栏
				columns : [ [
						{
							width: 100,
							sortable : true,
							field : 'user_name',
							title : '用户名'
						},
						{
							width: 80,
							sortable : true,
							field : 'user_mobile',
							title : '用户电话'
						},
						{
							width: 80,
							sortable : true,
							align : 'center',
							field : 'user_money',
							title : '用户余额'
						}					
						 ] ],
				onLoadSuccess : function() {
					$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
					
				}
			});  
};

function deptbalacesum(param){
	$.ajax({
		url : "queryDeptBalaceInfoSum",
		type : "POST",
		data : param,
		success : function(data) {
			if(data == null){
				return ;
			}
			var html = '\
				<div><span>余额汇总： '+data[0].sumdeptmoney+'\</span></div>\
				';
			$("#total2").html(html);
		}
	});
}

function userbalacesum(param){
	$.ajax({
		url : "queryUserBalaceInfoSum",
		type : "POST",
		data : param,
		success : function(data) {
			if(data == null){
				return ;
			}
			console.log(data);
			var html = '\
				<span>余额汇总： '+data[0].sumusermoney+'\</span>\
				';
			$("#total4").html(html);
		}
	});
}

function brokerbalacesum(param){
	$.ajax({
		url : "queryBrokerBalaceInfoSum",
		type : "POST",
		data : param,
		success : function(data) {
			if(data == null){
				return ;
			}
			console.log(data);
			var html = '\
				<span>余额汇总： '+data[0].sumbrokermoney+'\</span>\
				';
			$("#total3").html(html);
		}
	});
}

function expBrokerInfo() {
	var dept_id=document.getElementById("opt_dept_id").value;
	var type=document.getElementById("opt_dept_type").value;
	$.ajax({
		url : "queryBrokerForExcel",
		type : "POST",
		data : {
			dept_id:dept_id,
			type:type
		},
			success : function(msg) {
				if(msg.num>0){
					window.location.href= msg.url;
				}else{
					$.messager.alert('提示信息', '导出失败，查询无数据');
				}

			}
	});	
	//window.location.href = 'exportSettleDayReportInfo';
}

function expUserInfo() {
	var dept_id=document.getElementById("opt_dept_id").value;
	var type=document.getElementById("opt_dept_type").value;
	$.ajax({
		url : "queryUserForExcel",
		type : "POST",
		data : {
			dept_id:dept_id,
			type:type
		},
			success : function(msg) {
				if(msg.num>0){
					window.location.href= msg.url;
				}else{
					$.messager.alert('提示信息', '导出失败，查询无数据');
				}

			}
	});	
	//window.location.href = 'exportSettleDayReportInfo';
}

