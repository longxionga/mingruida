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
				 <span class="lay_text_bor mr20">创建时间：<input class="easyui-datetimebox" name="start_date" data-options="editable:true,showSeconds:true" value="" style="width:150px">&nbsp;-&nbsp;<input class="easyui-datetimebox" name="end_date" data-options="editable:true,showSeconds:true" value="" style="width:150px"></span>
				 <span class="lay_text_bor mr20">申请人姓名：<input class="easyui-textbox" style="width:150px;" name="user_name"/></span>
				 <span class="lay_text_bor mr20">申请人电话：<input class="easyui-textbox" style="width:150px;" name="b_mobile"/></span>
                 <span class="lay_text_bor mr20">位属服务商： <select id="settle_id" name="settle_id" class="easyui-combobox" data-options="valueField:'id'"style="width:150px" ></select></span>
	             <span class="lay_text_bor mr20">位属代理商：<select id="agent_id" name="agent_id" class="easyui-combobox" data-options="valueField:'id'" style="width:150px" ></select></span>
	             <span class="lay_text_bor mr20">角色：<input class="easyui-combobox" style="width:150px;" id="role_type" name="role_type"/></span>
				 <span class="lay_text_bor mr20">提现方式：<input class="easyui-combobox" style="width:150px;" id="tx_type" name="tx_type"/></span>
				 <span class="lay_text_bor mr10">订单号：<input class="easyui-textbox" style="width:150px;" name="tx_order_id"/></span>
				 <span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					<!--<span class="mr10"><a href="javascript:;" onclick="fan();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">调用返回接口</a></span>-->
				 </span>
				</form>
		</div>
		<div id="total" class="total"></div>
		</div>		
	</div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/report.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/platform/platform_refund_info.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript">
$('#is_use').combobox('setValue', '1');
</script>
</body>

</html>