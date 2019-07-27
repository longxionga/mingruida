package com.acl.goods.service.impl;

import com.acl.goods.dao.GoodsItemDao;
import com.acl.goods.service.GoodsItemService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * className:GoodsItemServiceImpl
 * author:wang
 * createDate:2017年4月26日 下午15:10:02
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Service
public class GoodsItemServiceImpl implements GoodsItemService {
    @Autowired
    private GoodsItemDao goodsItemDao;

    @Override
    public PageList<?> queryGoodsItem(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return goodsItemDao.queryGoodsItem(paramsMap, pageBounds);
    }

    @Override
    public void insertGoodsItem(HashMap<String, Object> paramsMap) {
        goodsItemDao.insertGoodsItem(paramsMap);
    }

    @Override
    public void updateGoodsItem(HashMap<String, Object> paramsMap) {
        goodsItemDao.updateGoodsItem(paramsMap);
    }

    @Override
    public List<Map<String, Object>> itemValidate(HashMap<String, Object> paramsMap) {
        return goodsItemDao.itemValidate(paramsMap);
    }

    @Override
    public List<Map<String, Object>> queryItemGoodsName(HashMap<String, Object> paramsMap) {
        return goodsItemDao.queryItemGoodsName(paramsMap);
    }
}
