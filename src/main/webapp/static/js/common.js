//全局监控
$(document)
.on('ajaxComplete', function(e, xhr, options){
	if(xhr.status === 401){
		xhr.abort();
		$.messager.alert('提示', "没有权限操作此功能", 'error');
	}
	if(xhr.status === 403){
		xhr.abort();
		$.messager.alert('提示', "请重新登录", 'error',function(){
			window.top.location.href="login";
		});
	}
	if(xhr.status === 404){
		xhr.abort();
		$.messager.alert('提示', "系统异常,请联系客服", 'error',function(){});
	}
	if(xhr.status === 405){
		xhr.abort();
		$.messager.alert('提示', "非法操作", 'error');
		window.top.location.href="../405";
	}
	if(xhr.status === 505){
		xhr.abort();
		window.top.location.href="../505";
	}
	if(xhr.status === 500){
		xhr.abort();
		$.messager.alert('提示', "系统异常,请联系客服", 'error',function(){});
	}
});

//是否可用公共下拉框
$('#rechargeWithdrawType').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '1',
		value : '用户'
	}, {
		label : '2',
		value : '经纪人'
	} ]
});

//通过和未通过,旧版的结算和代理商用
$('#fundtype_old').combobox({
	width : '100',
	height : '20',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '05',
		value : '申请金额'
	}, {
		label : '07',
		value : '未通过金额'
	} ]
});

//出入金类型
$('#fundtype').combobox({
    width : '100',
    height : '23',
    valueField : 'label',
    textField : 'value',
    panelHeight : 'auto',
    editable: true,
    data : [ {
        label : '05',
        value : '提现'
    }, {
        label : '09',
        value : '退款'
    } ]
});

//订单类型
$('#order_type').combobox({
    width : '150',
    height : '23',
    valueField : 'label',
    textField : 'value',
    panelHeight : 'auto',
    editable: false,
    data : [ {
        label : '1',
        value : '回退订单'
    }, {
        label : '2',
        value : '正常订单'
    } ]
});

//渠道
$('#channal').combobox({
    width : '150',
    height : '23',
    valueField : 'label',
    textField : 'value',
    panelHeight : 'auto',
    editable: false,
    multiple:true,
    data : [ {
        label : '微支付',
        value : '微支付'
    }]
});

//级别
$('#level').combobox({
    width : '150',
    height : '23',
    valueField : 'label',
    textField : 'value',
    panelHeight : 'auto',
    editable: false,
    data : [{
        label : '1',
        value : '低'
    } , {
        label : '2',
        value : '中'
    } , {
        label : '3',
        value : '高'
    } ]
});


$('#rechargeWithdraw').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : '100',
	editable: false,
	data : [ {
		label : '1',
		value : '充值'
	}, {
		label : '2',
		value : '提现'
	} ]
});
$('#userType').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	required : 'true',
	panelHeight : '100',
	editable: false,
	data : [ {
		label : '1',
		value : '用户'
	}, {
		label : '2',
		value : '经纪人'
	} ]
});
//提现方式
$('#withdraw_way').combobox({
	width : '120',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '01',
		value : '易极付'
	}, {
		label : '02',
		value : '微支付'
	},
	{
		label : '03',
		value : '财趣提现'
	},
	{
		label : '04',
		value : '摩宝'
	},
	]
});

$('#is_activity').combobox({
    width : '150',
    height : '30',
    valueField : 'label',
    textField : 'value',
    required : 'true',
    panelHeight : 'auto',
    editable: false,
    data : [ {
        label : '1',
        value : '是'
    }, {
        label : '0',
        value : '否'
    } ]
});

$('#is_use').combobox({
	width : '150',
	height : '30',
	valueField : 'label',
	textField : 'value',
	required : 'true',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '1',
		value : '是'
	}, {
		label : '0',
		value : '否'
	} ]
});

$('#item_is_use').combobox({
    width : '150',
    height : '30',
    valueField : 'label',
    textField : 'value',
    required : 'true',
    panelHeight : 'auto',
    editable: false,
    data : [ {
        label : '1',
        value : '是'
    }, {
        label : '0',
        value : '否'
    } ]
});

