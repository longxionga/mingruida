package com.acl.pojo;

import java.io.Serializable;
import java.util.Date;

public class UserProfitLoss implements Serializable {

  private static final long serialVersionUID = -3000051366647032317L;

  private String profitloss_id;
  
  private String user_id;
  
  private double user_money_before;
  
  private double user_money;
  
  private double user_money_after;
  
  private String money_type;
  
  private Date create_date;
  
  private String is_use;
  
  private String order_rs_no;
  
  private String order_id;

  public String getProfitloss_id() {
    return profitloss_id;
  }

  public void setProfitloss_id(String profitloss_id) {
    this.profitloss_id = profitloss_id;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public double getUser_money_before() {
    return user_money_before;
  }

  public void setUser_money_before(double user_money_before) {
    this.user_money_before = user_money_before;
  }

  public double getUser_money() {
    return user_money;
  }

  public void setUser_money(double user_money) {
    this.user_money = user_money;
  }

  public double getUser_money_after() {
    return user_money_after;
  }

  public void setUser_money_after(double user_money_after) {
    this.user_money_after = user_money_after;
  }

  public String getMoney_type() {
    return money_type;
  }

  public void setMoney_type(String money_type) {
    this.money_type = money_type;
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

  public String getOrder_rs_no() {
    return order_rs_no;
  }

  public void setOrder_rs_no(String order_rs_no) {
    this.order_rs_no = order_rs_no;
  }

public String getOrder_id() {
	return order_id;
}

public void setOrder_id(String order_id) {
	this.order_id = order_id;
}
  
  
  
}
