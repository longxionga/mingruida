package com.acl.platform.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.acl.utils.util.UUIDGenerator;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformNumberInfoService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.RandomValidateCode;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 *className:PlatformNumberInfoController
 *author:wangli
 *createDate:2017年2月21日 下午3:19:29
 *vsersion:3.0
 *department:安创乐科技
 *description:靓号管理
 */
@Controller
@RequestMapping("platform")
public class PlatformNumberInfoController extends CoreBaseController {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private IPlatformNumberInfoService platformNumberInfoService;
	
	@RequiresAuthentication
    @RequiresPermissions(value = {"/platform/numberInfo" })
    @RequestMapping("/numberInfo")
    public String numberInfo(){
	return "platform/platform_number_info";
    }
	
	/**
	 * 查询靓号信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/numberInfo" })
	@RequestMapping("/queryNumberInfo")
	public Object queryNumberInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));		
		if(paramsMap.get("sort")!=null){
			paramsMap.put("order_type", paramsMap.get("sort").toString()+" "+paramsMap.get("order").toString());
			pageBounds = new PageBounds(page,rows,Order.formString(paramsMap.get("sort").toString()+"."+paramsMap.get("order").toString()));
		}	
		//查询用户信息
		PageList<?> list = (PageList<?>)platformNumberInfoService.queryNumberInfo(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;

	}
	
	/**
	 * 修改靓号信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/numberInfo" })
	@RequestMapping("/updNumberInfo")
	public Object updateBackMenu(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{
			platformNumberInfoService.updateNumberInfo(paramsMap);
			msg.setMsg("更新成功");
			msg.setSuccess(true);
		    }catch(Exception e){
			msg.setMsg("更新失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * 添加靓号信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/numberInfo" })
	@RequestMapping("/insNumberInfo")
	public Object insNumberInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		//创建日期
		paramsMap.put("create_date",sdf.format(new java.util.Date()));
		
		//特殊组合AAAAAA
		String numberCode="";
		//查询数据库中存在的该组合的全字母类型已经有多少个，num<=26-已有个数
		String[]a=new String[]{"aaaaaa","bbbbbb","cccccc","dddddd","eeeeee","ffffff","gggggg","hhhhhh","iiiiii","jjjjjj","kkkkkk","llllll","mmmmmm","nnnnnn","oooooo","pppppp","qqqqqq","rrrrrr","ssssss","tttttt","uuuuuu","vvvvvv","wwwwww","xxxxxx","yyyyyy","zzzzzz"};
		String[]b=new String[]{"000000","111111","222222","333333","444444","555555","666666","777777","888888","999999"};
		try{	
			String num=request.getParameter("num");
			String type=request.getParameter("number_type");
			String group_type=request.getParameter("group_type");
			if(type.equals("1") && group_type.equals("3")){//AAAAAA无法生成数字字母组合
				msg.setMsg("新增失败,改靓号类型无法生成数字字母组合的靓号");
				msg.setSuccess(false);
				return msg;
			}
			//判断是否为自定义类型
			if(type.equals("0")){//自定义类型不支持自动生成
				msg.setMsg("新增失败,该靓号类型不支持自动生成");
				msg.setSuccess(false);
				return msg;
			}				
				
			//判断是否为AAAAAA类型
			if(type.equals("1")){	//AAAAAA
				if(num.equals("one")){//非批量
					if(StringUtils.convertString(request.getParameter("number_code")).equals("")){
						String number_code="";
						//用户未输入
						if(group_type.equals("1")){//纯字母
							for(int i=0;i<a.length;i++){
								number_code=a[i];
								//查询是否为重复的邀请码				
								HashMap<String,Object> map=new HashMap<>();
								map.put("number_code", number_code);							
								//先查询t_front_broker表中是否已经有经纪人使用过
								List<Map<String,Object>> list=platformNumberInfoService.queryBrokerCode(map);
								//查询t_back_number_info表中是否已经生成同样的邀请码
								List<Map<String,Object>> list1=platformNumberInfoService.queryNumberInfoCode(map);
								if(list.size()==0 && list1.size()==0){
									paramsMap.put("number_id", UUIDGenerator.generate());
									paramsMap.put("number_code", number_code);	
									platformNumberInfoService.insertNumberInfo(paramsMap);
									msg.setMsg("新增成功");
									msg.setSuccess(true);
									return msg;
								}
								msg.setMsg("新增失败，该组合类型的靓号已经使用完，无法生成该类型的靓号");
								msg.setSuccess(false);
								return msg;
							}

						}else {//纯数字
							for(int i=0;i<b.length;i++){
								number_code=b[i];
								//查询是否为重复的邀请码				
								HashMap<String,Object> map=new HashMap<>();
								map.put("number_code", number_code);							
								//先查询t_front_broker表中是否已经有经纪人使用过
								List<Map<String,Object>> list=platformNumberInfoService.queryBrokerCode(map);
								//查询t_back_number_info表中是否已经生成同样的邀请码
								List<Map<String,Object>> list1=platformNumberInfoService.queryNumberInfoCode(map);
								if(list.size()==0 && list1.size()==0){
									paramsMap.put("number_id", UUIDGenerator.generate());
									paramsMap.put("number_code", number_code);	
									platformNumberInfoService.insertNumberInfo(paramsMap);
									msg.setMsg("新增成功");
									msg.setSuccess(true);
									return msg;
								}
								msg.setMsg("新增失败，该组合类型的靓号已经使用完，无法生成该类型的靓号");
								msg.setSuccess(false);
								return msg;
							}
						}
					}else{//用户自行输入//前端已经验证过是否重复
						platformNumberInfoService.insertNumberInfo(paramsMap);
						msg.setMsg("新增成功");
						msg.setSuccess(true);
						return msg;
					}
				}else {//批量						
					String number_code="";
					//如果为批量增加，则根据数量批量生成
					int sum=Integer.parseInt(request.getParameter("sum"));
					if(group_type.equals("1")){//纯字母
						int n=sum>a.length?a.length:sum;	
						int count=0;
						for(int i=0;i<a.length;i++){
							number_code=a[i];
							//查询是否为重复的邀请码				
							HashMap<String,Object> map=new HashMap<>();
							map.put("number_code", number_code);							
							//先查询t_front_broker表中是否已经有经纪人使用过
							List<Map<String,Object>> list=platformNumberInfoService.queryBrokerCode(map);
							//查询t_back_number_info表中是否已经生成同样的邀请码
							List<Map<String,Object>> list1=platformNumberInfoService.queryNumberInfoCode(map);
							if(list.size()==0 && list1.size()==0){
								paramsMap.put("number_id", UUIDGenerator.generate());
								paramsMap.put("number_code", number_code);	
								platformNumberInfoService.insertNumberInfo(paramsMap);
								count+=1;
								if(count==n){
									break;
								}
							}								
						}
						if(count==0){
							msg.setMsg("新增失败，该组合类型的靓号已经使用完，无法生成该类型的靓号");
							msg.setSuccess(false);
							return msg;
						}else {
							msg.setMsg("新增成功,已经成功加入"+count+"个靓号");
							msg.setSuccess(true);
							return msg;
						}
						
					}else {//纯数字
						int n=sum>b.length?b.length:sum;	
						int count=0;
						for(int i=0;i<b.length;i++){
							number_code=b[i];
							//查询是否为重复的邀请码				
							HashMap<String,Object> map=new HashMap<>();
							map.put("number_code", number_code);							
							//先查询t_front_broker表中是否已经有经纪人使用过
							List<Map<String,Object>> list=platformNumberInfoService.queryBrokerCode(map);
							//查询t_back_number_info表中是否已经生成同样的邀请码
							List<Map<String,Object>> list1=platformNumberInfoService.queryNumberInfoCode(map);
							if(list.size()==0 && list1.size()==0){
								paramsMap.put("number_id", UUIDGenerator.generate());
								paramsMap.put("number_code", number_code);	
								platformNumberInfoService.insertNumberInfo(paramsMap);	
								count+=1;
								if(count==n){
									break;
								}
							}								
						}
						if(count==0){
							msg.setMsg("新增失败，该组合类型的靓号已经使用完，无法生成该类型的靓号");
							msg.setSuccess(false);
							return msg;
						}else {
							msg.setMsg("新增成功,已经成功加入"+count+"个靓号");
							msg.setSuccess(true);
							return msg;
						}
					}

				}
			}

			//除AAAAAA类型的增加	
			
			//判断是否为批量增加
			if(num.equals("one")){
				//如果判断不为批量增加，判断靓号输入是否为空，如果为空则自动生成		
				if(StringUtils.convertString(request.getParameter("number_code")).equals("")){
					for(int i=0;i<650;i++){//
						String number_code=RandomValidateCode.randNumber(Integer.parseInt(type), Integer.parseInt(group_type));
						//查询是否为重复的邀请码				
						HashMap<String,Object> map=new HashMap<>();
						map.put("number_code", number_code);							
						//先查询t_front_broker表中是否已经有经纪人使用过
						List<Map<String,Object>> list=platformNumberInfoService.queryBrokerCode(map);
						//查询t_back_number_info表中是否已经生成同样的邀请码
						List<Map<String,Object>> list1=platformNumberInfoService.queryNumberInfoCode(map);
						if(list.size()==0 && list1.size()==0){
							paramsMap.put("number_code", number_code);			
							paramsMap.put("number_id", UUIDGenerator.generate());
							platformNumberInfoService.insertNumberInfo(paramsMap);
							msg.setMsg("新增成功");
							msg.setSuccess(true);
							return msg;
						}
					}
					msg.setMsg("新增失败");
					msg.setSuccess(false);
					return msg;
				}else{//用户自行输入
					paramsMap.put("number_id", UUIDGenerator.generate());
					platformNumberInfoService.insertNumberInfo(paramsMap);
					msg.setMsg("新增成功");
					msg.setSuccess(true);
					return msg;
				}

			}else {//其他类型的批量生成
				if(type.equals("0")){//自定义类型不支持批量增加
					msg.setMsg("新增失败,该靓号类型不支持批量增加");
					msg.setSuccess(false);
					return msg;
				}
				//如果为批量增加，则根据数量批量生成
				int sum=Integer.parseInt(request.getParameter("sum"));
				int count=0;
				for(int i=0;i<sum;i++){
					String number_code=RandomValidateCode.randNumber(Integer.parseInt(type), Integer.parseInt(group_type));
					//查询是否为重复的邀请码				
					HashMap<String,Object> map=new HashMap<>();
					map.put("number_code", number_code);							
					//先查询t_front_broker表中是否已经有经纪人使用过
					List<Map<String,Object>> list=platformNumberInfoService.queryBrokerCode(map);
					//查询t_back_number_info表中是否已经生成同样的邀请码
					List<Map<String,Object>> list1=platformNumberInfoService.queryNumberInfoCode(map);
					if(list.size()==0 && list1.size()==0){
						paramsMap.put("number_code", number_code);
						paramsMap.put("number_id", UUIDGenerator.generate());
						platformNumberInfoService.insertNumberInfo(paramsMap);
						count+=1;
					}
				}
				msg.setMsg("新增成功,已经成功加入"+count+"个靓号");
				msg.setSuccess(true);
				return msg;
			}		
		}catch(Exception e){
			msg.setMsg("新增失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		msg.setMsg("新增成功");
		msg.setSuccess(true);
		return msg;
	}
	/**
	 * 删除靓号信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/numberInfo" })
	@RequestMapping("/delNumberInfo")
	public Object delNumberInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{	
			platformNumberInfoService.deleteNumberInfo(paramsMap);
			msg.setMsg("删除成功");
			msg.setSuccess(true);
		}catch(Exception e){
			msg.setMsg("删除失败");
			msg.setSuccess(false);

		}
		return msg;
	}
	/**
	 * 靓号重复性验证
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/numberInfo" })
	@RequestMapping("/codeValidate")
	public Object codeValidate(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		//先查询t_front_broker表中是否已经有经纪人使用过
		List<Map<String,Object>> list=platformNumberInfoService.queryBrokerCode(paramsMap);
		//查询t_back_number_info表中是否已经生成同样的邀请码
		List<Map<String,Object>> list1=platformNumberInfoService.queryNumberInfoCode(paramsMap);		
		if(list.size()==0 && list1.size()==0){
			msg.setSuccess(true);
		}else{
			msg.setSuccess(false);
		}
		return msg;
	}
	
	
	/**
	 * 靓号合法验证
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/numberInfo" })
	@RequestMapping("/codeRuleValidate")
	public Object codeRuleValidate(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		int i=0;
		//验证靓号是否符合所选规则		
		String number_code=request.getParameter("number_code");
		//靓号组合1、纯字2、纯数字3、字母数字组合
		String group_type=request.getParameter("group_type");
		//靓号类型1-AAAxxx模式  2-AABBxx模式  3-AABBCC模式   4-xxxAAA模式 
		String number_type=request.getParameter("number_type");
		int n=number_code.length();
		if(n!=6){
			msg.setMsg("输入靓号长度必须为6位");
			msg.setSuccess(false);			
			return msg;
		}

		if(number_code!=null && !number_code.equals("") && !group_type.equals("") && group_type!=null && number_type!=null && !number_type.equals("")){	
			
			boolean rule=false;
			switch (group_type) {
			case "1"://1、纯字2、纯数字3、字母数字组合
				rule=number_code.matches("[a-zA-Z]+");
				break;
			case "2"://纯数字
				rule = number_code.matches("[0-9]+"); 
				break;
			case "3":
				boolean rule1=number_code.matches("[a-zA-Z]+");
				boolean rule2=number_code.matches("[0-9]+"); 
				boolean rule3=number_code.matches("[0-9a-zA-Z]+");
				 if(rule1==true || rule2==true){
					rule=false;
				}else if(rule3==true){
					rule=true;					
				}	
				 break;
				
			}
			if(rule==true){
				
				char a=number_code.charAt(0);
				char b=number_code.charAt(1);
				char c=number_code.charAt(2);
				char d=number_code.charAt(3);
				char e=number_code.charAt(4);
				char f=number_code.charAt(5);
				switch (number_type) {
				case "1"://1-AAAAAA模式 
					if(a==b && b==c && c==d  && d==e && e==f)
						i=1;
					break;
				case "2":// 2-AAABBB模式  
					if(a==b && b==c && c!=d && d==e && e==f )
						i=1;					
					break;
				case "3"://3-AABBCC模式 
					if(a==b && c==d && e==f && b!=c && b!=e && c!=e)
						i=1;
					break;
				case "4"://  4-ABABAB模式 
							// abcdef
					if(a==c && c==e && b==d && d==f && a!=b)
						i=1;
					break;
				case "5"://  5-ABCABC模式 
					if(a==d && b==e && c==f && a!=b && a!=c && b!=c)
						i=1;
					break;
				case "0":
					i=1;
					break;
				}
			}
		}
		if(i==1){
			msg.setSuccess(true);
		}else{
			msg.setSuccess(false);
		}
		return msg;
	}
	
}
