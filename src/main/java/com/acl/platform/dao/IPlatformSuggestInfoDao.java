package com.acl.platform.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *className:IPlatformUserInfoDao
 *author:wangli
 *createDate:2016年8月25日 下午3:03:04
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IPlatformSuggestInfoDao {

	PageList<?> querySuggestInfo(HashMap<String, Object> paramsMap,
                              PageBounds pageBounds);


}
