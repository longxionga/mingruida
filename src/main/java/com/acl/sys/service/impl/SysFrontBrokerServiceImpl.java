package com.acl.sys.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.component.MongodbBaseDao;
import com.acl.component.SystemConfig;
import com.acl.sys.dao.ISysFrontBrokerDao;
import com.acl.sys.service.ISysFrontBrokerService;
import com.acl.utils.util.FormatDateUtil;
import com.acl.utils.util.MD5Utils;
import com.acl.utils.util.RandomValidateCode;

/**
 * className:SysFrontBrokerServiceImpl
 * author:huangs
 * createDate:2016年8月13日 上午10:56:02
 * vsersion:3.0
 * department:安创乐科技
 * description:
 */
@Service
@Transactional
public class SysFrontBrokerServiceImpl implements ISysFrontBrokerService {
    @Autowired
    private ISysFrontBrokerDao sysFrontBrokerDao;

    @Override
    public void insertFrontBroker(HashMap<String, Object> paramsMap) {
        sysFrontBrokerDao.insertFrontBroker(paramsMap);
    }

    /**
     * 为新代理商创建一个初始经纪人
     *
     * @param paramsMap
     */
    @Override
    public Map insertFrontInitBroker(HashMap<String, Object> paramsMap) {

        /**
         * 创建代理商 初始化一个顶级经纪人  t_front_broker
         *
         * broker_code  时间戳
         * broker_name 时间戳
         * broker_parent_id -1
         * dept_id  本id
         * broker_incode  000000
         * is_use 2
         */
    //	String dept_id = paramsMap.get("dept_id").toString();
    //	String dept_code = paramsMap.get("dept_code").toString();
    //	paramsMap.clear();
    //	paramsMap.put("dept_id", dept_id);
    //	paramsMap.put("dept_coe", dept_code);
        String timeMark = System.currentTimeMillis() + "";
        paramsMap.put("broker_id", timeMark);
        paramsMap.put("dept_id", paramsMap.get("dept_id").toString());
//	paramsMap.put("broker_code",paramsMap.get("dept_code").toString()+timeMark);
        paramsMap.put("broker_name", paramsMap.get("dept_name").toString() + "-初始化经纪人");
        paramsMap.put("broker_password", MD5Utils.MD5(SystemConfig.AncholPWD));//通过字典工具来获取 建议
        paramsMap.put("broker_parent_id", "-1");
        paramsMap.put("create_date", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
        paramsMap.put("is_use", "1");
        //paramsMap.put("broker_incode",RandomValidateCode.getRandomString(6));

//        broker_id, uuid
//        broker_wx_id, null
//        broker_code, 时间戳
//        broker_mobile, null
//        broker_name, 时间戳
//        broker_password, 密码md5
//        broker_parent_id, -1
//        create_date, 系统时间
//        is_use, 1
//        broker_money, 0
//        broker_incode, 邀请码  6为随机数 000000
//        dept_id,  所属代理商
//        broker_wxid null 

        sysFrontBrokerDao.insertFrontBroker(paramsMap);
        return paramsMap;
    }

    @Override
    public Map queryBrokerCode(HashMap<String, Object> paramsMap) {
        return sysFrontBrokerDao.queryBrokerCode(paramsMap);
    }

}
