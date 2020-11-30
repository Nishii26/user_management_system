package com.example.bootcamp.security;

import static com.example.bootcamp.security.SecurityConstants.EXPIRATION_TIME;
import static com.example.bootcamp.security.SecurityConstants.SECRET;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Configuration
public class GenerateJWT {
	private static final Logger log = LoggerFactory.getLogger(GenerateJWT.class);
	
	public static String generateJWTToken(String email,Long userId,String role) {
		String token = Jwts.builder()
                .setSubject(email)
                .claim("role",role)
                .claim("userId",userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
		return token;
	}

}
