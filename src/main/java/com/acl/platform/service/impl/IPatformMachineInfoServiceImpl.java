package com.acl.platform.service.impl;


import com.acl.platform.dao.IPlatformCompanyStaffDao;
import com.acl.platform.dao.IPlatformMachineInfoDao;
import com.acl.platform.dao.IPlatformMachineLogDao;
import com.acl.platform.service.IPlatformMachineInfoService;
import com.acl.pojo.LoginSession;
import com.acl.utils.excel.BasicExcelDataEntity;
import com.acl.utils.excel.CompanyStaffInfo;
import com.acl.utils.excel.ParsingShuaBaoMExcelUtil;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.CollectionUtil;
import com.acl.utils.util.FormatDateUtil;
import com.acl.utils.util.UUIDGenerator;
import org.omg.PortableInterceptor.ObjectReferenceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;

/**
 *className:IPatformMachineInfoServiceImpl
 *author:longxionga
 *createDate:2019年4月30日
 *department:铭锐达
 *description:充值券查询
 */
@Service
@Transactional
public class IPatformMachineInfoServiceImpl implements IPlatformMachineInfoService {

	@Autowired
	private IPlatformMachineInfoDao iPlatformMachineInfoDao;

	@Autowired
	private IPlatformCompanyStaffDao iPlatformCompanyStaffDao;

	@Autowired
	private IPlatformMachineLogDao iPlatformMachineLogDao;

