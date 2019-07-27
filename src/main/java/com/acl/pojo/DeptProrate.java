package com.acl.pojo;

import java.io.Serializable;
import java.util.Date;

public class DeptProrate implements Serializable {

  private static final long serialVersionUID = 3146731735397781157L;

  private String sys_id;
  
  private String p_id;
  
  private String ce_id;
  
  private String ch_id;
  
  private String s_id;
  
  private String a_id;
  
  private String b_id;
  
  private String p_allot;
  
  private String ce_allot;
  
  private String ch_allot;
  
  private String s_allot;
  
  private String a_allot;
  
  private String b_allot;
  
  private String is_use;
  
  private Date create_date;

  public String getSys_id() {
    return sys_id;
  }

  public void setSys_id(String sys_id) {
    this.sys_id = sys_id;
  }

  public String getP_id() {
    return p_id;
  }

  public void setP_id(String p_id) {
    this.p_id = p_id;
  }

  public String getCe_id() {
    return ce_id;
  }

  public void setCe_id(String ce_id) {
    this.ce_id = ce_id;
  }

  public String getCh_id() {
    return ch_id;
  }

  public void setCh_id(String ch_id) {
    this.ch_id = ch_id;
  }

  public String getS_id() {
    return s_id;
  }

  public void setS_id(String s_id) {
    this.s_id = s_id;
  }

  public String getA_id() {
    return a_id;
  }

  public void setA_id(String a_id) {
    this.a_id = a_id;
  }

  public String getB_id() {
    return b_id;
  }

  public void setB_id(String b_id) {
    this.b_id = b_id;
  }

  public String getP_allot() {
    return p_allot;
  }

  public void setP_allot(String p_allot) {
    this.p_allot = p_allot;
  }

  public String getCe_allot() {
    return ce_allot;
  }

  public void setCe_allot(String ce_allot) {
    this.ce_allot = ce_allot;
  }

  public String getCh_allot() {
    return ch_allot;
  }

  public void setCh_allot(String ch_allot) {
    this.ch_allot = ch_allot;
  }

  public String getS_allot() {
    return s_allot;
  }

  public void setS_allot(String s_allot) {
    this.s_allot = s_allot;
  }

  public String getA_allot() {
    return a_allot;
  }

  public void setA_allot(String a_allot) {
    this.a_allot = a_allot;
  }

  public String getB_allot() {
    return b_allot;
  }

  public void setB_allot(String b_allot) {
    this.b_allot = b_allot;
  }

  public String getIs_use() {
    return is_use;
  }

  public void setIs_use(String is_use) {
    this.is_use = is_use;
  }

  public Date getCreate_date() {
    return create_date;
  }

  public void setCreate_date(Date create_date) {
    this.create_date = create_date;
  }

}
