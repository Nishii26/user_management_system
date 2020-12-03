package com.example.bootcamp.utils;

import com.example.bootcamp.commons.ResponseBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {
	
	private JsonUtils() {}
	
	public static final GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
	public static final Gson gson = gsonBuilder.create();

	
	public static String getResponseJson(int status ,String responseMessage ,Object data){

		ResponseBody response = new ResponseBody();
		response.setStatus(status);
		response.setMessage(responseMessage);
		response.setData(data);
		return gson.toJson(response);
	}

}
