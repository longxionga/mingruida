		function loadData(param) {
			$('#data').datagrid({
				title: '系统设置',//当前页面标题
				fitColumns:true,//自动列宽
				autoRowHeight:true,//自动行高度
				singleSelect: true,//禁用选择多行
				border: !0,//不显示边框
				striped: true,//条纹行
				fit: true,//自适应高度
				nowrap: true,//单元格内文字禁止换行
				rownumbers: true,//显示行号
				checkOnSelect : false, //只有选择复选框时才选中
				selectOnCheck : false,//选择行时选中复选框
				pagination: !0,//显示分页
				pageSize:15,//每页显示数量 参数必须为pageList的对应
				pageList: [15, 30],//每页显示数量设置参数 
				url: "/sys/querySettingData",
				queryParams: param,//查询参数
				toolbar: '#toolbar',//工具栏
				columns: [[
					{
						sortable:false,
						width: parseInt($(this).width() * 0.1),
						field: 'start_time',
						title: '开始时间'
					},
					{
						sortable:false,
						width: parseInt($(this).width() * 0.1),
						field: 'end_time',
						title: '结束时间'
					},
					{
						sortable:false,
						width: parseInt($(this).width() * 0.05),
						field: 'z_is_use',
						title: '是否可用'
					},
				]],
				onLoadSuccess: function () {
					$("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
				}
			});
		}

		//添加/修改方法
		function update(id){
			var $this =$('#update');
			var $form = $this.find("form");
			$this.window('open');
			if (id == undefined){
				$this.panel({title:'添加'});
				$form.attr("action",$form.attr("action-add"));
			}else{
				$this.panel({title:'编辑'});
				$form.attr("action",$form.attr("action-edit"));
				$("#sys_id").val($('#data').datagrid('getData').rows[id].sys_id);
			}
			$this.form('clear');
			$this.form('load',$('#data').datagrid('getData').rows[id]);
		}

		$("#search").submit(function() {
			var $this = $(this);
			if(!$this.form("validate")){
				return false;
			}
			var param = getFormJson(this);
			loadData(param);//查询数据
			return false;
		});
		loadData();//初始化加载数据
