package com.ps.web.jdbc.api;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.ps.web.jdbc.model.Email;

public class EmailSendApi {
	public String sendEmail(Email em) {
		String name = em.getName();
		String recipient = em.getEmail();
		String subject = em.getSubject();
		String text = em.getMessage();
		
		String textMessage = makeMessage(name, text);
		
//		set gmail user and password from account witch is used to send email
		String user = "";
		String password = "";
		
		Properties properties = System.getProperties();
		properties.put("mail.transport.protocol", "smtps");
		properties.put("mail.smtps.auth", "true");
        properties.put("mail.smtps.socketFactory.port", "465");
        properties.put("mail.smtps.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtps.socketFactory.fallback", "false");
        properties.put("mail.smtps.quitwait", "false");
  
        Session mailSession = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("user", "password");
            }
        });
        mailSession.setDebug(true);
        
        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(subject);
            message.setContent(textMessage, "text/html");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            
            Transport transport = mailSession.getTransport();
            transport.connect("smtp.gmail.com", 465, user, password);
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (MessagingException e) {
        	e.printStackTrace();
        }
        return "Email send successful";
	}

	private String makeMessage(String name, String text) {
		StringBuilder str = new StringBuilder();
		str.append("<em>Hello");
		if (name != null) {
			str.append(name);
			str.append(", ");
		}
		str.append("</em><p>");
		str.append(text);
		str.append("</p>Regards,<br>Admin");

		return str.toString();
	}
}
