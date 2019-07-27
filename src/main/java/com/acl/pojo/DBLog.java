package com.acl.pojo;

import java.io.Serializable;

public class DBLog implements Serializable {

	private static final long serialVersionUID = 2472748153218517958L;

	private String log_id;
	private String log_time;
	private String cq_params;
	private String method_name;
	private String action_message;
	private int is_ok;
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getLog_time() {
		return log_time;
	}
	public void setLog_time(String log_time) {
		this.log_time = log_time;
	}
	public String getCq_params() {
		return cq_params;
	}
	public void setCq_paramsAdd(String cq_params) {
		this.cq_params += "," + cq_params;
	}
	public void setCq_params(String cq_params) {
		this.cq_params = cq_params;
	}
	public String getMethod_name() {
		return method_name;
	}
	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}
	public String getAction_message() {
		return action_message;
	}
	public void setAction_message(String action_message) {
		this.action_message = action_message;
	}
	public int getIs_ok() {
		return is_ok;
	}
	public void setIs_ok(int is_ok) {
		this.is_ok = is_ok;
	}

}
