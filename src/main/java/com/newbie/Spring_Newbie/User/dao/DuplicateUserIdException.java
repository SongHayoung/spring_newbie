package com.newbie.Spring_Newbie.User.dao;

public class DuplicateUserIdException extends RuntimeException{
    public DuplicateUserIdException(Throwable cause){
        super(cause);
    }
}
