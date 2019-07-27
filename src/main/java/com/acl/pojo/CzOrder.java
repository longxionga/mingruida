package com.acl.pojo;

import java.io.Serializable;
import java.util.Date;

public class CzOrder implements Serializable {

  private static final long serialVersionUID = -1932335396432323464L;

  private String order_id; // 订单ID
  
  private double order_money; // 订单金额
  
  private String user_id; // 用户ID
  
  private Date create_date; // 创建日期
  
  private String role_type; // 角色类型
  
  private String agent_id; // 代理商id
  
  private String agent_name; // 代理商名称
  
  private String settle_id; // 结算id
  
  private String settle_name; // 结算名字
  
  private String ch_id; // 渠道id
  
  private String ch_name; // 渠道名字
  
  private String ce_id; // 交易中心id
  
  private String ce_name; // 交易中心名字
  
  private String cz_type; // 充值类型
  
  private String cz_pc_mobile; // 终端
  
////////////////////////////////////////////////////////////////////////  
  
  private double order_rs_fee;  // 充值金额(充值回调表)
  
  private String order_rs_type; // 支付方式(充值回调表)
  
  private String order_rs_no;   // 流水号或批次号
  
////////////////////////////////////////////////////////////////////////  
  
  public String getOrder_rs_no() {
    return order_rs_no;
  }

  public String getCz_type() {
    return cz_type;
  }

  public void setCz_type(String cz_type) {
    this.cz_type = cz_type;
  }

  public String getCz_pc_mobile() {
    return cz_pc_mobile;
  }

  public void setCz_pc_mobile(String cz_pc_mobile) {
    this.cz_pc_mobile = cz_pc_mobile;
  }

  public String getRole_type() {
    return role_type;
  }

  public void setRole_type(String role_type) {
    this.role_type = role_type;
  }

  public String getAgent_id() {
    return agent_id;
  }

  public void setAgent_id(String agent_id) {
    this.agent_id = agent_id;
  }

  public String getAgent_name() {
    return agent_name;
  }

  public void setAgent_name(String agent_name) {
    this.agent_name = agent_name;
  }

  public String getSettle_id() {
    return settle_id;
  }

  public void setSettle_id(String settle_id) {
    this.settle_id = settle_id;
  }

  public String getSettle_name() {
    return settle_name;
  }

  public void setSettle_name(String settle_name) {
    this.settle_name = settle_name;
  }

  public String getCh_id() {
    return ch_id;
  }

  public void setCh_id(String ch_id) {
    this.ch_id = ch_id;
  }

  public String getCh_name() {
    return ch_name;
  }

  public void setCh_name(String ch_name) {
    this.ch_name = ch_name;
  }

  public String getCe_id() {
    return ce_id;
  }

  public void setCe_id(String ce_id) {
    this.ce_id = ce_id;
  }

  public String getCe_name() {
    return ce_name;
  }

  public void setCe_name(String ce_name) {
    this.ce_name = ce_name;
  }

  public void setOrder_rs_no(String order_rs_no) {
    this.order_rs_no = order_rs_no;
  }

  public String getOrder_rs_type() {
    return order_rs_type;
  }

  public void setOrder_rs_type(String order_rs_type) {
    this.order_rs_type = order_rs_type;
  }

  public double getOrder_rs_fee() {
    return order_rs_fee;
  }

  public void setOrder_rs_fee(double order_rs_fee) {
    this.order_rs_fee = order_rs_fee;
  }

  public String getOrder_id() {
    return order_id;
  }

  public void setOrder_id(String order_id) {
    this.order_id = order_id;
  }

  public double getOrder_money() {
    return order_money;
  }

  public void setOrder_money(double order_money) {
    this.order_money = order_money;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public Date getCreate_date() {
    return create_date;
  }

  public void setCreate_date(Date create_date) {
    this.create_date = create_date;
  }
  
}
