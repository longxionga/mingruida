package com.acl.goods.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.acl.goods.service.GoodsImageService;
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
 * className:GoodsImageController
 * author:wangli
 * createDate:2017年4月26日 下午1:57:00
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Controller
@RequestMapping("/goods")
public class GoodsImageController extends CoreBaseController {

    @Autowired
    private GoodsImageService goodsImageService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsImage"})
    @RequestMapping("/goodsImage")
    public String goodsImage() {
        return "goods/goods_image";
    }

    /**
     * 查询商品属性信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsImage"})
    @RequestMapping("/queryGoodsImage")
    public Object queryGoodsImage(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                  @RequestParam(defaultValue = "0", required = false) Integer page,
                                  @RequestParam(defaultValue = "10", required = false) Integer rows,
                                  @RequestParam HashMap<String, Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(""));
        //查询用户信息
        PageList<?> list = (PageList<?>) goodsImageService.queryGoodsImage(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;

    }


    /**
     * 修改商品属性信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsImage"})
    @RequestMapping("/updGoodsImage")
    public Object updGoodsImage(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            goodsImageService.updateGoodsImage(paramsMap);
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
     * 添加商品属性信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsImage"})
    @RequestMapping("/insGoodsImage")
    public Object insGoodsImage(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            goodsImageService.insertGoodsImage(paramsMap);
            //第二步操作商品图片

            msg.setMsg("新增成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("新增失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }
}
