package com.acl.platform.service.impl;

import com.acl.platform.dao.IPlatformCompanyStaffDao;
import com.acl.platform.dao.IPlatformMachineInfoDao;
import com.acl.platform.dao.IPlatformMachineOrderDao;
import com.acl.platform.dao.IPlatformMerchatInfoDao;
import com.acl.platform.service.IPlatformCompanyStaffService;
import com.acl.platform.service.IPlatformExcelSerivce;
import com.acl.platform.service.IPlatformUserInfoService;
import com.acl.sys.dao.ISysOptDeptInfoDao;
import com.acl.sys.dao.SysUserInfoDao;
import com.acl.utils.excel.*;
import com.acl.utils.util.CollectionUtil;
import com.acl.utils.util.DateFormatUtil;
import com.acl.utils.util.FormatDateUtil;
import com.acl.utils.util.UUIDGenerator;
import com.alibaba.druid.sql.visitor.functions.If;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述:
 *
 * @author 胡锋
 * @create 2019-03-19 16:04
 */
@Service
public class IPlatformExcelSerivceImpl implements IPlatformExcelSerivce {


    @Autowired
    private IPlatformUserInfoService platformUserInfoService;

    @Autowired
    private IPlatformCompanyStaffDao iPlatformCompanyStaffDao;

    @Autowired
    private ISysOptDeptInfoDao iSysOptDeptInfoDao;

    @Autowired
    private IPlatformCompanyStaffService iPlatformCompanyStaffService;

    @Autowired
    private SysUserInfoDao sysUserInfoDao;

    @Autowired
    private IPlatformMachineInfoDao iPlatformMachineInfoDao;

    @Autowired
    private IPlatformMachineOrderDao iPlatformMachineOrderDao;

    @Autowired
    private IPlatformMerchatInfoDao iPlatformMerchatInfoDao;

    /**
     * 商户信息解析
     * @param brandId
     * @param destTemp
     */
    @Override
    public void shuaBaoMerchant(String brandId, File destTemp) {
        ParsingShuaBaoMExcelUtil parsingAgentExcelUtil = new ParsingShuaBaoMExcelUtil();
        BasicExcelDataEntity basicExcelDataEntity = parsingAgentExcelUtil.getParsingExcelResult2(destTemp);
        List<ShuaBaoInfo> shuaBaoInfoList = preShuaBaoBasicExcelToList(basicExcelDataEntity);
        if(CollectionUtil.isNotEmpty(shuaBaoInfoList)){
            List<Map> list = new ArrayList<>();
            for (int j=0;j<shuaBaoInfoList.size();j++){
                ShuaBaoInfo basicInfo =  shuaBaoInfoList.get(j);
                System.out.println(basicInfo);
                Map<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("id", UUIDGenerator.generate());
                paramsMap.put("merchant_code",basicInfo.getMerchantCode());
                paramsMap.put("merchant_name",basicInfo.getMerchantName());
                paramsMap.put("broker_code",basicInfo.getBrokerCode());
                paramsMap.put("broker_name",basicInfo.getBrokerName());
                paramsMap.put("merchant_type",basicInfo.getMerchantType());
                paramsMap.put("brand_id",brandId);
                paramsMap.put("machine_code",basicInfo.getMachineCode());
                paramsMap.put("machine_type",basicInfo.getMachineType());
                paramsMap.put("current_month_amount","".equals(basicInfo.getCurrentMonthAmount())?null:basicInfo.getCurrentMonthAmount());
                paramsMap.put("three_month_amount","".equals(basicInfo.getThreeMonthAmount())?null:basicInfo.getThreeMonthAmount());
                paramsMap.put("bind_time","".equals(basicInfo.getBindTime())?null:FormatDateUtil.StrToDate(basicInfo.getBindTime()));
                paramsMap.put("bind_day", "".equals(basicInfo.getBindTime())?null:DateFormatUtil.convertDate(FormatDateUtil.StrToDate(basicInfo.getBindTime()),"yyyyMMdd"));
                paramsMap.put("activation",basicInfo.getActivation());
                paramsMap.put("activation_notes",basicInfo.getActivationNotes());
                paramsMap.put("activation_type",basicInfo.getActivationType());
                paramsMap.put("create_time",new Date());
                paramsMap.put("create_day",DateFormatUtil.convertDate(new Date(),"yyyyMMdd"));
                //paramsMap.put("del_tag",);
                list.add(paramsMap);

            }
            Map m = new HashMap();
            m.put("itemMap",list);
            platformUserInfoService.insertDataMerchant(m);
        }
    }


    /**
     * 商户信息解析
     * @param brandId
     * @param destTemp
     */
    @Override
    public  void shuaBaoTradeOrder(String brandId, File destTemp) {
        ParsingShuaBaoMExcelUtil parsingAgentExcelUtil = new ParsingShuaBaoMExcelUtil();
        BasicExcelDataEntity basicExcelDataEntity = parsingAgentExcelUtil.getParsingExcelResult2(destTemp);
        List<ShuaBaoTradeOrderInfo> shuaBaoInfoList = preShuaBaoTradeOrderExcelToList(basicExcelDataEntity);
        if(CollectionUtil.isNotEmpty(shuaBaoInfoList)){
            List<Map> list = new ArrayList<>();
            for (int j=0;j<shuaBaoInfoList.size();j++){
                ShuaBaoTradeOrderInfo basicInfo =  shuaBaoInfoList.get(j);
                System.out.println(basicInfo);
                Map<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("id", UUIDGenerator.generate());
                paramsMap.put("order_id", basicInfo.getOrderId());
                paramsMap.put("merchant_code",basicInfo.getMerchantCode());
                paramsMap.put("merchant_name",basicInfo.getMerchantName());
                paramsMap.put("broker_code",basicInfo.getBrokerCode());
                paramsMap.put("broker_name",basicInfo.getBrokerName());
                paramsMap.put("merchant_type",basicInfo.getMerchantType());
                paramsMap.put("brand_id",brandId);
                paramsMap.put("machine_code",basicInfo.getMachineCode());
                paramsMap.put("machine_type",basicInfo.getMachineType());
                if("交易成功".equals(basicInfo.getOrderState())){
                    paramsMap.put("order_state", '1');
                    paramsMap.put("order_type","pay");
                }else if("交易失败".equals(basicInfo.getOrderState())){
                    paramsMap.put("order_state", '2');
                }else {
                    paramsMap.put("order_state", null);
                }
                if("打款成功".equals(basicInfo.getArrivalState())){
                    paramsMap.put("arrival_state",'1');
                }else if("打款失败".equals(basicInfo.getArrivalState())){
                    paramsMap.put("arrival_state",'2');
                }else if("未打款".equals(basicInfo.getArrivalState())){
                    paramsMap.put("arrival_state",'3');
                }else if("充值成功".equals(basicInfo.getArrivalState())){
                    paramsMap.put("arrival_state",'4');
                }else{
                    paramsMap.put("arrival_state",null);
                }

                paramsMap.put("trade_time", FormatDateUtil.StrToDate(basicInfo.getTradeTime()));
                paramsMap.put("trade_day", DateFormatUtil.convertDate(FormatDateUtil.StrToDate(basicInfo.getTradeTime()),"yyyyMMdd"));
                paramsMap.put("settle_type",basicInfo.getSettleType());
                paramsMap.put("pay_type",basicInfo.getPayType());
                paramsMap.put("pay_card_type",basicInfo.getPayCardType());
                paramsMap.put("pay_card_no",basicInfo.getPayCardNo());
                paramsMap.put("merchant_rate",basicInfo.getMerchantRate());
                paramsMap.put("total_amount",basicInfo.getTotalAmount());
                paramsMap.put("real_amount",basicInfo.getRealAmount());
                paramsMap.put("create_time",new Date());
                paramsMap.put("create_day",DateFormatUtil.convertDate(new Date(),"yyyyMMdd"));
                //paramsMap.put("del_tag",);
                list.add(paramsMap);
                //platformUserInfoService.insertDataTradeOrder(paramsMap);
            }
            Map m = new HashMap();
            m.put("itemMap",list);
            platformUserInfoService.insertDataTradeOrderAll(m);
        }
    }


