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
				<span class="lay_text_bor mr10">商品名称：<input class="easyui-textbox" style="width:150px;" name="good_name"/></span>
				<span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton mr10" data-options="plain:true,iconCls:'icon-add'">新增</a></span>
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
				</span>
				</form>
			</div>
		</div>

		<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,closed:!0">
			<form id="updateform" method="post" autocomplete="off" action-add="insGoodsItem" action-edit="updGoodsItem" data-autoform="
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
						<td><input class="easyui-combobox" id="goods_id" name="goods_id" data-options="required:true"></td>
				  	</tr>
				  	<tr>																						 
				  		<td>单位：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="unit" name="unit"  data-options="required:true"></td>
				  	</tr>
					<tr>						
						<td>状态</td>
						<td><input class='easyui-textbox' style="width:150px;" data-options="required:true,validType:'int'" id="item_label" name="item_label"></td>
					 	<td>是否可用：</td>
						<td><input class="easyui-combobox" id='is_use' name='is_use' data-options="required:true"></td>
					</tr>
					<tr>
						<td>详情描述：</td>
						  <td colspan="3"><input class="easyui-textbox" style="width:400px;" id='description' name='description' data-options="required:true"></td>
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

			</form>
		</div>
		</div>
		<!-- 添加表单结束-->
	</div> 
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/common.js"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/goods/goods_item.js"></c:url>'></script>
</body>
</html>