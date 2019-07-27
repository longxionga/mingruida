<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${applicationScope.T}</title>
    <style>
    	.main-error-style-405{ margin:120px auto 0; width:396px; height:69px; background: url('${pageContext.request.contextPath}/static/images/error/405.png') no-repeat;}
    	.main-error-style-405 a{display: inline-block;margin: 47px 0 0 162px;color: #19c8be;text-decoration: none;font-size:14px;font-family: "Microsoft Yahei";}
    	.main-error-style-405 a:hover{color: #19c8be;text-decoration: none;}
    </style>
</head>
<body>
    <div class="page-header">
    	<div class="main-error-style-405">
    	<a href="${pageContext.request.contextPath}/main">返回首页 &nbsp;>></a>
    	</div>
    </div>
</body>
</html>
