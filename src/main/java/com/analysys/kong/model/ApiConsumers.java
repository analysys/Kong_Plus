package com.analysys.kong.model;

public class ApiConsumers extends BaseBean {
	private static final long serialVersionUID = 7090561020306229274L;
	private String id;
	private String api_id;
	private String consumer_id;
	private String consumer_name;
	private String max_callcount;
	private String max_callcount_ok;
	private String remarks;
	private String keys;
	private String client_id;
	private String client_secret;
	private String provision_key;
	private String auth_type;
	private String createtime;
	private String extend;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApi_id() {
		return api_id;
	}
	public void setApi_id(String api_id) {
		this.api_id = api_id;
	}
	public String getConsumer_id() {
		return consumer_id;
	}
	public void setConsumer_id(String consumer_id) {
		this.consumer_id = consumer_id;
	}
	public String getConsumer_name() {
		return consumer_name;
	}
	public void setConsumer_name(String consumer_name) {
		this.consumer_name = consumer_name;
	}
	public String getMax_callcount() {
		return max_callcount;
	}
	public void setMax_callcount(String max_callcount) {
		this.max_callcount = max_callcount;
	}
	public String getMax_callcount_ok() {
		return max_callcount_ok;
	}
	public void setMax_callcount_ok(String max_callcount_ok) {
		this.max_callcount_ok = max_callcount_ok;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getKeys() {
		return keys;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getClient_secret() {
		return client_secret;
	}
	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
	public String getProvision_key() {
		return provision_key;
	}
	public void setProvision_key(String provision_key) {
		this.provision_key = provision_key;
	}
	public String getAuth_type() {
		return auth_type;
	}
	public void setAuth_type(String auth_type) {
		this.auth_type = auth_type;
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
