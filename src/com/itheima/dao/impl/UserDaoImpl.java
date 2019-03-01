package com.itheima.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.C3P0Utils;

public class UserDaoImpl implements UserDao {

	@Override
	public void addUser(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "INSERT INTO USER VALUES(?,?,?,?,?,?,?,?,0)";
		qr.update(sql,user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getBirthday(),user.getSex(),user.getCode());
		
	}

	@Override
	public void activeCode(String code) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		qr.update("UPDATE USER SET state=1 WHERE CODE=?",code);
	}

	@Override
	public User login(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("SELECT * FROM USER WHERE username=? AND PASSWORD=?", new BeanHandler<User>(User.class),user.getUsername(),user.getPassword());
	}

	@Override
	public boolean ckName(String username) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		long count = (long) qr.query("select count(*) from user where username=?", new ScalarHandler(),username);
		if(count>0){
			return true;
		}
		return false;
	}

}
