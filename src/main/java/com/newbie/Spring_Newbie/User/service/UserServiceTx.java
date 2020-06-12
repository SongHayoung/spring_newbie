package com.newbie.Spring_Newbie.User.service;

import com.newbie.Spring_Newbie.User.dao.UserDao;
import com.newbie.Spring_Newbie.User.domain.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

public class UserServiceTx implements UserService{
    UserDao userDao;
    UserService userService;
    PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager){
        this.transactionManager = transactionManager;
    }
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    public void add(User user){
        userService.add(user);
    }

    public void upgradeLevels(){
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            userService.upgradeLevels();
            this.transactionManager.commit(status);
        }
        catch (RuntimeException e){
            this.transactionManager.rollback(status);
            throw e;
        }
    }

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    public void deleteAll() { 	userDao.deleteAll(); }
    public User get(String id) { return userDao.get(id); }
    public List<User> getAll() { return userDao.getAll(); }
    public void update(User user) { userDao.update(user); }
}
