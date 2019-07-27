package com.acl.utils.tree;

import java.util.Date;
import java.util.List;

/**
 *className:RoleTree
 *author:wangli
 *createDate:2016年8月13日 上午10:11:07
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public class RoleTree {
	private String role_id;
	private String role_name;
	private String role_image;
	private String role_desc;
	private Date create_date;
	private String is_use;
	private List<RoleTree> children;
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getRole_image() {
		return role_image;
	}
	public void setRole_image(String role_image) {
		this.role_image = role_image;
	}
	public String getRole_desc() {
		return role_desc;
	}
	public void setRole_desc(String role_desc) {
		this.role_desc = role_desc;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getIs_use() {
		return is_use;
	}
	public void setIs_use(String is_use) {
		this.is_use = is_use;
	}
	public List<RoleTree> getChildren() {
		return children;
	}
	public void setChildren(List<RoleTree> children) {
		this.children = children;
	}
	
}
