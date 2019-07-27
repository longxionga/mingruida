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
		<form id="updateform" method="post" autocomplete="off" action-add="insDeptInfo" action-edit="updDeptInfo" data-autoform="
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
			<table class="tables">
				<tr>
					<td>编  码：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="dept_code" name="dept_code" data-options="required:true,validType:['length[2,20]','english','unnormal','deptCode[\'deptCodeValidate\']']"></td>
					<td>名  称：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="dept_name" name="dept_name" data-options="required:true,validType:['length[2,20]','unnormal','deptCode[\'deptCodeValidate\']']"></td>
				</tr>
				<tr>
					<td>是否可用：</td>
					<td><input class="easyui-combobox" id='is_use' name='is_use'></input></td>
					<td>分配比率：</td>
					<td><input class="easyui-textbox" style="width:150px;" id="dept_ratio" name="dept_ratio" data-options="required:true,validType:['percentage','int']"></td>
				</tr>
				<tr>
					<td>余 额：</td>
					<td><input class="easyui-textbox" style="width:150px;" id="dept_money" name="dept_money" data-options="required:true,validType:'currency'"></td>
					<td>联系电话：</td>
					<td><input class="easyui-textbox" style="width:150px;" id="dept_mobile" name="dept_mobile" data-options="required:true,validType:'mobile'"></td>
				</tr>
				<tr>
					<td>微信ID：</td>
					<td><input class="easyui-textbox" style="width:150px;" id="dept_app_id" name="dept_app_id" data-options="validType:'unnormal'"></td>
					<td>微信密码：</td>
					<td><input class="easyui-textbox" style="width:150px;" id="dept_app_secret" name="dept_app_secret" data-options="validType:'unnormal'"></td>
				</tr>
				<tr>
					<td>是否推荐人：</td>
					<td>
						<select id="is_tj_man" class="easyui-combobox" name="is_tj_man" style="width:150px;">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</td>
					<td>推荐人比率：</td>
					<td><input class="easyui-textbox" style="width:150px;" id="tj_ratio" name="tj_ratio" data-options="validType:['percentage','int']"></td>
				</tr>
				<tr>
					<td>机构网站：</td>
					<td colspan="3"><input class="easyui-textbox" style="width:415px;" id="dept_url" name="dept_url" data-options="validType:'unnormal'"></td>
				</tr>
				<tr>
					<td>经纪人链接：</td>
					<td colspan="3"><input class="easyui-textbox" style="width:415px;" id="broker_url" name="broker_url" data-options="validType:'unnormal'"></td>

				</tr>
				<tr>
					<td>商城名：</td>
					<td colspan="3"><input class="easyui-textbox" style="width:150px;" id="dept_title" name="dept_title" data-options="validType:['length[2,20]','unnormal']"></td>

				</tr>
				<tr>
					<td colspan="4" align="center">
						<div class="pt15">
							<a href="javascript:;" onclick="$('#updateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
							<a href="#" id="reset" class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
						</div>
					</td>
				</tr>
			</table>
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

			<table class="tables">
				<tr>
					<td>交易中心：<span id="ce_name">交易中心</span></td>
					<td><input class="easyui-textbox" style="width:150px;"  id="ce_allot" name="ce_allot"  data-options="required:true,validType:['percentage','int']"></td>
				</tr>
				<tr>
					<td>渠道：<span id="ch_name">渠道</span></td>
					<td><input class="easyui-textbox" style="width:150px;"  id="ch_allot" name="ch_allot"  data-options="required:true,validType:['percentage','int']"></td>
				</tr>
				<tr>
					<td>服务商：<span id="s_name">服务商</span></td>
					<td><input class="easyui-textbox" style="width:150px;"  id="s_allot" name="s_allot"  data-options="required:true,validType:['percentage','int']"></td>
				</tr>
				<tr>
					<td>代  理  商：<span id="a_name">代理商</span></td>
					<td><input class="easyui-textbox" style="width:150px;"  id="dept_ratios" name="dept_ratio"  data-options="required:true,validType:['percentage','int']"></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<div class="pt15">
							<a href="javascript:;" onclick="$('#updateform1').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
							<a href="javascript:;" id="yingyong" class="easyui-linkbutton" style="width:80px;">应用</a>
							<a href="#" id="reset2" class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
						</div>
					</td>
				</tr>
			</table>
			<input type="hidden" id="types" name="types"/>
			<input type="hidden" id="_dept_id" name="dept_id"/>
			<input type="hidden" id="dept_parent_id" name="dept_parent_id"/>
			<input type="hidden" id="dept_type" name="dept_type"/>
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
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/sys/sys_dept_info.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>