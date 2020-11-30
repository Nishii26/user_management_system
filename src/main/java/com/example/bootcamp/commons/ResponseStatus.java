package com.example.bootcamp.commons;

import lombok.Data;

@Data
public class ResponseStatus {
	
	public static final int OK = 200;
	public static final int SUCCESS = 101;
	public static final int FAILED = 102;
	public static final int INTERNAL_ERROR = 500;
	public static final int ACCESS_DENIED = 403;
	public static final int CREATED = 201;
	public static final int BAD_REQUEST =400;
	public static final int NO_DATA_FOUND =404;
	public static final int DATABASE_EXCEPTION =402;
	public static final int EMAIL_ALREADY_EXISTS =103;
	public static final int PHONE_ALREADY_EXISTS =104;
	
}