package com.acl.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GoodsSpec implements Serializable {

	private static final long serialVersionUID = 1L;


	private String spec_id; 	//规格ID
	private String goods_id; 	// 商品ID
	private String spec_type; 	// 规格类型
	private String spec_name; 	// 规格名
	private String spec_order; 	// 排序字段
	private String create_time; // 创建时间
	private String update_time; // 更新时间
	private String is_use; 		// 是否可用


	public String getSpec_id() {
		return spec_id;
	}

	public void setSpec_id(String spec_id) {
		this.spec_id = spec_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getSpec_type() {
		return spec_type;
	}

	public void setSpec_type(String spec_type) {
		this.spec_type = spec_type;
	}

	public String getSpec_name() {
		return spec_name;
	}

	public void setSpec_name(String spec_name) {
		this.spec_name = spec_name;
	}

	public String getSpec_order() {
		return spec_order;
	}

	public void setSpec_order(String spec_order) {
		this.spec_order = spec_order;
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
}
