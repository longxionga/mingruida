package com.acl.pojo;

import java.io.Serializable;
import java.util.Date;

public class Wallet implements Serializable {

  private static final long serialVersionUID = -1932335396432323464L;

  private String wallet_id;
  
  private String user_id;
  
  private double user_money;
  
  private Date create_date;
  
  private String is_use;
  
  private Date update_date;

  public String getWallet_id() {
    return wallet_id;
  }

  public void setWallet_id(String wallet_id) {
    this.wallet_id = wallet_id;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public double getUser_money() {
    return user_money;
  }

  public void setUser_money(double user_money) {
    this.user_money = user_money;
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

  public Date getUpdate_date() {
    return update_date;
  }

  public void setUpdate_date(Date update_date) {
    this.update_date = update_date;
  }
  
}