    /**
     *
     * @param basicExcelDataEntity
     * @return
     */
    private  List<ShuaBaoInfo> preShuaBaoBasicExcelToList(BasicExcelDataEntity basicExcelDataEntity){
        Map<String,String> titleIndex = basicExcelDataEntity.getExcelTitleIndex();
        List<ShuaBaoInfo> basicInfoList = new ArrayList<>();
        System.out.println("titleIndex:"+titleIndex.toString());
        for(Map<String,String> map:basicExcelDataEntity.getExcelData()){
            System.out.println("excel:"+map.toString());
            ShuaBaoInfo basicInfo = new ShuaBaoInfo();
            for (String str:basicExcelDataEntity.getExcelTitleMap().keySet()){
                String colName = basicExcelDataEntity.getExcelTitleMap().get(str);
                if( colName.equals("商户编号")){
                    basicInfo.setMerchantCode(map.get(str));
                    continue;
                }
                if( colName.equals("商户名称")){
                    basicInfo.setMerchantName(map.get(str));
                    continue;
                }
                if( colName.equals("直属代理商编号")){
                    basicInfo.setBrokerCode(map.get(str));
                    continue;
                }

                if( colName.equals("直属代理商名称")){
                    basicInfo.setBrokerName(map.get(str));
                    continue;
                }
                if(colName.equals("商户类型")){
                    basicInfo.setMerchantType(map.get(str));
                    continue;
                }
                if( colName.equals("机具类型")){
                    basicInfo.setMachineType(map.get(str));
                    continue;
                }
                if( colName.equals("机具编号")){
                    basicInfo.setMachineCode(map.get(str));
                    continue;
                }
                if( colName.equals("当前月交易金额")){
                    basicInfo.setCurrentMonthAmount(map.get(str));
                    continue;
                }
                if( colName.equals("近三个月交易金额")){
                    basicInfo.setThreeMonthAmount(map.get(str));
                    continue;
                }
                if( colName.equals("绑定时间")){
                    basicInfo.setBindTime(map.get(str));
                    continue;
                }
                if( colName.equals("是否激活")){
                    basicInfo.setActivation(map.get(str));
                    continue;
                }
                if( colName.equals("未激活原因")){
                    basicInfo.setActivationNotes(map.get(str));
                    continue;
                }
                if( colName.equals("激活类型")){
                    basicInfo.setActivationType(map.get(str));
                    continue;
                }
            }

            if(org.apache.commons.lang3.StringUtils.isEmpty(basicInfo.getMachineCode())
                    || org.apache.commons.lang3.StringUtils.isEmpty(basicInfo.getMerchantCode())){
                System.out.println("数据不完整,跳过处理:"+basicInfo.toString());
                continue;
            }
            basicInfoList.add(basicInfo);
        }
        return basicInfoList;
    }



    /**
     *
     //
     //
     //    CREATED("CREATED","创建"),
     //    PAY_WAIT("PAY_WAIT","支付等待"),
     //    PAY_SUCCESS("PAY_SUCCESS","支付成功"),
     //    PAY_FAILED("PAY_FAILED","支付失败"),
     //    ORDER_REFUND_WAIT("ORDER_REFUND_WAIT","退款中"),
     //    ORDER_REFUND_ALL("ORDER_REFUND_ALL","全额退款"),
     //    ORDER_REFUND_PART("ORDER_REFUND_PART","全额退款"),
     //    ORDER_REFUND_FAILED("ORDER_REFUND_FAILED","退款失败"),
     //    CLOSED("ORDER_CLOSED","订单关闭"),
     //    ORDER_REPEAL("ORDER_REPEAL","撤销中");
     * @param basicExcelDataEntity
     * @return
     */
    private  List<ShuaBaoTradeOrderInfo> preShuaBaoTradeOrderExcelToList(BasicExcelDataEntity basicExcelDataEntity){
        Map<String,String> titleIndex = basicExcelDataEntity.getExcelTitleIndex();
        List<ShuaBaoTradeOrderInfo> basicInfoList = new ArrayList<>();
        System.out.println("titleIndex:"+titleIndex.toString());
        for(Map<String,String> map:basicExcelDataEntity.getExcelData()){
            System.out.println("excel:"+map.toString());
            ShuaBaoTradeOrderInfo basicInfo = new ShuaBaoTradeOrderInfo();
            for (String str:basicExcelDataEntity.getExcelTitleMap().keySet()){
                String colName = basicExcelDataEntity.getExcelTitleMap().get(str);

                if( colName.equals("交易日期")){
                    basicInfo.setTradeTime(map.get(str));
                    continue;
                }

                if( colName.equals("交易订单号")){
                    basicInfo.setOrderId(map.get(str));
                    continue;
                }

                if( colName.equals("结算类型")){
                    basicInfo.setSettleType(map.get(str));
                    continue;
                }

                if( colName.equals("商户编号")){
                    basicInfo.setMerchantCode(map.get(str));
                    continue;
                }
                if( colName.equals("商户名称")){
                    basicInfo.setMerchantName(map.get(str));
                    continue;
                }
                if( colName.equals("商户直属代理编号编号")){
                    basicInfo.setBrokerCode(map.get(str));
                    continue;
                }

                if( colName.equals("商户直属代理编号名称")){
                    basicInfo.setBrokerName(map.get(str));
                    continue;
                }
                if(colName.equals("支付类型")){
                    basicInfo.setPayType(map.get(str));
                    continue;
                }
                if( colName.equals("支付卡类型")){
                    basicInfo.setPayCardType(map.get(str));
                    continue;
                }
                if( colName.equals("机具SN")){
                    basicInfo.setMachineCode(map.get(str));
                    continue;
                }
                if( colName.equals("银行卡号")){
                    basicInfo.setPayCardNo(map.get(str));
                    continue;
                }
                if( colName.equals("商户费率(%)")){
                    basicInfo.setMerchantRate(map.get(str));
                    continue;
                }
                if( colName.equals("交易金额")){
                    basicInfo.setTotalAmount(map.get(str));
                    continue;
                }
                if( colName.equals("到账金额")){
                    basicInfo.setRealAmount(map.get(str));
                    continue;
                }
                if( colName.equals("到账状态")){
                    basicInfo.setArrivalState(map.get(str));
                    continue;
                }
                if( colName.equals("交易状态")){
                    basicInfo.setOrderState(map.get(str));
                    continue;
                }

            }

            if(org.apache.commons.lang3.StringUtils.isEmpty(basicInfo.getMerchantCode())
                    || org.apache.commons.lang3.StringUtils.isEmpty(basicInfo.getMerchantCode())){
                System.out.println("数据不完整,跳过处理:"+basicInfo.toString());
                continue;
            }
            basicInfoList.add(basicInfo);
        }
        return basicInfoList;
    }

