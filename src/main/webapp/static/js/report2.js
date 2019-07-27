//下拉联动加载品牌分公司和分公司经理
$(function(){
	$.ajax({
		url : "../platform/querystaffInfoForCombox",
		success : function(data) {
			$("#brand_id").combobox("loadData", data);
		}
	});
	$("#brand_id").combobox({

		onSelect : function(text) {
			$("#company_id").combobox("clear");
			$("#manager_id").combobox("clear");
			$("#company_id").combobox(
				"reload",
				"../platform/querystaffCompanyInfoForCombox?brand_id=" + text.id);
		}
	});

	$("#company_id").combobox({
		onSelect : function(record) {
			$("#manager_id").combobox("clear");
			$("#manager_id").combobox(
				"reload","../platform/queryCompanyManagerForCombox?company_name=" + record.id);
		}
	});


});

