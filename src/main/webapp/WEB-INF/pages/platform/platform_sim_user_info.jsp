<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${applicationScope.T}</title>
	<%@include file="../../../common/common.jsp"%>
	
	 <script type="text/javascript">

     var dept_type="${dept_type}";
     var agent_id ="${agent_id}";

   </script>
</head>
<body>
	<table id="data"></table>
	<div style="display: none">
		<!-- 工具栏开始 -->
		<div id="toolbar">
			<div class="pt10 pr10 pl10">
				<form id="search" method="post">
				   <span class="lay_text_bor mr10">用户名称：<input class="easyui-textbox" style="width:150px;" name="user_name" data-options="validType:'TextSearch'"/></span>
					<span class="lay_text_bor mr10">手机号：<input class="easyui-textbox" style="width:150px;" name="mobile"; data-options="validType:'mobile'"/></span>
					<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
						
						<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton mr10" data-options="plain:true,iconCls:'icon-add'">新增</a></span>
						
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					</span>
				</form>
			</div>
		</div>
		
		 
		<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;height:289" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			 
			 <form id="updateform" method="post" autocomplete="off" action-add="saveSimUserInfo" action-edit="updSimUserInfo" data-autoform="
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
				  		<td>用户名：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="user_name" name="user_name"  data-options="required:true,validType:['length[2,100]']"></td>
				  	    <td>手机号码：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="user_mobile" name="user_mobile"  data-options="required:true,validType:['mobile']"></td>
				  	</tr>
				  	
				  	<tr>
						<td>密码：</td>
               			<td><input type="password" style="width:150px;" name="user_password" class="easyui-textbox" style="width:180px;" data-options="required:true,validType:'length[5,100]',missingMessage:'不能为空',invalidMessage:'长度5-20字符'"></td>
						<td>金额：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="user_money" name="user_money"  data-options="required:true,validType:['intOrFloat']"></td>
					</tr>	
					<tr>
				  	    <td>是否可用：</td>
					    <td><input class="easyui-combobox" id='is_use' name='is_use'></input></td>
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
				   <input type="hidden"  id="user_id" name="user_id"/>
			</form>
		</div>
		
	</div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/platform/platform_sim_user_info.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>





