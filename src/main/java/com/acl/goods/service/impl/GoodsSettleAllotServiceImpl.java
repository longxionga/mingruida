package com.acl.goods.service.impl;

import com.acl.goods.dao.GoodsSettleAllotDao;
import com.acl.goods.service.GoodsSettleAllotService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * className:GoodsItemServiceImpl
 * author:wang
 * createDate:2017年4月26日 下午03:10:02
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Service
public class GoodsSettleAllotServiceImpl implements GoodsSettleAllotService {
    @Autowired
    private GoodsSettleAllotDao goodsSettleAllotDao;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public PageList<?> querySettleAllot(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return goodsSettleAllotDao.querySettleAllot(paramsMap, pageBounds);
    }

    @Override
    public void insertSettleAllot(HashMap<String, Object> paramsMap) {
        goodsSettleAllotDao.insertSettleAllot(paramsMap);
    }

    @Override
    public void updateGSettleAllot(HashMap<String, Object> paramsMap) {
        goodsSettleAllotDao.updateSettleAllot(paramsMap);

    }

    @Override
    public List<Map<String, Object>> settleValidate(HashMap<String, Object> paramsMap) {
        return goodsSettleAllotDao.settleValidate(paramsMap);
    }

    @Override
    public List<Map<String, Object>> querySettleName(HashMap<String, Object> paramsMap) {
        return goodsSettleAllotDao.querySettleName(paramsMap);
    }

    @Override
    public List<Map<String, Object>> querySettleAllotList(HashMap<String, Object> paramsMap) {
        return goodsSettleAllotDao.querySettleAllotList(paramsMap);
    }


    public void setSettleAllotToRedis(HashMap<String, Object> paramsMap) {
        List<Map<String, Object>> listMap = goodsSettleAllotDao.querySettleAllotList(null);
        Map<String, String> paramMap = new HashMap<>();
        for (Map<String, Object> m : listMap) {
            paramMap.put(m.get("settle_id").toString(), m.get("s_allot").toString());
        }
        stringRedisTemplate.opsForHash().putAll("settleallot", paramMap);
    }
}
