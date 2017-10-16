package com.analysys.kong.model;

public class Apis extends BaseBean {
	private static final long serialVersionUID = 5801963365530305418L;
	private String id;
	private String aname;
	private String uid;
	private String uname;
	private String visit_url;
	private String req_host;
	private String req_path;
	private String auth_type;
	private String remarks;
	private String createtime;
	private String whitelist;
	private String ip_whitelist;
	private String log_file;
	private String consumer_cnt;
	private String log_cnt;
	private String call_total;
	private String call_ok;
	private String extend;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getVisit_url() {
		return visit_url;
	}
	public void setVisit_url(String visit_url) {
		this.visit_url = visit_url;
	}
	public String getReq_host() {
		return req_host;
	}
	public void setReq_host(String req_host) {
		this.req_host = req_host;
	}
	public String getReq_path() {
		return req_path;
	}
	public void setReq_path(String req_path) {
		this.req_path = req_path;
	}
	public String getAuth_type() {
		return auth_type;
	}
	public void setAuth_type(String auth_type) {
		this.auth_type = auth_type;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getWhitelist() {
		return whitelist;
	}
	public void setWhitelist(String whitelist) {
		this.whitelist = whitelist;
	}
	public String getIp_whitelist() {
		return ip_whitelist;
	}
	public void setIp_whitelist(String ip_whitelist) {
		this.ip_whitelist = ip_whitelist;
	}
	public String getLog_file() {
		return log_file;
	}
	public void setLog_file(String log_file) {
		this.log_file = log_file;
	}
	public String getConsumer_cnt() {
		return consumer_cnt;
	}
	public void setConsumer_cnt(String consumer_cnt) {
		this.consumer_cnt = consumer_cnt;
	}
	public String getLog_cnt() {
		return log_cnt;
	}
	public void setLog_cnt(String log_cnt) {
		this.log_cnt = log_cnt;
	}
	public String getCall_total() {
		return call_total;
	}
	public void setCall_total(String call_total) {
		this.call_total = call_total;
	}
	public String getCall_ok() {
		return call_ok;
	}
	public void setCall_ok(String call_ok) {
		this.call_ok = call_ok;
	}
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
}
