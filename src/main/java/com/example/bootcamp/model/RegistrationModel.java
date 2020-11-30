package com.example.bootcamp.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.example.bootcamp.utils.Constant;

import lombok.Data;

@Data
public class RegistrationModel {

	@Pattern(regexp = Constant.ALPHABHETS_REGEX,message="firstname provided is invalid.")
	@NotBlank(message="FirstName is mandatory")
	private String firstName;
	
	@Pattern(regexp = Constant.ALPHABHETS_REGEX,message="lastname provided is invalid.")
	@NotBlank(message="LastName is mandatory")
	private String lastName;
	
	@Pattern(regexp=Constant.EMAIL_REGEX,message="email provided is invalid.")
	@NotBlank(message="Email is mandatory")
	private String email;
	
	@Pattern(regexp = Constant.ALPHABHETS_REGEX,message="gender provided is invalid.")
	private String gender;
	
	private String address;
	
	@Pattern(regexp = Constant.ALPHABHETS_SPACE_REGEX,message="state provided is invalid.")
	private String state;
	
	@Pattern(regexp = Constant.ALPHABHETS_SPACE_REGEX,message="country provided is invalid.")
	private String country;
	
	@Pattern(regexp = Constant.ALPHABHETS_REGEX,message="usertype provided is invalid.")
	@NotBlank(message="User type is mandatory")
	private String userType;
	
	@Pattern(regexp=Constant.INDIA_PHONE_REGEX,message="phone number provided is invalid.")
	private String phone;
	
	@Pattern(regexp =Constant.PASSWORD_REGEX, message="password provided is invalid.")
	@NotBlank(message="Password is mandatory")
	private String password;
}
