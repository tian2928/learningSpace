package com.itheima.domain;
/**
 *`itemid` VARCHAR(50) NOT NULL, -- 定单项id
  `count` INT(11) DEFAULT NULL,  -- 购买的商品数量
  `pid` VARCHAR(50) DEFAULT NULL, -- 商品编号
  `oid` VARCHAR(50) DEFAULT NULL, -- 定单编号
 *
 */
public class OrderItem {
	private String itemid;
	private String oid;
	private Product p;
	private int count;
	
	
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Product getP() {
		return p;
	}
	public void setP(Product p) {
		this.p = p;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
	
}
