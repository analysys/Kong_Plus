package com.analysys.kong.dao;

import java.util.List;

import com.analysys.kong.model.User;

public interface UserDao {
	
	List<User> getListByName(User user);

	List<User> getListByUser(User user);
	
	Integer getListCountByUser(User user);

	Integer insert(User user);

	Integer update(User user);
	
}
