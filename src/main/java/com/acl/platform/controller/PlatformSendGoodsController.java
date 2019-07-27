package com.acl.platform.controller;

import com.acl.core.CoreBaseController;
import com.acl.platform.service.PlatformSendGoodsService;
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
import java.util.HashMap;

/**
 * Created by wangli on 2017/11/9.
 */
@Controller
@RequestMapping("/platform")
public class PlatformSendGoodsController extends CoreBaseController{
    @Autowired
    private PlatformSendGoodsService platfromSendGoodsService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/sendGoods" })
    @RequestMapping("/sendGoods")
    public String sendGoods(){
        return "platform/platform_send_goods";
    }

    /**
     * 查询
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/sendGoods" })
    @RequestMapping("/querySendGoods")
    public Object querySendGoods(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                @RequestParam(defaultValue = "0", required = false) Integer page,
                                @RequestParam(defaultValue = "10", required = false) Integer rows,
                                @RequestParam HashMap<String,Object> paramsMap){
        PageBounds pageBounds = new PageBounds(page,rows, Order.formString(""));
        PageList<?> list = (PageList<?>)platfromSendGoodsService.querySendGoods(paramsMap,pageBounds);
        Object json = this.getJsonMap(list);
        return json;

    }

    /**
     * 审核
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/sendGoods" })
    @RequestMapping("/agree")
    public Object agree(@Session(create = false) SessionProvider session,@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
        try{
            LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
            paramsMap.put("reviewer",loginSession.getUser_name());
            platfromSendGoodsService.agree(paramsMap);
            msg.setMsg("审核成功");
            msg.setSuccess(true);
        }catch(Exception e){
            msg.setMsg("审核失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }


    /**
     * 审核拒绝
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/sendGoods" })
    @RequestMapping("/disagree")
    public Object disagree(@Session(create = false) SessionProvider session,@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
        try{
            LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
            paramsMap.put("reviewer",loginSession.getUser_name());
            platfromSendGoodsService.disagree(paramsMap);
            msg.setMsg("审核成功");
            msg.setSuccess(true);
        }catch(Exception e){
            msg.setMsg("审核失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }
}
