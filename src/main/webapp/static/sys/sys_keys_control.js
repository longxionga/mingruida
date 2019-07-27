function loadData() {
	$.ajax({
		url : "queryKeyControl",
		type : "POST",
		success : function(data) {			
			var html = '\
				<span>目前设置的值为：<span style="color:red;">'+ data.status +'\</span></span>\
				';
			$("#status").html(html);
		}
	});	
}
//打开keys2
function setKeys2() {         	
	$.messager.confirm('确认开启', '确定打开keys2吗?', function (r) {
		if (r) {
			$.ajax({
				url: "updKeyControl",
				type: "POST",
				data: { openKey: "keys2" },
				success: function (data) {
					if (data.success == true) {
						loadData();
						$.messager.alert('提示信息', data.msg, 'info');
					} else {
						$.messager.alert('错误信息', data.msg, 'error');
					}
				}
			});
		}
	});
}

//打开keys3
function setKeys3(key) { 
	$.messager.confirm('确认开启', '确定打开'+key+'吗?', function (r) {
		if (r) {
			$.ajax({
				url: "updKeyControl",
				type: "POST",
				data: { openKey: key },
				success: function (data) {
					if (data.success == true) {
						loadData();
						$.messager.alert('提示信息', data.msg, 'info');
					} else {
						$.messager.alert('错误信息', data.msg, 'error');
					}
				}
			});
		}
	});
}

//删除方法
function del() {         	
	$.messager.confirm('确认关闭', '确定关闭吗?', function (r) {
		if (r) {
			$.ajax({
				url: "delKeyControl",
				type: "POST",
				success: function (data) {
					if (data.success == true) {
						loadData();
						$.messager.alert('提示信息', data.msg, 'info');
					} else {
						$.messager.alert('错误信息', datae.msg, 'error');
					}
				}
			});
		}
	});
}
loadData();