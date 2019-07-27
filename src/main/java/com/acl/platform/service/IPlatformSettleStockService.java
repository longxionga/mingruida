package com.acl.platform.service;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;

/**
 * Created by wangli on 2017/11/17.
 */
public interface IPlatformSettleStockService {
    /**
     * 查询服务商库存信息
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> querySettleStock(HashMap<String, Object> paramsMap,
                                 PageBounds pageBounds);
}
