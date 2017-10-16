package com.analysys.kong.model;

public class ApiLogs extends BaseBean{
	private static final long serialVersionUID = -3326495677892955043L;
	private String started_at;
	private String response_size;
	private String response_status;
	private String request_method;
	private String request_uri;
	private String request_size;
	private String request_real_uri;
	private String x_consumer_id;
	private String x_forwarded_for;
	private String x_real_ip;
	private String x_consumer_username;
	private String client_ip;
	private String api_id;
	private String api_name;
	private String latencies_kong;
	private String latencies_proxy;   //请求转发时间
	private String latencies_request; //请求处理时间
	
	public String getStarted_at() {
		return started_at;
	}
	public void setStarted_at(String started_at) {
		this.started_at = started_at;
	}
	public String getResponse_size() {
		return response_size;
	}
	public void setResponse_size(String response_size) {
		this.response_size = response_size;
	}
	public String getResponse_status() {
		return response_status;
	}
	public void setResponse_status(String response_status) {
		this.response_status = response_status;
	}
	public String getRequest_method() {
		return request_method;
	}
	public void setRequest_method(String request_method) {
		this.request_method = request_method;
	}
	public String getRequest_uri() {
		return request_uri;
	}
	public void setRequest_uri(String request_uri) {
		this.request_uri = request_uri;
	}
	public String getRequest_size() {
		return request_size;
	}
	public void setRequest_size(String request_size) {
		this.request_size = request_size;
	}
	public String getRequest_real_uri() {
		return request_real_uri;
	}
	public void setRequest_real_uri(String request_real_uri) {
		this.request_real_uri = request_real_uri;
	}
	public String getX_consumer_id() {
		return x_consumer_id;
	}
	public void setX_consumer_id(String x_consumer_id) {
		this.x_consumer_id = x_consumer_id;
	}
	public String getX_forwarded_for() {
		return x_forwarded_for;
	}
	public void setX_forwarded_for(String x_forwarded_for) {
		this.x_forwarded_for = x_forwarded_for;
	}
	public String getX_real_ip() {
		return x_real_ip;
	}
	public void setX_real_ip(String x_real_ip) {
		this.x_real_ip = x_real_ip;
	}
	public String getX_consumer_username() {
		return x_consumer_username;
	}
	public void setX_consumer_username(String x_consumer_username) {
		this.x_consumer_username = x_consumer_username;
	}
	public String getClient_ip() {
		return client_ip;
	}
	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}
	public String getApi_id() {
		return api_id;
	}
	public void setApi_id(String api_id) {
		this.api_id = api_id;
	}
	public String getApi_name() {
		return api_name;
	}
	public void setApi_name(String api_name) {
		this.api_name = api_name;
	}
	public String getLatencies_kong() {
		return latencies_kong;
	}
	public void setLatencies_kong(String latencies_kong) {
		this.latencies_kong = latencies_kong;
	}
	public String getLatencies_proxy() {
		return latencies_proxy;
	}
	public void setLatencies_proxy(String latencies_proxy) {
		this.latencies_proxy = latencies_proxy;
	}
	public String getLatencies_request() {
		return latencies_request;
	}
	public void setLatencies_request(String latencies_request) {
		this.latencies_request = latencies_request;
	}
}
