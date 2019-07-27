package com.acl.component;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.acl.pojo.BuildAndSellInfo;
import com.acl.utils.mongo.CriteriaUtil;
import com.acl.utils.mongo.Pagination;
import com.acl.utils.util.StringUtils;
/**
 *author:hufeng
 *createDate:2016年8月16日 下午3:30:03
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@SuppressWarnings("hiding")
public class SimMongodbBaseDao<T> {
	/**
	 * 注入mongodbTemplate
	 */
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * spring mongodb 集成操作类
	 */
	protected MongoTemplate mongoTemplate;

	/**
	 * 保存一个对象到mongodb
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, Object> save(Map<String, Object> map, String collection) {
		mongoTemplate.save(map, collection);
		return map;
	}

	/**
	 * 保存一个对象到mongodb
	 * @param map
	 * @return
	 */
	public void save(Object obj, String collection) {
		mongoTemplate.save(obj, collection);
	}

	/**
	 * 按条件查询,并且删除记录
	 * 
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T findAndRemove(Query query, Class<T> cs) {
		Criteria criteria=new Criteria();
		criteria.andOperator(criteria.gte("age").is("11"));
		find(new Query(criteria), (Class<T>) Map.class, "tableName");
		
		return this.mongoTemplate.findAndRemove(query, cs);
	}

	/**
	 * 根据条件删除某个集合中的一条记录
	 * 
	 * @param query
	 * @return
	 */
	public void remove(Map<String, Object> map, String collction) {
		Criteria channleIdCri = new Criteria();
		channleIdCri.andOperator(CriteriaUtil.convertMap(map));
		mongoTemplate.remove(new Query(channleIdCri), collction);
	}

	/**
	 * 根据ID删除某个集合中的一条记录
	 * 
	 * @param query
	 * @return
	 */
	public void removeById(String value, String collction) {
		Criteria channleIdCri = new Criteria();
		channleIdCri.andOperator(Criteria.where("_id").is(value));
		mongoTemplate.remove(new Query(channleIdCri), collction);
	}

	/**
	 * 根据_id和集合来查询数据
	 * 
	 * @param id
	 * @param collectionName
	 *          集合名
	 * @return
	 */
	public <T> T findById(String id, String collectionName, Class<T> cs) {
		return mongoTemplate.findById(id, cs, collectionName);
	}

	/**
	 * 通过ID获取记录,如果不指定集合名 那么默认在第一个集合中查找
	 * 
	 * @param id
	 * @return
	 */
	public <T> T findById(String id, Class<T> cs) {
		return mongoTemplate.findById(id, cs);
	}

	/**
	 * 通过条件查询更新数据
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public void updateFirst(Query query, Update update, Class<?> entityClass) {
		mongoTemplate.updateFirst(query, update, entityClass);
	}

	/**
	 * 通过条件查询更新数据
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public void updateFirst(Map<String, Object> map, Update update, String collection) {
		Criteria channleIdCri = new Criteria();
		channleIdCri.andOperator(CriteriaUtil.convertMap(map));
		mongoTemplate.updateFirst(new Query(channleIdCri), update, collection);
	}

	/**
	 * 通过ID查询更新数据
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public void updateFitstById(String value, Update update, String collection) {
		Criteria channleIdCri = new Criteria();
		channleIdCri.andOperator(Criteria.where("_id").is(value));
		mongoTemplate.updateFirst(new Query(channleIdCri), update, collection);
	}

	/**
	 * 按条件批量更新
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public void updateMulti(String value, Update update, String collection) {
		// Update update = Update.update("name", user.getName()).set("age",
		// user.getAge());
		Criteria channleIdCri = new Criteria();
		channleIdCri.andOperator(Criteria.where("_id").is(value));
		mongoTemplate.updateMulti(new Query(channleIdCri), update, collection);
	}

	/**
	 * 查询并且修改记录
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public T findAndModify(Query query, Update update, Class<T> cs) {
		return this.mongoTemplate.findAndModify(query, update, cs);
	}

	/**
	 * 条件查询不分页
	 * 
	 * @param query
	 */
	public List<T> find(Query query, Class<T> cs, String collection) {
		return mongoTemplate.find(query, cs, collection);
	}
	

	
	/**
	 * 查询出所有数据
	 * 
	 * @return
	 */
	public List<T> find(Map<String, Object> map, Class<T> cs, String collection) {
		Criteria channleIdCri = new Criteria();
		channleIdCri.andOperator(CriteriaUtil.convertMap(map));
		return mongoTemplate.find(new Query(channleIdCri), cs, collection);
	}

	/**
	 * 通过一定的条件查询一个实体
	 * 
	 * @param query
	 * @return
	 */
	public T findOne(Query query, Class<T> cs) {
		return mongoTemplate.findOne(query, cs);
	}

	/**
	 * 普通分页查询,多个等于条件
	 * @param pageNo
	 * @param pageSize
	 * @param query
	 * @return
	 * @throws ParseException 
	 */
	public Pagination<T> pagingQuery(int pageNo, int pageSize, Map<String, Object> map, Class<T> cs, String collection) throws ParseException {
			Criteria channleIdCri = new Criteria();
		//查询参数
	  channleIdCri.andOperator(CriteriaUtil.convertMap(map));
		Query query = new Query();
		query.addCriteria(channleIdCri);
		long totalCount = this.mongoTemplate.count(query,collection);
		Pagination<T> page = new Pagination<T>(pageNo, pageSize, totalCount);
		query.skip(page.getFirstResult());// skip相当于从那条记录开始
		query.limit(pageSize);						// 从skip开始,取多少条记录
		List<T> rows = this.find(query, cs, collection);
		page.setRows(rows);
		return page;
	}
	
	

	
	/**
	 * 查询未订单
	 * @param pageNo
	 * @param pageSize
	 * @param query
	 * @return
	 * @throws ParseException 
	 */
	public Pagination<T> queryOrder(String user_id,String time,String column, int pageNo, int pageSize,Class<T> cs, String collection) throws ParseException {
			
	  //如果有时间的话,对时间也进行分页查询
			
		/*****时间补全*****/
		String start_time = time+" "+"00:00:00";
		String end_time 	= time+" "+"23:59:59";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate = sdf.parse(start_time);	
	  Date endDate	 = sdf.parse(end_time);	
	  /*****时间补全*****/
	  Criteria channleIdCri = new Criteria();
		channleIdCri.andOperator(Criteria.where(column).gte(beginDate),Criteria.where(column).lte(endDate),Criteria.where("user_id").is(user_id));
		
		Query query = new Query();
		query.addCriteria(channleIdCri);
		
		long totalCount = this.mongoTemplate.count(query,collection);
		Pagination<T> page = new Pagination<T>(pageNo, pageSize, totalCount);
		
		query.skip(page.getFirstResult());			// skip相当于从那条记录开始
		query.limit(pageSize);									// 从skip开始,取多少条记录
		Direction direction=Direction.DESC;
		query.with(new Sort(direction,column));	//sell_time倒序排列
		
		List<T> rows = this.find(query, cs, collection);
		page.setRows(rows);
		return page;
	}
	
	
	
	
	
	/**
	 * 条件查询大于小于 当买涨的时候,传递的点位小于等于止损点位就止损,传递的点位大于等于当前设置的止盈点位就止盈。
	 * 
	 * @return
	 */
	public List<T> findBygtOrlt(double point, String item_code, Class<T> cs, int i, int j, String collection) {
		Criteria criteria = new Criteria();
		criteria.andOperator(
				Criteria.where("gzp_sell_zs_point").gte(5101).orOperator(Criteria.where("gzp_sell_zy_point").lte(5101)),
				Criteria.where("commodity_name").is("cg"), Criteria.where("gzp_buy_type").is("buy"));
				return mongoTemplate.find(new Query(criteria), cs, collection);
	}

	/**
	 * 查询前台用户和经纪人信息
	 * @param pageNo
	 * @param pageSize
	 * @param query
	 * @return
	 * @throws ParseException 
	 */
	public Pagination<T> pagingQuery1(int pageNo, int pageSize, Map<String, Object> map, String collection) throws ParseException {
			Criteria channleIdCri = new Criteria();
		//查询参数
		if(map.get("user_name")!=null){
		channleIdCri=Criteria.where("user_name").regex(map.get("user_name").toString());
		channleIdCri.andOperator(channleIdCri);
	
		}
		if(map.get("broker_name")!=null){
			channleIdCri=Criteria.where("broker_name").regex(map.get("broker_name").toString());
			channleIdCri.andOperator(channleIdCri);
		
			}
		Query query = new Query();
		query.addCriteria(channleIdCri);
		long totalCount = this.mongoTemplate.count(query,collection);
		Pagination<T> page = new Pagination<T>(pageNo, pageSize, totalCount);
		query.skip(page.getFirstResult());// skip相当于从那条记录开始
		query.limit(pageSize);						// 从skip开始,取多少条记录
		List<T> rows = this.find(query,(Class<T>) Map.class, collection);
		page.setRows(rows);
		return page;
	}
	
	/**
	 * 查询建仓和平仓信息
	 * @param pageNo
	 * @param pageSize
	 * @param query
	 * @return
	 * @throws ParseException 
	 */
	public Pagination<T> pagingQuerySellAndBuild(int pageNo, int pageSize, Map<String, Object> map, String collection,String strmark) throws ParseException {
			Criteria channleIdCri = new Criteria();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
		//查询参数
		if(!"".equals(StringUtils.checkString(map.get("user_name")))){
			channleIdCri.and("user_name").regex(map.get("user_name").toString());
		}		
		//手机号 (加密OR解密)
		if(!"".equals(StringUtils.checkString(map.get("mobile")))){
			//String mobile =MySecurity.encryptAES(StringUtils.checkString(map.get("mobile")), SystemConfig.keyMy);
			channleIdCri.and("user_mobile").is(map.get("mobile").toString());
		
			}
		if(!"".equals(StringUtils.checkString(map.get("agent_id")))){
			channleIdCri.and("agent_id").is(map.get("agent_id").toString());
		
			}
		if(!"".equals(StringUtils.checkString(map.get("settle_id")))){
			channleIdCri.and("settle_id").is(map.get("settle_id").toString());
		
			}
		if(!"".equals(StringUtils.checkString(map.get("ch_id")))){
			channleIdCri.and("ch_id").is(map.get("ch_id").toString());
		
			}
		if(!"".equals(StringUtils.checkString(map.get("ce_id")))){
			channleIdCri.and("ce_id").is(map.get("ce_id").toString());
		
			}
		if(strmark.equals("build"))
		{
			if(!"".equals(StringUtils.checkString(map.get("begindate"))) && "".equals(StringUtils.checkString(map.get("enddate")))){
				//channleIdCri=Criteria.where("buy_time").gte(sdf.parse(map.get("begindate").toString()));
				channleIdCri.and("buy_time").gte(sdf.parse(map.get("begindate").toString()));
			
			}
			else if(!"".equals(StringUtils.checkString(map.get("enddate"))) && "".equals(StringUtils.checkString(map.get("begindate"))))
			{
				channleIdCri.and("buy_time").lte(sdf.parse(map.get("enddate").toString()));		
			
			}
			else if (!"".equals(StringUtils.checkString(map.get("begindate"))) && !"".equals(StringUtils.checkString(map.get("enddate"))))
			{
				channleIdCri.and("buy_time").lte(sdf.parse(map.get("enddate").toString())).gte(sdf.parse(map.get("begindate").toString()));
				//channleIdCri.andOperator(channleIdCri);			
			}	
		}else
		{
			if(!"".equals(StringUtils.checkString(map.get("begindate"))) && "".equals(StringUtils.checkString(map.get("enddate")))){
				//channleIdCri=Criteria.where("buy_time").gte(sdf.parse(map.get("begindate").toString()));
				channleIdCri.and("sell_time").gte(sdf.parse(map.get("begindate").toString()));
			
			}
			else if(!"".equals(StringUtils.checkString(map.get("enddate"))) && "".equals(StringUtils.checkString(map.get("begindate"))))
			{
				channleIdCri.and("sell_time").lte(sdf.parse(map.get("enddate").toString()));		
			
			}
			else if (!"".equals(StringUtils.checkString(map.get("begindate"))) && !"".equals(StringUtils.checkString(map.get("enddate"))))
			{
				channleIdCri.and("sell_time").lte(sdf.parse(map.get("enddate").toString())).gte(sdf.parse(map.get("begindate").toString()));
				//channleIdCri.andOperator(channleIdCri);
			
			}
		}
		
		Query query = new Query();
		query.addCriteria(channleIdCri).with(new Sort(new Order(Direction.DESC, "buy_time")));
		long totalCount = this.mongoTemplate.count(query,collection);
		Pagination<T> page = new Pagination<T>(pageNo, pageSize, totalCount);
		query.skip(page.getFirstResult());// skip相当于从那条记录开始
		query.limit(pageSize);						// 从skip开始,取多少条记录
		List<T> rows = this.find(query,(Class<T>) Map.class, collection);
		page.setRows(rows);
		return page;
	}
	
	/**
	 * 查询建仓和平仓信息
	 * @param pageNo
	 * @param pageSize
	 * @param query
	 * @return
	 * @throws ParseException 
	 */
	public List<BuildAndSellInfo> pagingQuerySellAndBuildCount(Map<String, Object> map, String collection,String strmark) throws ParseException {
		Criteria channleIdCri = new Criteria();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		//查询参数
		if(!"".equals(StringUtils.checkString(map.get("user_name")))){
		channleIdCri.and("user_name").regex(map.get("user_name").toString());
		channleIdCri.andOperator(channleIdCri);	
		}
		
		//手机号 (加密OR解密)
		if(!"".equals(StringUtils.checkString(map.get("mobile")))){
			//String mobile =MySecurity.encryptAES(StringUtils.checkString(map.get("mobile")), SystemConfig.keyMy);
			channleIdCri.and("user_mobile").is(map.get("mobile").toString());
			//channleIdCri.andOperator(channleIdCri);		
			}
		if(!"".equals(StringUtils.checkString(map.get("agent_id")))){
			channleIdCri.and("agent_id").is(map.get("agent_id").toString());
			//channleIdCri.andOperator(channleIdCri);
		
			}
		if(!"".equals(StringUtils.checkString(map.get("settle_id")))){
			channleIdCri.and("settle_id").is(map.get("settle_id").toString());
			//channleIdCri.andOperator(channleIdCri);
		
			}
		if(!"".equals(StringUtils.checkString(map.get("ch_id")))){
			channleIdCri.and("ch_id").is(map.get("ch_id").toString());
			//channleIdCri.andOperator(channleIdCri);
		
			}
		if(!"".equals(StringUtils.checkString(map.get("ce_id")))){
			channleIdCri.and("ce_id").is(map.get("ce_id").toString());
			//channleIdCri.andOperator(channleIdCri);		
			}
		if(strmark.equals("build"))
		{
			if(!"".equals(StringUtils.checkString(map.get("begindate"))) && "".equals(StringUtils.checkString(map.get("enddate")))){
				//channleIdCri=Criteria.where("buy_time").gte(sdf.parse(map.get("begindate").toString()));
				channleIdCri.and("buy_time").gte(sdf.parse(map.get("begindate").toString()));
			
			}
			else if(!"".equals(StringUtils.checkString(map.get("enddate"))) && "".equals(StringUtils.checkString(map.get("begindate"))))
			{
				channleIdCri.and("buy_time").lte(sdf.parse(map.get("enddate").toString()));		
			
			}
			else if (!"".equals(StringUtils.checkString(map.get("begindate"))) && !"".equals(StringUtils.checkString(map.get("enddate"))))
			{
				channleIdCri.and("buy_time").lte(sdf.parse(map.get("enddate").toString())).gte(sdf.parse(map.get("begindate").toString()));
				//channleIdCri.andOperator(channleIdCri);
			
			}	
		}else
		{
			if(!"".equals(StringUtils.checkString(map.get("begindate"))) && "".equals(StringUtils.checkString(map.get("enddate")))){
				//channleIdCri=Criteria.where("buy_time").gte(sdf.parse(map.get("begindate").toString()));
				channleIdCri.and("sell_time").gte(sdf.parse(map.get("begindate").toString()));
			
			}
			else if(!"".equals(StringUtils.checkString(map.get("enddate"))) && "".equals(StringUtils.checkString(map.get("begindate"))))
			{
				channleIdCri.and("sell_time").lte(sdf.parse(map.get("enddate").toString()));		
			
			}
			else if (!"".equals(StringUtils.checkString(map.get("begindate"))) && !"".equals(StringUtils.checkString(map.get("enddate"))))
			{
				channleIdCri.and("sell_time").lte(sdf.parse(map.get("enddate").toString())).gte(sdf.parse(map.get("begindate").toString()));
				//channleIdCri.andOperator(channleIdCri);
			
			}
		}
		
		//Criteria channleIdCri1 = new Criteria();
		Aggregation agg = Aggregation.newAggregation(
				Aggregation.match(
						channleIdCri),
				Aggregation.group().sum("buy_amount").as("sum_1").sum("buy_brokerage").as("sum_2").sum("sell_profit_loss").as("sum_3"));
		AggregationResults<BuildAndSellInfo> resultLot = mongoTemplate.aggregate(agg,
				collection, BuildAndSellInfo.class);
		//Object ob =new Object();
		List<BuildAndSellInfo> listInfo = resultLot.getMappedResults();
		if(!listInfo.isEmpty())
		{
			System.out.println(listInfo.get(0).getSum_1());
			System.out.println(listInfo.get(0).getSum_2());
			if(strmark.equals("sell")){
			System.out.println(listInfo.get(0).getSum_3());
			}
		}			       	       
		return listInfo;
	}

}
	
