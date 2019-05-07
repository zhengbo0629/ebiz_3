package com.ebiz.utils;

import com.ebiz.model.EbizCompany;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmailSenderCenter {

	private static EmailSenderCenter instance;
	private Properties gmailProps;
	private Properties hotmailProps;
	private Properties outlookProps;
    private Properties email163Props;
	private static ArrayList<String> to=new ArrayList<String>();
	private static ArrayList<String> sendedContaints=new ArrayList<String>();

	private EmailSenderCenter() {

		gmailProps = new Properties();
		gmailProps.put("mail.smtp.host", "smtp.gmail.com");
		gmailProps.put("mail.smtp.socketFactory.port", "465");
		gmailProps.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		gmailProps.put("mail.smtp.auth", "true");
		gmailProps.put("mail.smtp.port", "465");
		gmailProps.put("mail.transport.protocol", "smtp");


		hotmailProps=new Properties();
		hotmailProps.setProperty("mail.transport.protocol", "smtp");
		hotmailProps.setProperty("mail.host", "smtp.live.com");
		hotmailProps.put("mail.smtp.starttls.enable", "true");
		hotmailProps.put("mail.smtp.auth", "true");
		hotmailProps.put("mail.smtp.port", "587");

        outlookProps=new Properties();
        outlookProps.setProperty("mail.transport.protocol", "smtp");
        outlookProps.setProperty("mail.host", "smtp.office365.com");
        outlookProps.put("mail.smtp.starttls.enable", "true");
        outlookProps.put("mail.smtp.auth", "true");
        outlookProps.put("mail.smtp.port", "587");

        email163Props=new Properties();
        email163Props.setProperty("mail.transport.protocol", "smtp");
		System.out.println("选择163邮箱");
        email163Props.setProperty("mail.host", "smtp.163.com");
        email163Props.put("mail.smtp.starttls.enable", "true");
        email163Props.put("mail.smtp.auth", "true");
        email163Props.put("mail.smtp.port", "25");

		addRecipients();


	}

	private void addRecipients(){
		to.add("yinghaosun123@163.com");
		//to.add("yinghaosun123@hotmail.com");
		//to.add("gonewlife2015@gmail.com");  	// Tian Guoxun
		//to.add("clylovemoney@gmail.com");  	//
		//to.add("wookao.mitbbs@gmail.com");  	// Zou Quan
		//to.add("thudelin@gmail.com");  	//
		//to.add("gao.hyan@gmail.com");		 	// Gao HongYan
		//to.add("wu.zhiqiang.1020@gmail.com");	// Wu ZhiQiang
		//to.add("zhu3030@yahoo.com");			// Zhu Dongzhi
	}

	public void addRecipient(String emailAddress){
		to.add(emailAddress);
	}

	public static synchronized EmailSenderCenter getInstance() {
		if (instance == null) {
			instance = new EmailSenderCenter();
		}
		return instance;
	}

	public boolean sendEmailtoOneRecipientFromCompany(EbizCompany company,String emailAddress,String emailTitle,String emailContent, List<String> chosenFileStrings){
	final String email=company.getEmail();

	final String password=company.getEmailPassword();
		System.out.println("发件箱"+email+password);
	//	final String email = "yinghaosun123@163.com";
	//	final String password = "yinghaosun123";
		System.out.println("------------------------------------��ʼ����---------------------------");
		System.out.println("email = " + email);
		System.out.println("email = " + emailAddress);
		System.out.println("password = " + password);
		System.out.println("------------------------------------��������---------------------------");

		boolean result=true;
		Session session;
		try {
			Message message=null;
			if(email.toLowerCase().contains("@hotmail.com")){
				session = Session.getInstance(hotmailProps,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(email,
										password);
							}
						});
				message= new MimeMessage(session);
			}if(email.toLowerCase().contains("@outlook.com")){
				session = Session.getInstance(outlookProps,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(email,
										password);
							}
						});
				message= new MimeMessage(session);
			}if(email.toLowerCase().contains("@163.com")){
				session = Session.getInstance(email163Props,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(email,
										password);
							}
						});
				message= new MimeMessage(session);
			}else if(email.toLowerCase().contains("@gmail.com")){
				session = Session.getInstance(gmailProps,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(email,
										password);
							}
						});
				message= new MimeMessage(session);
			}else {
				return false;
			}
            System.out.println("邮箱已经开启smtp服务");
			//设置发信人
			message.setFrom(new InternetAddress(email));
			//设置收件人
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emailAddress));
			if (emailTitle==null){
				return false;
			}else{

				message.setSubject(MimeUtility.encodeText( emailTitle,"gb2312","B"));
			}

			Multipart mp = new MimeMultipart();

			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setContent(emailContent,"text/html;charset=gbk");
			mp.addBodyPart(mbp1);

			if(chosenFileStrings!=null)
			{
				MimeBodyPart mbp2 =null;
				for(int counter=0;counter<chosenFileStrings.size();counter++)
				{
					File file=new File(chosenFileStrings.get(counter));
					try {
						mbp2 = new MimeBodyPart();
						mbp2.attachFile(file);
						mp.addBodyPart(mbp2);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						result= false;
						e.printStackTrace();
						return false;
					}

				}
			}
			message.setContent(mp);
			System.out.println("Sending email to: "+emailAddress);
			session.getDebug();
			Transport.send(message);

			System.out.println("Done: Email send to: "+emailAddress);

		} catch (MessagingException | UnsupportedEncodingException e) {
			result= false;
			System.out.println("send email to: "+emailAddress+" was faild");
			throw new RuntimeException(e);
		}
		return result;
	}
	/**
	 *
	 * @param recpient
	 * @return
	 * @throws AddressException
	 */
	public Address[] getRecipientAddresses(List<String> recpient) throws Exception{

		Address[] address=new Address[recpient.size()];
		for(int i=0;i<recpient.size();i++){
			address[i]=new InternetAddress(recpient.get(i));
		}

		return address;
	}

	public boolean sendEmailtoMultipleReciForCompany(EbizCompany company,List<String> emailAddress,String emailTitle,String emailContent, List<String> chosenFileStrings){
//		final String email=company.getEmail();
//		final String password=company.getEmailPassword();
		final String email = "yinghaosun123@163.com";
		final String password = "yinghaosun123";
		System.out.println("email :" + email);
		boolean result=true;
		Session session;
		try {
			Message message=null;
			if(email.toLowerCase().contains("@hotmail.com")){
				session = Session.getInstance(hotmailProps,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(email,
										password);
							}
						});
				message= new MimeMessage(session);
			}if(email.toLowerCase().contains("@outlook.com")){
				session = Session.getInstance(outlookProps,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(email,
										password);
							}
						});
				message= new MimeMessage(session);
			}if(email.toLowerCase().contains("@163.com")){
				session = Session.getInstance(email163Props,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(email,
										password);
							}
						});
				message= new MimeMessage(session);
			}else if(email.toLowerCase().contains("@gmail.com")){
				session = Session.getInstance(gmailProps,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(email,
										password);
							}
						});
				message= new MimeMessage(session);
			}else {
				return false;
			}
			message.setFrom(new InternetAddress(email));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
