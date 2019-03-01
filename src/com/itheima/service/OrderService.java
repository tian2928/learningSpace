package com.itheima.service;

import com.itheima.domain.Order;
import com.itheima.domain.PageBean;

public interface OrderService {

	void createOrder(Order order);

	PageBean<Order> findByPage(int currentPage, int pageSize, String uid);

	Order findByOid(String oid);

	void updateOrder(Order order);

	void updateState(String oid);

}
