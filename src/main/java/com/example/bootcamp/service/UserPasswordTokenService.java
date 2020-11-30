package com.example.bootcamp.service;

import com.example.bootcamp.model.ResetPasswordModel;

public interface UserPasswordTokenService {

	String sendPasswordResetLink(String emailorPhone);

	String verifyPasswordResetLink(String resetLink);

	String updatePassword(ResetPasswordModel resetPasswordModel, String link);

}
