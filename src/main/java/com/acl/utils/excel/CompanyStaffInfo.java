package com.acl.utils.excel;

import java.io.Serializable;

public class CompanyStaffInfo implements Serializable {
    private String id;//

    private String parentid;//上级员工名称
    private String staffcode;//员工的身份证号
    private String staffname;//直营员工名称
    private String phoneNO;//手机号码
    private String staffstate;//状态：1在职 2离职 3其他
    private String startdate;//直营员工的入职时间
    private String enddate;//直营员工的离职时间
    private String position;//岗位1 经理2主管3组员4人事5其他
    private String branch;//员工所属分公司
    private String remarks;//备注信息
    private String channel;//渠道 1公司 2个人
    private String agentcode;//代理商编号
    private String agentname;//代理商名称

    private String fenqi;//分期数 1 一期 2.二期 3.三期 9.其他

    private String logincode;//员工系统登陆账号

    private String branchname;//员工所属分公司名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getStaffcode() {
        return staffcode;
    }

    public void setStaffcode(String staffcode) {
        this.staffcode = staffcode;
    }

    public String getStaffname() {
        return staffname;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
    }

    public String getPhoneNO() {
        return phoneNO;
    }

    public void setPhoneNO(String phoneNO) {
        this.phoneNO = phoneNO;
    }

    public String getStaffstate() {
        return staffstate;
    }

    public void setStaffstate(String staffstate) {
        this.staffstate = staffstate;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAgentcode() {
        return agentcode;
    }

    public void setAgentcode(String agentcode) {
        this.agentcode = agentcode;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getFenqi() {
        return fenqi;
    }

    public void setFenqi(String fenqi) {
        this.fenqi = fenqi;
    }

    public String getLogincode() {
        return logincode;
    }

    public void setLogincode(String logincode) {
        this.logincode = logincode;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    @Override
    public String toString() {
        return "CompanyStaffInfo{" +
                "id='" + id + '\'' +
                ", parentid='" + parentid + '\'' +
                ", staffcode='" + staffcode + '\'' +
                ", staffname='" + staffname + '\'' +
                ", phoneNO='" + phoneNO + '\'' +
                ", staffstate='" + staffstate + '\'' +
                ", startdate='" + startdate + '\'' +
                ", enddate='" + enddate + '\'' +
                ", position='" + position + '\'' +
                ", branch='" + branch + '\'' +
                ", remarks='" + remarks + '\'' +
                ", channel='" + channel + '\'' +
                ", agentcode='" + agentcode + '\'' +
                ", agentname='" + agentname + '\'' +
                ", fenqi='" + fenqi + '\'' +
                ", logincode='" + logincode + '\'' +
                '}';
    }
}
