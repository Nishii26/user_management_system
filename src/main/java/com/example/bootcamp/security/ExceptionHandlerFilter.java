package com.example.bootcamp.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.example.bootcamp.commons.ResponseMessage;
import com.example.bootcamp.commons.ResponseStatus;
import com.example.bootcamp.utils.JsonUtils;
import com.sun.tools.sjavac.Log;

public class ExceptionHandlerFilter extends OncePerRequestFilter{

	@Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
        	Log.error(e.getMessage());
            response.getWriter().write(JsonUtils.getResponseJson(ResponseStatus.INTERNAL_ERROR, ResponseMessage.INTERNAL_ERROR, null));
        }
    }
}
