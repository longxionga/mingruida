package com.acl.utils.tree;

import java.util.List;


/**
 *className:ComboTree
 *author:malx
 *createDate:2016年8月11日 下午5:30:51
 *vsersion:3.0
 *department:安创乐科技
 *description:下拉树模板类
 */
public class ComboTree {
	private String id;
	private String text;
	private boolean checked;
	private String iconCls;
	private List<ComboTree> children;
	
	public final String getId() {
		return id;
	}
	public final void setId(String id) {
		this.id = id;
	}
	public final String getText() {
		return text;
	}
	public final void setText(String text) {
		this.text = text;
	}
	public final boolean isChecked() {
		return checked;
	}
	public final void setChecked(boolean checked) {
		this.checked = checked;
	}
	public final String getIconCls() {
		return iconCls;
	}
	public final void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public final List<ComboTree> getChildren() {
		return children;
	}
	public final void setChildren(List<ComboTree> children) {
		this.children = children;
	}
}
