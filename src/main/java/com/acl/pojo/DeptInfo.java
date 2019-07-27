package com.acl.pojo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class DeptInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6876026525797881710L;
	/**
	 * 
	 */
    @JSONField(name="dept_id")
	private int dept_id;
	private String dept_code;
	private String dept_name;
	private String dept_mobile;
	private String create_date;
	private int is_use;
	private String dept_money;
	private int ce_allot;
	private int ch_allot;
	private int s_allot;
	private int dept_ratio;
	private String dept_type;
	private String dept_url;
	private String broker_url;
	private int is_tj_man;
	private int tj_ratio;
    @JSONField(name="_parentId")
	private int _parentId;
	private String dept_app_id;
	private String dept_app_secret;
	private String dept_title;
	private String state;

	public int getDept_id() {
		return dept_id;
	}

	public String getDept_code() {
		return dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public String getDept_mobile() {
		return dept_mobile;
	}

	public String getCreate_date() {
		return create_date;
	}

	public int getIs_use() {
		return is_use;
	}

	public String getDept_money() {
		return dept_money;
	}

	public int getDept_ratio() {
		return dept_ratio;
	}

	public String getDept_type() {
		return dept_type;
	}

	public String getDept_url() {
		return dept_url;
	}

	public String getBroker_url() {
		return broker_url;
	}

	public int getIs_tj_man() {
		return is_tj_man;
	}

	public int getTj_ratio() {
		return tj_ratio;
	}

	public int get_parentId() {
		return _parentId;
	}

	public String getDept_app_id() {
		return dept_app_id;
	}

	public String getDept_app_secret() {
		return dept_app_secret;
	}

	public String getState() {
		return state;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public void setDept_mobile(String dept_mobile) {
		this.dept_mobile = dept_mobile;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public void setIs_use(int is_use) {
		this.is_use = is_use;
	}

	public void setDept_money(String dept_money) {
		this.dept_money = dept_money;
	}

	public void setDept_ratio(int dept_ratio) {
		this.dept_ratio = dept_ratio;
	}

	public void setDept_type(String dept_type) {
		this.dept_type = dept_type;
	}

	public void setDept_url(String dept_url) {
		this.dept_url = dept_url;
	}

	public void setBroker_url(String broker_url) {
		this.broker_url = broker_url;
	}

	public void setIs_tj_man(int is_tj_man) {
		this.is_tj_man = is_tj_man;
	}

	public void setTj_ratio(int tj_ratio) {
		this.tj_ratio = tj_ratio;
	}

	public void set_parentId(int _parentId) {
		this._parentId = _parentId;
	}

	public void setDept_app_id(String dept_app_id) {
		this.dept_app_id = dept_app_id;
	}

	public void setDept_app_secret(String dept_app_secret) {
		this.dept_app_secret = dept_app_secret;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getCe_allot() {
		return ce_allot;
	}

	public void setCe_allot(int ce_allot) {
		this.ce_allot = ce_allot;
	}

	public int getCh_allot() {
		return ch_allot;
	}

	public void setCh_allot(int ch_allot) {
		this.ch_allot = ch_allot;
	}

	public int getS_allot() {
		return s_allot;
	}

	public void setS_allot(int s_allot) {
		this.s_allot = s_allot;
	}

	public String getDept_title() {
		return dept_title;
	}

	public void setDept_title(String dept_title) {
		this.dept_title = dept_title;
	}

}
