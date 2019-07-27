<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${applicationScope.T}</title>
    <%@include file="../../../common/common.jsp"%>
	<style>
		body{margin:0;padding:0;}
		h3{font-size:18px;margin:0;padding:0}
		.page-header{ margin:0 0 20px; padding:18px 30px; overflow:hidden; font-size:12px;border-bottom:1px solid #e9e9e9;}
		.page-title{ float:left;}
        .page-title h3{ font-size:22px; color:#474747; }
        .page-title span{ display: inline-block; margin-top:1px; color:#888;}
		.page-stats{ float:right;}
		.page-stats div{float:left;padding:0 45px;background:url('<c:url value="/static/images/homeborder.png?v=${applicationScope.v}"></c:url>') no-repeat center right;}
		.page-stats .last-child{padding-right:0;background:0;}
		.page-stats span{color:#808080;font-weight:100;}
		.page-stats h3{ font-size:14px; color:#333;font-weight:100;}
		
		.page-nav{ margin:0 30px 10px; padding:15px 0;}
		.page-nav span{ font-weight:bold; font-size:16px; color:#474747;}
        .page-nav i{ margin:-3px 10px 0 0; }

		.page-data{ margin:0 30px 30px 30px;padding:20px 10px 0;height:168px;background-color: #f5f5f5;} 
		.page-data .data-list{ float:left; width:25%;} 
		.page-data .data-bor{display:block;margin:0 auto; width:146px; min-height:146px;text-decoration: none; text-align: center; border-radius:100%; border:1px solid #fff;background-color: #fff; transition:none; } 
		.page-data .data-bor:hover{ border:1px solid #fcbc56;background-color: #fcbc56;}
        .page-data .data-bor:hover h3{ color:#fff; }
        .page-data .data-bor:hover span{ color:#fff; }
		.page-data .data-bor h3{ margin:45px 0 2px; font-weight: 100; font-size:24px; color:#ffb033;font-weight: bold;}
		.page-data .data-bor span{display:block; margin-bottom:16px; font-size:12px; color:#333;}
		/*.page-data .data-bor .data-more{display:none;}*/
		/*.page-data .data-bor:hover .data-more{display:block;}*/
		
		.page-info{ margin:0 30px 56px 30px; padding:10px 0; background-color:#f5f5f5;}
		.page-info h3{ padding:0 0 10px 38px; font-weight: 100; font-size:14px; color:#333;}
		.page-info .info-list{ margin-right:33px; overflow: hidden;}
		.page-info .info-list span{ float:left; margin-bottom:5px; width:33.3333333%;white-space:nowrap;text-overflow:ellipsis; overflow: hidden;}
		.page-info .info-list a{ margin-left:33px; font-size:14px;color: #333;text-decoration: none;}
        .page-info .info-list a:hover {color: #19c8be; text-decoration:underline; }
        .page-info .icon_listinfo{ display:inline-block; margin:-3px 7px 0 0; width:14px; height:14px; vertical-align:middle; background:url(static/images/icon_listen.png) no-repeat center;}
		.stats-box{overflow:hidden; margin:0 10px 30px 10px;}
		.stats-box div{float:left;width:25%;}
		.stats-box b{display:block;margin:0 20px; background:url('<c:url value="/static/easyui/themes/icons/icon_v2_home01.png?v=${applicationScope.v}"></c:url>') no-repeat center; background-size:100% 100%;}
		.stats-box span{display:block;color:#fff;font-weight:100; padding:28px 20px 1px 20px;font-size:14px;}
		.stats-box h3{color:#fff;font-weight:100;padding:1px 20px 28px 20px;font-weight: bold;}
		.stats-box a{display:block;padding: 5px 10px;font-size: 12px;color: #999;background-color: #f9f9f9;border-top: 1px solid #d9d9d9;text-decoration:none;font-weight:100;}
		.stats-box a:hover{ color:#333;}
		
		.stats-chart{ padding:0 0 20px 0; float:left; width:50%;}
		.stats-right{ float:left; width:50%;}
	</style>
</head>
<body>
    <div class="page-header">
        <div class="page-title">
            <h3>你好,${loginName}!</h3>
        </div>
        <div class="page-stats">
            <div>
                <span>当前登录IP</span>
                <h3>${currentIp }</h3>
            </div>
            <div>
                <span>上次登录IP</span>
                <h3>${userIp }</h3>
            </div>
            <div>
                <span>上次登陆时间</span>
                <h3>${loginDate }</h3>
            </div>
        </div>
    </div>
    <div class="page-nav">
        <span><i class="icon_home_list01"></i>信息总览</span>
    </div>
    <div class="stats-box">
        <div>
            <b>
                <span>员工总数（人）</span>
                <h3 tab='total_client' id="total_client">loading</h3>
            </b>
        </div>
        <div>
            <b>
                <span>客户总资金量（元）</span>
                <h3 tab='total_balance' id="total_balance">loading</h3>
            </b>
        </div>
        <div>
            <b>
                <span>交易金额（元）</span>
                <h3 tab='total_fee' id="total_fee">loading</h3>
            </b>
        </div>
		<div>
		    <b>
		        <span>我的金额（元）</span>
		        <h3 tab='balance' id="balance">loading</h3>
		    </b>
		</div>
    </div>
    <div class="page-nav">
        <span><i class="icon_home_list02"></i>今日数据</span>
    </div>
    <div class="page-data">
        <div class="data-list">
        	<a class="data-bor" title="新注册用户" data-menutitle="用户信息管理">
                <h3 tab='new_user' id="new_user">loading</h3>
                <span>新注册用户 (人)</span>
            </a>
        </div>
    </div>
    <!--信息详情查看 -->
    <div id="msg" class="easyui-window" title="系统公告" closed="true" style="width:800px;height:400px;padding:20px;" data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
    </div>
  	<div class="page-nav">
       <span><i class="icon_home_list03"></i>新注册用户</span>
    </div>
    <div id="main1" class="stats-main" style="height:300px;"></div>
    <div id="main3" class="stats-main" style="height:300px;"></div>
    <div class="page-nav">
        <span><i class="icon_home_list04"></i>系统公告</span>
    </div>
     <div class="page-info">
        <div class="info-list">
            <c:forEach items="${info}" var="a" varStatus="s">
                <span><a href="javascript:;" item-cid="${a.center_id}" item-title="${a.center_title }" item-date="${a.create_date }" >${a.center_title}</a></span>
            </c:forEach>
        </div>
    </div> 
    <script type="text/javascript" src="static/echarts/echarts.js"></script>
    <script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
	<script type="text/javascript">
	var context="";
	var title="";
	var create_date="";
	
	$(".page-data .j-data-bor").click(function () {
		window.top.addt($(this).attr('href'),$(this).attr('data-menutitle'));
		return false;
	});
    //信息详情查看
    $(document).on("click","[item-cid]",function () {
    	var $this = $(this);
    	var center_id=$this.attr("item-cid");    
    	check(center_id);
    });
    function check(center_id){
    	$.ajax({
            type: "POST",
            url: '${pageContext.request.contextPath}'+"/queryMsgInfo",
            dataType: 'json',
            data:{center_id:center_id},
            success: function (msg) {               
                context =msg[0].center_text;
	        	title =msg[0].center_title;
	        	create_date= msg[0].create_date;  
	        	show();
            }
        });   
    };
    function show(){
		var html = 
			'<div class="lay_home_info_list">'+
				'<div class="info_title">'+title+'</div>'+
				'<div class="info_date">'+create_date+'</div>'+
				'<div class="context">'+context+'</div>'+
			'</div>';
              $("#msg").html(html);
              $('#msg').window({
                  top:($(window).height() - 400) * 0.5+$(document).scrollTop(),   
                  left:($(window).width() - 800) * 0.5
               });
              $('#msg').window('open');
    };
    
    function initEchartsBar(date,num,showTarget){
        // 路径配置
        require.config({
            paths: {
            	echarts: 'static/echarts'
            }
        });
        // 使用
        require(
            [
    			'echarts',
    			'echarts/theme/macarons',
    			'echarts/chart/bar'
            ],
            function (ec,theme) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById(showTarget),theme);

                option = {
               	    tooltip : {
               	        trigger: 'axis',
               	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
               	            type : 'shadow'      // 默认为直线，可选为：'line' | 'shadow'
               	        }
               	    },
                    grid:{
                    	x:65,
                     	x2:30,
                     	y:40
                    },
                    legend: {
                        data:['总仓储费','模式一仓储费']
                    },
               	    xAxis : [
               	        {
               	            type : 'category',
               	            data : date
               	        }
               	    ],
               	    yAxis : [
               	        {
               	            type : 'value'
               	        }
               	    ],
               	    series : [
	      						{
	               	            name:'模式一仓储费',
	               	            type:'bar',
	               	            stack: '仓储费',
	               	            data:num,
								itemStyle: {
									normal: {color:'#ff7f50'}
								}
	                	    },
{
							name:'总仓储费',
							type:'line',
							data:num,
							itemStyle: {
								normal: {color:'#60cec8'}
							}
						}
               	    ]
               	};

                // 为echarts对象加载数据 
                myChart.setOption(option);
                $(window).resize(function(){
                	myChart.resize();
				});
            }
        );
    };
    
    function initEcharts(xAxisData,yAxisData,showTarget){
        // 路径配置
        require.config({
            paths: {
            	echarts: 'static/echarts'
            }
        });
        // 使用
        require(
            [
    			'echarts',
    			'echarts/theme/macarons',
    			'echarts/chart/line'
            ],
            function (ec,theme) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById(showTarget),theme);
                var option = {
               		tooltip: {
                        show: true
                    },
                    calculable : true,
                    grid:{
                    	x:65,
                     	x2:30,
                     	y:40
                    },
                    legend: {
                        data:['人数']
                    },
                    xAxis: [
                        {
                            type: 'category',
                            data: xAxisData,
                            boundaryGap:false
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value',
                           	axisLabel : {
                                formatter: '{value}'
                            }
                        }
                    ],
                    series: [
                        {
                            "name": "人数",
                            "type": "line",
                            "data": yAxisData
                        }
                    ]
                };
                // 为echarts对象加载数据 
                myChart.setOption(option);
                $(window).resize(function(){
                	myChart.resize();
				});
            }
        );
    };

	$.ajax({
        type: "POST",
        url: '${pageContext.request.contextPath}'+"/newUserlist",
        dataType: 'json',
        success: function (msg) {
        	initEcharts(msg.date,msg.num,"main1");
        }
    });
	
	$.ajax({
        type: "POST",
        url: '${pageContext.request.contextPath}'+"/listBrokerage",
        dataType: 'json',
        success: function (msg) {
            console.log(msg);
            //initEchartsBar(msg.date,msg.num,"main3");
        }
    }); 
	
	$.ajax({
        type: "POST",
        url: '${pageContext.request.contextPath}'+"/queryNewsAndData",
        dataType: 'json',
        success: function (msg) {
        	$("#total_client").html(msg.total_client); 
        	$("#total_balance").html(fmoney(msg.total_balance,2));
        	$("#total_fee").html(fmoney(msg.total_fee,2));
        	$("#balance").html(fmoney(msg.balance,2));
        	$("#new_user").html(msg.new_user);
        	$("#brokerage").html(fmoney(msg.brokerage,2));
        	$("#withdraw_amount").html(fmoney(msg.withdraw_amount,2));
        	$("#charge_amount").html(fmoney(msg.charge_amount,2));
        }
    });
    </script>
</body>
</html>
