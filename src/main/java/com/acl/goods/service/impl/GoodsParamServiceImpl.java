package com.acl.goods.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.goods.service.GoodsParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acl.goods.dao.GoodsParamDao;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * className:GoodsParamServiceImpl
 * author:wangli
 * createDate:2017年4月25日 上午9:10:02
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Service
public class GoodsParamServiceImpl implements GoodsParamService {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private GoodsParamDao goodsParamDao;

    @Override
    public PageList<?> queryGoodsParam(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return goodsParamDao.queryGoodsParam(paramsMap, pageBounds);
    }

    @Override
    public void insertGoodsParam(HashMap<String, Object> paramsMap) {
        paramsMap.put("create_time", sdf.format(new java.util.Date()));
        goodsParamDao.insertGoodsParam(paramsMap);
    }

    @Override
    public void updateGoodsParam(HashMap<String, Object> paramsMap) {
        paramsMap.put("update_time", sdf.format(new java.util.Date()));
        goodsParamDao.updateGoodsParam(paramsMap);
    }

    @Override
    public List<Map<String, Object>> paramValidate(HashMap<String, Object> paramsMap) {
        return goodsParamDao.paramValidate(paramsMap);
    }

    @Override
    public List<Map<String, Object>> queryGoodsName(HashMap<String, Object> paramsMap) {
        return goodsParamDao.queryGoodsName(paramsMap);
    }

}
