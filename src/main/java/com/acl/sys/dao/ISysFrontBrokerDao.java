package com.acl.sys.dao;

import java.util.HashMap;
import java.util.Map;

/**
 *className:ISysFrontBrokerDao
 *author:huangs
 *createDate:2016年8月13日 上午10:56:59
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface ISysFrontBrokerDao {
    void insertFrontBroker(HashMap<String, Object> paramsMap);

    Map queryBrokerCode(HashMap<String, Object> paramsMap);
}
