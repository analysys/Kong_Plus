package com.analysys.kong.model;

public class ApioAuth extends BaseBean {
	private static final long serialVersionUID = -4706658773930282195L;
	private String api_id;
	private String plugin_id;
	private String scope;
	private String scope_need;
	private String expiration;
	private String grant_type;
	private String provision_key;
	private String createtime;
	private String extend;
	
	public String getApi_id() {
		return api_id;
	}
	public void setApi_id(String api_id) {
		this.api_id = api_id;
	}
	public String getPlugin_id() {
		return plugin_id;
	}
	public void setPlugin_id(String plugin_id) {
		this.plugin_id = plugin_id;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getScope_need() {
		return scope_need;
	}
	public void setScope_need(String scope_need) {
		this.scope_need = scope_need;
	}
	public String getExpiration() {
		return expiration;
	}
	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	public String getProvision_key() {
		return provision_key;
	}
	public void setProvision_key(String provision_key) {
		this.provision_key = provision_key;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
}
