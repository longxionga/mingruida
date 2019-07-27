package com.acl.sys.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.acl.sys.service.SysDeptInfoService;
import com.acl.utils.util.RandomValidateCode;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;
import com.acl.pojo.DeptInfo;
import com.acl.pojo.LoginSession;
import com.acl.sys.service.ISysFrontBrokerService;
import com.acl.utils.util.FormatDateUtil;
import com.acl.utils.util.NetUtils;
import com.acl.utils.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 * className:SysDeptInfoController
 * author:huangs
 * createDate:2016年8月10日 下午6:11:13
 * version:3.0
 * description:部门管理
 */
@Controller
@RequestMapping("/sys")
public class SysDeptInfoController extends CoreBaseController {

    @Autowired
    private SysDeptInfoService sysDeptInfoService;

    @Autowired
    private ISysFrontBrokerService sysFrontBrokerService;


    @RequiresAuthentication
    @RequiresPermissions(value = {"/sys/deptInfo"})
    @RequestMapping("/deptInfo")
    public String deptInfo(@Session(create = false) SessionProvider session, ModelMap modelMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        modelMap.addAttribute("deptType", loginSession.getDept_type());
        return "sys/sys_dept_info";
    }


    /**
     * 查询部门信息 代码层面组装 组织架构树
     *
     * @return
     */
/*	@ResponseBody
    @RequiresAuthentication
	@RequiresPermissions(value = { "/sys/deptInfo" })
	@RequestMapping("/queryDeptInfos")
	public Object queryDeptInfos(@Session(create = false) SessionProvider session, HttpServletRequest request,
			@RequestParam HashMap<String, Object> paramsMap) {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		List<DeptTreeGrid> list = new ArrayList<DeptTreeGrid>();
		DeptTreeGrid tree = new DeptTreeGrid();
//		HashMap<String, Object> map = new HashMap<>();
		paramsMap.put("dept_id", loginSession.getDept_id());
		// map.put("dept_parent_id", "-1");
		// map.put("dept_id", loginSession.getDept_id().equals("0") ? "-1" :
		// loginSession.getDept_id());
		List<Map<String, Object>> deptGrids = sysDeptInfoService.queryDeptInfo(paramsMap);
		if (deptGrids != null) {
			for (Map<String, Object> deptMap : deptGrids) {
				tree.setDept_id(deptMap.get("dept_id").toString());
				tree.setDept_code(deptMap.get("dept_code").toString());
				tree.setDept_name(deptMap.get("dept_name").toString());
				tree.setDept_mobile(deptMap.get("dept_mobile") == null ? "" : deptMap.get("dept_mobile").toString());
				tree.setDept_money(deptMap.get("dept_money") == null ? "" : deptMap.get("dept_money").toString());
				tree.setDept_ratio(Integer.parseInt(deptMap.get("dept_ratio").toString()));
				tree.setDept_type(deptMap.get("dept_type").toString());
				tree.setDept_database(
						deptMap.get("dept_database") == null ? "" : deptMap.get("dept_database").toString());
				tree.setDept_url(deptMap.get("dept_url") == null ? "" : deptMap.get("dept_url").toString());
				tree.setBroker_url(deptMap.get("broker_url") == null ? "" : deptMap.get("broker_url").toString());
				tree.setIs_tj_man(deptMap.get("is_tj_man") == null ? "" : deptMap.get("is_tj_man").toString());
				tree.setTj_ratio(deptMap.get("tj_ratio") == null ? "" : deptMap.get("tj_ratio").toString());
				tree.setDept_parent_id(deptMap.get("dept_parent_id").toString());
				tree.setIs_use(deptMap.get("is_use").toString());
				tree.setDept_app_id(deptMap.get("dept_app_id") == null ? "" : deptMap.get("dept_app_id").toString());
				tree.setDept_app_secret(
						deptMap.get("dept_app_secret") == null ? "" : deptMap.get("dept_app_secret").toString());
				tree.setChildren(initTree(deptMap.get("dept_id").toString()));
			}
		}
		list.add(tree);
		return list;
	}*/

