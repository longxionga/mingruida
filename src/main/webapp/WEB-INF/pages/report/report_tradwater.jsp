<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

				<span class="lay_text_bor mr10">所属品牌：<input class="easyui-combobox"   id="brind_id" name="brindid" data-options="valueField:'id',textField:'text'" style="width:150px;" /></span>

				<span class="lay_text_bor mr20">时间分组：<input class="easyui-combobox" style="width:150px;" id="time_group" name="time_group"/></span>



				<span class="lay_text_bor mr10">日期选择：
					<input class="easyui-datebox" name="start_date" data-options="editable:false,showSeconds:true" style="width:150px;"id="skssj" />&nbsp;-
				<input class="easyui-datebox" name="end_date" data-options="editable:false,showSeconds:true" style="width:150px;" id="sjssj"/></span>
				<br/>

				<span class="lay_text_bor">
					 <span class="mr10"><a href="javascript:;" onclick="exportExcel();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-ok'">导出Excel</a></span>
						<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>

                    	<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>

                </span>
			</form>
		</div>
	</div>
</div>


<script>




	$('#time_group').combobox({
		width : '150',
		height : '23',
		valueField : 'label',
		textField : 'value',
		panelHeight : 'auto',
		editable: false,
		data : [{
			label : '0',
			value : '全部'
		}, {
			label : '1',
			value : '今天'
		},{
			label : '2',
			value : '昨天'
		} ,{
			label : '3',
			value : '本周'
		}
		,{
			label : '4',
			value : '上周'
		}
		,{
			label : '5',
			value : '本月'
		}
		,{
			label : '6',
			value : '上月'
		}
		,{
			label : '7',
			value : '今年'
		}
		]
	});



	// 今年开始日期
	function getYearStartDate() {
		var myDate = new Date();
		return (myDate.getFullYear() + "-01-01 00:00:00");
	}
	// 今年结束日期
	function getYearEndDate() {
		var myDate = new Date();
		return (myDate.getFullYear() + "-12-31 23:59:59");
	}
	// 上月开始日期
	function getLastMonthStartDate() {
		var myDate = new Date();
		var m = myDate.getMonth();
		var y = myDate.getFullYear();
		if (m == 0) {
			y = y - 1;
			m = 12;
		}
		return (y + "-" + (m > 9 ? m : ("0" + m)) + "-01")+" 00:00:00";
	}
	// 上月结束日期
	function getLastMonthEndDate() {
		var myDate = new Date();
		var day = 30;
		var m = myDate.getMonth();
		var y = myDate.getFullYear();
		if (m == 0) {
			y = y - 1;
			m = 12;
		}
		if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 | m == 12) {
			day = 31;
		} else if (m == 2) {
			var v = myDate.getFullYear() % 4;
			if (v == 0)
				day = 28;
			else
				day = 29;
		} else if (m == 4 || m == 6 || m == 9 || m == 11) {
			day = 30;
		}
		return (y + "-" + (m > 9 ? m : ("0" + m)) + "-" + day)+" 23:59:59";
	}

	// 本月开始日期
	function getMonthStartDate() {
		var myDate = new Date();
		var m = myDate.getMonth() + 1;
		return (myDate.getFullYear() + "-" + (m > 9 ? m : ("0" + m)) + "-01")+" 00:00:00";
	}
	// 本月结束日期
	function getMonthEndDate() {
		var myDate = new Date();
		var day = 30;
		var m = myDate.getMonth() + 1;

		if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 | m == 12) {
			day = 31;
		} else if (m == 2) {
			var v = myDate.getFullYear() % 4;
			if (v == 0)
				day = 28;
			else
				day = 29;
		} else if (m == 4 || m == 6 || m == 9 || m == 11) {
			day = 30;
		}
		return (myDate.getFullYear() + "-" + (m > 9 ? m : ("0" + m)) + "-" + day)+" 23:59:59";
	}
	// 上周开始日期
	function getLastWeekStartDate() {
		var myDate = new Date();
		var day = myDate.getDate() - myDate.getDay() - 6;
		var m = myDate.getMonth() + 1;
		if (day <= 0) {
			m = myDate.getMonth();
			if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 | m == 12) {
				day = 31 + day;
			} else if (m == 2) {
				var v = myDate.getFullYear() % 4;
				if (v == 0)
					day = 28 + day;
				else
					day = 29 + day;
			} else if (m == 4 || m == 6 || m == 9 || m == 11) {
				day = 30 + day;
			}
		}
		return (myDate.getFullYear() + "-" + (m > 9 ? m : ("0" + m)) + "-" + (day > 9 ? day
				: ("0" + day)))+" 00:00:00";
	}
	// 上周结束日期
	function getLastWeekEndDate() {
		var myDate = new Date();
		var day = myDate.getDate() + (6 - myDate.getDay()) - 6;
		var m = myDate.getMonth() + 1;
		if (day <= 0) {
			m = myDate.getMonth();
			if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 | m == 12) {
				day = 31 + day;
			} else if (m == 2) {
				var v = myDate.getFullYear() % 4;
				if (v == 0)
					day = 28 + day;
				else
					day = 29 + day;
			} else if (m == 4 || m == 6 || m == 9 || m == 11) {
				day = 30 + day;
			}
		}
		return (myDate.getFullYear() + "-" + (m > 9 ? m : ("0" + m)) + "-" + (day > 9 ? day
				: ("0" + day)))+" 23:59:59";
	}
	// 本周开始日期
	function getWeekStartDate() {
		var myDate = new Date();
		var day = myDate.getDate() - myDate.getDay() + 1;
		var m = myDate.getMonth() + 1;
		if (day <= 0) {
			m = myDate.getMonth();
			if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 | m == 12) {
				day = 31 + day;
			} else if (m == 2) {
				var v = myDate.getFullYear() % 4;
				if (v == 0)
					day = 28 + day;
				else
					day = 29 + day;
			} else if (m == 4 || m == 6 || m == 9 || m == 11) {
				day = 30 + day;
			}
		}
		return (myDate.getFullYear() + "-" + (m > 9 ? m : ("0" + m)) + "-" + (day > 9 ? day
				: ("0" + day)))+" 00:00:00";
	}
	// 本周结束日期
	function getWeekEndDate() {
		var myDate = new Date();
		var day = myDate.getDate() + (7 - myDate.getDay());
		var m = myDate.getMonth() + 1;

		var dt = 0;
		if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 | m == 12) {
			dt = 31;
		} else if (m == 2) {
			var v = myDate.getFullYear() % 4;
			if (v == 0)
				dt = 28;
			else
				dt = 29;
		} else if (m == 4 || m == 6 || m == 9 || m == 11) {
			dt = 30;
		}
		if (day > dt) {
			m = myDate.getMonth() + 2;
			day = day - dt;
		}
		return (myDate.getFullYear() + "-" + (m > 9 ? m : ("0" + m)) + "-" + (day > 9 ? day
				: ("0" + day)))+" 23:59:59";
	}
	//今天开始
	function getToDayStart() {
		var myDate = new Date();
		var m = myDate.getMonth() + 1;
		var day = myDate.getDate();
		return (myDate.getFullYear() + "-" + (m > 9 ? m : ("0" + m)) + "-" + (day > 9 ? day
				: ("0" + day)))+" 00:00:00";
	}
	//今天结束
	function getToDayEnd() {
		var myDate = new Date();
		var m = myDate.getMonth() + 1;
		var day = myDate.getDate();
		return (myDate.getFullYear() + "-" + (m > 9 ? m : ("0" + m)) + "-" + (day > 9 ? day
				: ("0" + day)))+" 23:59:59";
	}

	//昨天开始
	function getYesterdayStart() {
		var myDate = new Date();
		var m = myDate.getMonth() + 1;
		var day = myDate.getDate() - 1;
		return (myDate.getFullYear() + "-" + (m > 9 ? m : ("0" + m)) + "-" + (day > 9 ? day
				: ("0" + day)))+" 00:00:00";
	}

	//昨天开始
	function getYesterdayEnd() {
		var myDate = new Date();
		var m = myDate.getMonth() + 1;
		var day = myDate.getDate() - 1;
		return (myDate.getFullYear() + "-" + (m > 9 ? m : ("0" + m)) + "-" + (day > 9 ? day
				: ("0" + day)))+" 23:59:59";
	}





	$("#time_group").combobox({

		onSelect: function () {
			var time_group=$('#time_group').combobox('getValue');//获取combobox的value方法
			if (time_group == 0) {
				$('#skssj').datebox('setValue', '');
				$('#sjssj').datebox('setValue', '');

			} else if (time_group == 1) {
					$('#skssj').datebox('setValue', getToDayStart());
					$('#sjssj').datebox('setValue', getToDayStart());

			} else if (time_group == 2){
				$('#skssj').datebox('setValue', getYesterdayStart());
				$('#sjssj').datebox('setValue', getYesterdayEnd());
			}
			else if (time_group == 3){
				$('#skssj').datebox('setValue', getWeekStartDate());
				$('#sjssj').datebox('setValue', getWeekEndDate());
			}
			else if (time_group == 4){
				$('#skssj').datebox('setValue', getLastWeekStartDate());
				$('#sjssj').datebox('setValue', getLastWeekEndDate());
			}
			else if (time_group == 5){
				$('#skssj').datebox('setValue', getMonthStartDate());
				$('#sjssj').datebox('setValue', getMonthEndDate());
			}
			else if (time_group == 6){
				$('#skssj').datebox('setValue', getLastMonthStartDate());
				$('#sjssj').datebox('setValue', getLastMonthEndDate());
			}
			else if (time_group == 7){
				$('#skssj').datebox('setValue', getYearStartDate());
				$('#sjssj').datebox('setValue', getYearEndDate());
			}
		}
	})




	$(function() {
		var curr_time = new Date();
		var strDate = curr_time.getFullYear() + "";
		strDate += curr_time.getMonth() +1 + "";
		strDate += curr_time.getDate()-1;

		var strendDate = curr_time.getFullYear() + "";
		strendDate += curr_time.getMonth()+1 + "";
		strendDate += curr_time.getDate()-1;
		$("#skssj").datebox("setValue", '');
		$("#sjssj").datebox("setValue", '');

		var skssj = $('#skssj').datebox('getValue');
		var sjssj = $('#sjssj').datebox('getValue');


		loadData({start_date:'',end_date:''});//初始化加载数据
	});

</script>
<script type="text/plain" id="myEditor"></script>
<script type="text/plain" id="upload_ue"></script>

<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/public.js?v=${applicationScope.v}"></c:url>'></script>

<script type="text/javascript" src='<c:url value="/static/report/report_tradwater.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>