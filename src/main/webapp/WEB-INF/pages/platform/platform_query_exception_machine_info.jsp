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
				<span class="lay_text_bor mr10">更新日期：<input class="easyui-datebox" name="update_time" data-options="editable:false,showSeconds:true" style="width:150px;"id="skssj" />
				</span>
				<span class="lay_text_bor mr10">所属品牌：<input  id="brindnameid" name="brindnameid" data-options="valueField:'id',textField:'text'" style="width:150px;" /></span>
				<span class="lay_text_bor mr20">机具序列号：<input class="easyui-textbox" style="width:150px;" name="machinecode"/></span>
				<span class="lay_text_bor mr20"> 是否变动：<input class="easyui-combobox" id="ischange" style="width:150px;" name="ischange"/></span>

			<br/>

				<span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="brachremore();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-ok'">机具批量删除</a></span>
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>

                </span>
			</form>
		</div>
	</div>

</div>
<div id="formobj"></div>

<script>
	$(function() {
		var curr_time = new Date();
		var strDate = curr_time.getFullYear() + "";
		strDate += curr_time.getMonth()+1 + "";
		// strDate += curr_time.getDate()-1;
		strDate += curr_time.getDate()-1;

		var strendDate = curr_time.getFullYear() + "";
		strendDate += curr_time.getMonth()+1 + "";
		strendDate += curr_time.getDate()-1;

		$("#skssj").datebox("setValue", strDate);
		// $("#sjssj").datebox("setValue", strendDate);

		var skssj = $('#skssj').datebox('getValue')
		// var sjssj = $('#sjssj').datebox('getValue')
		loadData({update_date:skssj,ischange:"1"});//初始化加载数据

	});
</script>

<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/platform/platform_query_exception_machine_info.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>