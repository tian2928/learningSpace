package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.Order;

public interface OrderDao {

	void insertOrder(Order order) throws SQLException;

	void insertOrderItems(Order order) throws SQLException;

	int count(String uid) throws SQLException;

	List<Order> findByPage(int currentPage, int pageSize, String uid) throws Exception;

	Order findByOid(String oid) throws Exception;

	void updateOrder(Order order) throws SQLException;

	void updateState(String oid) throws SQLException;

}
