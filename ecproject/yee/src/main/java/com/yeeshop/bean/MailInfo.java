package com.yeeshop.bean;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MailInfo {
    String from;
    String to;
    String cc;
    String bcc;
    String subject;
    String body;
    String files;

    public MailInfo(String from, String to, String subject, String body) {
		super();
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
}
