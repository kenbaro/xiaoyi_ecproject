package com.yeeshop.service;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.yeeshop.bean.MailInfo;

@Service
public class MailService {
    
    JavaMailSender mailer;

    public void send(MailInfo mailInfo) throws MessagingException{
        MimeMessage message = mailer.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
        helper.setFrom(mailInfo.getFrom());
        helper.setTo(mailInfo.getTo());
        helper.setSubject(mailInfo.getSubject());
        helper.setText(mailInfo.getBody());
        helper.setReplyTo(mailInfo.getFrom());

        if (mailInfo.getCc()!= null){helper.setCc(mailInfo.getCc());}
        if (mailInfo.getBcc()!=null){helper.setBcc(mailInfo.getBcc());}
        if (mailInfo.getFiles()!=null){
            String[] paths= mailInfo.getFiles().split(";");
            for (String path:paths){
                File file= new File(path);
                helper.addAttachment(file.getName(), file);
            }
        }
        mailer.send(message);
    }
}
