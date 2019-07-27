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
				<span class="lay_text_bor mr20">登录名称：<input class="easyui-textbox" style="width:150px;" name="login_name"/></span>
				<span class="lay_text_bor mr20">用户名：<input class="easyui-textbox" style="width:150px;" name="user_name"/></span>
				<span class="lay_text_bor mr20">电话：<input  class="easyui-textbox" style="width:150px;" name="mobile"  data-options="validType:'int'"/></span>
				<span class="lay_text_bor mr10">部门：<input  id="idept_id" name="idept_id" data-options="valueField:'id',textField:'text'" style="width:150px;" /></span>
				<!-- <span class="lay_text_bor mr20">时间选择：<input class="easyui-datetimebox" name="" data-options="editable:false,showSeconds:false" style="width:150px;" /></span>
				<span class="lay_text_bor mr20">下拉选择：<input class="easyui-textbox"  id='charge_channel'  name='charge_channel' style="width:150px;" /></span>
				<span class="lay_text_bor mr10">下拉搜索：<select id="SID" name="SID" class="easyui-combobox" data-options="valueField:'id'"style="width:150px;" validType="selectValueRequired['#SID']"></select></span> -->
				<span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton mr10" data-options="plain:true,iconCls:'icon-add'">新增</a></span>
					<!-- <a href="javascript:search();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查询</a> -->
					<!-- <a href="javascript:excel();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'">导出</a> -->
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					
				<!--	<span class="mr10"><a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-man'">设置推荐人比例</a></span>
					<span class="mr10"><a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-redo'">导出报表</a></span>
					<span class="mr10"><a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-ok'">确认选择</a></span>
					<span class="mr10"><a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">Remove</a></span>
					<span class="mr10"><a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'">Save</a></span>
					<span class="mr10"><a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cut',disabled:true">Cut</a></span>
					<span class="mr10"><a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-print'">Print</a></span>
					<span class="mr10"><a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-help'">Help</a></span>    -->
					<!-- 查询按钮放最前，刷新按钮放最后 -->
				</span>
				</form>
			</div>
		</div>

		<!-- 添加/修改表单开始 -->
		<div id="update" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:588px;height:335px;" closed="true" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
			<form id="updateform" method="post" autocomplete="off" action-add="insUserInfo" action-edit="updUserInfo" data-autoform="
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
				  		<td>登录名称：</td>
				  		<td><input class="easyui-textbox" style="width:150px;"  id="user_name" name="user_name" data-options="required:true,validType:['ajax[\'userValidate\']','length[2,100]','name']"/></td>
				  	    <td>部门：</td>
					    <td><input id="dept_id" name="dept_id" data-options="valueField:'id',textField:'text'"  ></input></td>
				  	</tr>
				  	
				  	<tr>
						<td>昵称：</td>
						<td><input class="easyui-textbox" style="width:150px;" name="user_nick_name" data-options="required:true,validType:'unnormal'"></input></td>
						
						<td>电话：</td>
						<td><input class="easyui-textbox" style="width:150px;" name="user_mobile" data-options="required:true,validType:'mobile'"></input></td>						
					</tr>
					<tr>
						<td>QQ：</td>
						<td><input class="easyui-textbox" style="width:150px;" name="user_qq" data-options="validType:'qq'"></td>
						<td>性别：</td>
					    <td><input class="easyui-combobox" style="width:150px;" id="user_gender" name="user_gender"></input></td>
					</tr>
					<tr>
					    <td>是否可用：</td>
					   <td><input class="easyui-combobox" id='is_use' name='is_use'></input></td>    
					   
					 <!--     <td>图片：</td>
						<td class="tables_td_pos"><input class="easyui-textbox"  style="width: 150px;" id="picture" name="user_icon" readonly="true"> 
						<a class="btns" href="javascript:void(0);" onclick="upImage();">上传图片</a>
						</td>     -->
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
				  <input type="hidden" value="" id="user_id" name="user_id"/>
			</form>
		</div>
		<!-- 添加表单开始 -->
	</div>
	
<script type="text/plain" id="myEditor"></script>
<script type="text/plain" id="upload_ue"></script>	
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/sys/sys_user_info.js?v=${applicationScope.v}"></c:url>'></script>

	<script type="text/javascript">

/*
	//上传图片
	var editor;
	var _editor;
	$(function() {
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
	            //将地址赋值给相应的input,只取第一张图片的路径
	           // $("#picture").attr("value", arg[0].src);
	            $("#picture").textbox('setValue',arg[0].src);
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
	    var myImage = _editor.getDialog("insertimage");
	    myImage.open();
	}
	//弹出文件上传的对话框
	function upFiles() {
	    var myFiles = _editor.getDialog("attachment");
	    myFiles.open();
	}

	UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
	UE.Editor.prototype.getActionUrl = function(action) {
		if (action == "uploadimage") {
			return "../../../../upload/uploadImage";
		} else {
			return this._bkGetActionUrl.call(this, action);
		}
	}  */
	
	</script>   

</body>
</html>