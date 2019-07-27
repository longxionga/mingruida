
function loadData(param) {
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
		url: "queryBrandRule",
		queryParams: param,//查询参数
		toolbar: '#toolbar',//工具栏
		
		columns: [[
		           {

					   width: 80,
		        	   field: 'id',
		        	   title: '品牌id'
		           },
		           {
		        	   width: 90,
		        	   field: 'name',
		        	   title: '品牌名称'
		           },
		          /* {
		        	   width: 80,
		        	   align : 'right',
		        	   field: 'activation_subsidy',
		        	   title: '激活补贴'
		           },*/
		           {
		        	   width: 80,
		        	   align : 'right',
		        	   field: 'activation_total',
		        	   title: '激活总数'
		           },
		           {
		        	   width: 80,
		        	   align : 'right',
		        	   field: 'un_activation_total',
		        	   title: '未激活'
		           },
		           {
		        	   width: 80,
		        	   align : 'right',
		        	   field: 'standard_total',
		        	   title: '达标总数'
		           },
		           {
		        	   width: 80,
		        	   align : 'right',
		        	   field: 'un_standard_total',
		        	   title: '未达标总数'
		           },
		           {
		        	   width: 80,
		        	   field: 'status',
		        	   title: '是否可用',
		        	   align : 'center',
		        	   formatter: function (value, row, index) {
		        		   if (value == '01')
		        			   return '是';
		        		   else
		        			   return '<span style="color:#f00;">否</span>';
		        	   }
		           },
		           {
		        	   width: 120,
		        	   field: 'create_time',
		        	   title: '创建日期',
                       formatter:dateTimeFormatter
		           },
					/*{
						width: 70,
						field: 'ops',
						title: '操作',
						align: 'center',
						formatter: function (value, row, index) {
							var str = '\
									   <a class="btns" href="javascript:sumM(\'' + row.id + '\');">计算分润</a>\
									   ';
							return str;
						}
					},*/
					{
						width: 70,
						field: 'ops',
						title: '操作',
						align: 'center',
						formatter: function (value, row, index) {
							var str = '\
											 <a class="btns" href="javascript:update(\'' + index + '\');">编辑</a>\
											   ';
							return str;
						}
					},
		           {
		        	   width: 380,
		        	   field: 'op',
		        	   title: '操作',
		        	   align: 'center',
		        	   formatter: function (value, row, index) {
		        		   var str = '\
		        		  	   <a class="btns" href="javascript:upFiles(\'' + row.id + '\');">上传商户明细</a>\
		        		  	   <a class="btns" href="javascript:upFiles2(\'' + row.id + '\');">上传交易明细</a>\
		        		  	   <a class="btns" href="javascript:upFiles3(\'' + row.id + '\');">上传机具信息</a>\
		        			   <a class="btns" href="javascript:upFiles4(\'' +  row.id  + '\');">上传员工信息</a>\
		        			   ';
		        		   return str;
		        	   }
		           }
		           ]],
		           onLoadSuccess: function () {
		        	   $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
		           }
	});
}

// //删除方法
// function del(id) {
// 	$.messager.confirm('确认删除', '确定要删除吗?', function (r) {
// 		if (r) {
// 			$.ajax({
// 				url: "delBankRule",
// 				type: "POST",
// 				data : {sys_id:id},
// 				success: function (data) {
// 					if (data.success == true) {
// 						$.messager.alert('提示信息', data.msg, 'info');
// 						$('#data').datagrid('reload');
// 					} else {
// 						$.messager.alert('错误信息', data.msg, 'error');
// 					}
// 				}
// 			});
// 		}
// 	});
// }

//添加/修改方法
var updateId;
function update(id) {
	console.log(id);
	var $this = $('#update');
	var $form = $this.find("form");
	updateId=id;
	if (updateId == undefined) {
		$('#updateform').form('clear');
		var thisData = {"cz_is_use":1,"tx_is_use":1};
		$this.form('load', thisData);
		$("#resetBtn").linkbutton('enable');
		$this.window({ title: '添加' });		
		$form.attr("action", $form.attr("action-add"));
	} else {
		$("#resetBtn").linkbutton('disable');
		$this.window({ title: '编辑' });
		$form.attr("action", $form.attr("action-edit"));
		// $("#id").val($('#data').datagrid('getData').rows[id].id);
		$this.form('clear');
		$this.form('load', $('#data').datagrid('getData').rows[id]);
	}
	
	
	$this.window({
    	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 680) * 0.5
    });
	$this.window('open');
}

// 为重置按钮添加单击事件 
$("#reset").click(function(){
	if (updateId == undefined) {
		$('#updateform').form('clear');
		var thisData = {"cz_is_use":1,"tx_is_use":1};
		$('#update').form('load', thisData);
	}else{
		update(updateId);	
	}	
});


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



$('#status').combobox({
	width : '150',
	height : '30',
	valueField : 'label',
	textField : 'value',
	required : 'true',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '01',
		value : '是'
	}, {
		label : '02',
		value : '否'
	} ]
});

$('#cz_is_use').combobox({
	width : '150',
	height : '30',
	valueField : 'label',
	textField : 'value',
	required : 'true',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '1',
		value : '是'
	}, {
		label : '0',
		value : '否'
	} ]
});

$('#tx_date').combobox({
	width : '150',
	height : '30',
	valueField : 'label',
	textField : 'value',
	required : 'true',
	panelHeight : 'auto',
	multiple:true,
	editable: false,
	data : [ {
		label : '星期一',
		value : '星期一'
	}, {
		label : '星期二',
		value : '星期二'
	}, 
	{
		label : '星期三',
		value : '星期三'
	}, 
	{
		label : '星期四',
		value : '星期四'
	}, 
	{
		label : '星期五',
		value : '星期五'
	}, 
	{
		label : '星期六',
		value : '星期六'
	}, 
	{
		label : '星期日',
		value : '星期日'
	} ]
});

$('#cz_date').combobox({
	width : '150',
	height : '30',
	valueField : 'label',
	textField : 'value',
	required : 'true',
	panelHeight : 'auto',
	multiple:true,
	editable: false,
	data : [ {
		label : '星期一',
		value : '星期一'
	}, {
		label : '星期二',
		value : '星期二'
	}, 
	{
		label : '星期三',
		value : '星期三'
	}, 
	{
		label : '星期四',
		value : '星期四'
	}, 
	{
		label : '星期五',
		value : '星期五'
	}, 
	{
		label : '星期六',
		value : '星期六'
	}, 
	{
		label : '星期日',
		value : '星期日'
	} ]
});

function up(){
	var tx_date=$('#tx_date').combobox('getValues').join(',');
	var cz_date=$('#cz_date').combobox('getValues').join(',');
	 $('#tx_date').combobox('setValue',tx_date);
	 $('#cz_date').combobox('setValue',cz_date);
	$("#updateform").submit();
	loadData();//初始化加载数据
}



//批量审核不通过
function sumM(brand_id){
    $.messager.confirm('提示信息', '确认计算品牌分润?', function(r){
        if (r){
            var begindateT = $('#begindate').datebox('getValue')
            $.messager.progress({
                title:'请等待',
                msg:'正在计算'+begindateT+'分润...',
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
                url: "doSumBrandRule",
                data: {
                    brand_id:brand_id,
                    begindate:begindateT
                },
                dataType: 'json',
                async: true,
                success: function(msg){
                    $.messager.progress("close");
                    $('#search').submit();
                    $.messager.alert('提示信息', '计算成功');
                },
                error:function(XMLHttpRequest, textStatus, errorThrown){
                    $.messager.progress("close");
                }
            });

        }
    });
}