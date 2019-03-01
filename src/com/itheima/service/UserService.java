package com.itheima.service;

import com.itheima.domain.User;

public interface UserService {

	/**
	 * 用户注册
	 * @param user
	 */
	void regist(User user);

	/**
	 * 用户激活
	 * @param code
	 */
	void activeCode(String code);

	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	User login(User user);

	boolean ckName(String username);

}
