package com.acl.platform.controller;

import com.acl.component.MongodbBaseDao;
import com.acl.core.CoreBaseController;
import com.acl.platform.service.PlatformAgentDeptService;
import com.acl.pojo.LoginSession;
import com.acl.sys.service.SysDeptInfoService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.FormatDateUtil;
import com.acl.utils.util.NetUtils;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * className:PlatformBrokerDeptController
 * author:wangli
 * createDate:2016年9月6日 上午10:48:57
 * vsersion:3.0
 * department:安创乐科技
 * description:代理商部门管理，对代理商下面的经纪人进行部门调配
 */
@Controller
@RequestMapping("/platform")
public class PlatformAgentDeptController extends CoreBaseController {
    @Autowired
    private PlatformAgentDeptService platformAgentDeptService;
    @Autowired
    private SysDeptInfoService sysDeptInfoService;
    @Autowired
    private MongodbBaseDao<T> mongodbBaseDao;
    @Autowired
    private MongoTemplate mongoTemplate;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/agentDept"})
    @RequestMapping("/agentDept")
    public String agentDept(@Session(create = false) SessionProvider session, ModelMap modelMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        modelMap.addAttribute("deptName", loginSession.getDept_name());
        modelMap.addAttribute("deptType", loginSession.getDept_type());
        return "platform/platform_agent_dept";
    }

    /**
     * 查询代理商部门信息
     *
     * @param request
     * @param page
     * @param rows
     * @param paramsMap
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/agentDept"})
    @RequestMapping("/queryAgentDept")
    public Object queryAgentDept(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                 @RequestParam(defaultValue = "0", required = false) Integer page,
                                 @RequestParam(defaultValue = "10", required = false) Integer rows,
                                 @RequestParam HashMap<String, Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(orderByCreateDate));
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        String dept_type = loginSession.getDept_type();
        switch (dept_type) {
            case "1"://交易中心
                paramsMap.put("ce_id", loginSession.getDept_id());
                break;
            case "2"://渠道
                paramsMap.put("ch_id", loginSession.getDept_id());
                break;
            case "3"://服务商
                paramsMap.put("settle_id", loginSession.getDept_id());
                break;
            case "4"://代理商
                paramsMap.put("agent_id", loginSession.getDept_id());
                break;
            case "5"://代理商部门
                paramsMap.put("dept_id", loginSession.getDept_id());
                break;
            case "6"://经纪人
                paramsMap.put("dept_id", loginSession.getDept_id());
                break;
        }
        // 查询代理商部门信息
        PageList<?> list = (PageList<?>) platformAgentDeptService.queryAgentDept(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;
    }

    /**
     * 经纪人更换部门
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/agentDept"})
    @RequestMapping("/updAgentdeptBrokers")
    public Object updateAgentdeptBrokers(@Session(create = false) SessionProvider session, @RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
            /*LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
			try{
			String [] brokers_id=paramsMap.get("brokers_id").toString().split(",");
			paramsMap.put("brokers_id",brokers_id);
			paramsMap.put("user_name", loginSession.getUser_name());
			paramsMap.put("user_id", loginSession.getUser_id());			
			if(brokers_id.length>0){
				//将修改信息保存到临时表中，多次修改采用先删除后添加的方式			
				platformAgentDeptService.deleteAgentdeptBrokers(paramsMap);
				for(int i=0;i<brokers_id.length;i++){
					HashMap<String,Object> tmp = new HashMap<String,Object>();	
					tmp.put("sys_id", UUIDGenerator.generate());
					tmp.put("dept_id",paramsMap.get("dept_id").toString());					
					tmp.put("broker_id", brokers_id[i]);	
					platformAgentDeptService.insertAgentdeptBrokers(tmp);
				}
			}
			
			 msg.setMsg("更新成功，次日生效");
			 msg.setSuccess(true);
			}catch(Exception e){
			 msg.setMsg("更新失败");
			 msg.setSuccess(false);
			 e.printStackTrace();
			}
			return msg;*/
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        try {
            dbLog.setMethod_name("经纪人更换部门");
            dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
            dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
            String[] brokers_id = paramsMap.get("brokers_id").toString().split(",");
            paramsMap.put("brokers_id", brokers_id);
            paramsMap.put("user_name", loginSession.getUser_name());
            paramsMap.put("user_id", loginSession.getUser_id());
            //设置经纪人到不同的部门
            platformAgentDeptService.updateAgentdeptBrokers(paramsMap);

            //经纪人更换部门更新mongodb
            if (brokers_id.length > 0) {
                for (int i = 0; i < brokers_id.length; i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("broker_id", brokers_id[i]);
                    Update update = new Update();
                    update.set("dept_id", paramsMap.get("dept_id").toString());
                    update.set("dept_name", paramsMap.get("dept_name").toString());
                }
            }
            dbLog.setAction_message("成功," + StringUtils.convertString(paramsMap.get("brokers_names")) + "更换部门为：" + StringUtils.convertString(paramsMap.get("dept_name")));
            dbLog.setIs_ok(0);
            msg.setMsg("更新成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            dbLog.setAction_message("失败");
            dbLog.setIs_ok(1);
            msg.setMsg("更新失败");
            msg.setSuccess(false);
            e.printStackTrace();
        } finally {
            dbLogService.insertLog(dbLog);
        }
        return msg;
    }

