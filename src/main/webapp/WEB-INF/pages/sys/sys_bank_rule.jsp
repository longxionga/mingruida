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
					<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					</span>
			</div>
		</div>

		<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" title="" style="padding:15px 13px 70px 30px;width:680px;height:450px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform" method="post" enctype="multipart/form-data"  autocomplete="off" action-add="insBankRule" action-edit="updBankRule" data-autoform="
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
				  <tr>
				  		<td>提现时间段：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="tx_time" name="tx_time"  data-options="required:true,validType:['length[2,100]']"></td>
				  	    <td>提现日期段：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="tx_date" name="tx_date"  data-options="required:true,validType:['length[2,150]']"></td>
				  	</tr> 
				  	
				  	 <tr>
				  		<td>最小提现金额：</td>																			
				  		<td><input class="easyui-textbox" style="width:150px;"  id="tx_min_money" name="tx_min_money"  data-options="required:true,validType:'intOrFloat',validType:'length[1,10]'"></td>
				  	    <td>最大提现金额：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="tx_max_money" name="tx_max_money"  data-options="required:true,validType:'intOrFloat',validType:'length[1,10]'"></td>
				  	</tr> 
				  	<tr>
				  		<td>最大充值总金额：</td>																			
				  		<td><input class="easyui-textbox" style="width:150px;"  id="cz_sum_money" name="cz_sum_money"  data-options="required:true,validType:'intOrFloat',validType:'length[1,10]'"></td>
				  	    <td>最大提现总金额：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="tx_sum_money" name="tx_sum_money"  data-options="required:true,validType:'intOrFloat',validType:'length[1,10]'"></td>
				  	</tr> 
				  	 <tr>
				  		<td>当天最大提现金额：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="tx_day_max_money" name="tx_day_max_money"  data-options="required:true,validType:'intOrFloat'"></td>
				  	</tr> 
				  	
				  	 <tr>
				  		<td>充值费率(千分率)：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="cz_rate" name="cz_rate"  data-options="required:true,validType:'intOrFloat'"></td>
				  	    <td>提现费率(千分率)：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="tx_rate" name="tx_rate"  data-options="required:true,validType:'intOrFloat'"></td>
				  	</tr> 
				  	
				  	 <tr>
				  		<td>充值时间段：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="cz_time" name="cz_time"  data-options="required:true,validType:['length[2,100]']"></td>
				  	    <td>充值日期段：</td>
				  		<td><input class="easyui-combobox" style="width:150px;"  id="cz_date" name="cz_date"  data-options="required:true,validType:['length[2,100]']"  onSelect="dateWeek()" onUnselect="dateWeek()"></td>
				  	</tr> 
				  	
				  	 <tr>
				  		<td>最小充值金额：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="cz_min_money" name="cz_min_money"  data-options="required:true,validType:'intOrFloat'"></td>
				  	    <td>最大充值金额：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="cz_max_money" name="cz_max_money"  data-options="required:true,validType:'intOrFloat'"></td>
				  	</tr>

					<tr>
						<td>当天最大充值金额：</td>
						<td><input class="easyui-textbox" style="width: 150px;"
							id="cz_day_max_money" name="cz_day_max_money"
							data-options="required:true,validType:'intOrFloat'"></td>

					</tr>
                    <tr>
					<td>现金券</td>
					<td colspan="3"><input class="easyui-textbox" style="width:443px;"  id="cash_money" name="cash_money"  data-options="required:true,validType:['length[2,100]']"></td>
					</tr>
				  	<tr>
					<tr>
				  		<td>充值是否可用：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="cz_is_use" name="cz_is_use"  data-options="required:true,validType:['length[1,20]']"></td>
				  	    <td>提现是否可用：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="tx_is_use" name="tx_is_use"  data-options="required:true,validType:['length[1,20]']"></td>
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
		<!-- 添加表单开始 -->
	</div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/sys/sys_bank_rule.js?v=${applicationScope.v}"></c:url>'></script>
</body>

</html>