package com.acl.utils.excel;

/**
 * 描述:
 *
 * @author 胡锋
 * @create 2019-03-12 21:27
 */
public class ShuaBaoTradeOrderInfo extends ShuaBaoInfo {
    //订单编号
    private String orderId;

    //订单类型
    private String orderType;

    //订单状态
    private String orderState;

    //结算类型
    private String settleType;

    //支付类型
    private String payType;

    //支付卡类型
    private String payCardType;

    //支付卡号
    private String payCardNo;

    //商户费率
    private String merchantRate;


    //交易金额
    private String totalAmount;


    //真实金额
    private String realAmount;

    //绑定时间
    private String tradeTime;

    //绑定日期
    private String tradeDay;

    //到账状态
    private String arrivalState;

    public String getArrivalState() {
        return arrivalState;
    }

    public void setArrivalState(String arrivalState) {
        this.arrivalState = arrivalState;
    }

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

    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
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

    public String getMerchantRate() {
        return merchantRate;
    }

    public void setMerchantRate(String merchantRate) {
        this.merchantRate = merchantRate;
    }

    @Override
    public String getTotalAmount() {
        return totalAmount;
    }

    @Override
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String getRealAmount() {
        return realAmount;
    }

    @Override
    public void setRealAmount(String realAmount) {
        this.realAmount = realAmount;
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
}
