package com.itheima.domain;

import java.io.Serializable;
import java.util.Date;

/**
  `pid` VARCHAR(32) NOT NULL,		-- 商品编号
  `pname` VARCHAR(50) DEFAULT NULL,	-- 商品名称
  `market_price` DOUBLE DEFAULT NULL, -- 市场价
  `shop_price` DOUBLE DEFAULT NULL,   -- 商城价
  `pimage` VARCHAR(200) DEFAULT NULL, -- 商品图片
  `pdate` DATETIME DEFAULT NULL,	-- 上传时间
  `is_hot` INT(11) DEFAULT NULL,	-- 是否热门	
  `pdesc` VARCHAR(255) DEFAULT NULL, -- 商品描述
  `pflag` INT(11) DEFAULT NULL,		-- 是否下架 0代表未下架
  `cid` VARCHAR(32) DEFAULT NULL,	-- 商品类别编号
 *
 */
public class Product implements Serializable{
	private String pid;
	private String pname;
	private double market_price;
	private double shop_price;
	private String pimage;
	private Date pdate;
	private int is_hot;
	private String pdesc;
	private int pflag;
	private String cid;
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (pid == null) {
			if (other.pid != null)
				return false;
		} else if (!pid.equals(other.pid))
			return false;
		return true;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public double getMarket_price() {
		return market_price;
	}
	public void setMarket_price(double market_price) {
		this.market_price = market_price;
	}
	public double getShop_price() {
		return shop_price;
	}
	public void setShop_price(double shop_price) {
		this.shop_price = shop_price;
	}
	public String getPimage() {
		return pimage;
	}
	public void setPimage(String pimage) {
		this.pimage = pimage;
	}
	public Date getPdate() {
		return pdate;
	}
	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}
	public int getIs_hot() {
		return is_hot;
	}
	public void setIs_hot(int is_hot) {
		this.is_hot = is_hot;
	}
	public String getPdesc() {
		return pdesc;
	}
	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}
	public int getPflag() {
		return pflag;
	}
	public void setPflag(int pflag) {
		this.pflag = pflag;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	
}
