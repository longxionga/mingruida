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
				<%--<span class="lay_text_bor mr10">所属品牌：<input  id="brindnameid" name="brindnameid" data-options="valueField:'id',textField:'text'" style="width:150px;" /></span>--%>
				<span class="lay_text_bor mr20">批次号：<input class="easyui-textbox" style="width:150px;" name="batchcode"/></span>
				<span class="lay_text_bor mr20">机具编号：<input class="easyui-textbox" style="width:150px;" name="machineSN"/></span>
					<span class="lay_text_bor mr20">机具序列号：<input class="easyui-textbox" style="width:150px;" name="machinecode"/></span>
				<span class="lay_text_bor mr20">归属员工：<input class="easyui-textbox" id="staffname111" style="width:150px;" name="staffname" /></span>
			<br/>

				<span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>

                </span>
			</form>
		</div>
	</div>

	<!-- 机具调拨/回调表单开始 -->


</div>
<div id="formobj"></div>

<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/platform/platform_find_machine_info.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>