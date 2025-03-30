package com.incapp.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

public class SendMail {
	public static void notificationMail(String email, String name) {
		try {
			String subject="Message: People Talk Notification";
			String content=name+" send somthing. Please visit people talk website.";
			//send mail code
			String sEmail="nikhilpathak3528@gmail.com";
			String sPassword="nfwm nyjl yith xopa";
			
			Properties props=new Properties();
            props.put("mail.smtp.host","smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port","465");
            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth","true");
            props.put("mail.smtp.port","465");
            props.put("mail.smtp.ssl.trust","smtp.gmail.com");
            Session ses=Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(sEmail,sPassword);
                    }
                }
            );
            Message message=new MimeMessage(ses);
            message.setFrom(new InternetAddress(sEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setContent(content,"text/html" );
            Transport.send(message);
			//end
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void passwordMail(String email, String password) {
		try {
			String subject="Password: People Talk Notification";
			String content="Your password for people talk website is "+password;
			//send mail code
			String sEmail="sender email id";
			String sPassword="password";
			
			Properties props=new Properties();
            props.put("mail.smtp.host","smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port","465");
            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth","true");
            props.put("mail.smtp.port","465");
            props.put("mail.smtp.ssl.trust","smtp.gmail.com");
            Session ses=Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(sEmail,sPassword);
                    }
                }
            );
            Message message=new MimeMessage(ses);
            message.setFrom(new InternetAddress(sEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setContent(content,"text/html" );
            Transport.send(message);
			//end
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
