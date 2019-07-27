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
				<span class="lay_text_bor mr10">交易流水号：<input class="easyui-textbox" style="width:150px;" name="order_id"/></span>
				<span class="lay_text_bor mr10">商户编号：<input class="easyui-textbox" style="width:150px;" name="merchant_code"/></span>
				<span class="lay_text_bor mr10">机具编号：<input class="easyui-textbox" style="width:150px;" name="machine_code"/></span>
				<br/>

				<span class="lay_text_bor mr20">交易类型：<input class="easyui-combobox" style="width:150px;" id="order_type_1" name="order_type"/></span>
				<span class="lay_text_bor mr20">支付类型：<input class="easyui-combobox" style="width:150px;" id="pay_type_1" name="pay_type"/></span>
				<span class="lay_text_bor mr20">订单状态：<input class="easyui-combobox" style="width:150px;" id="order_state_1" name="order_state"/></span>
			<br/>
				<span class="lay_text_bor mr10">日期选择：<input class="easyui-datebox" name="start_date" data-options="editable:false,showSeconds:true" style="width:150px;"id="skssj" />&nbsp;-
				<input class="easyui-datebox" name="end_date" data-options="editable:false,showSeconds:true" style="width:150px;" id="sjssj"/></span>
				<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
						<!--<span class="mr10"><a href="javascript:InitInfo();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">初始化机具</a></span>-->
						<%--<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新增</a></span>--%>
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
        strDate += curr_time.getMonth() + "";
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
<script type="text/javascript" src='<c:url value="/static/platform/platform_machine_order_info.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>