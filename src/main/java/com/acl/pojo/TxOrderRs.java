package com.acl.pojo;

import java.io.Serializable;
import java.util.Date;

public class TxOrderRs implements Serializable  {

  private static final long serialVersionUID = 4285790673292850866L;

  private String tx_rs_id;
  
  private String user_id;
  
  private String tx_order_id;
  
  private String tx_rs_status;
  
  private String tx_number;
  
  private double tx_rs_money;
  
  private double tx_rs_brokerage;
  
  private Date create_date;
  
  private String is_use;
  
  private String tx_type;

  public String getIs_use() {
    return is_use;
  }

  public void setIs_use(String is_use) {
    this.is_use = is_use;
  }

  public String getTx_type() {
    return tx_type;
  }

  public void setTx_type(String tx_type) {
    this.tx_type = tx_type;
  }

  public String getTx_rs_id() {
    return tx_rs_id;
  }

  public void setTx_rs_id(String tx_rs_id) {
    this.tx_rs_id = tx_rs_id;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getTx_order_id() {
    return tx_order_id;
  }

  public void setTx_order_id(String tx_order_id) {
    this.tx_order_id = tx_order_id;
  }

  public String getTx_rs_status() {
    return tx_rs_status;
  }

  public void setTx_rs_status(String tx_rs_status) {
    this.tx_rs_status = tx_rs_status;
  }

  public String getTx_number() {
    return tx_number;
  }

  public void setTx_number(String tx_number) {
    this.tx_number = tx_number;
  }

  public double getTx_rs_money() {
    return tx_rs_money;
  }

  public void setTx_rs_money(double tx_rs_money) {
    this.tx_rs_money = tx_rs_money;
  }

  public double getTx_rs_brokerage() {
    return tx_rs_brokerage;
  }

  public void setTx_rs_brokerage(double tx_rs_brokerage) {
    this.tx_rs_brokerage = tx_rs_brokerage;
  }

  public Date getCreate_date() {
    return create_date;
  }

  public void setCreate_date(Date create_date) {
    this.create_date = create_date;
  }

}
