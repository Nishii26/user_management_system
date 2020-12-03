package com.example.bootcamp.serviceimpl;

import java.util.Base64;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bootcamp.commons.ResponseBody;
import com.example.bootcamp.commons.ResponseMessage;
import com.example.bootcamp.commons.ResponseStatus;
import com.example.bootcamp.entity.UserPasswordToken;
import com.example.bootcamp.entity.Users;
import com.example.bootcamp.model.ResetPasswordModel;
import com.example.bootcamp.repo.UserPasswordTokenRepo;
import com.example.bootcamp.repo.UsersRepo;
import com.example.bootcamp.service.UserPasswordTokenService;
import com.example.bootcamp.utils.CommonUtils;
import com.example.bootcamp.utils.Constant;
import com.example.bootcamp.utils.EmailSender;
import com.example.bootcamp.utils.JsonUtils;
import com.example.bootcamp.utils.Timings;
import com.google.gson.Gson;

@Service
public class UserPasswordTokenServiceImpl implements UserPasswordTokenService{
	
	@Autowired
	UserPasswordTokenRepo userPasswordTokenRepo;
	
	@Autowired
	UsersRepo usersRepo;

	@Override
	public String sendPasswordResetLink(@Valid String emailOrPhone) {
		
		Users userByEmail = usersRepo.findByEmailOrPhone(emailOrPhone);
		if(null != userByEmail) {
			if(null != userByEmail.getEmail()) {
				UserPasswordToken userPasswordToken = new UserPasswordToken();
				userPasswordToken.setUserId(userByEmail.getUserId());
				userPasswordToken.setIsActive(true);
				userPasswordToken.setCreation(Timings.currentTime());
				userPasswordToken.setPasswordToken(RandomStringUtils.randomAlphanumeric(20));
				userPasswordTokenRepo.save(userPasswordToken);
				
				String encodedToken = Base64.getEncoder().encodeToString(userPasswordToken.getPasswordToken().getBytes());
				String encodedEmail = Base64.getEncoder().encodeToString(userByEmail.getEmail().getBytes());
				
				String link = "";
				if (Constant.LIVE_SERVER.equals(true))
					link = Constant.FORGOT_PASSWORD_LINK_PRODUCTION + "?token="+encodedToken+"&email="+encodedEmail;
				else
					link = Constant.FORGOT_PASSWORD_LINK_DEVELOPMENT +"?token="+ encodedToken+"&email="+encodedEmail;

				
				String text = "Hi " + userByEmail.getFirstName() + ""
						+ ",<br><br> Please find the Link below to reset password <br><br>" + link;				
				
				EmailSender.sendEmail(userByEmail.getEmail(), "Set Password",  "", text,Constant.BUSINESS_NAME);
				
				return JsonUtils.getResponseJson(ResponseStatus.SUCCESS, 
												 ResponseMessage.RESET_LINK_SUCCESS, 
												 link);
				
			}else {
				return JsonUtils.getResponseJson(ResponseStatus.NO_DATA_FOUND, 
						 ResponseMessage.EMAIL_NOT_FOUND, 
						 null);
			}
		}else {
			return JsonUtils.getResponseJson(ResponseStatus.BAD_REQUEST, 
											 ResponseMessage.EMAIL_OR_PHONE_NOT_FOUND, 
											 null);
		}

	}

