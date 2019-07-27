package com.acl.utils.mongo;

import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;

/** 
 * @author: chenwei
 * @version：1.0 
 * @创建时间：2016年7月28日 上午10:44:52 
 * @说明：
 *
 */
public class CriteriaUtil {
	public static Criteria[] convertMap(Map<String, Object> map){
  	Criteria[] ce = new Criteria[map.size()];
 	 	int i=0;
 	 	for (String key:map.keySet()) {
 		 ce[i] = Criteria.where(key).is(map.get(key));
 		 i++;
			
		}
 	 	return ce;
 }
}
