package com.analysys.kong.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.analysys.kong.model.ApiLogs;

public class JsonUtil {
	
	/**
	 * 解析JSON
	 * @param json
	 * @return
	 */
	public static ApiLogs parseRequest(String json) {
		ApiLogs apiLogs = new ApiLogs();
    	JSONObject fullJsonObj = (JSONObject) JSON.parse(json);
    	apiLogs.setStarted_at(fullJsonObj.getString("started_at"));
    	apiLogs.setClient_ip(fullJsonObj.getString("client_ip"));
    	
    	String responseJson = fullJsonObj.getString("response");
    	JSONObject responseJsonObj = (JSONObject) JSON.parse(responseJson);
		apiLogs.setResponse_size(responseJsonObj.getString("size"));
		apiLogs.setResponse_status(responseJsonObj.getString("status"));
    	
		String requestJson = fullJsonObj.getString("request");
		JSONObject requestJsonObj = (JSONObject) JSON.parse(requestJson);
		apiLogs.setRequest_method(requestJsonObj.getString("method"));
		apiLogs.setRequest_uri(requestJsonObj.getString("uri"));
		apiLogs.setRequest_size(requestJsonObj.getString("size"));
		apiLogs.setRequest_real_uri(requestJsonObj.getString("request_uri"));
		
		String requestHeadJson = requestJsonObj.getString("headers");
		JSONObject requestHeadJsonObj = (JSONObject) JSON.parse(requestHeadJson);
		apiLogs.setX_consumer_id(requestHeadJsonObj.getString("x-consumer-id"));
		apiLogs.setX_forwarded_for(requestHeadJsonObj.getString("x-forwarded-for"));
		apiLogs.setX_consumer_username(requestHeadJsonObj.getString("x-consumer-username"));
		apiLogs.setX_real_ip(requestHeadJsonObj.getString("x-real-ip"));
		
		String apiJson = fullJsonObj.getString("api");
		JSONObject apiJsonObj = (JSONObject) JSON.parse(apiJson);
		apiLogs.setApi_id(apiJsonObj.getString("id"));
		apiLogs.setApi_name(apiJsonObj.getString("name"));
		
		String latenciesJson = fullJsonObj.getString("latencies");
		JSONObject latenciesJsonObj = (JSONObject) JSON.parse(latenciesJson);
		apiLogs.setLatencies_kong(latenciesJsonObj.getString("kong"));
		apiLogs.setLatencies_proxy(latenciesJsonObj.getString("proxy"));
		apiLogs.setLatencies_request(latenciesJsonObj.getString("request"));
		
		return apiLogs;
	}
}
