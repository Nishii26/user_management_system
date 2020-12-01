package com.example.bootcamp.controller;

import javax.servlet.http.HttpServletRequest;
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
import com.example.bootcamp.model.EmailPhoneModel;
import com.example.bootcamp.model.ResetPasswordModel;
import com.example.bootcamp.service.UserPasswordTokenService;
import com.example.bootcamp.utils.Constant;
import com.example.bootcamp.utils.JsonUtils;

@RestController
@RequestMapping("/user")

public class UserPasswordTokenController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserPasswordTokenController.class);

	@Autowired
	UserPasswordTokenService userPasswordTokenService;
	
	@RequestMapping(value="/forgot-password",produces="application/json",method = {RequestMethod.POST})
	public ResponseEntity<String> sendPasswordResetLink( @Valid @RequestBody EmailPhoneModel emailorPhoneModel){
		String response =null;
		 try {
			    response = userPasswordTokenService.sendPasswordResetLink(emailorPhoneModel.getEmailorPhone());
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
	
	@RequestMapping(value="/verify-password-link",produces="application/json",method = {RequestMethod.GET})
	public ResponseEntity<String> verifyPasswordResetLink(HttpServletRequest req){
		String response =null;
		 try {
			 String resetLink = req.getHeader(Constant.RESET_LINK_HEADER);
			 	if(null == resetLink)
			 		response = JsonUtils.getResponseJson(ResponseStatus.FAILED, 
	                         ResponseMessage.EMAIL_OR_RESET_LINK_INVALID, 
	                         null);
			 	else
			 		response = userPasswordTokenService.verifyPasswordResetLink(resetLink);
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
	
	@RequestMapping(value="/update-password",produces="application/json",method = {RequestMethod.POST})
	public ResponseEntity<String> updatePassword(@Valid @RequestBody ResetPasswordModel resetPasswordModel , HttpServletRequest req){
		String response =null;
		 try {
			 	String resetLink = req.getHeader(Constant.RESET_LINK_HEADER);
			 	if(null == resetLink)
			 		response = JsonUtils.getResponseJson(ResponseStatus.FAILED, 
	                         ResponseMessage.EMAIL_OR_RESET_LINK_INVALID, 
	                         null);
			 	else
			 		response = userPasswordTokenService.updatePassword(resetPasswordModel,resetLink);
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
