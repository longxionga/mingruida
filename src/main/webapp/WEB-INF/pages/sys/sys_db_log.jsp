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
				<span class="lay_text_bor mr20">操作分类：<input class="easyui-textbox" style="width:150px;" name="method_name"/></span>
				<span class="lay_text_bor mr20">关键参数：<input class="easyui-textbox" style="width:150px;" name="cq_params"/></span>
				<span class="lay_text_bor mr20">结果状态：<input class="easyui-textbox" style="width:150px;" name="is_ok"/></span>
				<span class="lay_text_bor mr20">结果信息：<input  class="easyui-textbox" style="width:150px;" name="action_message"/></span>
				<span class="lay_text_bor mr10">时间选择：<input class="easyui-datebox" name="begin_date" data-options="editable:false,showSeconds:false" style="width:150px;" />&nbsp;-&nbsp;<input class="easyui-datebox" name="end_date" data-options="editable:false,showSeconds:false,validType:['TimeCheck[\'begin_date\']']" style="width:150px;" /></span>			
				<span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					</span>
				</form>
			</div>
			<div class="total">
               	<span>当前系统版本：v${applicationScope.v}</span>
            </div>
		</div>
	</div>
	
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/sys/sys_db_log.js?v=${applicationScope.v}"></c:url>'></script>

</body>
</html>