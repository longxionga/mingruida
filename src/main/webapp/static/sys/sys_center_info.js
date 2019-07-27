  var ue = UE.getEditor('container',{
        initialFrameWidth:[600],
        initialFrameHeight:[400],
        elementPathEnabled:false,
        wordCount:false
        });
  		dictInfo(1006,"center_type");
  		dictInfo(1006,'type');
  		dict(1006);
		function loadData(param) {
			$('#data').datagrid({
				title:'',//当前页面标题
				fitColumns:true,//自动列宽
				autoRowHeight:true,//自动行高度
				singleSelect: true,//禁用选择多行
				border: !1,//不显示边框
				striped: true,//条纹行
				fit: true,//自适应高度
				nowrap: true,//单元格内文字禁止换行
				rownumbers: true,//显示行号
				checkOnSelect : false, //只有选择复选框时才选中
				selectOnCheck : false,//选择行时选中复选框
				pagination: !0,//显示分页
				pageSize:20,//每页显示数量 参数必须为pageList的对应
				pageList: [20,40,80],//每页显示数量设置参数 
				url: "queryCenterInfo",
				queryParams: param,//查询参数
				toolbar: '#toolbar',//工具栏
				columns: [[
                    {
	                   sortable:false,
	                   align:'left',
	                   width : 180,
	                   field: 'center_abstract',
	                   title: '标题'
                    },
					{
						sortable:false,
						align:'left',
						width : 180,
						field: 'center_title',
						title: '正文标题'
					},
					{
						sortable:false,
						align:'center',
						width : 120,
						field: 'dept_name',
						title: '查看范围'
					},
					{
						width : 100,
						sortable:false,
						field: 'create_date',
						title: '创建时间',
						formatter:dateTimeFormatter
					},
					{
						width : 50,
						align: 'center',
						sortable:false,
						field: 'center_user',
						title: '创建人'
					},
					{
						width : 50,
						sortable:false,
						align:'center',
						field: 'center_type',
						title: '类型',
					    formatter:dictFormatter
					},
					{
						width : 50,
						sortable:false,
						align:'center',
						field: 'center_number',
						title: '浏览次数'
					},
					{
						width : 50,
						sortable:false,
						align:'center',
						field: 'is_use',
						title: '是否可用',
						formatter: function(value,row,index){
							if (row.is_use==1){
								return "是";
							}else{
								return "<span style='color:#f00;'>否</span>";
							}
						}
					},
					{
						width : 50,
						sortable:false,
						align:'center',
						field: 'is_important',
						title: '是否重要',
						formatter: function(value,row,index){
							if (row.is_important==1){
								return "是";
							}else{
								return "<span style='color:#f00;'>否</span>";
							}
						}
					},	
					{
						width : 100,
						sortable:false,
						field: 'use_date',
						title: '有效时间',
						formatter:function(value){
							if(value!=undefined){
								return dateTimeFormatter(value);
							}else{
								return "";
							}
						}
						
					},
					{
						width : 50,
						field: 'op',
						align:'center',
						title: '操作',
						align:'center',
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
					$(".datagrid-body td[field='center_title'],.datagrid-body td[field='center_abstract']").overTitle();
				}
			});
		}

	
		//添加/修改方法
		var updateId;
		function update(id){			
			var $this =$('#update');
			var $form = $this.find("form");
			updateId = id;
			$("#reset").attr("data-repickID",id);
			$this.form('clear');
			if (id == undefined){
				
				// 重置有效
				var thisData = {"center_user":loginName,"is_use":1};
				$this.form('load', thisData);
				$("#resetBtn").linkbutton('enable');				
				$("#center_user").textbox('readonly',true);
				$this.window({title:'添加'});
				$form.attr("action",$form.attr("action-add"));
				ue.ready(function() {
					ue.setContent("");
				});
				
			}else{
				// 重置无效
				$this.window({title:'编辑'});
				$form.attr("action",$form.attr("action-edit"));
				$("#center_user").textbox('readonly');
				$("#center_user").textbox("disableValidation");	
				$("#center_id").val($('#data').datagrid('getData').rows[id].center_id);
				var center_text=$('#data').datagrid('getData').rows[id].center_text;
				if(center_text!=undefined){
					ue.ready(function() {
					    ue.setContent(center_text);
					});
				}else{
					ue.ready(function() {
						ue.setContent("");
					});
				}
				
				$this.form('load',$('#data').datagrid('getData').rows[id]);
			}
			
			$this.window({
            	top:($(window).height() - 450) * 0.5+$(document).scrollTop(),   
            	left:($(window).width() - 700) * 0.5
            });
			$this.window('open');
		}
		
		//为重置按钮添加单击事件 
		$("#reset").click(function(){
			if (updateId == undefined) {
				$('#updateform').form('clear');
				//设置发布人为当前登陆者
				var thisData = {"center_user":loginName,"is_use":1};
				$("#update").form('load', thisData);
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
		//清除表单
		function clearForm(){
		  javascript:$('#updateform').form('clear');
		  ue.ready(function() {
			 ue.setContent("");
		});
		}
		loadData();//初始化加载数据
		