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
						<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新增</a></span>
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					</span>
        </div>
    </div>

    <!-- 添加/修改表单开始 -->
    <div id="update" class="easyui-window" title="" style="padding:15px 13px 70px 30px;width:680px;height:350px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
        <form id="updateform" method="post" enctype="multipart/form-data"  autocomplete="off" action-add="insRechargeActivity" action-edit="updRechargeActivity" data-autoform="
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
                    <td>充值金额：</td>
                    <td><input class="easyui-textbox" style="width:150px;"  id="recharge_money" name="recharge_money"  data-options="required:true,validType:['int','length[1,10]']"></td>
                    <td>赠品价格：</td>
                    <td><input class="easyui-textbox" style="width:150px;"  id="give_money" name="give_money"  data-options="required:true,validType:['int','giveMoney','length[1,10]']"></td>
                </tr>

                <tr>
                    <td>是否赠送：</td>
                    <td><input class="easyui-combobox" style="width:150px;"  id="item_is_use" name="give_use"  data-options="required:true,validType:['length[1,20]']"></td>
                    <td>赠送商品：</td>
                    <td><input class="easyui-combobox" style="width:150px;"  id="goods_id" name="goods_id" data-options="required:true"></td>
                </tr>
                <tr>
                    <td>是否可用：</td>
                    <td><input class="easyui-combobox" style="width:150px;"  id="is_use" name="is_use"  data-options="required:true"></td>
                    <td>包装费：</td>
                    <td><input class="easyui-textbox" style="width:150px;"  id="package_money" name="package_money"  data-options="required:true,validType:['int','length[1,5]']"></td>

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

            <input type="hidden"  id="sys_id" name="sys_id"/>
        </form>
    </div>
    <!-- 添加表单开始 -->
</div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/sys/sys_recharge_activity.js?v=${applicationScope.v}"></c:url>'></script>
</body>

</html>