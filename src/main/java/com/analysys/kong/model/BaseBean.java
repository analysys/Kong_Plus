package com.analysys.kong.model;

import java.io.Serializable;

public class BaseBean implements Serializable {
	private static final long serialVersionUID = 3301025076973207627L;
	private Integer offset;
	private Integer limit;
	private String sys_type;
	private String user_role;
	
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public String getSys_type() {
		return sys_type;
	}
	public void setSys_type(String sys_type) {
		this.sys_type = sys_type;
	}
	public String getUser_role() {
		return user_role;
	}
	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}
}
