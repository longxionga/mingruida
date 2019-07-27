package com.acl.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.platform.dao.PlatformBrokerDao;
import com.acl.platform.service.PlatformBrokerService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.component.MongodbBaseDao;
import com.acl.component.SystemConfig;
import com.acl.utils.util.MD5Utils;
import com.acl.utils.mongo.Pagination;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * className:IPlatformBrokerServiceImpl
 * author:wangli
 * createDate:2016年8月29日 下午5:08:36
 * vsersion:3.0
 * department:安创乐科技
 * description:
 */
@Service
@Transactional
public class PlatformBrokerServiceImpl implements PlatformBrokerService {

    @Autowired
    private PlatformBrokerDao platformBrokerDao;

    @Autowired
    private MongodbBaseDao<T> mongodbBaseDao;

    @Override
    public void updateBrokerPwd(HashMap<String, Object> paramsMap) {
        paramsMap.put("broker_password", MD5Utils.MD5(SystemConfig.AncholPWD));
        platformBrokerDao.updateBrokerPwd(paramsMap);
        Map<String, Object> map = new HashMap<>();
        map.put("broker_id", paramsMap.get("broker_id").toString());
        Update update = new Update();
        update.set("broker_password", MD5Utils.MD5(SystemConfig.AncholPWD));
        mongodbBaseDao.updateFirst(map, update, "t_front_broker");
    }

    @Override
    public void updateBrokerState(HashMap<String, Object> paramsMap) {
        platformBrokerDao.updateBrokerState(paramsMap);
        Map<String, Object> map = new HashMap<>();
        map.put("broker_id", paramsMap.get("broker_id").toString());
        Update update = new Update();
        update.set("is_use", paramsMap.get("is_use").toString());

    }

    @Override
    public Pagination<T> queryBroker(HashMap<String, Object> paramsMap) {
        int pageNo = Integer.parseInt(paramsMap.get("page").toString());
        int pageSize = Integer.parseInt(paramsMap.get("rows").toString());
        Map<String, Object> map = new HashMap<>();
        if (paramsMap.get("broker_name") != null) {
            map.put("broker_name", paramsMap.get("broker_name").toString());
        }
        try {
            return mongodbBaseDao.pagingQuery1(pageNo, pageSize, map, "t_front_broker");
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PageList<?> queryBrokerInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return platformBrokerDao.queryBrokerInfo(paramsMap, pageBounds);
    }

    @Override
    public PageList<?> queryBrokerUser(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return platformBrokerDao.queryBrokerUser(paramsMap, pageBounds);
    }

    @Override
    public void updateBrokerUser(HashMap<String, Object> paramsMap) {
        //对数据库里数据进行处理
        platformBrokerDao.updateBrokerUser(paramsMap);
        //对mongoDB里面的数据进行处理
        Map<String, Object> map = new HashMap<>();
        map.put("broker_id", paramsMap.get("broker_id").toString());
        Update update = new Update();
        update.set("broker_parent_id", paramsMap.get("current_broker_id").toString());
        //update.set("broker_parent_incode",paramsMap.get("current_broker_incode").toString());
    }

    public PageList<?> queryBrokers(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return platformBrokerDao.queryBrokers(paramsMap, pageBounds);
    }

    @Override
    public List<Map<String, Object>> queryBrokerForMon(Map<String, Object> map) {
        return platformBrokerDao.queryBrokerForMon(map);
    }

    @Override
    public List<Map<String, Object>> queryBrokerUserForMon(Map<String, Object> map) {
        return platformBrokerDao.queryBrokerUserForMon(map);
    }

    @Override
    public void upBroker(HashMap<String, Object> paramsMap) {
        platformBrokerDao.upBroker(paramsMap);
    }

    @Override
    public List<Map<String, Object>> queryBrokerAllot(Map<String, Object> map) {
        return platformBrokerDao.queryBrokerAllot(map);
    }

    @Override
    public void insertBrokerProrateTemp(HashMap<String, Object> paramsMap) {
        platformBrokerDao.insertBrokerProrateTemp(paramsMap);
    }

    @Override
    public void updateBrokerProrateTemp(HashMap<String, Object> paramsMap) {
        platformBrokerDao.updateBrokerProrateTemp(paramsMap);
    }

    @Override
    public List<Map<String, Object>> queryAgentAllot(Map<String, Object> map) {
        return platformBrokerDao.queryAgentAllot(map);
    }

    @Override
    public void updateBrokerProrate(HashMap<String, Object> paramsMap) {
        platformBrokerDao.updateBrokerProrate(paramsMap);
    }

    @Override
    public List<Map<String, Object>> queryBrokerForProrate(Map<String, Object> map) {
        return platformBrokerDao.queryBrokerForProrate(map);
    }

    @Override
    public void insertBrokerProrate(HashMap<String, Object> paramsMap) {
        platformBrokerDao.insertBrokerProrate(paramsMap);
    }


    @Override
    public List<Map<String, Object>> queryUserByMobile(Map<String, Object> map) {
        return platformBrokerDao.queryUserByMobile(map);
    }

    @Override
    public Map<String, Object> queryUserById(Map<String, Object> map) {
        return platformBrokerDao.queryUserById(map);
    }

    public PageList<?> queryUserInfoDept(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return platformBrokerDao.queryUserInfoDept(paramsMap, pageBounds);
    }
}
