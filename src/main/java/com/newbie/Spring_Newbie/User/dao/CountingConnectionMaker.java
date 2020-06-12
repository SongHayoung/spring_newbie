package com.newbie.Spring_Newbie.User.dao;
import java.sql.*;
public class CountingConnectionMaker implements ConnectionMaker{
    int counter = 0;
    private ConnectionMaker realConnectionMaker;

    public CountingConnectionMaker(ConnectionMaker realConnectionMaker){
        this.realConnectionMaker = realConnectionMaker;
    }

    public Connection makeConnection() throws ClassNotFoundException, SQLException{
        this.counter++;
        return realConnectionMaker.makeConnection();
    }

    public int getCounter(){
        return this.counter;
    }
}
