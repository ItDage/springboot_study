package com.itdage.util;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	
	
	public static String getYMDHMSByDate(Date date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(date);
	}
	
//	public static void main(String[] args) {
//		System.out.println(getYMDHMSByDate(new Date()));
//	}
}
