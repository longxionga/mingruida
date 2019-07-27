<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${applicationScope.T}</title>
	<%@include file="../../../common/common.jsp"%>
	<script type="text/javascript" src='<c:url value="/static/ueditor/ueditor.config.js?v=${applicationScope.v}"></c:url>'></script>
    <script type="text/javascript" src='<c:url value="/static/ueditor/ueditor.all.js?v=${applicationScope.v}"></c:url>'></script>
	<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
</head>
<body>
	<table id="data"></table>
	<div style="display: none">
		<!-- 工具栏开始 -->
		<div id="toolbar">
			<div class="pt10 pr10 pl10">
				<form id="search" method="post">
				   <span class="lay_text_bor mr20">标题：<input class="easyui-textbox" style="width:150px;" name="center_abstract"/></span>
				   <span class="lay_text_bor mr20">正文标题：<input class="easyui-textbox" style="width:150px;" name="center_title" /></span>
				    <span class="lay_text_bor mr10">消息类型：<input class="easyui-combobox" style="width:150px;" id="type" name="center_type" /></span>
					<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
						<!-- <span class="mr10"><a href="javascript:;" onclick="$('#search').form('clear');" id="reset1" class="easyui-linkbutton" style="width:80px;">重 置</a></span> -->
						<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新增</a></span>
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
						
					</span>
				</form>
			</div>
		</div>
		<!-- 工具栏结束 -->
		<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" title="" style="width:700px;height:450px; padding:10px 10px 70px 10px;overflow-x: hidden;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform" method="post" autocomplete="off" action-add="insCenterInfo" action-edit="updCenterInfo" data-autoform="
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
				   <table class="tables" style="margin-left: 20px;">
				  
				  	<tr>
				  	 <td>标题</td>
				  	<td colspan="3"><input class="easyui-textbox" style="width:474px;" data-options="required:true,validType:'length[2,100]'" id="center_abstract" name="center_abstract"></td>
				  	</tr>
				  	<tr>
				  	 <td>部门</td>
				  	<td colspan="3"><input id="dept_id" name="dept_id" data-options="valueField:'id',textField:'text'"  ></input>
				  	</td>
				  	</tr>
				  	<tr>
						<td width="60">类型</td>
						<td><input class='easyui-textbox' style="width:150px;" data-options="required:true" id="center_type" name="center_type"></td>
				  	    <td width="60">发布人</td>
						<td><input class="easyui-textbox" style="width:150px;" data-options="required:true,validType:'length[2,20]'" id="center_user" name="center_user"></td>
					</tr>
					<tr>
						<td>浏览次数</td>
						<td><input class="easyui-textbox" style="width:150px;"   data-options="validType:'int'" name="center_number"></td>
						<td>是否可用</td>
						<td colspan="3"><input class="easyui-combox" style="width:150px;" id="is_use" name="is_use"></td>
					</tr>
					<tr>
					  <td>有效日期</td>
					<td><input class="easyui-datetimebox" name="use_date" data-options="required:true,editable:false,showSeconds:false" style="width:150px;" /></td>
					 <td>是否重要</td>
					  <td><input class="easyui-combox" style="width:150px;" id="is_important" name="is_important"></td>
					</tr>	
						<tr>
				  	<td>正文标题</td>
				  	<td colspan="3"><input class="easyui-textbox" style="width:474px;" name="center_title"  data-options="required:true,validType:'titles'"></td>
				  	</tr>
			  	<tr>
			  		<td colspan="4"><div style="height:30px;">内容</div><script id="container" name="center_text" type="text/plain"></script></td>
			  	</tr>			  
				  </table>
					<div class="lay_tables_oper">	
						<div class="oper_width">	 
							<a href="javascript:;" onclick="$('#updateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
							<a href="#" class="easyui-linkbutton l-btn-grey"  id="reset" style="width:80px;">重 置</a>
						</div>
					</div>
				  <input type="hidden" value="" id="center_id" name="center_id"/>
			</form>
		</div>
		<!-- 添加表单结束 -->
	</div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/sys/sys_center_info.js?v=${applicationScope.v}"></c:url>'></script>
</body>
	<script type="text/javascript">
	UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
	UE.Editor.prototype.getActionUrl = function(action) {
		if (action == "uploadimage") {
			return "../../../../upload/uploadImage";
		} else {
			return this._bkGetActionUrl.call(this, action);
		}
	};
var loginName="${loginName}";
</script>
</html>