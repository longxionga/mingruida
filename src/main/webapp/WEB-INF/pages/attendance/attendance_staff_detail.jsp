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
				<span  class="lay_text_bor mr10">日期选择:
					<input editable="false" class="easyui-datebox" data-options="require:true" type="text" name="begindate" id="begindate"  style="width:100px;"></input>
				</span>
                <span class="lay_text_bor mr20">品牌：<input class="easyui-combobox" id="brand_id" name="brand_id" style="width:150px;"  data-options="valueField:'id',textField:'text'"/></span>
                <span class="lay_text_bor mr20">所属分公司：<input class="easyui-combobox" id="company_id" name="company_name" style="width:150px;" data-options="valueField:'id',textField:'text'"/></span>
                <span class="lay_text_bor mr10">分公司经理名称：<input  class="easyui-combobox" id="manager_id" name="manager_name" data-options="valueField:'id',textField:'text'" style="width:150px;" /></span>

				<span class="lay_text_bor mr20">员工名称：<input class="easyui-textbox" style="width:150px;" name="staffname"/></span>
				<span class="lay_text_bor">
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>

                    	<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>

                </span>
			</form>
		</div>
	</div>
</div>


<script>
	$(function() {
		var currTime=new Date();
		var strDate=currTime.getFullYear()+"-"+(currTime.getMonth()+1)+"-01";
		$('#begindate').datebox({
			onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
				span.trigger('click'); //触发click事件弹出月份层
				if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
					tds = p.find('div.calendar-menu-month-inner td');
					tds.click(function (e) {
						e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
						var year = /\d{4}/.exec(span.html())[0]//得到年份
								, month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
						$('#begindate').datebox('hidePanel')//隐藏日期对象
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
		var p = $('#begindate').datebox('panel'), //日期选择对象
				tds = false, //日期选择对象中月份
				yearIpt = p.find('input.calendar-menu-year'),//年份输入框
				span = p.find('span.calendar-text'); //显示月份层的触发控件

		$('#begindate').datebox('setValue',strDate);//默认加载当前月份

		$(".textbox").css({ height: "28px" });
		$(".textbox .textbox-text").css({
			paddingTop:0,
			paddingBottom:0,
			height: "28px"
		});
		$(".datebox .combo-arrow").css({ height: "28px" });
		// $('#brindnameid').datebox('setValue','6');//默认加载当前月份
		// $('#position_yg').datebox('setValue','2');//默认加载当前月份
      var date = 	$('#begindate').datebox('getValue');
		loadData({begindate:date});//初始化加载数据


	});
</script>

<script type="text/plain" id="myEditor"></script>
<script type="text/plain" id="upload_ue"></script>

<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/report2.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/attendance/attendance_staff_detail.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>