<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${applicationScope.T}部门管理</title>
	<%@include file="../../../common/common.jsp"%>
	<script type="text/javascript">
     var dept_type="${deptType}";
   </script>
	
</head>
<body>
	<table id="data"></table>
	<div style="display: none">
		<!-- 工具栏开始 -->
		<div id="toolbar">
			<div class="pt10 pr10 pl10">
				<form id="search" method="post">
				   <span class="lay_text_bor mr20">编码 ：<input id="search_dept_id" class="easyui-textbox" style="width:150px;" name="dept_code" data-options="validType:['length[1,20]','unnormal']"/></span>
				   <span class="lay_text_bor mr10">名称：<input id="search_dept_name" class="easyui-textbox" style="width:150px;" name="dept_name" data-options="validType:['length[1,20]','unnormal']"/></span>
				   <input type="hidden" id="deptType" value="${deptType }"/>
					<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
						<!-- <span class="mr10"><a href="javascript:;" onclick="$('#search').form('clear');" id="reset1" class="easyui-linkbutton" style="width:80px;">重 置</a></span> -->
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					</span>
				</form>
			</div>
		</div>
		<!-- 添加/修改表单开始 -->
		
		<div id="update" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:595px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform" method="post" autocomplete="off" action-add="insOPDeptInfo" action-edit="updOPDeptInfo" data-autoform="
				callback:function(o,j){
				    if(o.success==true){
				      $.messager.alert('提示信息',o.msg,'info');
				      $('#update').window('close');
                      $('#data').treegrid('reload');
				   }else{
				      $.messager.alert('错误信息',o.msg,'error');
				   }
				}
				">
				  <input type="hidden" id="_dept_id" name="dept_id"/>
				  <input type="hidden" id="dept_parent_id" name="dept_parent_id"/>
				  <input type="hidden" id="dept_type" name="dept_type"/>
			</form>
		</div>
		
		<!-- 添加/修改表单开始比率模板 -->
		<div id="update1" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;height:381px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform1" method="post" autocomplete="off" action="updDeptInfo" data-autoform="
				callback:function(o,j){
				    if(o.success==true){
				      $.messager.alert('提示信息',o.msg,'info');
				      $('#update1').window('close');
                      $('#data').treegrid('reload');
				   }else{
				      $.messager.alert('错误信息',o.msg,'error');
				   }
				}
				">	
				  <input type="hidden" id="types" name="types"/>
				  <input type="hidden" id="opt_dept_id" name="opt_dept_id"/>
				  <input type="hidden" id="dept_parent_id" name="dept_parent_id"/>
				  <input type="hidden" id="opt_dept_type" name="opt_dept_type"/>
			</form>
		</div>
	
<div id="deptupdate" class="easyui-window" title="部门选择" closed="true"  style="width:700px;padding:0;height:450px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
<!-- 工具栏开始 -->
		<div id="toolbar1">
			<div style="padding-bottom:10px;">部门名称：<input id="dept_names" class="easyui-textbox" style="width: 150px;" name="dept_names" data-options="validType:'TextSearch'"/>		
			      <a href="javascript:;" onclick="getParams();" class="easyui-linkbutton" iconCls="icon-search">查询</a> 
			      <a href="javascript:;" id="refresh" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
			</div>	
		</div>
	<table id="data1"></table>
	<input type="hidden" id="odept_id" name="odept_id">
	<!-- <div stlye="height:20px">
	<a href="javascript:;" onclick="$('#updateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
	 -->
</div>		
	</div>
	
	<div id="txsh_detail_2" class="easyui-window" title="余额查看" closed="true" style="width:700px;padding:0;height:450px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<div id="toolbar02">
			<div id="total2" style="display: inline-block;"></div>
		</div>
    	<table id="data2" class="easyui-datagrid" title="详情列表"></table>    	
    </div>
    <div id="txsh_detail_3" class="easyui-window" title="经纪人余额查看" closed="true" style="width:700px;padding:0;height:450px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<div id="toolbar03">
			<div>
				<form id="" method="post">
						<span class="mr10"><a href="javascript:;" onclick='expBrokerInfo();'  class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-download'">导出</a></span>
				</form>
				<div id="total3" class="total"></div>
			</div>
		</div>
    	<table id="data3" class="easyui-datagrid" title="详情列表"></table>    
    </div>
    <div id="txsh_detail_4" class="easyui-window" title="用户余额查看" closed="true" style="width:700px;padding:0;height:450px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<div id="toolbar04">
			<div>
				<form id="" method="post">
						<span class="mr10"><a href="javascript:;" onclick='expUserInfo();'  class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-download'">导出</a></span>
				</form>
				<div id="total4" class="total"></div>
			</div>
		</div>
    	<table id="data4" class="easyui-datagrid" title="详情列表"></table>    
    </div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/sys/sys_op_dept_info.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>