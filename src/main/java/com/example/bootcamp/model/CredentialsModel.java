package com.example.bootcamp.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.example.bootcamp.utils.Constant;

import lombok.Data;

@Data
public class CredentialsModel {
	
	@NotBlank(message="Email or Phone is mandatory")
	private String emailorPhone;
	
	@Pattern(regexp =Constant.PASSWORD_REGEX, message="password provided is invalid.")
	@NotBlank(message="Password is mandatory")
	private String password;

}
