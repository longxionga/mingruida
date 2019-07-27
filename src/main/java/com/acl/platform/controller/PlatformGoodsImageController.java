package com.acl.platform.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.acl.goods.service.GoodsCategoryService;
import com.acl.utils.util.UUIDGenerator;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformGoodsImageService;
import com.acl.sys.service.impl.SysFileServiceImpl;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 * className:PlatformGoodsImage
 * author:wangli
 * createDate:2017年2月6日 下午4:16:46
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Controller
@RequestMapping("platform")
public class PlatformGoodsImageController extends CoreBaseController {
    private SystemConfig systemConfig;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private IPlatformGoodsImageService platformGoodsImageService;
    @Autowired
    private SysFileServiceImpl sysFileService;
    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/goodsImage"})
    @RequestMapping("/goodsImage")
    public String goodsImage() {
        return "platform/platform_goods_image";
    }


    /**
     * 查询商品图片信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/goodsImage"})
    @RequestMapping("/queryGoodsImage")
    public Object queryGoodsImage(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                  @RequestParam(defaultValue = "0", required = false) Integer page,
                                  @RequestParam(defaultValue = "10", required = false) Integer rows,
                                  @RequestParam HashMap<String, Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(""));
        //查询用户信息
        PageList<?> list = (PageList<?>) platformGoodsImageService.queryGoodsImage(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;

    }


    /**
     * 修改商品图片信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/goodsImage"})
    @RequestMapping("/updGoodsImage")
    public Object updGoodsImage(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            String[] goods_urls = paramsMap.get("category_url").toString().split(",");
            if (goods_urls.length > 0) {
                String path = goods_urls[0].toString();

                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("id", paramsMap.get("id"));
                if (path.indexOf(systemConfig.vsimgUrl) != -1) {
                    map.put("category_url", path);
                } else {
                    map.put("category_url", systemConfig.vsimgUrl + path);
                }

                map.put("category_name", paramsMap.get("category_name"));
                map.put("category_code", paramsMap.get("category_code"));
                map.put("type", paramsMap.get("type"));
                map.put("is_order", paramsMap.get("is_order"));
                map.put("category_desc", paramsMap.get("category_desc"));
                platformGoodsImageService.updateGoodsImage(map);
            }

            if (paramsMap.get("type").equals("0")) {
                //查杀所有数据库中type为0的图片链接
                List<Map<String, Object>> list = platformGoodsImageService.queryGoodsUrl(paramsMap);
                String urlKey = "";
                for (int i = 0; i < list.size(); i++) {
                    if (i != (list.size() - 1)) {
                        urlKey += list.get(i).get("category_url") + ",";
                    } else {
                        urlKey += list.get(i).get("category_url");
                    }
                }
                //将数据库信息同步到redis中
                //String key = "banner_key";
                //redisTemplate.opsForValue().set(key, String.valueOf(urlKey));
            }
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
     * 添加商品图片信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/goodsImage"})
    @RequestMapping("/insGoodsImage")
    public Object insGoodsImage(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            String[] goods_urls = paramsMap.get("category_url").toString().split(",");
            if (goods_urls.length > 0) {
                for (int i = 0; i < goods_urls.length; i++) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("id", UUIDGenerator.generate());
                    map.put("category_url", systemConfig.vsimgUrl + goods_urls[i]);
                    map.put("category_name", paramsMap.get("category_name"));
                    map.put("category_code", paramsMap.get("category_code"));
                    map.put("type", paramsMap.get("type"));
                    if (paramsMap.get("is_order") == null || paramsMap.get("is_order") == "") {
                        map.put("is_order", i + 1);
                    } else {
                        map.put("is_order", paramsMap.get("is_order"));
                    }

                    map.put("category_desc", paramsMap.get("category_desc"));
                    platformGoodsImageService.insertGoodsImage(map);
                }
                if (paramsMap.get("type").equals("0")) {
                    List<Map<String, Object>> list = platformGoodsImageService.queryGoodsUrl(paramsMap);
                    String urlKey = "";
                    for (int i = 0; i < list.size(); i++) {
                        if (i != (list.size() - 1)) {
                            urlKey += list.get(i).get("category_url") + ",";
                        } else {
                            urlKey += list.get(i).get("category_url");
                        }
                    }

                }
            }
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
     * 删除商品图片信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/goodsImage"})
    @RequestMapping("/delGoodsImage")
    public Object delGoodsImage(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        try {
            platformGoodsImageService.deleteGoodsImage(paramsMap);
            sysFileService.deleteFile(request.getParameter("category_url"));
            if (paramsMap.get("type").equals("0")) {
                //查杀所有数据库中type为0的图片链接
                List<Map<String, Object>> list = platformGoodsImageService.queryGoodsUrl(paramsMap);
                String urlKey = "";
                for (int i = 0; i < list.size(); i++) {
                    if (i != (list.size() - 1)) {
                        urlKey += list.get(i).get("category_url") + ",";
                    } else {
                        urlKey += list.get(i).get("category_url");
                    }
                }
                //将数据库信息同步到redis中
                String key = "banner_key";
                redisTemplate.opsForValue().set(key, String.valueOf(urlKey));
            }
            msg.setMsg("删除成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("删除失败");
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
    //@RequiresPermissions(value = { "/sys/dictInfo" })
    @RequestMapping("/getGoods")
    public Object getGoods(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = platformGoodsImageService.queryGoodsName(paramsMap);
        return list;
    }

    /**
     * 获取系统中商品编码和商品名称信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    //@RequiresPermissions(value = { "/sys/dictInfo" })
    @RequestMapping("/getGoodsCategory")
    public Object getGoodsCategory(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = goodsCategoryService.getGoodsCategory(paramsMap);
        return list;
    }
}
