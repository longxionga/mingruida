package com.acl.goods.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.goods.dao.GoodsProrateDao;
import com.acl.goods.service.GoodsProrateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * className:GoodsProrateServiceImpl
 * author:wangli
 * createDate:2017年4月26日 下午5:02:51
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Service
public class GoodsProrateServiceImpl implements GoodsProrateService {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private GoodsProrateDao goodsProrateDao;

    @Override
    public PageList<?> queryGoodsProrate(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return goodsProrateDao.queryGoodsProrate(paramsMap, pageBounds);
    }

    @Override
    public void insertGoodsProrate(HashMap<String, Object> paramsMap) {
        paramsMap.put("create_time", sdf.format(new java.util.Date()));
        goodsProrateDao.insertGoodsProrate(paramsMap);
    }

    @Override
    public void updateGoodsProrate(HashMap<String, Object> paramsMap) {
        paramsMap.put("update_time", sdf.format(new java.util.Date()));
        goodsProrateDao.updateGoodsProrate(paramsMap);
    }

    @Override
    public List<Map<String, Object>> getMallLevel(HashMap<String, Object> paramsMap) {
        return goodsProrateDao.getMallLevel(paramsMap);
    }

    @Override
    public List<Map<String, Object>> prorateValidate(HashMap<String, Object> paramsMap) {
        return goodsProrateDao.prorateValidate(paramsMap);
    }

}
