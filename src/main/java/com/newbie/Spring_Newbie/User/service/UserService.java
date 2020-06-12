package com.newbie.Spring_Newbie.User.service;

import com.newbie.Spring_Newbie.User.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserService {
    void add(User user);
    void deleteAll();
    void update(User user);

    @Transactional(readOnly=true)
    User get(String id);

    @Transactional(readOnly=true)
    List<User> getAll();

    void upgradeLevels();
}