	/*private List<DeptTreeGrid> initTree(String dept_id) {
        List<DeptTreeGrid> list = new ArrayList<DeptTreeGrid>();
		HashMap<String, Object> map = new HashMap<>();
		map.put("dept_parent_id", dept_id);
		List<Map<String, Object>> deptGrids = sysDeptInfoService.queryDeptInfo(map);
		if (deptGrids != null) {
			for (Map<String, Object> deptMap : deptGrids) {
				DeptTreeGrid tree = new DeptTreeGrid();
				tree.setDept_id(StringUtils.checkString(deptMap.get("dept_id")));
				tree.setDept_code(StringUtils.checkString(deptMap.get("dept_code")));
				tree.setDept_name(StringUtils.checkString(deptMap.get("dept_name")));
				tree.setDept_mobile(StringUtils.checkString(deptMap.get("dept_mobile")));
				tree.setDept_money(StringUtils.checkString(deptMap.get("dept_money")));
				tree.setDept_ratio(StringUtils.checkInt(deptMap.get("dept_ratio")));//int
				tree.setDept_type(StringUtils.checkString(deptMap.get("dept_type")));
				tree.setDept_database(StringUtils.checkString(deptMap.get("dept_database")));
				tree.setDept_url(StringUtils.checkString(deptMap.get("dept_url")));
				tree.setBroker_url(StringUtils.checkString(deptMap.get("broker_url")));
				tree.setIs_tj_man(StringUtils.checkString(deptMap.get("is_tj_man")));
				tree.setTj_ratio(StringUtils.checkString(deptMap.get("tj_ratio")));
				tree.setDept_parent_id(StringUtils.checkString(deptMap.get("dept_parent_id")));
				tree.setIs_use(StringUtils.checkString(deptMap.get("is_use")));
				tree.setDept_app_id(StringUtils.checkString(deptMap.get("dept_app_id")));
				tree.setDept_app_secret(StringUtils.checkString(deptMap.get("dept_app_secret")));
				tree.setChildren(initTree(StringUtils.checkString(deptMap.get("dept_id"))));
				if("4".equals(StringUtils.checkString(deptMap.get("dept_type")))){
					tree.setState("closed");
				}
				list.add(tree);
			}
		}
		return list;
	}*/

