package com.itheima.dao;

import java.sql.SQLException;

import com.itheima.domain.User;

public interface UserDao {

	/**
	 * 用户注册
	 * @param user
	 * @throws SQLException 
	 */
	void addUser(User user) throws SQLException;

	/**
	 * 用户激活
	 * @param code
	 * @throws SQLException 
	 */
	void activeCode(String code) throws SQLException;

	/**
	 * 用户登录
	 * @param user
	 * @return
	 * @throws SQLException 
	 */
	User login(User user) throws SQLException;

	boolean ckName(String username) throws SQLException;

}
