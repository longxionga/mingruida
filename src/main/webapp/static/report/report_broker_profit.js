/**
 *
 */
function loadData(param, url) {
    $('#data').datagrid(
        {
            title: '',// 当前页面标题
            fitColumns: true,// 自动列宽
            autoRowHeight: true,// 自动行高度
            singleSelect: true,// 禁用选择多行
            border: !1,// 不显示边框
            striped: true,// 条纹行
            fit: true,// 自适应高度
            nowrap: true,// 单元格内文字禁止换行
            rownumbers: true,// 显示行号
            checkOnSelect: false, // 只有选择复选框时才选中
            selectOnCheck: false,// 选择行时选中复选框
            pagination: !0,// 显示分页
            pageSize: 20,// 每页显示数量 参数必须为pageList的对应
            pageList: [20, 40, 80],// 每页显示数量设置参数
            idField: 'broker_id',
            url: url,
            remoteSort: false,
            queryParams: param,// 查询参数
            toolbar: '#toolbar',// 工具栏
            columns: [[
                {
                    sortable: true,
                    width: 100,
                    field: 'broker_name',
                    align: 'center',
                    title: '经纪人名称'
                },
                {
                    sortable: true,
                    field: 'cz_money',
                    title: '用户充值总额',
                    align: 'right',
                    width: 100
                },
                {
                    sortable: true,
                    width: 100,
                    field: 'tx_money',
                    align: 'right',
                    title: '用户提现总额'
                },
                {
                    sortable: true,
                    field: 'profit',
                    title: '净充值总额',
                    align: 'right',
                    width: 100
                },
                {
                    width: 100,
                    field: 'settle_name',
                    align: 'center',
                    title: '服务商名称'
                },
                {
                    width: 100,
                    field: 'agent_name',
                    align: 'center',
                    title: '代理商名称'
                },
                {
                    width: 100,
                    field: 'dept_name',
                    align: 'center',
                    title: '部门名称'
                }


            ]],
            onLoadSuccess: function () {
                $("head").append("<style>.btns{ background-color:#f4f4f4;border:1px #d0d0d0 solid;line-height:12px;border-radius:4px;padding:2px 20px;text-decoration: none; display: inline-block; margin-right: 5px; color:#444;}.btns:hover{color:#f00; border-color:#f00;}</style>");
            }
        });
}


$("#search").submit(function () {
    var $this = $(this);
    if (!$this.form("validate")) {
        return false;
    }
    var param = getFormJson(this);
    loadData(param, "queryBrokerProfitReport");//查询数据

    return false;
});

/*function expInfo() {
 //var arrayData = $("#search").serialize();
 window.location.href = '';
 }*/
loadData();//初始化加载数据