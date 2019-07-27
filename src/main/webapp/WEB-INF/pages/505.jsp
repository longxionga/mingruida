<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${applicationScope.T}</title>
    <style>
    	body{background-color:#f2f2f2;}
    	.main-error-style-505{ margin:120px auto 0; width:396px; height:87px; background: url('${pageContext.request.contextPath}/static/images/error/505.png') no-repeat;}
    	.main-error-style-505 p{margin:0;padding:0;letter-spacing: 3px;}
    	.main-error-style-505 .title{padding:17px 0 4px 194px;color: #474747;font-size:24px;font-family: "Microsoft Yahei";}
    	.main-error-style-505 .cont{padding-left: 196px;color: #474747;font-size:16px;font-family: "Microsoft Yahei";}
    </style>
</head>
<body>
    <div class="page-header">
    	<div class="main-error-style-505">
    		<p class="title">系统结算中...</p>
    		<p class="cont">早4:00-早7:00</p>
    	</div>
    </div>
</body>