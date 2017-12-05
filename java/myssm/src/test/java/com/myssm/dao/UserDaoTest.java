package com.myssm.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.myssm.model.User;

public class UserDaoTest extends AbstractDaoTest {
	@Autowired
	private UserDao userDao;

	@Test
	public void testQueryById() throws Exception {
		int userId = 1;
		User user = userDao.selectUserById(userId);
		System.out.println(user);
	}

}
