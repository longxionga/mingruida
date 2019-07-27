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
				<span class="lay_text_bor mr20">品牌：<input class="easyui-textbox" style="width:150px;" id="ibrand_id" name="ibrand_id"/></span>
<%--				<span class="lay_text_bor mr20">所属部门：<input class="easyui-textbox" style="width:150px;" id="isdept_id" name="isdept_id"/></span>--%>
				<span class="lay_text_bor mr20">所属分公司：<input class="easyui-combobox" id="isdept_id" name="isdept_id" style="width:150px;"  data-options="valueField:'id',textField:'text'" /></span>

				<span class="lay_text_bor mr20">分公司主管：<input class="easyui-combobox" id="orgid" name="orgid" style="width:150px;"  data-options="valueField:'id',textField:'text'" /></span>
				<span class="lay_text_bor mr20">员工名称：<input class="easyui-textbox" style="width:150px;" name="staffname"/></span>
				<span class="lay_text_bor mr10">岗位：<input  id="position_yg" name="position" data-options="valueField:'id',textField:'text'" style="width:150px;" /></span>

			<%--<span class="lay_text_bor mr10">充值时间：<input class="easyui-datebox" name="start_date" data-options="editable:false,showSeconds:true" style="width:150px;"id="skssj" />&nbsp;-
				<input class="easyui-datebox" name="end_date" data-options="editable:false,showSeconds:true" style="width:150px;" id="sjssj"/>
				</span>--%>
				<br/>
				<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
						<%--<span class="mr10"><a class="btns" href="javascript:void(0);" onclick="upFiles();">上传员工信息</a></span>--%>
						<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新增下级员工</a></span>
<%--
                    	<span class="mr10"><a href="javascript:;" onclick="closeAll();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除所有用户</a></span>
--%>
                    	<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>

                </span>
			</form>
		</div>
	</div>
	<!-- 添加/修改表单开始 -->
	<div id="update" class="easyui-window" title="" style="padding:15px 13px 70px 30px;width:680px;height:480px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<form id="updateform" method="post" enctype="multipart/form-data"  autocomplete="off" action-add="insStaffInfo" action-edit="updStaffInfo" data-autoform="
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
			<table class="tables">
				<%--<tr>
					<td>品牌：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="brand_id" name="brandid"  data-options="required:true"></td>
					<td>所属部门：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="sdept_id" name="branch"  data-options="required:true"></td>
				</tr>--%>
				<tr>
					<td>员工身份证号：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="staffcode" name="staffcode"  data-options="required:true,validType:['length[2,100]','idcard']"></td>
					<td>员工名称：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="staffname" name="staffname"  data-options="required:true,validType:['length[1,20]','name']"></td>
				</tr>
				<tr>
					<td>入职时间：</td>
					<td><input class="easyui-datebox" name="startdate" data-options="required:true,editable:false,showSeconds:true" style="width:150px;"id="startdate" /></td>
					<td>离职时间：</td>
					<td><input class="easyui-datebox" name="enddate" data-options="editable:false,showSeconds:true" style="width:150px;" id="enddate"/></td>
				</tr>
				<tr>
					<td>岗位：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="position" name="position"  data-options="required:true"></td>
					<td>手机号：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="phoneNO" name="phoneNO"  data-options="required:true"></td>
					<%--<td>登陆账户：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="logincode" name="logincode"  data-options="required:true,validType:['length[1,20]']"></td>--%>
				</tr>
				<tr>

					<td>上级员工：</td>
					<td><input class="easyui-combobox" style="width:150px;"  id="parentid" name="parentid"  data-options="required:true,valueField:'id',textField:'text'"/></td>
					<td>详细地址：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="detailedaddress" name="detailedaddress"  data-options="validType:['length[1,100]']"></td>
				</tr>
				<%--<tr>
					<td>分期：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="bystages" name="bystages" ></td>
					<td>登陆账户：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="logincode" name="logincode"  data-options="required:true,validType:['length[1,20]']"></td>
				</tr>
				<tr>
					<td>岗位：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="position" name="position"  data-options="required:true"></td>
					<td>状态：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="staffstate" name="staffstate"  data-options="required:true"></td>
				</tr>
				<tr>
					<td>手机号：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="phoneNO" name="phoneNO"  data-options="required:true,validType:'mobile'"></td>
					<td>是否可用：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="isstate" name="isstate"  data-options="required:true"></td>

				</tr>--%>
				<%--<tr>
					<td>上级员工：</td>
					<td><input class="easyui-combobox" style="width:150px;"  id="parentid" name="parentid"  data-options="valueField:'id',textField:'text'"/></td>

					<td>详细地址：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="detailedaddress" name="detailedaddress"  data-options="validType:['length[1,100]']"></td>
				</tr>--%>

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

	<!-- 员工升职 -->
	<div id="promotion" class="easyui-window" title="" style="padding:15px 13px 70px 30px;width:680px;height:280px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<form id="promotionform" method="post" enctype="multipart/form-data"  autocomplete="off" action="staffPromotion" data-autoform="
				callback:function(o,j){
				    if(o.success==true){
				      $.messager.alert('提示信息',o.msg,'info');
				      $('#promotion').window('close');
                      $('#data').treegrid('reload');
				   }else{
				      $.messager.alert('错误信息',o.msg,'error');
				   }
				}
				" >
			<table class="tables">

				<tr>
					<td>员工身份证名称：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="staffname_1"  name="staffname"  data-options="required:true,validType:['length[1,20]','name']"></td>
					<td>员工身份证号：</td>
					<td><input class="easyui-textbox" style="width:150px;" name="staffcode"  data-options="required:true,validType:['length[2,100]','idcard']" readonly></td>
				</tr>
				<tr>
					<td>原岗位：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="position_1" name="position"  data-options="required:true" readonly></td>
					<td>手机号：</td>
					<td><input class="easyui-textbox" style="width:150px;" name="phoneNO"  data-options="required:true" readonly></td>
				</tr>
				<tr>

					<td>原上级员工：</td>
					<td><input class="easyui-combobox" style="width:150px;"  id="parentid_1" name="parentid"  readonly="readonly" data-options="required:true,valueField:'id',textField:'text'"/></td>
					<td>入职时间：</td>
					<td><input class="easyui-datebox" name="startdate" data-options="required:true,editable:false,showSeconds:true" style="width:150px;" readonly /></td>
				</tr>
			</table>
			<div class="lay_tables_oper">
				<div class="oper_width">
					<a href="javascript:;" onclick="uppromotion()" class="easyui-linkbutton" style="width:80px;">提 交 </a>
<%--
					<a href="#"  id="proreset" class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
--%>
				</div>
			</div>
			<input type="hidden"  id="id_1" name="id"/>
		</form>
	</div>
</div>

<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/report3.js?v=${applicationScope.v}"></c:url>'></script>

<script type="text/javascript" src='<c:url value="/static/platform/platform_query_staff.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>