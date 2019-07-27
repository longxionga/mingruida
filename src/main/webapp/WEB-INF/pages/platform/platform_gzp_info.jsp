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
				   <span class="lay_text_bor mr10">商品名称：<input class="easyui-textbox" style="width:150px;" name="gzp_name"/></span>
					<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
						<span class="mr10"><a href="javascript:InitInfo();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">初始化商品</a></span>
						<span class="mr10"><a href="javascript:InitSimInfo();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">初始化模拟盘</a></span>
						<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新增</a></span>
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
						
					</span>
				</form>
			</div>
		</div>

		<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" title="" style="width:900px;height:427px;padding:15px 13px 15px 30px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform" method="post" autocomplete="off" action-add="${pageContext.request.contextPath}/platform/insGzpInfo" action-edit="${pageContext.request.contextPath}/platform/updGzpInfo" data-autoform="
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
				  		<td>商品名称：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="gzp_name" name="gzp_name"  data-options="required:true,validType:'length[1,20]'"></td>
				  	    <td>商品标号：</td>
						<td><input class="easyui-textbox" style="width:150px;" name="gzp_code" data-options="required:true,validType:'length[1,10]'"></td>
				  	    <td>是否可用：</td>
					    <td><input class="easyui-combobox" style="width:150px;" id='is_use' name='is_use' data-options="required:true"></input></td>
				  	</tr>
				  	
				  	<tr>
						
						<td>商品数型：</td>
						<td><input class="easyui-textbox" style="width:150px;" name="gzp_number" data-options="required:true,validType:['int','length[1,2]']"></td>
						<td>商品排序：</td>
						<td><input class="easyui-textbox" style="width:150px;" name="gzp_order" data-options="required:true,validType:['int','length[1,10]']"></td>
						<td>商品单位：</td>
						<td><input class="easyui-textbox" style="width:150px;" name="gzp_unit" data-options="required:true,validType:'length[1,10]'"></td>
					</tr>					
					<tr>
					    <td>商品类型：</td>
					    <td><input class="easyui-textbox" style="width:150px;" name="gzp_type" data-options="required:true,validType:'length[1,5]'"></td>
					    <td>商品价格：</td>
					    <td><input class="easyui-textbox" style="width:150px;" name="gzp_money" data-options="required:true,validType:['intOrFloat','length[1,10]']"></td>
					       <td>商品仓储费：</td>
					    <td><input class="easyui-textbox" style="width:150px;" name="gzp_poundage" data-options="required:true,validType:['intOrFloat','length[1,10]']"></td>
				    </tr>
				    <tr>					 
					    <td>波动盈亏：</td>
					    <td><input class="easyui-textbox" style="width:150px;" name="gzp_profit" data-options="required:true,validType:['intOrFloat','length[1,5]']"></td>
					       <td>商品手数：</td>
					    <td><input class="easyui-textbox" style="width:150px;" name="gzp_lot" data-options="required:true,validType:['int','length[1,5]']"></td>
					    <td>商品别名：</td>
				  		<td><input class="easyui-textbox" style="width:150px;" name="gzp_alias"  data-options="required:true,validType:'length[1,100]'"></td>			
				    </tr>
				    <tr>
					    <td>是否支持众筹：</td>
					    <td colspan="5"><input class="easyui-combobox" style="width:150px;" id='is_zc' name='is_zc' data-options="required:true"></input><td>
					    
				    </tr>			   
				    <tr>
					    <td>商品非交易日期：</td>
					    <td colspan="5"><input class="easyui-textbox" style="width:690px;" name="gzp_buy_no_date" data-options="required:true,validType:'length[1,200]'"></td>
					    
				    </tr>
				     <tr>
					    <td>商品非交易时间：</td>
					    <td colspan="5"><input class="easyui-textbox" style="width:690px;" name="gzp_buy_no_time" data-options="required:true,validType:'length[1,200]'"></td>
					    
				    </tr>
					<tr>
						<td colspan="6" align="center">
							<div class="pt15">
							<a href="javascript:;" onclick="$('#updateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
							<a href="#" id="reset" class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
							</div>
						</td>
					</tr>				  
				  </table>
				  <input type="hidden" value="" id="gzp_id" name="gzp_id"/>
			</form>
		</div>
		<!-- 添加表单开始 -->
	
	<div id="deptupdate" class="easyui-window" title="部门选择" closed="true"  style="width:700px;padding:0;height:450px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
<!-- 工具栏开始 -->
		<div id="toolbar1">
			<div style="padding-bottom: 10px;">部门名称：<input id="dept_names" class="easyui-textbox" style="width: 150px;" name="dept_names" data-options="validType:'TextSearch'"/>		
			      <span class="mr10"><a href="javascript:;" onclick="getParams();" class="easyui-linkbutton" iconCls="icon-search">查询</a> 
			      <a href="javascript:;" id="refresh" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
			</div>
			<div style="border-top: 1px #ddd solid; padding-top: 10px;">
				<a href="javascript:getSelected();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">保存</a>				
			</div>
		</div>
	<table id="data1"></table>
	<input type="hidden" id="hgzp_id" name="hgzp_id">
	<!-- <div stlye="height:20px">
	<a href="javascript:;" onclick="$('#updateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
	 -->
</div>

</div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/platform/platform_gzp_info.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>