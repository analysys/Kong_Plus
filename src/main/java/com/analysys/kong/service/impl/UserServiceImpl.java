package com.analysys.kong.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.analysys.kong.dao.UserDao;
import com.analysys.kong.model.User;
import com.analysys.kong.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;
	
	@Override
	public Integer getListCountByUser(User user) {
		return userDao.getListCountByUser(user);
	}
	
	@Override
	public List<User> getListByName(User user) {
		return userDao.getListByName(user);
	}

	/**
	 * 获取用户列表
	 * @param user
	 * @return
	 */
	@Override
	public List<User> getListByUser(User user) {
		return userDao.getListByUser(user);
	}

	/**
	 * 用户注册
	 * @param register
	 */
	@Override
	public void addUser(User user) {
		userDao.insert(user);
	}

	/**
	 * 更新用户信息
	 * @param user
	 */
	@Override
	public void updateUser(User user) {
		userDao.update(user);
	}
}
