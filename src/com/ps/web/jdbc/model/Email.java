package com.ps.web.jdbc.model;

import java.util.Map;

public class Email {
	private String subject;
	private String message;
	private Map<String, String> recipients;

	

	public Email(String subject, String message, Map<String, String> recipients) {
		this.subject = subject;
		this.message = message;
		this.recipients = recipients;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getRecipients() {
		return recipients;
	}

	public void setRecipients(Map<String, String> recipients) {
		this.recipients = recipients;
	}
	
}
