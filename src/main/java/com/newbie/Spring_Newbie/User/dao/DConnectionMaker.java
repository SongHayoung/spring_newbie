package com.newbie.Spring_Newbie.User.dao;

import java.sql.*;

public class DConnectionMaker implements ConnectionMaker{
    public Connection makeConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost/Spring_Newbie?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","admin");
        return c;
    }
}
