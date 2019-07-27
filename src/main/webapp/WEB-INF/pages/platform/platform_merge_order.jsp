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
                  <span class="lay_text_bor mr10">名称：
					   <input class="easyui-textbox" style="width: 150px;" name="name" /></span>
					<span class="lay_text_bor mr10">昵称：
					   <input class="easyui-textbox" style="width: 150px;" name="nick_name" /></span>
                    <span class="lay_text_bor mr10">手机号：
					   <input class="easyui-textbox" style="width: 150px;" name="mobile" /></span>
                    <span class="lay_text_bor">
					    <span class="mr10">
						<a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
                        <span class="mr10">
						<a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
                    </span>
            </form>
        </div>
    </div>

    <!-- 订单详情开始 -->
    <div id="orderupdate" class="easyui-window" title="订单详情" closed="true"
         style="width: 700px; padding: 0; height: 450px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">

        <div id="toolbar3">
            <div style="border-top: 1px #ddd solid;padding-top:10px;">
                <a href="javascript:getSelected1();" class="easyui-linkbutton"
                   data-options="plain:true,iconCls:'icon-add'">保存</a>
                <a href="javascript:;" id="refresh" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
            </div>
        </div>
        <table id="order"></table>
        <input type="hidden" id="pay_id" name="pay_id">
    </div>
</div>



<!-- 订单详情结束 -->
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/platform/platform_merge_order.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>