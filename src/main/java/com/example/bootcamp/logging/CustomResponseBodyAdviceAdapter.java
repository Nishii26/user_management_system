package com.example.bootcamp.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.bootcamp.commons.ResponseBody;
import com.example.bootcamp.commons.ResponseStatus;
import com.google.gson.Gson;

@ControllerAdvice
public class CustomResponseBodyAdviceAdapter extends ResponseEntityExceptionHandler implements ResponseBodyAdvice<Object> {
    
    @Autowired
    LoggingService loggingService;
    
    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }
    
    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        if (serverHttpRequest instanceof ServletServerHttpRequest &&
                serverHttpResponse instanceof ServletServerHttpResponse) {
            loggingService.logResponse(
                    ((ServletServerHttpRequest) serverHttpRequest).getServletRequest(),
                    ((ServletServerHttpResponse) serverHttpResponse).getServletResponse(), o);
        }
        
        return o;
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, 
      HttpHeaders headers, 
      HttpStatus status, 
      WebRequest request) {
    	Gson gson = new Gson();
    	ResponseBody response = new ResponseBody();
    	response.setStatus(ResponseStatus.BAD_REQUEST);
    	response.setMessage(ex.getBindingResult().toString());
    	response.setData(null);
    	return new ResponseEntity<>(gson.toJson(response),HttpStatus.BAD_REQUEST);
    }
       
   
}
