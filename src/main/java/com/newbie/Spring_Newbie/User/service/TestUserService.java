package com.newbie.Spring_Newbie.User.service;

import com.newbie.Spring_Newbie.User.domain.User;

import java.util.List;

public class TestUserService extends UserServiceImpl {
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
