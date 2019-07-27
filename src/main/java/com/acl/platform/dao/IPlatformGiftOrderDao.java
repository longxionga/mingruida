package com.acl.platform.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/9/22.
 */
public interface IPlatformGiftOrderDao {
    PageList<?> queryGiftOrder(HashMap<String, Object> paramsMap,
                               PageBounds pageBounds);

    void updateLogistics(HashMap<String, Object> paramsMap);

    List<Map<String, Object>> queryGiftDetail(HashMap<String, Object> paramsMap);
}
