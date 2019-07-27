package com.acl.goods.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.goods.dao.GoodsSpecDao;
import com.acl.pojo.GoodsSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acl.goods.service.GoodsSpecService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * className:GoodsSpecServiceImpl
 * author:wangli
 * createDate:2017年4月25日 下午7:10:34
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Service
public class GoodsSpecServiceImpl implements GoodsSpecService {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private GoodsSpecDao goodsSpecDao;

    @Override
    public PageList<?> queryGoodsSpec(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return goodsSpecDao.queryGoodsSpec(paramsMap, pageBounds);
    }

    @Override
    public void insertKsnSpec(GoodsSpec goodsSpec) {
        goodsSpec.setCreate_time(sdf.format(new java.util.Date()));
        goodsSpecDao.insertKsnSpec(goodsSpec);
    }

    @Override
    public void updateKsnSpec(HashMap<String, Object> paramsMap) {
        paramsMap.put("update_time", sdf.format(new java.util.Date()));
        goodsSpecDao.updateKsnSpec(paramsMap);
    }

    @Override
    public List<Map<String, Object>> specValidate(HashMap<String, Object> paramsMap) {
        return goodsSpecDao.specValidate(paramsMap);
    }

	@Override
	public void updateGoodsSpec(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		 paramsMap.put("update_time", sdf.format(new java.util.Date()));
	     goodsSpecDao.updateGoodsSpec(paramsMap);
	}

	@Override
	public void insertGoodsSpec(GoodsSpec goodsSpec) {
		 goodsSpec.setCreate_time(sdf.format(new java.util.Date()));
	     goodsSpecDao.insertGoodsSpec(goodsSpec);
	}

}
