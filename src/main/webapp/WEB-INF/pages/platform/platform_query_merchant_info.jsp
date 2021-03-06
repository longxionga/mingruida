<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${applicationScope.T}</title>
	<%@include file="../../../common/common.jsp"%>
	
	<style type="text/css">
		/* .tabs-header{padding-bottom:10px;} */
	</style>
</head>
<body>
<table id="data"></table>

<div style="display: none">
	<!-- 工具栏开始 -->
	<div id="toolbar">

		<div class="pt10 pr10 pl10">
			<form id="search" method="post">
				<span class="lay_text_bor mr20">品牌：<input class="easyui-combobox" style="width:150px;" id="brand_id" name="brand_id"/></span>
				<span class="lay_text_bor mr10">商户名称：<input class="easyui-textbox" style="width:150px;" id="merchant_name" name="merchant_name"/></span>
				<span class="lay_text_bor mr10">商户编号：<input class="easyui-textbox" style="width:150px;" id="merchant_code" name="merchant_code"/></span>
				<span class="lay_text_bor mr10">机具编号：<input class="easyui-textbox" style="width:150px;" id="machine_code" name="machine_code"/></span>
				<br/>
				<span class="lay_text_bor mr10">员工名称：<input class="easyui-textbox" style="width:150px;" id="staffname" name="staffname"/></span>
				<span class="lay_text_bor mr20">审核状态：<input class="easyui-combobox" style="width:150px;" id="audit_status" name="audit_status"/></span>
				<br/>
				<span class="lay_text_bor mr10">日期选择：<input class="easyui-datebox" name="start_date" data-options="editable:false,showSeconds:true" style="width:150px;"id="skssj" />&nbsp;-
				<input class="easyui-datebox" name="end_date" data-options="editable:false,showSeconds:true" style="width:150px;" id="sjssj"/>
				</span>
				<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:;" onclick="exportExcel();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-ok'">导出Excel</a></span>
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>

					</span>
			</form>
		</div>
	</div>

</div>


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
        $("#sjssj").datebox("setValue", strendDate);

        var skssj = $('#skssj').datebox('getValue')
        var sjssj = $('#sjssj').datebox('getValue')
        loadData({start_date:skssj,end_date:sjssj});//初始化加载数据

	});
</script>

<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/platform/platform_query_merchat_info.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>