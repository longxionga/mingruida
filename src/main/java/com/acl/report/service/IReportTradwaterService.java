package com.acl.report.service;

import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IReportTradwaterService {

    /*** 
    * @Description: 统计所有交易流水 
    * @Param: [paramsMap, pageBounds, loginSession] 
    * @return: com.acl.utils.paginator.domain.PageList<?> 
    * @Author: 易昙
    * @Date: 2019/7/25 
    */ 
    
    PageList<?> queryTradwaterPageList(HashMap<String, Object> paramsMap,
                                         PageBounds pageBounds, LoginSession loginSession);



    /***
    * @Description:  查询交易流水数量统计
    * @Param: [hashMap, loginSession]
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    * @Author: 易昙
    * @Date: 2019/7/25
    */
    Map<String, Object> countTradwaterInfo(HashMap<String, Object> hashMap,LoginSession loginSession);

    /*** 
    * @Description: 流水导出
    * @Param: [map, loginSession] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 易昙
    * @Date: 2019/7/25 
    */ 
    
    List<Map<String, Object>> exportTradwaterInfo(Map<String,Object> map,LoginSession loginSession);


}
