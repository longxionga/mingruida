package com.acl.utils.excel;

/**
 * 描述:
 * 枚举
 *
 * @aouthor 胡锋
 * @create 2018-06-04 17:01
 */
public enum  OrderState {

    CREATED("CREATED","创建"),
    PAY_WAIT("PAY_WAIT","支付等待"),
    PAY_SUCCESS("PAY_SUCCESS","支付成功"),
    PAY_FAILED("PAY_FAILED","支付失败"),
    ORDER_REFUND_WAIT("ORDER_REFUND_WAIT","退款中"),
    ORDER_REFUND_ALL("ORDER_REFUND_ALL","全额退款"),
    ORDER_REFUND_PART("ORDER_REFUND_PART","全额退款"),
    ORDER_REFUND_FAILED("ORDER_REFUND_FAILED","退款失败"),
    CLOSED("ORDER_CLOSED","订单关闭"),
    ORDER_REPEAL("ORDER_REPEAL","撤销中"),

    MACHANE_BOUND("MACHANE_BOUND","已绑定"),
    MACHANE_UNBOUND("MACHANE_UNBOUND","未绑定");

    private String code;
    private String desc;

    private OrderState(String code,String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OrderState getByCode(String code) {
        if (code == null) {
            return null;
        } else {
            OrderState[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                OrderState param = var1[var3];
                if (param.getCode().equals(code)) {
                    return param;
                }
            }

            return null;
        }
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return desc;
    }

}
