package com.example.bootcamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.bootcamp.commons.ResponseStatus;
import com.example.bootcamp.utils.Constant;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestUserPasswordTokenController {
	
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
		public void forgetPasswordWithCorrectInput() throws Exception {
			
		String exampleUserJson = "{ \"emailorPhone\": \"9599944411\"}";
	      MvcResult result = this.mockMvc.perform(post("/user/forgot-password")
		               .content(exampleUserJson)
		               .contentType(MediaType.APPLICATION_JSON)).andReturn();
	      int status= JsonPath.read(result.getResponse().getContentAsString(), "$.status");
	      assertEquals(ResponseStatus.SUCCESS, status, "Incorrect Response Status");
	    }
	  
	  @Test
		public void forgetPasswordWithIncorrectInput() throws Exception {
			
		String exampleUserJson = "{ \"emailorPhone\": \"\" }";
	      MvcResult result = this.mockMvc.perform(post("/user/forgot-password")
		               .content(exampleUserJson)
		               .contentType(MediaType.APPLICATION_JSON)).andReturn();
	      int status= JsonPath.read(result.getResponse().getContentAsString(), "$.status");
	      assertEquals(ResponseStatus.BAD_REQUEST, status, "Incorrect Response Status");
	    }
 
	  @Test
		public void verifyPasswordValidity() throws Exception {
			
	      MvcResult result = this.mockMvc.perform(get("/user/verify-password-link")
	    		  	   .header(Constant.RESET_LINK_HEADER, "?token=SjJ3czBiRlVkamNPMnBhQnBWUjY=&email=Z25pc2h0aGEyNkB5YWhvby5jb20=")
		               .contentType(MediaType.APPLICATION_JSON)).andReturn();
	      int status= JsonPath.read(result.getResponse().getContentAsString(), "$.status");
	      assertEquals(ResponseStatus.FAILED, status, "Incorrect Response Status");
	    }
	  
	  @Test
		public void updatePasswordWithIncorrectNewPasswordFormat() throws Exception {
			
		  String exampleUserJson = "{ \"confirmPassword\": \"Qwerty@321\", \"email\": \"Z25pc2h0aGEyNkB5YWhvby5jb20=\", \"newPassword\": \"321\", \"oldPassword\": \"Qwerty@123\" }";
	      MvcResult result = this.mockMvc.perform(post("/user/update-password")
	    		       .content(exampleUserJson)
	    		  	   .header(Constant.RESET_LINK_HEADER, "?token=SjJ3czBiRlVkamNPMnBhQnBWUjY=&email=Z25pc2h0aGEyNkB5YWhvby5jb20=")
		               .contentType(MediaType.APPLICATION_JSON)).andReturn();
	      int status= JsonPath.read(result.getResponse().getContentAsString(), "$.status");
	      assertEquals(ResponseStatus.BAD_REQUEST, status, "Incorrect Response Status");
	    }
	  
	  @Test
		public void updatePasswordWithIncorrectConfirmPasswordFormat() throws Exception {
			
		  String exampleUserJson = "{ \"confirmPassword\": \"Q321\", \"email\": \"Z25pc2h0aGEyNkB5YWhvby5jb20=\", \"newPassword\": \"Qwerty@321\", \"oldPassword\": \"Qwerty@123\" }";
	      MvcResult result = this.mockMvc.perform(post("/user/update-password")
	    		       .content(exampleUserJson)
	    		  	   .header(Constant.RESET_LINK_HEADER, "?token=SjJ3czBiRlVkamNPMnBhQnBWUjY=&email=Z25pc2h0aGEyNkB5YWhvby5jb20=")
		               .contentType(MediaType.APPLICATION_JSON)).andReturn();
	      int status= JsonPath.read(result.getResponse().getContentAsString(), "$.status");
	      assertEquals(ResponseStatus.BAD_REQUEST, status, "Incorrect Response Status");
	    }

	  @Test
		public void updatePasswordWithMismatchConfirmNewPassword() throws Exception {
			
		  String exampleUserJson = "{ \"confirmPassword\": \"Qwerty@123\", \"email\": \"Z25pc2h0aGEyNkB5YWhvby5jb20=\", \"newPassword\": \"Qwerty@321\", \"oldPassword\": \"Qwerty@123\" }";
	      MvcResult result = this.mockMvc.perform(post("/user/update-password")
	    		       .content(exampleUserJson)
	    		  	   .header(Constant.RESET_LINK_HEADER, "?token=SjJ3czBiRlVkamNPMnBhQnBWUjY=&email=Z25pc2h0aGEyNkB5YWhvby5jb20=")
		               .contentType(MediaType.APPLICATION_JSON)).andReturn();
	      int status= JsonPath.read(result.getResponse().getContentAsString(), "$.status");
	      assertEquals(ResponseStatus.FAILED, status, "Incorrect Response Status");
	    }
	  
	  @Test
			public void updatePasswordWithIncorrectOldPassword() throws Exception {
				
			  String exampleUserJson = "{ \"confirmPassword\": \"Qwerty@321\", \"email\": \"Z25pc2h0aGEyNkB5YWhvby5jb20=\", \"newPassword\": \"Qwerty@321\", \"oldPassword\": \"Qwer@qw123\" }";
		      MvcResult result = this.mockMvc.perform(post("/user/update-password")
		    		       .content(exampleUserJson)
		    		  	   .header(Constant.RESET_LINK_HEADER, "?token=SjJ3czBiRlVkamNPMnBhQnBWUjY=&email=Z25pc2h0aGEyNkB5YWhvby5jb20=")
			               .contentType(MediaType.APPLICATION_JSON)).andReturn();
		      int status= JsonPath.read(result.getResponse().getContentAsString(), "$.status");
		      assertEquals(ResponseStatus.FAILED, status, "Incorrect Response Status");
		    }
	  

}
