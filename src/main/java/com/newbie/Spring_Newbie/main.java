package com.newbie.Spring_Newbie;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.newbie.Spring_Newbie.User.dao.*;
import com.newbie.Spring_Newbie.User.domain.*;

public class main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        //ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao dao = context.getBean("userDao",UserDao.class);
        User user = new User();
        user.setID("user1");
        user.setName("song");
        user.setPassword("pass1");

        dao.add(user);

        System.out.println(user.getID() + " success");

        User user2 = dao.get(user.getID());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getID() + " search success");

        //CountingConnectionMaker ccm = context.getBean("connectionMaker",CountingConnectionMaker.class);
        //System.out.println("Connection counter : " + ccm.getCounter());
    }
}
