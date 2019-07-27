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

<div>
		<!-- 工具栏开始 -->
		<div id="toolbar" class="datagrid-toolbar">		
			<div class="pt10 pr10 pl10">
			<form id="search" method="post">				
				<!--<span class="lay_text_bor mr20">用户名：<input class="easyui-textbox" style="width:150px;" name="user_name"/></span>  -->
				
				<span class="lay_text_bor mr10">手机号：<input  class="easyui-textbox" style="width:150px;" name="mobile" data-options="required:true,validType:'mobile'" /></span>				
				
				<span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>

					<!-- 查询按钮放最前，刷新按钮放最后 -->
				</span>
			</form>
			</div>
            <div id="total" class="total">
            </div>
		</div>
</div>
	
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/report.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/sys/sys_back_operation_info.js?v=${applicationScope.v}"></c:url>'></script>

</body>
</html>