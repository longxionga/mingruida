<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${applicationScope.T}</title>
    <style>
    	.main-error-style-403{ margin:120px auto 0; width:396px; height:69px; background: url('${pageContext.request.contextPath}/static/images/error/403_.png') no-repeat;}
    	.main-error-style-403 a{display: inline-block;margin: 47px 0 0 162px;color: #19c8be;text-decoration: none;font-size:14px;font-family: "Microsoft Yahei";}
    	.main-error-style-403 a:hover{color: #19c8be;text-decoration: none;}
    </style>
    <script type="text/javascript"> 
	var i = 3; 
	var intervalid;
	i--;
	intervalid = setInterval("fun()", 1000); 
	function fun() { 
		if (i == -1) { 
			window.location.href = "${pageContext.request.contextPath}/login"; 
			clearInterval(intervalid);
			i=0;
		} 
		document.getElementById("mes").innerHTML = i; 
		i--; 
	} 
</script> 
</head>
<body>
    <div class="page-header">
    	<div class="main-error-style-403">
    		<a href="${pageContext.request.contextPath}/login">请登录 <span id="mes">3</span>&nbsp;>></a>
    	</div>
    </div>
</body>
</html>