$('#spec_is_use').combobox({
    width : '150',
    height : '30',
    valueField : 'label',
    textField : 'value',
    required : 'true',
    panelHeight : 'auto',
    editable: false,
    data : [ {
        label : '1',
        value : '是'
    }, {
        label : '0',
        value : '否'
    } ]
});

$('#is_important').combobox({
	width : '150',
	height : '30',
	valueField : 'label',
	textField : 'value',
	required : 'true',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '1',
		value : '是'
	}, {
		label : '0',
		value : '否'
	} ]
});
$('#week').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	required : 'true',
	panelHeight : '100',
	editable: false,
	data : [ {
		label : '1',
		value : '星期一'
	}, {
		label : '2',
		value : '星期二'
	}, {
		label : '3',
		value : '星期三'
	}, {
		label : '4',
		value : '星期四'
	}, {
		label : '5',
		value : '星期五'
	}, {
		label : '6',
		value : '星期六'
	}, {
		label : '7',
		value : '星期天'
	} ]
});
//性别公共下拉框
$('#user_gender').combobox({
	width : '150',
	height : '30',
	valueField : 'label',
	textField : 'value',
	required : 'true',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '01',
		value : '男'
	}, {
		label : '02',
		value : '女'
	} ]
});

//部门下拉框
$('#dept_id').combotree({
	url : 'queryDeptInfo',
	width : '150',
	height : '30',
	//multiple:true,
	editable : true,
	required: true,
	panelWidth : '260px',
	panelHeight : '260px'
});


//查询部门下拉框
$('#idept_id').combotree({
	url : 'queryDeptInfo',
	width : '150',
	height : '30',
	//multiple:true,
	editable : true,
	panelWidth : '260px',
	panelHeight : '260px'
    //onLoadSuccess: function (row, data) {
    	/*var node = $('#idept_id').tree('find', 1);

    	$('#idept_id').tree('select', node.target);*/
    	//$('#idept_id').combotree('tree').tree("collapseAll");
    //}
});

//是否推荐人,去掉不为空
$('#iis_recommend').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	editable: false,
	panelHeight : 'auto',
	onSelect: function(rec){
		if(rec.label=='0'){
			$('#recommend_allot').numberbox('disable');
			$('#recommend_allot').numberbox('setValue',0);
		}else{
			$('#recommend_allot').numberbox('enable');
		}
    },
	data : [ {
		label : '1',
		value : '是'
	}, {
		label : '0',
		value : '否'
	} ]
});

//是否子菜单
$('#is_leaf').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	required : 'true',
	panelHeight : 'auto',
	editable : false,
	data : [ {
		label : '1',
		value : '是'
	}, {
		label : '0',
		value : '否'
	} ]
});

//是否子菜单
$('#is_leaf').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	required : 'true',
	panelHeight : 'auto',
	editable : false,
	data : [ {
		label : '1',
		value : '是'
	}, {
		label : '0',
		value : '否'
	} ]
});
//问题位置
$('#location').combobox({
	width : '150',
	height : '23',
	valueField : 'value',
	textField : 'label',
	panelHeight : 'auto',
	editable : false,
	data : [ {
		label : '前台',
		value : '1'
	}, {
		label : '后台',
		value : '0'
	} ]
});

//是否解决
$('#is_solve').combobox({
	width : '150',
	height : '23',
	valueField : 'value',
	textField : 'label',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '是',
		value : '1'
	},
	{
		label : '否',
		value : '0'
	}]
});

//文章类型
$('#textType').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ 
	{
		label : '04',
		value : '后台通知'
	},
	{
		label : '03',
		value : '信息中心'
	}]
});

