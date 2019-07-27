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
				<span class="lay_text_bor mr10">订单编码：<input class="easyui-textbox" style="width:150px;" name="order_code"/></span>
				<span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
				</span>
				</form>
			</div>
		</div>

		<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;height:258px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform" method="post" autocomplete="off" action-add="" action-edit="updGoodsOrderRefund" data-autoform="
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
				  		<td>快递公司：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="logistics" name="logistics"  data-options="required:true,validType:['length[1,100]']"></td>
				  	    <td>快递单号：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="logistics_no" name="logistics_no"  data-options="required:true,validType:['length[1,150]']"></td>
				  	</tr> 	
				  	<tr>
				  		<td>退货金额：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="refund_money" name="refund_money"  data-options="required:true,validType:['length[1,100]']"></td>
				  	    <td>退货理由：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="refund_reason" name="refund_reason"  data-options="required:true,validType:['length[1,150]']"></td>
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
				  <input type="hidden" value="" id="refund_id" name="refund_id"/>
			</form>
		</div>
		
		<!-- 添加/修改表单开始 -->
		<div id="disupdate" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;height:314px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="disupdateform" method="post" autocomplete="off" action-add="disAgreeGoodsOrderRefund" action-edit="disAgreeGoodsOrderRefund" data-autoform="
				callback:function(o,j){
				    if(o.success==true){
				      $.messager.alert('提示信息',o.msg,'info');
				      $('#disupdate').window('close');
                      $('#data').datagrid('reload');
				   }else{
				      $.messager.alert('错误信息',o.msg,'error');
				   }
				}
				">
				  <table class="tables">
				  	<tr>
				  		<tr>
				  		<td style="vertical-align:top;">驳回原因：</td>
				  		<td colspan="3">
				  			<input id="reject_reason" class="easyui-textbox easyui-textbox-re" style="width:420px;height:200px" name="reject_reason" data-options="required:true,multiline:true,validType:['length[2,100]']">
				  		</td>
				  		</tr>
				  	</tr>				  
					<tr>
						<td colspan="4" align="center">
							<div class="pt30">
								<a href="javascript:;" onclick="$('#disupdateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
								<a href="javascript:;" onclick="$('#disupdateform').form('clear');" id="reset" class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
							</div>
						</td>
					</tr>
				  </table>
				  <input type="hidden" value="" id="refund_id" name="refund_id"/>
			</form>
		</div>
		</div>
		<!-- 添加表单结束-->
	</div> 
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/common.js"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/goods/goods_order_refund.js"></c:url>'></script>
</body>
</html>