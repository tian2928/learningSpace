package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.Category;

public interface CategoryDao {

	/**
	 * 查询所有分类信息
	 * @return
	 * @throws SQLException 
	 */
	List<Category> findAll() throws SQLException;

	void addCategory(Category c) throws SQLException;

	void delCategory(String cid) throws SQLException;

	Category findByCid(String cid) throws SQLException;

	void update(Category c) throws SQLException;

}
