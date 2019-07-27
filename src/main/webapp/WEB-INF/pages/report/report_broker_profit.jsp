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
				<!-- <span class="lay_text_bor mr20">代理商名：<input class="easyui-textbox" style="width:150px;" name="agent_name"/></span>
				<span class="lay_text_bor mr20">服务商名：<input  class="easyui-textbox" style="width:150px;" name="settle_name" /></span> -->
				<span class="lay_text_bor mr20">服务商选择：<input class="easyui-combobox" id="settle_id" name="settle_id" data-options="valueField:'id',textField:'text'" style="width:150px;" /></span>
				<span class="lay_text_bor mr20">代理商选择：<input class="easyui-combobox"  id="agent_id"  name="agent_id" data-options="valueField:'id',textField:'text'" style="width:150px;" /></span>
				<span class="lay_text_bor mr20">部门选择：<input class="easyui-combobox"  id="DID"  name="DID" data-options="valueField:'id',textField:'text'" style="width:150px;" /></span>
				<span class="lay_text_bor mr20">经纪人名称：<input class="easyui-textbox" style="width:150px;" name="broker_name"/></span>
				<span class="lay_text_bor mr20">统计日期：<input class="easyui-datetimebox" id="skssj"s name="start_date" data-options="editable:false,showSeconds:false" style="width:150px;" />&nbsp;-
				<input class="easyui-datetimebox" id="sjssj" name="end_date" data-options="editable:false,showSeconds:false" style="width:150px;" /></span>
				<span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<!--<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton mr10" data-options="plain:true,iconCls:'icon-add'">新增</a></span> -->
					<!-- <a href="javascript:search();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查询</a> -->
					<!-- <a href="javascript:excel();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'">导出</a> -->
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					<!-- <span class="mr10"><a href="#" onclick='expInfo();' class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-redo'">导出报表</a></span>-->

					<!-- 查询按钮放最前，刷新按钮放最后 -->
				</span>
				</form>
			</div>

		</div>
</div>

<script>
    $(function() {
        var curr_time = new Date();
        var strDate = curr_time.getFullYear() + "-";
        strDate += curr_time.getMonth() + 1 + "-";
        strDate += curr_time.getDate() + "-";
        strDate += " " + "00" + ":";
        strDate += "00" + ":";
        strDate += "00";

        var strendDate = curr_time.getFullYear() + "-";
        strendDate += curr_time.getMonth() + 1 + "-";
        strendDate += curr_time.getDate();
        strendDate += " " + "23" + ":";
        strendDate += "59" + ":";
        strendDate += "59";

        $("#skssj").datetimebox("setValue", strDate);
        $("#sjssj").datetimebox("setValue", strendDate);
    });
</script>
	
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/report.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/report/report_broker_profit.js?v=${applicationScope.v}"></c:url>'></script>

</body>
</html>