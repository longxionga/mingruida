package com.acl.platform.service.impl;

import com.acl.platform.dao.IPlatformCompanyStaffDao;
import com.acl.platform.dao.IPlatformMachineOrderDao;
import com.acl.platform.service.IPlatformMachineOrderService;
import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class IPatformMachineOrderServiceImpl implements IPlatformMachineOrderService {

    @Autowired
    private IPlatformMachineOrderDao IPlatformMachineOrderDao;
    @Autowired
    private IPlatformCompanyStaffDao iPlatformCompanyStaffDao;

    @Override
    public PageList<?> queryMachineOrderPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds, LoginSession loginSession) {
        PageList<?>  stawalist = null ;
        //根据登陆账户所属员工信息过滤数据‘
        String loginname = loginSession.getUser_name();//登陆用户名
        String dept_ids =loginSession.getDept_id(); //登陆用户部门id
        String loginid = loginSession.getUser_id();//登陆用户id
        if (dept_ids!=null && (dept_ids.equals("0") || dept_ids.equals("3"))){//管理员和服务商权限
            stawalist=IPlatformMachineOrderDao.queryMachineOrderPageList(paramsMap, pageBounds);
        }else {

            Map<String,Object> ma= new HashMap<String, Object>();
            ma.put("loginid",loginid);
            PageList<?> pageList = new PageList<>();
            //根据登陆名称查询员工信息
            List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.findStaffInfoById(ma);
            if (staffmap!=null && staffmap.size()>0){
                String orgid = staffmap.get(0).get("id").toString();//员工id
                String ibrand_id = staffmap.get(0).get("brandid").toString();//品牌
                String branch = staffmap.get(0).get("branch").toString();//部门
                String position = staffmap.get(0).get("position").toString();//岗位

                if (position!=null && position.equals("1")){ //分公司经理
                    paramsMap.put("branch",branch);
                }else {
                    paramsMap.put("id","-1");
                }
            }else{//账号未绑定员工
                paramsMap.put("id","-1");
            }
            stawalist=IPlatformMachineOrderDao.queryMachineOrderPageList(paramsMap, pageBounds);
        }
        return stawalist;
    }

    @Override
    public List<Map<String, Object>> getOrderTypeCombobox(HashMap<String, Object> hashMap) {
        return IPlatformMachineOrderDao.getOrderTypeCombobox(hashMap);
    }

    @Override
    public List<Map<String, Object>> getPayTypeCombobox(HashMap<String, Object> hashMap) {
        return IPlatformMachineOrderDao.getPayTypeCombobox(hashMap);
    }
}