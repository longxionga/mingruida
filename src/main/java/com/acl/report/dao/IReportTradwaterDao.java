package com.acl.report.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @program: sala_back
 * @description:
 * @author: 易昙
 * @create: 2019-07-25 10:24
 **/
public interface IReportTradwaterDao {

    /**
     * 流水统计查询报表
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryTradwaterPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds);


    /*** 
    * @Description: 交易流水导出 
    * @Param: [map] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 易昙
    * @Date: 2019/7/25 
    */ 

    List<Map<String,Object>> exportTradwaterInfo(Map<String,Object> map);




    /***
    * @Description:  流水信息数量统计
    * @Param: [paramsMap]
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    * @Author: 易昙
    * @Date: 2019/7/25
    */



    Map<String, Object> countTradwaterInfo(Map<String, Object> paramsMap);

}
