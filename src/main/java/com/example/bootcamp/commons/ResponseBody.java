package com.example.bootcamp.commons;

import lombok.Data;

@Data
public class ResponseBody {
	
	private int status;
	private String message;
	private Object data;
}
