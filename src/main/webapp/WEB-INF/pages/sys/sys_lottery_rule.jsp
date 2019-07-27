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
			<form id="updateform" method="post" enctype="multipart/form-data"  autocomplete="off" action-add="" action-edit="updateLotteryRule" data-autoform="
				callback:function(o,j){
				    if(o.success==true){
				      $.messager.alert('提示信息',o.msg,'info');
				      $('#update').window('close');
                      $('#data').datagrid('reload');
				   }else{
				      $.messager.alert('错误信息',o.msg,'error');
				   }
				}
				" >
				  <table class="tables">
				  	 <tr>
				  		<td>活动类型：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="lottery_type" name="lottery_type"  data-options="required:true,validType:'length[1,10]'"></td>
				  	    <td>活动服务费率：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="lottery_service_rate" name="lottery_service_rate"  data-options="required:true,validType:'length[1,10]'"></td>
				  	</tr>

					<tr>
						<td>活动限制数量：</td>
						<td><input class="easyui-textbox" style="width:150px;"  id="lottery_limit_number" name="lottery_limit_number"  data-options="required:true,validType:'length[1,10]'"></td>

				  	    <%--<td>活动状态：</td>--%>
				  		<%--<td><input class="easyui-textbox" style="width:150px;"  id="statuss" name="statuss"  data-options="required:true,validType:['length[1,20]']"></td>--%>
				  	</tr> 

				  </table>
				<div class="lay_tables_oper">
					<div class="oper_width">
						<a href="javascript:;" onclick="$('#updateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
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
<script type="text/javascript" src='<c:url value="/static/sys/sys_lottery_rule.js?v=${applicationScope.v}"></c:url>'></script>
</body>

</html>