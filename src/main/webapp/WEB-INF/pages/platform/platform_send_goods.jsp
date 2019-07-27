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
                <span class="lay_text_bor mr10">状态：<input class="easyui-textbox" style="width:150px;" id="status" name="status"/></span>
                <span class="lay_text_bor mr20">创建时间：<input class="easyui-datetimebox" name="start_date" data-options="editable:true,showSeconds:true" value="" style="width:150px">&nbsp;-&nbsp;<input class="easyui-datetimebox" name="end_date" data-options="editable:true,showSeconds:true" value="" style="width:150px"></span>
                <span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
				</span>
            </form>
        </div>
    </div>
</div>
<!-- 添加表单结束-->
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/common.js"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/platform/platform_send_goods.js"></c:url>'></script>
</body>
</html>