package com.acl.platform.controller;

import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;
import com.acl.platform.service.PlatformGiftOrderService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.HttpClientUtil;

import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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


@Controller
@RequestMapping("/platform")
public class PlatformGiftOrderController  extends CoreBaseController {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private PlatformGiftOrderService platformGiftOrderService;


    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/giftOrder" })
    @RequestMapping("/giftOrder")
    public String giftOrder(){
        return "platform/platform_gift_order";
    }

    /**
     * 查询
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/giftOrder" })
    @RequestMapping("/queryGiftOrder")
    public Object queryGiftOrder(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                 @RequestParam(defaultValue = "0", required = false) Integer page,
                                 @RequestParam(defaultValue = "10", required = false) Integer rows,
                                 @RequestParam HashMap<String,Object> paramsMap){

        PageBounds pageBounds = new PageBounds(page,rows, Order.formString(""));

        PageList<?> list = (PageList<?>)platformGiftOrderService.queryGiftOrder(paramsMap,pageBounds);
        //PageList<?> listMap= PhoneCodeSSL.getUserMobileList(list, paramsMap);
        Object json = this.getJsonMap(list);

        return json;
    }

    /**
     * 设置快递信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/giftOrder" })
    @RequestMapping("/updLogistics")
    public Object updLogistics(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
        try{
            if(!paramsMap.get("logistics_no").toString().equals("")){
                String logNo=paramsMap.get("logistics_no").toString().replace(" ","");
                paramsMap.put("logistics_no",logNo);
            }

            platformGiftOrderService.updateLogistics(paramsMap);
            msg.setMsg("更新成功");
            msg.setSuccess(true);
        }catch(Exception e){
            msg.setMsg("更新失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }



    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/giftOrder" })
    @RequestMapping("/queryGiftDetail")
    public Object queryGiftDetail(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                  @RequestParam HashMap<String, Object> paramMap){
        List<Map<String, Object>> goodsList = platformGiftOrderService.queryGiftDetail(paramMap);
        return goodsList;
    }

    /**
     * 验证电话号码是否和用户手机号或者配置表中手机号重复
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/giftOrder" })
    @RequestMapping("/queryLogistics")
    public Object queryLogistics(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){

        String logistics=paramsMap.get("logistics").toString();
        String logistics_no=paramsMap.get("logistics_no").toString();
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();

        String url="https://www.kuaidi100.com/query?";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("type",logistics);
        paramMap.put("postid",logistics_no);
        boolean proxy= SystemConfig.use_proxy.equals("1")?true:false;
        String data = HttpClientUtil.httpPost(url, paramMap, "UTF-8", proxy);

        data=data.replace("null","\'暂无数据\'");
        jsonObject=JSONObject.fromObject(data);

        return jsonObject;
    }
}