    /**
     * 查询部门信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/queryDeptInfoByPa")
    public Object queryDeptInfoByPa(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                    @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        HashMap<String, Object> parmas = new HashMap<String, Object>();
        parmas.put("dept_id", loginSession.getDept_id());
        //sysDeptInfoService.queryDeptChildNodeInfo(parmas);
        if (paramsMap.isEmpty()) {
            paramsMap.put("dept_id", loginSession.getDept_id());
        }
        //paramsMap.put("search", loginSession.getDept_id());

        List<Map<String, Object>> list = sysDeptInfoService.queryDeptInfo(paramsMap);
        Object json = JSONObject.toJSON(list);
        return json;
    }


    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/sys/deptInfo"})
    @RequestMapping("/queryDeptInfos")
    public Object queryDeptInfos(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                 @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        int dept_id = StringUtils.checkInt(loginSession.getDept_id()) == 0 ? -1 : StringUtils.checkInt(loginSession.getDept_id());
        paramsMap.put("dept_id", dept_id);
//		List<Map<String, Object>> treeClist = sysDeptInfoService.queryDeptChildInfo(paramsMap);
//        sysDeptInfoService.queryDeptChildNodeInfo(paramsMap);
        List<DeptInfo> treeClist = sysDeptInfoService.queryDeptChildInfoPojo(paramsMap);
        Map<String, Object> mapjson = new HashMap<>();
        mapjson.put("total", treeClist.size());
        mapjson.put("rows", JSONObject.toJSON(treeClist));
        Object json = JSONObject.toJSON(mapjson);
        return json;
    }

    /**
     * 查询子部门信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/queryDeptChildInfo")
    public Object queryDeptChildInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                     @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        if (paramsMap.isEmpty()) {
            paramsMap.put("dept_id", loginSession.getDept_id());
        }
        List<Map<String, Object>> list = sysDeptInfoService.queryDeptChildInfo(paramsMap);
        Map<String, Object> mapjson = new HashMap<>();
        mapjson.put("total", list.size());
        mapjson.put("rows", JSONObject.toJSON(list));
        Object json = JSONObject.toJSON(mapjson);
        return json;
    }

    /**
     * 编辑部门信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/sys/deptInfo"})
    @RequestMapping("/updDeptInfo")
    public Object updateDeptInfo(@Session(create = false) SessionProvider session,
                                 @RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        int dept_type = StringUtils.checkInt(paramsMap.get("dept_type"));
        if (3 != dept_type) {
            paramsMap.put("is_tj_man", "0");
            paramsMap.put("tj_ratio", "0");
        } else {
            int tj_ratio = StringUtils.checkInt(paramsMap.get("tj_ratio"));
            if (0 > tj_ratio || tj_ratio > 100) {
                msg.setMsg("编辑失败,09021824推荐人比率分配范围为：0 ~ 100 。");
                msg.setSuccess(false);
                return msg;
            }
        }
        try {
            LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
            dbLog.setMethod_name("编辑部门信息");
            dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
            dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
            paramsMap.put("create_date", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
            sysDeptInfoService.updateDeptInfo(paramsMap);

            //初始化代理商比例
            //sysDeptInfoService.initAgentAllotInfo(paramsMap);

            dbLog.setAction_message("成功,编辑后信息为部门编码:" + StringUtils.convertString(paramsMap.get("dept_code")) + "部门名称：" + StringUtils.convertString(paramsMap.get("dept_name")) + "是否可用：" + StringUtils.convertString(paramsMap.get("is_use")) + "分配比例：" + StringUtils.convertString(paramsMap.get("dept_ratio")) + "部门金额：" + StringUtils.convertString(paramsMap.get("dept_money")) + "联系电话" + StringUtils.convertString(paramsMap.get("dept_mobile")) + "微信id" + StringUtils.convertString(paramsMap.get("dept_app_id")) + "微信秘钥" + StringUtils.convertString(paramsMap.get("dept_app_secret")) + "是否有推荐人" + StringUtils.convertString(paramsMap.get("is_tj_man")) + "推荐人分成比例" + StringUtils.convertString(paramsMap.get("tj_ratio")));
            dbLog.setIs_ok(0);
            msg.setMsg("编辑成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            dbLog.setAction_message("失败");
            dbLog.setIs_ok(1);
            msg.setMsg("编辑失败,08111305");
            msg.setSuccess(false);
            e.printStackTrace();
        } finally {
            dbLogService.insertLog(dbLog);
        }
        return msg;
    }

    /**
     * 验证
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/deptCodeValidate")
    public Object deptCodeValidate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        msg.setSuccess(false);
        if (null == paramsMap.get("dept_code") || "".equals(paramsMap.get("dept_code"))) {
            return msg;
        }
        if (2 > paramsMap.get("dept_code").toString().length()) {
            return msg;
        }
        paramsMap.put("countBy", "codeOrName");
        Map<String, Object> count = sysDeptInfoService.countDeptInfo(paramsMap);
        int cou = StringUtils.checkInt(count.get("num"));
        //int cou = Integer.parseInt(count.get("num").toString());
        if (cou > 0) {
            msg.setSuccess(false);
        } else {
            msg.setSuccess(true);
        }
        return msg;
    }

    /**
     * 插入部门信息 dept_parent_id dept_type 父节点的值 dept_code dept_name 验证 必须
     * dept_money dept_mobile dept_parent_id 必须 dept_database dept_url可为空
     * dept_type dept_ratio 特殊
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/sys/deptInfo"})
    @RequestMapping("/insDeptInfo")
    public Object insertDeptInfo(@Session(create = false) SessionProvider session, @RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        int dept_type = StringUtils.checkInt(null == paramsMap.get("dept_type") ? "-1" : paramsMap.get("dept_type"));
        //int dept_type = Integer.parseInt(null == paramsMap.get("dept_type") ? "-1" : paramsMap.get("dept_type").toString());
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        try {
            dbLog.setMethod_name("新增部门信息");
            dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
            dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
            if (-1 == dept_type) {
                msg.setMsg("操作失败");
                msg.setSuccess(false);
                dbLog.setAction_message("操作失败");
                dbLog.setIs_ok(1);
                return msg;
            }

            paramsMap.put("countBy", "codeOrName");
            Map<String, Object> count = sysDeptInfoService.countDeptInfo(paramsMap);
            //int cou = Integer.parseInt(count.get("num").toString());
            int cou = StringUtils.checkInt(count.get("num"));
            if (cou > 0) {
                msg.setMsg("插入失败,08111003部门编码或名称已存在。");
                msg.setSuccess(false);
                dbLog.setAction_message("插入失败,08111003部门编码或名称已存在。");
                dbLog.setIs_ok(2);
                return msg;
            }
            paramsMap.remove("countBy");
            // dept_type dept_ratio
            // 平台 0 0
            // 交易中心 1 10A
            // 渠道部 2 10B
            // 中亿富融 3 20C < (100-10A-10B)
            // 财趣 4 20D =< (100-10A-10B-20C)
            // 部门一 5 0
            // 10A+10B+20C+20D =< 100
            /*
             * 已经分配了的比率
			 */
            if (dept_type >= 4) {
                dept_type = 5;
                paramsMap.put("dept_ratio", "0");
            } else {
                dept_type += 1;
            }
            paramsMap.put("dept_type", dept_type + "");
            paramsMap.put("is_tj_man",
                    paramsMap.get("is_tj_man") == null ? "0" : paramsMap.get("is_tj_man").toString());
            paramsMap.put("tj_ratio", paramsMap.get("tj_ratio") == null ? "0" : paramsMap.get("tj_ratio").toString());
            if (dept_type != 3) {
                paramsMap.put("is_tj_man", "0");
                paramsMap.put("tj_ratio", "0");
            } else {
                int tj_ratio = Integer.parseInt(paramsMap.get("tj_ratio").toString());
                if (0 > tj_ratio && tj_ratio > 100) {
                    msg.setMsg("插入失败,09021824推荐人比率分配范围为：0 ~ 100 。");
                    msg.setSuccess(false);
                    dbLog.setAction_message("插入失败,09021824推荐人比率分配范围为：0 ~ 100 。");
                    dbLog.setIs_ok(3);
                    return msg;
                }
            }
            paramsMap.put("is_use", paramsMap.get("is_use") == null ? "0" : paramsMap.get("is_use").toString());// 默认为1
            int dept_ratio = Integer.parseInt(paramsMap.get("dept_ratio").toString());
            if (dept_ratio != 0) {
                HashMap<String, Object> queryMap = new HashMap<String, Object>();
                queryMap.put("dept_id", paramsMap.get("dept_parent_id").toString());
                List<Map<String, Object>> pqrentList = sysDeptInfoService.queryDeptParentInfo(queryMap);
                int current_ratio = 0;
                for (Map<String, Object> map : pqrentList) {
                    current_ratio += Integer.parseInt(map.get("dept_ratio").toString());
                }

                if (dept_ratio > 100 - current_ratio) {
                    msg.setMsg("插入失败,08111033交易比率分配不得超过" + (100 - current_ratio) + "。");
                    msg.setSuccess(false);
                    dbLog.setAction_message("插入失败,08111033交易比率分配不得超过" + (100 - current_ratio) + "。");
                    dbLog.setIs_ok(4);
                    return msg;
                }
            }

