package com.acl.sys.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:ISysDepProrateTempDao
 * author:huangs
 * createDate:2016年8月13日 下午3:37:07
 * vsersion:3.0
 * department:安创乐科技
 * description:
 */
public interface ISysDeptProrateTempDao {

    PageList<?> queryDeptProrateTemp(HashMap<String, Object> paramsMap, PageBounds pageBounds);

    PageList<?> queryDeptProrateTempByView(HashMap<String, Object> paramsMap, PageBounds pageBounds);

    void insertDeptProrateTemp(HashMap<String, Object> paramsMap);

    void updateDeptProrateTemp(HashMap<String, Object> paramsMap);

    void insertAgentProrateTemp(HashMap<String, Object> paramsMap);

    void updateAgentProrateTemp(HashMap<String, Object> paramsMap);

    void updateAgentProrateInfo(HashMap<String, Object> paramsMap);


    int queryAgentProrateTempCount(HashMap<String, Object> paramsMap);

    List<Map<String, Object>> queryAgentProrateTemp(HashMap<String, Object> paramsMap);

    List<Map<String, Object>> queryAgentProrateList(HashMap<String, Object> paramsMap);

    PageList<?> queryAgentProrateBrand(HashMap<String, Object> paramsMap, PageBounds pageBounds);
}
