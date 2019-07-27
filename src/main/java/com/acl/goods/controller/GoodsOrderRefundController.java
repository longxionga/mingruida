package com.acl.goods.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;
import com.acl.goods.service.GoodsOrderRefundService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 * className:GoodsOrderRefundController
 * author:wangli
 * createDate:2017年4月25日 下午3:37:41
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Controller
@RequestMapping("/goods")
public class GoodsOrderRefundController extends CoreBaseController {
    @Autowired
    private GoodsOrderRefundService goodsOrderRefundService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/orderRefund"})
    @RequestMapping("/orderRefund")
    public String orderRefund() {
        return "goods/goods_order_refund";
    }


    /**
     * 查询退款/退货订单信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/orderRefund"})
    @RequestMapping("/queryGoodsOrderRefund")
    public Object queryGoodsOrderRefund(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                        @RequestParam(defaultValue = "0", required = false) Integer page,
                                        @RequestParam(defaultValue = "10", required = false) Integer rows,
                                        @RequestParam HashMap<String, Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(""));
        //查询用户信息
        PageList<?> list = (PageList<?>) goodsOrderRefundService.queryGoodsOrderRefund(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;

    }


    /**
     * 物流信息更新成功
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/orderRefund"})
    @RequestMapping("/updGoodsOrderRefund")
    public Object updGoodsOrderRefund(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            goodsOrderRefundService.updGoodsOrderRefund(paramsMap);
            msg.setMsg("物流信息更新成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("审核失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }


    /**
     * 审核通过
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/orderRefund"})
    @RequestMapping("/agreeGoodsOrderRefund")
    public Object agreeGoodsOrderRefund(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            //goodsOrderRefundService.agreeGoodsOrderRefund(paramsMap);
            msg.setMsg("审核成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("审核失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 审核不通过
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/orderRefund"})
    @RequestMapping("/disAgreeGoodsOrderRefund")
    public Object disAgreeGoodsOrderRefund(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            //goodsOrderRefundService.disAgreeGoodsOrderRefund(paramsMap);
            msg.setMsg("审核成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("审核失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }
}
