<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${applicationScope.T}分配比率管理</title>
	<%@include file="../../../common/common.jsp"%>

</head>
<body>
<input type="hidden" id="deptType" value="${deptType}"/>
	<table id="data"></table>
	<div style="display: none">
		<!-- 工具栏开始 -->
		<div id="toolbar">
			<div class="pt10 pr10 pl10">
				<form id="search" method="post">
				<span class="lay_text_bor mr20">代理商名称：<input class="easyui-textbox" style="width:150px;" name="agent_name"/></span>
				<!--<span class="lay_text_bor mr10">联系电话：<input  class="easyui-textbox" style="width:150px;" name="broker_mobile" data-options="validType:'mobile'"/></span>-->
				<span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
				</span>
				</form>
			</div>
		</div>
                                                                                                                                                                                        
		<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;height:460px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform" method="post" autocomplete="off" action="updAgentProrateTemp" data-autoform="
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
				  		<td>交易中心：<span id="ce_name">交易中心</span></td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="ce_allot" name="ce_allot"  data-options="required:true,validType:['percentage','int']"></td>
				  	</tr>
				  	<tr>
				  		<td>渠  道  部：<span id="ch_name">渠道部</span></td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="ch_allot" name="ch_allot"  data-options="required:true,validType:['percentage','int']"></td>
				  	</tr>
				  	<tr>
				  		<td>服务商：<span id="s_name">服务商</span></td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="s_allot" name="s_allot"  data-options="required:true,validType:['percentage','int']"></td>
				  	</tr>
				  	<tr>
				  		<td>代  理  商：<span id="a_name">代理商</span></td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="a_allot" name="a_allot"  data-options="required:true,validType:['percentage','int']"></td>
				  	</tr>
				  	<tr>
				  		<td>比例1：<span id="allot_1">比例1</span></td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="b_allot_1" name="b_allot_1" data-options="required:true,validType:['percentage','int']"></td>
				  	</tr>
					<tr>
						<td>比例2：<span id="allot_2">比例3</span></td>
						<td><input class="easyui-textbox" style="width:150px;"  id="b_allot_2" name="b_allot_2" data-options="required:true,validType:['percentage','int']"></td>
					</tr>
					<tr>
						<td>比例3：<span id="allot_3">比例3</span></td>
						<td><input class="easyui-textbox" style="width:150px;"  id="b_allot_3" name="b_allot_3" data-options="required:true,validType:['percentage','int']"></td>
					 </tr>
					<tr>
						<td colspan="2" align="center">
							<div class="pt15">
								<a href="javascript:;" onclick="$('#updateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
								<a href="javascript:;" onclick="update($(this).attr('data-repickID'));" id="reset" class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
							</div>
						</td>
					</tr>				  
				  </table>
				  <input type="hidden" value="" id="sys_id" name="sys_id"/>
				  <input type="hidden" value="" id="p_id" name="p_id"/>
				  <input type="hidden" value="" id="p_allot" name="p_allot"/>
				  <input type="hidden" value="" id="ce_id" name="ce_id"/>
				  <input type="hidden" value="" id="ch_id" name="ch_id"/>
				  <input type="hidden" value="" id="s_id" name="s_id"/>
				  <input type="hidden" value="" id="a_id" name="a_id"/>
				  <input type="hidden" value="" id="b_id" name="b_id"/>

			</form>
		</div>
	</div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/sys/sys_agent_prorate_temp.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>