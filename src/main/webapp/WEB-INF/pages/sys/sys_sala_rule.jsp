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
					<span class="lay_text_bor">
						<a class="btns" href="javascript:void(0);" onclick="upFiles();">上传工资</a>
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					</span>
			</div>
		</div>

		<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" title="" style="padding:15px 13px 70px 30px;width:680px;height:250px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform" method="post" enctype="multipart/form-data"  autocomplete="off" action-add="insSalaRule" action-edit="updSalaRule" data-autoform="
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
				  		<td>员工工资标题：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="sala_title" name="sala_title"  data-options="required:true,validType:['length[2,100]']"></td>
				  	    <td>员工查询工资是否可用：</td>
					  <td><input class="easyui-textbox" style="width:150px;"  id="sala_status" name="sala_status"  data-options="required:true,validType:['length[1,20]']"></td>
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

	<script type="text/javascript">
        var _editor;
        $(function() {



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

                })
                //侦听文件上传，取上传文件列表中第一个上传的文件的路径
                _editor.addListener('afterUpfile', function (t, arg) {
                    $("#file").attr("value", _editor.options.filePath + arg[0].url);
                })
            });
        });
        //弹出图片上传的对话框
        function upImage() {
            var myImage = _editor.getDialog("insertimage");
            //var myImage = _editor.getDialog("simpleupload");
            myImage.open();
        }

        //弹出文件上传的对话框
        function upFiles() {
            var myFiles = _editor.getDialog("attachment");
            myFiles.open();
        }

        UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
        UE.Editor.prototype.getActionUrl = function(action) {
            /* if (action == "uploadimage") {
                var floder=$('#goods_name').combobox('getValue');
                return "../../../../upload/uploadGoodsImage?floder="+floder;
            } else {
                return this._bkGetActionUrl.call(this, action);
            } */
            if (action == "uploadimage" || action == "uploadfile") {
                return "../../../../upload/uploadfile";
            } else {
                return this._bkGetActionUrl.call(this, action);
            }
        }
	</script>
	<script type="text/plain" id="upload_ue"></script>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/sys/sys_sala_rule.js?v=${applicationScope.v}"></c:url>'></script>
</body>

</html>