package com.acl.pojo;

import java.io.Serializable;
import java.util.Date;

public class CzOrderRs implements Serializable {

  private static final long serialVersionUID = -6653717345356547957L;

  private String order_rs_id;
  
  private String order_id;
  
  private String order_rs_no;
  
  private String user_id;
  
  private double order_rs_fee;
  
  private double order_rs_brokeage;
  
  private String order_rs_type;
  
  private String order_rs_status;
  
  private Date create_date;
  
  private String is_use;
  
  public String getOrder_rs_type() {
    return order_rs_type;
  }

  public void setOrder_rs_type(String order_rs_type) {
    this.order_rs_type = order_rs_type;
  }

  public String getOrder_rs_id() {
    return order_rs_id;
  }

  public void setOrder_rs_id(String order_rs_id) {
    this.order_rs_id = order_rs_id;
  }

  public String getOrder_id() {
    return order_id;
  }

  public void setOrder_id(String order_id) {
    this.order_id = order_id;
  }

  public String getOrder_rs_no() {
    return order_rs_no;
  }

  public void setOrder_rs_no(String order_rs_no) {
    this.order_rs_no = order_rs_no;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public double getOrder_rs_fee() {
    return order_rs_fee;
  }

  public void setOrder_rs_fee(double order_rs_fee) {
    this.order_rs_fee = order_rs_fee;
  }

  public double getOrder_rs_brokeage() {
    return order_rs_brokeage;
  }

  public void setOrder_rs_brokeage(double order_rs_brokeage) {
    this.order_rs_brokeage = order_rs_brokeage;
  }

  public String getOrder_rs_status() {
    return order_rs_status;
  }

  public void setOrder_rs_status(String order_rs_status) {
    this.order_rs_status = order_rs_status;
  }

  public Date getCreate_date() {
    return create_date;
  }

  public void setCreate_date(Date create_date) {
    this.create_date = create_date;
  }

  public String getIs_use() {
    return is_use;
  }

  public void setIs_use(String is_use) {
    this.is_use = is_use;
  }
  
}
