package com.acl.platform.service;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *className:IPlatformFrontUserInfo
 *author:wangli
 *createDate:2016年8月25日 下午2:56:53
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IPlatformSuggestInfoService {


	PageList<?> querySuggestInfo(HashMap<String, Object> paramsMap,
                              PageBounds pageBounds);



}
