<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${applicationScope.T}提现审批</title>
	<%@include file="../../../common/common.jsp"%>
</head>
<body>
	<table id="data"></table>
	<div style="display: none">
		<!-- 工具栏开始 -->
		<div id="toolbar">
			<div class="pt10 pr10 pl10">
				<form id="search" method="post">
				<span class="lay_text_bor mr20">部门：<input class="easyui-textbox" id="idept_id" name="dept_id" style="width:150px;" /></span>
				<span class="lay_text_bor mr20">部门等级：<input class="easyui-textbox" id="dept_type" name="dept_type" style="width:150px;" /></span>
				<span class="lay_text_bor mr20">审批状态：
					<select id="cc" class="easyui-combobox" name="is_status" style="width:150px;" data-options="editable:false">
					    <option value="0">待审核</option>
					    <option value="1">已审核</option>
					    <option value="2">完成</option>
					    <option value="3">审核打回</option>
					    <option value="0,1,2,3">---全部---</option>
					</select>
				</span>
				<span class="lay_text_bor mr20">姓名：<input class="easyui-textbox" name="tx_name" style="width:150px;" /></span>
				<span class="lay_text_bor mr20">申请额度：<input class="easyui-textbox" name="tx_money" style="width:150px;" /></span>
				<span class="lay_text_bor mr10">时间选择：<input class="easyui-datebox" name="begin_date" data-options="editable:false,showSeconds:false" style="width:150px;" />&nbsp;-&nbsp;<input class="easyui-datebox" name="end_date" data-options="editable:false,showSeconds:false,validType:['TimeCheck[\'begin_date\']']" style="width:150px;" /></span>			
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
<script type="text/javascript" src='<c:url value="/static/sys/sys_with_draw_approve.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>