            //增加dept_title
            paramsMap.put("dept_title", paramsMap.get("dept_title"));
            // 从0计数就不用加一 不再使用uuid
            paramsMap.put("dept_id", sysDeptInfoService.countDeptInfo(null).get("num1").toString());
            paramsMap.put("create_date", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
            sysDeptInfoService.insertDeptInfo(paramsMap);


            if (dept_type == 4) {
                /**
                 * 初始化一个部门
                 */
                //产生一条代理商比例记录
                sysDeptInfoService.initAgentAllotInfo(paramsMap);

                String code = RandomValidateCode.getRandomString(6);
                paramsMap.put("broker_incode", code);
                while (MapUtils.isNotEmpty(sysFrontBrokerService.queryBrokerCode(paramsMap))) {
                    code = RandomValidateCode.getRandomString(6);
                    paramsMap.put("broker_incode", code);
                }
                String dept_code = paramsMap.get("dept_code").toString();
                String dept_name = paramsMap.get("dept_name").toString();
                paramsMap.put("dept_parent_id", paramsMap.get("dept_id").toString());
                paramsMap.put("dept_name", dept_name + "-初始化部门");
                paramsMap.put("dept_code", dept_code + "-1");
                paramsMap.put("dept_ratio", "0");
                paramsMap.put("dept_money", "0");
                paramsMap.put("dept_mobile", "-1");
                paramsMap.put("dept_type", "5");
                paramsMap.remove("dept_app_id");
                paramsMap.remove("dept_app_secret");
                // paramsMap.put("dept_id", UUIDGenerator.generate());
                paramsMap.put("dept_id", sysDeptInfoService.countDeptInfo(null).get("num1").toString());
                paramsMap.put("broker_url",code);
                sysDeptInfoService.insertDeptInfo(paramsMap);
                /**
                 * 创建代理商 初始化一个顶级经纪人 t_front_broker
                 *
                 * broker_code 时间戳 broker_name 时间戳 broker_parent_id -1 dept_id
                 * 本id broker_incode 000000 is_use 1
                 */
                paramsMap.put("dept_code", dept_code);
                paramsMap.put("dept_name", dept_name);
                Map brokerMap = sysFrontBrokerService.insertFrontInitBroker(paramsMap);


            }
            if (dept_type == 4) {
                dbLog.setAction_message("新增成功,部门编码:" + StringUtils.convertString(paramsMap.get("dept_code")) + "部门名称：" + StringUtils.convertString(paramsMap.get("dept_name")) + "是否可用：" + StringUtils.convertString(paramsMap.get("is_use")) + "分配比例：" + StringUtils.convertString(paramsMap.get("dept_ratio")) + "部门金额：" + StringUtils.convertString(paramsMap.get("dept_money")) + "联系电话:" + StringUtils.convertString(paramsMap.get("dept_mobile")) + "微信id:" + StringUtils.convertString(paramsMap.get("dept_app_id")) + "微信秘钥:" + StringUtils.convertString(paramsMap.get("dept_app_secret")) + "是否有推荐人:" + StringUtils.convertString(paramsMap.get("is_tj_man")) + "推荐人分成比例:" + StringUtils.convertString(paramsMap.get("tj_ratio")));
                dbLog.setIs_ok(1);
                msg.setMsg("新增成功，请注意添加代理商初始分配比率模板");
            } else {
                dbLog.setAction_message("新增成功,部门编码:" + StringUtils.convertString(paramsMap.get("dept_code")) + "部门名称：" + StringUtils.convertString(paramsMap.get("dept_name")) + "是否可用：" + StringUtils.convertString(paramsMap.get("is_use")) + "分配比例：" + StringUtils.convertString(paramsMap.get("dept_ratio")) + "部门金额：" + StringUtils.convertString(paramsMap.get("dept_money")) + "联系电话:" + StringUtils.convertString(paramsMap.get("dept_mobile")) + "微信id:" + StringUtils.convertString(paramsMap.get("dept_app_id")) + "微信秘钥:" + StringUtils.convertString(paramsMap.get("dept_app_secret")) + "是否有推荐人:" + StringUtils.convertString(paramsMap.get("is_tj_man")) + "推荐人分成比例:" + StringUtils.convertString(paramsMap.get("tj_ratio")));
                dbLog.setIs_ok(1);
                msg.setMsg("新增成功");
            }
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("插入失败,08111304");
            msg.setSuccess(false);
            dbLog.setAction_message("插入失败,08111304");
            dbLog.setIs_ok(5);
            e.printStackTrace();
        } finally {
            dbLogService.insertLog(dbLog);
        }
        return msg;
    }


    /**
     * 获取代理商的父节点分配比率模板
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/sys/deptInfo"})
    @RequestMapping("/getDeptRatioModel")
    public Object getDeptRatioModel(HttpServletRequest request,
                                    @RequestParam HashMap<String, Object> paramsMap) {
        List<Map<String, Object>> DeptRatioModelList = sysDeptInfoService.queryDeptParentInfo(paramsMap);
        return DeptRatioModelList;
    }

    /**
     * 部门转部门操作
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/sys/deptInfo"})
    @RequestMapping("/saveODeptToNDeptInfo")
    public Object saveODeptToNDeptInfo(HttpServletRequest request,
                                       @RequestParam HashMap<String, Object> paramsMap) {

        boolean retstr = (boolean) sysDeptInfoService.savaOdeptToNdept(paramsMap);
        if (retstr) {
            msg.setSuccess(true);
            msg.setMsg("转移成功!");
        } else {
            msg.setSuccess(false);
            msg.setMsg("转移失败,请确认数据问题!");
        }
        return msg;
    }


    /**
     * 重置该代理下的所有user_wxid
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/sys/deptInfo"})
    @RequestMapping("/clearUserWeiXinInfo")
    public Object clearUserWeiXinInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                      @RequestParam HashMap<String, Object> paramsMap) {
        try {
            // 重置用户微信ID
            sysDeptInfoService.clearUserWeiXinInfo(paramsMap);
            msg.setMsg("操作成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("操作失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }

}
