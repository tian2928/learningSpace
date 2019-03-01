package com.itheima.utils;

import java.util.UUID;

public class UUIDUtils {
	//得到一个随机字符串
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	
	public static void main(String[] args) {
		System.out.println(getUUID());
	}
}
