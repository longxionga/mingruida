//下拉联动加载品牌分公司和分公司经理
$(function(){
	$.ajax({
		url : "../platform/queryStaffDeptInfoForCombox",
		success : function(data) {
			$("#sdept_id").combobox("loadData", data);
		}
	});
	$("#sdept_id").combobox({

		onSelect : function(text) {
			$("#orgid").combobox("clear");
			$("#orgid").combobox(
				"reload",
				"../platform/queryStaffzhuguanForCombox?sdept_id=" + text.id);
		}
	});

});
