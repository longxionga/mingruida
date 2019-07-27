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
					<span class="lay_text_bor mr10">角色名称：
					   <input class="easyui-textbox" style="width: 150px;" name="role_name" /></span>
					   <span class="lay_text_bor">
					    <span class="mr10">
						<a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
						<span class="mr10">
						<a href="javascript:update();" class="easyui-linkbutton mr10" data-options="plain:true,iconCls:'icon-add'">新增</a></span> 
						<!-- <a href="javascript:search();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查询</a> -->
						<!-- <a href="javascript:excel();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'">导出</a> -->
						<span class="mr10">
						<a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					</span>
				</form>
			</div>
		</div>

		<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" closed="true"
			style="padding: 15px 13px 15px 30px; width: 588px; height:258px;"
			data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform" method="post" autocomplete="off"
				action-add="insBackRole" action-edit="updBackRole"
				data-autoform="
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
						<td>角色名称：</td>
						<td><input class="easyui-textbox" style="width: 150px;"
							id="role_name" name="role_name"
							data-options="required:true,validType:['ajax[\'roleValidate\']','length[2,100]']"></td>
						<td>是否可用：</td>
						<td><input class="easyui-combobox" id='is_use' name='is_use'></input></td>
					</tr>
					<tr>
						<td>说明：</td>
						<td><input class="easyui-textbox" style="width: 150px;"
							name="role_desc" data-options="required:true,validType:'length[2,100]'"></td>
						<td>部门等级</td>
						<td><input class='easyui-combobox' style="width: 150px;"
							data-options="required:true" id="dict_id" name="dict_id">
						</td>
					</tr>					
					<tr>
						<td colspan="4" align="center">
							<div class="pt30">
								<a href="javascript:;" onclick="$('#updateform').submit();"
									class="easyui-linkbutton" style="width: 80px;">提 交 </a> <a
									href="#"
									id="reset" class="easyui-linkbutton l-btn-grey"
									style="width: 80px;">重 置</a>
							</div>
						</td>
					</tr>
				</table>
				<input type="hidden" value="" id="role_id" name="role_id" />
			</form>
		</div>
	<!-- 添加表单结束-->
	<!-- 菜单分配开始 -->
	<!-- <div id="menuDiv" class="easyui-window" title="" closed="true"
			style="width: 700px; padding: 0; height: 450px;"
			data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,closed:!0">
			<span class="mr10"><a href="javascript:saveMenu();" class="easyui-linkbutton mr10" data-options="plain:true,iconCls:'icon-add'">保存</a></span>
			<table id="menu"></table>			
		</div>  -->


	<div id="menuupdate" class="easyui-window" title="分配菜单" closed="true"
		style="width: 700px; padding: 0; height: 450px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<div id="toolbar2">
			<div style="border-top: 0px #ddd solid;">
				<a href="javascript:getSelected();" class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-add'">保存</a>
			</div>
		</div>
		<table id="menu"></table>
		<input type="hidden" id="role_id" name="role_id">
	</div>

	<!-- 菜单分配结束 -->
	<!-- 人员分配开始 -->
	<div id="userupdate" class="easyui-window" title="人员分配" closed="true"
		style="width: 700px; padding: 0; height: 450px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">

		<div id="toolbar3">
		<div style="padding-bottom:10px;">用户名称：<input id="user_nick_names" class="easyui-textbox" style="width: 150px;" name="user_nick_names" data-options="validType:'TextSearch'"/>		
			      <span class="mr10"><a href="javascript:;" onclick="getParams();" class="easyui-linkbutton" iconCls="icon-search">查询</a> 
			      <a href="javascript:;" id="refresh" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
			</div>
			<div style="border-top: 1px #ddd solid;padding-top:10px;">
				<a href="javascript:getSelected1();" class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-add'">保存</a>
			</div>
		</div>
		<table id="user"></table>
		<input type="hidden" id="role_id" name="role_id">
		<input type="hidden" id="dict_id" name="dict_id">
	</div>
	</div>
	<!-- <div id="userDiv" class="easyui-window" title="" closed="true"
			style="width: 700px; padding: 0; height: 450px;"
			data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,closed:!0">
			<span class="mr10"><a href="javascript:saveUser();" class="easyui-linkbutton mr10" data-options="plain:true,iconCls:'icon-add'">保存</a></span>
			<table id="user"></table>
			 <input type="hidden"  id="roleId" name="roleId"/>		
		</div> -->


	<!-- 人员分配结束 -->
	<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
	<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
	<script type="text/javascript" src='<c:url value="/static/sys/sys_back_role.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>