package com.acl.platform.service.impl;

import com.acl.platform.dao.IPlatformMergeOrderDao;
import com.acl.platform.dao.PlatformGiftOrderDao;
import com.acl.platform.service.PlatformGiftOrderService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class PlatformGiftOrderServiceImpl implements PlatformGiftOrderService {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private PlatformGiftOrderDao platformGiftOrderDao;

    @Autowired
    private IPlatformMergeOrderDao platformMergeOrderDao;


    @Override
    public PageList<?> queryGiftOrder(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return platformGiftOrderDao.queryGiftOrder(paramsMap,pageBounds);
    }

    @Override
    public void updateLogistics(HashMap<String, Object> paramsMap) {
        paramsMap.put("update_time",sdf.format(new Date()));
        platformGiftOrderDao.updateLogistics(paramsMap);
        platformGiftOrderDao.updateTrade(paramsMap);

        //修改状态  判断是否状态为代发货 如果是代发货则更改状态为已发货
        HashMap<String,Object> orderMap=new HashMap<>();
         orderMap.put("status","3");
        orderMap.put("update_time",new Date());
        orderMap.put("post_id",paramsMap.get("id"));

        platformGiftOrderDao.updateOrderStatus(orderMap);

    }

    @Override
    public void updateOrder(HashMap<String, Object> paramsMap) {
        paramsMap.put("update_time",sdf.format(new Date()));
        platformGiftOrderDao.updateLogistics(paramsMap);

    }

    @Override
    public List<Map<String, Object>> queryGiftDetail(HashMap<String, Object> paramsMap) {
        return platformGiftOrderDao.queryGiftDetail(paramsMap);
    }
}
