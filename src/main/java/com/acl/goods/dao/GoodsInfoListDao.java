package com.acl.goods.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:GoodsInfoListDao
 * author:wangzhe
 * createDate:2017年04月22日 下午2:44:51
 * version:3.0
 * department:安创乐科技
 * description:实物接口
 */
public interface GoodsInfoListDao {

    /**
     * 查询模式一信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryGoodsListInfo(HashMap<String, Object> paramsMap,
                                   PageBounds pageBounds);

    /**
     * 插入模式一信息
     *
     * @param paramsMap
     */
    void insertGoodsInfo(HashMap<String, Object> paramsMap);

    /**
     * 更新模式一信息
     *
     * @param paramsMap
     */
    void updateGoodsInfo(HashMap<String, Object> paramsMap);


    /**
     * 插入模式一部门中间表
     *
     * @param paramsMap
     */
    void insertGoodsDeptInfo(HashMap<String, Object> paramsMap);


    /**
     * 删除商品部门信息
     *
     * @param paramsMap
     */
    void deleteGoodsDept(HashMap<String, Object> paramsMap);

    /**
     * 查询商品和部门信息
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryGoodsDeptInfo(HashMap<String, Object> paramsMap);


    /**
     * 查询商品验证信息
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryIsValidationInfo(HashMap<String, Object> paramsMap);

    /**
     * 查询商品信息
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> selectGoodsList(HashMap<String, Object> paramsMap);

    /**
     * 查询商品信息
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryGoodsValidationInfo(HashMap<String, Object> paramsMap);


}
