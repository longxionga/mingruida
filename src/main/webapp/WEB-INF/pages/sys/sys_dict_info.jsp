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
				   <span class="lay_text_bor mr20">字典编码：<input class="easyui-textbox" style="width:150px;" name="dict_code"/></span>
				   <span class="lay_text_bor mr20">字典id：<input class="easyui-textbox" style="width:150px;" name="dict_id"/></span>
				   <span class="lay_text_bor mr20">字典说明：<input  class="easyui-combobox" style="width:150px;" id="dict_desc" name="dict_desc" /></span>
					<span class="lay_text_bor mr10">是否可用：
						<select id="cc" class="easyui-combobox" name="is_use_s" style="width:150px;">
						    <option value="1" select>是</option>
						    <option value="0">否</option>
						    <option value="0,1">---全部---</option>
						</select>
					</span>
					<span class="lay_text_bor mr20">字典名称：<input  class="easyui-textbox" style="width:150px;" name="dict_name" /></span>
					<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
						<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新增</a></span>
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					</span>
				</form>
			</div>
		</div>

		<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;height:330px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform" method="post" autocomplete="off" action-add="insBackDictInfo" action-edit="updBackDictInfo" data-autoform="
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
						<td>分类编号：</td>
						<td><input class="easyui-textbox" style="width: 150px;"
							id="dict_code" name="dict_code"
							data-options="required:true,validType:['codeId[\'dictCodeIdValidate\']','length[2,19]']"></td>
						<td>分类名称：</td>
						<td><input class="easyui-textbox" style="width: 150px;"
							id="dict_name" name="dict_name"
							data-options="required:true,validType:['codeName[\'dictValidate\']','length[2,50]']"></input></td>
					</tr>
					
					<tr>
						<td>分类id：</td>
						<td><input class="easyui-textbox" style="width: 150px;"
							id="dict_id" name="dict_id" data-options="required:true,validType:['codeId[\'dictCodeIdValidate\']','length[1,10]']"></td>
						<td>是否可用：</td>
					    <td><input class="easyui-combobox" id='is_use' name='is_use'></input></td>
					</tr>
					
					<tr>
						<td>描述：</td>
						<td><input class="easyui-textbox" style="width: 150px;"
							name="dict_desc" data-options="required:true,validType:'length[1,50]'"></td>
						<td>排序：</td>
						<td><input class="easyui-textbox" style="width: 150px;"
							name="is_order" data-options="required:true,validType:'length[1,10]',validType:'int'"></td>
					</tr>
					<tr>
						  <td>字典值：</td>
						  <td><input class="easyui-textbox" style="width: 150px;"
									 name="dict_value" data-options="validType:'length[1,50]'"></td>
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
				  				  
			</form>
		</div>
		<!-- 添加表单开始 -->
	</div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/sys/sys_dict_info.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>