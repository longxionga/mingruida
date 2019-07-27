package com.acl.utils.excel;

import java.io.Serializable;
import java.util.Map;

public class RefundVouchersInfo implements Serializable {
     //
    private String id;
    //充值时间
    private  String rechargedate;
    //返券时间
    private  String returndate;
    //到期时间
    private  String expiredate;
    //商户编号
    private  String merchantNO;
    //商户名称
    private  String merchantname;
    //代理商编号
    private  String agentNO;
    //代理商名称
    private  String agentname;
    //机具类型
    private  String implementstype;
    //充值金额
    private  String rechargeamount;
    //返券金额
    private  String returnamout;
    //已用金额
    private  String alreadyamount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRechargedate() {
        return rechargedate;
    }

    public void setRechargedate(String rechargedate) {
        this.rechargedate = rechargedate;
    }

    public String getReturndate() {
        return returndate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }

    public String getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(String expiredate) {
        this.expiredate = expiredate;
    }

    public String getMerchantNO() {
        return merchantNO;
    }

    public void setMerchantNO(String merchantNO) {
        this.merchantNO = merchantNO;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    public String getAgentNO() {
        return agentNO;
    }

    public void setAgentNO(String agentNO) {
        this.agentNO = agentNO;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getImplementstype() {
        return implementstype;
    }

    public void setImplementstype(String implementstype) {
        this.implementstype = implementstype;
    }

    public String getRechargeamount() {
        return rechargeamount;
    }

    public void setRechargeamount(String rechargeamount) {
        this.rechargeamount = rechargeamount;
    }

    public String getReturnamout() {
        return returnamout;
    }

    public void setReturnamout(String returnamout) {
        this.returnamout = returnamout;
    }

    public String getAlreadyamount() {
        return alreadyamount;
    }

    public void setAlreadyamount(String alreadyamount) {
        this.alreadyamount = alreadyamount;
    }

    @Override
    public String toString() {
        return "RefundVouchersInfo{" +
                "id='" + id + '\'' +
                ", rechargedate='" + rechargedate + '\'' +
                ", returndate='" + returndate + '\'' +
                ", expiredate='" + expiredate + '\'' +
                ", merchantNO='" + merchantNO + '\'' +
                ", merchantname='" + merchantname + '\'' +
                ", agentNO='" + agentNO + '\'' +
                ", agentname='" + agentname + '\'' +
                ", implementstype='" + implementstype + '\'' +
                ", rechargeamount='" + rechargeamount + '\'' +
                ", returnamout='" + returnamout + '\'' +
                ", alreadyamount='" + alreadyamount + '\'' +
                '}';
    }
}
