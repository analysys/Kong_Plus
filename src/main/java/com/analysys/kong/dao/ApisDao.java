package com.analysys.kong.dao;

import java.util.List;
import java.util.Map;

import com.analysys.kong.model.ApiConsumers;
import com.analysys.kong.model.ApiLogs;
import com.analysys.kong.model.ApioAuth;
import com.analysys.kong.model.Apis;

public interface ApisDao {
	
	Integer getListCountByParam(Apis api);
	
	List<Apis> getListByApis(Apis api);
	
	Integer getListCountByApis(Apis api);

	Integer insert(Apis api);

	Integer update(Apis api);
	
	Integer delete(Apis api);
	
	List<ApiConsumers> getConsumersByApis(ApiConsumers capi);
	
	List<ApiConsumers> getAllConsumersForoAuth(ApiConsumers capi);
	
	String getProvisionKeyForoAuth(ApiConsumers capi);
	
	Integer insertConsumers(ApiConsumers capi);
	
	Integer updateConsumers(ApiConsumers capi);

	Integer deleteConsumers(ApiConsumers capi);
	
	Integer addLogs(ApiLogs apiLogs);
	
	List<ApiLogs> getLogListByApis(ApiLogs apiLogs);
	
	Integer getLogListCountByApis(ApiLogs apiLogs);
	
	List<ApioAuth> getApioAuthList(ApioAuth apioAuth);
	
	Integer addOauth(ApioAuth apioAuth);
	
	@SuppressWarnings("rawtypes")
	List<Map> getConsumerCallCount();
	
	@SuppressWarnings("rawtypes")
	List<Map> reportConsumerCallCount(ApiLogs apiLogs);
	
	@SuppressWarnings("rawtypes")
	List<Map> getStatusCallCount(ApiLogs apiLogs);
}
