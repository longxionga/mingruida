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
						<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新增考勤配置</a></span>
						<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					</span>
		</div>
	</div>

	<!-- 添加/修改表单开始 -->
	<div id="update" class="easyui-window" title="" style="padding:15px 13px 70px 30px;width:680px;height:250px;" data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
		<form id="updateform" method="post" enctype="multipart/form-data"  autocomplete="off" action-add="insertstaffworkattendanceinfo" action-edit="updatestaffworkattendanceinfo" data-autoform="
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
					<td>考勤时间：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="attendance_time" name="attendance_time"  data-options="required:true,validType:['length[1,20]']"></td>
					<td>全勤天数：</td>
					<td><input class="easyui-numberbox" style="width:150px;"  id="day_count" name="day_count"  data-options="required:true,validType:['length[1,2]']"></td>
				</tr>
				<tr>
					<td>是否可用：</td>
					<td><input class="easyui-textbox" style="width:150px;"  id="attendance_status" name="attendance_status"  data-options="required:true,validType:['length[1,20]']"></td>
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
	var ruleId ;
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

		var currTime=new Date();
		var strDate=currTime.getFullYear()+"-"+(currTime.getMonth()+1)+"-01";
		$('#attendance_time').datebox({
			onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
				span.trigger('click'); //触发click事件弹出月份层
				if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
					tds = p.find('div.calendar-menu-month-inner td');
					tds.click(function (e) {
						e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
						var year = /\d{4}/.exec(span.html())[0]//得到年份
								, month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
						$('#attendance_time').datebox('hidePanel')//隐藏日期对象
								.datebox('setValue', year + '-' + month); //设置日期的值
					});
				}, 0);
				yearIpt.unbind();//解绑年份输入框中任何事件
			},
			parser: function (s) {
				if (!s) return new Date();
				var arr = s.split('-');
				return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
			},
			formatter: function (d) { return d.getFullYear() + '-' + ((d.getMonth() + 1)<10?'0'+(d.getMonth() + 1):(d.getMonth() + 1));/*getMonth返回的是0开始的，忘记了。。已修正*/ }
		});
		var p = $('#attendance_time').datebox('panel'), //日期选择对象
				tds = false, //日期选择对象中月份
				yearIpt = p.find('input.calendar-menu-year'),//年份输入框
				span = p.find('span.calendar-text'); //显示月份层的触发控件

		$('#attendance_time').datebox('setValue',strDate);//默认加载当前月份

		$(".textbox").css({ height: "28px" });
		$(".textbox .textbox-text").css({
			paddingTop:0,
			paddingBottom:0,
			height: "28px"
		});
		$(".datebox .combo-arrow").css({ height: "28px" });
	});
	//弹出图片上传的对话框
	function upImage() {
		var myImage = _editor.getDialog("insertimage");
		//var myImage = _editor.getDialog("simpleupload");
		myImage.open();
	}

	//弹出文件上传的对话框
	function upFiles(id) {
		ruleId = id ;
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
		console.info(ruleId);
		if (action == "uploadimage" || action == "uploadfile") {
			return "../../../../attendance/uploadfileattdetail?attID="+ruleId;
		} else {
			return this._bkGetActionUrl.call(this, action);
		}
	}

</script>
<script type="text/plain" id="upload_ue"></script>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/attendance/attendance_staff.js?v=${applicationScope.v}"></c:url>'></script>
</body>

</html>