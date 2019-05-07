package com.ebiz.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SendEmailUtil {
    private static SendEmailUtil instance;
    private static  Properties gmailProps;
    private static  Properties hotmailProps;
    private static  Properties outlookProps;
    private static  Properties email163Props;
    private static ArrayList<String> to=new ArrayList<String>();
    private static ArrayList<String> sendedContaints=new ArrayList<String>();

    public static void putProp(){
        System.out.println("1212312312312");
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
        email163Props.setProperty("mail.host", "smtp.163.com");
        email163Props.put("mail.smtp.starttls.enable", "true");
        email163Props.put("mail.smtp.auth", "true");
        email163Props.put("mail.smtp.port", "25");

        addRecipients();
    }

    public SendEmailUtil(){
        System.out.println("1212312312312");
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
        email163Props.setProperty("mail.host", "smtp.163.com");
        email163Props.put("mail.smtp.starttls.enable", "true");
        email163Props.put("mail.smtp.auth", "true");
        email163Props.put("mail.smtp.port", "25");

        addRecipients();
    }


    //批量添加收件人
    private static void addRecipients(){
        //to.add("zhengbonuli521@163.com");
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

    //单例模式
    public static synchronized SendEmailUtil getInstance() {
        if (instance == null) {
            instance = new SendEmailUtil();
        }
        return instance;
    }


    /**
     * 发送单个的邮件（一个接收者）
     * @param companyEmail
     * @param companyPassword
     * @param emailAddress
     * @param emailTitle
     * @param emailContent
     * @param chosenFileStrings
     * @return
     */
    public static boolean sendEmailtoOneRecipientFromCompany(String companyEmail , String companyPassword,String emailAddress,String emailTitle,String emailContent, List<String> chosenFileStrings){
        putProp();
        final String email=companyEmail;
		final String password=companyPassword;
//        final String email = "yinghaosun123@163.com";
//        final String password = "yinghaosun123";
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
     * 发送多个邮件（多个收件人）
     * @param companyEmail  发邮件的邮箱地址
     * @param companyPassword 发送邮箱的密码
     * @param emailAddress  将要发送的邮箱的地址
     * @param emailTitle    邮件标题
     * @param emailContent  邮件内容
     * @param chosenFileStrings 发送的附件地址（文件）
     * @return
     */
    public static boolean sendEmailtoMultipleReciForCompany(String companyEmail , String companyPassword ,List<String> emailAddress,String emailTitle,String emailContent, List<String> chosenFileStrings){
        putProp();
        for(String address : emailAddress){
            System.out.println(address);
        }
//        final String email = companyEmail;
//		final String password = companyPassword;
        final String email = "yinghaosun123@163.com";
        final String password = "yinghaosun123";
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
            message.setRecipients(Message.RecipientType.BCC, getRecipientAddresses(emailAddress));
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


    /**
     * 将收件人地址转为地址对象
     * @param recpient
     * @return
     * @throws Exception
     */
    public static Address[] getRecipientAddresses(List<String> recpient) throws Exception{
        Address[] address=new Address[recpient.size()];
        for(int i=0;i<recpient.size();i++){
            address[i]=new InternetAddress(recpient.get(i));
        }
        return address;
    }

}
