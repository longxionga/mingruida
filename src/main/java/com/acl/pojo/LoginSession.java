package com.acl.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.acl.component.SystemConfig;
import com.acl.utils.util.MD5Utils;
import com.acl.utils.util.StringUtils;

/**
 * className:LoginSession author:huangs createDate:2016年8月18日 下午6:00:24
 * vsersion:3.0 department:安创乐科技 description:登录sessionpojo
 */
public class LoginSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6132251679999769762L;
	private String user_id;
	private String user_name;
	private String user_password;
	private String user_nick_name;
	private String user_gender;
	private String user_mobile;
	private String user_qq;
	private String user_ip;
	private String login_date;
	private int error_count;
	private String dept_id;
	private String settle_id = "";// 会员单位 dept_type=3
	private String agent_id = "";// 代理商 dept_type=4
	private String dept_code;
	private String dept_name;
	private String dept_mobile;
	private String dept_money;
	private String dept_ratio;
	private String dept_type;
	private String dept_url;
	private String dept_parent_id;
	private int user_use;
	private int role_use;
	private int dept_use;
	private String role_id;
	private String role_name;
	private String current_ip;
	private Date current_date;
	private String auth_code;
	private boolean defaultPWD;

	private  String brand_id;
	private String company_name;

	public LoginSession(Map<String, Object> map) {
		this.setDept_id(StringUtils.checkString(map.get("dept_id")));
		this.setDept_type(StringUtils.checkString(map.get("dept_type")));
		this.setUser_id(StringUtils.checkString(map.get("user_id")));
		this.setUser_name(StringUtils.checkString(map.get("user_name")));
		this.setUser_nick_name(StringUtils.checkString(map.get("user_nick_name")));
		this.setUser_ip(StringUtils.checkString(map.get("user_ip")));
		this.setLogin_date(StringUtils.checkString(map.get("login_date")));
		this.setError_count(StringUtils.checkInt(map.get("error_count")));
		this.setDept_code(StringUtils.checkString(map.get("dept_code")));
		this.setDept_name(StringUtils.checkString(map.get("dept_name")));
		this.setDept_parent_id(StringUtils.checkString(map.get("dept_parent_id")));
		this.setRole_id(StringUtils.checkString(map.get("role_id")));
		this.setRole_name(StringUtils.checkString(map.get("role_name")));
		this.setUser_use(StringUtils.checkInt(map.get("user_use")));
		this.setDept_use(StringUtils.checkInt(map.get("dept_use")));
		this.setRole_use(StringUtils.checkInt(map.get("role_use")));
		this.setDefaultPWD(StringUtils.checkString(map.get("user_password")).equals(MD5Utils.MD5(SystemConfig.AncholPWD)) ? true : false );
		this.setBrand_id(StringUtils.checkString(map.get("brand_id")));
		this.setBrand_id(StringUtils.checkString(map.get("company_name")));
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_nick_name() {
		return user_nick_name;
	}

	public void setUser_nick_name(String user_nick_name) {
		this.user_nick_name = user_nick_name;
	}

	public String getUser_gender() {
		return user_gender;
	}

	public void setUser_gender(String user_gender) {
		this.user_gender = user_gender;
	}

	public String getUser_mobile() {
		return user_mobile;
	}

	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}

	public String getUser_qq() {
		return user_qq;
	}

	public void setUser_qq(String user_qq) {
		this.user_qq = user_qq;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}

	public String getLogin_date() {
		return login_date;
	}

	public void setLogin_date(String login_date) {
		this.login_date = login_date;
	}

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

	public String getDept_money() {
		return dept_money;
	}

	public void setDept_money(String dept_money) {
		this.dept_money = dept_money;
	}

	public String getDept_ratio() {
		return dept_ratio;
	}

	public void setDept_ratio(String dept_ratio) {
		this.dept_ratio = dept_ratio;
	}

	public String getDept_type() {
		return dept_type;
	}

	public void setDept_type(String dept_type) {
		this.dept_type = dept_type;
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

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getCurrent_ip() {
		return current_ip;
	}

	public void setCurrent_ip(String current_ip) {
		this.current_ip = current_ip;
	}

	public Date getCurrent_date() {
		return current_date;
	}

	public void setCurrent_date(Date current_date) {
		this.current_date = current_date;
	}

	public int getError_count() {
		return error_count;
	}

	public void setError_count(int error_count) {
		this.error_count = error_count;
	}

	public String getAuth_code() {
		return auth_code;
	}

	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	public int getUser_use() {
		return user_use;
	}

	public void setUser_use(int user_use) {
		this.user_use = user_use;
	}

	public int getRole_use() {
		return role_use;
	}

	public void setRole_use(int role_use) {
		this.role_use = role_use;
	}

	public int getDept_use() {
		return dept_use;
	}

	public void setDept_use(int dept_use) {
		this.dept_use = dept_use;
	}

	public String getSettle_id() {
		return settle_id;
	}

	public void setSettle_id(String settle_id) {
		this.settle_id = settle_id;
	}

	public String getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}

	public boolean isDefaultPWD() {
		return defaultPWD;
	}

	public void setDefaultPWD(boolean defaultPWD) {
		this.defaultPWD = defaultPWD;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
}
