package com.acl.utils.tree;

import java.util.List;

import org.apache.tools.ant.types.resources.selectors.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *className:GoodsCategoryTree
 *author:wangli
 *createDate:2017年4月22日 下午5:46:54
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public class GoodsCategoryTree {
	
	//商品类目id
	private String category_id;
	//商品类目名称
    private String category_name;
    //商品类目父级id
    private String superior_id;
    //商品类目层次
    private String category_level;
    //商品类目创建时间
    private String create_time;
    //商品类目修改时间
    private String update_time;
    //商品类目是否可用
    private String is_use;
    
    private List<GoodsCategoryTree> children;

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getSuperior_id() {
		return superior_id;
	}

	public void setSuperior_id(String superior_id) {
		this.superior_id = superior_id;
	}

	public String getCategory_level() {
		return category_level;
	}

	public void setCategory_level(String category_level) {
		this.category_level = category_level;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getIs_use() {
		return is_use;
	}

	public void setIs_use(String is_use) {
		this.is_use = is_use;
	}

	public List<GoodsCategoryTree> getChildren() {
		return children;
	}

	public void setChildren(List<GoodsCategoryTree> children) {
		this.children = children;
	}

	
}
