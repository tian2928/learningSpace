package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.utils.C3P0Utils;

public class CateogryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findAll() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("SELECT * FROM category", new BeanListHandler<Category>(Category.class));
	}

	@Override
	public void addCategory(Category c) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		qr.update("INSERT INTO category VALUES(?,?)",c.getCid(),c.getCname());
	}

	@Override
	public void delCategory(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		qr.update("delete from category where cid=?",cid);
	}

	@Override
	public Category findByCid(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("select * from category where cid=?", new BeanHandler<Category>(Category.class),cid);
	}

	@Override
	public void update(Category c) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		qr.update("UPDATE category SET cname=? WHERE cid=?",c.getCname(),c.getCid());
	}

}
