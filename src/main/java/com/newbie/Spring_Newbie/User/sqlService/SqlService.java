package com.newbie.Spring_Newbie.User.sqlService;

public interface SqlService {
    String getSql(String key) throws SqlRetrievalFailureException;
}
