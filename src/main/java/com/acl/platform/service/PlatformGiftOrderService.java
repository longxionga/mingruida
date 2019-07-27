package com.acl.platform.service;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PlatformGiftOrderService {
    PageList<?> queryGiftOrder(HashMap<String, Object> paramsMap, PageBounds pageBounds);

    void updateLogistics(HashMap<String, Object> paramsMap);

    void updateOrder(HashMap<String,Object> paramsMap);

    List<Map<String, Object>> queryGiftDetail(HashMap<String, Object> paramsMap);

}
