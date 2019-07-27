package com.acl.report.service.impl;


import com.acl.report.dao.IReportStaffWagesDao;
import com.acl.report.service.IReportStaffWagesService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.DoubleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *className:IPatformMachineInfoServiceImpl
 *author:longxionga
 *createDate:2019年4月30日
 *department:铭锐达
 *description:充值券查询
 */
@Service
@Transactional
public class IReportStaffWagesServiceImpl implements IReportStaffWagesService {

	@Autowired
	private IReportStaffWagesDao iReportStaffWagesDao;


	@Override
	public PageList<?> queryStaffWagesPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		PageList<?>  stawalist = null ;
		if (paramsMap!=null && paramsMap.get("position")!=null && !"".equals(paramsMap.get("brindnameid"))){
			if(paramsMap.get("brindnameid").equals("6")){//乐刷刷宝
				 stawalist=iReportStaffWagesDao.queryshuabaoStaffWagesPageList(paramsMap, pageBounds);
				if(paramsMap.get("position")!=null && !"".equals(paramsMap.get("position")) && paramsMap.get("position").equals("2")){//主管流水提成= 主管流水+组员流水
					paramsMap.put("position","3");
					List<Map<String, Object>>  mp= iReportStaffWagesDao.queryshuabaoStaffPaymentFlowSum(paramsMap);
					    this.shuabaoStaffWages(stawalist,mp);

				}
			}

		}else {
           stawalist=iReportStaffWagesDao.queryshuabaoStaffWagesPageList(paramsMap, pageBounds);
		//	this.shuabaoStaffWages(stawalist,null);
        }
		return stawalist;
	}

    private void shuabaoStaffWages(PageList<?> stawalist, List<Map<String, Object>> mp) {
	    if (stawalist!=null && stawalist.size()>0){
            for (int i = 0; i < stawalist.size(); i++) {
				Map<String,Object> map = (Map<String, Object>) stawalist.get(i);
				String s= String.valueOf(map.get("id"));
				Double jjkxkzf1= Double.valueOf(String.valueOf(map.get("jjkxkzf")));
				Double jjkkjzf1= Double.valueOf(String.valueOf(map.get("jjkkjzf")));
                if (mp!=null && mp.size()>0){
					for (int j = 0; j < mp.size(); j++) {
						Map<String, Object> map2 = mp.get(j);
						String p= String.valueOf(map2.get("parentid"));
						Double jjkxkzf2= Double.valueOf(String.valueOf(map2.get("jjkxkzf")));
						Double jjkkjzf2= Double.valueOf(String.valueOf(map2.get("jjkkjzf")));

						if (s.equals(p)){
							jjkxkzf1 = DoubleUtils.add(jjkxkzf1,jjkxkzf2);
							jjkkjzf1=DoubleUtils.add(jjkkjzf1,jjkkjzf2);
						}

					}

				}
				Double skzftc = DoubleUtils.mul(jjkxkzf1,0.0003);
				Double kjzftc = DoubleUtils.mul(jjkkjzf1,0.0002);
				Double hj= DoubleUtils.add(skzftc,kjzftc);
				Double shtc = DoubleUtils.mul(hj,0.92);
				map.put("jjkkjzf",jjkkjzf1);
				map.put("kjzftc",kjzftc);
				map.put("kjzffl","0.0002");
				map.put("jjkxkzf",jjkxkzf1);
				map.put("skzffl","0.0003");
				map.put("skzftc",skzftc);
				map.put("hj",hj);
				map.put("dksd","0.92%");
				map.put("shtc",shtc);
            }
        }

    }

}
