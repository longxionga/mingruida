package com.acl.goods.controller;

import com.acl.core.CoreBaseController;
import com.acl.goods.service.*;
import com.acl.pojo.GoodsSpec;
import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.FormatDateUtil;
import com.acl.utils.util.NetUtils;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;
import net.sf.json.JSONArray;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * className:GoodInfoListController
 * author:wangzhe
 * createDate:2017年04月22日 下午03:14:58
 * version:3.0
 * department:安创乐科技
 * description:商品信息管理
 */

@Controller
@RequestMapping("/goods")
public class GoodsInfoListController extends CoreBaseController {

    @Autowired
    private GoodsInfoListService goodsInfoListService;

    @Autowired
    private GoodsItemService goodsItemService;

    @Autowired
    private GoodsSpecService goodsSpecService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GoodsParamService goodsParamService;

    @Autowired
    private GoodsProrateService goodsProrateService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/goodsInfo")
    public String goodsInfo() {
        return "goods/goods_info";
    }

    /**
     * 查询商品信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/queryGoodsInfoList")
    public Object queryGoodsInfoList(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                     @RequestParam(defaultValue = "0", required = false) Integer page,
                                     @RequestParam(defaultValue = "10", required = false) Integer rows,
                                     @RequestParam HashMap<String, Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(orderByCreateTime));
        if (paramsMap.get("sort") != null) {
            paramsMap.put("order_type", paramsMap.get("sort").toString() + " " + paramsMap.get("order").toString());
            pageBounds = new PageBounds(page, rows, Order.formString(paramsMap.get("sort").toString() + "." + paramsMap.get("order").toString()));
        }
        //查询用户信息
        PageList<?> list = (PageList<?>) goodsInfoListService.queryGoodsListInfo(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;

    }

    /*
     * 查询模式一与部门信息关系
     * @return
     **/
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/queryGoodsDeptInfo")
    public Object queryGoodsDeptInfo(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {

        if (paramsMap != null && paramsMap.containsKey("goods_id")) {
            paramsMap.put("goods_id", paramsMap.get("goods_id").toString().trim());
        }
        paramsMap.put("is_use", "1");
        List<Map<String, Object>> list = goodsInfoListService.queryGoodsDeptInfo(paramsMap);
        String jsonStr = JSONArray.fromObject(list).toString();
        JSONArray ja = JSONArray.fromObject(jsonStr);
        return ja;
    }


    /**
     * 新增商品操作
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/insGoodsInfo")
    public Object insertGoodsInfo(@Session(create = false) SessionProvider session,@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

            dbLog.setMethod_name("新增商品信息");
            dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
            dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));

            paramsMap.put("goods_id", "0");
            //paramsMap.put("goods_id", UUIDGenerator.generate());
            paramsMap.put("create_time", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
            paramsMap.put("update_time", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));

            goodsInfoListService.insertGoodsInfo(paramsMap);

            dbLog.setAction_message("新增成功,新增商品名为:" + StringUtils.convertString(paramsMap.get("goods_name")) + "商品标号：" + StringUtils.convertString(paramsMap.get("goods_code")) + "是否可用：" + StringUtils.convertString(paramsMap.get("is_use")) + "商品标签：" + StringUtils.convertString(paramsMap.get("goods_label")) + "商品别名：" + StringUtils.convertString(paramsMap.get("goods_alias")) + "商品描述" + StringUtils.convertString(paramsMap.get("description")) + "商品类别" + StringUtils.convertString(paramsMap.get("category_id")));
            dbLog.setIs_ok(0);
            msg.setMsg("新增成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            dbLog.setAction_message("新增失败");
            dbLog.setIs_ok(1);
            msg.setMsg("插入失败");
            msg.setSuccess(false);
            e.printStackTrace();
        } finally {
            dbLogService.insertLog(dbLog);
        }
        return msg;
    }

    /**
     * 更新商品信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/modGoodsInfo")
    public Object modGoodsInfo(@Session(create = false) SessionProvider session,@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {

            LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

            dbLog.setMethod_name("新增商品信息");
            dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
            dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
            // 更新Redis
            HashMap<String, Object> paramItem = new HashMap<String, Object>();//商品详情
            HashMap<String, Object> paramSpec = new HashMap<String, Object>();//商品规格

            paramsMap.put("update_time", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));

            //详情
            paramItem.put("goods_id", paramsMap.get("goods_id"));
            paramItem.put("unit", paramsMap.get("unit"));
            paramItem.put("fare_price", paramsMap.get("fare_price"));
            paramItem.put("description", paramsMap.get("description"));
            paramItem.put("is_use", paramsMap.get("item_is_use"));

            goodsInfoListService.modGoodsInfo(paramsMap);

            dbLog.setAction_message("编辑成功,编辑商品名为:" + StringUtils.convertString(paramsMap.get("goods_name")) + "商品标号：" + StringUtils.convertString(paramsMap.get("goods_code")) + "是否可用：" + StringUtils.convertString(paramsMap.get("is_use")) + "商品标签：" + StringUtils.convertString(paramsMap.get("goods_label")) + "商品别名：" + StringUtils.convertString(paramsMap.get("goods_alias")) + "商品描述" + StringUtils.convertString(paramsMap.get("description")) + "商品类别" + StringUtils.convertString(paramsMap.get("category_id")));
            dbLog.setIs_ok(0);
            msg.setMsg("更新成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            dbLog.setAction_message("编辑失败");
            dbLog.setIs_ok(1);
            msg.setMsg("更新失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }finally {
            dbLogService.insertLog(dbLog);
        }
        return msg;
    }

    /**
     * 初始化商品信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/initGoodsInfo")
    public Object initGoodsInfo(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) throws Exception {

        //图片显示
        List<Map<String, Object>> listMap = goodsInfoListService.queryGoodsValidationInfo(paramsMap);

        if (listMap.size() > 0) {
            if (listMap.get(0).get("img_goods_id") != null) {
                if (listMap.size() == 3) {
                    if (listMap.get(0).get("param_goods_id") != null) {
                        if (listMap.get(0).get("spec_goods_id") != null) {
                            if (listMap.get(0).get("pror_goods_id") != null) {
                                //goodsInfoListService.initGoodsToRedis();
                                msg.setSuccess(true);
                                msg.setMsg("商品初始化成功!");
                            } else {
                                msg.setSuccess(false);
                                msg.setMsg("商品初始化失败，请添加商品比率!");
                            }
                        } else {
                            msg.setSuccess(false);
                            msg.setMsg("商品初始化失败，请添加商品规格!");
                        }
                    } else {
                        msg.setSuccess(false);
                        msg.setMsg("商品初始化失败，请添加商品参数!");
                    }
                } else {
                    msg.setSuccess(false);
                    msg.setMsg("商品初始化失败，请把图片添加完整!");
                }
            } else {
                msg.setSuccess(false);
                msg.setMsg("商品初始化失败，请添加商品图片!");
            }

        } else {
            msg.setSuccess(false);
            msg.setMsg("商品初始化失败，请添加商品图片!");
        }

        return msg;
    }

    /**
     * 保存商品信息到库存表中
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/saveGoodsDeptInfo")
    public Object saveGoodsDeptInfo(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        // trim登陆名前后空白
        try {
            String dept_id = paramsMap.get("dept_id").toString();
            if (dept_id != "") {
                paramsMap.put("goods_id", paramsMap.get("goods_id").toString().trim());
                List<Map<String, Object>> parmap = goodsInfoListService.selectGoodsList(paramsMap);

                if (parmap != null && parmap.get(0).get("spec_id") != null) {
                    paramsMap.put("spec_id", parmap.get(0).get("spec_id").toString());
                    paramsMap.put("create_time", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));


                    if (paramsMap != null && paramsMap.containsKey("dept_id")) {
                        String[] dept_ida = dept_id.split(",");
                        for (int i = 0; i < dept_ida.length; i++) {
                            paramsMap.put("dept_id", dept_ida[i].toString());
                            // 插入库存表中
                            if (goodsInfoListService.queryGoodsDeptInfo(paramsMap).size() == 0) {
                                goodsInfoListService.insertGoodsDeptInfo(paramsMap);
                            }
                        }
                    }
                    //goodsInfoListService.initStockToRedis();
                    msg.setMsg("保存成功");
                    msg.setSuccess(true);
                } else {
                    msg.setMsg("保存失败,请设置该商品的规格信息");
                    msg.setSuccess(false);
                }
            } else {
                //不选择部门提交，将删除对应的表数据并更新redis
                goodsInfoListService.deleteGoodsDept(paramsMap);
                //goodsInfoListService.initStockToRedis();
                msg.setMsg("保存成功");
                msg.setSuccess(true);
            }
        } catch (Exception e) {
            msg.setMsg("保存失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }


    /**
     * 查询商品属性信息
     *
     * @return
     */
   /* @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/queryGoodsParam")
    public Object queryGoodsParam(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                  @RequestParam(defaultValue = "0", required = false) Integer page,
                                  @RequestParam(defaultValue = "10", required = false) Integer rows,
                                  @RequestParam HashMap<String, Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(""));
        //查询用户信息
        PageList<?> list = (PageList<?>) goodsParamService.queryGoodsParam(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;

    }*/


    /**
     * 修改商品属性信息
     *
     * @return
     */
    /*@ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/updGoodsParam")
    public Object updGoodsParam(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            goodsParamService.updateGoodsParam(paramsMap);

            //更新参数后写入到redis
            goodsInfoListService.saveGoodsParamToRedis(Long.valueOf(paramsMap.get("param_goods_id").toString()));
            msg.setMsg("更新成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("更新失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }
*/

    /**
     * 添加商品属性信息
     *
     * @return
     */
   /* @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/insGoodsParam")
    public Object insGoodsParam(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            //第三步操作商品参数

            goodsParamService.insertGoodsParam(paramsMap);
            msg.setMsg("新增成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("新增失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }*/


    /**
     * 验证同一商品的商品参数是否重复
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/paramValidate")
    public Object paramValidate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = goodsParamService.paramValidate(paramsMap);
        if (list.size() == 0) {
            msg.setSuccess(true);
        } else {
            msg.setSuccess(false);
        }
        return msg;
    }

    /**
     * 获取系统中商品编码和商品名称信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/queryGoodsName")
    public Object queryGoodsName(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = goodsParamService.queryGoodsName(paramsMap);
        return list;
    }


    /**
     * 查询商品提成比率信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/queryGoodsProrate")
    public Object queryGoodsProrate(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                    @RequestParam(defaultValue = "0", required = false) Integer page,
                                    @RequestParam(defaultValue = "10", required = false) Integer rows,
                                    @RequestParam HashMap<String, Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(""));
        //查询用户信息
        PageList<?> list = (PageList<?>) goodsProrateService.queryGoodsProrate(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;

    }


    /**
     * 修改商品提成比率信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/updGoodsProrate")
    public Object updGoodsProrate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            goodsProrateService.updateGoodsProrate(paramsMap);
            //更新商品提成比率后写入到redis
            goodsInfoListService.saveGoodsProrateToRedis(Long.valueOf(paramsMap.get("prorate_goods_id").toString()));
            msg.setMsg("更新成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("更新失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }


    /**
     * 添加商品提成比率信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/insGoodsProrate")
    public Object insGoodsProrate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            goodsProrateService.insertGoodsProrate(paramsMap);

            //第五步操作商品比率
            msg.setMsg("新增成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("新增失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 验证同一商品的商品参数是否重复
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/prorateValidate")
    public Object prorateValidate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = goodsProrateService.prorateValidate(paramsMap);
        if (list.size() == 0) {
            msg.setSuccess(true);
        } else {
            msg.setSuccess(false);
        }
        return msg;
    }

    /**
     * 获取等级id和名称
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/getMallLevel")
    public Object getMallLevel(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = goodsProrateService.getMallLevel(paramsMap);
        return list;
    }

    /**
     * 查询商品规格信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/queryGoodsSpec")
    public Object queryGoodsSpec(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                 @RequestParam(defaultValue = "0", required = false) Integer page,
                                 @RequestParam(defaultValue = "10", required = false) Integer rows,
                                 @RequestParam HashMap<String, Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(""));
        //查询用户信息
        PageList<?> list = (PageList<?>) goodsSpecService.queryGoodsSpec(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;

    }


    /**
     * 修改商品规格信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/updGoodsSpec")
    public Object updGoodsSpec(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            goodsSpecService.updateGoodsSpec(paramsMap);
            //更新规格后写入到redis
            //goodsInfoListService.saveGoodsSpecToRedis(Long.valueOf(paramsMap.get("goods_ids").toString()));
            msg.setMsg("更新成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("更新失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }


    /**
     * 添加商品规格信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/insGoodsSpec")
    public Object insGoodsSpec(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {

            GoodsSpec goodsSpec = new GoodsSpec();

            goodsSpec.setGoods_id(paramsMap.get("goods_ids").toString());
            goodsSpec.setSpec_name(paramsMap.get("spec_names").toString());
            goodsSpec.setIs_use(paramsMap.get("is_uses").toString());
            goodsSpec.setSpec_order(paramsMap.get("spec_order").toString());
            //新增规格
            goodsSpecService.insertGoodsSpec(goodsSpec);
            //初始库存信息
            paramsMap.put("spec_id", goodsSpec.getSpec_id());
            paramsMap.put("goods_id", paramsMap.get("goods_ids").toString());
            paramsMap.put("create_time", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
            goodsInfoListService.insertGoodsDeptInfo(paramsMap);

            //第四步操作商品规格
            msg.setMsg("新增成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("新增失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }


    /**
     * 验证同一商品的商品参数是否重复
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsInfo"})
    @RequestMapping("/specValidate")
    public Object specValidate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = goodsSpecService.specValidate(paramsMap);
        if (list.size() == 0) {
            msg.setSuccess(true);
        } else {
            msg.setSuccess(false);
        }
        return msg;
    }
}