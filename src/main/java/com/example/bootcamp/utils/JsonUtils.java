package com.example.bootcamp.utils;

import com.example.bootcamp.commons.ResponseBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {
	
	public static GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
	public static Gson gson = gsonBuilder.create();
	
	/*
	 * public static String getResponseJson(int status ,String responseMessage
	 * ,Object data) throws JSONException {
	 * 
	 * JSONObject obj = new JSONObject(); obj.put("status", status);
	 * obj.put("message", responseMessage); if (data == null) obj.put("data",
	 * JSONObject.NULL); else obj.put("data", new JSONObject(gson.toJson(data)));
	 * return obj.toString(); }
	 */
	
	public static String getResponseJson(int status ,String responseMessage ,Object data){

		ResponseBody response = new ResponseBody();
		response.setStatus(status);
		response.setMessage(responseMessage);
		response.setData(data);
		return gson.toJson(response).toString();
	}

}
