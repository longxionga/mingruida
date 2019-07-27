package com.acl.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class BuildAndSellInfo implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3830570408653933751L;
	//统计字段
	private String sum_1; //交易额
	private BigDecimal sum_2; //买入手数
	private double sum_3; //手续费 ==仓储费
	private String sum_4; 
	private String sum_5; 
	private String sum_6; 	
	private String user_id;	
	private String order_id;
	private String trading_rule;	
	private String zc_order_id;
	
	public String getSum_1() {
		return sum_1;
	}
	public void setSum_1(String sum_1) {
		this.sum_1 = sum_1;
	}
	public BigDecimal getSum_2() {
		return sum_2;
	}
	public void setSum_2(BigDecimal sum_2) {
		this.sum_2 = sum_2;
	}
	public double getSum_3() {
		return sum_3;
	}
	public void setSum_3(double sum_3) {
		this.sum_3 = sum_3;
	}
	public String getSum_4() {
		return sum_4;
	}
	public void setSum_4(String sum_4) {
		this.sum_4 = sum_4;
	}
	public String getSum_5() {
		return sum_5;
	}
	public void setSum_5(String sum_5) {
		this.sum_5 = sum_5;
	}
	public String getSum_6() {
		return sum_6;
	}
	public void setSum_6(String sum_6) {
		this.sum_6 = sum_6;
	}
	public String getUserId() {
		return user_id;
	}
	public void setUseId(String user_id) {
		this.user_id = user_id;
	}
	public String getOrderId() {
		return order_id;
	}
	public void setOrderId(String order_id) {
		this.order_id = order_id;
	}
	public String getTradeRule() {
		return trading_rule;
	}
	public void setTradeRule(String trading_rule) {
		this.trading_rule = trading_rule;
	}
	public String getZcOrderId() {
		return zc_order_id;
	}
	public void setZcOrderId(String zc_order_id) {
		this.zc_order_id = zc_order_id;
	}

	
}