//			List<String> emailAddressList = new ArrayList<>();
//			emailAddressList.add("1547998987@qq.com");
			message.setRecipients(Message.RecipientType.BCC, this.getRecipientAddresses(emailAddress));
			if (emailTitle==null){
				return false;
			}else{
				message.setSubject(MimeUtility.encodeText( emailTitle,"gb2312","B"));
			}
			Multipart mp = new MimeMultipart();

			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setContent(emailContent,"text/html;charset=gbk");
			mp.addBodyPart(mbp1);
			if(chosenFileStrings!=null)
			{
				MimeBodyPart mbp2 =null;
				for(int counter=0;counter<chosenFileStrings.size();counter++)
				{
					File file=new File(chosenFileStrings.get(counter));
					try {
						mbp2 = new MimeBodyPart();
						mbp2.attachFile(file);
						mp.addBodyPart(mbp2);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						result= false;
						e.printStackTrace();
						return false;
					}

				}
			}
			message.setContent(mp);
			System.out.println("Sending email to: "+emailAddress);
			session.getDebug();
			Transport.send(message);

			System.out.println("Done: Email send to: "+emailAddress);

		} catch (Exception e) {
			result= false;
			System.out.println("send email to: "+emailAddress+" was faild");
			throw new RuntimeException(e);
		}
		return result;
	}


	public static void main(String[] args) {

		System.out.println("I am main in.........");

		System.out.println("I am main out.........");
	}
}
