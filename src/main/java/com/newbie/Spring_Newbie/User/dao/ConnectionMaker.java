package com.newbie.Spring_Newbie.User.dao;

import java.sql.*;
public interface ConnectionMaker {
    public Connection makeConnection() throws ClassNotFoundException, SQLException;
}
