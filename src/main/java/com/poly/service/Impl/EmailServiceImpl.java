package com.poly.service.Impl;

import javax.servlet.ServletContext;

import com.poly.entity.User;
import com.poly.service.EmailService;
import com.poly.util.SendMailUtils;

public class EmailServiceImpl implements EmailService {

	private static final String EMAIL_WELCOME_SUBJECT = "Welcome to YG FAMILY";
	private static final String EMAIL_FOGOT_PASS = "New password";
	@Override
	public void sendMail(ServletContext context, User recipient, String type) {

		String host = context.getInitParameter("host");
		String port = context.getInitParameter("port");
		String user = context.getInitParameter("user");
		String pass = context.getInitParameter("pass");

		
		
		try {
			String content = null;
			String subject = null;
			switch(type) {
			case "welcome":
				subject = EMAIL_WELCOME_SUBJECT;
				content = "Dear " + recipient.getUsername();
				break;
			case"forgot":
				subject = EMAIL_FOGOT_PASS;
				content = "Dear " + recipient.getUsername()+ ", new password: "+recipient.getPassword();
				break;
				
			default:
				subject = "YG FAMILY";
				content = "Maybe this email is wrong";
			}
			SendMailUtils.sendEmail(host, port, user, pass, recipient.getEmail(), subject, content);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
