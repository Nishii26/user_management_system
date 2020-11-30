package com.example.bootcamp.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bootcamp.entity.Users;
import com.example.bootcamp.repo.UsersRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    
	@Autowired
	UsersRepo userRepo;
		
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    
    @Override
    public UserDetails loadUserByUsername(String emailOrPhone) throws UsernameNotFoundException {
        Users user = userRepo.findByEmailOrPhone(emailOrPhone);
        if (null==user) {
        	log.info("email/phone number or the password is incorrect");
            throw new UsernameNotFoundException(emailOrPhone);
        }
        ArrayList<String> roles = new ArrayList<>();
        String userType = "ROLE_"+user.getUserType();
    	roles.add(userType);
    	List<GrantedAuthority> list = roles.stream()
    		        .map(SimpleGrantedAuthority::new)
    		        .collect(Collectors.toList());
        return new User(emailOrPhone,user.getPassword(),list);
    }

}
