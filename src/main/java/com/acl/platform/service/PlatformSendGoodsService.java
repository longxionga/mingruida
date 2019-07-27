package com.acl.platform.service;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;

/**
 * Created by wangli on 2017/11/9.
 */
public interface PlatformSendGoodsService {
    /**
     * 查询服务商下单信息
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> querySendGoods(HashMap<String, Object> paramsMap,
                               PageBounds pageBounds);
    /**
     * 审核出货
     * @param paramsMap
     */
    void agree(HashMap<String, Object> paramsMap);

    /**
     * 拒绝出货
     * @param paramsMap
     */
    void disagree(HashMap<String, Object> paramsMap);


}
