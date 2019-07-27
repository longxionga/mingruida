package com.acl.platform.service.impl;


import com.acl.component.SystemConfig;
import com.acl.platform.dao.IPlatformCompanyStaffDao;
import com.acl.platform.dao.IPlatformMachineInfoDao;
import com.acl.platform.dao.IPlatformWithDrawalsApplyDao;
import com.acl.platform.service.IPlatformCompanyStaffService;
import com.acl.pojo.LoginSession;
import com.acl.sys.dao.SysBackDictInfoDao;
import com.acl.sys.dao.SysBackRoleDao;
import com.acl.sys.dao.SysUserInfoDao;
import com.acl.utils.excel.BasicExcelDataEntity;
import com.acl.utils.excel.CompanyStaffInfo;
import com.acl.utils.excel.ParsingShuaBaoMExcelUtil;
import com.acl.utils.excel.RefundVouchersInfo;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *className:IPatformrefundvouchersServiceImpl
 *author:longxionga
 *createDate:2019年4月30日
 *department:铭锐达
 *description:充值券查询
 */
@Service
@Transactional
public class IPatformCompanyStaffServiceImpl implements IPlatformCompanyStaffService {

	@Autowired
	private IPlatformCompanyStaffDao iPlatformCompanyStaffDao;

	@Autowired
	private SysUserInfoDao sysUserInfoDao;

	@Autowired
	private SysBackRoleDao sysBackRoleDao;

	@Autowired
	private IPlatformMachineInfoDao iplatformMachineInfoDao;



	@Override
	public PageList<?> queryCompanyStaffInfoPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		//管理员可以查看说有数据，员工只能查看下级员工数据
		String loginname = paramsMap.get("user_name").toString();
		String dept_ids = paramsMap.get("dept_ids").toString();
		Map<String,Object> ma= new HashMap<String, Object>();
		ma.put("loginname",loginname);
		PageList<?> pageList = new PageList<>();

