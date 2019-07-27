package com.acl.goods.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.acl.goods.service.GoodsOrderService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 * className:GoodsOrderController
 * author:wangli
 * createDate:2017年4月25日 下午8:14:58
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Controller
@RequestMapping("/goods")
public class GoodsOrderController extends CoreBaseController {

    @Autowired
    private GoodsOrderService goodsOrderService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsOrder"})
    @RequestMapping("/goodsOrder")
    public String goodsOrder() {
        return "goods/goods_order";
    }


    /**
     * 查询订单信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsOrder"})
    @RequestMapping("/queryGoodsOrder")
    public Object queryGoodsOrder(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                  @RequestParam(defaultValue = "0", required = false) Integer page,
                                  @RequestParam(defaultValue = "10", required = false) Integer rows,
                                  @RequestParam HashMap<String, Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(""));
        //查询用户信息
        PageList<?> list = (PageList<?>) goodsOrderService.queryGoodsOrder(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;

    }

    /**
     * 填写快递公司及单号
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsOrder"})
    @RequestMapping("/putInLogistics")
    public Object putInLogistics(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            goodsOrderService.putInLogistics(paramsMap);
            msg.setMsg("操作成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("操作失败");
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
    @RequiresPermissions(value = {"/goods/goodsOrder"})
    @RequestMapping("/sendOutGoodsOrder")
    public Object sendOutGoodsOrder(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            goodsOrderService.sendOutGoodsOrder(paramsMap);
            msg.setMsg("操作成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("操作失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }
}
