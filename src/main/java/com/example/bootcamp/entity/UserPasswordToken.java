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
@Table(name="user_password_link")
@SequenceGenerator(name="user_password_link_id_seq", initialValue=1, allocationSize=1)
public class UserPasswordToken {
	
	@Id
	@javax.persistence.GeneratedValue(strategy = GenerationType.SEQUENCE,generator="user_password_link_id_seq")
	@Column(name="id")
	private Long id;
	@Column(name="user_id")
	private Long userId;
	@Column(name="is_active")
	private Boolean isActive;
	@Column(name="password_link")
	private String passwordToken;
	@Column(name="creation")
	private Long creation;
	

}
