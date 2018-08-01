package com.belfry.bequank.BL;

import com.belfry.bequank.entity.User;
import com.belfry.bequank.repository.UserRepository;
import com.belfry.bequank.util.MESSAGE;
import com.belfry.bequank.util.ROLE;
import com.sun.mail.util.MailSSLSocketFactory;
import net.sf.json.JSONObject;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.*;
/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 10:19 AM 8/1/18
 * @Modifiedby:
 */

public class LoginBL {
    public JSONObject login(String username, String password, UserRepository userRepository, Map<Long,String> userlist) {
        JSONObject response=new JSONObject();
        User u=userRepository.findByUsername(username);
        String token=UUID.randomUUID().toString().replace("-", "");
        /*
        if(username.equals("M")&&password.equals("M"))return MESSAGE.MSG_SUCCESS;
        if there will be a manager......
        */
        // TODO: 8/1/18 something about token need to be reorganized
        if(u!=null){
            if(u.getPassword().equals(password)) {
                response.put("status", MESSAGE.MSG_SUCCESS);
                response.put("token", token);
                response.put("role", u.getRole());
                userlist.put(u.getId(),token);// only when login is successful will we add it to the list.
            }else{
                response.put("status", MESSAGE.MSG_WRONG_PASSWORD);
                response.put("token", token);// login failed then this attribute is of no use, although it exists
                response.put("role", u.getRole());
            }
        }
        else {
            response.put("status",MESSAGE.MSG_USER_NOTEXIST);
            response.put("token",token);// same as this one
            response.put("role",u.getRole());
        }
        return response;
    }
    public int sendVerificationCode(String email) throws MessagingException, GeneralSecurityException {
        Properties props = new Properties();
        int code=(int)(Math.random()*900000+100000);
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.qq.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        MailSSLSocketFactory ssl = new MailSSLSocketFactory();
        ssl.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", ssl);

        Session session = Session.getInstance(props);

        MimeMessage message = new MimeMessage(session);
        message.setSubject("您的GeekMark众包网站验证码");
        message.setFrom(new InternetAddress("2320468069@qq.com"));


        String pre = "<p>您请求的beQuant注册服务验证码为：<font size=\"5\" color=\"red\"><b>";
        String suf = "</b></font>，如非本人操作，请忽视本信息，并尽快修改您的密码。</p>" +
                "<p>请勿将此验证码告知他人。</p><p>GeekMark团队</p>";
//        message.setContent(pre + r + suf, "text/html;charset=UTF-8");

        //////////
        MimeMultipart all = new MimeMultipart("related");
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(pre + code + suf, "text/html;charset=UTF-8");
        all.addBodyPart(text);

//        MimeBodyPart pic = new MimeBodyPart();
//        pic.setFileName("GeekMark.png");
//        pic.setDataHandler(new DataHandler(new FileDataSource("src//GeekMark.png")));
//        pic.setContentID("logo");
//        all.addBodyPart(pic);
        message.setContent(all);
        //////////

        Transport transport = session.getTransport();
        transport.connect("smtp.qq.com", "2320468069@qq.com", "mohrzsktikvyebbe");

        transport.sendMessage(message, new Address[] { new InternetAddress(email) });
        transport.close();
        System.out.println("code: "+code);
        return code;
    }
    public JSONObject signup(String email,String nickname,String password,UserRepository userRepository){
        User u=userRepository.findByEmail(email);
        JSONObject response=new JSONObject();
        if(u!=null){
            response.put("status",MESSAGE.MSG_DUPLICATE_EMAIL);
            response.put("message","邮箱已使用");
        }
        else{
            User user=new User(email,password,nickname,ROLE.NORMAL);
            userRepository.save(user);
            response.put("status",MESSAGE.MSG_SUCCESS);
        }
        return response;
    }

}
