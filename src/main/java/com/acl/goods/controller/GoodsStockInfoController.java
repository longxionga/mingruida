package com.acl.goods.controller;

import com.acl.core.CoreBaseController;
import com.acl.goods.service.GoodsStockInfoService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.FormatDateUtil;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * className:GoodsStockInfoController
 * author:wangzhe
 * createDate:2017年04月22日 下午03:14:58
 * version:3.0
 * department:安创乐科技
 * description:库存信息管理
 */

@Controller
@RequestMapping("/goods")
public class GoodsStockInfoController extends CoreBaseController {

    @Autowired
    private GoodsStockInfoService goodsStockInfoService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsStockInfo"})
    @RequestMapping("/goodsStockInfo")
    public String goodsStockInfo() {
        return "goods/goods_stock_info";
    }

    /**
     * 查询库存信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsStockInfo"})
    @RequestMapping("/queryGoodsStockInfoList")
    public Object queryGoodsStockInfoList(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                          @RequestParam(defaultValue = "0", required = false) Integer page,
                                          @RequestParam(defaultValue = "10", required = false) Integer rows,
                                          @RequestParam HashMap<String, Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(orderByGoodsId));
        if (paramsMap.get("sort") != null) {
            paramsMap.put("order_type", paramsMap.get("sort").toString() + " " + paramsMap.get("order").toString());
            pageBounds = new PageBounds(page, rows, Order.formString(paramsMap.get("sort").toString() + "." + paramsMap.get("order").toString()));
        }
        if (paramsMap.get("agents_id") != null) {
            paramsMap.put("settle_id", paramsMap.get("agents_id").toString());
        }
        //查询用户信息
        PageList<?> list = (PageList<?>) goodsStockInfoService.queryGoodsStockListInfo(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;

    }

    /**
     * 更新库存信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsStockInfo"})
    @RequestMapping("/modGoodsStockInfo")
    public Object modGoodsStockInfo(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            // 更新库存信息
//			GoodsStock stock = new GoodsStock();

            paramsMap.put("spec_id", paramsMap.get("spec_id").toString());
            paramsMap.put("status", "0");
            List<Map<String, Object>> listNum = goodsStockInfoService.queryGoodsSpecSum(paramsMap);
            if (listNum.size() > 0) {
                int amount = Integer.parseInt(paramsMap.get("total_amount").toString()) - Integer.parseInt(listNum.get(0).get("num").toString());
                paramsMap.put("booked_amount", amount);
            } else {
                paramsMap.put("booked_amount", paramsMap.get("total_amount"));
            }

            paramsMap.put("update_time", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));


            // 更新Redis
            goodsStockInfoService.updateGoodsStockInfo(paramsMap);


//			stock = goodsStockInfoService.selectStockToMysql(Long.valueOf(paramsMap.get("sku_id").toString()));
//
//			if(stock.getIsUse().equals("0")){
//				goodsStockInfoService.removeStockToRedis(stock,1);
//			}else{
//					goodsStockInfoService.initSignStockToRedis(stock);
//				}

            msg.setMsg("更新成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("更新失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 初始化库存商品信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsStockInfo"})
    @RequestMapping("/initGoodsStockInfo")
    public Object initGoodsStockInfo(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) throws Exception {
        goodsStockInfoService.initStockToRedis();
        msg.setSuccess(true);
        msg.setMsg("库存初始化成功!");
        return msg;
    }
}
