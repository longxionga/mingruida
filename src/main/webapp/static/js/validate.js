
//拓展String长度方法,中文为2,英文为1
String.prototype.gblen = function() {  
  var len = 0;  
  for (var i=0; i<this.length; i++) {  
    if (this.charCodeAt(i)>127 || this.charCodeAt(i)==94) {  
       len += 2;  
     } else {  
       len ++;  
     }  
   }  
  return len;  
}

function log(m) {
	if (window.console && window.console.log) {
		window.console.log(m);
	}
	else if (window.opera && window.opera.postError) {
		window.opera.postError(m);
	}
}

function getFormJson(form) {
	var o = {};
	var a = $(form).serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
}

$(document).on("submit","[data-autoform]",function(){
	var $this = $(this);
	if(!$this.form("validate")){
		return false;
	}
	var param = getFormJson(this);//获取表单元素的值 json对象
	var option = $this.data("autoform"); //获取表单参数
	var action = $this.attr("action"); //获取表单提交接口
	
	option =  eval("({"+option+"})"); 
	
	$.ajax({
		url : action,
		type : "POST",
		data : param,
		
		success:function(data) {
			option.callback(data,param);
			return false;
		}
	});
	
	return false;
});


//自定义表单验证
$(function(){
	var jsonData;
	$.extend($.fn.validatebox.defaults.rules, {
		// 验证身份证
		idcard: {
	        validator: function (value) {
	            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
	        },
	        message: '身份证号码格式不正确'
	    },
	    minLength: {
	        validator: function (value, param) {
	            return value.length >= param[0];
	        },
	        message: '请输入至少（2）个字符.'
	    },
	   length: { validator: function (value, param) {
	        var len = $.trim(value).gblen();
	        return len >= param[0] && len <= param[1];
	    },
	        message: "输入内容长度必须介于{0}和{1}之间."
	    },
	    //验证电话号码
	    phone: {
	        validator: function (value) {
	            return /^((\d2,3)|(\d{3}\-))?(0\d2,3|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
	        },
	        message: '格式不正确,请使用下面格式:020-88888888'
	    },
	    //验证手机号码
	    mobile: {
	        validator: function (value) {
	            return /^(13|15|18|17)\d{9}$/i.test(value);
	        },
	        message: '手机号码格式不正确'
	    },
	    //验证整数或小数
	    intOrFloat: {
	        validator: function (value) {
	            return /^\d+(\.\d+)?$/i.test(value);
	        },
	        message: '请输入数字，并确保格式正确'
	    },
	  //验证整数
	    int: {
	        validator: function (value) {
	            return /^\d+$/i.test(value);
	        },
	        message: '请输入数字，并确保格式正确'
	    },
	    inta: {
	        validator: function (value) {
	            return /^\d{1,4}$/i.test(value);
	        },
	        message: '请输入数字4位，并确保格式正确'
	    },
	    intb: {
	        validator: function (value) {
	            return /^\d{1,2}$/i.test(value);
	        },
	        message: '请输入数字2位，并确保格式正确'
	    },
	    intgto:{
	    	validator: function (value) {
	            if(!/^\d+(\.\d+)?$/i.test(value))
	            	return false;
	            var ival= parseInt(value);
	            if(ival<100)
	            	return false;
	            return true;
	        },
	        message: '请输入大于等于100数字'
	    },

        //大于等于20小于等于100的数字.
        settle_ratio:{
            validator: function (value) {
                if(!/^\d+(\.\d+)?$/i.test(value))
                    return false;
                var ival= parseInt(value);
                if(ival<20)
                    return false;
                return true;
            },
            message: '单位初始占比不能低于20%!'
        },

        //大于等于0小于等于100的数字
	    percentage:{
	    	validator: function (value) {
	            if(!/^\d+(\.\d+)?$/i.test(value))
	            	return false;
	            var ival= parseInt(value);
	            if(ival>100||ival<0)
	            	return false;
	            return true;
	        },
	        message: '请输入0到100之间数字'
	    },
	   //验证货币
	    currency: {
	        validator: function (value) {
	            return /^\d+(\.\d+)?$/i.test(value);
	        },
	        message: '货币格式不正确'
	    },
	   //验证QQ,从10000开始
	    qq: {
	        validator: function (value) {
	            return /^[1-9]\d{4,9}$/i.test(value);
	        },
	        message: 'QQ号码格式不正确'
	    },
	  //验证整数 可正负数
	    integer: {
	        validator: function (value) {
	            //return /^[+]?[1-9]+\d*$/i.test(value);
	            return /^([+]?[0-9])|([-]?[0-9])+\d*$/i.test(value);
	        },
	        message: '请输入整数'
	    },
	  //验证年龄
	    age: {
	        validator: function (value) {
	            return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
	        },
	        message: '年龄必须是0到120之间的整数'
	    },
	  //验证中文
	    chinese: {
	        validator: function (value) {
	            return /^[\Α-\￥]+$/i.test(value);
	        },
	        message: '请输入中文'
	    },
	  //验证英语
	    english: {
	        validator: function (value) {
	            return /^[A-Za-z]+$/i.test(value);
	        },
	        message: '请输入英文'
	    },
	  //验证是否包含空格和非法字符
	    unnormal: {
	        validator: function (value) {
	        	
	        	if (value.indexOf(" ") == -1)
	        	{
	        		 return /.+/i.test(value);
	        	}else	        	
	        	{
	        		return false;
	        	}
	           
	        },
	        message: '输入值不能为空和包含其他非法字符'
	    },
	    
	    //验证是否包含空格和非法字符
	    numberLetter: {
	        validator: function (value) {
	        	var group_type=$('#group_type').combobox('getValue');
	        	var success=true;
	        	 if(group_type==1){
	        		    success= /^[A-Za-z]+$/.test(value);
			        }else if(group_type==2){
			        	success=  /^[0-9]+$/.test(value);
			        }else if(group_type==3){
			        	success= /^[A-Za-z0-9]+$/.test(value);
			        }
	           return  success;
	        },
	        message: '请输入符合规则的靓号'
	    },
	    
	    
	  //验证标题，可以为中文、英文、数字、标点符号长度
	    titles: {
	        validator: function (value) {
	        	return  /^[\u4e00-\u9fa5_a-zA-Z0-9_-？?''""“”, 《》，。.@!！￥$;:；：|、#]{1,50}$/i.test(value);
	        },
	        message: '标题不合法（包含中文、英文、数字、标点符号，长度为1到50个字符）'
	    },
	      
	  //验证用户名
	    username: {
	        validator: function (value) {
	            return /^[a-zA-Z][a-zA-Z0-9_]{1,15}$/i.test(value);
	        },
	        message: '登录名称不合法（字母开头，允许1-16字节，允许字母数字下划线）'
	    },
	    
	  //验证 100 到 99999999 (亿)之间的正整数
	    moneys:{
	    	validator: function (value) {
	    		return /^([1-9][0-9]{2,7})$/i.test(value);
	        },
	        message: '提现金额为不得小于100的整数'
	    },
	  //验证传真
	    faxno: {
	        validator: function (value) {
	            return /^((\d2,3)|(\d{3}\-))?(0\d2,3|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
	        },
	        message: '传真号码不正确'
	    },
	  //验证邮政编码
	    zip: {
	        validator: function (value) {
	            return /^[1-9]\d{5}$/i.test(value);
	        },
	        message: '邮政编码格式不正确'
	    },
	  //验证IP地址
	    ip: {
	        validator: function (value) {
	            return /d+.d+.d+.d+/i.test(value);
	        },
	        message: 'IP地址格式不正确'
	    },
	  //验证姓名，可以是中文或英文
	    name: {
	        validator: function (value) {
	            return /^[\Α-\￥]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
	        },
	        message: '请输入合法的姓名'
	    },
	  //验证姓名，去特殊字符
	    TextSearch:{
	    	validator: function(value){
	    		return /^[\u4e00-\u9fa5_a-zA-Z0-9]{1,50}$/i.test(value);
	    	},
	           message: '只允许输入中文、英文、数字'
	    },
	    //只允许输入英文和数字
	    EnglishOrint:{
	    	validator: function(value){
	    		return /^[a-zA-Z0-9]{1,50}$/i.test(value);
	    	},
	           message: '只允许输入英文、数字'
	    },
	    
	  //验证日期,格式yyyy-MM-dd或yyyy-M-d
	    date: {
	        validator: function (value) {
	            return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value);
	        },
	        message: '日期格式必须为:yyyy-MM-dd'
	    },
	    //验证msn账号
	    msn: {
	        validator: function (value) {
	            return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
	        },
	        message: '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
	    },
	    TimeCheck:{
	        validator:function(value,param){     
	         var s = $("input[name="+param[0]+"]").val();
	         //因为日期是统一格式的所以可以直接比较字符串 否则需要Date.parse(_date)转换
	         return value>=s;
	        },
	        message:'结算日期必须晚于开始日期'
	       },
	    //密码验证
	    same: {
	        validator: function (value, param) {
	            if ($("#" + param[0]).val() != "" && value != "") {
	                return $("#" + param[0]).val() == value;
	            } else {
	                return true;
	            }
	        },
	        message: '两次输入的密码不一致！'
	    },
	 
	   //远程验证公共方法
	    
	     ajax:{
	        validator:function(value, param){
	        var result = false;
	        var url = param[0];
	        var success;
	        $.ajax({
	            type: "POST",
	            url: url,
	            data: {
	                  name:value
	            }, 
	            dataType: 'json',
	            async: false,
	            success: function(msg){
	            	jsonData=msg;
	            }
	         });
	        return jsonData.success;
	        },
	        message:'名称重复'
	    },
	    mobile_check:{
	        validator:function(value, param){
	        var result = false;
	        var url = param[0];
	        var success;
	        $.ajax({
	            type: "POST",
	            url: url,
	            data: {
	                  name:value
	            }, 
	            dataType: 'json',
	            async: false,
	            success: function(msg){
	            	jsonData=msg;
	            }
	         });
	        return jsonData.success;
	        },
	        message:'手机号重复!'
	    },
		   //验证code和id是否重复
	    
	     codeId:{
	        validator:function(value, param){
	        var result = false;
	        var dict_id=document.getElementById('dict_id').value;
	        var dict_code=document.getElementById('dict_code').value;
	        var url = param[0];
	        var success;
	        $.ajax({
	            type: "POST",
	            url: url,
	            data: {
	                  dict_code:dict_code,
	                  dict_id:dict_id
	            }, 
	            dataType: 'json',
	            async: false,
	            success: function(msg){
	            	jsonData=msg;
	            }
	         });
	        return jsonData.success;
	        },
	        message:'id和编号重复'
	    }
	    ,
	    //验证code和name是否重复
	    
	     codeName:{
	        validator:function(value, param){
	        var result = false;
	        var dict_code=document.getElementById('dict_code').value;
	        var dict_name=document.getElementById('dict_name').value;
	        var url = param[0];
	        var success;
	        $.ajax({
	            type: "POST",
	            url: url,
	            data: {
	                  dict_code:dict_code,
	                  dict_name:dict_name
	            }, 
	            dataType: 'json',
	            async: false,
	            success: function(msg){
	            	jsonData=msg;
	            }
	         });
	        return jsonData.success;
	        },
	        message:'名称重复'
	    }
	    ,
		   //验证修改后的菜单名是否重复
	    
	     menuName:{
	        validator:function(value, param){
	        	
	        var result = false;
	        var menu_id=document.getElementById('menu_id').value;
	        var menu_name=document.getElementById('menu_name').value;
	        var url = param[0];
	        var success;
	        $.ajax({
	            type: "POST",
	            url: url,
	            data: {
	            	menu_id:menu_id,
	                menu_name:menu_name
	            }, 
	            dataType: 'json',
	            async: false,
	            success: function(msg){
	            	jsonData=msg;
	            }
	         });
	        return jsonData.success;
	        },
	        message:'菜单名称不可重复'
	    }
	    ,
	    //验证修改后的菜单名是否重复
	    
	     sysType:{
	        validator:function(value, param){
	        	
	        var result = false;
	      // var sys_type=document.getElementById('sys_type').value;
	        var sys_type=$('#sys_type').combobox('getText'); 
	        var url = param[0];
	        var success;
	        $.ajax({
	            type: "POST",
	            url: url,
	            data: {
	            	sys_type:sys_type
	            }, 
	            dataType: 'json',
	            async: false,
	            success: function(msg){
	            	jsonData=msg;
	            }
	         });
	        return jsonData.success;
	        },
	        message:'控制类型不可重复'
	    }
	    ,
	    //验证部门编码是否重复 ajax	    
	    deptCode : {
			validator : function(value, param) {
				var result = false;
				//var dept_code = document.getElementById('dept_code').value;
				var url = param[0];
				var success;
				$.ajax({
					type : "POST",
					url : url,
					data : {
						dept_code : value,
						dept_name : value
					},
					dataType : 'json',
					async : false,
					success : function(msg) {
						jsonData = msg;
					}
				});
				return jsonData.success;
			},
			message : '该部门编号或名称已存在'
		}    //验证修改后的菜单名是否重复
	    ,
	     numberCode:{  
	        validator:function(value, param){	        	
	        var result = false;
	        var number_id=document.getElementById('number_id').value;
	        var number_code=$('#number_code').textbox('getValue');
	        var group_type=$('#group_type').combobox('getValue');
	        var number_type=$('#number_type').combobox('getValue');
	        var url = param[0];
	        var success;
	
	        $.ajax({
	            type: "POST",
	            url: url,
	            data: {
	            	group_type:group_type,
	            	number_code:number_code,
	            	number_id:number_id,
	            	number_type:number_type
	            }, 
	            dataType: 'json',
	            async: false,
	            success: function(msg){
	            	jsonData=msg;
	            //	msg1=jsonData.msg;
	            }
	         });
	        return jsonData.success;
	        },
	       message:'靓号不可重复'
	    }  ,
	     numberRuleCode:{
		        validator:function(value, param){	  
		        	  var result = false;
		        var number_id=document.getElementById('number_id').value;
		        var number_code=$('#number_code').textbox('getValue');
		        var group_type=$('#group_type').combobox('getValue');
		        var number_type=$('#number_type').combobox('getValue');
		        var url = param[0];
		        var success=true;
		        $.ajax({
		            type: "POST",
		            url: url,
		            data: {
		            	group_type:group_type,
		            	number_code:number_code,
		            	number_id:number_id,
		            	number_type:number_type
		            }, 
		            dataType: 'json',
		            async: false,
		            success: function(msg){
		            	jsonData=msg;
		            	success=jsonData.success;
		            }
		         });
		        return success;
		        },
		       message:'请输入符合规则的靓号'
		    } 
	    ,
		   //验证商品类目名称是否重复
	    
	     categoryName:{
	        validator:function(value, param){
	        	
	        var result = false;
	        var category_id=document.getElementById('category_id').value;
	        var category_name=document.getElementById('category_name').value;
	        var url = param[0];
	        var success;
	        $.ajax({
	            type: "POST",
	            url: url,
	            data: {
	            	category_id:category_id,
	            	category_name:category_name
	            }, 
	            dataType: 'json',
	            async: false,
	            success: function(msg){
	            	jsonData=msg;
	            }
	         });
	        return jsonData.success;
	        },
	        message:'商品类目名称不可重复'
	    }
	    ,
		   //验证等级名称是否重复
	    
	     levelName:{
	        validator:function(value, param){
	        	
	        var result = false;
	        var level_id=document.getElementById('level_id').value;
	        var level_name=document.getElementById('level_name').value;
	        var url = param[0];
	        var success;
	        $.ajax({
	            type: "POST",
	            url: url,
	            data: {
	            	level_id:level_id,
	            	level_name:level_name
	            }, 
	            dataType: 'json',
	            async: false,
	            success: function(msg){
	            	jsonData=msg;
	            }
	         });
	        return jsonData.success;
	        },
	        message:'等级名称不可重复'
	    }
	    ,
		   //验证等级名称是否重复
	    
	     paramName:{
	        validator:function(value, param){
	        var result = false;
	        var param_id=document.getElementById('param_id').value;
	        var goods_id=document.getElementById('param_goods_id').value;
	        var param_name=document.getElementById('param_names').value;
	        var url = param[0];
	        var success;
	        $.ajax({
	            type: "POST",
	            url: url,
	            data: {
	            	param_id:param_id,
	            	goods_id:goods_id,
	            	param_name:param_name
	            }, 
	            dataType: 'json',
	            async: false,
	            success: function(msg){
	            	jsonData=msg;
	            }
	         });
	        return jsonData.success;
	        },
	        message:'同一商品的参数名称不可重复'
	    }
	    ,
		   //验证规格名称是否重复
	    
	     specName:{
	        validator:function(value, param){
	        var result = false;
	        var spec_id=document.getElementById('spec_ids').value;
	        var goods_id=document.getElementById('goods_ids').value;
	        var spec_name=document.getElementById('spec_names').value;
	        var url = param[0];
	        var success;
	        $.ajax({
	            type: "POST",
	            url: url,
	            data: {
	            	spec_id:spec_id,
	            	goods_id:goods_id,
	            	spec_name:spec_name
	            }, 
	            dataType: 'json',
	            async: false,
	            success: function(msg){
	            	jsonData=msg;
	            }
	         });
	        return jsonData.success;
	        },
	        message:'同一商品的规格名称不可重复'
	    }
	    ,
		   //验证同一商品同一等级的提成比率是否重复
	    
	     prorateName:{
	        validator:function(value, param){	        	
	        var result = false;
	        var goods_id=document.getElementById('prorate_goods_id').value;
	        var level_id=$('#level_id').combobox('getValue');
	        var url = param[0];
	        var success;
	        $.ajax({
	            type: "POST",
	            url: url,
	            data: {
	            	goods_id:goods_id,
	            	level_id:level_id
	            }, 
	            dataType: 'json',
	            async: false,
	            success: function(msg){
	            	jsonData=msg;
	            }
	         });
	        return jsonData.success;
	        },
	        message:'同一商品同一等级下提成比率不可重复'
	    },
        //计算订单价格
        calcOrder:{
            validator:function(value, param){
                var result = false;
               /* var goods_id=$('#goods_id').combobox('getValue');
                var spec_id=$('#spec_id').combobox('getValue');*/
                var spec_id=document.getElementById('spec_id').value;
                var goods_id=document.getElementById('goods_id').value;
                var num=$('#number').textbox('getText');
                var number=document.getElementById('number').value;
                var url = param[0];
                var success;
                $.ajax({
                    type: "POST",
                    url: url,
                    data: {
                        num:num,
                        goods_id:goods_id,
                        spec_id:spec_id,
                        number:number
                    },
                    dataType: 'json',
                    async: false,
                    success: function(msg){
                        jsonData=msg;
                        msgs='下单数量不能大于可订数量';
                        /*if(msg.success == false){
                            var total = '';

                            $("#total").html(total);
                        }*/
                       /* var total = '\
				<span style="color:red;">订单总价：¥ '+fmoney(msg.msg,2)+'\</span>\
				';
                        $("#total").html(total);*/
                    }
                });
                return jsonData.success;
            },
            message:'下单数量不能大于可订数量且只能输入数字'
        }
	});
	
	//初始化文本框样式
	$(".textbox").css({ height: "28px" });
	$(".textbox .textbox-text").css({
		paddingTop:0,
		paddingBottom:0,
	    height: "28px"
	});
	$(".spinner .textbox-text").css({ height: "auto" });

	$(".easyui-textbox-re").parent().find("span.textbox").css({ height: "auto" });
	$(".easyui-textbox-re").parent().find("textarea").css({
		paddingTop:"5px",
		paddingBottom:"5px",
	    height: "120px" 
	});

	//初始化选择框样式
	$(".datebox .combo-arrow").css({ height: "28px" });
	$('.easyui-combobox').combobox({
		height : '30'
	});
});


// 查询
function search(){
	var $this = $('#search');
	var $form = $this.find("form");
	$this.window('open');
}

$(document).on("submit","#search form",function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param);//查询数据
	$("#search").window('close');
	return false;
});
