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
				<span class="lay_text_bor mr20">品牌：<input class="easyui-combobox" style="width:150px;" id="brand_id" name="brand_id"/></span>
				<span class="lay_text_bor mr10">商户名称：<input class="easyui-textbox" style="width:150px;" name="merchant_name"/></span>
				<span class="lay_text_bor mr10">商户编号：<input class="easyui-textbox" style="width:150px;" name="merchant_code"/></span>
				<span class="lay_text_bor mr10">机具编号：<input class="easyui-textbox" style="width:150px;" name="machine_code"/></span>
				<span class="lay_text_bor mr10">日期选择：<input class="easyui-datebox" name="start_date" data-options="editable:false,showSeconds:true" style="width:150px;"id="skssj" />&nbsp;-
				<input class="easyui-datebox" name="end_date" data-options="editable:false,showSeconds:true" style="width:150px;" id="sjssj"/>
				</span>
				<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>

					</span>
			</form>
		</div>
	</div>

	<!-- 添加/修改表单开始 -->
	<div id="update" class="easyui-window" title="" style="width:900px;height:350px;padding:15px 13px 15px 30px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<form id="updateform" method="post" autocomplete="off" action-add="${pageContext.request.contextPath}/goods/insKsnInfo" action-edit="${pageContext.request.contextPath}/goods/modKsnInfo" data-autoform="
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
				<div title="机具" style="padding:20px">
					<table class="tables">
						<tr>
							<td>机具品牌：</td>
							<td><input class="easyui-combobox" style="width:150px;" name="brand" id="brand"  data-options="required:true"></td>
							<td>机具类型：</td>
							<td><input class="easyui-textbox" style="width:150px;"  id="type" name="type"  data-options="required:true"></td>
							<td>机具名称：</td>
							<td><input class="easyui-textbox" style="width:150px;" name="machine_name" data-options="required:true,validType:'length[1,10]'"></td>
						</tr>
						<tr>
							<td>机具编号：</td>
							<td><input class="easyui-textbox" style="width:150px;" name="machine_code" data-options="required:true,validType:'length[1,10]'"></td>
							<td>代理商商户：</td>
							<td><input class="easyui-combobox" style="width:150px;" name="agent_id" id="agent_id"  data-options="required:true"></td>
							<td>业务员：</td>
							<td><input class="easyui-textbox" style="width:150px;"  id="user_id" name="user_id"  data-options="required:true"></td>
						</tr>
						<tr>
							<td>激活状态：</td>
							<td><input class="easyui-combobox" style="width:150px;" id='activation_status' name='activation_status' data-options="required:true"></td>
							<td>使用状态：</td>
							<td><input class="easyui-textbox" style="width:150px;" id='usage_status' name="usage_status" data-options="required:true"></td>
							<td>分发状态：</td>
							<td><input class="easyui-textbox" style="width:150px;" id="handout_status" name="handout_status"  data-options="required:true"></td>
						</tr>
					</table>
				</div>
				<%--<div title="机具详情" style="padding:20px">--%>
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
			<input type="hidden" value="" id="id" name="id"/>
		</form>
	</div>
	<!-- 添加表单开始 -->
	<!-- 机具规格管理开始 -->
	<div id="goodsSpec" class="easyui-window" title="机具规格" style="width:700px;padding:0;height:450px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
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
				<form id="specform" method="post" autocomplete="off" action-add="${pageContext.request.contextPath}/goods/insKsnSpec" action-edit="${pageContext.request.contextPath}/goods/updKsnSpec" data-autoform="
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

							<td>使用状态：</td>
							<td><input class="easyui-combobox" id='usage_status' name='usage_status' data-options="required:true"></input></td>
							<td>激活状态：</td>
							<td><input class="easyui-textbox" style="width:150px;"  id="activation_status" name="activation_status"  data-options="required:true"></td>

						</tr>
						<tr>
							<td>分发状态：</td>
							<td><input class="easyui-textbox" style="width:150px;"  id="handout_status" name="handout_status"  data-options="required:true"></td>

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
					<input type="hidden" value="" id="id" name="id"/>
				</form>
			</div>
		</div>
	</div>
	<!-- 机具规格管理结束 -->
	
	
	<!-- 机具参数管理开始 -->
	<div id="goodsParam" class="easyui-window" title="机具参数" style="width:700px;padding:0;height:450px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
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
	<!-- 机具参数管理结束 -->
	
	<!-- 机具提成比率管理开始 -->
	<div id="goodsProrate" class="easyui-window" title="机具提成比率" style="width:700px;padding:0;height:450px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
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
	<!-- 机具提成比率管理结束 -->
	
	
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


<script>
    $(function() {
        var curr_time = new Date();
        var strDate = curr_time.getFullYear() + "";
        strDate += curr_time.getMonth()+1 + "";
        // strDate += curr_time.getDate()-1;
		strDate += curr_time.getDate()-1;

        var strendDate = curr_time.getFullYear() + "";
        strendDate += curr_time.getMonth()+1 + "";
        strendDate += curr_time.getDate()-1;

        $("#skssj").datebox("setValue", strDate);
        $("#sjssj").datebox("setValue", strendDate);

        var skssj = $('#skssj').datebox('getValue')
        var sjssj = $('#sjssj').datebox('getValue')
        loadData({order_type:queryDescOrAsc.create_time,start_date:skssj,end_date:sjssj});//初始化加载数据

	});
</script>

<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/report.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/platform/platform_merchat_info.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>