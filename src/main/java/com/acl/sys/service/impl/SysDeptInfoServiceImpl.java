package com.acl.sys.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.acl.platform.service.IPlatformUserInfoService;
import com.acl.sys.dao.ISysDeptProrateTempDao;
import com.acl.utils.util.FormatDateUtil;
import com.acl.utils.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.pojo.DeptInfo;
import com.acl.sys.dao.SysDeptInfoDao;
import com.acl.sys.service.SysDeptInfoService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.StringUtils;
import com.alibaba.fastjson.JSONObject;

/**
 * className:SysDeptInfoServiceImpl
 * author:huangs
 * createDate:2016年8月10日 下午7:06:41
 * version:3.0
 * department:安创乐科技
 * description:部门管理
 */
@Service
@Transactional
public class SysDeptInfoServiceImpl implements SysDeptInfoService {
    @Autowired
    private SysDeptInfoDao sysDeptInfoDao;

    @Autowired
    private ISysDeptProrateTempDao sysDeptProrateTempDao;

    public List<Map<String, Object>> queryDeptInfo(HashMap<String, Object> hashMap) {
        return sysDeptInfoDao.queryDeptInfo(hashMap);
    }

    public void insertDeptInfo(HashMap<String, Object> hashMap) {
        sysDeptInfoDao.insertDeptInfo(hashMap);
        //mongodbBaseDao.save(hashMap, "t_back_dept_info");
    }

    @Override
    public Map<String, Object> countDeptInfo(HashMap<String, Object> hashMap) {
        return sysDeptInfoDao.countDeptInfo(hashMap);
    }

    @Override
    public List<Map<String, Object>> queryDeptParentInfo(HashMap<String, Object> hashMap) {
        return sysDeptInfoDao.queryDeptParentInfo(hashMap);
    }

    @Override
    public List<Map<String, Object>> queryDeptChildInfo(HashMap<String, Object> hashMap) {
        return sysDeptInfoDao.queryDeptChildInfo(hashMap);
    }

    @Override
    public List<DeptInfo> queryDeptChildInfoPojo(HashMap<String, Object> hashMap) {
        return sysDeptInfoDao.queryDeptChildInfoPojo(hashMap);
    }

    @Override
    public void updateDeptInfo(HashMap<String, Object> paramsMap) {
        String is_use = StringUtils.checkString(paramsMap.get("is_use"));
        if (is_use != null && is_use != "") {
            sysDeptInfoDao.updateDeptInfo(paramsMap);
            HashMap<String, Object> map = new HashMap<>();
            map.put("dept_id", paramsMap.get("dept_id").toString());
//            Update update = new Update();
//            update.set("create_date", paramsMap.get("create_date").toString());
//            update.set("dept_code", paramsMap.get("dept_code").toString());
//            update.set("dept_mobile", paramsMap.get("dept_mobile").toString());
//            update.set("dept_money", paramsMap.get("dept_money").toString());
//            update.set("dept_name", paramsMap.get("dept_name").toString());
//            update.set("dept_parent_id", paramsMap.get("dept_parent_id").toString());
//            update.set("dept_ratio", paramsMap.get("dept_ratio").toString());
//            update.set("dept_type", paramsMap.get("dept_type").toString());
//            update.set("dept_url", paramsMap.get("dept_url").toString());
//            update.set("broker_url", paramsMap.get("broker_url").toString());
//            update.set("is_tj_man", paramsMap.get("is_tj_man").toString());
//            update.set("tj_ratio", paramsMap.get("tj_ratio").toString());
//            update.set("is_use", paramsMap.get("is_use").toString());
//            update.set("dept_app_id", paramsMap.get("dept_app_id").toString());
//            update.set("dept_app_secret", paramsMap.get("dept_app_secret").toString());
//            update.set("dept_title", paramsMap.get("dept_title").toString());
            //mongodbBaseDao.updateFirst(map, update, "t_back_dept_info");
        } else {
            sysDeptInfoDao.updateDeptInfo(paramsMap);
        }
    }

