package com.acl.platform.controller;

import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;

import com.acl.platform.service.PlatformMergeOrderService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.MySecurity;
import com.acl.utils.util.PhoneCodeSSL;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
 * Created by admin on 2017/9/21.
 */
@Controller
@RequestMapping("/platform")
public class PlatformMergeOrderController extends CoreBaseController {
    @Autowired
    private PlatformMergeOrderService platformMergeOrderService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/mergeOrder"})
    @RequestMapping("/mergeOrder")
    public String mergeOrder() {
        return "platform/platform_merge_order";
    }

    /**
     * 查询充值活动
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/mergeOrder"})
    @RequestMapping("/queryMergeOrder")
    public Object queryMergeOrder(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                  @RequestParam(defaultValue = "0", required = false) Integer page,
                                  @RequestParam(defaultValue = "10", required = false) Integer rows,
                                  @RequestParam HashMap<String, Object> paramsMap) {
        String sortString = "";
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(sortString));
        if (!"".equals(StringUtils.checkString(paramsMap.get("mobile")))) {
            paramsMap.put("mobile", paramsMap.get("mobile").toString());
        }
        PageList<?> list = (PageList<?>) platformMergeOrderService.queryMergeOrder(paramsMap, pageBounds);
        PageList<?> listMap = PhoneCodeSSL.getDataBaseListPage(list, paramsMap);
        Object json = this.getJsonMap(listMap);

        return json;
    }

    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/mergeOrder"})
    @RequestMapping("/queryOrderDetail")
    public Object queryOrderDetail(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                   @RequestParam HashMap<String, Object> paramMap) {
        List<Map<String, Object>> goodsList = platformMergeOrderService.queryOrderDetail(paramMap);
        return goodsList;
    }

    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/mergeOrder"})
    @RequestMapping("/saveMergeOrder")
    public Object saveMergeOrder(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            String payIds = paramsMap.get("payIds").toString();
            int i = platformMergeOrderService.mergeOrder(payIds);
            if (i == 0) {
                msg.setMsg("合单失败，请选择相同收货地址的订单进行合单！");
                msg.setSuccess(false);
                return msg;
            }
            msg.setMsg("保存成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("保存失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }


    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/mergeOrder"})
    @RequestMapping("/tradeOrder")
    public String tradeOrder() {
        return "platform/platform_trade_order";
    }

    /**
     * 查询充值活动
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/mergeOrder"})
    @RequestMapping("/queryTradeOrder")
    public Object tradeOrder(@Session(create = false) SessionProvider session, HttpServletRequest request,
                             @RequestParam(defaultValue = "0", required = false) Integer page,
                             @RequestParam(defaultValue = "10", required = false) Integer rows,
                             @RequestParam HashMap<String, Object> paramsMap) {
        String sortString = "";
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(sortString));
        if (!"".equals(StringUtils.checkString(paramsMap.get("mobile")))) {
            paramsMap.put("mobile", paramsMap.get("mobile").toString());
        }
        PageList<?> list = (PageList<?>) platformMergeOrderService.tradeOrder(paramsMap, pageBounds);
        PageList<?> listMap = PhoneCodeSSL.getDataBaseListPage(list, paramsMap);
        Object json = this.getJsonMap(listMap);

        return json;
    }
}
