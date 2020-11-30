package com.example.bootcamp.service;

import javax.servlet.http.HttpServletResponse;

import com.example.bootcamp.model.RegistrationModel;

public interface UserService {

	String registerUser(RegistrationModel newUserDetails,HttpServletResponse res);

}