    /**
     * 查询代理商下面的所有经纪人及部门
     *
     * @param request
     * @param page
     * @param rows
     * @param paramsMap
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/agentDept"})
    @RequestMapping("/queryAgentDeptBrokerInfo")
    public Object queryAgentDeptBrokerInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                           @RequestParam(defaultValue = "0", required = false) Integer page,
                                           @RequestParam(defaultValue = "10", required = false) Integer rows,
                                           @RequestParam HashMap<String, Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(orderByCreateDate));
        // 查询代理商下面的所有经纪人及部门
        PageList<?> list = (PageList<?>) platformAgentDeptService.queryAgentDeptBrokerInfo(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;
    }

    /**
     * 插入代理商部门信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/agentDept"})
    @RequestMapping("/insAgentDept")
    public Object insertAgentDept(@Session(create = false) SessionProvider session, @RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        String dept_id = sysDeptInfoService.countDeptInfo(null).get("num1").toString();
        //paramsMap.put("dept_id", sysDeptInfoService.countDeptInfo(null).get("num") + "");
        paramsMap.put("dept_id", dept_id);
        paramsMap.put("create_date", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
        paramsMap.put("dept_parent_id", loginSession.getDept_id());
        String agent_code = loginSession.getDept_code();

        //paramsMap.put("dept_code", agent_code+"-"+sysDeptInfoService.countDeptInfo(null).get("num"));
        paramsMap.put("dept_code", agent_code + "-" + dept_id);
        paramsMap.put("is_use", "1");
        paramsMap.put("dept_money", '0');
        paramsMap.put("dept_type", "5");
        paramsMap.put("dept_ratio", '0');

        paramsMap.put("dept_app_id", "");
        paramsMap.put("dept_app_secret", "");
        paramsMap.put("dept_url", "");
        paramsMap.put("broker_url", "");

        paramsMap.put("is_tj_man", "");
        paramsMap.put("tj_ratio", "");

        try {
            platformAgentDeptService.insertAgentDept(paramsMap);
            mongodbBaseDao.save(paramsMap, "t_back_dept_info");
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
     * 更新代理商部门信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/agentDept"})
    @RequestMapping("/updAgentDept")
    public Object updateAgentdept(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            platformAgentDeptService.updateAgentdept(paramsMap);
            Map<String, Object> map = new HashMap<>();
            map.put("dept_id", paramsMap.get("dept_id").toString());
            Update update = new Update();
            update.set("dept_name", paramsMap.get("dept_name").toString());
            update.set("dept_mobile", paramsMap.get("dept_mobile").toString());
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
     * 查询formongo
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/agentDept"})
    @RequestMapping("/queryDeptForMon")
    public Object queryDeptForMon(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            List<Map<String, Object>> list = platformAgentDeptService.queryDeptForMon(paramsMap);
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> map = new HashMap<String, Object>();

                    String dept_code = StringUtils.convertString(list.get(i).get("dept_code"));
                    String dept_name = StringUtils.convertString(list.get(i).get("dept_name"));
                    String is_use = StringUtils.convertString(list.get(i).get("is_use"));
                    String dept_ratio = StringUtils.convertString(list.get(i).get("dept_ratio"));
                    String dept_money = StringUtils.convertString(list.get(i).get("dept_money"));
                    String dept_mobile = StringUtils.convertString(list.get(i).get("dept_mobile"));
                    String dept_app_id = StringUtils.convertString(list.get(i).get("dept_app_id"));
                    String dept_app_secret = StringUtils.convertString(list.get(i).get("dept_app_secret"));
                    String dept_url = StringUtils.convertString(list.get(i).get("dept_url"));
                    String broker_url = StringUtils.convertString(list.get(i).get("broker_url"));
                    String dept_id = StringUtils.convertString(list.get(i).get("dept_id"));
                    String dept_parent_id = StringUtils.convertString(list.get(i).get("dept_parent_id"));
                    String dept_type = StringUtils.convertString(list.get(i).get("dept_type"));
                    String create_date = StringUtils.convertString(list.get(i).get("create_date"));
                    String is_tj_man = StringUtils.convertString(list.get(i).get("is_tj_man"));
                    String tj_ratio = StringUtils.convertString(list.get(i).get("tj_ratio"));
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("dept_code", dept_code);
                    //查询mongodb中是否有此条数据
                    //如果有执行修改操作
                    //如果没有执行新增操作
                    map.put("dept_id", dept_id);
                    Update update = new Update();
                    update.set("dept_code", dept_code);
                    update.set("dept_name", dept_name);
                    update.set("is_use", is_use);
                    update.set("dept_ratio", dept_ratio);
                    update.set("dept_money", dept_money);
                    update.set("dept_mobile", dept_mobile);
                    update.set("dept_app_id", dept_app_id);
                    update.set("dept_app_secret", dept_app_secret);
                    update.set("dept_url", dept_url);
                    update.set("broker_url", broker_url);
                    update.set("dept_id", dept_id);
                    update.set("dept_parent_id", dept_parent_id);
                    update.set("dept_type", dept_type);
                    update.set("create_date", create_date);
                    update.set("is_tj_man", is_tj_man);
                    update.set("tj_ratio", tj_ratio);
                    mongoTemplate.upsert(new Query(Criteria.where("dept_id").is(dept_id)), update, "t_back_dept_info");
                    //	mongodbBaseDao.updateFirst(map, update, "t_back_dept_info");
                }

            }

            msg.setMsg("更新成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("更新失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }


}
