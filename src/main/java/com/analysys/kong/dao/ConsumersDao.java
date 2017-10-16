package com.analysys.kong.dao;

import java.util.List;

import com.analysys.kong.model.ApiConsumers;
import com.analysys.kong.model.Consumers;

public interface ConsumersDao {
	
	Integer getListCountByParam(Consumers consumer);
	
	List<Consumers> getListByConsumers(Consumers consumer);
	
	List<Consumers> getApiConsumersList(ApiConsumers aconsumer);
	
	Integer getListCountByConsumers(Consumers consumer);

	Integer insert(Consumers consumer);

	Integer update(Consumers consumer);
	
	Integer delete(Consumers consumer);
}
