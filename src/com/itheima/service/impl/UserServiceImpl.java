package com.itheima.service.impl;

import java.sql.SQLException;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.SendJMail;

public class UserServiceImpl implements UserService {

	@Override
	public void regist(User user) {
		UserDao ud = new UserDaoImpl();
		try {
			ud.addUser(user);
			String emailMsg = "<a href='http://localhost:8080/products60/userServlet?method=active&code="+user.getCode()+"'>马上激活</a>";
			SendJMail.sendMail(user.getEmail(), emailMsg );
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void activeCode(String code) {
		UserDao ud = new UserDaoImpl();
		try {
			ud.activeCode(code);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User login(User user) {
		UserDao ud = new UserDaoImpl();
		User existUser = null;
		try {
			existUser = ud.login(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return existUser;
	}

	@Override
	public boolean ckName(String username) {
		UserDao ud = new UserDaoImpl();
		boolean b = false;
		try {
			b = ud.ckName(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

}
