<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${applicationScope.T}</title>
	<%@include file="../../../common/common.jsp"%>
	<%@include file="../../../common/upload.jsp"%>

</head>
<body>
<table id="data"></table>

<div style="display: none">
	<!-- 工具栏开始 -->
	<div id="toolbar">

		<div class="pt10 pr10 pl10">
			<form id="search" method="post">
				<span class="lay_text_bor mr10">所属品牌：<input  id="brindnameid" name="brindnameid" data-options="valueField:'id',textField:'text'" style="width:150px;" /></span>
				<span class="lay_text_bor mr20">批次号：<input class="easyui-textbox" style="width:150px;" id="batchcode" name="batchcode"/></span>
<%--
				<span class="lay_text_bor mr20">机具编号：<input class="easyui-textbox" style="width:150px;" name="machineSN"/></span>
--%>
				<span class="lay_text_bor mr20">机具序列号：<input class="easyui-textbox" style="width:150px;" id="machinecode11" name="machinecode"/></span>
				<span class="lay_text_bor mr20">归属员工：<input class="easyui-textbox" style="width:150px;" id="staffname11" name="staffname"/></span>

				<br/>
				<span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="exportExcel();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-ok'">导出Excel</a></span>
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>

                    <%--	<span class="mr10"><a href="javascript:;" onclick="closeAll();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除所有机具</a></span>--%>
                    <span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>

                </span>
			</form>
		</div>
	</div>

	<!-- 添加/修改表单开始 -->
	<div id="update" class="easyui-window" title="" style="padding:15px 13px 70px 30px;width:680px;height:450px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<form id="updateform" method="post" enctype="multipart/form-data"  autocomplete="off" action-add="insBankRule" action-edit="updBankRule" data-autoform="
				callback:function(o,j){
				    if(o.success==true){
				      $.messager.alert('提示信息',o.msg,'info');
				      $('#update').window('close');
                      $('#data').treegrid('reload');
				   }else{
				      $.messager.alert('错误信息',o.msg,'error');
				   }
				}
				" >
			<table class="tables">
				<tr>
					<td>激活补贴：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="activation_subsidy" name="activation_subsidy"  data-options="required:true,validType:'intOrFloat',validType:'length[1,10]'"></td>
					<td>激活总数：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="activation_total" name="activation_total"  data-options="required:true,validType:'intOrFloat',validType:'length[1,10]'"></td>
				</tr>
				<tr>
					<td>未激活：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="un_activation_total" name="un_activation_total"  data-options="required:true,validType:'intOrFloat'"></td>
				</tr>
				<tr>
					<td>达标总数：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="standard_total" name="standard_total"  data-options="required:true,validType:'intOrFloat',validType:'length[1,10]'"></td>
					<td>未达标总数：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="un_standard_total" name="un_standard_total"  data-options="required:true,validType:'intOrFloat',validType:'length[1,10]'"></td>
				</tr>

				<tr>
					<td>品牌是否可用：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="status" name="status"  data-options="required:true,validType:['length[1,20]']"></td>
				</tr>
			</table>
			<div class="lay_tables_oper">
				<div class="oper_width">
					<a href="javascript:;" onclick="up()" class="easyui-linkbutton" style="width:80px;">提 交 </a>
					<a href="#"  id="reset" class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
				</div>
			</div>
			<input type="hidden"  id="id" name="id"/>
		</form>
	</div>
	<!-- 添加表单开始 -->

	<!-- 代理商所属部门下的所有代理商 -->
	<div id = "broker_id" class="easyui-window" title="更换所属代理商" style="width: 700px; padding: 0; height: 450px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<div id="toolbar1">
			<div>
				<input id="dataID" type="hidden">
				<input id="staffid" type="hidden">
				<input id="machinecode" type="hidden">
				<input id="brindid" type="hidden">

				<form id="search1" method="post">
					员工姓名：<input id="broker_name" class="easyui-textbox" style="width: 150px;" name="broker_name" data-options="validType:'TextSearch'"/>
					员工编号：<input id="broker_code" class="easyui-textbox" style="width: 150px;" name="broker_code"  data-options="validType:'TextSearch'"/>
					<a href="javascript:;" onclick="$('#search1').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
					<a href="javascript:;" id="refresh" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
				</form>
			</div>

		</div>
		<table id="broker"></table>
	</div>
</div>


<script>

	// function closeAll() {
	// 	$.messager.confirm('删除机具', '是否删除所有机具', function(r) {
	// 		if (r) {
	// 			$.ajax({
	// 				url : "deleteMachineInfoAll",
	// 				type : "POST",
	// 				dataType : "json",
	// 				data : {
	// 				},
	// 				success : function(data) {
	// 					$.messager.alert('提示信息', data.msg, 'info');
	// 					$('#search').submit();
	// 				}
	// 			});
	// 		}
	// 	});
	// }
</script>

<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/platform/platform_machine_info.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>