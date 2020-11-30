package com.example.bootcamp.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailSender extends Thread{
	
	private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);
	
	String email;

	String subject;

	String name;

	String text;

	String cc;
	String fileName;
	String filePath;
	String businessName;

	

	public EmailSender(String email, String subject, String name, String text,String businessName) {
		super();
		this.email = email;
		this.subject = subject;
		this.name = name;
		this.text = text;
		this.businessName=businessName;
	}

	public EmailSender(String email, String subject, String name, String text, String cc,String businessName) {
		super();
		this.email = email;
		this.subject = subject;
		this.name = name;
		this.text = text;
		this.cc = cc;
		this.businessName=businessName;

	}

	public EmailSender(String email, String subject, String name, String text, String cc, String fileName,
			String filePath,String businessName) {
		super();
		this.email = email;
		this.subject = subject;
		this.name = name;
		this.text = text;
		this.cc = cc;
		this.fileName = fileName;
		this.filePath = filePath;
		this.businessName=businessName;
	}

	public EmailSender(String email, String subject, String name, String text, String fileName, String filePath,String businessName) {
		super();
		this.email = email;
		this.subject = subject;
		this.name = name;
		this.text = text;
		this.fileName = fileName;
		this.filePath = filePath;
		this.businessName=businessName;
	}

	public void run() {
		long start = System.currentTimeMillis();
		final String username = Constant.SMTP_USERNAME;
        final String password = Constant.SMTP_PASSWORD;

        Properties props = new Properties();
  
        
        props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(username, password);
		    }
		});
        
		try {
			String to_email = email;
			
			System.out.println(to_email);
			Message message = new MimeMessage(session);
			message.setContent(text, "text/html; charset=utf-8");

			message.setFrom(new InternetAddress(Constant.EMAIL, businessName));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to_email));
			if (cc != null)
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
			message.setSubject(subject);
//			message.setText("DDD");
			message.setHeader("X-MC-Tags", "non-mandotary");
			try {Transport.send(message);
			logger.info("Successfully email sent to : "+email);
				
			} catch (Exception e) {
				
				logger.error(e.toString());
				logger.info("Failure in email : "+email);
				// TODO: handle exception
			}
			long end = System.currentTimeMillis();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void sendEmail(String email, String subject, String name, String text,String businessName) {
	
		/*
		 * System.out.println(email); System.out.println(subject);
		 * System.out.println(name); System.out.println(businessName);
		 */		
		EmailSender message_sender = new EmailSender(email, subject, name, text,businessName);
		message_sender.run();
	}

}
