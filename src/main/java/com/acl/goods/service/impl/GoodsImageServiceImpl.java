package com.acl.goods.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.acl.goods.dao.GoodsImageDao;
import com.acl.goods.service.GoodsImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acl.component.SystemConfig;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * className:GoodsImageServiceImpl
 * author:wangli
 * createDate:2017年4月26日 下午2:13:58
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Service
public class GoodsImageServiceImpl implements GoodsImageService {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private GoodsImageDao goodsImageDao;

    @Override
    public PageList<?> queryGoodsImage(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return goodsImageDao.queryGoodsImage(paramsMap, pageBounds);
    }

    @Override
    public void insertGoodsImage(HashMap<String, Object> paramsMap) {
        String[] goods_urls = paramsMap.get("img_uri").toString().split(",");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("goods_id", paramsMap.get("goods_id"));
        map.put("img_type", paramsMap.get("img_type"));
        map.put("create_time", sdf.format(new java.util.Date()));
        map.put("is_use", paramsMap.get("is_use"));
        if (goods_urls.length > 0) {
            for (int i = 0; i < goods_urls.length; i++) {
                map.put("img_uri", SystemConfig.vsimgUrl + goods_urls[i]);
                if (paramsMap.get("img_seq") == null || paramsMap.get("img_seq") == "") {
                    map.put("img_seq", i + 1);
                } else {
                    map.put("img_seq", paramsMap.get("img_seq"));
                }
                goodsImageDao.insertGoodsImage(map);
            }

        }
    }

    @Override
    public void updateGoodsImage(HashMap<String, Object> paramsMap) {
        String[] goods_urls = paramsMap.get("img_uri").toString().split(",");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("img_id", paramsMap.get("img_id"));
        map.put("goods_id", paramsMap.get("goods_id"));
        map.put("img_type", paramsMap.get("img_type"));
        map.put("update_time", sdf.format(new java.util.Date()));
        map.put("is_use", paramsMap.get("is_use"));
        map.put("img_seq", paramsMap.get("img_seq"));
        if (goods_urls.length > 0) {
            String path = goods_urls[0].toString();
            if (path.indexOf(SystemConfig.vsimgUrl) != -1) {
                map.put("img_uri", path);
            } else {
                map.put("img_uri", SystemConfig.vsimgUrl + path);
            }
            goodsImageDao.updateGoodsImage(map);
        }

    }

}
