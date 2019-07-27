<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${applicationScope.T}</title>
	<%@include file="../../../common/common.jsp"%>
   <script type="text/javascript">
   var deptType="${deptType}";
   </script>
</head>
<body>
	<table id="data"></table>
	<div style="display: none">
		<!-- 工具栏开始 -->
		<div id="toolbar">
		
		<div class="pt10 pr10 pl10">
					<span class="lay_text_bor">
					<c:if test="${deptType==0}">
						<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新增</a></span>
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					</c:if>
					</span>
			</div>
		</div>

		<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:680px;height: 290px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform" method="post" enctype="multipart/form-data"  autocomplete="off" action-add="insEntranceManager" action-edit="updEntranceManager" data-autoform="
				callback:function(o,j){
				    if(o.success==true){
				      $.messager.alert('提示信息',o.msg,'info');
				      $('#update').window('close');
                      $('#data').datagrid('reload');
				   }else{
				      $.messager.alert('错误信息',o.msg,'error');
				   }
				}
				" >
				  <table class="tables">
				  <tr>
				  		<td>代理商编码：</td>
				  		<td colspan="3"><input class="easyui-textbox" style="width:450px;"  id="agent_code" name="agent_code"  data-options="required:true"></td>
				  	</tr> 
				  	
				  	 <tr>
				  		<td>链接地址：</td>																			
				  		<td colspan="3"><input class="easyui-textbox" style="width:450px;"  id="url" name="url"  data-options="required:true"></td>
				  	</tr> 
				  	<tr>
				  		<td>入口：</td>																			
				  		<td><input class="easyui-textbox" style="width:150px;"  id="entrance" name="entrance"  data-options="required:true,validType:'int',validType:'length[1,10]'"></td>
				  	    <td>是否默认：</td>
				  		<td><input class="easyui-combobox" style="width:150px;"  id="default" name="default"  data-options="required:true,validType:'intOrFloat',validType:'length[1,10]'"></td>
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
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/sys/sys_entrance_manager.js?v=${applicationScope.v}"></c:url>'></script>
</body>

</html>