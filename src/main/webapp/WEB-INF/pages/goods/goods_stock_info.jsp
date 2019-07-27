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
				   <span class="lay_text_bor mr10">商品名称：<input class="easyui-textbox" style="width:150px;" name="goods_name"/></span>
					<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
						
					</span>
				</form>
			</div>
		</div>

		<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" title="" style="width:900px;height:270px;padding:15px 13px 15px 30px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform" method="post" autocomplete="off" action-add="" action-edit="modGoodsStockInfo" data-autoform="
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
						<td>原始价格：</td>
						<td><input class="easyui-textbox" style="width:150px;"  name="original_price"  data-options="required:true,validType:['intOrFloat','length[1,10]']"></td>
						<td>优惠价格：</td>
						<td><input class="easyui-textbox" style="width:150px;" name="discounted_price" data-options="required:true,validType:['intOrFloat','length[1,10]']"></td>
						<td>是否可用：</td>
						<td><input class="easyui-combobox" style="width:150px;" id='is_use' name='is_use' data-options="required:true"></input></td>
					</tr>
				  	<tr>
				  		<td>总量：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="total_amount" name="total_amount"  data-options="required:true,validType:['int','length[1,10]']"></td>
				  	    <td>交易数量：</td>
						<td><input class="easyui-textbox" style="width:150px;" name="buy_amount" data-options="required:true,validType:['int','length[1,10]']"></td>
						<td>运费：</td>
						<td><input class="easyui-textbox" style="width:150px;" name="fare_price" data-options="required:true,validType:['intOrFloat','length[1,10]']"></td>
					</tr>
					<tr>
						<%--<td>是否支持活动：</td>--%>
						<%--<td><input class="easyui-combobox" style="width:150px;" id='is_activity' name='is_activity' data-options="required:true"></td>--%>
						<td>用户返利(%)：</td>
						<td><input class="easyui-textbox" style="width:150px;" name="rebate" id="rebate" data-options="required:true,validType:['int','length[1,10]']"></td>
						<td>运费规则：</td>
						<td><input class="easyui-textbox" style="width:150px;" name="fare_rule" id="fare_rule" data-options="required:true,validType:['intOrFloat','length[1,10]']"></td>
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
				<input type="hidden" value="" id="sku_id" name="sku_id"/>
				<input type="hidden" value="" id="spec_id" name="spec_id"/>
			</form>
		</div>

</div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/goods/goods_stock_info.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/report.js?v=${applicationScope.v}"></c:url>'></script>

</body>
</html>