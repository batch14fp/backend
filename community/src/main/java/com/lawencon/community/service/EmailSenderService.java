package com.lawencon.community.service;

import javax.mail.MessagingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class EmailSenderService {

	 private final TemplateEngine templateEngine;
	 private final JavaMailSender javaMailSender;
	 
	 public EmailSenderService(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
	        this.templateEngine = templateEngine;
	        this.javaMailSender = javaMailSender;
	    }
	 
	 public String sendEmail(final String to, final String codeVerification) throws MessagingException {
	        Context context = new Context();
	        context.setVariable("codeVerification", codeVerification);

	        String process = templateEngine.process("emails/verification", context);
	        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
	        helper.setSubject("Code Verification Request");
	        helper.setText(process, true);
	        helper.setTo(to);
	        javaMailSender.send(mimeMessage);
	        return "Sent";
	 }
	 
}
