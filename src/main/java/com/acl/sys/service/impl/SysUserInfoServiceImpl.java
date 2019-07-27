package com.acl.sys.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.goods.dao.UserCapitalDao;
import com.acl.goods.dao.UserInfoDao;
import com.acl.platform.dao.FrontSalaAgentInfoDao;
import com.acl.pojo.FrontSalaAgentInfo;
import com.acl.pojo.UserCapital;
import com.acl.pojo.UserInfo;
import com.acl.sys.dao.SysUserInfoDao;
import com.acl.utils.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.sys.service.SysUserInfoService;
import com.acl.utils.msg.MobileMsgSend;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * className:SysUserInfoServiceImpl author:malx createDate:2016年8月10日 下午5:52:44
 * vsersion:3.0 department:安创乐科技 description:
 */
@Service
@Transactional
public class SysUserInfoServiceImpl implements SysUserInfoService {


	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	private UserCapitalDao userCapitalDao;

	@Autowired
	private SysUserInfoDao sysUserInfoDao;

	@Autowired
	private FrontSalaAgentInfoDao frontSalaAgentInfoDao;

	public PageList<?> queryUserInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return sysUserInfoDao.queryUserInfo(paramsMap, pageBounds);
	}

	@Override
	public void insertUserInfo(HashMap<String, Object> paramsMap) {
		sysUserInfoDao.insertUserInfo(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryTreeDeptInfo(Map<String, Object> map) {
		return sysUserInfoDao.queryTreeDeptInfo(map);
	}

	@Override
	public List<Map<String, Object>> userValidate(HashMap<String, Object> paramsMap) {
		return sysUserInfoDao.userValidate(paramsMap);
	}

	@Override
	public List<Map<String, Object>> mobileValidate(HashMap<String, Object> paramsMap) {
		return sysUserInfoDao.mobileValidate(paramsMap);
	}

	@Override
	public void updateUserInfo(HashMap<String, Object> paramsMap) {
		sysUserInfoDao.updateUserInfo(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryRoleMenuBase(Map<String, Object> map) {
		return sysUserInfoDao.queryRoleMenuBase(map);
	}

	@Override
	public void resetUserInfoPwd(HashMap<String, Object> paramsMap) {
		sysUserInfoDao.resetUserInfoPwd(paramsMap);
	}

	@Override
	public void sendPhoneCode(String mobile, String message,String rd) throws Exception {
		MobileMsgSend mobileMsgSend = new MobileMsgSend();
		mobileMsgSend.sendPhoneCode(mobile, message, true, rd, null);
	}

	@Override
	public List<Map<String, Object>> queryUserDeptInfo(Map<String, Object> paramsMap) {
		return sysUserInfoDao.queryUserDeptInfo(paramsMap);
	}

	@Override
	public void deleteUserRole(Map<String, Object> paramsMap) {
		sysUserInfoDao.deleteUserRole(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryDeptInfoByChildList(Map<String, Object> paramsMap) {
		return sysUserInfoDao.queryDeptInfoByChildList(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryUserBalanceInfo(Map<String, Object> map) {
		return sysUserInfoDao.queryUserBalanceInfo(map);
	}

	@Override
	public PageList<?> queryReportUserBalanceInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return sysUserInfoDao.queryReportUserBalanceInfo(paramsMap, pageBounds);
	}
	
	@Override
	public List<Map<String, Object>> queryUserBalanceExport(Map<String, Object> map) {
		return sysUserInfoDao.queryUserBalanceExport(map);
	}

	@Override
	public void updateUserMobile(Map<String, Object> map) {
		sysUserInfoDao.updateUserMobile(map);
	}

	@Override
	public int saveUserInfo(UserInfo userInfo) {
		UserCapital userCapital = new UserCapital();
		userCapital.setId(UUIDGenerator.generate());
		userCapital.setAmount(new BigDecimal("0"));
		userCapital.setStatus(1);
		userCapital.setUserId(userInfo.getId());
		userCapital.setCreateTime(new Date());
		userCapitalDao.insertSelective(userCapital);
		return userInfoDao.insertSelective(userInfo);
	}

	@Override
	public List<UserInfo> getUserInfoList() {
		return userInfoDao.getUserInfoList(null);
	}

	@Override
	public int updateUserInfo(UserInfo userInfo) {
		return userInfoDao.updateByPrimaryKeySelective(userInfo);
	}

	@Override
	public UserInfo getUserInfoById(String id) {
		return userInfoDao.selectByPrimaryKey(id);
	}

	@Override
	public int saveUserInfoAndFrontSalaAgentInfo(UserInfo userInfo,FrontSalaAgentInfo frontSalaAgentInfo) {
		UserCapital userCapital = new UserCapital();
		userCapital.setId(UUIDGenerator.generate());
		userCapital.setAmount(new BigDecimal("0"));
		userCapital.setStatus(1);
		userCapital.setUserId(userInfo.getId());
		userCapital.setCreateTime(new Date());
		userCapitalDao.insertSelective(userCapital);
		userInfoDao.insertSelective(userInfo);
		frontSalaAgentInfo.setUserId(userInfo.getId());
		return frontSalaAgentInfoDao.insertSelective(frontSalaAgentInfo);
	}

	@Override
	public int updateUserInfoAndFrontSalaAgentInfo(UserInfo userInfo, FrontSalaAgentInfo frontSalaAgentInfo) {
		userInfoDao.updateByPrimaryKeySelective(userInfo);
		frontSalaAgentInfo.setUserId(userInfo.getId());
		return frontSalaAgentInfoDao.insertSelective(frontSalaAgentInfo);
	}
}
