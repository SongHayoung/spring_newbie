package com.newbie.Spring_Newbie.User.service;

import com.newbie.Spring_Newbie.User.domain.User;

public interface UserService {
    void add(User user);
    void upgradeLevels();
}
