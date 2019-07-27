<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${applicationScope.T}</title>
	<%@include file="../../../common/common.jsp"%>
	<%@include file="../../../common/upload.jsp"%>
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
					<span class="lay_text_bor mr10">员工名称：<input class="easyui-textbox" style="width:150px;" name="name"/></span>
					<span class="lay_text_bor mr10">手机号：<input class="easyui-textbox" style="width:150px;" name="mobile" data-options="validType:'mobile'"/></span>
					<span class="lay_text_bor mr10">身份证：<input class="easyui-textbox" style="width:150px;" name="id_card" /></span>
					<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
						<span class="mr10"><a href="javascript:;" onclick="allDisAgree();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">批量删除</a></span>
						<span class="mr10"><a href="javascript:;" onclick="closeAll();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除所有用户</a></span>
						<!-- c标签判断 登陆者是否为代理商-->
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					</span>
				</form>
			</div>
		</div>

		<!-- 短信验证表单开始 -->
		<div id="code" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:350px;height:340px" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="codeform" method="post" enctype="multipart/form-data"  autocomplete="off" action-add="checkCode" >
				<table class="tables" id="tables">
					<tbody>
					</tbody>
				</table>
				<input type="hidden"  id="id" name="id"/>
			</form>
		</div>
	</div>



	<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
	<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/platform/platform_user_info.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/report.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>





