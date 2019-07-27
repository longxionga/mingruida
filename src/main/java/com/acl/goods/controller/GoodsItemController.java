package com.acl.goods.controller;

import com.acl.core.CoreBaseController;
import com.acl.goods.service.GoodsItemService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
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
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:GoodsItemController
 * author:wangzhe
 * createDate:2017年4月26日 下午03:09:00
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Controller
@RequestMapping("/goods")
public class GoodsItemController extends CoreBaseController {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private GoodsItemService goodsItemService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsItem"})
    @RequestMapping("/goodsItem")
    public String GoodsItem() {
        return "goods/goods_item";
    }


    /**
     * 查询商品属性信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsItem"})
    @RequestMapping("/queryGoodsItem")
    public Object queryGoodsItem(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                 @RequestParam(defaultValue = "0", required = false) Integer page,
                                 @RequestParam(defaultValue = "10", required = false) Integer rows,
                                 @RequestParam HashMap<String, Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(""));
        //查询用户信息
        PageList<?> list = (PageList<?>) goodsItemService.queryGoodsItem(paramsMap, pageBounds);
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
    @RequiresPermissions(value = {"/goods/goodsItem"})
    @RequestMapping("/updGoodsItem")
    public Object updGoodsItem(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            paramsMap.put("update_time", sdf.format(new java.util.Date()));
            goodsItemService.updateGoodsItem(paramsMap);
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
    @RequiresPermissions(value = {"/goods/goodsItem"})
    @RequestMapping("/insGoodsItem")
    public Object insGoodsItem(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            paramsMap.put("create_time", sdf.format(new java.util.Date()));
            goodsItemService.insertGoodsItem(paramsMap);
            msg.setMsg("新增成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("新增失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }


    /**
     * 验证同一商品的商品参数是否重复
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsItem"})
    @RequestMapping("/itemValidate")
    public Object itemValidate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = goodsItemService.itemValidate(paramsMap);
        if (list.size() == 0) {
            msg.setSuccess(true);
        } else {
            msg.setSuccess(false);
        }
        return msg;
    }

    /**
     * 获取系统中商品编码和商品名称信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/queryItemGoodsName")
    public Object queryItemGoodsName(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = goodsItemService.queryItemGoodsName(paramsMap);
        return list;
    }
}
