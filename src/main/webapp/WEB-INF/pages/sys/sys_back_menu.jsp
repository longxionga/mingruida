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
					<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					</span>
				</form>
			</div>
		</div>

		<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;height:289" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform" method="post" enctype="multipart/form-data"  autocomplete="off" action-add="insBackMenu" action-edit="updBackMenu" data-autoform="
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
				  		<td>菜单名称：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="menu_name" name="menu_name"  data-options="required:true,validType:['menuName[\'menuValidate\']','length[2,100]']"></td>
				  	    <td>是否可用：</td>
					    <td><input class="easyui-combobox" id='is_use' name='is_use' data-options="required:true"></input></td>
				  	</tr>
				  	
				  	<tr>
						<td>菜单URL：</td>
						<td><input class="easyui-textbox" style="width:150px;" name="menu_url" data-options="required:true,validType:'length[1,100]'"></td>
						<td>是否叶子：</td>
					    <td><input class="easyui-combobox" style="width:150px;" id="is_leaf" name="is_leaf"></input></td>
				 
					</tr>	
					</tr>
					 	<tr>
					    <td>排序：</td>
					    <td><input class="easyui-textbox" style="width:150px;"  name="menu_index" data-options="validType:'int'"/></td>
					    <td>菜单图标：</td>
						<td><input class="easyui-textbox" style="width:150px;" name="menu_icon"/></td>
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
				   <input type="hidden"  id="menu_id" name="menu_id"/>
                   <input type="hidden"  id="menu_parent_id" name="menu_parent_id"/>                   
			</form>
		</div>
		<!-- 添加表单结束-->
		
	</div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/sys/sys_back_menu.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>