package com.acl.report.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wangli on 2017/11/16.
 */
public interface IReprotSalesGoodsDao {
    /**
     * 查询商品订单报表
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryReportSalesGoods(HashMap<String, Object> paramsMap,
                                         PageBounds pageBounds);

    /**
     * 统计用户实付总金额
     * @param paramsMap
     * @return
     */
    List<HashMap<String,Object>> queryReportSalesSum(HashMap<String,Object> paramsMap);


}
