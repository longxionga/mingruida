package com.acl.sys.service.impl;

import com.acl.goods.dao.GoodsKsnListDao;
import com.acl.platform.dao.PlatformBrokerDao;
import com.acl.sys.dao.ISysSalaRuleDao;
import com.acl.sys.service.ISysSalaRuleService;
import com.acl.sys.service.SysDeptInfoService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.UUIDGenerator;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:SysBankRuleServiceImpl
 * author:wangli
 * createDate:2016年8月18日 下午7:50:01
 * vsersion:3.0
 * department:安创乐科技
 * description:
 */
@Service
@Transactional
public class SysSalaRuleServiceImpl implements ISysSalaRuleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysSalaRuleServiceImpl.class);

    @Autowired
    private ISysSalaRuleDao sysSalaRuleDao;

    @Override
    public PageList<?> querySalaRule(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return sysSalaRuleDao.querySalaRule(paramsMap, pageBounds);
    }

//	@Override
//	public void insertBankRule(HashMap<String, Object> paramsMap) {
//		sysBankRuleDao.insertBankRule(paramsMap);
//	}

    @Override
    public void updateSalaRule(HashMap<String, Object> paHashMap) {
        paHashMap.put("update_time", new Date());
        sysSalaRuleDao.updateSalaRule(paHashMap);
    }

    @Override
    public void insSalaRule(HashMap<String, Object> paHashMap) {
        paHashMap.put("id", UUIDGenerator.generate());
        paHashMap.put("create_time", new Date());
        paHashMap.put("sala_status", "01");
        paHashMap.put("sala_type", "02");
        sysSalaRuleDao.insertSalaRule(paHashMap);
    }

    @Override
    public List<Map<String, Object>> queryDictSalaRule(HashMap<String, Object> paramsMap) {
        return sysSalaRuleDao.queryDictSalaRule(paramsMap);
    }

    //	@Override
//	public void deleteBankRule(HashMap<String, Object> paHashMap) {
//		sysBankRuleDao.deleteBankRule(paHashMap);
//	}
    @Override
    public List<Map<String, Object>> queryDictBrandRule(HashMap<String, Object> paramsMap) {
        return sysSalaRuleDao.queryDictBrandRule(paramsMap);
    }

    @Autowired
    private SysDeptInfoService sysDeptInfoService;

    @Autowired
    private PlatformBrokerDao platformBrokerDao;

    @Autowired
    private GoodsKsnListDao goodsKsnListDao;

    @Override
    public int doSumBrandRule(HashMap<String, Object> paramsMap) {
        paramsMap.put("dept_type","4");
        paramsMap.put("create_day", StringUtils.replace((String) paramsMap.get("begindate"),"-",""));
        sysSalaRuleDao.deleteStatisticsMerchant(paramsMap);
        List<Map<String, Object>> deptList = sysDeptInfoService.queryDeptInfoByType(paramsMap);
        List<Map<String, Object>> userInfoList =  platformBrokerDao.queryUserInfoDeptList(null);
        LOGGER.info("代理商列表"+JSONArray.toJSONString(userInfoList));
        List<Map<String, Object>> dataMerchantAgentList = goodsKsnListDao.queryMerchantTransfList(null);
        LOGGER.info("代理商关系列表"+JSONArray.toJSONString(dataMerchantAgentList));
        Map<String, Object> queryParamsMap = new HashMap<>();
        queryParamsMap.put("brand_id",(String) paramsMap.get("brand_id"));
        queryParamsMap.put("start_date", StringUtils.replace((String) paramsMap.get("begindate"),"-",""));
        queryParamsMap.put("end_date", StringUtils.replace((String) paramsMap.get("begindate"),"-",""));
        List<Map<String, Object>> dataMerchantList = goodsKsnListDao.queryMerchantInfoList(queryParamsMap);
        LOGGER.info("商户列表"+JSONArray.toJSONString(dataMerchantList));
        List<Map<String, Object>> dataTradeOrderInfoList = goodsKsnListDao.queryTradeOrderInfoList(queryParamsMap);
        LOGGER.info("交易订单列表"+JSONArray.toJSONString(dataTradeOrderInfoList));
        for (int i=0 ;i<deptList.size();i++){
            Map<String, Object>  map = deptList.get(i);
            LOGGER.info(map.toString());
            Map<String, Object> statisticsParamsMap = new HashMap<>();
            statisticsParamsMap.put("id",UUIDGenerator.generate());
            statisticsParamsMap.put("type","01");
            statisticsParamsMap.put("brand_id",(String) paramsMap.get("brand_id"));
            statisticsParamsMap.put("create_day", StringUtils.replace((String) paramsMap.get("begindate"),"-",""));
            statisticsParamsMap.put("create_time",new Date());
            statisticsParamsMap.put("agent_id", (String) map.get("agent_id"));
            statisticsParamsMap.put("settle_id", (String) map.get("settle_id"));
            statisticsParamsMap.put("ch_id", (String) map.get("ch_id"));
            statisticsParamsMap.put("ce_id", (String) map.get("ce_id"));
            statisticsParamsMap.put("p_id", (String) map.get("p_id"));
            statisticsParamsMap.put("total_amount", 7);
            statisticsParamsMap.put("real_amount", 6);
            statisticsParamsMap.put("total_activation", 5);
            statisticsParamsMap.put("total_unactivation", 4);
            statisticsParamsMap.put("total_standard", 4);
            statisticsParamsMap.put("total_unstandard", 3);
            statisticsParamsMap.put("total_order_num", 2);
            statisticsParamsMap.put("total_order_amount", 1);
            sysSalaRuleDao.insertStatisticsMerchant(statisticsParamsMap);
        }
        return 0;
    }

    @Override
    public List<Map<String, Object>> queryBrandList(HashMap<String, Object> paramsMap) {
        return sysSalaRuleDao.queryBrandList(paramsMap);
    }
}
