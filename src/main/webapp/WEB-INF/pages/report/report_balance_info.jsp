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
				   <span class="lay_text_bor mr10">用户名称：<input class="easyui-textbox" style="width:150px;" name="user_name"/></span>
					<span class="lay_text_bor mr10">手机号：<input class="easyui-textbox" style="width:150px;" name="mobile"; data-options="validType:'mobile'"/></span>
					<span class="lay_text_bor mr20">位属服务商：<input  class="easyui-combobox" style="width:150px;" id="settle_id" name="settle_id" data-options="valueField:'id',textField:'text'"/></span>
				    <span class="lay_text_bor mr20">位属代理商：<input class="easyui-combobox" id="agent_id" name="agent_id" style="width:150px;" data-options="valueField:'id',textField:'text'"/></span>
					<span class="lay_text_bor mr20">位属部门：<input class="easyui-combobox" id="DID" name='DID' style="width:150px;" data-options="valueField:'id',textField:'text'" /></span>
					<span class="lay_text_bor mr10">位属经纪人：<input class="easyui-textbox" style="width:150px;" name="broker_name" data-options="validType:'TextSearch'"/></span> 	
					<span class="lay_text_bor mr20">是否可用：<input class="easyui-combobox" id="is_useb" name='is_useb' style="width:150px;" data-options="valueField:'id',textField:'text'" /></span>				
					<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					</span>
				</form>
			</div>
				<div id="total" class="total"></div>
		</div>
	</div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/report/report_balance_info.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/report.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>





