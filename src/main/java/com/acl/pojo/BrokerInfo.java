package com.acl.pojo;

import java.io.Serializable;

/**
 * @author: chenwei @version：1.0
 * @创建时间：2016年8月10日 下午1:13:22
 * @说明：经纪人
 *
 */
public class BrokerInfo implements Serializable {
	private static final long serialVersionUID = 8823446191183653036L;

	private String broker_id;
	private String broker_name;
	private String broker_code;

	private String broker_parent_id;
	private String broker_mobile;
	private String broker_password;

	private String broker_money;
	private String broker_incode;
	private String dept_id;
	private String dept_name;
	private String dept_code;

	private String create_date;
	private String is_use;

	private String broker_wx_id;
	private String broker_zf_wx_id;

	public String getBroker_id() {
		return broker_id;
	}

	public void setBroker_id(String broker_id) {
		this.broker_id = broker_id;
	}

	public String getBroker_name() {
		return broker_name;
	}

	public void setBroker_name(String broker_name) {
		this.broker_name = broker_name;
	}

	public String getBroker_code() {
		return broker_code;
	}

	public void setBroker_code(String broker_code) {
		this.broker_code = broker_code;
	}

	public String getBroker_parent_id() {
		return broker_parent_id;
	}

	public void setBroker_parent_id(String broker_parent_id) {
		this.broker_parent_id = broker_parent_id;
	}

	public String getBroker_mobile() {
		return broker_mobile;
	}

	public void setBroker_mobile(String broker_mobile) {
		this.broker_mobile = broker_mobile;
	}

	public String getBroker_password() {
		return broker_password;
	}

	public void setBroker_password(String broker_password) {
		this.broker_password = broker_password;
	}

	public String getBroker_money() {
		return broker_money;
	}

	public void setBroker_money(String broker_money) {
		this.broker_money = broker_money;
	}

	public String getBroker_incode() {
		return broker_incode;
	}

	public void setBroker_incode(String broker_incode) {
		this.broker_incode = broker_incode;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getIs_use() {
		return is_use;
	}

	public void setIs_use(String is_use) {
		this.is_use = is_use;
	}

	public String getBroker_wx_id() {
		return broker_wx_id;
	}

	public void setBroker_wx_id(String broker_wx_id) {
		this.broker_wx_id = broker_wx_id;
	}

	public String getBroker_zf_wx_id() {
		return broker_zf_wx_id;
	}

	public void setBroker_zf_wx_id(String broker_zf_wx_id) {
		this.broker_zf_wx_id = broker_zf_wx_id;
	}

}
