package com.acl.utils.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import org.apache.poi.ss.formula.functions.T;

import com.acl.component.SystemConfig;
import com.acl.utils.mongo.Pagination;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:PhoneCodeSSL
 *author:malx
 *createDate:2016年3月18日 下午7:19:49
 *vsersion:1.0
 *department:安创乐科技
 *description:手机号码权限公共类
 */
public class PhoneCodeSSL {
   
	/**
	 * 加密手机号码
	 * @param list
	 * @param paramsMap
	 * @return
	 */
	public static List<Map<String, Object>> getList(
			List<Map<String, Object>> lm, HashMap<String, Object> paramsMap) {
		    //不管是谁  都是加密四星
			 for(int i=0;i<lm.size();i++){
				 if(lm.get(i).get("mobile")!=null){
					String mobile= lm.get(i).get("mobile").toString();
					lm.get(i).put("mobile", StringUtils.getHideMobile(mobile));
				 }
			 }
		return lm;
	}
	
	
	
	/**
	 * 解密手机号
	 * @param list
	 * @param paramsMap
	 * @return
	 */
	public static List<Map<String, Object>> getDataBaseList(
			List<Map<String, Object>> lm, HashMap<String, Object> paramsMap) {
		 for(int i=0;i<lm.size();i++){
			if (lm.get(i).containsKey("mobile")&&!"".equals(StringUtils.checkString(lm.get(i).get("mobile")))) {
				String mobile= lm.get(i).get("mobile").toString();
				lm.get(i).put("mobile", StringUtils.getHideMobile(mobile));
			 }
		 }
		 return lm;
	}
	
	
	/**
	 * 解密手机号
	 * @param list
	 * @param paramsMap
	 * @return
	 */
	public static List<Map<String, Object>> getUserPhoneList(
			List<Map<String, Object>> lm, HashMap<String, Object> paramsMap) {
		 for(int i=0;i<lm.size();i++){
			if (lm.get(i).containsKey("用户电话")&&!"".equals(StringUtils.checkString(lm.get(i).get("用户电话")))) {
				String mobile= lm.get(i).get("用户电话").toString();
				lm.get(i).put("用户电话",mobile);
			 }
		 }
		 return lm;
	}
	/**
	 * 解析分页list中的电话号码信息
	 * 手机号解密之后加****
	 * @param lm
	 * @param paramsMap  mobile
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static PageList<?> getDataBaseListPage(PageList<?> lm, HashMap<String, Object> paramsMap) {
		for (Iterator<?> iterator = lm.iterator(); iterator.hasNext();) {
			HashMap<String, Object> map = (HashMap<String, Object>) iterator.next();
			if (map.containsKey("mobile")&&!"".equals(StringUtils.checkString(map.get("mobile")))) {
				map.put("mobile", StringUtils.getHideMobile(StringUtils.checkString(map.get("mobile"))));
			}
		}
		return lm;
	}
	
	
	/**
	 * 
	 * 手机号加****
	 * @param lm
	 * @param paramsMap  mobile
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<HashMap<String, Object>> getPhone(List<HashMap<String, Object>> lm, HashMap<String, Object> paramsMap) {
		for (Iterator<?> iterator = lm.iterator(); iterator.hasNext();) {
			HashMap<String, Object> map = (HashMap<String, Object>) iterator.next();
			if (map.containsKey("mobile")&&!"".equals(StringUtils.checkString(map.get("mobile")))) {
				map.put("mobile", StringUtils.getHideMobile(StringUtils.checkString(map.get("mobile"))));
			}
			if (map.containsKey("bank_no")&&!"".equals(StringUtils.checkString(map.get("bank_no")))) {
				map.put("bank_no",BankUtil.replaceStar(StringUtils.checkString(map.get("bank_no")), 6, StringUtils.checkString(map.get("bank_no")).length()-4));
			}
		}
		return lm;
	}
	
	/**
	 * 解析分页list中的电话号码信息
	 * 手机号解密之后加****
	 * @param lm
	 * @param paramsMap  mobile
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static PageList<?> getUserMobileList(PageList<?> lm, HashMap<String, Object> paramsMap) {
		for (Iterator<?> iterator = lm.iterator(); iterator.hasNext();) {
			HashMap<String, Object> map = (HashMap<String, Object>) iterator.next();
			if (map.containsKey("user_mobile")&&!"".equals(StringUtils.checkString(map.get("user_mobile")))) {
				map.put("user_mobile", StringUtils.getHideMobile(StringUtils.checkString(map.get("user_mobile"))));
			}
		}
		return lm;
	}
	
	/**
	 * 解密手机号，中间不加****
	 * @param lm
	 * @param paramsMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static PageList<?> getDataBaseListPage1(PageList<?> lm, HashMap<String, Object> paramsMap) {
		for (Iterator<?> iterator = lm.iterator(); iterator.hasNext();) {
			HashMap<String, Object> map = (HashMap<String, Object>) iterator.next();
			if (map.containsKey("mobile")&&!"".equals(StringUtils.checkString(map.get("mobile")))) {
				map.put("mobile", StringUtils.checkString(map.get("mobile")));
			}
			if (map.containsKey("total_map")&&!"".equals(StringUtils.checkString(map.get("total_map")))) {
				List<MergedRowTitle> list = JSONArray.parseArray((String) map.get("total_map"),MergedRowTitle.class);
				map.put("total_map", list);
			}
			if (map.containsKey("deduct_map")&&!"".equals(StringUtils.checkString(map.get("deduct_map")))) {
				List<MergedRowTitle> list = JSONArray.parseArray((String) map.get("deduct_map"),MergedRowTitle.class);
				map.put("deduct_map", list);
			}
		}
		return lm;
	}



	static  public class MergedRowTitle implements Serializable {

		private String key ;
		private Integer columnIndex;
		private String columnName ;
		private String currentColumnName ;
		private String columnValue ;
		private Integer firstColumn;
		private Integer lastColumn;
		private Integer firstRow;
		private Integer lastRow;

		@Override
		public String toString() {
			return "MergedRowTitle{" +
					"key='" + key + '\'' +
					", columnIndex=" + columnIndex +
					", columnName='" + columnName + '\'' +
					", currentColumnName='" + currentColumnName + '\'' +
					", columnValue='" + columnValue + '\'' +
					", firstColumn=" + firstColumn +
					", lastColumn=" + lastColumn +
					", firstRow=" + firstRow +
					", lastRow=" + lastRow +
					'}';
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public Integer getColumnIndex() {
			return columnIndex;
		}

		public void setColumnIndex(Integer columnIndex) {
			this.columnIndex = columnIndex;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public String getCurrentColumnName() {
			return currentColumnName;
		}

		public void setCurrentColumnName(String currentColumnName) {
			this.currentColumnName = currentColumnName;
		}

		public String getColumnValue() {
			return columnValue;
		}

		public void setColumnValue(String columnValue) {
			this.columnValue = columnValue;
		}

		public Integer getFirstColumn() {
			return firstColumn;
		}

		public void setFirstColumn(Integer firstColumn) {
			this.firstColumn = firstColumn;
		}

		public Integer getLastColumn() {
			return lastColumn;
		}

		public void setLastColumn(Integer lastColumn) {
			this.lastColumn = lastColumn;
		}

		public Integer getFirstRow() {
			return firstRow;
		}

		public void setFirstRow(Integer firstRow) {
			this.firstRow = firstRow;
		}

		public Integer getLastRow() {
			return lastRow;
		}

		public void setLastRow(Integer lastRow) {
			this.lastRow = lastRow;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Pagination<T> getDataBasePagination(Pagination<T> lm, HashMap<String, Object> paramsMap) {
		List<T> row = lm.getRows();
		for (Iterator iterator = row.iterator(); iterator.hasNext();) {
			HashMap<String, Object> map = (HashMap<String, Object>) iterator.next();
//			[{_id=af897171f6db465c85c3205017cd06d2, order_id=af897171f6db465c85c3205017cd06d2, ch_id=1, ce_id=0, settle_id=1, agent_id=6, broker_id=123123123, user_id=f3c818b3d02b40818158148db8d4b89f, buy_type=buy, buy_point=3769.0, buy_unit=5kg, buy_time=Mon Aug 29 17:36:30 CST 2016, buy_itemtype=ag6, buy_itemcode=ag2, buy_itemname=银, buy_brokerage=36.0, buy_price=500.0, buy_amount=4, buy_all_price=2036.0, is_use=1, buy_number=6, gdsy_buy_xz_time=0, gdsy_buy_ratio=0.0, gzp_profit=5.0, gzp_sell_zs_point=3669.0, gzp_sell_zy_point=0.0, item_id=69b9aa9bbf5542818be9889427cc006a, confirm=1, trading_rule=01}, {_id=7905fce7d84c4a9482b186a9f251d46a, order_id=7905fce7d84c4a9482b186a9f251d46a, ch_id=1, ce_id=0, settle_id=1, agent_id=6, broker_id=123123123, user_id=f3c818b3d02b40818158148db8d4b89f, buy_type=buy, buy_point=31915.0, buy_unit=1kg, buy_time=Mon Aug 29 17:36:33 CST 2016, buy_itemtype=cu10, buy_itemcode=cu, buy_itemname=铜, buy_brokerage=45.0, buy_price=100.0, buy_amount=5, buy_all_price=545.0, is_use=1, buy_number=10, gdsy_buy_xz_time=0, gdsy_buy_ratio=0.0, gzp_profit=1.0, gzp_sell_zs_point=30915.0, gzp_sell_zy_point=0.0, item_id=862cbd2309f041b3bb2ef0ebb0cba7a5, confirm=1, trading_rule=01},
//			{_id=5746ccacd8374a659d90c2dabf60d254, order_id=5746ccacd8374a659d90c2dabf60d254, ch_id=1, ce_id=0, settle_id=1, agent_id=6, broker_id=123123123, user_id=f3c818b3d02b40818158148db8d4b89f, buy_type=buy, buy_point=5780.0, buy_unit=5桶, buy_time=Mon Aug 29 17:36:37 CST 2016, buy_itemtype=oil12, buy_itemcode=oil2, buy_itemname=油, buy_brokerage=54.0, buy_price=500.0, buy_amount=6, buy_all_price=3054.0, is_use=1, buy_number=12, gdsy_buy_xz_time=0, gdsy_buy_ratio=0.0, gzp_profit=5.0, gzp_sell_zs_point=5680.0, gzp_sell_zy_point=0.0, item_id=8c876b8743b14d248f918e7fa5b5df3a, confirm=1, trading_rule=01}, {_id=739aa9928e524ea985527eb3db652db1, order_id=739aa9928e524ea985527eb3db652db1, p_id=0, ch_id=2, ce_id=1, settle_id=3, agent_id=4, dept_id=5, broker_id=1472544102146, user_id=0c3566f780414abea9d5701d4c043073, buy_type=sell, buy_point=5820.0, buy_unit=kg, buy_time=Tue Aug 30 20:59:30 CST 2016, sell_time=Tue Aug 30 21:29:30 CST 2016, buy_itemtype=oil12, buy_itemcode=oil1, buy_itemname=油, buy_brokerage=9.0, buy_price=100.0, buy_amount=1, buy_all_price=109.0, is_use=1, buy_number=12, gdsy_buy_xz_time=1800, gdsy_buy_ratio=70.0, gzp_profit=0.0, gzp_sell_zs_point=0.0, gzp_sell_zy_point=0.0, item_id=d3b11605ddc8417aac013df557d90e03, confirm=1, trading_rule=02}, {_id=c6f9169eb5d345a5afcea3f317981d54, order_id=c6f9169eb5d345a5afcea3f317981d54, p_id=0, ch_id=2, ce_id=1, settle_id=3, agent_id=4, dept_id=5, broker_id=1472544102146, user_id=0c3566f780414abea9d5701d4c043073, buy_type=buy, buy_point=5824.0, buy_unit=kg, buy_time=Tue Aug 30 21:15:55 CST 2016, sell_time=Tue Aug 30 21:45:55 CST 2016, buy_itemtype=oil12, buy_itemcode=oil1, buy_itemname=油, buy_brokerage=9.0, buy_price=100.0, buy_amount=1, buy_all_price=109.0, is_use=1, buy_number=12, gdsy_buy_xz_time=1800, gdsy_buy_ratio=70.0, gzp_profit=0.0, gzp_sell_zs_point=0.0, gzp_sell_zy_point=0.0, item_id=d3b11605ddc8417aac013df557d90e03, confirm=1, trading_rule=02}, {_id=837f9b4227c04f7aa99cda6a91bc3c78, order_id=837f9b4227c04f7aa99cda6a91bc3c78, p_id=0, ch_id=2, ce_id=1, settle_id=3, agent_id=4, dept_id=5, broker_id=1472544102146, user_id=0c3566f780414abea9d5701d4c043073, buy_type=sell, buy_point=5822.0, buy_unit=kg, buy_time=Tue Aug 30 21:18:11 CST 2016, sell_time=Tue Aug 30 21:48:11 CST 2016, buy_itemtype=oil12, buy_itemcode=oil1, buy_itemname=油, buy_brokerage=9.0, buy_price=100.0, buy_amount=1, buy_all_price=109.0, is_use=1, buy_number=12, gdsy_buy_xz_time=1800, gdsy_buy_ratio=70.0, gzp_profit=0.0, gzp_sell_zs_point=0.0, gzp_sell_zy_point=0.0, item_id=d3b11605ddc8417aac013df557d90e03, confirm=1, trading_rule=02}, {_id=157e7fe12e404ec0864def8a249a443c, order_id=157e7fe12e404ec0864def8a249a443c, p_id=0, ch_id=2, ce_id=1, settle_id=3, agent_id=4, dept_id=5, broker_id=1472544102146, user_id=0c3566f780414abea9d5701d4c043073, buy_type=buy, buy_point=5819.0, buy_unit=kg, buy_time=Tue Aug 30 21:19:14 CST 2016, sell_time=Tue Aug 30 21:49:14 CST 2016, buy_itemtype=oil12, buy_itemcode=oil1, buy_itemname=油, buy_brokerage=9.0, buy_price=100.0, buy_amount=1, buy_all_price=109.0, is_use=1, buy_number=12, gdsy_buy_xz_time=1800, gdsy_buy_ratio=70.0, gzp_profit=0.0, gzp_sell_zs_point=0.0, gzp_sell_zy_point=0.0, item_id=d3b11605ddc8417aac013df557d90e03, confirm=1, trading_rule=02}, {_id=f39a41c39cb14eccaafccf6a92a31230, order_id=f39a41c39cb14eccaafccf6a92a31230, p_id=0, ch_id=2, ce_id=1, settle_id=3, agent_id=4, dept_id=5, broker_id=1472544102146, user_id=0c3566f780414abea9d5701d4c043073, buy_type=sell, buy_point=5815.0, buy_unit=kg, buy_time=Tue Aug 30 21:21:53 CST 2016, sell_time=Tue Aug 30 21:51:53 CST 2016, buy_itemtype=oil12, buy_itemcode=oil1, buy_itemname=油, buy_brokerage=9.0, buy_price=100.0, buy_amount=1, buy_all_price=109.0, is_use=1, buy_number=12, gdsy_buy_xz_time=1800, gdsy_buy_ratio=70.0, gzp_profit=0.0, gzp_sell_zs_point=0.0, gzp_sell_zy_point=0.0, item_id=d3b11605ddc8417aac013df557d90e03, confirm=1, trading_rule=02}, {_id=9aa14e2616c54fa887c247a2fd213586, order_id=9aa14e2616c54fa887c247a2fd213586, p_id=0, ch_id=2, ce_id=1, settle_id=3, agent_id=4, dept_id=5, broker_id=1472544102146, user_id=0c3566f780414abea9d5701d4c043073, buy_type=buy, buy_point=5819.0, buy_unit=kg, buy_time=Tue Aug 30 21:26:35 CST 2016, sell_time=Tue Aug 30 21:27:35 CST 2016, buy_itemtype=oil12, buy_itemcode=oil1, buy_itemname=油, buy_brokerage=9.0, buy_price=100.0, buy_amount=1, buy_all_price=109.0, is_use=1, buy_number=12, gdsy_buy_xz_time=60, gdsy_buy_ratio=70.0, gzp_profit=0.0, gzp_sell_zs_point=0.0, gzp_sell_zy_point=0.0, item_id=bc70264f24104760b557ab7722dccda5, confirm=1, trading_rule=02}, {_id=ecc7869c3a624ad486b64c9f9789ed9b, order_id=ecc7869c3a624ad486b64c9f9789ed9b, p_id=0, ch_id=2, ce_id=1, settle_id=3, agent_id=4, dept_id=5, broker_id=3b7db193c23447cab46292011d9f3668, user_id=a246bd8345e34a34bc722acfebdf942a, buy_type=buy, buy_point=3904.0, buy_unit=克, buy_time=Tue Sep 13 10:54:46 CST 2016, buy_itemtype=ag6, buy_itemcode=ag, buy_itemname=银, buy_brokerage=9.0, buy_price=100.0, buy_amount=1, buy_all_price=109.0, is_use=1, buy_number=6, gdsy_buy_xz_time=0, gdsy_buy_ratio=0.0, gzp_profit=1.0, gzp_sell_zs_point=3804.0, gzp_sell_zy_point=0.0, item_id=65721ece806f468db359303a0ee52f76, confirm=2, trading_rule=01}, {_id=c6d594d554ae476aaff439edfdcc75f5, order_id=c6d594d554ae476aaff439edfdcc75f5, p_id=0, ch_id=2, ce_id=1, settle_id=3, settle_name=3会员单位, agent_id=4, agent_name=财趣, dept_id=5, dept_name=模拟盘-初始化部门, broker_id=3b7db193c23447cab46292011d9f3668, broker_name=梵蒂冈地方, user_id=ed8f20d0ad9b469c9ad0da5c23ced233, user_name=陈威, buy_type=buy, buy_point=3877.0, buy_unit=5kg, buy_time=Wed Sep 28 16:57:16 CST 2016, buy_itemtype=ag6, buy_itemcode=ag02, buy_itemname=银, buy_brokerage=45.0, buy_price=500.0, buy_amount=1, buy_all_price=545.0, is_use=1, buy_number=6, gdsy_buy_xz_time=0, gdsy_buy_ratio=0.0, gzp_profit=5.0, gzp_sell_zs_point=3777.0, gzp_sell_zy_point=0.0, item_id=1786bb1b2f2c48c7afabc8b55a6ccf68, confirm=1, trading_rule=01}, {_id=5390aebbf223413d9ffb2899cc2d5d6f, order_id=5390aebbf223413d9ffb2899cc2d5d6f, p_id=0, ch_id=2, ce_id=1, settle_id=3, settle_name=3会员单位, agent_id=4, agent_name=财趣, dept_id=5, dept_name=模拟盘-初始化部门, broker_id=3b7db193c23447cab46292011d9f3668, broker_name=梵蒂冈地方, user_id=ed8f20d0ad9b469c9ad0da5c23ced233, user_name=陈威, buy_type=buy, buy_point=3878.0, buy_unit=5kg, buy_time=Wed Sep 28 16:58:08 CST 2016, buy_itemtype=ag6, buy_itemcode=ag02, buy_itemname=银, buy_brokerage=45.0, buy_price=500.0, buy_amount=1, buy_all_price=545.0, is_use=1, buy_number=6, gdsy_buy_xz_time=0, gdsy_buy_ratio=0.0, gzp_profit=5.0, gzp_sell_zs_point=3778.0, gzp_sell_zy_point=0.0, item_id=1786bb1b2f2c48c7afabc8b55a6ccf68, confirm=2, trading_rule=01}, {_id=e4fdb15922ca4f9883e3c5c0cecd21e8, order_id=e4fdb15922ca4f9883e3c5c0cecd21e8, p_id=0, ch_id=2, ce_id=1, settle_id=3, settle_name=3会员单位, agent_id=4, agent_name=财趣, dept_id=5, dept_name=模拟盘-初始化部门, broker_id=3b7db193c23447cab46292011d9f3668, broker_name=梵蒂冈地方, user_id=a7aac305e84246c58fb2359be2693b0e, user_name=陈威, buy_type=buy, buy_point=3885.0, buy_unit=1kg, buy_time=Wed Sep 28 18:11:46 CST 2016, buy_itemtype=ag6, buy_itemcode=ag01, buy_itemname=银, buy_brokerage=9.0, buy_price=100.0, buy_amount=1, buy_all_price=109.0, is_use=1, buy_number=6, gdsy_buy_xz_time=0, gdsy_buy_ratio=0.0, gzp_profit=1.0, gzp_sell_zs_point=3785.0, gzp_sell_zy_point=0.0, item_id=16d73506508d4e09abe21eb01d231b23, confirm=1, trading_rule=01}]
			if (map.containsKey("user_mobile")&&!"".equals(StringUtils.checkString(map.get("user_mobile")))) {
				map.put("user_mobile", StringUtils.getHideMobile(StringUtils.checkString(map.get("user_mobile"))));
			}
		}		
		return lm;
	}
	
	
	/**
	 * 解密手机号，中间不加****
	 * @param lm
	 * @param paramsMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static PageList<?> getDataBaseListPage2(PageList<?> lm, HashMap<String, Object> paramsMap) {
		for (Iterator<?> iterator = lm.iterator(); iterator.hasNext();) {
			HashMap<String, Object> map = (HashMap<String, Object>) iterator.next();
			if (map.containsKey("user_mobile")&&!"".equals(StringUtils.checkString(map.get("user_mobile")))) {
				map.put("user_mobile", StringUtils.checkString(map.get("user_mobile")));
			}
		}
		return lm;
	}

	public static PageList<?> getDataBaseListPage3(PageList<?> lm, HashMap<String, Object> paramsMap) {
		for (Iterator<?> iterator = lm.iterator(); iterator.hasNext();) {
			HashMap<String, Object> map = (HashMap<String, Object>) iterator.next();
			if (map.containsKey("user_mobile")&&!"".equals(StringUtils.checkString(map.get("user_mobile")))) {
				map.put("user_mobile", StringUtils.getHideMobile(StringUtils.checkString(map.get("user_mobile"))));
			}
		}
		return lm;
	}


   /**
    * 手机号查询加密
    * @param paramsMap
    * @return
    */
	public static HashMap<String, Object> setDataMobileJm(
			HashMap<String, Object> paramsMap) {
		if (paramsMap.containsKey("mobile")&&!"".equals(StringUtils.checkString(paramsMap.get("mobile")))) {
			String mobile=MySecurity.encryptAES(paramsMap.get("mobile").toString(), SystemConfig.keyMy);
			paramsMap.put("mobile", mobile);
		}
		return paramsMap;
	}
	
	/**
	 * 屏蔽经纪人电话号码
	 * 
	 * @param lm
	 * @param paramsMap  mobile
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static PageList<?> getBrokerListPage(PageList<?> lm) {
		for (Iterator<?> iterator = lm.iterator(); iterator.hasNext();) {
			HashMap<String, Object> map = (HashMap<String, Object>) iterator.next();
			if (map.containsKey("mobile")&&!"".equals(StringUtils.checkString(map.get("mobile")))) {
				map.put("mobile", StringUtils.getHideMobile(StringUtils.checkString(map.get("mobile"))));
			}
		}
		return lm;
	}
}