    @Override
    public void queryDeptChildNodeInfo(HashMap<String, Object> paramsMap) {
        sysDeptInfoDao.queryDeptChildNodeInfo(paramsMap);

    }

    @Override
    public PageList<?> queryDeptMoneyInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return sysDeptInfoDao.queryDeptMoneyInfo(paramsMap, pageBounds);
    }

    @Override
    public List<HashMap<String, Object>> queryDeptMoneyCount(HashMap<String, Object> paramsMap) {
        // TODO Auto-generated method stub
        return sysDeptInfoDao.queryDeptMoneySum(paramsMap);
    }

    @SuppressWarnings("null")
    @Override
    public Object savaOdeptToNdept(HashMap<String, Object> hashMap) {

        boolean retstr = false;
        HashMap<String, Object> deptInfoList;
        List<Map<String, Object>> deptList = sysDeptInfoDao.queryOdeptOrNdeptInfo(hashMap);
        System.out.println(deptList);
        if (deptList.size() == 2) {
            hashMap.put("oPID", deptList.get(0).get("p_id").toString());
            hashMap.put("oCEID", deptList.get(0).get("c_id").toString());
            hashMap.put("oCHID", deptList.get(0).get("q_id").toString());
            hashMap.put("oSID", deptList.get(0).get("s_id").toString());
            hashMap.put("oAID", deptList.get(0).get("d_id").toString());
            hashMap.put("oDeptID", deptList.get(0).get("dept_id").toString());
            hashMap.put("oDeptCODE", deptList.get(0).get("d_code").toString());

            hashMap.put("nPID", deptList.get(1).get("p_id").toString());
            hashMap.put("nCEID", deptList.get(1).get("c_id").toString());
            hashMap.put("nCHID", deptList.get(1).get("q_id").toString());
            hashMap.put("nSID", deptList.get(1).get("s_id").toString());
            hashMap.put("nAID", deptList.get(1).get("d_id").toString());
            hashMap.put("nDeptID", deptList.get(1).get("dept_id").toString());
            hashMap.put("nDeptCODE", deptList.get(1).get("d_code").toString());

			 /*//{ p_name = 平台,
                    _code = rxjs,
					 d_code = rxdl,
					 dept_code = departtow,
					 d_name = 融信代理,
					 q_code = qudao,
					 c_code = jys,
					 dept_name = 融信二部,
					 q_name = 渠道,
					 p_code = pt,
					 c_name = 交易所,
					 d_id = 7,
					 q_id = 2,
					 dept_id = 13,
					 s_name = 融信结算,
					 c_id = 1,
					 s_id = 6,
					 p_id = 0 }]*/
            String oid = deptList.get(0).get("dept_id").toString();
            String ocode = deptList.get(0).get("d_code").toString();
            String nid = deptList.get(1).get("dept_id").toString();
            String ncode = deptList.get(1).get("d_code").toString();
            String nagent_id = deptList.get(1).get("d_id").toString();
            String nagent_code = deptList.get(1).get("d_code").toString();
            String nagent_name = deptList.get(1).get("d_name").toString();
            String nsettle_id = deptList.get(1).get("s_id").toString();
            String nsettle_name = deptList.get(1).get("s_name").toString();
            String ndept_name = deptList.get(1).get("dept_name").toString();
            //更新mongodb中的t_front_user_login中的agent_id,dept_id,
            //agent_code,agent_name,settle_id,settle_name,dept_name
            //更新mongodb中的t_front_broker中的dept_id,dept_code
            //调用存储过程
            sysDeptInfoDao.saveOdeptToNdeptInfo(hashMap);
            //更新t_front_user_login
            //Update updateUserLogin = Update.update("dept_id", nid).set("dept_code", ncode).set("agent_id", nagent_id)
            //       .set("agent_code", nagent_code).set("agent_name", nagent_name).set("settle_id", nsettle_id).set("settle_name", nsettle_name)
            //       .set("dept_name", ndept_name).set("user_wxid", "");
            //mongodbBaseDao.updateBeacthDeptInfo("user", oid, "", updateUserLogin, "t_front_user_login");
            //更新t_front_broker
            // Update updateBroker = Update.update("dept_id", nid).set("dept_code", ncode).set("broker_wx_id", "");
            //mongodbBaseDao.updateBeacthDeptInfo("broker", oid, "-1", updateBroker, "t_front_broker");

            //更新t_front_broker 顶级
            //Update updBroker = Update.update("is_use", "9");
            //mongodbBaseDao.updateBeacthDeptInfo("", nid, "-1", updBroker, "t_front_broker");
            retstr = true;

        } else {
            return retstr;
        }
        //System.out.println(hashMap);
        return retstr;
    }

    @Override
    public JSONObject queryQuitDeptInfo(HashMap<String, Object> hashMap) {
        JSONObject jObject = new JSONObject();
        String dept_id = hashMap.get("depts").toString();
        String order_date = hashMap.get("order_date").toString();

        HashMap<String, Object> infomap = new HashMap<String, Object>();
        infomap.put("dept_id", dept_id);
        infomap.put("order_date", order_date);
        //hashMap
        if (sysDeptInfoDao.queryQuitDeptInfo(infomap).equals("0")) {
            jObject.put("success", "true");
            jObject.put("dept_id", dept_id);

        } else {
            jObject.put("success", "false");
            jObject.put("dept_id", dept_id);
        }
        return jObject;
    }

    @Override
    public List<HashMap<String, Object>> queryDeptBalanceCount(HashMap<String, Object> paramsMap) {
        return sysDeptInfoDao.queryDeptBalanceCount(paramsMap);
    }

    @Override
    public List<HashMap<String, Object>> queryUserBalanceCount(HashMap<String, Object> paramsMap) {
        return sysDeptInfoDao.queryUserBalanceCount(paramsMap);
    }

    @Override
    public List<HashMap<String, Object>> queryBrokerBalanceCount(HashMap<String, Object> paramsMap) {
        return sysDeptInfoDao.queryBrokerBalanceCount(paramsMap);
    }

    @Override
    public PageList<?> queryDeptBalanceInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return sysDeptInfoDao.queryDeptBalanceInfo(paramsMap, pageBounds);
    }

    @Override
    public PageList<?> queryDeptUserBalanceInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return sysDeptInfoDao.queryDeptUserBalanceInfo(paramsMap, pageBounds);
    }

    @Override
    public PageList<?> queryDeptBrokerBalanceInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return sysDeptInfoDao.queryDeptBrokerBalanceInfo(paramsMap, pageBounds);
    }

    @Override
    public JSONObject clearDeptWeixinInfo(HashMap<String, Object> hashMap) {

        JSONObject jObject = new JSONObject();
        String dept_id = hashMap.get("dept_id").toString();
        String dept_type = hashMap.get("dept_id").toString();
        //更新t_front_broker
        //Update updateBroker = Update.update("broker_wx_id", "");
        //mongodbBaseDao.updateBeacthDeptInfo("broker", dept_id, "-1", updateBroker, "t_front_broker");

        //更新t_front_user_login
        //Update updateUserLogin = Update.update("user_wxid", "");
        //mongodbBaseDao.updateBeacthDeptInfo("user", dept_id, "", updateUserLogin, "t_front_user_login");

        jObject.put("dept_id", dept_id);
        jObject.put("dept_type", dept_type);
        jObject.put("success", "true");
        return jObject;
    }

    @Override
    public JSONObject settlementInfo(HashMap<String, Object> hashMap) {
        String dept_id = hashMap.get("dept_id").toString();
        String dept_type = hashMap.get("dept_id").toString();
        JSONObject jObject = new JSONObject();
        if (!"E".equals(sysDeptInfoDao.settlementInfo(hashMap))) {
            jObject.put("dept_id", dept_id);
            jObject.put("dept_type", dept_type);
            jObject.put("success", "true");
        } else {
            jObject.put("dept_id", dept_id);
            jObject.put("dept_type", dept_type);
            jObject.put("success", "false");
        }
        return jObject;
    }

    @Override
    public List<HashMap<String, Object>> queryDeptsInfo(HashMap<String, Object> paramsMap) {
        return sysDeptInfoDao.queryDeptsInfo(paramsMap);
    }

    @Override
    public void initAgentAllotInfo(HashMap<String, Object> paramsMap) {

        HashMap<String, Object> params = new HashMap<String, Object>();

        HashMap<String, Object> paramsInfo = new HashMap<String, Object>();

        params.put("a_id", paramsMap.get("dept_id"));

        paramsInfo.put("a_id", paramsMap.get("dept_id"));
        List<Map<String, Object>> prentList = sysDeptInfoDao.queryDeptParentInfo(paramsMap);

        if (prentList.size() > 0) {
            params.put("p_id", prentList.get(0).get("dept_id").toString());
            params.put("p_name", prentList.get(0).get("dept_name").toString());
            params.put("ce_id", prentList.get(1).get("dept_id").toString());
            params.put("ce_name", prentList.get(1).get("dept_name").toString());
            params.put("ch_id", prentList.get(2).get("dept_id").toString());
            params.put("ch_name", prentList.get(2).get("dept_name").toString());
            params.put("s_id", prentList.get(3).get("dept_id").toString());
            params.put("s_name", prentList.get(3).get("dept_name").toString());
            params.put("a_name", prentList.get(4).get("dept_name").toString());
            params.put("p_allot", "0");
//			params.put("ce_allot",prentList.get(4).get("ce_allot").toString());
//			params.put("ch_allot",prentList.get(4).get("ch_allot").toString());
//			params.put("s_allot",prentList.get(4).get("s_allot").toString());
            params.put("ce_allot", "0");
            params.put("ch_allot", "0");
            params.put("s_allot", "0");
            params.put("a_allot", "0");
            params.put("b_allot_1", "0");
            params.put("b_allot_2", "0");
            params.put("b_allot_3", "0");
            params.put("sys_id", UUIDGenerator.generate());
            params.put("create_date", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
        }
        //sysDeptProrateTempDao.queryAgentProrateTemp(params);
        if (sysDeptProrateTempDao.queryAgentProrateList(paramsInfo).size() > 0) {
            sysDeptProrateTempDao.updateAgentProrateInfo(params);
        } else {
            sysDeptProrateTempDao.insertAgentProrateTemp(params);
        }

    }

    @Override
    public void clearUserWeiXinInfo(HashMap<String, Object> paramsMap) {
        Map<String, Object> map = new HashMap<>();
        map.put("agent_id", paramsMap.get("agent_id").toString());
        //Update update = new Update();
        //update.set("user_wxid", "");
        //mongodbBaseDao.updateMultiInfo(paramsMap.get("agent_id").toString(), update, "t_front_user_login");
    }



    @Autowired
    private IPlatformUserInfoService platformUserInfoService;
    @Override
    public Object savaAgentToNdept(HashMap<String, Object> hashMap) {
        boolean retstr = false;
        List<Map<String, Object>> deptList = sysDeptInfoDao.queryOdeptOrNdeptInfo(hashMap);
        if (deptList.size() == 1) {
            hashMap.put("p_id", deptList.get(0).get("c_id").toString());
            hashMap.put("ce_id", deptList.get(0).get("q_id").toString());
            hashMap.put("ch_id", deptList.get(0).get("s_id").toString());
            hashMap.put("settle_id", deptList.get(0).get("d_id").toString());
            hashMap.put("agent_id", deptList.get(0).get("dept_id").toString());
            hashMap.put("dept_id", deptList.get(0).get("ddept_id").toString());
            hashMap.put("id",hashMap.get("user_id"));
            //hashMap.put("oDeptID", deptList.get(0).get("dept_id").toString());
            platformUserInfoService.savaAgentToNdept(hashMap);

            retstr = true;

        } else {
            return retstr;
        }
        return retstr;
    }


    public List<Map<String, Object>> queryDeptInfoByType(HashMap<String, Object> hashMap) {
        return sysDeptInfoDao.queryDeptInfoByType(hashMap);
    }

}


