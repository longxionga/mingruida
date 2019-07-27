
function loadData(param) {
	var boolFitColumns=true;
	if($(document).width()<=1600-220){
		boolFitColumns=false;
	}
	$('#data').datagrid(

		{

			title : '',// 当前页面标题

			fitColumns : true,// 自动列宽
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
			pageList : [ 20,40,80 ],// 每页显示数量设置参数
			idField:'id',
			url : 'queryStandardAchievementDPageList',
			remoteSort:false,
			queryParams : param,// 查询参数
			toolbar : '#toolbar',// 工具栏
			columns : [ [
				{
					width: 60,
					sortable : true,
					field : 'brindname',
					title : '机具品牌'
				},

				{
					width: 80,
					sortable : true,
					field : 'machinecode',
					title : '机具序列号'
				},
				{
					width: 100,
					sortable : true,
					field : 'merchant_code',
					title : '商户编号'

				},
				{
					width: 120,
					sortable : true,
					field : 'merchant_name',
					title : '商户名称'

				},
				{
					width: 120,
					sortable : true,
					field : 'bind_day',
					title : '入网时间'

				},
				{
					width: 100,
					sortable : true,
					field : 'merchant_type',
					title : '商户类型'

				},
				{
					width: 100,
					sortable : true,
					field : 'merchant_state',
					title : '商户状态'

				},

				{
					width: 100,
					sortable : true,
					field : 'audit_status',
					title : '审批状态'

				},
				{
					width: 100,
					sortable : true,
					field : 'rate_type',
					title : '费率类型'

				},
				{
					width: 100,
					sortable : true,
					field : 'staffcode',
					title : '员工编号'

				},
				{
					width: 120,
					sortable : true,
					field : 'staffname',
					title : '员工名称'

				},
				{
					width: 120,
					sortable : true,
					field : 'mcount',
					title : '交易大于100交易'

				},
				{
					width: 120,
					sortable : true,
					field : 'state',
					title : '是否达标'

				},
				{
					width: 120,
					sortable : true,
					field : 'dbdate',
					title : '达标日期'

				}

			] ],
			onLoadSuccess : function() {
				$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
			}
		});
}

//为重置按钮添加单击事件
$("#reset").click(function(){
	if (updateId == undefined) {
		$('#updateform').form('clear');
		var thisData = {"is_use":1};
		$('#update').form('load', thisData);
	}else{
		update(updateId);
	}
});

$("#search").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param);//查询数据
	return false;
});


$('#brand_id').combobox({
	url : "../platform/querystaffInfoForCombox",
	valueField : 'id',
	textField : 'text',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200'

});


$('#sdept_id').combobox({
	url : "../platform/queryStaffDeptInfoForCombox",
	valueField : 'id',
	textField : 'text',
	width : '150',
	height : '30',
	editable : false,
	panelHeight : '200',
	multiple:'true'
});

//员工岗位
$('#position').combobox({
	width : '150',
	height : '23',
	valueField : 'label',
	textField : 'value',
	panelHeight : 'auto',
	editable: false,
	data : [{
		label : '',
		value : '全部'
	}, {
		label : '1',
		value : '经理'
	},{
		label : '2',
		value : '主管'
	} ,{
		label : '3',
		value : '组员'
	}]
});

//审批状态
$('#audit_status').combobox({
    width : '150',
    height : '23',
    valueField : 'label',
    textField : 'value',
    panelHeight : 'auto',
    editable: false,
    data : [{
        label : '',
        value : '全部'
    }, {
        label : '审核通过',
        value : '审核通过'
    },{
        label : '已提交',
        value : '已提交'
    } ,{
        label : '审核拒绝',
        value : '审核拒绝'
    }]
});


//商户达标信息导出
function exportExcel() {
    $.messager.confirm('导入Excel', '是否导出索检到的数据？', function(r) {
        if (r) {
            var brand_id=$('#brand_id').combobox('getValue');//获取combobox的value方法

            var sdept_id=$('#sdept_id').combobox('getValue');//获取combobox的value方法

            var position=$('#position').combobox('getValue');//获取combobox的value方法
            var staffname=document.getElementById("staffname").value;
            var merchant_code=document.getElementById("merchant_code").value;
            var audit_status=$('#audit_status').combobox('getValue');//获取combobox的value方法
            var skssj=$('#skssj').datebox('getValue');//获取combobox的value方法
            var sjssj=$('#sjssj').datebox('getValue');//获取combobox的value方法
			var statue= $('#cc').datebox('getValue');//获取combobox的value方法


            $.ajax({
                url : "queryMerchantReportForExcel",
                type : "POST",
                dataType : "json",
                cache:false,
                data : {
                    brand_id:brand_id,
                    sdept_id:sdept_id,
                    position:position,
                    staffname:staffname,
                    merchant_code:merchant_code,
                    audit_status:audit_status,
                    start_date:skssj,
					statue:statue,
                    end_date:sjssj
                },
                beforeSend: function () {
                    $.messager.progress({
                        title:'请等待',
                        msg:'正在导出...',
                        text:'努力中...',
                        interval:'600'
                    });

                },

                complete: function () {
                    $.messager.progress('close');
                },
                success : function(msg) {
                    if(msg.num>0){
                        window.location.href= msg.url;
                    }else{
                        $.messager.alert('提示信息', '导出失败，查询无数据');
                    }
                }
            });
        }
    });
}