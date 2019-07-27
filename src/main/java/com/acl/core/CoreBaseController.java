package com.acl.core;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.acl.pojo.DBLog;
import com.acl.sys.service.DBLogService;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.paginator.domain.Paginator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;
import com.anchol.common.entity.CallBackMsg;

/**
 * className:CoreBaseController
 * author:malx
 * createDate:2015年12月21日 下午4:43:43
 * version:1.0
 * department:安创乐科技
 * description:核心基类
 */
@Controller
public class CoreBaseController {
    @Autowired
    public DBLogService dbLogService;

    public CallBackMsg msg = new CallBackMsg();

    public Date date = new Date();

    public DBLog dbLog = new DBLog();

    //根据时间排序(查询使用)
    public static String orderByCreateDate = "create_date.desc";

    //根据时间排序(查询使用)
    public static String orderByCreateTime = "create_time.desc";
    //根据时间排序(导出报表使用)
    public static String orderByCreateDateExp = "创建时间.desc";

    //根据盈亏排序(查询使用)
    public static String orderByProfitloss = "profit_loss.desc";


    //根据商品ID排序(查询使用)
    public static String orderByGoodsId = "goods_id.asc";


    /**
     * 返回request
     *
     * @return
     */
    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
    }

    /**
     * 返回response
     *
     * @return
     */
    public HttpServletResponse getResponse() {
        return ((ServletWebRequest) RequestContextHolder.getRequestAttributes())
                .getResponse();
    }

    /**
     * 返回session
     *
     * @return
     */
    public HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest().getSession();
    }

    /**
     * 返回对应的bean
     *
     * @param cls
     * @return
     */
    public Object getBean(Class<?> cls) {
        return ContextLoader.getCurrentWebApplicationContext().getBean(cls);
    }

    public Object getBean(String cls) {
        return ContextLoader.getCurrentWebApplicationContext().getBean(cls);
    }

    /**
     * 获取jsonMap对应的值
     *
     * @param list
     * @return
     */
    public Object getJsonMap(PageList<?> list) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Paginator paginator = list.getPaginator();
        int i = paginator.getTotalCount();
        jsonMap.put("total", i);// total键 存放总记录数，必须的 目前写死
        jsonMap.put("rows", list);// rows键 存放每页记录 list
        JSON json = (JSON) JSONObject.toJSON(jsonMap);
        return json;
    }


	/*public Map<String, Object> queryMyInfo(HttpServletRequest request,@Session(create = false) SessionProvider session,
      Map<String, Object> map) {
    if (session.getAttribute("userInfo") != null) {
    	AdminClient ai = (AdminClient) session.getAttribute("userInfo");
    	map.put("user_id", ai.getUSER_ID());
      if(ai.getSID() != null&&!ai.getSID().equals("")){//结算会员登录
        map.put("SID", ai.getSID());
        map.put("user_id", ai.getUSER_ID());
      }
      if(ai.getAID() != null&&!ai.getAID().equals("")){//代理商登录
            map.put("AID", ai.getAID());
            map.put("user_id", ai.getUSER_ID());
        }
        if(ai.getDID() != null&&!ai.getDID().equals("")){//部门登录
            map.put("DID", ai.getDID());
            map.put("user_id", ai.getUSER_ID());
        }
      if(ai.getBID() != null&&!ai.getBID().equals("")){//经纪人登录
        map.put("BID", ai.getBID());
        map.put("user_id", ai.getUSER_ID());
      }
      
      return map;
    }else{
      session.invalidate();
      return map;
    }
  }*/

    /**
     * @param pageURL 请求的页面
     * @return 请求页或者主页
     * @description 判断是否是登录状态
     */
    public String checkIsLogin(String pageURL, @Session(create = false) SessionProvider session) {
//		HttpSession session = this.getSession();
        if (session.getAttribute("isLogin") != null && (Boolean) session.getAttribute("isLogin").equals(true)) {
            return pageURL;
        }
        return "redirect:/sys/login";
    }

/*	*//**
     * @description 获取用户角色状态
     *            用户ID
     * @return 用户角色
     *//*
	public String getUserRole(String userId){
		List<Map<String, Object>> list = sysRoleManage.getRoleByUserId(userId);
		if(list != null && !list.isEmpty()){
			return String.valueOf(list.get(0).get("role_id"));
		}
		return "";
	}*/


    /**
     * 在导出时移除英文的日期,该日期导出前用作日期的排序
     *
     * @param pageList
     * @return
     */
    public List<Map<String, Object>> removeDate(PageList<?> pageList) {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> simpleList = (List<Map<String, Object>>) pageList;
        if (simpleList.size() > 0) {
            for (int i = 0; i < simpleList.size(); i++) {
                simpleList.get(i).remove("create_date");
            }
        }
        return simpleList;
    }


    public boolean testExcelCode(HttpServletRequest request, String code, @Session(create = false) SessionProvider session) {
        Object excelCode = session.getAttribute("excelCode");
        if (excelCode != null && code != null && excelCode.toString().equals(code)) {
            return true;
        }
        return false;
    }

    public boolean testWholeInfoCode(HttpServletRequest request, @Session(create = false) SessionProvider session) {
        Object wholeInfoToggle = session.getAttribute("wholeInfoToggle");
        if (wholeInfoToggle != null && "turnoff".equals(wholeInfoToggle.toString())) {
            return true;
        }
        return false;
    }
}