package com.acl.utils.tree;

import java.util.List;

/**
 *className:GridTree
 *author:wangli
 *createDate:2016年8月11日 下午7:56:24
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public class GridTree {
	//菜单id
	private String menu_id;
	//菜单名称
	private String menu_name;
	//菜单地址
	private String menu_url;
	//菜单图片
	private String menu_icon;
	//菜单序列
	private String menu_index;
	//父级菜单
	private String menu_parent_id;
	//是否叶子
	private String is_leaf;
	//是否可用
	private String is_use;
	//创建时间
	private String create_date;
	//是否复选框选中
	private Boolean checked=false;
	
	private List<GridTree> children;
	/**
     * 是否复选框选中
     */
  
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	public String getIs_use() {
		return is_use;
	}
	public void setIs_use(String is_use) {
		this.is_use = is_use;
	}
	public List<GridTree> getChildren() {
		return children;
	}
	public void setChildren(List<GridTree> children) {
		this.children = children;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public String getMenu_icon() {
		return menu_icon;
	}
	public void setMenu_icon(String menu_icon) {
		this.menu_icon = menu_icon;
	}
	public String getMenu_parent_id() {
		return menu_parent_id;
	}
	public void setMenu_parent_id(String menu_parent_id) {
		this.menu_parent_id = menu_parent_id;
	}
	public String getIs_leaf() {
		return is_leaf;
	}
	public void setIs_leaf(String is_leaf) {
		this.is_leaf = is_leaf;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getMenu_index() {
		return menu_index;
	}
	public void setMenu_index(String menu_index) {
		this.menu_index = menu_index;
	}
	
}
