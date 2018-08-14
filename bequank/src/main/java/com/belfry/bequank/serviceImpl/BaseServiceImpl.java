package com.belfry.bequank.serviceImpl;

import com.belfry.bequank.entity.User;
import com.belfry.bequank.repository.UserRepository;
import com.belfry.bequank.service.BaseService;
import com.belfry.bequank.util.JwtUtil;
import com.belfry.bequank.util.Message;
import com.belfry.bequank.util.Role;
import com.sun.mail.util.MailSSLSocketFactory;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.GeneralSecurityException;
import java.util.Properties;

@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    UserRepository repository;

    @Autowired
    StringRedisTemplate template;

    @Autowired
    JwtUtil jwtUtil;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public JSONObject register(JSONObject object) {
        JSONObject res = new JSONObject();
        String userName = object.getString("email");
        User user1 = repository.findByUserName(userName);
        if (user1 != null) {
            res.put("status", Message.MSG_DUPLICATE_EMAIL);
            res.put("message", "邮箱已使用");
            return res;
        }

        String actualCode = object.getString("identifyCode");
        String expectCode = template.opsForValue().get("codeList::" + userName);
        if (!actualCode.equals(expectCode)) {
            res.put("status", Message.MSG_WRONG_VERICODE);
            res.put("message", "验证码错误");
            return res;
        }

        String password = object.getString("password");
        String nickName = object.getString("nickName");

        User user = new User(userName, password, nickName, Role.NORMAL);
        repository.saveAndFlush(user);
        res.put("status", Message.MSG_SUCCESS);
        res.put("message", "注册成功");
        return res;
    }

    @Override
    public JSONObject login(User user) {
        JSONObject object = new JSONObject();
        User user1 = repository.findByUserName(user.getUserName());
        if (user1 == null) {
            object.put("status", Message.MSG_USER_NOTEXIST);
            object.put("message", "用户不存在");
            return object;
        }

        String expectPw = user1.getPassword();
        String actualPw = user.getPassword();
        if (!expectPw.equals(actualPw)) {
            object.put("status", Message.MSG_WRONG_PASSWORD);
            object.put("message", "密码错误");
            return object;
        }

        String token = jwtUtil.generateToken(user1);
        String role = user1.getRole();
        object.put("status", Message.MSG_SUCCESS);
        object.put("token", token);
        object.put("role", role);
        return object;
    }

    @Override
    @CacheEvict(value = "loginList",key = "#user.userName")
    public void logout(User user) {
        logger.info("user {} logs out", user.getUserName());
    }

    @Override
    public JSONObject sendVerificationCode(String email) throws GeneralSecurityException, MessagingException {
        Properties props = new Properties();

        //同一个bean的内部方法调用不会触发cache！因此要显式使用cache
        int code=(int)(Math.random()*900000+100000);
        template.opsForValue().set("codeList::" + email, Integer.toString(code));

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

        MimeMultipart all = new MimeMultipart("related");
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(pre + code + suf, "text/html;charset=UTF-8");
        all.addBodyPart(text);

        message.setContent(all);
        Transport transport = session.getTransport();
        transport.connect("smtp.qq.com", "2320468069@qq.com", "mohrzsktikvyebbe");

        transport.sendMessage(message, new Address[] { new InternetAddress(email) });
        transport.close();

        JSONObject object = new JSONObject();
        object.put("status", Message.MSG_SUCCESS);
        object.put("message", "发送成功");
        return object;
    }
    
}