    /**
     * 机具信息解析
     * @param brandId
     * @param destTemp
     */
    @Override
    public  void shuaBaoMachine(String brandId, File destTemp) {
        ParsingShuaBaoMExcelUtil parsingAgentExcelUtil = new ParsingShuaBaoMExcelUtil();
        BasicExcelDataEntity basicExcelDataEntity = parsingAgentExcelUtil.getParsingExcelResult(destTemp);
        List<MachineInfoInfo> shuaBaoInfoList = preShuaBaoMachineExcelToList(basicExcelDataEntity);
        if(CollectionUtil.isNotEmpty(shuaBaoInfoList)){
            List<Map> list = new ArrayList<>();
            for (int j=0;j<shuaBaoInfoList.size();j++){
                MachineInfoInfo basicInfo =  shuaBaoInfoList.get(j);
                System.out.println(basicInfo);
                Map<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("id", UUIDGenerator.generate());
                paramsMap.put("machineSN", basicInfo.getMachineSN());
                paramsMap.put("merchantcode",basicInfo.getMerchantcode());
                paramsMap.put("attagentcode",basicInfo.getAttagentcode());

                if ("已绑定".equals(basicInfo.getIsbound())){
                    paramsMap.put("isbound","1");
                }else if ("未绑定".equals(basicInfo.getIsbound())){
                    paramsMap.put("isbound","2");
                }else{
                    paramsMap.put("isbound","9");//其他
                }
                paramsMap.put("machinetype",basicInfo.getMachinetype());
                paramsMap.put("machinemodel",basicInfo.getMachinemodel());
                paramsMap.put("warehousingtype",basicInfo.getWarehousingtype());
                paramsMap.put("vipstatus",basicInfo.getVipstatus());
                paramsMap.put("attagentname",basicInfo.getAttagentname());

                paramsMap.put("merchantname", basicInfo.getMerchantname());
                paramsMap.put("machineryT1rate","".equals(basicInfo.getMachineryT1rate())?null:basicInfo.getMachineryT1rate());
                paramsMap.put("machineryT0rate","".equals(basicInfo.getMachineryT0rate())?null:basicInfo.getMachineryT0rate());
                paramsMap.put("machineryamount","".equals(basicInfo.getMachineryamount())?null:basicInfo.getMachineryamount());
                paramsMap.put("companyid",basicInfo.getStaffid());
                paramsMap.put("brindnameid",brandId);

                paramsMap.put("create_time",new Date());
                paramsMap.put("create_day",DateFormatUtil.convertDate(new Date(),"yyyyMMdd"));
                //paramsMap.put("del_tag",);
                list.add(paramsMap);
                //platformUserInfoService.insertDataTradeOrder(paramsMap);
            }
            Map m = new HashMap();
            m.put("itemMap",list);
            platformUserInfoService.insertDataMachineInfoAll(m);
        }
    }

    private  List<MachineInfoInfo> preShuaBaoMachineExcelToList(BasicExcelDataEntity basicExcelDataEntity){
        Map<String,String> titleIndex = basicExcelDataEntity.getExcelTitleIndex();
        List<MachineInfoInfo> basicInfoList = new ArrayList<>();
        System.out.println("titleIndex:"+titleIndex.toString());
        for(Map<String,String> map:basicExcelDataEntity.getExcelData()){
            System.out.println("excel:"+map.toString());
            MachineInfoInfo basicInfo = new MachineInfoInfo();
            for (String str:basicExcelDataEntity.getExcelTitleMap().keySet()){
                String colName = basicExcelDataEntity.getExcelTitleMap().get(str);

                if( colName.equals("机具SN")){
                    basicInfo.setMachineSN(map.get(str));
                    continue;
                }

                if( colName.equals("商户编号")){
                    basicInfo.setMerchantcode(map.get(str));
                    continue;
                }

                if( colName.equals("归属代理商编号")){
                    basicInfo.setAttagentcode(map.get(str));
                    Map paramap = new HashMap();
                   // paramap.put("agentname",map.get(str));
                    paramap.put("agentcode",map.get(str));
                    List<Map<String, Object>> list=	iPlatformCompanyStaffDao.findConpanyStaffByName(paramap);
                    if (list!=null && list.size()>0){
                        basicInfo.setStaffid(list.get(0).get("id").toString());
                    }else{
                        basicInfo.setStaffid("");
                    }

                    continue;
                }

                if( colName.equals("是否绑定")){
                    basicInfo.setIsbound(map.get(str));
                    continue;
                }
                if( colName.equals("机具类型")){
                    basicInfo.setMachinetype(map.get(str));
                    continue;
                }
                if( colName.equals("机具型号")){
                    basicInfo.setMachinemodel(map.get(str));
                    continue;
                }

                if( colName.equals("激活类型")){
                    basicInfo.setWarehousingtype(map.get(str));
                    continue;
                }
                if(colName.equals("VIP购买状态")){
                    basicInfo.setVipstatus(map.get(str));
                    continue;
                }
                if( colName.equals("归属代理商名称")){
                    basicInfo.setAttagentname(map.get(str));

                    continue;
                }
                if( colName.equals("商户名称")){
                    basicInfo.setMerchantname(map.get(str));
                    continue;
                }
                if( colName.equals("机具T1费率(%)")){
                    basicInfo.setMachineryT1rate(map.get(str));
                    continue;
                }
                if( colName.equals("机具T0费率(%)")){
                    basicInfo.setMachineryT0rate(map.get(str));
                    continue;
                }
                if( colName.equals("机具提现费(元)")){
                    basicInfo.setMachineryamount(map.get(str));
                    continue;
                }


            }

            if(org.apache.commons.lang3.StringUtils.isEmpty(basicInfo.getMachineSN())
                    || org.apache.commons.lang3.StringUtils.isEmpty(basicInfo.getMachineSN())){
                System.out.println("数据不完整,跳过处理:"+basicInfo.toString());
                continue;
            }
            basicInfoList.add(basicInfo);
        }
        return basicInfoList;
    }

