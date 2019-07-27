<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${applicationScope.T}提现申请</title>
	<%@include file="../../../common/common.jsp"%>
<!-- 服务商 与 供应商 提现申请 -->
</head>
<body>
	<table id="data"></table>
	<div style="display: none">
		<!-- 工具栏开始 -->
		<div id="toolbar">
			<div class="pt10 pr10 pl10">
				<form id="search" method="post">
				<!--<span class="lay_text_bor mr20">卡号：<input class="easyui-textbox" name="tx_number" style="width:150px;" /></span>-->
				<span class="lay_text_bor mr10">时间选择：<input class="easyui-datebox" name="begin_date" data-options="editable:false,showSeconds:false" style="width:150px;" />&nbsp;-&nbsp;<input class="easyui-datebox" name="end_date" data-options="editable:false,showSeconds:false,validType:['TimeCheck[\'begin_date\']']" style="width:150px;" /></span>			
				<span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton mr10" data-options="plain:true,iconCls:'icon-add'">新增申请</a></span>
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
				</span>
				</form>
			</div>
		</div>

		<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;" closed="true" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform" method="post" autocomplete="off" action="insWithDrawInfo" data-autoform="
				callback:function(o,j){
				    if(o.success==true){
				      	$('#update').window('close');
						$.messager.alert('提示', o.msg, 'info',function(){
					      window.location.reload();
						});
                       $('.messager-window .panel-tool-close').remove();
				   }else{
				      $.messager.alert('错误信息',o.msg,'error');
				   }
				}
				">
				  <table class="tables">
				  	<tr>
				  		<td style="width:90px;" >申请额度：</td>
				  		<td><input class="easyui-textbox" style="width:150px;" name="tx_money" data-options="required:true,validType:'moneys'"/></td>
				  	    <td style="width:90px;">姓名：</td>
					    <td><input class="easyui-textbox" style="width:150px;" name="tx_name"  data-options="required:true,validType:'unnormal'"/></td>
				  	</tr>

					<tr>
						<td id="h1">可提现金额：</td>
						<td><span style="width:150px;" id="withDrawMoney">可提现金额</span> 元</td>
						<td>账户余额：</td>
						<td><span style="width:150px;" id="deptMoney">账户余额</span> 元</td>
					</tr>
					<tr>
						<td id="h2">保证金合计：</td>
						<td colspan=3><span style="width:150px;" id="earnestMoney">保证金合计</span> 元</td>
					</tr>
					<tr>
						<td id="h3">说明：</td>
					    <td colspan=3>
					    	<p>可提现金额 = 账户余额 - 保证金合计（负数表示还需要充值的金额）</p>
					    </td>
					</tr>
					<tr>
						<td colspan=4><span style="width:150px;" id="Money"></span></td>
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
			</form>
		</div>
		<!-- 添加表单开始 -->
	</div>
<script type="text/javascript">
	var loginName="${loginName}";
	var deptType="${deptType}";
	var deptMoney="${deptMoney}";
	var earnestMoney="${earnestMoney}";
	var withDrawMoney="${withDrawMoney}";
</script>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/sys/sys_with_draw.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>