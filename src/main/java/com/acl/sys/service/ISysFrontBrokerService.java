package com.acl.sys.service;

import java.util.HashMap;
import java.util.Map;

/**
 *className:ISysFrontBrokerDao
 *author:huangs
 *createDate:2016年8月13日 上午10:54:11
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface ISysFrontBrokerService {
    
    void insertFrontBroker(HashMap<String, Object> paramsMap);

    Map insertFrontInitBroker(HashMap<String, Object> paramsMap);

    Map queryBrokerCode(HashMap<String, Object> paramsMap);
}