	@Override
	public PageList<?> queryMachineInfoPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds, LoginSession loginSession) {
		//根据登陆账户所属员工信息过滤数据‘
		String loginname = loginSession.getUser_name();//登陆用户名
		String dept_ids =loginSession.getDept_id(); //登陆用户部门id
		if (dept_ids!=null && dept_ids.equals("0")){//管理员和服务商权限
			return iPlatformMachineInfoDao.queryMachineInfoPageList(paramsMap, pageBounds);
		}else {

			Map<String,Object> ma= new HashMap<String, Object>();
			ma.put("loginname",loginname);
			PageList<?> pageList = new PageList<>();
			//根据登陆名称查询员工信息
			List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.querystaffInfoByType(ma);
			if (staffmap!=null && staffmap.size()>0){
				String orgid = staffmap.get(0).get("id").toString();//员工id
				String ibrand_id = staffmap.get(0).get("brandid").toString();//品牌
				String branch = staffmap.get(0).get("branch").toString();//部门

                if (dept_ids!=null && dept_ids.equals("3")){
                    paramsMap.put("ibrand_id",ibrand_id);
                }else {
                    paramsMap.put("orgid",orgid);
                    paramsMap.put("ibrand_id",ibrand_id);
                    paramsMap.put("branch",branch);
                }
                if (staffmap.get(0).containsKey("loginuserid") ){
					Object loginuserid = staffmap.get(0).get("loginuserid");//登陆账号
					if (loginuserid==null || "".equals(loginuserid)){
						paramsMap.put("id","-1");
					}
				}
			}else{//账号未绑定员工
                paramsMap.put("id","-1");
			}
			return iPlatformMachineInfoDao.queryMachineInfoPageList(paramsMap, pageBounds);
		}
	}

	@Override
	public int deleteMachineInfoAll(Map<String, Object> paramsMap) {
		return iPlatformMachineInfoDao.deleteMachineInfoAll(paramsMap);
	}

	@Override
	public Object savaMachaneToStaff(HashMap<String, Object> hashMap,LoginSession loginSession) {
		boolean retstr = false;
		List<Map<String, Object>> staffList =  iPlatformCompanyStaffDao.querystaffInfoByType(hashMap);
		if (staffList.size() == 1) {
			hashMap.put("staffid", staffList.get(0).get("id").toString());
//			hashMap.put("staffcode", staffList.get(0).get("staffcode").toString());
//			hashMap.put("staffname", staffList.get(0).get("staffname").toString());
//			hashMap.put("agentcode", staffList.get(0).get("agentcode").toString());
//			hashMap.put("agentname", staffList.get(0).get("agentname").toString());

			//hashMap.put("oDeptID", deptList.get(0).get("dept_id").toString());
			iPlatformMachineInfoDao.savaMachaneToStaff(hashMap);
			//


			//调拨成功插入调拨记录信息
			String machine_id = hashMap.get("machine_id").toString();
			HashMap<String ,Object> m = new HashMap<>();
			m.put("id",machine_id);
			List<Map<String, Object>> machineList =iPlatformMachineInfoDao.findMachineInfoList(m);
			//登陆员工
			String user_id = loginSession.getUser_id();

			//变更后员工信息
			String substaff_id = hashMap.get("staff_id").toString();
				//变更前员工信息

				String  login_id = hashMap.get("upstaffid").toString();
				//机具编号
				String machinecode = hashMap.get("machinecode").toString();
				//机具品牌
				String brindid = hashMap.get("brindid").toString();





			Map<String,Object> machinelogmap = new HashMap<>();
			machinelogmap.put("id", UUIDGenerator.generate());
			machinelogmap.put("create_time", new Date());
			machinelogmap.put("operate_userid", user_id);
			machinelogmap.put("operate_mode", "3");
			machinelogmap.put("create_time", new Date());
			machinelogmap.put("before_staffid", login_id);
			machinelogmap.put("after_staffid", substaff_id);
			machinelogmap.put("allocation_mode", '2');
			machinelogmap.put("machine_code", machinecode);
			machinelogmap.put("brand_id", brindid);
			iPlatformMachineLogDao.savaMachaneLog(machinelogmap);



			retstr = true;

		} else {
			return retstr;
		}
		return retstr;
	}

    @Override
    public PageList<?> getMchineInfoPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds, LoginSession loginSession) {
        //根据登陆账户所属员工信息过滤数据‘
        String loginname = loginSession.getUser_name();//登陆用户名
        String dept_ids =loginSession.getDept_id(); //登陆用户部门id
        if (dept_ids!=null && dept_ids.equals("0")){//管理员和服务商权限
            return iPlatformMachineInfoDao.queryMachineInfoPageList(paramsMap, pageBounds);
        }else {

            Map<String,Object> ma= new HashMap<String, Object>();
            ma.put("loginname",loginname);
            PageList<?> pageList = new PageList<>();
            //根据登陆名称查询员工信息
            List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.querystaffInfoByType(ma);
            if (staffmap!=null && staffmap.size()>0){
                String orgid = staffmap.get(0).get("id").toString();//员工id
                String ibrand_id = staffmap.get(0).get("brandid").toString();//品牌
                String branch = staffmap.get(0).get("branch").toString();//部门
                if (dept_ids!=null && dept_ids.equals("3")){
                    paramsMap.put("ibrand_id",ibrand_id);
                }else {
                    paramsMap.put("orgid",orgid);
                    paramsMap.put("ibrand_id",ibrand_id);
                    paramsMap.put("branch",branch);
                }

            }else{//账号未绑定员工
                paramsMap.put("id","-1");
            }
            return iPlatformMachineInfoDao.queryMachineInfoPageList(paramsMap, pageBounds);
        }
    }

	@Override
	public Object machineAllocation(HashMap<String, Object> hashMap ,LoginSession loginSession) {
		String Str= "";
		boolean retstr = false;
		///获取登陆员工信息
		String staffname = loginSession.getUser_name();
		String user_id = loginSession.getUser_id();
		Map<String,Object> pamap = new HashMap<>();
		pamap.put("loginname",staffname);
		List<Map<String, Object>>  staff= iPlatformCompanyStaffDao.querystaffInfoByType(pamap);
		if (staff==null){
			Str= "登陆用户,不是直营员工，不能调拨机具。";
			return false;
		}
		String login_id = staff.get(0).get("id").toString();
		String brandid = staff.get(0).get("brandid").toString();

		String allocation_mode = hashMap.get("allocation_mode").toString();//调拨方式
		String  substaff_id = hashMap.get("substaff_id").toString();
		HashMap<String,Object> pamap1 = new HashMap<>();
		pamap1.put("staff_id",substaff_id);
		List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.querystaffInfoByType(pamap1);
		String agentcode = "";
		String agentname = "";
		if (staffmap!=null && staffmap.size()>0){
			Object agentcodeo = staffmap.get(0).get("agentcode");
			Object agentnameo = staffmap.get(0).get("agentname");
			if (agentcodeo!=null){
				agentcode=agentcodeo.toString();
			}
			if (agentnameo!=null ){
				agentname= agentnameo.toString();
			}
		}else {
			Str= "找不到下级员工信息。";
			return Str;
		}


		if (allocation_mode!=null ){
			if (allocation_mode.equals("1")){//批次号
				String allocation_code = hashMap.get("allocation_code").toString();

				String [] alllist = allocation_code.split(",");

				for (String s : alllist) {
					HashMap<String ,Object> para= new HashMap<>();
					para.put("batchcode",s);
					//根据批次号查询机具信息
					List<Map<String, Object>>  mali = iPlatformMachineInfoDao.findMachineInfoList(para);
					if (mali!=null && mali.size()==0){
						for (Map<String, Object> stringObjectMap : mali) {
							String s_id = stringObjectMap.get("id").toString();
							String macode = stringObjectMap.get("machinecode").toString();

							String isbound = stringObjectMap.get("isbound").toString();
							String ifdeposit = stringObjectMap.get("ifdeposit").toString();


							if (!s_id.equals(login_id)){//机具信息没有和员工信息绑定不能调拨
								Str = Str+s+"机具号未和员工绑定，不能调拨";
								return Str;
							}

							if (isbound !=null && isbound.equals("1")){  //表示已绑定商户
								Str =Str+s+"机具号已绑定商户，不能调拨";
								return  Str;

							}
							if (ifdeposit!=null && ifdeposit.equals("是")){
								Str =Str+s+"机具号已绑定商户且已缴纳押金，不能调拨";
								return  Str;
							}

							HashMap<String ,Object> ma= new HashMap<>();
							ma.put("machinecode",macode);
							ma.put("substaff_id",substaff_id);
							ma.put("agentcode", agentcode);
							ma.put("agentname", agentname);

							iPlatformMachineInfoDao.machineAllocations(ma);

							//调拨成功插入调拨记录信息

							Map<String,Object> machinelogmap = new HashMap<>();
							machinelogmap.put("id", UUIDGenerator.generate());
							machinelogmap.put("create_time", new Date());
							machinelogmap.put("operate_userid", user_id);
							machinelogmap.put("operate_mode", "1");
							machinelogmap.put("create_time", new Date());
							machinelogmap.put("before_staffid", login_id);
							machinelogmap.put("after_staffid", substaff_id);
							machinelogmap.put("allocation_mode", allocation_mode);
							machinelogmap.put("machine_code", macode);
							machinelogmap.put("brand_id", brandid);
							iPlatformMachineLogDao.savaMachaneLog(machinelogmap);

						}
					}else {//批次号下没有机具信息，数据错误
						Str = Str+s+"数据错误";
						return  Str;
					}


				}
				Str = "调拨成功";
				retstr= true;

			}else if (allocation_mode.equals("2")){//机具编号
				String allocation_code = hashMap.get("allocation_code").toString();

				String [] alllist = allocation_code.split("\r\n");

				for (String s : alllist) {
					String staffid = "";
					HashMap<String ,Object> para= new HashMap<>();
					para.put("machinecode",s);
					para.put("orgid",login_id);
					//根据机具编号查询机具信息
					List<Map<String, Object>>  mali = iPlatformMachineInfoDao.findMachineInfoList(para);
					if (mali==null || mali.size()<=0){//登陆员工未绑定机具信息
						Str = Str+s+"机具号未和员工绑定，不能调拨";
						return  Str;
					}else  if(mali.size()==1){
						String isbound = mali.get(0).get("isbound").toString();
						String ifdeposit = mali.get(0).get("ifdeposit").toString();
						if (isbound !=null && isbound.equals("1")){  //表示已绑定商户
							Str =Str+s+"机具号已绑定商户，不能调拨";
							return  Str;

						}
						if (ifdeposit!=null && ifdeposit.equals("是")){
							Str =Str+s+"机具号已绑定商户且已缴纳押金，不能调拨";
							return  Str;
						}
						staffid=mali.get(0).get("staffid").toString();
					}else if (mali.size()>1){
						Str = Str+s+"数据错误";
						return  Str;
					}

					HashMap<String ,Object> ma= new HashMap<>();
					ma.put("machinecode",s);

					ma.put("substaff_id",substaff_id);
					ma.put("agentcode", agentcode);
					ma.put("agentname", agentname);
					iPlatformMachineInfoDao.machineAllocation(ma);

					//调拨成功插入调拨记录
					Map<String,Object> machinelogmap = new HashMap<>();
					machinelogmap.put("id", UUIDGenerator.generate());
					machinelogmap.put("create_time", new Date());
					machinelogmap.put("operate_userid", user_id);
					machinelogmap.put("operate_mode", "1");
					machinelogmap.put("create_time", new Date());
					machinelogmap.put("before_staffid", staffid);
					machinelogmap.put("after_staffid", substaff_id);
					machinelogmap.put("allocation_mode", allocation_mode);
					machinelogmap.put("machine_code", s);
					machinelogmap.put("brand_id", brandid);
					iPlatformMachineLogDao.savaMachaneLog(machinelogmap);
				}
				Str = "调拨成功";
				retstr= true;

			}else if (allocation_mode.equals("3")){//区间调拨


				String mcode_start = hashMap.get("mcode_start").toString();
				String mcode_end = hashMap.get("mcode_end").toString();

				long a =Long.valueOf(mcode_start);
				long b =Long.valueOf(mcode_end);
				for (long i = a ; a<=b ;a++){
			        String machinecode = "0000"+String.valueOf(a);
					HashMap<String ,Object> para= new HashMap<>();
					para.put("machinecode",machinecode);
					para.put("orgid",login_id);

					//根据机具编号查询机具信息
					List<Map<String, Object>>  mali = iPlatformMachineInfoDao.findMachineInfoList(para);

					String staffid = "";
					if (mali==null || mali.size()<=0){//登陆员工未绑定机具信息
						Str = Str+machinecode+"机具号未和员工绑定，不能调拨";
						return  Str;
					}else  if(mali.size()==1){
						String isbound = mali.get(0).get("isbound").toString();
						String ifdeposit = mali.get(0).get("ifdeposit").toString();
						if (isbound !=null && isbound.equals("1")){  //表示已绑定商户
							Str =Str+machinecode+"机具号已绑定商户，不能调拨";
							return  Str;

						}
						if (ifdeposit!=null && ifdeposit.equals("是")){
							Str =Str+machinecode+"机具号已绑定商户且已缴纳押金，不能调拨";
							return  Str;
						}
						staffid=mali.get(0).get("staffid").toString();
					}else if (mali.size()>1){
						Str = Str+machinecode+"数据错误";
						return  Str;
					}

					HashMap<String ,Object> ma= new HashMap<>();
					ma.put("machinecode",machinecode);

					ma.put("substaff_id",substaff_id);
					ma.put("agentcode", agentcode);
					ma.put("agentname", agentname);
					iPlatformMachineInfoDao.machineAllocation(ma);

					//调拨成功插入调拨记录
					Map<String,Object> machinelogmap = new HashMap<>();
					machinelogmap.put("id", UUIDGenerator.generate());
					machinelogmap.put("create_time", new Date());
					machinelogmap.put("operate_userid", user_id);
					machinelogmap.put("operate_mode", "1");
					machinelogmap.put("create_time", new Date());
					machinelogmap.put("before_staffid", staffid);
					machinelogmap.put("after_staffid", substaff_id);
					machinelogmap.put("allocation_mode", allocation_mode);
					machinelogmap.put("machine_code", machinecode);
					machinelogmap.put("brand_id", brandid);
					iPlatformMachineLogDao.savaMachaneLog(machinelogmap);
				}
				Str = "调拨成功";
				retstr= true;

			}
		}
		Str = "调拨成功";
		return Str;
	}

	@Override
	public Object machineCallback(HashMap<String, Object> hashMap ,LoginSession loginSession) {
		boolean retstr = false;
		String Str = "";
		//获取登陆员工信息
		String staffname = loginSession.getUser_name();
		String user_id = loginSession.getUser_id();
		Map<String,Object> pamap = new HashMap<>();
		pamap.put("loginname",staffname);
		List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.querystaffInfoByType(pamap);

		String brandid = "";
		String substaff_id ="";
		String agentcode = "";
		String agentname = "";
		if (staffmap!=null && staffmap.size()==1){
			substaff_id= staffmap.get(0).get("id").toString();
			agentcode = staffmap.get(0).get("agentcode")!=null ? staffmap.get(0).get("agentcode").toString():"";
			agentname = staffmap.get(0).get("agentname")!=null ? staffmap.get(0).get("agentname").toString():"";
			brandid = staffmap.get(0).get("brandid").toString();
		}else {//登陆账户未绑定员工信息
			Str = Str+"登陆账户未绑定员工信息";
			return Str;
		}
		HashMap<String ,Object> bus= new HashMap<>();
		bus.put("orgid",substaff_id);
		//登陆用户下级员工信息
		List<Map<String, Object>>  substaffmap= iPlatformCompanyStaffDao.querystaffInfoByType(bus);

		String allocation_mode = hashMap.get("allocation_mode").toString();
		String allocation_code = hashMap.get("allocation_code").toString();
		//String substaff_id = hashMap.get("substaff_id").toString();

		String[] alllist = allocation_code.split(",");

		if (allocation_mode != null) {
			if (allocation_mode.equals("1")) {//批次号
				for (String s : alllist) {
					HashMap<String ,Object> para= new HashMap<>();
					para.put("machinecode",s);
					//根据机具编号查询机具信息
					List<Map<String, Object>>  mali = iPlatformMachineInfoDao.findMachineInfoList(para);
					if (mali!=null && mali.size()>0){
						for (Map<String, Object> stringObjectMap : mali) {
							boolean bool = false;
							String s_id = stringObjectMap.get("staffid").toString();
							String macode = stringObjectMap.get("machinecode").toString();
							String isbound = mali.get(0).get("isbound").toString();
							String ifdeposit = mali.get(0).get("ifdeposit").toString();
							if (s_id.equals(substaff_id)){
								Str =Str+s+"机具号已绑定当前员工";
								return  Str;
							}
							if (isbound !=null && isbound.equals("1")){  //表示已绑定商户
								Str =Str+s+"机具号已绑定商户，不能回调";
								return  Str;

							}
							if (ifdeposit!=null && ifdeposit.equals("是")){
								Str =Str+s+"机具号已绑定商户且已缴纳押金，不能回调";
								return  Str;
							}
							for (Map<String, Object> objectMap : substaffmap) {
								String loginid = objectMap.get("id").toString();
								if (s_id.equals(loginid)){//机具信息没有和员工信息绑定不能调拨
									bool = true;
								}
							}
							if (!bool){
								Str = Str+macode+"机具信息不在员工下级不能回调";
								return retstr;
							}
							Map<String ,Object> ma= new HashMap<>();
							ma.put("machinecode",macode);
							ma.put("substaff_id",substaff_id);
							ma.put("agentcode", agentcode);
							ma.put("agentname", agentname);
							iPlatformMachineInfoDao.machineAllocation(ma);

							//插入调拨记录
							Map<String,Object> machinelogmap = new HashMap<>();
							machinelogmap.put("id", UUIDGenerator.generate());
							machinelogmap.put("create_time", new Date());
							machinelogmap.put("operate_userid", user_id);
							machinelogmap.put("operate_mode", "2");
							machinelogmap.put("create_time", new Date());
							machinelogmap.put("before_staffid", s_id);
							machinelogmap.put("after_staffid", substaff_id);
							machinelogmap.put("allocation_mode", allocation_mode);
							machinelogmap.put("machine_code", macode);
							machinelogmap.put("brand_id", brandid);
							iPlatformMachineLogDao.savaMachaneLog(machinelogmap);

						}
					}else {//批次号下没有机具信息，数据错误
						Str = Str+"批次号下没有机具信息，数据错误";
						return  Str;
					}

				}
				Str = "回调成功";
				retstr = true;

			} else if (allocation_mode.equals("2")) {//机具编号
				for (String s : alllist) {
					HashMap<String ,Object> para= new HashMap<>();
					para.put("machinecode",s);
					para.put("orgid",substaff_id);
					//根据机具编号查询机具信息
					List<Map<String, Object>>  mali = iPlatformMachineInfoDao.findMachineInfoList(para);
					if (mali==null && mali.size()<=0){//登陆员工未绑定机具信息
						Str = Str+s+"机具号不在员工下级后台";
						return  Str;
					}else if (mali.size()==1){
						String isbound = mali.get(0).get("isbound").toString();
						String ifdeposit = mali.get(0).get("ifdeposit").toString();
						if (isbound !=null && isbound.equals("1")){  //表示已绑定商户
							Str =Str+s+"机具号已绑定商户，不能回调";
							return  Str;

						}
						if (ifdeposit!=null && ifdeposit.equals("是")){
							Str =Str+s+"机具号已绑定商户且已缴纳押金，不能回调";
							return  Str;
						}
					}else if (mali.size()>1){
						Str =Str+s+"数据错误";
						return  Str;
					}
					Map<String ,Object> ma= new HashMap<>();
					ma.put("machinecode",s);
					ma.put("substaff_id",substaff_id);
					ma.put("agentcode", agentcode);
					ma.put("agentname", agentname);
					iPlatformMachineInfoDao.machineAllocation(ma);
					//插入调拨记录
					Map<String,Object> machinelogmap = new HashMap<>();
					machinelogmap.put("id", UUIDGenerator.generate());
					machinelogmap.put("create_time", new Date());
					machinelogmap.put("operate_userid", user_id);
					machinelogmap.put("operate_mode", "2");
					machinelogmap.put("create_time", new Date());
					//调拨前绑定的员工id
					String beforeid = mali.get(0).get("staffid").toString();
					machinelogmap.put("before_staffid", beforeid);
					machinelogmap.put("after_staffid", substaff_id);
					machinelogmap.put("allocation_mode", allocation_mode);
					machinelogmap.put("machine_code", allocation_code);
					machinelogmap.put("brand_id", brandid);
					iPlatformMachineLogDao.savaMachaneLog(machinelogmap);
				}
				Str = "回调成功";
				retstr = true;

			}
		}
		Str = "回调成功";
		return Str;
	}

	@Override
	public Object machineCallbackSingle(HashMap<String, Object> hashMap ,LoginSession loginSession) {
		boolean retstr = false;
		String Str = "";
		//获取登陆员工信息
		String staffname = loginSession.getUser_name();
		String user_id = loginSession.getUser_id();
		Map<String,Object> pamap = new HashMap<>();
		pamap.put("loginname",staffname);
		List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.querystaffInfoByType(pamap);

		String brandid = "";
		String substaff_id ="";
		String agentcode = "";
		String agentname = "";
		if (staffmap!=null && staffmap.size()==1){
			substaff_id= staffmap.get(0).get("id").toString();
			agentcode = staffmap.get(0).get("agentcode")!=null ? staffmap.get(0).get("agentcode").toString():"";
			agentname = staffmap.get(0).get("agentname")!=null ? staffmap.get(0).get("agentname").toString():"";
			brandid = staffmap.get(0).get("brandid").toString();
		}else {//登陆账户未绑定员工信息
			Str = Str+"登陆账户未绑定员工信息,不能机具回调";
			return false;
		}
		HashMap<String ,Object> bus= new HashMap<>();
		bus.put("orgid",substaff_id);
		//登陆用户下级员工信息
		List<Map<String, Object>>  substaffmap= iPlatformCompanyStaffDao.querystaffInfoByType(bus);

		String machine_id = hashMap.get("machine_id").toString();

		HashMap<String ,Object> para= new HashMap<>();
		para.put("id",machine_id);
		//根据机具编号查询机具信息
		List<Map<String, Object>>  mali = iPlatformMachineInfoDao.findMachineInfoList(para);

		if (mali!=null && mali.size()>0){

			for (Map<String, Object> stringObjectMap : mali) {
				Boolean bool = false;
				String s_id = stringObjectMap.get("staffid").toString();
				String macode = stringObjectMap.get("machinecode").toString();

				String isbound = mali.get(0).get("isbound").toString();
				String ifdeposit = mali.get(0).get("ifdeposit").toString();
				if (substaff_id.equals(s_id)){
					Str =Str+macode+"机具号已绑定当前员工";
					return  Str;
				}
				if (isbound !=null && isbound.equals("1")){  //表示已绑定商户
					Str =Str+macode+"机具号已绑定商户，不能回调";
					return  Str;

				}
				if (ifdeposit!=null && ifdeposit.equals("是")){
					Str =Str+macode+"机具号已绑定商户且已缴纳押金，不能回调";
					return  Str;
				}

				for (Map<String, Object> objectMap : substaffmap) {
					String loginid = objectMap.get("id").toString();
					if (s_id.equals(loginid)){//
						bool=true;
					}
				}
				if (!bool){
					Str =Str+macode+"机具信息未与员工信息绑定，不能回调";
					return  Str;
				}
				Map<String ,Object> ma= new HashMap<>();
				ma.put("machinecode",macode);
				ma.put("substaff_id",substaff_id);
				ma.put("agentcode", agentcode);
				ma.put("agentname", agentname);
				iPlatformMachineInfoDao.machineAllocation(ma);

				//插入调拨记录
				Map<String,Object> machinelogmap = new HashMap<>();
				machinelogmap.put("id", UUIDGenerator.generate());
				machinelogmap.put("create_time", new Date());
				machinelogmap.put("operate_userid", user_id);
				machinelogmap.put("operate_mode", "2");
				machinelogmap.put("create_time", new Date());
				machinelogmap.put("before_staffid", s_id);
				machinelogmap.put("after_staffid", substaff_id);
				machinelogmap.put("allocation_mode", "2");
				machinelogmap.put("machine_code", macode);
				machinelogmap.put("brand_id", brandid);
				iPlatformMachineLogDao.savaMachaneLog(machinelogmap);

			}
			retstr = true;
		}else {//批次号下没有机具信息，数据错误
			Str =Str+"机具信息不存在，数据错误";
			return  Str;
		}
		Str= "回调成功";
		return Str;
	}


	@Override
	public List<Map<String, Object>> exportMachineReportInfo(Map<String, Object> map,LoginSession loginSession) {

        //根据登陆账户所属员工信息过滤数据‘
        String loginname = loginSession.getUser_name();//登陆用户名
        String dept_ids =loginSession.getDept_id(); //登陆用户部门id

        if (dept_ids!=null && dept_ids.equals("0")){//管理员和服务商权限
            return iPlatformMachineInfoDao.exportMachineReportInfo(map);
        }else {

            Map<String,Object> ma= new HashMap<String, Object>();
            ma.put("loginname",loginname);
            PageList<?> pageList = new PageList<>();
            //根据登陆名称查询员工信息
            List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.querystaffInfoByType(ma);
            if (staffmap!=null && staffmap.size()>0){
                String orgid = staffmap.get(0).get("id").toString();//员工id
                String ibrand_id = staffmap.get(0).get("brandid").toString();//品牌
                String branch = staffmap.get(0).get("branch").toString();//部门

                if (dept_ids!=null && dept_ids.equals("3")){
                    map.put("ibrand_id",ibrand_id);
                }else {
                    map.put("orgid",orgid);
                    map.put("ibrand_id",ibrand_id);
                    map.put("branch",branch);
                }
                if (staffmap.get(0).containsKey("loginuserid") ){
                    Object loginuserid = staffmap.get(0).get("loginuserid");//登陆账号
                    if (loginuserid==null || "".equals(loginuserid)){
                        map.put("id","-1");
                    }
                }
            }else{//账号未绑定员工
                map.put("id","-1");
            }
            return iPlatformMachineInfoDao.exportMachineReportInfo(map);

        }
	}

	@Override
	public Map<String, Object> countMachineInfo(HashMap<String, Object> hashMap) {
		// 查询代理商日报表符合条件的记录总数
		return iPlatformMachineInfoDao.countMachineInfo(hashMap);
	}
}
