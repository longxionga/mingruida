package com.acl.platform.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acl.platform.dao.IPlatformCommonPaymentDao;
import com.acl.platform.service.IPlatformCommonPaymentService;
import com.acl.pojo.CzOrder;

/**
 *className:CommonPaymentServiceImpl
 *author:zhangyi
 *createDate:2016年8月23日 下午3:34:39
 *vsersion:1.0
 *department:安创乐科技
 *description:
 */
@Service
public class IPlatformCommonPaymentServiceImpl implements IPlatformCommonPaymentService {
  
  @Autowired
  private IPlatformCommonPaymentDao CommonPaymentDao;
  
  /**
   * 通过订单号查询用户充值订单
   **/
  @Override
  public CzOrder queryCzOrderByOrderId(Map<String, Object> map) {
    return CommonPaymentDao.queryCzOrderByOrderId(map);
  }

  /**
   * 通过订单号查询充值用户
   **/
  @Override
  public Map<String, Object> queryBrokerParentInfo(Map<String, Object> map) {
    return CommonPaymentDao.queryBrokerParentInfo(map);
  }
  
}
