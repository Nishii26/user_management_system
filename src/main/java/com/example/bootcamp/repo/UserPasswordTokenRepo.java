package com.example.bootcamp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bootcamp.entity.UserPasswordToken;

public interface UserPasswordTokenRepo extends JpaRepository<UserPasswordToken, Long>{
	
	Optional<UserPasswordToken> findByPasswordToken(String passwordLink);

}
