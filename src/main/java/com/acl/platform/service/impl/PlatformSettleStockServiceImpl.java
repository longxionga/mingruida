package com.acl.platform.service.impl;

import com.acl.platform.dao.IPlatformSettleStockDao;
import com.acl.platform.service.IPlatformSettleStockService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by wangli on 2017/11/17.
 */
@Service
public class PlatformSettleStockServiceImpl implements IPlatformSettleStockService {
    @Autowired
    private IPlatformSettleStockDao platformSettleStockDao;


    @Override
    public PageList<?> querySettleStock(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return platformSettleStockDao.querySettleStock(paramsMap,pageBounds);
    }
}
