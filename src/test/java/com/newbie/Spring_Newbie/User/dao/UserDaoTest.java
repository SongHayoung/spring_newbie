package com.newbie.Spring_Newbie.User.dao;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.newbie.Spring_Newbie.User.domain.Level;
import com.newbie.Spring_Newbie.User.domain.User;
import static org.hamcrest.Matcher.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.SpringVersion;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.junit.runner.JUnitCore;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/test-applicationContext.xml")
public class UserDaoTest {
    @Autowired ApplicationContext context;
    @Autowired DataSource dataSource;
    private UserDao dao;
    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setUp(){
        this.dao = context.getBean("userDao", UserDao.class);
        this.user1 = new User("user1","user1","pass1", "user1@ksug.org", Level.BASIC, 1, 0);
        this.user2 = new User("user2","user2","pass2", "user2@ksug.org", Level.SILVER, 55, 10);
        this.user3 = new User("user3","user3","pass3", "user3@ksug.org", Level.GOLD, 100, 40);
    }

    @Test
    public void andAndGet() throws SQLException {
        dao.deleteAll();
        assertThat(dao.getCount(),is(0));

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(),is(2));
        User userget1 = dao.get(user1.getID());
        checkSameUser(userget1, user1);

        User userget2 = dao.get(user2.getID());
        checkSameUser(userget2, user2);
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

    @Test
    public void getAll() throws SQLException{
        dao.deleteAll();

        List<User> users0 = dao.getAll();
        assertThat(users0.size(), is(0));

        dao.add(user1);
        List<User> users1 = dao.getAll();
        assertThat(users1.size(),is(1));
        checkSameUser(user1,users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        assertThat(users2.size(), is(2));
        checkSameUser(user1,users2.get(0));
        checkSameUser(user2,users2.get(1));

        dao.add(user3);
        List<User> users3 = dao.getAll();
        assertThat(users3.size(), is(3));
        checkSameUser(user1,users3.get(0));
        checkSameUser(user2,users3.get(1));
        checkSameUser(user3,users3.get(2));
    }

    private void checkSameUser(User user1, User user2){
        assertThat(user1.getID(), is(user2.getID()));
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getPassword(), is(user2.getPassword()));
        assertThat(user1.getLevel(),is(user2.getLevel()));
        assertThat(user1.getLogin(),is(user2.getLogin()));
        assertThat(user1.getRecommend(),is(user2.getRecommend()));
    }

    @Test
    public void sqlExceptionTranslate(){
        dao.deleteAll();

        try{
            dao.add(user1);
            dao.add(user2);
        }
        catch(DuplicateKeyException ex){
            SQLException sqlEX = (SQLException)ex.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);

            assertThat(set.translate(null, null, sqlEX),instanceOf(DuplicateKeyException.class));
        }
    }

    @Test
    public void update(){
        dao.deleteAll();

        dao.add(user1);
        dao.add(user2);

        user1.setName("USER1");
        user1.setPassword("PASS1");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);
        dao.update(user1);

        User user1update = dao.get(user1.getID());
        checkSameUser(user1, user1update);
        User user2nonupdate = dao.get(user2.getID());
        checkSameUser(user2, user2nonupdate);
    }
    public static void main(String[] args) {
        JUnitCore.main("com.newbie.Spring_Newbie.User.dao.UserDaoTest");
    }
}