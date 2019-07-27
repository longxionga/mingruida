package com.acl.platform.controller;

import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformSettleStockService;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by wangli on 2017/11/17.
 */
@Controller
@RequestMapping("platform")
public class PlatformSettleStockController extends CoreBaseController{
    @Autowired
    private IPlatformSettleStockService platformSettleStockService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/settleStock" })
    @RequestMapping("/settleStock")
    public String purchase(@Session(create = false) SessionProvider session, ModelMap modelMap){
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        modelMap.addAttribute("deptType", loginSession.getDept_type());
        return "platform/platform_settle_stock";
    }
    /**
     * 查询
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/settleStock" })
    @RequestMapping("/querySettleStock")
    public Object querySettleStock(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                @RequestParam(defaultValue = "0", required = false) Integer page,
                                @RequestParam(defaultValue = "10", required = false) Integer rows,
                                @RequestParam HashMap<String,Object> paramsMap){
        PageBounds pageBounds = new PageBounds(page,rows, Order.formString(""));
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        if(!loginSession.getDept_type().equals("0")){
            paramsMap.put("settle_id",loginSession.getDept_id());
        }

        PageList<?> list = (PageList<?>)platformSettleStockService.querySettleStock(paramsMap,pageBounds);
        Object json = this.getJsonMap(list);
        return json;

    }
}
