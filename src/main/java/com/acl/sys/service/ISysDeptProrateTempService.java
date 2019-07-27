package com.acl.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:ISysDeptProrateTempService
 *author:huangs
 *createDate:2016年8月13日 下午4:07:03
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface ISysDeptProrateTempService {
   
    PageList<?> queryDeptProrateTemp(HashMap<String, Object> paramsMap,PageBounds pageBounds);
    
    PageList<?> queryDeptProrateTempByView(HashMap<String, Object> paramsMap, PageBounds pageBounds);
    
    void insertDeptProrateTemp(HashMap<String, Object> paramsMap);
    
    void updateDeptProrateTemp(HashMap<String, Object> paramsMap);


    PageList<?> queryAgentProrateTemp(HashMap<String, Object> paramsMap, PageBounds pageBounds);

    void insertAgentProrateTemp(HashMap<String, Object> paramsMap);

    void updateAgentProrateTemp(HashMap<String, Object> paramsMap);


    PageList<?> queryAgentProrateBrand(HashMap<String, Object> paramsMap, PageBounds pageBounds);

}
