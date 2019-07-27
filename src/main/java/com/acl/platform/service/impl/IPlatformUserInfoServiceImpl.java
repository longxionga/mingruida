package com.acl.platform.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.platform.dao.FrontSalaAgentInfoDao;
import com.acl.pojo.UserInfo;
import com.acl.sys.service.SysUserInfoService;
import com.acl.utils.util.DateFormatUtil;
import com.acl.utils.util.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.acl.component.MongodbBaseDao;
import com.acl.component.SystemConfig;
import com.acl.platform.dao.IPlatformUserInfoDao;
import com.acl.platform.service.IPlatformUserInfoService;
import org.springframework.data.mongodb.core.query.Update;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.MD5Utils;
/**
 *className:IPlatformUserInfoServiceImpl
 *author:wangli
 *createDate:2016年8月25日 下午2:57:55
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Service
@Transactional
public class IPlatformUserInfoServiceImpl implements IPlatformUserInfoService {
	@Autowired
	private IPlatformUserInfoDao platformUserInfoDao;
	
	@Autowired
	private MongodbBaseDao<T> mongodbBaseDao;

	@Autowired
	private SysUserInfoService sysUserInfoService;

	@Autowired
	private FrontSalaAgentInfoDao frontSalaAgentInfoDao;

	@Override
	public void updateUserInfoPwd(HashMap<String, Object> paramsMap) {
		UserInfo  userInfo= sysUserInfoService.getUserInfoById((String) paramsMap.get("id"));
		if(userInfo!=null){
			userInfo.setPassword(MD5Utils.sign(StringUtils.substring(userInfo.getIdCard(),userInfo.getIdCard().length()-6,userInfo.getIdCard().length()), MD5Utils.PWD_KEY, MD5Utils.DEFAULT_UTF_8_INPUT_CHARSET));
			paramsMap.put("password", userInfo.getPassword());
			platformUserInfoDao.updateUserInfoPwd(paramsMap);
		}

	}

	@Override
	public void updateUserInfoState(HashMap<String, Object> paramsMap) {
		platformUserInfoDao.updateUserInfoState(paramsMap);

	}


	@Override
	public HashMap<String, Object>  selectUserCz(HashMap<String, Object> paramsMap) {
		return platformUserInfoDao.selectUserCz(paramsMap);
	}

	@Override
	public void logoutUserInfoState(HashMap<String, Object> paramsMap) {
		platformUserInfoDao.logoutUserInfoState(paramsMap);

	}

	/*	@Override
	public Pagination<T> queryUserInfo(HashMap<String, Object> paramsMap)  {
		int pageNo=Integer.parseInt(paramsMap.get("page").toString());
		int pageSize=Integer.parseInt(paramsMap.get("rows").toString());
		Map<String, Object> map=new HashMap<>();
		if(paramsMap.get("user_name")!=null){
			map.put("user_name", paramsMap.get("user_name").toString());
		}
		try {
			 mongodbBaseDao.pagingQuery1(pageNo, pageSize, map, "t_front_user_login");

		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null;
	}   */

	//从数据库查询所有数据
	@Override
	public PageList<?> queryUserInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return platformUserInfoDao.queryUserInfo(paramsMap,pageBounds);
	}

	@Override
	public void updateUserBroker(HashMap<String, Object> paramsMap) {
		platformUserInfoDao.updateUserBroker(paramsMap);


	}

	@Override
	public void deleteUserBroker(HashMap<String, Object> paramsMap) {
		platformUserInfoDao.deleteUserBroker(paramsMap);
	}

	@Override
	public void insertUserBroker(HashMap<String, Object> paramsMap) {
		platformUserInfoDao.insertUserBroker(paramsMap);
	}

	@Override
	public void updateUserWeiXinInfo(HashMap<String, Object> paramsMap) {
		platformUserInfoDao.updateUserWeiXinInfo(paramsMap);

	}

	@Override
	public List<Map<String, Object>> queryBrokerLevel(HashMap<String, Object> paramsMap) {
		return platformUserInfoDao.queryBrokerLevel(paramsMap);
	}

	@Override
	public void logoutBrokerInfoState(HashMap<String, Object> paramsMap) {
		platformUserInfoDao.logoutBrokerInfoState(paramsMap);
		//操作mongodb

		
	}

	@Override
	public void updateNumberState(HashMap<String, Object> paramsMap) {
		platformUserInfoDao.updateNumberState(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryUserBalanceSum(Map<String, Object> map) {
		return platformUserInfoDao.queryUserBalanceSum(map);
	}



	@Override
	public int updateUserInfoidCardAuth(HashMap<String, Object> paramsMap) {
		return platformUserInfoDao.updateUserInfoidCardAuth(paramsMap);
	}


	@Override
	public void updateCloseAll(HashMap<String, Object> paramsMap) {
		paramsMap.put("status", 2);
		platformUserInfoDao.updateCloseAll(paramsMap);
	}

	//从数据库查询所有数据
	@Override
	public PageList<?> queryUserAgentInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return platformUserInfoDao.queryUserAgentInfo(paramsMap,pageBounds);
	}



	@Override
	public int deleteSalaAgentAll(HashMap<String, Object> paramsMap) {
		return frontSalaAgentInfoDao.deleteSalaAgentAll(paramsMap);
	}

	@Override
	public int insertDataMerchant(Map<String, Object> paramsMap) {
		return platformUserInfoDao.insertDataMerchant(paramsMap);
	}

	@Override
	public int insertDataTradeOrder(Map<String, Object> paramsMap) {
		return platformUserInfoDao.insertDataTradeOrder(paramsMap);
	}

	@Override
	public int insertDataTradeOrderAll(Map<String, Object> paramsMap) {
		return platformUserInfoDao.insertDataTradeOrderAll(paramsMap);
	}


	//从数据库查询所有数据
	@Override
	public PageList<?> queryUserTransf(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return platformUserInfoDao.queryUserTransf(paramsMap,pageBounds);
	}

	@Override
	public int insertUserTransf(HashMap<String, Object> paramsMap) {
		String[] merchantIds = paramsMap.get("merchantIds").toString().split(",");
		paramsMap.put("merchantIds", merchantIds);
		platformUserInfoDao.deleteUserTransf(paramsMap);
		//String user_id = (String) paramsMap.get("user_id");
		//经纪人更换部门更新mongodb
		if (merchantIds.length > 0) {
			for (int i = 0; i < merchantIds.length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("broker_code", merchantIds[i]);
				map.put("id", UUIDGenerator.generate());
				map.put("user_id",  (String) paramsMap.get("user_id"));
				map.put("create_time",new Date());
				map.put("create_day", DateFormatUtil.convertDate(new Date(),"yyyyMMdd"));
				platformUserInfoDao.insertUserTransf(map);
			}
		}
		return merchantIds.length ;
	}
	//从数据库查询所有数据
	@Override
	public PageList<?> giftMerchantInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return platformUserInfoDao.giftMerchantInfo(paramsMap,pageBounds);
	}

	@Override
	public int savaAgentToNdept(HashMap<String, Object> paramsMap) {
		return platformUserInfoDao.savaAgentToNdept(paramsMap);
	}
	@Override
	public int insertDataMachineInfoAll(Map<String, Object> paramsMap) {
		return platformUserInfoDao.insertDataMachineInfoAll(paramsMap);
	}
	@Override
	public int insertDataHKMachineInfoAll(Map<String, Object> paramsMap) {
		return platformUserInfoDao.insertDataHKMachineInfoAll(paramsMap);
	}

    @Override
    public int insertHKDataTradeOrderAll(Map<String, Object> paramsMap) {
        return platformUserInfoDao.insertHKDataTradeOrderAll(paramsMap);
    }

	@Override
	public int insertHKMerchant(Map<String, Object> paramsMap) {
		return platformUserInfoDao.insertHKMerchant(paramsMap);
	}
}


