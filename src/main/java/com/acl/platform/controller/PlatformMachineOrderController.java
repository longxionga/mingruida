package com.acl.platform.controller;

import com.acl.core.CoreBaseController;
import com.acl.goods.service.GoodsKsnListService;
import com.acl.platform.service.IPlatformMachineOrderService;
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
 * @author longxionga
 * @create 2019-03-19 11:45
 */
@Controller
@RequestMapping("/platform")
public class PlatformMachineOrderController extends CoreBaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlatformMachineOrderController.class);


    @Autowired
    private IPlatformMachineOrderService iPlatformMachineOrderService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/machineOrderInfo"})
    @RequestMapping("/machineOrderInfo")
    public String machineOrderInfo() {
        return "platform/platform_machine_order_info";
    }



    /**
     * 机具交易信息查询
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/machineOrderInfo"})
    @RequestMapping("/queryMachineOrderPageList")
    public Object queryMachineOrderPageList(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                        @RequestParam(defaultValue = "0", required = false) Integer page,
                                        @RequestParam(defaultValue = "10", required = false) Integer rows,
                                        @RequestParam HashMap<String, Object> paramsMap) {
        try {
            PageBounds pageBounds = new PageBounds(page, rows, Order.formString(""));
            LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
            if (paramsMap.get("start_date") != null) {
                paramsMap.put("start_date", StringUtils.replace((String) paramsMap.get("start_date"),"-",""));
            }
            if (paramsMap.get("end_date") != null) {
                paramsMap.put("end_date", StringUtils.replace((String) paramsMap.get("end_date"),"-",""));
            }
            //查询用户信息
            PageList<?> list = (PageList<?>) iPlatformMachineOrderService.queryMachineOrderPageList(paramsMap, pageBounds,loginSession);
            Object json = this.getJsonMap(list);
            return json;
        }catch (Exception e){
            LOGGER.error("查询异常：",e);
        }
        return null;

    }
    /**
     * 交易类型下拉框
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/getOrderTypeCombobox")
    public Object getOrderTypeCombobox(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                                 @RequestParam(defaultValue = "0", required = false) Integer page,
                                                 @RequestParam(defaultValue = "10", required = false) Integer rows,
                                                 @RequestParam HashMap<String,Object> paramsMap){
        List<Map<String,Object>> list = iPlatformMachineOrderService.getOrderTypeCombobox(paramsMap);
        return list;
    }
    /**
     * 支付类型下拉框
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/getPayTypeCombobox")
    public Object getPayTypeCombobox(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                       @RequestParam(defaultValue = "0", required = false) Integer page,
                                       @RequestParam(defaultValue = "10", required = false) Integer rows,
                                       @RequestParam HashMap<String,Object> paramsMap){
        List<Map<String,Object>> list = iPlatformMachineOrderService.getPayTypeCombobox(paramsMap);
        return list;
    }
}

