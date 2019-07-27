dictInfo(1011,"sys_type");
dict(1011);
function loadData(param) {
	$('#data').datagrid({
		title: '',//当前页面标题
		fitColumns: true,//自动列宽
		autoRowHeight: true,//自动行高度
		singleSelect: false,//禁用选择多行
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
		url: "querySysControl",
		remoteSort:false,
		queryParams: param,//查询参数
		toolbar: '#toolbar',//工具栏
		columns: [[
		           {
		        	   width: 100,
		        	   field: 'remark',
		        	   align: 'center',
		        	   title: '控制说明'
		           },
		           {
		        	   width: 100,
		        	   field: 'sys_type',
		        	   title: '控制类型',
		        	   formatter:dictFormatter
		           },		           
		           {
		        	   width: 50,
		        	   field: 'is_use',
		        	   title: '是否可用',
		        	   align: 'center',
		        	   formatter: function (value, row, index) {
		        		   if (value == 1)
		        			   return '是';
		        		   else
		        			   return '<span style="color:#f00;">否</span>';
		        	   }
		           },
		           {
		        	   width: 50,
		        	   field: 'op',
		        	   title: '操作',
		        	   align: 'center',
		        	   formatter: function (value, row, index) {
		        		   var str = '\
		        			   <a class="btns" href="javascript:update(\'' + index + '\');">编辑</a>\
		        			   <a class="btns" href="javascript:del(\'' + row.sys_id + '\');">删除</a>\
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

function loadData2(param) {
	$('#data2').treegrid(
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
						toolbar : '#toolbar2',// 工具栏
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
											return "<input type=\"checkbox\" checked=\"checked\" name=\"checkdeptgdsy\" id='check_dl_"+rowData.dept_id+"'></input>";
										}else
										{
											return "<input type=\"checkbox\" name=\"checkdeptgdsy\" id='check_dl_"+rowData.dept_id+"'></input>";
										}
										}else
										{
											return "<input type=\"checkbox\" name=\"checkdeptgdsy\" id='check_dl_"+rowData.dept_id+"'></input>";
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

//禁用出金
function loadData3(param) {
    $('#data3').treegrid(
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
            toolbar : '#toolbar3',// 工具栏
            columns : [ [
                {title:'<input type=\"checkbox\" name=\"checkdept3\" onclick=\"checkall3();\"  id=\"checkdept3\"></input>',field:'dept_id',formatter:function(value,rowData,rowIndex){
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
                                return "<input type=\"checkbox\" checked=\"checked\" name=\"checkdeptgdsy3\" id='check_wl_"+rowData.dept_id+"'></input>";
                            }else
                            {
                                return "<input type=\"checkbox\" name=\"checkdeptgdsy3\" id='check_wl_"+rowData.dept_id+"'></input>";
                            }
                        }else
                        {
                            return "<input type=\"checkbox\" name=\"checkdeptgdsy3\" id='check_wl_"+rowData.dept_id+"'></input>";
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


//禁用入金

function loadData4(param) {
    $('#data4').treegrid(
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
            toolbar : '#toolbar4',// 工具栏
            columns : [ [
                {title:'<input type=\"checkbox\" name=\"checkdept4\" onclick=\"checkall4();\"  id=\"checkdept4\"></input>',field:'dept_id',formatter:function(value,rowData,rowIndex){
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
                                return "<input type=\"checkbox\" checked=\"checked\" name=\"checkdeptgdsy4\" id='check_rc_"+rowData.dept_id+"'></input>";
                            }else
                            {
                                return "<input type=\"checkbox\" name=\"checkdeptgdsy4\" id='check_rc_"+rowData.dept_id+"'></input>";
                            }
                        }else
                        {
                            return "<input type=\"checkbox\" name=\"checkdeptgdsy4\" id='check_rc_"+rowData.dept_id+"'></input>";
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

//删除方法
function del(sys_id) {         	
	$.messager.confirm('确认删除', '确定要删除吗?', function (r) {
		if (r) {
			$.ajax({
				url: "delSysControl",
				type: "POST",
				data: { sys_id: sys_id },
				success: function (data) {
					if (data.success == true) {
						$.messager.alert('提示信息', data.msg, 'info');
						$('#data').datagrid('reload');
					} else {
						$.messager.alert('错误信息', datae.msg, 'error');
					}
				}
			});
		}
	});
}

//添加/修改方法
var updateId;
function update(id) {
	var $this = $('#update');
	var $form = $this.find("form");
	updateId=id;
	if (updateId == undefined) {
		var thisData = {"is_use":1};
		$this.form('clear');	
		$this.form('load', thisData);
		$("#resetBtn").linkbutton('enable');
		$("#sys_type").textbox("enableValidation");
		$("#sys_type").textbox('readonly',false);
		$form.attr("action", $form.attr("action-add"));
		$this.window({ title: '添加' });
	} else {
		$("#resetBtn").linkbutton('disable');
		$this.window({ title: '编辑' });
		// 重置有效
		$("#reset").linkbutton('enable');		
		$form.attr("action", $form.attr("action-edit"));
		$this.form('clear');
		$("#sys_type").textbox("disableValidation");
		$("#sys_type").textbox('readonly',true);
		$("#sys_id").val($('#data').datagrid('getData').rows[id].sys_id);
		$this.form('load', $('#data').datagrid('getData').rows[id]);
	}
	
	$this.window({
    	top:($(window).height() - 289) * 0.5+$(document).scrollTop(),   
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

function deptdisable(){
	//$("#data1").clear();	
	var $this = $('#deptdisable');
	$this.form('clear');
	//$this.form.refresh();
	var $form = $this.find("form");
	
	$("#data2").html("");
	$('#deptdisable').dialog('open');
	//var data = eval("({userid:'"+id+"'})")
	//gzp_id=idl
	//$("#hgzp_id").val(id);	
	$.ajax({
		url : "queryDeptDisableInfo",
		type : "POST",
		dataType:"json",
		data : {				
			//gzp_id:id
		},
		success : function(data){			 
			if(data.length>0)
			{	
				loadData2(data);
			}else
			{
				loadData2();
			}
		}
	});	
	
	$this.window({
    	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 700) * 0.5
    });
	$this.window('open');
}

//禁用出金
function deptwithdrawals(){
    //$("#data1").clear();
    var $this = $('#deptwithdrawals');
    $this.form('clear');
    //$this.form.refresh();
    var $form = $this.find("form");

    $("#data3").html("");
    $('#deptwithdrawals').dialog('open');
    //var data = eval("({userid:'"+id+"'})")
    //gzp_id=idl
    //$("#hgzp_id").val(id);
    $.ajax({
        url : "queryDeptWithdrawalsInfo",
        type : "POST",
        dataType:"json",
        data : {
            //gzp_id:id
        },
        success : function(data){
            if(data.length>0)
            {
                loadData3(data);
            }else
            {
                loadData3();
            }
        }
    });

    $this.window({
        top:($(window).height() - 450) * 0.5+$(document).scrollTop(),
        left:($(window).width() - 700) * 0.5
    });
    $this.window('open');
}

//禁用入金
function deptrecharge(){
    //$("#data1").clear();
    var $this = $('#deptrecharge');
    $this.form('clear');
    //$this.form.refresh();
    var $form = $this.find("form");

    $("#data4").html("");
    $('#deptrecharge').dialog('open');
    //var data = eval("({userid:'"+id+"'})")
    //gzp_id=idl
    //$("#hgzp_id").val(id);
    $.ajax({
        url : "queryDeptRechargeInfo",
        type : "POST",
        dataType:"json",
        data : {
            //gzp_id:id
        },
        success : function(data){
            if(data.length>0)
            {
                loadData4(data);
            }else
            {
                loadData4();
            }
        }
    });

    $this.window({
        top:($(window).height() - 450) * 0.5+$(document).scrollTop(),
        left:($(window).width() - 700) * 0.5
    });
    $this.window('open');
}




function getSelected1(){ 
    var idList = "";  
     $("input:checked").each(function(){
        var id = $(this).attr("id"); 
         
        if(id.indexOf('check_type')== -1 && id.indexOf("check_dl_")>-1)
            idList += id.replace("check_dl_",'')+',';
          
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

//禁用出金
function getSelected2(){
    var idList2 = "";


    $("input:checked").each(function(){
        var id2 = $(this).attr("id");

        if(id2.indexOf('check_type')== -1 && id2.indexOf("check_wl_")>-1)
            idList2 += id2.replace("check_wl_",'')+',';

    })

    idList2 = idList2.substring(0,idList2.length-1)
    var gzp_id = $("#hgzp_id").val();
    $.ajax({
        url : "saveDeptNoWithdrawals",
        type : "POST",
        dataType:"json",
        data : {
            dept_id:idList2
        },
        success : function(data){
            $.messager.alert('提示信息', data.msg, 'info');
            if(data.success==true)
            {
                $('#deptwithdrawals').dialog('close');
            }
        }
    });
    //alert(idList+"---"+gdsy_id);

}

//禁用入金
function getSelected3(){
    var idList3 = "";
    $("input:checked").each(function(){
        var id3 = $(this).attr("id");

        if(id3.indexOf('check_type')== -1 && id3.indexOf("check_rc_")>-1)
            idList3 += id3.replace("check_rc_",'')+',';

    })

    idList3 = idList3.substring(0,idList3.length-1)
    var gzp_id = $("#hgzp_id").val();
    $.ajax({
        url : "saveDeptNoRecharge",
        type : "POST",
        dataType:"json",
        data : {
            dept_id:idList3
        },
        success : function(data){
            $.messager.alert('提示信息', data.msg, 'info');
            if(data.success==true)
            {
                $('#deptrecharge').dialog('close');
            }
        }
    });
    //alert(idList+"---"+gdsy_id);

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


//
function getParams2(){
    //获取输入框的值
    var values = $("#dept_namewl").val();
    if(values!=''){
        var rows = $('#data3').treegrid('getData'),rst=[],rst1=[],rst2=[];
        for(var i=0;i<rows.length;i++){
            if((rows[i].dept_name).indexOf(values,0)>-1){
                rst.push(rows[i]);
            }else{
                rst1.push(rows[i]);
            }
        }
        rst2 = rst.concat(rst1);
        $('#data3').treegrid('loadData',rst2);
        $("tr[id^='datagrid-row-r2-2-'],tr[id^='datagrid-row-r2-1-']").removeClass("datagrid-row-selected");
        for(var i=0;i<rows.length;i++){
            if((rows[i].dept_name).indexOf(values,0)>-1){
                $("[node-id='"+rows[i].dept_id+"']").addClass("datagrid-row-selected");

            }
        }

    }else{
        deptwithdrawals();
    }
}

//
function getParams3(){
    //获取输入框的值
    var values = $("#dept_namerc").val();
    if(values!=''){
        var rows = $('#data4').treegrid('getData'),rst=[],rst1=[],rst2=[];
        for(var i=0;i<rows.length;i++){
            if((rows[i].dept_name).indexOf(values,0)>-1){
                rst.push(rows[i]);
            }else{
                rst1.push(rows[i]);
            }
        }
        rst2 = rst.concat(rst1);
        $('#data4').treegrid('loadData',rst2);
        $("tr[id^='datagrid-row-r2-2-'],tr[id^='datagrid-row-r2-1-']").removeClass("datagrid-row-selected");
        for(var i=0;i<rows.length;i++){
            if((rows[i].dept_name).indexOf(values,0)>-1){
                $("[node-id='"+rows[i].dept_id+"']").addClass("datagrid-row-selected");

            }
        }

    }else{
        deptrecharge();
    }
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

function checkall3(){
    //salert('ok');
    //alert( $("[name='checkdept']").prop("checked"));
    if($("[name='checkdept3']").prop("checked"))
    {
        $("[name='checkdeptgdsy3']").each(function(){
            if($(this).prop("checked"))
            {
                //$(this).removeAttr("checked");
            }else{
                $(this).prop("checked",'true');
            }
        })
    }
    if(!$("[name='checkdept3']").prop("checked"))
    {
        //alert("未选中");
        $("[name='checkdeptgdsy3']").each(function(){
            $(this).removeAttr("checked");
        })
    }

}

function checkall4(){
    //salert('ok');
    //alert( $("[name='checkdept']").prop("checked"));
    if($("[name='checkdept4']").prop("checked"))
    {
        $("[name='checkdeptgdsy4']").each(function(){
            if($(this).prop("checked"))
            {
                //$(this).removeAttr("checked");
            }else{
                $(this).prop("checked",'true');
            }
        })
    }
    if(!$("[name='checkdept4']").prop("checked"))
    {
        //alert("未选中");
        $("[name='checkdeptgdsy4']").each(function(){
            $(this).removeAttr("checked");
        })
    }

}

//刷新功能
$("#refresh1").click(function(){
    deptdisable();
});

//刷新功能
$("#refresh2").click(function(){
    deptwithdrawals();
});

//刷新功能
$("#refresh3").click(function(){
    deptrecharge();
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
