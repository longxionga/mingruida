package com.acl.sys.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.util.UUIDGenerator;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.component.MongodbBaseDao;
import com.acl.sys.dao.ISysCenterInfoDao;
import com.acl.sys.service.ISysCenterInfoService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.StringUtils;

/**
 *className:SysCenterInfoSereviceImpl
 *author:wangli
 *createDate:2016年8月16日 下午1:58:15
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Service
@Transactional
public class SysCenterInfoSereviceImpl implements ISysCenterInfoService {
	@Autowired 
	private ISysCenterInfoDao sysCenterInfoDao;
	
	@Autowired
	private MongodbBaseDao<T> mongodbBaseDao;
	
	protected MongoTemplate mongoTemplate;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public PageList<?> queryCenterInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		 return sysCenterInfoDao.queryCenterInfo(paramsMap, pageBounds);
	}
	
	@Override
	public void insertCenterInfo(HashMap<String, Object> paramsMap) {
		paramsMap.put("center_id", UUIDGenerator.generate());
		paramsMap.put("create_date", sdf.format(new Date()));
		sysCenterInfoDao.insertCenterInfo(paramsMap);
		//mongodbBaseDao.save(paramsMap, "centerInfo");
	}

	@Override
	public List<Map<String, Object>> CenterValidate(HashMap<String, Object> paramsMap) {
		return sysCenterInfoDao.CenterValidate(paramsMap);
	}

	@Override
	public void updateCenterInfo(HashMap<String, Object> paramsMap) {
		paramsMap.put("create_date", sdf.format(new Date()));
		sysCenterInfoDao.updateCenterInfo(paramsMap);
		Map<String,Object> map=new HashMap<>();
		map.put("center_id", paramsMap.get("center_id").toString());
		Update update = new Update();
		update.set("center_title", paramsMap.get("center_title").toString());
		update.set("center_text", paramsMap.get("center_text").toString());		
		update.set("is_use", paramsMap.get("is_use").toString());
		update.set("center_type", paramsMap.get("center_type").toString());
		update.set("center_number", paramsMap.get("center_number").toString());
		update.set("center_abstract", paramsMap.get("center_abstract").toString());
		update.set("is_important", paramsMap.get("is_important").toString());
		update.set("dept_id", StringUtils.checkString(paramsMap.get("dept_id")));
		update.set("dept_type", paramsMap.get("dept_type").toString());
		update.set("create_date", sdf.format(new Date()));
		//mongodbBaseDao.updateFirst(map, update, "centerInfo");
	}

	@Override
	public void deleteCenterInfo(HashMap<String, Object> paramsMap) {
		sysCenterInfoDao.deleteCenterInfo(paramsMap);
		//mongodbBaseDao.remove(paramsMap, "centerInfo");
	}

	@Override
	public PageList<?> queryMsg(HashMap<String, Object> paramsMap) {
		return sysCenterInfoDao.queryMsg(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryMsgInfo(HashMap<String, Object> paramsMap) {
		// 主页查询消息
		return sysCenterInfoDao.queryMsgInfo(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryMessage(HashMap<String, Object> paramsMap) {
		return sysCenterInfoDao.queryMessage(paramsMap);
	}
}
