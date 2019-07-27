package com.acl.platform.dao;

import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.paginator.domain.PageBounds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PlatformGiftOrderDao {
    PageList<?> queryGiftOrder(HashMap<String, Object> paramsMap,PageBounds pageBounds);

    void updateLogistics(HashMap<String, Object> paramsMap);

    void updateTrade(HashMap<String,Object> paramsMap);

    void updateOrderStatus(HashMap<String, Object> paramsMap);

    List<Map<String, Object>> queryGiftDetail(HashMap<String, Object> paramsMap);

}
