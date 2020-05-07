package com.newbie.Spring_Newbie.User.dao;

import com.newbie.Spring_Newbie.User.domain.Level;
import com.newbie.Spring_Newbie.User.domain.User;
import com.newbie.Spring_Newbie.User.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static com.newbie.Spring_Newbie.User.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static com.newbie.Spring_Newbie.User.service.UserService.MIN_RECCOMEND_FOR_GOLD;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/test-applicationContext.xml")
public class UserServiceTest {
    @Autowired 	UserService userService;
    @Autowired UserDao userDao;
    List<User> users;

    @Before
    public void setUp(){
        users = Arrays.asList(
                new User("user1", "user1", "pass1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
                new User("user2", "user2", "pass2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
                new User("user3", "user3", "pass3", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD-1),
                new User("user4", "user4", "pass4", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD),
                new User("user5", "user5", "pass5", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }
    @Test
    public void upgradeLevels(){
        userDao.deleteAll();
        for(User user : users) userDao.add(user);

        userService.upgradeLevels();

        checkLevel(users.get(0),false);
        checkLevel(users.get(1),true);
        checkLevel(users.get(2),false);
        checkLevel(users.get(3),true);
        checkLevel(users.get(4),false);
    }

    @Test
    public void add(){
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getID());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getID());

        assertThat(userWithLevelRead.getLevel(),is(userWithLevel.getLevel()));
        assertThat(userWithoutLevelRead.getLevel(),is(Level.BASIC));
    }

    private void checkLevel(User user, boolean upgraded){
        User userUpdate = userDao.get(user.getID());
        if(upgraded){
            assertThat(userUpdate.getLevel(),is(user.getLevel().nextLevel()));
        }
        else{
            assertThat(userUpdate.getLevel(),is(user.getLevel()));
        }
    }

    public static void main(String[] args) {
        JUnitCore.main("com.newbie.Spring_Newbie.User.dao.UserServiceTest");
    }
}
