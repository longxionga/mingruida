<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${applicationScope.T}</title>
	<%@include file="../../../common/common.jsp"%>
	<%@include file="../../../common/upload.jsp"%>

</head>
<body>
<table id="data"></table>

<div style="display: none">
	<!-- 工具栏开始 -->
	<div id="toolbar">

		<div class="pt10 pr10 pl10">
			<form id="search" method="post">
				<%--<span class="lay_text_bor mr10">所属品牌：<input  id="brindnameid" name="brindnameid" data-options="valueField:'id',textField:'text'" style="width:150px;" /></span>--%>
				<span class="lay_text_bor mr20">批次号：<input class="easyui-textbox" style="width:150px;" id="batchcode" name="batchcode"/></span>
<%--				<span class="lay_text_bor mr20">机具编号：<input class="easyui-textbox" style="width:150px;" id="machineSN" name="machineSN"/></span>--%>
				<span class="lay_text_bor mr20">机具序列号：<input class="easyui-textbox" style="width:150px;" id="machinecode" name="machinecode"/></span>
				<span class="lay_text_bor mr20">归属员工：<input class="easyui-textbox" id="staffname111" style="width:150px;" name="staffname" /></span>

				<span class="lay_text_bor mr20">是否绑定：<input class="easyui-combobox" id="isbound" style="width:150px;" name="isbound" /></span>
			<br/>

				<span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="allocation();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-ok'">机具调拨</a></span>
					<span class="mr10"><a href="javascript:;" onclick="exportExcel();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-ok'">导出Excel</a></span>
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>

                </span>
			</form>
		</div>
	</div>

	<!-- 机具调拨/回调表单开始 -->
	<div id="update" class="easyui-window" title="" style="padding:15px 13px 70px 30px;width:650px;height:550px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<form id="updateform" method="post" enctype="multipart/form-data"  autocomplete="off" action-add="machineAllocation" action-edit="machineCallback" data-autoform="
				callback:function(o,j){
				    if(o.success==true){
				      $.messager.alert('提示信息',o.msg,'info');
				      $('#update').window('close');
                      $('#data').treegrid('reload');
				   }else{
				      $.messager.alert('错误信息',o.msg,'error');
				   }
				}
				" >
			<div class=""  style="width:100%;height:40px;background: #e2e3e3; line-height: 40px;font-size: 15px" >

					<span class="lay_text_bor mr20">机具调拨列表</span>

			</div>
			<table class="tables">
				<%--<tr >
					<td  colspan="2"><input class="easyui-textbox" label="" value="终端调拨列表" labelPosition="top" multiline="true" style="width:100%;height:40px"></td>
				</tr>
--%>
				<tr >
					<td id="db_id">调拨方式：</td>
					<td><input class="easyui-textbox" style="width:300px;"  id="allocation_mode" name="allocation_mode"  data-options="required:true,valueField:'id',textField:'text'"></td>
				</tr>

				<tr id="t_id11"  >
					<td  colspan="2"><input class="easyui-textbox" label="" labelPosition="top" multiline="true" name="allocation_code" style="width:100%;height:120px" data-options="required:false"></td>
				</tr>
				<tr id="t_id12" >
					<td >机具调拨区间：</td>
					<td><input class="easyui-textbox" style="width:160px;"  id="mcode_start" name="mcode_start"   data-options="required:false"> &nbsp;- <input class="easyui-textbox" style="width:160px;"  id="mcode_end" name="mcode_end"  data-options="required:false"></td>

				</tr>
                    <tr id="t_id11"  >
                        <td  colspan="2"><span style="color: red">*注：机具序列号调拨，逗号分割，如：00003106681906043992518,00003106681906043992519 </span></td>
                    </tr>
				<tr id="staff_id1" >
					<td>下级员工：</td>
					<td><input class="easyui-combobox" style="width:200px;"  id="substaff_id" name="substaff_id"  data-options="required:true,valueField:'id',textField:'text'"></td>
				</tr>
			</table>

			<div class="lay_tables_oper">
				<div class="oper_width">
					<a href="javascript:;" onclick="up()" class="easyui-linkbutton" style="width:80px;">提 交 </a>
					<a href="#"  id="reset" class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
				</div>
			</div>
			<input type="hidden"  id="id" name="id"/>
		</form>
	</div>

	<!-- 机具调拨/回调表单开始 -->
	<div id="updatehd" class="easyui-window" title="" style="padding:15px 13px 70px 30px;width:550px;height:400px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<form id="updateformhd" method="post" enctype="multipart/form-data"  autocomplete="off" action-add="machineAllocation" action-edit="machineCallback" data-autoform="
				callback:function(o,j){
				    if(o.success==true){
				      $.messager.alert('提示信息',o.msg,'info');
				      $('#updatehd').window('close');
                      $('#data').treegrid('reload');
				   }else{
				      $.messager.alert('错误信息',o.msg,'error');
				   }
				}
				" >
			<div class=""  style="width:100%;height:40px;background: #e2e3e3; line-height: 40px;font-size: 15px" >

				<span class="lay_text_bor mr20">机具回调列表</span>

			</div>
			<table class="tables">
				<%--<tr >
					<td  colspan="2"><input class="easyui-textbox" label="" value="终端调拨列表" labelPosition="top" multiline="true" style="width:100%;height:40px"></td>
				</tr>
--%>
				<tr >
					<td >回调方式：</td>
					<td><input class="easyui-textbox" style="width:300px;"  id="allocation_mode2" name="allocation_mode"  data-options="required:true,valueField:'id',textField:'text'"></td>
				</tr>

				<tr >
					<td  colspan="2"><input class="easyui-textbox" label="" labelPosition="top" multiline="true" name="allocation_code" style="width:100%;height:120px" data-options="required:true"></td>
				</tr>

			</table>

			<div class="lay_tables_oper">
				<div class="oper_width">
					<a href="javascript:;" onclick="hdup()" class="easyui-linkbutton" style="width:80px;">提 交 </a>
					<a href="#"  id="reset2" class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
				</div>
			</div>
			<input type="hidden"  id="hd_id" name="id"/>
		</form>
	</div>
	<!-- 添加表单开始 -->

	<!-- 代理商所属部门下的所有代理商 -->
	<div id = "broker_id" class="easyui-window" title="更换所属代理商" style="width: 700px; padding: 0; height: 450px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<div id="toolbar1">
			<div>
				<input id="dataID" type="hidden">
				<form id="search1" method="post">
					员工姓名：<input id="broker_name" class="easyui-textbox" style="width: 150px;" name="broker_name" data-options="validType:'TextSearch'"/>
					员工编号：<input id="broker_code" class="easyui-textbox" style="width: 150px;" name="broker_code"  data-options="validType:'TextSearch'"/>
					<a href="javascript:;" onclick="$('#search1').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
					<a href="javascript:;" id="refresh" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
				</form>
			</div>

		</div>
		<table id="broker"></table>
	</div>
</div>
<div id="formobj"></div>

<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/platform/platform_query_machine_info.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>