    @Override
    public void haikeStaffImport(String brandId,File file)  throws Exception {
        ParsingShuaBaoMExcelUtil parsingAgentExcelUtil = new ParsingShuaBaoMExcelUtil();
        BasicExcelDataEntity basicExcelDataEntity = parsingAgentExcelUtil.getParsingExcelResult2(file);
        List<CompanyStaffInfo> haiKeInfoList = CompanyStaffExcelToList(basicExcelDataEntity);
        if(CollectionUtil.isNotEmpty(haiKeInfoList)){
            List<Map> list = new ArrayList<>();
            for (int j=0;j<haiKeInfoList.size();j++){
                CompanyStaffInfo companyStaffInfo =  haiKeInfoList.get(j);
                System.out.println(companyStaffInfo);
                Map<String, Object> paramsMap = new HashMap<>();
                String staffid =  UUIDGenerator.generate();
                paramsMap.put("id",staffid);
                paramsMap.put("parentid", companyStaffInfo.getParentid());
                paramsMap.put("staffname", companyStaffInfo.getStaffname());
                paramsMap.put("staffcode", companyStaffInfo.getStaffcode());
                paramsMap.put("agentcode",companyStaffInfo.getAgentcode());
                paramsMap.put("agentname",companyStaffInfo.getAgentname());
                paramsMap.put("phoneNO",companyStaffInfo.getPhoneNO());
                paramsMap.put("staffstate",'1');//员工状态正常
                paramsMap.put("startdate",companyStaffInfo.getStartdate()== null || "".equals(companyStaffInfo.getStartdate()) ?null:FormatDateUtil.StrToDate3(companyStaffInfo.getStartdate()));
                paramsMap.put("enddate",companyStaffInfo.getEnddate()==null || "".equals(companyStaffInfo.getEnddate())?null:FormatDateUtil.StrToDate3(companyStaffInfo.getEnddate()));
                paramsMap.put("position", companyStaffInfo.getPosition());
                paramsMap.put("branch", companyStaffInfo.getBranch());
                paramsMap.put("remarks", companyStaffInfo.getRemarks());
                paramsMap.put("channel", '1');//渠道默认公司
                paramsMap.put("brandid", "10");
             //   paramsMap.put("bystages", companyStaffInfo.getFenqi());
                paramsMap.put("isstate", "1");

              //登陆账户生成规则 经理主管默认生成系统登陆账户，账户生成规则 所属分公司+员工名称
                String position = companyStaffInfo.getPosition();
                String logincode = "";
                String loginuserid= "";
                if (companyStaffInfo.getLogincode()==null || "".equals(companyStaffInfo.getLogincode())){
                    HashMap<String,Object> map = new HashMap<>();

                    if (position!=null){
                        map.put("phoneNO",companyStaffInfo.getPhoneNO());//手机号
                        map.put("branch",companyStaffInfo.getBranch());//公司
                        map.put("staffname",companyStaffInfo.getStaffname());//员工姓名
                        if (position.equals("1")){//经理
                            String name = companyStaffInfo.getBranchname().split("-")[0];
                            logincode = name +"-"+ companyStaffInfo.getStaffname();

                            map.put("name",logincode);
                            List<Map<String, Object>> userlist = sysUserInfoDao.userValidate(map);
                            if (userlist!=null && userlist.size()>0){
                                String staffcode =  new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()));
                                logincode=logincode+staffcode.substring(staffcode.length()-3);

                            }
                            map.put("logincode",logincode);
                            //生成登陆账户
                            loginuserid= iPlatformCompanyStaffService.foundUserbyStaff(map,position );
                        }
                        if (position.equals("2")){//主管

                            String name = companyStaffInfo.getBranchname().split("-")[0];
                            //生成登陆账户
                            logincode = name +"-"+ companyStaffInfo.getStaffname();

                            map.put("name",logincode);
                            List<Map<String, Object>> userlist = sysUserInfoDao.userValidate(map);
                            if (userlist!=null && userlist.size()>0){
                                String staffcode =  new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()));
                                logincode=logincode+staffcode.substring(staffcode.length()-3);

                            }
                            map.put("logincode",logincode);
                            //生成登陆账户
                            loginuserid=    iPlatformCompanyStaffService.foundUserbyStaff(map,position);
                        }
                        if (position.equals("3") || position.equals("5") || position.equals("6")){//组员

                            String name = companyStaffInfo.getBranchname().split("-")[0];
                            //生成登陆账户
                            logincode = name +"-"+ companyStaffInfo.getStaffname();
                            map.put("name",logincode);
                            List<Map<String, Object>> userlist = sysUserInfoDao.userValidate(map);
                            if (userlist!=null && userlist.size()>0){
                                String staffcode =  new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()));
                                logincode=logincode+staffcode.substring(staffcode.length()-3);

                            }
                            //生成登陆账户
                            map.put("logincode",logincode);
                            loginuserid=   iPlatformCompanyStaffService.foundUserbyStaff(map,position);
                        }
                    }

                }else {
                    HashMap<String,Object> map = new HashMap<>();

                    if (position!=null){
                        map.put("phoneNO",companyStaffInfo.getPhoneNO());//手机号
                        map.put("branch",companyStaffInfo.getBranch());//公司
                        map.put("staffname",companyStaffInfo.getStaffname());//员工姓名
                        if (position.equals("1")){//经理
                            logincode = companyStaffInfo.getLogincode();
                            map.put("name",logincode);
                            List<Map<String, Object>> userlist = sysUserInfoDao.userValidate(map);
                            if (userlist!=null && userlist.size()>0){
                                String staffcode =  new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()));
                                logincode=logincode+staffcode.substring(staffcode.length()-3);

                            }
                            map.put("logincode",logincode);
                            //生成登陆账户
                            loginuserid=  iPlatformCompanyStaffService.foundUserbyStaff(map,position);
                        }
                        if (position.equals("2")){//主管

                            //生成登陆账户
                            logincode = companyStaffInfo.getLogincode();
                            map.put("name",logincode);
                            sysUserInfoDao.userValidate(map);
                            //生成登陆账户
                            loginuserid=   iPlatformCompanyStaffService.foundUserbyStaff(map,position);
                        }
                        if (position.equals("3") || position.equals("5") || position.equals("6")){//组员

                            String name = companyStaffInfo.getBranchname().split("-")[0];
                            //生成登陆账户
                            logincode = name +"-"+ companyStaffInfo.getStaffname();
                            map.put("name",logincode);
                            List<Map<String, Object>> userlist = sysUserInfoDao.userValidate(map);
                            if (userlist!=null && userlist.size()>0){
                                String staffcode =  new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()));
                                logincode=logincode+staffcode.substring(staffcode.length()-3);

                            }
                            map.put("logincode",logincode);
                            //生成登陆账户
                            loginuserid=  iPlatformCompanyStaffService.foundUserbyStaff(map,position);
                        }
                    }
                }

                paramsMap.put("loginuserid",loginuserid);
                paramsMap.put("logincode",logincode);
                list.add(paramsMap);

            }
            Map m = new HashMap();
            m.put("itemMap",list);
            iPlatformCompanyStaffDao.insertCompanyStaff(m);
        }
    }

    /**
     *  员工信息解析
     * @param basicExcelDataEntity
     * @return
     */
    private  List<CompanyStaffInfo> CompanyStaffExcelToList(BasicExcelDataEntity basicExcelDataEntity){
        Map<String,String> titleIndex = basicExcelDataEntity.getExcelTitleIndex();
        //sysBackDictInfoDao.
        List<CompanyStaffInfo> basicInfoList = new ArrayList<>();
        System.out.println("titleIndex:"+titleIndex.toString());
        for(Map<String,String> map:basicExcelDataEntity.getExcelData()){
            System.out.println("excel:"+map.toString());
            CompanyStaffInfo companyStaffInfo = new CompanyStaffInfo();
            Map paramap1 = new HashMap();
            for (String str:basicExcelDataEntity.getExcelTitleMap().keySet()){
                String colName = basicExcelDataEntity.getExcelTitleMap().get(str);

                if( colName.equals("渠道")){
                    if (map.get(str)!=null && !"".equals(map.get(str))){
                        if (map.get(str).equals("公司")){
                            companyStaffInfo.setChannel("1");
                        }else if (map.get(str).equals("个人")){
                            companyStaffInfo.setChannel("2");
                        }
                        else if (map.get(str).equals("其他")){
                            companyStaffInfo.setChannel("3");
                        }else {
                            companyStaffInfo.setChannel("3");
                        }
                    }
                    continue;
                }
                if( colName.equals("所属分公司")){

                    HashMap<String,Object> deptmap = new HashMap();
                    deptmap.put("dept_name2",map.get(str));
                    companyStaffInfo.setBranchname(map.get(str));
                    List<Map<String, Object>>  li=   iSysOptDeptInfoDao.findDeptInfoByDeptName(deptmap);

                    if (li!=null && li.size()>0){
                        String dept_id = li.get(0).get("dept_id").toString();
                        companyStaffInfo.setBranch(dept_id);
                        paramap1.put("branch",dept_id);
                    }else {
                        companyStaffInfo.setBranch("");
                    }

                    continue;
                }
                if( colName.equals("代理商编号")){
                    companyStaffInfo.setAgentcode(map.get(str));
                    continue;
                }

                if( colName.equals("代理商名称")){
                    companyStaffInfo.setAgentname(map.get(str));
                    continue;
                }
                if(colName.equals("员工姓名")){
                    companyStaffInfo.setStaffname(map.get(str));
                    continue;
                }
                if( colName.equals("上级员工")){
                    if (map.get(str)!=null && !"".equals(map.get(str))){
                        //Map paramap = new HashMap();
                        paramap1.put("name",map.get(str));
                        paramap1.put("brandid","10");

                        List<Map<String, Object>> list=	iPlatformCompanyStaffDao.findConpanyStaffByName(paramap1);
                        if(list!=null && list.size()>0){
                            companyStaffInfo.setParentid(list.get(0).get("id").toString());
                        }

                    }
                }
                if( colName.equals("岗位")){
                    if (map.get(str)!=null && !"".equals(map.get(str))){
                        if ("经理".equals(map.get(str))){
                            companyStaffInfo.setPosition("1");
                        }else if ("主管".equals(map.get(str))){
                            companyStaffInfo.setPosition("2");
                        }else if ("组员".equals(map.get(str))){
                            companyStaffInfo.setPosition("3");
                        }else if ("人事".equals(map.get(str))){
                            companyStaffInfo.setPosition("4");
                        }else if ("实习主管".equals(map.get(str))){
                            companyStaffInfo.setPosition("5");
                        }else if ("实习组员".equals(map.get(str))){
                            companyStaffInfo.setPosition("6");
                        }else if ("其他".equals(map.get(str))){
                            companyStaffInfo.setPosition("9");
                        }else {
                            companyStaffInfo.setPosition("9");
                        }
                    }
                    continue;
                }
                if( colName.equals("手机号")){
                    companyStaffInfo.setPhoneNO(map.get(str));
                    continue;
                }
                if( colName.equals("身份证号")){
                    companyStaffInfo.setStaffcode(map.get(str));
                    continue;
                }
                if( colName.equals("状态")){
                    if (map.get(str)!=null && !"".equals(map.get(str))){
                        if ("在职".equals(map.get(str))){
                            companyStaffInfo.setStaffstate("1");
                        }else if ("离职".equals(map.get(str))){
                            companyStaffInfo.setStaffstate("2");
                        }else if ("其他".equals(map.get(str))){
                            companyStaffInfo.setStaffstate("9");
                        }else {
                            companyStaffInfo.setStaffstate("9");
                        }
                    }
                    continue;
                }
                if( colName.equals("入职时间")){
                    companyStaffInfo.setStartdate(map.get(str));
                    continue;
                }
                if( colName.equals("离职时间")){
                    companyStaffInfo.setEnddate(map.get(str));
                    continue;
                }
//                if( colName.equals("分期")){
//
//                    if (map.get(str)!=null && !"".equals(map.get(str))){
//                        if ("一期".equals(map.get(str))){
//                            companyStaffInfo.setFenqi("1");
//                        }else if ("二期".equals(map.get(str))){
//                            companyStaffInfo.setFenqi("2");
//                        }else if ("三期".equals(map.get(str))){
//                            companyStaffInfo.setFenqi("3");
//                        }else {
//                            companyStaffInfo.setFenqi("9");
//                        }
//                    }
//                    continue;
//
//                }

                if( colName.equals("员工系统登陆账户")){
                    companyStaffInfo.setLogincode(map.get(str));
                    continue;
                }
            }

			/*if(org.apache.commons.lang3.StringUtils.isEmpty(basicInfo.getMachineCode())
					|| org.apache.commons.lang3.StringUtils.isEmpty(basicInfo.getMerchantCode())){
				System.out.println("数据不完整,跳过处理:"+basicInfo.toString());
				continue;
			}*/
            basicInfoList.add(companyStaffInfo);
        }
        return basicInfoList;
    }

    /**
     * 海科机具信息解析
     * @param brandId
     * @param destTemp
     */
    @Override
    public  void haikeMachine(String brandId, File destTemp) {
        ParsingShuaBaoMExcelUtil parsingAgentExcelUtil = new ParsingShuaBaoMExcelUtil();
        BasicExcelDataEntity basicExcelDataEntity = parsingAgentExcelUtil.getParsingExcelResult(destTemp);
        List<MachineInfoInfo> haikeInfoList = preHaikeMachineExcelToList(basicExcelDataEntity);
        if(CollectionUtil.isNotEmpty(haikeInfoList)){
            List<Map> list = new ArrayList<>();
            List<Map> uplist = new ArrayList<>();

            for (int j=0;j<haikeInfoList.size();j++){
                MachineInfoInfo basicInfo =  haikeInfoList.get(j);
                String machinecode = basicInfo.getMachinecode() ;
                Map pm = new HashMap();
                pm.put("machinecode",machinecode);

                List<Map<String, Object>> mlist = iPlatformMachineInfoDao.findMachineById(pm);

                if (mlist!=null && mlist.size()==1){ //已存在更新信息
                    Map<String, Object> upMap = new HashMap<>();
                    String id = mlist.get(0).get("id").toString();
                    upMap.put("id",id);

                    if ("已绑定".equals(basicInfo.getIsbound())){
                        upMap.put("isbound","1");
                    }else if ("未绑定".equals(basicInfo.getIsbound())){
                        upMap.put("isbound","2");
                    }else if ("已分配服务商".equals(basicInfo.getIsbound())){
                        upMap.put("isbound","3");
                    }else{
                        upMap.put("isbound","9");//其他
                    }

                    upMap.put("merchantcode",basicInfo.getMerchantcode());
                    upMap.put("merchantname", basicInfo.getMerchantname());
                    upMap.put("allocationnum", basicInfo.getAllocationnum());
                    upMap.put("ifdeposit", basicInfo.getIfdeposit());
                    upMap.put("policyident", basicInfo.getPolicyident());
                    upMap.put("machineident", basicInfo.getMachineident());
                    upMap.put("dotcode", basicInfo.getDotcode());
                    upMap.put("dotname", basicInfo.getDotname());
                    upMap.put("isencryption", basicInfo.getIsencryption());

                    upMap.put("update_time",new Date());
                    uplist.add(upMap);
                }else if (mlist.size()>1){
                    continue;
                }else {  //新增机具信息  // 默认都放在总后台
                    System.out.println(basicInfo);
                    Map<String, Object> paramsMap = new HashMap<>();
                    Map<String, Object> sMap = new HashMap<>();
                    sMap.put("staffname","铭锐达");
                    String staffid = "";
                    List<Map<String, Object>> slist =iPlatformCompanyStaffDao.findStaffInfoById(sMap);
                    if (slist!=null && slist.size()==1){
                        staffid= slist.get(0).get("id").toString();
                    }
                    paramsMap.put("staffid",staffid);
                    paramsMap.put("id", UUIDGenerator.generate());
                    paramsMap.put("machineSN", basicInfo.getMachineSN());
                    paramsMap.put("machinecode", basicInfo.getMachinecode());
                    paramsMap.put("batchcode", basicInfo.getBatchcode());
                    if ("已绑定".equals(basicInfo.getIsbound())){
                        paramsMap.put("isbound","1");
                    }else if ("未绑定".equals(basicInfo.getIsbound())){
                        paramsMap.put("isbound","2");
                    }else if ("已分配服务商".equals(basicInfo.getIsbound())){
                        paramsMap.put("isbound","3");
                    }else{
                        paramsMap.put("isbound","9");//其他
                    }
                    paramsMap.put("machinetype",basicInfo.getMachinetype());
                    paramsMap.put("attagentcode",basicInfo.getAttagentcode());
                    paramsMap.put("attagentname",basicInfo.getAttagentname());
                    paramsMap.put("perattagentname",basicInfo.getPerattagentname());

                    paramsMap.put("merchantcode",basicInfo.getMerchantcode());
                    paramsMap.put("merchantname", basicInfo.getMerchantname());
                    paramsMap.put("dotcode", basicInfo.getDotcode());
                    paramsMap.put("dotname", basicInfo.getDotname());
                    paramsMap.put("isencryption", basicInfo.getIsencryption());
                    paramsMap.put("machineident", basicInfo.getMachineident());
                    paramsMap.put("policyident", basicInfo.getPolicyident());
                    paramsMap.put("allocationnum", basicInfo.getAllocationnum());
                    paramsMap.put("ifdeposit", basicInfo.getIfdeposit());

                    paramsMap.put("brindid",brandId);

                    paramsMap.put("create_time",new Date());
                    paramsMap.put("create_day",DateFormatUtil.convertDate(new Date(),"yyyyMMdd"));
                    list.add(paramsMap);
                }

            }
            Map m = new HashMap();
            if (list!=null && list.size()>0){
                m.put("itemMap",list);
                platformUserInfoService.insertDataHKMachineInfoAll(m);
            }

            if (uplist!=null && uplist.size()>0){
                for (Map map : uplist) {
                    iPlatformMachineInfoDao.updateMachineinfo_2(map);
                }
            }

        }
    }


    private  List<MachineInfoInfo> preHaikeMachineExcelToList(BasicExcelDataEntity basicExcelDataEntity){
        Map<String,String> titleIndex = basicExcelDataEntity.getExcelTitleIndex();
        List<MachineInfoInfo> basicInfoList = new ArrayList<>();
        System.out.println("titleIndex:"+titleIndex.toString());
        for(Map<String,String> map:basicExcelDataEntity.getExcelData()){
            System.out.println("excel:"+map.toString());
            MachineInfoInfo basicInfo = new MachineInfoInfo();
            for (String str:basicExcelDataEntity.getExcelTitleMap().keySet()){
                String colName = basicExcelDataEntity.getExcelTitleMap().get(str);

                if( colName.equals("终端号")){
                    basicInfo.setMachineSN(map.get(str));
                    continue;
                }

                if( colName.equals("终端序列号")){
                    basicInfo.setMachinecode(map.get(str));
                    continue;
                }

                if( colName.equals("批次号")){
                    basicInfo.setBatchcode(map.get(str));

                    continue;
                }

                if( colName.equals("终端状态")){
                    basicInfo.setIsbound(map.get(str));
                    continue;
                }
                if( colName.equals("产品类型")){
                    basicInfo.setMachinetype(map.get(str));
                    continue;
                }
                if( colName.equals("服务商编号")){
                    basicInfo.setAttagentcode(map.get(str));

                    continue;
                }

                if( colName.equals("服务商名称")){
                    basicInfo.setAttagentname(map.get(str));
//                    Map paramap = new HashMap();
//                    paramap.put("agentname",map.get(str));
//                    List<Map<String, Object>> list=	iPlatformCompanyStaffDao.findConpanyStaffByName(paramap);
//                    if (list!=null && list.size()>0){
//                        basicInfo.setStaffid(list.get(0).get("id").toString());
//                    }else{
//                        basicInfo.setStaffid("");
//                    }
                    continue;
                }
                if(colName.equals("下级服务商名称")){
                    basicInfo.setPerattagentname(map.get(str));
                    continue;
                }
                if( colName.equals("商户编号")){
                    basicInfo.setMerchantcode(map.get(str));

                    continue;
                }
                if( colName.equals("商户名称")){
                    basicInfo.setMerchantname(map.get(str));
                    continue;
                }
                if( colName.equals("网点编号")){
                    basicInfo.setDotcode(map.get(str));
                    continue;
                }
                if( colName.equals("网点名称")){
                    basicInfo.setDotname(map.get(str));
                    continue;
                }
                if( colName.equals("是否加密终端")){
                    basicInfo.setIsencryption(map.get(str));
                    continue;
                }
                if( colName.equals("终端标识")){
                    basicInfo.setMachineident(map.get(str));
                    continue;
                }
                if( colName.equals("政策标识")){
                    basicInfo.setPolicyident(map.get(str));
                    continue;
                }
                if( colName.equals("调拨次数")){
                    basicInfo.setAllocationnum(map.get(str));
                    continue;
                }
                if( colName.equals("是否已缴纳押金")){
                    basicInfo.setIfdeposit(map.get(str));
                    continue;
                }

            }

            if(org.apache.commons.lang3.StringUtils.isEmpty(basicInfo.getMachineSN())
                    || org.apache.commons.lang3.StringUtils.isEmpty(basicInfo.getMachineSN())){
                System.out.println("数据不完整,跳过处理:"+basicInfo.toString());
                continue;
            }
            basicInfoList.add(basicInfo);
        }
        return basicInfoList;
    }

    /**
     * 海科交易流水信息解析
     * @param brandId
     * @param destTemp
     */
    @Override
    public  void haiKeTradeOrder(String brandId, File destTemp) {
        ParsingShuaBaoMExcelUtil parsingAgentExcelUtil = new ParsingShuaBaoMExcelUtil();
        BasicExcelDataEntity basicExcelDataEntity = parsingAgentExcelUtil.getParsingExcelResult2(destTemp);
        List<HaiKeTradeOrderInfo> shuaBaoInfoList = preHaiKeTradeOrderExcelToList(basicExcelDataEntity);
        if(CollectionUtil.isNotEmpty(shuaBaoInfoList)){
            List<Map> list = new ArrayList<>();
            List<Map> uplist = new ArrayList<>();
            for (int j=0;j<shuaBaoInfoList.size();j++){
                HaiKeTradeOrderInfo basicInfo =  shuaBaoInfoList.get(j);
                System.out.println(basicInfo);
                //交易流水号
                String orderid = basicInfo.getOrderId();
                HashMap<String ,Object> map = new HashMap<>();
                map.put("orderid",orderid);
                List<Map<String, Object>> maplist = iPlatformMachineOrderDao.queryMachineOrderById(map);
                if (maplist !=null && maplist.size()>0){
                    Map<String, Object> upMap = new HashMap<>();
                    String id = maplist.get(0).get("id").toString() ;
                    upMap.put("id", id);
                    if("交易成功".equals(basicInfo.getOrderState())){//交易状态
                        upMap.put("order_state", '1');
                    }else if("交易失败".equals(basicInfo.getOrderState())){
                        upMap.put("order_state", '2');
                    }else if("处理中".equals(basicInfo.getOrderState())){
                        upMap.put("order_state", '3');
                    }else {
                        upMap.put("order_state", null);
                    }
                    uplist.add(upMap);
                }else {
                    Map<String, Object> paramsMap = new HashMap<>();
                    paramsMap.put("id", UUIDGenerator.generate());
                    paramsMap.put("order_id", basicInfo.getOrderId());

                    paramsMap.put("machine_type", basicInfo.getMachineType());//产品类型

                    paramsMap.put("machine_code",basicInfo.getMachineCode());//终端编号
                    paramsMap.put("merchant_code",basicInfo.getMerchantCode());//商户编号
                    paramsMap.put("merchant_name",basicInfo.getMerchantName());//商户名称

                    paramsMap.put("trade_time", FormatDateUtil.StrToDate(basicInfo.getTradeTime()));//交易时间
                    paramsMap.put("trade_day", DateFormatUtil.convertDate(FormatDateUtil.StrToDate(basicInfo.getTradeTime()),"yyyyMMdd"));//交易日期
                    paramsMap.put("network_name",basicInfo.getNetworkName());//网点名称

                    paramsMap.put("butagentname",basicInfo.getButAgentname());//下级服务商

                    paramsMap.put("order_type",basicInfo.getOrderType());//交易类型
                    paramsMap.put("isshuagmian",basicInfo.getIsshuagmian());//是否双免
                    paramsMap.put("order_amount",basicInfo.getOrderAmount());//交易金额
                    paramsMap.put("yhjine",basicInfo.getYhjine());//优惠金额
                    paramsMap.put("qdyhjine",basicInfo.getQdyhjine());//渠道优惠金额

                    paramsMap.put("jyshxf",basicInfo.getJyshxf());//交易手续费
                    paramsMap.put("zshshxf",basicInfo.getZshshxf());//增收手续费
                    paramsMap.put("pay_type",basicInfo.getPayType());//交易方式
                    paramsMap.put("pay_card_type",basicInfo.getPayCardType());//卡类型
                    paramsMap.put("settle_type",basicInfo.getSettleType());//结算周期

                    if("交易成功".equals(basicInfo.getOrderState())){//交易状态
                        paramsMap.put("order_state", '1');
                    }else if("交易失败".equals(basicInfo.getOrderState())){
                        paramsMap.put("order_state", '2');
                    }else if("处理中".equals(basicInfo.getOrderState())){
                        paramsMap.put("order_state", '3');
                    }else {
                        paramsMap.put("order_state", null);
                    }
                    paramsMap.put("dingdanleixin",basicInfo.getDingdanleixin());//订单类型


                    paramsMap.put("brand_id",brandId);

                    paramsMap.put("create_time",new Date());
                    paramsMap.put("create_day",DateFormatUtil.convertDate(new Date(),"yyyyMMdd"));
                    //paramsMap.put("del_tag",);
                    list.add(paramsMap);
                }

            }
            Map m = new HashMap();
            if (list!=null && list.size()>0) {
                m.put("itemMap", list);
                platformUserInfoService.insertHKDataTradeOrderAll(m);
            }
            if (uplist!=null && uplist.size()>0) {
                //已存在的机具交易更新交易状态
                for (Map map : uplist) {
                    iPlatformMachineOrderDao.updateMachineOrder(map);
                }
            }
        }
    }

    private  List<HaiKeTradeOrderInfo> preHaiKeTradeOrderExcelToList(BasicExcelDataEntity basicExcelDataEntity){
        Map<String,String> titleIndex = basicExcelDataEntity.getExcelTitleIndex();
        List<HaiKeTradeOrderInfo> basicInfoList = new ArrayList<>();
        System.out.println("titleIndex:"+titleIndex.toString());
        for(Map<String,String> map:basicExcelDataEntity.getExcelData()){
            System.out.println("excel:"+map.toString());
            HaiKeTradeOrderInfo basicInfo = new HaiKeTradeOrderInfo();
            for (String str:basicExcelDataEntity.getExcelTitleMap().keySet()){
                String colName = basicExcelDataEntity.getExcelTitleMap().get(str);


                if( colName.equals("交易流水号")){
                    basicInfo.setOrderId(map.get(str));
                    continue;
                }
                if( colName.equals("产品类型")){
                    basicInfo.setMachineType(map.get(str));
                    continue;
                }

                if( colName.equals("终端编号")){
                    basicInfo.setMachineCode(map.get(str));
                    continue;
                }

                if( colName.equals("商户编号")){
                    basicInfo.setMerchantCode(map.get(str));
                    continue;
                }
                if( colName.equals("商户名称")){
                    basicInfo.setMerchantName(map.get(str));
                    continue;
                }
                if( colName.equals("交易时间")){
                    basicInfo.setTradeTime(map.get(str));
                    continue;
                }

                if( colName.equals("网点名称")){
                    basicInfo.setNetworkName(map.get(str));
                    continue;
                }
                if(colName.equals("下级服务商")){
                    basicInfo.setButAgentname(map.get(str));
                    continue;
                }
                if( colName.equals("交易类型")){
                    basicInfo.setOrderType(map.get(str));
                    continue;
                }
                if( colName.equals("是否双免")){
                    basicInfo.setIsshuagmian(map.get(str));
                    continue;
                }
                if( colName.equals("交易金额")){
                    basicInfo.setOrderAmount(map.get(str));
                    continue;
                }
                if( colName.equals("优惠金额")){
                    basicInfo.setYhjine(map.get(str));
                    continue;
                }
                if( colName.equals("渠道优惠金额")){
                    basicInfo.setQdyhjine(map.get(str));
                    continue;
                }
                if( colName.equals("交易手续费")){
                    basicInfo.setJyshxf(map.get(str));
                    continue;
                }
                if( colName.equals("增收手续费")){
                    basicInfo.setZshshxf(map.get(str));
                    continue;
                }
                if( colName.equals("交易方式")){
                    basicInfo.setPayType(map.get(str));
                    continue;
                }
                if( colName.equals("手机PAY类型")){
                    basicInfo.setShjPAYlx(map.get(str));
                    continue;
                }
                if( colName.equals("卡类型")){
                    basicInfo.setPayCardType(map.get(str));
                    continue;
                }

                if( colName.equals("结算周期")){
                    basicInfo.setSettleType(map.get(str));
                    continue;
                }

                if( colName.equals("交易状态")){
                    basicInfo.setOrderState(map.get(str));
                    continue;
                }

                if( colName.equals("订单类型")){
                    basicInfo.setDingdanleixin(map.get(str));
                    continue;
                }

            }

            if(org.apache.commons.lang3.StringUtils.isEmpty(basicInfo.getMerchantCode())
                    || org.apache.commons.lang3.StringUtils.isEmpty(basicInfo.getMerchantCode())){
                System.out.println("数据不完整,跳过处理:"+basicInfo.toString());
                continue;
            }
            basicInfoList.add(basicInfo);
        }
        return basicInfoList;

    }
    /**
     * 海科商户信息解析
     * @param brandId
     * @param destTemp
     */
    @Override
    public void HKMerchant(String brandId, File destTemp) {
        ParsingShuaBaoMExcelUtil parsingAgentExcelUtil = new ParsingShuaBaoMExcelUtil();
        BasicExcelDataEntity basicExcelDataEntity = parsingAgentExcelUtil.getParsingExcelResult2(destTemp);
        List<HKMachineInfo> HKInfoList = preHKBasicExcelToList(basicExcelDataEntity);
        if(CollectionUtil.isNotEmpty(HKInfoList)){
            List<Map> list = new ArrayList<>();
            List<Map> uplist = new ArrayList<>();
            for (int j=0;j<HKInfoList.size();j++){
                HKMachineInfo basicInfo =  HKInfoList.get(j);
                System.out.println(basicInfo);
                HashMap<String, Object> queryMap = new HashMap<>();
                String merchant_code= basicInfo.getMerchantCode();
                queryMap.put("merchant_code",merchant_code);
                List<Map<String,Object>>  mlist= iPlatformMerchatInfoDao.queryMerchantInfoList(queryMap);

                if (mlist!=null && mlist.size()>0){// 已存在的商户更新商户状态
                    String id = mlist .get(0).get("id").toString();
                    Map<String, Object> upMap = new HashMap<>();
                    upMap.put("id",id);//商户状态
                    
                    upMap.put("merchant_state",basicInfo.getMerchantState());//商户状态

                    upMap.put("machine_type",basicInfo.getMachineType());//产品类型

                    upMap.put("audit_status",basicInfo.getAuditStatus());//审核状态

                    upMap.put("rate_type",basicInfo.getRateType());//费率类型

                    upMap.put("bind_time",basicInfo.getBindTime());//入网时间
                    upMap.put("bind_day", "".equals(basicInfo.getBindTime())?null:DateFormatUtil.convertDate(FormatDateUtil.StrToDate(basicInfo.getBindTime()),"yyyyMMdd"));//入网日期

                    upMap.put("update_time",new Date());
                    uplist.add(upMap);
                }else {
                    Map<String, Object> paramsMap = new HashMap<>();
                    paramsMap.put("id", UUIDGenerator.generate());
                    paramsMap.put("merchant_code",basicInfo.getMerchantCode());//商户编号
                    paramsMap.put("merchant_name",basicInfo.getMerchantName());//商户名称
                    paramsMap.put("bind_time",basicInfo.getBindTime());//入网时间
                    paramsMap.put("bind_day", "".equals(basicInfo.getBindTime())?null:DateFormatUtil.convertDate(FormatDateUtil.StrToDate(basicInfo.getBindTime()),"yyyyMMdd"));//入网日期
                    paramsMap.put("broker_code",basicInfo.getBrokerCode());//服务商编号
                    paramsMap.put("broker_name",basicInfo.getBrokerName());//服务商名称
                    paramsMap.put("merchant_rovince",basicInfo.getMerchantRovince());//所在地区
                    paramsMap.put("subbroker_name",basicInfo.getSubbrokerName());//下级服务商
                    paramsMap.put("merchant_type",basicInfo.getMerchantType());//商户类型

                    paramsMap.put("merchant_state",basicInfo.getMerchantState());//商户状态

                    paramsMap.put("machine_type",basicInfo.getMachineType());//产品类型

                    paramsMap.put("audit_status",basicInfo.getAuditStatus());//审核状态

                    paramsMap.put("rate_type",basicInfo.getRateType());//费率类型

                    paramsMap.put("brand_id",brandId);

                    paramsMap.put("create_time",new Date());
                    paramsMap.put("create_day",DateFormatUtil.convertDate(new Date(),"yyyyMMdd"));
                    //paramsMap.put("del_tag",);
                    list.add(paramsMap);
                }


            }
            if (list!=null && list.size()>0) {
                Map m = new HashMap();
                m.put("itemMap", list);
                platformUserInfoService.insertHKMerchant(m);
            }
            if (uplist!=null && uplist.size()>0){
                for (Map map : uplist) {
                    iPlatformMerchatInfoDao.updateMerchantInfo(map);
                }
            }
        }
    }

    /**
     *
     * @param basicExcelDataEntity
     * @return
     */
    private  List<HKMachineInfo> preHKBasicExcelToList(BasicExcelDataEntity basicExcelDataEntity){
        Map<String,String> titleIndex = basicExcelDataEntity.getExcelTitleIndex();
        List<HKMachineInfo> basicInfoList = new ArrayList<>();
        System.out.println("titleIndex:"+titleIndex.toString());
        for(Map<String,String> map:basicExcelDataEntity.getExcelData()){
            System.out.println("excel:"+map.toString());
            HKMachineInfo basicInfo = new HKMachineInfo();
            for (String str:basicExcelDataEntity.getExcelTitleMap().keySet()){
                String colName = basicExcelDataEntity.getExcelTitleMap().get(str);
                if( colName.equals("商户编号")){
                    basicInfo.setMerchantCode(map.get(str));
                    continue;
                }
                if( colName.equals("商户名称")){
                    basicInfo.setMerchantName(map.get(str));
                    continue;
                }
                if( colName.equals("入网时间")){
                    basicInfo.setBindTime(map.get(str));
                    continue;
                }
                if( colName.equals("服务商编号")){
                    basicInfo.setBrokerCode(map.get(str));
                    continue;
                }

                if( colName.equals("服务商名称")){
                    basicInfo.setBrokerName(map.get(str));
                    continue;
                }

                if( colName.equals("所在地区")){
                    basicInfo.setMerchantRovince(map.get(str));
                    continue;
                }

                if( colName.equals("下级服务商")){
                    basicInfo.setSubbrokerName(map.get(str));
                    continue;
                }
                if(colName.equals("商户类型")){
                    basicInfo.setMerchantType(map.get(str));
                    continue;
                }
                if(colName.equals("商户状态")){
                    basicInfo.setMerchantState(map.get(str));
                    continue;
                }
                if( colName.equals("产品类型")){
                    basicInfo.setMachineType(map.get(str));
                    continue;
                }

                if( colName.equals("审核状态")){
                    basicInfo.setAuditStatus(map.get(str));
                    continue;
                }
                if( colName.equals("费率类型")){
                    basicInfo.setRateType(map.get(str));
                    continue;
                }
            }

            if(org.apache.commons.lang3.StringUtils.isEmpty(basicInfo.getMerchantName())
                    || org.apache.commons.lang3.StringUtils.isEmpty(basicInfo.getMerchantCode())){
                System.out.println("数据不完整,跳过处理:"+basicInfo.toString());
                continue;
            }
            basicInfoList.add(basicInfo);
        }
        return basicInfoList;
    }
}
