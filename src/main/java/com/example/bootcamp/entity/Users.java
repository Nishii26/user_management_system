package com.example.bootcamp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="users")
@SequenceGenerator(name="user_id_seq", initialValue=1, allocationSize=1)
public class Users {
	
	@Id
	@javax.persistence.GeneratedValue(strategy = GenerationType.SEQUENCE,generator="user_id_seq")
	@Column(name="id")
	private Long userId;
	@Column(name="email")
	private String email;
	@Column(name="password")
	private String password;
	@Column(name="phone")
	private String phone;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="gender")
	private String gender;
	@Column(name="address")
	private String address;
	@Column(name="state")
	private String state;
	@Column(name="country")
	private String country;
	@Column(name="user_type")
	private String userType;
	@Column(name="creation")
	private Long creationInMs;
	@Column(name="updation")
	private Long updationInMs;

}
