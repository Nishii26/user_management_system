package com.example.bootcamp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bootcamp.entity.Users;

public interface UsersRepo extends JpaRepository<Users, Long>{

	@Query(value="SELECT * FROM users WHERE email =:emailOrPhone OR phone =:emailOrPhone",nativeQuery=true)
	Users findByEmailOrPhone(@Param("emailOrPhone") String emailOrPhone);
	
	Optional<Users> findByEmail(String email);
	Optional<Users> findByPhone(String phone);
}
