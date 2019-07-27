package com.acl.goods.controller;

import com.acl.core.CoreBaseController;
import com.acl.goods.service.GoodsSettleAllotService;
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
 * className:GoodsSettleAllotController
 * author:wangzhe
 * createDate:2017年4月26日 下午03:09:00
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Controller
@RequestMapping("/goods")
public class GoodsSettleAllotController extends CoreBaseController {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private GoodsSettleAllotService goodsSettleAllotService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsSettleAllot"})
    @RequestMapping("/goodsSettleAllot")
    public String GoodsSettleAllot() {
        return "goods/settle_allot";
    }


    /**
     * 查询商品属性信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsSettleAllot"})
    @RequestMapping("/querySettleAllot")
    public Object querySettleAllot(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                   @RequestParam(defaultValue = "0", required = false) Integer page,
                                   @RequestParam(defaultValue = "10", required = false) Integer rows,
                                   @RequestParam HashMap<String, Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(""));
        //查询用户信息
        PageList<?> list = (PageList<?>) goodsSettleAllotService.querySettleAllot(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;

    }


    /**
     * 修改服务商比例
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsSettleAllot"})
    @RequestMapping("/updSettleAllot")
    public Object updSettleAllot(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            paramsMap.put("update_time", sdf.format(new java.util.Date()));
            goodsSettleAllotService.updateGSettleAllot(paramsMap);
            goodsSettleAllotService.setSettleAllotToRedis(paramsMap);
            //setAllotToRedis(paramsMap);
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
     * 添加服务商比列属性信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsSettleAllot"})
    @RequestMapping("/insSettleAllot")
    public Object insSettleAllot(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            paramsMap.put("create_time", sdf.format(new java.util.Date()));
            goodsSettleAllotService.insertSettleAllot(paramsMap);
            goodsSettleAllotService.setSettleAllotToRedis(paramsMap);
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
     * 验证服务商是否重复
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsSettleAllot"})
    @RequestMapping("/settleValidate")
    public Object settleValidate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = goodsSettleAllotService.settleValidate(paramsMap);
        if (list.size() == 0) {
            msg.setSuccess(true);
        } else {
            msg.setSuccess(false);
        }
        return msg;
    }

    /**
     * 获取deptInfo中的服务商ID和名称信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/querySettleName")
    public Object querySettleName(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = goodsSettleAllotService.querySettleName(paramsMap);
        return list;
    }

}
