function loadData(param, url) {
	var  columns  =  [
	       /*{
				checkbox : true,
				align : 'center',
				width : 20,
				//hidden:true,
				field : 'ck',
				title : '选择项'				
			},*/
			{
	    		align: 'center',
				field : 'mobile',
				title : '手机号',
				width:80
			},
			{
				field : 'name',
				title : '姓名',
				width : 50
			},
			{
				field : 'id_card',
				title : '身份证号',
				align : 'center',
				width: 120
			},
			{
				field : 'job',
				title : '岗位',
				align : 'right',
				width:80
			},
			{
				field : 'job_day',
				title : '入职时间',
				align : 'right',
				width:80
			},
			{
				field : 'title',
				title : '反馈标题',
				width:100
			},
        {
            field : 'notes',
            title : '反馈备注',
            width:160
        },
			{
				field : 'create_time',
				title : '创建时间',
				formatter : dateTimeFormatter,
				width:110
			},
			{
				field : 'status',
				title : '是否可用',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 1)
						return '是';
					else if(value==2)
						return '<span style="color:#f00;">否</span>';
					else
						return '<span style="color:#f00;">已注销</span>';
				},
				width:50
			}
             ];
	

	
	columns = [columns];	
	$('#data')
			.datagrid(
					{
						title : '',// 当前页面标题
						fitColumns : false,// 自动列宽
						autoRowHeight : true,// 自动行高度
						singleSelect : true,// 禁用选择多行
						border : !0,// 不显示边框
						striped : true,// 条纹行
						fit : true,// 自适应高度
						nowrap : true,// 单元格内文字禁止换行
						rownumbers : true,// 显示行号
						checkOnSelect : false, // 只有选择复选框时才选中
						selectOnCheck : false,// 选择行时选中复选框
						pagination : !0,// 显示分页
						pageSize : 20,// 每页显示数量 参数必须为pageList的对应
						pageList : [ 20, 40, 80 ],// 每页显示数量设置参数
						url : url,
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						/*frozenColumns:[[
							{
								field : 'user_name',
								title : '用户名'
							}
		        		]],*/
						columns :columns,
						onLoadSuccess : function() {
                            var imgUrl=$(".datagrid-body td[field='id_card_front']");
                            imgUrl.overDialog('<div class="over_dialog_img"><img src="#" /></div>');
                            var imgUrl2=$(".datagrid-body td[field='id_card_back']");
                            imgUrl2.overDialog('<div class="over_dialog_img"><img src="#" /></div>');
							$("head")
									.append(
											"<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
						}
					});
}



$("#search1").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	
	loadBroker(param);//查询数据
	return false;
});
//刷新功能
$("#refresh").click(function(){
	$('#search1').submit();
});

$("#search").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param, "querySuggestInfo");// 查询数据
	return false;
});

$("#decryption").click(function(){
	/*$("#dataID").val("decryption");
	$("#search").submit();*/
	//decryption();
	var $this = $('#code');
	var $form = $this.find("form");
	$('#codeform').form('clear');	        	
	$("#reset").linkbutton('enable');
	$this.window({ title: '请填写验证码' });		
	$form.attr("action", $form.attr("action-code"));
	$this.window({
    	top:($(window).height() - 240) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 350) * 0.5
    });
	$this.window('open');
	
});

function showDeductMap(id) {
    var total_map = $('#data').datagrid('getData').rows[id].deduct_map;
    /*$("#dataID").val("decryption");
    $("#search").submit();*/
    //decryption();
    var $this = $('#code');
    var $form = $this.find("form");
    $this.window({ title: '应扣工资' });
    $this.window({
        top:($(window).height() - 240) * 0.5+$(document).scrollTop(),
        left:($(window).width() - 350) * 0.5
    });
    $("#tables tr").empty();
    //addTr2('tables', 2)
    for(var i=0;i<total_map.length;i++){
        var v = total_map[i].columnValue;
        if(v ==null){
            v ="";
        }
        var trHtml="<tr>\n" +
            "\t\t\t\t\t\t<td>"+total_map[i].currentColumnName+"：</td>\n" +
            "\t\t\t\t\t\t<td>"+v +"</td>\n" +
            "\t\t\t\t\t</tr>";
        $("#tables tbody").append(trHtml);
    }
    $this.window('open');

}


function showTotalMap(id) {
    var total_map = $('#data').datagrid('getData').rows[id].total_map;
    /*$("#dataID").val("decryption");
    $("#search").submit();*/
    //decryption();
    var $this = $('#code');
    var $form = $this.find("form");
    $this.window({ title: '薪资及绩效提成' });
    $this.window({
        top:($(window).height() - 240) * 0.5+$(document).scrollTop(),
        left:($(window).width() - 350) * 0.5
    });
    $("#tables tr").empty();
    //addTr2('tables', 2)
	for(var i=0;i<total_map.length;i++){
		var v = total_map[i].columnValue;
		if(v ==null){
            v ="";
		}
        var trHtml="<tr>\n" +
            "\t\t\t\t\t\t<td>"+total_map[i].currentColumnName+"：</td>\n" +
            "\t\t\t\t\t\t<td>"+v +"</td>\n" +
            "\t\t\t\t\t</tr>";
        $("#tables tbody").append(trHtml);
    }
    $this.window('open');

}

/**
 * 为table指定行添加一行
 *
 * tab 表id
 * row 行数，如：0->第一行 1->第二行 -2->倒数第二行 -1->最后一行
 * trHtml 添加行的html代码
 *
 */
function addTr(tab, row, trHtml){
    //获取table最后一行 $("#tab tr:last")
    //获取table第一行 $("#tab tr").eq(0)
    //获取table倒数第二行 $("#tab tr").eq(-2)
    //$tr.after(trHtml);
    $("#"+tab+" tbody").append(trHtml);
}

function addTr2(tab, row){
    var trHtml="<tr align='center'><td width='30%'><input type='checkbox' name='ckb'/></td><td width='30%'>地理</td><td width='30%'>60</td></tr>";
    addTr(tab, row, trHtml);
}

function decryption(){//点击解密发送短信验证码
	 var setTime;
     var elem = $(".j_account_repicksms").find("span");
     var elemCount = $(".j_account_repicksms").find("span").html();
     var hideObj = $(".j_account_repicksms").show();
     $(".j_account_getsms").hide();
     setTime = setInterval(function () {
         if (elem.html() == 0) {
             hideObj.hide().parent().find(".j_account_getsms").show();
             elem.html(60);
             clearInterval(setTime);
             return false;
         }
         elemCount--;
         elem.html(elemCount);
     }, 1000);
	
	 $.ajax({
        type: "POST",      
        url: "sendPhoneCode",
        dataType: 'json',
        async: true,
        success: function(msg){  
        	if(msg.success==true){
        		$.messager.alert('提示信息', "信息发送成功,请注意查收手机信息", 'info');
        	}else{
        		$.messager.alert('提示', "信息发送失败,一分钟后请重新获取", 'error');
        	}
        }
 
     });
}

function checkCode(){
	var decryption_code= $('#decryption_code').val();
	$.ajax({
        type: "POST",      
        url: "checkCode",
        dataType: 'json',
        async: true,
        data:{decryption_code:decryption_code},
        success: function(msg){  
        	if(msg.success==true){	
        		$('#code').window('close');
        		$("#dataID").val("decryption");
        		$("#search").submit();
        	}else{
        		$.messager.alert('提示', "验证码输入有误", 'error');
        	}
           }
 
     });
}

loadData('','querySuggestInfo');//初始化加载数据
//loadData();// 初始化加载数据


