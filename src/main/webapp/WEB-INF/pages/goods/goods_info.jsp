<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${applicationScope.T}</title>
	<%@include file="../../../common/common.jsp"%>
	
	<style type="text/css">
		/* .tabs-header{padding-bottom:10px;} */
	</style>
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
						<!--<span class="mr10"><a href="javascript:InitInfo();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">初始化商品</a></span>-->
						<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新增</a></span>
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>

					</span>
			</form>
		</div>
	</div>

	<!-- 添加/修改表单开始 -->
	<div id="update" class="easyui-window" title="" style="width:900px;height:350px;padding:15px 13px 15px 30px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<form id="updateform" method="post" autocomplete="off" action-add="${pageContext.request.contextPath}/goods/insGoodsInfo" action-edit="${pageContext.request.contextPath}/goods/modGoodsInfo" data-autoform="
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
			<!-- <div style="margin:10px 0 10px 0;"></div> -->
			<%--<div class="easyui-tabs">--%>
				<div title="商品" style="padding:20px">
					<table class="tables">
						<tr>
							<td>商品分类：</td>
							<td><input class="easyui-combobox" style="width:150px;" name="category_id" id="category_id"  data-options="required:true"></td>
							<td>商品名称：</td>
							<td><input class="easyui-textbox" style="width:150px;"  id="goods_name" name="goods_name"  data-options="required:true,validType:'length[1,50]'"></td>
							<td>商品标号：</td>
							<td><input class="easyui-textbox" style="width:150px;" name="goods_code" data-options="required:true,validType:'length[1,10]'"></td>
						</tr>
						<tr>
							<td>是否可用：</td>
							<td><input class="easyui-combobox" style="width:150px;" id='is_use' name='is_use' data-options="required:true"></td>
							<td>商品标签：</td>
							<td><input class="easyui-textbox" style="width:150px;" name="goods_label" data-options="required:true,validType:['intOrFloat','length[1,5]']"></td>
							<td>商品别名：</td>
							<td><input class="easyui-textbox" style="width:150px;" name="goods_alias"  data-options="required:true,validType:'length[1,50]'"></td>
						</tr>
						<td>商品描述：</td>
						<td colspan="3"><input class="easyui-textbox" style="width:380px;" id='description' name='description' data-options="required:true"></td>
					</table>
				</div>
				<%--<div title="商品详情" style="padding:20px">--%>
					<%--<table class="tables">--%>
						<%--<tr>--%>
							<%--<td>单位：</td>--%>
							<%--<td><input class="easyui-textbox" style="width:150px;"  id="unit" name="unit"  data-options="required:true"></td>--%>
						<%--</tr>--%>
						<%--<tr>--%>
							<%--<td>是否可用：</td>--%>
							<%--<td><input class="easyui-combobox"  name='item_is_use'  id='item_is_use' data-options="required:true"></td>--%>
						<%--</tr>--%>
						<%--<tr>--%>
							<%--<td>详情描述：</td>--%>
							<%--<td colspan="3"><input class="easyui-textbox" style="width:380px;" id='description' name='description' data-options="required:true"></td>--%>
						<%--</tr>--%>
					<%--</table>--%>
				<%--</div>--%>
			<%--</div>--%>
			<table class="tables">
				<tr>
					<td align="center">
						<div class="pt15">
							<a href="javascript:;" onclick="$('#updateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
							<a href="#" id="reset" class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
						</div>
					</td>
				</tr>
			</table>
			<input type="hidden" value="" id="goods_id" name="goods_id"/>
			<input type="hidden" value="" id="spec_id" name="spec_id"/>
		</form>
	</div>
	<!-- 添加表单开始 -->
	<!-- 商品规格管理开始 -->
	<div id="goodsSpec" class="easyui-window" title="商品规格" style="width:700px;padding:0;height:450px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<table id="spec"></table>
		<div style="display: none">
			<!-- 工具栏开始 -->
			<div id="toolbarSpec">
				<div>
					<form id="searchSpec" method="post">
				<span>
					<span class="mr10"><a href="javascript:updateSpec();" class="easyui-linkbutton mr10" data-options="plain:true,iconCls:'icon-add'">新增</a></span>
					<!--<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>-->
				</span>
					</form>
				</div>
			</div>

			<!-- 添加/修改表单开始 -->
			<div id="updGoodsSpec" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;height:258px;"  data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
				<form id="specform" method="post" autocomplete="off" action-add="${pageContext.request.contextPath}/goods/insGoodsSpec" action-edit="${pageContext.request.contextPath}/goods/updGoodsSpec" data-autoform="
				callback:function(o,j){
				    if(o.success==true){
				      $.messager.alert('提示信息',o.msg,'info');
				      $('#updGoodsSpec').window('close');
                      $('#spec').datagrid('reload');
                      $('#data').datagrid('reload');
				   }else{
				      $.messager.alert('错误信息',o.msg,'error');
				   }
				}
				">
					<table class="tables">
						<tr>

							<td>是否可用：</td>
							<td><input class="easyui-combobox" id='is_uses' name='is_uses' data-options="required:true"></input></td>
							<td>规格名称：</td>
							<td><input class="easyui-textbox" style="width:150px;"  id="spec_names" name="spec_names"  data-options="required:true,validType:['specName[\'specValidate\']','length[1,25]']"></td>

						</tr>
						<tr>
							<td>规格排序：</td>
							<td><input class="easyui-textbox" style="width:150px;"  id="spec_order" name="spec_order"  data-options="required:true,validType:['int','length[1,2]']"></td>

						</tr>
						<tr>
							<td colspan="4" align="center">
								<div class="pt15">
									<a href="javascript:;"  onclick="$('#specform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
									<a href="javascript:;" onclick="$('#specform').form('clear');" id="reset1" class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
								</div>
							</td>
						</tr>
					</table>
					<input  type="hidden"  id="goods_ids" name="goods_ids"/>
					<input type="hidden" value=""  id="spec_ids" name="spec_ids"/>
				</form>
			</div>
		</div>
	</div>
	<!-- 商品规格管理结束 -->
	
	
	<!-- 商品参数管理开始 -->
	<div id="goodsParam" class="easyui-window" title="商品参数" style="width:700px;padding:0;height:450px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<table id="param"></table>
		<div style="display: none">
			<!-- 工具栏开始 -->
			<div id="toolbarParam">
				<div>
					<form id="searchParam" method="post">
				<span>
					<span class="mr10"><a href="javascript:updateParam();" class="easyui-linkbutton mr10" data-options="plain:true,iconCls:'icon-add'">新增</a></span>
				</span>
					</form>
				</div>
			</div>

			<!-- 添加/修改表单开始 -->
			<div id="updGoodsParam" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;height:258px;"  data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
				<form id="paramform" method="post" autocomplete="off" action-add="${pageContext.request.contextPath}/goods/insGoodsParam" action-edit="${pageContext.request.contextPath}/goods/updGoodsParam" data-autoform="
				callback:function(o,j){
				    if(o.success==true){
				      $.messager.alert('提示信息',o.msg,'info');
				      $('#updGoodsParam').window('close');
                      $('#param').datagrid('reload');
				   }else{
				      $.messager.alert('错误信息',o.msg,'error');
				   }
				}
				">
					<table class="tables">
						<tr>																						 
					  		<td>参数名称：</td>
					  		<td><input class="easyui-textbox" style="width:150px;"  id="param_names" name="param_names"  data-options="required:true,validType:['paramName[\'paramValidate\']','length[2,25]']"></td>
					  		
					  	    <td>参数值：</td>
							<td><input class="easyui-textbox" style="width:150px;" id="param_values" name="param_values" data-options="required:true"></td>
					  	</tr>
					  	
						<tr>						
							<!--<td>状态</td>
							<td><input class='easyui-textbox' style="width:150px;" data-options="required:true,validType:'int'" id="param_status" name="param_status"></td>
						 	-->
						 	<td>是否可用：</td>
							<td><input class="easyui-combobox" id='param_is_use' name='param_is_use' data-options="required:true"></input></td>
						</tr>
						<tr>
							<td colspan="4" align="center">
								<div class="pt15">
									<a href="javascript:;"  onclick="$('#paramform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
									<a href="javascript:;" onclick="$('#paramform').form('clear');" id="reset2" class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
								</div>
							</td>
						</tr>
					</table>
					<input  type="hidden" id="param_goods_id" name="param_goods_id"/>
				<input type="hidden" value=""  id="param_id" name="param_id"/>
				</form>
			</div>
		</div>
	</div>
	<!-- 商品参数管理结束 -->
	
	<!-- 商品提成比率管理开始 -->
	<div id="goodsProrate" class="easyui-window" title="商品提成比率" style="width:700px;padding:0;height:450px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<table id="prorate"></table>
		<div style="display: none">
			<!-- 工具栏开始 -->
			<div id="toolbarProrate">
				<div>
					<form id="searchProrate" method="post">
				<span>
					<span class="mr10"><a href="javascript:updateProrate();" class="easyui-linkbutton mr10" data-options="plain:true,iconCls:'icon-add'">新增</a></span>
				</span>
					</form>
				</div>
			</div>

			<!-- 添加/修改表单开始 -->
			<div id="updGoodsProrate" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;height:258px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
				<form id="prorateform" method="post" autocomplete="off" action-add="${pageContext.request.contextPath}/goods/insGoodsProrate" action-edit="${pageContext.request.contextPath}/goods/updGoodsProrate" data-autoform="
				callback:function(o,j){
				    if(o.success==true){
				      $.messager.alert('提示信息',o.msg,'info');
				      $('#updGoodsProrate').window('close');
                      $('#prorate').datagrid('reload');
				   }else{
				      $.messager.alert('错误信息',o.msg,'error');
				   }
				}
				">
					<table class="tables">
						<tr>
				  		
				  		<td>等级名称：</td>
				  		<td><input class="easyui-combobox" id="level_id" name="level_id"  data-options="required:true,validType:['prorateName[\'prorateValidate\']']"></input></td>
				  		
				  	</tr>
				 
				  	
					<tr>
				  	    <td>百分比(%)：</td>
						<td><input class="easyui-textbox" style="width:150px;" id="g_allot" name="g_allot" data-options="required:true,validType:'length[1,10]',validType:'int'"></td>
				  	
					 	<td>是否可用：</td>
						<td><input class="easyui-combobox" id='prorate_is_use' name='prorate_is_use' data-options="required:true"></input></td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<div class="pt30">
								<a href="javascript:;" onclick="$('#prorateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
								<a href="javascript:;" onclick="$('#prorateform').form('clear');" id="resetProrate" class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
							</div>
						</td>
					</tr>
				  </table>
				   <input type="hidden" value=""  id="goods_prorate_id" name="goods_prorate_id"/>
				   <input type="hidden" value=""  id="prorate_goods_id" name="prorate_goods_id"/>
				</form>
			</div>
		</div>
	</div>
	<!-- 商品提成比率管理结束 -->
	
	
	<div id="deptupdate" class="easyui-window" title="部门选择" closed="true"  style="width:700px;padding:0;height:450px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<!-- 工具栏开始 -->
		<div id="toolbar1">
			<div style="padding-bottom: 10px;">部门名称：<input id="dept_names" class="easyui-textbox" style="width: 150px;" name="dept_names" data-options="validType:'TextSearch'">
				<span class="mr10"><a href="javascript:;" onclick="getParams();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
					  <a href="javascript:;" id="refresh" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
			</div>
			<div style="border-top: 1px #ddd solid; padding-top: 10px;">
				<a href="javascript:getSelected();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">保存</a>
			</div>
		</div>
		<table id="data1"></table>
		<input type="hidden" id="hgoods_id" name="hgoods_id">
		<input type="hidden" id="hspec_id" name="hspec_id">
		<!-- <div stlye="height:20px">
        <a href="javascript:;" onclick="$('#updateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
         -->
	</div>

</div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/goods/goods_info.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>