//是否是经纪人
$('#is_agent').combobox({
	width : '120',
	height : '23',
	valueField : 'value',
	textField : 'label',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '经纪人',
		value : '1'
	}, 
	{
		label : '用户',
		value : '0'
	}]
});
//提现状态下拉框
$('#apply_states').combobox({
	width : '70',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '未通过',
		value : '未通过'
	}, {
		label : '完成',
		value : '完成'
	},{
		label : '未审核',
		value : '未审核'
	},{
		label : '已审核',
		value : '已审核'
	}]
});
//审核状态
$('#is_review').combobox({
	width : '120',
	height : '23',
	valueField : 'value',
	textField : 'label',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '已审核',
		value : '0'
	}, 
	{
		label : '待审核',
		value : '1'
	}]
});

//充值渠道
$("#charge_channel").combobox({
    width: '150',
    height: '30',
    valueField: 'value',
    textField: 'label',
    panelHeight: 'auto',
    editable: false,
    data: [{
        label: '微信支付',
        value: '微信支付'
    },
    {
        label: '易极付',
        value: '易极付'
    },
    {
        label: '摩宝支付',
        value: '摩宝支付'
    }
    ]
});

//设置交易实体
$('#tid').combobox({
	url : '/platform/queryTradeItemSelect',
	valueField : 'tid',
	textField : 'item_type_name',
	width : '150',
	height : '23',
	editable : false,
	required : true,
	panelHeight : 'auto'
});

//查询银行下拉框
$('#bank').combobox({
    url : '/withdraw/queryBankSelect',
    valueField : 'dict_id',
    textField : 'dict_name',
    width : '150',
    height : '23',
    editable : false,
    required : true,
    panelHeight : '100px'
});

$('#bank1').combobox({
    url : '/withdraw/queryBankSelect',
    valueField : 'dict_id',
    textField : 'dict_name',
    width : '150',
    height : '23',
    editable : false,
    required : false,
    panelHeight : '100px'
});

$('#ztid').combobox({
	url : '/platform/queryTradeItemSelect',
	valueField : 'tid',
	textField : 'item_type_name',
	width : '150',
	height : '23',
	editable : false,
	panelHeight : 'auto'
});

$('#recommend_allot').numberbox({
	onChange:function(newValue,oldValue){
		if(newValue>100||newValue<0){
			$.messager.alert('信息提示', '占比范围为0-100', 'info');
			$('#recommend_allot').numberbox('setValue',0);
		}
	}
});

//交易模式下拉框
$('#CID').combobox({
	width : '110',
	height : '23',
	valueField : 'value',
	textField : 'label',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '全部',
		value : '0'
	}, {
		label : '模式二',
		value : '02'
	},{
		label : '模式一',
		value : '01'
	}]
});

//提现角色
$('#role_type').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '01',
		value : '用户'
	}, {
		label : '02',
		value : '经纪人'
	} ]
});
//审核状态
$('#review_status').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	onLoadSuccess:function(){
	$(review_status).combobox('select',1);
		},
	data : [ {
		label : '1',
		value : '待审核'
	}, {
		label : '0',
		value : '已审核'
	} ]
});

//提现方式
$('#tx_type').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: true,
	data : [ {
		label : 'wechat',
		value : '微支付'
	},{
		label : 'yimadai',
		value : '一麻袋'
	},{
        label : 'chinapay',
		value : '银联'
    },{
		label : 'jytpay',
		value : '金运通'
	},{
        label : 'unionpay3',
        value : '益润茂银联'
    },{
        label : 'jytpayyrm',
        value : '益润茂金运通'
    },{
        label : 'jytpayjsxm',
        value : '久笙金运通'
    },{
        label : 'chinapayyyx',
        value : '盈裕鑫银联'
    }]
});

