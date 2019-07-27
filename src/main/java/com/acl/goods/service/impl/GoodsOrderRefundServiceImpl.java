package com.acl.goods.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.acl.goods.dao.GoodsOrderRefundDao;
import com.acl.goods.service.GoodsOrderRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * className:GoodsOrderRefundServiceImpl
 * author:wangli
 * createDate:2017年4月25日 下午3:47:35
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Service
@Transactional
public class GoodsOrderRefundServiceImpl implements GoodsOrderRefundService {


    @Autowired
    private GoodsOrderRefundDao goodsOrderRefundDao;


//	@Autowired
//	private OrderStatusDao orderStatusDao;
//
//	@Resource
//	private OrderDao orderDao;
//
//
//	@Autowired
//	private UserCapitalService userCapitalService;
//
//	@Autowired
//	private StringRedisTemplate redisTemplate;

    @Override
    public PageList<?> queryGoodsOrderRefund(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return goodsOrderRefundDao.queryGoodsOrderRefund(paramsMap, pageBounds);
    }

    /**
     * 同意流程:
     * 修改 t_front_mall_order_refund   status=2,reject_status=2,reject_time=now,update_time=now
     * 修改 t_front_mall_order          order_status=51,update_time=now
     * 增加 t_front_mall_order_status   order_status=51,create_time=now
     * 增加 t_front_user_profitloss  use_type=27
     * 更新mysql 用户余额
     * 删除redis order_id: {0}
     * 更新redis余额
     */
//	@Override
//	public void agreeGoodsOrderRefund(HashMap<String, Object> paramsMap) throws Exception {
//		Map<String, Object> result = goodsOrderRefundDao.getGoodsOrderRefund(paramsMap);
//		if(result!= null ){
//			//更新退款表状态,为完成
//			BigDecimal refund_money = (BigDecimal)result.get("refund_money");
//			paramsMap.put("status", "2");
//			paramsMap.put("reject_status", "2");
//			paramsMap.put("reject_time", new Date());
//			paramsMap.put("update_time", new Date());
//			goodsOrderRefundDao.updateGoodsOrderRefund(paramsMap);
//
//			//插入订单状态记录
//			OrderStatus orderStatus = new OrderStatus();
//			orderStatus.setOrderId((String)result.get("order_id"));
//			orderStatus.setIsUse("1");
//			orderStatus.setOrderStatus(OrderStatusEnums.CLOSE.getCode());
//			orderStatus.setCreateTime(new Date());
//			orderStatus.setProcessTime(new Date());
//			orderStatusDao.save(orderStatus);
//
//			//更新订单表状态为51,结束
//			Order order = new Order();
//			order.setOrderId((String)result.get("order_id"));
//			order.setOrderStatus(OrderStatusEnums.CLOSE.getCode());
//			order.setUpdateTime(new Date());
//			order.setAssignStatus(BrokerageStatusEnums.NOT_HANDLE.getCode());
//			orderDao.update(order);
//			//用户退款
//			userCapitalService.changeUserCapital((String)result.get("user_id"),refund_money, CapitalOperateEnums.ADD, CapitalUseTypeEnums.REFUND,order.getOrderId(),true);
//
//			//删除redis记录
//		      redisTemplate.delete("orderInfo:"+order.getOrderId());
//
//		}
//	}
//	@Override
//	public void disAgreeGoodsOrderRefund(HashMap<String, Object> paramsMap) {
//		Map<String, Object> result = goodsOrderRefundDao.getGoodsOrderRefund(paramsMap);
//		if(result!= null ){
//			//更新退款表状态,为完成
//			paramsMap.put("status", "2");
//			paramsMap.put("reject_status", "3");
//			paramsMap.put("reject_time",new Date());
//			paramsMap.put("update_time", new Date());
//			goodsOrderRefundDao.updateGoodsOrderRefund(paramsMap);
//
//			//插入订单状态记录
//			OrderStatus orderStatus = new OrderStatus();
//			orderStatus.setOrderId((String)result.get("order_id"));
//			orderStatus.setIsUse("1");
//			orderStatus.setOrderStatus(OrderStatusEnums.CLOSE.getCode());
//			orderStatus.setCreateTime(new Date());
//			orderStatus.setProcessTime(new Date());
//			orderStatusDao.save(orderStatus);
//
//			//更新订单表状态为51,结束
//			Order order = new Order();
//			order.setOrderId((String)result.get("order_id"));
//			order.setOrderStatus(OrderStatusEnums.CLOSE.getCode());
//			order.setUpdateTime(new Date());
//			order.setAssignStatus(BrokerageStatusEnums.TO_BE_ASSIGNED.getCode());
//			order.setAssignDate(new Date());
//			orderDao.update(order);
//			//删除redis记录
//		      redisTemplate.delete("orderInfo:"+order.getOrderId());
//		}
//
//	}
    @Override
    public void updGoodsOrderRefund(HashMap<String, Object> paramsMap) {
        paramsMap.put("update_time", new Date());
        goodsOrderRefundDao.updateGoodsOrderRefund(paramsMap);
    }


}
