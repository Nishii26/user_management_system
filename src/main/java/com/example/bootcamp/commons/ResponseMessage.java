package com.example.bootcamp.commons;

public class ResponseMessage {

	public static final String OK = "ok";
	public static final String SUCCESS = "Success";
	public static final String FAILED = "Failed";
	public static final String DATA_VALIDATION_ERROR = "Data Validation Error";
	public static final String INTERNAL_ERROR = "Failure due to the server not responding at this time. Please try again later. ";
	public static final String ACCESS_DENIED = "Forbiden Access Denied!";
	public static final String NO_DATA_FOUND = "No Data Found";
	public static final String EMAIL_ALREADY_EXISTS ="The provided email is already registered and they should try to login instead or use another email to register. ";
	public static final String PHONE_ALREADY_EXISTS ="The provided phone number is already registered and should try to login instead or use another phone number to register. ";
	public static final String REGISTERATION_FAILURE ="Registeration Failed. Please Try Again!";
	public static final String REGISTERATION_SUCCESS ="Thank you for signing up.";
	public static final String EMAIL_NOT_FOUND ="Your email entered is not registered with us. Please provide a valid email.";
	public static final String EMAIL_OR_PHONE_NOT_FOUND ="This email/phone number entered is not registered with us. Please provide a valid email/phone number";
	public static final String RESET_LINK_SUCCESS ="An email has been sent with a reset link. Please check your inbox.";
	public static final String EMAIL_OR_RESET_LINK_INVALID =" Invalid email or link.Please provide valid link";
	public static final String RESET_LINK_EXPIRED ="Your Link has Expired.Please try generating new link";
	public static final String RESET_LINK_VERIFIED ="Your Link verified successfully.";
	public static final String OLD_PASSWORD_INCORRECT ="Your Old Password is incorrect.";
	public static final String MATCH_FAIL ="Your New Password doesn't match Confirm Password";
	public static final String RESET_PASSWORD_SUCCESS ="Password updated successfully";
}
