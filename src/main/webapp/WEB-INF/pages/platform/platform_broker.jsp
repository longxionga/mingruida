<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${applicationScope.T}</title>
	<%@include file="../../../common/common.jsp"%>
	<script type="text/javascript">
     var dept_type="${dept_type}";
     var agent_id ="${agent_id}";
    </script>
</head>
<body>
	<table id="data"></table>
	<div style="display: none">
		<!-- 工具栏开始 -->
		<div id="toolbar">
			<div class="pt10 pr10 pl10">
				<form id="search" method="post">
				   <span class="lay_text_bor mr10">经纪人：<input class="easyui-textbox" style="width:150px;" name="broker_name" data-options="validType:'TextSearch'"/></span>
				   <span class="lay_text_bor mr10">手机号：<input class="easyui-textbox" style="width:150px;" name="broker_mobile" data-options="validType:'mobile'"/></span>  
				   <span class="lay_text_bor mr20">位属服务商：<input  class="easyui-combobox" style="width:150px;" id="settle_id" name="settle_id" data-options="valueField:'id',textField:'text'"/></span>
				   <span class="lay_text_bor mr20">位属代理商：<input class="easyui-combobox" id="agent_id" name="agent_id" style="width:150px;" data-options="valueField:'id',textField:'text'"/></span>
				   <span class="lay_text_bor mr20">位属部门：<input class="easyui-combobox" id="DID" name='DID' style="width:150px;" data-options="valueField:'id',textField:'text'" /></span>
				    <%--<span class="lay_text_bor mr20">代理商等级：<input class="easyui-combobox" id="broker_level" name='broker_level' style="width:150px;"  /></span>--%>
					<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
						
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					</span>
				</form>
			</div>
		</div>
		
			   
	   <!-- 代理商所属部门下的所有代理商 -->
	    <div id = "broker_id" class="easyui-window" title="更换所属代理商" style="width: 700px; padding: 0; height: 450px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<div id="toolbar1">
				<div>
					<form id="search1" method="post">
						<input id="dataID" type="hidden" name="broker_id" value="">
						姓名：<input id="broker_name" class="easyui-textbox" style="width: 150px;" name="broker_name" data-options="validType:'TextSearch'"/> 
						手机号：<input id="broker_mobile" class="easyui-textbox" style="width: 150px;" name="broker_mobile"  data-options="validType:'mobile'"/> 
							<a href="javascript:;" onclick="$('#search1').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a> 
							<a href="javascript:;" id="refresh" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
					</form>
				</div>
				
			</div>
			<table id="broker"></table>
		</div> 
		
	</div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/platform/platform_broker.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/report.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>