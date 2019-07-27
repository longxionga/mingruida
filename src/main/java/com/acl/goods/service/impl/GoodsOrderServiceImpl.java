package com.acl.goods.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acl.goods.dao.GoodsOrderDao;
import com.acl.goods.service.GoodsOrderService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.StringUtils;

/**
 * className:GoodsOrderServiceImpl
 * author:wangli
 * createDate:2017年4月25日 下午8:16:14
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Service
public class GoodsOrderServiceImpl implements GoodsOrderService {

    @Autowired
    private GoodsOrderDao goodsOrderDao;


//	@Resource
//	private OrderService orderService;

    @Override
    public PageList<?> queryGoodsOrder(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return goodsOrderDao.queryGoodsOrder(paramsMap, pageBounds);
    }

    @Override
    public void putInLogistics(HashMap<String, Object> paramsMap) {
        goodsOrderDao.updateGoodsOrder(paramsMap);
    }

    @Override
    public void sendOutGoodsOrder(HashMap<String, Object> paramsMap) {
//		String orderId=StringUtils.convertString(paramsMap.get("order_id"));
//		//根据orderId查询user_id
//		List<Map<String, Object>> user=goodsOrderDao.queryUserIdByOrderId(paramsMap);
//		try {
//			orderService.deliveredOrder(orderId, StringUtils.convertString(user.get(0).get("user_id")));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}


		
		/*Date currentDate = new Date();
		OrderStatus orderStatus=new OrderStatus();
		orderStatus.setOrderId(orderId);
		orderStatus.setOrderStatus(OrderStatusEnums.DELIVERED.getCode());
		orderStatus.setOrderStatusName(OrderStatusEnums.DELIVERED.getStatusDesc());
		orderStatus.setProcessTime(currentDate);
		orderStatus.setCreateTime(currentDate);
		orderStatus.setIsUse(RecordStatusEnums.VALID.getCode());
		com.acl.mall.order.domain.po.Order order=new com.acl.mall.order.domain.po.Order();
		order.setOrderId(StringUtils.convertString(paramsMap.get("order_id")));
		order.setOrderStatus(OrderStatusEnums.DELIVERED.getCode());
		order.setUserId(StringUtils.convertString(user.get(0).get("user_id")));
		orderMgrDao.deliveredOrder(order, orderStatus);*/
		/*String orderId=StringUtils.convertString(paramsMap.get("order_id"));
		Date currentDate = new Date();
		//修改订单状态
		paramsMap.put("logistics_status", OrderStatusEnums.DELIVERED.getCode());
		goodsOrderDao.updateGoodsOrder(paramsMap);
		//插入订单状态记录
		paramsMap.put("order_status", OrderStatusEnums.DELIVERED.getCode());
		paramsMap.put("process_time", sdf.format(new java.util.Date()));
		paramsMap.put("create_time", sdf.format(new java.util.Date()));
		paramsMap.put("is_use", RecordStatusEnums.VALID.getCode());
		goodsOrderDao.insertOrderStatus(paramsMap);
		//修改订单状态 插入订单状态记录到redis中
		OrderStatus orderStatus=new OrderStatus();
		orderStatus.setOrderId(orderId);
		orderStatus.setOrderStatus(OrderStatusEnums.DELIVERED.getCode());
		orderStatus.setOrderStatusName(OrderStatusEnums.DELIVERED.getStatusDesc());
		orderStatus.setProcessTime(currentDate);
		orderStatus.setCreateTime(currentDate);
		orderStatus.setIsUse(RecordStatusEnums.VALID.getCode());
		List<OrderStatus> statusList = new ArrayList<OrderStatus>();
        statusList.add(orderStatus);
        
        //根据orderId查询订单详情
        
        //更改redis订单信息
        String key = MessageFormat.format(OrderRedisKeyEnums.ORDER_INFO.getKey(),orderId);
       // updateRedisInfo(orderId,null,order,statusList);
        */

    }


}
