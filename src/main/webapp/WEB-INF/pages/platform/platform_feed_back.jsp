<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<%@include file="../../../common/common.jsp"%>
</head>
<body>
	<table id="data"></table>
	<div style="display: none">
		<!-- 工具栏开始 -->
		<div id="toolbar">
			<div class="pt10 pr10 pl10">
				<form id="search" method="post">
				<span class="lay_text_bor mr10">用户名称：<input class="easyui-textbox" style="width:150px;" name="user_name"/></span>
				<span class="lay_text_bor mr20">位属服务商： <select id="settle_id" name="settle_id" class="easyui-combobox" data-options="valueField:'id'"style="width:150px" ></select></span>
				<span class="lay_text_bor mr20">位属代理商：<select id="agent_id" name="agent_id" class="easyui-combobox" data-options="valueField:'id'" style="width:150px" ></select></span>
				<span class="lay_text_bor mr20">位属部门：<input class="easyui-combobox" id="DID" name='DID' style="width:150px;" data-options="valueField:'id',textField:'text'" /></span>

				<span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
				</span>
				</form>
			</div>
		</div>
		</div>
	<!--信息详情查看 -->
	<div id="msg" class="easyui-window" title="客户反馈信息" closed="true" style="width:800px;height:400px;padding:20px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
	</div>
	<div class="lay_info_window fadeInRight animated">
		<div class="info_w_title"></div>
		<div class="info_w_cont"></div>
	</div>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/common.js"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/report.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/platform/platform_feed_back.js"></c:url>'></script>
</body>
</html>