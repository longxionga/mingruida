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
				   <span class="lay_text_bor mr10">部门名称：<input class="easyui-textbox" style="width:150px;" name="dept_name"/></span>
					<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					
						<c:if test="${deptType==4}">
						<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新增部门</a></span>
						</c:if>
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					</span>
				</form>
			</div>
		</div>
		<!-- 设置代理商部门 -->
     	<div id = "broker_dept" class="easyui-window" title="设置代理商部门" style="width: 700px; padding: 0; height: 450px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<div id="toolbar1">
				<div style="padding-bottom: 10px;">
					<form id="search1" method="post">
						姓名：<input id="broker_name" class="easyui-textbox" style="width: 150px;"
							name="broker_name" /> 
							<input id="DID" type="hidden" style="width: 150px;"
							name="dept_id" /> 
							<a href="javascript:;"
							onclick="$('#search1').submit();" class="easyui-linkbutton"
							iconCls="icon-search">查询</a> 
							<a href="javascript:;" id="refresh" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
							<!-- <a href="javascript:;"
							onclick="$('#broker').datagrid('reload');"
							class="easyui-linkbutton"
							data-options="plain:true,iconCls:'icon-reload'">刷新</a> -->
					</form>
				</div>
				<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;height:200px" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform" method="post" autocomplete="off" action-add="${pageContext.request.contextPath}/platform/insAgentDept" action-edit="${pageContext.request.contextPath}/platform/updAgentDept" data-autoform="
				callback:function(o,j){
				    if(o.success==true){
				      $.messager.alert('提示信息',o.msg,'info');
				      $('#update').window('close');
                      $('#data').datagrid('reload');
				   }else{
				      $.messager.alert('错误信息',o.msg,'error');
				   }
				}
				">
				  <table class="tables">
					<tr>
						<td>部门名称：</td>
						<td><input class="easyui-textbox" style="width: 150px;"
							id="dept_name" name="dept_name"
							data-options="required:true,validType:['length[2,50]']"></td>
						<td>联系电话：</td>
						<td><input class="easyui-textbox" style="width: 150px;"
							id="dept_mobile" name="dept_mobile"
							data-options="required:true,validType:['mobile']"></td>
					</tr>						
					<tr>
						<td colspan="4" align="center">
							<div class="pt15">
							<a href="javascript:;" onclick="$('#updateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
							<a href="javascript:;" onclick="$('#updateform').form('clear');" id="reset" class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
							</div>
						</td>
					</tr>				  
				  </table>
				  		<input  type="hidden" name="dept_id"/>		  
			</form>
		</div>
		<!-- 添加表单结束 -->
				<div style="border-top: 1px #ddd solid; padding-top: 10px;">
					<a href="javascript:;" class="easyui-linkbutton"
						onclick="transferUser();"
						data-options="plain:true,iconCls:'icon-ok'">确认选择</a>
				</div>
			</div>
			<table id="broker"></table>
		</div> 
	</div>
	<script type="text/javascript">
	var deptType="${deptType}";
	var kflag = false;
	var deptNameVal;
	
	$(document).on("keydown",$("#dept_name").next().children(":text"),function() {
		if(kflag){
	        e.preventDefault();
	    }else{
			var $this = $("#dept_name").next().children(":text");
			deptNameVal=$this.val();
	        kflag = true;
	    }
	});
	$(document).on("keyup",$("#dept_name").next().children(":text"),function() {
	    var $this = $("#dept_name").next().children(":text");
	    if ($this.val().indexOf(" ") != -1&&$this.val()!="") {
	    	$this.val(deptNameVal);
	    }
	    kflag = false;
	});
	</script>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/platform/platform_agent_dept.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>