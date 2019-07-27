package com.acl.platform.service;

import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface IPlatformMachineOrderService {


    /**
     * 查询机具交易流水信息信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryMachineOrderPageList(HashMap<String, Object> paramsMap,
                                          PageBounds pageBounds, LoginSession loginSession);

    /**
     * 查询交易类型下拉框
     * @param hashMap
     * @return
     */
    List<Map<String, Object>> getOrderTypeCombobox(HashMap<String, Object> hashMap);

    /**
     * 支付类型下拉框
     * @param hashMap
     * @return
     */
    List<Map<String, Object>> getPayTypeCombobox(HashMap<String, Object> hashMap);

}