	@Override
	public String verifyPasswordResetLink(String resetLink) {
		
		String[] splitByEquals = resetLink.split("=", 4); 
		String encodedEmail = splitByEquals[3];
		String encodedToken = splitByEquals[1];
				
		byte[] decodedBytesToken = Base64.getDecoder().decode(encodedToken); 
		String decodedToken = new String(decodedBytesToken);
		byte[] decodedBytesEmail = Base64.getDecoder().decode(encodedEmail);
		String decodedEmail = new String(decodedBytesEmail);
		
		Optional<UserPasswordToken> userPasswordToken = userPasswordTokenRepo.findByPasswordToken(decodedToken);
		if(!userPasswordToken.isPresent()) {
			return JsonUtils.getResponseJson(ResponseStatus.NO_DATA_FOUND, 
											 ResponseMessage.EMAIL_OR_RESET_LINK_INVALID, 
											 null);
		}else {
			if(!userPasswordToken.get().getIsActive().equals(true)) {
				return JsonUtils.getResponseJson(ResponseStatus.FAILED,
							                     ResponseMessage.RESET_LINK_EXPIRED,
							                     null);
			}else {
				Optional<Users> user = usersRepo.findById(userPasswordToken.get().getUserId());
				if(!user.isPresent()) {
					return JsonUtils.getResponseJson(ResponseStatus.FAILED,
		                     ResponseMessage.NO_DATA_FOUND,
		                     null);
				}else {
					if(!user.get().getEmail().equalsIgnoreCase(decodedEmail)) {
						return JsonUtils.getResponseJson(ResponseStatus.FAILED,
			                     ResponseMessage.EMAIL_OR_RESET_LINK_INVALID,
			                     null);
					}else {
						long expiryTime =userPasswordToken.get().getCreation() + 10 * 60 * 1000;
						if (expiryTime < Timings.currentTime()) {
							userPasswordToken.get().setIsActive(false);
							userPasswordTokenRepo.save(userPasswordToken.get());
							return JsonUtils.getResponseJson(ResponseStatus.FAILED, 
													         ResponseMessage.EMAIL_OR_RESET_LINK_INVALID,
													         null);
						}
						return JsonUtils.getResponseJson(ResponseStatus.SUCCESS,
														 ResponseMessage.RESET_LINK_VERIFIED,
														 encodedEmail);
					}
				}
			}
		}
	}

	@Override
	public String updatePassword(ResetPasswordModel resetPasswordModel,String resetLink) {
		
		String[] splitByEquals = resetLink.split("=", 4); 
		String encodedToken = splitByEquals[1];
		
		byte[] decodedBytesToken = Base64.getDecoder().decode(encodedToken);
		String decodedToken = new String(decodedBytesToken);
		
		byte[] decodedBytesEmail = Base64.getDecoder().decode(resetPasswordModel.getEmail());
		String decodedEmail = new String(decodedBytesEmail);
				
		if(!resetPasswordModel.getConfirmPassword().equalsIgnoreCase(resetPasswordModel.getNewPassword()))
			return JsonUtils.getResponseJson(ResponseStatus.FAILED,
											 ResponseMessage.MATCH_FAIL,
											 null);
		Optional<Users> user = usersRepo.findByEmail(decodedEmail);
		if(!user.isPresent())
			return JsonUtils.getResponseJson(ResponseStatus.FAILED, 
                    ResponseMessage.EMAIL_NOT_FOUND, 
                    null);
		if(CommonUtils.matchPassword(user.get().getPassword(),resetPasswordModel.getNewPassword()))
			return JsonUtils.getResponseJson(ResponseStatus.FAILED, 
                    ResponseMessage.OLD_PASSWORD_INCORRECT, 
                    null);
		 String verifiedResponse = this.verifyPasswordResetLink(resetLink);
		 Gson gson = new Gson();
		 ResponseBody resp = gson.fromJson(verifiedResponse.toString(), ResponseBody.class);
		 if(resp.getStatus()!=101)
			 return verifiedResponse;
		 Optional<UserPasswordToken> userPasswordToken = userPasswordTokenRepo.findByPasswordToken(decodedToken);
		 if(!userPasswordToken.isPresent()) 
				return JsonUtils.getResponseJson(ResponseStatus.NO_DATA_FOUND, 
												 ResponseMessage.EMAIL_OR_RESET_LINK_INVALID, 
												 null);
		userPasswordToken.get().setIsActive(false);
		userPasswordTokenRepo.save(userPasswordToken.get());
		
		user.get().setPassword(CommonUtils.hashPassword(resetPasswordModel.getNewPassword()));
		usersRepo.save(user.get());
		
		return JsonUtils.getResponseJson(ResponseStatus.SUCCESS,
							             ResponseMessage.RESET_PASSWORD_SUCCESS,
										 null);	
	}
}
