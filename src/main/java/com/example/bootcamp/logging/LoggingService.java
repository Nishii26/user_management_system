package com.example.bootcamp.logging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingService {

	private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);
	
	void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Object body) {
		logger.info(" Method: "+ httpServletRequest.getMethod()+" URL: "+ httpServletRequest.getRequestURL());
		logger.info("Parameters"+httpServletRequest);
		logger.info("Reponse:"+body);
	}
}
