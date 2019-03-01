package com.itheima.domain;

import java.util.Date;
import java.util.List;

/**
  `oid` VARCHAR(50) NOT NULL, -- 定单号
  `ordertime` DATETIME DEFAULT NULL, -- 下单时间
  `total` DOUBLE DEFAULT NULL,  -- 总金额
  `state` INT(11) DEFAULT NULL, -- 支付状态  0：未支付 1：已支付 2：已发货 3：已收货
  `address` VARCHAR(30) DEFAULT NULL,  -- 收货人地址
  `name` VARCHAR(20) DEFAULT NULL,    -- 收货人姓名
  `telephone` VARCHAR(20) DEFAULT NULL, -- 电话
  `uid` VARCHAR(50) DEFAULT NULL,  -- 用户id（登录用户的id）
 *
 */
public class Order {
	private String oid;
	private Date ordertime;
	private double total;
	private int state;
	private String address;
	private String name;
	private String telephone;
	private User user;
	
	private List<OrderItem> orderItems;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	
}
