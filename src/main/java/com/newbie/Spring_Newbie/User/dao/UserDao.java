package com.newbie.Spring_Newbie.User.dao;

import com.newbie.Spring_Newbie.User.domain.User;
import java.util.List;

public interface UserDao {
    void add(User user);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    int getCount();
    void update(User user);
}
