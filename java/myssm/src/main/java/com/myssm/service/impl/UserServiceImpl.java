package com.myssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myssm.dao.UserDao;
//import com.myssm.dao.UserDaoImpl;
import com.myssm.model.User;
import com.myssm.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao dao;
	
	@Override
	public User selectUserById(Integer userId) {
		User user = dao.selectUserById(1);
		return user;
	}
	
}
