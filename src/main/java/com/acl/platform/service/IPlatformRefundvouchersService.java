package com.acl.platform.service;

import com.acl.pojo.CzOrder;
import com.acl.pojo.TxOrder;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public interface IPlatformRefundvouchersService {
    PageList<?> queryRefundvouchersInfoPageList(HashMap<String, Object> paramsMap,
                                          PageBounds pageBounds);

    /**
     * 充值券导入
     * @param file
     */
    void refundvouchersimport( File file);

    /**
     * 充值券插入
     * @param paramsMap
     */
    int insertRefundvouchers(Map<String, Object> paramsMap);

    /**
     * 删除充值券信息
     * @param paramsMap
     */
    int deleteRefundVoucherAll(Map<String, Object> paramsMap);


}
