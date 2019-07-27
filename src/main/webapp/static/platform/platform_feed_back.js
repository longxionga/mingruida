
function loadData(param) {
	$('#data').datagrid(
					{
						title : '',// 当前页面标题
						fitColumns : true,// 自动列宽
						autoRowHeight : true,// 自动行高度
						singleSelect : true,// 禁用选择多行
						border : !1,// 不显示边框
						striped : true,// 条纹行
						fit : true,// 自适应高度
						nowrap : true,// 单元格内文字禁止换行
						rownumbers : true,// 显示行号
						checkOnSelect : false, // 只有选择复选框时才选中
						selectOnCheck : false,// 选择行时选中复选框
						pagination : !0,// 显示分页
						pageSize : 20,// 每页显示数量 参数必须为pageList的对应
						pageList : [ 20,40,80 ],// 每页显示数量设置参数
						url : "queryFeedBack",
						queryParams : param,// 查询参数
						toolbar : '#toolbar',// 工具栏
						columns : [ [
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'user_name',
									title : '用户名称'
								},
								{
									sortable : false,
									width : parseInt($(this).width() * 0.1),
									field : 'advice',
									title : '用户建议'
								},
								{
									 sortable : false,
					            	 field : 'mobile',
					            	 title : '联系方式',
					            	 align: 'center',
					            	 width : 100
								},
								{
									 sortable : false,
					            	 field : 'voucher_uri1',
					            	 title : '图片说明1',
					            	 align: 'center',
					            	 width : 100
								},
								{
									 sortable : false,
					            	 field : 'voucher_uri2',
					            	 title : '图片说明2',
					            	 align: 'center',
					            	 width : 100
								},
								{
									 sortable : false,
					            	 field : 'voucher_uri3',
					            	 title : '图片说明3',
					            	 align: 'center',
					            	 width : 100
								},
								{
									 sortable : false,
					            	 field : 'ce_name',
					            	 title : '交易中心',
					            	 align: 'center',
					            	 width : 100
								},
								{
									 sortable : false,
					            	 field : 'ch_name',
					            	 title : '渠道',
					            	 align: 'center',
					            	 width : 100
								},
								{
									 sortable : false,
					            	 field : 'settle_name',
					            	 title : '结算会员',
					            	 align: 'center',
					            	 width : 100
								},{
									 sortable : false,
					            	 field : 'agent_name',
					            	 title : '代理商',
					            	 align: 'center',
					            	 width : 100
								},
								{
									 sortable : false,
					            	 field : 'dept_name',
					            	 title : '所属部门',
					            	 align: 'center',
					            	 width : 100
								},
								{
									width : 150,
									field : 'op',
									title : '操作',
									align : 'center',
									formatter : function(value, row, index) {
										console.log(row);
										var str = '\
											<a class="btns" href="javascript:;" onclick="check(\''+ row.feedback_id+'\');">详细</a>\
										';
										return str;
									}
								}] ],
						onLoadSuccess : function() {
							/*var imgUrl=$(".datagrid-body td[field='goods_url']");
							imgUrl.mouseover(function(){
								var thisObj=$(this);
								imgUrl.overDialog('<div class="over_dialog_img"><img src="'+thisObj.find("div").html()+'" /></div>');
							});*/
							var imgUrl=$(".datagrid-body td[field='voucher_uri1']");
							imgUrl.overDialog('<div class="over_dialog_img"><img src="#" /></div>');
							var imgUrl2=$(".datagrid-body td[field='voucher_uri2']");
							imgUrl2.overDialog('<div class="over_dialog_img"><img src="#" /></div>');
							var imgUrl3=$(".datagrid-body td[field='voucher_uri3']");
							imgUrl3.overDialog('<div class="over_dialog_img"><img src="#" /></div>');
						}
					});
}

$("#search").submit(function() {
	var $this = $(this);
	if (!$this.form("validate")) {
		return false;
	}
	var param = getFormJson(this);
	loadData(param);//查询数据
	return false;
});

function check(feedback_id){
    $.ajax({
        type: "POST",
        url: "queryFeedBackInfo",
        dataType: 'json',
        data:{feedback_id:feedback_id},
        success: function (msg) {
            context =msg[0].advice;
            user_name =msg[0].user_name;
            create_date= dateTimeFormatter(msg[0].create_time);
            if (msg[0].voucher_uri1 != undefined) {
                imgurl1 = msg[0].voucher_uri1;
            } else {
                imgurl1 = "";
            }
            if (msg[0].voucher_uri2 != undefined) {
                imgurl2 = msg[0].voucher_uri2;
            } else {
                imgurl2 = "";
            }
            if (msg[0].voucher_uri3 != undefined) {
                imgurl3 = msg[0].voucher_uri2;
            } else {
                imgurl3 = "";
            }
            show();
        }

    });

}
function show(){
    var html =
        '<div class="lay_home_info_list">'+
			'<div class="info_title">'+user_name+'</div>'+
			'<div class="info_date">'+create_date+'</div>'+
			'<div class="context">'+context+'</div>'+
			'<div class="over_dialog_img">图一：<img src="'+imgurl1+'" /></div>'+
       		'<div class="over_dialog_img">图二：<img src="'+imgurl2+'" /></div>'+
       		'<div class="over_dialog_img">图三：<img src="'+imgurl3+'" /></div>'+
        '</div>';
    $("#msg").html(html);
    $('#msg').window({
        top:($(window).height() - 400) * 0.5+$(document).scrollTop(),
        left:($(window).width() - 800) * 0.5
    });
    $('#msg').window('open');
}


loadData({order_type:queryDescOrAsc.create_time});//初始化加载数据