//充值方式
// $('#cz_type').combobox({
// 	width : '150',
// 	height : '23',
// 	valueField : 'label',
// 	textField : 'value',
// 	editable: true,
// 	data : [ {
// 		label : 'wechat',
// 		value : '微支付'
// 	},{
// 		label : 'yimadai',
// 		value : '一麻袋'
// 	} ,{
// 		label : 'ecpsspay',
// 		value : '汇潮'
// 	},{
// 		label : 'unionpay',
// 		value : '银联'
// 	},{
// 		label : 'swiftpass',
// 		value : '威富通'
// 	},{
// 		label : 'yijipay',
// 		value : '易极付'
// 	},{
// 		label : 'yoyipay',
// 		value : '甬易'
// 	},{
// 		label : 'znpay',
// 		value : '中南'
// 	}
// 	,{
// 		label : 'znalipay',
// 		value : '中南支付宝'
// 	},
// 	{
// 		label : 'QQpay',
// 		value : 'qq钱包'
// 	},
// 	{
// 		label : 'unionpay2',
// 		value : '武汉银联'
// 	},
// 	{
// 		label : 'qrwechatpay',
// 		value : '微信扫码'
// 	},
//     {
//         label : 'unionpay3',
//         value : '益润茂银联'
//     },
//     {
//          label : 'znwxpayymd',
//          value : '誉茂达中南微信'
//   	},
//     {
// 		 label : 'znalipayymd',
// 		 value : '誉茂达中南支付宝'
// 	},
// 	{
//     	 label:'jbqqpayyrmb',
// 	     value : '捷宝扫码'
// 	},
// 	{
//           label:'znwxpayqyjt',
//           value: '千钰金泰微信扫码'
// 	},
// 	{
// 		label:'znalipayqyjt',
// 		value: '千钰金泰支付宝扫码'
// 	},
//     {
//             label:'ouchuangpay',
//             value: '欧创快捷支付'
// 	},
// 		{
//             label:'jytpayyrm',
//             value: '益润茂金运通支付'
//
// 		},{
//
//             label:'zhqqpayymd',
//             value: '智慧QQ钱包'
//
// 		},{
//
//             label:'zhquickpayymd',
//             value: '智慧快捷支付'
//
// 		}
//
// 	]
// });

//提现方式
$('#num').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : 'more',
		value : '是'
	},{
		label : 'one',
		value : '否'
	} ]
});

//岗位
$('#position_yg').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '1',
		value : '经理'
	},{
		label : '2',
		value : '主管'
	},{
		label : '3',
		value : '组员'
	} ,{
		label : '4',
		value : '人事'
	} ,{
		label : '9',
		value : '其他'
	} ]
});

//所属品牌
$('#brindnameid_pp').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [ {
		label : '0',
		value : '全选'
	},{
		label : '1',
		value : '中汇'
	},{
		label : '2',
		value : '瑞和宝'
	},{
		label : '3',
		value : '星支付'
	} ,{
		label : '4',
		value : '星通宝'
	} ,{
		label : '5',
		value : '海科融通'
	} ,{
		label : '6',
		value : '乐刷'
	} ,{
		label : '7',
		value : '卡友'
	} ,{
		label : '8',
		value : '喔刷'
	} ,{
		label : '9',
		value : '拉卡拉'
	} ]
});

function expData(url){
    $.messager.confirm('确认导出','将要导出最近500条数据,是否继续?',function(r){
		if(r){
			windows.location.href=url; 
		}else{
			window.location.reload();
		}
	});
}

/**
 * 时间格式化 
 * @param value
 * @returns {String}
 */
function dateTimeFormatter(value) {
	if(value!=undefined){
		
    var date = new Date(value);
    var year = date.getFullYear().toString();
    var month = (date.getMonth() + 1);
    var day = date.getDate().toString();
    var hour = date.getHours().toString();
    var minutes = date.getMinutes().toString();
    var seconds = date.getSeconds().toString();
    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }
    if (hour < 10) {
        hour = "0" + hour;
    }
    if (minutes < 10) {
        minutes = "0" + minutes;
    }
    if (seconds < 10) {
        seconds = "0" + seconds;
    }
    return year + "/" + month + "/" + day + " " + hour + ":" + minutes + ":" + seconds;
	}else
	{
		return "";
	}
};

/**
 * 时间格式化
 * @param value
 * @returns {String}
 */
function dateTimeFormatter2(value) {
	if(value!=undefined){

		var date = new Date(value);
		var year = date.getFullYear().toString();
		var month = (date.getMonth() + 1);
		var day = date.getDate().toString();

		if (month < 10) {
			month = "0" + month;
		}
		if (day < 10) {
			day = "0" + day;
		}

		return year + "-" + month + "-" + day ;
	}else
	{
		return "";
	}
};

