package com.analysys.kong.service;

import java.util.List;

import com.analysys.kong.model.User;

public interface UserService {
	
	/**
	 * 获取用户列表
	 * 
	 * @param user
	 * @return
	 */
	public List<User> getListByUser(User user);
	public List<User> getListByName(User user);
	
	/**
	 * 获取用户数量
	 * @param user
	 * @return
	 */
	public Integer getListCountByUser(User user);

	/**
	 * 用户注册
	 * 
	 * @param user
	 */
	public void addUser(User user);

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 */
	public void updateUser(User user);
}
