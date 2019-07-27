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
		url: "queryUserTransf",
		queryParams: param,//查询参数
		toolbar: '#toolbar',//工具栏
		columns: [[
					{
						align: 'center',
						field : 'mobile',
						title : '手机号',
						width:80
					},
					{
						field : 'name',
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
						width: 100,
						field: 'create_time',
						title: '创建时间',
						formatter : dateTimeFormatter
					},
		           {
		        	   width: 150,
		        	   field: 'op',
		        	   title: '操作',
		        	   align: 'center',
		        	   formatter: function (value, row, index) {
		        		  if(deptType==4){
		        			  var str = '\
									<a class="btns" href="javascript:update(\''
									+ index
									+ '\');">编辑</a>\
									\<a class="btns" href="javascript:setTransf(\'' + row.id + '\');">设置经纪人</a>\
									';
										return str;  
		        		  }else{
                              var str = '\
									\<a class="btns" href="javascript:setTransf(\'' + row.id + '\');">设置品牌代理商</a>\
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

function loadBroker(list,param) {
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
		url: "queryMerchantInfoList",
		queryParams:param,// 查询参数
		toolbar: '#toolbar1',//工具栏
		columns: [[
            	{
					title:'<input type=\"checkbox\" name=\"checkuser\" onclick=\"checkall1();\"  id=\"checkuser\"></input>',
					field:'id',
					formatter:function(value,rowData,rowIndex){
						if(list !=null && list!='' && list!=undefined){
							var struser='';
							$.each(list, function(key, value) {
								struser += value.broker_code + ",";
							});
                            //console.info(list);
							//console.info("xxx"+struser);
							//if(struser.indexOf(rowData.user_id)>-1)
							if(strSplit(struser,rowData.broker_code))
							{
								return "<input type=\"checkbox\" checked=\"checked\" name=\"checkuserrole\" id='check_"+rowData.broker_code+"'></input>";
							}else
							{
								return "<input type=\"checkbox\" name=\"checkuserrole\" id='check_"+rowData.broker_code+"'></input>";
							}
						}else
						{
							return "<input type=\"checkbox\" name=\"checkuserrole\" id='check_"+rowData.broker_code+"'></input>";
						}
					},
					width:50
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
						field : 'broker_code',
						title : '直属代理商编号'
					},
					{
						width: 100,
						sortable : true,
						field : 'brand_name',
						title : '机具所属品牌'
					}

		           ]],
		           onLoadSuccess: function () {
		        	   $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
		           }
	});
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


function deptupdate(id) {
    $('#search1').form('clear');
    var $this = $('#deptupdate');
    $this.form('clear');
    var $form = $this.find("form");
    $("#data1").html("");

    //var data = eval("({userid:'"+id+"'})")
    //$this.form('clear');
    $("#odept_id").val(id);
    loadData1();

    $this.window({
        top: ($(window).height() - 450) * 0.5 + $(document).scrollTop(),
        left: ($(window).width() - 700) * 0.5
    });
    //$this.form('clear');
    $this.window('open');
}


function loadData1(param) {
    $('#data1').treegrid(
        {
            //title : '部门选择',// 当前页面标题
            fitColumns: true,// 自动列宽
            autoRowHeight: true,// 自动行高度
            checkOnSelect: true, // 只有选择复选框时才选中
            selectOnCheck: false,// 选择行时选中复选框
            singleSelect: true,// 禁用选择多行
            border: !1,// 不显示边框
            striped: true,// 条纹行
            fit: true,// 自适应高度
            nowrap: true,// 单元格内文字禁止换行
            rownumbers: true,// 显示行号
            animate: true,
            collapsible: true,
            url: "queryDeptInfoByPa?dept_type=4",
            idField: 'dept_id',
            treeField: 'dept_name',
            queryParams: param,// 查询参数
            toolbar: '#toolbar1',// 工具栏
            columns: [[
                {
                    width: parseInt($(this).width() * 0.05),
                    field: 'dept_id',
                    align: 'center',
                    title: '代理商'
                },
                {
                    width: parseInt($(this).width() * 0.2),
                    field: 'dept_name',
                    align: 'center',
                    title: '代理商名称'
                }, {
                    width: parseInt($(this).width() * 0.1),
                    field: 'dept_code',
                    align: 'center',
                    title: '代理商编码'
                }, {
                    width: parseInt($(this).width() * 0.05),
                    field: 'dept_type',
                    align: 'center',
                    title: '级别',
                    formatter: function (value) {
                        if (value == 0) {
                            return "平台";
                        }
                        if (value == 1) {
                            return "交易中心";
                        }
                        if (value == 2) {
                            return "运行中心";
                        }
                        if (value == 3) {
                            return "服务商";
                        }
                        if (value == 4) {
                            return "代理商";
                        }
                        if (value == 5) {
                            return "部门";
                        }
                    }
                },
                {
                    width: parseInt($(this).width() * 0.05),
                    field: 'is_use',
                    title: '是否有效',
                    align: 'center',
                    formatter: function (value) {
                        if (value == 1) {
                            return "有效";
                        } else if (value == 0) {
                            return "<span class='red'>无效</span>";
                        } else if (value == 4) {
                            return "<span class='red'>已退出</span>";
                        }
                    }
                },
                {
                    width: 100,
                    align: 'center',
                    field: 'op',
                    title: '操作',
                    formatter: function (value, row, index) {
                        var str = '';
                        if (row.is_use != 4) {
                            str += '\<a class="btns" href="javascript:;" onclick="getSelected(\'' + row.dept_id + '\');">转移</a>';
                        }
                        return str;
                    }
                }]],
            onLoadSuccess: function () {
                $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;border-radius:4px;line-height:12px;padding:2px 10px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
                $("#checkdept").attr("checked", false);
            }
        });
}

function setTransf(user_id) {
	//console.info(user_id)
	//var user_id=$('#data').datagrid('getData').rows[id].id;
	//打开用户转移窗口
	$('#broker_dept').window({
    	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),
    	left:($(window).width() - 700) * 0.5
    });
	$('#broker_dept').window('open');
	//部门id
	//设置全局参数
	queryDescOrAsc.user_id=user_id;
	window.queryDeptId={
        user_id:user_id ,
        brand_id: $("#brand_id").combobox('getValue'),
        broker_name: $("#broker_name").val()
	}
    $("#user_id").val(user_id);
    //$('#broker').datagrid('loadData',[]);
    $.ajax({
        url : "queryMerchantTransfList",
        type : "POST",
        dataType:"json",
        data : queryDeptId,
        success : function(data){
            if(data.length>0)
            {
                loadBroker(data,queryDeptId);
            }else
            {
                loadBroker("" , queryDeptId);
            }
        }
    });
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



function getSelected1(){

    var idList = "";
    $("input:checked").each(function(){
        var id = $(this).attr("id");
        if(id.indexOf('check_type')== -1 && id.indexOf("check_")>-1){
            idList += id.replace("check_",'')+',';
        }
    });
    idList = idList.substring(0,idList.length-1);
    if(idList.length==0){
        $.messager.alert('提示', '请先勾品牌代理商户!', 'error');
        return;
    }
    var user_id = $('#user_id').val();
    //console.info(user_id);
    //console.info(idList);
    $.ajax({
        url : "updateUserTransf",
        type : "POST",
        dataType:"json",
        data : {
            merchantIds:idList,
            user_id:user_id
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


//选择部门
function transferUser(){
	if($('#broker').datagrid('getChecked').length==0){
		$.messager.alert('提示', '请先勾品牌代理商户!', 'error');
		return;
	}

	/************经纪人id和姓名*****************/
	var brokers=$('#broker').datagrid('getChecked');
	var brokers_ids="";
	var brokers_names="";
	for(var i=0;i<brokers.length;i++){
		if(i==(brokers.length-1)){
			brokers_ids=brokers_ids+brokers[i].id;
			brokers_names=brokers_names+brokers[i].broker_name;
		}else{
			brokers_ids=brokers_ids+brokers[i].id+",";
			brokers_names=brokers_names+brokers[i].broker_name+",";
		}
	}
	/************经纪人id和姓名******************/
    $.ajax({
        type: "POST",
        url: "updateUserTransf",
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
	//loadBroker(param);
    setTransf($("#user_id").val());
	return false;
});


$("#refresh").click(function(){
    $('#search1').form('clear');
    $("#broker_name").textbox('setValue','');
	//$('#search1').submit();
    setTransf($("#user_id").val());
});



function getSelected(id) {
    $.messager.confirm('转移代理商', '是否确认需要转移', function (r) {
        if (r) {
            var odept_id = $('#odept_id').val();
            $.ajax({
                url: "savaAgentToNdept",
                type: "POST",
                dataType: "json",
                data: {
                    ndept_id: id,
                    user_id: odept_id
                },
                success: function (data) {
                    $.messager.alert('提示信息', data.msg, 'info');
                    if (data.success == true) {
                        $('#deptupdate').dialog('close');
                    }
                }

            });

        }
    });
}


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



$('#brand_id').combobox({
    url : '../platform/queryDictBrandRule',
    valueField : 'id',
    textField : 'name',
    width : '150',
    height : '30',
    editable : false,
    panelHeight : '200'
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
