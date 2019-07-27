loadData();
    function loadData() {
    	$('#data').treegrid(
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
    				url : "../sys/queryBackMenu",		
    				idField:'menu_id',
    				treeField: 'menu_name',
    				columns : [ [
    				             {
    				            	 sortable : false,
    				            	 width : parseInt($(this).width() * 0.1),
    				            	 field : 'menu_name',
    				            	 title : '菜单名称'
    				             }] ]
    				            
    			});
    }
    