<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <%@include file="../../../common/common.jsp" %>
    <%@include file="../../../common/upload.jsp" %>
</head>
<body>
<table id="data"></table>
<div style="display: none">
    <!-- 工具栏开始 -->
    <div id="toolbar">
        <div class="pt10 pr10 pl10">
            <form id="search" method="post">
                <span class="lay_text_bor mr10">机具名称：<input class="easyui-textbox" style="width:150px;"
                                                            name="category_name"/></span>
                <span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton"
                                          iconCls="icon-search">查询</a></span>
					<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton mr10"
                                          data-options="plain:true,iconCls:'icon-add'">新增</a></span>
                    <!-- <a href="javascript:search();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查询</a> -->
                    <!-- <a href="javascript:excel();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'">导出</a> -->
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton"
                                          data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
				</span>
            </form>
        </div>
    </div>

    <!-- 添加/修改表单开始 -->
    <div id="update" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;"
         data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,closed:!0">
        <form id="updateform" method="post" autocomplete="off" action-add="insGoodsImage" action-edit="updGoodsImage"
              data-autoform="
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
                    <td>机具名称：</td>
                    <td><input class="easyui-combobox" style="width:150px;" id="category_code" name="category_code"
                               data-options="required:true"></td>

                    <td>图片类型：</td>
                    <td><input class="easyui-combobox" style="width:150px;" id="type" name="type"
                               data-options="required:true"></td>
                </tr>
                <tr>
                    <td>说明：</td>
                    <td colspan="3"><input class='easyui-textbox' style="width:420px;" id="category_desc" name="category_desc"
                                           data-options="validType:'length[0,100]'"></td>
                </tr>
                <tr>

                    <td>排序</td>
                    <td>
                        <input class='easyui-textbox' style="width:150px;" data-options="validType:'int'" id="is_order"
                               name="is_order">
                    </td>

                </tr>
                <tr>

                    <td>图片：</td>
                    <td colspan="3" class="tables_td_pos">
                        <input class="easyui-textbox" style="width:420px;" id="category_url" name="category_url"
                               readonly="true" data-options="required:true">
                        <a class="btns" href="javascript:void(0);" onclick="upImage();">上传图片</a>
                    </td>
                </tr>

                <tr>
                    <td colspan="4" align="center">
                        <div class="pt30">
                            <a href="javascript:;" onclick="$('#updateform').submit();" class="easyui-linkbutton"
                               style="width:80px;">提 交 </a>
                            <a href="javascript:;" onclick="$('#updateform').form('clear');" id="reset"
                               class="easyui-linkbutton l-btn-grey" style="width:80px;">重 置</a>
                        </div>
                    </td>
                </tr>
            </table>
            <input type="hidden" value="" id="category_name" name="category_name"/>
            <input type="hidden" value="" id="id" name="id"/>
        </form>
        <div id="upload" class="easyui-window" title="" style="padding:0px 13px 15px 30px;width:588px;"
             data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,closed:!0">
            <div id="uploader">
                <p>您的浏览器未安装 Flash, Silverlight, Gears, BrowserPlus 或者支持 HTML5 .</p>
            </div>
        </div>

    </div>
</div>
<!-- 添加表单结束-->


</div>
<script type="text/javascript">
    var editor;
    var _editor;
    $(function () {
        editor = UE.getEditor('myEditor', {
            initialFrameWidth: 800,
            initialFrameHeight: 300,
        });


        //重新实例化一个编辑器，防止在上面的editor编辑器中显示上传的图片或者文件
        _editor = UE.getEditor('upload_ue');
        _editor.ready(function () {
            //设置编辑器不可用
            //  _editor.setDisabled();
            //隐藏编辑器，因为不会用到这个编辑器实例，所以要隐藏
            _editor.hide();
            //侦听图片上传
            _editor.addListener('beforeInsertImage', function (t, arg) {
                //将地址赋值给相应的input,只去第一张图片的路径
                // $("#picture").attr("value", arg[0].src);

                var urlImg = "";
                for (var i = 0; i < arg.length; i++) {
                    if (i == (arg.length - 1)) {
                        urlImg += arg[i].src;
                    } else {
                        urlImg += arg[i].src + ",";
                    }
                }
                $("#category_url").textbox('setValue', urlImg);
                //$("#goods_desc").textbox('setValue',urlImg);
                // $("#goods_image").textbox('setValue',urlImg);
                //图片预览
                $("#preview").attr("src", arg[0].src);
            })
            //侦听文件上传，取上传文件列表中第一个上传的文件的路径
            _editor.addListener('afterUpfile', function (t, arg) {
                $("#file").attr("value", _editor.options.filePath + arg[0].url);
            })
        });
    });
    //弹出图片上传的对话框
    function upImage() {
        var floder = $('#category_code').combobox('getValue');
        if (floder == "" || floder == undefined || floder == null) {
            alert("请先选择商品");
        } else {
            var myImage = _editor.getDialog("insertimage");
            //var myImage = _editor.getDialog("simpleupload");
            myImage.open();
        }

    }

    //弹出文件上传的对话框
    function upFiles() {
        var myFiles = _editor.getDialog("attachment");
        myFiles.open();
    }

    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function (action) {
        /* if (action == "uploadimage") {
         var floder=$('#goods_name').combobox('getValue');
         return "../../../../upload/uploadGoodsImage?floder="+floder;
         } else {
         return this._bkGetActionUrl.call(this, action);
         } */
        if (action == "uploadimage") {
            var floder =$('#category_code').combobox('getValue');
            return "../../../../upload/uploadGoodsImage?floder=" + floder;
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    }
</script>
<script type="text/plain" id="myEditor"></script>
<script type="text/plain" id="upload_ue"></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/common.js"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/platform/platform_goods_image.js"></c:url>'></script>
</body>
</html>