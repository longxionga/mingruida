<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${applicationScope.T}</title>
	<%@include file="../../../common/common.jsp"%>
</head>
<body>
	<table id="data"></table>
	<div style="display: none">
		<!-- 工具栏开始 -->
		<div id="toolbar">
			<div class="pt10 pr10 pl10">
				<form id="search" method="post">				  
					<span class="lay_text_bor mr10">是否可用：
						<select id="cc" class="easyui-combobox" name="is_use" style="width:150px;">
						    <option value="1" select>是</option>
						    <option value="0">否</option>
						    <option value="">---全部---</option>
						</select>
					</span>
					<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
						<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新增</a></span>
						<span class="mr10"><a href="javascript:;"  onclick="deptdisable();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">设置禁用代理商</a></span>						
						<span class="mr10"><a href="javascript:;"  onclick="deptwithdrawals();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">设置禁用代理商出金</a></span>
						<span class="mr10"><a href="javascript:;"  onclick="deptrecharge();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">设置禁用代理商入金</a></span>

						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					</span>
				</form>
			</div>
		</div>

		<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;height:289px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform" method="post" autocomplete="off" action-add="insSysControl" action-edit="updSysControl" data-autoform="
				callback:function(o,j){
				    if(o.success==true){
				      $.messager.alert('提示信息',o.msg,'info');
				      $('#update').window('close');
                      $('#data').datagrid('reload');
				   }else{
				      $.messager.alert('错误信息',o.msg,'error');
				   }
				}
				">
				  <table class="tables">
					<tr>						
						<td>控制类型：</td>
						<td><input class="easyui-textbox" style="width: 150px;"
							id="sys_type" name="sys_type"
							data-options="required:true,validType:['sysType[\'typeValidate\']','length[2,100]']"></input></td>
								<td>是否可用：</td>
					    <td><input class="easyui-combobox" id='is_use' name='is_use'></input></td>
					</tr>
					
					<tr>						
					<td>控制说明：</td>
						<td colspan="3"><input class="easyui-textbox" style="width: 417px;"
							id="remark" name="remark"
							data-options="required:true,validType:['length[2,100]']"></td>
					</tr>									
					<tr>
						<td colspan="4" align="center">
							<div class="pt15">
							<a href="javascript:;" onclick="$('#updateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
							<a href="#" id="reset" class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
							</div>
						</td>
					</tr>				  
				  </table>
				  		<input type="hidden"  id="sys_id" name="sys_id"/>		  
			</form>
		</div>
		<!-- 添加表单开始 -->
	<div id="deptdisable" class="easyui-window" title="部门选择" closed="true"  style="width:700px;padding:0;height:450px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<!-- 工具栏开始 -->
		<div id="toolbar2">
			<div style="padding-bottom: 10px;">部门名称：<input id="dept_namess" class="easyui-textbox" style="width: 150px;" name="dept_namess" data-options="validType:'TextSearch'"/>
	      <span class="mr10"><a href="javascript:;" onclick="getParams1();" class="easyui-linkbutton" iconCls="icon-search">查询</a>		
			  <a href="javascript:;" id="refresh1" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
			</div>
			<div style="border-top: 1px #ddd solid; padding-top: 10px;">
				<a href="javascript:getSelected1();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">保存</a>				
			</div>
		</div>
	<table id="data2"></table>
	<!-- <div stlye="height:20px">
	<a href="javascript:;" onclick="$('#updateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
	 -->
	</div>
		<-- 禁用出金 -->
		<div id="deptwithdrawals" class="easyui-window" title="部门选择" closed="true"  style="width:700px;padding:0;height:450px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<!-- 工具栏开始 -->
			<div id="toolbar3">
				<div style="padding-bottom: 10px;">部门名称：<input id="dept_namewl" class="easyui-textbox" style="width: 150px;" name="dept_namewl" data-options="validType:'TextSearch'"/>
					<span class="mr10"><a href="javascript:;" onclick="getParams2();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			  <a href="javascript:;" id="refresh2" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
				</div>
				<div style="border-top: 1px #ddd solid; padding-top: 10px;">
					<a href="javascript:getSelected2();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">保存</a>
				</div>
			</div>
			<table id="data3"></table>
			<!-- <div stlye="height:20px">
            <a href="javascript:;" onclick="$('#updateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
             -->
		</div>
		<-- 禁用入金 Recharge-->
		<div id="deptrecharge" class="easyui-window" title="部门选择" closed="true"  style="width:700px;padding:0;height:450px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<!-- 工具栏开始 -->
			<div id="toolbar4">
				<div style="padding-bottom: 10px;">部门名称：<input id="dept_namerc" class="easyui-textbox" style="width: 150px;" name="dept_namerc" data-options="validType:'TextSearch'"/>
					<span class="mr10"><a href="javascript:;" onclick="getParams3();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			  <a href="javascript:;" id="refresh3" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
				</div>
				<div style="border-top: 1px #ddd solid; padding-top: 10px;">
					<a href="javascript:getSelected3();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">保存</a>
				</div>
			</div>
			<table id="data4"></table>
			<!-- <div stlye="height:20px">
            <a href="javascript:;" onclick="$('#updateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
             -->
		</div>

	</div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/platform/platform_sys_control.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>