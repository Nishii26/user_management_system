package com.example.bootcamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.example.bootcamp.controller.UserController;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})

public class TestUserController {
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserController userController;

	@Test
	public void loginUser() throws Exception {
		
	String exampleUserJson = "{ \"emailorPhone\": \"9599944411\", \"password\": \"Qwerty@123\"}";
	       mvc.perform(post("/login")
	               .content(exampleUserJson)
	               .contentType(MediaType.APPLICATION_JSON))
	               .andExpect(status().isOk());
	   }
	
	@Test
	public void registerUser() throws Exception {
		
		String registerJson = "{\"email\": \"shubhamgupta@gmail.com\",\"firstName\": \"shubham\",\"gender\": \"Male\",\"lastName\": \"gupta\",\"password\": \"Qwerty@123\",\"phone\": \"9654259677\",\"userType\": \"USER\"}";
	       mvc.perform(post("/user/register")
	               .content(registerJson)
	               .contentType(MediaType.APPLICATION_JSON))
	               .andExpect(status().isOk());
	   }

}
