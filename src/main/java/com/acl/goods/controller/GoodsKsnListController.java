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


@Controller
@RequestMapping("/goods")
public class GoodsKsnListController extends CoreBaseController {

    @Autowired
    private GoodsKsnListService goodsKsnListService;

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
    @RequiresPermissions(value = {"/goods/ksnInfo"})
    @RequestMapping("/ksnInfo")
    public String goodsInfo() {
        return "goods/ksn_info";
    }

    /**
     * 查询商品信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/ksnInfo"})
    @RequestMapping("/queryKsnInfoList")
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
        PageList<?> list = (PageList<?>) goodsKsnListService.queryKsnListInfo(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;

    }

    /*
     * 查询模式一与部门信息关系
     * @return
     **/
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/ksnInfo"})
    @RequestMapping("/queryKsnDeptInfo")
    public Object queryGoodsDeptInfo(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {

        if (paramsMap != null && paramsMap.containsKey("goods_id")) {
            paramsMap.put("goods_id", paramsMap.get("goods_id").toString().trim());
        }
        paramsMap.put("is_use", "1");
        List<Map<String, Object>> list = goodsKsnListService.queryGoodsDeptInfo(paramsMap);
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
    @RequiresPermissions(value = {"/goods/ksnInfo"})
    @RequestMapping("/insKsnInfo")
    public Object insertGoodsInfo(@Session(create = false) SessionProvider session,@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

            dbLog.setMethod_name("新增机具信息");
            dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
            dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));

            paramsMap.put("id", "0");
            //paramsMap.put("goods_id", UUIDGenerator.generate());
            paramsMap.put("create_time", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
            paramsMap.put("update_time", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));

            goodsKsnListService.insertKsnInfo(paramsMap);

            dbLog.setAction_message("新增成功,新增机具名为:" + StringUtils.convertString(paramsMap.get("machine_name")) + "机具编号：" + StringUtils.convertString(paramsMap.get("machine_code")) + "机具品牌：" + StringUtils.convertString(paramsMap.get("brand")) + "机具类型：" + StringUtils.convertString(paramsMap.get("type")) + "激活状态" + StringUtils.convertString(paramsMap.get("activation_status")) + "使用状态" + StringUtils.convertString(paramsMap.get("usage_status")));
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
    @RequiresPermissions(value = {"/goods/ksnInfo"})
    @RequestMapping("/modKsnInfo")
    public Object modGoodsInfo(@Session(create = false) SessionProvider session,@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {

            LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

            dbLog.setMethod_name("编辑机具信息");
            dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
            dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
            // 更新Redis
            HashMap<String, Object> paramItem = new HashMap<String, Object>();
            HashMap<String, Object> paramSpec = new HashMap<String, Object>();

            paramsMap.put("update_time", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));

            //详情
            paramItem.put("id", paramsMap.get("id"));
            paramItem.put("brand", paramsMap.get("brand"));
            paramItem.put("type", paramsMap.get("type"));
            paramItem.put("machine_name", paramsMap.get("machine_name"));
            paramItem.put("machine_code", paramsMap.get("machine_code"));
            paramItem.put("agent_id", paramsMap.get("agent_id"));
            paramItem.put("user_id", paramsMap.get("user_id"));
            paramItem.put("activation_status", paramsMap.get("activation_status"));
            paramItem.put("usage_status", paramsMap.get("usage_status"));
            paramItem.put("handout_status", paramsMap.get("handout_status"));

            goodsKsnListService.modKsnInfo(paramsMap);

            dbLog.setAction_message("编辑成功,编辑机具名为:" + StringUtils.convertString(paramsMap.get("machine_name")) + "机具编号：" + StringUtils.convertString(paramsMap.get("machine_code")) + "机具品牌：" + StringUtils.convertString(paramsMap.get("brand")) + "机具类型：" + StringUtils.convertString(paramsMap.get("type")) + "激活状态" + StringUtils.convertString(paramsMap.get("activation_status")) + "使用状态" + StringUtils.convertString(paramsMap.get("usage_status")));
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
    @RequiresPermissions(value = {"/goods/ksnInfo"})
    @RequestMapping("/initKsnInfo")
    public Object initGoodsInfo(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) throws Exception {

        //图片显示
        List<Map<String, Object>> listMap = goodsKsnListService.queryGoodsValidationInfo(paramsMap);

        if (listMap.size() > 0) {
            if (listMap.get(0).get("img_goods_id") != null) {
                if (listMap.size() == 3) {
                    if (listMap.get(0).get("param_goods_id") != null) {
                        if (listMap.get(0).get("spec_goods_id") != null) {
                            if (listMap.get(0).get("pror_goods_id") != null) {
                                //goodsKsnListService.initGoodsToRedis();
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
    @RequiresPermissions(value = {"/goods/ksnInfo"})
    @RequestMapping("/saveKsnDeptInfo")
    public Object saveGoodsDeptInfo(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        // trim登陆名前后空白
        try {
            String dept_id = paramsMap.get("dept_id").toString();
            if (dept_id != "") {
                paramsMap.put("goods_id", paramsMap.get("goods_id").toString().trim());
                List<Map<String, Object>> parmap = goodsKsnListService.selectGoodsList(paramsMap);

                if (parmap != null && parmap.get(0).get("spec_id") != null) {
                    paramsMap.put("spec_id", parmap.get(0).get("spec_id").toString());
                    paramsMap.put("create_time", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));


                    if (paramsMap != null && paramsMap.containsKey("dept_id")) {
                        String[] dept_ida = dept_id.split(",");
                        for (int i = 0; i < dept_ida.length; i++) {
                            paramsMap.put("dept_id", dept_ida[i].toString());
                            // 插入库存表中
                            if (goodsKsnListService.queryGoodsDeptInfo(paramsMap).size() == 0) {
                                goodsKsnListService.insertGoodsDeptInfo(paramsMap);
                            }
                        }
                    }
                    //goodsKsnListService.initStockToRedis();
                    msg.setMsg("保存成功");
                    msg.setSuccess(true);
                } else {
                    msg.setMsg("保存失败,请设置该商品的规格信息");
                    msg.setSuccess(false);
                }
            } else {
                //不选择部门提交，将删除对应的表数据并更新redis
                goodsKsnListService.deleteGoodsDept(paramsMap);
                //goodsKsnListService.initStockToRedis();
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
     * 验证同一商品的商品参数是否重复
     *
     * @return
     */
   /* @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/ksnInfo"})
    @RequestMapping("/paramValidate")
    public Object paramValidate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = goodsParamService.paramValidate(paramsMap);
        if (list.size() == 0) {
            msg.setSuccess(true);
        } else {
            msg.setSuccess(false);
        }
        return msg;
    }*/

    /**
     * 获取系统中商品编码和商品名称信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/queryKsnName")
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
    @RequiresPermissions(value = {"/goods/ksnInfo"})
    @RequestMapping("/queryKsnProrate")
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
    @RequiresPermissions(value = {"/goods/ksnInfo"})
    @RequestMapping("/updKsnProrate")
    public Object updGoodsProrate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            goodsProrateService.updateGoodsProrate(paramsMap);
            //更新商品提成比率后写入到redis
            goodsKsnListService.saveGoodsProrateToRedis(Long.valueOf(paramsMap.get("prorate_goods_id").toString()));
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
    @RequiresPermissions(value = {"/goods/ksnInfo"})
    @RequestMapping("/insKsnProrate")
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
     *//*
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/ksnInfo"})
    @RequestMapping("/prorateValidate")
    public Object prorateValidate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = goodsProrateService.prorateValidate(paramsMap);
        if (list.size() == 0) {
            msg.setSuccess(true);
        } else {
            msg.setSuccess(false);
        }
        return msg;
    }*/

    /**
     * 获取等级id和名称
     *
     * @return
     */
   /* @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/getMallLevel")
    public Object getMallLevel(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = goodsProrateService.getMallLevel(paramsMap);
        return list;
    }*/

    /**
     * 查询商品规格信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/ksnInfo"})
    @RequestMapping("/queryKsnSpec")
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
    @RequiresPermissions(value = {"/goods/ksnInfo"})
    @RequestMapping("/updKsnSpec")
    public Object updGoodsSpec(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            goodsSpecService.updateKsnSpec(paramsMap);
            //更新规格后写入到redis
            //goodsKsnListService.saveGoodsSpecToRedis(Long.valueOf(paramsMap.get("goods_ids").toString()));
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
    @RequiresPermissions(value = {"/goods/ksnInfo"})
    @RequestMapping("/insKsnSpec")
    public Object insGoodsSpec(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {

            GoodsSpec goodsSpec = new GoodsSpec();

            goodsSpec.setGoods_id(paramsMap.get("id").toString());
            goodsSpec.setSpec_name(paramsMap.get("usage_status").toString());
            goodsSpec.setIs_use(paramsMap.get("activation_status").toString());
            goodsSpec.setSpec_order(paramsMap.get("handout_status").toString());
            //新增规格
            goodsSpecService.insertKsnSpec(goodsSpec);
            //初始库存信息
            paramsMap.put("spec_id", goodsSpec.getSpec_id());
            paramsMap.put("goods_id", paramsMap.get("goods_ids").toString());
            paramsMap.put("create_time", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
            goodsKsnListService.insertGoodsDeptInfo(paramsMap);

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
   /* @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/ksnInfo"})
    @RequestMapping("/specValidate")
    public Object specValidate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = goodsSpecService.specValidate(paramsMap);
        if (list.size() == 0) {
            msg.setSuccess(true);
        } else {
            msg.setSuccess(false);
        }
        return msg;
    }*/



    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/ksnInfo"})
    @RequestMapping("/centerKsnInfo")
    public String centerKsnInfo() {
        return "goods/center_ksn_info";
    }



    /**
     * 查询商品信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/ksnInfo"})
    @RequestMapping("/queryCenterKsnInfoList")
    public Object queryCenterKsnInfoList(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                     @RequestParam(defaultValue = "0", required = false) Integer page,
                                     @RequestParam(defaultValue = "10", required = false) Integer rows,
                                     @RequestParam HashMap<String, Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(orderByCreateTime));
        if (paramsMap.get("sort") != null) {
            paramsMap.put("order_type", paramsMap.get("sort").toString() + " " + paramsMap.get("order").toString());
            pageBounds = new PageBounds(page, rows, Order.formString(paramsMap.get("sort").toString() + "." + paramsMap.get("order").toString()));
        }
        //查询用户信息
        PageList<?> list = (PageList<?>) goodsKsnListService.queryKsnListInfo(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;

    }

}