	    if (dept_ids!=null && !"0".equals(dept_ids) ){
			//根据登陆名称查询员工信息
			List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.querystaffInfoByType(ma);
			if (staffmap!=null && staffmap.size()>0){
				String orgid = staffmap.get(0).get("id").toString();
				String ibrand_id = staffmap.get(0).get("brandid").toString();
				String branch = staffmap.get(0).get("branch").toString();
				paramsMap.put("orgid",orgid);
				paramsMap.put("ibrand_id",ibrand_id);
				paramsMap.put("branch",branch);
				pageList = iPlatformCompanyStaffDao.queryCompanyStaffInfoPageList(paramsMap, pageBounds);
				return pageList;
			}else{
				paramsMap.put("orgid","-1");
				pageList = iPlatformCompanyStaffDao.queryCompanyStaffInfoPageList(paramsMap, pageBounds);
				return pageList;
			}
		}
		else{
			pageList = iPlatformCompanyStaffDao.queryCompanyStaffInfoPageList(paramsMap, pageBounds);
			return pageList;
		}


	}

	@Override
	public void companystaffimport(File file) {
		ParsingShuaBaoMExcelUtil parsingAgentExcelUtil = new ParsingShuaBaoMExcelUtil();
		BasicExcelDataEntity basicExcelDataEntity = parsingAgentExcelUtil.getParsingExcelResult2(file);
		List<CompanyStaffInfo> shuaBaoInfoList = CompanyStaffExcelToList(basicExcelDataEntity);
		if(CollectionUtil.isNotEmpty(shuaBaoInfoList)){
			List<Map> list = new ArrayList<>();
			for (int j=0;j<shuaBaoInfoList.size();j++){
				CompanyStaffInfo companyStaffInfo =  shuaBaoInfoList.get(j);
				System.out.println(companyStaffInfo);
				Map<String, Object> paramsMap = new HashMap<>();
				paramsMap.put("id", UUIDGenerator.generate());
				paramsMap.put("parentid", companyStaffInfo.getParentid());
				paramsMap.put("staffname", companyStaffInfo.getStaffname());
				paramsMap.put("staffcode", companyStaffInfo.getStaffcode());
				paramsMap.put("agentcode",companyStaffInfo.getAgentcode());
				paramsMap.put("agentname",companyStaffInfo.getAgentname());
				paramsMap.put("phoneNO",companyStaffInfo.getPhoneNO());
				paramsMap.put("staffstate",companyStaffInfo.getStaffstate());
				paramsMap.put("startdate",companyStaffInfo.getStartdate()== null || "".equals(companyStaffInfo.getStartdate()) ?null:FormatDateUtil.StrToDate(companyStaffInfo.getStartdate()));
				paramsMap.put("enddate",companyStaffInfo.getEnddate()==null || "".equals(companyStaffInfo.getEnddate())?null:FormatDateUtil.StrToDate(companyStaffInfo.getEnddate()));
				paramsMap.put("position", companyStaffInfo.getPosition());
				paramsMap.put("branch", companyStaffInfo.getBranch());
				paramsMap.put("remarks", companyStaffInfo.getRemarks());
				paramsMap.put("channel", companyStaffInfo.getChannel());
				list.add(paramsMap);

			}
			Map m = new HashMap();
			m.put("itemMap",list);
			this.insertCompanyStaff(m);
		}
	}

	/**
	 *
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
				if( colName.equals("公司")){
					paramap1.put("branch",map.get(str));
					companyStaffInfo.setBranch(map.get(str));
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
				if(colName.equals("员工名称")){
					companyStaffInfo.setStaffname(map.get(str));
					continue;
				}
				if( colName.equals("上级员工")){
					if (map.get(str)!=null && !"".equals(map.get(str))){
						//Map paramap = new HashMap();
						paramap1.put("name",map.get(str));
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

	@Override
	public int insertCompanyStaff(Map<String, Object> paramsMap) {
		return iPlatformCompanyStaffDao.insertCompanyStaff(paramsMap);
	}

	@Override
	public int deleteCompanyStaffAll(Map<String, Object> paramsMap) {
		return iPlatformCompanyStaffDao.deleteCompanyStaffAll(paramsMap);
	}

	@Override
	public List<Map<String, Object>> querystaffInfoByType(HashMap<String, Object> hashMap) {
		//
		return iPlatformCompanyStaffDao.querystaffInfoByType(hashMap);
	}
	@Override
	public List<Map<String, Object>> querystaffCompanyInfo(HashMap<String, Object> hashMap) {
		return iPlatformCompanyStaffDao.querystaffCompanyInfo(hashMap);
	}
	@Override
	public List<Map<String, Object>> querystaffInfoForCombox(HashMap<String, Object> hashMap) {
		return iPlatformCompanyStaffDao.querystaffInfoForCombox(hashMap);
	}
	@Override
	public List<Map<String, Object>> querystaffCompanyInfoForCombox(HashMap<String, Object> hashMap) {
		return iPlatformCompanyStaffDao.querystaffCompanyInfoForCombox(hashMap);
	}
	@Override
	public List<Map<String, Object>> queryCompanyManagerForCombox(HashMap<String, Object> hashMap) {
		return iPlatformCompanyStaffDao.queryCompanyManagerForCombox(hashMap);
	}

	@Override
	public String insCompanyStaff(HashMap<String, Object> paHashMap , LoginSession loginSession)  throws Exception {
		paHashMap.put("id", UUIDGenerator.generate());
		paHashMap.put("create_time", new Date());
		paHashMap.put("channel","1");
		String loginuserid= "";
		String logincode="";
		String position = "";
		String position2 = "";
		String brandname = "";
		String msg = "";

		Map<String,Object> km = new HashMap<>();
		String t = paHashMap.get("parentid")!=null ? paHashMap.get("parentid").toString():"";
		km.put("staff_id",t);
		List<Map<String, Object>>  smpa=iPlatformCompanyStaffDao.findStaffInfoById(km);
		if (smpa==null  || smpa.size()== 0){ //保存失败，上级员工不存在
			return  "5";
		}

		/*//如果上级员工不存在默认未登陆员工
		if (paHashMap!=null && paHashMap.containsKey("parentid")){
			String sdept_id=paHashMap.get("branch").toString();
			Map<String,Object> ap = new HashMap<>();
			ap.put("sdept_id",sdept_id);

			List<Map<String, Object>>  smpalist=iPlatformCompanyStaffDao.findDeptInfoById(ap);
			if (smpalist!=null && smpalist.size()>0){
				brandname=smpalist.get(0).get("dept_name")!=null ?  smpalist.get(0).get("dept_name").toString():"";
			}
		}else {
			String loginname = loginSession.getUser_name();
			Map<String,Object> paramap = new HashMap<>();
			paramap.put("loginname",loginname);
			List<Map<String, Object>>  smpa=iPlatformCompanyStaffDao.querystaffInfoByType(paramap);

			if (smpa!=null && smpa.size()>0){
				paHashMap.put("brandid",smpa.get(0).get("brandid"));
				paHashMap.put("branch",smpa.get(0).get("branch"));
				paHashMap.put("staffstate","1");
				paHashMap.put("isstate","1");
				String parentid = smpa.get(0).get("id")!=null ?  smpa.get(0).get("id").toString():"";
				paHashMap.put("parentid",parentid);
				position2 = smpa.get(0).get("position")!=null ?  smpa.get(0).get("position").toString():"";

				brandname=smpa.get(0).get("dept_name")!=null ?  smpa.get(0).get("dept_name").toString():"";
			}
		}*/

		String sdept_id=paHashMap.get("branch").toString();
		Map<String,Object> ap = new HashMap<>();
		ap.put("sdept_id",sdept_id);

		List<Map<String, Object>>  smpalist=iPlatformCompanyStaffDao.findDeptInfoById(ap);
		if (smpalist!=null && smpalist.size()>0){
			brandname=smpalist.get(0).get("dept_name")!=null ?  smpalist.get(0).get("dept_name").toString():"";
		}else {
			return "3";
		}
		position=paHashMap.get("position").toString();

		//判断主管上级员工不能是主管
		if (position !=null && "2".equals(position)){
			String upstaffid = paHashMap.get("parentid")!=null ? paHashMap.get("parentid").toString():"";
			Map<String,Object> smap = new HashMap<>();
			smap.put("staffid",upstaffid);
			List<Map<String, Object>>  slist  = iPlatformCompanyStaffDao.findStaffInfoById(smap);
			if (slist!=null && slist.size()>0){
				String position11 = slist.get(0).get("position")!=null ? slist.get(0).get("position").toString():"";
				if (position11!=null && position11.equals("2")){  //
					return "2";
				}
			}else {
				msg= "1";
				return msg;
			}
		}
		HashMap<String,Object> maps = new HashMap<>();
		//员工创建登陆账户
        if (position!=null && ("1".equals(position) || "2".equals(position) || "3".equals(position))){
			String name = brandname.split("-")[0];
			String staffname =  paHashMap.get("staffname").toString();
			//生成登陆账户
			logincode = name +"-"+ staffname;
			maps.put("name",logincode);
			List<Map<String, Object>> userlist = sysUserInfoDao.userValidate(maps);
			if (userlist!=null && userlist.size()>0){
				String staffcode =  new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()));
				logincode=logincode+staffcode.substring(staffcode.length()-3);

			}
			paHashMap.put("logincode",logincode);
			loginuserid=foundUserbyStaff(paHashMap,position);

        }

		paHashMap.put("loginuserid",loginuserid);
		iPlatformCompanyStaffDao.insCompanyStaff(paHashMap);
		return "4";
	}

	@Override
	public void updCompanyStaff(HashMap<String, Object> paHashMap) {
		 paHashMap.put("update_time", new Date());
		iPlatformCompanyStaffDao.updCompanyStaff(paHashMap);
	}

	@Override
	public List<Map<String, Object>> queryStaffDeptInfoForCombox(HashMap<String, Object> hashMap) {
		return iPlatformCompanyStaffDao.queryStaffDeptInfoForCombox(hashMap);
	}
	@Override
	public List<Map<String, Object>> staffValidate(HashMap<String, Object> paramsMap) {
		return iPlatformCompanyStaffDao.staffValidate(paramsMap);
	}
	@Override
	public List<Map<String, Object>> staffNameValidate(HashMap<String, Object> paramsMap) {
		return iPlatformCompanyStaffDao.staffValidate(paramsMap);
	}
	@Override
	public String deleteCompanyStaff(Map<String, Object> paramsMap) throws Exception{
		String str = "";
		try {
			//查询员工信息是否绑定机具信息
			List<Map<String, Object>>  machinelist =iplatformMachineInfoDao.findMachineById(paramsMap);
			if (machinelist!=null && machinelist.size()>0){
				str = "员工已绑定机具信息不能删除";
			}else {
				//查询下级员工信息
				String staffid = paramsMap.get("staffid").toString();
				Map<String ,Object> map = new HashMap<>();
				map.put("parentid",staffid);
				List<Map<String, Object>>  upstafflist =iPlatformCompanyStaffDao.findStaffInfoById(map);
				if (upstafflist!=null && upstafflist.size()>0){
					str = "员工存在下级员工不能删除";
				}else {
					//查询员工信息
					List<Map<String, Object>>  stafflist =iPlatformCompanyStaffDao.findStaffInfoById(paramsMap);
					if (stafflist!=null && stafflist.size()>0){
						String loginid = stafflist.get(0).get("loginuserid").toString();
						Map<String ,Object> logmap = new HashMap<>();
						logmap.put("user_id",loginid);
						//删除用户角色信息
						sysUserInfoDao.deleteUserRole(logmap);
						//删除员工用户信息信息
						sysUserInfoDao.deleteStaffUser(logmap);
						iPlatformCompanyStaffDao.deleteCompanyStaff(paramsMap);
					}else {
						throw new Exception();
					}

					str = "删除成功";
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}

		return str;
	}

	@Override
	public List<Map<String, Object>> getCompanyStaffBox(HashMap<String, Object> hashMap,  LoginSession loginSession ) {

		String loginname = loginSession.getUser_name();
		String dept_id = loginSession.getDept_id();
		Map<String,Object> paramap = new HashMap<>();
		paramap.put("loginname",loginname);
		List<Map<String, Object>>  smpa=iPlatformCompanyStaffDao.querystaffInfoByType(paramap);
		String brandid = null ;
		String branch = null ;
		String position = null ;
		if (!dept_id.equals("0")){
		if (smpa!=null && smpa.size()>0 ){
			Map<String, Object> m = smpa.get(0);
			if (m!=null && m.containsKey("brandid")){
				hashMap.put("brandid",m.get("brandid"));
			}
			if (!dept_id.equals("3")){
			if (m!=null && m.containsKey("branch")){
				hashMap.put("branch",m.get("branch"));
			}
			}
//			if (m!=null && m.containsKey("position")){
//				String deptid = m.get("position").toString();
//				if (deptid.equals("2")){
//					hashMap.put("position","1");
//				}
//				else  if (deptid.equals("3")){
//					hashMap.put("position","2");
//				}else if (deptid.equals("1")){
//					hashMap.put("position","9");
//				}
//			}
		}}
		return iPlatformCompanyStaffDao.getCompanyStaffBox(hashMap);
	}
	@Override
	public List<Map<String, Object>> querySubStaffForCombox(HashMap<String, Object> hashMap, LoginSession loginSession) {

		String loginname = loginSession.getUser_name();
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("loginname",loginname);

		//查询登陆员工信息
		List<Map<String, Object>>  staffnamelist=this.querystaffInfoByType(map);

		if (staffnamelist!=null && staffnamelist.size()>0){
			String staff_id = (String) staffnamelist.get(0).get("id");
			hashMap.put("orgid",staff_id);

		}

		return iPlatformCompanyStaffDao.getCompanyStaffBox(hashMap);
	}
	@Override
	public String foundUserbyStaff(Map<String ,Object>  paHashMap,String position ) throws  Exception{

		//创建员工登陆账户信息
		Object o = paHashMap.get("logincode");
		String user_id= "";
		if (o!=null && o!="") {
			String loginname_1 = paHashMap.get("logincode").toString();

			String phoneNO = paHashMap.get("phoneNO").toString();

			String sdept_id = paHashMap.get("branch").toString();

			String staffname = paHashMap.get("staffname").toString();
			HashMap<String, Object> userMap = new HashMap<>();
			userMap.put("name", loginname_1);
			//验证登陆用户是否重复
			List<Map<String, Object>> userlist = sysUserInfoDao.userValidate(userMap);
			//查询用户岗位对应的角色
			HashMap<String, Object> roleMap = new HashMap<>();
			if (position != null) {
				if ("1".equals(position)) {
					roleMap.put("role_name", "分公司经理");
				} else if ("2".equals(position)) {
					roleMap.put("role_name", "分公司主管");
				} else if ("3".equals(position) || "5".equals(position) || "6".equals(position)) {
					roleMap.put("role_name", "分公司组员");
				}
			}

			List<Map<String, Object>> rolelist = sysBackRoleDao.fandBackRole(roleMap);
			if (userlist.size() == 0 && rolelist.size() == 1) {
				String role_id = rolelist.get(0).get("role_id").toString();
				//创建登陆用户信息
				HashMap<String, Object> paramsMap = new HashMap<>();
				user_id = UUIDGenerator.generate();
				paramsMap.put("user_id", user_id);
				paramsMap.put("user_password", MD5Utils.MD5(SystemConfig.AncholPWD));
				paramsMap.put("create_date", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));

				paramsMap.put("is_use", "1");
				paramsMap.put("user_name", loginname_1);
				paramsMap.put("user_nick_name", staffname);

				paramsMap.put("user_mobile", phoneNO);
				paramsMap.put("dept_id", sdept_id);
				paramsMap.put("user_gender", "01");

				sysUserInfoDao.insertUserInfo(paramsMap);

				Map<String, Object> tmp = new HashMap<String, Object>();
				tmp.put("sys_id", UUIDGenerator.generate());
				tmp.put("role_id", role_id);
				tmp.put("user_id", user_id);
					/*Map<String,Object> user = new HashMap<String,Object>();
					user.put("user_id", ids[i]);
					sysBackRoleDao.deleteBackRoleUserByUser(user);
					mongodbBaseDao.remove(user, "roleUser");*/
				sysBackRoleDao.insertBackRoleUser(tmp);
			} else {
				throw new Exception();
			}
		}else {
			throw new Exception();
		}
		return  user_id;
	}

	@Override
	public String insCompanyStaff2(HashMap<String, Object> paHashMap , LoginSession loginSession)  throws Exception {
		paHashMap.put("id", UUIDGenerator.generate());
		paHashMap.put("create_time", new Date());
		paHashMap.put("channel","1");
		String loginuserid= "";
		String logincode="";
		String dept_name = "";
		String position = "";
		String position2 = "";
		String msg = "";

		// 查询上级员工是否存在
		Map<String,Object> km = new HashMap<>();
		String t =paHashMap.get("parentid")!=null ? paHashMap.get("parentid").toString():"";
		km.put("staff_id",t);
		List<Map<String, Object>>  aa=iPlatformCompanyStaffDao.findStaffInfoById(km);
      if (aa==null  || aa.size()== 0){ //保存失败，上级员工不存在
      	return  "5";
	  }
		/*//如果上级员工不存在默认未登陆员工
		if (paHashMap!=null && paHashMap.containsKey("parentid") && paHashMap.containsValue("parentid")){

			String loginname = loginSession.getUser_name();
			Map<String,Object> paramap = new HashMap<>();
			paramap.put("loginname",loginname);
			List<Map<String, Object>>  smpa=iPlatformCompanyStaffDao.querystaffInfoByType(paramap);
			if (smpa!=null && smpa.size()>0){
				paHashMap.put("brandid",smpa.get(0).get("brandid"));
				paHashMap.put("branch",smpa.get(0).get("branch"));
				paHashMap.put("staffstate","1");
				paHashMap.put("isstate","1");
				dept_name=smpa.get(0).get("dept_name")!=null ?  smpa.get(0).get("dept_name").toString():"";
			}

		}else {
			String loginname = loginSession.getUser_name();
			Map<String,Object> paramap = new HashMap<>();
			paramap.put("loginname",loginname);
			List<Map<String, Object>>  smpa=iPlatformCompanyStaffDao.querystaffInfoByType(paramap);

			if (smpa!=null && smpa.size()>0){
				paHashMap.put("brandid",smpa.get(0).get("brandid"));
				paHashMap.put("branch",smpa.get(0).get("branch"));
				paHashMap.put("staffstate","1");
				paHashMap.put("isstate","1");
				String parentid = smpa.get(0).get("id")!=null ?  smpa.get(0).get("id").toString():"";
				paHashMap.put("parentid",parentid);
				position2 = smpa.get(0).get("position")!=null ?  smpa.get(0).get("position").toString():"";
				dept_name=smpa.get(0).get("dept_name")!=null ?  smpa.get(0).get("dept_name").toString():"";

			}
		}*/

		String loginname = loginSession.getUser_name();
		Map<String,Object> paramap = new HashMap<>();
		paramap.put("loginname",loginname);
		List<Map<String, Object>>  smpa=iPlatformCompanyStaffDao.querystaffInfoByType(paramap);

		if (smpa!=null && smpa.size()>0){
			paHashMap.put("brandid",smpa.get(0).get("brandid"));
			paHashMap.put("branch",smpa.get(0).get("branch"));
			paHashMap.put("staffstate","1");
			paHashMap.put("isstate","1");
			dept_name=smpa.get(0).get("dept_name")!=null ?  smpa.get(0).get("dept_name").toString():"";

		}else {
			return "3";
		}
		position=paHashMap.get("position").toString();
        //判断主管上级员工不能是主管
		if (position !=null && "2".equals(position)){
			String upstaffid = paHashMap.get("parentid")!=null ? paHashMap.get("parentid").toString():"";
			Map<String,Object> smap = new HashMap<>();
			smap.put("staffid",upstaffid);
			List<Map<String, Object>>  slist  = iPlatformCompanyStaffDao.findStaffInfoById(smap);
			if (slist!=null && slist.size()>0){
				String position11 = slist.get(0).get("position")!=null ? slist.get(0).get("position").toString():"";
				if (position11!=null && position11.equals("2")){  //
					return "2";
				}
			}else {
				msg= "1";
				return msg;
			}
		}

		List<Map<String, Object>>  map = iPlatformCompanyStaffDao.querystaffCompanyInfo(paHashMap);

		HashMap<String,Object> maps = new HashMap<>();
		//员工创建登陆账户
		if (position!=null && ("1".equals(position) || "2".equals(position) || "3".equals(position))){

			String name = dept_name.split("-")[0];
			String staffname =  paHashMap.get("staffname").toString();
			//生成登陆账户
			logincode = name +"-"+ staffname;
			maps.put("name",logincode);
			List<Map<String, Object>> userlist = sysUserInfoDao.userValidate(maps);
			if (userlist!=null && userlist.size()>0){
				String staffcode =  new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()));
				logincode=logincode+staffcode.substring(staffcode.length()-3);
			}
			paHashMap.put("logincode",logincode);
			loginuserid=foundUserbyStaff(paHashMap,position);
//			foundUserbyStaff(paHashMap,position);

		}

		paHashMap.put("loginuserid",loginuserid);
		iPlatformCompanyStaffDao.insCompanyStaff(paHashMap);

		return "4";

	}

	@Override
	public PageList<?> querystaffInfoInfoList(HashMap<String, Object> hashMap,
											  PageBounds pageBounds) {
		PageList<?>  pageList = iPlatformCompanyStaffDao.querystaffInfoInfoList(hashMap, pageBounds);
		return pageList;
	}

	@Override
	public List<Map<String, Object>> queryStaffzhuguanForCombox(HashMap<String, Object> hashMap) {
		return iPlatformCompanyStaffDao.queryStaffzhuguanForCombox(hashMap);
	}

	/**
	 * 组员升职
	 * @param paHashMap
	 */
	@Override
	public String staffPromotion(HashMap<String, Object> paHashMap) throws Exception {

		String staffid = paHashMap.get("id").toString() ;
		Map<String ,Object> stringObjectMap  = new HashMap<>();
		stringObjectMap.put("staffid",staffid);
		List<Map<String, Object>>  slist = iPlatformCompanyStaffDao.findStaffInfoById(stringObjectMap);
		if (slist!=null && slist.size()>0){
			HashMap<String, Object> smap= (HashMap<String, Object>) slist.get(0);
			String pon = slist.get(0).get("position").toString();
			String shengzhistate = slist.get(0).get("shengzhistate") !=null ? slist.get(0).get("shengzhistate").toString() : "";
			if (pon!=null && !pon.equals("3")){
				return "3";//升职失败，组员才能升职
			}
			if (shengzhistate!=null && shengzhistate.equals("1")){
				return "4";//升职失败，组员已升职
			}
			//  员工升职 1.原账户岗位修改为主管，上级员工修改为分公司经理 2 新建账户，复制原账户信息 3 原账户保留未绑定的机具，已绑定机具调拨新账号

			//创建新账户
			String x_id = UUIDGenerator.generate();
			smap.put("id",x_id);

			/*//创建员工登陆账号
			HashMap<String,Object> maps = new HashMap<>();
			//员工创建登陆账户
			if (pon!=null && ("1".equals(pon) || "2".equals(pon) || "3".equals(pon))){
				String phoneNO = slist.get(0).get("phoneNO").toString();
				String branch =slist.get(0).get("branch").toString();
				String name = slist.get(0).get("dept_name").toString().split("-")[0];
				String staffname =  paHashMap.get("staffname").toString();
//				//生成登陆账户
//				String logincode = name +"-"+ staffname;
//				maps.put("name",logincode);
//				List<Map<String, Object>> userlist = sysUserInfoDao.userValidate(maps);
//				if (userlist!=null && userlist.size()>0){
//					String staffcode =  new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()));
//					logincode=logincode+staffcode.substring(staffcode.length()-3);
//				}


//				paHashMap.put("logincode",logincode);
//				paHashMap.put("phoneNO",phoneNO);
//				paHashMap.put("branch",branch);
//			    String	loginuserid=foundUserbyStaff(paHashMap,pon);

//				smap.put("logincode",logincode);
//				smap.put("loginuserid",loginuserid);


			}*/
			smap.put("shengzhistate","1");
			iPlatformCompanyStaffDao.insCompanyStaff(smap);

			Map<String ,Object> jlMap  = new HashMap<>();
			jlMap.put("branch",slist.get(0).get("branch").toString());
			jlMap.put("brandid",slist.get(0).get("brandid").toString());
			//查询员工经理信息
			List<Map<String, Object>>  jllist = iPlatformCompanyStaffDao.queryStaffjingli(jlMap);
			String jl_id = jllist.get(0).get("id").toString();  //分公司经理信息

			HashMap<String ,Object> stringMap  = new HashMap<>();
			stringMap.put("id",staffid);
			stringMap.put("parentid",jl_id);
			stringMap.put("position","2");
			String date = DateFormatUtil.convertDate(new Date(),"yyyyMMdd");
			stringMap.put("startdate",date);
			iPlatformCompanyStaffDao.updCompanyStaff(stringMap);

			//变更已绑定的机具到新员工
			Map<String ,Object> xgMap  = new HashMap<>();
			xgMap.put("zhgstaffid",staffid);
			xgMap.put("zhystaffid",x_id);
			xgMap.put("update_time",new Date());
			iplatformMachineInfoDao.updateMachineForStaffid(xgMap);


		}else {
			return "2";//员工不存在，数据错误
		}
	return "1";//升职成功
	}

}
