package com.acl.platform.service.impl;

import com.acl.platform.dao.IPlatformMergeOrderDao;
import com.acl.platform.service.PlatformMergeOrderService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by admin on 2017/9/21.
 */
@Service
public class PlatformMergeOrderServiceImpl implements PlatformMergeOrderService {
    @Autowired
    private IPlatformMergeOrderDao platformMergeOrderDao;
    @Override
    public PageList<?> queryMergeOrder(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return platformMergeOrderDao.queryMergeOrder(paramsMap,pageBounds);
    }

    @Override
    public List<Map<String, Object>> queryOrderDetail(HashMap<String, Object> paramsMap) {
        return platformMergeOrderDao.queryOrderDetail(paramsMap);
    }

    @Override
    public int mergeOrder(String ids) {
        String[] id= ids.split(",");
        String remark="";
        HashMap<String, Object> paramsMap =new HashMap<String,Object>();
        paramsMap.put("ids",id);
        List<Map<String, Object>> orderList=platformMergeOrderDao.selectByIds(id);
        String[] address=new String[orderList.size()];
        String[] orderIds=new String[orderList.size()];
        for(int i=0;i<orderList.size();i++){
            address[i]=orderList.get(i).get("address_address").toString();
            orderIds[i]=orderList.get(i).get("id").toString();
        }
        Set set=new HashSet();
        for(int i=0;i<address.length;i++){
            set.add(address[i]);
        }
        if (set.size()!=1){
            //收货地址不同不能合单，请选择相同的收货地址进行合单操作
            return 0;
        }
        String merId= UUIDGenerator.generate();
        HashMap<String,Object> orderMap=new HashMap<>();
        //orderMap.put("status","13");//中间状态
        orderMap.put("update_time",new Date());
        orderMap.put("post_id",merId);
        orderMap.put("orderIds", orderIds);
        platformMergeOrderDao.updateOrderStatus(orderMap);
        BigDecimal totalAmount = new BigDecimal(0);

        if(orderList.size()!=0){
            for(int i=0;i<orderList.size();i++){

                totalAmount = totalAmount.add(new BigDecimal(orderList.get(i).get("payment_amount").toString()));
                remark+=orderList.get(i).get("product_name").toString()+"*"+orderList.get(i).get("product_number").toString()+"/";
            }
        }
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("id",merId);
        map.put("user_id",orderList.get(0).get("user_id").toString());
        map.put("amount",totalAmount);
        map.put("num",orderList.size());
        map.put("receiver_name",orderList.get(0).get("address_name"));
        map.put("receiver_mobile",orderList.get(0).get("address_mobile"));//加密的手机号
        map.put("receiver_addr",orderList.get(0).get("address_address"));
        map.put("create_time",new Date());
        map.put("remark",remark);
        platformMergeOrderDao.insertShipped(map);
        return 1;
    }


    @Override
    public PageList<?> tradeOrder(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return platformMergeOrderDao.tradeOrder(paramsMap,pageBounds);
    }


}
