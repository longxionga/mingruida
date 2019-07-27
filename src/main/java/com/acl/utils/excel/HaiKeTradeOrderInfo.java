package com.acl.utils.excel;

/**
 * 描述:
 *
 * @author longxionga
 * @create 2019-06-12 21:27
 */
public class HaiKeTradeOrderInfo extends OrderInfo {
    //网点名称
    private String networkName;

    //下级服务商
    private String butAgentname;

    //是否双免
    private String isshuagmian;

    //优惠金额
    private String yhjine;

    //渠道优惠金额
    private String qdyhjine;

    //交易手续费
    private String jyshxf;

    //增收手续费
    private String zshshxf;

    //手机PAY类型
    private String shjPAYlx;

    //订单类型
    private  String  dingdanleixin;


    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getButAgentname() {
        return butAgentname;
    }

    public void setButAgentname(String butAgentname) {
        this.butAgentname = butAgentname;
    }

    public String getIsshuagmian() {
        return isshuagmian;
    }

    public void setIsshuagmian(String isshuagmian) {
        this.isshuagmian = isshuagmian;
    }

    public String getYhjine() {
        return yhjine;
    }

    public void setYhjine(String yhjine) {
        this.yhjine = yhjine;
    }

    public String getQdyhjine() {
        return qdyhjine;
    }

    public void setQdyhjine(String qdyhjine) {
        this.qdyhjine = qdyhjine;
    }

    public String getJyshxf() {
        return jyshxf;
    }

    public void setJyshxf(String jyshxf) {
        this.jyshxf = jyshxf;
    }

    public String getZshshxf() {
        return zshshxf;
    }

    public void setZshshxf(String zshshxf) {
        this.zshshxf = zshshxf;
    }

    public String getShjPAYlx() {
        return shjPAYlx;
    }

    public void setShjPAYlx(String shjPAYlx) {
        this.shjPAYlx = shjPAYlx;
    }

    public String getDingdanleixin() {
        return dingdanleixin;
    }

    public void setDingdanleixin(String dingdanleixin) {
        this.dingdanleixin = dingdanleixin;
    }
}
