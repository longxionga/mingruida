package com.acl.goods.dao;

import java.util.List;

import com.acl.pojo.OrderStatus;

/**
 * 订单状态表数据库操作接口
 *
 * @author: wangyang
 * @version: 1.0
 * @datetime: 2017-04-19 20:39:34
 */
public interface OrderStatusDao {

    /**
     * 根据主键查询OrderStatus对象
     *
     * @param statusId
     * @return OrderStatus对象
     */
    OrderStatus selectByPrimaryKey(Long statusId);

    /**
     * 根据查询条件查询符合条件的OrderStatus对象
     *
     * @param orderStatus
     * @return 符合条件的OrderStatus对象List
     */
    List<OrderStatus> select(OrderStatus orderStatus);

    /**
     * 根据主键删除OrderStatus对象
     *
     * @param statusId
     * @return 影响条件数
     */
    int deleteByPrimaryKey(Long statusId);

    /**
     * 根据条件删除符合条件的OrderStatus对象
     *
     * @param orderStatus
     * @return 影响条件数
     */
    int delete(OrderStatus orderStatus);

    /**
     * 插入OrderStatus对象
     *
     * @param orderStatus
     * @return 影响条件数
     */
    int insert(OrderStatus orderStatus);

    /**
     * 更新OrderStatus对象
     *
     * @param orderStatus
     * @return 影响条件数
     */
    int update(OrderStatus orderStatus);

}
