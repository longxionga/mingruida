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
					 <span class="lay_text_bor mr20">订单ID：<input class="easyui-textbox" style="width:150px;" name="order_id"/></span>
				   <span class="lay_text_bor mr20">用户名：<input class="easyui-textbox" style="width:150px;" name="user_name"/></span>
				   <span class="lay_text_bor mr10">时间选择：<input class="easyui-datetimebox" name="start_date" data-options="editable:false,showSeconds:true" style="width:180px;" />&nbsp;-
				   <input class="easyui-datetimebox" name="end_date" data-options="editable:false,showSeconds:true" style="width:180px;" /></span>			
				   <span class="lay_text_bor mr20">订单状态：<input class="easyui-combobox" style="width:150px;" id="refund_status" name="refund_status" data-options="valueField:'id',textField:'text'"/></span>
					<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					</span>
				</form>
			</div>
		</div>

		<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;height:289px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform" method="post" autocomplete="off" action-add="disagreeShoppingRefund" action-edit="disagreeShoppingRefund" data-autoform="
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
				  		<td style="vertical-align:top;">原因：</td>
				  		<td colspan="3">
				  			<input id="reject_reason" class="easyui-textbox easyui-textbox-re" style="width:443px;height:200px" name="reject_reason" data-options="required:true,multiline:true,validType:['length[2,100]']">
				  		</td>
				  		</tr>
					
					<input type="hidden" value="" id="shopping_order_id" name="shopping_refund_id" />
					<tr>
						<td colspan="4" align="center">
							<div class="pt15">
							<a href="javascript:;" onclick="$('#updateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
							<a href="#" id="reset" class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
							</div>
						</td>
					</tr>				  
				  </table>
				  				  
			</form>
		</div>
		<!-- 添加表单开始 -->
	</div>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/report/report_shopping_refund.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>