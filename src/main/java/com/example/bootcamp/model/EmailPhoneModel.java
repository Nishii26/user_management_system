package com.example.bootcamp.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EmailPhoneModel {
	
	@NotBlank(message="Email/Phone is mandatory")
	private String emailorPhone;

}
