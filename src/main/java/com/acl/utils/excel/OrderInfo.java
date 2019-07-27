package com.acl.utils.excel;

import java.io.Serializable;
import java.util.Map;

public class OrderInfo extends BasicInfo {
    //交易流水号
    private String orderId;

    //交易类型
    private String orderType;

    //交易状态
    private String orderState;

    //交易时间
    private String tradeTime;

    //交易日期
    private String tradeDay;

    //合计金额
    private String orderAmount;

    //合计金额
    private String totalAmount;

    //支付类型
    private String payType;

    //支付卡类型
    private String payCardType;
    //支付卡号
    private String payCardNo;

    //商户编号
    private String merchantCode;

    //商户名称
    private String merchantName;

    //商户费率
    private String merchantRate;

    //机具编号
    private String machineCode;

    //机具名称
    private String machineName;

    //机具类型
    private String machineType;

    //结算类型
    private String settleType;

    //到账状态
    private String arrivalState;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getTradeDay() {
        return tradeDay;
    }

    public void setTradeDay(String tradeDay) {
        this.tradeDay = tradeDay;
    }

    @Override
    public String getTotalAmount() {
        return totalAmount;
    }

    @Override
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayCardType() {
        return payCardType;
    }

    public void setPayCardType(String payCardType) {
        this.payCardType = payCardType;
    }

    public String getPayCardNo() {
        return payCardNo;
    }

    public void setPayCardNo(String payCardNo) {
        this.payCardNo = payCardNo;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantRate() {
        return merchantRate;
    }

    public void setMerchantRate(String merchantRate) {
        this.merchantRate = merchantRate;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }

    public String getArrivalState() {
        return arrivalState;
    }

    public void setArrivalState(String arrivalState) {
        this.arrivalState = arrivalState;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }
}
