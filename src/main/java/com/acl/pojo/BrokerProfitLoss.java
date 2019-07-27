package com.acl.pojo;

import java.io.Serializable;
import java.util.Date;

public class BrokerProfitLoss implements Serializable {

  private static final long serialVersionUID = 1299506276361895198L;
  
  private String broker_profiloss_id;
  
  private String broker_id;
  
  private double broker_money_before;
  
  private double broker_money;
  
  private double broker_money_after;
  
  private String money_type;
  
  private Date create_date;
  
  private String is_use;
  
  private String order_id;

  public String getBroker_profiloss_id() {
    return broker_profiloss_id;
  }

  public void setBroker_profiloss_id(String broker_profiloss_id) {
    this.broker_profiloss_id = broker_profiloss_id;
  }

  public String getBroker_id() {
    return broker_id;
  }

  public void setBroker_id(String broker_id) {
    this.broker_id = broker_id;
  }

  public double getBroker_money_before() {
    return broker_money_before;
  }

  public void setBroker_money_before(double broker_money_before) {
    this.broker_money_before = broker_money_before;
  }

  public double getBroker_money() {
    return broker_money;
  }

  public void setBroker_money(double broker_money) {
    this.broker_money = broker_money;
  }

  public double getBroker_money_after() {
    return broker_money_after;
  }

  public void setBroker_money_after(double broker_money_after) {
    this.broker_money_after = broker_money_after;
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

public String getOrder_id() {
	return order_id;
}

public void setOrder_id(String order_id) {
	this.order_id = order_id;
}
  
  

}
