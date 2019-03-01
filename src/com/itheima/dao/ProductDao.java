package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.Product;

public interface ProductDao {

	List<Product> findHotProducts() throws SQLException;

	List<Product> findNewProducts() throws SQLException;

	int count(String cid) throws SQLException;

	List<Product> findByPage(int currentPage, int pageSize, String cid) throws SQLException;

	Product findByPid(String pid) throws SQLException;

	void addProduct(Product p) throws SQLException;

}
