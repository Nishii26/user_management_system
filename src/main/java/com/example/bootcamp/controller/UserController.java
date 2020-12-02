package com.example.bootcamp.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.bootcamp.commons.ResponseMessage;
import com.example.bootcamp.commons.ResponseStatus;
import com.example.bootcamp.model.CredentialsModel;
import com.example.bootcamp.model.RegistrationModel;
import com.example.bootcamp.service.UserService;
import com.example.bootcamp.utils.JsonUtils;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value="/login",produces="application/json",method = {RequestMethod.POST})
	public void loginUser(@RequestBody @Valid CredentialsModel userCredentials) {		
	}
	
	@RequestMapping(value="/user/register",produces="application/json",method = {RequestMethod.POST})
	public ResponseEntity<String> registerUser( @Valid @RequestBody RegistrationModel newUserDetails,HttpServletResponse res){
		String response =null;
		 try {
			    response = userService.registerUser(newUserDetails,res);
			    return new ResponseEntity<String>(response,HttpStatus.OK);
		 	} catch (DataIntegrityViolationException e) {
		    	logger.error(e.toString());
				return new ResponseEntity<String>(JsonUtils.getResponseJson(ResponseStatus.DATABASE_EXCEPTION, 
																			ResponseMessage.DATA_VALIDATION_ERROR, 
																			null),HttpStatus.OK);
		     } catch (Exception e) {
		    	logger.error(e.toString());
				return new ResponseEntity<String>(JsonUtils.getResponseJson(ResponseStatus.INTERNAL_ERROR, 
																			ResponseMessage.INTERNAL_ERROR, 
																			null),HttpStatus.OK);
		     }		
	}
	

}
