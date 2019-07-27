package com.acl.utils.tree;

import java.util.Date;
import java.util.List;

/**
 * className:ComboTree author:huangs createDate:2016年8月11日 下午5:30:51
 * vsersion:3.0 department:安创乐科技 description:部门管理下拉树模板类
 */
public class DeptTreeGrid {
    private String dept_id;
    private String dept_code;
    private String dept_name;
    private String dept_mobile;
    private Date create_date;
    private String is_use;
    private String dept_money;
    private int dept_ratio;
    private String dept_type;
    private String dept_database;
    private String dept_url;
    private String broker_url;
    private String dept_parent_id;
    private String dept_app_id;
    private String is_tj_man;
    private String tj_ratio;
    private String state;
    public String getDept_app_id() {
        return dept_app_id;
    }

    public void setDept_app_id(String dept_app_id) {
        this.dept_app_id = dept_app_id;
    }

    public String getDept_app_secret() {
        return dept_app_secret;
    }

    public void setDept_app_secret(String dept_app_secret) {
        this.dept_app_secret = dept_app_secret;
    }

    private String dept_app_secret;
    private List<DeptTreeGrid> children;

    public String getDept_id() {
	return dept_id;
    }

    public void setDept_id(String dept_id) {
	this.dept_id = dept_id;
    }

    public String getDept_code() {
	return dept_code;
    }

    public void setDept_code(String dept_code) {
	this.dept_code = dept_code;
    }

    public String getDept_name() {
	return dept_name;
    }

    public void setDept_name(String dept_name) {
	this.dept_name = dept_name;
    }

    public String getDept_mobile() {
	return dept_mobile;
    }

    public void setDept_mobile(String dept_mobile) {
	this.dept_mobile = dept_mobile;
    }

    public String getIs_use() {
	return is_use;
    }

    public void setIs_use(String is_use) {
	this.is_use = is_use;
    }

    public String getDept_type() {
	return dept_type;
    }

    public void setDept_type(String dept_type) {
	this.dept_type = dept_type;
    }

    public String getDept_database() {
	return dept_database;
    }

    public void setDept_database(String dept_database) {
	this.dept_database = dept_database;
    }

    public String getDept_url() {
	return dept_url;
    }

    public void setDept_url(String dept_url) {
	this.dept_url = dept_url;
    }

    public String getDept_parent_id() {
	return dept_parent_id;
    }

    public void setDept_parent_id(String dept_parent_id) {
	this.dept_parent_id = dept_parent_id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }


    public String getDept_money() {
        return dept_money;
    }

    public void setDept_money(String dept_money) {
        this.dept_money = dept_money;
    }

    public int getDept_ratio() {
        return dept_ratio;
    }

    public void setDept_ratio(int dept_ratio) {
        this.dept_ratio = dept_ratio;
    }

    public List<DeptTreeGrid> getChildren() {
	return children;
    }

    public void setChildren(List<DeptTreeGrid> children) {
	this.children = children;
    }

    public String getBroker_url() {
	return broker_url;
    }

    public void setBroker_url(String broker_url) {
	this.broker_url = broker_url;
    }

    public String getIs_tj_man() {
	return is_tj_man;
    }

    public void setIs_tj_man(String is_tj_man) {
	this.is_tj_man = is_tj_man;
    }

    public String getTj_ratio() {
	return tj_ratio;
    }

    public void setTj_ratio(String tj_ratio) {
	this.tj_ratio = tj_ratio;
    }

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
