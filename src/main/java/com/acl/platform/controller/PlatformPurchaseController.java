package com.acl.platform.controller;

import com.acl.core.CoreBaseController;
import com.acl.platform.service.PlatformPurchaseService;
import com.acl.pojo.LoginSession;
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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangli on 2017/11/8.
 */
@Controller
@RequestMapping("/platform")
public class PlatformPurchaseController extends CoreBaseController {

    @Autowired
    private PlatformPurchaseService platformPurchaseService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/purchase" })
    @RequestMapping("/purchase")
    public String purchase(){
        return "platform/platform_purchase";
    }

    /**
     * 查询商品
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/purchase" })
    @RequestMapping("/queryGoodsInfoForSettle")
    public Object queryGoodsInfoForSettle(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                @RequestParam(defaultValue = "0", required = false) Integer page,
                                @RequestParam(defaultValue = "10", required = false) Integer rows,
                                @RequestParam HashMap<String,Object> paramsMap){
        PageBounds pageBounds = new PageBounds(page,rows, Order.formString(""));
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        paramsMap.put("settle_id",loginSession.getDept_id());
        PageList<?> list = (PageList<?>)platformPurchaseService.queryGoodsInfoForSettle(paramsMap,pageBounds);
        Object json = this.getJsonMap(list);
        return json;

    }

    /**
     * 查询
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/purchase" })
    @RequestMapping("/queryPurchase")
    public Object queryPurchase(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                  @RequestParam(defaultValue = "0", required = false) Integer page,
                                  @RequestParam(defaultValue = "10", required = false) Integer rows,
                                  @RequestParam HashMap<String,Object> paramsMap){
        PageBounds pageBounds = new PageBounds(page,rows, Order.formString(""));
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        paramsMap.put("settle_id",loginSession.getDept_id());
        PageList<?> list = (PageList<?>)platformPurchaseService.queryPurchase(paramsMap,pageBounds);
        Object json = this.getJsonMap(list);
        return json;

    }

    /**
     * 下单
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/purchase" })
    @RequestMapping("/insertPurchase")
    public Object insertPurchase(@Session(create = false) SessionProvider session,@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        try{
            paramsMap.put("settle_id",loginSession.getDept_id());
            paramsMap.put("settle_code",loginSession.getDept_code());
            paramsMap.put("status","0");
            platformPurchaseService.insertPurchase(paramsMap);
            msg.setMsg("下单成功");
            msg.setSuccess(true);
        }catch(Exception e){
            dbLog.setAction_message(loginSession.getUser_name()+"服务商进货失败");
            dbLog.setIs_ok(1);
            msg.setMsg("下单失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }

    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/purchase"})
    @RequestMapping("/queryGoodsList")
    public Object queryGoodsList(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                 @RequestParam HashMap<String, Object> paramMap) {
        List<Map<String, Object>> goodsList = platformPurchaseService.queryGoodsList(paramMap);
        return goodsList;
    }

    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/purchase"})
    @RequestMapping("/queryGoodsSpec")
    public Object queryGoodsSpec(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                 @RequestParam HashMap<String, Object> paramMap) {
        List<Map<String, Object>> goodsList = platformPurchaseService.queryGoodsSpec(paramMap);
        return goodsList;
    }
    //进货时查询库存和价格
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/purchase"})
    @RequestMapping("/queryGoodsStock")
    public Object queryGoodsStock(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                 @RequestParam HashMap<String, Object> paramMap) {
        List<Map<String, Object>> goodsList = platformPurchaseService.queryGoodsStock(paramMap);
        return goodsList;
    }

    //进货时查询库存和价格
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/purchase"})
    @RequestMapping("/calcOrder")
    public Object calcOrder(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                  @RequestParam HashMap<String, Object> paramMap) {

        if(!paramMap.get("num").toString().matches("[0-9]+")){
            msg.setMsg("请输入数字");
            msg.setSuccess(false);
            return msg;
        }

        if(paramMap.get("goods_id").equals("") || paramMap.get("spec_id").equals("")){
            msg.setMsg("请先选择商品和规格");
            msg.setSuccess(false);
        }
        List<Map<String, Object>> goodsList = platformPurchaseService.queryGoodsStock(paramMap);
        int sum=0;
        if(goodsList.size()!=0){
            sum=Integer.parseInt(goodsList.get(0).get("booked_amount").toString());
        }
        if(Integer.parseInt(paramMap.get("num").toString())>sum){
            msg.setMsg("下单数量不得大于可订数量");
            msg.setSuccess(false);
        }else {
            msg.setSuccess(true);
        }
        return msg;
    }
}
