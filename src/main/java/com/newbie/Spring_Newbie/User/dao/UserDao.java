package com.newbie.Spring_Newbie.User.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.newbie.Spring_Newbie.User.domain.User;
import javax.sql.DataSource;
import java.sql.*;
import java.util.EmptyStackException;

public class UserDao {
    private DataSource dataSource;
    private ConnectionMaker connectionMaker;
    public UserDao(ConnectionMaker connectionMaker){ this.connectionMaker = connectionMaker;}
    public UserDao() {}
    public void add(User user) throws SQLException{
        //Connection c = connectionMaker.makeConnection();
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("insert into users(id,name,password) values(?,?,?)");
        ps.setString(1,user.getID());
        ps.setString(2,user.getName());
        ps.setString(3,user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }
    public User get(String id) throws SQLException{
        //Connection c = connectionMaker.makeConnection();
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?");
        ps.setString(1,id);

        ResultSet rs = ps.executeQuery();
        User user = null;
        if(rs.next()) {
            user = new User();
            user.setID(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }
        rs.close();
        ps.close();
        c.close();

        if(user==null) throw new EmptyResultDataAccessException(1);
        return user;
    }
    public void deleteAll() throws SQLException{
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("delete from users");
        ps.executeUpdate();

        ps.close();
        c.close();
    }
    public int getCount() throws SQLException{
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select count(*) from users");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }
    public void setConnectionMaker(ConnectionMaker connectionMaker){  this.connectionMaker = connectionMaker; }
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }
}
