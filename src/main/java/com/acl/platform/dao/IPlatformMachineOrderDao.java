package com.acl.platform.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2019-06-27
 */
public interface IPlatformMachineOrderDao {
    /**
     * 查询机具交易信息
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryMachineOrderPageList(HashMap<String, Object> paramsMap,
                                PageBounds pageBounds);


    List<Map<String, Object>> queryMachineOrderById(HashMap<String, Object> paramsMap);

    /**
     *  机具交易信息状态更新
     * @String name
     * @return
     */
    int updateMachineOrder(Map<String, Object> paramsMap);

    /**
     *  查询交易类型下拉框
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> getOrderTypeCombobox(Map<String, Object> paramsMap);

    /**
     *  查询支付类型下拉框
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> getPayTypeCombobox(Map<String, Object> paramsMap);


}
