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
			<form id="search" method="post">
				<span class="lay_text_bor mr20">服务商选择：<input class="easyui-combobox" id="settle_id" name="settle_id" data-options="valueField:'id',textField:'text'" style="width:150px;" /></span>
				<span class="lay_text_bor mr20">代理商选择：<input class="easyui-combobox"  id="agent_id"  name="agent_id" data-options="valueField:'id',textField:'text'" style="width:150px;" /></span>
				<span class="lay_text_bor mr20">位属部门：<input class="easyui-combobox" id="DID" name='DID' style="width:150px;" data-options="valueField:'id',textField:'text'" /></span>
				<!--<span class="lay_text_bor mr10">日期选择：<input class="easyui-datebox" name="begindate" data-options="editable:false,showSeconds:false" style="width:150px;" id="skssj"/></span>  -->			
				<!--  <span class="lay_text_bor mr10">日期选择：<input class="easyui-datetimespinner" name="begindate" value="6/24/2014" data-options="formatter:formatter2,parser:parser2,selections:[[0,4],[5,7]]" style="width:150px;"></span>-->
				<span  class="lay_text_bor mr10">日期选择:
				<input editable="false" class="easyui-datebox" data-options="require:true" type="text" name="begindate" id="begindate"  style="width:100px;"></input>
</span>
				<span class="lay_text_bor">
					<span class="mr10"><a href="javascript:;" onclick="$('#search').submit();" class="easyui-linkbutton" iconCls="icon-search">查询</a></span>
					<!--<span class="mr10"><a href="javascript:update();" class="easyui-linkbutton mr10" data-options="plain:true,iconCls:'icon-add'">新增</a></span> -->
					<!-- <a href="javascript:search();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查询</a> -->
					<!-- <a href="javascript:excel();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'">导出</a> -->
					<span class="mr10"><a href="javascript:window.location.reload();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></span>
					<!-- <span class="mr10"><a href="#" onclick='expInfo();' class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-redo'">导出报表</a></span>-->

					<!-- 查询按钮放最前，刷新按钮放最后 -->
				</span>
				</form>
			</div>
            <div id="total" class="total">
            </div>
		</div>		
</div>
  <!-- 提现充值详情查看 -->
    <div id="txsh_detail_2" class="easyui-window" title="详情查看" closed="true" style="width:700px;padding:0;height:450px;">
    	<table id="data2" class="easyui-datagrid" title="详情列表"></table>
    </div>

<script>
/*
$('#skssj').datebox({ 
     onChange:function (value){
        //事件  
    	alert(value);
        var curr_time = new Date();
		var strDate = curr_time.getYear() + "-";
		alert(strDate);
		strDate += curr_time.getMonth() + 1;	
		//strDate
 		$('#skssj').datebox('setValue',strDate); 	
     }
});

function formatter2(date){
	if (!date){return '';}
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	return y + '-' + (m<10?('0'+m):m);
}
function parser2(s){
	if (!s){return null;}
	var ss = s.split('-');
	var y = parseInt(ss[0],10);
	var m = parseInt(ss[1],10);
	if (!isNaN(y) && !isNaN(m)){
		return new Date(y,m-1,1);
	} else {
		return new Date();
	}
}

$(function(){
    var currTime=new Date();
    var strDate=currTime.getFullYear()+"-"+(currTime.getMonth()+1)+"-01";
    $('#dateid').datebox({formatter:function(date){
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? '0' + m : m;
        return y.toString() + '-' + m.toString();
    },parser:function(date){

        if (date) {
            return new Date(String(date).substring(0, 4) + '-'
                    + String(date).substring(5,7));
        } else {
            return new Date();
        }
    }});
    
    var p = $('#dateid').datebox('panel'), //日期选择对象
    tds = false, //日期选择对象中月份
    yearIpt = p.find('input.calendar-menu-year'),//年份输入框
    span = p.find('span.calendar-text'); //显示月份层的触发控件
    $('#dateid').datebox('setValue',strDate);//默认加载当前月份
    
});

*/
$(function () {
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
});
</script>	
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/js/report.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/static/report/report_conter_fee_dept_info.js?v=${applicationScope.v}"></c:url>'></script>



</body>
</html>