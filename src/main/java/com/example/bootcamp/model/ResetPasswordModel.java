package com.example.bootcamp.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.example.bootcamp.utils.Constant;

import lombok.Data;

@Data
public class ResetPasswordModel {

	@NotBlank(message="Email is mandatory")
	private String email;
	
	@Pattern(regexp =Constant.PASSWORD_REGEX, message="old password provided is invalid.")
	@NotBlank(message="Old Password is mandatory")
	private String oldPassword;
	
	@Pattern(regexp =Constant.PASSWORD_REGEX, message="confirm password provided is invalid.")
	@NotBlank(message="Confirm Password is mandatory")
	private String confirmPassword;
	
	@Pattern(regexp =Constant.PASSWORD_REGEX, message="new password provided is invalid.")
	@NotBlank(message="New Password is mandatory")
	private String newPassword;
}
