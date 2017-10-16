package com.analysys.kong.service;

import java.util.List;
import java.util.Map;

import com.analysys.kong.model.ApiConsumers;
import com.analysys.kong.model.ApiLogs;
import com.analysys.kong.model.ApioAuth;
import com.analysys.kong.model.Apis;

public interface ApisService {
	
	public Integer getListCountByParam(Apis apis);
	
	public List<Apis> getListByApis(Apis apis);
	
	public Integer getListCountByApis(Apis apis);

	public String addApi(Apis apis, ApioAuth apioAuth);

	public String updateApi(Apis apis);
	
	public void updateApiLog(Apis apis);
	
	public void deleteApi(Apis apis);
	
	public List<ApiConsumers> getConsumersByApis(ApiConsumers capi);
	
	public Integer insertConsumers(ApiConsumers capi);
	
	public Integer updateConsumers(ApiConsumers capi);

	public Integer deleteConsumers(ApiConsumers capi);
	
	public Integer addLogs(ApiLogs apiLogs);
	
	public List<ApiLogs> getLogListByApis(ApiLogs apiLogs);
	
	public Integer getLogListCountByApis(ApiLogs apiLogs);
	
	public List<ApioAuth> getApioAuthList(ApioAuth apioAuth);
	
	public Integer addOauth(ApioAuth apioAuth);
	
	@SuppressWarnings("rawtypes")
	public List<Map> getStatusCallCount(ApiLogs apiLogs);
	
	@SuppressWarnings("rawtypes")
	public List<Map> reportConsumerCallCount(ApiLogs apiLogs);
}
