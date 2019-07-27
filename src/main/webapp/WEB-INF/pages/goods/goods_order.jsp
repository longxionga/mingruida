<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<%@include file="../../../common/common.jsp"%>
</head>
<body>
	<table id="data"></table>
	<div style="display: none">
		<!-- 工具栏开始 -->
		<div id="toolbar">
			<div class="pt10 pr10 pl10">
				<form id="search" method="post">
				<span class="lay_text_bor mr10">收货电话：<input class="easyui-textbox" style="width:150px;" name="receiver_mobile"/></span>
				<span class="lay_text_bor mr10">订单编码：<input class="easyui-textbox" style="width:150px;" name="order_code"/></span>
				<span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
				</span>
				</form>
			</div>
		</div>

		<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;height:212px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform" method="post" autocomplete="off" action-add="" action-edit="putInLogistics" data-autoform="
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
				  		<td>物流公司：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="logistics" name="logistics"  data-options="required:true,validType:['length[2,25]']"></td>
				  		
				  	    <td>物流单号：</td>
						<td><input class="easyui-textbox" style="width:150px;" id="logistics_no" name="logistics_no" data-options="required:true,validType:['length[2,25]']"></td>
				  	</tr>
					<tr>
						<td colspan="4" align="center">
							<div class="pt30">
								<a href="javascript:;" onclick="$('#updateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
								<a href="javascript:;" onclick="$('#updateform').form('clear');" id="reset" class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
							</div>
						</td>
					</tr>
				  </table>
				  <input type="hidden" value="" id="order_id" name="order_id"/>
			</form>
		</div>
		</div>
		<!-- 添加表单结束-->
	</div> 
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/common.js"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/goods/goods_order.js"></c:url>'></script>
</body>
</html>