package com.acl.utils.excel;

import java.io.Serializable;

public class MachineInfoInfo implements Serializable {
    private String id;//id
    private String machineSN;//机具编号
    private String machinecode;//机具序列号
    private String merchantcode;//商户编号
    private String attagentcode;//归属代理商编号
    private String isbound;//是否绑定
    private String machinetype;//机具类型
    private String machinemodel;//机具型号
    private String warehousingtype;//激活类型
    private String vipstatus;//VIP购买状态
    private String attagentname;//归属代理商名称
    private String merchantname;//商户名称
    private String machineryT1rate;//机具T1费率(%)
    private String machineryT0rate;//机具T0费率(%)
    private String machineryamount;///机具提现费(元)
    private String staffid;//员工id
    private String brindid;//所属品牌

    private String batchcode;//批次号
    private String dotcode;//网点编号
    private String dotname;//网点名称
    private String isencryption;//是否加密
    private String machineident;//终端标识
    private String policyident;//政策标识
    private String allocationnum;//调拨次数
    private String ifdeposit;//是否已交押金
    private String perattagentname;//下级代理商名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMachineSN() {
        return machineSN;
    }

    public void setMachineSN(String machineSN) {
        this.machineSN = machineSN;
    }

    public String getMerchantcode() {
        return merchantcode;
    }

    public void setMerchantcode(String merchantcode) {
        this.merchantcode = merchantcode;
    }

    public String getAttagentcode() {
        return attagentcode;
    }

    public void setAttagentcode(String attagentcode) {
        this.attagentcode = attagentcode;
    }

    public String getIsbound() {
        return isbound;
    }

    public void setIsbound(String isbound) {
        this.isbound = isbound;
    }

    public String getMachinetype() {
        return machinetype;
    }

    public void setMachinetype(String machinetype) {
        this.machinetype = machinetype;
    }

    public String getMachinemodel() {
        return machinemodel;
    }

    public void setMachinemodel(String machinemodel) {
        this.machinemodel = machinemodel;
    }

    public String getWarehousingtype() {
        return warehousingtype;
    }

    public void setWarehousingtype(String warehousingtype) {
        this.warehousingtype = warehousingtype;
    }

    public String getVipstatus() {
        return vipstatus;
    }

    public void setVipstatus(String vipstatus) {
        this.vipstatus = vipstatus;
    }

    public String getAttagentname() {
        return attagentname;
    }

    public void setAttagentname(String attagentname) {
        this.attagentname = attagentname;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    public String getMachineryT1rate() {
        return machineryT1rate;
    }

    public void setMachineryT1rate(String machineryT1rate) {
        this.machineryT1rate = machineryT1rate;
    }

    public String getMachineryT0rate() {
        return machineryT0rate;
    }

    public void setMachineryT0rate(String machineryT0rate) {
        this.machineryT0rate = machineryT0rate;
    }

    public String getMachineryamount() {
        return machineryamount;
    }

    public void setMachineryamount(String machineryamount) {
        this.machineryamount = machineryamount;
    }

    public String getMachinecode() {
        return machinecode;
    }

    public void setMachinecode(String machinecode) {
        this.machinecode = machinecode;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getBrindid() {
        return brindid;
    }

    public void setBrindid(String brindid) {
        this.brindid = brindid;
    }

    public String getBatchcode() {
        return batchcode;
    }

    public void setBatchcode(String batchcode) {
        this.batchcode = batchcode;
    }

    public String getDotcode() {
        return dotcode;
    }

    public void setDotcode(String dotcode) {
        this.dotcode = dotcode;
    }

    public String getDotname() {
        return dotname;
    }

    public void setDotname(String dotname) {
        this.dotname = dotname;
    }

    public String getIsencryption() {
        return isencryption;
    }

    public void setIsencryption(String isencryption) {
        this.isencryption = isencryption;
    }

    public String getMachineident() {
        return machineident;
    }

    public void setMachineident(String machineident) {
        this.machineident = machineident;
    }

    public String getPolicyident() {
        return policyident;
    }

    public void setPolicyident(String policyident) {
        this.policyident = policyident;
    }

    public String getAllocationnum() {
        return allocationnum;
    }

    public void setAllocationnum(String allocationnum) {
        this.allocationnum = allocationnum;
    }

    public String getIfdeposit() {
        return ifdeposit;
    }

    public void setIfdeposit(String ifdeposit) {
        this.ifdeposit = ifdeposit;
    }

    public String getPerattagentname() {
        return perattagentname;
    }

    public void setPerattagentname(String perattagentname) {
        this.perattagentname = perattagentname;
    }

    @Override
    public String toString() {
        return "MachineInfoInfo{" +
                "id='" + id + '\'' +
                ", machineSN='" + machineSN + '\'' +
                ", machinecode='" + machinecode + '\'' +
                ", merchantcode='" + merchantcode + '\'' +
                ", attagentcode='" + attagentcode + '\'' +
                ", isbound='" + isbound + '\'' +
                ", machinetype='" + machinetype + '\'' +
                ", machinemodel='" + machinemodel + '\'' +
                ", warehousingtype='" + warehousingtype + '\'' +
                ", vipstatus='" + vipstatus + '\'' +
                ", attagentname='" + attagentname + '\'' +
                ", merchantname='" + merchantname + '\'' +
                ", machineryT1rate='" + machineryT1rate + '\'' +
                ", machineryT0rate='" + machineryT0rate + '\'' +
                ", machineryamount='" + machineryamount + '\'' +
                ", staffid='" + staffid + '\'' +
                ", brindid='" + brindid + '\'' +
                ", batchcode='" + batchcode + '\'' +
                ", dotcode='" + dotcode + '\'' +
                ", dotname='" + dotname + '\'' +
                ", isencryption='" + isencryption + '\'' +
                ", machineident='" + machineident + '\'' +
                ", policyident='" + policyident + '\'' +
                ", allocationnum='" + allocationnum + '\'' +
                ", ifdeposit='" + ifdeposit + '\'' +
                ", perattagentname='" + perattagentname + '\'' +
                '}';
    }
}
