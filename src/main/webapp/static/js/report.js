//下拉联动加载结算服务商和代理商
$(function(){
	$.ajax({
		url : "../platform/querySettleInfoForCombox",
		success : function(data) {
			$("#settle_id").combobox("loadData", data);
		}
	});
	$("#settle_id").combobox({
		onSelect : function(record) {
			$("#agent_id").combobox("clear");
			$("#DID").combobox("clear");
			$("#agent_id").combobox(
					"reload",
					"../platform/queryAgentInfoForCombox?settle_id=" + record.id);
		}
	});
			
	// 代理商与部门联动
	$("#agent_id").combobox({
		onSelect : function(record) {
			$("#DID").combobox("clear");
			$("#DID").combobox(
					"reload","../platform/queryDeptInfoForCombox?agent_id=" + record.id);
		}
	});


});	

/**
 * 报表开关
 * @param value
 * @returns {String}
 */
/*function getOffOn(sys_type) {	
	$.ajax({
		url : "../platform/checkUse",
		data:{sys_type:sys_type},
		success : function(data) {
			queryDescOrAsc.is_use=data[0].is_use;
		}
	});
	return queryDescOrAsc.is_use;
};
*/

$(function(){
    $.ajax({
        url : "../platform/queryAgentForCombox",
        success : function(data) {
            $("#agents_id").combobox("loadData", data);
        }
    });
});



