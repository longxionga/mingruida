<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <%@include file="../../../common/common.jsp"%>
</head>
<body>
<table id="data"></table>
<div style="display: none">
    <!-- 工具栏开始 -->
    <div id="toolbar">
        <div class="pt10 pr10 pl10">
            <form id="search" method="post">
                <span class="lay_text_bor mr10">商品：<input class="easyui-textbox" style="width:150px;" id="check_goods_name" name="check_goods_name"/></span>
                <span class="lay_text_bor mr20">规格：<input class="easyui-textbox" style="width:150px;" id="check_spec_name" name="check_spec_name"/></span>
                <span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
				</span>
            </form>
        </div>
    </div>

    <!-- 添加/修改表单开始 -->
    <div id="update" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
        <form id="updateform" method="post" autocomplete="off" action-add="insertPurchase" action-edit="insertPurchase" data-autoform="
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
                    <td>数量：</td>
                    <td><input class='easyui-textbox' style="width: 150px;" id="number" name="number" data-options="required:true,validType:'int',validType:['calcOrder[\'calcOrder\']','length[1,6]']"></td>
                </tr>

                <tr>
                    <td colspan="4" align="center">
                        <div class="pt30">
                            <a href="javascript:;" onclick="$('#updateform').submit();" class="easyui-linkbutton" style="width:80px;">提 交 </a>
                            <a href="javascript:;" onclick="$('#updateform').form('clear');" id="reset" class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
                        </div>
                    </td>
                </tr>
                    <input  type="hidden" id="goods_id" name="goods_id"/>
                    <input  type="hidden" id="spec_id" name="spec_id"/>
            </table>
        </form>
    </div>


    <!-- 设置代理商部门 -->
    <div id = "order_detail" class="easyui-window" title="历史订单" style="width: 700px; padding: 0; height: 450px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
        <table id="order"></table>
        <div id="toolbar1">
            <div style="padding-bottom: 10px;">
                <form id="search1" method="post">
                    审核状态：<input id="status" class="easyui-textbox" style="width: 150px;"
                              name="status" />
                    <input id="order_goods_id" type="hidden" style="width: 150px;"
                           name="order_goods_id" />
                    <input id="order_spec_id" type="hidden" style="width: 150px;"
                           name="order_spec_id" />
                    <a href="javascript:;"
                       onclick="$('#search1').submit();" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                    <a href="javascript:;" id="refresh" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
                    <!-- <a href="javascript:;"
                    onclick="$('#broker').datagrid('reload');"
                    class="easyui-linkbutton"
                    data-options="plain:true,iconCls:'icon-reload'">刷新</a> -->
                </form>
            </div>
        </div>
    </div>

</div>
<!-- 添加表单结束-->
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/common.js"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/platform/platform_purchase.js"></c:url>'></script>
</body>
</html>