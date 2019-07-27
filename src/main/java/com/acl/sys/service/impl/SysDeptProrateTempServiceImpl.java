package com.acl.sys.service.impl;

import com.acl.sys.dao.ISysDeptProrateTempDao;
import com.acl.sys.service.ISysDeptProrateTempService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.paginator.domain.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * className:SysDeptProrateTempServiceImpl
 * author:huangs
 * createDate:2016年8月13日 下午4:14:15
 * vsersion:3.0
 * department:安创乐科技
 * description:
 */
@Service
@Transactional
public class SysDeptProrateTempServiceImpl implements ISysDeptProrateTempService {
    @Autowired
    private ISysDeptProrateTempDao sysDeptProrateTempDao;

    @Override
    public PageList<?> queryDeptProrateTemp(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return sysDeptProrateTempDao.queryDeptProrateTemp(paramsMap, pageBounds);
    }

    @Override
    public PageList<?> queryDeptProrateTempByView(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return sysDeptProrateTempDao.queryDeptProrateTempByView(paramsMap, pageBounds);
    }

    @Override
    public void insertDeptProrateTemp(HashMap<String, Object> paramsMap) {
        sysDeptProrateTempDao.insertDeptProrateTemp(paramsMap);
    }

    @Override
    public void updateDeptProrateTemp(HashMap<String, Object> paramsMap) {
        sysDeptProrateTempDao.updateDeptProrateTemp(paramsMap);
    }

    @Override
    public PageList<?> queryAgentProrateTemp(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        int count = sysDeptProrateTempDao.queryAgentProrateTempCount(paramsMap);
        paramsMap.put("startRow", (pageBounds.getPage() - 1) * pageBounds.getLimit());
        paramsMap.put("endRow", pageBounds.getPage() * pageBounds.getLimit());
        Paginator paginator = new Paginator(pageBounds.getPage(), pageBounds.getLimit(), count);
        return new PageList<>(sysDeptProrateTempDao.queryAgentProrateTemp(paramsMap), paginator);
    }

    @Override
    public void insertAgentProrateTemp(HashMap<String, Object> paramsMap) {
        sysDeptProrateTempDao.insertAgentProrateTemp(paramsMap);
    }

    @Override
    public void updateAgentProrateTemp(HashMap<String, Object> paramsMap) {
        sysDeptProrateTempDao.updateAgentProrateTemp(paramsMap);
    }

    @Override
    public PageList<?> queryAgentProrateBrand(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return sysDeptProrateTempDao.queryAgentProrateBrand(paramsMap, pageBounds);
    }

}
