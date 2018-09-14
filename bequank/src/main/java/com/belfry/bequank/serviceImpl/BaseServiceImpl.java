package com.belfry.bequank.serviceImpl;

import com.belfry.bequank.entity.primary.User;
import com.belfry.bequank.exception.AuthorityException;
import com.belfry.bequank.repository.primary.UserRepository;
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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        String nickName = object.getString("nickname");

        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
        User user = new User(userName, password, nickName, null, null, null, null, null, null, null, Role.NORMAL, time.format(new Date()), 0.0, 0.0);
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
            throw new AuthorityException();
        }

        String expectPw = user1.getPassword();
        String actualPw = user.getPassword();
        if (!expectPw.equals(actualPw)) {
            throw new AuthorityException();
        }

        String token = jwtUtil.generateToken(user1);
        String role = user1.getRole();
        object.put("status", Message.MSG_SUCCESS);
        object.put("token", token);
        object.put("role", role);
        return object;
    }

    @Override
    @CacheEvict(value = "loginList", key = "#user.userName")
    public void logout(User user) {
        logger.info("user {} logs out", user.getUserName());
    }

    @Override
    public JSONObject sendVerificationCode(String email) throws GeneralSecurityException, MessagingException {
        Properties props = new Properties();

        //同一个bean的内部方法调用不会触发cache！因此要显式使用cache
        int code = (int) (Math.random() * 900000 + 100000);
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
        message.setSubject("您的beQuank金融平台验证码");
        message.setFrom(new InternetAddress("498924217@qq.com"));


        String pre = "<p>您请求的beQuank金融平台邮箱验证服务验证码为：<font size=\"5\" color=\"red\"><b>";
        String suf = "</b></font>，如非本人操作，请忽视本信息，并尽快修改您的密码。</p>" +
                "<p>请勿将此验证码告知他人。</p><p>belfry团队</p>";

        MimeMultipart all = new MimeMultipart("related");
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(pre + code + suf, "text/html;charset=UTF-8");
        all.addBodyPart(text);

        message.setContent(all);

        Transport transport = session.getTransport();
        transport.connect("smtp.qq.com", "498924217@qq.com", "bmtdvhahtfcebjfj");

        transport.sendMessage(message, new Address[]{new InternetAddress(email)});
        transport.close();
        JSONObject object = new JSONObject();
        object.put("status", Message.MSG_SUCCESS);
        object.put("message", "发送成功");
        return object;
    }

    @Override
    public User getProfile(long userId) {
        return repository.getById(userId);
    }

    @Override
    public JSONObject setProfile(long userId, JSONObject user) {
        JSONObject object = new JSONObject();
        User user1 = repository.getById(userId);
        if (user1 == null) {
            object.put("status", Message.MSG_FAILED);
        } else {
            user1.setNickname(user.optString("nickname"));
            user1.setAvatar(user.optString("avatar"));
            user1.setPhone(user.optString("phone"));
            user1.setGender(user.optString("gender"));
            user1.setBirthday(user.optString("birthday"));
            user1.setMoneyLevel(user.optString("moneyLevel"));
            user1.setBio(user.optString("bio"));
            user1.setExpectedProfit(Double.parseDouble(user.optString("expectedProfit")));
            repository.saveAndFlush(user1);
            object.put("status", Message.MSG_SUCCESS);
        }

        return object ;
    }

    @Override
    public JSONObject setPassword(long userId, JSONObject object) {
        User user = repository.getById(userId);
        JSONObject res = new JSONObject();
        if (user == null||!user.getPassword().equals(object.getString("oriPassword"))) {
            res.put("status", Message.MSG_FAILED);
        } else {
            user.setPassword(object.getString("newPassword"));
            repository.saveAndFlush(user);
            res.put("status", Message.MSG_SUCCESS);
        }
        return res;
    }

//    @Override
//    public JSONObject getProfile(User user) {
//        JSONObject res = new JSONObject();
//
//        if (repository.findByUserName(user.getUserName()) == null) {
//            res.put("status", Message.MSG_USER_NOTEXIST);
//            res.put("message", "用户不存在");
//            return res;
//        }
//
//        res.put("nickname", user.getNickname());
//        res.put("avatar", user.getAvatar());
//        res.put("phone", user.getPhone());
//        res.put("email", user.getEmail());
//        res.put("gender", user.getGender());
//        res.put("birthday", user.getBirthday());
//        res.put("moneyLevel", user.getMoneyLevel());
//        res.put("bio", user.getBio());
//        res.put("registerTime", user.getRegisterTime());
//
//        return res;
//    }

//    @Override
//    public JSONObject setProfile(User user, JSONObject object) {
//        JSONObject res = new JSONObject();
//
//        // TODO: 18-8-29 Response Code, how to check if revision is successful?
//        res.put("status", Message.MSG_SUCCESS);
//
//        repository.setProfile(user.getUserName(), object.getString("nickname"), object.getString("avatar"), object.getString("phone"), object.getString("email"), object.getString("gender"), object.getString("birthday"), object.getString("moneyLevel"), object.getString("bio"));
//
//        return res;
//    }
//
//    @Override
//    public JSONObject setPassword(User user, JSONObject object) {
//        JSONObject res = new JSONObject();
//
//        // TODO: 18-8-29 Response Code, how to check if revision is successful?
//        res.put("status", Message.MSG_SUCCESS);
//        User u = repository.findByUserName(user.getUserName());
//        if (!u.getPassword().equals(object.getString("oriPassword"))){
//            res.put("status",Message.MSG_WRONG_PASSWORD);
//            return res;
//        }
//
//        repository.setPassword(user.getUserName(), object.getString("newPassword"));
//
//        return res;
//    }

}
