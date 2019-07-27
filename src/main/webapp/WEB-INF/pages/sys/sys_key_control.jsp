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
	<div>
		<!-- 工具栏开始 -->
		<div id="toolbar">
			<div class="pt10 pr10 pl10">
				<form id="search" method="post">		  
					<div id="status" style="padding:10px 10px 20px;color: grey;font-size: 16px;border-botom:1px #e9e9e9 solid;"></div>
					<span class="lay_text_bor pl10">
						<span class="mr10"><a href="javascript:setKeys2();" class="easyui-linkbutton">开KEYS2</a></span>
						<span class="mr10"><a href="javascript:setKeys3('keys3');" class="easyui-linkbutton">开KEYS3</a></span>
						<span class="mr10"><a href="javascript:setKeys3('keys4');" class="easyui-linkbutton">开KEYS4</a></span>
					    <span class="mr10"><a href="javascript:del();" class="easyui-linkbutton">关闭</a></span>
					</span>
				</form>
			</div>
		</div>
		
		

		
		<!-- 添加表单开始 -->
	</div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/sys/sys_keys_control.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>