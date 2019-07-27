package com.acl.platform.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IPlatformRefundvouchersDao {

    /**
     * 充值券查询
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryRefundvouchersInfoPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds);

    /**
     *
     * @param paramsMap
     * @return
     */
    int insertRefundvouchers(Map<String, Object> paramsMap);
    /**
     *
     * @param paramsMap
     * @return
     */
    int deleteRefundVoucherAll(Map<String, Object> paramsMap);
}
