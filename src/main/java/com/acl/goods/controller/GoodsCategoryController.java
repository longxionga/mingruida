package com.acl.goods.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.acl.goods.service.GoodsCategoryService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;
import com.acl.utils.tree.GoodsCategoryTree;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 * className:GoodsCategoryController
 * author:wangli
 * createDate:2017年4月22日 上午9:56:00
 * version:3.0
 * department:安创乐科技
 * description:商品类目管理
 */
@Controller
@RequestMapping("/goods")
public class GoodsCategoryController extends CoreBaseController {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsCategory"})
    @RequestMapping("/goodsCategory")
    public String goodsCategory(@Session(create = false) SessionProvider session, ModelMap modelMap) {
        return "goods/goods_category";
    }


    /**
     * 查询所有商品类目信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsCategory"})
    @RequestMapping("/queryGoodsCategory")
    public Object queryGoodsCategory() {
        List<GoodsCategoryTree> list = new ArrayList<GoodsCategoryTree>();
        GoodsCategoryTree goodsCategoryTree = new GoodsCategoryTree();
        goodsCategoryTree.setCategory_id("-1");
        goodsCategoryTree.setCategory_name("机具品牌管理");
        goodsCategoryTree.setSuperior_id("");
        goodsCategoryTree.setCategory_level("0");
        goodsCategoryTree.setCreate_time("");
        goodsCategoryTree.setUpdate_time("");
        goodsCategoryTree.setIs_use("");
        goodsCategoryTree.setChildren(initTree("-1"));
        list.add(goodsCategoryTree);
        return list;
    }

    /**
     * 初始化树
     *
     * @param category_id
     * @return
     */
    private List<GoodsCategoryTree> initTree(String category_id) {
        List<GoodsCategoryTree> list = new ArrayList<GoodsCategoryTree>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("category_id", category_id);
        List<Map<String, Object>> menus = goodsCategoryService.queryGoodsCategory(map);
        if (menus != null) {
            for (Map<String, Object> u : menus) {
                GoodsCategoryTree goodsCategoryTree = new GoodsCategoryTree();
                goodsCategoryTree.setCategory_id(StringUtils.convertString(u.get("category_id")));
                goodsCategoryTree.setCategory_name(StringUtils.convertString(u.get("category_name")));
                goodsCategoryTree.setSuperior_id(StringUtils.convertString(u.get("superior_id")));
                goodsCategoryTree.setCategory_level(StringUtils.convertString(u.get("category_level")));
                goodsCategoryTree.setCreate_time(StringUtils.convertString(u.get("create_time")));
                goodsCategoryTree.setUpdate_time(StringUtils.convertString(u.get("update_time")));
                goodsCategoryTree.setIs_use(StringUtils.convertString(u.get("is_use")));
                goodsCategoryTree.setChildren(initTree(StringUtils.convertString(u.get("category_id"))));
                list.add(goodsCategoryTree);
            }
        }
        return list;
    }


    /**
     * 添加商品类目信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsCategory"})
    @RequestMapping("/insGoodsCategory")
    public Object insGoodsCategory(@Session(create = false) SessionProvider session, @RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            paramsMap.put("create_time", sdf.format(new java.util.Date()));
            int level = Integer.parseInt(StringUtils.convertString(paramsMap.get("category_level"))) + 1;
            paramsMap.put("category_level", level);
            goodsCategoryService.insertGoodsCategory(paramsMap);
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
     * 添加商品类目信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsCategory"})
    @RequestMapping("/updGoodsCategory")
    public Object updGoodsCategory(@Session(create = false) SessionProvider session, @RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            paramsMap.put("update_time", sdf.format(new java.util.Date()));
            goodsCategoryService.updateGoodsCategory(paramsMap);
            msg.setMsg("编辑成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("编辑失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 类目名称重复性验证
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsCategory"})
    @RequestMapping("/categoryValidate")
    public Object categoryValidate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = goodsCategoryService.categoryValidate(paramsMap);
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
    @RequestMapping("/getGoodsCategory")
    public Object getGoodsCategory(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = goodsCategoryService.getGoodsCategory(paramsMap);
        return list;
    }
}
