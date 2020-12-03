package com.example.bootcamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.bootcamp.commons.ResponseStatus;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestUserController {

  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
        //.apply(documentationConfiguration(restDocumentation))
        .build();
  }

	@Test
	public void loginUserWithCorrectCredentials() throws Exception {
		
	String exampleUserJson = "{ \"emailorPhone\": \"9599944411\", \"password\": \"Qwerty@123\"}";
      MvcResult result = this.mockMvc.perform(post("/login")
	               .content(exampleUserJson)
	               .contentType(MediaType.APPLICATION_JSON)).andReturn();
      int status = result.getResponse().getStatus();
      assertEquals(HttpStatus.OK.value(), status, "Incorrect Response Status");
    }
	
	@Test
	public void loginUserWithInCorrectCredentials() throws Exception {
		
	String exampleUserJson = "{ \"emailorPhone\": \"9599944411\", \"password\": \"Qwerty@345\"}";
      MvcResult result = this.mockMvc.perform(post("/login")
	               .content(exampleUserJson)
	               .contentType(MediaType.APPLICATION_JSON)).andReturn();
      int status = result.getResponse().getStatus();
      System.out.println("******** status - " + status);
      assertEquals(HttpStatus.FORBIDDEN.value(), status, "Incorrect Response Status");
    }
	
	@Test
	public void registerUserWithExistingEmail() throws Exception {
		
	  String registerJson = "{\"email\": \"gnishtha26@yahoo.com\",\"firstName\": \"nishtha\",\"gender\": \"Female\",\"lastName\": \"gupta\",\"password\": \"Qwerty@123\",\"phone\": \"9599944411\",\"userType\": \"USER\"}";
      MvcResult result = this.mockMvc.perform(post("/user/register")
	               .content(registerJson)
	               .contentType(MediaType.APPLICATION_JSON)).andReturn();
      int status= JsonPath.read(result.getResponse().getContentAsString(), "$.status");
      assertEquals(ResponseStatus.EMAIL_ALREADY_EXISTS, status, "Incorrect Response Status");
	}
	
	@Test
	public void registerUserWithInvalidEmail() throws Exception {
		
	  String registerJson = "{\"email\": \"gnishtha26.com\",\"firstName\": \"nishtha\",\"gender\": \"Female\",\"lastName\": \"gupta\",\"password\": \"Qwerty@123\",\"phone\": \"9599944411\",\"userType\": \"USER\"}";
      MvcResult result = this.mockMvc.perform(post("/user/register")
	               .content(registerJson)
	               .contentType(MediaType.APPLICATION_JSON)).andReturn();
      int status= JsonPath.read(result.getResponse().getContentAsString(), "$.status");
      assertEquals(ResponseStatus.BAD_REQUEST, status, "Incorrect Response Status");
	}
	
	@Test
	public void registerUserWithInvalidPhone() throws Exception {
		
	  String registerJson = "{\"email\": \"nishtha.gupta@quovantis.com\",\"firstName\": \"nishtha\",\"gender\": \"Female\",\"lastName\": \"gupta\",\"password\": \"Qwerty@123\",\"phone\": \"49599911\",\"userType\": \"USER\"}";
      MvcResult result = this.mockMvc.perform(post("/user/register")
	               .content(registerJson)
	               .contentType(MediaType.APPLICATION_JSON)).andReturn();
      int status= JsonPath.read(result.getResponse().getContentAsString(), "$.status");
      assertEquals(ResponseStatus.BAD_REQUEST, status, "Incorrect Response Status");
	}
	
	@Test
	public void registerUserWithExistingPhone() throws Exception {
		
	  String registerJson = "{\"email\": \"nishtha.gupta@quovantis.com\",\"firstName\": \"nishtha\",\"gender\": \"Female\",\"lastName\": \"gupta\",\"password\": \"Qwerty@123\",\"phone\": \"9599944411\",\"userType\": \"USER\"}";
      MvcResult result = this.mockMvc.perform(post("/user/register")
	               .content(registerJson)
	               .contentType(MediaType.APPLICATION_JSON)).andReturn();
      int status= JsonPath.read(result.getResponse().getContentAsString(), "$.status");
      assertEquals(ResponseStatus.PHONE_ALREADY_EXISTS, status, "Incorrect Response Status");
	}

}