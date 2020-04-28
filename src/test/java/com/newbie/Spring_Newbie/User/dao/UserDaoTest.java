package com.newbie.Spring_Newbie.User.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import com.newbie.Spring_Newbie.User.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.junit.runner.JUnitCore;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="/test-applicationContext.xml")
//@DirtiesContext
public class UserDaoTest {
    //private ApplicationContext context;
    //@Autowired
    private UserDao dao;
    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setUp(){
        dao = new UserDao();
        DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/Spring_Newbie?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","admin",true);
        dao.setDataSource(dataSource);
        /*
        System.out.println(this.context);
        System.out.println(this);
        this.dao = context.getBean("userDao",UserDao.class);
        */
        this.user1 = new User("user1","user1","pass1");
        this.user2 = new User("user2","user2","pass2");
        this.user3 = new User("user3","user3","pass3");
    }
    @Test
    public void andAndGet() throws SQLException {
        dao.deleteAll();
        assertThat(dao.getCount(),is(0));

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(),is(2));
        User userget1 = dao.get(user1.getID());
        assertThat(userget1.getName(),is(user1.getName()));
        assertThat(userget1.getPassword(),is(user1.getPassword()));

        User userget2 = dao.get(user2.getID());
        assertThat(userget2.getName(),is(user2.getName()));
        assertThat(userget2.getPassword(),is(user2.getPassword()));
    }
    @Test
    public void count() throws SQLException{
        dao.deleteAll();
        assertThat(dao.getCount(),is(0));

        dao.add(user1);
        assertThat(dao.getCount(),is(1));

        dao.add(user2);
        assertThat(dao.getCount(),is(2));

        dao.add(user3);
        assertThat(dao.getCount(),is(3));
    }
    @Test(expected=EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException{
        dao.deleteAll();
        assertThat(dao.getCount(),is(0));

        dao.get("unknown_id");
    }

    public static void main(String[] args) {
        JUnitCore.main("com.newbie.Spring_Newbie.User.dao.UserDaoTest");
    }
}