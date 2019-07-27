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
				<span class="lay_text_bor mr20">活动期号：<input class="easyui-textbox" id="lottery_no" name="lottery_no" data-options="valueField:'id',textField:'text'" style="width:150px;" /></span>
				<span class="lay_text_bor mr20">竞猜结果：<input class="easyui-textbox"  id="lottery_result"  name="lottery_result" data-options="valueField:'id',textField:'text'" style="width:150px;" /></span>
				<span class="lay_text_bor mr20">活动状态：<input class="easyui-combobox"  id="status"  name="status" required:true data-options="valueField:'id',textField:'text'" style="width:150px;" /></span>
				<span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>

				</span>
			</form>
		</div>

	</div>
</div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/report/report_lottery.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>