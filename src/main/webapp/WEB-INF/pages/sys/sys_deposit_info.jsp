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
				<span class="lay_text_bor mr20">服务商名：<input class="easyui-textbox" style="width:150px;" name="settle_name"  data-options="validType:'length[1,50]'"/></span>
				<!--<span class="lay_text_bor mr20">电话：<input  class="easyui-textbox" style="width:150px;" name="mobile"  data-options="validType:['int','length[1,11]']"/></span>  -->				
				<!--<span class="lay_text_bor mr20">位属经纪人：<input  class="easyui-textbox" style="width:150px;" name="broker_name" /></span>
								  <span class="lay_text_bor mr20">代理商选择：<input class="easyui-combobox"  id="agent_id"  name="agent_id" data-options="valueField:'id',textField:'text'" style="width:150px;" /></span>-->
				<span class="lay_text_bor mr20">服务商选择：<input class="easyui-combobox" id="settle_id" name="settle_id" data-options="valueField:'id',textField:'text'" style="width:150px;" /></span>
				<!--<span class="lay_text_bor mr10">时间选择：<input class="easyui-datetimebox" name="begindate" data-options="editable:false,showSeconds:true" style="width:180px;" />&nbsp;-
				<input class="easyui-datetimebox" name="enddate" data-options="editable:false,showSeconds:true" style="width:180px;" /></span>  -->			
				<span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<!--<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton mr10" data-options="plain:true,iconCls:'icon-add'">新增</a></span> -->
					<!-- <a href="javascript:search();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查询</a> -->
					<!-- <a href="javascript:excel();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'">导出</a> -->
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					<!-- <span class="mr10"><a href="#" onclick='expInfo();' class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-redo'">导出报表</a></span>-->

					<!-- 查询按钮放最前，刷新按钮放最后 -->
				</span>
				</form>
			</div>
			<!--  <div id="total" class="total"></div>-->
		</div>
		
			<!-- 添加/修改表单开始比率模板 -->
		<div id="update" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;height:381px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform1" method="post" autocomplete="off" action="updDepositInfo" data-autoform="
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
				  		<td>调整金额：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="dept_money" name="dept_money"  data-options="required:true,validType:['intOrFloat','length[1,10]']"></td>
				  	</tr>				  	
					<tr>
						<td colspan="2" align="center">
							<div class="pt15">
								<a href="javascript:;" onclick="modDeposit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
							</div>
						</td>
					</tr>				  
				  </table>
				  <input type="hidden" id="o_dept_id" name="dept_id"/>
				  <input type="hidden" id="dept_type" name="dept_type"/>
			</form>
		</div>
</div>



	
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/report.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/sys/sys_deposit_info.js?v=${applicationScope.v}"></c:url>'></script>

</body>
</html>