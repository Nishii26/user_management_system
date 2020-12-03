package com.example.bootcamp.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CommonUtils {
	
	private CommonUtils() {}
	
	public static String hashPassword(String rawPassword) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.encode(rawPassword);
	}
	
	public static boolean matchPassword(String bCryptPassword, String rawPassword) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.matches(rawPassword, bCryptPassword);
	}

}
