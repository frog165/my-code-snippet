package com.myssm.dao;

import com.myssm.model.User;

public interface UserDao {
	public User selectUserById(Integer userId); 
}
