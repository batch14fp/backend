package com.lawencon.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailSenderService {
	@Autowired
	private JavaMailSender mailSender;


	public void sendEmail(String toEmail, String subject, String body) {
		final SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("zelkiaanisa@gmail.com");
		message.setTo(toEmail);
		message.setSubject(subject);
		message.setText(body);

		mailSender.send(message);

		System.out.println("Mail sent Successfully...");

	}


}
