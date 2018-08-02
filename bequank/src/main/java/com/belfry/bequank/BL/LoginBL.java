package com.belfry.bequank.BL;

import com.belfry.bequank.entity.User;
import com.belfry.bequank.repository.UserRepository;
import com.belfry.bequank.util.LoginInfo;
import com.belfry.bequank.util.MESSAGE;
import com.belfry.bequank.util.ROLE;
import com.sun.mail.util.MailSSLSocketFactory;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.*;
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

public class LoginBL implements LoginBLInterface{
    @Autowired
    UserRepository userRepository;
    public JSONObject login(String username, String password, Map<String,String> userlist) {

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
                LoginInfo loginInfo=new LoginInfo(token,0);
                userlist.put(u.getUsername(),token);// only when login is successful will we add it to the list.
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
    public int sendVerificationCode(String email,Map<String,Integer> vericodelist) throws MessagingException, GeneralSecurityException {
        int code=(int)(Math.random()*900000.0+100000);

        Properties props = new Properties();
        props.put("mail.smtp.user", "bequank@outlook.com");
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "true");

        try {
            Authenticator auth = new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("bequank@outlook.com", "citi@2018");
                }
            };

            Session session = Session.getInstance(props, auth);

            MimeMessage message = new MimeMessage(session);
            message.setSubject("您在bequank平台注册的验证码");
            message.setFrom(new InternetAddress("bequank@outlook.com"));

            String pre = "<p>您在bequank平台注册的验证码为：<font size=\"5\" color=\"red\"><b>";
            String suf = "</b></font>，如非本人操作，请忽视本信息。</p>" +
                    "<p>请勿将此验证码告知他人。</p><p>bequank™平台</p>";

            MimeMultipart all = new MimeMultipart("related");
            MimeBodyPart text = new MimeBodyPart();
            text.setContent(pre + code + suf, "text/html;charset=UTF-8");
            all.addBodyPart(text);
            message.setContent(all);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            Transport.send(message);
            vericodelist.put(email,code);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("code: "+code);
        return code;

    }
    public JSONObject signup(String email,String nickname,String password,int vericode,Map<String,Integer> vericodelist){
        User u=userRepository.findByEmail(email);
        JSONObject response=new JSONObject();
        if(vericodelist.get(email)!=vericode){
            response.put("status",MESSAGE.MSG_WRONG_VERICODE);
            response.put("message","验证码错误");
        };
        if(u!=null){
            response.put("status",MESSAGE.MSG_DUPLICATE_EMAIL);
            response.put("message","邮箱已使用");
        }
        else{
            vericodelist.remove(email);
            User user=new User(email,password,nickname,ROLE.NORMAL);
            userRepository.save(user);
            response.put("status",MESSAGE.MSG_SUCCESS);
        }
        return response;
    }

}
