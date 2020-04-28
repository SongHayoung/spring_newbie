package com.newbie.Spring_Newbie.User.domain;

public class User {
    String id;
    String name;
    String password;
    public User(String id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }
    public User() {}
    public String getID(){
        return id;
    }
    public void setID(String id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