$.fn.extend({
	//表格内容的title
    "overTitle": function () {
    	$(this).each(function(){
    		$(this).attr("title",$(this).find("div").html());
    	});
    },
    "overDialog":function(contents){
    	$(this).mouseover(function(){
    		//var imgUrl=$(".datagrid-body td[field='goods_url']");
    		var top=$(this).offset().top;
    		var left=$(this).offset().left;
    		if(!$(".over_dialog").length){
	        	$("body").append(
		    	'<div class="over_dialog"><p class="p10">加载中...</p></div>');
    		}
    		var overDialog=$(".over_dialog");
    		overDialog.html(contents);
    		$(".over_dialog_img").find("img").attr("src",$(this).find("div").html());
    		if(overDialog.outerHeight()+top<=$(window).height()){
    			top=top+$(this).height();
    		}else{
    			top=top-overDialog.outerHeight();
    		}
    		overDialog.css({top:top,left:left}).show();
		});
    	$(this).mouseout(function(){
	    	$(".over_dialog").hide();
		});
	},
	"loading":function(open){
		if(open=="close"){
			$(".datagrid-mask,.datagrid-mask-msg").remove();
			return false;
		}
		$(this).append('<div class="datagrid-mask"></div><div class="datagrid-mask-msg"><div class="sk-bounce1"></div>&nbsp;<div class="sk-bounce2"></div>&nbsp;<div class="sk-bounce3"></div></div>');
		var maskMsg=$(".datagrid-mask-msg");
		maskMsg.css({
			left:"50%",
		    marginLeft: -(maskMsg.width()/2)
		}).show();
		$(".datagrid-mask").show();
	}
});

$("body").keydown(function (event) {
    var e = event || window.event || arguments.callee.caller.arguments[0];
    if (e && e.keyCode == 27) { // 按 Esc
    	$('.window .easyui-window').window('close');
        return false;
    }
});

function fmoney(s, n)
{
	n = n > 0 && n <= 20 ? n : 2;
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
	t = "";
	
	for (i = 0; i < l.length; i++) {
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
	}
	t=t.replace(",-","-");
    return t.split("").reverse().join("") + "." + r;  
};

function rmoney(s) {  
    return parseFloat(s.replace(/[^\d\.-]/g, ""));  
}  




/** 
 * combobox和combotree模糊查询 
 */  
(function(){  
    //combobox可编辑，自定义模糊查询  
    $.fn.combobox.defaults.editable = true;  
    $.fn.combobox.defaults.filter = function(q, row){  
        var opts = $(this).combobox('options');  
        return row[opts.textField].indexOf(q) >= 0;  
    };  
    //combotree可编辑，自定义模糊查询  
    $.fn.combotree.defaults.editable = true;  
    $.extend($.fn.combotree.defaults.keyHandler,{  
        up:function(){  
            console.log('up');  
        },  
        down:function(){  
            console.log('down');  
        },  
        enter:function(){  
            console.log('enter');  
        },  
        query:function(q){  
            var t = $(this).combotree('tree');  
            var nodes = t.tree('getChildren');  
            for(var i=0; i<nodes.length; i++){  
                var node = nodes[i];  
                if (node.text.indexOf(q) >= 0){  
                    $(node.target).show();  
                } else {  
                    $(node.target).hide();  
                }  
            }  
            var opts = $(this).combotree('options');  
            if (!opts.hasSetEvents){  
                opts.hasSetEvents = true;  
                var onShowPanel = opts.onShowPanel;  
                opts.onShowPanel = function(){  
                    var nodes = t.tree('getChildren');  
                    for(var i=0; i<nodes.length; i++){  
                        $(nodes[i].target).show();  
                    }  
                    onShowPanel.call(this);  
                };  
                $(this).combo('options').onShowPanel = opts.onShowPanel;  
            }  
        }  
    });  
})(jQuery);  