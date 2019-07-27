dictInfo(1024,'lottery_type');
dict(1024);
function loadData(param) {
	$('#data').datagrid({
		title: '',//当前页面标题
		fitColumns: false,//自动列宽
		autoRowHeight: true,//自动行高度
		singleSelect: true,//禁用选择多行
		border: !0,//不显示边框
		striped: true,//条纹行
		fit: true,//自适应高度
		nowrap: true,//单元格内文字禁止换行
		rownumbers: true,//显示行号
		checkOnSelect: false, //只有选择复选框时才选中
		selectOnCheck: false,//选择行时选中复选框	
		pagination : !0,// 显示分页
		pageSize : 20,// 每页显示数量 参数必须为pageList的对应
		pageList : [ 20,40,80 ],// 每页显示数量设置参数
		url: "queryLotteryRule",
		queryParams: param,//查询参数
		toolbar: '#toolbar',//工具栏
		
		columns: [[
		           {

					   width: 300,
		        	   field: 'lottery_type',
		        	   title: '活动类型',
                       formatter:dictFormatter
		           },
		           {
		        	   width: 300,
		        	   field: 'lottery_service_rate',
		        	   title: '活动服务费率'
		           },
		           {
		        	   width: 300,
		        	   align : 'right',
		        	   field: 'lottery_limit_number',
		        	   title: '活动限制数量'

		           },
		           // {
		        	//    width: 80,
		        	//    field: 'status',
		        	//    title: '活动状态',
		        	//    align : 'center',
		        	//    formatter: function (value, row, index) {
		        	// 	   if (value == 1)
		        	// 		   return '1';
		        	// 	   else
		        	// 		   return '<span style="color:#00ff00;">2</span>';
		        	//    }
		           // },
		           {
		        	   width: 100,
		        	   field: 'op',
		        	   title: '操作',
		        	   align: 'center',
		        	   formatter: function (value, row, index) {
		        		   var str = '\
		        			   <a class="btns" href="javascript:update(\'' + index + '\');">编辑</a>\
		        			   ';
		        		   return str;
		        	   }
		           }
		           ]],
		           onLoadSuccess: function () {
		        	   $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
		           }
	});
}


//添加/修改方法
var updateId;
function update(id) {
	var $this = $('#update');
	var $form = $this.find("form");
	updateId=id;
		$("#resetBtn").linkbutton('disable');
		$this.window({ title: '编辑' });
        $("#reset").linkbutton('enable');
		$form.attr("action", $form.attr("action-edit"));
		$this.form('clear');
		$this.form('load', $('#data').datagrid('getData').rows[id]);
	    $this.window({
    	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),   
    	left:($(window).width() - 680) * 0.5
    });
	   $this.window('open');
}

// 为重置按钮添加单击事件 
$("#reset").click(function(){
	if (updateId == undefined) {
		$('#updateform').form('clear');
		var thisData = {"status":1,"status":1};
		$('#update').form('load', thisData);
	}else{
		update(updateId);	
	}	
});


$("#search").submit(function () {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param);//查询数据
	return false;
});
loadData();//初始化加载数据

$('#status').combobox({
    width : '150',
    height : '30',
    valueField : 'label',
    textField : 'value',
    required : 'true',
    panelHeight : 'auto',
    editable: false,
    data : [ {
        label : '1',
        value : '1'
    }, {
        label : '2',
        value : '2'
    } ]
});