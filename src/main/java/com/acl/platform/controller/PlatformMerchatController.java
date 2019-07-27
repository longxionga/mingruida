package com.acl.platform.controller;

import com.acl.core.CoreBaseController;
import com.acl.goods.service.GoodsKsnListService;
import com.acl.platform.service.IPlatformUserInfoService;
import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.PhoneCodeSSL;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @author 胡锋
 * @create 2019-03-19 11:45
 */
@Controller
@RequestMapping("/platform")
public class PlatformMerchatController extends CoreBaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlatformMerchatController.class);


    @Autowired
    private GoodsKsnListService goodsKsnListService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/merchantInfo"})
    @RequestMapping("/merchantInfo")
    public String merchantInfo() {
        return "platform/platform_merchant_info";
    }



    /**
     * 查询商品信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/merchantInfo"})
    @RequestMapping("/queryMerchantInfoList")
    public Object queryMerchantInfoList(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                     @RequestParam(defaultValue = "0", required = false) Integer page,
                                     @RequestParam(defaultValue = "10", required = false) Integer rows,
                                     @RequestParam HashMap<String, Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(orderByCreateTime));
        if (paramsMap.get("sort") != null) {
            paramsMap.put("order_type", paramsMap.get("sort").toString() + " " + paramsMap.get("order").toString());
            pageBounds = new PageBounds(page, rows, Order.formString(paramsMap.get("sort").toString() + "." + paramsMap.get("order").toString()));
        }
        List<Map<String,Object>> list = goodsKsnListService.queryMerchantInfo(paramsMap);
        String jsonStr = JSONArray.fromObject(list).toString();
        JSONArray ja = JSONArray.fromObject(jsonStr);
        return ja;
    }


    /**
     * 查询商品信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/merchantInfo"})
    @RequestMapping("/queryMerchantInfoPageList")
    public Object queryMerchantInfoPageList(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                        @RequestParam(defaultValue = "0", required = false) Integer page,
                                        @RequestParam(defaultValue = "10", required = false) Integer rows,
                                        @RequestParam HashMap<String, Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(orderByCreateTime));
        if (paramsMap.get("sort") != null) {
            paramsMap.put("order_type", paramsMap.get("sort").toString() + " " + paramsMap.get("order").toString());
            pageBounds = new PageBounds(page, rows, Order.formString(paramsMap.get("sort").toString() + "." + paramsMap.get("order").toString()));
        }
        if (paramsMap.get("start_date") != null) {
            paramsMap.put("start_date", StringUtils.replace((String) paramsMap.get("start_date"),"-",""));
        }
        if (paramsMap.get("end_date") != null) {
            paramsMap.put("end_date", StringUtils.replace((String) paramsMap.get("end_date"),"-",""));
        }
        PageList<?> list = (PageList<?>) goodsKsnListService.queryMerchantInfoPageList(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;
    }


    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/tradeOrderInfo"})
    @RequestMapping("/tradeOrderInfo")
    public String tradeOrderInfo() {
        return "platform/platform_trade_order_info";
    }



    /**
     * 查询商品信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/tradeOrderInfo"})
    @RequestMapping("/queryTradeOrderInfoPageList")
    public Object queryTradeOrderInfoPageList(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                        @RequestParam(defaultValue = "0", required = false) Integer page,
                                        @RequestParam(defaultValue = "10", required = false) Integer rows,
                                        @RequestParam HashMap<String, Object> paramsMap) {
        try {
            PageBounds pageBounds = new PageBounds(page, rows, Order.formString(orderByCreateTime));
            if (paramsMap.get("sort") != null) {
                paramsMap.put("order_type", paramsMap.get("sort").toString() + " " + paramsMap.get("order").toString());
                pageBounds = new PageBounds(page, rows, Order.formString(paramsMap.get("sort").toString() + "." + paramsMap.get("order").toString()));
            }
            if (paramsMap.get("start_date") != null) {
                paramsMap.put("start_date", StringUtils.replace((String) paramsMap.get("start_date"),"-",""));
            }
            if (paramsMap.get("end_date") != null) {
                paramsMap.put("end_date", StringUtils.replace((String) paramsMap.get("end_date"),"-",""));
            }
            //查询用户信息
            PageList<?> list = (PageList<?>) goodsKsnListService.queryTradeOrderInfoPageList(paramsMap, pageBounds);
            Object json = this.getJsonMap(list);
            return json;
        }catch (Exception e){
            LOGGER.error("查询异常：",e);
        }
        return null;

    }




    /**
     * 查询商品信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/merchantInfo"})
    @RequestMapping("/queryMerchantTransfList")
    public Object queryMerchantTransfList(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                        @RequestParam(defaultValue = "0", required = false) Integer page,
                                        @RequestParam(defaultValue = "10", required = false) Integer rows,
                                        @RequestParam HashMap<String, Object> paramsMap) {
        List<Map<String,Object>> list = goodsKsnListService.queryMerchantTransfList(paramsMap);
        String jsonStr = JSONArray.fromObject(list).toString();
        JSONArray ja = JSONArray.fromObject(jsonStr);
        return ja;
    }




    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/giftMerchant" })
    @RequestMapping("/giftMerchant")
    public String giftMerchant(){
        return "platform/platform_gift_merchant";
    }


    @Autowired
    private IPlatformUserInfoService platformUserInfoService;
    /**
     * 查询
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/giftMerchant" })
    @RequestMapping("/giftMerchantInfo")
    public Object giftMerchantInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                 @RequestParam(defaultValue = "0", required = false) Integer page,
                                 @RequestParam(defaultValue = "10", required = false) Integer rows,
                                 @RequestParam HashMap<String,Object> paramsMap){

        String sortString = "";
        PageBounds pageBounds = new PageBounds(page,rows,Order.formString(sortString));
        //查询提现审核信息
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        paramsMap.put("dept_id", loginSession.getDept_id());
        paramsMap.put("account_type","02");
		/*if(!"".equals(StringUtils.checkString(paramsMap.get("b_mobile"))))
		{
			paramsMap.put("mobile",MySecurity.encryptAES(paramsMap.get("b_mobile").toString(), SystemConfig.keyMy));
		}	*/
        //PageList<?> list = (PageList<?>)platformUserAgentRechargeService.queryUserAgentRecharge(paramsMap, pageBounds);
        if (paramsMap.get("start_date") != null) {
            paramsMap.put("start_date", StringUtils.replace((String) paramsMap.get("start_date"),"-",""));
        }
        if (paramsMap.get("end_date") != null) {
            paramsMap.put("end_date", StringUtils.replace((String) paramsMap.get("end_date"),"-",""));
        }
        PageList<?> list = (PageList<?>) platformUserInfoService.giftMerchantInfo(paramsMap, pageBounds);
        //手机号数据库解密
        PageList<?> listMap = PhoneCodeSSL.getDataBaseListPage1(list, paramsMap);
        Object json = this.getJsonMap(listMap);
        return json;
    }




    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/giftMerchant" })
    @RequestMapping("/giftMerchantInfoDetail")
    public Object giftMerchantInfoDetail(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                  @RequestParam HashMap<String, Object> paramsMap){
        if (paramsMap.get("start_date") != null) {
            paramsMap.put("start_date", StringUtils.replace((String) paramsMap.get("start_date"),"-",""));
        }
        if (paramsMap.get("end_date") != null) {
            paramsMap.put("end_date", StringUtils.replace((String) paramsMap.get("end_date"),"-",""));
        }
        List<Map<String, Object>> goodsList = goodsKsnListService.giftMerchantInfoDetail(paramsMap);
        return goodsList;
    }

}

