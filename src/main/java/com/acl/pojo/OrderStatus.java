package com.acl.pojo;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 订单状态表
 *
 * @author: wangyang
 * @version: 1.0
 * @datetime: 2017-04-19 20:39:34
 */
public class OrderStatus implements Serializable {

    // 状态ID
    @JSONField(serialize = false)
    private Long statusId;
    // 订单ID
    @JSONField(serialize = false)
    private String orderId;
    // 状态
    private Integer orderStatus;
    // 时间
    private Date processTime;
    // 建立时间
    @JSONField(serialize = false)
    private Date createTime;
    // 更新时间
    @JSONField(serialize = false)
    private Date updateTime;
    // 是否可用
    @JSONField(serialize = false)
    private String isUse;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    
  
    public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}
