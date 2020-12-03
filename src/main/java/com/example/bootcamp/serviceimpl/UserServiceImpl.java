package com.example.bootcamp.serviceimpl;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bootcamp.commons.ResponseMessage;
import com.example.bootcamp.commons.ResponseStatus;
import com.example.bootcamp.entity.Users;
import com.example.bootcamp.model.RegistrationModel;
import com.example.bootcamp.repo.UsersRepo;
import com.example.bootcamp.security.GenerateJWT;
import com.example.bootcamp.security.SecurityConstants;
import com.example.bootcamp.service.UserService;
import com.example.bootcamp.utils.CommonUtils;
import com.example.bootcamp.utils.JsonUtils;
import com.example.bootcamp.utils.Timings;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UsersRepo usersRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public String registerUser(RegistrationModel newUserDetails,HttpServletResponse res){
		
		Optional<Users> userByEmail = usersRepo.findByEmail(newUserDetails.getEmail());
		if(!userByEmail.isPresent()) {
		
			if(null!=newUserDetails.getPhone()) {
				Optional<Users> userByPhone = usersRepo.findByPhone(newUserDetails.getPhone());
				if(!userByPhone.isPresent()) {
					Users user = this.saveUser(newUserDetails);
					if(null != user) {
						String token = GenerateJWT.generateJWTToken(user.getEmail(),user.getUserId(),user.getUserType());
						res.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX+ token);
						return JsonUtils.getResponseJson(ResponseStatus.CREATED, 
								 ResponseMessage.REGISTERATION_SUCCESS, 
								 user);
					}else {
						return JsonUtils.getResponseJson(ResponseStatus.INTERNAL_ERROR, 
								 ResponseMessage.REGISTERATION_FAILURE, 
								 null);
					}
					
				}else {
					return JsonUtils.getResponseJson(ResponseStatus.PHONE_ALREADY_EXISTS, 
							 ResponseMessage.PHONE_ALREADY_EXISTS, 
							 null);
				}
			}else {
				Users user = this.saveUser(newUserDetails);
				if(null != user) {
					String token = GenerateJWT.generateJWTToken(user.getEmail(),user.getUserId(),user.getUserType());
					res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX+ token);
					return JsonUtils.getResponseJson(ResponseStatus.CREATED, 
							 ResponseMessage.REGISTERATION_SUCCESS, 
							 user);
				}else {
					return JsonUtils.getResponseJson(ResponseStatus.INTERNAL_ERROR, 
							 ResponseMessage.REGISTERATION_FAILURE, 
							 null);
				}
			}
			
		}else {
			return JsonUtils.getResponseJson(ResponseStatus.EMAIL_ALREADY_EXISTS, 
											 ResponseMessage.EMAIL_ALREADY_EXISTS, 
											 null);
		}
		
	}
	
	public Users saveUser (RegistrationModel newUserDetails) {
		Users user = new Users();
		user.setFirstName(newUserDetails.getFirstName());
		user.setLastName(newUserDetails.getLastName());
		user.setEmail(newUserDetails.getEmail());
		if(null != newUserDetails.getPhone())
			user.setPhone(newUserDetails.getPhone());
		user.setUserType(newUserDetails.getUserType());
		user.setPassword(CommonUtils.hashPassword(newUserDetails.getPassword()));
		if(null != newUserDetails.getAddress())
			user.setAddress(newUserDetails.getAddress());
		if(null != newUserDetails.getState())
			user.setState(newUserDetails.getState());
		if(null != newUserDetails.getCountry())
			user.setCountry(newUserDetails.getCountry());
		user.setUpdationInMs(Timings.currentTime());
		user.setCreationInMs(Timings.currentTime());
		user = usersRepo.save(user);
		return user;
		
	}

}
