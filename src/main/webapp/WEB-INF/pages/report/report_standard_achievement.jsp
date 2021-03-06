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


				<span class="lay_text_bor mr10">所属品牌：<input  id="brand_id" name="brand_id" style="width:150px;" /></span>

				<span class="lay_text_bor mr20">所属分公司：<input class="easyui-combobox" id="sdept_id" name="sdept_id" style="width:150px;"  data-options="valueField:'id',textField:'text'" /></span>

				<span class="lay_text_bor mr20">分公司主管：<input class="easyui-combobox" id="orgid" name="orgid" style="width:150px;"  data-options="valueField:'id',textField:'text'" /></span>

				<span class="lay_text_bor mr20">员工岗位：<input class="easyui-combobox" style="width:150px;" id="position" name="position"/></span>

				<%--<span class="lay_text_bor mr20">员工名称：<input class="easyui-textbox" style="width:150px;" name="staffname"/></span>--%>

				<br/>
				<span class="lay_text_bor mr10">日期选择：<input class="easyui-datebox" name="start_date" data-options="editable:false,showSeconds:true" style="width:150px;"id="skssj" />&nbsp;-
				<input class="easyui-datebox" name="end_date" data-options="editable:false,showSeconds:true" style="width:150px;" id="sjssj"/></span>
				<span class="lay_text_bor">

				<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
<%--
						<span class="mr10"><a class="btns" href="javascript:void(0);" onclick="upFiles();">上传员工信息</a></span>
--%>
<%--
                    	<span class="mr10"><a href="javascript:;" onclick="closeAll();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除所有机具</a></span>
--%>
                    	<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>

                </span>
				</span>
			</form>
		</div>
	</div>
</div>


<script>
	$(function() {
		// var curr_time = new Date();
		// var strDate = curr_time.getFullYear() + "";
		// strDate += curr_time.getMonth() + "";
		// strDate += curr_time.getDate()-1;
		//
		// var strendDate = curr_time.getFullYear() + "";
		// strendDate += curr_time.getMonth()+1 + "";
		// strendDate += curr_time.getDate()-1;
		// $("#skssj").datebox("setValue", strDate);
		// $("#sjssj").datebox("setValue", strendDate);
		//
		// var skssj = $('#skssj').datebox('getValue')
		// var sjssj = $('#sjssj').datebox('getValue')
		loadData();//初始化加载数据
	});
</script>
<script type="text/plain" id="myEditor"></script>
<script type="text/plain" id="upload_ue"></script>

<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/report3.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/report/report_standard_achievement.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>