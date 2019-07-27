package com.acl.pojo;

import java.io.Serializable;
import java.util.Date;

public class TxOrder implements Serializable {

	private static final long serialVersionUID = -1615169025299019672L;

	private String tx_order_id;

	private double tx_money;

	private String user_id;

	private Date create_date;

	private String user_name;

	private String user_bank;

	private String user_bank_address;

	private String user_prov;

	private String user_city;

	private String bank_type;

	private String tx_type; // 提现类型

	private String tx_pc_mobile;  // 终端

	private String is_use;

	private String agent_id;

	private String agent_name;

	private String settle_id;

	private String settle_name;

	private String ce_id;

	private String ce_name;

	private String ch_id;

	private String ch_name;

	private String role_type;

	private String order_rs_no; // 订单流水号或批次号
		
	private String returnMsg;

	public String getOrder_rs_no() {
		return order_rs_no;
	}

	public void setOrder_rs_no(String order_rs_no) {
		this.order_rs_no = order_rs_no;
	}

	public String getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}

	public String getSettle_id() {
		return settle_id;
	}

	public void setSettle_id(String settle_id) {
		this.settle_id = settle_id;
	}

	public String getSettle_name() {
		return settle_name;
	}

	public void setSettle_name(String settle_name) {
		this.settle_name = settle_name;
	}

	public String getCe_id() {
		return ce_id;
	}

	public void setCe_id(String ce_id) {
		this.ce_id = ce_id;
	}

	public String getCe_name() {
		return ce_name;
	}

	public void setCe_name(String ce_name) {
		this.ce_name = ce_name;
	}

	public String getCh_id() {
		return ch_id;
	}

	public void setCh_id(String ch_id) {
		this.ch_id = ch_id;
	}

	public String getCh_name() {
		return ch_name;
	}

	public void setCh_name(String ch_name) {
		this.ch_name = ch_name;
	}

	public String getRole_type() {
		return role_type;
	}

	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}

	public String getTx_order_id() {
		return tx_order_id;
	}

	public void setTx_order_id(String tx_order_id) {
		this.tx_order_id = tx_order_id;
	}

	public double getTx_money() {
		return tx_money;
	}

	public void setTx_money(double tx_money) {
		this.tx_money = tx_money;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_bank() {
		return user_bank;
	}

	public void setUser_bank(String user_bank) {
		this.user_bank = user_bank;
	}

	public String getUser_bank_address() {
		return user_bank_address;
	}

	public void setUser_bank_address(String user_bank_address) {
		this.user_bank_address = user_bank_address;
	}

	public String getUser_prov() {
		return user_prov;
	}

	public void setUser_prov(String user_prov) {
		this.user_prov = user_prov;
	}

	public String getUser_city() {
		return user_city;
	}

	public void setUser_city(String user_city) {
		this.user_city = user_city;
	}

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public String getTx_type() {
		return tx_type;
	}

	public void setTx_type(String tx_type) {
		this.tx_type = tx_type;
	}

	public String getTx_pc_mobile() {
		return tx_pc_mobile;
	}

	public void setTx_pc_mobile(String tx_pc_mobile) {
		this.tx_pc_mobile = tx_pc_mobile;
	}

	public String getIs_use() {
		return is_use;
	}

	public void setIs_use(String is_use) {
		this.is_use = is_use;
	}



	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	
	

}
