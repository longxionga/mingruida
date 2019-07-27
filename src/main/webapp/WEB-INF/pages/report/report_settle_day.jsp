<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>${applicationScope.T}</title>
	<%@include file="../../../common/common.jsp"%>
	<table id="data"></table>
</head>
<body>


<div style="display: none">
		<!-- 工具栏开始 -->
		<div id="toolbar">		
			<div class="pt10 pr10 pl10">
			<form id="search" method="post">				
				<span class="lay_text_bor mr20">统计日期：<input class="easyui-datebox" id="start_date" name="start_date" data-options="editable:false,showSeconds:false" style="width:150px;" />&nbsp;-
				<input class="easyui-datebox" id="end_date" name="end_date" data-options="editable:false,showSeconds:false" style="width:150px;" /></span>	
				<span class="lay_text_bor mr10">位属服务商：<input  class="easyui-combobox" style="width:150px;" id="settle_id" name="settle_id" data-options="valueField:'id',textField:'text'"/></span>
				<span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					<!-- <span class="mr10"><a href="#" onclick='expInfo();' class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-redo'">导出报表</a></span>  -->

					<!-- 查询按钮放最前，刷新按钮放最后 -->
				</span>
				</form>
			</div>
			<div  id="total" class="total"></div>
		</div>
</div>



	
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/report.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/report/report_settle_day.js?v=${applicationScope.v}"></c:url>'></script>

</body>
</html>