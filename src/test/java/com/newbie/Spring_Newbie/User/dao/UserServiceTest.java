package com.newbie.Spring_Newbie.User.dao;

import com.newbie.Spring_Newbie.User.domain.Level;
import com.newbie.Spring_Newbie.User.domain.User;
import com.newbie.Spring_Newbie.User.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static com.newbie.Spring_Newbie.User.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static com.newbie.Spring_Newbie.User.service.UserServiceImpl.MIN_RECCOMEND_FOR_GOLD;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/test-applicationContext.xml")
public class UserServiceTest {
    @Autowired UserService userService;
    @Autowired UserService testUserService;
    @Autowired UserDao userDao;
    @Autowired MailSender mailSender;
    @Autowired PlatformTransactionManager transactionManager;
    @Autowired ApplicationContext context;
    List<User> users;

    @Before
    public void setUp(){
        users = Arrays.asList(
                new User("user1", "user1", "pass1", "user1@ksug.org", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
                new User("user2", "user2", "pass2", "user2@ksug.org", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
                new User("user3", "user3", "pass3", "user3@ksug.org", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD-1),
                new User("user4", "user4", "pass4", "user4@ksug.org", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD),
                new User("user5", "user5", "pass5", "user5@ksug.org", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }
    @Test
    public void upgradeLevels() throws Exception{
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        MockUserDao mockUserDao = new MockUserDao(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        List<User> updated = mockUserDao.getUpdated();
        assertThat(updated.size(),is(2));
        checkUserAndLevel(updated.get(0),"user2",Level.SILVER);
        checkUserAndLevel(updated.get(1),"user4",Level.GOLD);

        List<String> request = mockMailSender.getRequests();
        assertThat(request.size(), is(2));
        assertThat(request.get(0), is(users.get(1).getEmail()));
        assertThat(request.get(1), is(users.get(3).getEmail()));
    }

    @Test
    public void mockUpgradeLevels() throws Exception{
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        UserDao mockUserDao = mock(UserDao.class);
        when(mockUserDao.getAll()).thenReturn(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        MailSender mockMailSender = mock(MailSender.class);
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        verify(mockUserDao, times(2)).update(any(User.class));
        verify(mockUserDao, times(2)).update(any(User.class));
        verify(mockUserDao).update(users.get(1));
        assertThat(users.get(1).getLevel(), is(Level.SILVER));
        verify(mockUserDao).update(users.get(3));
        assertThat(users.get(3).getLevel(), is(Level.GOLD));

        ArgumentCaptor<SimpleMailMessage> mailMessageArg = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mockMailSender, times(2)).send(mailMessageArg.capture());
        List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
        assertThat(mailMessages.get(0).getTo()[0], is(users.get(1).getEmail()));
        assertThat(mailMessages.get(1).getTo()[0], is(users.get(3).getEmail()));
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

    @Test
    public void upgradeAllOrNothing() throws Exception{
        userDao.deleteAll();
        for(User user : users) userDao.add(user);

        try{
            this.testUserService.upgradeLevels();
            fail("TestUserServiceException expected");
        }
        catch(TestUserServiceException e){
        }
        checkLevel(users.get(1),false);
    }

    @Test
    public void advisortAutoProxyCreator(){
        assertThat(testUserService, is(java.lang.reflect.Proxy.class));
    }

    @Test(expected= TransientDataAccessResourceException.class)
    public void readOnlyTransactionAttribute() {
        testUserService.getAll();
    }

    @Test
    @Transactional(propagation= Propagation.NEVER)
    public void transactionSync() {
        userService.deleteAll();
        userService.add(users.get(0));
        userService.add(users.get(1));
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

    private  void checkUserAndLevel(User updated, String expectedId, Level expectedLevel){
        assertThat(updated.getID(), is(expectedId));
        assertThat(updated.getLevel(), is(expectedLevel));
    }

    static class TestUserService extends UserServiceImpl {
        private String id = "user4"; // users(3).getId()

        protected void upgradeLevel(User user) {
            if (user.getID().equals(this.id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }

        public List<User> getAll() {
            for(User user : super.getAll()) {
                super.update(user);
            }
            return null;
        }
    }

    static class TestUserServiceException extends RuntimeException{}

    static class MockMailSender implements MailSender{
        private List<String> requests = new ArrayList<String>();

        public List<String> getRequests(){ return requests; }

        public void send(SimpleMailMessage mailMessage) throws MailException{
            requests.add(mailMessage.getTo()[0]);
        }
        public void send(SimpleMailMessage[] mailMessage) throws MailException{}
    }

    static class MockUserDao implements UserDao{
        private List<User> users;
        private List<User> updated = new ArrayList();

        private MockUserDao(List<User> users){
            this.users = users;
        }

        public List<User> getUpdated(){
            return this.updated;
        }

        public List<User> getAll(){
            return this.users;
        }

        public void update(User user){
            updated.add(user);
        }

        public void add(User user) { throw new UnsupportedOperationException(); }
        public void deleteAll() { throw new UnsupportedOperationException(); }
        public User get(String id) { throw new UnsupportedOperationException(); }
        public int getCount() { throw new UnsupportedOperationException(); }
    }

    public static void main(String[] args) {
        JUnitCore.main("com.newbie.Spring_Newbie.User.dao.UserServiceTest");
    }

}
