package com.acl.utils.excel;

/**
 * 描述:
 *
 * @author 胡锋
 * @create 2019-03-12 21:27
 */
public class HKMachineInfo extends BasicInfo {
    //商户编号
    private String merchantCode;

    //商户名称
    private String merchantName;

    //服务商名称
    private String brokerName;

    //服务商编号
    private String brokerCode;

    //下级服务商名称
    private String subbrokerName;

    //商户类型
    private String merchantType;

    //商户所在地区
    private String merchantRovince;

    //商户状态
    private String merchantState;

    //机器编号
    private String machineCode;

    //机器类型
    private String machineType;

    //当前月交易
    private String currentMonthAmount;


    //三月月交易
    private String threeMonthAmount;

    //绑定时间
    private String bindTime;

    //绑定日期
    private String bindDay;

    //是否激活
    private String activation ;

    //激活原因
    private String activationNotes ;


    //激活类型
    private String activationType ;

    //审核状态
    private String auditStatus ;

    //费率类型
    private String rateType ;


    public String getActivationNotes() {
        return activationNotes;
    }

    public void setActivationNotes(String activationNotes) {
        this.activationNotes = activationNotes;
    }

    public String getActivationType() {
        return activationType;
    }

    public void setActivationType(String activationType) {
        this.activationType = activationType;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    public String getThreeMonthAmount() {
        return threeMonthAmount;
    }

    public void setThreeMonthAmount(String threeMonthAmount) {
        this.threeMonthAmount = threeMonthAmount;
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

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getBrokerCode() {
        return brokerCode;
    }

    public void setBrokerCode(String brokerCode) {
        this.brokerCode = brokerCode;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getCurrentMonthAmount() {
        return currentMonthAmount;
    }

    public void setCurrentMonthAmount(String currentMonthAmount) {
        this.currentMonthAmount = currentMonthAmount;
    }

    public String getBindTime() {
        return bindTime;
    }

    public void setBindTime(String bindTime) {
        this.bindTime = bindTime;
    }

    public String getBindDay() {
        return bindDay;
    }

    public void setBindDay(String bindDay) {
        this.bindDay = bindDay;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getSubbrokerName() {
        return subbrokerName;
    }

    public void setSubbrokerName(String subbrokerName) {
        this.subbrokerName = subbrokerName;
    }

    public String getMerchantRovince() {
        return merchantRovince;
    }

    public void setMerchantRovince(String merchantRovince) {
        this.merchantRovince = merchantRovince;
    }

    public String getMerchantState() {
        return merchantState;
    }

    public void setMerchantState(String merchantState) {
        this.merchantState = merchantState;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    @Override
    public String toString() {
        return "HKMachineInfo{" +
                "merchantCode='" + merchantCode + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", brokerName='" + brokerName + '\'' +
                ", brokerCode='" + brokerCode + '\'' +
                ", subbrokerName='" + subbrokerName + '\'' +
                ", merchantType='" + merchantType + '\'' +
                ", merchantRovince='" + merchantRovince + '\'' +
                ", merchantState='" + merchantState + '\'' +
                ", machineCode='" + machineCode + '\'' +
                ", machineType='" + machineType + '\'' +
                ", currentMonthAmount='" + currentMonthAmount + '\'' +
                ", threeMonthAmount='" + threeMonthAmount + '\'' +
                ", bindTime='" + bindTime + '\'' +
                ", bindDay='" + bindDay + '\'' +
                ", activation='" + activation + '\'' +
                ", activationNotes='" + activationNotes + '\'' +
                ", activationType='" + activationType + '\'' +
                ", auditStatus='" + auditStatus + '\'' +
                ", rateType='" + rateType + '\'' +
                '}';
    }
}
