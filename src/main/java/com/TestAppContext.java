package com;

import com.newbie.Spring_Newbie.User.dao.UserDao;
import com.newbie.Spring_Newbie.User.service.DummyMailSender;
import com.newbie.Spring_Newbie.User.service.TestUserService;
import com.newbie.Spring_Newbie.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;

@Configuration
@Profile("test")
public class TestAppContext {
    @Autowired UserDao userDao;

    @Bean
    public UserService testUserService(){
        return new TestUserService();
    }

    @Bean
    public MailSender mailSender(){
        return new DummyMailSender();
    }
}
