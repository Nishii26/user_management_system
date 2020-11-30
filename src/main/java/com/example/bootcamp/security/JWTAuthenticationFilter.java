package com.example.bootcamp.security;

import static com.example.bootcamp.security.SecurityConstants.EXPIRATION_TIME;
import static com.example.bootcamp.security.SecurityConstants.HEADER_STRING;
import static com.example.bootcamp.security.SecurityConstants.SECRET;
import static com.example.bootcamp.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.bootcamp.commons.ResponseMessage;
import com.example.bootcamp.commons.ResponseStatus;
import com.example.bootcamp.entity.Users;
import com.example.bootcamp.model.CredentialsModel;
import com.example.bootcamp.repo.UsersRepo;
import com.example.bootcamp.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private static final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
	
	private UsersRepo usersRepo;

	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
								   UsersRepo usersRepo) {
        this.authenticationManager = authenticationManager;
        this.usersRepo = usersRepo;
    }
	
	@Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            CredentialsModel creds = new ObjectMapper()
                    .readValue(req.getInputStream(), CredentialsModel.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmailorPhone(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	@Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
		
        String emailOrPhone = ((User) auth.getPrincipal()).getUsername();
        Users user = usersRepo.findByEmailOrPhone(emailOrPhone);
        if(null==user) {
        	log.error("email/phone number or the password is incorrect");
        	throw new IOException("email/phone number or the password is incorrect");
        }
        String token = Jwts.builder()
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .claim("role",auth.getAuthorities().stream().findFirst().get().toString())
                .claim("userId",user.getUserId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        
        Gson gson = new Gson();
        user.setPassword(null);
        String json = gson.toJson(user);
        log.info("UserLogin: "+json);
		res.getWriter().write(JsonUtils.getResponseJson(ResponseStatus.SUCCESS, ResponseMessage.SUCCESS, user));
	}
}
