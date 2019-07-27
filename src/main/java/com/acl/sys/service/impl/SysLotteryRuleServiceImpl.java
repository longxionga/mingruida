package com.acl.sys.service.impl;

import com.acl.sys.dao.SysLotteryRuleDao;
import com.acl.sys.service.SysLotteryRuleService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional
public class SysLotteryRuleServiceImpl implements SysLotteryRuleService {

    @Autowired
    private SysLotteryRuleDao sysLotteryRuleDao;
    @Override
    public PageList<?> queryLotteryRule(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return sysLotteryRuleDao.queryLotteryRule(paramsMap,pageBounds);
    }

    @Override
    public void updateLotteryRule(HashMap<String, Object> paHashMap) {
        sysLotteryRuleDao.updateLotteryRule(paHashMap);

    }
}
