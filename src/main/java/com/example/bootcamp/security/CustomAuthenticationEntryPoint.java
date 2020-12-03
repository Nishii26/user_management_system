package com.example.bootcamp.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.example.bootcamp.commons.ResponseMessage;
import com.example.bootcamp.commons.ResponseStatus;
import com.example.bootcamp.utils.JsonUtils;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse res,
			AuthenticationException authException) throws IOException, ServletException {
	      	res.setStatus(403);
			res.getWriter().write(JsonUtils.getResponseJson(ResponseStatus.ACCESS_DENIED, ResponseMessage.ACCESS_DENIED, null));
	}

}
