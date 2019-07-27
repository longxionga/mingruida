dictInfo(1026,"status");
dictInfo(1024,"lottery_type");
dict(1024);
function loadData(param,url) {
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
            idField:'AGENT_ID',
            url : url,
            remoteSort:false,
            queryParams : param,// 查询参数
            toolbar : '#toolbar',// 工具栏
            columns : [ [
                {
                    sortable : true,
                    width : 100,
                    field : 'lottery_no',
                    align : 'center',
                    title : '活动期号'
                },
                {
                    sortable : false,
                    width : 100,
                    field : 'lottery_type',
                    align : 'center',
                    title : '活动类型',
                    formatter:dictFormatter
                },
                {
                    sortable : false,
                    width : 100,
                    field : 'lottery_result',
                    align : 'center',
                    title : '活动结果'
                },
                {
                    sortable : true,
                    width : 100,
                    field : 'task_date',
                    align : 'right',
                    title : '任务日期',
                    formatter:dateTimeFormatter
                },
                {
                    sortable : true,
                    width : 100,
                    field : 'status',
                    align : 'right',
                    title : '活动状态',
                    formatter : function(value, row, index) {
                        if (value == '01')
                            return '已开奖';
                        else
                            return '未开奖';
                    }
                },
                {
                    sortable : true,
                    field : 'lottery_ticket',
                    title : '单双',
                    align : 'right',
                    width : 100,
                    formatter : function(value, row, index) {
                        if (value == '1')
                            return '单';
                        else if(value =='2'){
                            return '双'
                        }else {

                            return null;
                        }
                    }
                },
                {
                    sortable : true,
                    field : 'lottery_time',
                    title : '开奖时间',
                    align : 'right',
                    width : 100,
                    formatter:dateTimeFormatter

                },
                {
                    sortable : true,
                    field : 'create_time',
                    title : '创建时间',
                    align : 'right',
                    width : 100
                },
                {
                    sortable : true,
                    field : 'update_time',
                    title : '更新时间',
                    align : 'right',
                    width : 100
                }
            ] ],
            onLoadSuccess : function() {
                $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
            }
        });
}





$("#search").submit(function() {
    var $this = $(this);
    if (!$this.form("validate")) {
        return false;
    }
    var param = getFormJson(this);
    loadData(param,"queryLotteryInfo");//查询数据
    return false;
});

loadData();//初始化加载数据
